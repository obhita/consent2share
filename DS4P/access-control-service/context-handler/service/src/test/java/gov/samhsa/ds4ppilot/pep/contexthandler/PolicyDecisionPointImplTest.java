package gov.samhsa.ds4ppilot.pep.contexthandler;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.herasaf.xacml.core.api.PDP;
import org.herasaf.xacml.core.context.RequestMarshaller;
import org.herasaf.xacml.core.context.impl.RequestType;
import org.herasaf.xacml.core.policy.Evaluatable;
import org.herasaf.xacml.core.policy.EvaluatableID;
import org.herasaf.xacml.core.policy.PolicyMarshaller;
import org.herasaf.xacml.core.simplePDP.SimplePDPFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PolicyDecisionPointImplTest {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PolicyDecisionPointImplTest.class);
	private Evaluatable policy;
	private Evaluatable policy_invalid;
	private List<Evaluatable> policies;
	private List<Evaluatable> policies_invalid;
	private RequestType request;
	private PolicyDecisionPointImpl pdp;
	private PDP simplePDP;
	@Mock
	private PolicyDecisionPointImplData data;
	@Mock
	private RequestGenerator requestGeneratorMock;

	@Before
	public void setUp() throws Exception {
		pdp = new PolicyDecisionPointImpl(data, requestGeneratorMock);
		simplePDP = SimplePDPFactory.getSimplePDP();
		try {
			InputStream is = new FileInputStream(
					"src/test/resources/samplePolicy.xml");
			//Before calling this SimplePDPFactory.getSimplePDP() must be called
			policy = PolicyMarshaller.unmarshal(is);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		try {
			InputStream is = new FileInputStream(
					"src/test/resources/samplePolicyDenyByResourceIdMismatch.xml");
			policy_invalid = PolicyMarshaller.unmarshal(is);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		InputStream requestis = null;

		try {
			requestis = new FileInputStream(
					"src/test/resources/samplePolicyRequest.xml");
			request = RequestMarshaller.unmarshal(requestis);
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
		policy = null;
		policy_invalid = null;
		policies = null;
		policies_invalid = null;
		request = null;
		pdp = null;
	}

	@Test
	public void testDeployPoliciesListOfEvaluatable_NOT_APPLICABLE() {
		// Arrange
		LinkedList<Evaluatable> emptyPolicyList = new LinkedList<Evaluatable>();

		// Act
		String decision = pdp.evaluateRequest(request, emptyPolicyList)
				.getPdpDecision();

		// Assert
		assertEquals("NOT_APPLICABLE", decision);
	}

	@Test
	public void testDeployPoliciesListOfEvaluatable_PERMIT() {
		// Arrange
		pdp.deployPolicies(simplePDP, policies);

		// Act
		String decision = pdp.evaluateRequest(simplePDP, request, policies)
				.getPdpDecision();

		// Assert
		assertEquals("PERMIT", decision);
	}

	@Test
	public void testUndeployPolicy() {
		pdp.deployPolicies(simplePDP, policies);
		pdp.undeployPolicy(simplePDP, policy);
		assertEquals(
				"NOT_APPLICABLE",
				pdp.evaluateRequest(simplePDP, request,
						new LinkedList<Evaluatable>()).getPdpDecision());
	}

	@Test
	public void testUndeployPolicies() {
		EvaluatableID id = policy.getId();
		List<EvaluatableID> ids = new LinkedList<EvaluatableID>();
		ids.add(id);
		pdp.deployPolicies(simplePDP, policies);
		assertEquals(
				"PERMIT",
				pdp.evaluateRequest(simplePDP, request,
						new LinkedList<Evaluatable>()).getPdpDecision());
		pdp.undeployPoliciesById(simplePDP, ids);
		assertEquals(
				"NOT_APPLICABLE",
				pdp.evaluateRequest(simplePDP, request,
						new LinkedList<Evaluatable>()).getPdpDecision());
	}

	@Test
	public void testEvaluateRequest() {
		pdp.deployPolicies(simplePDP, policies);
		assertEquals(
				"PERMIT",
				pdp.evaluateRequest(simplePDP, request,
						new LinkedList<Evaluatable>()).getPdpDecision());
		pdp.undeployPolicy(simplePDP, policy);
		pdp.deployPolicies(simplePDP, policies_invalid);
		assertEquals(
				"DENY",
				pdp.evaluateRequest(simplePDP, request,
						new LinkedList<Evaluatable>()).getPdpDecision());
	}
}
