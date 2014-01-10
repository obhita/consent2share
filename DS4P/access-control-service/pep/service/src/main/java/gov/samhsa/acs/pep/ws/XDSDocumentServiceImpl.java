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
import gov.samhsa.ds4ppilot.contract.pep.XDSDocumentServicePortType;
import gov.samhsa.ds4ppilot.schema.pep.SaveDocumentSetToXdsRepositoryRequest;
import gov.samhsa.ds4ppilot.schema.pep.SaveDocumentSetToXdsRepositoryResponse;

import javax.jws.WebService;

@WebService(targetNamespace = "http://www.samhsa.gov/ds4ppilot/contract/pep", portName = "XDSDocumentServicePort", serviceName = "XDSDocumentService", endpointInterface = "gov.samhsa.ds4ppilot.contract.pep.XDSDocumentServicePortType")
public class XDSDocumentServiceImpl implements XDSDocumentServicePortType {

	private Pep pep;

	public XDSDocumentServiceImpl() {
	}

	public XDSDocumentServiceImpl(Pep pep) {

		this.pep = pep;
	}

	@Override
	public SaveDocumentSetToXdsRepositoryResponse saveDocumentSetToXdsRepository(
			SaveDocumentSetToXdsRepositoryRequest parameters) {
		SaveDocumentSetToXdsRepositoryResponse response = new SaveDocumentSetToXdsRepositoryResponse();

		boolean result = pep.saveDocumentSetToXdsRepository(parameters
				.getDocumentSet());

		response.setReturn(result);
		return response;
	}

	public void setPep(Pep pep) {
		this.pep = pep;
	}

	public void afterPropertiesSet() throws Exception {
		if (pep == null) {
			throw new IllegalArgumentException(
					String.format(
							"You must set the pep property of any beans of type {0}.",
							this.getClass()));
		}
	}
}
