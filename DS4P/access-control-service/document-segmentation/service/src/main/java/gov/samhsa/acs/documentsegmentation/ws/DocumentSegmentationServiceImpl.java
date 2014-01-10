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
package gov.samhsa.acs.documentsegmentation.ws;

import gov.samhsa.acs.documentsegmentation.DocumentSegmentation;
import gov.samhsa.consent2share.contract.documentsegmentation.DocumentSegmentationServicePortType;
import gov.samhsa.consent2share.schema.documentsegmentation.SegmentDocumentRequest;
import gov.samhsa.consent2share.schema.documentsegmentation.SegmentDocumentResponse;

import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

/**
 * The Class DocumentSegmentationServiceImpl.
 */
@MTOM(enabled = true, threshold = 512)
@WebService(targetNamespace = "http://www.samhsa.gov/consent2share/contract/documentsegmentation", 
			portName = "DocumentSegmentationServicePort", 
			serviceName = "DocumentSegmentationService", 
			endpointInterface = "gov.samhsa.consent2share.contract.documentsegmentation.DocumentSegmentationServicePortType")
public class DocumentSegmentationServiceImpl implements
		DocumentSegmentationServicePortType {

	/** The document processor. */
	private DocumentSegmentation documentSegmentationService;

	// Default constructor
	/**
	 * Instantiates a new process document service impl.
	 */
	public DocumentSegmentationServiceImpl() {
	}

	/**
	 * Instantiates a new process document service impl.
	 * 
	 * @param documentSegmentationService
	 *            the document segmentation service
	 */
	public DocumentSegmentationServiceImpl(
			DocumentSegmentation documentSegmentationService) {
		this.documentSegmentationService = documentSegmentationService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.contract.documentsegmentation.
	 * DocumentSegmentationServicePortType
	 * #segmentDocument(gov.samhsa.consent2share
	 * .schema.documentsegmentation.SegmentDocumentRequest)
	 */
	@Override
	public SegmentDocumentResponse segmentDocument(
			SegmentDocumentRequest parameters) {
		SegmentDocumentResponse response = new SegmentDocumentResponse();
		response = documentSegmentationService.segmentDocument(
				parameters.getDocument(), parameters.getEnforcementPolicies(),
				parameters.isPackageAsXdm(), parameters.isEncryptDocument(),
				parameters.getSenderEmailAddress(),
				parameters.getRecipientEmailAddress(),
				parameters.getXdsDocumentEntryUniqueId());
		return response;
	}
}
