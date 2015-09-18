package gov.samhsa.consent2share.si;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.audit.AuditService;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.spirit.wsclient.adapter.SpiritAdapter;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.spirit.ehr.ws.client.generated.EhrResponseDetail;
import com.spirit.ehr.ws.client.generated.XdsSrcSubmitRsp;

@RunWith(MockitoJUnitRunner.class)
public class ConsentRevokedMessageHandlerTest {

	@Mock
	private SimpleMarshaller marshallerMock;
	@Mock
	private ConsentGetter consentGetterMock;
	@Mock
	private SpiritAdapter spiritAdapter;
	@Mock
	private AuditService auditService;
	@Mock
	private EndpointStopper endpointStopperMock;

	@InjectMocks
	@Spy
	private ConsentRevokedMessageHandler sut;

	@Test
	public void testHandleMessage_Successes_Given_XDS_Returns_Success_Response()
			throws Throwable {
		// Arrange
		String consentIdMock = "1";
		byte[] revokedConsentPdf = new byte[1];
		RevokedConsentDto policyIdDto = mock(RevokedConsentDto.class);
		when(policyIdDto.getPatientEid()).thenReturn("FAM.123");
		when(policyIdDto.getXacmlCcdId()).thenReturn("PolicyId");
		when(policyIdDto.getPatientMrn()).thenReturn("locPolicyId");
		when(policyIdDto.getRevokedPdfConsent()).thenReturn(revokedConsentPdf);
		when(consentGetterMock.getRevokedConsentDto(1)).thenReturn(policyIdDto);
		when(
				marshallerMock
						.marshalWithoutRootElement(isA(XdsSrcSubmitRsp.class)))
				.thenReturn("marshalledXmlMock");

		XdsSrcSubmitRsp responsemock = mock(XdsSrcSubmitRsp.class);
		EhrResponseDetail responseDetailMock = mock(EhrResponseDetail.class);
		List<String> listSuccess = new ArrayList<String>();
		listSuccess.add("Success 1");
		when(responsemock.getResponseDetail()).thenReturn(responseDetailMock);
		when(responseDetailMock.getListSuccess()).thenReturn(listSuccess);
		when(
				spiritAdapter.deprecatePolicy("PolicyId", "locPolicyId",
						revokedConsentPdf)).thenReturn(responsemock);

		// Assert
		assertEquals("Consent is successfully revoked in XDS.b repository",
				sut.handleMessage(consentIdMock));
		verify(endpointStopperMock, times(1)).setCounter(0);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = Throwable.class)
	public void testHandleMessage_Throws_Exception_Given_XDS_Returns_Fail_Response()
			throws Throwable {

		String consentIdMock = "1";
		byte[] revokedConsentPdf = new byte[1];
		RevokedConsentDto policyIdDto = mock(RevokedConsentDto.class);
		when(policyIdDto.getPatientEid()).thenReturn("FAM.123");
		when(policyIdDto.getXacmlCcdId()).thenReturn("PolicyId");
		when(policyIdDto.getRevokedPdfConsent()).thenReturn(revokedConsentPdf);
		when(consentGetterMock.getRevokedConsentDto(1)).thenReturn(policyIdDto);

		when(spiritAdapter.login()).thenReturn("stateid123");

		when(
				spiritAdapter.deprecatePolicy("PolicyId", "FAM.123",
						revokedConsentPdf)).thenThrow(Throwable.class);

		// Assert
		sut.handleMessage(consentIdMock);

	}

}
