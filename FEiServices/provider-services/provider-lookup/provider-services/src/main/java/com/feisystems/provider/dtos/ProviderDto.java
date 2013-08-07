package com.feisystems.provider.dtos;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


public class ProviderDto implements Serializable {

	private static final long serialVersionUID = 7208730575128032697L;
	
	@NotNull
	private String npi;
	
	private String entityType;
	
	@ConstructorProperties({})
	public ProviderDto() {
		super();
	}	
	
	@ConstructorProperties({"npi", "entityType", 
		"providerEnumerationDate", "lastUpdateDate"})
	public ProviderDto(String npi, String entityType,
			Date providerEnumerationDate, Date lastUpdateDate) {
		super();
		this.npi = npi;
		this.entityType = entityType;
		this.providerEnumerationDate = providerEnumerationDate;
		this.lastUpdateDate = lastUpdateDate;
	}



	private String replacementNpi = null;
	private String employerIdentificationNumber = null;
	private Boolean isSoleProprietor = false;
	private Boolean isOrganizationSubpart = false;
	private String parentOrganizationLbn = null;
	private String parentOrganizationTin = null;
	private String providerOrganizationName = null;
	private String providerLastName = null;
	private String providerFirstName = null;
	private String providerMiddleName = null;
	private String providerNamePrefixText = null;
	private String providerNameSuffixText = null;
	private String providerCredentialText = null;
	private String providerFirstLineBusinessMailingAddress = null;
	private String providerSecondLineBusinessMailingAddress = null;
	private String providerBusinessMailingAddressCityName = null;
	private String providerBusinessMailingAddressStateName = null;
	@Pattern(regexp = "(\\b[0-9]{5}\\-[0-9]{4}\\b|\\b[0-9]{5}\\b|\\b[0-9]{9}\\b|\\b[0-9]{5}\\b)")
	private String providerBusinessMailingAddressPostalCode = null;
	private String providerBusinessMailingAddressCountryCode = null;
	private String providerBusinessMailingAddressTelephoneNumber = null;
	private String providerBusinessMailingAddressFaxNumber = null;
	private String providerFirstLineBusinessPracticeLocationAddress = null;
	private String providerSecondLineBusinessPracticeLocationAddress = null;
	private String providerBusinessPracticeLocationAddressCityName = null;
	private String providerBusinessPracticeLocationAddressStateName = null;
	@Pattern(regexp = "(\\b[0-9]{5}\\-[0-9]{4}\\b|\\b[0-9]{5}\\b|\\b[0-9]{9}\\b|\\b[0-9]{5}\\b)")
	private String providerBusinessPracticeLocationAddressPostalCode = null;
	private String providerBusinessPracticeLocationAddressCountryCode = null;
	private String providerBusinessPracticeLocationAddressTelephoneNumber = null;
	private String providerBusinessPracticeLocationAddressFaxNumber = null;
	
	@NotNull
	private Date providerEnumerationDate;
	
	@NotNull
	private Date lastUpdateDate = null;
	
