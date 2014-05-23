package gov.samhsa.acs.documentsegmentation.valueset;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.documentsegmentation.valueset.dto.ValueSetQueryDto;
import gov.samhsa.acs.documentsegmentation.valueset.dto.ValueSetQueryListDto;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class ValueSetServiceImplTest {

	private final String ENDPOINT_ADDRESS_MOCK = "ENDPOINT_ADDRESS_MOCK";
	private final String CODE_MOCK = "CODE_MOCK";
	private final String CODE_SYSTEM_MOCK = "CODE_SYSTEM_MOCK";
	private final String EXPECTED_REST_URL = "ENDPOINT_ADDRESS_MOCK?code={code}&codeSystemOid={codeSystemOid}";
	private final String EXPECTED_REST_LIST_URL = "ENDPOINT_ADDRESS_MOCK/rest";

	@Mock
	private RestTemplate restTemplateMock;

	@InjectMocks
	@Spy
	private ValueSetServiceImpl sut;

	@Before
	public void setUp() throws Exception {
		when(sut.configureRestTemplate()).thenReturn(restTemplateMock);
		ReflectionTestUtils.setField(sut, "endpointAddress",
				ENDPOINT_ADDRESS_MOCK);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testLookupValueSetCategories() {
		// Arrange
		ValueSetQueryDto queryMock = mock(ValueSetQueryDto.class);
		@SuppressWarnings("rawtypes")
		Set valueSetCategorySetMock = mock(Set.class);
		when(
				restTemplateMock.getForObject(eq(EXPECTED_REST_URL),
						eq(ValueSetQueryDto.class), isA(Map.class)))
				.thenReturn(queryMock);
		when(queryMock.getVsCategoryCodes())
				.thenReturn(valueSetCategorySetMock);

		// Act
		@SuppressWarnings("rawtypes")
		Set response = sut
				.lookupValueSetCategories(CODE_MOCK, CODE_SYSTEM_MOCK);

		// Assert
		assertEquals(valueSetCategorySetMock, response);
	}
	
/*	@SuppressWarnings("unchecked")
	@Test
	public void testRestfulValueSetCategories() {
		// Arrange
		ValueSetQueryListDto queryMock = mock(ValueSetQueryListDto.class);
		@SuppressWarnings("rawtypes")
		Set<ValueSetQueryDto> valueSetCategorySetMock = mock(Set.class);

		when(
				restTemplateMock.postForObject(eq(EXPECTED_REST_LIST_URL),queryMock,
						eq(ValueSetQueryListDto.class), isA(ValueSetQueryListDto.class)))
				.thenReturn(queryMock);
		when(queryMock.getValueSetQueryDtos())
				.thenReturn(valueSetCategorySetMock);

		// Act
		@SuppressWarnings("rawtypes")
		Set response = sut
				.lookupValueSetCategories(CODE_MOCK, CODE_SYSTEM_MOCK);

		// Assert
		assertEquals(valueSetCategorySetMock, response);
	}	*/
}
