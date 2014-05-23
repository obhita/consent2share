package gov.samhsa.acs.documentsegmentation.ws;

import static org.junit.Assert.assertTrue;
import gov.samhsa.acs.brms.RuleExecutionServiceImpl;
import gov.samhsa.acs.brms.guvnor.GuvnorServiceImpl;
import gov.samhsa.acs.common.tool.DocumentAccessorImpl;
import gov.samhsa.acs.common.tool.DocumentXmlConverterImpl;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.documentsegmentation.DocumentSegmentationImpl;
import gov.samhsa.acs.documentsegmentation.tools.AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl;
import gov.samhsa.acs.documentsegmentation.tools.DocumentEditorImpl;
import gov.samhsa.acs.documentsegmentation.tools.DocumentEncrypterImpl;
import gov.samhsa.acs.documentsegmentation.tools.DocumentFactModelExtractorImpl;
import gov.samhsa.acs.documentsegmentation.tools.DocumentMaskerImpl;
import gov.samhsa.acs.documentsegmentation.tools.DocumentRedactor;
import gov.samhsa.acs.documentsegmentation.tools.DocumentRedactorImpl;
import gov.samhsa.acs.documentsegmentation.tools.DocumentTaggerImpl;
import gov.samhsa.acs.documentsegmentation.tools.EmbeddedClinicalDocumentExtractorImpl;
import gov.samhsa.acs.documentsegmentation.tools.MetadataGeneratorImpl;
import gov.samhsa.acs.documentsegmentation.valueset.ValueSetServiceImplMock;
import gov.samhsa.consent2share.contract.documentsegmentation.DocumentSegmentationService;
import gov.samhsa.consent2share.contract.documentsegmentation.DocumentSegmentationServicePortType;
import gov.samhsa.consent2share.schema.documentsegmentation.SegmentDocumentRequest;
import gov.samhsa.consent2share.schema.documentsegmentation.SegmentDocumentResponse;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentSegmentationServiceImplRuleExecutionServiceIT {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	protected static URL wsdlURL;
	protected static QName serviceName;
	protected static QName portName;
	protected static Endpoint ep;
	protected static String address;
	private static String c32Document;
	private static String xacmlResult;
	private static String senderEmailAddress;
	private static String recipientEmailAddress;
	private static String xdsDocumentEntryUniqueId;
	private static String endpointAddressForAuditService;
	private static String endpointAddressGuvnorService;

	private static RuleExecutionServiceImpl ruleExecutionService;
	private static DocumentEditorImpl documentEditor;
	private static SimpleMarshallerImpl marshaller;
	private static DocumentEncrypterImpl documentEncrypter;
	private static DocumentRedactor documentRedactor;
	private static DocumentMaskerImpl documentMasker;
	private static DocumentTaggerImpl documentTagger;
	private static MetadataGeneratorImpl metadataGenerator;
	private static FileReaderImpl fileReader;
	private static DocumentFactModelExtractorImpl documentFactModelExtractor;
	private static DocumentXmlConverterImpl documentXmlConverter;
	private static DocumentAccessorImpl documentAccessor;
	private static EmbeddedClinicalDocumentExtractorImpl embeddedClinicalDocumentExtractor;
	private static AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl additionalMetadataGeneratorForSegmentedClinicalDocumentImpl;

	static {
		serviceName = new QName(
				"http://www.samhsa.gov/consent2share/contract/documentsegmentation",
				"DocumentSegmentationService");
		portName = new QName(
				"http://www.samhsa.gov/consent2share/contract/documentsegmentation",
				"DocumentSegmentationServicePort");
		// xacmlResult =
		// "<xacmlResult><pdpDecision>Permit</pdpDecision><purposeOfUse>TREAT</purposeOfUse><messageId>4617a579-1881-4e40-9f98-f85bd81d6502</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:org:us-privacy-law:42CFRPart2</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:org:refrain-policy:NORDSLCD</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:redact:ETH</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:redact:PSY</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:mask:HIV</pdpObligation></xacmlResult>";
		xacmlResult = "<xacmlResult><pdpDecision>Permit</pdpDecision><purposeOfUse>TREAT</purposeOfUse><messageId>4617a579-1881-4e40-9f98-f85bd81d6502</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:org:us-privacy-law:42CFRPart2</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:org:refrain-policy:NORDSLCD</pdpObligation><pdpObligation>ETH</pdpObligation><pdpObligation>PSY</pdpObligation><pdpObligation>HIV</pdpObligation></xacmlResult>";
		senderEmailAddress = "leo.smith@direct.obhita-stage.org";
		recipientEmailAddress = "Duane_Decouteau@direct.healthvault-stage.com";
		xdsDocumentEntryUniqueId = "123";
		endpointAddressForAuditService = "http://174.78.146.228:8080/DS4PACSServices/DS4PAuditService";
	}

	@Before
	public void setUpBeforeClass() throws Exception {
		fileReader = new FileReaderImpl();
		metadataGenerator = new MetadataGeneratorImpl();
		documentEditor = new DocumentEditorImpl(metadataGenerator, fileReader,
				new DocumentXmlConverterImpl());
		marshaller = new SimpleMarshallerImpl();
		documentEncrypter = new DocumentEncrypterImpl();
		documentRedactor = new DocumentRedactorImpl(new DocumentXmlConverterImpl(), null);
		documentMasker = new DocumentMaskerImpl();
		documentTagger = new DocumentTaggerImpl();
		documentFactModelExtractor = new DocumentFactModelExtractorImpl();
		additionalMetadataGeneratorForSegmentedClinicalDocumentImpl = new AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl();
		documentXmlConverter = new DocumentXmlConverterImpl();
		documentAccessor = new DocumentAccessorImpl();
		embeddedClinicalDocumentExtractor = new EmbeddedClinicalDocumentExtractorImpl(documentXmlConverter, documentAccessor);

		address = "http://localhost:9000/services/processDocumentservice";
		wsdlURL = new URL(address + "?wsdl");
		c32Document = fileReader.readFile("sampleC32/c32.xml");

		endpointAddressGuvnorService = "http://localhost:7070/guvnor-5.5.0.Final-tomcat-6.0/rest/packages/AnnotationRules/source";

		ruleExecutionService = new RuleExecutionServiceImpl(
				new GuvnorServiceImpl(endpointAddressGuvnorService, "admin",
						"admin"), new SimpleMarshallerImpl());
		ep = Endpoint
				.publish(
						address,
						new DocumentSegmentationServiceImpl(
								new DocumentSegmentationImpl(
										ruleExecutionService,
										null,
										documentEditor, marshaller,
										documentEncrypter, documentRedactor,
										documentMasker, documentTagger,
										documentFactModelExtractor,
										embeddedClinicalDocumentExtractor, new ValueSetServiceImplMock(fileReader), additionalMetadataGeneratorForSegmentedClinicalDocumentImpl)));
	}

	@After
	public void tearDownAfterClass() throws Exception {
		try {
			ep.stop();
		} catch (Throwable t) {
			logger.debug("Error thrown: " + t.getMessage());
		}
	}

	/*
	 * This test uses wsimport/wsdl2java generated artifacts, both service and
	 * SEI
	 */
	// Integration test
	@Test
	public void testSegmentDocument_RedactSensitivityETH()
			throws MalformedURLException {
		DocumentSegmentationService pds = new DocumentSegmentationService(
				wsdlURL, serviceName);
		DocumentSegmentationServicePortType pdspt = pds
				.getDocumentSegmentationServicePort();
		SegmentDocumentRequest param = new SegmentDocumentRequest();
		param.setDocument(c32Document.toString());
		param.setEnforcementPolicies(xacmlResult);
		param.setPackageAsXdm(false);
		param.setEncryptDocument(true);
		param.setSenderEmailAddress(senderEmailAddress);
		param.setRecipientEmailAddress(recipientEmailAddress);
		param.setXdsDocumentEntryUniqueId(xdsDocumentEntryUniqueId);
		SegmentDocumentResponse resp = pdspt.segmentDocument(param);
		logger.debug("resp.getMaskedDocument()");
		logger.debug(resp.getMaskedDocument());
		logger.debug("resp.getPostProcessingMetadata()");
		logger.debug(resp.getPostProcessingMetadata());
		logger.debug("resp.getKekEncryptionKey().toString()");
		logger.debug(resp.getKekEncryptionKey().toString());
		logger.debug("resp.getKekMaskingKey().toString()");
		logger.debug(resp.getKekMaskingKey().toString());
		logger.debug("resp.getProcessedDocument().toString()");
		logger.debug(resp.getProcessedDocument().toString());

		assertTrue(
				"The processed document should not contain this observation id per 'Substance abuse (disorder)' rule in Guvnor",
				!resp.getMaskedDocument().contains(
						"e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7"));
		assertTrue(
				"The processed document should not contain this observation id per 'DUMMY - Acute HIV' ruke un Guvnor (it should have been redacted)",
				!resp.getMaskedDocument().contains(
						"d11275e7-67ae-11db-bd13-0800200c9a66"));
	}
}
