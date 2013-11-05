package com.feisystems.provider;

import java.beans.ConstructorProperties;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;




@Entity
@Table(name="npi")
public class Provider implements Serializable {
 
	private static final long serialVersionUID = 7208730575128032697L;
	
	@Id
	@Column(name="npi")
	@NotNull
	private String npi;
	
	
	@Column(name="`Entity Type`")
	@NotNull
	private String entityType;
	
	@ConstructorProperties({})
	public Provider() {
		super();
	}	
	
	@ConstructorProperties({"npi", "entityType'", 
		"providerEnumerationDate", "lastUpdateDate"})
	public Provider(String npi, String entityType,
			String providerEnumerationDate, String lastUpdateDate) {
		super();
		this.npi = npi;
		this.entityType = entityType;
		this.providerEnumerationDate = providerEnumerationDate;
		this.lastUpdateDate = lastUpdateDate;
	}

	@Column(name="`Replacement NPI`")
	private String replacementNpi = null;

	@Column(name="`Employer Identification Number (EIN)`")
	private String employerIdentificationNumber = null;
	
	@Column(name="`Is Sole Proprietor`")	
	private Boolean isSoleProprietor;

	@Column(name="`Is Organization Subpart`")	
	private Boolean isOrganizationSubpart;

	@Column(name="`Parent Organization LBN`")	
	private String parentOrganizationLbn = null;
	
	@Column(name="`Parent Organization TIN`")	
	private String parentOrganizationTin = null;
	
	@Column(name="`Provider Organization Name (Legal Business Name)`")	
	private String providerOrganizationName = null;

	@Column(name="`Provider Last Name (Legal Name)`")	
	private String providerLastName = null;

	@Column(name="`Provider First Name`")	
	private String providerFirstName = null;

	@Column(name="`Provider Middle Name`")	
	private String providerMiddleName = null;

	@Column(name="`Provider Name Prefix Text`")	
	private String providerNamePrefixText = null;

	@Column(name="`Provider Name Suffix Text`")	
	private String providerNameSuffixText = null;

	@Column(name="`Provider Credential Text`")	
	private String providerCredentialText = null;

	@Column(name="`Provider First Line Business Mailing Address`")	
	private String providerFirstLineBusinessMailingAddress = null;

	@Column(name="`Provider Second Line Business Mailing Address`")	
	private String providerSecondLineBusinessMailingAddress = null;

	@Column(name="`Provider Business Mailing Address City Name`")	
	private String providerBusinessMailingAddressCityName = null;

	@Column(name="`Provider Business Mailing Address State Name`")	
	private String providerBusinessMailingAddressStateName = null;

	@Column(name="`Provider Business Mailing Address Postal Code`")	
	private String providerBusinessMailingAddressPostalCode = null;

	@Column(name="`Provider Business Mailing Address Country Code`")	
	private String providerBusinessMailingAddressCountryCode = null;

	@Column(name="`Provider Business Mailing Address Telephone Number`")	
	private String providerBusinessMailingAddressTelephoneNumber = null;

	@Column(name="`Provider Business Mailing Address Fax Number`")	
	private String providerBusinessMailingAddressFaxNumber = null;

	@Column(name="`Provider First Line Business Practice Location Address`")	
	private String providerFirstLineBusinessPracticeLocationAddress = null;

	@Column(name="`Provider Second Line Business Practice Location Address`")	
	private String providerSecondLineBusinessPracticeLocationAddress = null;

	@Column(name="`Provider Business Practice Location Address City Name`")	
	private String providerBusinessPracticeLocationAddressCityName = null;

	@Column(name="`Provider Business Practice Location Address State Name`")	
	private String providerBusinessPracticeLocationAddressStateName = null;

	@Column(name="`Provider Business Practice Location Address Postal Code`")	
	private String providerBusinessPracticeLocationAddressPostalCode = null;

	@Column(name="`Provider Business Practice Location Address Country Code`")	
	private String providerBusinessPracticeLocationAddressCountryCode = null;

