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
package gov.samhsa.acs.pep.xdsbrepository;

import gov.samhsa.acs.xdsb.repository.wsclient.XDSRepositorybWebServiceClient;
import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

/**
 * The Class XdsbRepositoryImpl.
 */
public class XdsbRepositoryImpl implements XdsbRepository {

	/** The endpoint address. */
	private final String endpointAddress;

	/**
	 * Instantiates a new xdsb repository impl.
	 * 
	 * @param endpointAddress
	 *            the endpoint address
	 */
	public XdsbRepositoryImpl(String endpointAddress) {
		this.endpointAddress = endpointAddress;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see XdsbRepository#
	 * provideAndRegisterDocumentSetRequest
	 * (ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequest)
	 */
	@Override
	public RegistryResponse provideAndRegisterDocumentSetRequest(
			ProvideAndRegisterDocumentSetRequest provideAndRegisterDocumentSetRequest) {
		RegistryResponse registryResponse = null;

		XDSRepositorybWebServiceClient xdsRepositoryWebServiceClient = new XDSRepositorybWebServiceClient(
				endpointAddress);
		registryResponse = xdsRepositoryWebServiceClient
				.provideAndRegisterDocumentSet(provideAndRegisterDocumentSetRequest);
		return registryResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see XdsbRepository#
	 * retrieveDocumentSetRequest
	 * (ihe.iti.xds_b._2007.RetrieveDocumentSetRequest)
	 */
	@Override
	public RetrieveDocumentSetResponse retrieveDocumentSetRequest(
			RetrieveDocumentSetRequest retrieveDocumentSetRequest) {
		RetrieveDocumentSetResponse retrieveDocumentSetRequestResponse = null;

		XDSRepositorybWebServiceClient xdsRepositoryWebServiceClient = new XDSRepositorybWebServiceClient(
				endpointAddress);
		retrieveDocumentSetRequestResponse = xdsRepositoryWebServiceClient
				.retrieveDocumentSet(retrieveDocumentSetRequest);
		return retrieveDocumentSetRequestResponse;
	}

}
