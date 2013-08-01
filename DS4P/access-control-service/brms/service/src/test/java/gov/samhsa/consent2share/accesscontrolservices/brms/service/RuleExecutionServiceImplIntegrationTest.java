package gov.samhsa.consent2share.accesscontrolservices.brms.service;

import gov.samhsa.consent2share.accesscontrolservices.brms.guvnor.GuvnorServiceImpl;
import gov.samhsa.consent2share.accesscontrolservices.brms.service.RuleExecutionServiceImpl;
import gov.samhsa.consent2share.schema.ruleexecutionservice.AssertAndExecuteClinicalFactsResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.drools.rule.Rule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;


/**
 * Unit test for simple App.
 */
public class RuleExecutionServiceImplIntegrationTest {

	/** The sut. */
	private RuleExecutionServiceImpl sut;

	/** The guvnor rest url. */
	private String guvnorRestUrl;

	/** The clinical facts. */
	private String clinicalFacts;

	private String endpointAddressGuvnorService;

	/**
	 * Set up.
	 */
	@Before
	public void setUp() {
		endpointAddressGuvnorService = "http://obhitaqaacs01/guvnor-5.5.0.Final-tomcat-6.0/rest/packages/AnnotationRules/source";

		sut = new RuleExecutionServiceImpl(new GuvnorServiceImpl(
				endpointAddressGuvnorService));
	}

	/**
	 * Test assert and execute clinical facts_ returns_ execution response.
	 */
	@Ignore("This test should be configured to run as an integration test.")
	@Test
	public void testAssertAndExecuteClinicalFacts_Returns_ExecutionResponse() {
		clinicalFacts = "<FactModel><xacmlResult><pdpDecision>Permit</pdpDecision><purposeOfUse>TREAT</purposeOfUse><messageId>71fe0397-3684-4acb-9811-6416c5c77b55</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId></xacmlResult>"
				+ "<ClinicalFacts><ClinicalFact><code>66214007</code><displayName>Substance abuse (disorder)</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName/><c32SectionTitle>Problems</c32SectionTitle><c32SectionLoincCode>11450-4</c32SectionLoincCode><observationId>d17e216</observationId></ClinicalFact><ClinicalFact><code>DOCUMENT</code></ClinicalFact></ClinicalFacts></FactModel>";

		AssertAndExecuteClinicalFactsResponse response = sut
				.assertAndExecuteClinicalFacts(clinicalFacts);
		String ruleExecutionContainerXML = response
				.getRuleExecutionResponseContainer();
		System.out.println("\n\n" + ruleExecutionContainerXML);

		Assert.assertNotNull(ruleExecutionContainerXML);
	}

	/**
	 * Test Clinical Rule Mental health problem (finding) REDACT rule
	 */
	@Ignore("This test should be configured to run as an integration test.")
	@Test
	public void testRule_clinicalRuleMentalHealthProblemREDACT() {
		clinicalFacts = "<FactModel><xacmlResult><pdpDecision>Permit</pdpDecision><purposeOfUse>TREAT</purposeOfUse><messageId>71fe0397-3684-4acb-9811-6416c5c77b55</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId></xacmlResult><ClinicalFacts><ClinicalFact><code>413307004</code><displayName>Mental health problem (finding)</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName/><c32SectionTitle>Problems</c32SectionTitle><c32SectionLoincCode>11450-4</c32SectionLoincCode><observationId>d17e216</observationId></ClinicalFact></ClinicalFacts></FactModel>";

		AssertAndExecuteClinicalFactsResponse response = sut
				.assertAndExecuteClinicalFacts(clinicalFacts);
		String ruleExecutionContainerXML = response
				.getRuleExecutionResponseContainer();
		System.out.println("\n\n" + ruleExecutionContainerXML);
		Assert.assertNotNull(ruleExecutionContainerXML);
		// Assertions below need a particular rule in Gunvor
		Assert.assertTrue("Sensitivity isn't PSY", ruleExecutionContainerXML
				.contains("<sensitivity>PSY</sensitivity>"));
		Assert.assertTrue("Confidentiality isn't R", ruleExecutionContainerXML
				.contains("<impliedConfSection>R</impliedConfSection>"));
		Assert.assertTrue("US Privacy Law isn't 42CFRPart2",
				ruleExecutionContainerXML
						.contains("<USPrivacyLaw>42CFRPart2</USPrivacyLaw>"));
		Assert.assertTrue(
				"Document Refrain Policy isn't NODSCLCD",
				ruleExecutionContainerXML
						.contains("<documentRefrainPolicy>NODSCLCD</documentRefrainPolicy>"));
		Assert.assertTrue(
				"Document Obligation Policy isn't ENCRYPT",
				ruleExecutionContainerXML
						.contains("<documentObligationPolicy>ENCRYPT</documentObligationPolicy>"));
		Assert.assertTrue("Clinical fact returned with a different code",
				ruleExecutionContainerXML.contains("<code>413307004</code>"));

	}
}
