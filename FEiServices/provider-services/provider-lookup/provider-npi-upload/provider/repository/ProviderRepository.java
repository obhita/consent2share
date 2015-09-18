package com.feisystems.provider.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.feisystems.provider.domain.Provider;

public interface ProviderRepository extends JpaRepository<Provider, String> {
	public Page<Provider> findAllByProviderGenderCodeLikeAndProviderBusinessPracticeLocationAddressStateNameAndProviderBusinessPracticeLocationAddressCityNameLikeAndTaxonomyLikeAndProviderBusinessPracticeLocationAddressTelephoneNumberLikeAndProviderLastNameLikeAndProviderFirstNameLikeAndEntityTypeLikeAndProviderOrganizationNameLike(String genderCode, String usStateAbbreviation, String city, String taxonomy, String phone, String lastName, String firstName, String entityType, String providerOrganizationName, Pageable pageable);
	public Page<Provider> findAllByProviderGenderCodeLikeAndProviderBusinessPracticeLocationAddressPostalCodeLikeAndTaxonomyLikeAndProviderBusinessPracticeLocationAddressTelephoneNumberLikeAndProviderLastNameLikeAndProviderFirstNameLikeAndEntityTypeLikeAndProviderOrganizationNameLike(
			String calculateGenderCode, String calculateZipcode,
			String calculateSearchString, String phone, String lastName,
			String firstName, String entityType,
			String orgnaizationName, Pageable page);
}
