/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *   
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *       * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *       * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *       * Neither the name of the <organization> nor the
 *         names of its contributors may be used to endorse or promote products
 *         derived from this software without specific prior written permission.
 *   
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *   DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.acs.xdsb.registry.wsclient.adapter;

import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.xdsb.common.XdsbDocumentReference;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.acs.xdsb.registry.wsclient.XdsbRegistryWebServiceClient;

import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest.DocumentRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import oasis.names.tc.ebxml_regrep.xsd.query._3.ResponseOptionType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.AdhocQueryType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ClassificationType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.SlotType1;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ValueListType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class XdsbRegistryAdapter.
 */
public class XdsbRegistryAdapter {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// Services
	/** The xdsb registry. */
	private XdsbRegistryWebServiceClient xdsbRegistry;

	/** The response filter. */
	private AdhocQueryResponseFilter responseFilter;

	/** The marshaller. */
	private SimpleMarshaller marshaller;

	/** The document xml converter. */
	private DocumentXmlConverter documentXmlConverter;

	// Supported XDS.b Format Codes
	/** The Constant FORMAT_CODE_CLINICAL_DOCUMENT. */
	public static final String FORMAT_CODE_CLINICAL_DOCUMENT = "'2.16.840.1.113883.10.20.1^^HITSP'";

	/** The Constant FORMAT_CODE_PRIVACY_CONSENT. */
	public static final String FORMAT_CODE_PRIVACY_CONSENT = "'1.3.6.1.4.1.19376.1.5.3.1.1.7^^IHE BPPC'";

	// AdhocQuery Slot Names
	/** The Constant SLOT_NAME_XDS_DOCUMENT_ENTRY_FORMAT_CODE. */
	public static final String SLOT_NAME_XDS_DOCUMENT_ENTRY_FORMAT_CODE = "$XDSDocumentEntryFormatCode";

	/** The Constant SLOT_NAME_XDS_DOCUMENT_ENTRY_PATIENT_ID. */
	public static final String SLOT_NAME_XDS_DOCUMENT_ENTRY_PATIENT_ID = "$XDSDocumentEntryPatientId";

	/** The Constant SLOT_NAME_XDS_DOCUMENT_ENTRYSTATUS. */
	public static final String SLOT_NAME_XDS_DOCUMENT_ENTRY_STATUS = "$XDSDocumentEntryStatus";

	// UUIDs
	/** The Constant UUID_XDS_DOCUMENTENTRY_UNIQUEID. */
	public static final String UUID_XDS_DOCUMENTENTRY_UNIQUEID = "urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab";

	/** The Constant UUID_XDS_DOCUMENTENTRY_AUTHOR. */
	public static final String UUID_XDS_DOCUMENTENTRY_AUTHOR = "urn:uuid:93606bcf-9494-43ec-9b4e-a7748d1a838d";

	/**
	 * Instantiates a new xdsb registry adapter.
	 * 
	 * @param xdsbRegistry
	 *            the xdsb registry
	 * @param responseFilter
	 *            the response filter
	 * @param marshaller
	 *            the marshaller
	 * @param documentXmlConverter
	 *            the document xml converter
	 */
	public XdsbRegistryAdapter(XdsbRegistryWebServiceClient xdsbRegistry,
			AdhocQueryResponseFilter responseFilter,
			SimpleMarshaller marshaller,
			DocumentXmlConverter documentXmlConverter) {
		this.xdsbRegistry = xdsbRegistry;
		this.responseFilter = responseFilter;
		this.marshaller = marshaller;
		this.documentXmlConverter = documentXmlConverter;

	}

	/**
	 * Registry stored query (direct call to the XDS.b registry service).
	 * 
	 * @param adhocQueryRequest
	 *            the adhoc query request
	 * @return the adhoc query response
	 */
	public AdhocQueryResponse registryStoredQuery(
			AdhocQueryRequest adhocQueryRequest) {
		return xdsbRegistry.registryStoredQuery(adhocQueryRequest);
	}

