package gov.samhsa.acs.documentsegmentation.tools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.brms.RuleExecutionServiceImpl;
import gov.samhsa.acs.brms.domain.FactModel;
import gov.samhsa.acs.brms.guvnor.GuvnorServiceImpl;
import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.namespace.PepNamespaceContext;
import gov.samhsa.acs.common.tool.DocumentAccessorImpl;
import gov.samhsa.acs.common.tool.DocumentXmlConverterImpl;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.xml.security.encryption.XMLEncryptionException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DocumentTaggerImplTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String N = "N";
	private static final String R = "R";
	private static final String V = "V";

	private static final String PROBLEMS_SECTION = "11450-4";
	private static final String ALLERGIES_SECTION = "48765-2";
	private static final String MEDICATIONS_SECTION = "10160-0";
	private static final String RESULTS_SECTION = "30954-2";

	private static final String REDACT = "REDACT";
	private static final String NO_ACTION = "NO_ACTION";

	private static FileReaderImpl fileReader;
	private static SimpleMarshallerImpl marshaller;
	private static DocumentFactModelExtractorImpl documentFactModelExtractor;
	private static EmbeddedClinicalDocumentExtractor embeddedClinicalDocumentExtractor;
	private static DocumentAccessorImpl documentAccessor;
	private static RuleExecutionServiceImpl ruleExecutionService;
	private static GuvnorServiceImpl guvnorServiceMock;

	private static String c32;
	private static String remC32;
	private static String robustC32;
	private static String executionResponseContainer;
	private static String remExecutionResponseContainer;
	private static String testRuleExecutionResponseContainer_xml;
	private static String messageId;
	private static DocumentXmlConverterImpl documentXmlConverter;

	private static DocumentTaggerImpl documentTagger;

	@Before
	public void setUp() throws Exception {
		// Arrange
		fileReader = new FileReaderImpl();
		documentAccessor = new DocumentAccessorImpl();
		marshaller = new SimpleMarshallerImpl();
		documentFactModelExtractor = new DocumentFactModelExtractorImpl();
		documentXmlConverter = new DocumentXmlConverterImpl();
		embeddedClinicalDocumentExtractor = new EmbeddedClinicalDocumentExtractorImpl(
				documentXmlConverter, documentAccessor);
		guvnorServiceMock = mock(GuvnorServiceImpl.class);
		String ruleSource = fileReader.readFile("testAnnotationRules.txt");
		when(guvnorServiceMock.getVersionedRulesFromPackage()).thenReturn(
				ruleSource);
		ruleExecutionService = new RuleExecutionServiceImpl(guvnorServiceMock,
				marshaller);
		c32 = fileReader.readFile("sampleC32/c32.xml");
		remC32 = fileReader.readFile("testRemC32.xml");
		robustC32 = fileReader
				.readFile("testMU_Rev3_HITSP_C32C83_4Sections_RobustEntries_NoErrors.xml");
		executionResponseContainer = fileReader
				.readFile("ruleExecutionResponseContainer.xml");
		remExecutionResponseContainer = fileReader
				.readFile("testRemRuleExecutionContainer.xml");
		testRuleExecutionResponseContainer_xml = fileReader
				.readFile("testRuleExecutionResponseContainer.xml");
		messageId = UUID.randomUUID().toString();
		documentXmlConverter = new DocumentXmlConverterImpl();

		documentTagger = new DocumentTaggerImpl();
	}

	@Test
	public void testTagDocument() throws Exception {
		// Arrange
		logger.debug(c32);

		// Act
		String taggedDocument = documentTagger.tagDocument(c32,
				executionResponseContainer);

		// Assert
		logger.debug(taggedDocument);

		assertTrue(!taggedDocument.contains("<confidentialityCode/>"));
		assertTrue(taggedDocument
				.contains("<confidentialityCode xmlns:ds4p=\"http://www.siframework.org/ds4p\""));
		assertTrue(taggedDocument.contains("code=\"R\""));
		assertTrue(taggedDocument
				.contains("codeSystem=\"2.16.840.1.113883.5.25\""));

		assertTrue(c32.contains("<confidentialityCode/>"));
		assertTrue(!c32
				.contains("<confidentialityCode xmlns:ds4p=\"http://www.siframework.org/ds4p\""));
		assertTrue(!c32.contains("code=\"R\""));
		assertTrue(!c32.contains("codeSystem=\"2.16.840.1.113883.5.25\""));
	}

	@Test
	public void testTagDocument_Entry_Level_Tagging() throws Exception {
		// Arrange
		logger.debug(c32);
		String factModelXml = documentFactModelExtractor.extractFactModel(c32,
				fileReader.readFile("testXacmlResultRedactHIV.xml"));
		c32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		FactModel factModel = marshaller.unmarshallFromXml(FactModel.class,
				factModelXml);
		String executionResponseContainer = ruleExecutionService
				.assertAndExecuteClinicalFacts(factModel)
				.getRuleExecutionResponseContainer();

		// Act
		String taggedDocument = documentTagger.tagDocument(c32,
				executionResponseContainer);

		// Assert
		logger.debug(taggedDocument);

		assertTrue(!taggedDocument.contains("<confidentialityCode/>"));
		assertTrue(taggedDocument
				.contains("<confidentialityCode xmlns:ds4p=\"http://www.siframework.org/ds4p\""));
		assertTrue(taggedDocument.contains("code=\"R\""));
		assertTrue(taggedDocument
				.contains("codeSystem=\"2.16.840.1.113883.5.25\""));

		assertTrue(c32.contains("<confidentialityCode/>"));
		assertTrue(!c32
				.contains("<confidentialityCode xmlns:ds4p=\"http://www.siframework.org/ds4p\""));
		assertTrue(!c32.contains("code=\"R\""));
		assertTrue(!c32.contains("codeSystem=\"2.16.840.1.113883.5.25\""));

		Document taggedDoc = documentXmlConverter.loadDocument(taggedDocument);
		verifyEntryLevelTags(taggedDoc, "d1e259",
				Arrays.asList(new String[] { "R", "NODSCLCD", "ENCRYPT" }));
		verifyEntryLevelTags(taggedDoc, "d1e220",
				Arrays.asList(new String[] { "V", "ENCRYPT", "NODSCLCD" }));
		verifyEntryLevelTags(taggedDoc, "d1e1261",
				Arrays.asList(new String[] { "R", "ENCRYPT", "NORDSCLCD" }));
	}

	@Test
	public void testTagDocument_Entry_Level_Tagging_Four_Sections()
			throws Exception {
		// Arrange
		logger.debug(c32);
		String factModelXml = documentFactModelExtractor.extractFactModel(c32,
				fileReader.readFile("testXacmlResultRedactHIV.xml"));
		c32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		FactModel factModel = marshaller.unmarshallFromXml(FactModel.class,
				factModelXml);
		String executionResponseContainer = ruleExecutionService
				.assertAndExecuteClinicalFacts(factModel)
				.getRuleExecutionResponseContainer();

		// Act
		String taggedDocument = documentTagger.tagDocument(c32,
				executionResponseContainer);

		// Assert
		Document taggedDoc = documentXmlConverter.loadDocument(taggedDocument);
		verifyEntryLevelTags(taggedDoc, "d1e1168", Arrays.asList(new String[] { "ENCRYPT", "NODSCLCD", "V" }));
		verifyEntryLevelTags(taggedDoc, "d1e503", Arrays.asList(new String[] { "ENCRYPT", "NODSCLCD", "R" }));
		verifyEntryLevelTags(taggedDoc, "d1e1168", Arrays.asList(new String[] { "ENCRYPT", "NODSCLCD", "V" }));
		verifyEntryLevelTags(taggedDoc, "d1e341", Arrays.asList(new String[] { "ENCRYPT", "NODSCLCD", "V" }));
		verifyEntryLevelTags(taggedDoc, "d1e849", Arrays.asList(new String[] { "ENCRYPT", "NODSCLCD", "V" }));
		verifyEntryLevelTags(taggedDoc, "d1e798", Arrays.asList(new String[] { "ENCRYPT", "NODSCLCD", "V" }));
		verifyEntryLevelTags(taggedDoc, "d1e220", Arrays.asList(new String[] { "ENCRYPT", "NODSCLCD", "V" }));
		verifyEntryLevelTags(taggedDoc, "d1e259", Arrays.asList(new String[] { "ENCRYPT", "NODSCLCD", "R" }));
		verifyEntryLevelTags(taggedDoc, "d1e564", Arrays.asList(new String[] { "ENCRYPT", "NODSCLCD", "V" }));
		verifyEntryLevelTags(taggedDoc, "d1e1261", Arrays.asList(new String[] { "ENCRYPT", "NORDSCLCD", "R" }));
	}

	@Test
	public void testTagDocument_Confidentiality_V() throws Exception {
		// Arrange
		String responseContainer = remExecutionResponseContainer.replace(
				"@itemAction", NO_ACTION);
		String expectedDocumentLevelConfidentiality = V;
		String expectedProblemsSectionLevelConfidentiality = V;

		// Act
		String taggedDocument = documentTagger.tagDocument(remC32,
				responseContainer);

		// Assert
		Document doc = documentXmlConverter.loadDocument(taggedDocument);
		verifyDocumentLevelConfidentiality(doc,
				expectedDocumentLevelConfidentiality);
		// --Problems Section Level
		verifySectionLevelConfidentiality(doc, PROBLEMS_SECTION,
				expectedProblemsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_R() throws Exception {
		// Arrange
		String responseContainer = remExecutionResponseContainer.replace(
				"@itemAction", REDACT);
		String expectedDocumentLevelConfidentiality = R;
		String expectedProblemsSectionLevelConfidentiality = R;

		// Act
		String taggedDocument = documentTagger.tagDocument(remC32,
				responseContainer);

		// Assert
		Document doc = documentXmlConverter.loadDocument(taggedDocument);
		// --Document Level
		verifyDocumentLevelConfidentiality(doc,
				expectedDocumentLevelConfidentiality);
		// --Problems Section Level
		verifySectionLevelConfidentiality(doc, PROBLEMS_SECTION,
				expectedProblemsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_N() throws Exception {
		// Arrange
		String problem1 = REDACT;
		String problem2 = REDACT;
		String allergy1 = REDACT;
		String allergy2 = REDACT;
		String medication1 = REDACT;
		String medication2 = REDACT;
		String result1 = REDACT;
		String result2 = REDACT;

		String expectedDocumentLevelConfidentiality = N;
		String expectedProblemsSectionLevelConfidentiality = N;
		String expectedAllergiesSectionLevelConfidentiality = N;
		String expectedMedicationsSectionLevelConfidentiality = N;
		String expectedResultsSectionLevelConfidentiality = N;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_R_From_Problems()
			throws Exception {
		// Arrange
		String problem1 = NO_ACTION;
		String problem2 = REDACT;
		String allergy1 = REDACT;
		String allergy2 = REDACT;
		String medication1 = REDACT;
		String medication2 = REDACT;
		String result1 = REDACT;
		String result2 = REDACT;

		String expectedDocumentLevelConfidentiality = R;
		String expectedProblemsSectionLevelConfidentiality = R;
		String expectedAllergiesSectionLevelConfidentiality = N;
		String expectedMedicationsSectionLevelConfidentiality = N;
		String expectedResultsSectionLevelConfidentiality = N;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_V_From_Problems1()
			throws Exception {
		// Arrange
		String problem1 = REDACT;
		String problem2 = NO_ACTION;
		String allergy1 = REDACT;
		String allergy2 = REDACT;
		String medication1 = REDACT;
		String medication2 = REDACT;
		String result1 = REDACT;
		String result2 = REDACT;

		String expectedDocumentLevelConfidentiality = V;
		String expectedProblemsSectionLevelConfidentiality = V;
		String expectedAllergiesSectionLevelConfidentiality = N;
		String expectedMedicationsSectionLevelConfidentiality = N;
		String expectedResultsSectionLevelConfidentiality = N;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_V_From_Problems2()
			throws Exception {
		// Arrange
		String problem1 = NO_ACTION;
		String problem2 = NO_ACTION;
		String allergy1 = REDACT;
		String allergy2 = REDACT;
		String medication1 = REDACT;
		String medication2 = REDACT;
		String result1 = REDACT;
		String result2 = REDACT;

		String expectedDocumentLevelConfidentiality = V;
		String expectedProblemsSectionLevelConfidentiality = V;
		String expectedAllergiesSectionLevelConfidentiality = N;
		String expectedMedicationsSectionLevelConfidentiality = N;
		String expectedResultsSectionLevelConfidentiality = N;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_R_From_Allergies()
			throws Exception {
		// Arrange
		String problem1 = REDACT;
		String problem2 = REDACT;
		String allergy1 = NO_ACTION;
		String allergy2 = REDACT;
		String medication1 = REDACT;
		String medication2 = REDACT;
		String result1 = REDACT;
		String result2 = REDACT;

		String expectedDocumentLevelConfidentiality = R;
		String expectedProblemsSectionLevelConfidentiality = N;
		String expectedAllergiesSectionLevelConfidentiality = R;
		String expectedMedicationsSectionLevelConfidentiality = N;
		String expectedResultsSectionLevelConfidentiality = N;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_V_From_Allergies1()
			throws Exception {
		// Arrange
		String problem1 = REDACT;
		String problem2 = REDACT;
		String allergy1 = REDACT;
		String allergy2 = NO_ACTION;
		String medication1 = REDACT;
		String medication2 = REDACT;
		String result1 = REDACT;
		String result2 = REDACT;

		String expectedDocumentLevelConfidentiality = V;
		String expectedProblemsSectionLevelConfidentiality = N;
		String expectedAllergiesSectionLevelConfidentiality = V;
		String expectedMedicationsSectionLevelConfidentiality = N;
		String expectedResultsSectionLevelConfidentiality = N;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_V_From_Allergies2()
			throws Exception {
		// Arrange
		String problem1 = REDACT;
		String problem2 = REDACT;
		String allergy1 = NO_ACTION;
		String allergy2 = NO_ACTION;
		String medication1 = REDACT;
		String medication2 = REDACT;
		String result1 = REDACT;
		String result2 = REDACT;

		String expectedDocumentLevelConfidentiality = V;
		String expectedProblemsSectionLevelConfidentiality = N;
		String expectedAllergiesSectionLevelConfidentiality = V;
		String expectedMedicationsSectionLevelConfidentiality = N;
		String expectedResultsSectionLevelConfidentiality = N;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_R_From_Medications()
			throws Exception {
		// Arrange
		String problem1 = REDACT;
		String problem2 = REDACT;
		String allergy1 = REDACT;
		String allergy2 = REDACT;
		String medication1 = NO_ACTION;
		String medication2 = REDACT;
		String result1 = REDACT;
		String result2 = REDACT;

		String expectedDocumentLevelConfidentiality = R;
		String expectedProblemsSectionLevelConfidentiality = N;
		String expectedAllergiesSectionLevelConfidentiality = N;
		String expectedMedicationsSectionLevelConfidentiality = R;
		String expectedResultsSectionLevelConfidentiality = N;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_V_From_Medications1()
			throws Exception {
		// Arrange
		String problem1 = REDACT;
		String problem2 = REDACT;
		String allergy1 = REDACT;
		String allergy2 = REDACT;
		String medication1 = REDACT;
		String medication2 = NO_ACTION;
		String result1 = REDACT;
		String result2 = REDACT;

		String expectedDocumentLevelConfidentiality = V;
		String expectedProblemsSectionLevelConfidentiality = N;
		String expectedAllergiesSectionLevelConfidentiality = N;
		String expectedMedicationsSectionLevelConfidentiality = V;
		String expectedResultsSectionLevelConfidentiality = N;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_V_From_Medications2()
			throws Exception {
		// Arrange
		String problem1 = REDACT;
		String problem2 = REDACT;
		String allergy1 = REDACT;
		String allergy2 = REDACT;
		String medication1 = NO_ACTION;
		String medication2 = NO_ACTION;
		String result1 = REDACT;
		String result2 = REDACT;

		String expectedDocumentLevelConfidentiality = V;
		String expectedProblemsSectionLevelConfidentiality = N;
		String expectedAllergiesSectionLevelConfidentiality = N;
		String expectedMedicationsSectionLevelConfidentiality = V;
		String expectedResultsSectionLevelConfidentiality = N;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_R_From_Results()
			throws Exception {
		// Arrange
		String problem1 = REDACT;
		String problem2 = REDACT;
		String allergy1 = REDACT;
		String allergy2 = REDACT;
		String medication1 = REDACT;
		String medication2 = REDACT;
		String result1 = NO_ACTION;
		String result2 = REDACT;

		String expectedDocumentLevelConfidentiality = R;
		String expectedProblemsSectionLevelConfidentiality = N;
		String expectedAllergiesSectionLevelConfidentiality = N;
		String expectedMedicationsSectionLevelConfidentiality = N;
		String expectedResultsSectionLevelConfidentiality = R;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_V_From_Results1()
			throws Exception {
		// Arrange
		String problem1 = REDACT;
		String problem2 = REDACT;
		String allergy1 = REDACT;
		String allergy2 = REDACT;
		String medication1 = REDACT;
		String medication2 = REDACT;
		String result1 = REDACT;
		String result2 = NO_ACTION;

		String expectedDocumentLevelConfidentiality = V;
		String expectedProblemsSectionLevelConfidentiality = N;
		String expectedAllergiesSectionLevelConfidentiality = N;
		String expectedMedicationsSectionLevelConfidentiality = N;
		String expectedResultsSectionLevelConfidentiality = V;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_V_From_Results2()
			throws Exception {
		// Arrange
		String problem1 = REDACT;
		String problem2 = REDACT;
		String allergy1 = REDACT;
		String allergy2 = REDACT;
		String medication1 = REDACT;
		String medication2 = REDACT;
		String result1 = NO_ACTION;
		String result2 = NO_ACTION;

		String expectedDocumentLevelConfidentiality = V;
		String expectedProblemsSectionLevelConfidentiality = N;
		String expectedAllergiesSectionLevelConfidentiality = N;
		String expectedMedicationsSectionLevelConfidentiality = N;
		String expectedResultsSectionLevelConfidentiality = V;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_V_Allergy_Overrides_Problem()
			throws Exception {
		// Arrange
		String problem1 = NO_ACTION;
		String problem2 = REDACT;
		String allergy1 = REDACT;
		String allergy2 = NO_ACTION;
		String medication1 = REDACT;
		String medication2 = REDACT;
		String result1 = REDACT;
		String result2 = REDACT;

		String expectedDocumentLevelConfidentiality = V;
		String expectedProblemsSectionLevelConfidentiality = R;
		String expectedAllergiesSectionLevelConfidentiality = V;
		String expectedMedicationsSectionLevelConfidentiality = N;
		String expectedResultsSectionLevelConfidentiality = N;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_V_Allergy_Overrides_Medication()
			throws Exception {
		// Arrange
		String problem1 = REDACT;
		String problem2 = REDACT;
		String allergy1 = REDACT;
		String allergy2 = NO_ACTION;
		String medication1 = NO_ACTION;
		String medication2 = REDACT;
		String result1 = REDACT;
		String result2 = REDACT;

		String expectedDocumentLevelConfidentiality = V;
		String expectedProblemsSectionLevelConfidentiality = N;
		String expectedAllergiesSectionLevelConfidentiality = V;
		String expectedMedicationsSectionLevelConfidentiality = R;
		String expectedResultsSectionLevelConfidentiality = N;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_V_Allergy_Overrides_Result()
			throws Exception {
		// Arrange
		String problem1 = REDACT;
		String problem2 = REDACT;
		String allergy1 = REDACT;
		String allergy2 = NO_ACTION;
		String medication1 = REDACT;
		String medication2 = REDACT;
		String result1 = NO_ACTION;
		String result2 = REDACT;

		String expectedDocumentLevelConfidentiality = V;
		String expectedProblemsSectionLevelConfidentiality = N;
		String expectedAllergiesSectionLevelConfidentiality = V;
		String expectedMedicationsSectionLevelConfidentiality = N;
		String expectedResultsSectionLevelConfidentiality = R;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_V_Problem_Overrides_Allergy()
			throws Exception {
		// Arrange
		String problem1 = REDACT;
		String problem2 = NO_ACTION;
		String allergy1 = NO_ACTION;
		String allergy2 = REDACT;
		String medication1 = REDACT;
		String medication2 = REDACT;
		String result1 = REDACT;
		String result2 = REDACT;

		String expectedDocumentLevelConfidentiality = V;
		String expectedProblemsSectionLevelConfidentiality = V;
		String expectedAllergiesSectionLevelConfidentiality = R;
		String expectedMedicationsSectionLevelConfidentiality = N;
		String expectedResultsSectionLevelConfidentiality = N;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_V_Problem_Overrides_Medication()
			throws Exception {
		// Arrange
		String problem1 = REDACT;
		String problem2 = NO_ACTION;
		String allergy1 = REDACT;
		String allergy2 = REDACT;
		String medication1 = NO_ACTION;
		String medication2 = REDACT;
		String result1 = REDACT;
		String result2 = REDACT;

		String expectedDocumentLevelConfidentiality = V;
		String expectedProblemsSectionLevelConfidentiality = V;
		String expectedAllergiesSectionLevelConfidentiality = N;
		String expectedMedicationsSectionLevelConfidentiality = R;
		String expectedResultsSectionLevelConfidentiality = N;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_V_Problem_Overrides_Result()
			throws Exception {
		// Arrange
		String problem1 = REDACT;
		String problem2 = NO_ACTION;
		String allergy1 = REDACT;
		String allergy2 = REDACT;
		String medication1 = REDACT;
		String medication2 = REDACT;
		String result1 = NO_ACTION;
		String result2 = REDACT;

		String expectedDocumentLevelConfidentiality = V;
		String expectedProblemsSectionLevelConfidentiality = V;
		String expectedAllergiesSectionLevelConfidentiality = N;
		String expectedMedicationsSectionLevelConfidentiality = N;
		String expectedResultsSectionLevelConfidentiality = R;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_V_Medication_Overrides_Problem()
			throws Exception {
		// Arrange
		String problem1 = NO_ACTION;
		String problem2 = REDACT;
		String allergy1 = REDACT;
		String allergy2 = REDACT;
		String medication1 = REDACT;
		String medication2 = NO_ACTION;
		String result1 = REDACT;
		String result2 = REDACT;

		String expectedDocumentLevelConfidentiality = V;
		String expectedProblemsSectionLevelConfidentiality = R;
		String expectedAllergiesSectionLevelConfidentiality = N;
		String expectedMedicationsSectionLevelConfidentiality = V;
		String expectedResultsSectionLevelConfidentiality = N;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_V_Medication_Overrides_Allergy()
			throws Exception {
		// Arrange
		String problem1 = REDACT;
		String problem2 = REDACT;
		String allergy1 = NO_ACTION;
		String allergy2 = REDACT;
		String medication1 = REDACT;
		String medication2 = NO_ACTION;
		String result1 = REDACT;
		String result2 = REDACT;

		String expectedDocumentLevelConfidentiality = V;
		String expectedProblemsSectionLevelConfidentiality = N;
		String expectedAllergiesSectionLevelConfidentiality = R;
		String expectedMedicationsSectionLevelConfidentiality = V;
		String expectedResultsSectionLevelConfidentiality = N;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_V_Medication_Overrides_Result()
			throws Exception {
		// Arrange
		String problem1 = REDACT;
		String problem2 = REDACT;
		String allergy1 = REDACT;
		String allergy2 = REDACT;
		String medication1 = REDACT;
		String medication2 = NO_ACTION;
		String result1 = NO_ACTION;
		String result2 = REDACT;

		String expectedDocumentLevelConfidentiality = V;
		String expectedProblemsSectionLevelConfidentiality = N;
		String expectedAllergiesSectionLevelConfidentiality = N;
		String expectedMedicationsSectionLevelConfidentiality = V;
		String expectedResultsSectionLevelConfidentiality = R;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_V_Result_Overrides_Problem()
			throws Exception {
		// Arrange
		String problem1 = NO_ACTION;
		String problem2 = REDACT;
		String allergy1 = REDACT;
		String allergy2 = REDACT;
		String medication1 = REDACT;
		String medication2 = REDACT;
		String result1 = REDACT;
		String result2 = NO_ACTION;

		String expectedDocumentLevelConfidentiality = V;
		String expectedProblemsSectionLevelConfidentiality = R;
		String expectedAllergiesSectionLevelConfidentiality = N;
		String expectedMedicationsSectionLevelConfidentiality = N;
		String expectedResultsSectionLevelConfidentiality = V;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_V_Result_Overrides_Allergy()
			throws Exception {
		// Arrange
		String problem1 = REDACT;
		String problem2 = REDACT;
		String allergy1 = NO_ACTION;
		String allergy2 = REDACT;
		String medication1 = REDACT;
		String medication2 = REDACT;
		String result1 = REDACT;
		String result2 = NO_ACTION;

		String expectedDocumentLevelConfidentiality = V;
		String expectedProblemsSectionLevelConfidentiality = N;
		String expectedAllergiesSectionLevelConfidentiality = R;
		String expectedMedicationsSectionLevelConfidentiality = N;
		String expectedResultsSectionLevelConfidentiality = V;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	@Test
	public void testTagDocument_Confidentiality_V_Result_Overrides_Medication()
			throws Exception {
		// Arrange
		String problem1 = REDACT;
		String problem2 = REDACT;
		String allergy1 = REDACT;
		String allergy2 = REDACT;
		String medication1 = NO_ACTION;
		String medication2 = REDACT;
		String result1 = REDACT;
		String result2 = NO_ACTION;

		String expectedDocumentLevelConfidentiality = V;
		String expectedProblemsSectionLevelConfidentiality = N;
		String expectedAllergiesSectionLevelConfidentiality = N;
		String expectedMedicationsSectionLevelConfidentiality = R;
		String expectedResultsSectionLevelConfidentiality = V;

		// Act and Assert
		testDocumentAndSectionLevelConfidentiality(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2,
				expectedDocumentLevelConfidentiality,
				expectedProblemsSectionLevelConfidentiality,
				expectedAllergiesSectionLevelConfidentiality,
				expectedMedicationsSectionLevelConfidentiality,
				expectedResultsSectionLevelConfidentiality);
	}

	private String setResponseContainer(String problem1, String problem2,
			String allergy1, String allergy2, String medication1,
			String medication2, String result1, String result2) {
		String responseContainer = testRuleExecutionResponseContainer_xml
				.replace("@Problem1", problem1).replace("@Problem2", problem2)
				.replace("@Allergy1", allergy1).replace("@Allergy2", allergy2)
				.replace("@Medication1", medication1)
				.replace("@Medication2", medication2)
				.replace("@Result1", result1).replace("@Result2", result2);
		return responseContainer;
	}

	private void verifySectionLevelConfidentiality(Document doc,
			String sectionCode, String expectedSectionLevelConfidentiality)
			throws XPathExpressionException {
		String xPathExprProblemsSectionLevel = "//hl7:section[hl7:code[@code='$']]/hl7:confidentialityCode"
				.replace("$", sectionCode);
		verifyCodeAttributeValue(doc, xPathExprProblemsSectionLevel,
				expectedSectionLevelConfidentiality);
	}

	private void verifyDocumentLevelConfidentiality(Document doc,
			String expectedDocumentLevelConfidentiality)
			throws XPathExpressionException {
		String xPathExprDocumentLevel = "/hl7:ClinicalDocument/hl7:confidentialityCode";
		verifyCodeAttributeValue(doc, xPathExprDocumentLevel,
				expectedDocumentLevelConfidentiality);
	}

	@Test(expected = DS4PException.class)
	public void testTagDocument_Throws_DS4PException() {
		// Empty xml file
		@SuppressWarnings("unused")
		String taggedDocument = documentTagger.tagDocument("",
				executionResponseContainer);
	}

	private Node getNode(Document xmlDocument, String xPathExpr)
			throws XPathExpressionException {
		// Create XPath instance
		XPathFactory xpathFact = XPathFactory.newInstance();
		XPath xpath = xpathFact.newXPath();
		xpath.setNamespaceContext(new PepNamespaceContext());

		// Evaluate XPath expression against parsed document
		Node node = (Node) xpath.evaluate(xPathExpr, xmlDocument,
				XPathConstants.NODE);

		return node;
	}

	private NodeList getNodeList(Document xmlDocument, String xPathExpr)
			throws XPathExpressionException {
		// Create XPath instance
		XPathFactory xpathFact = XPathFactory.newInstance();
		XPath xpath = xpathFact.newXPath();
		xpath.setNamespaceContext(new PepNamespaceContext());

		// Evaluate XPath expression against parsed document
		NodeList nodeList = (NodeList) xpath.evaluate(xPathExpr, xmlDocument,
				XPathConstants.NODESET);

		return nodeList;
	}

	private void verifyCodeAttributeValue(Document document, String xPathExpr,
			String expectedCodeAttributeValue) throws XPathExpressionException {
		Node documentLevelConfidentialityCodeNode = getNode(document, xPathExpr);
		String documentLevelConfidentialityCodeValue = documentLevelConfidentialityCodeNode
				.getAttributes().getNamedItem("code").getNodeValue();
		assertEquals(expectedCodeAttributeValue,
				documentLevelConfidentialityCodeValue);
	}

	private void testDocumentAndSectionLevelConfidentiality(String problem1,
			String problem2, String allergy1, String allergy2,
			String medication1, String medication2, String result1,
			String result2, String expectedDocumentLevelConfidentiality,
			String expectedProblemsSectionLevelConfidentiality,
			String expectedAllergiesSectionLevelConfidentiality,
			String expectedMedicationsSectionLevelConfidentiality,
			String expectedResultsSectionLevelConfidentiality)
			throws Exception, XPathExpressionException {
		String responseContainer = setResponseContainer(problem1, problem2,
				allergy1, allergy2, medication1, medication2, result1, result2);

		// Act
		String taggedDocument = documentTagger.tagDocument(robustC32,
				responseContainer);

		// Assert
		Document doc = documentXmlConverter.loadDocument(taggedDocument);
		verifyDocumentLevelConfidentiality(doc,
				expectedDocumentLevelConfidentiality);
		verifySectionLevelConfidentiality(doc, PROBLEMS_SECTION,
				expectedProblemsSectionLevelConfidentiality);
		verifySectionLevelConfidentiality(doc, ALLERGIES_SECTION,
				expectedAllergiesSectionLevelConfidentiality);
		verifySectionLevelConfidentiality(doc, MEDICATIONS_SECTION,
				expectedMedicationsSectionLevelConfidentiality);
		verifySectionLevelConfidentiality(doc, RESULTS_SECTION,
				expectedResultsSectionLevelConfidentiality);
	}

	private void verifyEntryLevelTags(Document taggedDoc,
			String generatedEntryId, List<String> expectedList)
			throws XPathExpressionException, XMLEncryptionException, Exception {
		String xPathExpr = "//hl7:entry[child::hl7:generatedEntryId='$generatedEntryId']//hl7:entryRelationship[descendant::hl7:templateId[@root='2.16.840.1.113883.3.3251.1.4']]//hl7:value/@code";
		String xPathExprForOrganizer = "//hl7:entry[child::hl7:generatedEntryId='$generatedEntryId']//hl7:component[child::hl7:organizer[child::hl7:templateId[@root='2.16.840.1.113883.3.3251.1.4']]]//hl7:value/@code";
		LinkedList<String> solutionList = new LinkedList<String>();

		// Add everything except organizer
		NodeList nodeList = getNodeList(taggedDoc,
				xPathExpr.replace("$generatedEntryId", generatedEntryId));
		addNodeListToSolutionList(solutionList, nodeList);

		// Add organizer
		nodeList = getNodeList(taggedDoc, xPathExprForOrganizer.replace(
				"$generatedEntryId", generatedEntryId));
		addNodeListToSolutionList(solutionList, nodeList);

		// Assert
		assertTrue(solutionList.containsAll(expectedList));
	}

	private void addNodeListToSolutionList(List<String> solutionList,
			NodeList nodeList) {
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			solutionList.add(node.getNodeValue());
		}
	}
	
	private String removeEmbeddedClinicalDocument(String factModelXml)
			throws Exception, XPathExpressionException, IOException,
			TransformerException {
		Document fmDoc = documentXmlConverter.loadDocument(factModelXml);
		Node ecd = documentAccessor.getNode(fmDoc,
				"//hl7:EmbeddedClinicalDocument");
		ecd.getParentNode().removeChild(ecd);
		factModelXml = documentXmlConverter.convertXmlDocToString(fmDoc);
		return factModelXml;
	}
}
