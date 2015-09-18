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

import gov.samhsa.acs.common.cxf.AbstractCXFLoggingConfigurerClient;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.pep.security.CredentialProvider;
import gov.samhsa.acs.pep.security.CredentialProviderImpl;
import gov.samhsa.ds4ppilot.contract.pep.PepPortType;
import gov.samhsa.ds4ppilot.contract.pep.PepService;
import gov.samhsa.ds4ppilot.contract.pep.PepService.PepPortTypeProxy;
import gov.samhsa.ds4ppilot.schema.pep.DirectEmailSendRequest;
import gov.samhsa.ds4ppilot.schema.pep.DirectEmailSendResponse;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;

import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
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
import org.apache.wss4j.common.util.DOM2Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * The Class PepWebServiceClient.
 */
public class PepWebServiceClient extends AbstractCXFLoggingConfigurerClient {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The endpoint address. */
	private final String endpointAddress;

	/** The credential provider. */
	private final CredentialProvider credentialProvider;

	/**
	 * Instantiates a new pep web service client.
	 *
	 * @param endpointAddress
	 *            the endpoint address
	 * @param cp
	 *            the cp
	 */
	public PepWebServiceClient(String endpointAddress, CredentialProvider cp) {
		this.endpointAddress = endpointAddress;
		this.credentialProvider = cp;
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
		try (PepPortTypeProxy port = createPort()) {
			final DirectEmailSendResponse directEmailSend = port
					.directEmailSend(directEmailSendRequest);
			logSecurityTokenIfLoggingEnabled(port);
			return directEmailSend;
		} catch (final Exception e) {
			throw toPepWebServiceClientException(e);
		}
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
		try (PepPortTypeProxy port = createPort()) {
			final AdhocQueryResponse registryStoredQuery = port
					.registryStoredQuery(registeryStoredQueryRequest);
			logSecurityTokenIfLoggingEnabled(port);
			return registryStoredQuery;
		} catch (final Exception e) {
			throw toPepWebServiceClientException(e);
		}
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
		try (PepPortTypeProxy port = createPort()) {
			final RetrieveDocumentSetResponse retrieveDocumentSet = port
					.retrieveDocumentSet(retrieveDocumentSetRequest);
			logSecurityTokenIfLoggingEnabled(port);
			return retrieveDocumentSet;
		} catch (final Exception e) {
			throw toPepWebServiceClientException(e);
		}
	}

	/**
	 * Creates the port.
	 *
	 * @return the pep port type proxy
	 */
	private PepPortTypeProxy createPort() {
		return configurePort(this::createPortProxy);
	}

