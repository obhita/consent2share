/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *       * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *       * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *       * Neither the name of the <organization> nor the
 *         names of its contributors may be used to endorse or promote products
 *         derived from this software without specific prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *   DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.consent2share.si;

import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * The Class ConsentGetterImpl.
 */
public class ConsentGetterImpl implements ConsentGetter {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The jdbc template. */
	private JdbcTemplate jdbcTemplate;

	/** The data source. */
	private DataSource dataSource;

	/** The signed consent dto row mapper. */
	private SignedConsentDtoRowMapper signedConsentDtoRowMapper;

	/** The revoked consent dto row mapper. */
	private RevokedConsentDtoRowMapper revokedConsentDtoRowMapper;

	/**
	 * The Constant SQL_GET_SIGNED_CONSENT.
	 * xacml_ccd,xacml_pdf_consent_from,xacml_pdf_consent_to
	 */
	public static final String SQL_GET_SIGNED_CONSENT = "select consent.xacml_ccd, xacml_pdf_consent_from,xacml_pdf_consent_to, consent.patient, consent.id, consent.consent_reference_id, signedpdfconsent.signed_pdf_consent_content, patient.medical_record_number "
			+ "from consent "
			+ "join patient on consent.patient=patient.id "
			+ "join signedpdfconsent on consent.signed_pdf_consent=signedpdfconsent.id "
			+ "where consent.id=?";

	/** The Constant SQL_GET_REVOKED_CONSENT. */
	public static final String SQL_GET_REVOKED_CONSENT = "select consent.id, consent.consent_reference_id, patient.enterprise_identifier, consent.patient, signedpdfconsent_revocation.signed_pdf_consent_revocation_content, patient.medical_record_number "
			+ "from consent "
			+ "join patient on consent.patient=patient.id "
			+ "join signedpdfconsent_revocation on consent.signed_pdf_consent_revoke=signedpdfconsent_revocation.id "
			+ "where consent.id=?";

	/**
	 * Instantiates a new consent getter impl.
	 *
	 * @param dataSource
	 *            the data source
	 * @param signedConsentDtoRowMapper
	 *            the signed consent dto row mapper
	 * @param revokedConsentDtoRowMapper
	 *            the revoked consent dto row mapper
	 */
	public ConsentGetterImpl(DataSource dataSource,
			SignedConsentDtoRowMapper signedConsentDtoRowMapper,
			RevokedConsentDtoRowMapper revokedConsentDtoRowMapper) {
		this.dataSource = dataSource;
		this.signedConsentDtoRowMapper = signedConsentDtoRowMapper;
		this.revokedConsentDtoRowMapper = revokedConsentDtoRowMapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.si.ConsentGetter#getSignedConsentDto(long)
	 */
	@Override
	public SignedConsentDto getSignedConsentDto(long consentId) {
		jdbcTemplate = getJdbcTemplate();

		List<SignedConsentDto> consents = this.jdbcTemplate.query(
				SQL_GET_SIGNED_CONSENT, new Object[] { consentId },
				signedConsentDtoRowMapper);
		logger.info("Consent is queried.");
		consents.removeAll(Collections.singleton(null));

		return consents.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.si.ConsentGetter#getRevokedConsentDto(long)
	 */
	@Override
	public RevokedConsentDto getRevokedConsentDto(long consentId) {
		jdbcTemplate = getJdbcTemplate();

		List<RevokedConsentDto> consents = this.jdbcTemplate.query(
				SQL_GET_REVOKED_CONSENT, new Object[] { consentId },
				revokedConsentDtoRowMapper);
		consents.removeAll(Collections.singleton(null));
		return consents.get(0);
	}

	/**
	 * Gets the jdbc template.
	 *
	 * @return the jdbc template
	 */
	JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}
}
