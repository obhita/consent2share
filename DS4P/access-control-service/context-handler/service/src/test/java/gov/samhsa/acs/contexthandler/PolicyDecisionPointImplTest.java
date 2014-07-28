package gov.samhsa.acs.contexthandler;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.audit.AuditService;
import gov.samhsa.acs.audit.AuditVerb;
import gov.samhsa.acs.audit.PredicateKey;
import gov.samhsa.acs.common.dto.XacmlRequest;
import gov.samhsa.acs.common.dto.XacmlResponse;
import gov.samhsa.acs.common.tool.DocumentAccessor;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;
import gov.samhsa.acs.common.tool.exception.DocumentAccessorException;
import gov.samhsa.acs.contexthandler.exception.NoPolicyFoundException;
import gov.samhsa.acs.contexthandler.exception.PolicyProviderException;
import gov.samhsa.consent2share.commonunit.io.ResourceFileReader;

import java.io.FileInputStream;
import java.io.InputStream;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import ch.qos.logback.audit.AuditException;

@RunWith(MockitoJUnitRunner.class)
public class PolicyDecisionPointImplTest {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PolicyDecisionPointImplTest.class);
	private Evaluatable policy;
	private Evaluatable policy_invalid;
	private List<Evaluatable> policies;
	private List<Evaluatable> policies_invalid;
	private RequestType request;
	private PDP simplePDP;
	
	@Mock
	private PolicyProvider policyProvider;
	@Mock
	private RequestGenerator requestGeneratorMock;
	@Mock
	private AuditService auditServiceMock;
	@Mock
	private DocumentXmlConverter documentXmlConverterMock;
	@Mock
	private DocumentAccessor documentAccessorMock;
	
	@Spy
	@InjectMocks
	private PolicyDecisionPointImpl pdp;

	@Before
	public void setUp() throws Exception {
		doNothing().when(auditServiceMock).audit(anyObject(), anyString(), isA(AuditVerb.class), anyString(), anyMapOf(PredicateKey.class, String.class));
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
	public void testDeployPoliciesListOfEvaluatable_NOT_APPLICABLE() throws AuditException {
		// Arrange
		LinkedList<Evaluatable> emptyPolicyList = new LinkedList<Evaluatable>();
		XacmlRequest xacmlRequest = new XacmlRequest();

		// Act
		String decision = pdp.evaluateRequest(request, emptyPolicyList, xacmlRequest)
				.getPdpDecision();

		// Assert
		assertEquals("NOT_APPLICABLE", decision);
	}

	@Test
	public void testDeployPoliciesListOfEvaluatable_PERMIT() throws AuditException, DocumentAccessorException {
		// Arrange
		XacmlRequest xacmlRequest = new XacmlRequest();
		NodeList nodeListMock = mock(NodeList.class);
		Document documentMock = mock(Document.class);
		when(documentXmlConverterMock.loadDocument(anyString())).thenReturn(documentMock);
		when(documentAccessorMock.getNodeList(eq(documentMock), anyString())).thenReturn(nodeListMock);
		
		// Act
		String decision = pdp.evaluateRequest(simplePDP, request, policies, xacmlRequest)
				.getPdpDecision();

		// Assert
		assertEquals("PERMIT", decision);
	}

	@Test
	public void testUndeployPolicy() throws AuditException {
		XacmlRequest xacmlRequest = new XacmlRequest();
		pdp.deployPolicies(simplePDP, policies, xacmlRequest, false);
		pdp.undeployPolicy(simplePDP, policy);
		assertEquals(
				"NOT_APPLICABLE",
				pdp.evaluateRequest(simplePDP, request,
						new LinkedList<Evaluatable>(), xacmlRequest).getPdpDecision());
	}

	@Test
	public void testUndeployPolicies() throws AuditException {
		XacmlRequest xacmlRequest = new XacmlRequest();
		EvaluatableID id = policy.getId();
		List<EvaluatableID> ids = new LinkedList<EvaluatableID>();
		ids.add(id);
		pdp.deployPolicies(simplePDP, policies, xacmlRequest, false);
		assertEquals(
				"PERMIT",
				pdp.evaluateRequest(simplePDP, request,
						new LinkedList<Evaluatable>(), xacmlRequest).getPdpDecision());
		pdp.undeployPoliciesById(simplePDP, ids);
		assertEquals(
				"NOT_APPLICABLE",
				pdp.evaluateRequest(simplePDP, request,
						new LinkedList<Evaluatable>(), xacmlRequest).getPdpDecision());
	}

	@Test
	public void testEvaluateRequest() throws AuditException {
		XacmlRequest xacmlRequest = new XacmlRequest();
		pdp.deployPolicies(simplePDP, policies, xacmlRequest, false);
		assertEquals(
				"PERMIT",
				pdp.evaluateRequest(simplePDP, request,
						new LinkedList<Evaluatable>(), xacmlRequest).getPdpDecision());
		pdp.undeployPolicy(simplePDP, policy);
		pdp.deployPolicies(simplePDP, policies_invalid, xacmlRequest, false);
		assertEquals(
				"DENY",
				pdp.evaluateRequest(simplePDP, request,
						new LinkedList<Evaluatable>(), xacmlRequest).getPdpDecision());
	}

	@Test
	public void testEvaluateRequestWithPatientUniqueId() throws AuditException, NoPolicyFoundException, PolicyProviderException {
		XacmlRequest xacmlRequest = new XacmlRequest();
		xacmlRequest.setPatientId("1");
		xacmlRequest.setRecipientSubjectNPI("");
		xacmlRequest.setIntermediarySubjectNPI("");
		pdp.deployPolicies(simplePDP, policies, xacmlRequest, false);
		assertEquals(
				"PERMIT",
				pdp.evaluateRequest(simplePDP, request,
						new LinkedList<Evaluatable>(), xacmlRequest).getPdpDecision());
		pdp.undeployPolicy(simplePDP, policy);
		pdp.deployPolicies(simplePDP, policies_invalid, xacmlRequest, false);
		assertEquals("DENY",
				pdp.evaluateRequest(simplePDP, request, xacmlRequest)
						.getPdpDecision());
	}

	@Test
	public void testEvaluateRequestWithRequestAndPatientUniqueId()
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException, AuditException, NoPolicyFoundException, PolicyProviderException {
		XacmlRequest xacmlRequest = new XacmlRequest();
		xacmlRequest.setPatientId("1");
		xacmlRequest.setRecipientSubjectNPI("");
		xacmlRequest.setIntermediarySubjectNPI("");
		pdp.deployPolicies(simplePDP, policies, xacmlRequest, false);
		assertEquals(
				"PERMIT",
				pdp.evaluateRequest(simplePDP, request,
						new LinkedList<Evaluatable>(), xacmlRequest).getPdpDecision());
		pdp.undeployPolicy(simplePDP, policy);
		pdp.deployPolicies(simplePDP, policies_invalid, xacmlRequest, false);
		when(pdp.getSimplePDP()).thenReturn(simplePDP);
		
		assertEquals("DENY", pdp.evaluateRequest(request, xacmlRequest)
				.getPdpDecision());
	}

	@Test
	public void testEvaluateRequestWithXacmlRequest() throws SecurityException,
			NoSuchFieldException, IllegalArgumentException,
			IllegalAccessException, AuditException, NoPolicyFoundException, PolicyProviderException {
		XacmlRequest xacmlRequest = new XacmlRequest();
		xacmlRequest.setIntermediarySubjectNPI("1285969170");
		xacmlRequest.setRecipientSubjectNPI("1568797520");
		xacmlRequest.setPurposeOfUse("TREATMENT");
		xacmlRequest.setPatientId("consent2share@outlook.com");
		when(
				requestGeneratorMock.generateRequest("1568797520",
						"1285969170", "TREATMENT", "consent2share@outlook.com"))
				.thenReturn(request);
		pdp.deployPolicies(simplePDP, policies, xacmlRequest, false);
		assertEquals(
				"PERMIT",
				pdp.evaluateRequest(simplePDP, request,
						new LinkedList<Evaluatable>(), xacmlRequest).getPdpDecision());
		pdp.undeployPolicy(simplePDP, policy);
		pdp.deployPolicies(simplePDP, policies_invalid, xacmlRequest, false);
		when(pdp.getSimplePDP()).thenReturn(simplePDP);
		assertEquals("DENY", pdp.evaluateRequest(xacmlRequest).getPdpDecision());
	}

	@Test
	public void testGetPolicies() throws NoPolicyFoundException, PolicyProviderException {
		List<Evaluatable> policies = new ArrayList<Evaluatable>();
		XacmlRequest xacmlRequest = new XacmlRequest();
		xacmlRequest.setPatientUniqueId("1");
		xacmlRequest.setRecipientSubjectNPI("");
		xacmlRequest.setIntermediarySubjectNPI("");
		xacmlRequest.setMessageId("");
		when(policyProvider.getPolicies(xacmlRequest)).thenReturn(policies);
		assertEquals(pdp.getPolicies(xacmlRequest), policies);
	}

	@Test
	public void testEvaluatePolicyForTrying_Given_Correct_Policy_Successds() {
		// Arrange
		PolicyDecisionPointImpl thePdp = new PolicyDecisionPointImpl(null, new RequestGenerator(), documentAccessorMock, documentXmlConverterMock, auditServiceMock);
		
		// Read policy file from resource
		final String policyFileUri = "xacmlPolicyForTrying.xml";
		String xacmlPolicy = ResourceFileReader
				.getStringFromResourceFile(policyFileUri);
		
		// Act
		XacmlResponse xacmlResponse =  thePdp.evaluatePolicyForTrying(xacmlPolicy, "TREATMENT").getXacmlResponse();
		
		// Assert
		assertEquals(xacmlResponse.getPdpDecision().toLowerCase(), "permit");
		assertEquals(xacmlResponse.getPdpObligation().size(), 11);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEvaluatePolicyForTrying_Given_Null_XacmlPolicy_Throws_Exception() {
		// Arrange
		String xacmlPolicy = null;
		
		// Act
		pdp.evaluatePolicyForTrying(xacmlPolicy, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEvaluatePolicyForTrying_Given_Empty_XacmlPolicy_Throws_Exception() {
		// Arrange
		String xacmlPolicy = " ";
		
		// Act
		pdp.evaluatePolicyForTrying(xacmlPolicy, null);
	}

}
