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

/**
 * The Class SimpleConsentDto.
 */
public class SimpleConsentDto {

	/** The consent. */
	private String consent;

	/** The patient id. */
	private long patientId;

	/** The e id. */
	private String eId;

	/**
	 * Instantiates a new simple consent dto.
	 */
	public SimpleConsentDto() {
	}

	/**
	 * Instantiates a new simple consent dto.
	 * 
	 * @param consent
	 *            the consent
	 * @param patientId
	 *            the patient id
	 * @param eId
	 *            the e id
	 */
	public SimpleConsentDto(String consent, long patientId, String eId) {
		super();
		this.consent = consent;
		this.patientId = patientId;
		this.eId = eId;
	}

	/**
	 * Gets the consent.
	 * 
	 * @return the consent
	 */
	public String getConsent() {
		return consent;
	}

	/**
	 * Sets the consent.
	 * 
	 * @param consent
	 *            the new consent
	 */
	public void setConsent(String consent) {
		this.consent = consent;
	}

	/**
	 * Gets the patient id.
	 * 
	 * @return the patient id
	 */
	public long getPatientId() {
		return patientId;
	}

	/**
	 * Sets the patient id.
	 * 
	 * @param patientId
	 *            the new patient id
	 */
	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}

	/**
	 * Gets the e id.
	 * 
	 * @return the e id
	 */
	public String geteId() {
		return eId;
	}

	/**
	 * Sets the e id.
	 * 
	 * @param eId
	 *            the new e id
	 */
	public void seteId(String eId) {
		this.eId = eId;
	}
}
