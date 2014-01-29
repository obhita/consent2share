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

import gov.samhsa.acs.common.dto.XacmlRequest;
import gov.samhsa.acs.common.dto.XacmlResponse;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.contexthandler.ContextHandler;
import gov.samhsa.acs.contexthandler.exception.NoPolicyFoundException;
import gov.samhsa.acs.documentsegmentation.DocumentSegmentation;
import gov.samhsa.acs.pep.Pep;
import gov.samhsa.acs.pep.saml.SamlTokenParser;
import gov.samhsa.acs.xdsb.common.XdsbErrorFactory;
import gov.samhsa.acs.xdsb.registry.wsclient.adapter.XdsbRegistryAdapter;
import gov.samhsa.acs.xdsb.repository.wsclient.adapter.XdsbRepositoryAdapter;
import gov.samhsa.ds4ppilot.contract.pep.FilterC32ServicePortType;
import gov.samhsa.ds4ppilot.schema.pep.FilterC32Request;
import gov.samhsa.ds4ppilot.schema.pep.FilterC32Response;
import gov.samhsa.ds4ppilot.schema.pep.PushC32Request;
import gov.samhsa.ds4ppilot.schema.pep.PushC32Response;
import gov.samhsa.ds4ppilot.schema.pep.RegisteryStoredQueryRequest;
import gov.samhsa.ds4ppilot.schema.pep.RegisteryStoredQueryResponse;
import gov.samhsa.ds4ppilot.schema.pep.RetrieveDocumentSetRequest;
import gov.samhsa.ds4ppilot.schema.pep.RetrieveDocumentSetResponse;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import org.apache.ws.security.SAMLTokenPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class FilterC32ServiceImpl.
 */
@WebService(targetNamespace = "http://www.samhsa.gov/ds4ppilot/contract/pep", portName = "FilterC32Port", serviceName = "FilterC32Service", endpointInterface = "gov.samhsa.ds4ppilot.contract.pep.FilterC32ServicePortType")
public class FilterC32ServiceImpl implements FilterC32ServicePortType {

	// Constants
	/** The Constant PERMIT. */
	private static final String PERMIT = "PERMIT";

	/** The Constant SUPPORTED_ADHOCQUERY_RESPONSE_TYPE. */
	private static final String SUPPORTED_ADHOCQUERY_RESPONSE_TYPE = "LeafClass";

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// Services
	/** The xdsb registry. */
	private XdsbRegistryAdapter xdsbRegistry;

	/** The xdsb repository. */
	private XdsbRepositoryAdapter xdsbRepository;

	/** The xdsb error factory. */
	private XdsbErrorFactory xdsbErrorFactory;

	/** The context handler. */
	private ContextHandler contextHandler;

	/** The document segmentation. */
	private DocumentSegmentation documentSegmentation;

	/** The marshaller. */
	private SimpleMarshaller marshaller;
	
	@Resource
	private WebServiceContext context;

	private SamlTokenParser samlTokenParser;
	
	private SAMLTokenPrincipal samlTokenPrincipal;
	
	
	
	
	/** The pep. */
	private Pep pep;

	/**
	 * Instantiates a new filter c32 service implementation.
	 */
	public FilterC32ServiceImpl() {
	}

	/**
	 * Instantiates a new filter C32 service implementation.
	 * 
	 * @param pep
	 *
	 */
	public FilterC32ServiceImpl(Pep pep) {

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

		FilterC32Response c32Response = pep.handleC32Request(
				parameters.getPatientId(), parameters.isPackageAsXdm(),
				parameters.getSenderEmailAddress(),
				parameters.getRecipientEmailAddress());

		return c32Response;
	}

	@Override
	public RetrieveDocumentSetResponse retrieveDocumentSet(
			RetrieveDocumentSetRequest parameters) {

		RetrieveDocumentSetResponse response = pep
				.retrieveDocumentSetRequest(parameters.getHomeCommunityId(),
						parameters.getRepositoryUniqueId(),
						parameters.getDocumentUniqueId(),
						parameters.getMessageId(),
						parameters.getEnforcePolicy());

		return response;
	}

	@Override
	public RegisteryStoredQueryResponse registeryStoredQuery(
			RegisteryStoredQueryRequest parameters) {
		RegisteryStoredQueryResponse response = pep
				.registeryStoredQueryRequest(parameters.getPatientId(),
						parameters.getEnforcePolicy());

		return response;
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

	@Override
	public PushC32Response pushC32(PushC32Request parameters) {
		System.out.println("PUSH C32.....");
		//pep.handleC32Request(parameters.getOriginalC32(), parameters.getSenderNpi(), parameters.getRecipientNpi());
		
//
//		if (samlTokenParser == null) {
//			samlTokenParser = new SamlTokenParser();
//		}
//	    samlTokenPrincipal = (SAMLTokenPrincipal)(context.getUserPrincipal());
//		
//		String resourceId = samlTokenParser.parse(samlTokenPrincipal.getToken(), "urn:oasis:names:tc:xacml:1.0:resource:resource-id");
//		String intermediarySubject = samlTokenParser.parse(samlTokenPrincipal.getToken(), "urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject");
//		String purposeOfUse = samlTokenParser.parse(samlTokenPrincipal.getToken(), "urn:oasis:names:tc:xspa:1.0:subject:purposeofuse");
//		String recipientSubject = samlTokenParser.parse(samlTokenPrincipal.getToken(), "urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject");
//		XacmlRequest xacmlRequest = setXacmlRequest(resourceId, purposeOfUse, intermediarySubject, recipientSubject);
//		
//		
//		if (!validateRetrieveDocumentSetRequest(input)) {
//			return xdsbErrorFactory
//					.errorRetrieveDocumentSetResponseMultipleRepositoryId();
//		}
//		
//		XacmlResponse xacmlResponse = null;
//		try{
//			xacmlResponse = contextHandler
//				.enforcePolicy(xacmlRequest);
//		}
//		catch(NoPolicyFoundException e){
//			logger.error(e.getMessage(), e);
//			return xdsbErrorFactory.errorRetrieveDocumentSetResponseNoConsentsFound(resourceId);
//		}
		
		
		return null;
	}

}
