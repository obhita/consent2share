/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.acs.brms.wsclient;

import gov.samhsa.consent2share.contract.ruleexecutionservice.RuleExecutionService;
import gov.samhsa.consent2share.contract.ruleexecutionservice.RuleExecutionServicePortType;
import gov.samhsa.consent2share.schema.ruleexecutionservice.AssertAndExecuteClinicalFactsRequest;
import gov.samhsa.consent2share.schema.ruleexecutionservice.AssertAndExecuteClinicalFactsResponse;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


/**
 * The Class RuleExecutionWebServiceClient.
 */
public class RuleExecutionWebServiceClient {
	
	/** The endpoint address. */
	private String endpointAddress;
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(RuleExecutionWebServiceClient.class);

	/**
	 * Instantiates a new rule execution web service client.
	 *
	 * @param endpointAddress the endpoint address
	 */
	public RuleExecutionWebServiceClient(String endpointAddress) {
		this.endpointAddress = endpointAddress;
	}

	
	/**
	 * Assert and execute clinical facts.
	 *
	 * @param andExecuteClinicalFactsRequest the and execute clinical facts request
	 * @return the assert and execute clinical facts response
	 */
	public AssertAndExecuteClinicalFactsResponse assertAndExecuteClinicalFacts(
			AssertAndExecuteClinicalFactsRequest andExecuteClinicalFactsRequest) {
		RuleExecutionServicePortType port;
		if (StringUtils.hasText(this.endpointAddress)) {
			port = createPort(endpointAddress);
		} else {
			// Using default endpoint address defined in the wsdl:port of wsdl
			// file
			port = createPort();
		}

		return assertAndExecuteClinicalFacts(port,
				andExecuteClinicalFactsRequest);
	}

	
	/**
	 * Assert and execute clinical facts.
	 *
	 * @param port the port
	 * @param request the request
	 * @return the assert and execute clinical facts response
	 */
	private AssertAndExecuteClinicalFactsResponse assertAndExecuteClinicalFacts(
			RuleExecutionServicePortType port,
			AssertAndExecuteClinicalFactsRequest request) {
		return port.assertAndExecuteClinicalFacts(request);
	}

	
	/**
	 * Creates the port.
	 *
	 * @return the rule execution service port type
	 */
	private RuleExecutionServicePortType createPort() {
		final URL WSDL_LOCATION = this.getClass().getClassLoader()
				.getResource("RuleExecutionService.wsdl");
		final QName SERVICE = new QName(
				"http://www.samhsa.gov/consent2share/contract/RuleExecutionService",
				"RuleExecutionService");

		RuleExecutionServicePortType port = new RuleExecutionService(
				WSDL_LOCATION, SERVICE)
				.getRuleExecutionServicePort();
		return port;
	}

	
	/**
	 * Creates the port.
	 *
	 * @param endpointAddress the endpoint address
	 * @return the rule execution service port type
	 */
	private RuleExecutionServicePortType createPort(
			String endpointAddress) {
		RuleExecutionServicePortType port = createPort();
		BindingProvider bp = (BindingProvider) port;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				endpointAddress);
		return port;
	}
	
	/** The Constant SERVICE_NAME. */
	private static final QName SERVICE_NAME = new QName("http://www.samhsa.gov/consent2share/contract/RuleExecutionService", "RuleExecutionService");
	
	/** The clinical facts. */
	private static String clinicalFacts =  "<FactModel><xacmlResult><pdpDecision>Permit</pdpDecision><purposeOfUse>TREAT</purposeOfUse><messageId>71fe0397-3684-4acb-9811-6416c5c77b55</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId></xacmlResult>"
		       + "<ClinicalFacts><ClinicalFact><code>66214007</code><displayName>Substance abuse (disorder)</displayName><codeSystem>2.16.840.1.113883.6.96</codeSystem><codeSystemName/><c32SectionTitle>Problems</c32SectionTitle><c32SectionLoincCode>11450-4</c32SectionLoincCode><observationId>d17e216</observationId></ClinicalFact><ClinicalFact><code>DOCUMENT</code></ClinicalFact></ClinicalFacts></FactModel>";
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String [] args)
	{
		URL wsdlURL = RuleExecutionService.WSDL_LOCATION;
		if (args.length > 0 && args[0] != null && !"".equals(args[0])) {
			File wsdlFile = new File(args[0]);
			try {
				if (wsdlFile.exists()) {
					wsdlURL = wsdlFile.toURI().toURL();
				} else {
					wsdlURL = new URL(args[0]);
				}
			} catch (MalformedURLException e) {
				LOGGER.error(e.getMessage(),e);
			}
		}
		
		RuleExecutionService ruleExecutionService = new RuleExecutionService(wsdlURL, SERVICE_NAME);
		RuleExecutionServicePortType port = ruleExecutionService.getRuleExecutionServicePort();
		
		LOGGER.debug("Invoking RuleExecutionService...");
		
		AssertAndExecuteClinicalFactsRequest parameters =  new AssertAndExecuteClinicalFactsRequest();
		parameters.setClinicalFactXmlString(clinicalFacts);
		
		AssertAndExecuteClinicalFactsResponse response = port.assertAndExecuteClinicalFacts(parameters);
		
		LOGGER.debug("RuleExecutionResponseContainer returned...");
		LOGGER.debug(response.getRuleExecutionResponseContainer());
		
		System.exit(0);
	}
}
