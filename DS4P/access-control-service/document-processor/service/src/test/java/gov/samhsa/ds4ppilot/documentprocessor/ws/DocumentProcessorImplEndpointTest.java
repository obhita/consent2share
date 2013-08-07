package gov.samhsa.ds4ppilot.documentprocessor.ws;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.ds4ppilot.contract.documentprocessor.ProcessDocumentService;
import gov.samhsa.ds4ppilot.contract.documentprocessor.ProcessDocumentServicePortType;
import gov.samhsa.ds4ppilot.documentprocessor.DocumentProcessor;
import gov.samhsa.ds4ppilot.schema.documentprocessor.ProcessDocumentRequest;
import gov.samhsa.ds4ppilot.schema.documentprocessor.ProcessDocumentResponse;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DocumentProcessorImplEndpointTest {
	private static Endpoint ep;

	private static ProcessDocumentResponse processDocumentResponse;
	private static URL wsdlURL;
	private static String address;

	private static QName serviceName;

	private static DocumentProcessor documentProcessorMock = mock(DocumentProcessor.class);

	static {
		serviceName = new QName(
				"http://www.samhsa.gov/ds4ppilot/contract/documentprocessor",
				"ProcessDocumentService");
	}

	@BeforeClass
	public static void setUp() throws Exception {
		address = "http://localhost:9000/services/processDocumentservice";
		wsdlURL = new URL(address + "?wsdl");
		processDocumentResponse = new ProcessDocumentResponse();

		processDocumentResponse
		.setMaskedDocument("<ClinicalDocument></ClinicalDocument>");
		processDocumentResponse.setProcessedDocument(null);

		ep = Endpoint.publish(address, new ProcessDocumentServiceImpl(documentProcessorMock));
	}

	@AfterClass
	public static void tearDown() {
		try {
			ep.stop();
		} catch (Throwable t) {
			System.out.println("Error thrown: " + t.getMessage());
		}
	}

	@Test
	public void processDocumentWorksWithGeneratedServiceAndSei()
			throws MalformedURLException {		
		ProcessDocumentService service = new ProcessDocumentService(wsdlURL, serviceName);
		ProcessDocumentServicePortType port = service.getProcessDocumentPort();
		ProcessDocumentRequest request = new ProcessDocumentRequest();

		when(documentProcessorMock.processDocument(null, null, false, false,
				null, null, null))
				.thenReturn(processDocumentResponse);

		ProcessDocumentResponse response = port.processDocument(request);

		validateResponse(response);
	}

	private void validateResponse(ProcessDocumentResponse resp) {
		System.out.println("resp.getMaskedDocument(): "
				+ resp.getMaskedDocument());
		Assert.assertEquals("<ClinicalDocument></ClinicalDocument>",
				processDocumentResponse.getMaskedDocument(),
				resp.getMaskedDocument());
	}
}
