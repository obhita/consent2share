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
package gov.samhsa.acs.pep;

import gov.samhsa.acs.brms.domain.SubjectPurposeOfUse;
import gov.samhsa.acs.brms.domain.XacmlResult;
import gov.samhsa.acs.common.dto.XacmlRequest;
import gov.samhsa.acs.common.dto.XacmlResponse;
import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.namespace.PepNamespaceContext;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.contexthandler.ContextHandler;
import gov.samhsa.acs.contexthandler.exception.NoPolicyFoundException;
import gov.samhsa.acs.documentsegmentation.DocumentSegmentation;
import gov.samhsa.acs.documentsegmentation.exception.InvalidOriginalClinicalDocumentException;
import gov.samhsa.acs.documentsegmentation.exception.InvalidSegmentedClinicalDocumentException;
import gov.samhsa.acs.pep.c32getter.C32Getter;
import gov.samhsa.acs.pep.saml.SamlTokenParser;
import gov.samhsa.acs.pep.xdsbregistry.XdsbRegistry;
import gov.samhsa.acs.pep.xdsbrepository.XdsbRepository;
import gov.samhsa.acs.xdsb.common.UniqueOidProviderImpl;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.acs.xdsb.common.XdsbMetadataGeneratorImpl;
import gov.samhsa.consent2share.schema.documentsegmentation.SegmentDocumentResponse;
import gov.samhsa.ds4ppilot.schema.pep.FilterC32Response;
import gov.samhsa.ds4ppilot.schema.pep.PushC32Request;
import gov.samhsa.ds4ppilot.schema.pep.PushC32Response;
import gov.samhsa.ds4ppilot.schema.pep.RegisteryStoredQueryResponse;
import gov.samhsa.ds4ppilot.schema.pep.RetrieveDocumentSetResponse;
import gov.va.ehtac.ds4p.ws.EnforcePolicy;
import gov.va.ehtac.ds4p.ws.EnforcePolicyResponse.Return;
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
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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

import org.apache.ws.security.SAMLTokenPrincipal;
import org.apache.ws.security.saml.ext.bean.AttributeBean;
import org.hl7.v3.Device;
import org.hl7.v3.Id;
import org.hl7.v3.PRPAIN201301UV02;
import org.hl7.v3.PRPAIN201302UV02;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

/**
 * The Class PepImpl.
 */
public class PepImpl implements Pep {

	/** The permit. */
	private final String PERMIT = "Permit";

	/** The context handler. */
	private final ContextHandler contextHandler;

	/** The C32 getter. */
	private final C32Getter c32Getter;

	/** The document segmentation service. */
	private final DocumentSegmentation documentSegmentation;

	/** The data handler to bytes converter. */
	private final DataHandlerToBytesConverter dataHandlerToBytesConverter;

	/** The xdsbRepository. */
	private final XdsbRepository xdsbRepository;

	/** The xdsbRegistry. */
	private final XdsbRegistry xdsbRegistry;

	/** The marshaller. */
	private final SimpleMarshaller marshaller;

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