	@Column(name="`Provider Business Practice Location Address Telephone Number`")	
	private String providerBusinessPracticeLocationAddressTelephoneNumber = null;

	@Column(name="`Provider Business Practice Location Address Fax Number`")	
	private String providerBusinessPracticeLocationAddressFaxNumber = null;

	@NotNull
	@Column(name="`Provider Enumeration Date`")
	private String providerEnumerationDate;


	@NotNull
	@Column(name="`Last Update Date`")
	private String lastUpdateDate = null;

	@Column(name="`NPI Deactivation Reason Code`")	
	private String npideactivationReasonCode = null;

	@Column(name="`NPI Deactivation Reason`")	
	private String npideactivationReason = null;

	@Column(name="`NPI Deactivation Date`")	
	private String npideactivationDate = null;

	@Column(name="`NPI Reactivation Date`")	
	private String npireactivationDate = null;

	@Column(name="`Provider Gender Code`")	
	private String providerGenderCode = null;

	@Column(name="`Provider Gender`")	
	private String providerGender = null;

	@Column(name="`Authorized Official Last Name`")	
	private String authorizedOfficialLastName = null;

	@Column(name="`Authorized Official First Name`")	
	private String authorizedOfficialFirstName = null;

	@Column(name="`Authorized Official Middle Name`")	
	private String authorizedOfficialMiddleName = null;

	@Column(name="`Authorized Official Title or Position`")	
	private String authorizedOfficialTitleorPosition = null;

	@Column(name="`Authorized Official Name Prefix Text`")	
	private String authorizedOfficialNamePrefixText = null;

	@Column(name="`Authorized Official Name Suffix Text`")	
	private String authorizedOfficialNameSuffixText = null;

	@Column(name="`Authorized Official Credential Text`")	
	private String authorizedOfficialCredentialText = null;

	@Column(name="`Authorized Official Telephone Number`")	
	private String authorizedOfficialTelephoneNumber = null;

	@Column(name="`Healthcare Provider Taxonomy Code_1`")	
	private String healthcareProviderTaxonomyCode_1 = null;

	@Column(name="`Healthcare Provider Taxonomy_1`")	
	private String taxonomy = null;

	@Column(name="`Provider License Number_1`")	
	private String providerLicenseNumber_1 = null;

	@Column(name="`Provider License Number State Code_1`")	
	private String providerLicenseNumberStateCode_1 = null;


