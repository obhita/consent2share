package gov.samhsa.ds4ppilot.documentprocessor.ws;

import static org.junit.Assert.assertTrue;
import gov.samhsa.consent2share.accesscontrolservice.documentprocessor.brms.RuleExecutionServiceClientImpl;
import gov.samhsa.ds4ppilot.contract.documentprocessor.ProcessDocumentService;
import gov.samhsa.ds4ppilot.contract.documentprocessor.ProcessDocumentServicePortType;
import gov.samhsa.ds4ppilot.documentprocessor.DocumentProcessorImpl;
import gov.samhsa.ds4ppilot.documentprocessor.audit.AuditServiceImpl;
import gov.samhsa.ds4ppilot.documentprocessor.ws.ProcessDocumentServiceImpl;
import gov.samhsa.ds4ppilot.schema.documentprocessor.ProcessDocumentRequest;
import gov.samhsa.ds4ppilot.schema.documentprocessor.ProcessDocumentResponse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class ProcessDocumentServiceImplRuleExecutionServiceIntegrationTest {
	protected static URL wsdlURL;
	protected static QName serviceName;
	protected static QName portName;
	protected static Endpoint ep;
	protected static String address;
	private static String c32Document;
	private static String xacmlResult;
	private static String senderEmailAddress;
	private static String recipientEmailAddress;
	private static String xdsDocumentEntryUniqueId;
	private static String endpointAddressForRuleExectionWebServiceClient;
	private static String endpointAddressForAuditService;

	static {
		serviceName = new QName(
				"http://www.samhsa.gov/ds4ppilot/contract/documentprocessor",
				"ProcessDocumentService");
		portName = new QName(
				"http://www.samhsa.gov/ds4ppilot/contract/documentprocessor",
				"ProcessDocumentPort");
		xacmlResult = "<xacmlResult><pdpDecision>Permit</pdpDecision><purposeOfUse>TREAT</purposeOfUse><messageId>4617a579-1881-4e40-9f98-f85bd81d6502</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:org:us-privacy-law:42CFRPart2</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:org:refrain-policy:NORDSLCD</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:redact:ETH</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:redact:PSY</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:mask:HIV</pdpObligation></xacmlResult>";
		senderEmailAddress = "leo.smith@direct.obhita-stage.org";
		recipientEmailAddress = "Duane_Decouteau@direct.healthvault-stage.com";
		xdsDocumentEntryUniqueId = "123";
		endpointAddressForRuleExectionWebServiceClient = "http://obhitaqaapp01/RuleExecutionService/services/RuleExecutionService";
		endpointAddressForAuditService = "http://174.78.146.228:8080/DS4PACSServices/DS4PAuditService";
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		address = "http://localhost:9000/services/processDocumentservice";
		wsdlURL = new URL(address + "?wsdl");
		c32Document = LoadXMLAsString("c32.xml");
		ep = Endpoint.publish(address, new ProcessDocumentServiceImpl(
				new DocumentProcessorImpl(
						new RuleExecutionServiceClientImpl(
								endpointAddressForRuleExectionWebServiceClient),
						new AuditServiceImpl(endpointAddressForAuditService))));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		try {
			ep.stop();
		} catch (Throwable t) {
			System.out.println("Error thrown: " + t.getMessage());
		}
	}

	/*
	 * This test uses wsimport/wsdl2java generated artifacts, both service and
	 * SEI
	 */
	@Ignore("This test should be configured to run as an integration test.")
	@Test
	public void testProcessDocumentRedactSensitivityETH() throws MalformedURLException {
		ProcessDocumentService pds = new ProcessDocumentService(wsdlURL,
				serviceName);
		ProcessDocumentServicePortType pdspt = pds.getProcessDocumentPort();
		ProcessDocumentRequest param = new ProcessDocumentRequest();
		param.setDocument(c32Document.toString());
		param.setEnforcementPolicies(xacmlResult);
		param.setPackageAsXdm(false);
		param.setEncryptDocument(true);
		param.setSenderEmailAddress(senderEmailAddress);
		param.setRecipientEmailAddress(recipientEmailAddress);
		param.setXdsDocumentEntryUniqueId(xdsDocumentEntryUniqueId);
		ProcessDocumentResponse resp = pdspt.processDocument(param);
		System.out.println("0=====================");
		System.out.println(resp.getMaskedDocument());
		System.out.println("1=====================");
		System.out.println(resp.getPostProcessingMetadata());
		System.out.println("2=====================");
		System.out.println(resp.getKekEncryptionKey());
		System.out.println("3=====================");
		System.out.println(resp.getKekMaskingKey());
		System.out.println("4=====================");
		System.out.println(resp.getProcessedDocument());
		System.out.println("5=====================");

		assertTrue("The processed document should not contain this observation id per 'Substance abuse (disorder)' rule in Guvnor",!resp.getMaskedDocument().contains("e11275e7-67ae-11db-bd13-0800200c9a66b827vs52h7"));
		assertTrue("The processed document should not contain this observation id per 'DUMMY - Acute HIV' ruke un Guvnor (it should have been masked)",!resp.getMaskedDocument().contains("d11275e7-67ae-11db-bd13-0800200c9a66"));
	}

	private static String LoadXMLAsString(String xmlFileName) {
		InputStream in = null;
		StringBuilder c32Document = new StringBuilder();

		try {
			in = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("c32.xml");

			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String line;
			while ((line = br.readLine()) != null) {
				c32Document.append(line);
			}

			br.close();
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return c32Document.toString();
	}

}
