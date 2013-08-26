package com.feisystems.provider.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.feisystems.provider.Provider;

public interface ProviderRepository extends JpaRepository<Provider, String> {
	public List<Provider> findAllByProviderGenderCodeLikeAndProviderBusinessPracticeLocationAddressStateNameAndProviderBusinessPracticeLocationAddressCityNameLikeAndTaxonomyLikeAndProviderBusinessPracticeLocationAddressTelephoneNumberLikeAndProviderLastNameLikeAndProviderFirstNameLikeAndEntityTypeLikeAndProviderOrganizationNameLike(String genderCode, String usStateAbbreviation, String city, String taxonomy, String phone, String lastName, String firstName, String entityType, String providerOrganizationName);
	public List<Provider> findAllByProviderGenderCodeLikeAndProviderBusinessPracticeLocationAddressPostalCodeLikeAndTaxonomyLikeAndProviderBusinessPracticeLocationAddressTelephoneNumberLikeAndProviderLastNameLikeAndProviderFirstNameLikeAndEntityTypeLikeAndProviderOrganizationNameLike(
			String calculateGenderCode, String calculateZipcode,
			String calculateSearchString, String phone, String lastName,
			String firstName, String entityType,
			String orgnaizationName);
}
