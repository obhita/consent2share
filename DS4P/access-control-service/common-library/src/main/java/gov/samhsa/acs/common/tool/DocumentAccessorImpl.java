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
package gov.samhsa.acs.common.tool;

import gov.samhsa.acs.common.namespace.PepNamespaceContext;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class DocumentAccessorImpl.
 */
public class DocumentAccessorImpl implements DocumentAccessor {

	/** The xpath factory. */
	private XPathFactory xpathFact;

	/**
	 * Instantiates a new document accessor impl.
	 */
	public DocumentAccessorImpl() {
		super();
		this.xpathFact = XPathFactory.newInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.common.tool.DocumentAccessor#getElement(org.w3c.dom.Document
	 * , java.lang.String)
	 */
	@Override
	public Element getElement(Document xmlDocument, String xPathExpr)
			throws XPathExpressionException {

		Node node = getNode(xmlDocument, xPathExpr);

		return (Element) node;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.common.tool.DocumentAccessor#getNode(org.w3c.dom.Document,
	 * java.lang.String)
	 */
	@Override
	public Node getNode(Document xmlDocument, String xPathExpr)
			throws XPathExpressionException {
		// Create XPath instance
		XPath xpath = createXPathInstance();

		// Evaluate XPath expression against parsed document
		Node node = (Node) xpath.evaluate(xPathExpr, xmlDocument,
				XPathConstants.NODE);
		return node;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.common.tool.DocumentAccessor#getNodeList(org.w3c.dom.Document
	 * , java.lang.String)
	 */
	@Override
	public NodeList getNodeList(Document xmlDocument, String xPathExpr)
			throws XPathExpressionException {
		// Create XPath instance
		XPath xpath = createXPathInstance();

		// Evaluate XPath expression against parsed document
		NodeList nodeList = (NodeList) xpath.evaluate(xPathExpr, xmlDocument,
				XPathConstants.NODESET);
		return nodeList;
	}

	/**
	 * Creates the x path instance.
	 * 
	 * @return the x path
	 */
	private XPath createXPathInstance() {
		// Create XPath instance
		XPath xpath = this.xpathFact.newXPath();
		xpath.setNamespaceContext(new PepNamespaceContext());
		return xpath;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public <ProcessingInstructionImpl> Document addingStylesheet(
	        Document doc, String xslHref) throws TransformerConfigurationException, ParserConfigurationException {
	    StringBuilder builder = new StringBuilder();
	    builder.append("type=\"text/xsl\" href=");
	    builder.append(xslHref);
	    
		ProcessingInstructionImpl pi = (ProcessingInstructionImpl) doc
	            .createProcessingInstruction("xml-stylesheet",builder.toString());
	    Element root = doc.getDocumentElement();
	    doc.insertBefore((Node) pi, root);
	    return doc;

	}	
}
