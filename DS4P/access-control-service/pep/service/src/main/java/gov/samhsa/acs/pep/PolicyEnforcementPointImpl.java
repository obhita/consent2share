package gov.samhsa.acs.pep;

import static gov.samhsa.acs.audit.AcsAuditVerb.DIRECT_EMAIL_SEND_REQUEST;
import static gov.samhsa.acs.audit.AcsAuditVerb.DIRECT_EMAIL_SEND_RESPONSE;
import static gov.samhsa.acs.audit.AcsAuditVerb.DIRECT_EMAIL_SEND_RESPONSE_ERROR_CONSENT;
import static gov.samhsa.acs.audit.AcsAuditVerb.DIRECT_EMAIL_SEND_RESPONSE_ERROR_SYSTEM;
import static gov.samhsa.acs.audit.AcsAuditVerb.REGISTRY_STORED_QUERY_REQUEST;
import static gov.samhsa.acs.audit.AcsAuditVerb.REGISTRY_STORED_QUERY_RESPONSE;
import static gov.samhsa.acs.audit.AcsAuditVerb.REGISTRY_STORED_QUERY_RESPONSE_ERROR_CONSENT;
import static gov.samhsa.acs.audit.AcsAuditVerb.REGISTRY_STORED_QUERY_RESPONSE_ERROR_SYSTEM;
import static gov.samhsa.acs.audit.AcsAuditVerb.RETRIEVE_DOCUMENT_SET_REQUEST;
import static gov.samhsa.acs.audit.AcsAuditVerb.RETRIEVE_DOCUMENT_SET_RESPONSE;
import static gov.samhsa.acs.audit.AcsAuditVerb.RETRIEVE_DOCUMENT_SET_RESPONSE_ERROR_CONSENT;
import static gov.samhsa.acs.audit.AcsAuditVerb.RETRIEVE_DOCUMENT_SET_RESPONSE_ERROR_SYSTEM;
import static gov.samhsa.acs.audit.AcsPredicateKey.INTERMEDIARY_SUBJECT_NPI;
import static gov.samhsa.acs.audit.AcsPredicateKey.PATIENT_UNIQUE_ID;
import static gov.samhsa.acs.audit.AcsPredicateKey.PURPOSE_OF_USE;
import static gov.samhsa.acs.audit.AcsPredicateKey.RECIPIENT_SUBJECT_NPI;
import static gov.samhsa.acs.audit.AcsPredicateKey.REQUEST_BODY;
import static gov.samhsa.acs.audit.AcsPredicateKey.RESPONSE_BODY;
import gov.samhsa.acs.audit.AuditService;
import gov.samhsa.acs.audit.AuditVerb;
import gov.samhsa.acs.audit.PredicateKey;
import gov.samhsa.acs.brms.domain.SubjectPurposeOfUse;
import gov.samhsa.acs.brms.domain.XacmlResult;
import gov.samhsa.acs.common.dto.PdpRequestResponse;
import gov.samhsa.acs.common.dto.XacmlRequest;
import gov.samhsa.acs.common.dto.XacmlResponse;
import gov.samhsa.acs.common.log.AcsLogger;
import gov.samhsa.acs.common.log.AcsLoggerFactory;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.common.tool.exception.SimpleMarshallerException;
import gov.samhsa.acs.common.validation.exception.XmlDocumentReadFailureException;
import gov.samhsa.acs.contexthandler.ContextHandler;
import gov.samhsa.acs.contexthandler.exception.NoPolicyFoundException;
import gov.samhsa.acs.contexthandler.exception.PolicyProviderException;
import gov.samhsa.acs.documentsegmentation.DocumentSegmentation;
import gov.samhsa.acs.documentsegmentation.exception.InvalidOriginalClinicalDocumentException;
import gov.samhsa.acs.documentsegmentation.exception.InvalidSegmentedClinicalDocumentException;
import gov.samhsa.acs.pep.aspect.AspectUtils;
import gov.samhsa.acs.pep.exception.InconsistentPatientUniqueIdException;
import gov.samhsa.acs.pep.exception.MissingParameterException;
import gov.samhsa.acs.pep.exception.MultipleRepositoryIdException;
import gov.samhsa.acs.pep.exception.NoDocumentFoundException;
import gov.samhsa.acs.pep.exception.PolicyEnforcementPointException;
import gov.samhsa.acs.pep.exception.UnsupportedFormatCodeException;
import gov.samhsa.acs.pep.exception.UnsupportedResponseOptionTypeException;
import gov.samhsa.acs.pep.saml.SamlTokenParser;
import gov.samhsa.acs.pep.saml.exception.SamlTokenParserException;
import gov.samhsa.acs.pep.saml.exception.SamlTokenPrincipalException;
import gov.samhsa.acs.xdsb.common.XdsbErrorFactory;
import gov.samhsa.acs.xdsb.registry.wsclient.adapter.XdsbRegistryAdapter;
import gov.samhsa.acs.xdsb.registry.wsclient.exception.XdsbRegistryAdapterException;
import gov.samhsa.acs.xdsb.repository.wsclient.adapter.XdsbRepositoryAdapter;
import gov.samhsa.acs.xdsb.repository.wsclient.exception.XdsbRepositoryAdapterException;
import gov.samhsa.consent2share.schema.documentsegmentation.SegmentDocumentResponse;
import gov.samhsa.ds4ppilot.schema.pep.DirectEmailSendRequest;
import gov.samhsa.ds4ppilot.schema.pep.DirectEmailSendResponse;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest.DocumentRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse.DocumentResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.apache.ws.security.SAMLTokenPrincipal;
import org.springframework.util.Assert;

import ch.qos.logback.audit.AuditException;

/**
 * The Class PolicyEnforcementPointImpl.
 */
