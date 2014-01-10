package gov.samhsa.acs.documentsegmentation.wsclient;

import gov.samhsa.acs.documentsegmentation.wsclient.DocumentSegmentationWebServiceClient;
import gov.samhsa.consent2share.schema.documentsegmentation.SegmentDocumentRequest;
import gov.samhsa.consent2share.schema.documentsegmentation.SegmentDocumentResponse;
import gov.samhsa.consent2share.contract.documentsegmentation.DocumentSegmentationService;
import gov.samhsa.consent2share.contract.documentsegmentation.DocumentSegmentationServicePortType;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Endpoint;

import org.junit.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentSegmentationClientTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	protected static Endpoint ep;
	protected static String address;

	private static SegmentDocumentResponse returnedValueOfProcessDocument;

	@Before
	public void setUp() {
		try {
			address = "http://localhost:9000/services/DocumentSegmentationService";
			ep = Endpoint.publish(address,
					new DocumentSegmentationServicePortTypeImpl());

			returnedValueOfProcessDocument = new SegmentDocumentResponse();
			returnedValueOfProcessDocument
					.setMaskedDocument("<ClinicalDocument></ClinicalDocument>");
			returnedValueOfProcessDocument.setProcessedDocument(null);
			returnedValueOfProcessDocument.setPostProcessingMetadata(null);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@After
	public void tearDown() {
		try {
			ep.stop();
		} catch (Throwable t) {
			logger.debug("Error thrown: " + t.getMessage());
		}
	}

	// Test if the stub web service activate properly
	@Test
	public void testStubWebServiceWorks() {

		DocumentSegmentationServicePortTypeImpl.returnedValueOfProcessDocument = returnedValueOfProcessDocument;
		SegmentDocumentRequest request = new SegmentDocumentRequest();
		request.setDocument("<ClinicalDocument></ClinicalDocument>");
		request.setEnforcementPolicies("<xacmlResult><pdpDecision>Permit</pdpDecision><purposeOfUse>TREAT</purposeOfUse><messageId>4617a579-1881-4e40-9f98-f85bd81d6502</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:org:us-privacy-law:42CFRPart2</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:org:refrain-policy:NORDSLCD</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:redact:ETH</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:redact:PSY</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:mask:HIV</pdpObligation></xacmlResult>");
		request.setPackageAsXdm(true);
		request.setEncryptDocument(true);
		request.setSenderEmailAddress("leo.smith@direct.obhita-stage.org");
		request.setRecipientEmailAddress("Duane_Decouteau@direct.healthvault.com");
		request.setXdsDocumentEntryUniqueId("123");

		SegmentDocumentResponse resp = createPort().segmentDocument(request);
		validateResponse(resp);
	}

	// Test if the SOAP client calling the stub web service correctly?
	@Test
	public void testWSClientSOAPCallWorks() {

		String document = "<ClinicalDocument></ClinicalDocument>";
		String enforcementPolicies = "<xacmlResult><pdpDecision>Permit</pdpDecision><purposeOfUse>TREAT</purposeOfUse><messageId>4617a579-1881-4e40-9f98-f85bd81d6502</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:org:us-privacy-law:42CFRPart2</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:org:refrain-policy:NORDSLCD</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:redact:ETH</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:redact:PSY</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:mask:HIV</pdpObligation></xacmlResult>";
		boolean packageAsXdm = true;
		boolean encryptDocument = true;
		String senderEmailAddress = "leo.smith@direct.obhita-stage.org";
		String recipientEmailAddress = "Duane_Decouteau@direct.healthvault.com";
		String xdsDocumentEntryUniqueId = "123";

		DocumentSegmentationServicePortTypeImpl.returnedValueOfProcessDocument = returnedValueOfProcessDocument;

		DocumentSegmentationWebServiceClient wsc = new DocumentSegmentationWebServiceClient(
				address);
		SegmentDocumentResponse resp = wsc.segmentDocument(document,
				enforcementPolicies, packageAsXdm, encryptDocument,
				senderEmailAddress, recipientEmailAddress,
				xdsDocumentEntryUniqueId);
		validateResponse(resp);
	}

	private void validateResponse(SegmentDocumentResponse resp) {
		logger.debug("resp.getMaskedDocument(): " + resp.getMaskedDocument());
		Assert.assertEquals("<ClinicalDocument></ClinicalDocument>",
				returnedValueOfProcessDocument.getMaskedDocument(),
				resp.getMaskedDocument());
	}

	private DocumentSegmentationServicePortType createPort() {
		final URL WSDL_LOCATION = ClassLoader
				.getSystemResource("DocumentSegmentationService.wsdl");
		final QName SERVICE = new QName(
				"http://www.samhsa.gov/consent2share/contract/documentsegmentation",
				"DocumentSegmentationService");

		DocumentSegmentationServicePortType port = new DocumentSegmentationService(
				WSDL_LOCATION, SERVICE).getDocumentSegmentationServicePort();
		BindingProvider bp = (BindingProvider) port;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				address);
		return port;
	}
}
