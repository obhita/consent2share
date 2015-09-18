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
public class RevokedConsentDtoRowMapperTest {

	@Mock
	private LobHandler lobHandlerMock;

	@InjectMocks
	private RevokedConsentDtoRowMapper sut;

	@Test
	public void testMapRow() throws SQLException {
		// Arrange
		ResultSet rsMock = mock(ResultSet.class);
		long consentIdMock = 1;
		String policyIdMock = "policyIdMock";
		String patientIdMock = "patientIdMock";
		byte[] revokedConsentPdfMock = new byte[1];
		when(rsMock.getLong("consent.id")).thenReturn(consentIdMock);
		when(rsMock.getString("consent.consent_reference_id")).thenReturn(
				policyIdMock);
		when(rsMock.getString("patient.enterprise_identifier")).thenReturn(
				patientIdMock);
		when(
				lobHandlerMock
						.getBlobAsBytes(rsMock,
								"signedpdfconsent_revocation.signed_pdf_consent_revocation_content"))
				.thenReturn(revokedConsentPdfMock);

		// Act
		RevokedConsentDto result = sut.mapRow(rsMock, 1);

		// Assert
		assertEquals(consentIdMock, result.getPcmConsentId());
		assertEquals(policyIdMock, result.getXacmlCcdId());
		assertEquals(patientIdMock, result.getPatientEid());
		assertEquals(revokedConsentPdfMock, result.getRevokedPdfConsent());
	}
}
