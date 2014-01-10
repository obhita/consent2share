package gov.samhsa.acs.common.tool;

import static org.junit.Assert.*;

import java.util.LinkedList;

import javax.xml.bind.JAXBException;

import gov.samhsa.acs.common.bean.RuleExecutionContainer;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.va.ds4p.cas.RuleExecutionResponse;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleMarshallerImplTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static FileReaderImpl fileReader;
	
	private static RuleExecutionContainer ruleExecutionContainer;
	private static String ruleExecutionContainerString;
	
	private static final String EXPECTED_RESPONSE_1 = "11450-4:Problems:66214007:SNOMED CT:Substance Abuse Disorder:ENCRYPT:NORDSLCD:REDACT:e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7:ETH:42CFRPart2:";
	private static final String EXPECTED_RESPONSE_2 = "11450-4:Problems:111880001:SNOMED CT:Acute HIV:ENCRYPT:NORDSLCD:MASK:d11275e7-67ae-11db-bd13-0800200c9a66:HIV:42CFRPart2:";
	private static final String EXPECTED_MARSHALL_RESPONSE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ruleExecutionContainer><executionResponseList/></ruleExecutionContainer>";
	
	private static SimpleMarshallerImpl marshaller;
	@BeforeClass
	public static void setUp() throws Exception {
		// Arrange
		fileReader = new FileReaderImpl();
		ruleExecutionContainerString = fileReader
				.readFile("ruleExecutionResponseContainer.xml");
		ruleExecutionContainer = null;

		marshaller = new SimpleMarshallerImpl();
	}

	@Test
	public void testUnmarshallFromXml() throws JAXBException {
		// Act
		ruleExecutionContainer = marshaller.unmarshallFromXml(
				RuleExecutionContainer.class, ruleExecutionContainerString);
		String[] results = new String[2];
		int i = 0;
		for (RuleExecutionResponse r : ruleExecutionContainer
				.getExecutionResponseList()) {
			String s = ruleExecutionResponseToString(r);
			results[i] = s;
			logger.debug(s);
			i++;
		}

		// Assert
		assertNotNull(ruleExecutionContainer);
		assertEquals(2, ruleExecutionContainer.getExecutionResponseList()
				.size());
		assertEquals(EXPECTED_RESPONSE_1, results[0]);
		assertEquals(EXPECTED_RESPONSE_2, results[1]);
	}
	
	@Test
	public void testMarshall() throws Throwable
	{
		// Arrange
		RuleExecutionContainer r = createRuleExecutionContainer();
		
		// Act
		String response = marshaller.marshall(r);
		
		// Assert
		assertEquals(EXPECTED_MARSHALL_RESPONSE,response);
	}

	private String ruleExecutionResponseToString(RuleExecutionResponse r) {
		StringBuilder builder = new StringBuilder();
		ruleExecutionResponseToStringAppender(builder,
				r.getC32SectionLoincCode());
		ruleExecutionResponseToStringAppender(builder, r.getC32SectionTitle());
		ruleExecutionResponseToStringAppender(builder, r.getCode());
		ruleExecutionResponseToStringAppender(builder, r.getCodeSystemName());
		ruleExecutionResponseToStringAppender(builder, r.getDisplayName());
		ruleExecutionResponseToStringAppender(builder,
				r.getDocumentObligationPolicy());
		ruleExecutionResponseToStringAppender(builder,
				r.getDocumentRefrainPolicy());
		ruleExecutionResponseToStringAppender(builder, r.getItemAction());
		ruleExecutionResponseToStringAppender(builder, r.getObservationId());
		ruleExecutionResponseToStringAppender(builder, r.getSensitivity());
		ruleExecutionResponseToStringAppender(builder, r.getUSPrivacyLaw());
		return builder.toString();
	}

	private void ruleExecutionResponseToStringAppender(StringBuilder builder,
			String s) {
		builder.append(s);
		builder.append(":");
	}
	
	private RuleExecutionContainer createRuleExecutionContainer()
	{
		RuleExecutionContainer r = new RuleExecutionContainer();
		r.setExecutionResponseList(new LinkedList<RuleExecutionResponse>());
		return r;
	}
}
