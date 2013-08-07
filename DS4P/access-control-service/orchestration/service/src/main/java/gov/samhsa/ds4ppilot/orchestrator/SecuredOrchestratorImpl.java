/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.ds4ppilot.orchestrator;

import gov.samhsa.ds4ppilot.common.beans.XacmlResult;
import gov.samhsa.ds4ppilot.common.exception.DS4PException;
import gov.samhsa.ds4ppilot.orchestrator.audit.AuditService;
import gov.samhsa.ds4ppilot.orchestrator.c32getter.C32Getter;
import gov.samhsa.ds4ppilot.orchestrator.contexthandler.ContextHandler;
import gov.samhsa.ds4ppilot.orchestrator.documentprocessor.DocumentProcessor;
import gov.samhsa.ds4ppilot.orchestrator.xdsbregistry.XdsbRegistry;
import gov.samhsa.ds4ppilot.orchestrator.xdsbrepository.XdsbRepository;
import gov.samhsa.ds4ppilot.schema.documentprocessor.ProcessDocumentResponse;
import gov.samhsa.ds4ppilot.schema.securedorchestrator.RegisteryStoredQueryResponse;
import gov.samhsa.ds4ppilot.schema.securedorchestrator.RetrieveDocumentSetResponse;

import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequest;
import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequest.Document;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest.DocumentRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse.DocumentResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.ws.BindingProvider;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import oasis.names.tc.ebxml_regrep.xsd.lcm._3.SubmitObjectsRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import oasis.names.tc.ebxml_regrep.xsd.query._3.ResponseOptionType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.AdhocQueryType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ClassificationType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ExtrinsicObjectType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.IdentifiableType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.SlotType1;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ValueListType;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryErrorList;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

import org.hl7.v3.Device;
import org.hl7.v3.Id;
import org.hl7.v3.PRPAIN201301UV02;
import org.hl7.v3.PRPAIN201302UV;
import org.hl7.v3.PatientIdentityFeedRequestType.ControlActProcess;
import org.hl7.v3.PatientIdentityFeedRequestType.ControlActProcess.Subject;
import org.hl7.v3.PatientIdentityFeedRequestType.ControlActProcess.Subject.RegistrationEvent;
import org.hl7.v3.PatientIdentityFeedRequestType.ControlActProcess.Subject.RegistrationEvent.Subject1;
import org.hl7.v3.PatientIdentityFeedRequestType.ControlActProcess.Subject.RegistrationEvent.Subject1.Patient;
import org.hl7.v3.PatientIdentityFeedRequestType.ControlActProcess.Subject.RegistrationEvent.Subject1.Patient.PatientPerson;
import org.hl7.v3.PatientIdentityFeedRequestType.ControlActProcess.Subject.RegistrationEvent.Subject1.Patient.PatientPerson.Addr;
import org.hl7.v3.PatientIdentityFeedRequestType.ControlActProcess.Subject.RegistrationEvent.Subject1.Patient.PatientPerson.BirthTime;
import org.hl7.v3.PatientIdentityFeedRequestType.ControlActProcess.Subject.RegistrationEvent.Subject1.Patient.PatientPerson.Name;
import org.hl7.v3.PatientIdentityFeedRequestType.Receiver;
import org.hl7.v3.PatientIdentityFeedRequestType.Sender;
import org.xml.sax.InputSource;

/**
 * The Class OrchestratorImpl.
 */
public class SecuredOrchestratorImpl implements SecuredOrchestrator {

	/** The permit. */
	private final String PERMIT = "Permit";

	/** The context handler. */
	private final ContextHandler contextHandler;

	/** The C32 getter. */
	private final C32Getter c32Getter;

	/** The document processor. */
	private final DocumentProcessor documentProcessor;

	/** The data handler to bytes converter. */
	private final DataHandlerToBytesConverter dataHandlerToBytesConverter;

