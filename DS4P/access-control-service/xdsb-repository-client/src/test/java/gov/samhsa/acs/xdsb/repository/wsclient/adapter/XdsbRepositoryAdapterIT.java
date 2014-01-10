package gov.samhsa.acs.xdsb.repository.wsclient.adapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import gov.samhsa.acs.common.tool.DocumentXmlConverterImpl;
import gov.samhsa.acs.common.tool.FileReader;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.acs.xdsb.common.XdsbErrorFactory;
import gov.samhsa.acs.xdsb.repository.wsclient.XDSRepositorybWebServiceClient;
import gov.samhsa.acs.xdsb.repository.wsclient.adapter.RetrieveDocumentSetResponseFilter;
import gov.samhsa.acs.xdsb.repository.wsclient.adapter.XdsbRepositoryAdapter;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class XdsbRepositoryAdapterIT {
	// Logger
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// Constants
	private static final String XDSB_SUCCESS_MESSAGE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns2:RegistryResponse status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\" xmlns:ns2=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" xmlns=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\"/>";
	private static final String HOME_COMMUNITY_ID = "2.16.840.1.113883.3.467";
	private static final String OPENEMPI_DOMAIN_ID = "2.16.840.1.113883.4.357";
	private static final String REPOSITORY_ID = "1.3.6.1.4.1.21367.2010.1.2.1040";
	private static final XdsbDocumentType XDSB_DOCUMENT_TYPE_CLINICAL_DOCUMENT = XdsbDocumentType.CLINICAL_DOCUMENT;
	private static final XdsbDocumentType XDSB_DOCUMENT_TYPE_PRIVACY_CONSENT = XdsbDocumentType.PRIVACY_CONSENT;

	// Endpoints by environment
	private static final String DEV_XDSB_REPO_ENDPOINT = "http://obhidevacs001:9080//axis2/services/xdsrepositoryb";
	private static final String QA_XDSB_REPO_ENDPOINT = "http://obhitaqaacs01:9080/axis2/services/xdsrepositoryb";
	private static final String DEMO_XDSB_REPO_ENDPOINT = "http://obhitademoacs01:9080/axis2/services/xdsrepositoryb";

	// Document references by environments
	private static final String DEV_CLINICAL_DOCUMENT_ID = "41421263015.98411.41414.91230.401390172014139";
	private static final String DEV_XACML_DOCUMENT_ID = "115131313411.1521214.42153.10531.01415253967874";

	private static final String QA_CLINICAL_DOCUMENT_ID = "822104.131561.47149.915712.121310311514154155";
	private static final String QA_XACML_DOCUMENT_ID = "13812431219.815137.41525.116410.7895113107152013";

	private static final String DEMO_CLINICAL_DOCUMENT_ID = "1159100196.8727.4619.9589.55410001012119150";
	private static final String DEMO_XACML_DOCUMENT_ID = "12156561203.101298.41246.11843.2145892121061503";

	// Variables
	private static String c32;
	private static String xacml;
	private static String clinicalDocumentId;
	private static String xacmlDocumentId;
	private static String endpointAddress;

	// Services
	private static FileReader fileReader;
	private static SimpleMarshaller marshaller;

	// System under test
	private static XdsbRepositoryAdapter xdsbRepositoryAdapter;

	@BeforeClass
	public static void setUp() throws Exception {
		fileReader = new FileReaderImpl();
		marshaller = new SimpleMarshallerImpl();
		c32 = fileReader.readFile("unitTestC32.xml");
		xacml = fileReader.readFile("xacml_policy.xml");

		// Set these to the values of the environment under test
		endpointAddress = DEV_XDSB_REPO_ENDPOINT;
		clinicalDocumentId = DEV_CLINICAL_DOCUMENT_ID;
		xacmlDocumentId = DEV_XACML_DOCUMENT_ID;

		xdsbRepositoryAdapter = new XdsbRepositoryAdapter(
				new XDSRepositorybWebServiceClient(endpointAddress),
				new SimpleMarshallerImpl(), new RetrieveDocumentSetResponseFilter(new DocumentXmlConverterImpl(), new XdsbErrorFactory()));
	}

	@Ignore
	@Test
	public void testProvideAndRegisterDocumentSetRequest_C32() throws Throwable {
		// Act
		RegistryResponse registryResponse = xdsbRepositoryAdapter
				.provideAndRegisterDocumentSet(c32, OPENEMPI_DOMAIN_ID,
						XDSB_DOCUMENT_TYPE_CLINICAL_DOCUMENT);
		String result = marshaller.marshall(registryResponse);
		logger.debug("testProvideAndRegisterDocumentSet_C32 Result:");
		logger.debug(result);

		// Assert
		assertEquals(XDSB_SUCCESS_MESSAGE, result);
	}

	@Ignore
	@Test
	public void testProvideAndRegisterDocumentSetRequest_Xacml_Consent()
			throws Throwable {

		// Act
		RegistryResponse registryResponse = xdsbRepositoryAdapter
				.provideAndRegisterDocumentSet(xacml, OPENEMPI_DOMAIN_ID,
						XDSB_DOCUMENT_TYPE_PRIVACY_CONSENT);
		String result = marshaller.marshall(registryResponse);
		logger.debug("testProvideAndRegisterDocumentSet_Xacml_Consent Result:");
		logger.debug(result);

		// Assert
		assertEquals(XDSB_SUCCESS_MESSAGE, result);
	}

	// make sure you have "unitTestC32.xml" in your XDS.b endpoint with
	// documentId= clinicalDocumentId
	// repositoryId= REPOSITORY_ID
	// to pass this test
	@Test
	public void testRetrieveDocumentSetRequest_C32() {
		// Act
		RetrieveDocumentSetResponse retrieveDocumentSetResponse = xdsbRepositoryAdapter
				.retrieveDocumentSet(clinicalDocumentId, REPOSITORY_ID);
		String result = new String(retrieveDocumentSetResponse
				.getDocumentResponse().get(0).getDocument());
		logger.debug(result);

		// Assert
		assertEquals(c32, result);
	}

	// make sure you have "xacml_policy.xml" in your XDS.b endpoint with
	// documentId= xacmlDocumentId
	// repositoryId= REPOSITORY_ID
	// to pass this test
	@Test
	public void testRetrieveDocumentSetRequest_Xacml_Consent() {
		// Act
		RetrieveDocumentSetResponse retrieveDocumentSetResponse = xdsbRepositoryAdapter
				.retrieveDocumentSet(xacmlDocumentId, REPOSITORY_ID);
		String result = new String(retrieveDocumentSetResponse
				.getDocumentResponse().get(0).getDocument());
		logger.debug(result);

		// Assert
		assertEquals(xacml, result);
	}
}
