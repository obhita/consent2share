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

import static gov.samhsa.consent2share.si.audit.SIAuditVerb.XDS_DEPRECATE_CONSENT;
import static gov.samhsa.consent2share.si.audit.SIPredicateKey.C2S_CONSENT_ID;
import static gov.samhsa.consent2share.si.audit.SIPredicateKey.C2S_PATIENT_ID;
import static gov.samhsa.consent2share.si.audit.SIPredicateKey.DOMAIN_ID;
import static gov.samhsa.consent2share.si.audit.SIPredicateKey.RESPONSE_BODY;
import static gov.samhsa.consent2share.si.audit.SIPredicateKey.RESPONSE_STATUS;
import static gov.samhsa.consent2share.si.audit.SIPredicateKey.XACML_POLICY_ID;
import gov.samhsa.acs.audit.PredicateKey;
import gov.samhsa.acs.common.tool.exception.SimpleMarshallerException;
import gov.samhsa.spirit.wsclient.adapter.SpiritAdapter;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import ch.qos.logback.audit.AuditException;

import com.spirit.ehr.ws.client.generated.XdsSrcSubmitRsp;

/**
 * The Class ConsentRevokedMessageHandler.
 */
public class ConsentRevokedMessageHandler extends AbstractConsentMessageHandler {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The spirit adapter. */
	@Autowired
	private SpiritAdapter spiritAdapter;

	/** The endpoint stopper. */
	@Autowired
	private EndpointStopper endpointStopper;

	/**
	 * Instantiates a new consent revoked message handler.
	 */
	public ConsentRevokedMessageHandler() {
		super();
	}

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
		final String patientMrn = policyIdDto.getPatientMrn();
		final String xacmlCcdId = policyIdDto.getXacmlCcdId();
		final byte[] revokedPdfConsent = policyIdDto.getRevokedPdfConsent();
		Assert.notNull(patientEid);
		Assert.notNull(patientMrn);
		Assert.notNull(xacmlCcdId);
		Assert.notNull(revokedPdfConsent);
		Assert.isTrue(revokedPdfConsent.length > 0);
		// Revoke consent
		XdsSrcSubmitRsp response = null;
		try {
			response = spiritAdapter.deprecatePolicy(xacmlCcdId, patientMrn,
					revokedPdfConsent);
			audit(messageId, policyIdDto, response);
		} catch (final Throwable e) {
			logger.error("Failed to revoke consent in XDS.b repository", e);

			throw e;
		}

		if (response.getResponseDetail().getListSuccess().isEmpty()) {
			final String errorMessage = "Failed to revoke consent in XDS.b repository becuase response status is not "
					+ URN_RESPONSE_SUCCESS;
			logger.error(errorMessage);

			throw new Exception(errorMessage);
		}
		endpointStopper.setCounter(0);
		return "Consent is successfully revoked in XDS.b repository";
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
	 */
	private void audit(String messageId, RevokedConsentDto policyIdDto,
			XdsSrcSubmitRsp registryResponse) {
		final Map<PredicateKey, String> predicateMap = auditService
				.createPredicateMap();
		predicateMap.put(C2S_CONSENT_ID,
				Long.toString(policyIdDto.getPcmConsentId()));
		predicateMap.put(C2S_PATIENT_ID,
				Long.toString(policyIdDto.getPcmPatientId()));
		predicateMap.put(DOMAIN_ID, domainId);
		predicateMap.put(XACML_POLICY_ID, policyIdDto.getXacmlCcdId());
		if (registryResponse != null) {
			if (!registryResponse.getResponseDetail().getListSuccess()
					.isEmpty()
					&& registryResponse.getResponseDetail().getListError()
							.isEmpty()) {
				predicateMap.put(RESPONSE_STATUS, "Succeeded");
			} else if (!registryResponse.getResponseDetail().getListSuccess()
					.isEmpty()
					&& !registryResponse.getResponseDetail().getListError()
							.isEmpty()) {
				predicateMap.put(RESPONSE_STATUS, "Partially failed.");
			} else {
				predicateMap.put(RESPONSE_STATUS, "Failed");
			}
			try {
				predicateMap.put(RESPONSE_BODY,
						marshaller.marshalWithoutRootElement(registryResponse));
			} catch (final SimpleMarshallerException e) {
				logger.warn(e.getMessage());
			}
		}
		try {
			auditService.audit(this, messageId, XDS_DEPRECATE_CONSENT,
					policyIdDto.getPatientMrn(), predicateMap);
		} catch (final AuditException e) {
			logger.error(e.getMessage());
		}
	}
}
