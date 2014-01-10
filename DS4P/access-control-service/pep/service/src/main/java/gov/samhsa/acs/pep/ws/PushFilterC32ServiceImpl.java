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
package gov.samhsa.acs.pep.ws;

import gov.samhsa.acs.pep.Pep;
import gov.samhsa.ds4ppilot.contract.pushpep.FilterC32ServicePortType;
import gov.samhsa.ds4ppilot.schema.pushpep.FilterC32Request;
import gov.samhsa.ds4ppilot.schema.pushpep.FilterC32Response;

import javax.jws.WebService;

/**
 * The Class FilterC32ServiceImpl.
 */
@WebService(targetNamespace = "http://www.samhsa.gov/ds4ppilot/contract/pushpep", portName = "PushFilterC32Port", serviceName = "PushFilterC32Service", endpointInterface = "gov.samhsa.ds4ppilot.contract.pushpep.FilterC32ServicePortType")
public class PushFilterC32ServiceImpl implements FilterC32ServicePortType {
	/** The pep. */
	private Pep pep;

	/**
	 * Instantiates a new filter c32 service implementation.
	 */
	public PushFilterC32ServiceImpl() {
	}

	/**
	 * Instantiates a new filter C32 service implementation.
	 * 
	 * @param pep
	 *
	 */
	public PushFilterC32ServiceImpl(Pep pep) {

		this.pep = pep;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.ds4ppilot.contract.pep.FilterC32ServicePortType
	 * #filterC32(gov.samhsa.ds4ppilot.schema.pep.FilterC32Request)
	 */
	@Override
	public FilterC32Response filterC32(FilterC32Request parameters) {

		gov.samhsa.ds4ppilot.schema.pep.FilterC32Response c32Response = null;
		FilterC32Response pushC32Response = new FilterC32Response();
		c32Response = pep.handleC32Request(
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
	 * Sets the pep.
	 * 
	 * @param pep
	 *            the new pep
	 */
	public void setPep(Pep pep) {
		this.pep = pep;
	}

	/**
	 * After properties set.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void afterPropertiesSet() throws Exception {
		if (pep == null) {
			throw new IllegalArgumentException(
					String.format(
							"You must set the pep property of any beans of type {0}.",
							this.getClass()));
		}
	}
}
