package gov.samhsa.consent2share.si;

import static gov.samhsa.consent2share.si.audit.SIAuditVerb.POLREP_DELETE_CONSENT;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.audit.AuditService;
import gov.samhsa.acs.audit.PredicateKey;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.polrep.client.PolRepRestClient;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

@RunWith(MockitoJUnitRunner.class)
public class ConsentRevokedMessageHandlerPolRepTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private ConsentGetter consentGetter;

	@Mock
	private AuditService auditService;

	@Mock
	private SimpleMarshaller marshaller;

	@Mock
	private PolRepRestClient polRepClient;

	@InjectMocks
	private ConsentRevokedMessageHandlerPolRep sut;

	@Test
	public void testHandleMessage() throws Throwable {
		// Arrange
		final String data = "1";
		final long dataLong = 1L;
		final String patientEid = "patientEid";
		final String xacmlCcdId = "xacmlCcdId";
		final String patientMrn = "patientMrn";
		final RevokedConsentDto revokedConsentDto = mock(RevokedConsentDto.class);

		when(consentGetter.getRevokedConsentDto(dataLong)).thenReturn(
				revokedConsentDto);
		when(revokedConsentDto.getPatientEid()).thenReturn(patientEid);
		when(revokedConsentDto.getXacmlCcdId()).thenReturn(xacmlCcdId);
		when(revokedConsentDto.getPatientMrn()).thenReturn(patientMrn);

		// Act
		sut.handleMessage(data);

		// Assert
		verify(polRepClient, times(1)).deletePolicy(xacmlCcdId);
		verify(auditService, times(1)).audit(eq(sut), anyString(),
				eq(POLREP_DELETE_CONSENT), eq(patientMrn),
				anyMapOf(PredicateKey.class, String.class));
	}

	@Test
	public void testHandleMessage_Throws_Exception() throws Throwable {
		// Arrange
		thrown.expect(Exception.class);
		final String data = "1";
		final long dataLong = 1L;
		final String patientEid = "patientEid";
		final String xacmlCcdId = "xacmlCcdId";
		final String patientMrn = "patientMrn";
		final RevokedConsentDto revokedConsentDto = mock(RevokedConsentDto.class);

		when(consentGetter.getRevokedConsentDto(dataLong)).thenReturn(
				revokedConsentDto);
		when(revokedConsentDto.getPatientEid()).thenReturn(patientEid);
		when(revokedConsentDto.getXacmlCcdId()).thenReturn(xacmlCcdId);
		when(revokedConsentDto.getPatientMrn()).thenReturn(patientMrn);
		doThrow(Exception.class).when(polRepClient).deletePolicy(xacmlCcdId);

		// Act
		sut.handleMessage(data);

		// Assert
		verify(polRepClient, times(1)).deletePolicy(xacmlCcdId);
		verify(auditService, times(1)).audit(eq(sut), anyString(),
				eq(POLREP_DELETE_CONSENT), eq(patientMrn),
				anyMapOf(PredicateKey.class, String.class));
	}

	@Test
	public void testHandleMessage_Throws_HttpStatusCodeException()
			throws Throwable {
		// Arrange
		thrown.expect(HttpStatusCodeException.class);
		final String data = "1";
		final long dataLong = 1L;
		final String patientEid = "patientEid";
		final String xacmlCcdId = "xacmlCcdId";
		final String patientMrn = "patientMrn";
		final RevokedConsentDto revokedConsentDto = mock(RevokedConsentDto.class);

		when(consentGetter.getRevokedConsentDto(dataLong)).thenReturn(
				revokedConsentDto);
		when(revokedConsentDto.getPatientEid()).thenReturn(patientEid);
		when(revokedConsentDto.getXacmlCcdId()).thenReturn(xacmlCcdId);
		when(revokedConsentDto.getPatientMrn()).thenReturn(patientMrn);
		final HttpStatusCodeException e = new HttpStatusCodeExceptionImpl(
				HttpStatus.NOT_FOUND);
		doThrow(e).when(polRepClient).deletePolicy(xacmlCcdId);

		// Act
		sut.handleMessage(data);

		// Assert
		verify(polRepClient, times(1)).deletePolicy(xacmlCcdId);
		verify(auditService, times(1)).audit(eq(sut), anyString(),
				eq(POLREP_DELETE_CONSENT), eq(patientMrn),
				anyMapOf(PredicateKey.class, String.class));
	}

	private class HttpStatusCodeExceptionImpl extends HttpStatusCodeException {

		private static final long serialVersionUID = -8148275781352783006L;

		protected HttpStatusCodeExceptionImpl(HttpStatus statusCode) {
			super(statusCode);
		}
	}
}
