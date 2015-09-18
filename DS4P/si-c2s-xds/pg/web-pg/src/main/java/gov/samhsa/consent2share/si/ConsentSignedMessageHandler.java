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

import static gov.samhsa.consent2share.si.audit.SIAuditVerb.XDS_ADD_CONSENT;
import static gov.samhsa.consent2share.si.audit.SIPredicateKey.C2S_CONSENT_ID;
import static gov.samhsa.consent2share.si.audit.SIPredicateKey.C2S_PATIENT_ID;
import static gov.samhsa.consent2share.si.audit.SIPredicateKey.DOMAIN_ID;
import static gov.samhsa.consent2share.si.audit.SIPredicateKey.RESPONSE_BODY;
import static gov.samhsa.consent2share.si.audit.SIPredicateKey.RESPONSE_STATUS;
import static gov.samhsa.consent2share.si.audit.SIPredicateKey.XACML_POLICY;
import static gov.samhsa.consent2share.si.audit.SIPredicateKey.XACML_POLICY_ID;
import gov.samhsa.acs.audit.PredicateKey;
import gov.samhsa.spirit.wsclient.adapter.SpiritAdapter;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ch.qos.logback.audit.AuditException;

import com.spirit.ehr.ws.client.generated.XdsSrcSubmitRsp;

/**
 * The Class ConsentSignedMessageHandler.
 */
public class ConsentSignedMessageHandler extends AbstractConsentMessageHandler {

	/** The control bus service. */
	@Autowired
	private BusController controlBusService;

	/** The notification publisher. */
	@Autowired
	private NotificationPublisher notificationPublisher;

	/** The spirit adapter. */
	@Autowired
	private SpiritAdapter spiritAdapter;

	@Autowired
	private EndpointStopper endpointStopper;

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * gov.samhsa.consent2share.si.AbstractConsentMessageHandler#handleMessage
	 * (java.lang.String)
	 */

	/**
	 * Instantiates a new consent signed message handler.
	 *
	 * @throws JAXBException
	 *             the JAXB exception
	 */
	public ConsentSignedMessageHandler() throws JAXBException {
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
		logger.debug("Consent Signed Message Received: ConsentId"
				+ new String(data));
		final String messageId = generateMessageId();
		final Long consentId = Long.parseLong(data);

		// Get consent
		final SignedConsentDto consentDto = consentGetter
				.getSignedConsentDto(consentId);

		XdsSrcSubmitRsp response = null;
		try {
			response = spiritAdapter.submitSignedConsent(
					consentDto.getXacmlCcd(), consentDto.getSignedPdfConsent(),
					consentDto.getXacmlPdfConsentFrom(),
					consentDto.getXacmlPdfConsentTo(),
					consentDto.getXacmlCcdId(), consentDto.getPatientMrn(),
					"USA");
			audit(messageId, consentDto, response);
		} catch (final Throwable e) {
			logger.error("Failed to save in xds.b repository", e);
			throw e;
		}

		if (response.getResponseDetail().getListSuccess().get(0) == null) {
			final String errorMessage = "Failed to save in XDS.b repository.";
			if (response.getResponseDetail().getListError() != null) {
				logger.error(response.getResponseDetail().getListError().get(0));
			}
			logger.error(errorMessage);

			throw new Exception(errorMessage);
		}
		endpointStopper.setCounter(0);
		return "Saved in XDS.b repository";
	}

	/**
	 * Audit.
	 *
	 * @param messageId
	 *            the message id
	 * @param consentDto
	 *            the consent dto
	 * @param registryResponse
	 *            the registry response
	 * @throws AuditException
	 *             the audit exception
	 */
	private void audit(String messageId, SignedConsentDto consentDto,
			XdsSrcSubmitRsp registryResponse) {
		final Map<PredicateKey, String> predicateMap = auditService
				.createPredicateMap();
		predicateMap.put(C2S_CONSENT_ID, consentDto.getPcmConsentId());
		predicateMap.put(C2S_PATIENT_ID,
				Long.toString(consentDto.getPcmPatientId()));
		predicateMap.put(DOMAIN_ID, domainId);
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
			} catch (final JAXBException e) {
				logger.warn(e.getMessage());
			}
		}
		predicateMap.put(XACML_POLICY_ID, consentDto.getXacmlCcdId());
		try {
			predicateMap.put(XACML_POLICY, new String(consentDto.getXacmlCcd(),
					"UTF-8"));
		} catch (final UnsupportedEncodingException e1) {
			predicateMap
					.put(XACML_POLICY, new String(consentDto.getXacmlCcd()));
		}
		try {
			auditService.audit(this, messageId, XDS_ADD_CONSENT,
					consentDto.getPatientMrn(), predicateMap);
		} catch (final AuditException e) {
			logger.error(e.getMessage());
		}
	}
}
