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
 * The Class RevokedConsentDto.
 */
public class RevokedConsentDto {

	/** The xacml ccd id. */
	private String xacmlCcdId;

	/** The pcm consent id. */
	private long pcmConsentId;

	/** The pcm patient id. */
	private long pcmPatientId;

	/** The patient eid. */
	private String patientEid;

	/** The patient mrn. */
	private String patientMrn;

	/** The revoked pdf consent. */
	private byte[] revokedPdfConsent;

	/**
	 * Gets the xacml ccd id.
	 *
	 * @return the xacml ccd id
	 */
	public String getXacmlCcdId() {
		return xacmlCcdId;
	}

	/**
	 * Sets the xacml ccd id.
	 *
	 * @param xacmlCcdId
	 *            the new xacml ccd id
	 */
	public void setXacmlCcdId(String xacmlCcdId) {
		this.xacmlCcdId = xacmlCcdId;
	}

	/**
	 * Gets the pcm consent id.
	 *
	 * @return the pcm consent id
	 */
	public long getPcmConsentId() {
		return pcmConsentId;
	}

	/**
	 * Sets the pcm consent id.
	 *
	 * @param pcmConsentId
	 *            the new pcm consent id
	 */
	public void setPcmConsentId(long pcmConsentId) {
		this.pcmConsentId = pcmConsentId;
	}

	/**
	 * Gets the pcm patient id.
	 *
	 * @return the pcm patient id
	 */
	public long getPcmPatientId() {
		return pcmPatientId;
	}

	/**
	 * Sets the pcm patient id.
	 *
	 * @param pcmPatientId
	 *            the new pcm patient id
	 */
	public void setPcmPatientId(long pcmPatientId) {
		this.pcmPatientId = pcmPatientId;
	}

	/**
	 * Gets the patient eid.
	 *
	 * @return the patient eid
	 */
	public String getPatientEid() {
		return patientEid;
	}

	/**
	 * Sets the patient eid.
	 *
	 * @param patientEid
	 *            the new patient eid
	 */
	public void setPatientEid(String patientEid) {
		this.patientEid = patientEid;
	}

	/**
	 * Gets the patient mrn.
	 *
	 * @return the patient mrn
	 */
	public String getPatientMrn() {
		return patientMrn;
	}

	/**
	 * Sets the patient mrn.
	 *
	 * @param patientMrn
	 *            the new patient mrn
	 */
	public void setPatientMrn(String patientMrn) {
		this.patientMrn = patientMrn;
	}

	/**
	 * Gets the revoked pdf consent.
	 *
	 * @return the revoked pdf consent
	 */
	public byte[] getRevokedPdfConsent() {
		return revokedPdfConsent;
	}

	/**
	 * Sets the revoked pdf consent.
	 *
	 * @param revokedPdfConsent
	 *            the new revoked pdf consent
	 */
	public void setRevokedPdfConsent(byte[] revokedPdfConsent) {
		this.revokedPdfConsent = revokedPdfConsent;
	}
}
