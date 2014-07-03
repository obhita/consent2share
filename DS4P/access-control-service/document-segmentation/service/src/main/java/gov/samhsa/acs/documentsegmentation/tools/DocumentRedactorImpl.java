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

import gov.samhsa.acs.brms.domain.ClinicalFact;
import gov.samhsa.acs.brms.domain.FactModel;
import gov.samhsa.acs.brms.domain.RuleExecutionContainer;
import gov.samhsa.acs.brms.domain.RuleExecutionResponse;
import gov.samhsa.acs.brms.domain.XacmlResult;
import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.tool.DocumentAccessor;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;
import gov.samhsa.acs.common.tool.exception.DocumentAccessorException;
import gov.samhsa.acs.documentsegmentation.tools.dto.RedactList;
import gov.samhsa.acs.documentsegmentation.tools.dto.RedactedDocument;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.xpath.XPathExpressionException;

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

	/** The document xml converter. */
	private DocumentXmlConverter documentXmlConverter;

	/** The document accessor. */
	private DocumentAccessor documentAccessor;

	/** The Constant XPATH_HUMAN_READABLE_TEXT_NODE. */
	public static final String XPATH_HUMAN_READABLE_TEXT_NODE = "//hl7:section[child::hl7:entry[child::hl7:generatedEntryId/text()='%1']]/hl7:text//*/text()[contains(lower-case(.), '%2')]";

	/** The Constant XPATH_SECTION. */
	public static final String XPATH_SECTION = "//hl7:structuredBody/hl7:component[child::hl7:section[child::hl7:code[@code='%1']]]";

	/** The Constant XPATH_ENTRY. */
	public static final String XPATH_ENTRY = "//hl7:entry[child::hl7:generatedEntryId[text()='%1']]";

	/**
	 * Instantiates a new document redactor impl.
	 */
	public DocumentRedactorImpl() {
	}

	/**
	 * Instantiates a new document redactor impl.
	 * 
	 * @param documentXmlConverter
	 *            the document xml converter
	 * @param documentAccessor
	 *            the document accessor
	 */
	public DocumentRedactorImpl(DocumentXmlConverter documentXmlConverter,
			DocumentAccessor documentAccessor) {
		super();
		this.documentXmlConverter = documentXmlConverter;
		this.documentAccessor = documentAccessor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.documentsegmentation.tools.DocumentRedactor#redactDocument
	 * (java.lang.String, gov.samhsa.acs.brms.domain.RuleExecutionContainer,
	 * gov.samhsa.acs.brms.domain.XacmlResult,
	 * gov.samhsa.acs.brms.domain.FactModel)
	 */
	@Override
	public RedactedDocument redactDocument(String document,
			RuleExecutionContainer ruleExecutionContainer, FactModel factModel) {

		Document xmlDocument = null;
		List<Node> redactNodeList = new LinkedList<Node>();
		RedactList redactList = new RedactList();
		Set<String> redactSectionSet = new HashSet<String>();
		Set<String> redactCategorySet = new HashSet<String>();
		XacmlResult xacmlResult = factModel.getXacmlResult();

		try {
			xmlDocument = documentXmlConverter.loadDocument(document);

			// If there is any section with a code exists in pdp obligations,
			// add that section to redactNodeList.
			for (String sectionCode : xacmlResult.getPdpObligations()) {
				int added = addNodesToList(xmlDocument, redactNodeList,
						redactList, XPATH_SECTION, sectionCode, "");
				if (added > 0) {
					redactSectionSet.add(sectionCode);
				}
			}

			// For each clinical fact
			for (ClinicalFact fact : factModel.getClinicalFactList()) {
				String foundCategory = null;
				// If there is at least one value set category in obligations
				if ((foundCategory = containsAny(
						xacmlResult.getPdpObligations(),
						fact.getValueSetCategories())) != null) {
					// Search and add the entry to redactNodeList
					addNodesToList(xmlDocument, redactNodeList, redactList,
							XPATH_ENTRY, fact.getEntry(), "");
					// Search and add human-readable text nodes to
					// redactNodeList
					addNodesToList(xmlDocument, redactNodeList, redactList,
							XPATH_HUMAN_READABLE_TEXT_NODE, fact.getEntry(),
							fact.getDisplayName().toLowerCase());
					addNodesToList(xmlDocument, redactNodeList, redactList,
							XPATH_HUMAN_READABLE_TEXT_NODE, fact.getEntry(),
							fact.getCode().toLowerCase());
					redactCategorySet.add(foundCategory);
				}
			}

			// Redact all nodes in redactNodeList (sections, entries, text
			// nodes)
			for (Node nodeToBeReadacted : redactNodeList) {
				redactNodeIfNotNull(nodeToBeReadacted);
			}

			// Mark redacted sections and entries in ruleExecutionContainer, so
			// they can be ignored during tagging
			for (RuleExecutionResponse response : ruleExecutionContainer
					.getExecutionResponseList()) {
				if (redactList.getRedactList().contains(
						response.getC32SectionLoincCode())
						|| redactList.getRedactList().contains(
								response.getEntry())) {
					response.setItemAction(RuleExecutionResponse.ITEM_ACTION_REDACT);
				}
			}
			
			// Add empty component/section under structuredBody if none exists (required to pass validation)
			Node structuredBody = documentAccessor.getNode(xmlDocument, "//hl7:structuredBody[not(hl7:component)]");
			if(structuredBody != null){
				Element emptyComponent = xmlDocument.createElementNS("urn:hl7-org:v3", "component");
				Element emptySection = xmlDocument.createElementNS("urn:hl7-org:v3", "section");
				emptyComponent.appendChild(emptySection);
				structuredBody.appendChild(emptyComponent);
			}

			// Convert redacted document to xml string
			document = documentXmlConverter.convertXmlDocToString(xmlDocument);

			// Debug
			// FileHelper.writeDocToFile(xmlDocument, "Redacted_C32.xml");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DS4PException(e.toString(), e);
		}
		return new RedactedDocument(document, redactSectionSet,
				redactCategorySet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.acs.documentsegmentation.tools.DocumentRedactor#
	 * cleanUpGeneratedEntryIds(java.lang.String)
	 */
	@Override
	public String cleanUpGeneratedEntryIds(String document) {
		// Remove all generatedEntryId elements to clean up the clinical
		// document
		Document xmlDocument = null;
		try {
			xmlDocument = documentXmlConverter.loadDocument(document);

			String xPathExprGeneratedEntryId = "//hl7:generatedEntryId";
			NodeList generatedEntryIds = null;

			generatedEntryIds = documentAccessor.getNodeList(xmlDocument,
					xPathExprGeneratedEntryId);

			if (generatedEntryIds != null) {
				for (int i = 0; i < generatedEntryIds.getLength(); i++) {
					Node generatedEntryIdNode = generatedEntryIds.item(i);
					Element generatedEntryIdElement = (Element) generatedEntryIdNode;
					generatedEntryIdElement.getParentNode().removeChild(
							generatedEntryIdElement);
				}
			}
			document = documentXmlConverter.convertXmlDocToString(xmlDocument);
		} catch (XPathExpressionException e) {
			logger.error(e.getMessage(), e);
			throw new DS4PException(e.toString(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DS4PException(e.toString(), e);
		}
		return document;
	}
	
	/* (non-Javadoc)
	 * @see gov.samhsa.acs.documentsegmentation.tools.DocumentRedactor#cleanUpEmbeddedClinicalDocumentFromFactModel(java.lang.String)
	 */
	@Override
	public String cleanUpEmbeddedClinicalDocumentFromFactModel(String factModelXml){
		try {
		Document factModel = documentXmlConverter.loadDocument(factModelXml);
		Element embeddedClinicalDocument = documentAccessor.getElement(factModel, "//hl7:EmbeddedClinicalDocument");

		embeddedClinicalDocument.getParentNode().removeChild(embeddedClinicalDocument);
		return documentXmlConverter.convertXmlDocToString(factModel);
		} catch (DocumentAccessorException e) {
			logger.error(e.getMessage(), e);
			throw new DS4PException(e);
		}
	}

	/**
	 * Adds the nodes to list.
	 * 
	 * @param xmlDocument
	 *            the xml document
	 * @param listOfNodes
	 *            the list of nodes
	 * @param redactList
	 *            the redact list
	 * @param xPathExpr
	 *            the x path expr
	 * @param value1
	 *            the value1
	 * @param value2
	 *            the value2
	 * @return the int number of added nodes
	 * @throws XPathExpressionException
	 *             the x path expression exception
	 */
	private int addNodesToList(Document xmlDocument, List<Node> listOfNodes,
			RedactList redactList, String xPathExpr, String value1,
			String value2) throws XPathExpressionException {
		int added = 0;
		NodeList nodeList = documentAccessor.getNodeList(xmlDocument, xPathExpr
				.replace("%1", value1).replace("%2", value2));
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				// add section or generated entry code to redactList, so they
				// can be ignored during tagging
				redactList.getRedactList().add(value1);
				listOfNodes.add(nodeList.item(i));
				added++;
			}
		}
		return added;
	}

	/**
	 * Redact node if not null.
	 * 
	 * @param nodeToBeRedacted
	 *            the node to be redacted
	 */
	private void redactNodeIfNotNull(Node nodeToBeRedacted) {
		if (nodeToBeRedacted != null) {
			// If displayName contains the code, it will be found twice and can
			// already be removed. Therefore, we need to check the parent
			try {
				nodeToBeRedacted.getParentNode().removeChild(nodeToBeRedacted);
			} catch (NullPointerException e) {
				StringBuilder builder = new StringBuilder();
				builder.append("The text value '");
				builder.append(nodeToBeRedacted.getNodeValue());
				builder.append("' must have been removed already, it cannot be removed again. This might happen if one of the search text contains the other.");
				logger.warn(builder.toString());
			}
		}
	}

	/**
	 * Contains any.
	 * 
	 * @param obligations
	 *            the obligations
	 * @param categories
	 *            the categories
	 * @return true, if successful
	 */
	private String containsAny(List<String> obligations, Set<String> categories) {
		if (obligations != null && categories != null) {
			for (String category : categories) {
				if (obligations.contains(category)) {
					return category;
				}
			}
		}
		return null;
	}
}
