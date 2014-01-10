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
package gov.samhsa.acs.pep.wsclient;

import gov.samhsa.ds4ppilot.contract.pep.FilterC32Service;
import gov.samhsa.ds4ppilot.contract.pep.FilterC32ServicePortType;
import gov.samhsa.ds4ppilot.schema.pep.FilterC32Request;
import gov.samhsa.ds4ppilot.schema.pep.FilterC32Response;
import gov.samhsa.ds4ppilot.schema.pep.RegisteryStoredQueryRequest;
import gov.samhsa.ds4ppilot.schema.pep.RegisteryStoredQueryResponse;
import gov.samhsa.ds4ppilot.schema.pep.RetrieveDocumentSetRequest;
import gov.samhsa.ds4ppilot.schema.pep.RetrieveDocumentSetResponse;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class FilterC32WebServiceClient {

	private final String wsdlFileName = "FilterC32Service.wsdl";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The endpoint address. */
	private String endpointAddress;

	public static void main(String[] args) {
		// Change pep-war cxf-servlet.xml to Use PolicyDecisionPointImplDataJdbc instead of PolicyDecisionPointImplDataXdsb to make this working
		String endpointAddress = "http://localhost:8080/Pep/services/filterc32service";
		
		FilterC32WebServiceClient filterC32WebServiceClient = new FilterC32WebServiceClient(endpointAddress);
		
		FilterC32Request filterC32Request = new FilterC32Request();
		
		//TODO: See PolicyDecisionPointImplDataJdbc for why this hard code value is used
		filterC32Request.setPatientId("PUI100010060001");
		
		//TODO: See ContextHandlerImpl for why use these hard code values here
		filterC32Request.setRecipientEmailAddress("Tao.Lin@direct.healthvault-stage.com");
		filterC32Request.setSenderEmailAddress("DoNotCare@donotcare.com");
		
		filterC32Request.setPackageAsXdm(false);
		FilterC32Response filterC32Response = filterC32WebServiceClient.filterC32(filterC32Request);
		
		System.out.println(filterC32Response.getPdpDecision());
		System.out.println(filterC32Response.getMaskedDocument());
	}

	public FilterC32WebServiceClient(String endpointAddress) {
		this.endpointAddress = endpointAddress;
	}
	
	public RetrieveDocumentSetResponse retrieveDocumentSet(
			RetrieveDocumentSetRequest retrieveDocumentSetRequest) {
		FilterC32ServicePortType port = createPort();

		RetrieveDocumentSetResponse result = port.retrieveDocumentSet(retrieveDocumentSetRequest);

		return result;
	}
	
	public RegisteryStoredQueryResponse registeryStoredQuery(
			RegisteryStoredQueryRequest registeryStoredQueryRequest) {
		FilterC32ServicePortType port = createPort();

		RegisteryStoredQueryResponse result = port.registeryStoredQuery(registeryStoredQueryRequest);

		return result;
	}

	public FilterC32Response filterC32(FilterC32Request filterC32Request) {
		logger.debug("Creating Filter C32 Service instance ...");
		
		FilterC32ServicePortType port = createPort();

		FilterC32Response result = port.filterC32(filterC32Request);

		return result;
	}

	private FilterC32ServicePortType createPort() {
		final URL WSDL_LOCATION = this.getClass().getClassLoader()
				.getResource(wsdlFileName);
		final QName SERVICE = new QName(
				"http://www.samhsa.gov/ds4ppilot/contract/pep",
				"FilterC32Service");

		FilterC32ServicePortType port = new FilterC32Service(WSDL_LOCATION,
				SERVICE).getFilterC32Port();
		
		if (StringUtils.hasText(this.endpointAddress)) {
			BindingProvider bp = (BindingProvider) port;
			bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
					endpointAddress);
		} 
		
		return port;
	}
}
