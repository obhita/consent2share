package gov.samhsa.acs.documentsegmentation.tools;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.brms.domain.Confidentiality;
import gov.samhsa.acs.brms.domain.ObligationPolicyDocument;
import gov.samhsa.acs.brms.domain.RefrainPolicy;
import gov.samhsa.acs.brms.domain.RuleExecutionContainer;
import gov.samhsa.acs.brms.domain.RuleExecutionResponse;
import gov.samhsa.acs.brms.domain.Sensitivity;
import gov.samhsa.acs.brms.domain.UsPrivacyLaw;
import gov.samhsa.acs.common.bean.XacmlResult;
import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.tool.DocumentXmlConverterImpl;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.documentsegmentation.tools.DocumentEditorImpl;
import gov.samhsa.acs.documentsegmentation.tools.DocumentRedactorImpl;
import gov.samhsa.acs.documentsegmentation.tools.MetadataGeneratorImpl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.io.FileUtils;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DocumentRedactorImplTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String PROBLEMS = "@Problems";
	private static final String ALLERGIES = "@Allergies";
	private static final String MEDICATIONS = "@Medications";
	private static final String RESULTS = "@Results";

	private static final String PROBLEMS_SECTION = "11450-4";
	private static final String ALLERGIES_SECTION = "48765-2";
	private static final String MEDICATIONS_SECTION = "10160-0";
	private static final String RESULTS_SECTION = "30954-2";
	private static final String NOT_SELECTED = "";

	private static final String EMPTY_RULE_EXECUTION_RESPONSE_CONTAINER = "<ruleExecutionContainer><executionResponseList></executionResponseList></ruleExecutionContainer>";

	private static SimpleMarshallerImpl marshaller;
	private static FileReaderImpl fileReader;
	private static DocumentEditorImpl documentEditor;
	private static DocumentEditorImpl documentEditorMock;
	private static DocumentXmlConverterImpl documentXmlConverter;
	private static DocumentXmlConverterImpl documentXmlConverterSpy;

	private static XacmlResult xacmlResultMock;

	private static String c32;
	private static String robustC32;
	private static String xacmlResult;
	private static Document c32Document;
	private static RuleExecutionContainer ruleExecutionContainer;
	private static RuleExecutionContainer ruleExecutionContainerWithHivEth;

	private static DocumentRedactorImpl documentRedactor;

	@Before
	public void setUp() throws Exception {
		// Arrange
		fileReader = new FileReaderImpl();
		marshaller = new SimpleMarshallerImpl();
		documentXmlConverter = new DocumentXmlConverterImpl();
		documentEditor = new DocumentEditorImpl(new MetadataGeneratorImpl(),
				fileReader, documentXmlConverter);

		ruleExecutionContainer = setRuleExecutionContainer();
		ruleExecutionContainerWithHivEth = marshaller.unmarshallFromXml(
				RuleExecutionContainer.class,
				fileReader.readFile("ruleExecutionResponseContainer.xml"));
		xacmlResultMock = setMockXacmlResult();
		c32 = fileReader.readFile("c32.xml");
		robustC32 = fileReader
				.readFile("testMU_Rev3_HITSP_C32C83_4Sections_RobustEntries_NoErrors.xml");
		xacmlResult = fileReader.readFile("testXacmlResult.xml");

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

	@Test
	public void testRedactDocument_Problems_Section_Redacted()
			throws XPathExpressionException, XMLEncryptionException, Exception {
		// Arrange
		String problems = PROBLEMS_SECTION;
		String allergies = NOT_SELECTED;
		String medications = NOT_SELECTED;
		String results = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results);

		// Act
		String redactedC32 = documentRedactor.redactDocument(robustC32,
				ruleExecutionContainer, xacmlResultObj);

		// Assert
		assertNotNull(getSectionElement(robustC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(robustC32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(robustC32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(robustC32, RESULTS_SECTION));
		// null
		assertNull(getSectionElement(redactedC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(redactedC32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(redactedC32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(redactedC32, RESULTS_SECTION));
	}

	@Test
	public void testRedactDocument_Allergies_Section_Redacted()
			throws XPathExpressionException, XMLEncryptionException, Exception {
		// Arrange
		String problems = NOT_SELECTED;
		String allergies = ALLERGIES_SECTION;
		String medications = NOT_SELECTED;
		String results = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results);

		// Act
		String redactedC32 = documentRedactor.redactDocument(robustC32,
				ruleExecutionContainer, xacmlResultObj);

		// Assert
		assertNotNull(getSectionElement(robustC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(robustC32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(robustC32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(robustC32, RESULTS_SECTION));
		assertNotNull(getSectionElement(redactedC32, PROBLEMS_SECTION));
		// null
		assertNull(getSectionElement(redactedC32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(redactedC32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(redactedC32, RESULTS_SECTION));
	}

	@Test
	public void testRedactDocument_Medications_Section_Redacted()
			throws XPathExpressionException, XMLEncryptionException, Exception {
		// Arrange
		String problems = NOT_SELECTED;
		String allergies = NOT_SELECTED;
		String medications = MEDICATIONS_SECTION;
		String results = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results);

		// Act
		String redactedC32 = documentRedactor.redactDocument(robustC32,
				ruleExecutionContainer, xacmlResultObj);

		// Assert
		assertNotNull(getSectionElement(robustC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(robustC32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(robustC32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(robustC32, RESULTS_SECTION));
		assertNotNull(getSectionElement(redactedC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(redactedC32, ALLERGIES_SECTION));
		// null
		assertNull(getSectionElement(redactedC32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(redactedC32, RESULTS_SECTION));
	}

	@Test
	public void testRedactDocument_Results_Section_Redacted()
			throws XPathExpressionException, XMLEncryptionException, Exception {
		// Arrange
		String problems = NOT_SELECTED;
		String allergies = NOT_SELECTED;
		String medications = NOT_SELECTED;
		String results = RESULTS_SECTION;
		initDocumentRedactorWithActualServices();
		RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results);

		// Act
		String redactedC32 = documentRedactor.redactDocument(robustC32,
				ruleExecutionContainer, xacmlResultObj);

		// Assert
		assertNotNull(getSectionElement(robustC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(robustC32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(robustC32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(robustC32, RESULTS_SECTION));
		assertNotNull(getSectionElement(redactedC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(redactedC32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(redactedC32, MEDICATIONS_SECTION));
		// null
		assertNull(getSectionElement(redactedC32, RESULTS_SECTION));
	}

	@Test
	public void testRedactDocument_Problems_Allergies_Sections_Redacted()
			throws XPathExpressionException, XMLEncryptionException, Exception {
		// Arrange
		String problems = PROBLEMS_SECTION;
		String allergies = ALLERGIES_SECTION;
		String medications = NOT_SELECTED;
		String results = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results);

		// Act
		String redactedC32 = documentRedactor.redactDocument(robustC32,
				ruleExecutionContainer, xacmlResultObj);

		// Assert
		assertNotNull(getSectionElement(robustC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(robustC32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(robustC32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(robustC32, RESULTS_SECTION));
		// null
		assertNull(getSectionElement(redactedC32, PROBLEMS_SECTION));
		// null
		assertNull(getSectionElement(redactedC32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(redactedC32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(redactedC32, RESULTS_SECTION));
	}

	@Test
	public void testRedactDocument_Problems_Medications_Sections_Redacted()
			throws XPathExpressionException, XMLEncryptionException, Exception {
		// Arrange
		String problems = PROBLEMS_SECTION;
		String allergies = NOT_SELECTED;
		String medications = MEDICATIONS_SECTION;
		String results = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results);

		// Act
		String redactedC32 = documentRedactor.redactDocument(robustC32,
				ruleExecutionContainer, xacmlResultObj);

		// Assert
		assertNotNull(getSectionElement(robustC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(robustC32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(robustC32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(robustC32, RESULTS_SECTION));
		// null
		assertNull(getSectionElement(redactedC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(redactedC32, ALLERGIES_SECTION));
		// null
		assertNull(getSectionElement(redactedC32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(redactedC32, RESULTS_SECTION));
	}

	@Test
	public void testRedactDocument_Problems_Results_Sections_Redacted()
			throws XPathExpressionException, XMLEncryptionException, Exception {
		// Arrange
		String problems = PROBLEMS_SECTION;
		String allergies = NOT_SELECTED;
		String medications = NOT_SELECTED;
		String results = RESULTS_SECTION;
		initDocumentRedactorWithActualServices();
		RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results);

		// Act
		String redactedC32 = documentRedactor.redactDocument(robustC32,
				ruleExecutionContainer, xacmlResultObj);

		// Assert
		assertNotNull(getSectionElement(robustC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(robustC32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(robustC32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(robustC32, RESULTS_SECTION));
		// null
		assertNull(getSectionElement(redactedC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(redactedC32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(redactedC32, MEDICATIONS_SECTION));
		// null
		assertNull(getSectionElement(redactedC32, RESULTS_SECTION));
	}

	@Test
	public void testRedactDocument_Allergies_Medications_Results_Sections_Redacted()
			throws XPathExpressionException, XMLEncryptionException, Exception {
		// Arrange
		String problems = NOT_SELECTED;
		String allergies = ALLERGIES_SECTION;
		String medications = MEDICATIONS_SECTION;
		String results = RESULTS_SECTION;
		initDocumentRedactorWithActualServices();
		RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results);

		// Act
		String redactedC32 = documentRedactor.redactDocument(robustC32,
				ruleExecutionContainer, xacmlResultObj);

		// Assert
		assertNotNull(getSectionElement(robustC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(robustC32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(robustC32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(robustC32, RESULTS_SECTION));
		assertNotNull(getSectionElement(redactedC32, PROBLEMS_SECTION));
		// null
		assertNull(getSectionElement(redactedC32, ALLERGIES_SECTION));
		// null
		assertNull(getSectionElement(redactedC32, MEDICATIONS_SECTION));
		// null
		assertNull(getSectionElement(redactedC32, RESULTS_SECTION));
	}

	@Test
	public void testRedactDocument_Allergies_Medications_Results_Sections_Redacted_Hiv_Eth_NotRedacted()
			throws XPathExpressionException, XMLEncryptionException, Exception {
		// Arrange
		String problems = NOT_SELECTED;
		String allergies = ALLERGIES_SECTION;
		String medications = MEDICATIONS_SECTION;
		String results = RESULTS_SECTION;
		initDocumentRedactorWithActualServices();
		RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results);
		String ethObservationId = "e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7";
		String hivObservationId = "d11275e7-67ae-11db-bd13-0800200c9a66";

		// Act
		String redactedC32 = documentRedactor.redactDocument(c32,
				ruleExecutionContainer, xacmlResultObj);

		// Assert
		assertNotNull(getSectionElement(c32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(c32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(c32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(c32, RESULTS_SECTION));
		assertNotNull(getSectionElement(redactedC32, PROBLEMS_SECTION));
		// null
		assertNull(getSectionElement(redactedC32, ALLERGIES_SECTION));
		// null
		assertNull(getSectionElement(redactedC32, MEDICATIONS_SECTION));
		// null
		assertNull(getSectionElement(redactedC32, RESULTS_SECTION));
		// RuleExecutionContainer is empty, so these entries should still be in
		// Problems section
		assertNotNull(getEntryElement(redactedC32, ethObservationId));
		assertNotNull(getEntryElement(redactedC32, hivObservationId));
	}

	@Test
	public void testRedactDocument_Allergies_Medications_Results_Sections_Hiv_Eth_Redacted()
			throws XPathExpressionException, XMLEncryptionException, Exception {
		// Arrange
		String problems = NOT_SELECTED;
		String allergies = ALLERGIES_SECTION;
		String medications = MEDICATIONS_SECTION;
		String results = RESULTS_SECTION;
		initDocumentRedactorWithActualServices();
		RuleExecutionContainer ruleExecutionContainer = ruleExecutionContainerWithHivEth;
		XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results);
		String ethObservationId = "e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7";
		String hivObservationId = "d11275e7-67ae-11db-bd13-0800200c9a66";

		// Act
		String redactedC32 = documentRedactor.redactDocument(c32,
				ruleExecutionContainer, xacmlResultObj);

		// Assert
		assertNotNull(getSectionElement(c32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(c32, ALLERGIES_SECTION));
		assertNotNull(getSectionElement(c32, MEDICATIONS_SECTION));
		assertNotNull(getSectionElement(c32, RESULTS_SECTION));
		assertNotNull(getSectionElement(redactedC32, PROBLEMS_SECTION));
		// null
		assertNull(getSectionElement(redactedC32, ALLERGIES_SECTION));
		// null
		assertNull(getSectionElement(redactedC32, MEDICATIONS_SECTION));
		// null
		assertNull(getSectionElement(redactedC32, RESULTS_SECTION));
		// RuleExecutionContainer has ETH and HIV sensitivities for these
		// observationIds, so the Problems section should exist, but these
		// entries should have been redacted.
		assertNull(getEntryElement(redactedC32, ethObservationId));
		assertNull(getEntryElement(redactedC32, hivObservationId));
	}
	
	@Test
	public void testRedactDocument_RemC32()
			throws XPathExpressionException, XMLEncryptionException, Exception {
		// Arrange
		initDocumentRedactorWithActualServices();
		String remC32 = fileReader.readFile("testRemC32.xml");
		String remRuleExecutionContainerActual = fileReader.readFile("testRemRuleExecutionContainerActual.xml");
		RuleExecutionContainer remRuleExecutionContainerActualObj = marshaller.unmarshallFromXml(RuleExecutionContainer.class, remRuleExecutionContainerActual);
		String remXacmlResult = fileReader.readFile("testRemXacmlResult.xml");
		XacmlResult remXacmlResultObj = marshaller.unmarshallFromXml(XacmlResult.class, remXacmlResult);
		String ethObservationId = "d17e379";
		String hivObservationId = "d17e356";		

		// Act
		String redactedC32 = documentRedactor.redactDocument(remC32,
				remRuleExecutionContainerActualObj, remXacmlResultObj);
		
		// Assert
		// sections
		assertNotNull(getSectionElement(remC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(remC32, MEDICATIONS_SECTION));
		assertNull(getSectionElement(remC32, ALLERGIES_SECTION));
		assertNull(getSectionElement(remC32, RESULTS_SECTION));
		assertNotNull(getSectionElement(redactedC32, PROBLEMS_SECTION));
		assertNotNull(getSectionElement(redactedC32, MEDICATIONS_SECTION));
		assertNull(getSectionElement(redactedC32, ALLERGIES_SECTION));
		assertNull(getSectionElement(redactedC32, RESULTS_SECTION));
		// entries
		assertNotNull(getEntryElement(remC32, ethObservationId));
		assertNotNull(getEntryElement(remC32, hivObservationId));
		assertNull(getEntryElement(redactedC32, ethObservationId));
		assertNull(getEntryElement(redactedC32, hivObservationId));
		// human readable
		assertTrue(getHumanReadableTextNodeList(remC32,"substance abuse").getLength() > 0);
		assertTrue(getHumanReadableTextNodeList(remC32,"hiv").getLength() > 0);
		assertTrue(getHumanReadableTextNodeList(redactedC32,"substance abuse").getLength() == 0);
		assertTrue(getHumanReadableTextNodeList(redactedC32,"hiv").getLength() == 0);
	}

	@Test(expected = DS4PException.class)
	public void testRedactDocument_Throws_DS4PException() {
		@SuppressWarnings("unused")
		String result = documentRedactor.redactDocument("", null, null);
	}

	@Test
	public void testRedactDocument_WrongSensitivityInContainer() {
		// Arrange
		initDocumentRedactorWithActualServices();

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
		// Arrange
		initDocumentRedactorWithActualServices();

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

	private Element getSectionElement(String c32, String sectionLoincCode)
			throws XPathExpressionException, XMLEncryptionException, Exception {
		String xPathExprSection = "//hl7:component[hl7:section[hl7:code[@code='%']]]";
		xPathExprSection = xPathExprSection.replace("%", sectionLoincCode);
		Element element = documentEditor.getElement(
				documentXmlConverter.loadDocument(c32), xPathExprSection);
		return element;
	}

	private Element getEntryElement(String c32, String observationId)
			throws XPathExpressionException, XMLEncryptionException, Exception {
		String xPathExprSection = "//hl7:id[@root='%']/ancestor::hl7:entry";
		xPathExprSection = xPathExprSection.replace("%", observationId);
		Element element = documentEditor.getElement(
				documentXmlConverter.loadDocument(c32), xPathExprSection);
		return element;
	}
	
	private NodeList getHumanReadableTextNodeList(String c32, String textContent)
			throws XPathExpressionException, XMLEncryptionException, Exception {
		String xPathExprHumanReadableTextNode = "//hl7:section/hl7:text//*/text()[contains(lower-case(.), '%')]";
		xPathExprHumanReadableTextNode = xPathExprHumanReadableTextNode.replace("%", textContent.toLowerCase());
		NodeList nodeList = documentEditor.getNodeList(
				documentXmlConverter.loadDocument(c32), xPathExprHumanReadableTextNode);
		return nodeList;
	}

	private XacmlResult initXacmlResult(String problems, String allergies,
			String medications, String results) throws JAXBException {
		String xacmlResultForTest = xacmlResult.replace(PROBLEMS, problems)
				.replace(ALLERGIES, allergies)
				.replace(MEDICATIONS, medications).replace(RESULTS, results);
		XacmlResult xacmlResultObj = marshaller.unmarshallFromXml(
				XacmlResult.class, xacmlResultForTest);
		xacmlResultObj.getPdpObligations().removeAll(Arrays.asList("", null));
		return xacmlResultObj;
	}

	private RuleExecutionContainer initRuleExecutionContainer()
			throws JAXBException {
		RuleExecutionContainer ruleExecutionContainer = marshaller
				.unmarshallFromXml(RuleExecutionContainer.class,
						EMPTY_RULE_EXECUTION_RESPONSE_CONTAINER);
		return ruleExecutionContainer;
	}

	private void initDocumentRedactorWithActualServices() {
		ReflectionTestUtils.setField(documentRedactor, "documentEditor",
				documentEditor);
		ReflectionTestUtils.setField(documentRedactor, "documentXmlConverter",
				documentXmlConverter);
	}

	private static RuleExecutionContainer setRuleExecutionContainer() {
		RuleExecutionContainer container = new RuleExecutionContainer();
		RuleExecutionResponse r1 = new RuleExecutionResponse();
		r1.setC32SectionLoincCode("11450-4");
		r1.setC32SectionTitle("Problems");
		r1.setCode("66214007");
		r1.setCodeSystemName("SNOMED CT");
		r1.setDisplayName("Substance Abuse Disorder");
		r1.setDocumentObligationPolicy(ObligationPolicyDocument.ENCRYPT);
		r1.setDocumentRefrainPolicy(RefrainPolicy.NODSCLCD);
		r1.setImpliedConfSection(Confidentiality.R);
		r1.setItemAction("REDACT");
		r1.setObservationId("e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7");
		r1.setSensitivity(Sensitivity.ETH);
		r1.setUSPrivacyLaw(UsPrivacyLaw._42CFRPart2);
		RuleExecutionResponse r2 = new RuleExecutionResponse();
		r2.setC32SectionLoincCode("11450-4");
		r2.setC32SectionTitle("Problems");
		r2.setCode("111880001");
		r2.setCodeSystemName("SNOMED CT");
		r2.setDisplayName("Acute HIV");
		r2.setDocumentObligationPolicy(ObligationPolicyDocument.ENCRYPT);
		r2.setDocumentRefrainPolicy(RefrainPolicy.NODSCLCD);
		r2.setImpliedConfSection(Confidentiality.R);
		r2.setItemAction("MASK");
		r2.setObservationId("d11275e7-67ae-11db-bd13-0800200c9a66");
		r2.setSensitivity(Sensitivity.HIV);
		r2.setUSPrivacyLaw(UsPrivacyLaw._42CFRPart2);
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
		r1.setDocumentObligationPolicy(ObligationPolicyDocument.ENCRYPT);
		r1.setDocumentRefrainPolicy(RefrainPolicy.NODSCLCD);
		r1.setImpliedConfSection(Confidentiality.R);
		r1.setItemAction("REDACT");
		r1.setObservationId("e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7");
		r1.setSensitivity(Sensitivity.STD);
		r1.setUSPrivacyLaw(UsPrivacyLaw._42CFRPart2);
		RuleExecutionResponse r2 = new RuleExecutionResponse();
		r2.setC32SectionLoincCode("11450-4");
		r2.setC32SectionTitle("Problems");
		r2.setCode("111880001");
		r2.setCodeSystemName("SNOMED CT");
		r2.setDisplayName("Acute HIV");
		r2.setDocumentObligationPolicy(ObligationPolicyDocument.ENCRYPT);
		r2.setDocumentRefrainPolicy(RefrainPolicy.NODSCLCD);
		r2.setImpliedConfSection(Confidentiality.R);
		r2.setItemAction("MASK");
		r2.setObservationId("d11275e7-67ae-11db-bd13-0800200c9a66");
		r2.setSensitivity(Sensitivity.SEX);
		r2.setUSPrivacyLaw(UsPrivacyLaw._42CFRPart2);
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
		when(mock.getPdpObligations()).thenReturn(obligations)
				.thenReturn(obligations).thenReturn(obligations);
		return mock;
	}

	private static XacmlResult setMockXacmlResult_WrongSensitivity() {
		XacmlResult mock = mock(XacmlResult.class);
		List<String> obligations = new LinkedList<String>();
		obligations.add("STD");
		obligations.add("SEX");
		when(mock.getPdpObligations()).thenReturn(obligations)
				.thenReturn(obligations).thenReturn(obligations);
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
				.thenReturn(e2).thenReturn(e3).thenReturn(e4).thenReturn(null)
				.thenReturn(null);

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
