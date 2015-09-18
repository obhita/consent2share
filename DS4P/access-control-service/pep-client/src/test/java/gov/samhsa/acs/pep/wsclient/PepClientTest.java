package gov.samhsa.acs.pep.wsclient;

import static org.junit.Assert.assertEquals;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.pep.security.CredentialProviderImpl;
import gov.samhsa.ds4ppilot.contract.pep.PepPortType;
import gov.samhsa.ds4ppilot.contract.pep.PepService;
import gov.samhsa.ds4ppilot.schema.pep.DirectEmailSendRequest;
import gov.samhsa.ds4ppilot.schema.pep.DirectEmailSendResponse;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;

import java.net.URL;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Endpoint;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.apache.cxf.ws.security.trust.STSClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * The Class PepClientTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class PepClientTest {

	/** The ep. */
	protected static Endpoint ep;

	/** The address. */
	protected static String address;

	/** The Constant returnedValueOfPep. */
	private static final AdhocQueryResponse returnedValueOfPep = new AdhocQueryResponse();

	/** The marshaller. */
	SimpleMarshallerImpl marshaller;

	/** The req string. */
	String reqString;

	/** The pep port type impl. */
	@Spy
	PepPortTypeImpl pepPortTypeImpl;

	/** The credential provider impl. */
	@Mock
	CredentialProviderImpl credentialProviderImpl;

	/**
	 * Sets the up.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Before
	public void setUp() throws Exception {
		Resource resource = new ClassPathResource(
				"/jettyServerPortForTesing.properties");
		Properties props = PropertiesLoaderUtils.loadProperties(resource);
		String portNumber = props
				.getProperty("jettyServerPortForTesing.number");

		address = String.format("http://localhost:%s/Pep/services/PepService",
				portNumber);

		ep = Endpoint.publish(address, new PepPortTypeImpl());

		PepPortTypeImpl.returnedValueOfPep = returnedValueOfPep;
		MockitoAnnotations.initMocks(this);
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@After
	public void tearDown() throws Exception {
		try {
			ep.stop();
		} catch (Throwable t) {
			System.out.println("Error thrown: " + t.getMessage());
		}
	}

	/**
	 * Test registry stored query.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testRegistryStoredQuery() throws Exception {
		AdhocQueryResponse resp = pepPortTypeImpl
				.registryStoredQuery(new AdhocQueryRequest());

		assertEquals(resp.getRequestId(), "testId");
	}

	/**
	 * Test retrieve document set.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testRetrieveDocumentSet() throws Exception {
		RetrieveDocumentSetResponse resp = pepPortTypeImpl
				.retrieveDocumentSet(new RetrieveDocumentSetRequest());

		assertEquals(resp.getRegistryResponse().getRequestId(), "testId");
	}

	/**
	 * Test direct email send.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testDirectEmailSend() throws Exception {
		DirectEmailSendResponse resp = pepPortTypeImpl
				.directEmailSend(new DirectEmailSendRequest());

		assertEquals(resp.getPatientId(), "testId");
	}

	/**
	 * Creates the port.
	 *
	 * @return the pep port type
	 */
	private PepPortType createPort() {
		final URL WSDL_LOCATION = ClassLoader.getSystemResource("Pep.wsdl");
		final QName SERVICE = new QName(
				"http://www.samhsa.gov/ds4ppilot/contract/pep", "PepService");

		PepPortType port = new PepService(WSDL_LOCATION, SERVICE)
				.getXDSHTTPEndpoint();
		BindingProvider bp = (BindingProvider) port;

		STSClient stsClient = (STSClient) bp.getRequestContext().get(
				"ws-security.sts.client");

		stsClient.getProperties().put("ws-security.username",
				credentialProviderImpl.getUsername());

		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				address);
		return port;
	}

	/**
	 * Construct adhoc query.
	 *
	 * @return the adhoc query request
	 * @throws Exception
	 *             the exception
	 */
	private static AdhocQueryRequest constructAdhocQuery() throws Exception {
		SimpleMarshallerImpl marshaller = new SimpleMarshallerImpl();
		String reqString = " <ns3:AdhocQueryRequest xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\"         xmlns:ns2=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\"         xmlns=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\"       xmlns:ns3=\"urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0\"><ns3:ResponseOption returnType=\"LeafClass\" returnComposedObjects=\"true\"/><AdhocQuery id=\"urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d\"><Slot name=\"$XDSDocumentEntryPatientId\"><ValueList><Value>'d3bb3930-7241-11e3-b4f7-00155d3a2124^^^&amp;2.16.840.1.113883.4.357&amp;ISO'</Value></ValueList></Slot><Slot name=\"$XDSDocumentEntryStatus\"><ValueList><Value>('urn:oasis:names:tc:ebxml-regrep:StatusType:Approved')</Value></ValueList></Slot><Slot name=\"$XDSDocumentEntryFormatCode\"><ValueList><Value>'2.16.840.1.113883.10.20.1^^HITSP'</Value></ValueList></Slot></AdhocQuery></ns3:AdhocQueryRequest>";
		AdhocQueryRequest request = marshaller.unmarshalFromXml(
				AdhocQueryRequest.class, reqString);

		return request;
	}
}
