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

import static gov.samhsa.consent2share.si.audit.SIAuditVerb.*;
import static gov.samhsa.consent2share.si.audit.SIPredicateKey.*;

import java.util.Map;
import java.util.UUID;

import gov.samhsa.acs.audit.PredicateKey;
import gov.samhsa.acs.common.tool.exception.SimpleMarshallerException;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import ch.qos.logback.audit.AuditException;

/**
 * The Class ConsentRevokedMessageHandler.
 */
public class ConsentRevokedMessageHandler extends AbstractConsentMessageHandler {

	/** The consent revoke service. */
	@Autowired
	private ConsentRevokeService consentRevokeService;

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
		String messageId = createMessageId();
		logger.debug("Consent Revoked Message Received: ConsentId"
				+ new String(data));

		Long consentId = Long.parseLong(data);

		// Get policy id
		PolicyIdDto policyIdDto = consentGetter.getPolicyId(consentId);
		String patientEid = policyIdDto.getPatientEid();
		String policyId = policyIdDto.getPolicyId();
		Assert.notNull(patientEid);
		Assert.notNull(policyId);
		String patientUniqueId = consentRevokeService.getPatientUniqueId(
				patientEid, domainId);
		Assert.notNull(patientUniqueId);

		// Revoke consent
		RegistryResponse response = null;
		try {
			response = consentRevokeService.revokeConsent(patientUniqueId,
					policyId, messageId);
			audit(messageId, policyIdDto, response);
		} catch (Throwable e) {
			logger.error("Failed to revoke consent in XDS.b repository", e);

			throw e;
		}

		if (!URN_RESPONSE_SUCCESS.equals(response.getStatus())) {
			String errorMessage = "Failed to revoke consent in XDS.b repository becuase response status is not "
					+ URN_RESPONSE_SUCCESS;
			logger.error(errorMessage);

			throw new Exception(errorMessage);
		}

		return "Consent is successfully revoked in XDS.b repository";
	}

	/**
	 * Creates the message id.
	 * 
	 * @return the string
	 */
	String createMessageId() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Audit.
	 * 
	 * @param messageId
	 *            the message id
	 * @param policyIdDto
	 *            the policy id dto
	 * @param registryResponse
	 *            the registry response
	 * @throws AuditException
	 *             the audit exception
	 */
	private void audit(String messageId, PolicyIdDto policyIdDto,
			RegistryResponse registryResponse) throws AuditException {
		Map<PredicateKey, String> predicateMap = auditService
				.createPredicateMap();
		predicateMap.put(C2S_CONSENT_ID,
				Long.toString(policyIdDto.getConsentId()));
		predicateMap.put(C2S_PATIENT_ID,
				Long.toString(policyIdDto.getPatientId()));
		predicateMap.put(DOMAIN_ID, domainId);
		predicateMap.put(XACML_POLICY_ID, policyIdDto.getPolicyId());
		if (registryResponse != null) {
			predicateMap.put(RESPONSE_STATUS, registryResponse.getStatus());
			try {
				predicateMap.put(RESPONSE_BODY,
						marshaller.marshal(registryResponse));
			} catch (SimpleMarshallerException e) {
				throw new AuditException(e.getMessage(), e);
			}
		}
		auditService.audit(this, messageId, XDS_DEPRECATE_CONSENT,
				policyIdDto.getPatientEid(), predicateMap);
	}
}
