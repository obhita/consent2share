package gov.samhsa.ds4ppilot.orchestrator.contexthandler;

import gov.samhsa.ds4ppilot.orchestrator.dto.XacmlResponse;

import java.util.List;

import org.herasaf.xacml.core.context.impl.RequestType;
import org.herasaf.xacml.core.policy.Evaluatable;
import org.herasaf.xacml.core.policy.EvaluatableID;

public interface PolicyDecisionPoint {

	/**
	 * Gets all xacml policies of a resource id.
	 * 
	 * @param resourceId the resource id
	 * @return the policies
	 */
	public abstract List<Evaluatable> getPolicies(String resourceId);

	/**
	 * Deploy all policies of a resource id.
	 * 
	 * @param resourceId the resource id
	 */
	public abstract void deployPolicies(String resourceId);

	/**
	 * Deploy policies.
	 * 
	 * @param policies the policies
	 */
	public abstract void deployPolicies(List<Evaluatable> policies);

	/**
	 * Retrieve the policy repository and undeploy the policy by its ID.
	 * 
	 * @param policy the policy
	 */
	public abstract void undeployPolicy(Evaluatable policy);

	/**
	 * Undeploy multiple policies on the policy repository.
	 * 
	 * @param policyIds the policy ids
	 */
	public abstract void undeployPolicies(List<EvaluatableID> policyIds);

	/**
	 * Evaluate the request using the simplePDP and retrieve the response from
	 * the PDP.
	 * 
	 * @param request the request
	 * @return the xacml response
	 */
	public abstract XacmlResponse evaluateRequest(RequestType request);

}