package gov.samhsa.acs.pep.ws;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.pep.PolicyTrying;
import gov.samhsa.acs.pep.ws.PolicyTryingServiceImpl;
import gov.samhsa.acs.pep.ws.contract.TryPolicyPortType;
import gov.samhsa.acs.pep.ws.contract.TryPolicyService;

import java.io.IOException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.ws.Endpoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class PolicyTryingServiceImplTest {

	private URL wsdlURL;
	private String address;
	private QName serviceName;
	private Endpoint ep;
	
	private PolicyTrying policyTrying;

	@Before
	public void setUp() throws Exception {
		serviceName = new QName("http://acs.samhsa.gov/pep/ws/contract", "TryPolicyService");

		address = "http://localhost:12345/services/TryPolicyService";
		wsdlURL = new URL(address + "?wsdl");
		
		policyTrying = mock(PolicyTrying.class);

		ep = Endpoint.publish(address, new PolicyTryingServiceImpl(policyTrying));
	}

	@After
	public void tearDown() throws Exception {
		try {
			ep.stop();
		} catch (Throwable t) {
			System.out.println("Error thrown: " + t.getMessage());
		}
	}

	@Test
	public void testTryPolicy() {
		// Arrange
		final String c32Xml = "";
		final String xacmlPolicy = "";
		final String segmentedC32 = "Segmented C32";
		final String purposeOfUse = "TREAT";
		
		TryPolicyService service = new TryPolicyService(wsdlURL, serviceName);
		TryPolicyPortType port = service.getTryPolicyServicePort();
		
		try {
			when(policyTrying.tryPolicy(c32Xml, xacmlPolicy, purposeOfUse)).thenReturn(segmentedC32);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
		// Act
		String result = port.tryPolicy(c32Xml, xacmlPolicy, purposeOfUse);

		// Assert
		assertEquals(segmentedC32, result);
	}
}
