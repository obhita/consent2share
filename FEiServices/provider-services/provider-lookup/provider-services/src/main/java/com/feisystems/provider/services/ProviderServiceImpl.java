package com.feisystems.provider.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.feisystems.provider.Provider;
import com.feisystems.provider.dtos.ProviderDto;
import com.feisystems.provider.mappers.ProviderMapper;
import com.feisystems.provider.repository.ProviderRepository;
import com.feisystems.provider.service.ProviderService;

/**
 * SQL implementation of {@code com.feisystems.provider.service.ProviderService}
 * .
 * 
 * @author Jason A. Hoppes
 * @see com.feisystems.provider.service.ProviderService
 */
@Component()
public class ProviderServiceImpl implements ProviderService {

	private static final long serialVersionUID = -5036892385834940777L;

	public ProviderServiceImpl() {
	}

	@Autowired
	private ProviderRepository providerRepository;
	@Autowired
	private ProviderMapper providerMapper;

	@Transactional(readOnly = true)
	@Override
	public ProviderDto getProvider(String npi) {
		if (npi == null) {
			throw new IllegalArgumentException(
					"npi is null in getProvider(String npi)");
		}
		return providerMapper.map(providerRepository.findOne(npi));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProviderDto> getByGenderCodeAndPostalCodeAndSpecialityAndTelephoneNumberAndLastNameAndFirstNameAndEntityTypeAndProviderOrganizationName(
			String genderCode, String postalCode, String taxonomy,
			String phone, String lastName, String firstName, String entityType, String providerOrganizationName) {
		List<Provider> repositoryList = (List<Provider>) providerRepository.findAllByProviderGenderCodeLikeAndProviderBusinessPracticeLocationAddressPostalCodeLikeAndTaxonomyLikeAndProviderBusinessPracticeLocationAddressTelephoneNumberLikeAndProviderLastNameLikeAndProviderFirstNameLikeAndEntityTypeLikeAndProviderOrganizationNameLike(
				calculateGenderCode(genderCode),
				calculateZipcode(postalCode),
				calculateSearchString(taxonomy), calculateSearchString(phone),
				lastName, firstName, calculateSearchString(entityType), calculateSearchString(providerOrganizationName));
		List<ProviderDto> map =  providerMapper
				.mapToProviderDtoList(repositoryList);
		return map;
	}

	@Override
	public List<ProviderDto> getByGenderCodeAndUSStateAbbreviationAndCityAndSpecialityAndTelephoneNumberAndLastNameAndFirstNameAndEntityTypeAndProviderOrganizationName(
			String genderCode, String usStateAbbreviation, String city,
			String taxonomy, String phone, String lastName, String firstName, String entityType, String providerOrganizationName) {
		List<ProviderDto> map = providerMapper.mapToProviderDtoList(providerRepository
				.findAllByProviderGenderCodeLikeAndProviderBusinessPracticeLocationAddressStateNameAndProviderBusinessPracticeLocationAddressCityNameAndTaxonomyLikeAndProviderBusinessPracticeLocationAddressTelephoneNumberLikeAndProviderLastNameLikeAndProviderFirstNameLikeAndEntityTypeLikeAndProviderOrganizationNameLike(
						calculateGenderCode(genderCode),
						usStateAbbreviation, calculateCity(city),
						calculateSearchString(taxonomy), calculateSearchString(phone),
						lastName, firstName, calculateSearchString(entityType), calculateSearchString(providerOrganizationName)));
		return map;
	}

	private String calculateZipcode(String original) {
		String result = null;
		if (original != null) {
			if (SEARCH_STRING.equals(original)) {
				return original;
			}
			result = original.replaceAll("-", "");
			if (result.length() < 9 && result.length() > 2) {
				result = original + SEARCH_STRING;
			}
		}
		return result;
	}

	private String calculateSearchString(String original) {
		String result = null;
		if (original != null) {
			String unmasked = original.replaceAll("_", " ");
			if (SEARCH_STRING.equals(unmasked)) {
				return unmasked;
			} else {
				if (unmasked.length() > 2) {
					result = unmasked + SEARCH_STRING;
				}
			}
		}
		return result;
	}

	private String calculateGenderCode(String gender) {
		if (gender == null
				|| !(gender.equalsIgnoreCase("m")
						|| gender.equalsIgnoreCase("f")
						|| gender.equalsIgnoreCase("male")
						|| gender.equalsIgnoreCase("female") || gender
							.equals("%"))) {
			throw new IllegalArgumentException(
					"Gender is null or is incorrect "
							+ "format.  Please see  "
							+ "com.feisystems.provider.service.ProviderService specifications.");
		}

		if (gender.length() > 1) {
			return gender.substring(0, 1).toLowerCase();
		}

		return gender.toLowerCase();
	}

	private String calculateCity(String original) {
		String result = null;
		if (original != null) {
			if (SEARCH_STRING.equals(original)) {
				return original;
			}
			result = original.replaceAll("_", " ");
		}
		return result;
	}

}
