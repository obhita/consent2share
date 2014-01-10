package gov.samhsa.acs.xdsb.repository.wsclient.adapter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.*;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.acs.xdsb.common.XdsbMetadataGeneratorImpl;
import gov.samhsa.acs.xdsb.repository.wsclient.XDSRepositorybWebServiceClient;
import gov.samhsa.acs.xdsb.repository.wsclient.adapter.RetrieveDocumentSetResponseFilter;
import gov.samhsa.acs.xdsb.repository.wsclient.adapter.XdsbRepositoryAdapter;
import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequest;
import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequest.Document;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;
import oasis.names.tc.ebxml_regrep.xsd.lcm._3.SubmitObjectsRequest;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class XdsbRepositoryAdapterTest {
	// Constants
	private static final String DOCUMENT_XML_STRING = "DOCUMENT_XML_STRING";
	private static final String HOME_COMMUNITY_ID = "HOME_COMMUNITY_ID";
	private static final String SUBMIT_OBJECTS_REQUEST_STRING = "SUBMIT_OBJECTS_REQUEST_STRING";

	// Mocks
	@Mock
	private XDSRepositorybWebServiceClient xdsbRepositoryMock;
	@Mock
	private XdsbMetadataGeneratorImpl xdsbMetadataGeneratorMock;
	@Mock
	private SimpleMarshaller marshallerMock;
	@Mock
	private RetrieveDocumentSetResponseFilter responseFilterMock;

	// System under test
	private static XdsbRepositoryAdapter xdsbRepositoryAdapterSpy;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		XdsbRepositoryAdapter xdsbRepositoryAdapter = new XdsbRepositoryAdapter(
				xdsbRepositoryMock, marshallerMock, responseFilterMock);
		xdsbRepositoryAdapterSpy = spy(xdsbRepositoryAdapter);
	}

	@Test
	public void testProvideAndRegisterDocumentSetRequest_Given_ProvideAndRegisterDocumentSetRequest() {
		// Arrange
		ProvideAndRegisterDocumentSetRequest provideAndRegisterDocumentSetRequest = new ProvideAndRegisterDocumentSetRequest();
		RegistryResponse registryResponse = new RegistryResponse();
		when(
				xdsbRepositoryMock
						.provideAndRegisterDocumentSet(provideAndRegisterDocumentSetRequest))
				.thenReturn(registryResponse);

		// Act
		RegistryResponse actualRegistryResponse = xdsbRepositoryAdapterSpy
				.provideAndRegisterDocumentSet(provideAndRegisterDocumentSetRequest);

		// Assert
		assertEquals(registryResponse, actualRegistryResponse);
	}

	@Test
	public void testProvideAndRegisterDocumentSetRequest_Given_DocumentXml_HomeCommunityId()
			throws Throwable {
		// Arrange
		when(
				xdsbMetadataGeneratorMock.generateMetadataXml(
						DOCUMENT_XML_STRING, HOME_COMMUNITY_ID)).thenReturn(
				SUBMIT_OBJECTS_REQUEST_STRING);

		SubmitObjectsRequest submitObjectRequest = new SubmitObjectsRequest();
		when(
				marshallerMock.unmarshallFromXml(SubmitObjectsRequest.class,
						SUBMIT_OBJECTS_REQUEST_STRING)).thenReturn(
				submitObjectRequest);

		Document document = new Document();
		when(xdsbRepositoryAdapterSpy.createDocument(DOCUMENT_XML_STRING))
				.thenReturn(document);

		ProvideAndRegisterDocumentSetRequest request = new ProvideAndRegisterDocumentSetRequest();
		when(
				xdsbRepositoryAdapterSpy
						.createProvideAndRegisterDocumentSetRequest(
								submitObjectRequest, document)).thenReturn(
				request);

		RegistryResponse response = new RegistryResponse();
		when(xdsbRepositoryMock.provideAndRegisterDocumentSet(request))
				.thenReturn(response);

		when(
				xdsbRepositoryAdapterSpy
						.createXdsbMetadataGenerator(isA(XdsbDocumentType.class)))
				.thenReturn(xdsbMetadataGeneratorMock);

		// Act
		RegistryResponse actualResponse = xdsbRepositoryAdapterSpy
				.provideAndRegisterDocumentSet(DOCUMENT_XML_STRING,
						HOME_COMMUNITY_ID, XdsbDocumentType.CLINICAL_DOCUMENT);

		// Assert
		assertEquals(response, actualResponse);
	}

	@Test
	public void testRetrieveDocumentSetRequest_Given_RetrieveDocumentSetRequest() {
		// Arrange
		RetrieveDocumentSetRequest retrieveDocumentSetRequest = new RetrieveDocumentSetRequest();
		RetrieveDocumentSetResponse retrieveDocumentSetResponse = new RetrieveDocumentSetResponse();
		when(
				xdsbRepositoryMock
						.retrieveDocumentSet(retrieveDocumentSetRequest))
				.thenReturn(retrieveDocumentSetResponse);

		// Act
		RetrieveDocumentSetResponse actualResponse = xdsbRepositoryAdapterSpy
				.retrieveDocumentSet(retrieveDocumentSetRequest);

		// Assert
		assertEquals(retrieveDocumentSetResponse, actualResponse);
	}

	@Test
	public void testRetrieveDocumentSetRequest_Given_DocumentUniqueId_RepositoryId() {
		// Arrange
		RetrieveDocumentSetRequest retrieveDocumentSetRequest = new RetrieveDocumentSetRequest();
		when(
				xdsbRepositoryAdapterSpy.createRetrieveDocumentSetRequest(
						DOCUMENT_XML_STRING, HOME_COMMUNITY_ID)).thenReturn(
				retrieveDocumentSetRequest);
		RetrieveDocumentSetResponse retrieveDocumentSetResponse = new RetrieveDocumentSetResponse();
		when(
				xdsbRepositoryMock
						.retrieveDocumentSet(retrieveDocumentSetRequest))
				.thenReturn(retrieveDocumentSetResponse);

		// Act
		RetrieveDocumentSetResponse actualResponse = xdsbRepositoryAdapterSpy
				.retrieveDocumentSet(DOCUMENT_XML_STRING,
						HOME_COMMUNITY_ID);

		// Assert
		assertEquals(retrieveDocumentSetResponse, actualResponse);
	}
}
