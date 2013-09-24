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
package gov.samhsa.consent2share.accesscontrolservice.documentsegmentation.tools;

import gov.samhsa.ds4ppilot.common.beans.RuleExecutionContainer;
import gov.samhsa.ds4ppilot.common.beans.XacmlResult;
import gov.samhsa.ds4ppilot.common.exception.DS4PException;
import gov.va.ds4p.cas.RuleExecutionResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The Class DocumentRedactorImpl.
 */
public class DocumentRedactorImpl implements DocumentRedactor {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The editor. */
	private DocumentEditor documentEditor;

	/** The document xml converter. */
	private DocumentXmlConverter documentXmlConverter;

	// commented out for redact-only application
	// /** The pdp obligation prefix for redact. */
	// private final String PDP_OBLIGATION_PREFIX_REDACT =
	// "urn:oasis:names:tc:xspa:2.0:resource:patient:redact:";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.accesscontrolservice.documentsegmentation.tools
	 * .DocumentRedactor#redactDocument(java.lang.String,
	 * gov.samhsa.ds4ppilot.common.beans.RuleExecutionContainer,
	 * gov.samhsa.ds4ppilot.common.beans.XacmlResult)
	 */
	@Override
	public String redactDocument(String document,
			RuleExecutionContainer ruleExecutionContainer,
			XacmlResult xacmlResult) {

		Document xmlDocument = null;
		String xmlString = null;

		try {
			xmlDocument = documentXmlConverter.loadDocument(document);

			for (RuleExecutionResponse response : ruleExecutionContainer
					.getExecutionResponseList()) {
				if (containsRedactObligation(xacmlResult, response)) {
					String observationId = response.getObservationId();
					String displayName = response.getDisplayName();

					// redact display Name
					String xPathExprDisplayName = "//hl7:td[.='%']/parent::hl7:tr";
					xPathExprDisplayName = xPathExprDisplayName.replace("%",
							displayName);

					Element elementToBeRedacted = documentEditor.getElement(
							xmlDocument, xPathExprDisplayName);

					elementToBeRedacted.getParentNode().removeChild(
							elementToBeRedacted);

					// mask element contents
					String xPathExprObservationId = "//hl7:id[@root='%']/ancestor::hl7:entry";
					xPathExprObservationId = xPathExprObservationId.replace(
							"%", observationId);

					elementToBeRedacted = documentEditor.getElement(
							xmlDocument, xPathExprObservationId);

					elementToBeRedacted.getParentNode().removeChild(
							elementToBeRedacted);

					xmlDocument.normalize();
				}
			}
			// Debug
			// FileHelper.writeDocToFile(xmlDocument, "Redacted_C32.xml");
			xmlString = documentXmlConverter.converXmlDocToString(xmlDocument);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DS4PException(e.toString(), e);
		}

		return xmlString;
	}

	/**
	 * Checks if the xacmlResult has any redact obligations for a single
	 * executionResonse's sensitivity value.
	 * 
	 * @param xacmlResult
	 *            the xacml result
	 * @param response
	 *            the response
	 * @return true, if successful
	 */
	private boolean containsRedactObligation(XacmlResult xacmlResult,
			RuleExecutionResponse response) {
		// commented out for redact-only application
		// return
		// xacmlResult.getPdpObligations().contains(PDP_OBLIGATION_PREFIX_REDACT+response.getSensitivity());
		logger.debug("xacmlResult.getPdpObligations().contains(response.getSensitivity())= "
				+ Boolean.toString(xacmlResult.getPdpObligations().contains(
						response.getSensitivity())));
		return xacmlResult.getPdpObligations().contains(
				response.getSensitivity());
	}

	/**
	 * Gets the document editor.
	 * 
	 * @return the document editor
	 */
	public DocumentEditor getDocumentEditor() {
		return documentEditor;
	}

	/**
	 * Sets the document editor.
	 * 
	 * @param documentEditor
	 *            the new document editor
	 */
	public void setDocumentEditor(DocumentEditor documentEditor) {
		this.documentEditor = documentEditor;
	}

	/**
	 * Gets the document xml converter.
	 * 
	 * @return the document xml converter
	 */
	public DocumentXmlConverter getDocumentXmlConverter() {
		return documentXmlConverter;
	}

	/**
	 * Sets the document xml converter.
	 * 
	 * @param documentXmlConverter
	 *            the new document xml converter
	 */
	public void setDocumentXmlConverter(
			DocumentXmlConverter documentXmlConverter) {
		this.documentXmlConverter = documentXmlConverter;
	}
}
