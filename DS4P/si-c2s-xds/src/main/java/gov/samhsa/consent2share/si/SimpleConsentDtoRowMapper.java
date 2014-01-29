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

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.lob.LobHandler;

/**
 * The Class SimpleConsentDtoRowMapper.
 */
public class SimpleConsentDtoRowMapper implements RowMapper<SimpleConsentDto> {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The lob handler. */
	private LobHandler lobHandler;

	/**
	 * Instantiates a new simple consent dto row mapper.
	 */
	public SimpleConsentDtoRowMapper() {
	}

	/**
	 * Instantiates a new simple consent dto row mapper.
	 * 
	 * @param lobHandler
	 *            the lob handler
	 */
	public SimpleConsentDtoRowMapper(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet,
	 * int)
	 */
	@Override
	public SimpleConsentDto mapRow(ResultSet rs, int i) throws SQLException {
		long consentIdLong = rs.getLong("id");
		long patientId = rs.getLong("consent.patient");
		String eId = rs.getString("patient.enterprise_identifier");
		String consent = new String(lobHandler.getBlobAsBytes(rs,
				"consent.xacml_policy_file"));

		logger.info("EID:" + eId + "; PatientId:" + patientId + "; Consent:"
				+ consentIdLong + " is fetched.");
		logger.debug("Consent File:\n" + consent);
		return new SimpleConsentDto(consent, patientId, eId);
	}
}
