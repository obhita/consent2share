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
package gov.samhsa.acs.contexthandler;

import gov.samhsa.acs.common.dto.XacmlRequest;
import gov.samhsa.acs.common.dto.XacmlResponse;

import java.util.LinkedList;
import java.util.List;

import org.herasaf.xacml.core.api.PDP;
import org.herasaf.xacml.core.api.PolicyRepository;
import org.herasaf.xacml.core.api.PolicyRetrievalPoint;
import org.herasaf.xacml.core.api.UnorderedPolicyRepository;
import org.herasaf.xacml.core.context.impl.RequestType;
import org.herasaf.xacml.core.context.impl.ResponseType;
import org.herasaf.xacml.core.context.impl.ResultType;
import org.herasaf.xacml.core.policy.Evaluatable;
import org.herasaf.xacml.core.policy.EvaluatableID;
import org.herasaf.xacml.core.policy.impl.AttributeAssignmentType;
import org.herasaf.xacml.core.policy.impl.ObligationType;
import org.herasaf.xacml.core.simplePDP.SimplePDPFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class PolicyDecisionPoint.
 */
public class PolicyDecisionPointImpl implements PolicyDecisionPoint {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PolicyDecisionPointImpl.class);

	/** Data service for policy decision point implementation. */
	private PolicyDecisionPointImplData data;

	/** The request generator. */
	private RequestGenerator requestGenerator;

	/** The simple pdp. */
	private PDP simplePDP;

	/**
	 * Instantiates a new policy decision point impl.
	 * 
	 * @param data
	 *            the data
	 * @param requestGenerator
	 *            the request generator
	 */
	public PolicyDecisionPointImpl(PolicyDecisionPointImplData data,
			RequestGenerator requestGenerator) {
		super();
		this.data = data;
		this.requestGenerator = requestGenerator;
		this.simplePDP = getSimplePDP();
	}

	/**
	 * Gets all xacml policies of a patient unique id.
	 * 
	 * @param patientUniqueId
	 *            the patient unique id
	 * @return the policies
	 */
	public List<Evaluatable> getPolicies(String patientUniqueId) {

		return data.getPolicies(patientUniqueId);
	}

	/**
	 * Deploy all policies of a patient unique id.
	 * 
	 * @param pdp
	 *            the pdp
	 * @param patientUniqueId
	 *            the patient unique id
	 * @return the list
	 */
	public List<Evaluatable> deployPolicies(PDP pdp, String patientUniqueId) {
		List<Evaluatable> deployedPolicies = getPolicies(patientUniqueId);
		deployPolicies(pdp, deployedPolicies);
		return deployedPolicies;
	}

	/**
	 * Deploy policies.
	 * 
	 * @param pdp
	 *            the pdp
	 * @param policies
	 *            the policies
	 */
	public void deployPolicies(PDP pdp, List<Evaluatable> policies) {
		PolicyRetrievalPoint repo = pdp.getPolicyRepository();
		UnorderedPolicyRepository repository = (UnorderedPolicyRepository) repo;
		repository.deploy(policies);
	}

	/**
	 * Undeploy multiple policies on the policy repository.
	 * 
	 * @param pdp
	 *            the pdp
	 * @param policy
	 *            the policy
	 */
	public void undeployPolicy(PDP pdp, Evaluatable policy) {
		PolicyRepository repo = (PolicyRepository) pdp.getPolicyRepository();
		repo.undeploy(policy.getId());

	}

	/**
	 * Undeploy policies.
	 * 
	 * @param pdp
	 *            the pdp
	 * @param policies
	 *            the policies
	 */
	public void undeployPolicies(PDP pdp, List<Evaluatable> policies) {
		PolicyRepository repo = (PolicyRepository) pdp.getPolicyRepository();
		for (Evaluatable policy : policies) {
			repo.undeploy(policy.getId());
		}
	}

	/**
	 * Undeploy multiple policies on the policy repository.
	 * 
	 * @param pdp
	 *            the pdp
	 * @param policyIds
	 *            the policy ids
	 */
	public void undeployPoliciesById(PDP pdp, List<EvaluatableID> policyIds) {
		PolicyRepository repo = (PolicyRepository) pdp.getPolicyRepository();
		repo.undeploy(policyIds);
	}

	/**
	 * Gets the simple pdp.
	 * 
	 * @return the simple pdp
	 */
	public PDP getSimplePDP() {
		return SimplePDPFactory.getSimplePDP();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.contexthandler.PolicyDecisionPoint#evaluateRequest
	 * (org.herasaf.xacml.core.api.PDP,
	 * org.herasaf.xacml.core.context.impl.RequestType, java.util.List)
	 */
	@Override
	public XacmlResponse evaluateRequest(PDP pdp, RequestType request,
			List<Evaluatable> policies) {
		LOGGER.info("evaluateRequest invoked");
		return managePoliciesAndEvaluateRequest(pdp, request, policies);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.contexthandler.PolicyDecisionPoint#evaluateRequest
	 * (org.herasaf.xacml.core.api.PDP,
	 * org.herasaf.xacml.core.context.impl.RequestType, java.lang.String)
	 */
	@Override
	public XacmlResponse evaluateRequest(PDP pdp, RequestType request,
			String patientUniqueId) {
		LOGGER.info("evaluateRequest invoked");
		List<Evaluatable> deployedPolicies = deployPolicies(pdp,
				patientUniqueId);
		return managePoliciesAndEvaluateRequest(pdp, request, deployedPolicies);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.contexthandler.PolicyDecisionPoint#evaluateRequest
	 * (org.herasaf.xacml.core.context.impl.RequestType)
	 */
	@Override
	public XacmlResponse evaluateRequest(RequestType request,
			String patientUniqueId) {
		LOGGER.info("evaluateRequest invoked");
		return managePoliciesAndEvaluateRequest(this.simplePDP, request,
				patientUniqueId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.contexthandler.PolicyDecisionPoint#evaluateRequest
	 * (gov.samhsa.acs.common.dto.XacmlRequest)
	 */
	public XacmlResponse evaluateRequest(XacmlRequest xacmlRequest) {
		LOGGER.info("evaluateRequest invoked");
		RequestType request = requestGenerator.generateRequest(
				xacmlRequest.getRecepientSubjectNPI(),
				xacmlRequest.getIntermediarySubjectNPI(),
				xacmlRequest.getPurposeOfUse(), xacmlRequest.getPatientId());
		return managePoliciesAndEvaluateRequest(this.simplePDP, request,
				xacmlRequest.getPatientUniqueId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.contexthandler.PolicyDecisionPoint#evaluateRequest
	 * (org.herasaf.xacml.core.context.impl.RequestType, java.util.List)
	 */
	@Override
	public XacmlResponse evaluateRequest(RequestType request,
			List<Evaluatable> policies) {
		LOGGER.info("evaluateRequest invoked");
		return managePoliciesAndEvaluateRequest(this.simplePDP, request,
				policies);
	}

	/**
	 * Manage policies and evaluate request (deploys the policies, evaluates the
	 * request. Policies are undeployed after evaluation).
	 * 
	 * @param pdp
	 *            the pdp
	 * @param request
	 *            the request
	 * @param patientUniqueId
	 *            the patient unique id
	 * @return the xacml response
	 */
	private XacmlResponse managePoliciesAndEvaluateRequest(PDP pdp,
			RequestType request, String patientUniqueId) {
		List<Evaluatable> deployedPolicies = deployPolicies(pdp,
				patientUniqueId);
		return managePoliciesAndEvaluateRequest(pdp, request, deployedPolicies);
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
	 * @return the xacml response
	 */
	private XacmlResponse managePoliciesAndEvaluateRequest(PDP pdp,
			RequestType request, List<Evaluatable> deployedPolicies) {
		XacmlResponse xacmlResponse = evaluateRequest(pdp, request);
		undeployPolicies(pdp, deployedPolicies);
		return xacmlResponse;
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
		XacmlResponse xacmlResponse = new XacmlResponse();

		ResponseType response = simplePDP.evaluate(request);
		for (ResultType r : response.getResults()) {
			LOGGER.debug("PDP Decision: " + r.getDecision().toString());
			xacmlResponse.setPdpDecision(r.getDecision().toString());

			if (r.getObligations() != null) {
				List<String> obligations = new LinkedList<String>();
				for (ObligationType o : r.getObligations().getObligations()) {
					for (AttributeAssignmentType a : o
							.getAttributeAssignments()) {
						for (Object c : a.getContent()) {
							LOGGER.debug("With Obligation: " + c);
							obligations.add(c.toString());
						}
					}
				}
				xacmlResponse.setPdpObligation(obligations);
			}
		}

		LOGGER.debug("xacmlResponse is ready: ");
		LOGGER.debug("xacmlResponse.pdpDecision: "
				+ xacmlResponse.getPdpDecision());
		if (xacmlResponse.getPdpObligation() != null) {
			for (String o : xacmlResponse.getPdpObligation()) {
				LOGGER.debug("xacmlResponse.pdpObligation: " + o);
			}
		}
		LOGGER.debug("xacmlResponse is ready!");
		return xacmlResponse;
	}
}