public class PolicyEnforcementPointImpl implements PolicyEnforcementPoint,
		PolicyTrying {
	// Constants
	/** The Constant PERMIT. */
	public static final String PERMIT = "PERMIT";

	/** The Constant SUPPORTED_ADHOCQUERY_RESPONSE_TYPE. */
	public static final String SUPPORTED_ADHOCQUERY_RESPONSE_TYPE = "LeafClass";

	/** The Constant URN_RESOURCE_ID. */
	public static final String URN_RESOURCE_ID = "urn:oasis:names:tc:xacml:1.0:resource:resource-id";

	/** The Constant URN_INTERMEDIARY_SUBJECT. */
	public static final String URN_INTERMEDIARY_SUBJECT = "urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject";

	/** The Constant URN_RECIPIENT_SUBJECT. */
	public static final String URN_RECIPIENT_SUBJECT = "urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject";

	/** The Constant URN_PURPOSE_OF_USE. */
	public static final String URN_PURPOSE_OF_USE = "urn:oasis:names:tc:xspa:1.0:subject:purposeofuse";

	/** The logger. */
	private final AcsLogger logger = AcsLoggerFactory
			.getLogger(this.getClass());

	// Services
	/** The xdsb registry. */
	private XdsbRegistryAdapter xdsbRegistry;

	/** The xdsb repository. */
	private XdsbRepositoryAdapter xdsbRepository;

	/** The xdsb error factory. */
	private XdsbErrorFactory xdsbErrorFactory;

	/** The context handler. */
	private ContextHandler contextHandler;

	/** The document segmentation. */
	private DocumentSegmentation documentSegmentation;

	/** The marshaller. */
	private SimpleMarshaller marshaller;

	/** The context. */
	@Resource
	private WebServiceContext context;

	/** The saml token parser. */
	private SamlTokenParser samlTokenParser;

	/** The data handler to bytes converter. */
	private DataHandlerToBytesConverter dataHandlerToBytesConverter;

	/** The audit service. */
	private AuditService auditService;

	/** The aspect utils. */
	private AspectUtils aspectUtils;

	/**
	 * Instantiates a new policy enforcement point impl.
	 */
	public PolicyEnforcementPointImpl() {
	}

	/**
	 * Instantiates a new policy enforcement point impl.
	 * 
	 * @param xdsbRegistry
	 *            the xdsb registry
	 * @param xdsbRepository
	 *            the xdsb repository
	 * @param xdsbErrorFactory
	 *            the xdsb error factory
	 * @param contextHandler
	 *            the context handler
	 * @param documentSegmentation
	 *            the document segmentation
	 * @param marshaller
	 *            the marshaller
	 * @param context
	 *            the context
	 * @param parser
	 *            the parser
	 * @param dataHandlerToBytesConverter
	 *            the data handler to bytes converter
	 * @param auditService
	 *            the audit service
	 * @param aspectUtils
	 *            the aspect utils
	 */
	public PolicyEnforcementPointImpl(XdsbRegistryAdapter xdsbRegistry,
			XdsbRepositoryAdapter xdsbRepository,
			XdsbErrorFactory xdsbErrorFactory, ContextHandler contextHandler,
			DocumentSegmentation documentSegmentation,
			SimpleMarshaller marshaller, WebServiceContext context,
			SamlTokenParser parser,
			DataHandlerToBytesConverter dataHandlerToBytesConverter,
			AuditService auditService, AspectUtils aspectUtils) {
		super();
		this.xdsbRegistry = xdsbRegistry;
		this.xdsbRepository = xdsbRepository;
		this.xdsbErrorFactory = xdsbErrorFactory;
		this.contextHandler = contextHandler;
		this.documentSegmentation = documentSegmentation;
		this.marshaller = marshaller;
		this.context = context;
		this.samlTokenParser = parser;
		if (this.samlTokenParser == null) {
			this.samlTokenParser = new SamlTokenParser();
		}
		this.dataHandlerToBytesConverter = dataHandlerToBytesConverter;
		this.auditService = auditService;
		this.aspectUtils = aspectUtils;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.pep.PolicyEnforcementPoint#registryStoredQuery(oasis
	 * .names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest)
	 */
	@Override
	public AdhocQueryResponse registryStoredQuery(AdhocQueryRequest req) {
		AdhocQueryResponse response = null;
		XacmlRequest xacmlRequest = null;
		String resourceId = null;
		String intermediarySubject = null;
		String purposeOfUse = null;
		String recipientSubject = null;
		final String messageId = createMessageId();
		PolicyEnforcementPointResponseStatus pepResponseStatus = PolicyEnforcementPointResponseStatus.SUCCESS;
		try {
			// logging AdhocQueryRequest
			logger.debug(messageId, aspectUtils.getInXMLFormat(req));

			SAMLTokenPrincipal samlTokenPrincipal = getUserPrincipal(messageId);

			resourceId = parseSamlToken(samlTokenPrincipal,
					URN_RESOURCE_ID, messageId);
			intermediarySubject = parseSamlToken(samlTokenPrincipal,
					URN_INTERMEDIARY_SUBJECT, messageId);
			purposeOfUse = parseSamlToken(samlTokenPrincipal,
					URN_PURPOSE_OF_USE, messageId);
			recipientSubject = parseSamlToken(samlTokenPrincipal,
					URN_RECIPIENT_SUBJECT, messageId);

			xacmlRequest = setXacmlRequest(resourceId,
					purposeOfUse, intermediarySubject, recipientSubject,
					messageId);

			// logging XacmlRequest
			logger.debug(messageId, aspectUtils.getInXMLFormat(xacmlRequest));

			// Audit incoming registryStoredQueryRequest
			auditRequest(req, xacmlRequest);

			// Validate input parameters
			validateAdhocQueryRequest(req, messageId);

			// Validate format code (only '2.16.840.1.113883.10.20.1^^HITSP' is
			// supported)
			String formatCode = xdsbRegistry.extractFormatCode(req);
			validateFormatCode(formatCode, messageId);

			// Validate response option type (only 'LeafClass' is supported,
			// because PEP requires metadata for filtering query response)
			validateResponseOptionType(req, messageId);

			// The patient id in $XDSDocumentEntryPatientId
			String patientUniqueId = xdsbRegistry.extractPatientId(req);
			validatePatientUniqueIdConsistency(patientUniqueId,
					xacmlRequest.getPatientUniqueId(), messageId);

			// Enforce policy
			XacmlResponse xacmlResponse = contextHandler
					.enforcePolicy(xacmlRequest);

			// logging xacmlResponse
			logger.info(messageId, "PDP Decision : " + xacmlResponse.getPdpDecision() );
			logger.debug(messageId, aspectUtils.getInXMLFormat(xacmlResponse));
			
			// If PDP returns PERMIT
			if (PERMIT.equals(xacmlResponse.getPdpDecision())) {
				// Search for clinical documents of the patient
				response = xdsbRegistry.registryStoredQuery(req,
						intermediarySubject, messageId);

				// logging AdhocQueryResponse
				logger.debug(messageId, aspectUtils.getInXMLFormat(response));

				// If the response returns empty, it means either there are no
				// documents found or they are filtered out by the authorId
				// (intermediaryNPI)
				validateResponse(response, patientUniqueId,
						intermediarySubject, messageId);
			} else {
				// PDP did not return PERMIT
				handlePDPDeny(messageId, xacmlRequest, xacmlResponse);
			}
			
			// Handle Errors
		} catch (SamlTokenPrincipalException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.error(messageId, "SAMLTokenPrincipal cannot be retrieved from WebServiceContext.", e);
			response = xdsbErrorFactory.errorAdhocQueryResponseInternalServerError();
		} catch (SamlTokenParserException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.error(messageId, "SamlTokenParser failed.", e);
			response = xdsbErrorFactory.errorAdhocQueryResponseInternalServerError();
		} catch (AuditException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.error(messageId, "AuditService failed.", e);
			response = xdsbErrorFactory.errorAdhocQueryResponseInternalServerError();
		} catch (XdsbRegistryAdapterException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.error(messageId, "XdsbRegistryAdapter failed.", e);
			response = xdsbErrorFactory.errorAdhocQueryResponseRegistryNotAvailable();
		} catch (MissingParameterException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.info(messageId, e.getMessage());
			logger.debug(messageId, e.getMessage(), e);
			response = xdsbErrorFactory.errorAdhocQueryResponseMissingParameters();
		} catch (UnsupportedFormatCodeException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.info(messageId, e.getMessage());
			logger.debug(messageId, e.getMessage(), e);
			response = xdsbErrorFactory.errorAdhocQueryResponseConstructByErrorMessage(e.getMessage());
		} catch (UnsupportedResponseOptionTypeException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.info(messageId, e.getMessage());
			logger.debug(messageId, e.getMessage(), e);
			response = xdsbErrorFactory.errorAdhocQueryResponseConstructByErrorMessage(e.getMessage());
		} catch (InconsistentPatientUniqueIdException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.info(messageId, e.getMessage());
			logger.debug(messageId, e.getMessage(), e);
			response = xdsbErrorFactory.errorAdhocQueryResponseConstructByErrorMessage(e.getMessage());
		} catch (NoPolicyFoundException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_CONSENT;
			logger.info(messageId, e.getMessage());
			logger.debug(messageId, e.getMessage(), e);
			response = xdsbErrorFactory.errorAdhocQueryResponseConstructByErrorMessage(e.getMessage());
		} catch (PolicyProviderException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.error(messageId, "PolicyProvider failed.", e);
			response = xdsbErrorFactory.errorAdhocQueryResponseInternalServerError();
		} catch (NoDocumentFoundException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.info(messageId, e.getMessage());
			logger.debug(messageId, e.getMessage(), e);
			response = xdsbErrorFactory.errorAdhocQueryResponseConstructByErrorMessage(e.getMessage());
		} catch (Throwable t) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.error(messageId, "Unexpected exception occured in PolicyEnforcementPoint.", t);
			response = xdsbErrorFactory.errorAdhocQueryResponseInternalServerError();
		} finally {
			// Audit response	
			try {
				if(xacmlRequest == null){
					xacmlRequest = setXacmlRequestIfNull(resourceId, messageId);
				}
				auditResponse(response, xacmlRequest, pepResponseStatus);
			} catch (Throwable e) {
				logger.error(messageId, "Cannot audit the response.", e);
				response = xdsbErrorFactory.errorAdhocQueryResponseInternalServerError();
			}				
		}		

		Assert.notNull(response, "The response cannot be null.");
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.pep.PolicyEnforcementPoint#retrieveDocumentSet(ihe
	 * .iti.xds_b._2007.RetrieveDocumentSetRequest)
	 */
	@Override
	public RetrieveDocumentSetResponse retrieveDocumentSet(
			RetrieveDocumentSetRequest input) {
		RetrieveDocumentSetResponse response = null;
		XacmlRequest xacmlRequest = null;
		String resourceId = null;
		String intermediarySubject = null;
		String purposeOfUse = null;
		String recipientSubject = null;
		final String messageId = createMessageId();
		PolicyEnforcementPointResponseStatus pepResponseStatus = PolicyEnforcementPointResponseStatus.SUCCESS;
		try {
			// logging RetrieveDocumentSetRequest
			logger.debug(messageId, aspectUtils.getInXMLFormat(input));

			SAMLTokenPrincipal samlTokenPrincipal = getUserPrincipal(messageId);

			resourceId = parseSamlToken(samlTokenPrincipal,
					URN_RESOURCE_ID, messageId);
			intermediarySubject = parseSamlToken(samlTokenPrincipal,
					URN_INTERMEDIARY_SUBJECT, messageId);
			purposeOfUse = parseSamlToken(samlTokenPrincipal,
					URN_PURPOSE_OF_USE, messageId);
			recipientSubject = parseSamlToken(samlTokenPrincipal,
					URN_RECIPIENT_SUBJECT, messageId);

			xacmlRequest = setXacmlRequest(resourceId,
					purposeOfUse, intermediarySubject, recipientSubject,
					messageId);

			// logging XacmlRequest
			logger.debug(messageId, aspectUtils.getInXMLFormat(xacmlRequest));

			// Audit incoming retrieveDocumentSetRequest
			auditRequest(input, xacmlRequest);

			// Validate request (check for multiple repository ids)
			validateRetrieveDocumentSetRequest(input, messageId);

			// Enforce policy
			XacmlResponse xacmlResponse = contextHandler
					.enforcePolicy(xacmlRequest);

			// logging xacmlResponse
			logger.debug(messageId, aspectUtils.getInXMLFormat(xacmlResponse));

			// If PDP returns PERMIT
			if (PERMIT.equals(xacmlResponse.getPdpDecision())) {
				// Retrieve documents
				response = xdsbRepository.retrieveDocumentSet(input,
						xacmlRequest.getPatientId(),
						xacmlRequest.getIntermediarySubjectNPI());

				// logging RetrieveDocumentSetResponse
				logger.debug(messageId, aspectUtils.getInXMLFormat(response));

				// Validate response
				validateRetrieveDocumentSetResponse(response, messageId);

				// Start segmentation
				segmentResponse(response, messageId, xacmlRequest,
						xacmlResponse);
			} else {
				// PDP did not return PERMIT
				handlePDPDeny(messageId, xacmlRequest, xacmlResponse);
			}
			
			// Handle errors			
		} catch (SamlTokenPrincipalException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.error(messageId, "SAMLTokenPrincipal cannot be retrieved from WebServiceContext.", e);
			response = xdsbErrorFactory.errorRetrieveDocumentSetInternalServerError();
		} catch (SamlTokenParserException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.error(messageId, "SamlTokenParser failed.", e);
			response = xdsbErrorFactory.errorRetrieveDocumentSetInternalServerError();
		} catch (AuditException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.error(messageId, "AuditService failed.", e);
			response = xdsbErrorFactory.errorRetrieveDocumentSetInternalServerError();
		} catch (MultipleRepositoryIdException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.info(messageId, e.getMessage());
			logger.debug(messageId, e.getMessage(), e);
			response = xdsbErrorFactory.errorRetrieveDocumentSetResponseConstructByErrorMessage(e.getMessage());
		} catch (NoPolicyFoundException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_CONSENT;
			logger.info(messageId, e.getMessage());
			logger.debug(messageId, e.getMessage(), e);
			response = xdsbErrorFactory.errorRetrieveDocumentSetResponseConstructByErrorMessage(e.getMessage());
		} catch (PolicyProviderException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.error(messageId, "PolicyProvider failed.", e);
			response = xdsbErrorFactory.errorRetrieveDocumentSetInternalServerError();
		} catch (XdsbRepositoryAdapterException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.error(messageId, "XdsbRepositoryAdapter failed.", e);
			response = xdsbErrorFactory.errorRetrieveDocumentSetResponseRepositoryNotAvailable();
		} catch (NoDocumentFoundException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.info(messageId, e.getMessage());
			logger.debug(messageId, e.getMessage(), e);
			response = xdsbErrorFactory.errorRetrieveDocumentSetResponseNotExistsOrAccessible(input);
		} catch (SimpleMarshallerException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.error(messageId, "SimpleMarshaller failed.", e);
			response = xdsbErrorFactory.errorRetrieveDocumentSetInternalServerError();
		} catch (Throwable t) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.error(messageId, "Unexpected exception occured in PolicyEnforcementPoint.", t);
			response = xdsbErrorFactory.errorRetrieveDocumentSetInternalServerError();
		} finally {
			// Audit response
			try {
				if(xacmlRequest == null){
					xacmlRequest = setXacmlRequestIfNull(resourceId, messageId);
				}
				auditResponse(response, xacmlRequest, pepResponseStatus);
			} catch (Throwable e) {
				logger.error(messageId, "Cannot audit the response.", e);
				response = xdsbErrorFactory.errorRetrieveDocumentSetInternalServerError();
			}
		}
		
		Assert.notNull(response, "The response cannot be null.");
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.pep.PolicyEnforcementPoint#directEmailSend(gov.samhsa
	 * .ds4ppilot.schema.pep.DirectEmailSendRequest)
	 */
	@Override
	public DirectEmailSendResponse directEmailSend(DirectEmailSendRequest input) {
		DirectEmailSendResponse response = new DirectEmailSendResponse();
		XacmlRequest xacmlRequest = null;
		String resourceId = null;
		String intermediarySubject = null;
		String purposeOfUse = null;
		String recipientSubject = null;
		final String messageId = createMessageId();
		final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
		final String NO_POLICY = "NO_POLICY";
		PolicyEnforcementPointResponseStatus pepResponseStatus = PolicyEnforcementPointResponseStatus.SUCCESS;
		try {
			// logging DirectEmailSendRequest
			logger.debug(messageId, aspectUtils.getInXMLFormat(input));

			SAMLTokenPrincipal samlTokenPrincipal = getUserPrincipal(messageId);

			resourceId = parseSamlToken(samlTokenPrincipal,
					URN_RESOURCE_ID, messageId);
			intermediarySubject = parseSamlToken(samlTokenPrincipal,
					URN_INTERMEDIARY_SUBJECT, messageId);
			purposeOfUse = parseSamlToken(samlTokenPrincipal,
					URN_PURPOSE_OF_USE, messageId);
			recipientSubject = parseSamlToken(samlTokenPrincipal,
					URN_RECIPIENT_SUBJECT, messageId);

			xacmlRequest = setXacmlRequest(resourceId,
					purposeOfUse, intermediarySubject, recipientSubject,
					messageId);

			// logging XacmlRequest
			logger.debug(messageId, aspectUtils.getInXMLFormat(xacmlRequest));

			// Audit incoming retrieveDocumentSetRequest
			auditRequest(input, xacmlRequest);

			// Enforce policy
			XacmlResponse xacmlResponse = contextHandler
					.enforcePolicy(xacmlRequest);

			// logging xacmlResponse
			logger.debug(messageId, aspectUtils.getInXMLFormat(xacmlResponse));

			if (PERMIT.equals(xacmlResponse.getPdpDecision())) {
				response.setPdpDecision(PERMIT);
				String originalC32 = input.getC32();

				byte[] processedPayload;

				XacmlResult xacmlResult = createXacmlResult(xacmlRequest,
						xacmlResponse);

				String xacmlResultString = marshaller.marshall(xacmlResult);

				String xdsDocumentEntryUniqueId = "";
				// packageAsXdm = input.getPackageAsXdm()
				boolean packageAsXdm = true;
				SegmentDocumentResponse segmentDocumentResponse = documentSegmentation
						.segmentDocument(originalC32, xacmlResultString, true);
				documentSegmentation.setAdditionalMetadataForSegmentedClinicalDocument(segmentDocumentResponse, input.getSenderEmail(), input.getRecipientEmail(), xdsDocumentEntryUniqueId, xacmlResult);
				documentSegmentation.setDocumentPayloadRawData(segmentDocumentResponse, packageAsXdm, input.getSenderEmail(), input.getRecipientEmail(), xacmlResult);

				processedPayload = dataHandlerToBytesConverter
						.toByteArray(segmentDocumentResponse.getDocumentPayloadRawData());

				response.setMaskedDocument(segmentDocumentResponse.getSegmentedDocumentXml());
				response.setFilteredStreamBody(processedPayload);

			} else {
				handlePDPDeny(messageId, xacmlRequest, xacmlResponse);
			}
			
			// Handle errors
		} catch (SamlTokenPrincipalException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.error(messageId, "SAMLTokenPrincipal cannot be retrieved from WebServiceContext.", e);
			response.setPdpDecision(INTERNAL_SERVER_ERROR);
		} catch (SamlTokenParserException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.error(messageId, "SamlTokenParser failed.", e);
			response.setPdpDecision(INTERNAL_SERVER_ERROR);
		} catch (AuditException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.error(messageId, "AuditService failed.", e);
			response.setPdpDecision(INTERNAL_SERVER_ERROR);
		} catch (NoPolicyFoundException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_CONSENT;
			logger.info(messageId, e.getMessage());
			logger.debug(messageId, e.getMessage(), e);
			response.setPdpDecision(NO_POLICY);
		} catch (PolicyProviderException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.error(messageId, "PolicyProvider failed.", e);
			response.setPdpDecision(INTERNAL_SERVER_ERROR);
		} catch (InvalidOriginalClinicalDocumentException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			StringBuilder errorBuilder = new StringBuilder();
			errorBuilder.append("Schema validation failed: Invalid document before segmentation: ");
			errorBuilder.append(e.getMessage());
			errorBuilder.append(".");
			logger.debug(messageId, errorBuilder.toString(), e);
			logger.error(messageId, errorBuilder.toString());
			response.setPdpDecision(INTERNAL_SERVER_ERROR);
		} catch (InvalidSegmentedClinicalDocumentException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			StringBuilder errorBuilder = new StringBuilder();
			errorBuilder.append("Schema validation failed: Invalid document after segmentation: ");
			errorBuilder.append(e.getMessage());
			errorBuilder.append(".");
			logger.debug(messageId, errorBuilder.toString(), e);
			logger.error(messageId, errorBuilder.toString());
			response.setPdpDecision(INTERNAL_SERVER_ERROR);
		} catch (XmlDocumentReadFailureException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			StringBuilder errorBuilder = new StringBuilder();
			errorBuilder.append("Schema validation failed: Unable to load schema: ");
			errorBuilder.append(e.getMessage());
			errorBuilder.append(".");
			logger.error(messageId, errorBuilder.toString(), e);
			response.setPdpDecision(INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.error(messageId, "DataHandlerToBytesConverter failed.", e);
			response.setPdpDecision(INTERNAL_SERVER_ERROR);
		} catch (SimpleMarshallerException e) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.error(messageId, "SimpleMarshaller failed.", e);
			response.setPdpDecision(INTERNAL_SERVER_ERROR);
		} catch (Throwable t) {
			pepResponseStatus = PolicyEnforcementPointResponseStatus.ERROR_SYSTEM;
			logger.error(messageId, "Unexpected exception occured in PolicyEnforcementPoint.", t);
			response.setPdpDecision(INTERNAL_SERVER_ERROR);
		}finally {
			// Audit response			
			try {
				if(xacmlRequest == null){
					xacmlRequest = setXacmlRequestIfNull(resourceId, messageId);
				}
				auditResponse(response, xacmlRequest, pepResponseStatus);
			} catch (Throwable e) {
				logger.error(messageId, "Cannot audit the response.", e);
				response.setPdpDecision(INTERNAL_SERVER_ERROR);
			}
		}

		Assert.notNull(response.getPdpDecision());
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.pep.PolicyTrying#tryPolicy(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public String tryPolicy(String c32Xml, String xacmlPolicy,
			String purposeOfUse) throws PolicyEnforcementPointException {
		final String messageId = createMessageId();
		SegmentDocumentResponse segmentDocumentResponse;
		try {
			PdpRequestResponse pdpRequestResponse = contextHandler
					.makeDecisionForTryingPolicy(xacmlPolicy, purposeOfUse);
			XacmlResponse xacmlResponse = pdpRequestResponse.getXacmlResponse();

			XacmlRequest xacmlRequest = pdpRequestResponse.getXacmlRequest();

			XacmlResult xacmlResult = createXacmlResult(xacmlRequest,
					xacmlResponse);

			String enforcementPolicies = marshaller.marshall(xacmlResult);

			segmentDocumentResponse = documentSegmentation.segmentDocument(
					c32Xml, enforcementPolicies, false);
		} catch (Throwable t) {
			logger.error(messageId, "Error in tryPolicy", t);
			throw new PolicyEnforcementPointException(t);
		}

		return segmentDocumentResponse.getSegmentedDocumentXml();
	}

	/**
	 * Sets the xacml request.
	 * 
	 * @param resourceId
	 *            the resource id
	 * @param purposeOfUse
	 *            the purpose of use
	 * @param intermediarySubject
	 *            the intermediary subject
	 * @param recipientSubject
	 *            the recipient subject
	 * @param messageId
	 *            the message id
	 * @return the xacml request
	 */
	public XacmlRequest setXacmlRequest(String resourceId, String purposeOfUse,
			String intermediarySubject, String recipientSubject,
			String messageId) {

		XacmlRequest xacmlRequest = new XacmlRequest();

		String homeCommunityId = "";
		String patientId = "";

		// Sample resourceId:
		// "d3bb3930-7241-11e3-b4f7-00155d3a2124^^^&2.16.840.1.113883.4.357&ISO"
		StringTokenizer tokenizer = new StringTokenizer(resourceId, "&");
		patientId = tokenizer.nextToken().replace("^^^", "");
		homeCommunityId = tokenizer.nextToken();

		xacmlRequest.setHomeCommunityId(homeCommunityId);
		xacmlRequest.setIntermediarySubjectNPI(intermediarySubject);
		xacmlRequest.setMessageId(messageId);
		xacmlRequest.setPurposeOfUse(purposeOfUse);
		xacmlRequest.setRecipientSubjectNPI(recipientSubject);
		xacmlRequest.setPatientId(patientId);

		xacmlRequest.setPatientUniqueId("'" + resourceId + "'");
		return xacmlRequest;
	}
	
	/**
	 * Sets the xacml request if null.
	 *
	 * @param resourceId the resource id
	 * @param messageId the message id
	 * @return the xacml request
	 */
	public XacmlRequest setXacmlRequestIfNull(String resourceId,
			final String messageId) {
		XacmlRequest xacmlRequest = new XacmlRequest();
		xacmlRequest.setMessageId(messageId);
		xacmlRequest.setPatientId(resourceId);
		return xacmlRequest;
	}	

	/**
	 * Validate adhoc query request.
	 * 
	 * @param req
	 *            the req
	 * @param messageId
	 *            the message id
	 * @return true, if successful
	 * @throws XdsbRegistryAdapterException
	 *             the xdsb registry adapter exception
	 * @throws MissingParameterException
	 *             the missing parameter exception
	 */
	boolean validateAdhocQueryRequest(AdhocQueryRequest req, String messageId)
			throws XdsbRegistryAdapterException, MissingParameterException {
		String formatCode = null;
		String pid = null;
		String status = null;
		try {
			formatCode = xdsbRegistry.extractFormatCode(req);
			pid = xdsbRegistry.extractPatientId(req);
			status = xdsbRegistry.extractDocumentEntryStatus(req);
		} catch (Throwable t) {
			logger.error(messageId, t.getMessage());
			String errMsg = "XdsbRegistryAdapter failed while trying to extract formatCode, patientId or documentEntryStatus from AdhocQueryRequest.";
			logger.error(messageId, errMsg);
			throw new XdsbRegistryAdapterException(errMsg);
		}
		boolean valid = isNotNull(formatCode) && isNotNull(pid) && isNotNull(status);
		if (!valid) {
			String errMsg = "Missing required parameters in AdhocQueryRequest.";
			logger.debug(messageId, errMsg);
			throw new MissingParameterException(errMsg);
		}
		return valid;
	}

	/**
	 * Segment response.
	 * 
	 * @param response
	 *            the response
	 * @param messageId
	 *            the message id
	 * @param xacmlRequest
	 *            the xacml request
	 * @param xacmlResponse
	 *            the xacml response
	 * @throws SimpleMarshallerException
	 *             the simple marshaller exception
	 * @throws AuditException
	 *             the audit exception
	 */
	private void segmentResponse(RetrieveDocumentSetResponse response,
			final String messageId, XacmlRequest xacmlRequest,
			XacmlResponse xacmlResponse) throws SimpleMarshallerException,
			AuditException {
		XacmlResult xacmlResult = createXacmlResult(xacmlRequest, xacmlResponse);
		String enforcementPolicies = marshaller.marshall(xacmlResult);
		Map<String, String> errorMap = new HashMap<String, String>();
		List<DocumentResponse> removeList = new LinkedList<DocumentResponse>();
		for (DocumentResponse documentResponse : response.getDocumentResponse()) {
			String document = new String(documentResponse.getDocument());

			String xdsDocumentEntryUniqueId = documentResponse
					.getDocumentUniqueId();

			SegmentDocumentResponse segmentDocumentResponse = null;
			try {
				segmentDocumentResponse = documentSegmentation.segmentDocument(
						document, enforcementPolicies, true);
			} catch (InvalidOriginalClinicalDocumentException e) {
				StringBuilder errorBuilder = new StringBuilder();
				errorBuilder.append("Schema validation failed: Invalid document before segmentation: ");
				errorBuilder.append(e.getMessage());
				errorBuilder.append(".");
				logger.debug(messageId, errorBuilder.toString(), e);
				logger.error(messageId, errorBuilder.toString());
				handleXmlValidationError(documentResponse, removeList, errorMap, xdsDocumentEntryUniqueId);
			} catch (InvalidSegmentedClinicalDocumentException e) {
				StringBuilder errorBuilder = new StringBuilder();
				errorBuilder.append("Schema validation failed: Invalid document after segmentation: ");
				errorBuilder.append(e.getMessage());
				errorBuilder.append(".");
				logger.debug(messageId, errorBuilder.toString(), e);
				logger.error(messageId, errorBuilder.toString());
				handleXmlValidationError(documentResponse, removeList, errorMap, xdsDocumentEntryUniqueId);
			} catch (XmlDocumentReadFailureException e) {
				StringBuilder errorBuilder = new StringBuilder();
				errorBuilder
						.append("Schema validation failed: Unable to load schema: ");
				errorBuilder.append(e.getMessage());
				errorBuilder.append(".");
				logger.error(messageId, errorBuilder.toString(), e);
				handleXmlValidationError(documentResponse, removeList, errorMap, xdsDocumentEntryUniqueId);
			}
			if (segmentDocumentResponse != null) {
				documentResponse.setDocument(segmentDocumentResponse.getSegmentedDocumentXml().getBytes());
			}

			// logging SegmentDocumentResponse
			logger.debug(messageId, aspectUtils.getInXMLFormat(segmentDocumentResponse));

		}

		// If there are any document failed schema validation
		if (removeList.size() > 0) {
			// Remove invalid document
			response.getDocumentResponse().removeAll(removeList);
			// Add proper error message in the response, and return it
			xdsbErrorFactory.errorRetrieveDocumentSetResponseSchemaValidation(response, errorMap);
		}
	}

	/**
	 * Handle xml validation error.
	 * 
	 * @param documentResponse
	 *            the document response
	 * @param removeList
	 *            the remove list
	 * @param errorMap
	 *            the error map
	 * @param xdsDocumentEntryUniqueId
	 *            the xds document entry unique id
	 */
	private void handleXmlValidationError(DocumentResponse documentResponse,
			List<DocumentResponse> removeList, Map<String, String> errorMap,
			String xdsDocumentEntryUniqueId) {
		StringBuilder builder = new StringBuilder();
		builder.append("Document validation error occured for documentUniqueId='");
		builder.append(xdsDocumentEntryUniqueId);
		builder.append("'. Please contact to system administrator if this error persists.");
		errorMap.put(xdsDocumentEntryUniqueId, builder.toString());
		removeList.add(documentResponse);
	}

	/**
	 * Checks if is not null.
	 * 
	 * @param s
	 *            the s
	 * @return true, if is not null
	 */
	private boolean isNotNull(String s) {
		return s != null && !"".equals(s);
	}

	/**
	 * Sets the xacml result.
	 * 
	 * @param xacmlRequest
	 *            the xacml request
	 * @param xacmlResponse
	 *            the xacml response
	 * @return the xacml result
	 */
	private XacmlResult createXacmlResult(XacmlRequest xacmlRequest,
			XacmlResponse xacmlResponse) {
		XacmlResult xacmlResult = new XacmlResult();
		xacmlResult.setHomeCommunityId(xacmlRequest.getHomeCommunityId());
		xacmlResult.setMessageId(xacmlRequest.getMessageId());
		xacmlResult.setPdpDecision(xacmlResponse.getPdpDecision());
		xacmlResult.setPdpObligations(xacmlResponse.getPdpObligation());
		xacmlResult.setSubjectPurposeOfUse(SubjectPurposeOfUse
				.fromAbbreviation(xacmlRequest.getPurposeOfUse()));
		xacmlResult.setPatientId(xacmlRequest.getPatientId());
		return xacmlResult;
	}

	/**
	 * Creates the message id.
	 * 
	 * @return the string
	 */
	private String createMessageId() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Audit request.
	 * 
	 * @param request
	 *            the request
	 * @param xacmlRequest
	 *            the xacml request
	 * @throws AuditException
	 *             the audit exception
	 */
	private void auditRequest(Object request, XacmlRequest xacmlRequest)
			throws AuditException {
		AuditVerb requestType = null;
		if (request instanceof AdhocQueryRequest) {
			requestType = REGISTRY_STORED_QUERY_REQUEST;
		} else if (request instanceof RetrieveDocumentSetRequest) {
			requestType = RETRIEVE_DOCUMENT_SET_REQUEST;
		} else if (request instanceof DirectEmailSendRequest) {
			requestType = DIRECT_EMAIL_SEND_REQUEST;
		} else {
			StringBuilder builder = new StringBuilder();
			builder.append("Invalid request type: ");
			builder.append(request.getClass().getName());
			builder.append(": This request cannot be audited by Policy Enforcement Point.");
			String errMsg = builder.toString();
			logger.error(xacmlRequest.getMessageId(), errMsg);
			throw new AuditException(errMsg);
		}
		Map<PredicateKey, String> predicateMap = auditService
				.createPredicateMap();
		predicateMap.put(INTERMEDIARY_SUBJECT_NPI,
				xacmlRequest.getIntermediarySubjectNPI());
		predicateMap.put(RECIPIENT_SUBJECT_NPI,
				xacmlRequest.getRecipientSubjectNPI());
		predicateMap.put(PATIENT_UNIQUE_ID, xacmlRequest.getPatientUniqueId());
		predicateMap.put(PURPOSE_OF_USE, xacmlRequest.getPurposeOfUse());
		try {
			predicateMap.put(REQUEST_BODY, marshaller.marshall(request));
		} catch (SimpleMarshallerException e) {
			logger.error(xacmlRequest.getMessageId(), e.getMessage());
			logger.error(xacmlRequest.getMessageId(),
					"SimpleMarshaller failed during auditing.");
			throw new AuditException(e.getMessage(), e);
		}
		auditService.audit(this, xacmlRequest.getMessageId(), requestType,
				xacmlRequest.getPatientId(), predicateMap);
	}
	
	/**
	 * Audit response.
	 *
	 * @param response the response
	 * @param xacmlRequest the xacml request
	 * @param pepResponseStatus the pep response status
	 * @throws SimpleMarshallerException the simple marshaller exception
	 * @throws AuditException the audit exception
	 */
	private void auditResponse(Object response,
			XacmlRequest xacmlRequest, PolicyEnforcementPointResponseStatus pepResponseStatus)
			throws SimpleMarshallerException, AuditException {
		AuditVerb auditVerb = resolveResponseAuditVerb(response, pepResponseStatus);
		Assert.notNull(auditVerb, "Cannot resolve AuditVerb; it cannot be null.");
		Map<PredicateKey, String> predicateMap = auditService.createPredicateMap();
		predicateMap.put(RESPONSE_BODY, marshaller.marshall(response));
		auditService.audit(this, xacmlRequest.getMessageId(), auditVerb, xacmlRequest.getPatientId(), predicateMap);
	}

	/**
	 * Parses the saml token.
	 * 
	 * @param samlTokenPrincipal
	 *            the saml token principal
	 * @param urn
	 *            the urn
	 * @param messageId
	 *            the message id
	 * @return the string
	 * @throws SamlTokenParserException
	 *             the saml token parser exception
	 */
	private String parseSamlToken(SAMLTokenPrincipal samlTokenPrincipal,
			String urn, String messageId) throws SamlTokenParserException {
		String tokenValue = null;
		try {
			tokenValue = samlTokenParser.parse(samlTokenPrincipal.getToken(),
					urn);
		} catch (Throwable t) {
			logger.error(messageId, t.getMessage());
			throw new SamlTokenParserException(t.getMessage(), t);
		}
		return tokenValue;
	}

	/**
	 * Validate format code.
	 * 
	 * @param formatCode
	 *            the format code
	 * @param messageId
	 *            the message id
	 * @return true, if successful
	 * @throws UnsupportedFormatCodeException
	 *             the unsupported format code exception
	 */
	private boolean validateFormatCode(String formatCode, String messageId)
			throws UnsupportedFormatCodeException {
		boolean valid = XdsbRegistryAdapter.FORMAT_CODE_CLINICAL_DOCUMENT
				.equals(formatCode);
		if (!valid) {
			StringBuilder builder = new StringBuilder();
			builder.append(formatCode);
			builder.append(" format code is not supported by Policy Enforcement Point.");
			builder.append(" The only supported format code is ");
			builder.append(XdsbRegistryAdapter.FORMAT_CODE_CLINICAL_DOCUMENT);
			builder.append(".");
			String errMsg = builder.toString();
			logger.debug(messageId, errMsg);
			throw new UnsupportedFormatCodeException(errMsg);
		}
		return valid;
	}

	/**
	 * Validate response option type.
	 * 
	 * @param req
	 *            the req
	 * @param messageId
	 *            the message id
	 * @return true, if successful
	 * @throws XdsbRegistryAdapterException
	 *             the xdsb registry adapter exception
	 * @throws UnsupportedResponseOptionTypeException
	 *             the unsupported response option type exception
	 */
	private boolean validateResponseOptionType(AdhocQueryRequest req,
			String messageId) throws XdsbRegistryAdapterException,
			UnsupportedResponseOptionTypeException {
		boolean valid;
		try {
			valid = SUPPORTED_ADHOCQUERY_RESPONSE_TYPE.equals(xdsbRegistry
					.extractResponseOptionReturnType(req));
		} catch (Throwable t) {
			logger.error(messageId, t.getMessage());
			String errMsg = "XdsbRegistryAdapter failed while extracting AdhocQuery ResponseOptionType.";
			logger.error(messageId, errMsg);
			throw new XdsbRegistryAdapterException(errMsg);
		}
		if (!valid) {
			StringBuilder builder = new StringBuilder();
			builder.append("Policy Enforcement Point only supports '");
			builder.append(SUPPORTED_ADHOCQUERY_RESPONSE_TYPE);
			builder.append("' response option return type.");
			String errMsg = builder.toString();
			logger.debug(messageId, errMsg);
			throw new UnsupportedResponseOptionTypeException(errMsg);
		}
		return valid;
	}

	/**
	 * Validate patient unique id consistency.
	 * 
	 * @param patientUniqueIdFromRequest
	 *            the patient unique id from request
	 * @param patientUniqueIdFromSaml
	 *            the patient unique id from saml
	 * @param messageId
	 *            the message id
	 * @return true, if successful
	 * @throws InconsistentPatientUniqueIdException
	 *             the inconsistent patient unique id exception
	 */
	private boolean validatePatientUniqueIdConsistency(
			String patientUniqueIdFromRequest, String patientUniqueIdFromSaml,
			String messageId) throws InconsistentPatientUniqueIdException {
		boolean valid = patientUniqueIdFromRequest
				.equals(patientUniqueIdFromSaml);
		if (!valid) {
			StringBuilder builder = new StringBuilder();
			builder.append("The document entry patient id in $XDSDocumentEntryPatientId (");
			builder.append(patientUniqueIdFromRequest);
			builder.append(") does not match the patient unique id (");
			builder.append(patientUniqueIdFromSaml);
			builder.append(") generated from SAML header.");
			String errMsg = builder.toString();
			logger.debug(messageId, errMsg);
			throw new InconsistentPatientUniqueIdException(errMsg);
		}
		return valid;
	}

	/**
	 * Validate response.
	 * 
	 * @param response
	 *            the response
	 * @param patientUniqueId
	 *            the patient unique id
	 * @param intermediarySubject
	 *            the intermediary subject
	 * @param messageId
	 *            the message id
	 * @return true, if successful
	 * @throws NoDocumentFoundException
	 *             the no document found exception
	 */
	private boolean validateResponse(AdhocQueryResponse response,
			String patientUniqueId, String intermediarySubject, String messageId)
			throws NoDocumentFoundException {
		boolean emptyResponse = (response.getRegistryObjectList()
				.getIdentifiable() == null || response.getRegistryObjectList()
				.getIdentifiable().size() == 0)
				&& (response.getResponseSlotList() == null || response
						.getResponseSlotList().getSlot().size() == 0)
				&& (response.getRegistryErrorList() == null || response
						.getRegistryErrorList().getRegistryError().size() == 0);
		if (emptyResponse) {
			StringBuilder builder = new StringBuilder();
			builder.append("No documents found for patient ");
			builder.append(patientUniqueId);
			builder.append(" authored by ");
			builder.append(intermediarySubject);
			builder.append(".");
			String errMsg = builder.toString();
			logger.error(messageId, errMsg);
			throw new NoDocumentFoundException(errMsg);
		}
		return !emptyResponse;
	}

	/**
	 * Gets the user principal.
	 * 
	 * @param messageId
	 *            the message id
	 * @return the user principal
	 * @throws SamlTokenPrincipalException
	 *             the saml token principal exception
	 */
	private SAMLTokenPrincipal getUserPrincipal(String messageId)
			throws SamlTokenPrincipalException {
		try {
			SAMLTokenPrincipal samlTokenPrincipal = (SAMLTokenPrincipal) (context
					.getUserPrincipal());
			return samlTokenPrincipal;
		} catch (Throwable t) {
			logger.error(messageId, t.getMessage());
			throw new SamlTokenPrincipalException(t);
		}
	}

	/**
	 * Validate retrieve document set request.
	 * 
	 * @param input
	 *            the input
	 * @param messageId
	 *            the message id
	 * @return true, if successful
	 * @throws MultipleRepositoryIdException
	 *             the multiple repository id exception
	 */
	private boolean validateRetrieveDocumentSetRequest(
			RetrieveDocumentSetRequest input, String messageId)
			throws MultipleRepositoryIdException {
		String firstRepositoryId = null;
		Assert.notEmpty(input.getDocumentRequest());
		for (DocumentRequest docReq : input.getDocumentRequest()) {
			// Capture the first document request's repository id
			if (firstRepositoryId == null) {
				firstRepositoryId = docReq.getRepositoryUniqueId();
			}
			// Check if rest of the repository ids are same
			if (!firstRepositoryId.equals(docReq.getRepositoryUniqueId())) {
				String errMsg = "All repository ids in RetrieveDocumentSetRequest need to be same.";
				logger.debug(messageId, errMsg);
				throw new MultipleRepositoryIdException(errMsg);
			}
		}
		return true;
	}

	/**
	 * Validate retrieve document set response.
	 * 
	 * @param response
	 *            the response
	 * @param messageId
	 *            the message id
	 * @return true, if successful
	 * @throws NoDocumentFoundException
	 *             the no document found exception
	 */
	private boolean validateRetrieveDocumentSetResponse(
			RetrieveDocumentSetResponse response, final String messageId)
			throws NoDocumentFoundException {
		if (response.getDocumentResponse() == null
				|| response.getDocumentResponse().size() == 0) {

			if (response.getRegistryResponse().getRegistryErrorList()
					.getRegistryError().size() > 0) {
				String errMsg = "No document found in XDS.b repository. XDS.b returns an error response.";
				logger.debug(messageId, errMsg);
				throw new NoDocumentFoundException(errMsg);
			}
		}
		return true;
	}

	/**
	 * Handle pdp deny.
	 * 
	 * @param messageId
	 *            the message id
	 * @param xacmlRequest
	 *            the xacml request
	 * @param xacmlResponse
	 *            the xacml response
	 * @throws NoPolicyFoundException
	 *             the no policy found exception
	 */
	private void handlePDPDeny(final String messageId,
			XacmlRequest xacmlRequest, XacmlResponse xacmlResponse)
			throws NoPolicyFoundException {
		// Log PDP decision
		StringBuilder logMsg = new StringBuilder();
		logMsg.append("PDP did not return ");
		logMsg.append(PERMIT);
		logMsg.append(". PDP decision:");
		logMsg.append(xacmlResponse.getPdpDecision());
		logMsg.append(".");
		logger.info(messageId, logMsg.toString());

		// Throw NoPolicyFoundException.
		StringBuilder noConsentsFoundErrorStringBuilder = new StringBuilder();
		noConsentsFoundErrorStringBuilder.append("No consents found for patient ");
		noConsentsFoundErrorStringBuilder.append(xacmlRequest.getPatientUniqueId());
		noConsentsFoundErrorStringBuilder.append(".");
		String errMsg = noConsentsFoundErrorStringBuilder.toString();
		logger.error(messageId, errMsg);
		throw new NoPolicyFoundException(errMsg);
	}
	
	/**
	 * Resolve response audit verb.
	 *
	 * @param response the response
	 * @param pepResponseStatus the pep response status
	 * @return the audit verb
	 */
	private AuditVerb resolveResponseAuditVerb(Object response,
			PolicyEnforcementPointResponseStatus pepResponseStatus) {
		AuditVerb auditVerb = null;
		if(response instanceof AdhocQueryResponse){
			switch(pepResponseStatus){
			case ERROR_CONSENT:
				auditVerb = REGISTRY_STORED_QUERY_RESPONSE_ERROR_CONSENT;
				break;
			case ERROR_SYSTEM:
				auditVerb = REGISTRY_STORED_QUERY_RESPONSE_ERROR_SYSTEM;
				break;
			case SUCCESS:
				auditVerb = REGISTRY_STORED_QUERY_RESPONSE;
				break;
			default:
				auditVerb = null;
				break;			
			}
		} else if(response instanceof RetrieveDocumentSetResponse){
			switch(pepResponseStatus){
			case ERROR_CONSENT:
				auditVerb = RETRIEVE_DOCUMENT_SET_RESPONSE_ERROR_CONSENT;
				break;
			case ERROR_SYSTEM:
				auditVerb = RETRIEVE_DOCUMENT_SET_RESPONSE_ERROR_SYSTEM;
				break;
			case SUCCESS:
				auditVerb = RETRIEVE_DOCUMENT_SET_RESPONSE;
				break;
			default:
				auditVerb = null;
				break;			
			}
		} else if(response instanceof DirectEmailSendResponse){
			switch(pepResponseStatus){
			case ERROR_CONSENT:
				auditVerb = DIRECT_EMAIL_SEND_RESPONSE_ERROR_CONSENT;
				break;
			case ERROR_SYSTEM:
				auditVerb = DIRECT_EMAIL_SEND_RESPONSE_ERROR_SYSTEM;
				break;
			case SUCCESS:
				auditVerb = DIRECT_EMAIL_SEND_RESPONSE;
				break;
			default:
				auditVerb = null;
				break;			
			}
		} else{
			auditVerb = null;
		}

		return auditVerb;
	}
}
