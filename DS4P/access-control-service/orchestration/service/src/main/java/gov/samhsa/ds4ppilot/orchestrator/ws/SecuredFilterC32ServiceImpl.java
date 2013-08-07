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
package gov.samhsa.ds4ppilot.orchestrator.ws;

import gov.samhsa.ds4ppilot.contract.securedorchestrator.SecuredFilterC32ServicePortType;
import gov.samhsa.ds4ppilot.orchestrator.SecuredOrchestrator;
import gov.samhsa.ds4ppilot.schema.securedorchestrator.RegisteryStoredQueryRequest;
import gov.samhsa.ds4ppilot.schema.securedorchestrator.RegisteryStoredQueryResponse;
import gov.samhsa.ds4ppilot.schema.securedorchestrator.RetrieveDocumentSetRequest;
import gov.samhsa.ds4ppilot.schema.securedorchestrator.RetrieveDocumentSetResponse;

import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.soap.Addressing;

import org.apache.cxf.annotations.EndpointProperties;

/**
 * The Class FilterC32ServiceImpl.
 */
@WebService(targetNamespace = "http://www.samhsa.gov/ds4ppilot/contract/securedorchestrator", portName = "SecuredFilterC32Port", serviceName = "SecuredFilterC32Service", endpointInterface = "gov.samhsa.ds4ppilot.contract.securedorchestrator.SecuredFilterC32ServicePortType")
@HandlerChain(file = "/samlhandler.xml")

public class SecuredFilterC32ServiceImpl implements
		SecuredFilterC32ServicePortType {

	/** The secured orchestrator. */
	private SecuredOrchestrator securedOrchestrator;

	/**
	 * Instantiates a new filter c32 service implementation.
	 */
	public SecuredFilterC32ServiceImpl() {
	}

	/**
	 * Instantiates a new filter C32 service implementation.
	 * 
	 * @param Orchestrator
	 *            the orchestrator
	 */
	public SecuredFilterC32ServiceImpl(SecuredOrchestrator securedOrchestrator) {

		this.securedOrchestrator = securedOrchestrator;
	}

	@Override
	public RetrieveDocumentSetResponse retrieveDocumentSet(
			RetrieveDocumentSetRequest parameters) {

		RetrieveDocumentSetResponse response = securedOrchestrator
				.retrieveDocumentSetRequest(parameters.getDocumentUniqueId(),
						parameters.getMessageId(),  parameters.getIntendedRecipient());

		return response;
	}

	@Override
	public RegisteryStoredQueryResponse registeryStoredQuery(
			RegisteryStoredQueryRequest parameters) {
		RegisteryStoredQueryResponse response = securedOrchestrator
				.registeryStoredQueryRequest(parameters.getPatientId(),
						parameters.getMessageId());

		return response;
	}

	/**
	 * Sets the orchestrator.
	 * 
	 * @param orchestrator
	 *            the new orchestrator
	 */
	public void setOrchestrator(SecuredOrchestrator securedOrchestrator) {
		this.securedOrchestrator = securedOrchestrator;
	}

	/**
	 * After properties set.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void afterPropertiesSet() throws Exception {
		if (securedOrchestrator == null) {
			throw new IllegalArgumentException(
					String.format(
							"You must set the orchestrator property of any beans of type {0}.",
							this.getClass()));
		}
	}

}
