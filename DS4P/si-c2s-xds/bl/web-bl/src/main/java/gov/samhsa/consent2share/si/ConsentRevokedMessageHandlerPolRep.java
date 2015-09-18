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

import static gov.samhsa.consent2share.si.audit.SIAuditVerb.POLREP_DELETE_CONSENT;
import static gov.samhsa.consent2share.si.audit.SIPredicateKey.C2S_CONSENT_ID;
import static gov.samhsa.consent2share.si.audit.SIPredicateKey.C2S_PATIENT_ID;
import static gov.samhsa.consent2share.si.audit.SIPredicateKey.DOMAIN_ID;
import static gov.samhsa.consent2share.si.audit.SIPredicateKey.RESPONSE_STATUS;
import static gov.samhsa.consent2share.si.audit.SIPredicateKey.XACML_POLICY_ID;
import gov.samhsa.acs.audit.PredicateKey;
import gov.samhsa.acs.polrep.client.PolRepRestClient;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpStatusCodeException;

import ch.qos.logback.audit.AuditException;

/**
 * The Class ConsentRevokedMessageHandlerPolRep.
 */
public class ConsentRevokedMessageHandlerPolRep extends
		AbstractConsentMessageHandler {

	/** The Constant SUCCESS. */
	private static final String SUCCESS = "SUCCESS";

	/** The pol rep client. */
	@Autowired
	private PolRepRestClient polRepClient;

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * gov.samhsa.consent2share.si.AbstractConsentMessageHandler#handleMessage
	 * (java.lang.String)
	 */
	@Override
	public String handleMessage(String data) throws Throwable {
		final String messageId = generateMessageId();
		logger.debug("Consent Revoked Message Received: ConsentId "
				+ new String(data));

		final Long consentId = Long.parseLong(data);

		// Get policy id
		final RevokedConsentDto policyIdDto = consentGetter
				.getRevokedConsentDto(consentId);
		final String patientEid = policyIdDto.getPatientEid();
		final String policyId = policyIdDto.getXacmlCcdId();
		Assert.notNull(patientEid);
		Assert.notNull(policyId);

		try {
			polRepClient.deletePolicy(policyId);
			audit(messageId, policyIdDto, Optional.empty());
		} catch (final HttpStatusCodeException e) {
			logger.error(
					"Failed to delete consent in policy repository with status code: "
							+ e.getStatusCode().toString(), e);
			audit(messageId, policyIdDto, Optional.of(e));
			throw e;
		} catch (final Exception e) {
			logger.error("Failed to delete consent in policy repository", e);
			throw e;
		}

		return "Consent is deleted from policy repository";
	}

	/**
	 * Audit.
	 *
	 * @param messageId
	 *            the message id
	 * @param policyIdDto
	 *            the policy id dto
	 * @param exception
	 *            the exception
	 * @throws AuditException
	 *             the audit exception
	 */
	private void audit(String messageId, RevokedConsentDto policyIdDto,
			Optional<HttpStatusCodeException> exception) throws AuditException {
		final Map<PredicateKey, String> predicateMap = auditService
				.createPredicateMap();
		predicateMap.put(C2S_CONSENT_ID,
				Long.toString(policyIdDto.getPcmConsentId()));
		predicateMap.put(C2S_PATIENT_ID,
				Long.toString(policyIdDto.getPcmPatientId()));
		predicateMap.put(DOMAIN_ID, domainId);
		predicateMap.put(XACML_POLICY_ID, policyIdDto.getXacmlCcdId());

		// default status: success
		predicateMap.put(RESPONSE_STATUS, SUCCESS);
		// set status to response status code if there is an exception
		exception.ifPresent(e -> predicateMap.put(RESPONSE_STATUS, e
				.getStatusCode().toString()));

		auditService.audit(this, messageId, POLREP_DELETE_CONSENT,
				policyIdDto.getPatientMrn(), predicateMap);
	}
}
