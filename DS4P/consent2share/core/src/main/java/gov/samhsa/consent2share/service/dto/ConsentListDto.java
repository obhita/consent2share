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
package gov.samhsa.consent2share.service.dto;

import java.util.Date;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

// TODO: Auto-generated Javadoc
/**
 * The Class ConsentListDto.
 */
public class ConsentListDto {

	/** The id. */
	private Long id;

	/** The to disclose name. */
	private Set<String> toDiscloseName;

	/** The is made to name. */
	private Set<String> isMadeToName;

	/** The do not share clinical document type codes. */
	private Set<String> doNotShareClinicalDocumentTypeCodes;

	/** The do not share clinical document section type codes. */
	private Set<String> doNotShareClinicalDocumentSectionTypeCodes;

	/** The do not share sensitivity policy codes. */
	private Set<String> doNotShareSensitivityPolicyCodes;

	/** The do not share for purpose of use codes. */
	private Set<String> shareForPurposeOfUseCodes;

	/** The do not share clinical concept codes. */
	private Set<String> doNotShareClinicalConceptCodes;

	/** The consent stage. */
	private int consentStage;

	/** The revoke stage. */
	private int revokeStage;
	
	// Consent Sharing date
	/** The consent start. */
	private Date consentStart;

	
	/** The consent end. */
	private Date consentEnd;
	
	/**
	 * Gets the consent start.
	 *
	 * @return the consent start
	 */
	
	public Date getConsentStart() {
		return consentStart;
	}

	/**
	 * Sets the consent start.
	 *
	 * @param string the new consent start
	 */
	public void setConsentStart(Date consentStart) {
		this.consentStart = consentStart;
	}

	/**
	 * Gets the consent end.
	 *
	 * @return the consent end
	 */
	public Date getConsentEnd() {
		return consentEnd;
	}

	/**
	 * Sets the consent end.
	 *
	 * @param consentEnd the new consent end
	 */
	public void setConsentEnd(Date consentEnd) {
		this.consentEnd = consentEnd;
	}

	
	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the to disclose name.
	 * 
	 * @return the to disclose name
	 */
	public Set<String> getToDiscloseName() {
		return toDiscloseName;
	}

	/**
	 * Sets the to disclose name.
	 * 
	 * @param toDiscloseName
	 *            the new to disclose name
	 */
	public void setToDiscloseName(Set<String> toDiscloseName) {
		this.toDiscloseName = toDiscloseName;
	}

	/**
	 * Gets the checks if is made to name.
	 * 
	 * @return the checks if is made to name
	 */
	public Set<String> getIsMadeToName() {
		return isMadeToName;
	}

	/**
	 * Sets the checks if is made to name.
	 * 
	 * @param isMadeToName
	 *            the new checks if is made to name
	 */
	public void setIsMadeToName(Set<String> isMadeToName) {
		this.isMadeToName = isMadeToName;
	}

	/**
	 * Gets the do not share clinical document type codes.
	 * 
	 * @return the do not share clinical document type codes
	 */
	public Set<String> getDoNotShareClinicalDocumentTypeCodes() {
		return doNotShareClinicalDocumentTypeCodes;
	}

	/**
	 * Sets the do not share clinical document type codes.
	 * 
	 * @param doNotShareClinicalDocumentTypeCodes
	 *            the new do not share clinical document type codes
	 */
	public void setDoNotShareClinicalDocumentTypeCodes(
			Set<String> doNotShareClinicalDocumentTypeCodes) {
		this.doNotShareClinicalDocumentTypeCodes = doNotShareClinicalDocumentTypeCodes;
	}

	/**
	 * Gets the do not share clinical document section type codes.
	 * 
	 * @return the do not share clinical document section type codes
	 */
	public Set<String> getDoNotShareClinicalDocumentSectionTypeCodes() {
		return doNotShareClinicalDocumentSectionTypeCodes;
	}

	/**
	 * Sets the do not share clinical document section type codes.
	 * 
	 * @param doNotShareClinicalDocumentSectionTypeCodes
	 *            the new do not share clinical document section type codes
	 */
	public void setDoNotShareClinicalDocumentSectionTypeCodes(
			Set<String> doNotShareClinicalDocumentSectionTypeCodes) {
		this.doNotShareClinicalDocumentSectionTypeCodes = doNotShareClinicalDocumentSectionTypeCodes;
	}

	/**
	 * Gets the do not share sensitivity policy codes.
	 * 
	 * @return the do not share sensitivity policy codes
	 */
	public Set<String> getDoNotShareSensitivityPolicyCodes() {
		return doNotShareSensitivityPolicyCodes;
	}

	/**
	 * Sets the do not share sensitivity policy codes.
	 * 
	 * @param doNotShareSensitivityPolicyCodes
	 *            the new do not share sensitivity policy codes
	 */
	public void setDoNotShareSensitivityPolicyCodes(
			Set<String> doNotShareSensitivityPolicyCodes) {
		this.doNotShareSensitivityPolicyCodes = doNotShareSensitivityPolicyCodes;
	}

	/**
	 * Gets the do not share for purpose of use codes.
	 * 
	 * @return the do not share for purpose of use codes
	 */
	public Set<String> getShareForPurposeOfUseCodes() {
		return shareForPurposeOfUseCodes;
	}

	/**
	 * Sets the do not share for purpose of use codes.
	 *
	 * @param shareForPurposeOfUseCodes the new share for purpose of use codes
	 */
	public void setShareForPurposeOfUseCodes(
			Set<String> shareForPurposeOfUseCodes) {
		this.shareForPurposeOfUseCodes = shareForPurposeOfUseCodes;
	}

	/**
	 * Gets the consent stage.
	 * 
	 * @return the consent stage
	 */
	public int getConsentStage() {
		return consentStage;
	}

	/**
	 * Sets the consent stage.
	 * 
	 * @param consentStage
	 *            the new consent stage
	 */
	public void setConsentStage(int consentStage) {
		this.consentStage = consentStage;
	}

	/**
	 * Gets the do not share clinical concept codes.
	 * 
	 * @return the do not share clinical concept codes
	 */
	public Set<String> getDoNotShareClinicalConceptCodes() {
		return doNotShareClinicalConceptCodes;
	}

	/**
	 * Sets the do not share clinical concept codes.
	 * 
	 * @param doNotShareClinicalConceptCodes
	 *            the new do not share clinical concept codes
	 */
	public void setDoNotShareClinicalConceptCodes(
			Set<String> doNotShareClinicalConceptCodes) {
		this.doNotShareClinicalConceptCodes = doNotShareClinicalConceptCodes;
	}

	/**
	 * Gets the revoke stage.
	 * 
	 * @return the revoke stage
	 */
	public int getRevokeStage() {
		return revokeStage;
	}

	/**
	 * Sets the revoke stage.
	 * 
	 * @param revokeStage
	 *            the new revoke stage
	 */
	public void setRevokeStage(int revokeStage) {
		this.revokeStage = revokeStage;
	}

}
