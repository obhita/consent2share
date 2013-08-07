package gov.samhsa.schemas.client;

import gov.samhsa.ds4ppilot.contract.orchestrator.XDSDocumentService;
import gov.samhsa.ds4ppilot.contract.orchestrator.XDSDocumentServicePortType;
import gov.samhsa.ds4ppilot.schema.orchestrator.SaveDocumentSetToXdsRepositoryRequest;
import gov.samhsa.ds4ppilot.schema.orchestrator.SaveDocumentSetToXdsRepositoryResponse;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Endpoint;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class XdsDocumentWebServiceClientTest {

	protected static Endpoint ep;
	protected static String address;

	private static final SaveDocumentSetToXdsRepositoryResponse returnedValueOfSaveDocumentSetToXdsRepository = new SaveDocumentSetToXdsRepositoryResponse();

	@BeforeClass
	public static void setUp() {
		address = "http://localhost:9000/services/XDSDocumentService";
		ep = Endpoint.publish(address, new XDSDocumentServicePortTypeImpl());
		returnedValueOfSaveDocumentSetToXdsRepository.setReturn(true);
		XDSDocumentServicePortTypeImpl.returnedValueOfSaveDocumentSetToXdsRepository = returnedValueOfSaveDocumentSetToXdsRepository;
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
		final String document = "";
		SaveDocumentSetToXdsRepositoryRequest request = new SaveDocumentSetToXdsRepositoryRequest();
		request.setDocumentSet(document);
		SaveDocumentSetToXdsRepositoryResponse resp = createPort().saveDocumentSetToXdsRepository(request);
		validateResponse(resp.isReturn());
	}

	// Test if the SOAP client calling the stub web service correctly?
	@Test
	public void testWSClientSOAPCallWorks() {
		final String document = "";
		XdsDocumentWebServiceClient wsc = new XdsDocumentWebServiceClient(
				address);
		boolean resp = wsc.saveDocumentSetToXdsRepository(document);
		validateResponse(resp);
	}

	private void validateResponse(boolean resp) {
		Assert.assertEquals("Returned Response wrong", returnedValueOfSaveDocumentSetToXdsRepository.isReturn(), resp);
	}

	private XDSDocumentServicePortType createPort() {
		final URL WSDL_LOCATION = ClassLoader
				.getSystemResource("XdsDocumentService.wsdl");
		final QName SERVICE = new QName(
				"http://www.samhsa.gov/ds4ppilot/contract/orchestrator",
				"XDSDocumentService");

		XDSDocumentServicePortType port = new XDSDocumentService(WSDL_LOCATION, SERVICE).getXDSDocumentServicePort();
		BindingProvider bp = (BindingProvider) port;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				address);
		return port;
	}
}
