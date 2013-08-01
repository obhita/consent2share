/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.consent2share.service.consentexport;

import gov.samhsa.consent2share.domain.consent.Consent;
import gov.samhsa.consent2share.domain.consent.ConsentDoNotShareClinicalDocumentSectionTypeCode;
import gov.samhsa.consent2share.domain.consent.ConsentDoNotShareClinicalDocumentTypeCode;
import gov.samhsa.consent2share.domain.consent.ConsentShareForPurposeOfUseCode;
import gov.samhsa.consent2share.domain.consent.ConsentDoNotShareSensitivityPolicyCode;
import gov.samhsa.consent2share.domain.consent.ConsentIndividualProviderDisclosureIsMadeTo;
import gov.samhsa.consent2share.domain.consent.ConsentIndividualProviderPermittedToDisclose;
import gov.samhsa.consent2share.domain.consent.ConsentOrganizationalProviderDisclosureIsMadeTo;
import gov.samhsa.consent2share.domain.consent.ConsentOrganizationalProviderPermittedToDisclose;
import gov.samhsa.consent2share.domain.consent.ConsentRepository;
import gov.samhsa.consent2share.domain.reference.ClinicalConceptCode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class ConsentExportServiceImpl.
 */
@Service
@Transactional(readOnly=true)
public class ConsentExportServiceImpl implements ConsentExportService {

	/** The consent repository. */
	@Autowired
	ConsentRepository consentRepository;

