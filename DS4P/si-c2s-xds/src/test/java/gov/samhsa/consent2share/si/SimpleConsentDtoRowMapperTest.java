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
public class SimpleConsentDtoRowMapperTest {

	@Mock
	private LobHandler lobHandlerMock;

	@InjectMocks
	private SimpleConsentDtoRowMapper sut;

	@Test
	public void testMapRow() throws SQLException {
		// Arrange
		ResultSet rsMock = mock(ResultSet.class);
		int iMock = 1;
		long consentIdMock = 2;
		long patientIdMock = 3;
		String eidMock = "eidMock";
		String consentMock = "consentMock";
		byte[] consentMockBytes = consentMock.getBytes();
		when(rsMock.getLong("id")).thenReturn(consentIdMock);
		when(rsMock.getLong("consent.patient")).thenReturn(patientIdMock);
		when(rsMock.getString("patient.enterprise_identifier")).thenReturn(
				eidMock);
		when(lobHandlerMock.getBlobAsBytes(rsMock, "consent.xacml_policy_file"))
				.thenReturn(consentMockBytes);

		// Act
		SimpleConsentDto response = sut.mapRow(rsMock, iMock);

		// Assert
		assertEquals(consentMock, response.getConsent());
		assertEquals(eidMock, response.geteId());
		assertEquals(patientIdMock, response.getPatientId());
	}
}
