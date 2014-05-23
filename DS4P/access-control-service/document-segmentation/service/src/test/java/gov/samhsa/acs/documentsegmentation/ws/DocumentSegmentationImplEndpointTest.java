package gov.samhsa.acs.documentsegmentation.ws;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.common.dto.XacmlRequest;
import gov.samhsa.acs.common.validation.exception.XmlDocumentReadFailureException;
import gov.samhsa.acs.documentsegmentation.DocumentSegmentation;
import gov.samhsa.acs.documentsegmentation.exception.InvalidOriginalClinicalDocumentException;
import gov.samhsa.acs.documentsegmentation.exception.InvalidSegmentedClinicalDocumentException;
import gov.samhsa.acs.documentsegmentation.ws.DocumentSegmentationServiceImpl;
import gov.samhsa.consent2share.contract.documentsegmentation.DocumentSegmentationService;
import gov.samhsa.consent2share.contract.documentsegmentation.DocumentSegmentationServicePortType;
import gov.samhsa.consent2share.schema.documentsegmentation.SegmentDocumentRequest;
import gov.samhsa.consent2share.schema.documentsegmentation.SegmentDocumentResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.soap.SOAPFaultException;

import org.junit.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.audit.AuditException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class DocumentSegmentationImplEndpointTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static Endpoint ep;
	private static SegmentDocumentResponse segmentDocumentResponse;
	private static URL wsdlURL;
	private static String address;
	private static QName serviceName;

	private static DocumentSegmentation documentSegmentationMock = mock(DocumentSegmentation.class);
	private static XacmlRequest xacmlRequestMock = mock(XacmlRequest.class);

	static {
		serviceName = new QName(
				"http://www.samhsa.gov/consent2share/contract/documentsegmentation",
				"DocumentSegmentationService");
	}

	@Before
	public void setUp() throws Exception {
		Resource resource = new ClassPathResource("/jettyServerPortForTesing.properties");
    	Properties props = PropertiesLoaderUtils.loadProperties(resource);
    	String portNumber = props.getProperty("jettyServerPortForTesing.number");

        address = String.format("http://localhost:%s/services/DocumentSegmentationService", portNumber);
        
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

	@Test(expected = SOAPFaultException.class)
	public void processDocumentWorksWithGeneratedServiceAndSei()
			throws MalformedURLException, InvalidOriginalClinicalDocumentException, InvalidSegmentedClinicalDocumentException, XmlDocumentReadFailureException, AuditException {
		DocumentSegmentationService service = new DocumentSegmentationService(
				wsdlURL, serviceName);
		DocumentSegmentationServicePortType port = service
				.getDocumentSegmentationServicePort();
		SegmentDocumentRequest request = new SegmentDocumentRequest();

		when(
				documentSegmentationMock.segmentDocument(null, null, false,
						false, null, null, null,xacmlRequestMock, false)).thenReturn(
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
