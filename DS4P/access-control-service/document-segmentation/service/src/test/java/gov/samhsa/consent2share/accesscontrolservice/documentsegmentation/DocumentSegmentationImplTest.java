package gov.samhsa.consent2share.accesscontrolservice.documentsegmentation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.accesscontrolservice.brms.service.RuleExecutionService;
import gov.samhsa.consent2share.accesscontrolservice.common.tool.DocumentXmlConverterImpl;
import gov.samhsa.consent2share.accesscontrolservice.common.tool.FileReader;
import gov.samhsa.consent2share.accesscontrolservice.common.tool.FileReaderImpl;
import gov.samhsa.consent2share.accesscontrolservice.common.tool.SimpleMarshallerImpl;
import gov.samhsa.consent2share.accesscontrolservice.documentsegmentation.audit.AuditServiceImpl;
import gov.samhsa.consent2share.accesscontrolservice.documentsegmentation.tools.AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl;
import gov.samhsa.consent2share.accesscontrolservice.documentsegmentation.tools.DocumentEditorImpl;
import gov.samhsa.consent2share.accesscontrolservice.documentsegmentation.tools.DocumentEncrypterImpl;
import gov.samhsa.consent2share.accesscontrolservice.documentsegmentation.tools.DocumentFactModelExtractorImpl;
import gov.samhsa.consent2share.accesscontrolservice.documentsegmentation.tools.DocumentMaskerImpl;
import gov.samhsa.consent2share.accesscontrolservice.documentsegmentation.tools.DocumentRedactorImpl;
import gov.samhsa.consent2share.accesscontrolservice.documentsegmentation.tools.DocumentTaggerImpl;
import gov.samhsa.consent2share.accesscontrolservice.documentsegmentation.tools.MetadataGeneratorImpl;
import gov.samhsa.consent2share.schema.documentsegmentation.SegmentDocumentResponse;
import gov.samhsa.consent2share.schema.ruleexecutionservice.AssertAndExecuteClinicalFactsResponse;
import gov.samhsa.ds4ppilot.common.beans.RuleExecutionContainer;
import gov.samhsa.ds4ppilot.common.beans.XacmlResult;
import gov.samhsa.ds4ppilot.common.exception.DS4PException;
import gov.samhsa.ds4ppilot.common.utils.FileHelper;
import gov.va.ds4p.cas.RuleExecutionResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.xml.xpath.XPathExpressionException;

