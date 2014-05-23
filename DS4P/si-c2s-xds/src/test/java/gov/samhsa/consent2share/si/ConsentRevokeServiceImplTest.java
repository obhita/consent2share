package gov.samhsa.consent2share.si;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.common.tool.DocumentAccessor;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.acs.xdsb.common.XdsbMetadataGeneratorImpl;
import gov.samhsa.acs.xdsb.registry.wsclient.adapter.XdsbRegistryAdapter;
import gov.samhsa.acs.xdsb.repository.wsclient.adapter.XdsbRepositoryAdapter;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse.DocumentResponse;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@RunWith(MockitoJUnitRunner.class)
public class ConsentRevokeServiceImplTest {
	@Mock
	private XdsbRegistryAdapter xdsbRegistryMock;
	@Mock
	private XdsbRepositoryAdapter xdsbRepositoryMock;
	@Mock
	private DocumentAccessor documentAccessorMock;
	@Mock
	private DocumentXmlConverter documentXmlConverterMock;
	@Mock
	private SimpleMarshaller marshallerMock;

	@Spy
	@InjectMocks
	private ConsentRevokeServiceImpl sut;

	@Test
	public void testFindConsentEntryUuidByPolicyId() throws Exception,
			Throwable {
		// Arrange
		String patientUniqueIdMock = "patientUniqueIdMock";
		AdhocQueryResponse adhocQueryResponseMock = mock(AdhocQueryResponse.class);
		String adhocQueryResponseXmlMock = "adhocQueryResponseXmlMock";
		Document adhocQueryResponseDocMock = mock(Document.class);
		NodeList nodeListMock = mock(NodeList.class);
		Node nodeMock = mock(Node.class);
		String valueMock = "valueMock";
		NamedNodeMap nodeAttributesMapMock = mock(NamedNodeMap.class);
		Node valueNodeMock = mock(Node.class);
		Node registryObjectNodeMock = mock(Node.class);
		String registryObjectMock = "registryObjectMock";
		RetrieveDocumentSetRequest retrieveDocumentSetRequestMock = mock(RetrieveDocumentSetRequest.class);
		RetrieveDocumentSetResponse allPrivacyConsentsMock = new RetrieveDocumentSetResponse();
		DocumentResponse documentResponseMock = mock(DocumentResponse.class);
		allPrivacyConsentsMock.getDocumentResponse().add(documentResponseMock);
		String documentContentMock = "documentContentMock";
		Document docMock = mock(Document.class);
		Node policyIdNode = mock(Node.class);
		when(
				xdsbRegistryMock.registryStoredQuery(patientUniqueIdMock, null,
						XdsbDocumentType.PRIVACY_CONSENT, false, "")).thenReturn(
				adhocQueryResponseMock);
		when(marshallerMock.marshall(adhocQueryResponseMock)).thenReturn(
				adhocQueryResponseXmlMock);
		when(documentXmlConverterMock.loadDocument(adhocQueryResponseXmlMock))
				.thenReturn(adhocQueryResponseDocMock);
		when(
				documentAccessorMock
						.getNodeList(
								adhocQueryResponseDocMock,
								ConsentRevokeServiceImpl.XPATH_TO_DOCUMENT_ENTRY_UNIQUEID_EXTERNALIDENTIFIER))
				.thenReturn(nodeListMock);
		when(nodeListMock.getLength()).thenReturn(1);
		when(nodeListMock.item(0)).thenReturn(nodeMock);
		when(nodeMock.getAttributes()).thenReturn(nodeAttributesMapMock);
		when(nodeAttributesMapMock.getNamedItem("value")).thenReturn(
				valueNodeMock);
		when(nodeAttributesMapMock.getNamedItem("registryObject")).thenReturn(
				registryObjectNodeMock);
		when(valueNodeMock.getNodeValue()).thenReturn(valueMock);
		when(registryObjectNodeMock.getNodeValue()).thenReturn(
				registryObjectMock);
		when(
				xdsbRegistryMock
						.extractXdsbDocumentReferenceListAsRetrieveDocumentSetRequest(adhocQueryResponseMock))
				.thenReturn(retrieveDocumentSetRequestMock);
		when(
				xdsbRepositoryMock
						.retrieveDocumentSet(retrieveDocumentSetRequestMock))
				.thenReturn(allPrivacyConsentsMock);
		when(documentResponseMock.getDocument()).thenReturn(
				documentContentMock.getBytes());
		when(documentXmlConverterMock.loadDocument(documentContentMock))
				.thenReturn(docMock);
		when(
				documentAccessorMock.getNode(docMock,
						ConsentRevokeServiceImpl.XPATH_EXPR_XACML_POLICYID))
				.thenReturn(policyIdNode);
		when(policyIdNode.getNodeValue()).thenReturn(valueMock);
		when(documentResponseMock.getDocumentUniqueId()).thenReturn(valueMock);

		// Act
		String result = sut.findConsentEntryUuidByPolicyId(patientUniqueIdMock,
				valueMock, "");

		// Assert
		assertEquals(registryObjectMock, result);
	}

	@Test
	public void testRevokeConsent() throws Exception, Throwable {
		// Arrange
		String patientUniqueIdMock = "patientUniqueIdMock";
		String policyIdMock = "policyIdMock";
		String consentUuidMock = "consentUuidMock";
		String messageIdMock = "messageIdMock";
		RegistryResponse registryResponseMock = mock(RegistryResponse.class);
		doReturn(consentUuidMock).when(sut).findConsentEntryUuidByPolicyId(
				patientUniqueIdMock, policyIdMock, messageIdMock);
		when(
				xdsbRepositoryMock.provideAndRegisterDocumentSet(
						XdsbRepositoryAdapter.EMPTY_XML_DOCUMENT, null,
						XdsbDocumentType.DEPRECATE_PRIVACY_CONSENT,
						patientUniqueIdMock, consentUuidMock)).thenReturn(
				registryResponseMock);

		// Act
		RegistryResponse actualRespnose = sut.revokeConsent(
				patientUniqueIdMock, policyIdMock, messageIdMock);

		// Assert
		assertEquals(registryResponseMock, actualRespnose);
	}

	@Test
	public void testGetPatientUniqueId() {
		// Arrange
		String patientIdMock = "patientIdMock";
		String domainIdMock = "domainIdMock";
		String patientUniqueIdMock = "patientUniqueIdMock";
		when(xdsbRegistryMock.getPatientUniqueId(patientIdMock, domainIdMock))
				.thenReturn(patientUniqueIdMock);

		// Act
		String actualResponse = sut.getPatientUniqueId(patientIdMock,
				domainIdMock);

		// Assert
		assertEquals(patientUniqueIdMock, actualResponse);
	}

	@Test
	public void testCreateXdsbMetadataGenerator() {
		// Act
		XdsbMetadataGeneratorImpl xdsbMetadataGeneratorImpl = sut
				.createXdsbMetadataGenerator();

		// Assert
		assertNotNull(xdsbMetadataGeneratorImpl);
		assertTrue(xdsbMetadataGeneratorImpl instanceof XdsbMetadataGeneratorImpl);
		XdsbDocumentType xdsbDocumentType = (XdsbDocumentType) ReflectionTestUtils
				.getField(xdsbMetadataGeneratorImpl,
						"documentTypeForXdsbMetadata");
		assertEquals(XdsbDocumentType.DEPRECATE_PRIVACY_CONSENT,
				xdsbDocumentType);
	}
}
