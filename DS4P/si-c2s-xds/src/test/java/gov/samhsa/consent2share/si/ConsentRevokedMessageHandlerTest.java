package gov.samhsa.consent2share.si;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class ConsentRevokedMessageHandlerTest {

	@Mock
	private ConsentRevokeService consentRevokeServiceMock;
	@Mock
	private ConsentGetter consentGetterMock;

	@InjectMocks
	private ConsentRevokedMessageHandler sut;

	private String domainIdMock;

	@Before
	public void setUp() throws Exception {
		domainIdMock = "domainIdMock";
		ReflectionTestUtils.setField(sut, "domainId", domainIdMock);
	}

	@Test
	public void testHandleMessage_Successes_Given_XDS_Returns_Success_Response()
			throws Throwable {
		// Arrange
		String consentIdMock = "1";
		long consentIdLongMock = 1;
		PolicyIdDto policyDtoMock = mock(PolicyIdDto.class);
		String patientIdMock = "patientIdMock";
		String policyIdMock = "policyIdMock";
		String patientUniqueIdMock = "patientUniqueIdMock";
		RegistryResponse responseMock = mock(RegistryResponse.class);
		when(consentGetterMock.getPolicyId(consentIdLongMock)).thenReturn(
				policyDtoMock);
		when(policyDtoMock.getPatientId()).thenReturn(patientIdMock);
		when(policyDtoMock.getPolicyId()).thenReturn(policyIdMock);
		when(
				consentRevokeServiceMock.getPatientUniqueId(patientIdMock,
						domainIdMock)).thenReturn(patientUniqueIdMock);
		when(
				consentRevokeServiceMock.revokeConsent(patientUniqueIdMock,
						policyIdMock)).thenReturn(responseMock);
		when(responseMock.getStatus()).thenReturn(
				ConsentRevokedMessageHandler.URN_RESPONSE_SUCCESS);

		// Act
		String responseMessage = sut.handleMessage(consentIdMock);

		// Assert
		assertEquals("Consent is successfully revoked in XDS.b repository",
				responseMessage);
	}

	@Test
	public void testHandleMessage_Throws_Exception_Given_XDS_Returns_Fail_Response()
			throws Throwable {
		// Arrange
		String consentIdMock = "1";
		long consentIdLongMock = 1;
		PolicyIdDto policyDtoMock = mock(PolicyIdDto.class);
		String patientIdMock = "patientIdMock";
		String policyIdMock = "policyIdMock";
		String patientUniqueIdMock = "patientUniqueIdMock";
		RegistryResponse responseMock = mock(RegistryResponse.class);
		when(consentGetterMock.getPolicyId(consentIdLongMock)).thenReturn(
				policyDtoMock);
		when(policyDtoMock.getPatientId()).thenReturn(patientIdMock);
		when(policyDtoMock.getPolicyId()).thenReturn(policyIdMock);
		when(
				consentRevokeServiceMock.getPatientUniqueId(patientIdMock,
						domainIdMock)).thenReturn(patientUniqueIdMock);
		when(
				consentRevokeServiceMock.revokeConsent(patientUniqueIdMock,
						policyIdMock)).thenReturn(responseMock);
		when(responseMock.getStatus()).thenReturn("FAIL");

		try {
			// Act
			sut.handleMessage(consentIdMock);
		} catch (Throwable e) {
			String errorMessageExpected = "Failed to revoke consent in XDS.b repository becuase response status is not "
					+ ConsentRevokedMessageHandler.URN_RESPONSE_SUCCESS;
			// Assert
			assertEquals(errorMessageExpected, e.getMessage());
		}
	}

	@Test
	public void testHandleMessage_Throws_Exception_Given_XDS_Throws_Exception()
			throws Throwable {
		// Arrange
		String consentIdMock = "1";
		long consentIdLongMock = 1;
		PolicyIdDto policyDtoMock = mock(PolicyIdDto.class);
		String patientIdMock = "patientIdMock";
		String policyIdMock = "policyIdMock";
		String patientUniqueIdMock = "patientUniqueIdMock";
		when(consentGetterMock.getPolicyId(consentIdLongMock)).thenReturn(
				policyDtoMock);
		when(policyDtoMock.getPatientId()).thenReturn(patientIdMock);
		when(policyDtoMock.getPolicyId()).thenReturn(policyIdMock);
		when(
				consentRevokeServiceMock.getPatientUniqueId(patientIdMock,
						domainIdMock)).thenReturn(patientUniqueIdMock);
		
		Throwable exMock = mock(Throwable.class);
		
		when(
				consentRevokeServiceMock.revokeConsent(patientUniqueIdMock,
						policyIdMock)).thenThrow(exMock);

		try {
			// Act
			sut.handleMessage(consentIdMock);
		} catch (Throwable e) {
			// Assert
			assertEquals(exMock, e);
		}
	}
}