	/** The model mapper. */
	@Autowired
	ModelMapper modelMapper;

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.consentexport.ConsentExportService#exportCDAR2Consent(java.lang.Long)
	 */
	public String exportCDAR2Consent(Long consentId) {

		Consent consent = consentRepository.findOne(consentId);
		ConsentExportDto consentExportDto = consentExportMap(consent);
		ByteArrayOutputStream sr1 = jaxbMarshall(consentExportDto);
		StreamSource bais = new StreamSource(new ByteArrayInputStream(
				sr1.toByteArray()));
		URL cd2 = this.getClass().getClassLoader().getResource("c2cdar2.xsl");
		String xslID = cd2.toString();
		StreamResult srcdar = saxonTransform(xslID, bais);
		
		String cdar2 = srcdar.getOutputStream().toString();
		
		return cdar2;
	}
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.consentexport.ConsentExportService#exportXACMLConsent(java.lang.Long)
	 */
	public String exportXACMLConsent(Long consentId) {

		Consent consent = consentRepository.findOne(consentId);
		ConsentExportDto consentExportDto = consentExportMap(consent);
		ByteArrayOutputStream sr1 = jaxbMarshall(consentExportDto);
		StreamSource bais = new StreamSource(new ByteArrayInputStream(
				sr1.toByteArray()));
		URL cd2 = this.getClass().getClassLoader().getResource("c2xacml.xsl");
		String xslID = cd2.toString();
		StreamResult srcdar = saxonTransform(xslID, bais);
		String xacml = srcdar.getOutputStream().toString();
		
		return xacml;
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.consentexport.ConsentExportService#makeConsentExportDto()
	 */
	public ConsentExportDto makeConsentExportDto() {
		return new ConsentExportDto();
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.consentexport.ConsentExportService#makeTypeCodesDto()
	 */
	public TypeCodesDto makeTypeCodesDto() {
		return new TypeCodesDto();
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.consentexport.ConsentExportService#consentExportMap(gov.samhsa.consent2share.domain.consent.Consent)
	 */
	public ConsentExportDto consentExportMap(Consent consent) {
		ConsentExportDto consentExportDto = makeConsentExportDto();
		
		consentExportDto.setConsentReferenceid(consent.getConsentReferenceId());

		PatientExportDto patientExportDto = modelMapper.map(
				consent.getPatient(), PatientExportDto.class);
		consentExportDto.setPatientExportDto(patientExportDto);

		if (consent.getLegalRepresentative() != null) {
			PatientExportDto legalRepresentativeDto = modelMapper.map(
					consent.getLegalRepresentative(), PatientExportDto.class);
			consentExportDto.setLegalRepresentative(legalRepresentativeDto);
		}

		Set<IndividualProviderExportDto> indiprovidersPermittedToDisclose = new HashSet<IndividualProviderExportDto>();

		for (ConsentIndividualProviderPermittedToDisclose pptd : consent
				.getProvidersPermittedToDisclose()) {

			IndividualProviderExportDto iptd = modelMapper.map(
					pptd.getIndividualProvider(),
					IndividualProviderExportDto.class);
			indiprovidersPermittedToDisclose.add(iptd);
		}
		consentExportDto
				.setProvidersPermittedToDisclose(indiprovidersPermittedToDisclose);

		Set<IndividualProviderExportDto> indiprovidersDisclosureIsMadeTo = new HashSet<IndividualProviderExportDto>();

		for (ConsentIndividualProviderDisclosureIsMadeTo pdmt : consent
				.getProvidersDisclosureIsMadeTo()) {

			IndividualProviderExportDto iptd2 = modelMapper.map(
					pdmt.getIndividualProvider(),
					IndividualProviderExportDto.class);
			indiprovidersDisclosureIsMadeTo.add(iptd2);
		}
		consentExportDto
				.setProvidersDisclosureIsMadeTo(indiprovidersDisclosureIsMadeTo);

		Set<OrganizationalProviderExportDto> orgaprovidersPermittedToDisclose = new HashSet<OrganizationalProviderExportDto>();

		for (ConsentOrganizationalProviderPermittedToDisclose coptd : consent
				.getOrganizationalProvidersPermittedToDisclose()) {

			OrganizationalProviderExportDto optd = modelMapper.map(
					coptd.getOrganizationalProvider(),
					OrganizationalProviderExportDto.class);
			orgaprovidersPermittedToDisclose.add(optd);
		}
		consentExportDto
				.setOrganizationalProvidersPermittedToDisclose(orgaprovidersPermittedToDisclose);

		Set<OrganizationalProviderExportDto> orgaprovidersDisclosureIsMadeTo = new HashSet<OrganizationalProviderExportDto>();

		for (ConsentOrganizationalProviderDisclosureIsMadeTo opdmt : consent
				.getOrganizationalProvidersDisclosureIsMadeTo()) {

			OrganizationalProviderExportDto optd2 = modelMapper.map(
					opdmt.getOrganizationalProvider(),
					OrganizationalProviderExportDto.class);
			orgaprovidersDisclosureIsMadeTo.add(optd2);
		}
		consentExportDto
				.setOrganizationalProvidersDisclosureIsMadeTo(orgaprovidersDisclosureIsMadeTo);

		Set<TypeCodesDto> consentDoNotShareClinicalDocumentTypeCode = new HashSet<TypeCodesDto>();
		for (ConsentDoNotShareClinicalDocumentTypeCode item : consent
				.getDoNotShareClinicalDocumentTypeCodes()) {
			TypeCodesDto tcd = makeTypeCodesDto();
			tcd.setDisplayName(item.getClinicalDocumentTypeCode()
					.getDisplayName());
			tcd.setCode(item.getClinicalDocumentTypeCode().getCode());
			tcd.setCodeSystem(item.getClinicalDocumentTypeCode()
					.getCodeSystem());
			tcd.setCodeSystemName(item.getClinicalDocumentTypeCode()
					.getCodeSystemName());
			consentDoNotShareClinicalDocumentTypeCode.add(tcd);
		}
		consentExportDto
				.setDoNotShareClinicalDocumentTypeCodes(consentDoNotShareClinicalDocumentTypeCode);

		Set<TypeCodesDto> consentDoNotShareClinicalDocumentSectionTypeCode = new HashSet<TypeCodesDto>();
		for (ConsentDoNotShareClinicalDocumentSectionTypeCode item : consent
				.getDoNotShareClinicalDocumentSectionTypeCodes()) {
			TypeCodesDto tcd1 = makeTypeCodesDto();
			tcd1.setDisplayName(item.getClinicalDocumentSectionTypeCode()
					.getDisplayName());
			tcd1.setCode(item.getClinicalDocumentSectionTypeCode().getCode());
			tcd1.setCodeSystem(item.getClinicalDocumentSectionTypeCode()
					.getCodeSystem());
			tcd1.setCodeSystemName(item.getClinicalDocumentSectionTypeCode()
					.getCodeSystemName());
			consentDoNotShareClinicalDocumentSectionTypeCode.add(tcd1);

		}
		consentExportDto
				.setDoNotShareClinicalDocumentSectionTypeCodes(consentDoNotShareClinicalDocumentSectionTypeCode);

		Set<TypeCodesDto> consentDoNotShareSensitivityPolicyCode = new HashSet<TypeCodesDto>();
		for (ConsentDoNotShareSensitivityPolicyCode item : consent
				.getDoNotShareSensitivityPolicyCodes()) {

			TypeCodesDto tcd2 = makeTypeCodesDto();
			tcd2.setDisplayName(item.getSensitivityPolicyCode()
					.getDisplayName());
			tcd2.setCode(item.getSensitivityPolicyCode().getCode());
			tcd2.setCodeSystem(item.getSensitivityPolicyCode().getCodeSystem());
			tcd2.setCodeSystemName(item.getSensitivityPolicyCode()
					.getCodeSystemName());
			consentDoNotShareSensitivityPolicyCode.add(tcd2);

		}
		consentExportDto
				.setDoNotShareSensitivityPolicyCodes(consentDoNotShareSensitivityPolicyCode);

		Set<TypeCodesDto> consentShareForPurposeOfUseCode = new HashSet<TypeCodesDto>();
		for (ConsentShareForPurposeOfUseCode item : consent
				.getShareForPurposeOfUseCodes()) {

			TypeCodesDto tcd3 = makeTypeCodesDto();
			tcd3.setDisplayName(item.getPurposeOfUseCode().getDisplayName());
			tcd3.setCode(item.getPurposeOfUseCode().getCode());
			tcd3.setCodeSystem(item.getPurposeOfUseCode().getCodeSystem());
			tcd3.setCodeSystemName(item.getPurposeOfUseCode()
					.getCodeSystemName());
			consentShareForPurposeOfUseCode.add(tcd3);

		}
		consentExportDto
				.setShareForPurposeOfUseCodes(consentShareForPurposeOfUseCode);

		Set<TypeCodesDto> consentDoNotShareClinicalConceptCodes = new HashSet<TypeCodesDto>();
		for (ClinicalConceptCode item : consent
				.getDoNotShareClinicalConceptCodes()) {
			TypeCodesDto tcd4 = makeTypeCodesDto();
			tcd4.setDisplayName(item.getDisplayName());
			tcd4.setCode(item.getCode());
			tcd4.setCodeSystem(item.getCodeSystem());
			tcd4.setCodeSystemName(item.getCodeSystemName());
			consentDoNotShareClinicalConceptCodes.add(tcd4);
		}
		consentExportDto
				.setDoNotShareClinicalConceptCodes(consentDoNotShareClinicalConceptCodes);

		consentExportDto.setConsentStart(consent.getStartDate());
		consentExportDto.setConsentEnd(consent.getEndDate());
		consentExportDto.setSignedDate(consent.getSignedDate());
		consentExportDto.setVersion(consent.getVersion());
		consentExportDto.setRevocationDate(consent.getRevocationDate());

		return consentExportDto;

	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.consentexport.ConsentExportService#jaxbMarshall(gov.samhsa.consent2share.service.consentexport.ConsentExportDto)
	 */
	public ByteArrayOutputStream jaxbMarshall(ConsentExportDto consentExportDto) {
		ByteArrayOutputStream marshalresult = new ByteArrayOutputStream();
		

		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(ConsentExportDto.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			marshaller.marshal(consentExportDto, marshalresult);
			//marshaller.marshal(consentExportDto,  new File("c:\\consent2share\\ConsentExport.xml"));
			
		}

		catch (Exception e) {
			logger.warn("Error in JAXB Transfroming");
			logger.warn("The exception is", e);
		}

		return marshalresult;

	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.service.consentexport.ConsentExportService#saxonTransform(java.lang.String, javax.xml.transform.stream.StreamSource)
	 */
	public StreamResult saxonTransform(String xslID, StreamSource streamSource) {

		StreamResult srcdar = new StreamResult(new ByteArrayOutputStream());
		try {
			TransformerFactory tfactory = TransformerFactory.newInstance();

			Transformer transformer = tfactory.newTransformer(new StreamSource(
					xslID));
			transformer.transform(streamSource, srcdar);
		} catch (Exception e) {
			logger.warn("Error in SAXON Transfroming");
			logger.warn("The exception is", e);
		}

		return srcdar;

	}

}
