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
package gov.samhsa.consent2share.accesscontrolservice.documentsegmentation;

import gov.samhsa.consent2share.accesscontrolservice.brms.service.RuleExecutionService;
import gov.samhsa.consent2share.accesscontrolservice.common.tool.SimpleMarshaller;
import gov.samhsa.consent2share.accesscontrolservice.documentsegmentation.audit.AuditService;
import gov.samhsa.consent2share.accesscontrolservice.documentsegmentation.tools.AdditionalMetadataGeneratorForSegmentedClinicalDocument;
import gov.samhsa.consent2share.accesscontrolservice.documentsegmentation.tools.DocumentEditor;
import gov.samhsa.consent2share.accesscontrolservice.documentsegmentation.tools.DocumentEncrypter;
import gov.samhsa.consent2share.accesscontrolservice.documentsegmentation.tools.DocumentFactModelExtractor;
import gov.samhsa.consent2share.accesscontrolservice.documentsegmentation.tools.DocumentMasker;
import gov.samhsa.consent2share.accesscontrolservice.documentsegmentation.tools.DocumentRedactor;
import gov.samhsa.consent2share.accesscontrolservice.documentsegmentation.tools.DocumentTagger;
import gov.samhsa.consent2share.schema.documentsegmentation.SegmentDocumentResponse;
import gov.samhsa.ds4ppilot.common.beans.RuleExecutionContainer;
import gov.samhsa.ds4ppilot.common.beans.XacmlResult;
import gov.samhsa.ds4ppilot.common.exception.DS4PException;
import gov.samhsa.ds4ppilot.common.utils.EncryptTool;
import gov.samhsa.ds4ppilot.common.utils.FileHelper;

import java.security.Key;

import javax.activation.DataHandler;

import org.apache.axiom.attachments.ByteArrayDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * The Class DocumentSegmentationImpl.
 */
public class DocumentSegmentationImpl implements DocumentSegmentation {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The rule execution web service client. */
	private final RuleExecutionService ruleExecutionService;

	/** The audit service. */
	private final AuditService auditService;

	/** The de sede encrypt key. */
	private Key deSedeEncryptKey;

	/** The de sede mask key. */
	private Key deSedeMaskKey;

	/** The document editor. */
	private final DocumentEditor documentEditor;

	/** The marshaller. */
	private final SimpleMarshaller marshaller;

	/** The document encrypter. */
	private final DocumentEncrypter documentEncrypter;

	/** The document redactor. */
	private final DocumentRedactor documentRedactor;

	/** The document masker. */
	private final DocumentMasker documentMasker;

	/** The document tagger. */
	private final DocumentTagger documentTagger;

	/** The document fact model extractor. */
	private final DocumentFactModelExtractor documentFactModelExtractor;

	/** The additional metadata generator for segmented clinical document. */
	private final AdditionalMetadataGeneratorForSegmentedClinicalDocument additionalMetadataGeneratorForSegmentedClinicalDocument;

	/**
	 * Gets the de sede encrypt key.
	 * 
	 * @return the de sede encrypt key
	 */
	public Key getDeSedeEncryptKey() {
		return deSedeEncryptKey;
	}

	/**
	 * Sets the de sede encrypt key.
	 * 
	 * @param deSedeEncryptKey
	 *            the new de sede encrypt key
	 */
	public void setDeSedeEncryptKey(Key deSedeEncryptKey) {
		this.deSedeEncryptKey = deSedeEncryptKey;
	}

	/**
	 * Gets the de sede mask key.
	 * 
	 * @return the de sede mask key
	 */
	public Key getDeSedeMaskKey() {
		return deSedeMaskKey;
	}

	/**
	 * Sets the de sede mask key.
	 * 
	 * @param deSedeMaskKey
	 *            the new de sede mask key
	 */
	public void setDeSedeMaskKey(Key deSedeMaskKey) {
		this.deSedeMaskKey = deSedeMaskKey;
	}

	/**
	 * Instantiates a new document processor impl.
	 * 
	 * @param ruleExecutionWebServiceClient
	 *            the rule execution web service client
	 * @param auditService
	 *            the audit service
	 * @param documentEditor
	 *            the document editor
	 * @param marshaller
	 *            the marshaller
	 * @param documentEncrypter
	 *            the document encrypter
	 * @param documentRedactor
	 *            the document redactor
	 * @param documentMasker
	 *            the document masker
	 * @param documentTagger
	 *            the document tagger
	 */
	public DocumentSegmentationImpl(
			RuleExecutionService ruleExecutionService,
			AuditService auditService,
			DocumentEditor documentEditor,
			SimpleMarshaller marshaller,
			DocumentEncrypter documentEncrypter,
			DocumentRedactor documentRedactor,
			DocumentMasker documentMasker,
			DocumentTagger documentTagger,
			DocumentFactModelExtractor documentFactModelExtractor,
			AdditionalMetadataGeneratorForSegmentedClinicalDocument additionalMetadataGeneratorForSegmentedClinicalDocument) {

		this.ruleExecutionService = ruleExecutionService;
		this.auditService = auditService;
		this.documentEditor = documentEditor;
		this.marshaller = marshaller;
		this.documentEncrypter = documentEncrypter;
		this.documentRedactor = documentRedactor;
		this.documentMasker = documentMasker;
		this.documentTagger = documentTagger;
		this.documentFactModelExtractor = documentFactModelExtractor;
		this.additionalMetadataGeneratorForSegmentedClinicalDocument = additionalMetadataGeneratorForSegmentedClinicalDocument;
	}

