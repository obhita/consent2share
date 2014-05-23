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
package gov.samhsa.acs.documentsegmentation;

import ch.qos.logback.audit.AuditException;
import gov.samhsa.acs.common.dto.XacmlRequest;
import gov.samhsa.acs.common.validation.exception.XmlDocumentReadFailureException;
import gov.samhsa.acs.documentsegmentation.exception.InvalidOriginalClinicalDocumentException;
import gov.samhsa.acs.documentsegmentation.exception.InvalidSegmentedClinicalDocumentException;
import gov.samhsa.consent2share.schema.documentsegmentation.SegmentDocumentResponse;

/**
 * The Interface DocumentSegmentation.
 */
public interface DocumentSegmentation {

	/**
	 * Segment document.
	 *
	 * @param document the document
	 * @param enforcementPolicies the enforcement policies
	 * @param packageAsXdm the package as xdm
	 * @param encryptDocument the encrypt document
	 * @param senderEmailAddress the sender email address
	 * @param recipientEmailAddress the recipient email address
	 * @param xdsDocumentEntryUniqueId the xds document entry unique id
	 * @param xacmlRequest the xacml request
	 * @param isAudited the is audited
	 * @return the segment document response
	 * @throws XmlDocumentReadFailureException the xml document read failure exception
	 * @throws InvalidOriginalClinicalDocumentException the invalid original clinical document exception
	 * @throws InvalidSegmentedClinicalDocumentException the invalid segmented clinical document exception
	 * @throws AuditException the audit exception
	 */
	public SegmentDocumentResponse segmentDocument(String document,
			String enforcementPolicies, boolean packageAsXdm,
			boolean encryptDocument, String senderEmailAddress,
			String recipientEmailAddress, String xdsDocumentEntryUniqueId, XacmlRequest xacmlRequest, boolean isAudited)
			throws XmlDocumentReadFailureException,
			InvalidOriginalClinicalDocumentException,
			InvalidSegmentedClinicalDocumentException, AuditException;
}
