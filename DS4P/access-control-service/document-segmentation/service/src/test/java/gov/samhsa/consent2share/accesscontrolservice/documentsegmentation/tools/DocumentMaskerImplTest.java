package gov.samhsa.consent2share.accesscontrolservice.documentsegmentation.tools;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.accesscontrolservice.common.tool.DocumentXmlConverterImpl;
import gov.samhsa.consent2share.accesscontrolservice.common.tool.FileReaderImpl;
import gov.samhsa.ds4ppilot.common.beans.RuleExecutionContainer;
import gov.samhsa.ds4ppilot.common.beans.XacmlResult;
import gov.samhsa.ds4ppilot.common.exception.DS4PException;
import gov.samhsa.ds4ppilot.common.utils.EncryptTool;
import gov.va.ds4p.cas.RuleExecutionResponse;

import java.util.LinkedList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentMaskerImplTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static FileReaderImpl fileReader;
	private static DocumentXmlConverterImpl documentXmlConverter;

	private static XacmlResult xacmlResultMock;

	private static RuleExecutionContainer ruleExecutionContainer;
	private static String c32;
	
	private static DocumentMaskerImpl documentMasker;

	@BeforeClass
	public static void setUp() throws Exception {
		fileReader = new FileReaderImpl();
		documentXmlConverter = new DocumentXmlConverterImpl();

		documentMasker = new DocumentMaskerImpl();
		documentMasker.setDocumentXmlConverter(documentXmlConverter);

		c32 = fileReader.readFile("c32.xml");
		ruleExecutionContainer = setRuleExecutionContainer();
		xacmlResultMock = setMockXacmlResult();
	}

	@Test
	public void testMaskElement() {
		logger.debug(c32);
		String s = null;
		try {
			// Act
			s = documentMasker.maskDocument(c32,
					EncryptTool.generateKeyEncryptionKey(),
					ruleExecutionContainer, xacmlResultMock);
			logger.debug(s);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		assertNotNull(s);
		// TODO might need better assertion for masking if the masking code is
		// activated (disabling redact-only comments)
	}

	@Test(expected = DS4PException.class)
	public void testMaskElement_Throws_DS4PException() throws Exception {
		// Empty xml file
		@SuppressWarnings("unused")
		String s = documentMasker.maskDocument("",
				EncryptTool.generateKeyEncryptionKey(), ruleExecutionContainer,
				xacmlResultMock);
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

	private static XacmlResult setMockXacmlResult() {
		XacmlResult temp = mock(XacmlResult.class);
		List<String> obligations = new LinkedList<String>();
		obligations.add("ETH");
		obligations.add("HIV");
		when(temp.getPdpObligations()).thenReturn(obligations);
		return temp;
	}
}
