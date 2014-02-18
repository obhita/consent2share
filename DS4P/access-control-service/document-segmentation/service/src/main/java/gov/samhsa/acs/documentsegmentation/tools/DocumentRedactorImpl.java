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
package gov.samhsa.acs.documentsegmentation.tools;

import javax.xml.xpath.XPathExpressionException;

import gov.samhsa.acs.brms.domain.RuleExecutionContainer;
import gov.samhsa.acs.brms.domain.RuleExecutionResponse;
import gov.samhsa.acs.brms.domain.Sensitivity;
import gov.samhsa.acs.common.bean.XacmlResult;
import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;

import org.apache.xml.security.encryption.XMLEncryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

	/**
	 * Instantiates a new document redactor impl.
	 */
	public DocumentRedactorImpl() {
	}

	/**
	 * Instantiates a new document redactor impl.
	 * 
	 * @param documentEditor
	 *            the document editor
	 * @param documentXmlConverter
	 *            the document xml converter
	 */
	public DocumentRedactorImpl(DocumentEditor documentEditor,
			DocumentXmlConverter documentXmlConverter) {
		super();
		this.documentEditor = documentEditor;
		this.documentXmlConverter = documentXmlConverter;
	}

	// commented out for redact-only application
	// /** The pdp obligation prefix for redact. */
	// private final String PDP_OBLIGATION_PREFIX_REDACT =
	// "urn:oasis:names:tc:xspa:2.0:resource:patient:redact:";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.documentsegmentation.tools.DocumentRedactor#redactDocument
	 * (java.lang.String, gov.samhsa.acs.brms.domain.RuleExecutionContainer,
	 * gov.samhsa.acs.common.bean.XacmlResult)
	 */
	@Override
	public String redactDocument(String document,
			RuleExecutionContainer ruleExecutionContainer,
			XacmlResult xacmlResult) {

		Document xmlDocument = null;
		String xmlString = null;

		try {
			xmlDocument = documentXmlConverter.loadDocument(document);
			
			// Section based redaction
			String xPathExprSection = "//hl7:component[hl7:section[hl7:code[@code='%']]]";
			for (String c32SectionLoincCode : xacmlResult.getPdpObligations()) {
				redactElement(xmlDocument, xPathExprSection,
						c32SectionLoincCode);
				xmlDocument.normalize();
			}

			// Entry based redaction
			for (RuleExecutionResponse response : ruleExecutionContainer
					.getExecutionResponseList()) {
				if (containsRedactObligation(xacmlResult, response)) {
					String observationId = response.getObservationId();
					String displayName = response.getDisplayName();
					String code = response.getCode();

					// Redact Human-readable text
					// redact display Name (in section/text)
					String xPathExprHumanReadableTextNode = "//hl7:section/hl7:text//*/text()[contains(lower-case(.), '%')]";
					redactNodes(xmlDocument, xPathExprHumanReadableTextNode,
							displayName.toLowerCase());
					// redact code (in section/text)
					redactNodes(xmlDocument, xPathExprHumanReadableTextNode,
							code.toLowerCase());

					// Redact entry
					String xPathExprObservationId = "//hl7:id[@root='%']/ancestor::hl7:entry";
					redactElement(xmlDocument, xPathExprObservationId,
							observationId);

					xmlDocument.normalize();
					response.setItemAction(RuleExecutionResponse.ITEM_ACTION_REDACT);
				}

				// Set itemAction="REDACT" for the sections that will be
				// removed. If the itemAction is not set like this, the tagging
				// code will not ignore redacted sections while calculating
				// document level confidentiality code.
				if (xacmlResult.getPdpObligations().contains(
						response.getC32SectionLoincCode())) {
					response.setItemAction(RuleExecutionResponse.ITEM_ACTION_REDACT);
				}
			}

			// Debug
			// FileHelper.writeDocToFile(xmlDocument, "Redacted_C32.xml");
			xmlString = documentXmlConverter.convertXmlDocToString(xmlDocument);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DS4PException(e.toString(), e);
		}

		return xmlString;
	}

	/**
	 * Redact element. If no element is found at the XPath, nothing will be
	 * done.
	 * 
	 * @param xmlDocument
	 *            the xml document
	 * @param xPathExprToElement
	 *            the XPath expression to the element to be redacted
	 * @param variableValue
	 *            the variable value. the '%' character in xPathExprToElement
	 *            (if exists) will be replaced with this value.
	 * @throws XPathExpressionException
	 *             the x path expression exception
	 * @throws XMLEncryptionException
	 *             the xML encryption exception
	 * @throws Exception
	 *             the exception
	 */
	private void redactElement(Document xmlDocument, String xPathExprToElement,
			String variableValue) throws XPathExpressionException,
			XMLEncryptionException, Exception {
		String redactXpathExprSection = xPathExprToElement.replace("%",
				variableValue);
		Element elementToBeRedacted = documentEditor.getElement(xmlDocument,
				redactXpathExprSection);

		redactNodeIfNotNull(elementToBeRedacted);
	}

	/**
	 * Redact nodes.
	 * 
	 * @param xmlDocument
	 *            the xml document
	 * @param xPathExprToElement
	 *            the x path expr to element
	 * @param variableValue
	 *            the variable value
	 * @throws XPathExpressionException
	 *             the x path expression exception
	 * @throws XMLEncryptionException
	 *             the xML encryption exception
	 * @throws Exception
	 *             the exception
	 */
	private void redactNodes(Document xmlDocument, String xPathExprToElement,
			String variableValue) throws XPathExpressionException,
			XMLEncryptionException, Exception {
		String redactXpathExprSection = xPathExprToElement.replace("%",
				variableValue);
		NodeList nodesToBeRedacted = documentEditor.getNodeList(xmlDocument,
				redactXpathExprSection);
		if (nodesToBeRedacted != null) {
			for (int i = 0; i < nodesToBeRedacted.getLength(); i++) {
				redactNodeIfNotNull(nodesToBeRedacted.item(i));
			}
		}
	}

	/**
	 * Redact node if not null.
	 * 
	 * @param nodeToBeRedacted
	 *            the node to be redacted
	 */
	private void redactNodeIfNotNull(Node nodeToBeRedacted) {
		if (nodeToBeRedacted != null) {
			nodeToBeRedacted.getParentNode().removeChild(nodeToBeRedacted);
		}
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
		boolean result = false;
		if (response.getSensitivity() != null) {
			result = xacmlResult.getPdpObligations().contains(
					response.getSensitivity().toString());
		}
		return result;
	}
}
