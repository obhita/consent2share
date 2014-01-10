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
package gov.samhsa.consent2share.esignaturepolling;

import echosign.api.clientv15.dto15.DocumentInfo;
import gov.samhsa.consent2share.domain.consent.AbstractSignedPDFDocument;
import gov.samhsa.consent2share.domain.consent.Consent;
import gov.samhsa.consent2share.domain.consent.ConsentRepository;
import gov.samhsa.consent2share.infrastructure.EchoSignSignatureService;
import gov.samhsa.consent2share.service.consentexport.ConsentExportService;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

// TODO: Auto-generated Javadoc
/**
 * The Class EchoSignPollingService.
 */
@Component("esignaturePollingService")
@Transactional
public class EchoSignPollingService implements EsignaturePollingService {
	
	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The Signed_ staus. */
	private final String Signed_Staus = "SIGNED";

	/** The consent repository. */
	private ConsentRepository consentRepository;
	
	/** The signature service. */
	private EchoSignSignatureService signatureService;

	/**
	 * Instantiates a new echo sign polling service.
	 *
	 * @param ConsentRepository the consent repository
	 * @param signatureService the signature service
	 */
	@Autowired
	public EchoSignPollingService(ConsentRepository ConsentRepository,
			EchoSignSignatureService signatureService) {
		this.consentRepository = ConsentRepository;
		this.signatureService = signatureService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.esignaturepolling.EsignaturePollingService#poll
	 * ()
	 */
	@Override
	public void poll() {
		logger.info("Starts polling at {}...", new Date().toString());
		
		try {
			Set<Consent> consentSet=new HashSet<Consent>();
			consentSet.addAll(consentRepository
					.findBySignedPdfConsentDocumentSignedStatusNot(Signed_Staus));
			consentSet.addAll(consentRepository
					.findBySignedPdfConsentRevokeDocumentSignedStatusNot(Signed_Staus));

			for (Consent consent : consentSet) {
				AbstractSignedPDFDocument document = SignedPDFDocumentBinder(consent);

				String documentKey = document.getDocumentId();
				String signStatus = document.getDocumentSignedStatus();

				logger.info(
						"Starts calling adobe echosign web service to get document info at {}...",
						new Date().toString());

				DocumentInfo latestDocumentInfo = signatureService
						.getDocumentInfo(documentKey);

				logger.info("Ended calling adobe echosign web service to get document info at {}.",
						new Date().toString());

				String latestSignStatus = latestDocumentInfo.getStatus()
						.toString();

				if (!latestSignStatus.equals(signStatus)) {
					document.setDocumentSignedStatus(latestSignStatus);

					if (latestSignStatus.equals(Signed_Staus)) {
						
						logger.info(
								"Starts calling adobe echosign web service to get signed document at {}...",
								new Date().toString());
						
						byte[] latestData = signatureService
								.getLatestDocument(documentKey);
						
						logger.info("Ended calling adobe echosign web service to get signed document at {}.",
								new Date().toString());
						
						document.setContent(latestData, consent.getId());
					}
				}

				consentRepository.save(consent);
			}
		} catch (Exception e) {
			logger.error("Error occurred:", e);
		}

		logger.info("Ended polling at {}.", new Date().toString());
	}
	
	/**
	 * Signed pdf document binder.
	 *
	 * @param consent the consent
	 * @return the abstract signed pdf document
	 */
	private AbstractSignedPDFDocument SignedPDFDocumentBinder(Consent consent){
		if(consent.getSignedPdfConsent().getDocumentSignedStatus().equals("SIGNED"))
			return consent.getSignedPdfConsentRevoke();
		return consent.getSignedPdfConsent();
	}
}
