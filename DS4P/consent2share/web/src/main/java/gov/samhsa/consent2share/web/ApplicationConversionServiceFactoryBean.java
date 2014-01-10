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
package gov.samhsa.consent2share.web;

import gov.samhsa.consent2share.domain.clinicaldata.ClinicalDocument;
import gov.samhsa.consent2share.domain.consent.Consent;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.service.clinicaldata.ClinicalDocumentService;
import gov.samhsa.consent2share.service.consent.ConsentService;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;
import gov.samhsa.consent2share.service.patient.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

/**
 * The Class ApplicationConversionServiceFactoryBean.
 */
@Configurable
/**
 * A central place to register application converters and formatters. 
 */
public class ApplicationConversionServiceFactoryBean extends
		FormattingConversionServiceFactoryBean {

	/* (non-Javadoc)
	 * @see org.springframework.format.support.FormattingConversionServiceFactoryBean#installFormatters(org.springframework.format.FormatterRegistry)
	 */
	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	/** The clinical document service. */
	@Autowired
	ClinicalDocumentService clinicalDocumentService;

	/** The consent service. */
	@Autowired
	ConsentService consentService;

	/** The patient service. */
	@Autowired
	PatientService patientService;

	/**
	 * Gets the clinical document to string converter.
	 *
	 * @return the clinical document to string converter
	 */
	public Converter<ClinicalDocument, String> getClinicalDocumentToStringConverter() {
		return new Converter<ClinicalDocument, java.lang.String>() {
			public String convert(ClinicalDocument clinicalDocument) {
				return new StringBuilder().append(clinicalDocument.getName())
						.append(' ').append(clinicalDocument.getDescription())
						.append(' ').append(clinicalDocument.getFilename())
						.append(' ').append(clinicalDocument.getContent())
						.toString();
			}
		};
	}

	/**
	 * Gets the id to clinical document converter.
	 *
	 * @return the id to clinical document converter
	 */
	public Converter<Long, ClinicalDocument> getIdToClinicalDocumentConverter() {
		return new Converter<java.lang.Long, ClinicalDocument>() {
			public ClinicalDocument convert(java.lang.Long id) {
				return clinicalDocumentService.findClinicalDocument(id);
			}
		};
	}

	/**
	 * Gets the string to clinical document converter.
	 *
	 * @return the string to clinical document converter
	 */
	public Converter<String, ClinicalDocument> getStringToClinicalDocumentConverter() {
		return new Converter<java.lang.String, ClinicalDocument>() {
			public ClinicalDocument convert(String id) {
				return getObject().convert(getObject().convert(id, Long.class),
						ClinicalDocument.class);
			}
		};
	}

	/**
	 * Gets the consent to string converter.
	 *
	 * @return the consent to string converter
	 */
	public Converter<Consent, String> getConsentToStringConverter() {
		return new Converter<Consent, java.lang.String>() {
			public String convert(Consent consent) {
				return new StringBuilder().append(consent.getName())
						.append(' ').append(consent.getDescription())
						.append(' ').append(consent.getUnsignedPdfConsent())
						.append(' ').append(consent.getSignedDate()).toString();
			}
		};
	}

	/**
	 * Gets the id to consent converter.
	 *
	 * @return the id to consent converter
	 */
	public Converter<Long, Consent> getIdToConsentConverter() {
		return new Converter<java.lang.Long, Consent>() {
			public gov.samhsa.consent2share.domain.consent.Consent convert(
					java.lang.Long id) {
				return consentService.findConsent(id);
			}
		};
	}

	/**
	 * Gets the string to consent converter.
	 *
	 * @return the string to consent converter
	 */
	public Converter<String, Consent> getStringToConsentConverter() {
		return new Converter<java.lang.String, Consent>() {
			public gov.samhsa.consent2share.domain.consent.Consent convert(
					String id) {
				return getObject().convert(getObject().convert(id, Long.class),
						Consent.class);
			}
		};
	}

	/**
	 * Gets the patient to string converter.
	 *
	 * @return the patient to string converter
	 */
	public Converter<Patient, String> getPatientToStringConverter() {
		return new Converter<Patient, java.lang.String>() {
			public String convert(Patient patient) {
				return new StringBuilder().append(patient.getFirstName())
						.append(' ').append(patient.getLastName()).append(' ')
						.append(patient.getPrefix()).append(' ')
						.append(patient.getEmail()).toString();
			}
		};
	}

	/**
	 * Gets the id to patient converter.
	 *
	 * @return the id to patient converter
	 */
	public Converter<Long, PatientProfileDto> getIdToPatientConverter() {
		return new Converter<java.lang.Long, PatientProfileDto>() {
			public PatientProfileDto convert(java.lang.Long id) {
				return patientService.findPatient(id);
			}
		};
	}

	/**
	 * Gets the string to patient converter.
	 *
	 * @return the string to patient converter
	 */
	public Converter<String, Patient> getStringToPatientConverter() {
		return new Converter<java.lang.String, Patient>() {
			public Patient convert(String id) {
				return getObject().convert(getObject().convert(id, Long.class),
						Patient.class);
			}
		};
	}

	/**
	 * Install label converters.
	 *
	 * @param registry the registry
	 */
	public void installLabelConverters(FormatterRegistry registry) {
		registry.addConverter(getClinicalDocumentToStringConverter());
		registry.addConverter(getIdToClinicalDocumentConverter());
		registry.addConverter(getStringToClinicalDocumentConverter());

		registry.addConverter(getConsentToStringConverter());
		registry.addConverter(getIdToConsentConverter());
		registry.addConverter(getStringToConsentConverter());

		registry.addConverter(getPatientToStringConverter());
		registry.addConverter(getIdToPatientConverter());
		registry.addConverter(getStringToPatientConverter());
	}

	/* (non-Javadoc)
	 * @see org.springframework.format.support.FormattingConversionServiceFactoryBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		installLabelConverters(getObject());
	}
}
