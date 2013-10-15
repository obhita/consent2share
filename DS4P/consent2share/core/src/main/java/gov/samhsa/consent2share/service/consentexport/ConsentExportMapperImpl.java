package gov.samhsa.consent2share.service.consentexport;

import gov.samhsa.consent.ConsentDto;
import gov.samhsa.consent.IndividualProviderDto;
import gov.samhsa.consent.OrganizationalProviderDto;
import gov.samhsa.consent.PatientDto;
import gov.samhsa.consent.TypeCodesDto;
import gov.samhsa.consent2share.domain.consent.Consent;
import gov.samhsa.consent2share.domain.consent.ConsentDoNotShareClinicalDocumentSectionTypeCode;
import gov.samhsa.consent2share.domain.consent.ConsentDoNotShareClinicalDocumentTypeCode;
import gov.samhsa.consent2share.domain.consent.ConsentDoNotShareSensitivityPolicyCode;
import gov.samhsa.consent2share.domain.consent.ConsentIndividualProviderDisclosureIsMadeTo;
import gov.samhsa.consent2share.domain.consent.ConsentIndividualProviderPermittedToDisclose;
import gov.samhsa.consent2share.domain.consent.ConsentOrganizationalProviderDisclosureIsMadeTo;
import gov.samhsa.consent2share.domain.consent.ConsentOrganizationalProviderPermittedToDisclose;
import gov.samhsa.consent2share.domain.consent.ConsentShareForPurposeOfUseCode;
import gov.samhsa.consent2share.domain.reference.ClinicalConceptCode;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsentExportMapperImpl implements ConsentExportMapper {
	
	/** The model mapper. */
	@Autowired
	ModelMapper modelMapper;
	
	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ConsentDto map(Consent consent) {		
		ConsentDto consentDto = new ConsentDto();
		
		consentDto.setConsentReferenceid(consent.getConsentReferenceId());

		PatientDto patientDto = modelMapper.map(
				consent.getPatient(), PatientDto.class);
		consentDto.setPatientDto(patientDto);

		if (consent.getLegalRepresentative() != null) {
			PatientDto legalRepresentativeDto = modelMapper.map(
					consent.getLegalRepresentative(), PatientDto.class);
			consentDto.setLegalRepresentative(legalRepresentativeDto);
		}

		Set<IndividualProviderDto> indiprovidersPermittedToDisclose = new HashSet<IndividualProviderDto>();

		for (ConsentIndividualProviderPermittedToDisclose pptd : consent
				.getProvidersPermittedToDisclose()) {

			IndividualProviderDto iptd = modelMapper.map(
					pptd.getIndividualProvider(),
					IndividualProviderDto.class);
			indiprovidersPermittedToDisclose.add(iptd);
		}
		consentDto
				.setProvidersPermittedToDisclose(indiprovidersPermittedToDisclose);

		Set<IndividualProviderDto> indiprovidersDisclosureIsMadeTo = new HashSet<IndividualProviderDto>();

		for (ConsentIndividualProviderDisclosureIsMadeTo pdmt : consent
				.getProvidersDisclosureIsMadeTo()) {

			IndividualProviderDto iptd2 = modelMapper.map(
					pdmt.getIndividualProvider(),
					IndividualProviderDto.class);
			indiprovidersDisclosureIsMadeTo.add(iptd2);
		}
		consentDto
				.setProvidersDisclosureIsMadeTo(indiprovidersDisclosureIsMadeTo);

		Set<OrganizationalProviderDto> orgaprovidersPermittedToDisclose = new HashSet<OrganizationalProviderDto>();

		for (ConsentOrganizationalProviderPermittedToDisclose coptd : consent
				.getOrganizationalProvidersPermittedToDisclose()) {

			OrganizationalProviderDto optd = modelMapper.map(
					coptd.getOrganizationalProvider(),
					OrganizationalProviderDto.class);
			orgaprovidersPermittedToDisclose.add(optd);
		}
		consentDto
				.setOrganizationalProvidersPermittedToDisclose(orgaprovidersPermittedToDisclose);

		Set<OrganizationalProviderDto> orgaprovidersDisclosureIsMadeTo = new HashSet<OrganizationalProviderDto>();

		for (ConsentOrganizationalProviderDisclosureIsMadeTo opdmt : consent
				.getOrganizationalProvidersDisclosureIsMadeTo()) {

			OrganizationalProviderDto optd2 = modelMapper.map(
					opdmt.getOrganizationalProvider(),
					OrganizationalProviderDto.class);
			orgaprovidersDisclosureIsMadeTo.add(optd2);
		}
		consentDto
				.setOrganizationalProvidersDisclosureIsMadeTo(orgaprovidersDisclosureIsMadeTo);

		Set<TypeCodesDto> consentDoNotShareClinicalDocumentTypeCode = new HashSet<TypeCodesDto>();
		for (ConsentDoNotShareClinicalDocumentTypeCode item : consent
				.getDoNotShareClinicalDocumentTypeCodes()) {
			TypeCodesDto tcd = new TypeCodesDto();
			tcd.setDisplayName(item.getClinicalDocumentTypeCode()
					.getDisplayName());
			tcd.setCode(item.getClinicalDocumentTypeCode().getCode());
			tcd.setCodeSystem(item.getClinicalDocumentTypeCode()
					.getCodeSystem());
			tcd.setCodeSystemName(item.getClinicalDocumentTypeCode()
					.getCodeSystemName());
			consentDoNotShareClinicalDocumentTypeCode.add(tcd);
		}
		consentDto
				.setDoNotShareClinicalDocumentTypeCodes(consentDoNotShareClinicalDocumentTypeCode);

		Set<TypeCodesDto> consentDoNotShareClinicalDocumentSectionTypeCode = new HashSet<TypeCodesDto>();
		for (ConsentDoNotShareClinicalDocumentSectionTypeCode item : consent
				.getDoNotShareClinicalDocumentSectionTypeCodes()) {
			TypeCodesDto tcd1 = new TypeCodesDto();
			tcd1.setDisplayName(item.getClinicalDocumentSectionTypeCode()
					.getDisplayName());
			tcd1.setCode(item.getClinicalDocumentSectionTypeCode().getCode());
			tcd1.setCodeSystem(item.getClinicalDocumentSectionTypeCode()
					.getCodeSystem());
			tcd1.setCodeSystemName(item.getClinicalDocumentSectionTypeCode()
					.getCodeSystemName());
			consentDoNotShareClinicalDocumentSectionTypeCode.add(tcd1);

		}
		consentDto
				.setDoNotShareClinicalDocumentSectionTypeCodes(consentDoNotShareClinicalDocumentSectionTypeCode);

		Set<TypeCodesDto> consentDoNotShareSensitivityPolicyCode = new HashSet<TypeCodesDto>();
		for (ConsentDoNotShareSensitivityPolicyCode item : consent
				.getDoNotShareSensitivityPolicyCodes()) {

			TypeCodesDto tcd2 = new TypeCodesDto();
			tcd2.setDisplayName(item.getSensitivityPolicyCode()
					.getDisplayName());
			tcd2.setCode(item.getSensitivityPolicyCode().getCode());
			tcd2.setCodeSystem(item.getSensitivityPolicyCode().getCodeSystem());
			tcd2.setCodeSystemName(item.getSensitivityPolicyCode()
					.getCodeSystemName());
			consentDoNotShareSensitivityPolicyCode.add(tcd2);

		}
		consentDto
				.setDoNotShareSensitivityPolicyCodes(consentDoNotShareSensitivityPolicyCode);

		Set<TypeCodesDto> consentShareForPurposeOfUseCode = new HashSet<TypeCodesDto>();
		for (ConsentShareForPurposeOfUseCode item : consent
				.getShareForPurposeOfUseCodes()) {

			TypeCodesDto tcd3 = new TypeCodesDto();
			tcd3.setDisplayName(item.getPurposeOfUseCode().getDisplayName());
			tcd3.setCode(item.getPurposeOfUseCode().getCode());
			tcd3.setCodeSystem(item.getPurposeOfUseCode().getCodeSystem());
			tcd3.setCodeSystemName(item.getPurposeOfUseCode()
					.getCodeSystemName());
			consentShareForPurposeOfUseCode.add(tcd3);

		}
		consentDto
				.setShareForPurposeOfUseCodes(consentShareForPurposeOfUseCode);

		Set<TypeCodesDto> consentDoNotShareClinicalConceptCodes = new HashSet<TypeCodesDto>();
		for (ClinicalConceptCode item : consent
				.getDoNotShareClinicalConceptCodes()) {
			TypeCodesDto tcd4 =new TypeCodesDto();
			tcd4.setDisplayName(item.getDisplayName());
			tcd4.setCode(item.getCode());
			tcd4.setCodeSystem(item.getCodeSystem());
			tcd4.setCodeSystemName(item.getCodeSystemName());
			consentDoNotShareClinicalConceptCodes.add(tcd4);
		}
		consentDto
				.setDoNotShareClinicalConceptCodes(consentDoNotShareClinicalConceptCodes);

		consentDto.setConsentStart(consent.getStartDate());
		consentDto.setConsentEnd(consent.getEndDate());
		consentDto.setSignedDate(consent.getSignedDate());
		consentDto.setVersion(consent.getVersion());
		consentDto.setRevocationDate(consent.getRevocationDate());

		return consentDto;

	}

	public ModelMapper getModelMapper() {
		return modelMapper;
	}

	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	

}