	public String getNpi() {
		return npi;
	}
	public void setNpi(String npi) {
		this.npi = npi;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public String getReplacementNpi() {
		return replacementNpi;
	}
	public void setReplacementNpi(String replacementNpi) {
		this.replacementNpi = replacementNpi;
	}
	public String getEmployerIdentificationNumber() {
		return employerIdentificationNumber;
	}
	public void setEmployerIdentificationNumber(String employerIdentificationNumber) {
		this.employerIdentificationNumber = employerIdentificationNumber;
	}
	public Boolean getIsSoleProprietor() {
		return isSoleProprietor;
	}
	public void setIsSoleProprietor(Boolean isSoleProprietor) {
		this.isSoleProprietor = isSoleProprietor;
	}
	public Boolean getIsOrganizationSubpart() {
		return isOrganizationSubpart;
	}
	public void setIsOrganizationSubpart(Boolean isOrganizationSubpart) {
		this.isOrganizationSubpart = isOrganizationSubpart;
	}
	public String getParentOrganizationLbn() {
		return parentOrganizationLbn;
	}
	public void setParentOrganizationLbn(String parentOrganizationLbn) {
		this.parentOrganizationLbn = parentOrganizationLbn;
	}
	public String getParentOrganizationTin() {
		return parentOrganizationTin;
	}
	public void setParentOrganizationTin(String parentOrganizationTin) {
		this.parentOrganizationTin = parentOrganizationTin;
	}
	public String getProviderOrganizationName() {
		return providerOrganizationName;
	}
	public void setProviderOrganizationName(String providerOrganizationName) {
		this.providerOrganizationName = providerOrganizationName;
	}
	public String getProviderLastName() {
		return providerLastName;
	}
	public void setProviderLastName(String providerLastName) {
		this.providerLastName = providerLastName;
	}
	public String getProviderFirstName() {
		return providerFirstName;
	}
	public void setProviderFirstName(String providerFirstName) {
		this.providerFirstName = providerFirstName;
	}
	public String getProviderMiddleName() {
		return providerMiddleName;
	}
	public void setProviderMiddleName(String providerMiddleName) {
		this.providerMiddleName = providerMiddleName;
	}
	public String getProviderNamePrefixText() {
		return providerNamePrefixText;
	}
	public void setProviderNamePrefixText(String providerNamePrefixText) {
		this.providerNamePrefixText = providerNamePrefixText;
	}
	public String getProviderNameSuffixText() {
		return providerNameSuffixText;
	}
	public void setProviderNameSuffixText(String providerNameSuffixText) {
		this.providerNameSuffixText = providerNameSuffixText;
	}
	public String getProviderCredentialText() {
		return providerCredentialText;
	}
	public void setProviderCredentialText(String providerCredentialText) {
		this.providerCredentialText = providerCredentialText;
	}

	public String getProviderFirstLineBusinessMailingAddress() {
		return providerFirstLineBusinessMailingAddress;
	}
	public void setProviderFirstLineBusinessMailingAddress(
			String providerFirstLineBusinessMailingAddress) {
		this.providerFirstLineBusinessMailingAddress = providerFirstLineBusinessMailingAddress;
	}
	public String getProviderSecondLineBusinessMailingAddress() {
		return providerSecondLineBusinessMailingAddress;
	}
	public void setProviderSecondLineBusinessMailingAddress(
			String providerSecondLineBusinessMailingAddress) {
		this.providerSecondLineBusinessMailingAddress = providerSecondLineBusinessMailingAddress;
	}
	public String getProviderBusinessMailingAddressCityName() {
		return providerBusinessMailingAddressCityName;
	}
	public void setProviderBusinessMailingAddressCityName(
			String providerBusinessMailingAddressCityName) {
		this.providerBusinessMailingAddressCityName = providerBusinessMailingAddressCityName;
	}
	public String getProviderBusinessMailingAddressStateName() {
		return providerBusinessMailingAddressStateName;
	}
	public void setProviderBusinessMailingAddressStateName(
			String providerBusinessMailingAddressStateName) {
		this.providerBusinessMailingAddressStateName = providerBusinessMailingAddressStateName;
	}
	public String getProviderBusinessMailingAddressPostalCode() {
		return providerBusinessMailingAddressPostalCode;
	}
	public void setProviderBusinessMailingAddressPostalCode(
			String providerBusinessMailingAddressPostalCode) {
		this.providerBusinessMailingAddressPostalCode = providerBusinessMailingAddressPostalCode;
	}
	public String getProviderBusinessMailingAddressCountryCode() {
		return providerBusinessMailingAddressCountryCode;
	}
	public void setProviderBusinessMailingAddressCountryCode(
			String providerBusinessMailingAddressCountryCode) {
		this.providerBusinessMailingAddressCountryCode = providerBusinessMailingAddressCountryCode;
	}
	public String getProviderBusinessMailingAddressTelephoneNumber() {
		return providerBusinessMailingAddressTelephoneNumber;
	}
	public void setProviderBusinessMailingAddressTelephoneNumber(
			String providerBusinessMailingAddressTelephoneNumber) {
		this.providerBusinessMailingAddressTelephoneNumber = providerBusinessMailingAddressTelephoneNumber;
	}
	public String getProviderBusinessMailingAddressFaxNumber() {
		return providerBusinessMailingAddressFaxNumber;
	}
	public void setProviderBusinessMailingAddressFaxNumber(
			String providerBusinessMailingAddressFaxNumber) {
		this.providerBusinessMailingAddressFaxNumber = providerBusinessMailingAddressFaxNumber;
	}
	public String getProviderFirstLineBusinessPracticeLocationAddress() {
		return providerFirstLineBusinessPracticeLocationAddress;
	}
	public void setProviderFirstLineBusinessPracticeLocationAddress(
			String providerFirstLineBusinessPracticeLocationAddress) {
		this.providerFirstLineBusinessPracticeLocationAddress = providerFirstLineBusinessPracticeLocationAddress;
	}
	public String getProviderSecondLineBusinessPracticeLocationAddress() {
		return providerSecondLineBusinessPracticeLocationAddress;
	}
	public void setProviderSecondLineBusinessPracticeLocationAddress(
			String providerSecondLineBusinessPracticeLocationAddress) {
		this.providerSecondLineBusinessPracticeLocationAddress = providerSecondLineBusinessPracticeLocationAddress;
	}
	public String getProviderBusinessPracticeLocationAddressCityName() {
		return providerBusinessPracticeLocationAddressCityName;
	}
	public void setProviderBusinessPracticeLocationAddressCityName(
			String providerBusinessPracticeLocationAddressCityName) {
		this.providerBusinessPracticeLocationAddressCityName = providerBusinessPracticeLocationAddressCityName;
	}
	public String getProviderBusinessPracticeLocationAddressStateName() {
		return providerBusinessPracticeLocationAddressStateName;
	}
	public void setProviderBusinessPracticeLocationAddressStateName(
			String providerBusinessPracticeLocationAddressStateName) {
		this.providerBusinessPracticeLocationAddressStateName = providerBusinessPracticeLocationAddressStateName;
	}
	public String getProviderBusinessPracticeLocationAddressPostalCode() {
		return providerBusinessPracticeLocationAddressPostalCode;
	}
	public void setProviderBusinessPracticeLocationAddressPostalCode(
			String providerBusinessPracticeLocationAddressPostalCode) {
		this.providerBusinessPracticeLocationAddressPostalCode = providerBusinessPracticeLocationAddressPostalCode;
	}
	public String getProviderBusinessPracticeLocationAddressCountryCode() {
		return providerBusinessPracticeLocationAddressCountryCode;
	}
	public void setProviderBusinessPracticeLocationAddressCountryCode(
			String providerBusinessPracticeLocationAddressCountryCode) {
		this.providerBusinessPracticeLocationAddressCountryCode = providerBusinessPracticeLocationAddressCountryCode;
	}
	public String getProviderBusinessPracticeLocationAddressTelephoneNumber() {
		return providerBusinessPracticeLocationAddressTelephoneNumber;
	}
	public void setProviderBusinessPracticeLocationAddressTelephoneNumber(
			String providerBusinessPracticeLocationAddressTelephoneNumber) {
		this.providerBusinessPracticeLocationAddressTelephoneNumber = providerBusinessPracticeLocationAddressTelephoneNumber;
	}
	public String getProviderBusinessPracticeLocationAddressFaxNumber() {
		return providerBusinessPracticeLocationAddressFaxNumber;
	}
	public void setProviderBusinessPracticeLocationAddressFaxNumber(
			String providerBusinessPracticeLocationAddressFaxNumber) {
		this.providerBusinessPracticeLocationAddressFaxNumber = providerBusinessPracticeLocationAddressFaxNumber;
	}
	
	public String getProviderEnumerationDate() {
		return providerEnumerationDate;
	}
	public void setProviderEnumerationDate(String providerEnumerationDate) {
		this.providerEnumerationDate = providerEnumerationDate;
	}
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	public String getNpideactivationReasonCode() {
		return npideactivationReasonCode;
	}
	public void setNpideactivationReasonCode(String npideactivationReasonCode) {
		this.npideactivationReasonCode = npideactivationReasonCode;
	}
	public String getNpideactivationReason() {
		return npideactivationReason;
	}
	public void setNpideactivationReason(String npideactivationReason) {
		this.npideactivationReason = npideactivationReason;
	}
	
	public String getNpideactivationDate() {
		return npideactivationDate;
	}
	public void setNpideactivationDate(String npideactivationDate) {
		this.npideactivationDate = npideactivationDate;
	}
	
	public String getNpireactivationDate() {
		return npireactivationDate;
	}
	public void setNpireactivationDate(String npireactivationDate) {
		this.npireactivationDate = npireactivationDate;
	}
	public String getProviderGenderCode() {
		return providerGenderCode;
	}
	public void setProviderGenderCode(String providerGenderCode) {
			this.providerGenderCode = providerGenderCode;
	}
	public String getProviderGender() {
		return providerGender;
	}
	public void setProviderGender(String providerGender) {
		this.providerGender = providerGender;
	}
	public String getAuthorizedOfficialLastName() {
		return authorizedOfficialLastName;
	}
	public void setAuthorizedOfficialLastName(String authorizedOfficialLastName) {
		this.authorizedOfficialLastName = authorizedOfficialLastName;
	}
	public String getAuthorizedOfficialFirstName() {
		return authorizedOfficialFirstName;
	}
	public void setAuthorizedOfficialFirstName(String authorizedOfficialFirstName) {
		this.authorizedOfficialFirstName = authorizedOfficialFirstName;
	}
	public String getAuthorizedOfficialMiddleName() {
		return authorizedOfficialMiddleName;
	}
	public void setAuthorizedOfficialMiddleName(String authorizedOfficialMiddleName) {
		this.authorizedOfficialMiddleName = authorizedOfficialMiddleName;
	}
	public String getAuthorizedOfficialTitleorPosition() {
		return authorizedOfficialTitleorPosition;
	}
	public void setAuthorizedOfficialTitleorPosition(
			String authorizedOfficialTitleorPosition) {
		this.authorizedOfficialTitleorPosition = authorizedOfficialTitleorPosition;
	}
	public String getAuthorizedOfficialNamePrefixText() {
		return authorizedOfficialNamePrefixText;
	}
	public void setAuthorizedOfficialNamePrefixText(
			String authorizedOfficialNamePrefixText) {
		this.authorizedOfficialNamePrefixText = authorizedOfficialNamePrefixText;
	}
	public String getAuthorizedOfficialNameSuffixText() {
		return authorizedOfficialNameSuffixText;
	}
	public void setAuthorizedOfficialNameSuffixText(
			String authorizedOfficialNameSuffixText) {
		this.authorizedOfficialNameSuffixText = authorizedOfficialNameSuffixText;
	}
	public String getAuthorizedOfficialCredentialText() {
		return authorizedOfficialCredentialText;
	}
	public void setAuthorizedOfficialCredentialText(
			String authorizedOfficialCredentialText) {
		this.authorizedOfficialCredentialText = authorizedOfficialCredentialText;
	}
	public String getAuthorizedOfficialTelephoneNumber() {
		return authorizedOfficialTelephoneNumber;
	}
	public void setAuthorizedOfficialTelephoneNumber(
			String authorizedOfficialTelephoneNumber) {
		this.authorizedOfficialTelephoneNumber = authorizedOfficialTelephoneNumber;
	}
	public String getHealthcareProviderTaxonomyCode_1() {
		return healthcareProviderTaxonomyCode_1;
	}
	public void setHealthcareProviderTaxonomyCode_1(
			String healthcareProviderTaxonomyCode_1) {
		this.healthcareProviderTaxonomyCode_1 = healthcareProviderTaxonomyCode_1;
	}
	public String getHealthcareProviderTaxonomy_1() {
		return taxonomy;
	}
	public void setHealthcareProviderTaxonomy_1(String healthcareProviderTaxonomy_1) {
		this.taxonomy = healthcareProviderTaxonomy_1;
	}
	public String getProviderLicenseNumber_1() {
		return providerLicenseNumber_1;
	}
	public void setProviderLicenseNumber_1(String providerLicenseNumber_1) {
		this.providerLicenseNumber_1 = providerLicenseNumber_1;
	}
	public String getProviderLicenseNumberStateCode_1() {
		return providerLicenseNumberStateCode_1;
	}
	public void setProviderLicenseNumberStateCode_1(
			String providerLicenseNumberStateCode_1) {
		this.providerLicenseNumberStateCode_1 = providerLicenseNumberStateCode_1;
	}

	
}
