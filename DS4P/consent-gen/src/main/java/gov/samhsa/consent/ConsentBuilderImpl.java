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
package gov.samhsa.consent;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * The Class ConsentBuilderImpl.
 */
public class ConsentBuilderImpl implements ConsentBuilder {

	/** The consent dto factory. */
	@Autowired
	ConsentDtoFactory consentDtoFactory;

	/** The consent transformer. */
	@Autowired
	ConsentTransformer consentTransformer;

	/** The Constant CDAR2XSLNAME. */
	private final static String CDAR2XSLNAME = "c2cdar2.xsl";

	/** The Constant XACMLXSLNAME. */
	private final static String XACMLXSLNAME = "c2xacml.xsl";

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent.ConsentBuilder#buildConsent2Cdar2(long)
	 */
	@Override
	public String buildConsent2Cdar2(long consentId) throws ConsentGenException {
		ConsentDto consentDto = consentDtoFactory.createConsentDto(consentId);
		String cdar2 = consentTransformer.transform(consentDto, CDAR2XSLNAME,
				null);
		return cdar2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent.ConsentBuilder#buildConsent2Xacml(java.lang.Object)
	 */
	@Override
	public String buildConsent2Xacml(Object obj) throws ConsentGenException {
		ConsentDto consentDto = consentDtoFactory.createConsentDto(obj);
		String xacml = consentTransformer.transform(consentDto, XACMLXSLNAME,
				consentDto.getPatientDto().getEnterpriseIdentifier());
		return xacml;
	}

	/**
	 * Sets the consent dto factory.
	 * 
	 * @param consentDtoFactory
	 *            the new consent dto factory
	 */
	public void setConsentDtoFactory(ConsentDtoFactory consentDtoFactory) {
		this.consentDtoFactory = consentDtoFactory;
	}

	/**
	 * Sets the consent transformer.
	 * 
	 * @param consentTransformer
	 *            the new consent transformer
	 */
	public void setConsentTransformer(ConsentTransformer consentTransformer) {
		this.consentTransformer = consentTransformer;
	}
}