	/**
	 * Registry stored query (indirect call to the XDS.b registry service with a
	 * simplified interface).
	 * 
	 * @param patientId
	 *            the patient id
	 * @param domainId
	 *            the domain id
	 * @param authorId
	 *            the author id
	 * @param xdsbDocumentType
	 *            the xdsb document type
	 * @param serviceTimeAware
	 *            the service time aware
	 * @return the adhoc query response
	 * @throws Exception
	 *             the exception
	 * @throws Throwable
	 *             the throwable
	 */
	public AdhocQueryResponse registryStoredQuery(String patientId,
			String domainId, String authorId,
			XdsbDocumentType xdsbDocumentType, boolean serviceTimeAware)
			throws Exception, Throwable {
		String patientUniqueId = getPatientUniqueId(patientId, domainId);

		return registryStoredQuery(patientUniqueId, authorId, xdsbDocumentType,
				serviceTimeAware);
	}

	/**
	 * Registry stored query.
	 * 
	 * @param patientUniqueId
	 *            the patient unique id
	 * @param authorNPI
	 *            the author npi
	 * @param xdsbDocumentType
	 *            the xdsb document type
	 * @param serviceTimeAware
	 *            the service time aware
	 * @return the adhoc query response
	 * @throws Exception
	 *             the exception
	 * @throws Throwable
	 *             the throwable
	 */
	public AdhocQueryResponse registryStoredQuery(String patientUniqueId,
			String authorNPI, XdsbDocumentType xdsbDocumentType,
			boolean serviceTimeAware) throws Exception, Throwable {
		// Create a query request to search by patient unique id
		AdhocQueryRequest registryStoredQuery = createRegistryStoredQueryByPatientId(
				patientUniqueId, xdsbDocumentType, serviceTimeAware);

		return registryStoredQueryFilterByAuthorNPI(registryStoredQuery,
				authorNPI);
	}

	/**
	 * Registry stored query.
	 * 
	 * @param req
	 *            the req
	 * @param authorNPI
	 *            the author npi
	 * @return the adhoc query response
	 * @throws Throwable
	 *             the throwable
	 */
	public AdhocQueryResponse registryStoredQuery(AdhocQueryRequest req,
			String authorNPI) throws Throwable {
		return registryStoredQueryFilterByAuthorNPI(req, authorNPI);
	}

