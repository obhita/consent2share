package gov.samhsa.ds4ppilot.orchestrator.contexthandler;

import static org.junit.Assert.*;

import gov.samhsa.ds4ppilot.orchestrator.dto.XacmlResponse;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.herasaf.xacml.core.context.RequestMarshaller;
import org.herasaf.xacml.core.context.impl.RequestType;
import org.herasaf.xacml.core.policy.Evaluatable;
import org.herasaf.xacml.core.policy.EvaluatableID;
import org.herasaf.xacml.core.policy.PolicyMarshaller;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PolicyDecisionPointImplTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(PolicyDecisionPointImplTest.class);
	private Evaluatable policy;
	private Evaluatable policy_invalid;
	private List<Evaluatable> policies;
	private List<Evaluatable> policies_invalid;
	private RequestType request;
	private PolicyDecisionPoint pdp;

	@Before
	public void setUp() throws Exception {
		pdp = new PolicyDecisionPointImpl();
		
		try {
			InputStream is = new FileInputStream("src/test/resources/samplePolicy.xml");
			policy=PolicyMarshaller.unmarshal(is);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		try {
			InputStream is = new FileInputStream("src/test/resources/samplePolicyDenyByResourceIdMismatch.xml");
			policy_invalid=PolicyMarshaller.unmarshal(is);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		
		InputStream requestis=null;
		
		try {
			requestis=new FileInputStream("src/test/resources/samplePolicyRequest.xml");
			request=RequestMarshaller.unmarshal(requestis);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		policies = new LinkedList<Evaluatable>();
		policies.add(policy);
		policies_invalid = new LinkedList<Evaluatable>();
		policies_invalid.add(policy_invalid);
	}

	@After
	public void tearDown() throws Exception {
		policy=null;
		policy_invalid=null;
		policies=null;
		policies_invalid=null;
		request=null;
		pdp=null;
	}

	@Test
	public void testDeployPoliciesListOfEvaluatable() {
		assertEquals("NOT_APPLICABLE",pdp.evaluateRequest(request).getPdpDecision());
		pdp.deployPolicies(policies);
		assertEquals("PERMIT",pdp.evaluateRequest(request).getPdpDecision());
	}

	@Test
	public void testUndeployPolicy() {
		pdp.deployPolicies(policies);
		pdp.undeployPolicy(policy);
		assertEquals("NOT_APPLICABLE",pdp.evaluateRequest(request).getPdpDecision());
	}

	@Test
	public void testUndeployPolicies() {
		EvaluatableID id = policy.getId();
		List<EvaluatableID> ids = new LinkedList<EvaluatableID>();
		ids.add(id);
		pdp.deployPolicies(policies);
		assertEquals("PERMIT",pdp.evaluateRequest(request).getPdpDecision());
		pdp.undeployPolicies(ids);
		assertEquals("NOT_APPLICABLE",pdp.evaluateRequest(request).getPdpDecision());
	}

	@Test
	public void testEvaluateRequest() {
		pdp.deployPolicies(policies);
		assertEquals("PERMIT",pdp.evaluateRequest(request).getPdpDecision());
		pdp.undeployPolicy(policy);
		pdp.deployPolicies(policies_invalid);
		assertEquals("DENY",pdp.evaluateRequest(request).getPdpDecision());
	}

}
