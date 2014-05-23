/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *   
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *       * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *       * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *       * Neither the name of the <organization> nor the
 *         names of its contributors may be used to endorse or promote products
 *         derived from this software without specific prior written permission.
 *   
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *   DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.acs.pep.wsclient;

import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.pep.security.CredentialProvider;
import gov.samhsa.acs.pep.security.CredentialProviderImpl;
import gov.samhsa.ds4ppilot.contract.pep.PepPortType;
import gov.samhsa.ds4ppilot.contract.pep.PepService;
import gov.samhsa.ds4ppilot.schema.pep.DirectEmailSendRequest;
import gov.samhsa.ds4ppilot.schema.pep.DirectEmailSendResponse;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;

import java.net.URL;
import java.util.LinkedList;
import java.util.Scanner;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.SecurityConstants;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.tokenstore.TokenStore;
import org.apache.cxf.ws.security.trust.STSClient;
import org.apache.ws.security.util.DOM2Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * The Class PepWebServiceClient.
 */
public class PepWebServiceClient {

	/** The port. */
	private PepPortType port;

	/** The logger. */
	private static Logger logger = LoggerFactory
			.getLogger(PepWebServiceClient.class);

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws Throwable
	 *             the throwable
	 */
	public static void main(String[] args) throws Throwable {
		String address = "http://localhost:8080/Pep/services/PepService";

		PepWebServiceClient client = new PepWebServiceClient(address,
				new CredentialProviderImpl());

		AdhocQueryResponse response1 = client
				.registryStoredQuery(constructAdhocQuery());

		printSecurityToken(client.getPort());

		SimpleMarshallerImpl marshaller = new SimpleMarshallerImpl();

		logger.debug("Adhoc Response: " + marshaller.marshall(response1));

		Scanner scan = new Scanner(System.in);
		logger.debug("Please enter documentUniqueId:");
		LinkedList<String> documentIds = new LinkedList<String>();
		boolean go = false;
		while (!go && scan.hasNextLine()) {
			String command = scan.nextLine();
			if ("go".equalsIgnoreCase(command)) {
				go = true;
			} else {
				documentIds.add(command);
			}
		}
		scan.close();

		RetrieveDocumentSetResponse retrieveDocResp = null;
		try {
			retrieveDocResp = client
					.retrieveDocumentSet(constructRetrieveDocumentRequest(documentIds));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		logger.debug("Retrieve Response: "
				+ marshaller.marshall(retrieveDocResp));

		if (null != retrieveDocResp.getDocumentResponse())
			logger.debug(new String(retrieveDocResp.getDocumentResponse()
					.get(0).getDocument()));
	}

	/**
	 * Instantiates a new pep web service client.
	 * 
	 * @param endpointAddress
	 *            the endpoint address
	 * @param cp
	 *            the cp
	 */
	public PepWebServiceClient(String endpointAddress, CredentialProvider cp) {
		/*
		 * try { wsdlURL = new URL(endpointAddress + "?wsdl"); } catch
		 * (MalformedURLException ex) { logger.error("URL not formed properly");
		 * }
		 */
		final URL wsdlURL = this.getClass().getClassLoader()
				.getResource("Pep.wsdl");

		final QName serviceName = new QName(
				"http://www.samhsa.gov/ds4ppilot/contract/pep", "PepService");

		PepService service = new PepService(wsdlURL, serviceName);
		port = service.getXDSHTTPEndpoint();

		BindingProvider bp = (BindingProvider) port;
		STSClient stsClient = (STSClient) bp.getRequestContext().get(
				"ws-security.sts.client");

		stsClient.getProperties().put("ws-security.username", cp.getUsername());

		if (StringUtils.hasText(endpointAddress)) {
			bp.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddress);
		}
	}

	/**
	 * Gets the port.
	 * 
	 * @return the port
	 */
	PepPortType getPort() {
		return port;
	}

