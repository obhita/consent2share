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
 * The Class SignedConsentDto.
 */
public class SignedConsentDto {

	/** The xacml policy id. */
	private String xacmlCcdId;

	/**
	 * The XACML policy to give access to consentTo(Recipient) provider for CCD.
	 */
	private byte[] xacmlCcd;

	/**
	 * The XACML policy to give access to consentFrom(Intermediary) provider for
	 * consent pdf.
	 */
	private byte[] xacmlPdfConsentFrom;

	/**
	 * The XACML policy to give access to consentTo(Recipient) provider for
	 * consent pdf.
	 */
	private byte[] xacmlPdfConsentTo;

	/** The signed pdf consent. */
	private byte[] signedPdfConsent;

	/** The c2s pcm patient id. */
	private long pcmPatientId;

	/** The c2s pcm consent id. */
	private String pcmConsentId;

	/** the c2s local patient identifier (mrn). */
	private String patientMrn;

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
	 * Gets the xacml ccd.
	 *
	 * @return the xacml ccd
	 */
	public byte[] getXacmlCcd() {
		return xacmlCcd;
	}

	/**
	 * Sets the xacml ccd.
	 *
	 * @param xacmlCcd
	 *            the new xacml ccd
	 */
	public void setXacmlCcd(byte[] xacmlCcd) {
		this.xacmlCcd = xacmlCcd;
	}

	/**
	 * Gets the xacml pdf consent from.
	 *
	 * @return the xacml pdf consent from
	 */
	public byte[] getXacmlPdfConsentFrom() {
		return xacmlPdfConsentFrom;
	}

	/**
	 * Sets the xacml pdf consent from.
	 *
	 * @param xacmlPdfConsentFrom
	 *            the new xacml pdf consent from
	 */
	public void setXacmlPdfConsentFrom(byte[] xacmlPdfConsentFrom) {
		this.xacmlPdfConsentFrom = xacmlPdfConsentFrom;
	}

	/**
	 * Gets the xacml pdf consent to.
	 *
	 * @return the xacml pdf consent to
	 */
	public byte[] getXacmlPdfConsentTo() {
		return xacmlPdfConsentTo;
	}

	/**
	 * Sets the xacml pdf consent to.
	 *
	 * @param xacmlPdfConsentTo
	 *            the new xacml pdf consent to
	 */
	public void setXacmlPdfConsentTo(byte[] xacmlPdfConsentTo) {
		this.xacmlPdfConsentTo = xacmlPdfConsentTo;
	}

	/**
	 * Gets the signed pdf consent.
	 *
	 * @return the signed pdf consent
	 */
	public byte[] getSignedPdfConsent() {
		return signedPdfConsent;
	}

	/**
	 * Sets the signed pdf consent.
	 *
	 * @param signedPdfConsent
	 *            the new signed pdf consent
	 */
	public void setSignedPdfConsent(byte[] signedPdfConsent) {
		this.signedPdfConsent = signedPdfConsent;
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
	 * Gets the pcm consent id.
	 *
	 * @return the pcm consent id
	 */
	public String getPcmConsentId() {
		return pcmConsentId;
	}

	/**
	 * Sets the pcm consent id.
	 *
	 * @param pcmConsentId
	 *            the new pcm consent id
	 */
	public void setPcmConsentId(String pcmConsentId) {
		this.pcmConsentId = pcmConsentId;
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
}