	/** The xdsbRepository. */
	private final XdsbRepository xdsbRepository;

	/** The audit service. */
	private final AuditService auditService;

	/** The xdsbRegistry. */
	private final XdsbRegistry xdsbRegistry;

	/** The subject purpose of use. */
	private String subjectPurposeOfUse; // = "TREAT";

	/** The subject locality. */
	private String subjectLocality; // = "2.16.840.1.113883.3.467";

	/** The organization. */
	private String organization; // = "SAMHSA";

	/** The organization id. */
	private String organizationId; // = "FEiSystems";

	/** The resource name. */
	private String resourceName; // = "NwHINDirectSend";

	/** The resource type. */
	private String resourceType; // = "C32";

	/** The resource action. */
	private String resourceAction; // = "Execute";

	private String repositoryUniqueId;

	private String subjectEmailAddress;

	private String homeCommunityId;

	/**
	 * Instantiates a new orchestrator impl.
	 * 
	 * @param contextHandler
	 *            the context handler
	 * @param c32Getter
	 *            the C32 getter
	 * @param documentProcessor
	 *            the document processor
	 * @param dataHandlerToBytesConverter
	 *            the data handler to bytes converter
	 */
	public SecuredOrchestratorImpl(ContextHandler contextHandler,
			C32Getter c32Getter, DocumentProcessor documentProcessor,
			AuditService auditService,
			DataHandlerToBytesConverter dataHandlerToBytesConverter,
			XdsbRepository xdsbRepository, XdsbRegistry xdsbRegistry) {
		super();
		this.contextHandler = contextHandler;
		this.c32Getter = c32Getter;
		this.documentProcessor = documentProcessor;
		this.dataHandlerToBytesConverter = dataHandlerToBytesConverter;
		this.xdsbRepository = xdsbRepository;
		this.xdsbRegistry = xdsbRegistry;
		this.auditService = auditService;
	}

