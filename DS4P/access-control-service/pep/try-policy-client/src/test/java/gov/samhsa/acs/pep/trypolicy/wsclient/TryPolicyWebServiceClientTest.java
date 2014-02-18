package gov.samhsa.acs.pep.trypolicy.wsclient;

import gov.samhsa.acs.pep.ws.contract.TryPolicyPortType;
import gov.samhsa.acs.pep.ws.contract.TryPolicyService;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Endpoint;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TryPolicyWebServiceClientTest {

	protected static Endpoint ep;
	protected static String address;
	
	private static final String returnedValueOfTryPolicy = "Policy is tried";

	@BeforeClass
	public static void setUp() {
		address = "http://localhost:9000/services/TryPolicyService";
		ep = Endpoint.publish(address, new TryPolicyPortTypeImpl());
		TryPolicyPortTypeImpl.returnedValueOfTryPolicy = returnedValueOfTryPolicy;
	}

	@AfterClass
	public static void tearDown() {
		try {
			ep.stop();
		} catch (Throwable t) {
			System.out.println("Error thrown: " + t.getMessage());
		}
	}

	// Test if the stub web service activate properly
	@Test
	public void testStubWebServiceWorks() {
		final String c32Xml = "";
		final String xacmlPolicy = "";
		
		String resp = createPort().tryPolicy(c32Xml, xacmlPolicy);
		validateResponse(resp);
	}

	// Test if the SOAP client calling the stub web service correctly?
	@Test
	public void testWSClientSOAPCallWorks() {
		final String c32Xml = "";
		final String xacmlPolicy = "";

		TryPolicyWebServiceClient wsc = new TryPolicyWebServiceClient(address);
		String resp = wsc.tryPolicy(c32Xml, xacmlPolicy);
		validateResponse(resp);
	}

	private void validateResponse(String resp) {
		Assert.assertEquals("Try policy reutruend wrong", returnedValueOfTryPolicy, resp);
	}

	private TryPolicyPortType createPort() {
		final URL WSDL_LOCATION = ClassLoader
				.getSystemResource("TryPolicy.wsdl");
		final QName SERVICE = new QName("http://acs.samhsa.gov/pep/ws/contract", "TryPolicyService");

		TryPolicyPortType port = new TryPolicyService(WSDL_LOCATION, SERVICE).getTryPolicyServicePort();
		BindingProvider bp = (BindingProvider) port;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				address);
		return port;
	}
}
