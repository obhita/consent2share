package gov.samhsa.schemas.client;

import gov.samhsa.ds4ppilot.contract.documentprocessor.ProcessDocumentService;
import gov.samhsa.ds4ppilot.contract.documentprocessor.ProcessDocumentServicePortType;
import gov.samhsa.ds4ppilot.schema.documentprocessor.ProcessDocumentRequest;
import gov.samhsa.ds4ppilot.schema.documentprocessor.ProcessDocumentResponse;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Endpoint;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DocumentProcessorClientTest {
	protected static Endpoint ep;
	protected static String address;

	private static ProcessDocumentResponse returnedValueOfProcessDocument;

	@BeforeClass
	public static void setUp() {
		try {
			address = "http://localhost:9000/services/ProcessDocumentService";
			ep = Endpoint.publish(address, new IDocumentProcessorImpl());

			returnedValueOfProcessDocument = new ProcessDocumentResponse();
			returnedValueOfProcessDocument
					.setMaskedDocument("<ClinicalDocument></ClinicalDocument>");
			returnedValueOfProcessDocument.setProcessedDocument(null);
			returnedValueOfProcessDocument.setPostProcessingMetadata(null);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public void testStubWebServiceWorks() {

		IDocumentProcessorImpl.returnedValueOfProcessDocument = returnedValueOfProcessDocument;
		ProcessDocumentRequest request = new ProcessDocumentRequest();
		request.setDocument("<ClinicalDocument></ClinicalDocument>");
		request.setEnforcementPolicies("<xacmlResult><pdpDecision>Permit</pdpDecision><purposeOfUse>TREAT</purposeOfUse><messageId>4617a579-1881-4e40-9f98-f85bd81d6502</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:org:us-privacy-law:42CFRPart2</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:org:refrain-policy:NORDSLCD</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:redact:ETH</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:redact:PSY</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:mask:HIV</pdpObligation></xacmlResult>");
		request.setPackageAsXdm(true);
		request.setEncryptDocument(true);
		request.setSenderEmailAddress("leo.smith@direct.obhita-stage.org");
		request.setRecipientEmailAddress("Duane_Decouteau@direct.healthvault.com");
		request.setXdsDocumentEntryUniqueId("123");
		

		ProcessDocumentResponse resp = createPort().processDocument(request);
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

		IDocumentProcessorImpl.returnedValueOfProcessDocument = returnedValueOfProcessDocument;

		DocumentProcessorWebServiceClient wsc = new DocumentProcessorWebServiceClient(
				address);
		ProcessDocumentResponse resp = wsc.processDocument(document,
				enforcementPolicies, packageAsXdm, encryptDocument,
				senderEmailAddress, recipientEmailAddress, xdsDocumentEntryUniqueId);
		validateResponse(resp);
	}

	private void validateResponse(ProcessDocumentResponse resp) {
		System.out.println("resp.getMaskedDocument(): "
				+ resp.getMaskedDocument());
		Assert.assertEquals("<ClinicalDocument></ClinicalDocument>",
				returnedValueOfProcessDocument.getMaskedDocument(),
				resp.getMaskedDocument());
	}

	private ProcessDocumentServicePortType createPort() {
		final URL WSDL_LOCATION = ClassLoader
				.getSystemResource("DocumentProcessor.wsdl");
		final QName SERVICE = new QName(
				"http://www.samhsa.gov/ds4ppilot/contract/documentprocessor",
				"ProcessDocumentService");

		ProcessDocumentServicePortType port = new ProcessDocumentService(
				WSDL_LOCATION, SERVICE).getProcessDocumentPort();
		BindingProvider bp = (BindingProvider) port;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				address);
		return port;
	}
}
