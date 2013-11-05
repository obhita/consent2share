package gov.samhsa.ds4ppilot.pep.contexthandler;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import gov.samhsa.ds4ppilot.pep.contexthandler.ContextHandler;
import gov.samhsa.ds4ppilot.pep.contexthandler.ContextHandlerImpl;
import gov.samhsa.ds4ppilot.pep.contexthandler.PolicyDecisionPoint;
import gov.samhsa.ds4ppilot.pep.contexthandler.RequestGenerator;
import gov.samhsa.ds4ppilot.pep.dto.XacmlRequest;
import gov.samhsa.ds4ppilot.pep.dto.XacmlResponse;

import java.util.Arrays;
import java.util.List;

import org.herasaf.xacml.core.context.impl.RequestType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class ContextHandlerImplTest {
	
	private ContextHandler contextHandler;
	
	@Mock
	private PolicyDecisionPoint policyDesicionPointMock;
	
	@Mock
	private RequestGenerator requestGeneratorMock;
	
	private XacmlRequest xacmlRequestValid;
	private XacmlRequest xacmlRequestInvalidByIntNPI;
	private XacmlRequest xacmlRequestInvalidByRecNPI;
	private XacmlRequest xacmlRequestInvalidByPurpose;
	private XacmlRequest xacmlRequestInvalidByResource;
	
	private RequestType requestValid;
	private RequestType requestInvalidByIntNPI;
	private RequestType requestInvalidByRecNPI;
	private RequestType requestInvalidByPurpose;
	private RequestType requestInvalidByResource;
	
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
		xacmlRequestValid.setIntermediarySubjectNPI(INTERMEDIARY_SUBJECT_NPI_VALID);
		xacmlRequestValid.setRecepientSubjectNPI(RECEPIENT_SUBJECT_NPI_VALID);
		xacmlRequestValid.setPurposeOfUse(PURPOSE_OF_USE_VALID);
		xacmlRequestValid.setResourceId(RESOURCE_ID_VALID);
		
		// invalid xacml requests
		xacmlRequestInvalidByIntNPI = clone(xacmlRequestValid);
		xacmlRequestInvalidByIntNPI.setIntermediarySubjectNPI(INVALID_INPUT);
		
		xacmlRequestInvalidByRecNPI = clone(xacmlRequestValid);
		xacmlRequestInvalidByRecNPI.setRecepientSubjectNPI(INVALID_INPUT);
		
		xacmlRequestInvalidByPurpose = clone(xacmlRequestValid);
		xacmlRequestInvalidByPurpose.setPurposeOfUse(INVALID_INPUT);
		
		xacmlRequestInvalidByResource = clone(xacmlRequestValid);
		xacmlRequestInvalidByResource.setResourceId(INVALID_INPUT);
		
		// permit xacml response
		xacmlResponseValid = new XacmlResponse();
		obligations = Arrays.asList(new String[]{"ETH", "PSY", "HIV"});
		xacmlResponseValid.setPdpDecision(PERMIT);
		xacmlResponseValid.setPdpObligation(obligations);
		
		// deny xacml response
		xacmlResponseInvalid = new XacmlResponse();
		xacmlResponseInvalid.setPdpDecision(DENY);
		
		// request types
		requestValid = new RequestType();
		requestInvalidByIntNPI = new RequestType();
		requestInvalidByRecNPI = new RequestType();
		requestInvalidByPurpose = new RequestType();
		requestInvalidByResource = new RequestType();

		contextHandler = new ContextHandlerImpl(policyDesicionPointMock, requestGeneratorMock);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link gov.samhsa.ds4ppilot.pep.contexthandler.ContextHandlerImpl#enforcePolicy(gov.samhsa.ds4ppilot.pep.dto.XacmlRequest)}.
	 */
	@Test
	public final void testEnforcePolicyXacmlRequest_Permit() {
		
		// Arrange
		when(requestGeneratorMock.generateRequest(RECEPIENT_SUBJECT_NPI_VALID, INTERMEDIARY_SUBJECT_NPI_VALID, PURPOSE_OF_USE_VALID, RESOURCE_ID_VALID)).thenReturn(requestValid);
		when(policyDesicionPointMock.evaluateRequest(requestValid)).thenReturn(xacmlResponseValid);

		// Act
		XacmlResponse resp = contextHandler.enforcePolicy(xacmlRequestValid);
		
		// Assert
		assertTrue(isXacmlResponsePermit(resp));
	}
	
	/**
	 * Test method for {@link gov.samhsa.ds4ppilot.pep.contexthandler.ContextHandlerImpl#enforcePolicy(gov.samhsa.ds4ppilot.pep.dto.XacmlRequest)}.
	 */
	@Test
	public final void testEnforcePolicyXacmlRequest_DenyByIntNPI() {
		
		// Arrange
		when(requestGeneratorMock.generateRequest(RECEPIENT_SUBJECT_NPI_VALID, INVALID_INPUT, PURPOSE_OF_USE_VALID, RESOURCE_ID_VALID)).thenReturn(requestInvalidByIntNPI);
		when(policyDesicionPointMock.evaluateRequest(requestInvalidByIntNPI)).thenReturn(xacmlResponseInvalid);

		// Act
		XacmlResponse resp = contextHandler.enforcePolicy(xacmlRequestInvalidByIntNPI);
		
		// Assert
		assertTrue(isXacmlResponseDeny(resp));
	}
	
	/**
	 * Test method for {@link gov.samhsa.ds4ppilot.pep.contexthandler.ContextHandlerImpl#enforcePolicy(gov.samhsa.ds4ppilot.pep.dto.XacmlRequest)}.
	 */
	@Test
	public final void testEnforcePolicyXacmlRequest_DenyByPurpose() {
		
		// Arrange
		when(requestGeneratorMock.generateRequest(RECEPIENT_SUBJECT_NPI_VALID, INTERMEDIARY_SUBJECT_NPI_VALID, INVALID_INPUT, RESOURCE_ID_VALID)).thenReturn(requestInvalidByPurpose);
		when(policyDesicionPointMock.evaluateRequest(requestInvalidByPurpose)).thenReturn(xacmlResponseInvalid);

		// Act
		XacmlResponse resp = contextHandler.enforcePolicy(xacmlRequestInvalidByPurpose);
		
		// Assert
		assertTrue(isXacmlResponseDeny(resp));
	}
	
	/**
	 * Test method for {@link gov.samhsa.ds4ppilot.pep.contexthandler.ContextHandlerImpl#enforcePolicy(gov.samhsa.ds4ppilot.pep.dto.XacmlRequest)}.
	 */
	@Test
	public final void testEnforcePolicyXacmlRequest_DenyByRecNPI() {
		
		// Arrange
		when(requestGeneratorMock.generateRequest(INVALID_INPUT, INTERMEDIARY_SUBJECT_NPI_VALID, PURPOSE_OF_USE_VALID, RESOURCE_ID_VALID)).thenReturn(requestInvalidByRecNPI);
		when(policyDesicionPointMock.evaluateRequest(requestInvalidByRecNPI)).thenReturn(xacmlResponseInvalid);

		// Act
		XacmlResponse resp = contextHandler.enforcePolicy(xacmlRequestInvalidByRecNPI);
		
		// Assert
		assertTrue(isXacmlResponseDeny(resp));
	}
	
	/**
	 * Test method for {@link gov.samhsa.ds4ppilot.pep.contexthandler.ContextHandlerImpl#enforcePolicy(gov.samhsa.ds4ppilot.pep.dto.XacmlRequest)}.
	 */
	@Test
	public final void testEnforcePolicyXacmlRequest_DenyByResource() {
		
		// Arrange
		when(requestGeneratorMock.generateRequest(RECEPIENT_SUBJECT_NPI_VALID, INTERMEDIARY_SUBJECT_NPI_VALID, PURPOSE_OF_USE_VALID, INVALID_INPUT)).thenReturn(requestInvalidByResource);
		when(policyDesicionPointMock.evaluateRequest(requestInvalidByResource)).thenReturn(xacmlResponseInvalid);

		// Act
		XacmlResponse resp = contextHandler.enforcePolicy(xacmlRequestInvalidByResource);
		
		// Assert
		assertTrue(isXacmlResponseDeny(resp));
	}
	
	private boolean isXacmlResponseDeny(XacmlResponse resp) {
		return resp.getPdpDecision().equals(DENY) &&
				resp.getPdpObligation() == null;
	}

	private boolean isXacmlResponsePermit(XacmlResponse resp) {
		return resp.getPdpDecision().equals(PERMIT) &&
				resp.getPdpObligation().containsAll(obligations);
	}	

	private XacmlRequest clone(XacmlRequest req)
	{
		XacmlRequest newReq = new XacmlRequest();
		newReq.setIntermediarySubjectNPI(req.getIntermediarySubjectNPI());
		newReq.setRecepientSubjectNPI(req.getRecepientSubjectNPI());
		newReq.setPurposeOfUse(req.getPurposeOfUse());
		newReq.setResourceId(req.getResourceId());
		return newReq;
		
	}

}
