package gov.samhsa.consent2share.accesscontrolservice.pep.xdsb;

import gov.samhsa.consent2share.accesscontrolservice.common.tool.SimpleMarshaller;
import gov.samhsa.ds4ppilot.pep.UniqueOidProviderImpl;
import gov.samhsa.ds4ppilot.pep.XdsbMetadataGenerator;
import gov.samhsa.ds4ppilot.pep.XdsbMetadataGeneratorImpl;
import gov.samhsa.ds4ppilot.pep.xdsbrepository.XdsbRepository;
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
	private XdsbRepository xdsbRepository;

	/** The xdsb metadata generator. */
	private XdsbMetadataGenerator xdsbMetadataGenerator;

	/** The marshaller. */
	private SimpleMarshaller marshaller;

	/**
	 * Instantiates a new xdsb repository adapter.
	 * 
	 * @param xdsbRepository
	 *            the xdsb repository
	 * @param marshaller
	 *            the marshaller
	 */
	public XdsbRepositoryAdapter(XdsbRepository xdsbRepository,
			SimpleMarshaller marshaller) {

		this.xdsbRepository = xdsbRepository;
		this.marshaller = marshaller;
		this.xdsbMetadataGenerator = null;
	}

	/**
	 * Provide and register document set request (direct call to the XDS.b
	 * repository service).
	 * 
	 * @param provideAndRegisterDocumentSetRequest
	 *            the provide and register document set request
	 * @return the registry response
	 */
	public RegistryResponse provideAndRegisterDocumentSetRequest(
			ProvideAndRegisterDocumentSetRequest provideAndRegisterDocumentSetRequest) {
		return xdsbRepository
				.provideAndRegisterDocumentSetRequest(provideAndRegisterDocumentSetRequest);
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
	public RegistryResponse provideAndRegisterDocumentSetRequest(
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

		RegistryResponse response = provideAndRegisterDocumentSetRequest(request);
		return response;
	}

	/**
	 * Retrieve document set request (direct call to XDS.b repository service).
	 * 
	 * @param request
	 *            the request
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse retrieveDocumentSetRequest(
			RetrieveDocumentSetRequest request) {
		return xdsbRepository.retrieveDocumentSetRequest(request);
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
	public RetrieveDocumentSetResponse retrieveDocumentSetRequest(
			String documentUniqueId, String repositoryId) {
		RetrieveDocumentSetRequest request = createRetrieveDocumentSetRequest(
				documentUniqueId, repositoryId);

		RetrieveDocumentSetResponse retrieveDocumentSetResponse = retrieveDocumentSetRequest(request);
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
		setXdsbMetadataGenerator(createXdsbMetadataGenerator(documentTypeForXdsbMetadata));
		String metadata = xdsbMetadataGenerator.generateMetadataXml(
				documentXmlString, homeCommunityId);
		setXdsbMetadataGenerator(null);
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
				documentTypeForXdsbMetadata);
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
	 * Sets the XDS.b metadata generator.
	 * 
	 * @param xdsbMetadataGenerator
	 *            the new XDS.b metadata generator
	 */
	void setXdsbMetadataGenerator(XdsbMetadataGenerator xdsbMetadataGenerator) {
		this.xdsbMetadataGenerator = xdsbMetadataGenerator;
	}
}
