/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.schemas.client;

import gov.samhsa.ds4ppilot.contract.documentprocessor.ProcessDocumentService;
import gov.samhsa.ds4ppilot.contract.documentprocessor.ProcessDocumentServicePortType;
import gov.samhsa.ds4ppilot.schema.documentprocessor.ProcessDocumentRequest;
import gov.samhsa.ds4ppilot.schema.documentprocessor.ProcessDocumentResponse;

import java.net.URL;
import java.util.Date;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * The Class DocumentProcessorWebServiceClient.
 */
public class DocumentProcessorWebServiceClient {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DocumentProcessorWebServiceClient.class);

	/** The endpoint address. */
	private final String endpointAddress;

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		String document = "<ClinicalDocument></ClinicalDocument>";
		String enforcementPolicies = "<xacmlResult><pdpDecision>Permit</pdpDecision><purposeOfUse>TREAT</purposeOfUse><messageId>4617a579-1881-4e40-9f98-f85bd81d6502</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:org:us-privacy-law:42CFRPart2</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:org:refrain-policy:NORDSLCD</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:redact:ETH</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:redact:PSY</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:mask:HIV</pdpObligation></xacmlResult>";
		boolean packageAsXdm = true;
		boolean encryptDocument = true;
		String senderEmailAddress = "leo.smith@direct.obhita-stage.org";
		String recipientEmailAddress = "Duane_Decouteau@direct.healthvault.com";
		String xdsDocumentEntryUniqueId = "123";

		DocumentProcessorWebServiceClient documentProcessorService = new DocumentProcessorWebServiceClient(
				null);
		documentProcessorService.run(document, enforcementPolicies,
				packageAsXdm, encryptDocument, senderEmailAddress,
				recipientEmailAddress, xdsDocumentEntryUniqueId);
	}

	/**
	 * Instantiates a new document processor web service client.
	 * 
	 * @param endpointAddress
	 *            the endpoint address
	 */
	public DocumentProcessorWebServiceClient(String endpointAddress) {
		this.endpointAddress = endpointAddress;
	}

	/**
	 * Process document.
	 * 
	 * @param document
	 *            the document
	 * @param enforcementPolicies
	 *            the enforcement policies
	 * @param packageAsXdm
	 *            the package as xdm
	 * @param senderEmailAddress
	 *            the sender email address
	 * @param recipientEmailAddress
	 *            the recipient email address
	 * @return the process document response
	 */
	public ProcessDocumentResponse processDocument(String document,
			String enforcementPolicies, boolean packageAsXdm,
			boolean encryptDocument, String senderEmailAddress,
			String recipientEmailAddress,
			String xdsDocumentEntryUniqueId) {
		ProcessDocumentServicePortType port;
		if (StringUtils.hasText(this.endpointAddress)) {
			port = createPort(endpointAddress);
		} else {
			// Using default endpoint address defined in the wsdl:port of wsdl
			// file
			port = createPort();
		}
		return processDocument(port, document, enforcementPolicies,
				packageAsXdm, encryptDocument, senderEmailAddress,
				recipientEmailAddress, xdsDocumentEntryUniqueId);
	}

	/**
	 * Process document.
	 * 
	 * @param port
	 *            the port
	 * @param document
	 *            the document
	 * @param enforcementPolicies
	 *            the enforcement policies
	 * @param packageAsXdm
	 *            the package as xdm
	 * @param senderEmailAddress
	 *            the sender email address
	 * @param recipientEmailAddress
	 *            the recipient email address
	 * @return the process document response
	 */
	private ProcessDocumentResponse processDocument(
			ProcessDocumentServicePortType port, String document,
			String enforcementPolicies, boolean packageAsXdm,
			boolean encryptDocument, String senderEmailAddress,
			String recipientEmailAddress, String xdsDocumentEntryUniqueId) {
		ProcessDocumentRequest request = new ProcessDocumentRequest();
		request.setDocument(document);
		request.setEnforcementPolicies(enforcementPolicies);
		request.setPackageAsXdm(packageAsXdm);
		request.setSenderEmailAddress(senderEmailAddress);
		request.setRecipientEmailAddress(recipientEmailAddress);
		request.setEncryptDocument(encryptDocument);
		request.setXdsDocumentEntryUniqueId(xdsDocumentEntryUniqueId);
		return port.processDocument(request);
	}

	/**
	 * Creates the port.
	 * 
	 * @return the process document service port type
	 */
	private ProcessDocumentServicePortType createPort() {
		final URL WSDL_LOCATION = this.getClass().getClassLoader()
				.getResource("DocumentProcessor.wsdl");
		final QName SERVICE = new QName(
				"http://www.samhsa.gov/ds4ppilot/contract/documentprocessor",
				"ProcessDocumentService");

		ProcessDocumentServicePortType port = new ProcessDocumentService(
				WSDL_LOCATION, SERVICE).getProcessDocumentPort();
		return port;
	}

	/**
	 * Creates the port.
	 * 
	 * @param endpointAddress
	 *            the endpoint address
	 * @return the process document service port type
	 */
	private ProcessDocumentServicePortType createPort(String endpointAddress) {
		ProcessDocumentServicePortType port = createPort();
		BindingProvider bp = (BindingProvider) port;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				endpointAddress);
		return port;
	}

	/**
	 * Run.
	 * 
	 * @param document
	 *            the document
	 * @param enforcementPolicies
	 *            the enforcement policies
	 * @param packageAsXdm
	 *            the package as xdm
	 * @param senderEmailAddress
	 *            the sender email address
	 * @param recipientEmailAddress
	 *            the recipient email address
	 */
	private void run(String document, String enforcementPolicies,
			boolean packageAsXdm, boolean encryptDocument,
			String senderEmailAddress, String recipientEmailAddress, String xdsDocumentEntryUniqueId) {
		try {
			LOGGER.debug("Creating DocumentProcessor service instance ...");
			long start = new Date().getTime();

			// Get a reference to the SOAP service interface.
			ProcessDocumentServicePortType port = createPort();

			long end = new Date().getTime();
			LOGGER.debug("...Done! DocumentProcessor instance: {}", port);
			LOGGER.debug(
					"Time required to initialize DocumentProcessor service interface: {} seconds",
					(end - start) / 1000f);

			start = new Date().getTime();

			ProcessDocumentResponse result = processDocument(port, document,
					enforcementPolicies, packageAsXdm, encryptDocument,
					senderEmailAddress, recipientEmailAddress, xdsDocumentEntryUniqueId);
			end = new Date().getTime();
			LOGGER.debug("Time required to invoke 'enforcePolicy': {} seconds",
					(end - start) / 1000f);

			System.out.print(result.getMaskedDocument());
			LOGGER.debug("");
			LOGGER.debug("Program complete, exiting");
		} catch (final Exception e) {
			LOGGER.error("An exception occurred, exiting", e);
		}
	}
}
