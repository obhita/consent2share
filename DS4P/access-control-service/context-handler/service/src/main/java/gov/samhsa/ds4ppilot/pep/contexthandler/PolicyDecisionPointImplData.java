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
package gov.samhsa.ds4ppilot.pep.contexthandler;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.herasaf.xacml.core.SyntaxException;
import org.herasaf.xacml.core.policy.Evaluatable;
import org.herasaf.xacml.core.policy.PolicyMarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Component;

/**
 * The Class PolicyDecisionPointImplData.
 */
@Component
public class PolicyDecisionPointImplData {

	/** The jdbc template. */
	private JdbcTemplate jdbcTemplate;

	/** The data source. */
	@Autowired
	private DataSource dataSource;

	/** The lob handler. */
	@Autowired
	private LobHandler lobHandler;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PolicyDecisionPointImplData.class);

	/**
	 * Gets the policies.
	 *
	 * @param resourceId the resource id
	 * @return the policies
	 */
	public List<Evaluatable> getPolicies(String resourceId) {

		String sql = "select consent.xacml_policy_file from consent right "
				+ "join signedpdfconsent on consent.signed_pdf_consent=signedpdfconsent.id "
				+ "where signedpdfconsent.document_signed_status='signed' and signedpdfconsent.signer_email=?";
		jdbcTemplate = new JdbcTemplate(dataSource);

		@SuppressWarnings("unchecked")
		List<Evaluatable> xacmlPolicies = this.jdbcTemplate.query(sql,
				new Object[] { resourceId }, new RowMapper() {
					@Override
					public Evaluatable mapRow(ResultSet rs, int i)
							throws SQLException {
						Evaluatable policy = null;
						InputStream policyInputStream = lobHandler
								.getBlobAsBinaryStream(rs,
										"consent.xacml_policy_file");
						if (policyInputStream != null) {
							try {
								policy = PolicyMarshaller
										.unmarshal(policyInputStream);
							} catch (SyntaxException e) {
								LOGGER.debug(e.toString(),e);
							}
						}

						return policy;
					}
				});
		xacmlPolicies.removeAll(Collections.singleton(null));
		return xacmlPolicies;
	}

}
