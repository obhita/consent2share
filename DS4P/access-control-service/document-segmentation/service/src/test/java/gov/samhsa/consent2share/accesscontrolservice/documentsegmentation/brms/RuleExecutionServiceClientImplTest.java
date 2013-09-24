package gov.samhsa.consent2share.accesscontrolservice.documentsegmentation.brms;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.accesscontrolservice.brms.client.RuleExecutionWebServiceClient;
import gov.samhsa.consent2share.schema.ruleexecutionservice.AssertAndExecuteClinicalFactsRequest;
import gov.samhsa.consent2share.schema.ruleexecutionservice.AssertAndExecuteClinicalFactsResponse;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleExecutionServiceClientImplTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static RuleExecutionWebServiceClient ruleExecutionWebServiceClientMock;
	private static final String FACT_MODEL = "<FactModel><xacmlResult><pdpDecision>Permit</pdpDecision><purposeOfUse>TREAT</purposeOfUse><messageId>71fe0397-3684-4acb-9811-6416c5c77b55</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId></xacmlResult><ClinicalFacts><ClinicalFact><code>66214007</code><displayName>Substance abuse (disorder)</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName/><c32SectionTitle>Problems</c32SectionTitle><c32SectionLoincCode>11450-4</c32SectionLoincCode><observationId>d17e216</observationId></ClinicalFact><ClinicalFact><code>DOCUMENT</code></ClinicalFact></ClinicalFacts></FactModel>";
	private static final String RULE_EXECUTION_CONTAINER = "<ruleExecutionContainer><executionResponseList><executionResponse><documentObligationPolicy>ENCRYPT</documentObligationPolicy><documentRefrainPolicy>NODSCLCD</documentRefrainPolicy><USPrivacyLaw>_42CFRPart2</USPrivacyLaw></executionResponse><executionResponse><c32SectionLoincCode>11450-4</c32SectionLoincCode><code>66214007</code><codeSystem>2.16.840.1.113883.6.96</codeSystem><displayName>Substance abuse (disorder)</displayName><impliedConfSection>R</impliedConfSection><observationId>d17e216</observationId><sensitivity>ETH</sensitivity></executionResponse></executionResponseList></ruleExecutionContainer>";

	private static AssertAndExecuteClinicalFactsRequest request;
	private static AssertAndExecuteClinicalFactsResponse response;

	private static RuleExecutionServiceClientImpl ruleExecutionServiceClient;
	private static RuleExecutionServiceClientImpl ruleExecutionServiceClientSpy;

	@BeforeClass
	public static void setUp() throws Exception {
		response = new AssertAndExecuteClinicalFactsResponse();
		response.setRuleExecutionResponseContainer(RULE_EXECUTION_CONTAINER);
		request = new AssertAndExecuteClinicalFactsRequest();
		request.setClinicalFactXmlString(FACT_MODEL);

		ruleExecutionWebServiceClientMock = mock(RuleExecutionWebServiceClient.class);
		when(
				ruleExecutionWebServiceClientMock
						.assertAndExecuteClinicalFacts(request)).thenReturn(
				response);

		ruleExecutionServiceClient = new RuleExecutionServiceClientImpl("");
		ruleExecutionServiceClientSpy = spy(ruleExecutionServiceClient);
		when(ruleExecutionServiceClientSpy.getRuleExecutionWebServiceClient())
				.thenReturn(ruleExecutionWebServiceClientMock);
		when(ruleExecutionServiceClientSpy.createRequest(FACT_MODEL))
				.thenReturn(request);
	}

	@Test
	public void testAssertAndExecuteClinicalFacts() {
		String result = ruleExecutionServiceClientSpy
				.assertAndExecuteClinicalFacts(FACT_MODEL);
		logger.debug(result);
		assertEquals(RULE_EXECUTION_CONTAINER, result);
	}
}
