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
package gov.samhsa.acs.pep;

import gov.samhsa.ds4ppilot.schema.pep.FilterC32Response;
import gov.samhsa.ds4ppilot.schema.pep.RegisteryStoredQueryResponse;
import gov.samhsa.ds4ppilot.schema.pep.RetrieveDocumentSetResponse;
import gov.va.ehtac.ds4p.ws.EnforcePolicy;


/**
 * The Interface Pep.
 */
public interface Pep {

	/**
	 * Handle c32 request.
	 *
	 * @param patientId the patient id
	 * @param packageAsXdm the package as XDM
	 * @param senderEmailAddress the sender email address
	 * @param recipientEmailAddress the recipient email address
	 * @return the filter c32 response
	 */
	public FilterC32Response handleC32Request(String patientId, boolean packageAsXdm, String senderEmailAddress, String recipientEmailAddress);
	
	/**
	 * Handle c32 request.
	 *
	 * @param recepientSubjectNPI the recepient subject npi
	 * @param intermediarySubjectNPI the intermediary subject npi
	 * @param resourceId the resource id
	 * @param packageAsXdm the package as xdm
	 * @param senderEmailAddress the sender email address
	 * @param recipientEmailAddress the recipient email address
	 * @param xdsDocumentEntryUniqueId the xds document entry unique id
	 * @return the filter c32 response
	 */
	public FilterC32Response handleC32Request(String recepientSubjectNPI, String intermediarySubjectNPI, String resourceId, boolean packageAsXdm, String senderEmailAddress, String recipientEmailAddress, String xdsDocumentEntryUniqueId);

	/**
	 * Retrieve document set request.
	 *
	 * @param homeCommunityId the home community id
	 * @param repositoryUniqueId the repository unique id
	 * @param documentUniqueId the document unique id
	 * @param messageId the message id
	 * @param enforcePolicy the enforce policy
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse retrieveDocumentSetRequest(String homeCommunityId, String repositoryUniqueId, String documentUniqueId, String messageId, EnforcePolicy enforcePolicy);

	/**
	 * Registery stored query request.
	 *
	 * @param patientId the patient id
	 * @param enforcePolicy the enforce policy
	 * @return the registery stored query response
	 */
	public RegisteryStoredQueryResponse registeryStoredQueryRequest(String patientId, EnforcePolicy enforcePolicy);

	/**
	 * Save document set to xds repository.
	 *
	 * @param documentSet the document set
	 * @return true, if successful
	 */
	public boolean saveDocumentSetToXdsRepository(String documentSet);
}
