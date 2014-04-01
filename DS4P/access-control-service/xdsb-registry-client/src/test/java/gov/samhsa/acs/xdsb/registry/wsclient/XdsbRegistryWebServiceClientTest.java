package gov.samhsa.acs.xdsb.registry.wsclient;

import static org.junit.Assert.*;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.mockito.Mockito.*;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.xdsb.registry.wsclient.XdsbRegistryWebServiceClient;
import gov.samhsa.ds4p.xdsbregistry.DocumentRegistryService;
import ihe.iti.xds_b._2007.XDSRegistry;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Endpoint;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.hl7.v3.Id;
import org.hl7.v3.MCCIIN000002UV01;
import org.hl7.v3.PRPAIN201301UV02;
import org.hl7.v3.PRPAIN201302UV02;
import org.hl7.v3.PRPAIN201304UV02;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class XdsbRegistryWebServiceClientTest {

	protected static Endpoint ep;
	protected static String address;
	private static AdhocQueryResponse returnedValueOfRegistryStoredQuery = new AdhocQueryResponse();
	private static FileReaderImpl fileReader;

	@BeforeClass
	public static void setUp() {
		fileReader = new FileReaderImpl();
		try {
			address = "http://localhost:12345/services/xdsregistryb";
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
	
	@Test
	public void testAddPatientRegistryRecord() throws Throwable{
		// Arrange
		String expectedResponse = fileReader.readFile("unitTestMCCI_IN000002UV01.xml");
		PRPAIN201301UV02 requestMock = mock(PRPAIN201301UV02.class);
		MCCIIN000002UV01 responseMock = setMCCIIN000002UV01();
		XdsbRegistryServiceImpl.returnedValueOfPatientRegistryRecordAdded = responseMock;
		XdsbRegistryWebServiceClient wsc = new XdsbRegistryWebServiceClient(
				address);
		
		// Act
		String actualResponse = wsc.addPatientRegistryRecord(requestMock);
		
		// Assert
		assertXMLEqual("", expectedResponse, actualResponse);
	}
	
	@Test
	public void testResolvePatientRegistryDuplicates() throws Throwable{
		// Arrange
		String expectedResponse = fileReader.readFile("unitTestMCCI_IN000002UV01.xml");
		PRPAIN201304UV02 requestMock = mock(PRPAIN201304UV02.class);
		MCCIIN000002UV01 responseMock = setMCCIIN000002UV01();
		XdsbRegistryServiceImpl.returnedValueOfPatientRegistryDuplicatesResolved = responseMock;
		XdsbRegistryWebServiceClient wsc = new XdsbRegistryWebServiceClient(
				address);
		
		// Act
		String actualResponse = wsc.resolvePatientRegistryDuplicates(requestMock);
		
		// Assert
		assertXMLEqual("", expectedResponse, actualResponse);
	}
	
	@Test
	public void testRevisePatientRegistryRecord() throws Throwable{
		// Arrange
		String expectedResponse = fileReader.readFile("unitTestMCCI_IN000002UV01.xml");
		PRPAIN201302UV02 requestMock = mock(PRPAIN201302UV02.class);
		MCCIIN000002UV01 responseMock = setMCCIIN000002UV01();
		XdsbRegistryServiceImpl.returnedValueOfPatientRegistryRecordRevised = responseMock;
		XdsbRegistryWebServiceClient wsc = new XdsbRegistryWebServiceClient(
				address);
		
		// Act
		String actualResponse = wsc.revisePatientRegistryRecord(requestMock);
		
		// Assert
		assertXMLEqual("", expectedResponse, actualResponse);
	}
	
	private MCCIIN000002UV01 setMCCIIN000002UV01() {
		MCCIIN000002UV01 responseMock = new MCCIIN000002UV01();
		Id idMock = new Id();
		idMock.setExtension("extensionMock");
		idMock.setRoot("rootMock");
		responseMock.setId(idMock);
		return responseMock;
	}

	private void validateResponseOfRetrieveDocumentSetRequest(Object resp) {
		assertNotNull(resp);
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