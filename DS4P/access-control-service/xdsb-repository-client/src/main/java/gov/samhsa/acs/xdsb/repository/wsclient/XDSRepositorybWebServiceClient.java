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
package gov.samhsa.acs.xdsb.repository.wsclient;

import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;
import ihe.iti.xds_b._2007.XDSRepository;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

import org.springframework.util.StringUtils;
import org.tempuri.DocumentRepositoryService;

/**
 * The Class XDSRepositorybWebServiceClient.
 */
public class XDSRepositorybWebServiceClient {

	/** The endpoint address. */
	private final String endpointAddress;

	/**
	 * Instantiates a new xDS repositoryb web service client.
	 * 
	 * @param endpointAddress
	 *            the endpoint address
	 */
	public XDSRepositorybWebServiceClient(String endpointAddress) {
		this.endpointAddress = endpointAddress;
	}

	/**
	 * Retrieve document set request.
	 * 
	 * @param retrieveDocumentSet
	 *            the retrieve document set
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse retrieveDocumentSet(
			RetrieveDocumentSetRequest retrieveDocumentSet) {
		XDSRepository port = createPort();

		return port.retrieveDocumentSet(retrieveDocumentSet);
	}

	/**
	 * Provide and register document set reponse.
	 * 
	 * @param provideAndRegisterDocumentSet
	 *            the provide and register document set
	 * @return the registry response
	 */
	public RegistryResponse provideAndRegisterDocumentSet(
			ProvideAndRegisterDocumentSetRequest provideAndRegisterDocumentSet) {
		XDSRepository port = createPort();
		return port
				.provideAndRegisterDocumentSet(provideAndRegisterDocumentSet);

	}

	/**
	 * Creates the port.
	 * 
	 * @return the xDS repository
	 */
	private XDSRepository createPort() {
		final URL WSDL_LOCATION = this.getClass().getClassLoader()
				.getResource("XDS.b_repository.net.wsdl");
		final QName SERVICE = new QName("http://tempuri.org/",
				"DocumentRepositoryService");

		XDSRepository port = new DocumentRepositoryService(WSDL_LOCATION,
				SERVICE).getXDSRepositoryHTTPEndpoint();

		if (StringUtils.hasText(this.endpointAddress)) {
			BindingProvider bp = (BindingProvider) port;
			bp.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddress);

		}

		return port;
	}
}
