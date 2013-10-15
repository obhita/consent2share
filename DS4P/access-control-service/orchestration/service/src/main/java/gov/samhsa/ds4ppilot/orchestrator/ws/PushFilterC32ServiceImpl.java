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

import gov.samhsa.ds4ppilot.contract.pushorchestrator.FilterC32ServicePortType;
import gov.samhsa.ds4ppilot.orchestrator.Orchestrator;
import gov.samhsa.ds4ppilot.schema.orchestrator.RegisteryStoredQueryRequest;
import gov.samhsa.ds4ppilot.schema.orchestrator.RegisteryStoredQueryResponse;
import gov.samhsa.ds4ppilot.schema.orchestrator.RetrieveDocumentSetRequest;
import gov.samhsa.ds4ppilot.schema.orchestrator.RetrieveDocumentSetResponse;
import gov.samhsa.ds4ppilot.schema.pushorchestrator.FilterC32Request;
import gov.samhsa.ds4ppilot.schema.pushorchestrator.FilterC32Response;

import javax.jws.WebService;

/**
 * The Class FilterC32ServiceImpl.
 */
@WebService(targetNamespace = "http://www.samhsa.gov/ds4ppilot/contract/pushorchestrator", portName = "PushFilterC32Port", serviceName = "PushFilterC32Service", endpointInterface = "gov.samhsa.ds4ppilot.contract.pushorchestrator.FilterC32ServicePortType")
public class PushFilterC32ServiceImpl implements FilterC32ServicePortType {
	/** The orchestrator. */
	private Orchestrator orchestrator;

	/**
	 * Instantiates a new filter c32 service implementation.
	 */
	public PushFilterC32ServiceImpl() {
	}

	/**
	 * Instantiates a new filter C32 service implementation.
	 * 
	 * @param Orchestrator
	 *            the orchestrator
	 */
	public PushFilterC32ServiceImpl(Orchestrator orchestrator) {

		this.orchestrator = orchestrator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.ds4ppilot.contract.orchestrator.FilterC32ServicePortType
	 * #filterC32(gov.samhsa.ds4ppilot.schema.orchestrator.FilterC32Request)
	 */
	@Override
	public FilterC32Response filterC32(FilterC32Request parameters) {

		gov.samhsa.ds4ppilot.schema.orchestrator.FilterC32Response c32Response = null;
		FilterC32Response pushC32Response = new FilterC32Response();
		c32Response = orchestrator.handleC32Request(
				parameters.getPatientId(), parameters.isPackageAsXdm(),
				parameters.getSenderEmailAddress(),
				parameters.getRecipientEmailAddress());

		pushC32Response.setFilteredStreamBody(c32Response.getFilteredStreamBody());
		pushC32Response.setMaskedDocument(c32Response.getMaskedDocument());
		pushC32Response.setPatientId(c32Response.getPatientId());
		pushC32Response.setPdpDecision(c32Response.getPdpDecision());
		return pushC32Response;
	}

	

	/**
	 * Sets the orchestrator.
	 * 
	 * @param orchestrator
	 *            the new orchestrator
	 */
	public void setOrchestrator(Orchestrator orchestrator) {
		this.orchestrator = orchestrator;
	}

	/**
	 * After properties set.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void afterPropertiesSet() throws Exception {
		if (orchestrator == null) {
			throw new IllegalArgumentException(
					String.format(
							"You must set the orchestrator property of any beans of type {0}.",
							this.getClass()));
		}
	}
}
