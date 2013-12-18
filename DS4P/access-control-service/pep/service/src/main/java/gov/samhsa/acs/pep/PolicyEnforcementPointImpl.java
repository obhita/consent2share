package gov.samhsa.acs.pep;

import gov.samhsa.acs.common.bean.XacmlResult;
import gov.samhsa.acs.common.dto.XacmlRequest;
import gov.samhsa.acs.common.dto.XacmlResponse;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.contexthandler.ContextHandler;
import gov.samhsa.acs.documentsegmentation.DocumentSegmentation;
import gov.samhsa.acs.xdsb.common.XdsbErrorFactory;
import gov.samhsa.acs.xdsb.registry.wsclient.adapter.XdsbRegistryAdapter;
import gov.samhsa.acs.xdsb.repository.wsclient.adapter.XdsbRepositoryAdapter;
import gov.samhsa.consent2share.schema.documentsegmentation.SegmentDocumentResponse;
import gov.samhsa.ds4ppilot.schema.pep.DirectEmailSendRequest;
import gov.samhsa.ds4ppilot.schema.pep.DirectEmailSendResponse;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest.DocumentRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse.DocumentResponse;

import java.util.UUID;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class PolicyEnforcementPointImpl.
 */
public class PolicyEnforcementPointImpl implements PolicyEnforcementPoint {
	// Constants
	/** The Constant PERMIT. */
	private static final String PERMIT = "PERMIT";

	/** The Constant SUPPORTED_ADHOCQUERY_RESPONSE_TYPE. */
	private static final String SUPPORTED_ADHOCQUERY_RESPONSE_TYPE = "LeafClass";

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// Parameters
	// --Request
	/** The patient unique id. */
	private String patientUniqueId;
	// --SAML Header
	/** The patient id. */
	private String patientId;

	/** The home community id. */
	private String homeCommunityId;

	/** The subject purpose of use. */
	private String subjectPurposeOfUse;

	/** The recepient subject npi. */
	private String recepientSubjectNPI;

