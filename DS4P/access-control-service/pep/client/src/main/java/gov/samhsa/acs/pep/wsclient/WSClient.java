package gov.samhsa.acs.pep.wsclient;

import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.pep.security.CredentialProvider;
import gov.samhsa.ds4ppilot.contract.pep.PepPortType;
import gov.samhsa.ds4ppilot.contract.pep.PepService;
import gov.samhsa.ds4ppilot.schema.pep.DirectEmailSendRequest;
import gov.samhsa.ds4ppilot.schema.pep.DirectEmailSendResponse;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Scanner;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.ws.security.SecurityConstants;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.tokenstore.TokenStore;
import org.apache.cxf.ws.security.trust.STSClient;
import org.apache.ws.security.util.DOM2Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class WSClient {

	private final String wsdlFileName = "Pep.wsdl";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The endpoint address. */
	private String endpointAddress;
	private CredentialProvider cp;
	private static URL wsdlURL = null;
	private static QName serviceName = new QName(
			"http://www.samhsa.gov/ds4ppilot/contract/pep", "PepService");

	public static void main(String[] args) throws Throwable {
		String address = "http://localhost:8080/Pep/services/PepService";
		Properties prop = new Properties();
		try {
			wsdlURL = new URL(address + "?wsdl");
			// load a properties file from class path, inside static method
			prop.load(WSClient.class.getClassLoader().getResourceAsStream(
					"issuer.properties"));

			System.out.println("WSDL url: " + wsdlURL);

			PepService service = new PepService(wsdlURL, serviceName);
			PepPortType port = service.getXDSHTTPEndpoint();

			BindingProvider bp = (BindingProvider) port;
			STSClient stsClient = (STSClient) bp.getRequestContext().get(
					"ws-security.sts.client");

			stsClient.getProperties().put("ws-security.username", "alice");

			Client client = ClientProxy.getClient(port);
			client.getInInterceptors().add(new LoggingInInterceptor());
			client.getOutInterceptors().add(new LoggingOutInterceptor());

			AdhocQueryResponse response1 = port
					.registryStoredQuery(constructAdhocQuery());
			SimpleMarshallerImpl marshaller = new SimpleMarshallerImpl();

			Scanner scan = new Scanner(System.in);
			System.out.println("Please enter documentUniqueId:");
			LinkedList<String> documentIds = new LinkedList<String>();
			boolean go = false;
			while(!go && scan.hasNextLine()){
				String command = scan.nextLine();
				if("go".equalsIgnoreCase(command)){
					go = true;
				}
				else{
					documentIds.add(command);
				}
			}
			scan.close();
			//String documentUniqueId = scan.nextLine();
			RetrieveDocumentSetResponse retrieveDocResp = port
					.retrieveDocumentSet(constructRetrieveDocumentRequest(documentIds));

			// FileHelper.writeBytesToFile(retrieveDocResp.getDocumentResponse()
			// .get(0).getDocument(), "retrievedDocumentForTest.xml");

			// Direct
			/*
			 * DirectEmailSendRequest directEmailSendRequest = new
			 * DirectEmailSendRequest();
			 * directEmailSendRequest.setSenderNPI("1114252178");
			 * directEmailSendRequest.setRecipientNPI("1760717789");
			 * directEmailSendRequest .setResourceId(
			 * "d3bb3930-7241-11e3-b4f7-00155d3a2124^^^&2.16.840.1.113883.4.357&ISO"
			 * ); directEmailSendRequest.setPurposeOfUse("TREAT");
			 * DirectEmailSendResponse directEmailSendResponse = port
			 * .directEmailSend(directEmailSendRequest);
			 * System.out.println("Direct response: " +
			 * directEmailSendResponse.getPdpDecision());
			 */

			printSecurityToken(port);

			System.out.println("Adhoc Response: "
					+ marshaller.marshall(response1));
			System.out.println("Retrieve Response: "
					+ marshaller.marshall(retrieveDocResp));
			System.out.println(new String(retrieveDocResp.getDocumentResponse()
					.get(0).getDocument()));

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public WSClient(String endpointAddress) {
		// Put the user name in the client

		this.endpointAddress = endpointAddress;
	}

	public WSClient(String endpointAddress, CredentialProvider cp) {
		// Put the user name in the client
		this.cp = cp;
		this.endpointAddress = endpointAddress;
		try {
			wsdlURL = new URL(endpointAddress + "?wsdl");
		} catch (MalformedURLException ex) {
			logger.error("URL not formed properly");
		}

		PepService service = new PepService(wsdlURL, serviceName);
		PepPortType port = service.getXDSHTTPEndpoint();

		BindingProvider bp = (BindingProvider) port;
		STSClient stsClient = (STSClient) bp.getRequestContext().get(
				"ws-security.sts.client");

		stsClient.getProperties().put("ws-security.username",
				CredentialProvider.getUser());
	}

	public RetrieveDocumentSetResponse retrieveDocumentSet(
			RetrieveDocumentSetRequest retrieveDocumentSetRequest) {
		PepPortType port = createPort();

		RetrieveDocumentSetResponse result = port
				.retrieveDocumentSet(retrieveDocumentSetRequest);

		return result;
	}

	public AdhocQueryResponse registerStoredQuery(
			AdhocQueryRequest registeryStoredQueryRequest) {
		PepPortType port = createPort();

		AdhocQueryResponse result = port
				.registryStoredQuery(registeryStoredQueryRequest);

		return result;
	}

	public DirectEmailSendResponse directEmailSend(
			DirectEmailSendRequest directEmailSendRequest) {
		PepPortType port = createPort();

		DirectEmailSendResponse result = port
				.directEmailSend(directEmailSendRequest);

		return result;
	}

	private PepPortType createPort() {
		final URL WSDL_LOCATION = this.getClass().getClassLoader()
				.getResource(wsdlFileName);
		final QName SERVICE = new QName(
				"http://www.samhsa.gov/ds4ppilot/contract/pep", "PepService");

		PepPortType port = new PepService(WSDL_LOCATION, SERVICE)
				.getXDSHTTPEndpoint();

		if (StringUtils.hasText(this.endpointAddress)) {
			BindingProvider bp = (BindingProvider) port;
			bp.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddress);
		}

		return port;
	}

	private static AdhocQueryRequest constructAdhocQuery() throws Exception {
		SimpleMarshallerImpl marshaller = new SimpleMarshallerImpl();
		String reqString = " <ns3:AdhocQueryRequest xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\"         xmlns:ns2=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\"         xmlns=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\"       xmlns:ns3=\"urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0\"><ns3:ResponseOption returnType=\"LeafClass\" returnComposedObjects=\"true\"/><AdhocQuery id=\"urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d\"><Slot name=\"$XDSDocumentEntryPatientId\"><ValueList><Value>'d3bb3930-7241-11e3-b4f7-00155d3a2124^^^&amp;2.16.840.1.113883.4.357&amp;ISO'</Value></ValueList></Slot><Slot name=\"$XDSDocumentEntryStatus\"><ValueList><Value>('urn:oasis:names:tc:ebxml-regrep:StatusType:Approved')</Value></ValueList></Slot><Slot name=\"$XDSDocumentEntryFormatCode\"><ValueList><Value>'2.16.840.1.113883.10.20.1^^HITSP'</Value></ValueList></Slot></AdhocQuery></ns3:AdhocQueryRequest>";
		AdhocQueryRequest request = marshaller.unmarshallFromXml(
				AdhocQueryRequest.class, reqString);

		return request;
	}

	private static RetrieveDocumentSetRequest constructRetrieveDocumentRequest(String documentUniqueId)
			throws Exception {
		SimpleMarshallerImpl marshaller = new SimpleMarshallerImpl();
		String retrieveDocumentSetString = "<urn:RetrieveDocumentSetRequest xmlns:urn=\"urn:ihe:iti:xds-b:2007\"><urn:DocumentRequest><urn:RepositoryUniqueId>1.3.6.1.4.1.21367.2010.1.2.1040</urn:RepositoryUniqueId><urn:DocumentUniqueId>"+documentUniqueId+"</urn:DocumentUniqueId></urn:DocumentRequest></urn:RetrieveDocumentSetRequest>";
		RetrieveDocumentSetRequest request = marshaller.unmarshallFromXml(
				RetrieveDocumentSetRequest.class, retrieveDocumentSetString);

		return request;
	}
	
	private static RetrieveDocumentSetRequest constructRetrieveDocumentRequest(LinkedList<String> documentIds)
			throws Exception {
		SimpleMarshallerImpl marshaller = new SimpleMarshallerImpl();
		StringBuilder builder = new StringBuilder();
		builder.append("<urn:RetrieveDocumentSetRequest xmlns:urn=\"urn:ihe:iti:xds-b:2007\">");
		for(String documentId : documentIds){
			builder.append("<urn:DocumentRequest><urn:RepositoryUniqueId>1.3.6.1.4.1.21367.2010.1.2.1040</urn:RepositoryUniqueId><urn:DocumentUniqueId>");
			builder.append(documentId);
			builder.append("</urn:DocumentUniqueId></urn:DocumentRequest>");
		}
		builder.append("</urn:RetrieveDocumentSetRequest>");

		RetrieveDocumentSetRequest request = marshaller.unmarshallFromXml(
				RetrieveDocumentSetRequest.class, builder.toString());

		return request;
	}

	private static void printSecurityToken(PepPortType port) {
		Client client = ClientProxy.getClient(port);
		Endpoint ep = client.getEndpoint();
		String id = (String) ep.get(SecurityConstants.TOKEN_ID);
		TokenStore store = (TokenStore) ep.getEndpointInfo().getProperty(
				TokenStore.class.getName());
		SecurityToken tok = store.getToken(id);
		Element e = tok.getToken();

		System.out.println("******************** TOKEN ********************");
		System.out.println(DOM2Writer.nodeToString(e));
		System.out.println("******************** TOKEN ********************");
	}

}