	/**
	 * Extract XDS.b document reference list.
	 * 
	 * @param adhocQueryResponse
	 *            the adhoc query response
	 * @return the list of XdsbDocumentReference objects having documentUniqueId
	 *         and repositoryId pairs to retrieve the documents
	 * @throws Exception
	 *             the exception
	 * @throws Throwable
	 *             the throwable
	 */
	public List<XdsbDocumentReference> extractXdsbDocumentReferenceList(
			AdhocQueryResponse adhocQueryResponse) throws Exception, Throwable {
		String adhocQueryResponseXmlString = marshaller
				.marshall(adhocQueryResponse);
		Document doc = documentXmlConverter
				.loadDocument(adhocQueryResponseXmlString);

		// Scan the document ExtrinsicObject elements
		NodeList extrinsicObjects = doc.getElementsByTagName("ExtrinsicObject");

		// Temporary storage for documentUniqueIds and repositryIds
		Map<Integer, String> documentUniqueIdMap = new HashMap<Integer, String>();
		Map<Integer, String> repositoryUniqueIdMap = new HashMap<Integer, String>();

		// For each ExtrinsicObject
		for (int i = 0; i < extrinsicObjects.getLength(); i++) {
			Node extrinsicObject = extrinsicObjects.item(i);
			NodeList extrinsicObjectItems = extrinsicObject.getChildNodes();

			// For each element of ExtrinsicObject
			for (int j = 0; j < extrinsicObjectItems.getLength(); j++) {
				Node extrinsicObjectItem = extrinsicObjectItems.item(j);

				// If the element is ExternalIdentifier with
				// a XDSDocumentEntry UniqueId
				if ("ExternalIdentifier".equals(extrinsicObjectItem
						.getNodeName())
						&& UUID_XDS_DOCUMENTENTRY_UNIQUEID
								.equals(extrinsicObjectItem.getAttributes()
										.getNamedItem("identificationScheme")
										.getNodeValue())) {
					// Get the value of UniqueId and store in temporary map
					documentUniqueIdMap.put(i, extrinsicObjectItem
							.getAttributes().getNamedItem("value")
							.getNodeValue());
				}

				// If the element is a Slot with repositoryUniqueId
				if ("Slot".equals(extrinsicObjectItem.getNodeName())
						&& "repositoryUniqueId".equals(extrinsicObjectItem
								.getAttributes().getNamedItem("name")
								.getNodeValue())) {
					// Get the value of repositoryUniqueId and store in
					// temporary map
					repositoryUniqueIdMap.put(i, extrinsicObjectItem
							.getChildNodes().item(0).getChildNodes().item(0)
							.getTextContent());
				}
			}
		}

		// Combine the maps into a list of XdsbDocumentReference
		Set<Integer> keys = documentUniqueIdMap.keySet();
		List<XdsbDocumentReference> repositoryAndDocumentUniqueIdList = new LinkedList<XdsbDocumentReference>();
		for (Integer key : keys) {
			XdsbDocumentReference xdsbDocumentReference = new XdsbDocumentReference(
					documentUniqueIdMap.get(key),
					repositoryUniqueIdMap.get(key));
			repositoryAndDocumentUniqueIdList.add(xdsbDocumentReference);
		}

		return repositoryAndDocumentUniqueIdList;
	}

	/**
	 * Extract xdsb document reference list as retrieve document set request.
	 * 
	 * @param adhocQueryResponse
	 *            the adhoc query response
	 * @return the retrieve document set request
	 * @throws Exception
	 *             the exception
	 * @throws Throwable
	 *             the throwable
	 */
	public RetrieveDocumentSetRequest extractXdsbDocumentReferenceListAsRetrieveDocumentSetRequest(
			AdhocQueryResponse adhocQueryResponse) throws Exception, Throwable {
		String adhocQueryResponseXmlString = marshaller
				.marshall(adhocQueryResponse);
		Document doc = documentXmlConverter
				.loadDocument(adhocQueryResponseXmlString);

		// Scan the document ExtrinsicObject elements
		NodeList extrinsicObjects = doc.getElementsByTagName("ExtrinsicObject");

		// Temporary storage for documentUniqueIds and repositryIds
		Map<Integer, String> documentUniqueIdMap = new HashMap<Integer, String>();
		Map<Integer, String> repositoryUniqueIdMap = new HashMap<Integer, String>();

		// For each ExtrinsicObject
		for (int i = 0; i < extrinsicObjects.getLength(); i++) {
			Node extrinsicObject = extrinsicObjects.item(i);
			NodeList extrinsicObjectItems = extrinsicObject.getChildNodes();

			// For each element of ExtrinsicObject
			for (int j = 0; j < extrinsicObjectItems.getLength(); j++) {
				Node extrinsicObjectItem = extrinsicObjectItems.item(j);

				// If the element is ExternalIdentifier with
				// a XDSDocumentEntry UniqueId
				if ("ExternalIdentifier".equals(extrinsicObjectItem
						.getNodeName())
						&& UUID_XDS_DOCUMENTENTRY_UNIQUEID
								.equals(extrinsicObjectItem.getAttributes()
										.getNamedItem("identificationScheme")
										.getNodeValue())) {
					// Get the value of UniqueId and store in temporary map
					documentUniqueIdMap.put(i, extrinsicObjectItem
							.getAttributes().getNamedItem("value")
							.getNodeValue());
				}

				// If the element is a Slot with repositoryUniqueId
				if ("Slot".equals(extrinsicObjectItem.getNodeName())
						&& "repositoryUniqueId".equals(extrinsicObjectItem
								.getAttributes().getNamedItem("name")
								.getNodeValue())) {
					// Get the value of repositoryUniqueId and store in
					// temporary map
					repositoryUniqueIdMap.put(i, extrinsicObjectItem
							.getChildNodes().item(0).getChildNodes().item(0)
							.getTextContent());
				}
			}
		}

		// Combine the maps into a list of XdsbDocumentReference
		Set<Integer> keys = documentUniqueIdMap.keySet();
		RetrieveDocumentSetRequest retrieveDocumentSetRequest = new RetrieveDocumentSetRequest();
		List<DocumentRequest> documentRequestList = retrieveDocumentSetRequest
				.getDocumentRequest();
		for (Integer key : keys) {
			XdsbDocumentReference xdsbDocumentReference = new XdsbDocumentReference(
					documentUniqueIdMap.get(key),
					repositoryUniqueIdMap.get(key));
			documentRequestList.add(xdsbDocumentReference);
		}

		return retrieveDocumentSetRequest;
	}

