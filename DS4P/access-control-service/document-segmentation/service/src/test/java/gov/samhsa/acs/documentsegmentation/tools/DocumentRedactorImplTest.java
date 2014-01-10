package gov.samhsa.acs.documentsegmentation.tools;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.common.bean.RuleExecutionContainer;
import gov.samhsa.acs.common.bean.XacmlResult;
import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.tool.DocumentXmlConverterImpl;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.documentsegmentation.tools.DocumentEditorImpl;
import gov.samhsa.acs.documentsegmentation.tools.DocumentRedactorImpl;
import gov.samhsa.acs.documentsegmentation.tools.MetadataGeneratorImpl;
import gov.va.ds4p.cas.RuleExecutionResponse;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.io.FileUtils;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DocumentRedactorImplTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static FileReaderImpl fileReader;
	private static DocumentEditorImpl documentEditor;
	private static DocumentEditorImpl documentEditorMock;
	private static DocumentXmlConverterImpl documentXmlConverterSpy;

	private static XacmlResult xacmlResultMock;

	private static String c32;
	private static Document c32Document;
	private static RuleExecutionContainer ruleExecutionContainer;

	private static DocumentRedactorImpl documentRedactor;

	@Before
	public void setUp() throws Exception {
		// Arrange
		fileReader = new FileReaderImpl();

		documentEditor = new DocumentEditorImpl(new MetadataGeneratorImpl(),
				fileReader, new DocumentXmlConverterImpl());

		ruleExecutionContainer = setRuleExecutionContainer();
		xacmlResultMock = setMockXacmlResult();
		c32 = fileReader.readFile("c32.xml");

		documentXmlConverterSpy = setSpyDocumentXmlConverter();
		documentEditorMock = setMockDocumentEditor();

		documentRedactor = new DocumentRedactorImpl(documentEditorMock,
				documentXmlConverterSpy);
	}

	@Test
	public void testRedactDocument() {
		// Act
		String result = documentRedactor.redactDocument(c32,
				ruleExecutionContainer, xacmlResultMock);
		logger.debug("RESULT--> " + result);

		// Assert
		assertTrue(c32
				.contains("e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7"));
		assertTrue(c32.contains("d11275e7-67ae-11db-bd13-0800200c9a66"));
		assertTrue(!result
				.contains("e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7"));
		assertTrue(!result.contains("d11275e7-67ae-11db-bd13-0800200c9a66"));
	}

	@Test(expected = DS4PException.class)
	public void testRedactDocument_Throws_DS4PException() {
		@SuppressWarnings("unused")
		String result = documentRedactor.redactDocument("", null, null);
	}

	@Test
	public void testRedactDocument_WrongSensitivityInContainer() {
		// Act
		String result = documentRedactor.redactDocument(c32,
				setRuleExecutionContainer_WrongSensitivity(), xacmlResultMock);
		logger.debug("RESULT--> " + result);

		// Assert
		assertTrue(c32
				.contains("e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7"));
		assertTrue(c32.contains("d11275e7-67ae-11db-bd13-0800200c9a66"));
		assertTrue(result
				.contains("e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7"));
		assertTrue(result.contains("d11275e7-67ae-11db-bd13-0800200c9a66"));
	}

	@Test
	public void testRedactDocument_WrongSensitivityInXacmlResult() {
		// Act
		String result = documentRedactor.redactDocument(c32,
				ruleExecutionContainer, setMockXacmlResult_WrongSensitivity());
		logger.debug("RESULT--> " + result);

		// Assert
		assertTrue(c32
				.contains("e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7"));
		assertTrue(c32.contains("d11275e7-67ae-11db-bd13-0800200c9a66"));
		assertTrue(result
				.contains("e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7"));
		assertTrue(result.contains("d11275e7-67ae-11db-bd13-0800200c9a66"));
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

	private static RuleExecutionContainer setRuleExecutionContainer_WrongSensitivity() {
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
		r1.setSensitivity("STD");
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
		r2.setSensitivity("SEX");
		r2.setUSPrivacyLaw("42CFRPart2");
		List<RuleExecutionResponse> list = new LinkedList<RuleExecutionResponse>();
		list.add(r1);
		list.add(r2);
		container.setExecutionResponseList(list);
		return container;
	}

	private static XacmlResult setMockXacmlResult() {
		XacmlResult mock = mock(XacmlResult.class);
		List<String> obligations = new LinkedList<String>();
		obligations.add("ETH");
		obligations.add("HIV");
		when(mock.getPdpObligations()).thenReturn(obligations);
		return mock;
	}

	private static XacmlResult setMockXacmlResult_WrongSensitivity() {
		XacmlResult mock = mock(XacmlResult.class);
		List<String> obligations = new LinkedList<String>();
		obligations.add("STD");
		obligations.add("SEX");
		when(mock.getPdpObligations()).thenReturn(obligations);
		return mock;
	}

	private static DocumentXmlConverterImpl setSpyDocumentXmlConverter()
			throws Exception {
		DocumentXmlConverterImpl converter = new DocumentXmlConverterImpl();
		DocumentXmlConverterImpl spy = spy(converter);

		c32Document = readDocument("src/test/resources/xmlDocument.txt");

		doReturn(c32Document).when(spy).loadDocument(anyString());
		return spy;
	}

	private static DocumentEditorImpl setMockDocumentEditor()
			throws XPathExpressionException, XMLEncryptionException, Exception {
		DocumentEditorImpl mock = mock(DocumentEditorImpl.class);

		Element e1 = documentEditor.getElement(c32Document,
				"//hl7:td[.='Substance Abuse Disorder']/parent::hl7:tr");
		Element e2 = documentEditor
				.getElement(
						c32Document,
						"//hl7:id[@root='e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7']/ancestor::hl7:entry");
		Element e3 = documentEditor.getElement(c32Document,
				"//hl7:td[.='Acute HIV']/parent::hl7:tr");
		Element e4 = documentEditor
				.getElement(c32Document,
						"//hl7:id[@root='d11275e7-67ae-11db-bd13-0800200c9a66']/ancestor::hl7:entry");
		when(mock.getElement(isA(Document.class), anyString())).thenReturn(e1)
				.thenReturn(e2).thenReturn(e3).thenReturn(e4);

		return mock;
	}

	private static Document readDocument(String filePath) throws IOException,
			ClassNotFoundException {
		return (Document) readObject(filePath);
	}

	private static Object readObject(String filePath) throws IOException,
			ClassNotFoundException {
		File file = new File(filePath);
		byte[] b = null;

		b = FileUtils.readFileToByteArray(file);
		ByteArrayInputStream in = new ByteArrayInputStream(b);
		ObjectInputStream is = new ObjectInputStream(in);
		return is.readObject();
	}
}
