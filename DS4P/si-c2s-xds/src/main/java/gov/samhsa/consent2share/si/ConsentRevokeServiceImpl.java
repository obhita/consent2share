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
package gov.samhsa.consent2share.si;

import gov.samhsa.acs.common.tool.DocumentAccessor;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.xdsb.common.UniqueOidProviderImpl;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.acs.xdsb.common.XdsbMetadataGeneratorImpl;
import gov.samhsa.acs.xdsb.registry.wsclient.adapter.XdsbRegistryAdapter;
import gov.samhsa.acs.xdsb.repository.wsclient.adapter.XdsbRepositoryAdapter;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse.DocumentResponse;

import java.util.HashMap;
import java.util.Map;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class ConsentRevokeServiceImpl.
 */
public class ConsentRevokeServiceImpl implements ConsentRevokeService {

	/** The xdsb registry. */
	private XdsbRegistryAdapter xdsbRegistry;

	/** The xdsb repository. */
	private XdsbRepositoryAdapter xdsbRepository;

	/** The document accessor. */
	private DocumentAccessor documentAccessor;

	/** The document xml converter. */
	private DocumentXmlConverter documentXmlConverter;

	/** The marshaller. */
	private SimpleMarshaller marshaller;

	/** The Constant XPATH_TO_DOCUMENT_ENTRY_UNIQUEID_EXTERNALIDENTIFIER. */
	public static final String XPATH_TO_DOCUMENT_ENTRY_UNIQUEID_EXTERNALIDENTIFIER = "//rim:ExternalIdentifier[@identificationScheme='urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab']";

	/** The Constant XPATH_EXPR_XACML_POLICYID. */
	public static final String XPATH_EXPR_XACML_POLICYID = "//xacml2:Policy/@PolicyId";

	/**
	 * Instantiates a new consent revoke service impl.
	 * 
	 * @param xdsbRegistry
	 *            the xdsb registry
	 * @param xdsbRepository
	 *            the xdsb repository
	 * @param documentAccessor
	 *            the document accessor
	 * @param documentXmlConverter
	 *            the document xml converter
	 * @param marshaller
	 *            the marshaller
	 */
	public ConsentRevokeServiceImpl(XdsbRegistryAdapter xdsbRegistry,
			XdsbRepositoryAdapter xdsbRepository,
			DocumentAccessor documentAccessor,
			DocumentXmlConverter documentXmlConverter,
			SimpleMarshaller marshaller) {
		super();
		this.xdsbRegistry = xdsbRegistry;
		this.xdsbRepository = xdsbRepository;
		this.documentAccessor = documentAccessor;
		this.documentXmlConverter = documentXmlConverter;
		this.marshaller = marshaller;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.si.ConsentRevokeService#
	 * findConsentEntryUuidByPolicyId(java.lang.String, java.lang.String)
	 */
	@Override
	public String findConsentEntryUuidByPolicyId(String patientUniqueId,
			String policyId) throws Exception, Throwable {
		Assert.notNull(patientUniqueId);
		Assert.notNull(policyId);

		AdhocQueryResponse adhocQueryResponse = xdsbRegistry
				.registryStoredQuery(patientUniqueId, null,
						XdsbDocumentType.PRIVACY_CONSENT, false);
		String adhocQueryResponseXml = marshaller.marshall(adhocQueryResponse);
		Document adhocQueryResponseDoc = documentXmlConverter
				.loadDocument(adhocQueryResponseXml);
		NodeList nodeList = documentAccessor.getNodeList(adhocQueryResponseDoc,
				XPATH_TO_DOCUMENT_ENTRY_UNIQUEID_EXTERNALIDENTIFIER);
		Map<String, String> documentUniqueIdUuidMap = new HashMap<String, String>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			documentUniqueIdUuidMap.put(
					node.getAttributes().getNamedItem("value").getNodeValue(),
					node.getAttributes().getNamedItem("registryObject")
							.getNodeValue());
		}

		RetrieveDocumentSetRequest retrieveDocumentSetRequest = xdsbRegistry
				.extractXdsbDocumentReferenceListAsRetrieveDocumentSetRequest(adhocQueryResponse);
		RetrieveDocumentSetResponse allPrivacyConsents = xdsbRepository
				.retrieveDocumentSet(retrieveDocumentSetRequest);
		for (DocumentResponse docResp : allPrivacyConsents
				.getDocumentResponse()) {
			String docXml = new String(docResp.getDocument());
			Document doc = documentXmlConverter.loadDocument(docXml);
			String docPolicyId = documentAccessor.getNode(doc,
					XPATH_EXPR_XACML_POLICYID).getNodeValue();
			if (policyId.equals(docPolicyId)) {
				return documentUniqueIdUuidMap.get(docResp
						.getDocumentUniqueId());
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.si.ConsentRevokeService#revokeConsent(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public RegistryResponse revokeConsent(String patientUniqueId,
			String policyId) throws Exception, Throwable {
		Assert.notNull(patientUniqueId);
		Assert.notNull(policyId);
		String consentUuid = findConsentEntryUuidByPolicyId(patientUniqueId,
				policyId);
		Assert.notNull(consentUuid, "Consent UUID cannot be found in XDS.b");
		RegistryResponse resp = xdsbRepository.provideAndRegisterDocumentSet(
				XdsbRepositoryAdapter.EMPTY_XML_DOCUMENT, null,
				XdsbDocumentType.DEPRECATE_PRIVACY_CONSENT, patientUniqueId,
				consentUuid);
		return resp;
	}
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.si.ConsentRevokeService#getPatientUniqueId(java.lang.String, java.lang.String)
	 */
	@Override
	public String getPatientUniqueId(String patientId, String domainId){
		return this.xdsbRegistry.getPatientUniqueId(patientId, domainId);
	}

	/**
	 * Creates the xdsb metadata generator.
	 * 
	 * @return the xdsb metadata generator impl
	 */
	XdsbMetadataGeneratorImpl createXdsbMetadataGenerator() {
		return new XdsbMetadataGeneratorImpl(new UniqueOidProviderImpl(),
				XdsbDocumentType.DEPRECATE_PRIVACY_CONSENT, this.marshaller);
	}
}