	@Override
	public RetrieveDocumentSetResponse retrieveDocumentSetRequest(
			String documentUniqueId, String messageId, String intendedRecipient) {
		RetrieveDocumentSetResponse retrieveDocumentSetResponse = new RetrieveDocumentSetResponse();
		RetrieveDocumentSetRequest retrieveDocumentSetRequest = new RetrieveDocumentSetRequest();
		ihe.iti.xds_b._2007.RetrieveDocumentSetResponse xdsbRetrieveDocumentSetResponse = null;
		StringWriter xacmlResponseXml = new StringWriter();
		byte[] processedPayload;

		try {
			DocumentRequest documentRequest = new DocumentRequest();
			documentRequest.setHomeCommunityId(homeCommunityId);
			documentRequest.setRepositoryUniqueId(repositoryUniqueId);
			documentRequest.setDocumentUniqueId(documentUniqueId);
			retrieveDocumentSetRequest.getDocumentRequest()
					.add(documentRequest);

			List<String> obligations = auditService
					.getObligationsByMessageId(messageId);
			String purposeOfUse = auditService
					.getPurposeOfUseByMessageId(messageId);

			XacmlResult xacmlResult = new XacmlResult();
			xacmlResult.setHomeCommunityId(homeCommunityId);
			xacmlResult.setMessageId(messageId);
			xacmlResult.setPdpDecision(PERMIT);
			xacmlResult.setPdpObligations(obligations);
			xacmlResult.setSubjectPurposeOfUse(purposeOfUse);

			JAXBContext jaxbContext = JAXBContext
					.newInstance(XacmlResult.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty("com.sun.xml.bind.xmlDeclaration",
					Boolean.FALSE);
			marshaller.marshal(xacmlResult, xacmlResponseXml);

			xdsbRetrieveDocumentSetResponse = xdsbRepository
					.retrieveDocumentSetRequest(retrieveDocumentSetRequest);

			// get original cda
			DocumentResponse documentResponse = xdsbRetrieveDocumentSetResponse
					.getDocumentResponse().get(0);
			byte[] rawDocument = documentResponse.getDocument();
			String originalDocument = new String(rawDocument);
			// System.out.println(originalC32);

			if (!isConsentDocument(originalDocument)) {
				ProcessDocumentResponse processDocumentResponse = documentProcessor
						.processDocument(originalDocument,
								xacmlResponseXml.toString(), false, true,
								"leo.smith@direct.obhita-stage.org",
								intendedRecipient, documentUniqueId);
				processedPayload = dataHandlerToBytesConverter
						.toByteArray(processDocumentResponse
								.getProcessedDocument());
				// get processed document
				String processedDocument = new String(processedPayload);
				// System.out.println("processedDoc: " + processedDocument);
				// set processed document in payload
				DocumentResponse document = new DocumentResponse();
				document.setDocument(processedDocument.getBytes());
				xdsbRetrieveDocumentSetResponse.getDocumentResponse().set(0,
						document);
				// set response from xdsb
				retrieveDocumentSetResponse
						.setReturn(marshall(xdsbRetrieveDocumentSetResponse));
				retrieveDocumentSetResponse
						.setKekEncryptionKey(processDocumentResponse
								.getKekEncryptionKey());
				retrieveDocumentSetResponse
						.setKekMaskingKey(processDocumentResponse
								.getKekMaskingKey());
				retrieveDocumentSetResponse.setMetadata(processDocumentResponse
						.getPostProcessingMetadata());
			} else {
				DocumentResponse document = new DocumentResponse();
				document.setDocument(rawDocument);
				xdsbRetrieveDocumentSetResponse.getDocumentResponse().set(0,
						document);
				// set response from xdsb
				retrieveDocumentSetResponse
						.setReturn(marshall(xdsbRetrieveDocumentSetResponse));
			}

		} catch (PropertyException e) {
			throw new DS4PException(e.toString(), e);
		} catch (JAXBException e) {
			throw new DS4PException(e.toString(), e);
		} catch (IOException e) {
			throw new DS4PException(e.toString(), e);
		} catch (Throwable e) {
			throw new DS4PException(e.toString(), e);
		}

		return retrieveDocumentSetResponse;
	}

	@Override
	public RegisteryStoredQueryResponse registeryStoredQueryRequest(
			String patientId, String messageId) {
		AdhocQueryRequest registryStoredQuery = new AdhocQueryRequest();

		ResponseOptionType responseOptionType = new ResponseOptionType();
		responseOptionType.setReturnComposedObjects(true);
		responseOptionType.setReturnType("LeafClass");
		registryStoredQuery.setResponseOption(responseOptionType);

		AdhocQueryType adhocQueryType = new AdhocQueryType();
		adhocQueryType.setId("urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d"); // FindDocuments
		// by patientId
		registryStoredQuery.setAdhocQuery(adhocQueryType);

		SlotType1 patientIdSlotType = new SlotType1();
		patientIdSlotType.setName("$XDSDocumentEntryPatientId");
		ValueListType patientIdValueListType = new ValueListType();

		if (patientId.indexOf("'") != 0) {
			patientId = "'" + patientId;
		}
		if (patientId.lastIndexOf("'") != patientId.length() - 1) {
			patientId = patientId + "'";
		}
		patientIdValueListType.getValue().add(patientId); // PatientId
		patientIdSlotType.setValueList(patientIdValueListType);
		adhocQueryType.getSlot().add(patientIdSlotType);

		SlotType1 statusSlotType = new SlotType1();
		statusSlotType.setName("$XDSDocumentEntryStatus");
		ValueListType statusValueListType = new ValueListType();
		statusValueListType.getValue().add(
				"('urn:oasis:names:tc:ebxml-regrep:StatusType:Approved')");
		statusSlotType.setValueList(statusValueListType);
		adhocQueryType.getSlot().add(statusSlotType);
		RegisteryStoredQueryResponse response = new RegisteryStoredQueryResponse();

		try {
			AdhocQueryResponse result = xdsbRegistry
					.registryStoredQuery(registryStoredQuery);

			result = getResponseWithLatestDocumentEntriesForConsentAndNonconsent(result);

			String xmlResponse = marshall(result);
			response.setReturn(xmlResponse);

		} catch (Throwable e) {
			throw new DS4PException(e.toString(), e);
		}

		return response;
	}

	/**
	 * Gets the subject purpose of use.
	 * 
	 * @return the subject purpose of use
	 */
	public String getSubjectPurposeOfUse() {
		return subjectPurposeOfUse;
	}

	/**
	 * Sets the subject purpose of use.
	 * 
	 * @param subjectPurposeOfUse
	 *            the new subject purpose of use
	 */
	public void setSubjectPurposeOfUse(String subjectPurposeOfUse) {
		this.subjectPurposeOfUse = subjectPurposeOfUse;
	}

	/**
	 * Gets the subject locality.
	 * 
	 * @return the subject locality
	 */
	public String getSubjectLocality() {
		return subjectLocality;
	}

	/**
	 * Sets the subject locality.
	 * 
	 * @param subjectLocality
	 *            the new subject locality
	 */
	public void setSubjectLocality(String subjectLocality) {
		this.subjectLocality = subjectLocality;
	}

	/**
	 * Gets the organization.
	 * 
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * Sets the organization.
	 * 
	 * @param organization
	 *            the new organization
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * Gets the organization id.
	 * 
	 * @return the organization id
	 */
	public String getOrganizationId() {
		return organizationId;
	}

	/**
	 * Sets the organization id.
	 * 
	 * @param organizationId
	 *            the new organization id
	 */
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getRepositoryUniqueId() {
		return repositoryUniqueId;
	}

	public void setRepositoryUniqueId(String repositoryUniqueId) {
		this.repositoryUniqueId = repositoryUniqueId;
	}

	public String getHomeCommunityId() {
		return homeCommunityId;
	}

	public void setHomeCommunityId(String homeCommunityId) {
		this.homeCommunityId = homeCommunityId;
	}

	public String getSubjectEmailAddress() {
		return subjectEmailAddress;
	}

	public void setSubjectEmailAddress(String subjectEmailAddress) {
		this.subjectEmailAddress = subjectEmailAddress;
	}

	/**
	 * Gets the resource name.
	 * 
	 * @return the resource name
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * Sets the resource name.
	 * 
	 * @param resourceName
	 *            the new resource name
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * Gets the resource type.
	 * 
	 * @return the resource type
	 */
	public String getResourceType() {
		return resourceType;
	}

	/**
	 * Sets the resource type.
	 * 
	 * @param resourceType
	 *            the new resource type
	 */
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	/**
	 * Gets the resource action.
	 * 
	 * @return the resource action
	 */
	public String getResourceAction() {
		return resourceAction;
	}

	/**
	 * Sets the resource action.
	 * 
	 * @param resourceAction
	 *            the new resource action
	 */
	public void setResourceAction(String resourceAction) {
		this.resourceAction = resourceAction;
	}

	private static String marshall(Object obj) throws Throwable {
		final JAXBContext context = JAXBContext.newInstance(obj.getClass());
		Marshaller marshaller = context.createMarshaller();

		StringWriter stringWriter = new StringWriter();
		marshaller.marshal(obj, stringWriter);

		return stringWriter.toString();
	}

	private static <T> T unmarshallFromXml(Class<T> clazz, String xml)
			throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Unmarshaller um = context.createUnmarshaller();
		ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes());
		return (T) um.unmarshal(input);
	}

	private static org.w3c.dom.Document loadXmlFrom(String xml)
			throws Exception {
		InputSource is = new InputSource(new StringReader(xml));
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = null;
		builder = factory.newDocumentBuilder();
		org.w3c.dom.Document document = builder.parse(is);
		return document;
	}

	private static AdhocQueryResponse getResponseWithLatestDocumentEntriesForConsentAndNonconsent(
			AdhocQueryResponse adhocQueryResponse) {
		int documentEntryCount = adhocQueryResponse.getRegistryObjectList()
				.getIdentifiable().size();
		if (documentEntryCount >= 2) {
			int theLatestConsentDocumentEntryIndex = -1;
			Date theLatestConsentDocumentEntryCreationTime = new Date(
					Long.MIN_VALUE);

			int theLatestNonConsentDocumentEntryIndex = -1;
			Date theLatestNonConsentDocumentEntryCreationTime = new Date(
					Long.MIN_VALUE);

			for (int index = 0; index < documentEntryCount; index++) {
				JAXBElement<?> jaxbElement = adhocQueryResponse
						.getRegistryObjectList().getIdentifiable().get(index);

				@SuppressWarnings("unchecked")
				JAXBElement<ExtrinsicObjectType> jaxbElementOfExtrinsicObjectType = (JAXBElement<ExtrinsicObjectType>) (jaxbElement);
				if (!jaxbElementOfExtrinsicObjectType.equals(null)) {
					ExtrinsicObjectType extrinsicObjectType = jaxbElementOfExtrinsicObjectType
							.getValue();

					boolean isConsentDocumentEntry = false;

					// Get the classCode (Consent or others)
					for (ClassificationType classificationType : extrinsicObjectType
							.getClassification()) {
						if (classificationType
								.getClassificationScheme()
								.equalsIgnoreCase(
										"urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a")) {
							if (classificationType.getNodeRepresentation()
									.equalsIgnoreCase("Consent")) {
								isConsentDocumentEntry = true;
							}
						}
					}

					for (SlotType1 slotType1 : extrinsicObjectType.getSlot()) {
						if (slotType1.getName().equals("creationTime")) {
							String datetimeString = slotType1.getValueList()
									.getValue().get(0);
							int lengthOfDateTimeString = datetimeString
									.length();

							int year = lengthOfDateTimeString >= 4 ? Integer
									.parseInt(datetimeString.substring(0, 4))
									: 0;
							int month = lengthOfDateTimeString >= 6 ? Integer
									.parseInt(datetimeString.substring(4, 6))
									: 0;
							int day = lengthOfDateTimeString >= 8 ? Integer
									.parseInt(datetimeString.substring(6, 8))
									: 0;
							int hour = lengthOfDateTimeString >= 10 ? Integer
									.parseInt(datetimeString.substring(8, 10))
									: 0;
							int minute = lengthOfDateTimeString >= 12 ? Integer
									.parseInt(datetimeString.substring(10, 12))
									: 0;
							int second = lengthOfDateTimeString >= 14 ? Integer
									.parseInt(datetimeString.substring(12, 14))
									: 0;

							GregorianCalendar gregorianCalendar = new GregorianCalendar(
									year, month, day, hour, minute, second);

							Date creationTime = gregorianCalendar.getTime();

							if (isConsentDocumentEntry
									&& creationTime
											.after(theLatestConsentDocumentEntryCreationTime)) {
								theLatestConsentDocumentEntryCreationTime = creationTime;
								theLatestConsentDocumentEntryIndex = index;
							} else if (!isConsentDocumentEntry
									&& creationTime
											.after(theLatestNonConsentDocumentEntryCreationTime)) {
								theLatestNonConsentDocumentEntryCreationTime = creationTime;
								theLatestNonConsentDocumentEntryIndex = index;
							}
						}
					}
				}
			}

			List<JAXBElement<? extends IdentifiableType>> latestDocumentEntryList = new ArrayList<JAXBElement<? extends IdentifiableType>>();

			if (theLatestConsentDocumentEntryIndex != -1) {
				latestDocumentEntryList.add(adhocQueryResponse
						.getRegistryObjectList().getIdentifiable()
						.get(theLatestConsentDocumentEntryIndex));
			}

			if (theLatestNonConsentDocumentEntryIndex != -1) {

				latestDocumentEntryList.add(adhocQueryResponse
						.getRegistryObjectList().getIdentifiable()
						.get(theLatestNonConsentDocumentEntryIndex));
			}

			if (latestDocumentEntryList.size() > 0) {
				adhocQueryResponse.getRegistryObjectList().getIdentifiable()
						.clear();
			}

			for (int i = 0; i < latestDocumentEntryList.size(); i++) {
				adhocQueryResponse.getRegistryObjectList().getIdentifiable()
						.add(latestDocumentEntryList.get(i));
			}
		}

		/*try {
			System.out.println(marshall(adhocQueryResponse));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		return adhocQueryResponse;
	}

	private boolean isConsentDocument(String originalDocument) {
		boolean consentDocumentExists = false;

		try {
			final String hl7Namespace = "urn:hl7-org:v3";
			final String hl7NamespacePrefix = "hl7";

			org.w3c.dom.Document document = loadXmlFrom(originalDocument);

			// We map the prefixes to URIs
			NamespaceContext namespaceContext = new NamespaceContext() {
				@Override
				public String getNamespaceURI(String prefix) {
					String uri;
					if (prefix.equals(hl7NamespacePrefix))
						uri = hl7Namespace;
					else
						throw new IllegalArgumentException(prefix);
					return uri;
				}

				@Override
				public Iterator<?> getPrefixes(String val) {
					throw new UnsupportedOperationException();
				}

				@Override
				public String getPrefix(String uri) {
					throw new UnsupportedOperationException();
				}
			};

			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			xpath.setNamespaceContext(namespaceContext);

			String xpathExpression = "count(//*[@root='2.16.840.1.113883.3.445.1']) > 0";
			consentDocumentExists = (Boolean) xpath.evaluate(xpathExpression,
					document, XPathConstants.BOOLEAN);

		} catch (Exception e) {
			throw new DS4PException(
					"Error occurred when getting the templateId count for consent from CDA document.",
					e);
		}

		return consentDocumentExists;
	}

	public static boolean patientExistsInRegistyBeforeAdding(
			String responseOfAddPatient) {

		boolean patientExistsInRegistyBeforeAdding = false;

		try {
			// TODO: Refactor these code to a new class to be testable
			final String hl7Namespace = "urn:hl7-org:v3";
			final String hl7NamespacePrefix = "hl7";

			org.w3c.dom.Document document = loadXmlFrom(responseOfAddPatient);

			// We map the prefixes to URIs
			NamespaceContext namespaceContext = new NamespaceContext() {
				@Override
				public String getNamespaceURI(String prefix) {
					String uri;
					if (prefix.equals(hl7NamespacePrefix))
						uri = hl7Namespace;
					else
						throw new IllegalArgumentException(prefix);
					return uri;
				}

				@Override
				public Iterator<?> getPrefixes(String val) {
					throw new UnsupportedOperationException();
				}

				@Override
				public String getPrefix(String uri) {
					throw new UnsupportedOperationException();
				}
			};

			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			xpath.setNamespaceContext(namespaceContext);

			// Get acknowledgment type code
			String xpathForAcknowledgementTypeCode = String.format(
					"//%s:acknowledgement/%s:typeCode/@code",
					hl7NamespacePrefix, hl7NamespacePrefix);
			String acknowledgementTypeCode = xpath.evaluate(
					xpathForAcknowledgementTypeCode, document);

			if (acknowledgementTypeCode.equals("CE")) {
				patientExistsInRegistyBeforeAdding = true;
			}

		} catch (Exception e) {
			throw new DS4PException(
					"Error occurred when getting the patient id and other patient demographic information from CDA document.",
					e);
		}

		return patientExistsInRegistyBeforeAdding;
	}
}
