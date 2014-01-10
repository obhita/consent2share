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

import gov.samhsa.acs.pep.wsclient.FilterC32WebServiceClient;
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
import javax.xml.ws.Endpoint;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FilterC32WebServiceClientTest {

	private static Endpoint ep;
	private static String address;
	
	private static final RetrieveDocumentSetResponse returnedValueOfRetrieveDocumentSet = new RetrieveDocumentSetResponse();
	private static final String returnedString1 = "bla-1";
	
	private static final RegisteryStoredQueryResponse returnedValueOfRegisteryStoredQuery = new RegisteryStoredQueryResponse();
	private static final String returnedString2 = "bla-2";
	
	private static final FilterC32Response returnedValueOfFilterC32 = new FilterC32Response();
	private static final String pdpDecision = "Decistion";
	
	private final String wsdlFileName = "FilterC32Service.wsdl";

	@BeforeClass
	public static void setUp() {
		address = "http://localhost:9000/services/FilterC32Service";
		ep = Endpoint.publish(address, new FilterC32ServicePortTypeImpl());
		
		FilterC32ServicePortTypeImpl.returnedValueOfRetrieveDocumentSet = returnedValueOfRetrieveDocumentSet;
		returnedValueOfRetrieveDocumentSet.setReturn(returnedString1);
		
		FilterC32ServicePortTypeImpl.returnedValueOfRegisteryStoredQuery = returnedValueOfRegisteryStoredQuery;
		returnedValueOfRegisteryStoredQuery.setReturn(returnedString2);
		
		FilterC32ServicePortTypeImpl.returnedValueOfFilterC32 = returnedValueOfFilterC32; 
		returnedValueOfFilterC32.setPdpDecision(pdpDecision);
	}

	@AfterClass
	public static void tearDown() {
		try {
			ep.stop();
		} catch (Throwable t) {
			System.out.println("Error thrown: " + t.getMessage());
		}
	}

	// Test if the stub web service activate properly
	@Test
	public void testStubWebService_Works() {
		final FilterC32Request request = new FilterC32Request();
		
		FilterC32Response resp = createPort().filterC32(request);
		validateResponseOfFilterC32(resp);
	}

	// Test if the SOAP client calling the stub web service correctly?
	@Test
	public void testFilterC32WebServiceClient_FilterC32_Works() {
		final FilterC32Request request = new FilterC32Request();

		FilterC32WebServiceClient wsc = new FilterC32WebServiceClient(address);
		FilterC32Response resp = wsc.filterC32(request);
		validateResponseOfFilterC32(resp);
	}
	
	@Test
	public void testFilterC32WebServiceClient_RetrieveDocumentSet_Works() {
		final RetrieveDocumentSetRequest request = new RetrieveDocumentSetRequest();

		FilterC32WebServiceClient wsc = new FilterC32WebServiceClient(address);
		RetrieveDocumentSetResponse resp = wsc.retrieveDocumentSet(request);
		validateResponseOfRetrieveDocumentSet(resp);
	}
	
	@Test
	public void testFilterC32WebServiceClient_RegisteryStoredQuery_Works() {
		final RegisteryStoredQueryRequest request = new RegisteryStoredQueryRequest();

		FilterC32WebServiceClient wsc = new FilterC32WebServiceClient(address);
		RegisteryStoredQueryResponse resp = wsc.registeryStoredQuery(request);
		validateResponseOfRegisteryStoredQuery(resp);
	}

	private void validateResponseOfRetrieveDocumentSet(
			RetrieveDocumentSetResponse resp) {
		Assert.assertEquals(returnedValueOfRetrieveDocumentSet.getReturn(), resp.getReturn());
	}
	
	private void validateResponseOfRegisteryStoredQuery(
			RegisteryStoredQueryResponse resp) {
		Assert.assertEquals(returnedValueOfRegisteryStoredQuery.getReturn(), resp.getReturn());
	}

	private void validateResponseOfFilterC32(FilterC32Response resp) {
		Assert.assertEquals(returnedValueOfFilterC32.getPdpDecision(), resp.getPdpDecision());
	}
	
	private FilterC32ServicePortType createPort() {
		final URL WSDL_LOCATION = this.getClass().getClassLoader()
				.getResource(wsdlFileName);
		final QName SERVICE = new QName(
				"http://www.samhsa.gov/ds4ppilot/contract/pep",
				"FilterC32Service");

		FilterC32ServicePortType port = new FilterC32Service(WSDL_LOCATION,
				SERVICE).getFilterC32Port();
		
		BindingProvider bp = (BindingProvider) port;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				address);
		return port;
	}
}
