package gov.samhsa.consent2share.accesscontrolservice.pep.xdsb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.accesscontrolservice.common.tool.DocumentXmlConverter;
import gov.samhsa.consent2share.accesscontrolservice.common.tool.DocumentXmlConverterImpl;
import gov.samhsa.consent2share.accesscontrolservice.common.tool.FileReader;
import gov.samhsa.consent2share.accesscontrolservice.common.tool.FileReaderImpl;
import gov.samhsa.consent2share.accesscontrolservice.common.tool.SimpleMarshaller;
import gov.samhsa.consent2share.accesscontrolservice.common.tool.SimpleMarshallerImpl;
import gov.samhsa.ds4ppilot.pep.xdsbregistry.XdsbRegistry;

import java.util.List;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.AdhocQueryType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.SlotType1;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.Document;

public class XdsbRegistryAdapterTest {
	// Constants
	private static final String PATIENT_ID = "PATIENT_ID";
	private static final String HOME_COMMUNITY_ID = "HOME_COMMUNITY_ID";
	private static final XdsbDocumentType XDSB_DOCUMENT_TYPE_CLINICAL_DOCUMENT = XdsbDocumentType.CLINICAL_DOCUMENT;
	private static final XdsbDocumentType XDSB_DOCUMENT_TYPE_PRIVACY_CONSENT = XdsbDocumentType.PRIVACY_CONSENT;

	// Mocks
	@Mock
	private XdsbRegistry xdsbRegistryMock;
	@Mock
	private SimpleMarshaller marshallerMock;
	@Mock
	private DocumentXmlConverter documentXmlConverterMock;

	// Helpers
	private FileReader fileReader;
	private SimpleMarshaller marshaller;
	private DocumentXmlConverter documentXmlConverter;

	// System under test
	private XdsbRegistryAdapter xdsbRegistryAdapterSpy;

	@Before
	public void setUp() throws Exception {
		fileReader = new FileReaderImpl();
		marshaller = new SimpleMarshallerImpl();
		documentXmlConverter = new DocumentXmlConverterImpl();

		MockitoAnnotations.initMocks(this);
		XdsbRegistryAdapter xdsbRegistryAdapter = new XdsbRegistryAdapter(
				xdsbRegistryMock, marshallerMock, documentXmlConverterMock);
		xdsbRegistryAdapterSpy = spy(xdsbRegistryAdapter);
	}

	@Test
	public void testRegistryStoredQuery_Given_AdhocQueryRequest() {
		// Arrange
		AdhocQueryRequest adhocQueryRequest = new AdhocQueryRequest();
		AdhocQueryResponse adhocQueryResponse = new AdhocQueryResponse();
		when(xdsbRegistryMock.registryStoredQuery(adhocQueryRequest))
				.thenReturn(adhocQueryResponse);

		// Act
		AdhocQueryResponse actualResponse = xdsbRegistryAdapterSpy
				.registryStoredQuery(adhocQueryRequest);

		// Assert
		assertEquals(adhocQueryResponse, actualResponse);
	}

	@Test
	public void testRegistryStoredQuery_Given_PatientId_HomeCommunityId_XdsbDocumentType_ClinicalDocument()
			throws Exception, Throwable {
		// Arrange
		AdhocQueryRequest adhocQueryRequest = new AdhocQueryRequest();
		AdhocQueryResponse adhocQueryResponse = new AdhocQueryResponse();
		when(
				xdsbRegistryAdapterSpy.createRegistryStoredQueryByPatientId(
						PATIENT_ID, HOME_COMMUNITY_ID,
						XDSB_DOCUMENT_TYPE_CLINICAL_DOCUMENT)).thenReturn(
				adhocQueryRequest);
		when(xdsbRegistryMock.registryStoredQuery(adhocQueryRequest))
				.thenReturn(adhocQueryResponse);

		// Act
		AdhocQueryResponse actualResponse = xdsbRegistryAdapterSpy
				.registryStoredQuery(PATIENT_ID, HOME_COMMUNITY_ID,
						XDSB_DOCUMENT_TYPE_CLINICAL_DOCUMENT);

		// Assert
		assertEquals(adhocQueryResponse, actualResponse);
	}

	@Test
	public void testRegistryStoredQuery_Given_PatientId_HomeCommunityId_XdsbDocumentType_PrivacyConsent()
			throws Exception, Throwable {
		// Arrange
		AdhocQueryRequest adhocQueryRequest = new AdhocQueryRequest();
		AdhocQueryResponse adhocQueryResponse = new AdhocQueryResponse();
		when(
				xdsbRegistryAdapterSpy.createRegistryStoredQueryByPatientId(
						PATIENT_ID, HOME_COMMUNITY_ID,
						XDSB_DOCUMENT_TYPE_PRIVACY_CONSENT)).thenReturn(
				adhocQueryRequest);
		when(xdsbRegistryMock.registryStoredQuery(adhocQueryRequest))
				.thenReturn(adhocQueryResponse);

		// Act
		AdhocQueryResponse actualResponse = xdsbRegistryAdapterSpy
				.registryStoredQuery(PATIENT_ID, HOME_COMMUNITY_ID,
						XDSB_DOCUMENT_TYPE_PRIVACY_CONSENT);

		// Assert
		assertEquals(adhocQueryResponse, actualResponse);
	}

