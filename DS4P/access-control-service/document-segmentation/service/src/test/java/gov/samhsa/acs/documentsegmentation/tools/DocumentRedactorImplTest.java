package gov.samhsa.acs.documentsegmentation.tools;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.brms.domain.ClinicalFact;
import gov.samhsa.acs.brms.domain.Confidentiality;
import gov.samhsa.acs.brms.domain.FactModel;
import gov.samhsa.acs.brms.domain.ObligationPolicyDocument;
import gov.samhsa.acs.brms.domain.RefrainPolicy;
import gov.samhsa.acs.brms.domain.RuleExecutionContainer;
import gov.samhsa.acs.brms.domain.RuleExecutionResponse;
import gov.samhsa.acs.brms.domain.Sensitivity;
import gov.samhsa.acs.brms.domain.UsPrivacyLaw;
import gov.samhsa.acs.brms.domain.XacmlResult;
import gov.samhsa.acs.common.dto.XacmlRequest;
import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.tool.DocumentAccessorImpl;
import gov.samhsa.acs.common.tool.DocumentXmlConverterImpl;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.documentsegmentation.tools.DocumentEditorImpl;
import gov.samhsa.acs.documentsegmentation.tools.DocumentRedactorImpl;
import gov.samhsa.acs.documentsegmentation.tools.MetadataGeneratorImpl;
import gov.samhsa.acs.documentsegmentation.valueset.ValueSetService;
import gov.samhsa.acs.documentsegmentation.valueset.ValueSetServiceImplMock;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DocumentRedactorImplTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String PROBLEMS = "@Problems";
	private static final String ALLERGIES = "@Allergies";
	private static final String MEDICATIONS = "@Medications";
	private static final String RESULTS = "@Results";
	private static final String HIV = "HIV";
	private static final String HIV_SELECTED = "HIV";
	private static final String PSY = "PSY";
	private static final String PSY_SELECTED = "Psychiatric Information";
	private static final String ETH = "ETH";
	private static final String ETH_SELECTED = "Drug Abuse";
	private static final String GDIS = "GDIS";
	private static final String SDV = "SDV";
	private static final String SEX = "SEX";
	private static final String STD = "STD";

	private static final String PROBLEMS_SECTION = "11450-4";
	private static final String ALLERGIES_SECTION = "48765-2";
	private static final String MEDICATIONS_SECTION = "10160-0";
	private static final String RESULTS_SECTION = "30954-2";
	private static final String NOT_SELECTED = "";

	private static final String EMPTY_RULE_EXECUTION_RESPONSE_CONTAINER = "<ruleExecutionContainer><executionResponseList></executionResponseList></ruleExecutionContainer>";
	private static final String MOCK_XACML_RESULT = "<xacmlResult><pdpDecision>Permit</pdpDecision><purposeOfUse>TREAT</purposeOfUse><messageId>4617a579-1881-4e40-9f98-f85bd81d6502</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId><pdpObligation>Drug Abuse</pdpObligation><pdpObligation>Psychiatric Information</pdpObligation><pdpObligation>HIV</pdpObligation></xacmlResult>";

	private static SimpleMarshallerImpl marshaller;
	private static FileReaderImpl fileReader;
	private static DocumentEditorImpl documentEditor;
	private static DocumentXmlConverterImpl documentXmlConverter;
	private static DocumentAccessorImpl documentAccessor;
	private static DocumentAccessorImpl documentAccessorMock;
	private static DocumentFactModelExtractorImpl factModelExtractor;
	private static EmbeddedClinicalDocumentExtractorImpl embeddedClinicalDocumentExtractor;
	private static ValueSetService valueSetService;
	private static DocumentXmlConverterImpl documentXmlConverterSpy;

	private static XacmlResult xacmlResultMock;

	private String c32;
	private String robustC32;
	private String xacmlResult;
	private Document c32Document;
	private RuleExecutionContainer ruleExecutionContainer;
	private RuleExecutionContainer ruleExecutionContainerWithHivEth;

	private static DocumentRedactor documentRedactor;

	@Before
	public void setUp() throws Exception {
		// Arrange
		factModelExtractor = new DocumentFactModelExtractorImpl();
		fileReader = new FileReaderImpl();
		marshaller = new SimpleMarshallerImpl();
		documentXmlConverter = new DocumentXmlConverterImpl();
		documentEditor = new DocumentEditorImpl(new MetadataGeneratorImpl(),
				fileReader, documentXmlConverter);
		documentAccessor = new DocumentAccessorImpl();
		documentAccessorMock = mock(DocumentAccessorImpl.class);
		valueSetService = new ValueSetServiceImplMock(fileReader);
		embeddedClinicalDocumentExtractor = new EmbeddedClinicalDocumentExtractorImpl(
				documentXmlConverter, documentAccessor);

		ruleExecutionContainer = setRuleExecutionContainer();
		ruleExecutionContainerWithHivEth = marshaller.unmarshallFromXml(
				RuleExecutionContainer.class,
				fileReader.readFile("ruleExecutionResponseContainer.xml"));
		xacmlResultMock = setMockXacmlResult(MOCK_XACML_RESULT);
		c32 = fileReader.readFile("sampleC32/c32.xml");
		robustC32 = fileReader
				.readFile("testMU_Rev3_HITSP_C32C83_4Sections_RobustEntries_NoErrors.xml");
		xacmlResult = fileReader.readFile("testXacmlResult.xml");

		documentXmlConverterSpy = setSpyDocumentXmlConverter();

		documentRedactor = new DocumentRedactorImpl(documentXmlConverterSpy,
				documentAccessorMock);
	}

	@Test
	public void testRedactDocument() throws Throwable {
		// Act
		initDocumentRedactorWithActualServices();
		String factModelXml = factModelExtractor.extractFactModel(c32,
				MOCK_XACML_RESULT);
		c32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		FactModel factModel = marshaller.unmarshallFromXml(FactModel.class,
				factModelXml);
		setValueSetCategories(factModel);
		factModel.setXacmlResult(xacmlResultMock);
		String result = documentRedactor.redactDocument(c32,
				ruleExecutionContainer, factModel).getRedactedDocument();
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
	public void testRedactDocument_Problems_Section_Redacted() throws Throwable {
		// Arrange
		String problems = PROBLEMS_SECTION;
		String allergies = NOT_SELECTED;
		String medications = NOT_SELECTED;
		String results = NOT_SELECTED;
		String hiv = NOT_SELECTED;
		String psy = NOT_SELECTED;
		String eth = NOT_SELECTED;
		String gdis = NOT_SELECTED;
		String sdv = NOT_SELECTED;
		String sex = NOT_SELECTED;
		String std = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results, hiv, psy, eth, gdis, sdv, sex, std);
		String factModelXml = factModelExtractor.extractFactModel(robustC32,
				marshaller.marshall(xacmlResultObj));
		robustC32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		FactModel factModel = marshaller.unmarshallFromXml(FactModel.class,
				factModelXml);
		setValueSetCategories(factModel);
		factModel.setXacmlResult(xacmlResultObj);

		// Act
		String redactedC32 = documentRedactor.redactDocument(robustC32,
				ruleExecutionContainer, factModel).getRedactedDocument();

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
			throws Throwable {
		// Arrange
		String problems = NOT_SELECTED;
		String allergies = ALLERGIES_SECTION;
		String medications = NOT_SELECTED;
		String results = NOT_SELECTED;
		String hiv = NOT_SELECTED;
		String psy = NOT_SELECTED;
		String eth = NOT_SELECTED;
		String gdis = NOT_SELECTED;
		String sdv = NOT_SELECTED;
		String sex = NOT_SELECTED;
		String std = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results, hiv, psy, eth, gdis, sdv, sex, std);
		String factModelXml = factModelExtractor.extractFactModel(robustC32,
				marshaller.marshall(xacmlResultObj));
		robustC32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		FactModel factModel = marshaller.unmarshallFromXml(FactModel.class,
				factModelXml);
		setValueSetCategories(factModel);
		factModel.setXacmlResult(xacmlResultObj);

		// Act
		String redactedC32 = documentRedactor.redactDocument(robustC32,
				ruleExecutionContainer, factModel).getRedactedDocument();

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
			throws Throwable {
		// Arrange
		String problems = NOT_SELECTED;
		String allergies = NOT_SELECTED;
		String medications = MEDICATIONS_SECTION;
		String results = NOT_SELECTED;
		String hiv = NOT_SELECTED;
		String psy = NOT_SELECTED;
		String eth = NOT_SELECTED;
		String gdis = NOT_SELECTED;
		String sdv = NOT_SELECTED;
		String sex = NOT_SELECTED;
		String std = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results, hiv, psy, eth, gdis, sdv, sex, std);
		String factModelXml = factModelExtractor.extractFactModel(robustC32,
				marshaller.marshall(xacmlResultObj));
		robustC32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		FactModel factModel = marshaller.unmarshallFromXml(FactModel.class,
				factModelXml);
		setValueSetCategories(factModel);
		factModel.setXacmlResult(xacmlResultObj);

		// Act
		String redactedC32 = documentRedactor.redactDocument(robustC32,
				ruleExecutionContainer, factModel).getRedactedDocument();

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
	public void testRedactDocument_Results_Section_Redacted() throws Throwable {
		// Arrange
		String problems = NOT_SELECTED;
		String allergies = NOT_SELECTED;
		String medications = NOT_SELECTED;
		String results = RESULTS_SECTION;
		String hiv = NOT_SELECTED;
		String psy = NOT_SELECTED;
		String eth = NOT_SELECTED;
		String gdis = NOT_SELECTED;
		String sdv = NOT_SELECTED;
		String sex = NOT_SELECTED;
		String std = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results, hiv, psy, eth, gdis, sdv, sex, std);
		String factModelXml = factModelExtractor.extractFactModel(robustC32,
				marshaller.marshall(xacmlResultObj));
		robustC32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		FactModel factModel = marshaller.unmarshallFromXml(FactModel.class,
				factModelXml);
		setValueSetCategories(factModel);
		factModel.setXacmlResult(xacmlResultObj);

		// Act
		String redactedC32 = documentRedactor.redactDocument(robustC32,
				ruleExecutionContainer, factModel).getRedactedDocument();

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
			throws Throwable {
		// Arrange
		String problems = PROBLEMS_SECTION;
		String allergies = ALLERGIES_SECTION;
		String medications = NOT_SELECTED;
		String results = NOT_SELECTED;
		String hiv = NOT_SELECTED;
		String psy = NOT_SELECTED;
		String eth = NOT_SELECTED;
		String gdis = NOT_SELECTED;
		String sdv = NOT_SELECTED;
		String sex = NOT_SELECTED;
		String std = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results, hiv, psy, eth, gdis, sdv, sex, std);
		String factModelXml = factModelExtractor.extractFactModel(robustC32,
				marshaller.marshall(xacmlResultObj));
		robustC32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		FactModel factModel = marshaller.unmarshallFromXml(FactModel.class,
				factModelXml);
		setValueSetCategories(factModel);
		factModel.setXacmlResult(xacmlResultObj);

		// Act
		String redactedC32 = documentRedactor.redactDocument(robustC32,
				ruleExecutionContainer, factModel).getRedactedDocument();

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
			throws Throwable {
		// Arrange
		String problems = PROBLEMS_SECTION;
		String allergies = NOT_SELECTED;
		String medications = MEDICATIONS_SECTION;
		String results = NOT_SELECTED;
		String hiv = NOT_SELECTED;
		String psy = NOT_SELECTED;
		String eth = NOT_SELECTED;
		String gdis = NOT_SELECTED;
		String sdv = NOT_SELECTED;
		String sex = NOT_SELECTED;
		String std = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results, hiv, psy, eth, gdis, sdv, sex, std);
		String factModelXml = factModelExtractor.extractFactModel(robustC32,
				marshaller.marshall(xacmlResultObj));
		robustC32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		FactModel factModel = marshaller.unmarshallFromXml(FactModel.class,
				factModelXml);
		setValueSetCategories(factModel);
		factModel.setXacmlResult(xacmlResultObj);

		// Act
		String redactedC32 = documentRedactor.redactDocument(robustC32,
				ruleExecutionContainer, factModel).getRedactedDocument();

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
			throws Throwable {
		// Arrange
		String problems = PROBLEMS_SECTION;
		String allergies = NOT_SELECTED;
		String medications = NOT_SELECTED;
		String results = RESULTS_SECTION;
		String hiv = NOT_SELECTED;
		String psy = NOT_SELECTED;
		String eth = NOT_SELECTED;
		String gdis = NOT_SELECTED;
		String sdv = NOT_SELECTED;
		String sex = NOT_SELECTED;
		String std = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results, hiv, psy, eth, gdis, sdv, sex, std);
		String factModelXml = factModelExtractor.extractFactModel(robustC32,
				marshaller.marshall(xacmlResultObj));
		robustC32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		FactModel factModel = marshaller.unmarshallFromXml(FactModel.class,
				factModelXml);
		setValueSetCategories(factModel);
		factModel.setXacmlResult(xacmlResultObj);

		// Act
		String redactedC32 = documentRedactor.redactDocument(robustC32,
				ruleExecutionContainer, factModel).getRedactedDocument();

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
			throws Throwable {
		// Arrange
		String problems = NOT_SELECTED;
		String allergies = ALLERGIES_SECTION;
		String medications = MEDICATIONS_SECTION;
		String results = RESULTS_SECTION;
		String hiv = NOT_SELECTED;
		String psy = NOT_SELECTED;
		String eth = NOT_SELECTED;
		String gdis = NOT_SELECTED;
		String sdv = NOT_SELECTED;
		String sex = NOT_SELECTED;
		String std = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results, hiv, psy, eth, gdis, sdv, sex, std);
		String factModelXml = factModelExtractor.extractFactModel(robustC32,
				marshaller.marshall(xacmlResultObj));
		robustC32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		FactModel factModel = marshaller.unmarshallFromXml(FactModel.class,
				factModelXml);
		setValueSetCategories(factModel);
		factModel.setXacmlResult(xacmlResultObj);

		// Act
		String redactedC32 = documentRedactor.redactDocument(robustC32,
				ruleExecutionContainer, factModel).getRedactedDocument();

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
			throws Throwable {
		// Arrange
		String problems = NOT_SELECTED;
		String allergies = ALLERGIES_SECTION;
		String medications = MEDICATIONS_SECTION;
		String results = RESULTS_SECTION;
		String hiv = NOT_SELECTED;
		String psy = NOT_SELECTED;
		String eth = NOT_SELECTED;
		String gdis = NOT_SELECTED;
		String sdv = NOT_SELECTED;
		String sex = NOT_SELECTED;
		String std = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		RuleExecutionContainer ruleExecutionContainer = initRuleExecutionContainer();
		XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results, hiv, psy, eth, gdis, sdv, sex, std);
		String ethObservationId = "e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7";
		String hivObservationId = "d11275e7-67ae-11db-bd13-0800200c9a66";
		String factModelXml = factModelExtractor.extractFactModel(c32,
				marshaller.marshall(xacmlResultObj));
		c32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		FactModel factModel = marshaller.unmarshallFromXml(FactModel.class,
				factModelXml);
		setValueSetCategories(factModel);
		factModel.setXacmlResult(xacmlResultObj);

		// Act
		String redactedC32 = documentRedactor.redactDocument(c32,
				ruleExecutionContainer, factModel).getRedactedDocument();

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
	public void testCleanUpGeneratedEntryIds() throws Exception{
		// Arrange
		initDocumentRedactorWithActualServices();
		String c32WithGeneratedEntryIds = fileReader.readFile("testC32WithGeneratedEntryIds.xml");
		
		// Act
		String cleanC32 = documentRedactor.cleanUpGeneratedEntryIds(c32WithGeneratedEntryIds);
		
		// Assert
		Document docWithGeneratedEntryIds = documentXmlConverter.loadDocument(c32WithGeneratedEntryIds);
		Document docCleanedUp = documentXmlConverter.loadDocument(cleanC32);		
		String xPathExpr = "//hl7:generatedEntryId";
		NodeList foundGeneratedEntryIds = documentAccessor.getNodeList(docWithGeneratedEntryIds, xPathExpr);
		// original document must have entry ids
		assertTrue(foundGeneratedEntryIds.getLength()>0);
		foundGeneratedEntryIds = documentAccessor.getNodeList(docCleanedUp, xPathExpr);
		// clean document shouldn't have any entry id elements
		assertTrue(foundGeneratedEntryIds.getLength()==0);		
	}
		
	@SuppressWarnings("unchecked")
	@Test(expected = DS4PException.class)
	public void testCleanUpGeneratedEntryIds_Throws_XPathExpressionException() throws Exception{
		// Arrange
		when(documentAccessorMock.getNodeList(isA(Document.class), eq("//hl7:generatedEntryId"))).thenThrow(XPathExpressionException.class);
		
		// Act
		documentRedactor.cleanUpGeneratedEntryIds("someString");	
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = DS4PException.class)
	public void testCleanUpGeneratedEntryIds_Throws_Exception() throws Exception{
		// Arrange
		String c32WithGeneratedEntryIds = "someDocument";
		when(documentXmlConverterSpy.loadDocument(c32WithGeneratedEntryIds)).thenThrow(Exception.class);
		
		// Act
		documentRedactor.cleanUpGeneratedEntryIds(c32WithGeneratedEntryIds);	
	}
	
	@Test(expected = DS4PException.class)
	public void testCleanUpGeneratedEntryIds_Throws_Exception2() throws Exception{
		// Arrange
		String c32WithGeneratedEntryIds = "someDocument";
		Document documentMock = mock(Document.class);
		NodeList nodeListMock = mock(NodeList.class);
		when(documentXmlConverterSpy.loadDocument(c32WithGeneratedEntryIds)).thenReturn(documentMock);
		when(documentAccessorMock.getNodeList(documentMock, "//hl7:generatedEntryId")).thenReturn(nodeListMock);
		when(nodeListMock.getLength()).thenReturn(0);
		doThrow(Exception.class).when(documentXmlConverterSpy).convertXmlDocToString(documentMock);
		
		// Act
		documentRedactor.cleanUpGeneratedEntryIds(c32WithGeneratedEntryIds);	
	}

	@Test
	public void testRedactDocument_Allergies_Medications_Results_Sections_Hiv_Eth_Redacted()
			throws Throwable {
		// Arrange
		String problems = NOT_SELECTED;
		String allergies = ALLERGIES_SECTION;
		String medications = MEDICATIONS_SECTION;
		String results = RESULTS_SECTION;
		String hiv = HIV_SELECTED;
		String psy = NOT_SELECTED;
		String eth = ETH_SELECTED;
		String gdis = NOT_SELECTED;
		String sdv = NOT_SELECTED;
		String sex = NOT_SELECTED;
		String std = NOT_SELECTED;
		initDocumentRedactorWithActualServices();
		RuleExecutionContainer ruleExecutionContainer = ruleExecutionContainerWithHivEth;
		XacmlResult xacmlResultObj = initXacmlResult(problems, allergies,
				medications, results, hiv, psy, eth, gdis, sdv, sex, std);
		String factModelXml = factModelExtractor.extractFactModel(c32,
				marshaller.marshall(xacmlResultObj));
		c32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		FactModel factModel = marshaller.unmarshallFromXml(FactModel.class,
				factModelXml);
		setValueSetCategories(factModel);
		String ethObservationId = "e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7";
		String hivObservationId = "d11275e7-67ae-11db-bd13-0800200c9a66";
		factModel.setXacmlResult(xacmlResultObj);

		// Act
		String redactedC32 = documentRedactor.redactDocument(c32,
				ruleExecutionContainer, factModel).getRedactedDocument();

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
	public void testRedactDocument_RemC32() throws Throwable {
		// Arrange
		initDocumentRedactorWithActualServices();
		String remC32 = fileReader.readFile("testRemC32.xml");
		String remRuleExecutionContainerActual = fileReader
				.readFile("testRemRuleExecutionContainerActual.xml");
		RuleExecutionContainer remRuleExecutionContainerActualObj = marshaller
				.unmarshallFromXml(RuleExecutionContainer.class,
						remRuleExecutionContainerActual);
		String remXacmlResult = fileReader.readFile("testRemXacmlResult.xml");
		XacmlResult remXacmlResultObj = marshaller.unmarshallFromXml(
				XacmlResult.class, remXacmlResult);
		String factModelXml = factModelExtractor.extractFactModel(remC32,
				marshaller.marshall(remXacmlResultObj));
		FactModel factModel = marshaller.unmarshallFromXml(FactModel.class,
				factModelXml);
		setValueSetCategories(factModel);
		remC32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		String ethObservationId = "d17e379";
		String hivObservationId = "d17e356";
		factModel.setXacmlResult(xacmlResultMock);

		// Act
		String redactedC32 = documentRedactor.redactDocument(remC32,
				remRuleExecutionContainerActualObj, factModel).getRedactedDocument();

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
		assertTrue(getHumanReadableTextNodeList(remC32, "substance abuse")
				.getLength() > 0);
		assertTrue(getHumanReadableTextNodeList(remC32, "hiv").getLength() > 0);
		assertTrue(getHumanReadableTextNodeList(redactedC32, "substance abuse")
				.getLength() == 0);
		assertTrue(getHumanReadableTextNodeList(redactedC32, "hiv").getLength() == 0);
	}

	@Test(expected = DS4PException.class)
	public void testRedactDocument_Throws_DS4PException() {
		@SuppressWarnings("unused")
		String result = documentRedactor.redactDocument("", null, new FactModel()).getRedactedDocument();
	}

	// Sensitivity in container doesn't mean anything anymore.
	@Test
	public void testRedactDocument_WrongSensitivityInContainer()
			throws Exception {
		// Arrange
		initDocumentRedactorWithActualServices();
		String xacmlResultWithWrongObligations = MOCK_XACML_RESULT.replace(
				"<pdpObligation>Drug Abuse</pdpObligation>", "").replace(
				"<pdpObligation>HIV</pdpObligation>", "");
		XacmlResult xacmlResult = setMockXacmlResult(xacmlResultWithWrongObligations);
		String factModelXml = factModelExtractor.extractFactModel(c32,
				xacmlResultWithWrongObligations);
		c32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		FactModel factModel = marshaller.unmarshallFromXml(FactModel.class,
				factModelXml);
		setValueSetCategories(factModel);
		factModel.setXacmlResult(xacmlResult);

		// Act
		String result = documentRedactor.redactDocument(c32,
				setRuleExecutionContainer_WrongSensitivity(), factModel).getRedactedDocument();
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
	public void testRedactDocument_WrongSensitivityInXacmlResult()
			throws Exception {
		// Arrange
		initDocumentRedactorWithActualServices();
		String xacmlResultWithWrongObligations = MOCK_XACML_RESULT.replace(
				"<pdpObligation>Drug Abuse</pdpObligation>", "").replace(
				"<pdpObligation>HIV</pdpObligation>", "");
		String factModelXml = factModelExtractor.extractFactModel(c32,
				xacmlResultWithWrongObligations);
		c32 = embeddedClinicalDocumentExtractor
				.extractClinicalDocumentFromFactModel(factModelXml);
		factModelXml = removeEmbeddedClinicalDocument(factModelXml);
		FactModel factModel = marshaller.unmarshallFromXml(FactModel.class,
				factModelXml);
		setValueSetCategories(factModel);
		factModel.setXacmlResult(setMockXacmlResult(xacmlResultWithWrongObligations));

		// Act
		String result = documentRedactor.redactDocument(c32,
				ruleExecutionContainer,
				factModel).getRedactedDocument();
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
		xPathExprHumanReadableTextNode = xPathExprHumanReadableTextNode
				.replace("%", textContent.toLowerCase());
		NodeList nodeList = documentEditor.getNodeList(
				documentXmlConverter.loadDocument(c32),
				xPathExprHumanReadableTextNode);
		return nodeList;
	}

	private XacmlResult initXacmlResult(String problems, String allergies,
			String medications, String results, String hiv, String psy,
			String eth, String gdis, String sdv, String sex, String std)
			throws JAXBException {
		String xacmlResultForTest = xacmlResult.replace(PROBLEMS, problems)
				.replace(ALLERGIES, allergies)
				.replace(MEDICATIONS, medications).replace(RESULTS, results)
				.replace(HIV, hiv).replace(PSY, psy).replace(ETH, eth)
				.replace(GDIS, gdis).replace(SDV, sdv).replace(SEX, sex)
				.replace(STD, std);
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
		ReflectionTestUtils.setField(documentRedactor, "documentXmlConverter",
				documentXmlConverter);
		ReflectionTestUtils.setField(documentRedactor, "documentAccessor",
				documentAccessor);
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

	private static XacmlResult setMockXacmlResult(String xacmlResultXml)
			throws JAXBException {
		/*
		 * XacmlResult mock = mock(XacmlResult.class); List<String> obligations
		 * = new LinkedList<String>(); obligations.add("ETH");
		 * obligations.add("HIV");
		 * when(mock.getPdpObligations()).thenReturn(obligations)
		 * .thenReturn(obligations).thenReturn(obligations); return mock;
		 */
		return marshaller.unmarshallFromXml(XacmlResult.class, xacmlResultXml);
	}

	@SuppressWarnings("unused")
	private static XacmlResult setMockXacmlResult_WrongSensitivity() {
		XacmlResult mock = mock(XacmlResult.class);
		List<String> obligations = new LinkedList<String>();
		obligations.add("STD");
		obligations.add("SEX");
		when(mock.getPdpObligations()).thenReturn(obligations)
				.thenReturn(obligations).thenReturn(obligations);
		return mock;
	}

	private DocumentXmlConverterImpl setSpyDocumentXmlConverter()
			throws Exception {
		DocumentXmlConverterImpl converter = new DocumentXmlConverterImpl();
		DocumentXmlConverterImpl spy = spy(converter);

		c32Document = readDocument("src/test/resources/xmlDocument.txt");

		doReturn(c32Document).when(spy).loadDocument(anyString());
		return spy;
	}

	@SuppressWarnings("unused")
	private DocumentEditorImpl setMockDocumentEditor()
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

	private void setValueSetCategories(FactModel factModel) {
		// Get and set value set categories to clinical facts
		for (ClinicalFact fact : factModel.getClinicalFactList()) {
			// Get value set categories
			Set<String> valueSetCategories = valueSetService
					.lookupValueSetCategories(fact.getCode(),
							fact.getCodeSystem());
			// Set retrieved value set categories to the clinical fact
			fact.setValueSetCategories(valueSetCategories);
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
