package gov.samhsa.consent2share.si;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.acs.xdsb.repository.wsclient.adapter.XdsbRepositoryAdapter;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class ConsentSignedMessageHandlerTest {

	private static final String SUCCESS = ConsentSignedMessageHandler.URN_RESPONSE_SUCCESS;
	private static final String FAIL = "FAIL";

	@Mock
	private ConsentGetter consentGetterMock;
	@Mock
	private XdsbRepositoryAdapter xdsbRepositoryMock;
	@Mock
	private DocumentXmlConverter documentXmlConverterMock;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@InjectMocks
	private ConsentSignedMessageHandler sut;

	@Test
	public void testHandleMessage_Success() throws Throwable {
		// Arrange
		String registryResponseReturnValue = SUCCESS;
		boolean xdsbThrowsThrowable = false;
		String dataMock = arrangeCommon(registryResponseReturnValue,
				xdsbThrowsThrowable);

		// Act
		String response = sut.handleMessage(dataMock);

		// Assert
		assertEquals("Saved in XDS.b repository", response);
	}

	@Test
	public void testHandleMessage_Fail() throws Throwable {
		// Arrange
		String registryResponseReturnValue = FAIL;
		boolean xdsbThrowsThrowable = false;
		String dataMock = arrangeCommon(registryResponseReturnValue,
				xdsbThrowsThrowable);

		// Act
		String response = sut.handleMessage(dataMock);

		// Assert
		assertEquals("Failed to save in XDS.b repository", response);
	}

	@Test
	public void testHandleMessage_Fail_With_Caught_Exception() throws Throwable {
		// Arrange
		String registryResponseReturnValue = FAIL;
		boolean xdsbThrowsThrowable = true;
		String dataMock = arrangeCommon(registryResponseReturnValue,
				xdsbThrowsThrowable);

		// Act
		String response = sut.handleMessage(dataMock);

		// Assert
		assertEquals("Failed to save in XDS.b repository", response);
	}

	private String arrangeCommon(String registryResponseReturnValue,
			boolean xdsbThrowsThrowable) throws Throwable {
		String dataMock = "1";
		String consentMock = "consentMock";
		String domainIdMock = "domainIdMock";
		ReflectionTestUtils.setField(sut, "domainId", domainIdMock);
		SimpleConsentDto simpleConsentDtoMock = mock(SimpleConsentDto.class);
		RegistryResponse registryResponseMock = mock(RegistryResponse.class);
		when(simpleConsentDtoMock.getConsent()).thenReturn(consentMock);
		when(consentGetterMock.getConsent(1)).thenReturn(simpleConsentDtoMock);
		if (xdsbThrowsThrowable) {
			arrangeXdsbRepositoryMock_Throws_Throwable(consentMock,
					domainIdMock, registryResponseMock);
		} else {
			arrangeXdsbRepositoryMock(consentMock, domainIdMock,
					registryResponseMock);
		}

		when(registryResponseMock.getStatus()).thenReturn(
				registryResponseReturnValue);
		return dataMock;
	}

	private void arrangeXdsbRepositoryMock(String consentMock,
			String domainIdMock, RegistryResponse registryResponseMock)
			throws Throwable {
		when(
				xdsbRepositoryMock.provideAndRegisterDocumentSet(consentMock,
						domainIdMock, XdsbDocumentType.PRIVACY_CONSENT))
				.thenReturn(registryResponseMock);
	}

	@SuppressWarnings("unchecked")
	private void arrangeXdsbRepositoryMock_Throws_Throwable(String consentMock,
			String domainIdMock, RegistryResponse registryResponseMock)
			throws Throwable {
		when(
				xdsbRepositoryMock.provideAndRegisterDocumentSet(consentMock,
						domainIdMock, XdsbDocumentType.PRIVACY_CONSENT))
				.thenThrow(Throwable.class);
	}
}
