package gov.samhsa.consent2share.accesscontrolservice.documentsegmentation.tools;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.samhsa.consent2share.accesscontrolservice.common.tool.DocumentXmlConverterImpl;
import gov.samhsa.consent2share.accesscontrolservice.common.tool.FileReaderImpl;
import gov.samhsa.ds4ppilot.common.beans.RuleExecutionContainer;
import gov.samhsa.ds4ppilot.common.exception.DS4PException;
import gov.samhsa.ds4ppilot.common.utils.EncryptTool;
import gov.va.ds4p.cas.RuleExecutionResponse;

import java.security.Key;
import java.util.LinkedList;
import java.util.List;

import org.apache.xml.security.encryption.EncryptedKey;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DocumentEncrypterImplTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static FileReaderImpl fileReader;
	private static DocumentXmlConverterImpl documentXmlConverter;

	private static String c32;
	private static Document c32Document;

	private static RuleExecutionContainer ruleExecutionContainer;
	private static final String ENCRYPTION_PREFIX = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ClinicalDocument xmlns=\"urn:hl7-org:v3\"                  xmlns:sdtc=\"urn:hl7-org:sdtc\"                  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">   <xenc:EncryptedData xmlns:xenc=\"http://www.w3.org/2001/04/xmlenc#\"                       Type=\"http://www.w3.org/2001/04/xmlenc#Content\">      <xenc:EncryptionMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#aes128-cbc\"/>      <ds:KeyInfo xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">         <xenc:EncryptedKey>            <xenc:EncryptionMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#kw-tripledes\"/>            <xenc:CipherData>               <xenc:CipherValue>";

	private static DocumentEncrypterImpl documentEncrypter;
	
	@Before
	public void setUp() throws Exception {
		fileReader = new FileReaderImpl();
		documentXmlConverter = new DocumentXmlConverterImpl();

		documentEncrypter = new DocumentEncrypterImpl();
		documentEncrypter.setDocumentXmlConverter(documentXmlConverter);

		c32 = fileReader.readFile("c32.xml");
		c32Document = documentXmlConverter.loadDocument(c32);

		ruleExecutionContainer = setRuleExecutionContainer();
	}

	@Test
	public void testEncryptElement() {
		// Arrange
		Key aesSymmetricKey = null;
		Key deSedeEncryptKey = null;
		EncryptedKey encryptedKey = null;
		Element rootElement = null;
		try {
			aesSymmetricKey = EncryptTool.generateDataEncryptionKey();
			deSedeEncryptKey = EncryptTool.generateKeyEncryptionKey();
			String algorithmURI = XMLCipher.TRIPLEDES_KeyWrap;
			XMLCipher keyCipher = XMLCipher.getInstance(algorithmURI);
			keyCipher.init(XMLCipher.WRAP_MODE, deSedeEncryptKey);
			encryptedKey = keyCipher.encryptKey(c32Document, aesSymmetricKey);
			rootElement = c32Document.getDocumentElement();

			String notEncrypted = documentXmlConverter.convertXmlDocToString(c32Document);

			// Act
			documentEncrypter.encryptElement(c32Document, aesSymmetricKey,
					encryptedKey, rootElement);
			String encrypted = documentXmlConverter.convertXmlDocToString(c32Document);
			logger.debug("NOT ENCRYPTED--> " + notEncrypted);
			logger.debug("ENCRYPTED--> " + encrypted);

			// Assert
			assertNotEquals(notEncrypted, encrypted);
			assertTrue(encrypted.startsWith(ENCRYPTION_PREFIX));

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			fail(e.getMessage().toString());
		}
	}

	@Test
	public void testEncryptDocument() {
		// Act
		logger.debug("NOT ENCRYPTED--> " + c32);
		String encrypted = null;
		try {
			encrypted = documentEncrypter.encryptDocument(
					EncryptTool.generateKeyEncryptionKey(), c32,
					ruleExecutionContainer);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.debug("ENCRYPTED--> " + encrypted);

		// Assert
		assertNotEquals(c32, encrypted);
		assertTrue(encrypted.startsWith(ENCRYPTION_PREFIX));
	}

	@Test
	public void testEncryptDocument_EmptyRuleExecutionContainer() {
		// Act
		logger.debug("NOT ENCRYPTED--> " + c32);
		String encrypted = null;
		try {
			List<RuleExecutionResponse> list = new LinkedList<RuleExecutionResponse>();
			RuleExecutionContainer container = new RuleExecutionContainer();
			container.setExecutionResponseList(list);
			encrypted = documentEncrypter.encryptDocument(
					EncryptTool.generateKeyEncryptionKey(), c32, container);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.debug("ENCRYPTED--> " + encrypted);

		// Assert
		assertNotEquals(c32, encrypted);
		assertNull(encrypted);
	}

	@Test
	public void testEncryptDocument_NoEncryptObligationPolicy() {
		// Act
		logger.debug("NOT ENCRYPTED--> " + c32);
		String encrypted = null;
		try {
			List<RuleExecutionResponse> list = new LinkedList<RuleExecutionResponse>();
			RuleExecutionContainer container = new RuleExecutionContainer();
			RuleExecutionResponse resp = new RuleExecutionResponse();
			resp.setDocumentObligationPolicy("HUAPRV");
			list.add(resp);
			container.setExecutionResponseList(list);
			encrypted = documentEncrypter.encryptDocument(
					EncryptTool.generateKeyEncryptionKey(), c32, container);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.debug("ENCRYPTED--> " + encrypted);

		// Assert
		assertNotEquals(c32, encrypted);
		assertNull(encrypted);
	}

	@Test(expected = DS4PException.class)
	public void testEncryptDocument_Throws_DS4PException()
			throws XMLEncryptionException, Exception {
		// Empty xml file
		documentEncrypter.encryptDocument(
				EncryptTool.generateDataEncryptionKey(), "",
				ruleExecutionContainer);
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
}
