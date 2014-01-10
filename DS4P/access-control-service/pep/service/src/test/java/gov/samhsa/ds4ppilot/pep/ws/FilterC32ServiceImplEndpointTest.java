package gov.samhsa.ds4ppilot.pep.ws;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.pep.Pep;
import gov.samhsa.acs.pep.ws.FilterC32ServiceImpl;
import gov.samhsa.ds4ppilot.contract.pep.FilterC32Service;
import gov.samhsa.ds4ppilot.contract.pep.FilterC32ServicePortType;
import gov.samhsa.ds4ppilot.schema.pep.FilterC32Request;
import gov.samhsa.ds4ppilot.schema.pep.FilterC32Response;
import gov.samhsa.ds4ppilot.schema.pep.RetrieveDocumentSetRequest;
import gov.samhsa.ds4ppilot.schema.pep.RetrieveDocumentSetResponse;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

public class FilterC32ServiceImplEndpointTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FilterC32ServiceImplEndpointTest.class);

	private static URL wsdlURL;
	private static String address;
	private static QName serviceName;
	private static QName portName;

	private static Endpoint ep;

	private static FilterC32Response filterC32Response;
	private static RetrieveDocumentSetResponse retrieveDocumentSetResponse;

	private static String filterC32SOAPMessageString;
	private static String filterC32SOAPMessagePayloadString;
	private static final int LENGTH = 1;

	private static Pep orchrstratorMock = mock(Pep.class);

	@BeforeClass
	public static void setUp() throws Exception {
		serviceName = new QName(
				"http://www.samhsa.gov/ds4ppilot/contract/pep",
				"FilterC32Service");
		portName = new QName(
				"http://www.samhsa.gov/ds4ppilot/contract/pep",
				"FilterC32Port");

		address = "http://localhost:9000/services/filterc32service";
		wsdlURL = new URL(address + "?wsdl");

		filterC32Response = new FilterC32Response();
		filterC32Response.setPatientId("12345");
		filterC32Response.setPdpDecision("permit");
		filterC32Response.setFilteredStreamBody(new byte[LENGTH]);
		filterC32SOAPMessagePayloadString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ns2:FilterC32Response><patientId>12345</patientId><pdpDecision>permit</pdpDecision><filteredStreamBody>AA==</filteredStreamBody></ns2:FilterC32Response>";
		filterC32SOAPMessageString = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Header/><soap:Body><ns2:FilterC32Response xmlns:ns2=\"http://www.samhsa.gov/ds4ppilot/schema/pep\"><patientId>12345</patientId><pdpDecision>permit</pdpDecision><filteredStreamBody>AA==</filteredStreamBody></ns2:FilterC32Response></soap:Body></soap:Envelope>";

		retrieveDocumentSetResponse = new RetrieveDocumentSetResponse();
		retrieveDocumentSetResponse
		.setReturn("<ns3:RetrieveDocumentSetResponse xmlns:ns2=\"urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0\" xmlns=\"urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0\" xmlns:ns4=\"urn:oasis:names:tc:ebxml-regrep:xsd:query:3.0\" xmlns:ns3=\"urn:ihe:iti:xds-b:2007\"> <ns2:RegistryResponse status=\"urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success\"/> <ns3:DocumentResponse> <ns3:RepositoryUniqueId>1.3.6.1.4.1.21367.2010.1.2.1040</ns3:RepositoryUniqueId> <ns3:DocumentUniqueId>$uniqueId06</ns3:DocumentUniqueId> <ns3:mimeType>text/plain</ns3:mimeType> <ns3:Document>VGhlIHF1aWNrIGJyb3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZy4NClRoZSBxdWljayBicm93biBmb3gganVtcHMgb3ZlciB0aGUgbGF6eSBkb2cuDQpUaGUgcXVpY2sgYnJvd24gZm94IGp1bXBzIG92ZXIgdGhlIGxhenkgZG9nLg0KVGhlIHF1aWNrIGJyb3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZy4NClRoZSBxdWljayBicm93biBmb3gganVtcHMgb3ZlciB0aGUgbGF6eSBkb2cuDQpUaGUgcXVpY2sgYnJvd24gZm94IGp1bXBzIG92ZXIgdGhlIGxhenkgZG9nLg0KVGhlIHF1aWNrIGJyb3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZy4NClRoZSBxdWljayBicm93biBmb3gganVtcHMgb3ZlciB0aGUgbGF6eSBkb2cuDQpUaGUgcXVpY2sgYnJvd24gZm94IGp1bXBzIG92ZXIgdGhlIGxhenkgZG9nLg0KVGhlIHF1aWNrIGJyb3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZy4NClRoZSBxdWljayBicm93biBmb3gganVtcHMgb3ZlciB0aGUgbGF6eSBkb2cuDQpUaGUgcXVpY2sgYnJvd24gZm94IGp1bXBzIG92ZXIgdGhlIGxhenkgZG9nLg0KVGhlIHF1aWNrIGJyb3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZy4NClRoZSBxdWljayBicm93biBmb3gganVtcHMgb3ZlciB0aGUgbGF6eSBkb2cuDQpUaGUgcXVpY2sgYnJvd24gZm94IGp1bXBzIG92ZXIgdGhlIGxhenkgZG9nLg==</ns3:Document> </ns3:DocumentResponse> </ns3:RetrieveDocumentSetResponse>");

		// portName is same as the value specified in FilterC32ServiceImpl class
		// @WebService annotation
		ep = Endpoint.publish(address, new FilterC32ServiceImpl(
				orchrstratorMock));
	}

	@AfterClass
	public static void tearDown() {
		try {
			ep.stop();
		} catch (Throwable t) {
			LOGGER.debug("Error thrown: " + t.getMessage());
		}
	}

	/*
	 * This test uses wsimport/wsdl2java generated artifacts, both service and
	 * SEI
	 */
	@Test
	public void filterC32WithGeneratedServiceAndSei()
			throws MalformedURLException {
		// FilterC32Service service = new FilterC32Service(null, serviceName);
		// Add this line because of null wsdl location when initiate
		// FilterC32Service
		// service.addPort( FilterC32Service.FilterC32Port,
		// SOAPBinding.SOAP11HTTP_BINDING, address );

		FilterC32Service service = new FilterC32Service(wsdlURL, serviceName);
		FilterC32ServicePortType port = service.getFilterC32Port();
		FilterC32Request request = new FilterC32Request();

		when(orchrstratorMock.handleC32Request(null, false, null, null))
		.thenReturn(filterC32Response);

		FilterC32Response respoonse = port.filterC32(request);

		validateResponse(respoonse);
	}

	/*
	 * This test uses raw Service class for service, wsimport/wsdl2java *
	 * generated SEI
	 */
	@Test
	public void filterC32WorksWithRawServiceAndSei()
			throws MalformedURLException {
		Service jaxwsService = Service.create(wsdlURL, serviceName);
		FilterC32ServicePortType port = jaxwsService
				.getPort(FilterC32ServicePortType.class);
		FilterC32Request request = new FilterC32Request();

		when(orchrstratorMock.handleC32Request(null, false, null, null))
		.thenReturn(filterC32Response);

		FilterC32Response response = port.filterC32(request);

		validateResponse(response);
	}

	/*
	 * This test uses wsimport/wsdl2java generated artifacts, both service and
	 * SEI
	 */
	@Test
	public void retrieveDocumentSetWithGeneratedServiceAndSei()
			throws MalformedURLException {

		FilterC32Service service = new FilterC32Service(wsdlURL, serviceName);
		FilterC32ServicePortType port = service.getFilterC32Port();
		RetrieveDocumentSetRequest request = new RetrieveDocumentSetRequest();

		when(
				orchrstratorMock.retrieveDocumentSetRequest(null, null, null,
						null, null)).thenReturn(retrieveDocumentSetResponse);

		RetrieveDocumentSetResponse response = port
				.retrieveDocumentSet(request);

		validateRetrieveDocumentSetResponse(response);
	}

	/*
	 * This test uses raw Service class for service, wsimport/wsdl2java *
	 * generated SEI
	 */
	@Test
	public void retrieveDocumentSetWorksWithRawServiceAndSei()
			throws MalformedURLException {
		Service jaxwsService = Service.create(wsdlURL, serviceName);
		FilterC32ServicePortType port = jaxwsService
				.getPort(FilterC32ServicePortType.class);
		RetrieveDocumentSetRequest request = new RetrieveDocumentSetRequest();

		when(
				orchrstratorMock.retrieveDocumentSetRequest(null, null, null,
						null, null)).thenReturn(retrieveDocumentSetResponse);

		RetrieveDocumentSetResponse response = port
				.retrieveDocumentSet(request);

		validateRetrieveDocumentSetResponse(response);
	}

	/*
	 * This test uses raw Service class for service, Dispatch<SOAPMessage> for
	 * client No wsimport/wsdl2java needed. Note works with full SOAP message
	 * (Service.Mode.MESSAGE)
	 */
	@Ignore()
	@Test
	public void filterC32WorksWithRawServiceAndDispatchOfSOAPMessage()
			throws Exception {
		Service jaxwsService = Service.create(wsdlURL, serviceName);
		Dispatch<SOAPMessage> disp = jaxwsService.createDispatch(portName,
				SOAPMessage.class, Service.Mode.MESSAGE);
		InputStream is = getClass().getClassLoader().getResourceAsStream(
				"fullSOAPMessage.xml");
		SOAPMessage reqMsg = MessageFactory.newInstance().createMessage(null,
				is);
		when(
				orchrstratorMock.handleC32Request(anyString(), anyBoolean(),
						anyString(), anyString()))
						.thenReturn(filterC32Response);

		SOAPMessage responseSOAPMessage = disp.invoke(reqMsg);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		responseSOAPMessage.writeTo(out);
		String responseSOAPMessageString = new String(out.toByteArray());

		assertEquals("Filter C32 Service not returning expected SOAP message",
				filterC32SOAPMessageString, responseSOAPMessageString);
	}

	/*
	 * This test uses raw Service class for service, Dispatch<Source> for
	 * client. No wsimport/wsdl2java run needed. Uses payload (soap:body
	 * contents) only (Service.Mode.PAYLOAD), but can be configured to use
	 * MESSAGE. Note CXF supports other options such as Dispatch<DOMSource>,
	 * Dispatch<SAXSource>, and Dispatch<StreamSource>, search CXF source code
	 * for examples.
	 */
	@Ignore
	@Test
	public void filterC32WorksWithRawServiceAndDispatchOfSource()
			throws Exception {

		Service jaxwsService = Service.create(wsdlURL, serviceName);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputStream is = getClass().getClassLoader().getResourceAsStream(
				"justPayload.xml");
		Document newDoc = builder.parse(is);
		DOMSource request = new DOMSource(newDoc);
		// Below works for both CXF and Metro:
		Dispatch<Source> disp = jaxwsService.createDispatch(portName,
				Source.class, Service.Mode.PAYLOAD);

		when(
				orchrstratorMock.handleC32Request(anyString(), anyBoolean(),
						anyString(), anyString()))
						.thenReturn(filterC32Response);

		Source responsePayloadSource = disp.invoke(request);
		Transformer trans = TransformerFactory.newInstance().newTransformer();
		// DOMResult domResult = new DOMResult();
		StreamResult streamResult = new StreamResult();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		streamResult.setOutputStream(out);
		trans.transform(responsePayloadSource, streamResult);

		String responsePayload = new String(out.toByteArray());

		assertEquals("Filter C32 Service not returning expected response",
				filterC32SOAPMessagePayloadString, responsePayload);
	}

	/*
	 * This test uses raw Service class for service, Dispatch<JAXBContext> for
	 * client. Conveniently uses JAX-WS generated artifacts.
	 */
	// @Test
	/*
	 * public void filterC32WorksWithRawServiceAndDispatchOfJAXBContext() throws
	 * Exception { when(orchrstratorMock.handleC32Request(null, false, null,
	 * null)) .thenReturn(filterC32Response);
	 * 
	 * Service jaxwsService = Service.create(wsdlURL, serviceName); JAXBContext
	 * jaxbContext = JAXBContext
	 * .newInstance("gov.samhsa.ds4ppilot.schema.pep");
	 * Dispatch<Object> jaxbDispatch = jaxwsService.createDispatch(portName,
	 * jaxbContext, Service.Mode.PAYLOAD); FilterC32Request request = new
	 * FilterC32Request(); JAXBElement<FilterC32Request> requestElement = new
	 * JAXBElement<FilterC32Request>( new QName(
	 * "http://www.samhsa.gov/ds4ppilot/contract/pep",
	 * "FilterC32Request"), FilterC32Request.class, request);
	 * 
	 * FilterC32Response response = (FilterC32Response) jaxbDispatch
	 * .invoke(requestElement);
	 * 
	 * validateResponse(response); }
	 */

	private void validateResponse(FilterC32Response response) {
		assertEquals("Filter C32 Service not returning expected Patient ID",
				filterC32Response.getPatientId(), response.getPatientId());
		assertEquals("Filter C32 Service not returning expected PDP decision",
				filterC32Response.getPdpDecision(), response.getPdpDecision());
		assertEquals(
				"Filter C32 Service not returning expected document bytes",
				LENGTH, response.getFilteredStreamBody().length);
	}

	private void validateRetrieveDocumentSetResponse(
			RetrieveDocumentSetResponse response) {
		assertEquals("<ns3:RetrieveDocument",
				response.getReturn().substring(0, 21));

	}
}