	@Test
	public void testExtractXdsbDocumentReferenceList_Given_AdhocQueryResponse_ClinicalDocument()
			throws Exception, Throwable {
		// Arrange
		XdsbDocumentReference xdsbDocumentReference1 = new XdsbDocumentReference(
				"41421263015.98411.41414.91230.401390172014139",
				"1.3.6.1.4.1.21367.2010.1.2.1040");
		XdsbDocumentReference xdsbDocumentReference2 = new XdsbDocumentReference(
				"1513150391310.11184.4632.11139.05080551281557",
				"1.3.6.1.4.1.21367.2010.1.2.1040");
		String adhocQueryResponseString = fileReader
				.readFile("adhocQueryResponseClinicalDocument.xml");
		AdhocQueryResponse adhocQueryResponse = marshaller.unmarshallFromXml(
				AdhocQueryResponse.class, adhocQueryResponseString);
		when(marshallerMock.marshall(adhocQueryResponse)).thenReturn(
				adhocQueryResponseString);
		Document doc = documentXmlConverter
				.loadDocument(adhocQueryResponseString);
		when(documentXmlConverterMock.loadDocument(adhocQueryResponseString))
				.thenReturn(doc);

		// Act
		List<XdsbDocumentReference> list = xdsbRegistryAdapterSpy
				.extractXdsbDocumentReferenceList(adhocQueryResponse,
						XdsbDocumentType.CLINICAL_DOCUMENT);

		// Assert
		assertEquals(2, list.size());
		assertTrue(list.contains(xdsbDocumentReference1));
		assertTrue(list.contains(xdsbDocumentReference2));
	}

	@Test
	public void testExtractXdsbDocumentReferenceList_Given_AdhocQueryResponse_PrivacyConsent()
			throws Exception, Throwable {
		// Arrange
		XdsbDocumentReference xdsbDocumentReference1 = new XdsbDocumentReference(
				"2931513224.111050.43108.1114145.628015389614413",
				"1.3.6.1.4.1.21367.2010.1.2.1040");
		XdsbDocumentReference xdsbDocumentReference2 = new XdsbDocumentReference(
				"12132124715.97915.410413.87115.61142312711102135",
				"1.3.6.1.4.1.21367.2010.1.2.1040");
		String adhocQueryResponseString = fileReader
				.readFile("adhocQueryResponsePrivacyConsent.xml");
		AdhocQueryResponse adhocQueryResponse = marshaller.unmarshallFromXml(
				AdhocQueryResponse.class, adhocQueryResponseString);
		when(marshallerMock.marshall(adhocQueryResponse)).thenReturn(
				adhocQueryResponseString);
		Document doc = documentXmlConverter
				.loadDocument(adhocQueryResponseString);
		when(documentXmlConverterMock.loadDocument(adhocQueryResponseString))
				.thenReturn(doc);

		// Act
		List<XdsbDocumentReference> list = xdsbRegistryAdapterSpy
				.extractXdsbDocumentReferenceList(adhocQueryResponse,
						XdsbDocumentType.PRIVACY_CONSENT);

		// Assert
		assertEquals(2, list.size());
		assertTrue(list.contains(xdsbDocumentReference1));
		assertTrue(list.contains(xdsbDocumentReference2));
	}

	@Test
	public void testAddFormatCode_Given_AdhocQueryType_XdsbDocumentType_ClinicalDocument() {
		// Arrange
		String actualValue = null;
		String expectedValue = XdsbRegistryAdapter.CLINICAL_DOCUMENT_FORMAT_CODE;
		AdhocQueryType adhocQueryType = new AdhocQueryType();

		// Act
		xdsbRegistryAdapterSpy.addFormatCode(adhocQueryType,
				XDSB_DOCUMENT_TYPE_CLINICAL_DOCUMENT);
		List<SlotType1> slotList = adhocQueryType.getSlot();
		for (SlotType1 slot : slotList) {
			List<String> values = slot.getValueList().getValue();
			for (String value : values) {
				actualValue = value;
			}
		}

		// Assert
		assertNotNull(actualValue);
		assertEquals(expectedValue, actualValue);
	}

	@Test
	public void testAddFormatCode_Given_AdhocQueryType_XdsbDocumentType_PrivacyConsent() {
		// Arrange
		String actualValue = null;
		String expectedValue = XdsbRegistryAdapter.PRIVACY_CONSENT_FORMAT_CODE;
		AdhocQueryType adhocQueryType = new AdhocQueryType();

		// Act
		xdsbRegistryAdapterSpy.addFormatCode(adhocQueryType,
				XDSB_DOCUMENT_TYPE_PRIVACY_CONSENT);
		List<SlotType1> slotList = adhocQueryType.getSlot();
		for (SlotType1 slot : slotList) {
			List<String> values = slot.getValueList().getValue();
			for (String value : values) {
				actualValue = value;
			}
		}

		// Assert
		assertNotNull(actualValue);
		assertEquals(expectedValue, actualValue);
	}
}
