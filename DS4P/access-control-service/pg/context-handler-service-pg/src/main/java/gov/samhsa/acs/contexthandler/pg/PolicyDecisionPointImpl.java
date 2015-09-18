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
package gov.samhsa.acs.contexthandler.pg;

import static gov.samhsa.acs.audit.AcsAuditVerb.DEPLOY_POLICY;
import static gov.samhsa.acs.audit.AcsPredicateKey.XACML_POLICY;
import static gov.samhsa.acs.audit.AcsPredicateKey.XACML_POLICY_ID;
import gov.samhsa.acs.audit.AuditService;
import gov.samhsa.acs.audit.PredicateKey;
import gov.samhsa.acs.common.dto.PdpRequestResponse;
import gov.samhsa.acs.common.dto.XacmlRequest;
import gov.samhsa.acs.common.dto.XacmlResponse;
import gov.samhsa.acs.common.tool.DocumentAccessor;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;
import gov.samhsa.acs.common.tool.exception.DocumentAccessorException;
import gov.samhsa.acs.common.tool.exception.DocumentXmlConverterException;
import gov.samhsa.acs.contexthandler.PolicyDecisionPoint;
import gov.samhsa.acs.contexthandler.PolicyProvider;
import gov.samhsa.acs.contexthandler.exception.NoPolicyFoundException;
import gov.samhsa.acs.contexthandler.exception.PolicyProviderException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.herasaf.xacml.core.WritingException;
import org.herasaf.xacml.core.api.PDP;
import org.herasaf.xacml.core.api.PolicyRepository;
import org.herasaf.xacml.core.api.PolicyRetrievalPoint;
import org.herasaf.xacml.core.api.UnorderedPolicyRepository;
import org.herasaf.xacml.core.context.impl.RequestType;
import org.herasaf.xacml.core.context.impl.ResponseType;
import org.herasaf.xacml.core.context.impl.ResultType;
import org.herasaf.xacml.core.policy.Evaluatable;
import org.herasaf.xacml.core.policy.EvaluatableID;
import org.herasaf.xacml.core.policy.PolicyMarshaller;
import org.herasaf.xacml.core.policy.impl.AttributeAssignmentType;
import org.herasaf.xacml.core.policy.impl.ObligationType;
import org.herasaf.xacml.core.simplePDP.SimplePDPFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import ch.qos.logback.audit.AuditException;

/**
 * The Class PolicyDecisionPointImpl.
 */
public class PolicyDecisionPointImpl implements PolicyDecisionPoint {

	/** The logger. */
	private final Logger logger = LoggerFactory
			.getLogger(PolicyDecisionPointImpl.class);

	/** The policy provider. */
	private final PolicyProvider policyProvider;

	/** The request generator. */
	private final RequestGenerator requestGenerator;

	/** The document accessor. */
	private final DocumentAccessor documentAccessor;

	/** The document xml converter. */
	private final DocumentXmlConverter documentXmlConverter;

	/** The audit service. */
	private final AuditService auditService;

	/**
	 * Instantiates a new policy decision point impl.
	 *
	 * @param policyProvider
	 *            the policy provider
	 * @param requestGenerator
	 *            the request generator
	 * @param documentAccessor
	 *            the document accessor
	 * @param documentXmlConverter
	 *            the document xml converter
	 * @param auditService
	 *            the audit service
	 */
	public PolicyDecisionPointImpl(PolicyProvider policyProvider,
			RequestGenerator requestGenerator,
			DocumentAccessor documentAccessor,
			DocumentXmlConverter documentXmlConverter, AuditService auditService) {
		super();
		this.policyProvider = policyProvider;
		this.requestGenerator = requestGenerator;
		this.documentAccessor = documentAccessor;
		this.documentXmlConverter = documentXmlConverter;
		this.auditService = auditService;
		// to initialize herasaf context
		getSimplePDP();
	}

