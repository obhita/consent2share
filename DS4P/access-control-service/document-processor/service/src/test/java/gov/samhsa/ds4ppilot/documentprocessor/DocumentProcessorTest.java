package gov.samhsa.ds4ppilot.documentprocessor;


import gov.samhsa.consent2share.accesscontrolservice.documentprocessor.brms.RuleExecutionServiceClientImpl;
import gov.samhsa.ds4ppilot.common.beans.RuleExecutionContainer;
import gov.samhsa.ds4ppilot.common.beans.XacmlResult;
import gov.samhsa.ds4ppilot.common.utils.FileHelper;
import gov.samhsa.ds4ppilot.common.utils.XmlHelper;
import gov.samhsa.ds4ppilot.documentprocessor.audit.AuditServiceImpl;
import gov.samhsa.ds4ppilot.schema.documentprocessor.ProcessDocumentResponse;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Key;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import junit.framework.Assert;

import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.utils.EncryptionConstants;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DocumentProcessorTest {

	private static String xacmlResult;
	private static String ruleExecutionResponseContainer;
	private static String c32Document;
	private static boolean packageAsXdm;
	private static String homeCommunityId;
	private static String senderEmailAddress;
	private static String recipientEmailAddress;
	private static String endpointAddressForAuditServcie;
	private static String endpointAddressForRuleExectionWebServiceClient;
	private static String xdsDocumentEntryUniqueId;
	private static XacmlResult xacmlResultObject;

	@BeforeClass
	public static void setUp() {
		c32Document = LoadXMLAsString("c32.xml");
		xacmlResult = "<xacmlResult><pdpDecision>Permit</pdpDecision><purposeOfUse>TREAT</purposeOfUse><messageId>4617a579-1881-4e40-9f98-f85bd81d6502</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:org:us-privacy-law:42CFRPart2</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:org:refrain-policy:NORDSLCD</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:redact:ETH</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:redact:PSY</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:mask:HIV</pdpObligation></xacmlResult>";
		ruleExecutionResponseContainer = "<ruleExecutionContainer><executionResponseList><executionResponse><c32SectionLoincCode>11450-4</c32SectionLoincCode><c32SectionTitle>Problems</c32SectionTitle><code>66214007</code><codeSystemName>SNOMED CT</codeSystemName><displayName>Substance Abuse Disorder</displayName><documentObligationPolicy>ENCRYPT</documentObligationPolicy><documentRefrainPolicy>NORDSLCD</documentRefrainPolicy><impliedConfSection>R</impliedConfSection><itemAction>REDACT</itemAction><observationId>e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7</observationId><sensitivity>ETH</sensitivity><USPrivacyLaw>42CFRPart2</USPrivacyLaw></executionResponse><executionResponse><c32SectionLoincCode>11450-4</c32SectionLoincCode><c32SectionTitle>Problems</c32SectionTitle><code>111880001</code><codeSystemName>SNOMED CT</codeSystemName><displayName>Acute HIV</displayName><documentObligationPolicy>ENCRYPT</documentObligationPolicy><documentRefrainPolicy>NORDSLCD</documentRefrainPolicy><impliedConfSection>R</impliedConfSection><itemAction>MASK</itemAction><observationId>d11275e7-67ae-11db-bd13-0800200c9a66</observationId><sensitivity>HIV</sensitivity><USPrivacyLaw>42CFRPart2</USPrivacyLaw></executionResponse></executionResponseList></ruleExecutionContainer>";
		packageAsXdm = true;
		senderEmailAddress = "leo.smith@direct.obhita-stage.org";
		recipientEmailAddress = "Duane_Decouteau@direct.healthvault-stage.com";
		homeCommunityId = "2.16.840.1.113883.3.467";
		endpointAddressForAuditServcie = "http://174.78.146.228:8080/DS4PACSServices/DS4PAuditService";
		endpointAddressForRuleExectionWebServiceClient = "http://obhitaqaapp01/RuleExecutionService/services/RuleExecutionService";
		xdsDocumentEntryUniqueId = "123";
		try {
			xacmlResultObject = unmarshallFromXml(XacmlResult.class,xacmlResult);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	@Ignore("This test should be configured to run as an integration test.")
	@Test
	public void processDocument_Process_Document() {

		DocumentProcessorImpl documentProcessor = new DocumentProcessorImpl(
				new RuleExecutionServiceClientImpl(
						endpointAddressForRuleExectionWebServiceClient),
				new AuditServiceImpl(endpointAddressForAuditServcie));
		ProcessDocumentResponse result = documentProcessor.processDocument(
				c32Document.toString(), xacmlResult, false, true,
				senderEmailAddress, recipientEmailAddress, xdsDocumentEntryUniqueId);

		Assert.assertNotNull(result);
	}

	@Ignore("This test should be configured to run as an integration test.")
	@Test
	public void processDocument_ExtractClinicalFacts() {
		DocumentProcessorImpl documentProcessor = new DocumentProcessorImpl(
				new RuleExecutionServiceClientImpl(
						endpointAddressForRuleExectionWebServiceClient),
				new AuditServiceImpl(endpointAddressForAuditServcie));

		String factModel = documentProcessor.extractFactModel(c32Document,
				xacmlResult);

		Assert.assertNotNull(factModel);

	}

	@Ignore("This test should be configured to run as an integration test.")
	@Test
	public void processDocument_TagDocument() throws Exception {
		DocumentProcessorImpl documentProcessor = new DocumentProcessorImpl(
				new RuleExecutionServiceClientImpl(
						endpointAddressForRuleExectionWebServiceClient),
				new AuditServiceImpl(endpointAddressForAuditServcie));

		String taggedDocument = documentProcessor.tagDocument(c32Document,
				ruleExecutionResponseContainer, UUID.randomUUID().toString());

		FileHelper.writeStringToFile(taggedDocument, "unitTest_Tagged_C32.xml");

		Assert.assertNotSame(taggedDocument, c32Document);
	}

	@Ignore("This test should be configured to run as an integration test.")
	@Test
	public void processDocument_MetadataXmlDocument() throws Exception {
		DocumentProcessorImpl documentProcessor = new DocumentProcessorImpl(
				new RuleExecutionServiceClientImpl(
						endpointAddressForRuleExectionWebServiceClient),
				new AuditServiceImpl(endpointAddressForAuditServcie));

		String metadataXmlDocument = documentProcessor.generateMetadataXml(
				c32Document, ruleExecutionResponseContainer, homeCommunityId,
				senderEmailAddress, recipientEmailAddress);

		FileHelper.writeStringToFile(metadataXmlDocument,
				"unitTest_Metadata.xml");

		Assert.assertNotSame(metadataXmlDocument, c32Document);
	}

	@Ignore("This test should be configured to run as an integration test.")
	@Test
	public void processDocument_EncryptDocument() {
		DocumentProcessorImpl documentProcessor = new DocumentProcessorImpl(
				new RuleExecutionServiceClientImpl(
						endpointAddressForRuleExectionWebServiceClient),
				new AuditServiceImpl(endpointAddressForAuditServcie));

		RuleExecutionContainer ruleExecutionContainer = null;
		String encryptedDocument = null;

		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(RuleExecutionContainer.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			ByteArrayInputStream input = new ByteArrayInputStream(
					ruleExecutionResponseContainer.getBytes());
			ruleExecutionContainer = (RuleExecutionContainer) jaxbUnmarshaller
					.unmarshal(input);
			encryptedDocument = documentProcessor.encryptDocument(c32Document,
					ruleExecutionContainer);

		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Assert.assertNotSame(encryptedDocument, c32Document);
	}

	@Ignore("This test should be configured to run as an integration test.")
	@Test
	public void processDocument_MaskElement() {
		DocumentProcessorImpl documentProcessor = new DocumentProcessorImpl(
				new RuleExecutionServiceClientImpl(
						endpointAddressForRuleExectionWebServiceClient),
				new AuditServiceImpl(endpointAddressForAuditServcie));

		RuleExecutionContainer ruleExecutionContainer = null;
		String encryptedDocument = null;

		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(RuleExecutionContainer.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			ByteArrayInputStream input = new ByteArrayInputStream(
					ruleExecutionResponseContainer.getBytes());
			ruleExecutionContainer = (RuleExecutionContainer) jaxbUnmarshaller
					.unmarshal(input);

			encryptedDocument = documentProcessor.maskElement(c32Document,
					ruleExecutionContainer, xacmlResultObject);
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Assert.assertNotSame(encryptedDocument, c32Document);
	}

	@Ignore("This test should be configured to run as an integration test.")
	@Test
	public void processDocument_RedactElement() {
		DocumentProcessorImpl documentProcessor = new DocumentProcessorImpl(
				new RuleExecutionServiceClientImpl(
						endpointAddressForRuleExectionWebServiceClient),
				new AuditServiceImpl(endpointAddressForAuditServcie));

		RuleExecutionContainer ruleExecutionContainer = null;
		String redactedDocument = null;

		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance(RuleExecutionContainer.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			ByteArrayInputStream input = new ByteArrayInputStream(
					ruleExecutionResponseContainer.getBytes());
			ruleExecutionContainer = (RuleExecutionContainer) jaxbUnmarshaller
					.unmarshal(input);

			redactedDocument = documentProcessor.redactElement(c32Document,
					ruleExecutionContainer, xacmlResultObject);
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Assert.assertNotSame(redactedDocument, c32Document);
	}

	private static String LoadXMLAsString(String xmlFileName) {
		InputStream in = null;
		StringBuilder c32Document = new StringBuilder();

		try {
			in = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("c32.xml");

			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String line;
			while ((line = br.readLine()) != null) {
				c32Document.append(line);
			}

			br.close();
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return c32Document.toString();
	}

	@Ignore("This test should be configured to run as an integration test.")
	@Test
	public void processDocument_DecryptDocument() {
		DocumentProcessorImpl documentProcessor = new DocumentProcessorImpl(
				new RuleExecutionServiceClientImpl(
						endpointAddressForRuleExectionWebServiceClient),
				new AuditServiceImpl(endpointAddressForAuditServcie));

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

			document = documentProcessor.redactElement(c32Document,
					ruleExecutionContainer, xacmlResultObject);

			Document processedDoc = XmlHelper.loadDocument(document);
			FileHelper
					.writeDocToFile(processedDoc, "unitTest_Redacted_C32.xml");

			document = documentProcessor.maskElement(document,
					ruleExecutionContainer, xacmlResultObject);

			processedDoc = XmlHelper.loadDocument(document);
			FileHelper.writeDocToFile(processedDoc, "unitTest_Masked_C32.xml");

			document = documentProcessor.encryptDocument(document,
					ruleExecutionContainer);

			processedDoc = XmlHelper.loadDocument(document);

			FileHelper.writeDocToFile(processedDoc,
					"unitTest_Encrypted_C32.xml");

			/*
			 * Load the key to be used for decrypting the xml data encryption
			 * key.
			 */
			Key desedeEncryptKey = documentProcessor.getDeSedeEncryptKey();
			Key desedeMaskKey = documentProcessor.getDeSedeMaskKey();

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

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Assert.assertNotSame(document, c32Document);
	}

	@Test
	public void processDocument_DecryptDocumentFromZip() {

		Document processedDoc;
		DESedeKeySpec desedeEncryptKeySpec;
		DESedeKeySpec desedeMaskKeySpec;
		try {

			org.apache.xml.security.Init.init();

			ZipInputStream zis = new ZipInputStream(Thread.currentThread()
					.getContextClassLoader()
					.getResourceAsStream("Patientone_Asample_XDM.zip"));

			byte[] processDocBytes = entryBytesFromZipBytes(zis,
					"SUBSET01/DOCUMENT.xml");
			String processDocString = new String(processDocBytes);
			FileHelper.writeStringToFile(processDocString,
					"processDocString.xml");

			processedDoc = XmlHelper.loadDocument(processDocString);

			byte[] kekEncryptionKeyBytes = entryBytesFromZipBytes(zis,
					"kekEncryptionKey");

			desedeEncryptKeySpec = new DESedeKeySpec(kekEncryptionKeyBytes);
			SecretKeyFactory skfEncrypt = SecretKeyFactory
					.getInstance("DESede");
			SecretKey desedeEncryptKey = skfEncrypt
					.generateSecret(desedeEncryptKeySpec);

			byte[] kekMaskingKeyBytes = entryBytesFromZipBytes(zis,
					"kekMaskingKey");

			desedeMaskKeySpec = new DESedeKeySpec(kekMaskingKeyBytes);
			SecretKeyFactory skfMask = SecretKeyFactory.getInstance("DESede");
			SecretKey desedeMaskKey = skfMask.generateSecret(desedeMaskKeySpec);

			zis.close();

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
					"unitTest_DecryptedUnMasked_C32_from_zip.xml");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private byte[] entryBytesFromZipBytes(ZipInputStream zip_inputstream,
			String entryName) throws IOException {

		ZipEntry current_zip_entry = null;
		byte[] buf = new byte[4096];
		boolean found = false;
		current_zip_entry = zip_inputstream.getNextEntry();
		while ((current_zip_entry != null) && !found) {
			if (current_zip_entry.getName().equals(entryName)) {
				found = true;
				ByteArrayOutputStream output = streamToOutputByteStream(zip_inputstream);
				buf = output.toByteArray();
				output.flush();
				output.close();
			} else {
				current_zip_entry = zip_inputstream.getNextEntry();
			}
		}

		return buf;
	}

	private ByteArrayOutputStream streamToOutputByteStream(
			ZipInputStream zip_inputstream) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		int data = 0;
		while ((data = zip_inputstream.read()) != -1) {
			output.write(data);
		}
		return output;
	}

	private static <T> T unmarshallFromXml(Class<T> clazz, String xml)
			throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Unmarshaller um = context.createUnmarshaller();
		ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes());
		return (T) um.unmarshal(input);
	}

}
