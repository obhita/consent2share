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

import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

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
		logger.debug("Consent Revoked Message Received: ConsentId"
				+ new String(data));

		Long consentId = Long.parseLong(data);

		// Get policy id
		PolicyIdDto policyIdDto = consentGetter.getPolicyId(consentId);
		String patientId = policyIdDto.getPatientId();
		String policyId = policyIdDto.getPolicyId();
		Assert.notNull(patientId);
		Assert.notNull(policyId);
		String patientUniqueId = consentRevokeService.getPatientUniqueId(
				patientId, domainId);
		Assert.notNull(patientUniqueId);

		// Revoke consent
		RegistryResponse response = null;
		try {
			response = consentRevokeService.revokeConsent(patientUniqueId,
					policyId);
		} catch (Throwable e) {
			logger.error("Failed to revoke consent in XDS.b repository", e);

			throw e;
		}

		if (!URN_RESPONSE_SUCCESS.equals(response.getStatus())) {
			String errorMessage = "Failed to revoke consent in XDS.b repository becuase response status is not " + URN_RESPONSE_SUCCESS;
			logger.error(errorMessage);

			throw new Exception(errorMessage);
		}

		return "Consent is successfully revoked in XDS.b repository";
	}
}