	/**
	 * Retrieve document set.
	 * 
	 * @param retrieveDocumentSetRequest
	 *            the retrieve document set request
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse retrieveDocumentSet(
			RetrieveDocumentSetRequest retrieveDocumentSetRequest) {

		RetrieveDocumentSetResponse result = port
				.retrieveDocumentSet(retrieveDocumentSetRequest);

		return result;
	}

	/**
	 * Registry stored query.
	 * 
	 * @param registeryStoredQueryRequest
	 *            the registery stored query request
	 * @return the adhoc query response
	 */
	public AdhocQueryResponse registryStoredQuery(
			AdhocQueryRequest registeryStoredQueryRequest) {

		AdhocQueryResponse result = port
				.registryStoredQuery(registeryStoredQueryRequest);

		return result;
	}

	/**
	 * Direct email send.
	 * 
	 * @param directEmailSendRequest
	 *            the direct email send request
	 * @return the direct email send response
	 */
	public DirectEmailSendResponse directEmailSend(
			DirectEmailSendRequest directEmailSendRequest) {

		DirectEmailSendResponse result = port
				.directEmailSend(directEmailSendRequest);

		return result;
	}

	/**
	 * Construct adhoc query.
	 * 
	 * @return the adhoc query request
	 * @throws Exception
	 *             the exception
	 */
	private static AdhocQueryRequest constructAdhocQuery() throws Exception {
		SimpleMarshallerImpl marshaller = new SimpleMarshallerImpl();
		String reqString = " <ns3:AdhocQueryRequest xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\"         xmlns:ns2=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\"         xmlns=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\"       xmlns:ns3=\"urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0\"><ns3:ResponseOption returnType=\"LeafClass\" returnComposedObjects=\"true\"/><AdhocQuery id=\"urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d\"><Slot name=\"$XDSDocumentEntryPatientId\"><ValueList><Value>'d3bb3930-7241-11e3-b4f7-00155d3a2124^^^&amp;2.16.840.1.113883.4.357&amp;ISO'</Value></ValueList></Slot><Slot name=\"$XDSDocumentEntryStatus\"><ValueList><Value>('urn:oasis:names:tc:ebxml-regrep:StatusType:Approved')</Value></ValueList></Slot><Slot name=\"$XDSDocumentEntryFormatCode\"><ValueList><Value>'2.16.840.1.113883.10.20.1^^HITSP'</Value></ValueList></Slot></AdhocQuery></ns3:AdhocQueryRequest>";
		AdhocQueryRequest request = marshaller.unmarshallFromXml(
				AdhocQueryRequest.class, reqString);

		return request;
	}

	/**
	 * Construct retrieve document request.
	 * 
	 * @param documentIds
	 *            the document ids
	 * @return the retrieve document set request
	 * @throws Exception
	 *             the exception
	 */
	private static RetrieveDocumentSetRequest constructRetrieveDocumentRequest(
			LinkedList<String> documentIds) throws Exception {
		SimpleMarshallerImpl marshaller = new SimpleMarshallerImpl();
		StringBuilder builder = new StringBuilder();
		builder.append("<urn:RetrieveDocumentSetRequest xmlns:urn=\"urn:ihe:iti:xds-b:2007\">");
		for (String documentId : documentIds) {
			builder.append("<urn:DocumentRequest><urn:RepositoryUniqueId>1.3.6.1.4.1.21367.2010.1.2.1040</urn:RepositoryUniqueId><urn:DocumentUniqueId>");
			builder.append(documentId);
			builder.append("</urn:DocumentUniqueId></urn:DocumentRequest>");
		}
		builder.append("</urn:RetrieveDocumentSetRequest>");

		RetrieveDocumentSetRequest request = marshaller.unmarshallFromXml(
				RetrieveDocumentSetRequest.class, builder.toString());
		logger.debug("RetrieveDocumentSetRequest XML:" + builder.toString());
		return request;
	}

	/**
	 * Prints the security token.
	 * 
	 * @param port
	 *            the port
	 */
	private static void printSecurityToken(PepPortType port) {
		Client client = ClientProxy.getClient(port);
		Endpoint ep = client.getEndpoint();
		String id = (String) ep.get(SecurityConstants.TOKEN_ID);
		TokenStore store = (TokenStore) ep.getEndpointInfo().getProperty(
				TokenStore.class.getName());
		SecurityToken tok = store.getToken(id);
		Element e = tok.getToken();

		logger.debug("******************** TOKEN ********************");
		logger.debug(DOM2Writer.nodeToString(e));
		logger.debug("******************** TOKEN ********************");
	}

}
