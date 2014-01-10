package gov.samhsa.acs.contexthandler;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.common.dto.XacmlRequest;
import gov.samhsa.acs.common.dto.XacmlResponse;
import gov.samhsa.acs.contexthandler.ContextHandler;
import gov.samhsa.acs.contexthandler.ContextHandlerImpl;
import gov.samhsa.acs.contexthandler.PolicyDecisionPoint;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ContextHandlerImplTest {

	private ContextHandler contextHandler;

	@Mock
	private PolicyDecisionPoint policyDesicionPointMock;

	private XacmlRequest xacmlRequestValid;
	private XacmlRequest xacmlRequestInvalidByIntNPI;
	private XacmlRequest xacmlRequestInvalidByRecNPI;
	private XacmlRequest xacmlRequestInvalidByPurpose;
	private XacmlRequest xacmlRequestInvalidByResource;

	private XacmlResponse xacmlResponseValid;
	private XacmlResponse xacmlResponseInvalid;

	private static final String INTERMEDIARY_SUBJECT_NPI_VALID = "1346575297";
	private static final String RECEPIENT_SUBJECT_NPI_VALID = "1083949036";
	private static final String PURPOSE_OF_USE_VALID = "TREAT";
	private static final String RESOURCE_ID_VALID = "consent2share@outlook.com";
	private static final String INVALID_INPUT = "1111111111";
	private static final String PERMIT = "PERMIT";
	private static final String DENY = "DENY";

	private List<String> obligations;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		// valid xacml request
		xacmlRequestValid = new XacmlRequest();
		xacmlRequestValid
				.setIntermediarySubjectNPI(INTERMEDIARY_SUBJECT_NPI_VALID);
		xacmlRequestValid.setRecepientSubjectNPI(RECEPIENT_SUBJECT_NPI_VALID);
		xacmlRequestValid.setPurposeOfUse(PURPOSE_OF_USE_VALID);
		xacmlRequestValid.setPatientId(RESOURCE_ID_VALID);
		xacmlRequestValid.setPatientUniqueId(RESOURCE_ID_VALID);

		// invalid xacml requests
		xacmlRequestInvalidByIntNPI = clone(xacmlRequestValid);
		xacmlRequestInvalidByIntNPI.setIntermediarySubjectNPI(INVALID_INPUT);

		xacmlRequestInvalidByRecNPI = clone(xacmlRequestValid);
		xacmlRequestInvalidByRecNPI.setRecepientSubjectNPI(INVALID_INPUT);

		xacmlRequestInvalidByPurpose = clone(xacmlRequestValid);
		xacmlRequestInvalidByPurpose.setPurposeOfUse(INVALID_INPUT);

		xacmlRequestInvalidByResource = clone(xacmlRequestValid);
		xacmlRequestInvalidByResource.setPatientId(INVALID_INPUT);
		xacmlRequestInvalidByResource.setPatientUniqueId(INVALID_INPUT);

		// permit xacml response
		xacmlResponseValid = new XacmlResponse();
		obligations = Arrays.asList(new String[] { "ETH", "PSY", "HIV" });
		xacmlResponseValid.setPdpDecision(PERMIT);
		xacmlResponseValid.setPdpObligation(obligations);

		// deny xacml response
		xacmlResponseInvalid = new XacmlResponse();
		xacmlResponseInvalid.setPdpDecision(DENY);

		contextHandler = new ContextHandlerImpl(policyDesicionPointMock);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link gov.samhsa.acs.contexthandler.ContextHandlerImpl#enforcePolicy(gov.samhsa.acs.common.dto.XacmlRequest)}
	 * .
	 */
	@Test
	public final void testEnforcePolicyXacmlRequest_Permit() {

		// Arrange
		when(policyDesicionPointMock.evaluateRequest(xacmlRequestValid))
				.thenReturn(xacmlResponseValid);

		// Act
		XacmlResponse resp = contextHandler.enforcePolicy(xacmlRequestValid);

		// Assert
		assertTrue(isXacmlResponsePermit(resp));
	}

	/**
	 * Test method for
	 * {@link gov.samhsa.acs.contexthandler.ContextHandlerImpl#enforcePolicy(gov.samhsa.acs.common.dto.XacmlRequest)}
	 * .
	 */
	@Test
	public final void testEnforcePolicyXacmlRequest_DenyByIntNPI() {

		// Arrange
		when(
				policyDesicionPointMock
						.evaluateRequest(xacmlRequestInvalidByIntNPI))
				.thenReturn(xacmlResponseInvalid);

		// Act
		XacmlResponse resp = contextHandler
				.enforcePolicy(xacmlRequestInvalidByIntNPI);

		// Assert
		assertTrue(isXacmlResponseDeny(resp));
	}

	/**
	 * Test method for
	 * {@link gov.samhsa.acs.contexthandler.ContextHandlerImpl#enforcePolicy(gov.samhsa.acs.common.dto.XacmlRequest)}
	 * .
	 */
	@Test
	public final void testEnforcePolicyXacmlRequest_DenyByPurpose() {

		// Arrange
		when(
				policyDesicionPointMock
						.evaluateRequest(xacmlRequestInvalidByPurpose))
				.thenReturn(xacmlResponseInvalid);

		// Act
		XacmlResponse resp = contextHandler
				.enforcePolicy(xacmlRequestInvalidByPurpose);

		// Assert
		assertTrue(isXacmlResponseDeny(resp));
	}

	/**
	 * Test method for
	 * {@link gov.samhsa.acs.contexthandler.ContextHandlerImpl#enforcePolicy(gov.samhsa.acs.common.dto.XacmlRequest)}
	 * .
	 */
	@Test
	public final void testEnforcePolicyXacmlRequest_DenyByRecNPI() {

		// Arrange
		when(
				policyDesicionPointMock
						.evaluateRequest(xacmlRequestInvalidByRecNPI))
				.thenReturn(xacmlResponseInvalid);

		// Act
		XacmlResponse resp = contextHandler
				.enforcePolicy(xacmlRequestInvalidByRecNPI);

		// Assert
		assertTrue(isXacmlResponseDeny(resp));
	}

	/**
	 * Test method for
	 * {@link gov.samhsa.acs.contexthandler.ContextHandlerImpl#enforcePolicy(gov.samhsa.acs.common.dto.XacmlRequest)}
	 * .
	 */
	@Test
	public final void testEnforcePolicyXacmlRequest_DenyByResource() {

		// Arrange
		when(
				policyDesicionPointMock
						.evaluateRequest(xacmlRequestInvalidByResource))
				.thenReturn(xacmlResponseInvalid);

		// Act
		XacmlResponse resp = contextHandler
				.enforcePolicy(xacmlRequestInvalidByResource);

		// Assert
		assertTrue(isXacmlResponseDeny(resp));
	}

	private boolean isXacmlResponseDeny(XacmlResponse resp) {
		return resp.getPdpDecision().equals(DENY)
				&& resp.getPdpObligation() == null;
	}

	private boolean isXacmlResponsePermit(XacmlResponse resp) {
		return resp.getPdpDecision().equals(PERMIT)
				&& resp.getPdpObligation().containsAll(obligations);
	}

	private XacmlRequest clone(XacmlRequest req) {
		XacmlRequest newReq = new XacmlRequest();
		newReq.setIntermediarySubjectNPI(req.getIntermediarySubjectNPI());
		newReq.setRecepientSubjectNPI(req.getRecepientSubjectNPI());
		newReq.setPurposeOfUse(req.getPurposeOfUse());
		newReq.setPatientId(req.getPatientId());
		newReq.setPatientUniqueId(req.getPatientUniqueId());
		return newReq;
	}
}