	/** The intermediary subject npi. */
	private String intermediarySubjectNPI;

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
	 */
	public PolicyEnforcementPointImpl(XdsbRegistryAdapter xdsbRegistry,
			XdsbRepositoryAdapter xdsbRepository,
			XdsbErrorFactory xdsbErrorFactory, ContextHandler contextHandler,
			DocumentSegmentation documentSegmentation,
			SimpleMarshaller marshaller) {
		super();
		this.xdsbRegistry = xdsbRegistry;
		this.xdsbRepository = xdsbRepository;
		this.xdsbErrorFactory = xdsbErrorFactory;
		this.contextHandler = contextHandler;
		this.documentSegmentation = documentSegmentation;
		this.marshaller = marshaller;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.pep.PolicyEnforcementPoint#registryStoredQuery(oasis
	 * .names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest)
	 */
	@Override
	public AdhocQueryResponse registryStoredQuery(AdhocQueryRequest req) {
		// Validate input parameters
		if (!validateAdhocQueryRequest(req)) {
			return xdsbErrorFactory.errorAdhocQueryResponseMissingParameters();
		}
		// Validate format code (only '2.16.840.1.113883.10.20.1^^HITSP' is
		// supported)
		String formatCode = xdsbRegistry.extractFormatCode(req);
		if (!XdsbRegistryAdapter.FORMAT_CODE_CLINICAL_DOCUMENT
				.equals(formatCode)) {
			return xdsbErrorFactory
					.errorAdhocQueryResponseUnsupportedFormatCode(formatCode);
		}

		// Validate response option type (only 'LeafClass' is supported, because
		// PEP required metadata for filtering query response)
		if (!SUPPORTED_ADHOCQUERY_RESPONSE_TYPE.equals(xdsbRegistry
				.extractResponseOptionReturnType(req))) {
			return xdsbErrorFactory
					.errorAdhocQueryResponseUnsupportedResponseOptionType(SUPPORTED_ADHOCQUERY_RESPONSE_TYPE);
		}

		// The patient id in $XDSDocumentEntryPatientId
		this.patientUniqueId = xdsbRegistry.extractPatientId(req);
		if (!patientUniqueId.equals(xdsbRegistry.getPatientUniqueId(patientId,
				homeCommunityId))) {
			return xdsbErrorFactory
					.errorAdhocQueryResponseInconsistentPatientUniqueId(
							patientUniqueId, xdsbRegistry.getPatientUniqueId(
									patientId, homeCommunityId));
		}
		XacmlRequest xacmlRequest = setXacmlRequest();
		XacmlResponse xacmlResponse = contextHandler
				.enforcePolicy(xacmlRequest);
		// If PDP returns PERMIT
		if (PERMIT.equals(xacmlResponse.getPdpDecision())) {
			// Search for clinical documents of the patient
			AdhocQueryResponse response;
			try {
				response = xdsbRegistry.registryStoredQuery(req,
						intermediarySubjectNPI);
			} catch (Throwable e) {
				logger.error(e.getMessage(), e);
				return xdsbErrorFactory
						.errorAdhocQueryResponseRegistryNotAvailable();
			}
			// If the response returns empty, it means either there are no
			// documents found or they are filtered out by the authorId
			// (intermediaryNPI)
			if ((response.getRegistryObjectList().getIdentifiable() == null || response
					.getRegistryObjectList().getIdentifiable().size() == 0)
					&& (response.getResponseSlotList() == null || response
							.getResponseSlotList().getSlot().size() == 0)
					&& (response.getRegistryErrorList() == null || response
							.getRegistryErrorList().getRegistryError().size() == 0)) {
				// Return no documents found error message
				return xdsbErrorFactory
						.errorAdhocQueryResponseNoDocumentsFound(
								patientUniqueId, intermediarySubjectNPI);
			}
			// Return successfully retrieved and filtered response
			return response;
			// If PDP returns DENY or NOT_APPLICABLE
		} else {
			// Return access denied by PDP error
			return xdsbErrorFactory.errorAdhocQueryResponseAccessDeniedByPDP();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.pep.PolicyEnforcementPoint#retrieveDocumentSet(ihe
	 * .iti.xds_b._2007.RetrieveDocumentSetRequest)
	 */
	@Override
	public RetrieveDocumentSetResponse retrieveDocumentSet(
			RetrieveDocumentSetRequest input) {
		if (!validateRetrieveDocumentSetRequest(input)) {
			return xdsbErrorFactory
					.errorRetrieveDocumentSetResponseMultipleRepositoryId();
		}
		this.patientUniqueId = xdsbRegistry.getPatientUniqueId(patientId,
				homeCommunityId);
		XacmlRequest xacmlRequest = setXacmlRequest();
		XacmlResponse xacmlResponse = contextHandler
				.enforcePolicy(xacmlRequest);
		if (PERMIT.equals(xacmlResponse.getPdpDecision())) {
			RetrieveDocumentSetResponse response;
			try {
				response = xdsbRepository.retrieveDocumentSet(input, patientId,
						intermediarySubjectNPI);
			} catch (Throwable e) {
				logger.error(e.getMessage(), e);
				return xdsbErrorFactory
						.errorRetrieveDocumentSetResponseRepositoryNotAvailable();
			}

			if (response.getDocumentResponse() == null
					|| response.getDocumentResponse().size() == 0) {
				return xdsbErrorFactory
						.errorRetrieveDocumentSetResponseNotExistsOrAccessible(input);
			}
			// Start segmentation
			// return response;
			XacmlResult xacmlResult = getXacmlResult(xacmlRequest,
					xacmlResponse);
			for (DocumentResponse documentResponse : response
					.getDocumentResponse()) {
				String document = new String(documentResponse.getDocument());
				String enforcementPolicies;
				try {
					enforcementPolicies = marshaller.marshall(xacmlResult);
				} catch (Throwable e) {
					logger.error(e.getMessage(), e);
					return xdsbErrorFactory
							.errorRetrieveDocumentSetResponseInternalPEPError();
				}
				boolean packageAsXdm = false;
				boolean encryptDocument = false;
				String senderEmailAddress = "";
				String recipientEmailAddress = "";
				String xdsDocumentEntryUniqueId = documentResponse
						.getDocumentUniqueId();
				SegmentDocumentResponse segmentDocumentResponse = documentSegmentation
						.segmentDocument(document, enforcementPolicies,
								packageAsXdm, encryptDocument,
								senderEmailAddress, recipientEmailAddress,
								xdsDocumentEntryUniqueId);
				documentResponse.setDocument(segmentDocumentResponse
						.getMaskedDocument().getBytes());
			}
			return response;
		} else {
			return xdsbErrorFactory
					.errorRetrieveDocumentSetResponseAccessDeniedByPDP();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.pep.PolicyEnforcementPoint#directEmailSend(gov.samhsa
	 * .ds4ppilot.schema.pep.DirectEmailSendRequest)
	 */
	@Override
	public DirectEmailSendResponse directEmailSend(
			DirectEmailSendRequest parameters) {
		// TODO: implement directEmailSend operation
		return new DirectEmailSendResponse();
	}

	/**
	 * Gets the patient id.
	 * 
	 * @return the patient id
	 */
	public String getPatientId() {
		return patientId;
	}

	/**
	 * Sets the patient id.
	 * 
	 * @param patientId
	 *            the new patient id
	 */
	public void setPatientId(String patientId) {
		this.patientId = patientId;
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
	 * Gets the recepient subject npi.
	 * 
	 * @return the recepient subject npi
	 */
	public String getRecepientSubjectNPI() {
		return recepientSubjectNPI;
	}

	/**
	 * Sets the recepient subject npi.
	 * 
	 * @param recepientSubjectNPI
	 *            the new recepient subject npi
	 */
	public void setRecepientSubjectNPI(String recepientSubjectNPI) {
		this.recepientSubjectNPI = recepientSubjectNPI;
	}

	/**
	 * Gets the intermediary subject npi.
	 * 
	 * @return the intermediary subject npi
	 */
	public String getIntermediarySubjectNPI() {
		return intermediarySubjectNPI;
	}

	/**
	 * Sets the intermediary subject npi.
	 * 
	 * @param intermediarySubjectNPI
	 *            the new intermediary subject npi
	 */
	public void setIntermediarySubjectNPI(String intermediarySubjectNPI) {
		this.intermediarySubjectNPI = intermediarySubjectNPI;
	}

	/**
	 * Sets the xacml request.
	 * 
	 * @return the xacml request
	 */
	private XacmlRequest setXacmlRequest() {
		XacmlRequest xacmlRequest = new XacmlRequest();
		xacmlRequest.setHomeCommunityId(homeCommunityId);
		xacmlRequest.setIntermediarySubjectNPI(intermediarySubjectNPI);
		xacmlRequest.setMessageId(UUID.randomUUID().toString());
		xacmlRequest.setPurposeOfUse(subjectPurposeOfUse);
		xacmlRequest.setRecepientSubjectNPI(recepientSubjectNPI);
		xacmlRequest.setPatientId((patientUniqueId.substring(0,
				patientUniqueId.indexOf('^'))).replace("'", ""));
		xacmlRequest.setPatientUniqueId(patientUniqueId);
		return xacmlRequest;
	}

	/**
	 * Validate adhoc query request.
	 * 
	 * @param req
	 *            the req
	 * @return true, if successful
	 */
	private boolean validateAdhocQueryRequest(AdhocQueryRequest req) {
		String formatCode = xdsbRegistry.extractFormatCode(req);
		String pid = xdsbRegistry.extractPatientId(req);
		String status = xdsbRegistry.extractDocumentEntryStatus(req);
		return isNotNull(formatCode) && isNotNull(pid) && isNotNull(status);
	}

	/**
	 * Validate retrieve document set request.
	 * 
	 * @param input
	 *            the input
	 * @return true, if successful
	 */
	private boolean validateRetrieveDocumentSetRequest(
			RetrieveDocumentSetRequest input) {
		String firstRepositoryId = null;
		for (DocumentRequest docReq : input.getDocumentRequest()) {
			// Capture the first document request's repository id
			if (firstRepositoryId == null) {
				firstRepositoryId = docReq.getRepositoryUniqueId();
			}
			// Check if rest of the repository ids are same
			if (!firstRepositoryId.equals(docReq.getRepositoryUniqueId())) {
				return false;
			}
		}
		return true;
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
	 * Gets the xacml result.
	 * 
	 * @param xacmlRequest
	 *            the xacml request
	 * @param xacmlResponse
	 *            the xacml response
	 * @return the xacml result
	 */
	private XacmlResult getXacmlResult(XacmlRequest xacmlRequest,
			XacmlResponse xacmlResponse) {
		XacmlResult xacmlResult = new XacmlResult();
		xacmlResult.setHomeCommunityId(xacmlRequest.getHomeCommunityId());
		xacmlResult.setMessageId(xacmlRequest.getMessageId());
		xacmlResult.setPdpDecision(xacmlResponse.getPdpDecision());
		xacmlResult.setPdpObligations(xacmlResponse.getPdpObligation());
		xacmlResult.setSubjectPurposeOfUse(xacmlRequest.getPurposeOfUse());
		return xacmlResult;
	}
}