	/**
	 * Creates the port.
	 *
	 * @return the pep port type proxy
	 */
	private PepPortTypeProxy createPortProxy() {
		final URL wsdlURL = this.getClass().getClassLoader()
				.getResource("Pep.wsdl");

		final QName serviceName = new QName(
				"http://www.samhsa.gov/ds4ppilot/contract/pep", "PepService");

		final PepService service = new PepService(wsdlURL, serviceName);
		final PepPortTypeProxy port = service.getXDSHTTPEndpoint();

		final BindingProvider bp = port;
		final STSClient stsClient = (STSClient) bp.getRequestContext().get(
				"ws-security.sts.client");

		stsClient.getProperties().put("ws-security.username",
				this.credentialProvider.getUsername());

		if (StringUtils.hasText(endpointAddress)) {
			bp.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddress);
		}
		return port;
	}

	/**
	 * Logs the security token.
	 *
	 * @param port
	 *            the port
	 */
	private void logSecurityToken(PepPortType port) {
		final Optional<Endpoint> ep = Optional.of(port)
				.map(ClientProxy::getClient).map(Client::getEndpoint);
		final Optional<String> tokenId = ep.map(
				e -> e.get(SecurityConstants.TOKEN_ID)).map(o -> (String) o);
		final Optional<TokenStore> tokenStore = ep
				.map(Endpoint::getEndpointInfo)
				.map(e -> e.getProperty(TokenStore.class.getName()))
				.map(ts -> (TokenStore) ts);
		final Optional<SecurityToken> securityToken = tokenId
				.flatMap(tid -> tokenStore.map(ts -> ts.getToken(tid)));
		final Optional<Element> tokenElement = securityToken
				.map(SecurityToken::getToken);
		logger.info(tokenElement.map(PepWebServiceClient::tokenElementToString)
				.orElse("ERROR while trying to log SecurityToken"));
	}

	/**
	 * Log security token if logging enabled.
	 *
	 * @param port
	 *            the port
	 */
	private void logSecurityTokenIfLoggingEnabled(PepPortTypeProxy port) {
		if (isEnableLoggingInterceptors()) {
			logSecurityToken(port);
		}
	}

	/**
	 * To pep web service client exception.
	 *
	 * @param exception
	 *            the exception
	 * @return the pep web service client exception
	 */
	private PepWebServiceClientException toPepWebServiceClientException(
			Exception exception) {
		logger.error("Error closing PepWebServiceClient port");
		logger.error(exception.getMessage(), exception);
		return new PepWebServiceClientException(exception);
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws Throwable
	 *             the throwable
	 */
	public static void main(String[] args) throws Throwable {
		try {
			// // set proxy
			// System.setProperty("http.proxyHost", "127.0.0.1");
			// System.setProperty("https.proxyHost", "127.0.0.1");
			// System.setProperty("http.proxyPort", "8888");
			// System.setProperty("https.proxyPort", "8888");

			final Boolean enableCXFLogging = Arrays.stream(args)
					.filter(arg -> arg.startsWith("-enableCXFLogging"))
					.findFirst().map(arg -> arg.split("=")[1])
					.map(Boolean::parseBoolean).orElse(Boolean.FALSE);

			final String address = "http://localhost:8080/Pep-bl/services/PepService";

			final PepWebServiceClient client = new PepWebServiceClient(address,
					new CredentialProviderImpl());
			client.setEnableLoggingInterceptors(enableCXFLogging);

			final AdhocQueryResponse response1 = client
					.registryStoredQuery(constructAdhocQuery());

			final SimpleMarshallerImpl marshaller = new SimpleMarshallerImpl();

			System.out.println("Adhoc Response: "
					+ marshaller.marshal(response1));

			final Scanner scan = new Scanner(System.in);
			System.out.println("Please enter documentUniqueId:");
			final LinkedList<String> documentIds = new LinkedList<String>();
			boolean go = false;
			while (!go && scan.hasNextLine()) {
				final String command = scan.nextLine();
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
			} catch (final Exception e) {
				e.printStackTrace();
			}

			System.out.println("Retrieve Response: "
					+ marshaller.marshal(retrieveDocResp));

			if (null != retrieveDocResp.getDocumentResponse()) {
				System.out.println(new String(retrieveDocResp
						.getDocumentResponse().get(0).getDocument()));
			}
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			// // clean up proxy
			// final Properties sysProps = System.getProperties();
			// sysProps.remove("http.proxyHost");
			// sysProps.remove("https.proxyHost");
			// sysProps.remove("http.proxyPort");
			// sysProps.remove("https.proxyPort");
		}
	}

	/**
	 * Construct adhoc query.
	 *
	 * @return the adhoc query request
	 * @throws Exception
	 *             the exception
	 */
	private static AdhocQueryRequest constructAdhocQuery() throws Exception {
		final SimpleMarshallerImpl marshaller = new SimpleMarshallerImpl();
		final String reqString = " <ns3:AdhocQueryRequest xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\"         xmlns:ns2=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\"         xmlns=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\"       xmlns:ns3=\"urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0\"><ns3:ResponseOption returnType=\"LeafClass\" returnComposedObjects=\"true\"/><AdhocQuery id=\"urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d\"><Slot name=\"$XDSDocumentEntryPatientId\"><ValueList><Value>'d9d460e0-e1fc-11e4-941d-00155d0a6a16^^^&amp;2.16.840.1.113883.4.357&amp;ISO'</Value></ValueList></Slot><Slot name=\"$XDSDocumentEntryStatus\"><ValueList><Value>('urn:oasis:names:tc:ebxml-regrep:StatusType:Approved')</Value></ValueList></Slot><Slot name=\"$XDSDocumentEntryFormatCode\"><ValueList><Value>'2.16.840.1.113883.10.20.1^^HITSP'</Value></ValueList></Slot></AdhocQuery></ns3:AdhocQueryRequest>";
		final AdhocQueryRequest request = marshaller.unmarshalFromXml(
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
		final SimpleMarshallerImpl marshaller = new SimpleMarshallerImpl();
		final StringBuilder builder = new StringBuilder();
		builder.append("<urn:RetrieveDocumentSetRequest xmlns:urn=\"urn:ihe:iti:xds-b:2007\">");
		for (final String documentId : documentIds) {
			builder.append("<urn:DocumentRequest><urn:RepositoryUniqueId>1.3.6.1.4.1.21367.2010.1.2.1040</urn:RepositoryUniqueId><urn:DocumentUniqueId>");
			builder.append(documentId);
			builder.append("</urn:DocumentUniqueId></urn:DocumentRequest>");
		}
		builder.append("</urn:RetrieveDocumentSetRequest>");

		final RetrieveDocumentSetRequest request = marshaller.unmarshalFromXml(
				RetrieveDocumentSetRequest.class, builder.toString());
		System.out.println("RetrieveDocumentSetRequest XML:"
				+ builder.toString());
		return request;
	}

	/**
	 * Token element to string.
	 *
	 * @param e
	 *            the e
	 * @return the string
	 */
	private static final String tokenElementToString(Element e) {
		return new StringBuilder()
				.append("\n******************** TOKEN ********************\n")
				.append(DOM2Writer.nodeToString(e))
				.append("\n******************** TOKEN ********************")
				.toString();
	}
}
