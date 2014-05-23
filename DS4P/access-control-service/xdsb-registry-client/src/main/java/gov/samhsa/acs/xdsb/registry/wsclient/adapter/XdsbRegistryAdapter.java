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
import gov.samhsa.acs.common.log.AcsLogger;
import gov.samhsa.acs.common.log.AcsLoggerFactory;
import gov.samhsa.acs.common.tool.DocumentAccessor;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.common.tool.exception.DocumentAccessorException;
import gov.samhsa.acs.common.tool.exception.DocumentXmlConverterException;
import gov.samhsa.acs.common.tool.exception.SimpleMarshallerException;
import gov.samhsa.acs.xdsb.common.XdsbDocumentReference;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.acs.xdsb.registry.wsclient.XdsbRegistryWebServiceClient;
import gov.samhsa.acs.xdsb.registry.wsclient.exception.XdsbRegistryAdapterException;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest.DocumentRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
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

import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class XdsbRegistryAdapter.
 */
public class XdsbRegistryAdapter {

	/** The logger. */
	private final AcsLogger logger = AcsLoggerFactory
			.getLogger(this.getClass());

	// Services
	/** The xdsb registry. */
	private XdsbRegistryWebServiceClient xdsbRegistry;

	/** The response filter. */
	private AdhocQueryResponseFilter responseFilter;

	/** The marshaller. */
	private SimpleMarshaller marshaller;

	/** The document xml converter. */
	private DocumentXmlConverter documentXmlConverter;

	/** The document accessor. */
	private DocumentAccessor documentAccessor;

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
	/** The Constant UUID_XDS_DOCUMENTENTRY. */
	public static final String UUID_XDS_DOCUMENTENTRY = "urn:uuid:7edca82f-054d-47f2-a032-9b2a5b5186c1";

	/** The Constant UUID_XDS_DOCUMENTENTRY_UNIQUEID. */
	public static final String UUID_XDS_DOCUMENTENTRY_UNIQUEID = "urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab";

	/** The Constant UUID_XDS_DOCUMENTENTRY_AUTHOR. */
	public static final String UUID_XDS_DOCUMENTENTRY_AUTHOR = "urn:uuid:93606bcf-9494-43ec-9b4e-a7748d1a838d";

	/** The Constant UUID_XDS_SUBMISSION_SET_UNIQUEID. */
	public static final String UUID_XDS_SUBMISSION_SET_UNIQUEID = "urn:uuid:96fdda7c-d067-4183-912e-bf5ee74998a8";

	// Stored Queries
	// Find Submission Sets
	/** The Constant STORED_QUERY_FIND_SUBMISSION_SETS. */
	private static final String STORED_QUERY_FIND_SUBMISSION_SETS = "<ns3:AdhocQueryRequest xmlns:ns2=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\" xmlns:ns3=\"urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0\" xmlns:ns4=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\"><ns3:ResponseOption returnComposedObjects=\"true\" returnType=\"LeafClass\"/><ns2:AdhocQuery id=\"urn:uuid:f26abbcb-ac74-4422-8a30-edb644bbc1a9\"><ns2:Slot name=\"$XDSSubmissionSetPatientId\"><ns2:ValueList><ns2:Value>@XDSSubmissionSetPatientId</ns2:Value></ns2:ValueList></ns2:Slot><ns2:Slot name=\"$XDSSubmissionSetAuthorPerson\"><ns2:ValueList><ns2:Value>@XDSSubmissionSetAuthorPerson</ns2:Value></ns2:ValueList></ns2:Slot><ns2:Slot name=\"$XDSSubmissionSetStatus\"><ns2:ValueList><ns2:Value>('urn:oasis:names:tc:ebxml-regrep:StatusType:Approved')</ns2:Value></ns2:ValueList></ns2:Slot></ns2:AdhocQuery></ns3:AdhocQueryRequest>";

	/** The Constant PARAM_XDS_SUBMISSION_SET_PATIENT_ID. */
	private static final String PARAM_XDS_SUBMISSION_SET_PATIENT_ID = "@XDSSubmissionSetPatientId";

