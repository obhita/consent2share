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
package gov.samhsa.acs.xdsb.repository.wsclient.adapter;

import java.util.List;

import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.xdsb.common.UniqueOidProviderImpl;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.acs.xdsb.common.XdsbMetadataGenerator;
import gov.samhsa.acs.xdsb.common.XdsbMetadataGeneratorImpl;
import gov.samhsa.acs.xdsb.repository.wsclient.XDSRepositorybWebServiceClient;
import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequest;
import ihe.iti.xds_b._2007.ProvideAndRegisterDocumentSetRequest.Document;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest.DocumentRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;
import oasis.names.tc.ebxml_regrep.xsd.lcm._3.SubmitObjectsRequest;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

/**
 * The Class XdsbRepositoryAdapter.
 */
public class XdsbRepositoryAdapter {

	// Services
	/** The xdsb repository. */
	private XDSRepositorybWebServiceClient xdsbRepository;

	/** The marshaller. */
	private SimpleMarshaller marshaller;

	/** The response filter. */
	private RetrieveDocumentSetResponseFilter responseFilter;

	/**
	 * Instantiates a new xdsb repository adapter.
	 */
	public XdsbRepositoryAdapter() {
	}

	/**
	 * Instantiates a new xdsb repository adapter.
	 * 
	 * @param xdsbRepository
	 *            the xdsb repository
	 * @param marshaller
	 *            the marshaller
	 * @param responseFilter
	 *            the response filter
	 */
	public XdsbRepositoryAdapter(XDSRepositorybWebServiceClient xdsbRepository,
			SimpleMarshaller marshaller,
			RetrieveDocumentSetResponseFilter responseFilter) {

		this.xdsbRepository = xdsbRepository;
		this.marshaller = marshaller;
		this.responseFilter = responseFilter;
	}

	/**
	 * Provide and register document set request (direct call to the XDS.b
	 * repository service).
	 * 
	 * @param provideAndRegisterDocumentSetRequest
	 *            the provide and register document set request
	 * @return the registry response
	 */
	public RegistryResponse provideAndRegisterDocumentSet(
			ProvideAndRegisterDocumentSetRequest provideAndRegisterDocumentSetRequest) {
		return xdsbRepository
				.provideAndRegisterDocumentSet(provideAndRegisterDocumentSetRequest);
	}

	/**
	 * Provide and register document set request (indirect call to the XDS.b
	 * repository service with a simplified interface).
	 * 
	 * @param documentXmlString
	 *            the document xml string
	 * @param homeCommunityId
	 *            the home community id
	 * @param documentTypeForXdsbMetadata
	 *            the document type for xdsb metadata
	 * @return the registry response
	 * @throws Throwable
	 *             the throwable
	 */
	public RegistryResponse provideAndRegisterDocumentSet(
			String documentXmlString, String homeCommunityId,
			XdsbDocumentType documentTypeForXdsbMetadata) throws Throwable {

		String submitObjectRequestXml = generateMetadata(documentXmlString,
				homeCommunityId, documentTypeForXdsbMetadata);
		SubmitObjectsRequest submitObjectRequest = new SubmitObjectsRequest();
		submitObjectRequest = marshaller.unmarshallFromXml(
				SubmitObjectsRequest.class, submitObjectRequestXml);

		Document document = createDocument(documentXmlString);

		ProvideAndRegisterDocumentSetRequest request = createProvideAndRegisterDocumentSetRequest(
				submitObjectRequest, document);

		RegistryResponse response = provideAndRegisterDocumentSet(request);
		return response;
	}

	/**
	 * Retrieve document set request (direct call to XDS.b repository service).
	 * 
	 * @param request
	 *            the request
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse retrieveDocumentSet(
			RetrieveDocumentSetRequest request) {
		return xdsbRepository.retrieveDocumentSet(request);
	}

	/**
	 * Retrieve document set.
	 * 
	 * @param request
	 *            the request
	 * @param patientId
	 *            the patient id
	 * @param authorNPI
	 *            the author npi
	 * @return the retrieve document set response
	 * @throws Throwable
	 *             the throwable
	 */
	public RetrieveDocumentSetResponse retrieveDocumentSet(
			RetrieveDocumentSetRequest request, String patientId,
			String authorNPI) throws Throwable {
		RetrieveDocumentSetResponse response = xdsbRepository
				.retrieveDocumentSet(request);
		response = responseFilter.filterByPatientAndAuthor(response, patientId,
				authorNPI);
		return response;
	}

