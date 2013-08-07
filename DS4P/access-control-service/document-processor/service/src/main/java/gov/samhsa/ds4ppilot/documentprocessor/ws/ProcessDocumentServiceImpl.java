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
package gov.samhsa.ds4ppilot.documentprocessor.ws;

import gov.samhsa.ds4ppilot.contract.documentprocessor.ProcessDocumentServicePortType;
import gov.samhsa.ds4ppilot.documentprocessor.DocumentProcessor;
import gov.samhsa.ds4ppilot.schema.documentprocessor.ProcessDocumentRequest;
import gov.samhsa.ds4ppilot.schema.documentprocessor.ProcessDocumentResponse;

import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

/**
 * The Class ProcessDocumentServiceImpl.
 */
@MTOM(enabled = true, threshold = 512)
@WebService(targetNamespace = "http://www.samhsa.gov/ds4ppilot/contract/documentprocessor", 
portName="ProcessDocumentPort",
serviceName="ProcessDocumentService", 
endpointInterface="gov.samhsa.ds4ppilot.contract.documentprocessor.ProcessDocumentServicePortType")
public class ProcessDocumentServiceImpl implements ProcessDocumentServicePortType {

	/** The document processor. */
	private DocumentProcessor documentProcessor;

	// Default constructor
	/**
	 * Instantiates a new process document service impl.
	 */
	public ProcessDocumentServiceImpl() {

	}

	/**
	 * Instantiates a new process document service impl.
	 *
	 * @param documentProcessor the document processor
	 */
	public ProcessDocumentServiceImpl(DocumentProcessor documentProcessor) {
		this.documentProcessor = documentProcessor;
	}	


	/* (non-Javadoc)
	 * @see gov.samhsa.ds4ppilot.contract.documentprocessor.ProcessDocumentServicePortType#processDocument(gov.samhsa.ds4ppilot.schema.documentprocessor.ProcessDocumentRequest)
	 */
	@Override
	public ProcessDocumentResponse processDocument(
			ProcessDocumentRequest parameters) {
		ProcessDocumentResponse response = new ProcessDocumentResponse();
		response = documentProcessor.processDocument(parameters.getDocument(), parameters.getEnforcementPolicies(), parameters.isPackageAsXdm(), parameters.isEncryptDocument(), parameters.getSenderEmailAddress(), parameters.getRecipientEmailAddress(), parameters.getXdsDocumentEntryUniqueId());
		return response;
	}	

	/**
	 * Gets the document processor.
	 *
	 * @return the document processor
	 */
	public DocumentProcessor getDocumentProcessor() {
		return documentProcessor;
	}

	/**
	 * Sets the push orchestrator.
	 *
	 * @param documentProcessor the new push orchestrator
	 */
	public void setDocumentProcessor(DocumentProcessor documentProcessor) {
		this.documentProcessor = documentProcessor;
	}
}
