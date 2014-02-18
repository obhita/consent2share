package gov.samhsa.acs.documentsegmentation;

import gov.samhsa.acs.brms.RuleExecutionServiceImpl;
import gov.samhsa.acs.brms.domain.RuleExecutionContainer;
import gov.samhsa.acs.brms.guvnor.GuvnorServiceImpl;
import gov.samhsa.acs.common.bean.XacmlResult;
import gov.samhsa.acs.common.tool.DocumentXmlConverterImpl;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.common.util.EncryptTool;
import gov.samhsa.acs.common.util.FileHelper;
import gov.samhsa.acs.documentsegmentation.DocumentSegmentationImpl;
import gov.samhsa.acs.documentsegmentation.audit.AuditServiceImpl;
import gov.samhsa.acs.documentsegmentation.tools.AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl;
import gov.samhsa.acs.documentsegmentation.tools.DocumentEditorImpl;
import gov.samhsa.acs.documentsegmentation.tools.DocumentEncrypterImpl;
import gov.samhsa.acs.documentsegmentation.tools.DocumentFactModelExtractorImpl;
import gov.samhsa.acs.documentsegmentation.tools.DocumentMaskerImpl;
import gov.samhsa.acs.documentsegmentation.tools.DocumentRedactorImpl;
import gov.samhsa.acs.documentsegmentation.tools.DocumentTaggerImpl;
import gov.samhsa.acs.documentsegmentation.tools.MetadataGeneratorImpl;
import gov.samhsa.consent2share.schema.documentsegmentation.SegmentDocumentResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Key;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.utils.EncryptionConstants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DocumentSegmentationImplIT {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static String xacmlResult;
	private static String ruleExecutionResponseContainer;
	private static String c32Document;
	private static String senderEmailAddress;
	private static String recipientEmailAddress;
	private static String endpointAddressForAuditServcie;
	private static String endpointAddressForRuleExectionWebServiceClient;
	private static String xdsDocumentEntryUniqueId;
	private static XacmlResult xacmlResultObject;
	private static String endpointAddressGuvnorService;

	private static RuleExecutionServiceImpl ruleExecutionService;
	private static SimpleMarshallerImpl marshaller;
	private static FileReaderImpl fileReader;
	private static DocumentXmlConverterImpl documentXmlConverter;
	private static MetadataGeneratorImpl metadataGenerator;
	private static DocumentEditorImpl documentEditor;
	private static DocumentTaggerImpl documentTagger;
	private static DocumentEncrypterImpl documentEncrypter;
	private static DocumentRedactorImpl documentRedactor;
	private static DocumentMaskerImpl documentMasker;
	private static DocumentFactModelExtractorImpl documentFactModelExtractor;
	private static AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl additionalMetadataGeneratorForSegmentedClinicalDocumentImpl;

	@Before
	public void setUp() throws IOException {
		fileReader = new FileReaderImpl();
		documentXmlConverter = new DocumentXmlConverterImpl();
		metadataGenerator = new MetadataGeneratorImpl();
		documentEditor = new DocumentEditorImpl(metadataGenerator, fileReader,
				documentXmlConverter);
		marshaller = new SimpleMarshallerImpl();
		documentTagger = new DocumentTaggerImpl();
		documentEncrypter = new DocumentEncrypterImpl(documentXmlConverter);
		documentRedactor = new DocumentRedactorImpl(documentEditor,
				documentXmlConverter);
		documentMasker = new DocumentMaskerImpl();
		documentFactModelExtractor = new DocumentFactModelExtractorImpl();
		additionalMetadataGeneratorForSegmentedClinicalDocumentImpl = new AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl();

		c32Document = fileReader.readFile("c32.xml");
		xacmlResult = "<xacmlResult><pdpDecision>Permit</pdpDecision><purposeOfUse>TREAT</purposeOfUse><messageId>4617a579-1881-4e40-9f98-f85bd81d6502</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:org:us-privacy-law:42CFRPart2</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:org:refrain-policy:NORDSLCD</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:redact:ETH</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:redact:PSY</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:mask:HIV</pdpObligation></xacmlResult>";
		ruleExecutionResponseContainer = "<ruleExecutionContainer><executionResponseList><executionResponse><c32SectionLoincCode>11450-4</c32SectionLoincCode><c32SectionTitle>Problems</c32SectionTitle><code>66214007</code><codeSystemName>SNOMED CT</codeSystemName><displayName>Substance Abuse Disorder</displayName><documentObligationPolicy>ENCRYPT</documentObligationPolicy><documentRefrainPolicy>NORDSLCD</documentRefrainPolicy><impliedConfSection>R</impliedConfSection><itemAction>REDACT</itemAction><observationId>e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7</observationId><sensitivity>ETH</sensitivity><USPrivacyLaw>42CFRPart2</USPrivacyLaw></executionResponse><executionResponse><c32SectionLoincCode>11450-4</c32SectionLoincCode><c32SectionTitle>Problems</c32SectionTitle><code>111880001</code><codeSystemName>SNOMED CT</codeSystemName><displayName>Acute HIV</displayName><documentObligationPolicy>ENCRYPT</documentObligationPolicy><documentRefrainPolicy>NORDSLCD</documentRefrainPolicy><impliedConfSection>R</impliedConfSection><itemAction>MASK</itemAction><observationId>d11275e7-67ae-11db-bd13-0800200c9a66</observationId><sensitivity>HIV</sensitivity><USPrivacyLaw>42CFRPart2</USPrivacyLaw></executionResponse></executionResponseList></ruleExecutionContainer>";
		senderEmailAddress = "leo.smith@direct.obhita-stage.org";
		recipientEmailAddress = "Duane_Decouteau@direct.healthvault-stage.com";
		endpointAddressForAuditServcie = "http://174.78.146.228:8080/DS4PACSServices/DS4PAuditService";
		endpointAddressForRuleExectionWebServiceClient = "http://localhost:90/RuleExecutionService/services/RuleExecutionService";
		xdsDocumentEntryUniqueId = "123";
		endpointAddressGuvnorService = "http://localhost:7070/guvnor-5.5.0.Final-tomcat-6.0/rest/packages/AnnotationRules/source";

		ruleExecutionService = new RuleExecutionServiceImpl(
				new GuvnorServiceImpl(endpointAddressGuvnorService, "admin",
						"admin"), new SimpleMarshallerImpl());
		try {
			xacmlResultObject = marshaller.unmarshallFromXml(XacmlResult.class,
					xacmlResult);
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
		}
	}

	// Integration test
	@Test
	public void testSegmentDocument_Segment_Document() {

		DocumentSegmentationImpl documentSegmentation = new DocumentSegmentationImpl(
				ruleExecutionService, new AuditServiceImpl(
						endpointAddressForAuditServcie), documentEditor,
				marshaller, documentEncrypter, documentRedactor,
				documentMasker, documentTagger, documentFactModelExtractor,
				additionalMetadataGeneratorForSegmentedClinicalDocumentImpl);
		SegmentDocumentResponse result = documentSegmentation.segmentDocument(
				c32Document.toString(), xacmlResult, false, true,
				senderEmailAddress, recipientEmailAddress,
				xdsDocumentEntryUniqueId);

		Assert.assertNotNull(result);
	}

	// Integration test
	@Test
	public void testSegmentDocument_Decrypt_Document() {

		RuleExecutionContainer ruleExecutionContainer = null;
		String document = "";

		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(RuleExecutionContainer.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			ByteArrayInputStream input = new ByteArrayInputStream(
					ruleExecutionResponseContainer.getBytes());
			ruleExecutionContainer = (RuleExecutionContainer) jaxbUnmarshaller
					.unmarshal(input);

			document = documentRedactor.redactDocument(c32Document,
					ruleExecutionContainer, xacmlResultObject);

			Document processedDoc = documentXmlConverter.loadDocument(document);
			FileHelper
					.writeDocToFile(processedDoc, "unitTest_Redacted_C32.xml");

			// commented out for redact-only application
			// document = documentSegmentation.maskElement(document,
			// ruleExecutionContainer, xacmlResultObject);

			processedDoc = documentXmlConverter.loadDocument(document);
			FileHelper.writeDocToFile(processedDoc, "unitTest_Masked_C32.xml");

			/*
			 * Generate the key to be used for decrypting the xml data
			 * encryption key.
			 */
			Key desedeEncryptKey = EncryptTool.generateKeyEncryptionKey();
			Key desedeMaskKey = EncryptTool.generateKeyEncryptionKey();

			document = documentEncrypter.encryptDocument(desedeEncryptKey,
					document, ruleExecutionContainer);

			processedDoc = documentXmlConverter.loadDocument(document);

			FileHelper.writeDocToFile(processedDoc,
					"unitTest_Encrypted_C32.xml");

			/*************************************************
			 * DECRYPT DOCUMENT
			 *************************************************/
			Element encryptedDataElement = (Element) processedDoc
					.getElementsByTagNameNS(
							EncryptionConstants.EncryptionSpecNS,
							EncryptionConstants._TAG_ENCRYPTEDDATA).item(0);

			/*
			 * The key to be used for decrypting xml data would be obtained from
			 * the keyinfo of the EncrypteData using the kek.
			 */
			XMLCipher xmlCipher = XMLCipher.getInstance();
			xmlCipher.init(XMLCipher.DECRYPT_MODE, null);
			xmlCipher.setKEK(desedeEncryptKey);

			/*
			 * The following doFinal call replaces the encrypted data with
			 * decrypted contents in the document.
			 */
			if (encryptedDataElement != null)
				xmlCipher.doFinal(processedDoc, encryptedDataElement);

			FileHelper.writeDocToFile(processedDoc,
					"unitTest_Decrypted_C32.xml");

			/*************************************************
			 * DECRYPT ELEMENTS
			 *************************************************/
			NodeList encryptedDataElements = processedDoc
					.getElementsByTagNameNS(
							EncryptionConstants.EncryptionSpecNS,
							EncryptionConstants._TAG_ENCRYPTEDDATA);

			while (encryptedDataElements.getLength() > 0) {
				/*
				 * The key to be used for decrypting xml data would be obtained
				 * from the keyinfo of the EncrypteData using the kek.
				 */
				XMLCipher xmlMaskCipher = XMLCipher.getInstance();
				xmlMaskCipher.init(XMLCipher.DECRYPT_MODE, null);
				xmlMaskCipher.setKEK(desedeMaskKey);

				xmlMaskCipher.doFinal(processedDoc,
						((Element) encryptedDataElements.item(0)));

				encryptedDataElements = processedDoc.getElementsByTagNameNS(
						EncryptionConstants.EncryptionSpecNS,
						EncryptionConstants._TAG_ENCRYPTEDDATA);
			}

			FileHelper.writeDocToFile(processedDoc,
					"unitTest_DecryptedUnMasked_C32.xml");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		Assert.assertNotSame(document, c32Document);
	}
}
