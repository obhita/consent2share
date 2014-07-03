package com.feisystems.provider.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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

	@Override
	public Map<String, Object> getByGenderCodeAndPostalCodeAndSpecialityAndTelephoneNumberAndLastNameAndFirstNameAndEntityTypeAndProviderOrganizationName(
			String genderCode, String postalCode, String taxonomy,
			String phone, String lastName, String firstName, String entityType, String providerOrganizationName, String pageNumber) {
		PageRequest page = new PageRequest(Integer.parseInt(pageNumber), 10, Direction.ASC, "providerLastName");
		
		Page<Provider> pages = providerRepository.findAllByProviderGenderCodeLikeAndProviderBusinessPracticeLocationAddressPostalCodeLikeAndTaxonomyLikeAndProviderBusinessPracticeLocationAddressTelephoneNumberLikeAndProviderLastNameLikeAndProviderFirstNameLikeAndEntityTypeLikeAndProviderOrganizationNameLike(
				calculateGenderCode(genderCode),
				calculateZipcode(postalCode),
				calculateSearchString(taxonomy), calculateSearchString(phone),
				searchPartialString(lastName), searchPartialString(firstName), calculateSearchString(entityType), calculateSearchString(providerOrganizationName), page);

		List<ProviderDto> results = providerMapper.mapToProviderDtoList(pages.getContent());
		
		Map<String, Object> pageResultsMap = new HashMap<String, Object>();
		pageResultsMap.put("results", results);
		pageResultsMap.put("totalNumberOfProviders", pages.getTotalElements());
		pageResultsMap.put("totalPages", pages.getTotalPages());
		pageResultsMap.put("itemsPerPage", pages.getSize());
		pageResultsMap.put("currentPage", pages.getNumber());
		
		return pageResultsMap;
	}

	@Override
	public Map<String, Object> getByGenderCodeAndUSStateAbbreviationAndCityAndSpecialityAndTelephoneNumberAndLastNameAndFirstNameAndEntityTypeAndProviderOrganizationName(
			String genderCode, String usStateAbbreviation, String city,
			String taxonomy, String phone, String lastName, String firstName, String entityType, String providerOrganizationName, String pageNumber) {
		PageRequest page = new PageRequest(Integer.parseInt(pageNumber), 10, Direction.ASC, "providerLastName");
		
		Page<Provider> pages = providerRepository
				.findAllByProviderGenderCodeLikeAndProviderBusinessPracticeLocationAddressStateNameAndProviderBusinessPracticeLocationAddressCityNameLikeAndTaxonomyLikeAndProviderBusinessPracticeLocationAddressTelephoneNumberLikeAndProviderLastNameLikeAndProviderFirstNameLikeAndEntityTypeLikeAndProviderOrganizationNameLike(
						calculateGenderCode(genderCode),
						usStateAbbreviation, calculateCity(city),
						calculateSearchString(taxonomy), calculateSearchString(phone),
						searchPartialString(lastName), searchPartialString(firstName), calculateSearchString(entityType), calculateSearchString(providerOrganizationName), page);
		
		List<ProviderDto> results = providerMapper.mapToProviderDtoList(pages.getContent());
		
		Map<String, Object> pageResultsMap = new HashMap<String, Object>();
		pageResultsMap.put("results", results);
		pageResultsMap.put("totalNumberOfProviders", pages.getTotalElements());
		pageResultsMap.put("totalPages", pages.getTotalPages());
		pageResultsMap.put("itemsPerPage", pages.getSize());
		pageResultsMap.put("currentPage", pages.getNumber());
		
		return pageResultsMap;
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
		
		return searchPartialString(result);
	}
	
	
	/**
	 * Allows partial searches for provider search. Used by jpa repository findByFIELD_NAMELike method 
	 * Ex: "Balt" returns "Baltimore"
	 *
	 * @param searchField the search field
	 * @return the string
	 */
	private String searchPartialString(String searchField) {
		return new StringBuilder().append(SEARCH_STRING).append(searchField).append(SEARCH_STRING).toString();
	}

}