	/**
	 * Retrieve document set request (indirect call to the XDS.b repository
	 * service with a simplified interface).
	 * 
	 * @param documentUniqueId
	 *            the document unique id
	 * @param repositoryId
	 *            the repository id
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse retrieveDocumentSet(
			String documentUniqueId, String repositoryId) {
		RetrieveDocumentSetRequest request = createRetrieveDocumentSetRequest(
				documentUniqueId, repositoryId);

		RetrieveDocumentSetResponse retrieveDocumentSetResponse = retrieveDocumentSet(request);
		return retrieveDocumentSetResponse;
	}

	/**
	 * Retrieve document set.
	 * 
	 * @param docRequest
	 *            the doc request
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse retrieveDocumentSet(
			DocumentRequest docRequest) {
		RetrieveDocumentSetRequest request = createRetrieveDocumentSetRequest(docRequest);

		RetrieveDocumentSetResponse retrieveDocumentSetResponse = retrieveDocumentSet(request);
		return retrieveDocumentSetResponse;
	}

	/**
	 * Retrieve document set.
	 * 
	 * @param docRequest
	 *            the doc request
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse retrieveDocumentSet(
			List<DocumentRequest> docRequest) {
		RetrieveDocumentSetRequest request = createRetrieveDocumentSetRequest(docRequest);

		RetrieveDocumentSetResponse retrieveDocumentSetResponse = retrieveDocumentSet(request);
		return retrieveDocumentSetResponse;
	}

	/**
	 * Generate metadata.
	 * 
	 * @param documentXmlString
	 *            the document xml string
	 * @param homeCommunityId
	 *            the home community id
	 * @param documentTypeForXdsbMetadata
	 *            the document type for xdsb metadata
	 * @return the string
	 */
	String generateMetadata(String documentXmlString, String homeCommunityId,
			XdsbDocumentType documentTypeForXdsbMetadata) {
		XdsbMetadataGenerator xdsbMetadataGenerator = createXdsbMetadataGenerator(documentTypeForXdsbMetadata);
		String metadata = xdsbMetadataGenerator.generateMetadataXml(
				documentXmlString, homeCommunityId);
		return metadata;
	}

	/**
	 * Creates the xdsb metadata generator.
	 * 
	 * @param documentTypeForXdsbMetadata
	 *            the document type for xdsb metadata
	 * @return the xdsb metadata generator impl
	 */
	XdsbMetadataGeneratorImpl createXdsbMetadataGenerator(
			XdsbDocumentType documentTypeForXdsbMetadata) {
		return new XdsbMetadataGeneratorImpl(new UniqueOidProviderImpl(),
				documentTypeForXdsbMetadata, this.marshaller);
	}

	/**
	 * Creates the provide and register document set request.
	 * 
	 * @param submitObjectRequest
	 *            the submit object request
	 * @param document
	 *            the document
	 * @return the provide and register document set request
	 */
	ProvideAndRegisterDocumentSetRequest createProvideAndRegisterDocumentSetRequest(
			SubmitObjectsRequest submitObjectRequest, Document document) {
		ProvideAndRegisterDocumentSetRequest request = new ProvideAndRegisterDocumentSetRequest();
		request.setSubmitObjectsRequest(submitObjectRequest);
		request.getDocument().add(document);
		return request;
	}

	/**
	 * Creates the document.
	 * 
	 * @param documentXmlString
	 *            the document xml string
	 * @return the document
	 */
	Document createDocument(String documentXmlString) {
		Document document = new Document();
		document.setId("Document01");
		document.setValue(documentXmlString.getBytes());
		return document;
	}

	/**
	 * Creates the retrieve document set request.
	 * 
	 * @param documentUniqueId
	 *            the document unique id
	 * @param repositoryId
	 *            the repository id
	 * @return the retrieve document set request
	 */
	RetrieveDocumentSetRequest createRetrieveDocumentSetRequest(
			String documentUniqueId, String repositoryId) {
		RetrieveDocumentSetRequest request = new RetrieveDocumentSetRequest();
		DocumentRequest documentRequest = new DocumentRequest();

		documentRequest.setDocumentUniqueId(documentUniqueId);
		documentRequest.setRepositoryUniqueId(repositoryId);
		request.getDocumentRequest().add(documentRequest);
		return request;
	}

	/**
	 * Creates the retrieve document set request.
	 * 
	 * @param docRequest
	 *            the doc request
	 * @return the retrieve document set request
	 */
	RetrieveDocumentSetRequest createRetrieveDocumentSetRequest(
			DocumentRequest docRequest) {
		RetrieveDocumentSetRequest request = new RetrieveDocumentSetRequest();
		request.getDocumentRequest().add(docRequest);
		return request;
	}

	/**
	 * Creates the retrieve document set request.
	 * 
	 * @param docRequest
	 *            the doc request
	 * @return the retrieve document set request
	 */
	RetrieveDocumentSetRequest createRetrieveDocumentSetRequest(
			List<DocumentRequest> docRequest) {
		RetrieveDocumentSetRequest request = new RetrieveDocumentSetRequest();
		request.getDocumentRequest().addAll(docRequest);
		return request;
	}
}