	/** The Constant PARAM_XDS_SUBMISSION_SET_AUTHOR_PERSON. */
	private static final String PARAM_XDS_SUBMISSION_SET_AUTHOR_PERSON = "@XDSSubmissionSetAuthorPerson";

	// Get Submission Set and Contents
	/** The Constant STORED_QUERY_GET_SUBMISSION_SET_AND_CONTENTS. */
	private static final String STORED_QUERY_GET_SUBMISSION_SET_AND_CONTENTS = "<ns3:AdhocQueryRequest xmlns:ns2=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\" xmlns:ns3=\"urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0\" xmlns:ns4=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\"><ns3:ResponseOption returnComposedObjects=\"true\" returnType=\"LeafClass\"/><ns2:AdhocQuery id=\"urn:uuid:e8e3cb2c-e39c-46b9-99e4-c12f57260b83\"><ns2:Slot name=\"$XDSSubmissionSetUniqueId\"><ns2:ValueList><!-- This operation only works for the first value, do not pass multiple values here.--><ns2:Value>'@XDSSubmissionSetUniqueId'</ns2:Value></ns2:ValueList></ns2:Slot></ns2:AdhocQuery></ns3:AdhocQueryRequest>";

	/** The Constant PARAM_XDS_SUBMISSION_SET_UNIQUEID. */
	private static final String PARAM_XDS_SUBMISSION_SET_UNIQUEID = "@XDSSubmissionSetUniqueId";

