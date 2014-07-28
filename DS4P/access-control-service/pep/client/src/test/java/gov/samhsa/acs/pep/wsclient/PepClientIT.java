package gov.samhsa.acs.pep.wsclient;

import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.pep.security.CredentialProvider;
import gov.samhsa.ds4ppilot.contract.pep.PepPortType;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;

import java.util.LinkedList;
import java.util.Scanner;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.SecurityConstants;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;
import org.apache.cxf.ws.security.tokenstore.TokenStore;
import org.apache.ws.security.util.DOM2Writer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Element;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@ActiveProfiles("test")
public class PepClientIT {

	@Spy
	private PepWebServiceClient client;

	@Autowired
	private CredentialProvider credentialProvider;

	private static Logger logger = LoggerFactory.getLogger(PepWebServiceClient.class);

	@Before
	public void setUp() throws Exception {
		String address = "http://localhost:8080/Pep/services/PepService";
		client = new PepWebServiceClient(address, credentialProvider);
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testPep() throws Exception {

		AdhocQueryResponse response1 = client.registryStoredQuery(constructAdhocQuery());

		printSecurityToken(client.getPort());

		SimpleMarshallerImpl marshaller = new SimpleMarshallerImpl();

		logger.debug("Adhoc Response: " + marshaller.marshal(response1));

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
			retrieveDocResp = client.retrieveDocumentSet(constructRetrieveDocumentRequest(documentIds));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

		logger.debug("Retrieve Response: " + marshaller.marshal(retrieveDocResp));
		if (null == retrieveDocResp.getDocumentResponse())
			logger.debug(new String(retrieveDocResp.getDocumentResponse().get(0).getDocument()));
	}

	private static AdhocQueryRequest constructAdhocQuery() throws Exception {
		SimpleMarshallerImpl marshaller = new SimpleMarshallerImpl();
		String reqString = " <ns3:AdhocQueryRequest xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\"         xmlns:ns2=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\"         xmlns=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\"       xmlns:ns3=\"urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0\"><ns3:ResponseOption returnType=\"LeafClass\" returnComposedObjects=\"true\"/><AdhocQuery id=\"urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d\"><Slot name=\"$XDSDocumentEntryPatientId\"><ValueList><Value>'d3bb3930-7241-11e3-b4f7-00155d3a2124^^^&amp;2.16.840.1.113883.4.357&amp;ISO'</Value></ValueList></Slot><Slot name=\"$XDSDocumentEntryStatus\"><ValueList><Value>('urn:oasis:names:tc:ebxml-regrep:StatusType:Approved')</Value></ValueList></Slot><Slot name=\"$XDSDocumentEntryFormatCode\"><ValueList><Value>'2.16.840.1.113883.10.20.1^^HITSP'</Value></ValueList></Slot></AdhocQuery></ns3:AdhocQueryRequest>";
		AdhocQueryRequest request = marshaller.unmarshalFromXml(AdhocQueryRequest.class, reqString);

		return request;
	}

	private static RetrieveDocumentSetRequest constructRetrieveDocumentRequest(LinkedList<String> documentIds)
			throws Exception {
		SimpleMarshallerImpl marshaller = new SimpleMarshallerImpl();
		StringBuilder builder = new StringBuilder();
		builder.append("<urn:RetrieveDocumentSetRequest xmlns:urn=\"urn:ihe:iti:xds-b:2007\">");
		for (String documentId : documentIds) {
			builder.append("<urn:DocumentRequest><urn:RepositoryUniqueId>1.3.6.1.4.1.21367.2010.1.2.1040</urn:RepositoryUniqueId><urn:DocumentUniqueId>");
			builder.append(documentId);
			builder.append("</urn:DocumentUniqueId></urn:DocumentRequest>");
		}
		builder.append("</urn:RetrieveDocumentSetRequest>");

		RetrieveDocumentSetRequest request = marshaller.unmarshalFromXml(RetrieveDocumentSetRequest.class,
				builder.toString());
		logger.debug("RetrieveDocumentSetRequest XML:" + builder.toString());
		return request;
	}

	private static void printSecurityToken(PepPortType port) {
		Client client = ClientProxy.getClient(port);
		Endpoint ep = client.getEndpoint();
		String id = (String) ep.get(SecurityConstants.TOKEN_ID);
		TokenStore store = (TokenStore) ep.getEndpointInfo().getProperty(TokenStore.class.getName());
		SecurityToken tok = store.getToken(id);
		Element e = tok.getToken();

		logger.debug("******************** TOKEN ********************");
		logger.debug(DOM2Writer.nodeToString(e));
		logger.debug("******************** TOKEN ********************");
	}
}
