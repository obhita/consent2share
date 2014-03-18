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
	private SimpleConsentDtoRowMapper simpleConsentDtoRowMapperMock;
	@Mock
	private PolicyIdDtoRowMapper policyIdDtoRowMapperMock;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Spy
	@InjectMocks
	private ConsentGetterImpl sut;

	@Test
	public void testGetConsent() throws SQLException {
		// Arrange
		long consentIdMock = 1;
		SimpleConsentDto simpleConsentDtoMock = mock(SimpleConsentDto.class);
		LinkedList<SimpleConsentDto> simpleConsentDtoListMock = new LinkedList<SimpleConsentDto>();
		simpleConsentDtoListMock.add(simpleConsentDtoMock);
		when(
				simpleConsentDtoRowMapperMock.mapRow(isA(ResultSet.class),
						anyInt())).thenReturn(simpleConsentDtoMock);
		when(sut.getJdbcTemplate()).thenReturn(jdbcTemplateMock);
		when(
				jdbcTemplateMock.query(eq(ConsentGetterImpl.SQL_GET_CONSENT),
						isA(Object[].class), eq(simpleConsentDtoRowMapperMock)))
				.thenReturn(simpleConsentDtoListMock);

		// Act
		SimpleConsentDto response = sut.getConsent(consentIdMock);

		// Assert
		assertEquals(simpleConsentDtoMock, response);
	}

	@Test
	public void testGetPolicyId() throws SQLException {
		long consentIdMock = 1;
		LinkedList<PolicyIdDto> policyIdDtoListMock = new LinkedList<PolicyIdDto>();
		PolicyIdDto policyIdDtoMock = mock(PolicyIdDto.class);
		policyIdDtoListMock.add(policyIdDtoMock);
		when(policyIdDtoRowMapperMock.mapRow(isA(ResultSet.class), anyInt()))
				.thenReturn(policyIdDtoMock);
		when(sut.getJdbcTemplate()).thenReturn(jdbcTemplateMock);
		when(
				jdbcTemplateMock.query(eq(ConsentGetterImpl.SQL_GET_POLICY_ID),
						isA(Object[].class), eq(policyIdDtoRowMapperMock)))
				.thenReturn(policyIdDtoListMock);

		PolicyIdDto response = sut.getPolicyId(consentIdMock);

		assertEquals(policyIdDtoMock, response);
	}
}
