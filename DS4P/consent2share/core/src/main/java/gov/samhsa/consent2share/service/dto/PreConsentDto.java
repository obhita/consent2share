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

/**
 * The Class PreConsentDto.
 */
public class PreConsentDto {
	
	/** The id. */
	private String id;
	
	/** The organizational providers permitted to disclose. */
	private Set<String> organizationalProvidersPermittedToDisclose;
	
	/** The organizational providers disclosure is made to. */
	private Set<String> organizationalProvidersDisclosureIsMadeTo;
	
	/** The providers permitted to disclose. */
	private Set<String> providersPermittedToDisclose;
	
	/** The providers disclosure is made to. */
	private Set<String> providersDisclosureIsMadeTo;
	
	// Consent Sharing date
	/** The consent start. */
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date consentStart;

	/** The consent end. */
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date consentEnd;
	
	/** The do not share for purpose of use codes. */
	private Set<String> shareForPurposeOfUseCodes;
	
	/** The authorizer names. */
	private Set<String> authorizerNames;
	
	/** The made to names. */
	private Set<String> madeToNames;


	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the organizational providers permitted to disclose.
	 *
	 * @return the organizational providers permitted to disclose
	 */
	public Set<String> getOrganizationalProvidersPermittedToDisclose() {
		return organizationalProvidersPermittedToDisclose;
	}

	/**
	 * Sets the organizational providers permitted to disclose.
	 *
	 * @param organizationalProvidersPermittedToDisclose the new organizational providers permitted to disclose
	 */
	public void setOrganizationalProvidersPermittedToDisclose(
			Set<String> organizationalProvidersPermittedToDisclose) {
		this.organizationalProvidersPermittedToDisclose = organizationalProvidersPermittedToDisclose;
	}

	/**
	 * Gets the organizational providers disclosure is made to.
	 *
	 * @return the organizational providers disclosure is made to
	 */
	public Set<String> getOrganizationalProvidersDisclosureIsMadeTo() {
		return organizationalProvidersDisclosureIsMadeTo;
	}

	/**
	 * Sets the organizational providers disclosure is made to.
	 *
	 * @param organizationalProvidersDisclosureIsMadeTo the new organizational providers disclosure is made to
	 */
	public void setOrganizationalProvidersDisclosureIsMadeTo(
			Set<String> organizationalProvidersDisclosureIsMadeTo) {
		this.organizationalProvidersDisclosureIsMadeTo = organizationalProvidersDisclosureIsMadeTo;
	}

	/**
	 * Gets the providers permitted to disclose.
	 *
	 * @return the providers permitted to disclose
	 */
	public Set<String> getProvidersPermittedToDisclose() {
		return providersPermittedToDisclose;
	}

	/**
	 * Sets the providers permitted to disclose.
	 *
	 * @param providersPermittedToDisclose the new providers permitted to disclose
	 */
	public void setProvidersPermittedToDisclose(
			Set<String> providersPermittedToDisclose) {
		this.providersPermittedToDisclose = providersPermittedToDisclose;
	}

	/**
	 * Gets the providers disclosure is made to.
	 *
	 * @return the providers disclosure is made to
	 */
	public Set<String> getProvidersDisclosureIsMadeTo() {
		return providersDisclosureIsMadeTo;
	}

	/**
	 * Sets the providers disclosure is made to.
	 *
	 * @param providersDisclosureIsMadeTo the new providers disclosure is made to
	 */
	public void setProvidersDisclosureIsMadeTo(
			Set<String> providersDisclosureIsMadeTo) {
		this.providersDisclosureIsMadeTo = providersDisclosureIsMadeTo;
	}


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
	 * @param consentStart the new consent start
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
	 * Gets the authorizer names.
	 *
	 * @return the authorizer names
	 */
	public Set<String> getAuthorizerNames() {
		return authorizerNames;
	}
	
	/**
	 * Sets the authorizer names.
	 *
	 * @param authorizerNames the new authorizer names
	 */
	public void setAuthorizerNames(Set<String> authorizerNames) {
		this.authorizerNames = authorizerNames;
	}

	/**
	 * Gets the made to names.
	 *
	 * @return the made to names
	 */
	public Set<String> getMadeToNames() {
		return madeToNames;
	}
	
	/**
	 * Sets the made to names.
	 *
	 * @param madeToNames the new made to names
	 */
	public void setMadeToNames(Set<String> madeToNames) {
		this.madeToNames = madeToNames;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PreConsentDto [organizationalProvidersPermittedToDisclose="
				+ organizationalProvidersPermittedToDisclose
				+ ", organizationalProvidersDisclosureIsMadeTo="
				+ organizationalProvidersDisclosureIsMadeTo
				+ ", providersPermittedToDisclose="
				+ providersPermittedToDisclose
				+ ", providersDisclosureIsMadeTo="
				+ providersDisclosureIsMadeTo + ", consentStart="
				+ consentStart + ", consentEnd=" + consentEnd
				+ ", shareForPurposeOfUseCodes="
				+ shareForPurposeOfUseCodes + "]";
	}
}
