package gov.samhsa.acs.contexthandler;

import static org.junit.Assert.assertEquals;

import gov.samhsa.acs.common.dto.XacmlRequest;
import gov.samhsa.acs.common.dto.XacmlResponse;
import gov.samhsa.acs.contexthandler.PolicyDecisionPointImpl;
import gov.samhsa.acs.contexthandler.PolicyProvider;
import gov.samhsa.acs.contexthandler.RequestGenerator;
import gov.samhsa.consent2share.commonunit.io.ResourceFileReader;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
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
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
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
	private PolicyProvider policyProvider;
	@Mock
	private RequestGenerator requestGeneratorMock;

	@Before
	public void setUp() throws Exception {
		pdp = new PolicyDecisionPointImpl(policyProvider, requestGeneratorMock);
		simplePDP = SimplePDPFactory.getSimplePDP();
		try {
			InputStream is = new FileInputStream(
					"src/test/resources/samplePolicy.xml");
			// Before calling this SimplePDPFactory.getSimplePDP() must be
			// called
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

	@Test
	public void testEvaluateRequestWithPatientUniqueId() {
		pdp.deployPolicies(simplePDP, policies);
		assertEquals(
				"PERMIT",
				pdp.evaluateRequest(simplePDP, request,
						new LinkedList<Evaluatable>()).getPdpDecision());
		pdp.undeployPolicy(simplePDP, policy);
		pdp.deployPolicies(simplePDP, policies_invalid);
		assertEquals("DENY",
				pdp.evaluateRequest(simplePDP, request, "1", "", "")
						.getPdpDecision());
	}

	@Test
	public void testEvaluateRequestWithRequestAndPatientUniqueId()
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		pdp.deployPolicies(simplePDP, policies);
		assertEquals(
				"PERMIT",
				pdp.evaluateRequest(simplePDP, request,
						new LinkedList<Evaluatable>()).getPdpDecision());
		pdp.undeployPolicy(simplePDP, policy);
		pdp.deployPolicies(simplePDP, policies_invalid);
		Field simplePDPField = pdp.getClass().getDeclaredField("simplePDP");
		simplePDPField.setAccessible(true);
		simplePDPField.set(pdp, simplePDP);
		assertEquals("DENY", pdp.evaluateRequest(request, "1", "", "")
				.getPdpDecision());
	}

	@Test
	public void testEvaluateRequestWithXacmlRequest() throws SecurityException,
			NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException {
		XacmlRequest xacmlRequest = new XacmlRequest();
		xacmlRequest.setIntermediarySubjectNPI("1285969170");
		xacmlRequest.setRecepientSubjectNPI("1568797520");
		xacmlRequest.setPurposeOfUse("TREAT");
		xacmlRequest.setPatientId("consent2share@outlook.com");
		when(
				requestGeneratorMock.generateRequest("1568797520",
						"1285969170", "TREAT", "consent2share@outlook.com"))
				.thenReturn(request);
		pdp.deployPolicies(simplePDP, policies);
		assertEquals(
				"PERMIT",
				pdp.evaluateRequest(simplePDP, request,
						new LinkedList<Evaluatable>()).getPdpDecision());
		pdp.undeployPolicy(simplePDP, policy);
		pdp.deployPolicies(simplePDP, policies_invalid);
		Field simplePDPField = pdp.getClass().getDeclaredField("simplePDP");
		simplePDPField.setAccessible(true);
		simplePDPField.set(pdp, simplePDP);
		assertEquals("DENY", pdp.evaluateRequest(xacmlRequest).getPdpDecision());
	}

	@Test
	public void testGetPolicies() {
		List<Evaluatable> policies = new ArrayList<Evaluatable>();
		when(policyProvider.getPolicies("1", "", "")).thenReturn(policies);
		assertEquals(pdp.getPolicies("1", "", ""), policies);
	}

	@Test
	public void testEvaluatePolicyForTrying_Given_Correct_Policy_Successds() {
		// Arrange
		PolicyDecisionPointImpl thePdp = new PolicyDecisionPointImpl(null, new RequestGenerator());
		
		// Read policy file from resource
		final String policyFileUri = "xacmlPolicyForTrying.xml";
		String xacmlPolicy = ResourceFileReader
				.getStringFromResourceFile(policyFileUri);
		
		// Act
		XacmlResponse xacmlResponse =  thePdp.evaluatePolicyForTrying(xacmlPolicy).getXacmlResponse();
		
		// Assert
		assertEquals(xacmlResponse.getPdpDecision().toLowerCase(), "permit");
		assertEquals(xacmlResponse.getPdpObligation().size(), 11);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEvaluatePolicyForTrying_Given_Null_XacmlPolicy_Throws_Exception() {
		// Arrange
		String xacmlPolicy = null;
		
		// Act
		pdp.evaluatePolicyForTrying(xacmlPolicy);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEvaluatePolicyForTrying_Given_Empty_XacmlPolicy_Throws_Exception() {
		// Arrange
		String xacmlPolicy = " ";
		
		// Act
		pdp.evaluatePolicyForTrying(xacmlPolicy);
	}

}