	/**
	 * Extract patient id.
	 * 
	 * @param req
	 *            the req
	 * @return the string
	 */
	public String extractPatientId(AdhocQueryRequest req) {
		return extractSlotValue(req, SLOT_NAME_XDS_DOCUMENT_ENTRY_PATIENT_ID);
	}

	/**
	 * Extract format code.
	 * 
	 * @param req
	 *            the req
	 * @return the string
	 */
	public String extractFormatCode(AdhocQueryRequest req) {
		return extractSlotValue(req, SLOT_NAME_XDS_DOCUMENT_ENTRY_FORMAT_CODE);
	}

	/**
	 * Extract response option return type.
	 * 
	 * @param req
	 *            the req
	 * @return the string
	 */
	public String extractResponseOptionReturnType(AdhocQueryRequest req) {
		return req.getResponseOption().getReturnType();
	}

	/**
	 * Extract document entry status.
	 * 
	 * @param req
	 *            the req
	 * @return the string
	 */
	public String extractDocumentEntryStatus(AdhocQueryRequest req) {
		return extractSlotValue(req, SLOT_NAME_XDS_DOCUMENT_ENTRY_STATUS);
	}

	/**
	 * Gets the patient unique id.
	 * 
	 * @param patientId
	 *            the patient id
	 * @param domainId
	 *            the domain id
	 * @return the patient unique id
	 */
	public String getPatientUniqueId(String patientId, String domainId) {
		StringBuilder builder = new StringBuilder();
		builder.append("'");
		builder.append(patientId);
		builder.append("^^^&");
		builder.append(domainId);
		builder.append("&ISO'");
		return builder.toString();
	}

	/**
	 * Creates the registry stored query by patient id.
	 * 
	 * @param patientUniqueId
	 *            the patient unique id
	 * @param xdsbDocumentType
	 *            the xdsb document type
	 * @param serviceTimeAware
	 *            the service time aware
	 * @return the adhoc query request
	 */
	AdhocQueryRequest createRegistryStoredQueryByPatientId(
			String patientUniqueId, XdsbDocumentType xdsbDocumentType,
			boolean serviceTimeAware) {
		// Create registryStoredQuery
		AdhocQueryRequest registryStoredQuery = new AdhocQueryRequest();
		// Set response option to return all metadata of the documents in the
		// response
		setResponseOptionToGetAllMetadata(registryStoredQuery);

		// Create a query type that will find documents by given patient id
		AdhocQueryType adhocQueryType = createFindDocumentsByPatientIdQueryType();
		registryStoredQuery.setAdhocQuery(adhocQueryType);

		// Modify query type

		// add patient id (will only return document references of the given
		// patient id)
		addPatientId(adhocQueryType, patientUniqueId);

		// add approved document entry status (will only return document
		// references with approved status)
		addEntryStatusApproved(adhocQueryType);

		// add format code (will only return document references for the given
		// supported document type)
		addFormatCode(adhocQueryType, xdsbDocumentType);

		if (serviceTimeAware) {
			// add service time constraint (will only return the document
			// references within valid service start/end time)
			addServiceTimeConstraint(adhocQueryType);
		}

		return registryStoredQuery;
	}

