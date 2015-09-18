package gov.samhsa.consent2share.si;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.support.lob.LobHandler;

@RunWith(MockitoJUnitRunner.class)
public class SignedConsentDtoRowMapperTest {

	@Mock
	private LobHandler lobHandlerMock;

	@InjectMocks
	private SignedConsentDtoRowMapper sut;

	@Test
	public void testMapRow() throws SQLException {
		// Arrange
		ResultSet rsMock = mock(ResultSet.class);
		int iMock = 1;
		long consentIdMock = 2;
		long patientIdMock = 3;
		String mrnMock = "mrnMock";
		String consentMock = "consentMock";
		byte[] consentMockBytes = consentMock.getBytes();
		byte[] signedConsentPdfBytes = new byte[1];
		when(rsMock.getLong("id")).thenReturn(consentIdMock);
		when(rsMock.getLong("consent.patient")).thenReturn(patientIdMock);
		when(rsMock.getString("patient.medical_record_number")).thenReturn(
				mrnMock);
		when(lobHandlerMock.getBlobAsBytes(rsMock, "consent.xacml_ccd"))
				.thenReturn(consentMockBytes);
		when(
				lobHandlerMock.getBlobAsBytes(rsMock,
						"consent.xacml_pdf_consent_from")).thenReturn(
				consentMockBytes);
		when(
				lobHandlerMock.getBlobAsBytes(rsMock,
						"consent.xacml_pdf_consent_to")).thenReturn(
				consentMockBytes);
		when(
				lobHandlerMock.getBlobAsBytes(rsMock,
						"signedpdfconsent.signed_pdf_consent_content"))
				.thenReturn(signedConsentPdfBytes);

		// Act
		SignedConsentDto response = sut.mapRow(rsMock, iMock);

		// Assert
		assertEquals(consentMockBytes, response.getXacmlCcd());
		assertEquals(consentMock, new String(response.getXacmlCcd()));
		assertEquals(mrnMock, response.getPatientMrn());
		assertEquals(patientIdMock, response.getPcmPatientId());
		assertEquals(signedConsentPdfBytes, response.getSignedPdfConsent());
	}
}
