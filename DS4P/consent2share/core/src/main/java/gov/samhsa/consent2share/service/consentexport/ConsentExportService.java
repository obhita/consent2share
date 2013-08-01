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
package gov.samhsa.consent2share.service.consentexport;

import gov.samhsa.consent2share.domain.consent.Consent;

import java.io.ByteArrayOutputStream;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.security.access.annotation.Secured;

/**
 * The Interface ConsentExportService.
 */
@Secured("ROLE_USER")
public abstract interface ConsentExportService {
	
	/**
	 * Export cda r2 consent.
	 *
	 * @param consentId the consent id
	 */
	String  exportCDAR2Consent(Long consentId);
	
	/**
	 * Export xacml consent.
	 *
	 * @param consentId the consent id
	 */
	String exportXACMLConsent(Long consentId);
	
	/**
	 * Jaxb marshall.
	 *
	 * @param consentExportDto the consent export dto
	 * @return the byte array output stream
	 */
	ByteArrayOutputStream jaxbMarshall(ConsentExportDto consentExportDto);
	
	/**
	 * Saxon transform.
	 *
	 * @param xslID the xsl id
	 * @param streamSource the stream source
	 * @return the stream result
	 */
	StreamResult saxonTransform(String xslID, StreamSource streamSource );
	
	/**
	 * Make consent export dto.
	 *
	 * @return the consent export dto
	 */
	ConsentExportDto makeConsentExportDto();
	
	/**
	 * Consent export map.
	 *
	 * @param consent the consent
	 * @return the consent export dto
	 */
	ConsentExportDto consentExportMap(Consent consent);
	
	/**
	 * Make type codes dto.
	 *
	 * @return the type codes dto
	 */
	TypeCodesDto makeTypeCodesDto();
}