import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.apache.xml.security.utils.EncryptionConstants;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DocumentSegmentationImplTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static String xacmlResult;
	private static XacmlResult xacmlResultObj;
	private static RuleExecutionContainer ruleExecutionContainerObj;

	private static String senderEmailAddress;
	private static String recipientEmailAddress;

	private static FileReader fileReader;
	private static DocumentXmlConverterImpl documentXmlConverter;

	private static RuleExecutionService ruleExecutionServiceClientMock;
	private static AuditServiceImpl auditServiceMock;
	private static DocumentEditorImpl documentEditorMock;
	private static DocumentFactModelExtractorImpl documentFactModelExtractorMock;
	private static SimpleMarshallerImpl marshallerMock;
	private static DocumentRedactorImpl documentRedactorMock;
	private static DocumentTaggerImpl documentTaggerMock;
	private static DocumentMaskerImpl documentMaskerMock;
	private static DocumentEncrypterImpl documentEncrypterMock;
	private static AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl additionalMetadataGeneratorForSegmentedClinicalDocumentImplMock;

	private static String testOriginal_C32_xml;
	private static String testFactModel_xml;
	private static String testExecutionResponseContainer_xml;
	private static String testRedacted_C32_xml;
	private static String testTagged_C32_xml;
	private static String testMasked_C32_xml;
	private static String testEncrypted_C32_xml;
	private static String testAdditionalMetadata_xml;

	private static DocumentSegmentation documentSegmentation;

	@BeforeClass
	public static void setUp() throws XPathExpressionException,
			XMLEncryptionException, Exception {
		senderEmailAddress = "leo.smith@direct.obhita-stage.org";
		recipientEmailAddress = "Duane_Decouteau@direct.healthvault-stage.com";

		// File reader
		fileReader = new FileReaderImpl();
		// Document-Xml converter
		documentXmlConverter = new DocumentXmlConverterImpl();

		testOriginal_C32_xml = fileReader.readFile("testOriginal_C32.xml");
		testFactModel_xml = fileReader.readFile("testFactModel.xml");
		testExecutionResponseContainer_xml = fileReader
				.readFile("testExecutionResponseContainer.xml");
		testRedacted_C32_xml = fileReader.readFile("testRedacted_C32.xml");
		testTagged_C32_xml = fileReader.readFile("testTagged_C32.xml");
		testMasked_C32_xml = fileReader.readFile("testMasked_C32.xml");
		testEncrypted_C32_xml = fileReader.readFile("testEncrypted_C32.xml");
		testAdditionalMetadata_xml = fileReader
				.readFile("testAdditionalMetadata.xml");

		// Xacml result
		xacmlResult = "<xacmlResult><pdpDecision>PERMIT</pdpDecision><purposeOfUse>TREAT</purposeOfUse><messageId>cf8cace6-6331-4a45-8e79-5bf503925be4</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId><pdpObligation>51848-0</pdpObligation><pdpObligation>121181</pdpObligation><pdpObligation>47420-5</pdpObligation><pdpObligation>46240-8</pdpObligation><pdpObligation>ETH</pdpObligation><pdpObligation>GDIS</pdpObligation><pdpObligation>PSY</pdpObligation><pdpObligation>SEX</pdpObligation><pdpObligation>18748-4</pdpObligation><pdpObligation>11504-8</pdpObligation><pdpObligation>34117-2</pdpObligation></xacmlResult>";
		xacmlResultObj = setXacmlResult();

		// Document editor mock
		documentEditorMock = mock(DocumentEditorImpl.class);
		when(documentEditorMock.setDocumentCreationDate(testOriginal_C32_xml))
				.thenReturn(testOriginal_C32_xml);
		when(
				documentEditorMock.setDocumentPayloadRawData(anyString(),
						anyBoolean(), anyString(), anyString(),
						any(XacmlResult.class), anyString(), any(byte[].class),
						any(byte[].class))).thenReturn(null);

		// Fact model extractor mock
		documentFactModelExtractorMock = mock(DocumentFactModelExtractorImpl.class);
		when(
				documentFactModelExtractorMock.extractFactModel(
						testOriginal_C32_xml, xacmlResult)).thenReturn(
				testFactModel_xml);

		// BRMS client mock
		ruleExecutionServiceClientMock = mock(RuleExecutionService.class);
		when(
				ruleExecutionServiceClientMock
						.assertAndExecuteClinicalFacts(testFactModel_xml))
				.thenReturn(mock(AssertAndExecuteClinicalFactsResponse.class));
		
		when(ruleExecutionServiceClientMock
						.assertAndExecuteClinicalFacts(testFactModel_xml).getRuleExecutionResponseContainer())
						.thenReturn(testExecutionResponseContainer_xml);

		// Marshaller mock
		marshallerMock = mock(SimpleMarshallerImpl.class);
		ruleExecutionContainerObj = setRuleExecutionContainer();
		when(
				marshallerMock.unmarshallFromXml(
						eq(RuleExecutionContainer.class), anyString()))
				.thenReturn(ruleExecutionContainerObj);
		when(
				marshallerMock.unmarshallFromXml(eq(XacmlResult.class),
						anyString())).thenReturn(xacmlResultObj);

		// Documnent redactor
		documentRedactorMock = mock(DocumentRedactorImpl.class);
		when(
				documentRedactorMock.redactDocument(eq(testOriginal_C32_xml),
						eq(ruleExecutionContainerObj), eq(xacmlResultObj)))
				.thenReturn(testRedacted_C32_xml);

		// Document tagger
		documentTaggerMock = mock(DocumentTaggerImpl.class);
		when(
				documentTaggerMock.tagDocument(testRedacted_C32_xml,
						testExecutionResponseContainer_xml,
						xacmlResultObj.getMessageId())).thenReturn(
				testTagged_C32_xml);

		// Audit service mock
		auditServiceMock = mock(AuditServiceImpl.class);
		when(
				auditServiceMock.updateAuthorizationEventWithAnnotatedDoc(
						anyString(), anyString())).thenReturn(null);

		// Document masker
		documentMaskerMock = mock(DocumentMaskerImpl.class);
		when(
				documentMaskerMock.maskDocument(eq(testTagged_C32_xml),
						any(Key.class), eq(ruleExecutionContainerObj),
						eq(xacmlResultObj))).thenReturn(testMasked_C32_xml);

		// Document encrypter
		documentEncrypterMock = mock(DocumentEncrypterImpl.class);
		when(
				documentEncrypterMock.encryptDocument(any(Key.class),
						eq(testMasked_C32_xml), eq(ruleExecutionContainerObj)))
				.thenReturn(testEncrypted_C32_xml);

		// Additional Metadata Generator For Segmented Clinical Document
		additionalMetadataGeneratorForSegmentedClinicalDocumentImplMock = mock(AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl.class);
		when(
				additionalMetadataGeneratorForSegmentedClinicalDocumentImplMock
						.generateMetadataXml(
								"cf8cace6-6331-4a45-8e79-5bf503925be4",
								testTagged_C32_xml,
								testExecutionResponseContainer_xml,
								senderEmailAddress, recipientEmailAddress,
								"TREAT", "123")).thenReturn(
				testAdditionalMetadata_xml);

		documentSegmentation = new DocumentSegmentationImpl(
				ruleExecutionServiceClientMock, auditServiceMock,
				documentEditorMock, marshallerMock, documentEncrypterMock,
				documentRedactorMock, documentMaskerMock, documentTaggerMock,
				documentFactModelExtractorMock,
				additionalMetadataGeneratorForSegmentedClinicalDocumentImplMock);
	}

	@Test(expected = DS4PException.class)
	public void testSegmentDocument_Given_Real_Marshaller_Throws_DS4PException()
			throws IOException {
		// Arrange
		boolean xdm = true;
		boolean ecrypt = true;
		DocumentSegmentationImpl documentSegmentationWithRealMarshaller = new DocumentSegmentationImpl(
				ruleExecutionServiceClientMock, auditServiceMock,
				documentEditorMock, new SimpleMarshallerImpl(),
				documentEncrypterMock, documentRedactorMock,
				documentMaskerMock, documentTaggerMock,
				documentFactModelExtractorMock,
				additionalMetadataGeneratorForSegmentedClinicalDocumentImplMock);

		// Act
		@SuppressWarnings("unused")
		SegmentDocumentResponse resp = documentSegmentationWithRealMarshaller
				.segmentDocument("", "", xdm, ecrypt, "", "", "");

		// Assert
		// expect DS4PException
	}

	@Test(expected = DS4PException.class)
	public void testSegmentDocument_Given_Real_DocumentEditor_Throws_DS4PException()
			throws IOException {
		// Arrange
		boolean xdm = true;
		boolean ecrypt = true;
		DocumentEditorImpl realDocumentEditorImpl = new DocumentEditorImpl();
		realDocumentEditorImpl.setFileReader(new FileReaderImpl());
		realDocumentEditorImpl
				.setMetadataGenerator(new MetadataGeneratorImpl());

		DocumentSegmentationImpl documentSegmentationWithRealDocumentEditor = new DocumentSegmentationImpl(
				ruleExecutionServiceClientMock, auditServiceMock,
				realDocumentEditorImpl, marshallerMock, documentEncrypterMock,
				documentRedactorMock, documentMaskerMock, documentTaggerMock,
				documentFactModelExtractorMock,
				additionalMetadataGeneratorForSegmentedClinicalDocumentImplMock);

		// Act
		@SuppressWarnings("unused")
		SegmentDocumentResponse resp = documentSegmentationWithRealDocumentEditor
				.segmentDocument("", "", xdm, ecrypt, "", "", "");

		// Assert
		// expect DS4PException
	}

	@Test
	public void testSegmentDocument_Given_XdmTrue_EncryptTrue() throws IOException {
		boolean xdm = true;
		boolean ecrypt = true;
		// Act
		SegmentDocumentResponse resp = documentSegmentation.segmentDocument(
				testOriginal_C32_xml, xacmlResult, xdm, ecrypt,
				senderEmailAddress, recipientEmailAddress, "123");

		// Assert
		validateResponse(resp, ecrypt);
	}

	@Test
	public void testSegmentDocument_Given_XdmFalse_EncryptTrue() throws IOException {
		// Arrange
		boolean xdm = false;
		boolean ecrypt = true;

		// Act
		SegmentDocumentResponse resp = documentSegmentation.segmentDocument(
				testOriginal_C32_xml, xacmlResult, xdm, ecrypt,
				senderEmailAddress, recipientEmailAddress, "123");

		// Assert
		validateResponse(resp, ecrypt);
	}

	@Test
	public void testSegmentDocument_Given_XdmTrue_EncryptFalse() throws IOException {
		// Arrange
		boolean xdm = true;
		boolean ecrypt = false;

		// Act
		SegmentDocumentResponse resp = documentSegmentation.segmentDocument(
				testOriginal_C32_xml, xacmlResult, xdm, ecrypt,
				senderEmailAddress, recipientEmailAddress, "123");

		// Assert
		validateResponse(resp, ecrypt);
	}

	@Test
	public void testSegmentDocument_Given_XdmFalse_EncryptFalse() throws IOException {
		// Arrange
		boolean xdm = false;
		boolean ecrypt = false;

		// Act
		SegmentDocumentResponse resp = documentSegmentation.segmentDocument(
				testOriginal_C32_xml, xacmlResult, xdm, ecrypt,
				senderEmailAddress, recipientEmailAddress, "123");

		// Assert
		validateResponse(resp, ecrypt);
	}

	@Test
	public void testSegmentDocument_Decrypt_Document_From_Zip() {

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

			processedDoc = documentXmlConverter.loadDocument(processDocString);

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
			logger.error(e.getMessage(), e);
		}
	}

	private void validateResponse(SegmentDocumentResponse resp, boolean encrypt) {
		// Assert masked document
		logger.debug(resp.getMaskedDocument());
		assertEquals(testMasked_C32_xml, resp.getMaskedDocument());

		// Assert processed document
		logger.debug(resp.getProcessedDocument().toString());
		assertTrue(resp.getProcessedDocument().toString()
				.startsWith("javax.activation.DataHandler@"));

		// Assert kek encryption key
		if (encrypt) {
			logger.debug(resp.getKekEncryptionKey().toString());
			assertNotNull(resp.getKekEncryptionKey());
		} else {
			logger.debug("KekEncryptionKey= null");
			assertNull(resp.getKekEncryptionKey());
		}

		// Assert kek masking key
		logger.debug(resp.getKekMaskingKey().toString());
		assertTrue(resp.getKekMaskingKey().toString().startsWith("[B"));

		// Assert processing metadata
		String metadata = resp.getPostProcessingMetadata();
		logger.debug(metadata);
		assertNotNull(metadata);
		assertTrue(metadata
				.contains("<rim:Value>2.16.840.1.113883.1.11.16926</rim:Value>"));
		assertTrue(metadata
				.contains("<rim:Value>^^Internet^leo.smith@direct.obhita-stage.org</rim:Value>"));
		assertTrue(metadata
				.contains("<rim:Value>^^Internet^Duane_Decouteau@direct.healthvault-stage.com</rim:Value>"));
		assertTrue(metadata.contains("nodeRepresentation=\"TREAT\">"));
		assertTrue(metadata
				.contains("<rim:LocalizedString value=\"Treatment\"/>"));
		assertTrue(metadata.contains("nodeRepresentation=\"ENCRYPT\">"));
		assertTrue(metadata
				.contains("<rim:LocalizedString value=\"ENCRYPT\"/>"));
		assertTrue(metadata.contains("nodeRepresentation=\"NODSCLCD\">"));
		assertTrue(metadata
				.contains("<rim:LocalizedString value=\"NODSCLCD\"/>"));
	}

	private static XacmlResult setXacmlResult() {
		XacmlResult xacmlResultObject = new XacmlResult();
		xacmlResultObject.setPdpDecision("PERMIT");
		xacmlResultObject.setSubjectPurposeOfUse("TREAT");
		xacmlResultObject.setMessageId("cf8cace6-6331-4a45-8e79-5bf503925be4");
		xacmlResultObject.setHomeCommunityId("2.16.840.1.113883.3.467");
		String[] o = { "51848-0", "121181", "47420-5", "46240-8", "ETH",
				"GDIS", "PSY", "SEX", "18748-4", "11504-8", "34117-2" };
		List<String> obligations = Arrays.asList(o);
		xacmlResultObject.setPdpObligations(obligations);
		return xacmlResultObject;
	}

	private static RuleExecutionContainer setRuleExecutionContainer() {
		RuleExecutionContainer container = new RuleExecutionContainer();
		RuleExecutionResponse r1 = new RuleExecutionResponse();
		r1.setC32SectionLoincCode("11450-4");
		r1.setC32SectionTitle("Problems");
		r1.setCode("66214007");
		r1.setCodeSystemName("SNOMED CT");
		r1.setDisplayName("Substance Abuse Disorder");
		r1.setDocumentObligationPolicy("ENCRYPT");
		r1.setDocumentRefrainPolicy("NORDSLCD");
		r1.setImpliedConfSection(RuleExecutionResponse.Confidentiality.R);
		r1.setItemAction("REDACT");
		r1.setObservationId("e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7");
		r1.setSensitivity("ETH");
		r1.setUSPrivacyLaw("42CFRPart2");
		RuleExecutionResponse r2 = new RuleExecutionResponse();
		r2.setC32SectionLoincCode("11450-4");
		r2.setC32SectionTitle("Problems");
		r2.setCode("111880001");
		r2.setCodeSystemName("SNOMED CT");
		r2.setDisplayName("Acute HIV");
		r2.setDocumentObligationPolicy("ENCRYPT");
		r2.setDocumentRefrainPolicy("NORDSLCD");
		r2.setImpliedConfSection(RuleExecutionResponse.Confidentiality.R);
		r2.setItemAction("MASK");
		r2.setObservationId("d11275e7-67ae-11db-bd13-0800200c9a66");
		r2.setSensitivity("HIV");
		r2.setUSPrivacyLaw("42CFRPart2");
		List<RuleExecutionResponse> list = new LinkedList<RuleExecutionResponse>();
		list.add(r1);
		list.add(r2);
		container.setExecutionResponseList(list);
		return container;
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
}
