package com.feisystems.provider.app.service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.feisystems.provider.Provider;
import com.feisystems.provider.dtos.ProviderDto;
import com.feisystems.provider.mappers.ProviderMapper;
import com.feisystems.provider.repository.ProviderRepository;
import com.feisystems.provider.services.ProviderServiceImpl;
import com.feisystems.provider.test.ProviderServiceTestBase;

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
		Date d = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		when(mockProviderRepository.findOne(anyString())).thenReturn(new Provider("9999", "Individual",formatter.format(d), formatter.format(d)));
		when(mockProviderRepository.findAllByProviderGenderCodeLikeAndProviderBusinessPracticeLocationAddressPostalCodeLikeAndTaxonomyLikeAndProviderBusinessPracticeLocationAddressTelephoneNumberLikeAndProviderLastNameLikeAndProviderFirstNameLikeAndEntityTypeLikeAndProviderOrganizationNameLike("m", "12345%", "general%", "%", "%", "%", "%", "%")).thenReturn(postalCodeReturn());		
		when(mockProviderRepository.findAllByProviderGenderCodeLikeAndProviderBusinessPracticeLocationAddressStateNameAndProviderBusinessPracticeLocationAddressCityNameLikeAndTaxonomyLikeAndProviderBusinessPracticeLocationAddressTelephoneNumberLikeAndProviderLastNameLikeAndProviderFirstNameLikeAndEntityTypeLikeAndProviderOrganizationNameLike("f", "pa", "york", "substance abuse%", "%", "%", "%", "%", "%")).thenReturn(cityStateReturn());
		
		when(mockProviderMapper.map(any(Provider.class))).thenReturn(new ProviderDto("9999", "Individual", d, d));
		when(mockProviderMapper.mapToProviderDtoList(any(List.class))).thenReturn(cityStateDtoReturn());
	}

	@Test
	public void testGetProvider() {
		ProviderDto provider = providerServiceImpl.getProvider("9999");
		assertNotNull(provider);
		assertEquals("9999", provider.getNpi());
	}

	@Test
	public void testGetByGenderCodeAndPostalCodeAndSpecialityAndTelephoneNumberAndLastNameAndFirstName() {
		List<ProviderDto> providers = providerServiceImpl.getByGenderCodeAndPostalCodeAndSpecialityAndTelephoneNumberAndLastNameAndFirstNameAndEntityTypeAndProviderOrganizationName("m", "12345", "general", "%", "%", "%", "%", "%");
		assertEquals(4,providers.size());
	}

	@Test
	public void testGetByGenderCodeAndUSStateAbbreviationAndCityAndSpecialityAndTelephoneNumberAndLastNameAndFirstName() {
		List<ProviderDto> providers = providerServiceImpl.getByGenderCodeAndUSStateAbbreviationAndCityAndSpecialityAndTelephoneNumberAndLastNameAndFirstNameAndEntityTypeAndProviderOrganizationName("f", "pa", "york", "substance abuse", "%", "%", "%", "%", "%");
		assertEquals(4,providers.size());
	}

}