	/**
	 * Creates the find documents by patient id query type.
	 * 
	 * @return the adhoc query type
	 */
	AdhocQueryType createFindDocumentsByPatientIdQueryType() {
		AdhocQueryType adhocQueryType = new AdhocQueryType();
		// FindDocuments by patientId
		adhocQueryType.setId("urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d");
		return adhocQueryType;
	}

	/**
	 * Sets the response option to get all metadata.
	 * 
	 * @param registryStoredQuery
	 *            the new response option to get all metadata
	 */
	void setResponseOptionToGetAllMetadata(AdhocQueryRequest registryStoredQuery) {
		ResponseOptionType responseOptionType = new ResponseOptionType();
		responseOptionType.setReturnComposedObjects(true);
		responseOptionType.setReturnType("LeafClass");
		registryStoredQuery.setResponseOption(responseOptionType);
	}

	/**
	 * Adds the patient id.
	 * 
	 * @param adhocQueryType
	 *            the adhoc query type
	 * @param patientUniqueId
	 *            the patient unique id
	 */
	void addPatientId(AdhocQueryType adhocQueryType, String patientUniqueId) {
		if (!patientUniqueId.startsWith("'") || !patientUniqueId.endsWith("'")) {
			patientUniqueId = patientUniqueId.replace("'", "");
			patientUniqueId = "'" + patientUniqueId + "'";
		}
		SlotType1 patientIdSlotType = new SlotType1();
		patientIdSlotType.setName(SLOT_NAME_XDS_DOCUMENT_ENTRY_PATIENT_ID);
		ValueListType patientIdValueListType = new ValueListType();
		patientIdValueListType.getValue().add(patientUniqueId); // PatientId
		patientIdSlotType.setValueList(patientIdValueListType);
		adhocQueryType.getSlot().add(patientIdSlotType);
	}

	// void addAuthorId(AdhocQueryType adhocQueryType, String authorId) {
	// if(!authorId.startsWith("'") || !authorId.endsWith("'"))
	// {
	// authorId = authorId.replace("'", "");
	// authorId = "'"+authorId+"'";
	// }
	// SlotType1 authorIdSlotType = new SlotType1();
	// authorIdSlotType.setName("$XDSSubmissionSetAuthorPerson");
	// ValueListType authorIdValueListType = new ValueListType();
	// authorIdValueListType.getValue().add(authorId); // AuthorId
	// authorIdSlotType.setValueList(authorIdValueListType);
	// adhocQueryType.getSlot().add(authorIdSlotType);
	// // ClassificationType classification = new ClassificationType();
	// // classification.setClassificationScheme(UUID_XDS_DOCUMENTENTRY_AUTHOR);
	// // classification.getSlot().add(authorIdSlotType);
	// //
	// classification.setClassifiedObject("urn:uuid:be91a84a-5cbf-4231-b97b-1ee84a0dc23b");
	// // classification.setNodeRepresentation("");
	// //
	// classification.setObjectType("urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification");
	// //
	// classification.setStatus("urn:oasis:names:tc:ebxml-regrep:StatusType:Submitted");
	// // classification.setId(UUID.randomUUID().toString());
	// // adhocQueryType.getClassification().add(classification);
	// }

	/**
	 * Adds the entry status approved.
	 * 
	 * @param adhocQueryType
	 *            the adhoc query type
	 */
	void addEntryStatusApproved(AdhocQueryType adhocQueryType) {
		SlotType1 statusSlotType = new SlotType1();
		statusSlotType.setName("$XDSDocumentEntryStatus");
		ValueListType statusValueListType = new ValueListType();
		statusValueListType.getValue().add(
				"('urn:oasis:names:tc:ebxml-regrep:StatusType:Approved')");
		statusSlotType.setValueList(statusValueListType);
		adhocQueryType.getSlot().add(statusSlotType);
	}

