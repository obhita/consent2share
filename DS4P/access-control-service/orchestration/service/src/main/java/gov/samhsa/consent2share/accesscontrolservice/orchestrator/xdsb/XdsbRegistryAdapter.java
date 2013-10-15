package gov.samhsa.consent2share.accesscontrolservice.orchestrator.xdsb;

import gov.samhsa.consent2share.accesscontrolservice.common.tool.DocumentXmlConverter;
import gov.samhsa.consent2share.accesscontrolservice.common.tool.SimpleMarshaller;
import gov.samhsa.ds4ppilot.common.exception.DS4PException;
import gov.samhsa.ds4ppilot.orchestrator.xdsbregistry.XdsbRegistry;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import oasis.names.tc.ebxml_regrep.xsd.query._3.ResponseOptionType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.AdhocQueryType;
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
	private XdsbRegistry xdsbRegistry;

	/** The marshaller. */
	private SimpleMarshaller marshaller;

	/** The document xml converter. */
	private DocumentXmlConverter documentXmlConverter;

	// Supported XDS.b Format Codes
	/** The Constant CLINICAL_DOCUMENT_FORMAT_CODE. */
	public static final String CLINICAL_DOCUMENT_FORMAT_CODE = "'2.16.840.1.113883.10.20.1^^HITSP'";

	/** The Constant PRIVACY_CONSENT_FORMAT_CODE. */
	public static final String PRIVACY_CONSENT_FORMAT_CODE = "'1.3.6.1.4.1.19376.1.5.3.1.1.7^^IHE BPPC'";

	// UUIDs
	public static final String UUID_XDS_DOCUMENTENTRY_UNIQUEID = "urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab";

	/**
	 * Instantiates a new xdsb registry adapter.
	 * 
	 * @param xdsbRegistry
	 *            the xdsb registry
	 * @param marshaller
	 *            the marshaller
	 * @param documentXmlConverter
	 *            the document xml converter
	 */
	public XdsbRegistryAdapter(XdsbRegistry xdsbRegistry,
			SimpleMarshaller marshaller,
			DocumentXmlConverter documentXmlConverter) {
		this.xdsbRegistry = xdsbRegistry;
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
	 * @param homeCommunityId
	 *            the home community id
	 * @param xdsbDocumentType
	 *            the xdsb document type
	 * @return the adhoc query response
	 * @throws Exception
	 *             the exception
	 * @throws Throwable
	 *             the throwable
	 */
	public AdhocQueryResponse registryStoredQuery(String patientId,
			String homeCommunityId, XdsbDocumentType xdsbDocumentType)
			throws Exception, Throwable {
		// Create a query request to search by patient unique id
		AdhocQueryRequest registryStoredQuery = createRegistryStoredQueryByPatientId(
				patientId, homeCommunityId, xdsbDocumentType);

		// Query Response
		AdhocQueryResponse adhocQueryResponse = registryStoredQuery(registryStoredQuery);
		logger.debug(marshaller.marshall(adhocQueryResponse));
		return adhocQueryResponse;
	}

	/**
	 * Extract XDS.b document reference list.
	 * 
	 * @param adhocQueryResponse
	 *            the adhoc query response
	 * @param xdsbDocumentType
	 *            the xdsb document type
	 * @return the list of XdsbDocumentReference objects having documentUniqueId
	 *         and repositoryId pairs to retrieve the documents
	 * @throws Exception
	 *             the exception
	 * @throws Throwable
	 *             the throwable
	 */
	public List<XdsbDocumentReference> extractXdsbDocumentReferenceList(
			AdhocQueryResponse adhocQueryResponse,
			XdsbDocumentType xdsbDocumentType) throws Exception, Throwable {
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
	 * Creates the registry stored query by patient id.
	 * 
	 * @param patientId
	 *            the patient id
	 * @param homeCommunityId
	 *            the home community id
	 * @param xdsbDocumentType
	 *            the xdsb document type
	 * @return the adhoc query request
	 */
	AdhocQueryRequest createRegistryStoredQueryByPatientId(String patientId,
			String homeCommunityId, XdsbDocumentType xdsbDocumentType) {
		AdhocQueryRequest registryStoredQuery = new AdhocQueryRequest();
		ResponseOptionType responseOptionType = new ResponseOptionType();
		responseOptionType.setReturnComposedObjects(true);
		responseOptionType.setReturnType("LeafClass");
		registryStoredQuery.setResponseOption(responseOptionType);

		AdhocQueryType adhocQueryType = new AdhocQueryType();
		adhocQueryType.setId("urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d"); // FindDocuments
																				// by
																				// patientId
		registryStoredQuery.setAdhocQuery(adhocQueryType);

		SlotType1 patientIdSlotType = new SlotType1();
		patientIdSlotType.setName("$XDSDocumentEntryPatientId");
		ValueListType patientIdValueListType = new ValueListType();

		String patientUniqueId = getPatientUniqueId(patientId, homeCommunityId);

		patientIdValueListType.getValue().add(patientUniqueId); // PatientId
		patientIdSlotType.setValueList(patientIdValueListType);
		adhocQueryType.getSlot().add(patientIdSlotType);

		SlotType1 statusSlotType = new SlotType1();
		statusSlotType.setName("$XDSDocumentEntryStatus");
		ValueListType statusValueListType = new ValueListType();
		statusValueListType.getValue().add(
				"('urn:oasis:names:tc:ebxml-regrep:StatusType:Approved')");
		statusSlotType.setValueList(statusValueListType);
		adhocQueryType.getSlot().add(statusSlotType);

		// add format code
		addFormatCode(adhocQueryType, xdsbDocumentType);

		return registryStoredQuery;
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
		formatCodeSlotType.setName("$XDSDocumentEntryFormatCode");
		ValueListType formatCodeValueListType = new ValueListType();

		if (xdsbDocumentType.equals(XdsbDocumentType.CLINICAL_DOCUMENT)) {
			formatCodeValueListType.getValue().add(
					CLINICAL_DOCUMENT_FORMAT_CODE);
		} else if (xdsbDocumentType.equals(XdsbDocumentType.PRIVACY_CONSENT)) {
			formatCodeValueListType.getValue().add(PRIVACY_CONSENT_FORMAT_CODE);
		} else {
			logger.error("Unsupported XDS.b document format code");
			throw new DS4PException("Unsupported XDS.b document format code");
		}

		formatCodeSlotType.setValueList(formatCodeValueListType);
		adhocQueryType.getSlot().add(formatCodeSlotType);
	}

	/**
	 * Gets the patient unique id.
	 * 
	 * @param patientId
	 *            the patient id
	 * @param homeCommunityId
	 *            the home community id
	 * @return the patient unique id
	 */
	private String getPatientUniqueId(String patientId, String homeCommunityId) {
		StringBuilder builder = new StringBuilder();
		builder.append("'");
		builder.append(patientId);
		builder.append("^^^&");
		builder.append(homeCommunityId);
		builder.append("&ISO'");
		return builder.toString();
	}
}
