package gov.samhsa.ds4ppilot.pep.ws;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.pep.PolicyEnforcementPoint;
import gov.samhsa.acs.pep.ws.PepPortTypeImpl;
import gov.samhsa.ds4ppilot.contract.pep.PepPortType;
import gov.samhsa.ds4ppilot.contract.pep.PepService;
import gov.samhsa.ds4ppilot.schema.pep.DirectEmailSendRequest;
import gov.samhsa.ds4ppilot.schema.pep.DirectEmailSendResponse;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponseType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PepPortTypeImplEndpointTest {

	private static final String TEST_REQUEST_ID = "TEST_REQUEST_ID";
	private URL wsdlURL;
	private String address;
	private QName serviceName;


	private Endpoint ep;

	@Mock
	private PolicyEnforcementPoint pep;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		serviceName = new QName("http://www.samhsa.gov/ds4ppilot/contract/pep",
				"PepService");

		address = "http://localhost:9000/services/PepService";
		wsdlURL = new URL(address + "?wsdl");

		ep = Endpoint.publish(address, new PepPortTypeImpl(pep));
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
	public void testDirectEmailSend() {
		// Arrange
		DirectEmailSendRequest req = new DirectEmailSendRequest();
		DirectEmailSendResponse resp = new DirectEmailSendResponse();
		resp.setReturn(TEST_REQUEST_ID);
		PepService service = new PepService(wsdlURL, serviceName);
		PepPortType port = service.getXDSHTTPEndpoint();
		when(pep.directEmailSend(isA(DirectEmailSendRequest.class)))
				.thenReturn(resp);

		// Act
		DirectEmailSendResponse response = port.directEmailSend(req);

		// Assert
		assertEquals(resp.getReturn(), response.getReturn());
	}

	@Test
	public void testRegistryStoredQuery() {
		// Arrange
		AdhocQueryRequest req = new AdhocQueryRequest();
		AdhocQueryResponse resp = new AdhocQueryResponse();
		resp.setRequestId(TEST_REQUEST_ID);
		PepService service = new PepService(wsdlURL, serviceName);
		PepPortType port = service.getXDSHTTPEndpoint();
		when(pep.registryStoredQuery(isA(AdhocQueryRequest.class))).thenReturn(
				resp);

		// Act
		AdhocQueryResponse response = port.registryStoredQuery(req);

		// Assert
		assertEquals(resp.getRequestId(), response.getRequestId());
	}

	@Test
	public void testRetrieveDocumentSet() {
		// Arrange
		RetrieveDocumentSetRequest req = new RetrieveDocumentSetRequest();
		RetrieveDocumentSetResponse resp = new RetrieveDocumentSetResponse();
		RegistryResponseType regResp = new RegistryResponseType();
		regResp.setRequestId(TEST_REQUEST_ID);
		resp.setRegistryResponse(regResp);
		PepService service = new PepService(wsdlURL, serviceName);
		PepPortType port = service.getXDSHTTPEndpoint();
		when(pep.retrieveDocumentSet(isA(RetrieveDocumentSetRequest.class))).thenReturn(resp);
		
		// Act
		RetrieveDocumentSetResponse response = port.retrieveDocumentSet(req);

		// Assert
		assertEquals(resp.getRegistryResponse().getRequestId(), response.getRegistryResponse().getRequestId());
	}
}