	/**
	 * Adds the format code.
	 * 
	 * @param adhocQueryType
	 *            the adhoc query type
	 * @param xdsbDocumentType
	 *            the xdsb document type
	 */
	void addFormatCode(AdhocQueryType adhocQueryType,
			XdsbDocumentType xdsbDocumentType) {
		SlotType1 formatCodeSlotType = new SlotType1();
		formatCodeSlotType.setName(SLOT_NAME_XDS_DOCUMENT_ENTRY_FORMAT_CODE);
		ValueListType formatCodeValueListType = new ValueListType();

		if (xdsbDocumentType.equals(XdsbDocumentType.CLINICAL_DOCUMENT)) {
			formatCodeValueListType.getValue().add(
					FORMAT_CODE_CLINICAL_DOCUMENT);
		} else if (xdsbDocumentType.equals(XdsbDocumentType.PRIVACY_CONSENT)) {
			formatCodeValueListType.getValue().add(FORMAT_CODE_PRIVACY_CONSENT);
		} else {
			logger.error("Unsupported XDS.b document format code");
			throw new DS4PException("Unsupported XDS.b document format code");
		}

		formatCodeSlotType.setValueList(formatCodeValueListType);
		adhocQueryType.getSlot().add(formatCodeSlotType);
	}

	/**
	 * Adds the service time constraint.
	 * 
	 * @param adhocQueryType
	 *            the adhoc query type
	 */
	void addServiceTimeConstraint(AdhocQueryType adhocQueryType) {
		String currentTime = getCurrentTime();

		// Set upper value of XDSDocumentEntry serviceStartTime
		SlotType1 serviceStartSlotType = new SlotType1();
		serviceStartSlotType.setName("$XDSDocumentEntryServiceStartTimeTo");
		ValueListType serviceStartValueListType = new ValueListType();
		serviceStartValueListType.getValue().add(currentTime);
		serviceStartSlotType.setValueList(serviceStartValueListType);
		adhocQueryType.getSlot().add(serviceStartSlotType);

		// Set lower value of XDSDocumentEntry serviceStopTime
		SlotType1 serviceStopSlotType = new SlotType1();
		serviceStopSlotType.setName("$XDSDocumentEntryServiceStopTimeFrom");
		ValueListType serviceStopValueListType = new ValueListType();
		serviceStopValueListType.getValue().add(currentTime);
		serviceStopSlotType.setValueList(serviceStopValueListType);
		adhocQueryType.getSlot().add(serviceStopSlotType);
	}

	/**
	 * Registry stored query filter by author npi.
	 * 
	 * @param req
	 *            the req
	 * @param authorNPI
	 *            the author npi
	 * @return the adhoc query response
	 * @throws Throwable
	 *             the throwable
	 */
	private AdhocQueryResponse registryStoredQueryFilterByAuthorNPI(
			AdhocQueryRequest req, String authorNPI) throws Throwable {
		// Query Response
		AdhocQueryResponse adhocQueryResponse = registryStoredQuery(req);
		if (authorNPI != null && !"".equals(authorNPI)) {
			adhocQueryResponse = responseFilter.filterByAuthor(
					adhocQueryResponse, authorNPI);
		}
		logger.debug(marshaller.marshall(adhocQueryResponse));
		return adhocQueryResponse;
	}

	/**
	 * Gets the current time.
	 * 
	 * @return the current time
	 */
	private String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}

	/**
	 * Extract slot value.
	 * 
	 * @param req
	 *            the req
	 * @param slotName
	 *            the slot name
	 * @return the string
	 */
	private String extractSlotValue(AdhocQueryRequest req, String slotName) {
		List<SlotType1> slots = req.getAdhocQuery().getSlot();
		for (SlotType1 slot : slots) {
			if (slotName.equals(slot.getName())) {
				return slot.getValueList().getValue().get(0);
			}
		}
		return null;
	}
}
