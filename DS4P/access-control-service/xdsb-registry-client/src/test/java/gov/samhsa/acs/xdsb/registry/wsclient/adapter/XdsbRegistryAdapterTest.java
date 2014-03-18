package gov.samhsa.acs.xdsb.registry.wsclient.adapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import gov.samhsa.acs.common.tool.DocumentAccessor;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;
import gov.samhsa.acs.common.tool.DocumentXmlConverterImpl;
import gov.samhsa.acs.common.tool.FileReader;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.xdsb.common.XdsbDocumentReference;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.acs.xdsb.registry.wsclient.XdsbRegistryWebServiceClient;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBException;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.AdhocQueryType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.SlotType1;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XdsbRegistryAdapterTest {
	// Constants
	private static final String PATIENT_ID = "PATIENT_ID";
	private static final String DOMAIN_ID = "DOMAIN_ID";
	private String patientUniqueId;
	private static final XdsbDocumentType XDSB_DOCUMENT_TYPE_CLINICAL_DOCUMENT = XdsbDocumentType.CLINICAL_DOCUMENT;
	private static final XdsbDocumentType XDSB_DOCUMENT_TYPE_PRIVACY_CONSENT = XdsbDocumentType.PRIVACY_CONSENT;

	// Mocks
	@Mock
	private XdsbRegistryWebServiceClient xdsbRegistryMock;
	@Mock
	private SimpleMarshaller marshallerMock;
	@Mock
	private DocumentXmlConverter documentXmlConverterMock;
	@Mock
	private AdhocQueryResponseFilter responseFilterMock;
	@Mock
	private DocumentAccessor documentAccessorMock;

	// Helpers
	private FileReader fileReader;
	private SimpleMarshaller marshaller;
	private DocumentXmlConverter documentXmlConverter;

	// System under test
	private XdsbRegistryAdapter xdsbRegistryAdapterSpy;

	@Before
	public void setUp() throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append("'");
		builder.append(PATIENT_ID);
		builder.append("^^^&");
		builder.append(DOMAIN_ID);
		builder.append("&ISO'");
		this.patientUniqueId = builder.toString();

		fileReader = new FileReaderImpl();
		marshaller = new SimpleMarshallerImpl();
		documentXmlConverter = new DocumentXmlConverterImpl();

		MockitoAnnotations.initMocks(this);
		XdsbRegistryAdapter xdsbRegistryAdapter = new XdsbRegistryAdapter(
				xdsbRegistryMock, responseFilterMock, marshallerMock,
				documentXmlConverterMock, documentAccessorMock);
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
						patientUniqueId, XDSB_DOCUMENT_TYPE_CLINICAL_DOCUMENT,
						false)).thenReturn(adhocQueryRequest);
		when(xdsbRegistryMock.registryStoredQuery(adhocQueryRequest))
				.thenReturn(adhocQueryResponse);

		// Act
		AdhocQueryResponse actualResponse = xdsbRegistryAdapterSpy
				.registryStoredQuery(patientUniqueId, null,
						XDSB_DOCUMENT_TYPE_CLINICAL_DOCUMENT, false);

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
						patientUniqueId, XDSB_DOCUMENT_TYPE_PRIVACY_CONSENT,
						true)).thenReturn(adhocQueryRequest);
		when(xdsbRegistryMock.registryStoredQuery(adhocQueryRequest))
				.thenReturn(adhocQueryResponse);

		// Act
		AdhocQueryResponse actualResponse = xdsbRegistryAdapterSpy
				.registryStoredQuery(patientUniqueId, null,
						XDSB_DOCUMENT_TYPE_PRIVACY_CONSENT, true);

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
				.extractXdsbDocumentReferenceList(adhocQueryResponse);

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
				.extractXdsbDocumentReferenceList(adhocQueryResponse);

		// Assert
		assertEquals(2, list.size());
		assertTrue(list.contains(xdsbDocumentReference1));
		assertTrue(list.contains(xdsbDocumentReference2));
	}

	@Test
	public void testAddFormatCode_Given_AdhocQueryType_XdsbDocumentType_ClinicalDocument() {
		// Arrange
		String actualValue = null;
		String expectedValue = XdsbRegistryAdapter.FORMAT_CODE_CLINICAL_DOCUMENT;
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
		String expectedValue = XdsbRegistryAdapter.FORMAT_CODE_PRIVACY_CONSENT;
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

	@Test
	public void testExtractXdsbDocumentReferenceListAsRetrieveDocumentSetRequest()
			throws Exception, Throwable {
		// Arrange
		AdhocQueryResponse responseMock = mock(AdhocQueryResponse.class);
		String marshalledXmlStringMock = "marshalledXmlStringMock";
		XdsbDocumentReference ref1 = new XdsbDocumentReference(
				"41421263015.98411.41414.91230.401390172014139",
				"1.3.6.1.4.1.21367.2010.1.2.1040");
		XdsbDocumentReference ref2 = new XdsbDocumentReference(
				"1513150391310.11184.4632.11139.05080551281557",
				"1.3.6.1.4.1.21367.2010.1.2.1040");
		when(marshallerMock.marshall(responseMock)).thenReturn(
				marshalledXmlStringMock);
		when(documentXmlConverterMock.loadDocument(marshalledXmlStringMock))
				.thenReturn(
						documentXmlConverter.loadDocument(fileReader
								.readFile("adhocQueryResponseClinicalDocument.xml")));

		// Act
		RetrieveDocumentSetRequest actualResponse = xdsbRegistryAdapterSpy
				.extractXdsbDocumentReferenceListAsRetrieveDocumentSetRequest(responseMock);

		// Assert
		assertTrue(actualResponse.getDocumentRequest().contains(ref1));
		assertTrue(actualResponse.getDocumentRequest().contains(ref2));
	}

	@Test
	public void testGetPatientUniqueId() {
		// Arrange
		String patientIdMock = "patientIdMock";
		String domainIdMock = "domainIdMock";
		String expectedResponse = "'patientIdMock^^^&domainIdMock&ISO'";

		// Act
		String actualResponse = xdsbRegistryAdapterSpy.getPatientUniqueId(
				patientIdMock, domainIdMock);

		// Assert
		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void testExtractFormatCode() throws JAXBException, IOException {
		// Arrange
		String expectedResponse = "'2.16.840.1.113883.10.20.1^^HITSP'";
		AdhocQueryRequest requestMock = marshaller.unmarshallFromXml(
				AdhocQueryRequest.class,
				fileReader.readFile("unitTestAdhocQueryRequest.xml"));

		// Act
		String actualResponse = xdsbRegistryAdapterSpy
				.extractFormatCode(requestMock);

		// Assert
		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void testExtractPatientId() throws JAXBException, IOException {
		// Arrange
		String expectedResponse = "'1c5c59f0-5788-11e3-84b3-00155d3a2124^^^&2.16.840.1.113883.4.357&ISO'";
		AdhocQueryRequest requestMock = marshaller.unmarshallFromXml(
				AdhocQueryRequest.class,
				fileReader.readFile("unitTestAdhocQueryRequest.xml"));

		// Act
		String actualResponse = xdsbRegistryAdapterSpy
				.extractPatientId(requestMock);

		// Assert
		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void testExtractResponseOptionReturnType() throws JAXBException,
			IOException {
		// Arrange
		String expectedResponse = "LeafClass";
		AdhocQueryRequest requestMock = marshaller.unmarshallFromXml(
				AdhocQueryRequest.class,
				fileReader.readFile("unitTestAdhocQueryRequest.xml"));

		// Act
		String actualResponse = xdsbRegistryAdapterSpy
				.extractResponseOptionReturnType(requestMock);

		// Assert
		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void testExtractDocumentEntryStatus() throws JAXBException,
			IOException {
		// Arrange
		String expectedResponse = "('urn:oasis:names:tc:ebxml-regrep:StatusType:Approved')";
		AdhocQueryRequest requestMock = marshaller.unmarshallFromXml(
				AdhocQueryRequest.class,
				fileReader.readFile("unitTestAdhocQueryRequest.xml"));

		// Act
		String actualResponse = xdsbRegistryAdapterSpy
				.extractDocumentEntryStatus(requestMock);

		// Assert
		assertEquals(expectedResponse, actualResponse);
	}

	@Test
	public void testRegistryStoredQuery() throws Exception, Throwable {
		// Arrange
		String patientIdMock = "patientIdMock";
		String domainIdMock = "domainIdMock";
		String authorIdMock = "authorIdMock";
		XdsbDocumentType xdsbDocumentTypeMock = XdsbDocumentType.CLINICAL_DOCUMENT;
		boolean serviceTimeAwareMock = false;
		AdhocQueryResponse responseMock = new AdhocQueryResponse();
		AdhocQueryResponse filteredResponseMock = new AdhocQueryResponse();
		when(xdsbRegistryMock.registryStoredQuery(isA(AdhocQueryRequest.class)))
				.thenReturn(responseMock);
		when(responseFilterMock.filterByAuthor(responseMock, authorIdMock))
				.thenReturn(filteredResponseMock);

		// Act
		AdhocQueryResponse actualResponse = xdsbRegistryAdapterSpy
				.registryStoredQuery(patientIdMock, domainIdMock, authorIdMock,
						xdsbDocumentTypeMock, serviceTimeAwareMock);

		// Assert
		assertEquals(filteredResponseMock, actualResponse);
	}

	@Test
	public void testFindDeprecatedDocumentUniqueIds() throws Throwable {
		// Arrange
		String submissionSetPatientIdMock = "submissionSetPatientIdMock";
		String submissionSetAuthorPersonMock = "submissionSetAuthorPersonMock";
		AdhocQueryRequest findSubmissionSetsRequestMock = mock(AdhocQueryRequest.class);
		AdhocQueryResponse findSubmissionSetsResponseMock = mock(AdhocQueryResponse.class);
		AdhocQueryRequest getSubmissionSetAndContentsRequestMock = mock(AdhocQueryRequest.class);
		AdhocQueryResponse getSubmissionSetAndContentsResponseMock = mock(AdhocQueryResponse.class);
		List<String> extractSubmissionSetUniqueIdsMock = new LinkedList<String>();
		String extractSubmissionSetUniqueIdMock = "extractSubmissionSetUniqueIdMock";
		extractSubmissionSetUniqueIdsMock.add(extractSubmissionSetUniqueIdMock);
		String deprecatedDocumentUniqueIdMock = "deprecatedDocumentUniqueIdMock";
		when(
				xdsbRegistryAdapterSpy.createFindSubmissionSetsRequest(
						submissionSetPatientIdMock,
						submissionSetAuthorPersonMock)).thenReturn(
				findSubmissionSetsRequestMock);
		when(
				xdsbRegistryMock
						.registryStoredQuery(findSubmissionSetsRequestMock))
				.thenReturn(findSubmissionSetsResponseMock);
		doReturn(extractSubmissionSetUniqueIdsMock)
				.when(xdsbRegistryAdapterSpy).extractSubmissionSetUniqueIds(
						findSubmissionSetsResponseMock);
		when(
				xdsbRegistryAdapterSpy
						.createGetSubmissionSetAndContentsRequest(extractSubmissionSetUniqueIdMock))
				.thenReturn(getSubmissionSetAndContentsRequestMock);
		when(
				xdsbRegistryMock
						.registryStoredQuery(getSubmissionSetAndContentsRequestMock))
				.thenReturn(getSubmissionSetAndContentsResponseMock);
		doReturn(deprecatedDocumentUniqueIdMock).when(xdsbRegistryAdapterSpy)
				.extractDeprecatedDocumentUniqueId(
						getSubmissionSetAndContentsResponseMock);

		// Act
		List<String> result = xdsbRegistryAdapterSpy
				.findDeprecatedDocumentUniqueIds(submissionSetPatientIdMock,
						submissionSetAuthorPersonMock);

		// Assert
		assertTrue(result.contains(deprecatedDocumentUniqueIdMock));
	}

	@Test
	public void testFindSubmissionSets() throws JAXBException {
		// Arrange
		String submissionSetPatientIdMock = "submissionSetPatientIdMock";
		String submissionSetAuthorPersonMock = "submissionSetAuthorPersonMock";
		AdhocQueryRequest findSubmissionSetsRequestMock = mock(AdhocQueryRequest.class);
		AdhocQueryResponse findSubmissionSetsResponseMock = mock(AdhocQueryResponse.class);
		when(
				xdsbRegistryAdapterSpy.createFindSubmissionSetsRequest(
						submissionSetPatientIdMock,
						submissionSetAuthorPersonMock)).thenReturn(
				findSubmissionSetsRequestMock);
		when(
				xdsbRegistryMock
						.registryStoredQuery(findSubmissionSetsRequestMock))
				.thenReturn(findSubmissionSetsResponseMock);

		// Act
		AdhocQueryResponse actualResponse = xdsbRegistryAdapterSpy
				.findSubmissionSets(submissionSetPatientIdMock,
						submissionSetAuthorPersonMock);

		// Assert
		assertEquals(findSubmissionSetsResponseMock, actualResponse);
	}

	@Test
	public void testGetSubmissionSetAndContents() throws JAXBException {
		// Arrange
		String submissionSetPatientIdMock = "submissionSetPatientIdMock";
		AdhocQueryRequest getSubmissionSetAndContentsRequestMock = mock(AdhocQueryRequest.class);
		AdhocQueryResponse getSubmissionSetAndContentsResponseMock = mock(AdhocQueryResponse.class);
		when(
				xdsbRegistryAdapterSpy
						.createGetSubmissionSetAndContentsRequest(submissionSetPatientIdMock))
				.thenReturn(getSubmissionSetAndContentsRequestMock);
		when(
				xdsbRegistryMock
						.registryStoredQuery(getSubmissionSetAndContentsRequestMock))
				.thenReturn(getSubmissionSetAndContentsResponseMock);

		// Act
		AdhocQueryResponse actualResponse = xdsbRegistryAdapterSpy
				.getSubmissionSetAndContents(submissionSetPatientIdMock);

		// Assert
		assertEquals(getSubmissionSetAndContentsResponseMock, actualResponse);
	}

	@Test
	public void testExtractSubmissionSetUniqueIds() throws Throwable {
		// Arrange
		AdhocQueryResponse responseMock = mock(AdhocQueryResponse.class);
		String responseXmlMock = "responseXmlMock";
		Document documentMock = mock(Document.class);
		NodeList nodeListMock = mock(NodeList.class);
		Node nodeMock = mock(Node.class);
		String nodeValueMock = "nodeValueMock";
		when(marshallerMock.marshall(responseMock)).thenReturn(responseXmlMock);
		when(documentXmlConverterMock.loadDocument(responseXmlMock))
				.thenReturn(documentMock);
		when(documentAccessorMock.getNodeList(eq(documentMock), anyString()))
				.thenReturn(nodeListMock);
		when(nodeListMock.getLength()).thenReturn(1);
		when(nodeListMock.item(0)).thenReturn(nodeMock);
		when(nodeMock.getNodeValue()).thenReturn(nodeValueMock);

		// Act
		List<String> submissionSetUniqueIds = xdsbRegistryAdapterSpy
				.extractSubmissionSetUniqueIds(responseMock);

		// Assert
		assertTrue(submissionSetUniqueIds.contains(nodeValueMock));
	}

	@Test
	public void testExtractDeprecatedDocumentUniqueId() throws Throwable {
		// Arrange
		AdhocQueryResponse responseMock = mock(AdhocQueryResponse.class);
		String responseXmlMock = "responseXmlMock";
		Document documentMock = mock(Document.class);
		Node nodeMock = mock(Node.class);
		String nodeValueMock = "nodeValueMock";
		when(marshallerMock.marshall(responseMock)).thenReturn(responseXmlMock);
		when(documentXmlConverterMock.loadDocument(responseXmlMock))
				.thenReturn(documentMock);
		when(documentAccessorMock.getNode(eq(documentMock), anyString()))
				.thenReturn(nodeMock);
		when(nodeMock.getNodeValue()).thenReturn(nodeValueMock);

		// Act
		String deprecatedDocumentUniqueId = xdsbRegistryAdapterSpy
				.extractDeprecatedDocumentUniqueId(responseMock);

		// Assert
		assertEquals(nodeValueMock, deprecatedDocumentUniqueId);
	}
}
