package gov.samhsa.consent2share.si;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.audit.AuditService;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
//import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;
import gov.samhsa.spirit.wsclient.adapter.SpiritAdapter;
import gov.samhsa.spirit.wsclient.dto.EhrPatientClientListDto;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.spirit.ehr.ws.client.generated.DocumentClientDto;
import com.spirit.ehr.ws.client.generated.EhrPatientClientDto;
import com.spirit.ehr.ws.client.generated.EhrResponseDetail;
import com.spirit.ehr.ws.client.generated.SourceSubmissionClientDto;
import com.spirit.ehr.ws.client.generated.XdsSrcSubmitRsp;

@RunWith(MockitoJUnitRunner.class)
public class ConsentSignedMessageHandlerTest {

	// private static final String SUCCESS =
	// AbstractConsentMessageHandler.URN_RESPONSE_SUCCESS;
	// private static final String FAIL = "FAIL";

	@Mock
	private ConsentGetter consentGetterMock;
	@Mock
	private DocumentXmlConverter documentXmlConverterMock;
	@Mock
	private AuditService auditServiceMock;
	@Mock
	private SimpleMarshaller marshallerMock;
	@Mock
	private SpiritAdapter spiritAdapter;
	@Mock
	private EndpointStopper endpointStopperMock;

	final String dataMock = "1";
	final byte[] consentMock = new byte[1];
	final byte[] signedConsentPdfMock = new byte[1];
	final byte[] pdfConsentFromXacmlMock = new byte[1];
	final byte[] pdfConsentToXacmlMock = new byte[1];
	final String domainIdMock = "domainIdMock";
	final String patientEidMock = "patientEidMock";
	final String patientLocalIdMock = "patientLocalIdMock";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@InjectMocks
	private ConsentSignedMessageHandler sut;

	@Test
	public void testHandleMessage_Successes_Given_XDS_Returns_Success_Response()
			throws Throwable {
		// Arrange
		ReflectionTestUtils.setField(sut, "domainId", domainIdMock);
		SignedConsentDto simpleConsentDtoMock = mock(SignedConsentDto.class);
		String xacmlPolicyIdMock = "xacmlPolicyIdMock";

		when(simpleConsentDtoMock.getPatientMrn()).thenReturn(
				patientLocalIdMock);
		when(simpleConsentDtoMock.getXacmlCcdId())
				.thenReturn(xacmlPolicyIdMock);
		when(simpleConsentDtoMock.getXacmlCcd()).thenReturn(consentMock);
		when(simpleConsentDtoMock.getXacmlPdfConsentFrom()).thenReturn(
				pdfConsentFromXacmlMock);
		when(simpleConsentDtoMock.getXacmlPdfConsentTo()).thenReturn(
				pdfConsentToXacmlMock);
		when(simpleConsentDtoMock.getXacmlCcd()).thenReturn(consentMock);
		when(simpleConsentDtoMock.getSignedPdfConsent()).thenReturn(
				signedConsentPdfMock);
		when(consentGetterMock.getSignedConsentDto(1)).thenReturn(
				simpleConsentDtoMock);

		when(spiritAdapter.login()).thenReturn("stateid123");

		SourceSubmissionClientDto sourceSubmissionClientDto = mock(SourceSubmissionClientDto.class);
		when(spiritAdapter.generatePolicyMetadata(anyString())).thenReturn(
				sourceSubmissionClientDto);

		List<DocumentClientDto> documentClientDtolistmock = new ArrayList<DocumentClientDto>();
		DocumentClientDto documentClientDtomock = mock(DocumentClientDto.class);
		documentClientDtolistmock.add(documentClientDtomock);

		when(sourceSubmissionClientDto.getDocuments()).thenReturn(
				documentClientDtolistmock);

		List<EhrPatientClientDto> patientClientDtoList = new ArrayList<EhrPatientClientDto>();
		EhrPatientClientDto patientClientDtomock = mock(EhrPatientClientDto.class);
		patientClientDtoList.add(patientClientDtomock);

		EhrPatientClientListDto ehrPatientClientListDto = mock(EhrPatientClientListDto.class);
		when(ehrPatientClientListDto.getEhrPatientClientListDto()).thenReturn(
				patientClientDtoList);
		when(ehrPatientClientListDto.getStateId()).thenReturn("stateid123");
		when(spiritAdapter.queryPatientsWithPids(anyString(), anyString()))
				.thenReturn(ehrPatientClientListDto);

		XdsSrcSubmitRsp responsemock = mock(XdsSrcSubmitRsp.class);
		EhrResponseDetail responseDetailMock = mock(EhrResponseDetail.class);
		List<String> listSuccess = new ArrayList<String>();
		listSuccess.add("Success 1");
		when(responsemock.getResponseDetail()).thenReturn(responseDetailMock);
		when(responseDetailMock.getListSuccess()).thenReturn(listSuccess);
		when(
				spiritAdapter.submitSignedConsent(consentMock,
						signedConsentPdfMock, pdfConsentFromXacmlMock,
						pdfConsentToXacmlMock, xacmlPolicyIdMock,
						patientLocalIdMock, "USA")).thenReturn(responsemock);

		// Act
		String response = sut.handleMessage(dataMock);

		// Assert
		assertEquals("Saved in XDS.b repository", response);
		verify(endpointStopperMock, times(1)).setCounter(0);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = Throwable.class)
	public void testHandleMessage_Throws_Exception_Given_XDS_Returns_Fail_Response()
			throws Throwable {
		// Arrange
		ReflectionTestUtils.setField(sut, "domainId", domainIdMock);
		SignedConsentDto simpleConsentDtoMock = mock(SignedConsentDto.class);
		when(simpleConsentDtoMock.getPatientMrn()).thenReturn(
				patientLocalIdMock);
		when(simpleConsentDtoMock.getXacmlCcdId()).thenReturn("xacmlPolicyId");
		when(simpleConsentDtoMock.getXacmlCcd()).thenReturn(consentMock);
		when(consentGetterMock.getSignedConsentDto(1)).thenReturn(
				simpleConsentDtoMock);

		when(spiritAdapter.login()).thenReturn("stateid123");

		SourceSubmissionClientDto sourceSubmissionClientDto = mock(SourceSubmissionClientDto.class);
		when(spiritAdapter.generatePolicyMetadata(anyString())).thenReturn(
				sourceSubmissionClientDto);

		List<DocumentClientDto> documentClientDtolistmock = new ArrayList<DocumentClientDto>();
		DocumentClientDto documentClientDtomock = mock(DocumentClientDto.class);
		documentClientDtolistmock.add(documentClientDtomock);

		when(sourceSubmissionClientDto.getDocuments()).thenReturn(
				documentClientDtolistmock);

		List<EhrPatientClientDto> patientClientDtoList = new ArrayList<EhrPatientClientDto>();
		EhrPatientClientDto patientClientDtomock = mock(EhrPatientClientDto.class);
		patientClientDtoList.add(patientClientDtomock);

		EhrPatientClientListDto ehrPatientClientListDto = mock(EhrPatientClientListDto.class);
		when(ehrPatientClientListDto.getEhrPatientClientListDto()).thenReturn(
				patientClientDtoList);
		when(ehrPatientClientListDto.getStateId()).thenReturn("stateid123");
		when(spiritAdapter.queryPatientsWithPids(anyString(), anyString()))
				.thenReturn(ehrPatientClientListDto);

		when(
				spiritAdapter.submitDocument(patientClientDtomock,
						sourceSubmissionClientDto, false, "stateid123"))
				.thenThrow(Throwable.class);

		sut.handleMessage(dataMock);

	}

}
