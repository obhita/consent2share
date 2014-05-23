package gov.samhsa.consent2share.si;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import gov.samhsa.acs.audit.AuditService;
import gov.samhsa.acs.audit.PredicateKey;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.common.tool.exception.DocumentAccessorException;
import gov.samhsa.consent2share.si.audit.SIAuditVerb;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class ConsentRevokedMessageHandlerTest {

	@Mock
	private ConsentRevokeService consentRevokeServiceMock;
	@Mock
	private ConsentGetter consentGetterMock;
	@Mock
	private AuditService auditServiceMock;
	@Mock
	private SimpleMarshaller marshallerMock;

	@InjectMocks
	@Spy
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
		long patientIdMock = 2;
		PolicyIdDto policyDtoMock = mock(PolicyIdDto.class);
		String patientEidMock = "patientEidMock";
		String policyIdMock = "policyIdMock";
		String patientUniqueIdMock = "patientUniqueIdMock";
		String messageIdMock = "messageIdMock";
		RegistryResponse responseMock = mock(RegistryResponse.class);
		doNothing().when(auditServiceMock).audit(eq(sut), anyString(),
				eq(SIAuditVerb.XDS_DEPRECATE_CONSENT), eq(patientEidMock),
				anyMapOf(PredicateKey.class, String.class));
		when(consentGetterMock.getPolicyId(consentIdLongMock)).thenReturn(
				policyDtoMock);
		when(policyDtoMock.getPatientEid()).thenReturn(patientEidMock);
		when(policyDtoMock.getPatientId()).thenReturn(patientIdMock);
		when(policyDtoMock.getPolicyId()).thenReturn(policyIdMock);
		when(
				consentRevokeServiceMock.getPatientUniqueId(patientEidMock,
						domainIdMock)).thenReturn(patientUniqueIdMock);
		when(
				consentRevokeServiceMock.revokeConsent(patientUniqueIdMock,
						policyIdMock, messageIdMock)).thenReturn(responseMock);
		when(sut.createMessageId()).thenReturn(messageIdMock);
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
		long patientIdMock = 2;
		PolicyIdDto policyDtoMock = mock(PolicyIdDto.class);
		String patientEidMock = "patientEidMock";
		String policyIdMock = "policyIdMock";
		String patientUniqueIdMock = "patientUniqueIdMock";
		String messageIdMock = "messageIdMock";
		RegistryResponse responseMock = mock(RegistryResponse.class);
		doNothing().when(auditServiceMock).audit(eq(sut), anyString(),
				eq(SIAuditVerb.XDS_DEPRECATE_CONSENT), eq(patientEidMock),
				anyMapOf(PredicateKey.class, String.class));
		when(consentGetterMock.getPolicyId(consentIdLongMock)).thenReturn(
				policyDtoMock);
		when(policyDtoMock.getPatientEid()).thenReturn(patientEidMock);
		when(policyDtoMock.getPatientId()).thenReturn(patientIdMock);
		when(policyDtoMock.getPolicyId()).thenReturn(policyIdMock);
		when(
				consentRevokeServiceMock.getPatientUniqueId(patientEidMock,
						domainIdMock)).thenReturn(patientUniqueIdMock);
		when(
				consentRevokeServiceMock.revokeConsent(patientUniqueIdMock,
						policyIdMock, messageIdMock)).thenReturn(responseMock);
		when(sut.createMessageId()).thenReturn(messageIdMock);
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

	@Test(expected=DocumentAccessorException.class)
	public void testHandleMessage_Throws_Exception_Given_XDS_Throws_Exception()
			throws Throwable {
		// Arrange
		String consentIdMock = "1";
		long consentIdLongMock = 1;
		long patientIdMock = 2;
		PolicyIdDto policyDtoMock = mock(PolicyIdDto.class);
		String patientEidMock = "patientEidMock";
		String policyIdMock = "policyIdMock";
		String patientUniqueIdMock = "patientUniqueIdMock";
		String messageIdMock = "messageIdMock";
		doNothing().when(auditServiceMock).audit(eq(sut), anyString(),
				eq(SIAuditVerb.XDS_DEPRECATE_CONSENT), eq(patientEidMock),
				anyMapOf(PredicateKey.class, String.class));
		when(consentGetterMock.getPolicyId(consentIdLongMock)).thenReturn(
				policyDtoMock);
		when(policyDtoMock.getPatientEid()).thenReturn(patientEidMock);
		when(policyDtoMock.getPatientId()).thenReturn(patientIdMock);
		when(policyDtoMock.getPolicyId()).thenReturn(policyIdMock);
		when(
				consentRevokeServiceMock.getPatientUniqueId(patientEidMock,
						domainIdMock)).thenReturn(patientUniqueIdMock);
		when(sut.createMessageId()).thenReturn(messageIdMock);
		doThrow(DocumentAccessorException.class).when(consentRevokeServiceMock).revokeConsent(patientUniqueIdMock,
				policyIdMock, messageIdMock);	

		// Act
		sut.handleMessage(consentIdMock);
	}
}
