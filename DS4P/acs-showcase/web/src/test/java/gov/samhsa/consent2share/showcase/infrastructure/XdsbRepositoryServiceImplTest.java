package gov.samhsa.consent2share.showcase.infrastructure;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.acs.xdsb.repository.wsclient.adapter.RetrieveDocumentSetResponseFilter;
import gov.samhsa.acs.xdsb.repository.wsclient.adapter.XdsbRepositoryAdapter;
import gov.samhsa.consent2share.showcase.exception.AcsShowCaseException;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class XdsbRepositoryServiceImplTest {
	@Mock
	private XdsbRepositoryAdapter xdsbRepositoryAdapterMock;
	@Mock
	private SimpleMarshaller simpleMarshallerMock;
	@Mock
	private RetrieveDocumentSetResponseFilter retrieveDocumentSetResponseFilterMock;
	@Mock
	RegistryResponse registryResponseMock;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@InjectMocks
	private XdsbRepositoryServiceImpl sut;

	@Test
	public void testProvideAndRegisterDocumentSet() throws Throwable {
		// Arrange
		sut.setXdsbRepository(xdsbRepositoryAdapterMock);
		String documentXmlStringMock = "documentXmlStringMock";
		String domainIdMock = "domainIdMock";
		XdsbDocumentType xdsbDocumentTypeMock = XdsbDocumentType.CLINICAL_DOCUMENT;

		when(
				xdsbRepositoryAdapterMock.provideAndRegisterDocumentSet(
						documentXmlStringMock, domainIdMock,
						xdsbDocumentTypeMock)).thenReturn(registryResponseMock);

		// Act
		RegistryResponse actualResponse = sut.provideAndRegisterDocumentSet(
				documentXmlStringMock, domainIdMock, xdsbDocumentTypeMock);

		// Assert
		assertEquals(registryResponseMock, actualResponse);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testProvideAndRegisterDocumentSet_Throws_Throwable()
			throws Throwable {
		// Arrange
		sut.setXdsbRepository(xdsbRepositoryAdapterMock);
		String documentXmlStringMock = "documentXmlStringMock";
		String domainIdMock = "domainIdMock";
		XdsbDocumentType xdsbDocumentTypeMock = XdsbDocumentType.CLINICAL_DOCUMENT;

		when(
				xdsbRepositoryAdapterMock.provideAndRegisterDocumentSet(
						documentXmlStringMock, domainIdMock,
						xdsbDocumentTypeMock)).thenThrow(Throwable.class);
		thrown.expect(AcsShowCaseException.class);

		// Act
		@SuppressWarnings("unused")
		RegistryResponse actualResponse = sut.provideAndRegisterDocumentSet(
				documentXmlStringMock, domainIdMock, xdsbDocumentTypeMock);
	}
}
