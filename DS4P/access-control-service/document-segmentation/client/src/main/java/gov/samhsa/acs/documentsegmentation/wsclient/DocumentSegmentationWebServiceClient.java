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
package gov.samhsa.acs.documentsegmentation.wsclient;

import gov.samhsa.consent2share.schema.documentsegmentation.SegmentDocumentRequest;
import gov.samhsa.consent2share.schema.documentsegmentation.SegmentDocumentResponse;
import gov.samhsa.consent2share.contract.documentsegmentation.DocumentSegmentationService;
import gov.samhsa.consent2share.contract.documentsegmentation.DocumentSegmentationServicePortType;

import java.net.URL;
import java.util.Date;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * The Class DocumentSegmentationWebServiceClient.
 */
public class DocumentSegmentationWebServiceClient {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

		DocumentSegmentationWebServiceClient documentSegmentationService = new DocumentSegmentationWebServiceClient(
				null);
		documentSegmentationService.run(document, enforcementPolicies,
				packageAsXdm, encryptDocument, senderEmailAddress,
				recipientEmailAddress, xdsDocumentEntryUniqueId);
	}

	/**
	 * Instantiates a new document segmentation web service client.
	 * 
	 * @param endpointAddress
	 *            the endpoint address
	 */
	public DocumentSegmentationWebServiceClient(String endpointAddress) {
		this.endpointAddress = endpointAddress;
	}

	/**
	 * Segment document.
	 * 
	 * @param document
	 *            the document
	 * @param enforcementPolicies
	 *            the enforcement policies
	 * @param packageAsXdm
	 *            the package as xdm
	 * @param encryptDocument
	 *            the encrypt document
	 * @param senderEmailAddress
	 *            the sender email address
	 * @param recipientEmailAddress
	 *            the recipient email address
	 * @param xdsDocumentEntryUniqueId
	 *            the xds document entry unique id
	 * @return the segment document response
	 */
	public SegmentDocumentResponse segmentDocument(String document,
			String enforcementPolicies, boolean packageAsXdm,
			boolean encryptDocument, String senderEmailAddress,
			String recipientEmailAddress, String xdsDocumentEntryUniqueId) {
		DocumentSegmentationServicePortType port;
		if (StringUtils.hasText(this.endpointAddress)) {
			port = createPort(endpointAddress);
		} else {
			// Using default endpoint address defined in the wsdl:port of wsdl
			// file
			port = createPort();
		}
		return segmentDocument(port, document, enforcementPolicies,
				packageAsXdm, encryptDocument, senderEmailAddress,
				recipientEmailAddress, xdsDocumentEntryUniqueId);
	}

	/**
	 * Segment document.
	 * 
	 * @param port
	 *            the port
	 * @param document
	 *            the document
	 * @param enforcementPolicies
	 *            the enforcement policies
	 * @param packageAsXdm
	 *            the package as xdm
	 * @param encryptDocument
	 *            the encrypt document
	 * @param senderEmailAddress
	 *            the sender email address
	 * @param recipientEmailAddress
	 *            the recipient email address
	 * @param xdsDocumentEntryUniqueId
	 *            the xds document entry unique id
	 * @return the segment document response
	 */
	private SegmentDocumentResponse segmentDocument(
			DocumentSegmentationServicePortType port, String document,
			String enforcementPolicies, boolean packageAsXdm,
			boolean encryptDocument, String senderEmailAddress,
			String recipientEmailAddress, String xdsDocumentEntryUniqueId) {
		SegmentDocumentRequest request = new SegmentDocumentRequest();
		request.setDocument(document);
		request.setEnforcementPolicies(enforcementPolicies);
		request.setPackageAsXdm(packageAsXdm);
		request.setSenderEmailAddress(senderEmailAddress);
		request.setRecipientEmailAddress(recipientEmailAddress);
		request.setEncryptDocument(encryptDocument);
		request.setXdsDocumentEntryUniqueId(xdsDocumentEntryUniqueId);
		return port.segmentDocument(request);
	}

	/**
	 * Creates the port.
	 * 
	 * @return the document segmentation service port type
	 */
	private DocumentSegmentationServicePortType createPort() {
		final URL WSDL_LOCATION = this.getClass().getClassLoader()
				.getResource("DocumentSegmentationService.wsdl");
		final QName SERVICE = new QName(
				"http://www.samhsa.gov/consent2share/contract/documentsegmentation",
				"DocumentSegmentationService");

		DocumentSegmentationServicePortType port = new DocumentSegmentationService(
				WSDL_LOCATION, SERVICE).getDocumentSegmentationServicePort();
		return port;
	}

	/**
	 * Creates the port.
	 * 
	 * @param endpointAddress
	 *            the endpoint address
	 * @return the document segmentation service port type
	 */
	private DocumentSegmentationServicePortType createPort(
			String endpointAddress) {
		DocumentSegmentationServicePortType port = createPort();
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
	 * @param encryptDocument
	 *            the encrypt document
	 * @param senderEmailAddress
	 *            the sender email address
	 * @param recipientEmailAddress
	 *            the recipient email address
	 * @param xdsDocumentEntryUniqueId
	 *            the xds document entry unique id
	 */
	private void run(String document, String enforcementPolicies,
			boolean packageAsXdm, boolean encryptDocument,
			String senderEmailAddress, String recipientEmailAddress,
			String xdsDocumentEntryUniqueId) {
		try {
			logger.debug("Creating DocumentSegmentation service instance ...");
			long start = new Date().getTime();

			// Get a reference to the SOAP service interface.
			DocumentSegmentationServicePortType port = createPort();

			long end = new Date().getTime();
			logger.debug("...Done! DocumentSegmentation instance: {}", port);
			logger.debug(
					"Time required to initialize DocumentSegmentation service interface: {} seconds",
					(end - start) / 1000f);

			start = new Date().getTime();

			SegmentDocumentResponse result = segmentDocument(port, document,
					enforcementPolicies, packageAsXdm, encryptDocument,
					senderEmailAddress, recipientEmailAddress,
					xdsDocumentEntryUniqueId);
			end = new Date().getTime();
			logger.debug("Time required to invoke 'enforcePolicy': {} seconds",
					(end - start) / 1000f);

			logger.debug(result.getMaskedDocument());
			logger.debug("");
			logger.debug("Program complete, exiting");
		} catch (final Exception e) {
			logger.error("An exception occurred, exiting", e);
		}
	}
}