	/** The home community id. */
	private String homeCommunityId;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PepImpl.class);

	/**
	 * Instantiates a new pep impl.
	 * 
	 * @param contextHandler
	 *            the context handler
	 * @param c32Getter
	 *            the C32 getter
	 * @param documentSegmentation
	 *            the document segmentation
	 * @param dataHandlerToBytesConverter
	 *            the data handler to bytes converter
	 * @param xdsbRepository
	 *            the xdsb repository
	 * @param xdsbRegistry
	 *            the xdsb registry
	 * @param marshaller
	 *            the marshaller
	 */
	public PepImpl(ContextHandler contextHandler, C32Getter c32Getter,
			DocumentSegmentation documentSegmentation,
			DataHandlerToBytesConverter dataHandlerToBytesConverter,
			XdsbRepository xdsbRepository, XdsbRegistry xdsbRegistry,
			SimpleMarshaller marshaller) {
		super();
		this.contextHandler = contextHandler;
		this.c32Getter = c32Getter;
		this.documentSegmentation = documentSegmentation;
		this.dataHandlerToBytesConverter = dataHandlerToBytesConverter;
		this.xdsbRepository = xdsbRepository;
		this.xdsbRegistry = xdsbRegistry;
		this.marshaller = marshaller;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Pep#handleC32Request(java. lang.String, boolean, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public FilterC32Response handleC32Request(String patientId,
			boolean packageAsXdm, String senderEmailAddress,
			String recipientEmailAddress) {
		StringWriter xacmlResponseXml = new StringWriter();
		byte[] processedPayload;
		FilterC32Response c32Response = new FilterC32Response();
		c32Response.setPatientId(patientId);

		Return result = null;
		try {
			EnforcePolicy.Xspasubject xspasubject = setXspaSubject(
					recipientEmailAddress, UUID.randomUUID().toString());
			EnforcePolicy.Xsparesource xsparesource = setXspaResource(patientId);

			result = null;
		} catch (Exception e) {
			throw new DS4PException(e.toString(), e);
		}

		c32Response.setPdpDecision(result.getPdpDecision());

		if (result.getPdpDecision().equals(PERMIT)) {
			String originalC32 = c32Getter.getC32(patientId);

			try {
				XacmlResult xacmlResult = getXacmlResponse(result);

				JAXBContext jaxbContext = JAXBContext
						.newInstance(XacmlResult.class);
				Marshaller marshaller = jaxbContext.createMarshaller();
				marshaller.setProperty("com.sun.xml.bind.xmlDeclaration",
						Boolean.FALSE);
				marshaller.marshal(xacmlResult, xacmlResponseXml);

				SegmentDocumentResponse segmentDocumentResponse = documentSegmentation
						.segmentDocument(originalC32,
								xacmlResponseXml.toString(), packageAsXdm,
								true, senderEmailAddress,
								recipientEmailAddress, "");

				processedPayload = dataHandlerToBytesConverter
						.toByteArray(segmentDocumentResponse
								.getProcessedDocument());

				c32Response.setMaskedDocument(segmentDocumentResponse
						.getMaskedDocument());
				c32Response.setFilteredStreamBody(processedPayload);
			} catch (PropertyException e) {
				throw new DS4PException(e.toString(), e);
			} catch (JAXBException e) {
				throw new DS4PException(e.toString(), e);
			} catch (IOException e) {
				throw new DS4PException(e.toString(), e);
			} catch (InvalidOriginalClinicalDocumentException e) {
				throw new DS4PException(e.toString(), e);
			} catch (InvalidSegmentedClinicalDocumentException e) {
				throw new DS4PException(e.toString(), e);
			}
		}

		return c32Response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Pep#handleC32Request(java. lang.String, boolean, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public FilterC32Response handleC32Request(String originalC32,
			String senderNpi, String recipientNpi) {
		
//		
//		if (samlTokenParser == null) {
//			samlTokenParser = new SamlTokenParser();
//		}
//	    samlTokenPrincipal = (SAMLTokenPrincipal)(context.getUserPrincipal());
//		
//		String resourceId = samlTokenParser.parse(samlTokenPrincipal.getToken(), "urn:oasis:names:tc:xacml:1.0:resource:resource-id");
//		String intermediarySubject = samlTokenParser.parse(samlTokenPrincipal.getToken(), "urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject");
//		String purposeOfUse = samlTokenParser.parse(samlTokenPrincipal.getToken(), "urn:oasis:names:tc:xspa:1.0:subject:purposeofuse");
//		String recipientSubject = samlTokenParser.parse(samlTokenPrincipal.getToken(), "urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject");
//		XacmlRequest xacmlRequest = setXacmlRequest(resourceId, purposeOfUse, intermediarySubject, recipientSubject);
//		
//		
//		if (!validateRetrieveDocumentSetRequest(input)) {
//			return xdsbErrorFactory
//					.errorRetrieveDocumentSetResponseMultipleRepositoryId();
//		}
//		
//		XacmlResponse xacmlResponse = null;
//		try{
//			xacmlResponse = contextHandler
//				.enforcePolicy(xacmlRequest);
//		}
//		catch(NoPolicyFoundException e){
//			logger.error(e.getMessage(), e);
//			return xdsbErrorFactory.errorRetrieveDocumentSetResponseNoConsentsFound(resourceId);
//		}
//		if (PERMIT.equals(xacmlResponse.getPdpDecision())) {
		
		
		

			
			
			return null;
			
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see Pep#handleC32Request(java. lang.String, boolean, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public FilterC32Response handleC32Request(String recepientSubjectNPI,
			String intermediarySubjectNPI, String resourceId,
			boolean packageAsXdm, String senderEmailAddress,
			String recipientEmailAddress, String xdsDocumentEntryUniqueId) {
		StringWriter xacmlResponseXml = new StringWriter();
		byte[] processedPayload;
		FilterC32Response c32Response = new FilterC32Response();
		c32Response.setPatientId(resourceId);

		XacmlResponse xacmlResponse = null;
		XacmlRequest xacmlRequest = null;
		try {

			xacmlRequest = setXacmlRequest(recepientSubjectNPI,
					intermediarySubjectNPI, subjectPurposeOfUse, resourceId,
					UUID.randomUUID().toString());
			xacmlResponse = contextHandler.enforcePolicy(xacmlRequest);

		} catch (Exception e) {
			throw new DS4PException(e.toString(), e);
		}

		c32Response.setPdpDecision(xacmlResponse.getPdpDecision());

		if (xacmlResponse.getPdpDecision().toLowerCase()
				.equals(PERMIT.toLowerCase())) {

			String originalC32 = c32Getter.getC32(resourceId);

			try {
				XacmlResult xacmlResult = getXacmlResponse(xacmlRequest,
						xacmlResponse);

				JAXBContext jaxbContext = JAXBContext
						.newInstance(XacmlResult.class);
				Marshaller marshaller = jaxbContext.createMarshaller();
				marshaller.setProperty("com.sun.xml.bind.xmlDeclaration",
						Boolean.FALSE);
				marshaller.marshal(xacmlResult, xacmlResponseXml);

				SegmentDocumentResponse segmentDocumentResponse = documentSegmentation
						.segmentDocument(originalC32,
								xacmlResponseXml.toString(), packageAsXdm,
								true, senderEmailAddress,
								recipientEmailAddress, xdsDocumentEntryUniqueId);

				processedPayload = dataHandlerToBytesConverter
						.toByteArray(segmentDocumentResponse
								.getProcessedDocument());

				c32Response.setMaskedDocument(segmentDocumentResponse
						.getMaskedDocument());
				c32Response.setFilteredStreamBody(processedPayload);
			} catch (PropertyException e) {
				throw new DS4PException(e.toString(), e);
			} catch (JAXBException e) {
				throw new DS4PException(e.toString(), e);
			} catch (IOException e) {
				throw new DS4PException(e.toString(), e);
			} catch (InvalidOriginalClinicalDocumentException e) {
				throw new DS4PException(e.toString(), e);
			} catch (InvalidSegmentedClinicalDocumentException e) {
				throw new DS4PException(e.toString(), e);
			}
		}

		return c32Response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Pep#saveDocumentSetToXdsRepository(java.lang.String)
	 */
	@Override
	public boolean saveDocumentSetToXdsRepository(String documentSet) {

		String patientId = null;
		String patientLastName = null;
		String patientFirstName = null;
		String patientAddressLine = null;
		String patientCity = null;
		String patientState = null;
		String patientBirthDate = null;

		try {
			// TODO: Refactor these code to a new class to be testable
			org.w3c.dom.Document document = loadXmlFrom(documentSet);

			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			xpath.setNamespaceContext(new PepNamespaceContext());

			// Get patient id
			String xpathForPatientId = "//hl7:recordTarget/hl7:patientRole/hl7:id/@extension[1]";
			patientId = xpath.evaluate(xpathForPatientId, document);

			// Get patient last name
			String xpathForLastName = "//hl7:patientRole/hl7:patient/hl7:name/hl7:family/text()";
			patientLastName = xpath.evaluate(xpathForLastName, document);

			// Get patient first name
			String xpathForFirstName = "//hl7:patientRole/hl7:patient/hl7:name/hl7:given[1]/text()";
			patientFirstName = xpath.evaluate(xpathForFirstName, document);

			// Get patient address line
			String xpathForAddressLine = "//hl7:patientRole/hl7:addr/hl7:streetAddressLine[1]";
			patientAddressLine = xpath.evaluate(xpathForAddressLine, document);

			// Get patient city
			String xpathForCity = "//hl7:patientRole/hl7:addr/hl7:city";
			patientCity = xpath.evaluate(xpathForCity, document);

			// Get patient state
			String xpathForState = "//hl7:patientRole/hl7:addr/hl7:state";
			patientState = xpath.evaluate(xpathForState, document);

			// Get patient birth date
			String xpathForBirthDate = "//hl7:patientRole/hl7:patient/hl7:birthTime/@value";
			patientBirthDate = xpath.evaluate(xpathForBirthDate, document);

		} catch (Exception e) {
			throw new DS4PException(
					"Error occurred when getting the patient id and other patient demographic information from CDA document.",
					e);
		}

		// PatientPerson
		PatientPerson patientPerson = new PatientPerson();
		Name name = new Name();
		name.setFamily(patientLastName);
		name.setGiven(patientFirstName);
		patientPerson.setName(name);

		BirthTime birthTime = new BirthTime();
		birthTime.setValue(patientBirthDate);
		patientPerson.setBirthTime(birthTime);

		Addr addr = new Addr();
		addr.setStreetAddressLine(patientAddressLine);
		addr.setCity(patientCity);
		addr.setState(patientState);
		patientPerson.getAddr().add(addr);

		// Patient
		Patient patient = new Patient();
		Id patientHl7Id = new Id();
		patientHl7Id.setRoot(subjectLocality); // Domain Id (Home Community Id)
		patientHl7Id.setExtension(patientId); // PatientId in the domain
		patient.setId(patientHl7Id);
		patient.setPatientPerson(patientPerson);

		// Subject 1
		Subject1 subject1 = new Subject1();
		subject1.setPatient(patient);

		// RegistrationEvent
		RegistrationEvent registrationEvent = new RegistrationEvent();
		registrationEvent.setSubject1(subject1);

		// Subject
		Subject subject = new Subject();
		subject.setRegistrationEvent(registrationEvent);

		// ControlActProcess
		ControlActProcess controlActProcess = new ControlActProcess();
		controlActProcess.setSubject(subject);

		// PRPAIN201301UV02
		PRPAIN201301UV02 prpain201301uv02 = new PRPAIN201301UV02();
		prpain201301uv02.setControlActProcess(controlActProcess);

		Id PRPAIN201302UVId = new Id();
		PRPAIN201302UVId.setRoot("cdc0d3fa-4467-11dc-a6be-3603d686610257");
		prpain201301uv02.setId(PRPAIN201302UVId);

		Receiver receiver = new Receiver();
		receiver.setTypeCode("RCV");
		Device receiverDevice = new Device();
		receiverDevice.setDeterminerCode("INSTANCE");
		Id receiverDeviceId = new Id();
		receiverDeviceId.setRoot("1.2.840.114350.1.13.99999.4567");
		receiverDevice.setId(receiverDeviceId);
		receiver.setDevice(receiverDevice);
		prpain201301uv02.setReceiver(receiver);

		Sender sender = new Sender();
		sender.setTypeCode("SND");
		Device senderDevice = new Device();
		senderDevice.setDeterminerCode("INSTANCE");
		Id senderDeviceId = new Id();
		senderDeviceId.setRoot("1.2.840.114350.1.13.99998.8734");
		senderDevice.setId(senderDeviceId);
		sender.setDevice(senderDevice);
		prpain201301uv02.setSender(sender);

		// First try to add patient to XdsbRegistry
		String responseOfAddPatient = xdsbRegistry
				.addPatientRegistryRecord(prpain201301uv02);

		if (patientExistsInRegistyBeforeAdding(responseOfAddPatient)) {
			// Try to revise patient
			// PRPAIN201302UV
			PRPAIN201302UV02 prpain201302uv = new PRPAIN201302UV02();

			prpain201302uv.setControlActProcess(controlActProcess);

			prpain201302uv.setId(PRPAIN201302UVId);

			prpain201302uv.setReceiver(receiver);

			prpain201302uv.setSender(sender);

			String result = xdsbRegistry
					.revisePatientRegistryRecord(prpain201302uv);

			// TODO: Check the result here to see if the CA code is return. If
			// not throws exception
			// LOGGER.debug(result);
		}

		String metadataString = new XdsbMetadataGeneratorImpl(
				new UniqueOidProviderImpl(),
				XdsbDocumentType.CLINICAL_DOCUMENT, marshaller)
				.generateMetadataXml(documentSet, subjectLocality, null, null);

		SubmitObjectsRequest submitObjectRequest = null;

		// Marshal this metadata string to SubmitObjectsRequest
		try {
			submitObjectRequest = unmarshallFromXml(SubmitObjectsRequest.class,
					metadataString);
		} catch (JAXBException e1) {
			LOGGER.debug(e1.toString(), e1);
		}

		// LOGGER.debug(metadataString);

		String documentId = null;

		// Get the document id from meta data
		try {
			org.w3c.dom.Document document = loadXmlFrom(metadataString);

			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			xpath.setNamespaceContext(new PepNamespaceContext());

			// Get document id
			String xpathForDocumentId = "//rim:ExtrinsicObject/@id[1]";
			documentId = xpath.evaluate(xpathForDocumentId, document);

		} catch (Exception e1) {
			LOGGER.debug(e1.toString(), e1);
		}

		Document document = new Document();
		document.setId(documentId);
		document.setValue(documentSet.getBytes());

		ProvideAndRegisterDocumentSetRequest request = new ProvideAndRegisterDocumentSetRequest();
		request.getDocument().add(document);
		request.setSubmitObjectsRequest(submitObjectRequest);

		RegistryResponse registryResponse = null;
		try {
			registryResponse = xdsbRepository
					.provideAndRegisterDocumentSetRequest(request);

			/*
			 * try { LOGGER.debug(marshall(registryResponse)); } catch
			 * (Throwable e) { LOGGER.debug(e.toString(),e); }
			 */

			RegistryErrorList registryErrorList = registryResponse
					.getRegistryErrorList();

			if (registryErrorList != null
					&& registryErrorList.getRegistryError().size() > 0)
				return false;

		} catch (Exception e) {
			throw new DS4PException(
					"Document cannot be saved to the XDS repository.", e);
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Pep#retrieveDocumentSetRequest(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, gov.va.ehtac.ds4p.ws.EnforcePolicy)
	 */
	@Override
	public RetrieveDocumentSetResponse retrieveDocumentSetRequest(
			String homeCommunityId, String repositoryUniqueId,
			String documentUniqueId, String messageId,
			EnforcePolicy enforcePolicy) {
		RetrieveDocumentSetResponse retrieveDocumentSetResponse = new RetrieveDocumentSetResponse();
		RetrieveDocumentSetRequest retrieveDocumentSetRequest = new RetrieveDocumentSetRequest();
		ihe.iti.xds_b._2007.RetrieveDocumentSetResponse xdsbRetrieveDocumentSetResponse = null;
		StringWriter xacmlResponseXml = new StringWriter();
		byte[] processedPayload;
		Return result = null;

		try {
			DocumentRequest documentRequest = new DocumentRequest();
			documentRequest.setHomeCommunityId(homeCommunityId);
			documentRequest.setRepositoryUniqueId(repositoryUniqueId);
			documentRequest.setDocumentUniqueId(documentUniqueId);
			retrieveDocumentSetRequest.getDocumentRequest()
					.add(documentRequest);

			// TODO: Remove this class after checking
			result = null;

			if (result.getPdpDecision().equals(PERMIT)) {

				XacmlResult xacmlResult = getXacmlResponse(result);

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
				// LOGGER.debug(originalC32);

				if (!isConsentDocument(originalDocument)) {
					SegmentDocumentResponse segmentDocumentResponse = documentSegmentation
							.segmentDocument(originalDocument, xacmlResponseXml
									.toString(), /* "<xacmlResult><pdpDecision>Permit</pdpDecision><purposeOfUse>TREAT</purposeOfUse><messageId>4617a579-1881-4e40-9f98-f85bd81d6502</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:org:us-privacy-law:42CFRPart2</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:org:refrain-policy:NORDSLCD</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:redact:ETH</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:redact:PSY</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:mask:HIV</pdpObligation></xacmlResult>" */
									false, true,
									"leo.smith@direct.obhita-stage.org",
									enforcePolicy.getXspasubject()
											.getSubjectEmailAddress(), "");
					processedPayload = dataHandlerToBytesConverter
							.toByteArray(segmentDocumentResponse
									.getProcessedDocument());
					// get processed document
					String processedDocument = new String(processedPayload);
					// LOGGER.debug("processedDoc: " + processedDocument);
					// set processed document in payload
					DocumentResponse document = new DocumentResponse();
					document.setDocument(processedDocument.getBytes());
					xdsbRetrieveDocumentSetResponse.getDocumentResponse().set(
							0, document);
					// set response from xdsb
					retrieveDocumentSetResponse
							.setReturn(marshall(xdsbRetrieveDocumentSetResponse));
					retrieveDocumentSetResponse
							.setKekEncryptionKey(segmentDocumentResponse
									.getKekEncryptionKey());
					retrieveDocumentSetResponse
							.setKekMaskingKey(segmentDocumentResponse
									.getKekMaskingKey());
					retrieveDocumentSetResponse
							.setMetadata(segmentDocumentResponse
									.getPostProcessingMetadata());
				} else {
					DocumentResponse document = new DocumentResponse();
					document.setDocument(rawDocument);
					xdsbRetrieveDocumentSetResponse.getDocumentResponse().set(
							0, document);
					// set response from xdsb
					retrieveDocumentSetResponse
							.setReturn(marshall(xdsbRetrieveDocumentSetResponse));
				}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see Pep#registeryStoredQueryRequest(java.lang.String,
	 * gov.va.ehtac.ds4p.ws.EnforcePolicy)
	 */
	@Override
	public RegisteryStoredQueryResponse registeryStoredQueryRequest(
			String patientId, EnforcePolicy enforcePolicy) {
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

		Return enforcePolicyResult = null;
		try {

			enforcePolicyResult = null;

			// verify identify of the individual and return decision
			if (enforcePolicyResult.getPdpDecision().equals(PERMIT)) {

				AdhocQueryResponse result = xdsbRegistry
						.registryStoredQuery(registryStoredQuery);

				result = getResponseWithLatestDocumentEntriesForConsentAndNonconsent(result);

				String xmlResponse = marshall(result);
				response.setReturn(xmlResponse);
			}
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

	/**
	 * Gets the home community id.
	 * 
	 * @return the home community id
	 */
	public String getHomeCommunityId() {
		return homeCommunityId;
	}

	/**
	 * Sets the home community id.
	 * 
	 * @param homeCommunityId
	 *            the new home community id
	 */
	public void setHomeCommunityId(String homeCommunityId) {
		this.homeCommunityId = homeCommunityId;
	}

	/**
	 * Sets the xspa resource.
	 * 
	 * @param patientId
	 *            the patient id
	 * @return the enforce policy. xsparesource
	 */
	public EnforcePolicy.Xsparesource setXspaResource(String patientId) {
		EnforcePolicy.Xsparesource xsparesource = new EnforcePolicy.Xsparesource();
		xsparesource.setResourceId(patientId);
		xsparesource.setResourceName(resourceName);
		xsparesource.setResourceType(resourceType);
		xsparesource.setResourceAction(resourceAction);
		return xsparesource;
	}

	/**
	 * Sets the xspa subject.
	 * 
	 * @param recipientEmailAddress
	 *            the recipient email address
	 * @param messageId
	 *            the message id
	 * @return the enforce policy. xspasubject
	 */
	public EnforcePolicy.Xspasubject setXspaSubject(
			String recipientEmailAddress, String messageId) {
		EnforcePolicy.Xspasubject xspasubject = new EnforcePolicy.Xspasubject();
		xspasubject.setSubjectPurposeOfUse(subjectPurposeOfUse);
		xspasubject.setSubjectLocality(subjectLocality);
		xspasubject.setSubjectEmailAddress(recipientEmailAddress);
		xspasubject.setSubjectId(recipientEmailAddress);
		xspasubject.setOrganization(organization);
		xspasubject.setOrganizationId(organizationId);
		xspasubject.setMessageId(messageId);
		return xspasubject;
	}

	/**
	 * Sets the xacml request.
	 * 
	 * @param recepientSubjectNPI
	 *            the recepient subject npi
	 * @param intermediarySubjectNPI
	 *            the intermediary subject npi
	 * @param purposeOfUse
	 *            the purpose of use
	 * @param resourceId
	 *            the resource id
	 * @param messageId
	 *            the message id
	 * @return the xacml request
	 */
	public XacmlRequest setXacmlRequest(String recepientSubjectNPI,
			String intermediarySubjectNPI, String purposeOfUse,
			String resourceId, String messageId) {
		XacmlRequest xacmlRequest = new XacmlRequest();
		xacmlRequest.setIntermediarySubjectNPI(intermediarySubjectNPI);
		xacmlRequest.setPurposeOfUse(purposeOfUse);
		xacmlRequest.setRecepientSubjectNPI(recepientSubjectNPI);
		xacmlRequest.setPatientId(resourceId);
		xacmlRequest.setPatientUniqueId(resourceId);
		xacmlRequest.setMessageId(messageId);
		xacmlRequest.setHomeCommunityId(homeCommunityId);
		return xacmlRequest;
	}

	/**
	 * Gets the XACML response.
	 * 
	 * @param result
	 *            the result
	 * @return the XACML response
	 */
	private XacmlResult getXacmlResponse(Return result) {
		XacmlResult xacmlResult = new XacmlResult();
		xacmlResult.setHomeCommunityId(result.getHomeCommunityId());
		xacmlResult.setMessageId(result.getMessageId());
		xacmlResult.setPdpDecision(result.getPdpDecision());
		xacmlResult.setPdpObligations(result.getPdpObligation());
		xacmlResult.setSubjectPurposeOfUse(SubjectPurposeOfUse.fromAbbreviation(result.getPurposeOfUse()));
		return xacmlResult;
	}

	/**
	 * Gets the xacml response.
	 * 
	 * @param xacmlRequest
	 *            the xacml request
	 * @param xacmlResponse
	 *            the xacml response
	 * @return the xacml response
	 */
	private XacmlResult getXacmlResponse(XacmlRequest xacmlRequest,
			XacmlResponse xacmlResponse) {
		XacmlResult xacmlResult = new XacmlResult();
		xacmlResult.setHomeCommunityId(xacmlRequest.getHomeCommunityId());
		xacmlResult.setMessageId(xacmlRequest.getMessageId());
		xacmlResult.setPdpDecision(xacmlResponse.getPdpDecision());
		xacmlResult.setPdpObligations(xacmlResponse.getPdpObligation());
		xacmlResult.setSubjectPurposeOfUse(SubjectPurposeOfUse.fromAbbreviation(xacmlRequest.getPurposeOfUse()));
		return xacmlResult;
	}

	/**
	 * Marshall.
	 * 
	 * @param obj
	 *            the obj
	 * @return the string
	 * @throws Throwable
	 *             the throwable
	 */
	private static String marshall(Object obj) throws Throwable {
		final JAXBContext context = JAXBContext.newInstance(obj.getClass());
		Marshaller marshaller = context.createMarshaller();

		StringWriter stringWriter = new StringWriter();
		marshaller.marshal(obj, stringWriter);

		return stringWriter.toString();
	}

	/**
	 * Unmarshall from xml.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @param xml
	 *            the xml
	 * @return the t
	 * @throws JAXBException
	 *             the jAXB exception
	 */
	@SuppressWarnings("unchecked")
	private static <T> T unmarshallFromXml(Class<T> clazz, String xml)
			throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Unmarshaller um = context.createUnmarshaller();
		ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes());
		return (T) um.unmarshal(input);
	}

	/**
	 * Load xml from.
	 * 
	 * @param xml
	 *            the xml
	 * @return the org.w3c.dom. document
	 * @throws Exception
	 *             the exception
	 */
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

	/**
	 * Gets the response with latest document entries for consent and
	 * nonconsent.
	 * 
	 * @param adhocQueryResponse
	 *            the adhoc query response
	 * @return the response with latest document entries for consent and
	 *         nonconsent
	 */
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

		/*
		 * try { LOGGER.debug(marshall(adhocQueryResponse)); } catch (Throwable
		 * e) { LOGGER.debug(e.toString(),e); }
		 */

		return adhocQueryResponse;
	}

	/**
	 * Checks if is consent document.
	 * 
	 * @param originalDocument
	 *            the original document
	 * @return true, if is consent document
	 */
	private boolean isConsentDocument(String originalDocument) {
		boolean consentDocumentExists = false;

		try {

			org.w3c.dom.Document document = loadXmlFrom(originalDocument);

			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			xpath.setNamespaceContext(new PepNamespaceContext());

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

	/**
	 * Patient exists in registy before adding.
	 * 
	 * @param responseOfAddPatient
	 *            the response of add patient
	 * @return true, if successful
	 */
	public static boolean patientExistsInRegistyBeforeAdding(
			String responseOfAddPatient) {

		boolean patientExistsInRegistyBeforeAdding = false;

		try {
			// TODO: Refactor these code to a new class to be testable
			org.w3c.dom.Document document = loadXmlFrom(responseOfAddPatient);

			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			xpath.setNamespaceContext(new PepNamespaceContext());

			// Get acknowledgment type code
			String xpathForAcknowledgementTypeCode = String.format(
					"//%s:acknowledgement/%s:typeCode/@code",
					PepNamespaceContext.HL7_PREFIX,
					PepNamespaceContext.HL7_PREFIX);
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
