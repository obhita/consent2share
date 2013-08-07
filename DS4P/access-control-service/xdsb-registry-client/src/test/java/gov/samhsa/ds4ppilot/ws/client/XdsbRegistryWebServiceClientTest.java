package gov.samhsa.ds4ppilot.ws.client;

import gov.samhsa.ds4p.xdsbregistry.DocumentRegistryService;
import ihe.iti.xds_b._2007.XDSRegistry;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Endpoint;

import junit.framework.Assert;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class XdsbRegistryWebServiceClientTest {

	protected static Endpoint ep;
	protected static String address;
	private static AdhocQueryResponse returnedValueOfRegistryStoredQuery = new AdhocQueryResponse();

	@BeforeClass
	public static void setUp() {
		try {
			address = "http://localhost:9000/services/xdsregistryb";
			ep = Endpoint.publish(address, new XdsbRegistryServiceImpl());

			XdsbRegistryServiceImpl.returnedValueOfRegistryStoredQuery = returnedValueOfRegistryStoredQuery;

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
		AdhocQueryRequest adhocQueryRequest = new AdhocQueryRequest();

		Object response = createPort().registryStoredQuery(adhocQueryRequest);
		validateResponseOfRetrieveDocumentSetRequest(response);
	}

	// Test if the SOAP client calling the stub web service correctly?

	@Test
	public void testWSClientSOAPCallWorks_retrieveDocumentSetRequest() {
		AdhocQueryRequest adhocQueryRequest = new AdhocQueryRequest();

		XdsbRegistryWebServiceClient wsc = new XdsbRegistryWebServiceClient(
				address);
		Object resp = wsc.registryStoredQuery(adhocQueryRequest);
		validateResponseOfRetrieveDocumentSetRequest(resp);
	}

	private void validateResponseOfRetrieveDocumentSetRequest(Object resp) {
		Assert.assertNotNull(resp);
	}

	private XDSRegistry createPort() {
		final URL WSDL_LOCATION = this.getClass().getClassLoader()
				.getResource("XDS.b_registry.net.wsdl");
		final QName SERVICE = new QName("http://samhsa.gov/ds4p/XDSbRegistry/",
				"DocumentRegistryService");

		XDSRegistry port = new DocumentRegistryService(WSDL_LOCATION, SERVICE)
				.getXDSRegistryHTTPEndpoint();

		BindingProvider bp = (BindingProvider) port;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				address);

		return port;
	}
}