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
import org.springframework.stereotype.Component;

/**
 * The Class ConsentGetterImpl.
 */
@Component
public class ConsentGetterImpl implements ConsentGetter {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The jdbc template. */
	private JdbcTemplate jdbcTemplate;

	/** The data source. */
	private DataSource dataSource;

	/** The simple consent dto row mapper. */
	private SimpleConsentDtoRowMapper simpleConsentDtoRowMapper;

	/**
	 * Instantiates a new consent getter impl.
	 */
	public ConsentGetterImpl() {
	}

	/**
	 * Instantiates a new consent getter impl.
	 * 
	 * @param dataSource
	 *            the data source
	 * @param simpleConsentDtoRowMapper
	 *            the simple consent dto row mapper
	 */
	public ConsentGetterImpl(DataSource dataSource,
			SimpleConsentDtoRowMapper simpleConsentDtoRowMapper) {
		this.dataSource = dataSource;
		this.simpleConsentDtoRowMapper = simpleConsentDtoRowMapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.si.ConsentGetter#getConsent(long)
	 */
	@Override
	public SimpleConsentDto getConsent(long consentId) {

		String sql = "select consent.xacml_policy_file, consent.patient, consent.id, patient.enterprise_identifier "
				+ "from consent "
				+ "join patient on consent.patient=patient.id "
				+ "where consent.id=?";

		jdbcTemplate = getJdbcTemplate();

		List<SimpleConsentDto> consents = this.jdbcTemplate.query(sql,
				new Object[] { consentId }, simpleConsentDtoRowMapper);
		logger.info("Consent is queried.");
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
