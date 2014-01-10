package gov.samhsa.acs.contexthandler;

import static org.junit.Assert.*;
import gov.samhsa.acs.common.dto.XacmlResponse;
import gov.samhsa.acs.contexthandler.PolicyDecisionPoint;
import gov.samhsa.acs.contexthandler.PolicyDecisionPointImpl;
import gov.samhsa.acs.contexthandler.PolicyDecisionPointImplData;
import gov.samhsa.acs.contexthandler.RequestGenerator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import org.herasaf.xacml.core.api.PDP;
import org.herasaf.xacml.core.context.RequestMarshaller;
import org.herasaf.xacml.core.context.impl.RequestType;
import org.herasaf.xacml.core.policy.Evaluatable;
import org.herasaf.xacml.core.policy.PolicyMarshaller;
import org.herasaf.xacml.core.simplePDP.SimplePDPFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PolicyDecisionPointDemoIT {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PolicyDecisionPointDemoIT.class);
	private final String policyFile = "src/test/resources/samplePolicy.xml";
	private final String requestFile = "src/test/resources/samplePolicyRequest.xml";
	private PDP simplePDP;
	private Evaluatable policy;
	private List<Evaluatable> policies;
	private RequestType request;
	private PolicyDecisionPoint pdp;
	@Mock
	private PolicyDecisionPointImplData dataMock;
	@Mock
	private RequestGenerator requestGeneratorMock;

	@Before
	public void setUp() throws Exception {
		simplePDP = SimplePDPFactory.getSimplePDP();
		pdp = new PolicyDecisionPointImpl(dataMock,requestGeneratorMock);

		try {
			InputStream is = new FileInputStream(policyFile);
			policy = PolicyMarshaller.unmarshal(is);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		InputStream requestis = null;
		try {
			requestis = new FileInputStream(requestFile);
			request = RequestMarshaller.unmarshal(requestis);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		policies = new LinkedList<Evaluatable>();
		policies.add(policy);
	}

	public void printPolicy(){
		try {
			InputStream in=new FileInputStream(policyFile);;
			String readLine;
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			System.out.println("");
			System.out.println("");
			System.out.println("****************************************");
			System.out.println("**Policy file being evaluated against**");
			System.out.println("****************************************");
			System.out.println("");
			while (((readLine = br.readLine()) != null)) {
				System.out.println(readLine);
			}
		} catch (IOException e) {
			LOGGER.warn("Error happens when printing policy file:", e);
		}
	}

	public void printRequest(){
		try {
			InputStream in=new FileInputStream(requestFile);;
			String readLine;
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			System.out.println("");
			System.out.println("");
			System.out.println("**************************************");
			System.out.println("**Request being sent to XACML engine**");
			System.out.println("**************************************");
			System.out.println("");
			while (((readLine = br.readLine()) != null)) {
				System.out.println(readLine);
			}
		} catch (IOException e) {
			LOGGER.warn("Error happens when printing request file:", e);
		}
	}

	@Test
	public void test() {
		XacmlResponse response = pdp.evaluateRequest(simplePDP,request,policies);
		printPolicy();
		printRequest();
		System.out.println("");
		System.out.println("");
		System.out.println("************");
		System.out.println("**Decision**");
		System.out.println("************");
		System.out.println("");
		System.out.println("Decision: " + response.getPdpDecision());
		for (String obligation : response.getPdpObligation())
			System.out.println("With Obligation: " + obligation);
	}

}
