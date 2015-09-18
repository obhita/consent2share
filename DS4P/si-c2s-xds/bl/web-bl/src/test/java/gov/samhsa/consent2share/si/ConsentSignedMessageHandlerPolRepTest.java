package gov.samhsa.consent2share.si;

import static gov.samhsa.consent2share.commonunit.matcher.ArgumentMatchers.matching;
import static gov.samhsa.consent2share.si.audit.SIAuditVerb.POLREP_ADD_CONSENT;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.audit.AuditService;
import gov.samhsa.acs.audit.PredicateKey;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.polrep.client.PolRepRestClient;
import gov.samhsa.acs.polrep.client.dto.PolicyContentContainerDto;

import org.junit.After;
import org.junit.Before;
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
public class ConsentSignedMessageHandlerPolRepTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private PolRepRestClient polRepClient;

	@Mock
	private BusController controlBusService;

	@Mock
	private NotificationPublisher notificationPublisher;

	@Mock
	protected ConsentGetter consentGetter;

	@Mock
	protected AuditService auditService;

	@Mock
	protected SimpleMarshaller marshaller;

	@InjectMocks
	private ConsentSignedMessageHandlerPolRep sut;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHandleMessage() throws Throwable {
		// Arrange
		final String data = "1";
		final long dataLong = 1L;
		final String xacmlCcdId = "xacmlCcdId";
		final String xacmlCcd = "xacmlCcd";
		final byte[] xacmlCcdBytes = xacmlCcd.getBytes();
		final boolean force = false;
		final String patientMrn = "patientMrn";
		final SignedConsentDto signedConsentDto = mock(SignedConsentDto.class);

		when(consentGetter.getSignedConsentDto(dataLong)).thenReturn(
				signedConsentDto);
		when(signedConsentDto.getXacmlCcdId()).thenReturn(xacmlCcdId);
		when(signedConsentDto.getXacmlCcd()).thenReturn(xacmlCcdBytes);
		when(signedConsentDto.getPatientMrn()).thenReturn(patientMrn);

		// Act
		sut.handleMessage(data);

		// Assert
		verify(polRepClient, times(1)).addPolicies(
				argThat(matching((PolicyContentContainerDto r) -> new String(r
						.getPolicies().get(0).getPolicy()).equals(xacmlCcd))),
				eq(force));
		verify(auditService, times(1)).audit(eq(sut), anyString(),
				eq(POLREP_ADD_CONSENT), eq(patientMrn),
				anyMapOf(PredicateKey.class, String.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testHandleMessage_Throws_Exception() throws Throwable {
		// Arrange
		thrown.expect(Exception.class);
		final String data = "1";
		final long dataLong = 1L;
		final String xacmlCcdId = "xacmlCcdId";
		final String xacmlCcd = "xacmlCcd";
		final byte[] xacmlCcdBytes = xacmlCcd.getBytes();
		final boolean force = false;
		final String patientMrn = "patientMrn";
		final SignedConsentDto signedConsentDto = mock(SignedConsentDto.class);

		when(consentGetter.getSignedConsentDto(dataLong)).thenReturn(
				signedConsentDto);
		when(signedConsentDto.getXacmlCcdId()).thenReturn(xacmlCcdId);
		when(signedConsentDto.getXacmlCcd()).thenReturn(xacmlCcdBytes);
		when(signedConsentDto.getPatientMrn()).thenReturn(patientMrn);
		when(
				polRepClient.addPolicies(any(PolicyContentContainerDto.class),
						eq(force))).thenThrow(Exception.class);

		// Act
		sut.handleMessage(data);

		// Assert
		verify(polRepClient, times(1)).addPolicies(
				argThat(matching((PolicyContentContainerDto r) -> new String(r
						.getPolicies().get(0).getPolicy()).equals(xacmlCcd))),
				eq(force));
		verify(auditService, times(1)).audit(eq(sut), anyString(),
				eq(POLREP_ADD_CONSENT), eq(patientMrn),
				anyMapOf(PredicateKey.class, String.class));
	}

	@Test
	public void testHandleMessage_Throws_HttpStatusCodeException()
			throws Throwable {
		// Arrange
		thrown.expect(HttpStatusCodeException.class);
		final String data = "1";
		final long dataLong = 1L;
		final String xacmlCcdId = "xacmlCcdId";
		final String xacmlCcd = "xacmlCcd";
		final byte[] xacmlCcdBytes = xacmlCcd.getBytes();
		final boolean force = false;
		final String patientMrn = "patientMrn";
		final SignedConsentDto signedConsentDto = mock(SignedConsentDto.class);

		when(consentGetter.getSignedConsentDto(dataLong)).thenReturn(
				signedConsentDto);
		when(signedConsentDto.getXacmlCcdId()).thenReturn(xacmlCcdId);
		when(signedConsentDto.getXacmlCcd()).thenReturn(xacmlCcdBytes);
		when(signedConsentDto.getPatientMrn()).thenReturn(patientMrn);
		final HttpStatusCodeExceptionImpl e = new HttpStatusCodeExceptionImpl(
				HttpStatus.CONFLICT);
		when(
				polRepClient.addPolicies(any(PolicyContentContainerDto.class),
						eq(force))).thenThrow(e);

		// Act
		sut.handleMessage(data);

		// Assert
		verify(polRepClient, times(1)).addPolicies(
				argThat(matching((PolicyContentContainerDto r) -> new String(r
						.getPolicies().get(0).getPolicy()).equals(xacmlCcd))),
				eq(force));
		verify(auditService, times(1)).audit(eq(sut), anyString(),
				eq(POLREP_ADD_CONSENT), eq(patientMrn),
				anyMapOf(PredicateKey.class, String.class));
	}

	private class HttpStatusCodeExceptionImpl extends HttpStatusCodeException {

		private static final long serialVersionUID = -8148275781352783006L;

		protected HttpStatusCodeExceptionImpl(HttpStatus statusCode) {
			super(statusCode);
		}
	}
}
