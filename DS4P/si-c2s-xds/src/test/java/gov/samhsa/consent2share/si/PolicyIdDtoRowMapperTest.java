package gov.samhsa.consent2share.si;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PolicyIdDtoRowMapperTest {

	@InjectMocks
	private PolicyIdDtoRowMapper sut;

	@Test
	public void testMapRow() throws SQLException {
		// Arrange
		ResultSet rsMock = mock(ResultSet.class);
		long consentIdMock = 1;
		String policyIdMock = "policyIdMock";
		String patientIdMock = "patientIdMock";
		when(rsMock.getLong("consent.id")).thenReturn(consentIdMock);
		when(rsMock.getString("consent.consent_reference_id")).thenReturn(
				policyIdMock);
		when(rsMock.getString("patient.enterprise_identifier")).thenReturn(
				patientIdMock);

		// Act
		PolicyIdDto result = sut.mapRow(rsMock, 1);

		// Assert
		assertEquals(consentIdMock, result.getConsentId());
		assertEquals(policyIdMock, result.getPolicyId());
		assertEquals(consentIdMock, result.getConsentId());
	}
}
