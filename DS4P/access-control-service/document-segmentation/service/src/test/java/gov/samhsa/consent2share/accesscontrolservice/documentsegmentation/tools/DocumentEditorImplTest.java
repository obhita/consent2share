package gov.samhsa.consent2share.accesscontrolservice.documentsegmentation.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.accesscontrolservice.common.tool.FileReaderImpl;
import gov.samhsa.ds4ppilot.common.beans.XacmlResult;
import gov.samhsa.ds4ppilot.common.utils.EncryptTool;
import gov.samhsa.ds4ppilot.common.utils.XmlHelper;

import java.security.Key;

import org.apache.axiom.attachments.ByteArrayDataSource;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DocumentEditorImplTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static FileReaderImpl fileReader;
	private static MetadataGeneratorImpl metadataGeneratorMock;
	private static XacmlResult xacmlResultMock;

	private static String c32;
	private static Document c32Document;
	private static String xPathDate;
	private static String ruleExecutionResponseContainer;
	private static final String EXPECTED_DATE = "20101026130945";

	private static DocumentEditorImpl documentEditor;

	@BeforeClass
	public static void setUp() throws Exception {
		fileReader = new FileReaderImpl();
		metadataGeneratorMock = mock(MetadataGeneratorImpl.class);
		when(
				metadataGeneratorMock.generateMetadataXml(anyString(),
						anyString(), anyString(), anyString(), anyString()))
				.thenReturn(fileReader.readFile("testMetadata.xml"));
		documentEditor = new DocumentEditorImpl();
		documentEditor.setFileReader(fileReader);
		documentEditor.setMetadataGenerator(metadataGeneratorMock);

		c32 = fileReader.readFile("c32.xml");
		c32Document = XmlHelper.loadDocument(c32);
		xPathDate = "//hl7:effectiveTime";
		ruleExecutionResponseContainer = "<ruleExecutionContainer><executionResponseList><executionResponse><c32SectionLoincCode>11450-4</c32SectionLoincCode><c32SectionTitle>Problems</c32SectionTitle><code>66214007</code><codeSystemName>SNOMED CT</codeSystemName><displayName>Substance Abuse Disorder</displayName><documentObligationPolicy>ENCRYPT</documentObligationPolicy><documentRefrainPolicy>NORDSLCD</documentRefrainPolicy><impliedConfSection>R</impliedConfSection><itemAction>REDACT</itemAction><observationId>e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7</observationId><sensitivity>ETH</sensitivity><USPrivacyLaw>42CFRPart2</USPrivacyLaw></executionResponse><executionResponse><c32SectionLoincCode>11450-4</c32SectionLoincCode><c32SectionTitle>Problems</c32SectionTitle><code>111880001</code><codeSystemName>SNOMED CT</codeSystemName><displayName>Acute HIV</displayName><documentObligationPolicy>ENCRYPT</documentObligationPolicy><documentRefrainPolicy>NORDSLCD</documentRefrainPolicy><impliedConfSection>R</impliedConfSection><itemAction>MASK</itemAction><observationId>d11275e7-67ae-11db-bd13-0800200c9a66</observationId><sensitivity>HIV</sensitivity><USPrivacyLaw>42CFRPart2</USPrivacyLaw></executionResponse></executionResponseList></ruleExecutionContainer>";
		xacmlResultMock = mock(XacmlResult.class);
	}

	@Test
	public void testSetDocumentCreationDate() {
		// Arrange
		logger.debug(c32);
		Element e1 = null;
		Element e2 = null;
		try {
			// Act
			Document c32DocumentTemp = XmlHelper.loadDocument(documentEditor
					.setDocumentCreationDate(c32));
			e1 = documentEditor.getElement(c32Document, xPathDate);
			e2 = documentEditor.getElement(c32DocumentTemp, xPathDate);
			logger.debug(e1.getAttribute("value"));
			logger.debug(e2.getAttribute("value"));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		// Assert
		assertNotNull(e1);
		assertNotNull(e2);
		assertNotEquals(e1.getAttribute("value"), e2.getAttribute("value"));
	}

	@Test
	public void testGetElement() {

		try {
			// Act
			String dateValue = documentEditor
					.getElement(c32Document, xPathDate).getAttribute("value");

			// Assert
			assertEquals(EXPECTED_DATE, dateValue);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Test
	public void testSetDocumentPayloadRawData_PackageAsXdmTrue() {
		// Arrange
		boolean packageAsXdm = true;
		testSetDocumentPayloadRawData(packageAsXdm);
	}

	@Test
	public void testSetDocumentPayloadRawData_PackageAsXdmFalse() {
		// Arrange
		boolean packageAsXdm = false;
		testSetDocumentPayloadRawData(packageAsXdm);
	}

	private void testSetDocumentPayloadRawData(boolean packageAsXdm) {
		// Arrange
		when(xacmlResultMock.getHomeCommunityId()).thenReturn(
				"2.16.840.1.113883.3.467");
		byte[] encryptionKeyBytes = null;
		byte[] maskingKeyBytes = null;
		ByteArrayDataSource rawData = null;
		try {
			Key deSedeEncryptKey = EncryptTool.generateKeyEncryptionKey();
			Key deSedeMaskKey = EncryptTool.generateKeyEncryptionKey();
			encryptionKeyBytes = deSedeEncryptKey.getEncoded();
			maskingKeyBytes = deSedeMaskKey.getEncoded();

			// Act
			rawData = documentEditor.setDocumentPayloadRawData(c32,
					packageAsXdm, "sender@email.com", "recepient@email.com",
					xacmlResultMock, ruleExecutionResponseContainer,
					maskingKeyBytes, encryptionKeyBytes);
			logger.debug(rawData.toString());
			logger.debug(rawData.getContentType());
			logger.debug(rawData.getName());
			logger.debug(rawData.getInputStream().toString());
			assertTrue(rawData.getInputStream().toString()
					.startsWith("java.io.ByteArrayInputStream@"));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		// Assert
		assertNotNull(rawData);
		assertTrue(rawData.toString().startsWith(
				"org.apache.axiom.attachments.ByteArrayDataSource@"));
		assertEquals("application/octet-stream", rawData.getContentType());
	}
}
