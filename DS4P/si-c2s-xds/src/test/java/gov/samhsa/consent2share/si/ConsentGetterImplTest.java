package gov.samhsa.consent2share.si;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.sql.DataSource;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

@RunWith(MockitoJUnitRunner.class)
public class ConsentGetterImplTest {
	@Mock
	private JdbcTemplate jdbcTemplateMock;
	@Mock
	private DataSource dataSourceMock;
	@Mock
	private SimpleConsentDtoRowMapper rowMapperMock;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Spy
	@InjectMocks
	private ConsentGetterImpl sut;

	@Test
	public void testGetConsent() throws SQLException {
		// Arrange
		long consentIdMock = 1;
		String sql = "select consent.xacml_policy_file, consent.patient, consent.id, patient.enterprise_identifier "
				+ "from consent "
				+ "join patient on consent.patient=patient.id "
				+ "where consent.id=?";
		SimpleConsentDto simpleConsentDtoMock = mock(SimpleConsentDto.class);
		LinkedList<SimpleConsentDto> simpleConsentDtoListMock = new LinkedList<SimpleConsentDto>();
		simpleConsentDtoListMock.add(simpleConsentDtoMock);
		when(rowMapperMock.mapRow(isA(ResultSet.class), anyInt())).thenReturn(
				simpleConsentDtoMock);
		when(sut.getJdbcTemplate()).thenReturn(jdbcTemplateMock);
		when(
				jdbcTemplateMock.query(eq(sql), isA(Object[].class),
						eq(rowMapperMock)))
				.thenReturn(simpleConsentDtoListMock);

		// Act
		SimpleConsentDto response = sut.getConsent(consentIdMock);

		// Assert
		assertEquals(simpleConsentDtoMock, response);
	}
}