	/**
	 * ***************************************** Process document based on
	 * drools directives ******************************************.
	 * 
	 * @param document
	 *            the document
	 * @param enforcementPolicies
	 *            the enforcement policies
	 * @param packageAsXdm
	 *            the package as xdm
	 * @param encryptDocument
	 *            the encrypt document
	 * @param senderEmailAddress
	 *            the sender email address
	 * @param recipientEmailAddress
	 *            the recipient email address
	 * @param xdsDocumentEntryUniqueId
	 *            the xds document entry unique id
	 * @return the process document response
	 */
	@Override
	public SegmentDocumentResponse segmentDocument(String document,
			String enforcementPolicies, boolean packageAsXdm,
			boolean encryptDocument, String senderEmailAddress,
			String recipientEmailAddress, String xdsDocumentEntryUniqueId) {

		Assert.notNull(document);
		Assert.notNull(enforcementPolicies);

		RuleExecutionContainer ruleExecutionContainer = null;
		XacmlResult xacmlResult = null;
		ByteArrayDataSource rawData = null;
		enforcementPolicies = enforcementPolicies.replace(
				" xmlns:ns2=\"http://ws.ds4p.ehtac.va.gov/\"", "");
		SegmentDocumentResponse segmentDocumentResponse = new SegmentDocumentResponse();

		try {

			document = documentEditor.setDocumentCreationDate(document);

			// FileHelper.writeStringToFile(document, "Original_C32.xml");

			// extract factModel
			String factModel = documentFactModelExtractor.extractFactModel(
					document, enforcementPolicies);
			// FileHelper.writeStringToFile(factModel, "FactModel.xml");

			// get execution response container
			String executionResponseContainer = ruleExecutionService
					.assertAndExecuteClinicalFacts(factModel).getRuleExecutionResponseContainer();

			// unmarshall from xml to RuleExecutionContainer
			ruleExecutionContainer = marshaller.unmarshallFromXml(
					RuleExecutionContainer.class, executionResponseContainer);

			// FileHelper.writeStringToFile(executionResponseContainer,
			// "ExecutionResponseContainer.xml");

			// unmarshall from xml to XacmlResult
			xacmlResult = marshaller.unmarshallFromXml(XacmlResult.class,
					enforcementPolicies);

			// redact document
			document = documentRedactor.redactDocument(document,
					ruleExecutionContainer, xacmlResult);

			// tag document
			document = documentTagger.tagDocument(document,
					executionResponseContainer, xacmlResult.getMessageId());

			String additionalMetadataForSegmentedClinicalDocument = additionalMetadataGeneratorForSegmentedClinicalDocument
					.generateMetadataXml(xacmlResult.getMessageId(), document,
							executionResponseContainer, senderEmailAddress,
							recipientEmailAddress,
							xacmlResult.getSubjectPurposeOfUse(),
							xdsDocumentEntryUniqueId);
			// FileHelper.writeStringToFile(additionalMetadataForSegmentedClinicalDocument,"additional_metadata.xml");

			segmentDocumentResponse
					.setPostProcessingMetadata(additionalMetadataForSegmentedClinicalDocument);

			// log annotated doc
			auditService.updateAuthorizationEventWithAnnotatedDoc(
					xacmlResult.getMessageId(), document);
			// FileHelper.writeStringToFile(document, "Tagged_C32.xml");

			// mask document
			/*
			 * Get a key to be used for encrypting the symmetric key. Here we
			 * are generating a DESede key.
			 */
			deSedeMaskKey = EncryptTool.generateKeyEncryptionKey();
			document = documentMasker.maskDocument(document, deSedeMaskKey,
					ruleExecutionContainer, xacmlResult);
			segmentDocumentResponse.setMaskedDocument(document);

			byte[] maskingKeyBytes = deSedeMaskKey.getEncoded();
			segmentDocumentResponse.setKekMaskingKey(maskingKeyBytes);

			byte[] encryptionKeyBytes = null;
			// encrypt document
			if (encryptDocument) {
				/*
				 * Get a key to be used for encrypting the symmetric key. Here
				 * we are generating a DESede key.
				 */
				deSedeEncryptKey = EncryptTool.generateKeyEncryptionKey();
				document = documentEncrypter.encryptDocument(deSedeEncryptKey,
						document, ruleExecutionContainer);
				encryptionKeyBytes = deSedeEncryptKey.getEncoded();

				segmentDocumentResponse.setKekEncryptionKey(encryptionKeyBytes);
			}

			rawData = documentEditor.setDocumentPayloadRawData(document,
					packageAsXdm, senderEmailAddress, recipientEmailAddress,
					xacmlResult, executionResponseContainer, maskingKeyBytes,
					encryptionKeyBytes);
			segmentDocumentResponse.setProcessedDocument(new DataHandler(
					rawData));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DS4PException(e.toString(), e);
		}

		return segmentDocumentResponse;
	}
}
