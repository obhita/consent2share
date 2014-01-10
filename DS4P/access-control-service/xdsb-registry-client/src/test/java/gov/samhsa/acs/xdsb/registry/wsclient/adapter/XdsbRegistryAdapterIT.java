package gov.samhsa.acs.xdsb.registry.wsclient.adapter;

import static org.junit.Assert.assertTrue;
import gov.samhsa.acs.common.tool.DocumentXmlConverterImpl;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.xdsb.common.XdsbDocumentReference;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.acs.xdsb.registry.wsclient.XdsbRegistryWebServiceClient;
import gov.samhsa.acs.xdsb.registry.wsclient.adapter.AdhocQueryResponseFilter;
import gov.samhsa.acs.xdsb.registry.wsclient.adapter.XdsbRegistryAdapter;

import java.util.List;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class XdsbRegistryAdapterIT {
	// Logger
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// Constants
	private static final String PATIENT_ID = "PUI100010060001";
	private static final String HOME_COMMUNITY_ID = "2.16.840.1.113883.3.467";
	private static final String REPOSITORY_ID = "1.3.6.1.4.1.21367.2010.1.2.1040";

	// Endpoints by environment
	private static final String DEV_XDSB_REGISTRY_ENDPOINT = "http://obhidevacs001:9080/axis2/services/xdsregistryb";
	private static final String QA_XDSB_REGISTRY_ENDPOINT = "http://obhitaqaacs01:9080/axis2/services/xdsregistryb";
	private static final String DEMO_XDSB_REGISTRY_ENDPOINT = "http://obhitademoacs01:9080/axis2/services/xdsregistryb";

	// Document references by environments
	private static final String DEV_CLINICAL_DOCUMENT_ID = "41421263015.98411.41414.91230.401390172014139";
	private static final String DEV_XACML_DOCUMENT_ID = "2931513224.111050.43108.1114145.628015389614413";

	private static final String QA_CLINICAL_DOCUMENT_ID = "822104.131561.47149.915712.121310311514154155";
	private static final String QA_XACML_DOCUMENT_ID = "13812431219.815137.41525.116410.7895113107152013";

	private static final String DEMO_CLINICAL_DOCUMENT_ID = "1159100196.8727.4619.9589.55410001012119150";
	private static final String DEMO_XACML_DOCUMENT_ID = "12156561203.101298.41246.11843.2145892121061503";

	// Variables
	private static String endpointAddress;
	private static String clinicalDocumentId;
	private static String xacmlDocumentId;

	// System under test
	private static XdsbRegistryAdapter xdsbRegistryAdapter;

	@BeforeClass
	public static void setUp() throws Exception {
		endpointAddress = DEV_XDSB_REGISTRY_ENDPOINT;
		clinicalDocumentId = DEV_CLINICAL_DOCUMENT_ID;
		xacmlDocumentId = DEV_XACML_DOCUMENT_ID;

		xdsbRegistryAdapter = new XdsbRegistryAdapter(new XdsbRegistryWebServiceClient(
				endpointAddress), new AdhocQueryResponseFilter(new SimpleMarshallerImpl(), new DocumentXmlConverterImpl()), new SimpleMarshallerImpl(),
				new DocumentXmlConverterImpl());
	}

	// make sure you have a clinical document in your XDS.b endpoint with
	// documentId= clinicalDocumentId
	// repositoryId= REPOSITORY_ID
	// to pass this test
	@Test
	public void testRegistryStoredQuery_Clinical_Document() throws Exception,
			Throwable {
		// Arrange
		XdsbDocumentReference xdsbDocumentReference = new XdsbDocumentReference(
				clinicalDocumentId, REPOSITORY_ID);
		XdsbDocumentType xdsbDocumentType = XdsbDocumentType.CLINICAL_DOCUMENT;

		// Act
		AdhocQueryResponse adhocQueryResponse = xdsbRegistryAdapter
				.registryStoredQuery(PATIENT_ID, HOME_COMMUNITY_ID,
						xdsbDocumentType,false);
		List<XdsbDocumentReference> xdsbDocumentReferenceList = xdsbRegistryAdapter
				.extractXdsbDocumentReferenceList(adhocQueryResponse);
		logger.debug(xdsbDocumentType.toString());
		logger.debug(xdsbDocumentReferenceList.toString());

		// Assert
		assertTrue(xdsbDocumentReferenceList.contains(xdsbDocumentReference));
	}

	// make sure you have a consent policy document in your XDS.b endpoint with
	// documentId= xacmlDocumentId
	// repositoryId= REPOSITORY_ID
	// to pass this test
	@Test
	public void testRegistryStoredQuery_Privacy_Consent() throws Exception,
			Throwable {
		// Arrange
		XdsbDocumentReference xdsbDocumentReference = new XdsbDocumentReference(
				xacmlDocumentId, REPOSITORY_ID);
		XdsbDocumentType xdsbDocumentType = XdsbDocumentType.PRIVACY_CONSENT;

		// Act
		AdhocQueryResponse adhocQueryResponse = xdsbRegistryAdapter
				.registryStoredQuery(PATIENT_ID, HOME_COMMUNITY_ID,
						xdsbDocumentType,true);
		List<XdsbDocumentReference> xdsbDocumentReferenceList = xdsbRegistryAdapter
				.extractXdsbDocumentReferenceList(adhocQueryResponse);
		logger.debug(xdsbDocumentType.toString());
		logger.debug(xdsbDocumentReferenceList.toString());

		// Assert
		assertTrue(xdsbDocumentReferenceList.contains(xdsbDocumentReference));
	}
}
