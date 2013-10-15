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
package gov.samhsa.ds4ppilot.orchestrator.contexthandler;

import gov.samhsa.ds4ppilot.orchestrator.dto.XacmlResponse;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The Class PolicyDecisionPoint.
 */
@Component("policyDecisionPoint")
public class PolicyDecisionPointImpl implements PolicyDecisionPoint {

	/** The simple pdp. */
	private PDP simplePDP;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PolicyDecisionPointImpl.class);
	
	/** Data service for policy decision point implementation. */
	@Autowired
	private PolicyDecisionPointImplData data;

	/**
	 * Instantiates a new policy decision point.
	 */
	public PolicyDecisionPointImpl() {
		this.simplePDP = SimplePDPFactory.getSimplePDP();
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.ds4ppilot.orchestrator.contexthandler.PolicyDecisionPoint#getPolicies(java.lang.String)
	 */
	@Override
	public List<Evaluatable> getPolicies(String resourceId) {

		return data.getPolicies(resourceId);
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.ds4ppilot.orchestrator.contexthandler.PolicyDecisionPoint#deployPolicies(java.lang.String)
	 */
	@Override
	public void deployPolicies(String resourceId) {
		deployPolicies(getPolicies(resourceId));
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.ds4ppilot.orchestrator.contexthandler.PolicyDecisionPoint#deployPolicies(java.util.List)
	 */
	@Override
	public void deployPolicies(List<Evaluatable> policies) {
		PolicyRetrievalPoint repo = simplePDP.getPolicyRepository();
		UnorderedPolicyRepository repository = (UnorderedPolicyRepository) repo;
		repository.deploy(policies);
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.ds4ppilot.orchestrator.contexthandler.PolicyDecisionPoint#undeployPolicy(org.herasaf.xacml.core.policy.Evaluatable)
	 */
	@Override
	public void undeployPolicy(Evaluatable policy) {
		PolicyRepository repo = (PolicyRepository) simplePDP
				.getPolicyRepository();
		repo.undeploy(policy.getId());

	}

	/* (non-Javadoc)
	 * @see gov.samhsa.ds4ppilot.orchestrator.contexthandler.PolicyDecisionPoint#undeployPolicies(java.util.List)
	 */
	@Override
	public void undeployPolicies(List<EvaluatableID> policyIds) {
		PolicyRepository repo = (PolicyRepository) simplePDP
				.getPolicyRepository();
		repo.undeploy(policyIds);
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.ds4ppilot.orchestrator.contexthandler.PolicyDecisionPoint#evaluateRequest(org.herasaf.xacml.core.context.impl.RequestType)
	 */
	@Override
	public XacmlResponse evaluateRequest(RequestType request) {
		LOGGER.info("evaluateRequest invoked");

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