	/**
	 * Instantiates a new policy decision point impl.
	 *
	 * @param requestGenerator
	 *            the request generator
	 * @param documentAccessor
	 *            the document accessor
	 * @param documentXmlConverter
	 *            the document xml converter
	 */
	public PolicyDecisionPointImpl(RequestGenerator requestGenerator,
			DocumentAccessor documentAccessor,
			DocumentXmlConverter documentXmlConverter) {
		super();
		this.policyProvider = null;
		this.requestGenerator = requestGenerator;
		this.documentAccessor = documentAccessor;
		this.documentXmlConverter = documentXmlConverter;
		this.auditService = null;
		// to initialize herasaf context
		getSimplePDP();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.contexthandler.PolicyDecisionPoint#evaluatePolicyForTrying
	 * (java.lang.String)
	 */
	@Override
	public synchronized PdpRequestResponse evaluatePolicyForTrying(
			String xacmlPolicy, String purposeOfUse) {
		Assert.hasText(xacmlPolicy, "Xacml policy is not set");

		NodeList senderNodeList = null;
		NodeList recipientNodeList = null;

		try {
			final Document xmlDoc = documentXmlConverter
					.loadDocument(xacmlPolicy);

			// XPath ignores namespaces by using local-name() XPath function
			senderNodeList = documentAccessor
					.getNodeList(
							xmlDoc,
							"//*[local-name() = 'SubjectAttributeDesignator'][@AttributeId='urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject']/../../*[local-name() = 'AttributeValue']");

			recipientNodeList = documentAccessor
					.getNodeList(
							xmlDoc,
							"//*[local-name() = 'SubjectAttributeDesignator'][@AttributeId='urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject']/../../*[local-name() = 'AttributeValue']");

			final NodeList currentDateNodeList = documentAccessor
					.getNodeList(
							xmlDoc,
							"//*[local-name() = 'EnvironmentAttributeDesignator'][@AttributeId='urn:oasis:names:tc:xacml:1.0:environment:current-dateTime']/../..");

			// Remove environment:current-dateTime nodes
			for (int i = 0; i < currentDateNodeList.getLength(); i++) {
				final Node node = currentDateNodeList.item(i);
				node.getParentNode().removeChild(node);
			}

			// Get the updated policy after removing
			// environment:current-dateTime nodes
			xacmlPolicy = documentXmlConverter.convertXmlDocToString(xmlDoc);

		} catch (final Exception e) {
			logger.error(
					"Exception occured when trying to query and manipulate xaml policy string",
					e);
		}

		// Create xacmlRequest
		final XacmlRequest xacmlRequest = new XacmlRequest();

		// String resourceId = resourceNode.getTextContent();
		// xacmlRequest.setPatientId(resourceId);

		final String intermediarySubjectNPI = senderNodeList.item(0)
				.getTextContent();
		xacmlRequest.setIntermediarySubjectNPI(intermediarySubjectNPI);

		final String recipientSubjectNPI = recipientNodeList.item(0)
				.getTextContent();
		xacmlRequest.setRecipientSubjectNPI(recipientSubjectNPI);

		xacmlRequest.setPurposeOfUse(purposeOfUse);

		final RequestType request = requestGenerator.generateRequest(
				xacmlRequest.getRecipientSubjectNPI(),
				xacmlRequest.getIntermediarySubjectNPI(),
				xacmlRequest.getPurposeOfUse());

		final InputSource source = new InputSource(
				new StringReader(xacmlPolicy));

		Evaluatable policy = null;
		try {
			policy = PolicyMarshaller.unmarshal(source);
		} catch (final Exception e) {
			logger.error(
					"Exception occured when trying to unmarshal xaml policy to be used by PDP engine",
					e);
		}

		final List<Evaluatable> policies = new ArrayList<Evaluatable>();
		policies.add(policy);

		final XacmlResponse xacmlResponse = managePoliciesAndEvaluateRequest(
				policies, xacmlRequest, request);

		final PdpRequestResponse pdpRequestResponse = new PdpRequestResponse();

		pdpRequestResponse.setXacmlRequest(xacmlRequest);
		pdpRequestResponse.setXacmlResponse(xacmlResponse);

		return pdpRequestResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.contexthandler.PolicyDecisionPoint#evaluateRequest(org
	 * .herasaf.xacml.core.api.PDP,
	 * org.herasaf.xacml.core.context.impl.RequestType, java.util.List)
	 */
	@Override
	@Deprecated
	public XacmlResponse evaluateRequest(PDP pdp, RequestType request,
			List<Evaluatable> policies, XacmlRequest xacmlRequest)
			throws AuditException {
		logger.info("evaluateRequest invoked");
		return managePoliciesAndEvaluateRequest(pdp, request, policies,
				xacmlRequest);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.contexthandler.PolicyDecisionPoint#evaluateRequest(org
	 * .herasaf.xacml.core.api.PDP,
	 * org.herasaf.xacml.core.context.impl.RequestType, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	@Deprecated
	public XacmlResponse evaluateRequest(PDP pdp, RequestType request,
			XacmlRequest xacmlRequest) throws AuditException,
			NoPolicyFoundException, PolicyProviderException {
		logger.info("evaluateRequest invoked");
		final List<Evaluatable> deployedPolicies = deployPolicies(pdp,
				xacmlRequest);
		return managePoliciesAndEvaluateRequest(pdp, request, deployedPolicies,
				xacmlRequest);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.contexthandler.PolicyDecisionPoint#evaluateRequest(org
	 * .herasaf.xacml.core.context.impl.RequestType, java.util.List)
	 */
	@Override
	@Deprecated
	public XacmlResponse evaluateRequest(RequestType request,
			List<Evaluatable> policies, XacmlRequest xacmlRequest)
			throws AuditException {
		logger.info("evaluateRequest invoked");
		return managePoliciesAndEvaluateRequest(getSimplePDP(), request,
				policies, xacmlRequest);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.contexthandler.PolicyDecisionPoint#evaluateRequest(org
	 * .herasaf.xacml.core.context.impl.RequestType, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public XacmlResponse evaluateRequest(RequestType request,
			XacmlRequest xacmlRequest) throws AuditException,
			NoPolicyFoundException, PolicyProviderException {
		logger.info("evaluateRequest invoked");
		return managePoliciesAndEvaluateRequest(request, xacmlRequest);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.contexthandler.PolicyDecisionPoint#evaluateRequest(gov
	 * .samhsa.acs.common.dto.XacmlRequest)
	 */
	@Override
	public XacmlResponse evaluateRequest(XacmlRequest xacmlRequest)
			throws AuditException, NoPolicyFoundException,
			PolicyProviderException {
		logger.info("evaluateRequest invoked");
		final RequestType request = requestGenerator.generateRequest(
				xacmlRequest.getRecipientSubjectNPI(),
				xacmlRequest.getIntermediarySubjectNPI(),
				xacmlRequest.getPurposeOfUse());
		return managePoliciesAndEvaluateRequest(request, xacmlRequest);
	}

	/**
	 * Deploy policies.
	 *
	 * @param pdp
	 *            the pdp
	 * @param policies
	 *            the policies
	 * @param xacmlRequest
	 *            the xacml request
	 * @param isAudited
	 *            the is audited
	 * @throws AuditException
	 *             the audit exception
	 */
	void deployPolicies(PDP pdp, List<Evaluatable> policies,
			XacmlRequest xacmlRequest, boolean isAudited) throws AuditException {
		try {
			final PolicyRetrievalPoint repo = pdp.getPolicyRepository();
			final UnorderedPolicyRepository repository = (UnorderedPolicyRepository) repo;
			repository.deploy(policies);
			if (isAudited) {
				for (final Evaluatable policy : policies) {
					auditPolicy(policy, xacmlRequest);
				}
			}
		} catch (AuditException | WritingException | IOException
				| DocumentAccessorException | DocumentXmlConverterException e) {
			// TODO (BU): ADD ERROR LOG
			undeployAllPolicies(pdp);
			throw new AuditException(e.getMessage(), e);
		}
	}

	/**
	 * Gets all xacml policies of a patient unique id.
	 *
	 * @param xacmlRequest
	 *            the xacml request
	 * @return the policies
	 * @throws NoPolicyFoundException
	 *             the no policy found exception
	 * @throws PolicyProviderException
	 *             the policy provider exception
	 */
	List<Evaluatable> getPolicies(XacmlRequest xacmlRequest)
			throws NoPolicyFoundException, PolicyProviderException {

		return policyProvider.getPolicies(xacmlRequest);
	}

	/**
	 * Gets the simple pdp.
	 *
	 * @return the simple pdp
	 */
	PDP getSimplePDP() {
		return SimplePDPFactory.getSimplePDP();
	}

	/**
	 * Undeploy multiple policies on the policy repository.
	 *
	 * @param pdp
	 *            the pdp
	 * @param policyIds
	 *            the policy ids
	 */
	void undeployPoliciesById(PDP pdp, List<EvaluatableID> policyIds) {
		final PolicyRepository repo = (PolicyRepository) pdp
				.getPolicyRepository();
		repo.undeploy(policyIds);
	}

	/**
	 * Undeploy multiple policies on the policy repository.
	 *
	 * @param pdp
	 *            the pdp
	 * @param policy
	 *            the policy
	 */
	void undeployPolicy(PDP pdp, Evaluatable policy) {
		final PolicyRepository repo = (PolicyRepository) pdp
				.getPolicyRepository();
		repo.undeploy(policy.getId());

	}

	/**
	 * Audit policy.
	 *
	 * @param policy
	 *            the policy
	 * @param xacmlRequest
	 *            the xacml request
	 * @throws WritingException
	 *             the writing exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws DocumentAccessorException
	 *             the document accessor exception
	 * @throws AuditException
	 *             the audit exception
	 */
	private void auditPolicy(Evaluatable policy, XacmlRequest xacmlRequest)
			throws WritingException, IOException, DocumentAccessorException,
			AuditException {
		final StringWriter writer = new StringWriter();
		PolicyMarshaller.marshal(policy, writer);
		final Map<PredicateKey, String> predicateMap = auditService
				.createPredicateMap();
		final String policyString = writer.toString();
		writer.close();
		final NodeList policyIdNodeList = documentAccessor.getNodeList(
				documentXmlConverter.loadDocument(policyString), "//@PolicyId");
		Set<String> policyIdSet = null;
		if (policyIdNodeList.getLength() > 0) {
			policyIdSet = new HashSet<String>();
			for (int i = 0; i < policyIdNodeList.getLength(); i++) {
				policyIdSet.add(policyIdNodeList.item(i).getNodeValue());
			}
		}
		predicateMap.put(XACML_POLICY, policyString);
		if (policyIdSet != null) {
			predicateMap.put(XACML_POLICY_ID, policyIdSet.toString());
		}
		auditService.audit(this, xacmlRequest.getMessageId(), DEPLOY_POLICY,
				xacmlRequest.getPatientId(), predicateMap);
	}

	/**
	 * Deploy all policies of a patient unique id.
	 *
	 * @param pdp
	 *            the pdp
	 * @param xacmlRequest
	 *            the xacml request
	 * @return the list
	 * @throws AuditException
	 *             the audit exception
	 * @throws NoPolicyFoundException
	 *             the no policy found exception
	 * @throws PolicyProviderException
	 *             the policy provider exception
	 */
	private List<Evaluatable> deployPolicies(PDP pdp, XacmlRequest xacmlRequest)
			throws AuditException, NoPolicyFoundException,
			PolicyProviderException {
		final List<Evaluatable> deployedPolicies = getPolicies(xacmlRequest);
		deployPolicies(pdp, deployedPolicies, xacmlRequest, true);
		return deployedPolicies;
	}

	/**
	 * Evaluate request.
	 *
	 * @param simplePDP
	 *            the simple pdp
	 * @param request
	 *            the request
	 * @return the xacml response
	 */
	private XacmlResponse evaluateRequest(PDP simplePDP, RequestType request) {
		final XacmlResponse xacmlResponse = new XacmlResponse();

		final ResponseType response = simplePDP.evaluate(request);
		for (final ResultType r : response.getResults()) {
			logger.debug("PDP Decision: " + r.getDecision().toString());
			xacmlResponse.setPdpDecision(r.getDecision().toString());

			if (r.getObligations() != null) {
				final List<String> obligations = new LinkedList<String>();
				for (final ObligationType o : r.getObligations()
						.getObligations()) {
					for (final AttributeAssignmentType a : o
							.getAttributeAssignments()) {
						for (final Object c : a.getContent()) {
							logger.debug("With Obligation: " + c);
							obligations.add(c.toString());
						}
					}
				}
				xacmlResponse.setPdpObligation(obligations);
			}
		}

		logger.debug("xacmlResponse.pdpDecision: "
				+ xacmlResponse.getPdpDecision());
		logger.debug("xacmlResponse is ready!");
		return xacmlResponse;
	}

	/**
	 * Manage policies and evaluate request.
	 *
	 * @param policies
	 *            the policies
	 * @param xacmlRequest
	 *            the xacml request
	 * @param request
	 *            the request
	 * @return the xacml response
	 */
	private synchronized XacmlResponse managePoliciesAndEvaluateRequest(
			List<Evaluatable> policies, XacmlRequest xacmlRequest,
			RequestType request) {
		final PDP pdp = getSimplePDP();
		try {
			deployPolicies(pdp, policies, xacmlRequest, false);
		} catch (final AuditException e) {
			// it shouldn't throw AuditException when isAudited=false
		}
		final XacmlResponse xacmlResponse = evaluateRequest(pdp, request);
		undeployAllPolicies(pdp);
		return xacmlResponse;
	}

	/**
	 * Manage policies and evaluate request.
	 *
	 * @param pdp
	 *            the pdp
	 * @param request
	 *            the request
	 * @return the xacml response
	 */
	private XacmlResponse managePoliciesAndEvaluateRequest(PDP pdp,
			RequestType request) {
		final XacmlResponse xacmlResponse = evaluateRequest(pdp, request);
		undeployAllPolicies(pdp);
		return xacmlResponse;
	}

	/**
	 * Manage policies and evaluate request (the policies should already be
	 * deployed on pdp. Evaluates the request and undeploys the policies after
	 * evaluation).
	 *
	 * @param pdp
	 *            the pdp
	 * @param request
	 *            the request
	 * @param deployedPolicies
	 *            the deployed policies
	 * @param xacmlRequest
	 *            the xacml request
	 * @return the xacml response
	 * @throws AuditException
	 *             the audit exception
	 */
	@Deprecated
	private XacmlResponse managePoliciesAndEvaluateRequest(PDP pdp,
			RequestType request, List<Evaluatable> deployedPolicies,
			XacmlRequest xacmlRequest) throws AuditException {
		deployPolicies(pdp, deployedPolicies, xacmlRequest, true);
		final XacmlResponse xacmlResponse = evaluateRequest(pdp, request);
		undeployPolicies(pdp, deployedPolicies);
		return xacmlResponse;
	}

	/**
	 * Manage policies and evaluate request.
	 *
	 * @param request
	 *            the request
	 * @param xacmlRequest
	 *            the xacml request
	 * @return the xacml response
	 * @throws AuditException
	 *             the audit exception
	 * @throws NoPolicyFoundException
	 *             the no policy found exception
	 * @throws PolicyProviderException
	 *             the policy provider exception
	 */
	private synchronized XacmlResponse managePoliciesAndEvaluateRequest(
			RequestType request, XacmlRequest xacmlRequest)
			throws AuditException, NoPolicyFoundException,
			PolicyProviderException {
		final PDP pdp = getSimplePDP();
		deployPolicies(pdp, xacmlRequest);
		return managePoliciesAndEvaluateRequest(pdp, request);
	}

	/**
	 * Undeploy all policies.
	 *
	 * @param pdp
	 *            the pdp
	 */
	private void undeployAllPolicies(PDP pdp) {
		final PolicyRepository repo = (PolicyRepository) pdp
				.getPolicyRepository();
		final List<Evaluatable> policies = new LinkedList<Evaluatable>(
				repo.getDeployment());
		for (final Evaluatable policy : policies) {
			repo.undeploy(policy.getId());
		}
	}

	/**
	 * Undeploy policies.
	 *
	 * @param pdp
	 *            the pdp
	 * @param policies
	 *            the policies
	 */
	private void undeployPolicies(PDP pdp, List<Evaluatable> policies) {
		final PolicyRepository repo = (PolicyRepository) pdp
				.getPolicyRepository();
		for (final Evaluatable policy : policies) {
			repo.undeploy(policy.getId());
		}
	}
}
