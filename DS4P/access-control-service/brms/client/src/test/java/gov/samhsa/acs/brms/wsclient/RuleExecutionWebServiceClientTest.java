/*******************************************************************************
 * Copyright 2012 SAMHSA
 * 
 * Licensed under the Substance Abuse & Mental Health Services Administration (SAMHSA), you may not use this file except in compliance with the License.
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package gov.samhsa.acs.brms.wsclient;

import gov.samhsa.acs.brms.wsclient.RuleExecutionWebServiceClient;
import gov.samhsa.consent2share.contract.ruleexecutionservice.RuleExecutionService;
import gov.samhsa.consent2share.contract.ruleexecutionservice.RuleExecutionServicePortType;
import gov.samhsa.consent2share.schema.ruleexecutionservice.AssertAndExecuteClinicalFactsRequest;
import gov.samhsa.consent2share.schema.ruleexecutionservice.AssertAndExecuteClinicalFactsResponse;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Endpoint;

import org.junit.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleExecutionWebServiceClientTest {
	protected static Endpoint ep;
	protected static String address;
	private static final Logger LOGGER = LoggerFactory.getLogger(RuleExecutionWebServiceClientTest.class);

	private static final AssertAndExecuteClinicalFactsResponse returnedValueOfAssertAndExecuteClinicalFacts = new AssertAndExecuteClinicalFactsResponse();
	private static final String ruleExecutionResponseContaine = "RuleExecutionResponseContaine";

	@BeforeClass
	public static void setUp() {
		address = "http://localhost:9000/services/RuleExecutionService";
		ep = Endpoint.publish(address,
				new RuleExecutionServicePortTypeImpl());

		returnedValueOfAssertAndExecuteClinicalFacts
				.setRuleExecutionResponseContainer(ruleExecutionResponseContaine);
		RuleExecutionServicePortTypeImpl.returnedValueOfAssertAndExecuteClinicalFacts = returnedValueOfAssertAndExecuteClinicalFacts;
	}

	@AfterClass
	public static void tearDown() {
		try {
			ep.stop();
		} catch (Throwable t) {
			LOGGER.debug("Error thrown: " + t.getMessage());
		}
	}

	// Test if the stub web service activate properly
	@Test
	public void testStubWebServiceWorks() {
		AssertAndExecuteClinicalFactsResponse resp = createPort().assertAndExecuteClinicalFacts(new AssertAndExecuteClinicalFactsRequest());
		validateResponse(resp);
	}

	// Test if the SOAP client calling the stub web service correctly?
	@Test
	public void testWSClientSOAPCallWorks() {
		RuleExecutionWebServiceClient wsc = new RuleExecutionWebServiceClient(
				address);
		AssertAndExecuteClinicalFactsResponse resp = wsc.assertAndExecuteClinicalFacts(new AssertAndExecuteClinicalFactsRequest());
		validateResponse(resp);
	}

	private void validateResponse(
			AssertAndExecuteClinicalFactsResponse andExecuteClinicalFactsResponse) {
		Assert.assertEquals(
				"Returned AssertAndExecuteClinicalFactsResponse wrong",
				returnedValueOfAssertAndExecuteClinicalFacts
						.getRuleExecutionResponseContainer(),
				andExecuteClinicalFactsResponse
						.getRuleExecutionResponseContainer());
	}

	private RuleExecutionServicePortType createPort() {
		final URL WSDL_LOCATION = this.getClass().getClassLoader()
				.getResource("RuleExecutionService.wsdl");
		final QName SERVICE = new QName(
				"http://www.samhsa.gov/consent2share/contract/RuleExecutionService",
				"RuleExecutionService");

		RuleExecutionServicePortType port = new RuleExecutionService(
				WSDL_LOCATION, SERVICE)
				.getRuleExecutionServicePort();
		BindingProvider bp = (BindingProvider) port;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				address);
		return port;
	}
}