	/** The Constant SUBMISSION_SET_STATUS_DEPRECATED. */
	private static final String SUBMISSION_SET_STATUS_DEPRECATED = "urn:oasis:names:tc:ebxml-regrep:StatusType:Deprecated";

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
	 * @param documentAccessor
	 *            the document accessor
	 */
	public XdsbRegistryAdapter(XdsbRegistryWebServiceClient xdsbRegistry,
			AdhocQueryResponseFilter responseFilter,
			SimpleMarshaller marshaller,
			DocumentXmlConverter documentXmlConverter,
			DocumentAccessor documentAccessor) {
		this.xdsbRegistry = xdsbRegistry;
		this.responseFilter = responseFilter;
		this.marshaller = marshaller;
		this.documentXmlConverter = documentXmlConverter;
		this.documentAccessor = documentAccessor;
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
	 * @param messageId
	 *            the message id
	 * @return the adhoc query response
	 * @throws XdsbRegistryAdapterException
	 *             the xdsb registry adapter exception
	 */
	public AdhocQueryResponse registryStoredQuery(String patientId,
			String domainId, String authorId,
			XdsbDocumentType xdsbDocumentType, boolean serviceTimeAware,
			String messageId) throws XdsbRegistryAdapterException {
		String patientUniqueId = getPatientUniqueId(patientId, domainId);

		return registryStoredQuery(patientUniqueId, authorId, xdsbDocumentType,
				serviceTimeAware, messageId);
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
	 * @param messageId
	 *            the message id
	 * @return the adhoc query response
	 * @throws XdsbRegistryAdapterException
	 *             the xdsb registry adapter exception
	 */
	public AdhocQueryResponse registryStoredQuery(String patientUniqueId,
			String authorNPI, XdsbDocumentType xdsbDocumentType,
			boolean serviceTimeAware, String messageId)
			throws XdsbRegistryAdapterException {
		// Create a query request to search by patient unique id
		AdhocQueryRequest registryStoredQuery = createRegistryStoredQueryByPatientId(
				patientUniqueId, xdsbDocumentType, serviceTimeAware, messageId);

		return registryStoredQueryFilterByAuthorNPI(registryStoredQuery,
				authorNPI, messageId);
	}

	/**
	 * Registry stored query.
	 * 
	 * @param req
	 *            the req
	 * @param authorNPI
	 *            the author npi
	 * @param messageId
	 *            the message id
	 * @return the adhoc query response
	 * @throws XdsbRegistryAdapterException
	 *             the xdsb registry adapter exception
	 */
	public AdhocQueryResponse registryStoredQuery(AdhocQueryRequest req,
			String authorNPI, String messageId)
			throws XdsbRegistryAdapterException {
		try {
			return registryStoredQueryFilterByAuthorNPI(req, authorNPI,
					messageId);
		} catch (Throwable t) {
			throw new XdsbRegistryAdapterException(t);
		}
	}

	/**
	 * Find deprecated document unique ids.
	 * 
	 * @param submissionSetPatientId
	 *            the submission set patient id
	 * @param submissionSetAuthorPerson
	 *            the submission set author person
	 * @param messageId
	 *            the message id
	 * @return the list
	 * @throws XdsbRegistryAdapterException
	 *             the xdsb registry adapter exception
	 */
	public List<String> findDeprecatedDocumentUniqueIds(
			String submissionSetPatientId, String submissionSetAuthorPerson,
			String messageId) throws XdsbRegistryAdapterException {
		submissionSetPatientId = submissionSetPatientId.replaceAll("&(?!amp;)",
				"&amp;");
		submissionSetAuthorPerson = submissionSetAuthorPerson.replaceAll(
				"&(?!amp;)", "&amp;");

		List<String> deprecatedDocumentUniqueIds = new LinkedList<String>();
		AdhocQueryResponse findSubmissionSetsResponse = findSubmissionSets(
				submissionSetPatientId, submissionSetAuthorPerson);
		List<String> submissionSetUniqueIds = extractSubmissionSetUniqueIds(findSubmissionSetsResponse);
		for (String submissionSetUniqueId : submissionSetUniqueIds) {
			AdhocQueryResponse getSubmissionSetAndContentsResponse = getSubmissionSetAndContents(
					submissionSetUniqueId, messageId);
			String deprecatedDocumentUniqueId = extractDeprecatedDocumentUniqueId(
					getSubmissionSetAndContentsResponse, messageId);
			if (deprecatedDocumentUniqueId != null) {
				deprecatedDocumentUniqueIds.add(deprecatedDocumentUniqueId);
			}
		}
		return deprecatedDocumentUniqueIds;
	}

	/**
	 * Find submission sets.
	 * 
	 * @param submissionSetPatientId
	 *            the submission set patient id
	 * @param submissionSetAuthorPerson
	 *            the submission set author person
	 * @return the adhoc query response
	 * @throws XdsbRegistryAdapterException
	 *             the xdsb registry adapter exception
	 */
	public AdhocQueryResponse findSubmissionSets(String submissionSetPatientId,
			String submissionSetAuthorPerson)
			throws XdsbRegistryAdapterException {
		Assert.notNull(submissionSetPatientId);
		Assert.notNull(submissionSetAuthorPerson);

		AdhocQueryRequest findSubmissionSetsRequest = createFindSubmissionSetsRequest(
				submissionSetPatientId, submissionSetAuthorPerson);

		return registryStoredQuery(findSubmissionSetsRequest);
	}

	/**
	 * Gets the submission set and contents.
	 * 
	 * @param submissionSetUniqueId
	 *            the submission set unique id
	 * @param messageId
	 *            the message id
	 * @return the gets the submission set and contents
	 * @throws XdsbRegistryAdapterException
	 *             the xdsb registry adapter exception
	 */
	public AdhocQueryResponse getSubmissionSetAndContents(
			String submissionSetUniqueId, String messageId)
			throws XdsbRegistryAdapterException {
		// Invoke this method for each submissionSetId at a time. This stored
		// query doesn't support retrieval of multiple submission sets, do not
		// try to implement it.

		Assert.notNull(submissionSetUniqueId);

		AdhocQueryRequest getSubmissionSetAndContentsRequest = createGetSubmissionSetAndContentsRequest(
				submissionSetUniqueId, messageId);

		return registryStoredQuery(getSubmissionSetAndContentsRequest);
	}

	/**
	 * Extract submission set unique ids.
	 * 
	 * @param response
	 *            the response
	 * @return the list
	 * @throws XdsbRegistryAdapterException
	 *             the xdsb registry adapter exception
	 */
	public List<String> extractSubmissionSetUniqueIds(
			AdhocQueryResponse response) throws XdsbRegistryAdapterException {
		try {
			List<String> submissionSetUniqueIdList = new LinkedList<String>();
			String responseXml = this.marshaller.marshall(response);
			Document responseDoc = this.documentXmlConverter
					.loadDocument(responseXml);
			String xPathExpr = "//rim:ExternalIdentifier[@identificationScheme='$']/@value";
			NodeList nodeList = this.documentAccessor.getNodeList(responseDoc,
					xPathExpr.replace("$", UUID_XDS_SUBMISSION_SET_UNIQUEID));
			for (int i = 0; i < nodeList.getLength(); i++) {
				submissionSetUniqueIdList.add(nodeList.item(i).getNodeValue());
			}
			return submissionSetUniqueIdList;
		} catch (SimpleMarshallerException | DocumentAccessorException e) {
			throw new XdsbRegistryAdapterException(e);
		}
	}

	/**
	 * Extract deprecated document unique id.
	 * 
	 * @param response
	 *            the response
	 * @param messageId
	 *            the message id
	 * @return the string
	 * @throws XdsbRegistryAdapterException
	 *             the xdsb registry adapter exception
	 */
	public String extractDeprecatedDocumentUniqueId(
			AdhocQueryResponse response, String messageId)
			throws XdsbRegistryAdapterException {
		try {
			String responseXml = this.marshaller.marshall(response);
			Document responseDoc = this.documentXmlConverter
					.loadDocument(responseXml);
			// Extract documentUniqueId if there is an association in the
			// submission
			// set with a NewStatus slot having SUBMISSION_SET_STATUS_DEPRECATED
			// value. Return null, if there is not any.
			StringBuilder builder = new StringBuilder();
			builder.append("//rim:Association[descendant::rim:Slot[@name='NewStatus']][descendant::rim:Value[.='");
			builder.append(SUBMISSION_SET_STATUS_DEPRECATED);
			builder.append("']]/preceding-sibling::rim:ExtrinsicObject[@objectType='");
			builder.append(UUID_XDS_DOCUMENTENTRY);
			builder.append("']/descendant::rim:ExternalIdentifier[@identificationScheme='");
			builder.append(UUID_XDS_DOCUMENTENTRY_UNIQUEID);
			builder.append("']/@value");
			String xPathExpr = builder.toString();
			Node node = this.documentAccessor.getNode(responseDoc, xPathExpr);
			if (node == null) {
				return null;
			} else {
				return node.getNodeValue();
			}
		} catch (SimpleMarshallerException | DocumentAccessorException e) {
			logger.error(messageId, e.getMessage());
			throw new XdsbRegistryAdapterException(e.getMessage(), e);
		}
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
	 * @throws XdsbRegistryAdapterException
	 *             the xdsb registry adapter exception
	 */
	public RetrieveDocumentSetRequest extractXdsbDocumentReferenceListAsRetrieveDocumentSetRequest(
			AdhocQueryResponse adhocQueryResponse)
			throws XdsbRegistryAdapterException {
		String adhocQueryResponseXmlString;
		try {
			adhocQueryResponseXmlString = marshaller
					.marshall(adhocQueryResponse);
			Document doc = documentXmlConverter
					.loadDocument(adhocQueryResponseXmlString);

			// Scan the document ExtrinsicObject elements
			NodeList extrinsicObjects = doc
					.getElementsByTagName("ExtrinsicObject");

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
									.equals(extrinsicObjectItem
											.getAttributes()
											.getNamedItem(
													"identificationScheme")
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
								.getChildNodes().item(0).getChildNodes()
								.item(0).getTextContent());
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

		} catch (SimpleMarshallerException | DocumentXmlConverterException e) {
			throw new XdsbRegistryAdapterException(e);
		}
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
	 * Creates the find submission sets request.
	 * 
	 * @param submissionSetPatientId
	 *            the submission set patient id
	 * @param submissionSetAuthorPerson
	 *            the submission set author person
	 * @return the adhoc query request
	 * @throws XdsbRegistryAdapterException
	 *             the xdsb registry adapter exception
	 */
	AdhocQueryRequest createFindSubmissionSetsRequest(
			String submissionSetPatientId, String submissionSetAuthorPerson)
			throws XdsbRegistryAdapterException {
		try {
			AdhocQueryRequest findSubmissionSetsRequest = this.marshaller
					.unmarshallFromXml(
							AdhocQueryRequest.class,
							STORED_QUERY_FIND_SUBMISSION_SETS.replace(
									PARAM_XDS_SUBMISSION_SET_PATIENT_ID,
									submissionSetPatientId).replace(
									PARAM_XDS_SUBMISSION_SET_AUTHOR_PERSON,
									submissionSetAuthorPerson));

			return findSubmissionSetsRequest;
		} catch (SimpleMarshallerException e) {
			throw new XdsbRegistryAdapterException(e);
		}
	}

	/**
	 * Creates the get submission set and contents request.
	 * 
	 * @param submissionSetUniqueId
	 *            the submission set unique id
	 * @param messageId
	 *            the message id
	 * @return the adhoc query request
	 * @throws XdsbRegistryAdapterException
	 *             the xdsb registry adapter exception
	 */
	AdhocQueryRequest createGetSubmissionSetAndContentsRequest(
			String submissionSetUniqueId, String messageId)
			throws XdsbRegistryAdapterException {
		try {
			AdhocQueryRequest getSubmissionSetAndContentsRequest = this.marshaller
					.unmarshallFromXml(AdhocQueryRequest.class,
							STORED_QUERY_GET_SUBMISSION_SET_AND_CONTENTS
									.replace(PARAM_XDS_SUBMISSION_SET_UNIQUEID,
											submissionSetUniqueId));

			return getSubmissionSetAndContentsRequest;
		} catch (SimpleMarshallerException e) {
			logger.error(messageId, e.getMessage());
			throw new XdsbRegistryAdapterException(e);
		}
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
	 * @param messageId
	 *            the message id
	 * @return the adhoc query request
	 */
	AdhocQueryRequest createRegistryStoredQueryByPatientId(
			String patientUniqueId, XdsbDocumentType xdsbDocumentType,
			boolean serviceTimeAware, String messageId) {
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
		addFormatCode(adhocQueryType, xdsbDocumentType, messageId);

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
			StringBuilder builder = new StringBuilder();
			builder.append("'");
			builder.append(patientUniqueId);
			builder.append("'");
			patientUniqueId = builder.toString();
		}
		SlotType1 patientIdSlotType = new SlotType1();
		patientIdSlotType.setName(SLOT_NAME_XDS_DOCUMENT_ENTRY_PATIENT_ID);
		ValueListType patientIdValueListType = new ValueListType();
		patientIdValueListType.getValue().add(patientUniqueId); // PatientId
		patientIdSlotType.setValueList(patientIdValueListType);
		adhocQueryType.getSlot().add(patientIdSlotType);
	}

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
	 * @param messageId
	 *            the message id
	 */
	void addFormatCode(AdhocQueryType adhocQueryType,
			XdsbDocumentType xdsbDocumentType, String messageId) {
		SlotType1 formatCodeSlotType = new SlotType1();
		formatCodeSlotType.setName(SLOT_NAME_XDS_DOCUMENT_ENTRY_FORMAT_CODE);
		ValueListType formatCodeValueListType = new ValueListType();

		if (xdsbDocumentType.equals(XdsbDocumentType.CLINICAL_DOCUMENT)) {
			formatCodeValueListType.getValue().add(
					FORMAT_CODE_CLINICAL_DOCUMENT);
		} else if (xdsbDocumentType.equals(XdsbDocumentType.PRIVACY_CONSENT)) {
			formatCodeValueListType.getValue().add(FORMAT_CODE_PRIVACY_CONSENT);
		} else {
			logger.error(messageId, "Unsupported XDS.b document format code");
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
	 * @param messageId
	 *            the message id
	 * @return the adhoc query response
	 * @throws XdsbRegistryAdapterException
	 *             the xdsb registry adapter exception
	 */
	private AdhocQueryResponse registryStoredQueryFilterByAuthorNPI(
			AdhocQueryRequest req, String authorNPI, String messageId)
			throws XdsbRegistryAdapterException {
		try {
			// Query Response
			AdhocQueryResponse adhocQueryResponse = registryStoredQuery(req);
			if (authorNPI != null && !"".equals(authorNPI)) {
				adhocQueryResponse = responseFilter.filterByAuthor(
						adhocQueryResponse, authorNPI);
			}
			logger.debug(messageId, marshaller.marshall(adhocQueryResponse));
			return adhocQueryResponse;
		} catch (SimpleMarshallerException e) {
			throw new XdsbRegistryAdapterException(e);
		}
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
