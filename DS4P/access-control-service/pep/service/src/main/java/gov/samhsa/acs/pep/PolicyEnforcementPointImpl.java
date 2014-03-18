package gov.samhsa.acs.pep;

import gov.samhsa.acs.brms.domain.SubjectPurposeOfUse;
import gov.samhsa.acs.brms.domain.XacmlResult;
import gov.samhsa.acs.common.dto.PdpRequestResponse;
import gov.samhsa.acs.common.dto.XacmlRequest;
import gov.samhsa.acs.common.dto.XacmlResponse;
import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.contexthandler.ContextHandler;
import gov.samhsa.acs.contexthandler.exception.NoPolicyFoundException;
import gov.samhsa.acs.documentsegmentation.DocumentSegmentation;
import gov.samhsa.acs.pep.saml.SamlTokenParser;
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

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.ws.WebServiceContext;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.apache.ws.security.SAMLTokenPrincipal;
import org.herasaf.xacml.core.api.PDP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class PolicyEnforcementPointImpl.
 */
public class PolicyEnforcementPointImpl implements PolicyEnforcementPoint,
		PolicyTrying {
	// Constants
	/** The Constant PERMIT. */
	private static final String PERMIT = "PERMIT";

	/** The Constant SUPPORTED_ADHOCQUERY_RESPONSE_TYPE. */
	private static final String SUPPORTED_ADHOCQUERY_RESPONSE_TYPE = "LeafClass";

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

	@Resource
	private WebServiceContext context;

	private SamlTokenParser samlTokenParser;

	private SAMLTokenPrincipal samlTokenPrincipal;

	/** The data handler to bytes converter. */
	private DataHandlerToBytesConverter dataHandlerToBytesConverter;

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
	 */
	public PolicyEnforcementPointImpl(XdsbRegistryAdapter xdsbRegistry,
			XdsbRepositoryAdapter xdsbRepository,
			XdsbErrorFactory xdsbErrorFactory, ContextHandler contextHandler,
			DocumentSegmentation documentSegmentation,
			SimpleMarshaller marshaller, WebServiceContext context,
			SamlTokenParser parser,
			DataHandlerToBytesConverter dataHandlerToBytesConverter) {
		super();
		this.xdsbRegistry = xdsbRegistry;
		this.xdsbRepository = xdsbRepository;
		this.xdsbErrorFactory = xdsbErrorFactory;
		this.contextHandler = contextHandler;
		this.documentSegmentation = documentSegmentation;
		this.marshaller = marshaller;
		this.context = context;
		this.samlTokenParser = parser;
		this.dataHandlerToBytesConverter = dataHandlerToBytesConverter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.pep.PolicyEnforcementPoint#registryStoredQuery(oasis
	 * .names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest)
	 */
	@Override
	public AdhocQueryResponse registryStoredQuery(AdhocQueryRequest req) {
		if (samlTokenParser == null) {
			samlTokenParser = new SamlTokenParser();
		}
		samlTokenPrincipal = (SAMLTokenPrincipal) (context.getUserPrincipal());

		String resourceId = samlTokenParser.parse(
				samlTokenPrincipal.getToken(),
				"urn:oasis:names:tc:xacml:1.0:resource:resource-id");
		String intermediarySubject = samlTokenParser
				.parse(samlTokenPrincipal.getToken(),
						"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject");
		String purposeOfUse = samlTokenParser.parse(
				samlTokenPrincipal.getToken(),
				"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse");
		String recipientSubject = samlTokenParser
				.parse(samlTokenPrincipal.getToken(),
						"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject");
		XacmlRequest xacmlRequest = setXacmlRequest(resourceId, purposeOfUse,
				intermediarySubject, recipientSubject);

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
		String patientUniqueId = xdsbRegistry.extractPatientId(req);
		if (!patientUniqueId.equals(xacmlRequest.getPatientUniqueId())) {
			return xdsbErrorFactory
					.errorAdhocQueryResponseInconsistentPatientUniqueId(
							patientUniqueId,
							xdsbRegistry.getPatientUniqueId(
									xacmlRequest.getPatientId(),
									xacmlRequest.getHomeCommunityId()));
		}
		XacmlResponse xacmlResponse = null;
		try {
			xacmlResponse = contextHandler.enforcePolicy(xacmlRequest);
		} catch (NoPolicyFoundException e) {
			logger.error(e.getMessage(), e);
			return xdsbErrorFactory
					.errorAdhocQueryResponseNoConsentsFound(patientUniqueId);
		}
		// If PDP returns PERMIT
		if (PERMIT.equals(xacmlResponse.getPdpDecision())) {
			// Search for clinical documents of the patient
			AdhocQueryResponse response;
			try {
				response = xdsbRegistry.registryStoredQuery(req,
						intermediarySubject);
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
								patientUniqueId, intermediarySubject);
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
	 * @see gov.samhsa.acs.pep.PolicyEnforcementPoint#retrieveDocumentSet(ihe
	 * .iti.xds_b._2007.RetrieveDocumentSetRequest)
	 */
	@Override
	public RetrieveDocumentSetResponse retrieveDocumentSet(
			RetrieveDocumentSetRequest input) {

		if (samlTokenParser == null) {
			samlTokenParser = new SamlTokenParser();
		}
		samlTokenPrincipal = (SAMLTokenPrincipal) (context.getUserPrincipal());

		String resourceId = samlTokenParser.parse(
				samlTokenPrincipal.getToken(),
				"urn:oasis:names:tc:xacml:1.0:resource:resource-id");
		String intermediarySubject = samlTokenParser
				.parse(samlTokenPrincipal.getToken(),
						"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject");
		String purposeOfUse = samlTokenParser.parse(
				samlTokenPrincipal.getToken(),
				"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse");
		String recipientSubject = samlTokenParser
				.parse(samlTokenPrincipal.getToken(),
						"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject");
		XacmlRequest xacmlRequest = setXacmlRequest(resourceId, purposeOfUse,
				intermediarySubject, recipientSubject);

		if (!validateRetrieveDocumentSetRequest(input)) {
			return xdsbErrorFactory
					.errorRetrieveDocumentSetResponseMultipleRepositoryId();
		}

		XacmlResponse xacmlResponse = null;
		try {
			xacmlResponse = contextHandler.enforcePolicy(xacmlRequest);
		} catch (NoPolicyFoundException e) {
			logger.error(e.getMessage(), e);
			return xdsbErrorFactory
					.errorRetrieveDocumentSetResponseNoConsentsFound(resourceId);
		}
		if (PERMIT.equals(xacmlResponse.getPdpDecision())) {
			RetrieveDocumentSetResponse response;
			try {
				response = xdsbRepository.retrieveDocumentSet(input,
						xacmlRequest.getPatientId(),
						xacmlRequest.getIntermediarySubjectNPI());
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
			XacmlResult xacmlResult = createXacmlResult(xacmlRequest,
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
	 * @see gov.samhsa.acs.pep.PolicyEnforcementPoint#directEmailSend(gov.samhsa
	 * .ds4ppilot.schema.pep.DirectEmailSendRequest)
	 */
	@Override
	public DirectEmailSendResponse directEmailSend(DirectEmailSendRequest input) {
		DirectEmailSendResponse response = new DirectEmailSendResponse();

		if (samlTokenParser == null) {
			samlTokenParser = new SamlTokenParser();
		}
		samlTokenPrincipal = (SAMLTokenPrincipal) (context.getUserPrincipal());

		String resourceId = samlTokenParser.parse(
				samlTokenPrincipal.getToken(),
				"urn:oasis:names:tc:xacml:1.0:resource:resource-id");
		String intermediarySubject = samlTokenParser
				.parse(samlTokenPrincipal.getToken(),
						"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject");
		String purposeOfUse = samlTokenParser.parse(
				samlTokenPrincipal.getToken(),
				"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse");
		String recipientSubject = samlTokenParser
				.parse(samlTokenPrincipal.getToken(),
						"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject");
		XacmlRequest xacmlRequest = setXacmlRequest(resourceId, purposeOfUse,
				intermediarySubject, recipientSubject);

		// if (!validateRetrieveDocumentSetRequest(input)) {
		// return xdsbErrorFactory
		// .errorRetrieveDocumentSetResponseMultipleRepositoryId();
		// }

		XacmlResponse xacmlResponse = null;
		try {
			xacmlResponse = contextHandler.enforcePolicy(xacmlRequest);
		} catch (NoPolicyFoundException e) {
			logger.error(e.getMessage(), e);
			// return
			// xdsbErrorFactory.errorRetrieveDocumentSetResponseNoConsentsFound(resourceId);
			response.setPdpDecision("NO_POLICY");
			return response;
		}

		if (PERMIT.equals(xacmlResponse.getPdpDecision())) {
			response.setPdpDecision(PERMIT);
			String originalC32 = input.getC32();

			byte[] processedPayload;

			try {
				XacmlResult xacmlResult = createXacmlResult(xacmlRequest,
						xacmlResponse);
				// JAXBContext jaxbContext = JAXBContext
				// .newInstance(XacmlResult.class);
				// Marshaller marshaller = jaxbContext.createMarshaller();
				// marshaller.setProperty("com.sun.xml.bind.xmlDeclaration",
				// Boolean.FALSE);
				// marshaller.marshal(xacmlResult, xacmlResponseXml);
				String xacmlResultString = marshaller.marshall(xacmlResult);

				String xdsDocumentEntryUniqueId = "";
				// packageAsXdm = input.getPackageAsXdm()
				boolean packageAsXdm = true;
				SegmentDocumentResponse segmentDocumentResponse = documentSegmentation
						.segmentDocument(originalC32, xacmlResultString,
								packageAsXdm, true, input.getSenderEmail(),
								input.getRecipientEmail(),
								xdsDocumentEntryUniqueId);

				processedPayload = dataHandlerToBytesConverter
						.toByteArray(segmentDocumentResponse
								.getProcessedDocument());

				response.setMaskedDocument(segmentDocumentResponse
						.getMaskedDocument());
				response.setFilteredStreamBody(processedPayload);
			} catch (PropertyException e) {
				throw new DS4PException(e.toString(), e);
			} catch (JAXBException e) {
				throw new DS4PException(e.toString(), e);
			} catch (IOException e) {
				throw new DS4PException(e.toString(), e);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		} else {
			response.setPdpDecision("DENY");
		}
		return response;
	}

	@Override
	public String tryPolicy(String c32Xml, String xacmlPolicy, String purposeOfUse) {
		PdpRequestResponse pdpRequestResponse = contextHandler
				.makeDecisionForTryingPolicy(xacmlPolicy, purposeOfUse);
		XacmlResponse xacmlResponse = pdpRequestResponse.getXacmlResponse();
		
		XacmlRequest xacmlRequest = pdpRequestResponse.getXacmlRequest();

		XacmlResult xacmlResult = createXacmlResult(xacmlRequest, xacmlResponse);
		

		String enforcementPolicies = null;

		try {
			enforcementPolicies = marshaller.marshall(xacmlResult);
		} catch (Throwable e) {
			logger.error("Error in tryPolicy", e);
		}

		boolean packageAsXdm = false;
		boolean encryptDocument = false;
		String senderEmailAddress = "";
		String recipientEmailAddress = "";
		String xdsDocumentEntryUniqueId = "UniquId";
		SegmentDocumentResponse segmentDocumentResponse = documentSegmentation
				.segmentDocument(c32Xml, enforcementPolicies, packageAsXdm,
						encryptDocument, senderEmailAddress,
						recipientEmailAddress, xdsDocumentEntryUniqueId);
		String segmentedC32 = segmentDocumentResponse.getMaskedDocument();

		return segmentedC32;
	}

	/**
	 * Sets the xacml request.
	 * 
	 * @return the xacml request
	 */
	public XacmlRequest setXacmlRequest(String resourceId, String purposeOfUse,
			String intermediarySubject, String recipientSubject) {

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
		xacmlRequest.setMessageId(UUID.randomUUID().toString());
		xacmlRequest.setPurposeOfUse(purposeOfUse);
		xacmlRequest.setRecepientSubjectNPI(recipientSubject);
		xacmlRequest.setPatientId(patientId);

		xacmlRequest.setPatientUniqueId("'" + resourceId + "'");
		return xacmlRequest;
	}

	/**
	 * Validate adhoc query request.
	 * 
	 * @param req
	 *            the req
	 * @return true, if successful
	 */
	boolean validateAdhocQueryRequest(AdhocQueryRequest req) {
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
		xacmlResult.setSubjectPurposeOfUse(SubjectPurposeOfUse.fromAbbreviation(xacmlRequest.getPurposeOfUse()));
		return xacmlResult;
	}
}
