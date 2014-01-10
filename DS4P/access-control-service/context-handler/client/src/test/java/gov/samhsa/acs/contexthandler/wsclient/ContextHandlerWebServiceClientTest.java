package gov.samhsa.acs.contexthandler.wsclient;

import gov.samhsa.acs.contexthandler.wsclient.ContextHandlerWebServiceClient;
import gov.va.ehtac.ds4p.ws.DS4PContextHandler;
import gov.va.ehtac.ds4p.ws.DS4PContextHandler_Service;
import gov.va.ehtac.ds4p.ws.EnforcePolicy;
import gov.va.ehtac.ds4p.ws.XacmlObligationsType;
import gov.va.ehtac.ds4p.ws.EnforcePolicyResponse.Return;
import gov.va.ehtac.ds4p.ws.XacmlResultType;
import gov.va.ehtac.ds4p.ws.XacmlStatusDetailType;
import gov.va.ehtac.ds4p.ws.XacmlStatusType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Endpoint;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContextHandlerWebServiceClientTest {

	protected static Endpoint ep;
	protected static String address;
	
	private static Return returnedValueOfEnforcePolicy;

	@BeforeClass
	public static void setUp() {
		try {
			address = "http://localhost:9000/services/DS4PContextHandler";
			ep = Endpoint.publish(address, new DS4PContextHandlerImpl());
			
			returnedValueOfEnforcePolicy = new Return ();
			returnedValueOfEnforcePolicy.setPdpDecision("Permit");
			returnedValueOfEnforcePolicy.setPurposeOfUse("TREAT");
			returnedValueOfEnforcePolicy.setHomeCommunityId("2.16.840.1.113883.3.467");
			returnedValueOfEnforcePolicy.setResourceId("");
			returnedValueOfEnforcePolicy.setResourceName("");
			returnedValueOfEnforcePolicy.setPdpStatus("");
			returnedValueOfEnforcePolicy.setPdpRequest("");
			returnedValueOfEnforcePolicy.setPdpResponse("");
			returnedValueOfEnforcePolicy.setRequestTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2008,10,1)));
			returnedValueOfEnforcePolicy.setResponseTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2008,10,1)));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
		final EnforcePolicy.Xspasubject xspasubject = new EnforcePolicy.Xspasubject();
		final EnforcePolicy.Xsparesource xsparesource = new EnforcePolicy.Xsparesource();		
		
		DS4PContextHandlerImpl.returnedValueOfEnforcePolicy = returnedValueOfEnforcePolicy;		
		Return resp = createPort().enforcePolicy(xspasubject, xsparesource);
		validateResponse(resp);
	}

	// Test if the SOAP client calling the stub web service correctly?
	@Test
	public void testWSClientSOAPCallWorks() {
		
		final EnforcePolicy.Xspasubject xspasubject = new EnforcePolicy.Xspasubject();
		final EnforcePolicy.Xsparesource xsparesource = new EnforcePolicy.Xsparesource();		
		
		DS4PContextHandlerImpl.returnedValueOfEnforcePolicy = returnedValueOfEnforcePolicy;		

		ContextHandlerWebServiceClient wsc = new ContextHandlerWebServiceClient(address);
		Return resp = wsc.enforcePolicy(xspasubject, xsparesource);
		validateResponse(resp);
	}

	private void validateResponse(Return resp) {
		System.out.println("resp.getPdpDecision(): " + resp.getPdpDecision());
		Assert.assertEquals("Permit", returnedValueOfEnforcePolicy.getPdpDecision(), resp.getPdpDecision());
	}

	private DS4PContextHandler createPort() {		
		final URL WSDL_LOCATION = ClassLoader.getSystemResource("DS4PContextHandler.wsdl");
		final QName SERVICE = new QName("http://ws.ds4p.ehtac.va.gov/",
				"DS4PContextHandler");

		DS4PContextHandler port = new DS4PContextHandler_Service(WSDL_LOCATION, SERVICE).getDS4PContextHandlerPort();
		BindingProvider bp = (BindingProvider) port;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				address);
		return port;
	}
}

