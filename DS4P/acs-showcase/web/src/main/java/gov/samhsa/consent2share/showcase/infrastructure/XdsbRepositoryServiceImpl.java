/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *   
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *       * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *       * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *       * Neither the name of the <organization> nor the
 *         names of its contributors may be used to endorse or promote products
 *         derived from this software without specific prior written permission.
 *   
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *   DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.consent2share.showcase.infrastructure;

import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.acs.xdsb.repository.wsclient.XDSRepositorybWebServiceClient;
import gov.samhsa.acs.xdsb.repository.wsclient.adapter.RetrieveDocumentSetResponseFilter;
import gov.samhsa.acs.xdsb.repository.wsclient.adapter.XdsbRepositoryAdapter;
import gov.samhsa.consent2share.showcase.exception.AcsShowCaseException;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The Class XdsbRepositoryServiceImpl.
 */
@Component
public class XdsbRepositoryServiceImpl implements XdsbRepositoryService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The xdsb repository. */
	private XdsbRepositoryAdapter xdsbRepository;

	/**
	 * Instantiates a new xdsb repository service impl.
	 * 
	 * @param endpointAddress
	 *            the endpoint address
	 * @param marshaller
	 *            the marshaller
	 * @param responseFilter
	 *            the response filter
	 */
	@Autowired
	public XdsbRepositoryServiceImpl(
			@Value("${xdsbRepositoryEndpointAddress}") String endpointAddress,
			SimpleMarshaller marshaller,
			RetrieveDocumentSetResponseFilter responseFilter) {
		this.xdsbRepository = new XdsbRepositoryAdapter(
				new XDSRepositorybWebServiceClient(endpointAddress),
				marshaller, responseFilter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.showcase.infrastructure.XdsbRepositoryService
	 * #provideAndRegisterDocumentSet(java.lang.String, java.lang.String,
	 * gov.samhsa
	 * .consent2share.accesscontrolservice.xdsb.common.XdsbDocumentType)
	 */
	@Override
	public RegistryResponse provideAndRegisterDocumentSet(
			String documentXmlString, String domainId,
			XdsbDocumentType xdsbDocumentType) {
		RegistryResponse response = null;
		try {
			response = xdsbRepository.provideAndRegisterDocumentSet(
					documentXmlString, domainId, xdsbDocumentType);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			throw new AcsShowCaseException(
					"XdsbRepositoryService failed! Cannot invoke the provideAndRegisterDocumentSet operation on xdsbRepository!");
		}
		return response;
	}

	/**
	 * Gets the xdsb repository.
	 * 
	 * @return the xdsb repository
	 */
	XdsbRepositoryAdapter getXdsbRepository() {
		return xdsbRepository;
	}

	/**
	 * Sets the xdsb repository.
	 * 
	 * @param xdsbRepository
	 *            the new xdsb repository
	 */
	void setXdsbRepository(XdsbRepositoryAdapter xdsbRepository) {
		this.xdsbRepository = xdsbRepository;
	}
}
