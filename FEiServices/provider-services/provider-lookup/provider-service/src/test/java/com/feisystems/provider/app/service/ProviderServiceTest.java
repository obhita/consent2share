package com.feisystems.provider.app.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.feisystems.provider.domain.Provider;
import com.feisystems.provider.domain.repository.ProviderRepository;
import com.feisystems.provider.service.ProviderServiceImpl;
import com.feisystems.provider.service.dto.ProviderDto;
import com.feisystems.provider.service.dto.ProvidersDto;
import com.feisystems.provider.service.mapper.ProviderMapper;
import com.feisystems.provider.test.ProviderServiceTestBase;
@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class ProviderServiceTest extends ProviderServiceTestBase {

	@Mock
	private ProviderRepository mockProviderRepository;

	@Mock
	private ProviderMapper mockProviderMapper;

	@InjectMocks
	private ProviderServiceImpl providerServiceImpl = new ProviderServiceImpl();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}


	@Before
	public void setUp() throws Exception {
		String d= "10/10/2010";
		List list = Mockito.spy(new ArrayList());
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		when(mockProviderRepository.findOne(anyString())).thenReturn(new Provider("9999", "Individual",d, d));
		Page mockedPage = mock(Page.class);

		when(mockedPage.getContent()).thenReturn(list);
		when(list.size()).thenReturn(4);

		when(mockProviderRepository.findAllByProviderGenderCodeLikeAndProviderBusinessPracticeLocationAddressPostalCodeLikeAndTaxonomyLikeAndProviderBusinessPracticeLocationAddressTelephoneNumberLikeAndProviderLastNameLikeAndProviderFirstNameLikeAndEntityTypeLikeAndProviderOrganizationNameLike(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), any(Pageable.class))).thenReturn(mockedPage);
		when(mockProviderRepository.findAllByProviderGenderCodeLikeAndProviderBusinessPracticeLocationAddressStateNameAndProviderBusinessPracticeLocationAddressCityNameLikeAndTaxonomyLikeAndProviderBusinessPracticeLocationAddressTelephoneNumberLikeAndProviderLastNameLikeAndProviderFirstNameLikeAndEntityTypeLikeAndProviderOrganizationNameLike(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(),anyString(), any(Pageable.class))).thenReturn(mockedPage);

		when(mockProviderMapper.map(any(Provider.class))).thenReturn(new ProviderDto("9999", "Individual", d, d));
		when(mockProviderMapper.mapToProviderDtoList(any(List.class))).thenReturn(list);

	}

	@Test
	public void testGetProvider() {
		ProviderDto provider = providerServiceImpl.getProvider("9999");
		assertNotNull(provider);
		assertEquals("9999", provider.getNpi());
	}

	@Test
	public void testGetByGenderCodeAndPostalCodeAndSpecialityAndTelephoneNumberAndLastNameAndFirstName() {

		Map<String, Object> providers = providerServiceImpl.getByGenderCodeAndPostalCodeAndSpecialityAndTelephoneNumberAndLastNameAndFirstNameAndEntityTypeAndProviderOrganizationName("m", "12345", "general", "%", "%", "%", "%", "%", "0");
		assertEquals(4,((List<ProvidersDto>)providers.get("results")).size());
	}

	@Test
	public void testGetByGenderCodeAndUSStateAbbreviationAndCityAndSpecialityAndTelephoneNumberAndLastNameAndFirstName() {

		Map<String, Object> providers = providerServiceImpl.getByGenderCodeAndUSStateAbbreviationAndCityAndSpecialityAndTelephoneNumberAndLastNameAndFirstNameAndEntityTypeAndProviderOrganizationName("f", "pa", "york", "substance abuse", "%", "%", "%", "%", "%", "0");
		assertEquals(4,((List<ProvidersDto>)providers.get("results")).size());
	}

}