	private String npideactivationReasonCode = null;
	private String npideactivationReason = null;
	private Date npideactivationDate = null;
	private Date npireactivationDate = null;
	private Character providerGenderCode = null;
	private String providerGender = null;
	private String authorizedOfficialLastName = null;
	private String authorizedOfficialFirstName = null;
	private String authorizedOfficialMiddleName = null;
	private String authorizedOfficialTitleorPosition = null;
	private String authorizedOfficialNamePrefixText = null;
	private String authorizedOfficialNameSuffixText = null;
	private String authorizedOfficialCredentialText = null;
	private String authorizedOfficialTelephoneNumber = null;
	private String healthcareProviderTaxonomyCode_1 = null;
	private String healthcareProviderTaxonomy_1 = null;
	private String providerLicenseNumber_1 = null;
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
	public Date getProviderEnumerationDate() {
		return providerEnumerationDate;
	}
	public void setProviderEnumerationDate(Date providerEnumerationDate) {
		this.providerEnumerationDate = providerEnumerationDate;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
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
	public Date getNpideactivationDate() {
		return npideactivationDate;
	}
	public void setNpideactivationDate(Date npideactivationDate) {
		this.npideactivationDate = npideactivationDate;
	}
	public Date getNpireactivationDate() {
		return npireactivationDate;
	}
	public void setNpireactivationDate(Date npireactivationDate) {
		this.npireactivationDate = npireactivationDate;
	}
	public Character getProviderGenderCode() {
		return providerGenderCode;
	}
	public void setProviderGenderCode(Character providerGenderCode) {
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
		return healthcareProviderTaxonomy_1;
	}
	public void setHealthcareProviderTaxonomy_1(String healthcareProviderTaxonomy_1) {
		this.healthcareProviderTaxonomy_1 = healthcareProviderTaxonomy_1;
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


	@Override
	public String toString() {
		return "ProviderDto [npi=" + npi + ", entityType=" + entityType
				+ ", replacementNpi=" + replacementNpi
				+ ", employerIdentificationNumber="
				+ employerIdentificationNumber + ", isSoleProprietor="
				+ isSoleProprietor + ", isOrganizationSubpart="
				+ isOrganizationSubpart + ", parentOrganizationLbn="
				+ parentOrganizationLbn + ", parentOrganizationTin="
				+ parentOrganizationTin + ", providerOrganizationName="
				+ providerOrganizationName + ", providerLastName="
				+ providerLastName + ", providerFirstName=" + providerFirstName
				+ ", providerMiddleName=" + providerMiddleName
				+ ", providerNamePrefixText=" + providerNamePrefixText
				+ ", providerNameSuffixText=" + providerNameSuffixText
				+ ", providerCredentialText=" + providerCredentialText
				+ ", providerFirstLineBusinessMailingAddress="
				+ providerFirstLineBusinessMailingAddress
				+ ", providerSecondLineBusinessMailingAddress="
				+ providerSecondLineBusinessMailingAddress
				+ ", providerBusinessMailingAddressCityName="
				+ providerBusinessMailingAddressCityName
				+ ", providerBusinessMailingAddressStateName="
				+ providerBusinessMailingAddressStateName
				+ ", providerBusinessMailingAddressPostalCode="
				+ providerBusinessMailingAddressPostalCode
				+ ", providerBusinessMailingAddressCountryCode="
				+ providerBusinessMailingAddressCountryCode
				+ ", providerBusinessMailingAddressTelephoneNumber="
				+ providerBusinessMailingAddressTelephoneNumber
				+ ", providerBusinessMailingAddressFaxNumber="
				+ providerBusinessMailingAddressFaxNumber
				+ ", providerFirstLineBusinessPracticeLocationAddress="
				+ providerFirstLineBusinessPracticeLocationAddress
				+ ", providerSecondLineBusinessPracticeLocationAddress="
				+ providerSecondLineBusinessPracticeLocationAddress
				+ ", providerBusinessPracticeLocationAddressCityName="
				+ providerBusinessPracticeLocationAddressCityName
				+ ", providerBusinessPracticeLocationAddressStateName="
				+ providerBusinessPracticeLocationAddressStateName
				+ ", providerBusinessPracticeLocationAddressPostalCode="
				+ providerBusinessPracticeLocationAddressPostalCode
				+ ", providerBusinessPracticeLocationAddressCountryCode="
				+ providerBusinessPracticeLocationAddressCountryCode
				+ ", providerBusinessPracticeLocationAddressTelephoneNumber="
				+ providerBusinessPracticeLocationAddressTelephoneNumber
				+ ", providerBusinessPracticeLocationAddressFaxNumber="
				+ providerBusinessPracticeLocationAddressFaxNumber
				+ ", providerEnumerationDate=" + providerEnumerationDate
				+ ", lastUpdateDate=" + lastUpdateDate
				+ ", npideactivationReasonCode=" + npideactivationReasonCode
				+ ", npideactivationReason=" + npideactivationReason
				+ ", npideactivationDate=" + npideactivationDate
				+ ", npireactivationDate=" + npireactivationDate
				+ ", providerGenderCode=" + providerGenderCode
				+ ", providerGender=" + providerGender
				+ ", authorizedOfficialLastName=" + authorizedOfficialLastName
				+ ", authorizedOfficialFirstName="
				+ authorizedOfficialFirstName
				+ ", authorizedOfficialMiddleName="
				+ authorizedOfficialMiddleName
				+ ", authorizedOfficialTitleorPosition="
				+ authorizedOfficialTitleorPosition
				+ ", authorizedOfficialNamePrefixText="
				+ authorizedOfficialNamePrefixText
				+ ", authorizedOfficialNameSuffixText="
				+ authorizedOfficialNameSuffixText
				+ ", authorizedOfficialCredentialText="
				+ authorizedOfficialCredentialText
				+ ", authorizedOfficialTelephoneNumber="
				+ authorizedOfficialTelephoneNumber
				+ ", healthcareProviderTaxonomyCode_1="
				+ healthcareProviderTaxonomyCode_1
				+ ", healthcareProviderTaxonomy_1="
				+ healthcareProviderTaxonomy_1 
				+ ", providerLicenseNumber_1="
				+ providerLicenseNumber_1
				+ ", providerLicenseNumberStateCode_1="
				+ providerLicenseNumberStateCode_1 + "]";
	}

	
}
