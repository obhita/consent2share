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
package gov.samhsa.schemas.client;

import gov.samhsa.ds4ppilot.contract.orchestrator.XDSDocumentService;
import gov.samhsa.ds4ppilot.contract.orchestrator.XDSDocumentServicePortType;
import gov.samhsa.ds4ppilot.schema.orchestrator.SaveDocumentSetToXdsRepositoryRequest;
import gov.samhsa.ds4ppilot.schema.orchestrator.SaveDocumentSetToXdsRepositoryResponse;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.springframework.util.StringUtils;

public class XdsDocumentWebServiceClient {

	private String endpointAddress;

	public static void main(String[] args) {
		final String document = "PUI100010060001";

		XdsDocumentWebServiceClient XDSDocumentService = new XdsDocumentWebServiceClient(
				null);

		XDSDocumentService.saveDocumentSetToXdsRepository(document);
	}

	public XdsDocumentWebServiceClient(String endpointAddress) {
		this.endpointAddress = endpointAddress;
	}

	public boolean saveDocumentSetToXdsRepository(String document) {
		XDSDocumentServicePortType port;
		if (StringUtils.hasText(this.endpointAddress)) {
			port = createPort(endpointAddress);
		} else {
			// Using default endpoint address defined in the wsdl:port of wsdl
			// file
			port = createPort();
		}

		return saveDocumentSetToXdsRepository(port, document);
	}

	private boolean saveDocumentSetToXdsRepository(
			XDSDocumentServicePortType port, String document) {
		SaveDocumentSetToXdsRepositoryRequest request = new SaveDocumentSetToXdsRepositoryRequest();
		request.setDocumentSet(document);
		SaveDocumentSetToXdsRepositoryResponse response = port
				.saveDocumentSetToXdsRepository(request);

		return response.isReturn();
	}

	private XDSDocumentServicePortType createPort() {
		final URL WSDL_LOCATION = this.getClass().getClassLoader()
				.getResource("XdsDocumentService.wsdl");
		final QName SERVICE = new QName(
				"http://www.samhsa.gov/ds4ppilot/contract/orchestrator",
				"XDSDocumentService");

		XDSDocumentServicePortType port = new XDSDocumentService(WSDL_LOCATION,
				SERVICE).getXDSDocumentServicePort();
		return port;
	}

	private XDSDocumentServicePortType createPort(String endpointAddress) {
		XDSDocumentServicePortType port = createPort();
		BindingProvider bp = (BindingProvider) port;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				endpointAddress);
		return port;
	}
}
