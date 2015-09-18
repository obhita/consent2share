package com.feisystems.provider.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.feisystems.provider.domain.Provider;

public interface ProviderRepository extends JpaRepository<Provider, String> {
	@Query("SELECT DISTINCT p FROM Provider p WHERE p.providerGenderCode LIKE ?1 AND p.providerBusinessPracticeLocationAddressStateName LIKE ?2 AND p.providerBusinessPracticeLocationAddressCityName LIKE ?3 AND "
			+ "p.taxonomy LIKE ?4 AND p.providerBusinessPracticeLocationAddressTelephoneNumber LIKE ?5 AND p.providerFirstName LIKE ?6 AND p.entityType LIKE ?7 AND "
			+ "p.providerLastName LIKE ?8 AND p.providerOrganizationName LIKE ?9 ")
	public Page<Provider> findAllByProviderGenderCodeLikeAndProviderBusinessPracticeLocationAddressStateNameAndProviderBusinessPracticeLocationAddressCityNameLikeAndTaxonomyLikeAndProviderBusinessPracticeLocationAddressTelephoneNumberLikeAndProviderLastNameLikeAndProviderFirstNameLikeAndEntityTypeLikeAndProviderOrganizationNameLike(
		String genderCode, String usStateAbbreviation, String city,
		String taxonomy, String phone, String firstName, String entityType,
		String lastName, String providerOrganizationName, Pageable pageable);

	@Query("SELECT DISTINCT p FROM Provider p WHERE p.providerGenderCode LIKE ?1 AND p.providerBusinessPracticeLocationAddressStateName LIKE ?2 AND p.providerBusinessPracticeLocationAddressCityName LIKE ?3 AND "
			+ "p.taxonomy LIKE ?4 AND p.providerBusinessPracticeLocationAddressTelephoneNumber LIKE ?5 AND p.providerFirstName LIKE ?6 AND p.entityType LIKE ?7 AND "
			+ "(p.providerLastName LIKE ?8 OR p.providerOrganizationName LIKE ?8) ")
	public Page<Provider> findAllByProviderGenderCodeLikeAndProviderBusinessPracticeLocationAddressStateNameAndProviderBusinessPracticeLocationAddressCityNameLikeAndTaxonomyLikeAndProviderBusinessPracticeLocationAddressTelephoneNumberLikeAndProviderLastNameLikeAndProviderFirstNameLikeAndEntityTypeLikeAndProviderLastNameLikeOrProviderOrganizationNameLike(
			String genderCode, String usStateAbbreviation, String city,
			String taxonomy, String phone, String firstName, String entityType,
			String lastnameOrFacilityName, Pageable pageable);

	@Query("SELECT DISTINCT p FROM Provider p WHERE p.providerGenderCode LIKE ?1 AND p.providerBusinessPracticeLocationAddressPostalCode LIKE ?2 AND "
			+ "p.taxonomy LIKE ?3 AND p.providerBusinessPracticeLocationAddressTelephoneNumber LIKE ?4 AND p.providerFirstName LIKE ?5 AND p.entityType LIKE ?6 AND "
			+ "p.providerLastName LIKE ?7 AND p.providerOrganizationName LIKE ?8 ")
	public Page<Provider> findAllByProviderGenderCodeLikeAndProviderBusinessPracticeLocationAddressPostalCodeLikeAndTaxonomyLikeAndProviderBusinessPracticeLocationAddressTelephoneNumberLikeAndProviderLastNameLikeAndProviderFirstNameLikeAndEntityTypeLikeAndProviderOrganizationNameLike(
			String calculateGenderCode, String calculateZipcode,
			String calculateSearchString, String phone, String firstName,
			String entityType, String lastName, String orgnaizationName,
			Pageable page);

	@Query("SELECT DISTINCT p FROM Provider p WHERE p.providerGenderCode LIKE ?1 AND p.providerBusinessPracticeLocationAddressPostalCode LIKE ?2 AND "
			+ "p.taxonomy LIKE ?3 AND p.providerBusinessPracticeLocationAddressTelephoneNumber LIKE ?4 AND p.providerFirstName LIKE ?5 AND p.entityType LIKE ?6 AND "
			+ "(p.providerLastName LIKE ?7 OR p.providerOrganizationName LIKE ?7) ")
	public Page<Provider> findAllByProviderGenderCodeLikeAndProviderBusinessPracticeLocationAddressPostalCodeLikeAndTaxonomyLikeAndProviderBusinessPracticeLocationAddressTelephoneNumberLikeAndProviderLastNameLikeAndProviderFirstNameLikeAndEntityTypeLikeAndProviderLastNameLikeOrProviderOrganizationNameLike(
			String calculateGenderCode, String calculateZipcode,
			String calculateSearchString, String phone, String firstName,
			String entityType, String lastnameOrFacilityName, Pageable page);
}
