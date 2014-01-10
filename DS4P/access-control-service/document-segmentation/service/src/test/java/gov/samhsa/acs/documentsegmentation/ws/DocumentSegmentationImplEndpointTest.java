package gov.samhsa.acs.documentsegmentation.ws;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.documentsegmentation.DocumentSegmentation;
import gov.samhsa.acs.documentsegmentation.ws.DocumentSegmentationServiceImpl;
import gov.samhsa.consent2share.contract.documentsegmentation.DocumentSegmentationService;
import gov.samhsa.consent2share.contract.documentsegmentation.DocumentSegmentationServicePortType;
import gov.samhsa.consent2share.schema.documentsegmentation.SegmentDocumentRequest;
import gov.samhsa.consent2share.schema.documentsegmentation.SegmentDocumentResponse;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;

import org.junit.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentSegmentationImplEndpointTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static Endpoint ep;
	private static SegmentDocumentResponse segmentDocumentResponse;
	private static URL wsdlURL;
	private static String address;
	private static QName serviceName;

	private static DocumentSegmentation documentSegmentationMock = mock(DocumentSegmentation.class);

	static {
		serviceName = new QName(
				"http://www.samhsa.gov/consent2share/contract/documentsegmentation",
				"DocumentSegmentationService");
	}

	@Before
	public void setUp() throws Exception {
		address = "http://localhost:9000/services/DocumentSegmentationService";
		wsdlURL = new URL(address + "?wsdl");
		segmentDocumentResponse = new SegmentDocumentResponse();

		segmentDocumentResponse
				.setMaskedDocument("<ClinicalDocument></ClinicalDocument>");
		segmentDocumentResponse.setProcessedDocument(null);

		ep = Endpoint.publish(address, new DocumentSegmentationServiceImpl(
				documentSegmentationMock));
	}

	@After
	public void tearDown() {
		try {
			ep.stop();
		} catch (Throwable t) {
			logger.debug("Error thrown: " + t.getMessage());
		}
	}

	@Test
	public void processDocumentWorksWithGeneratedServiceAndSei()
			throws MalformedURLException {
		DocumentSegmentationService service = new DocumentSegmentationService(
				wsdlURL, serviceName);
		DocumentSegmentationServicePortType port = service
				.getDocumentSegmentationServicePort();
		SegmentDocumentRequest request = new SegmentDocumentRequest();

		when(
				documentSegmentationMock.segmentDocument(null, null, false,
						false, null, null, null)).thenReturn(
				segmentDocumentResponse);

		SegmentDocumentResponse response = port.segmentDocument(request);

		validateResponse(response);
	}

	private void validateResponse(SegmentDocumentResponse resp) {
		logger.debug("resp.getMaskedDocument(): " + resp.getMaskedDocument());
		Assert.assertEquals("<ClinicalDocument></ClinicalDocument>",
				segmentDocumentResponse.getMaskedDocument(),
				resp.getMaskedDocument());
	}
}
