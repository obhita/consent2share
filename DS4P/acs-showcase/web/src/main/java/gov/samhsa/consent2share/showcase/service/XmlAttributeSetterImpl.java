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
package gov.samhsa.consent2share.showcase.service;

import gov.samhsa.acs.common.namespace.PepNamespaceContext;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * The Class XmlAttributeSetterImpl.
 */
@Component
public class XmlAttributeSetterImpl implements XmlAttributeSetter {

	/** The document xml converter. */
	@Autowired
	private DocumentXmlConverter documentXmlConverter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.showcase.service.XmlAttributeSetter#
	 * setAttributeValue(java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String setAttributeValue(String docString, String xPathExpr,
			String attributeName, String newValue) throws Exception,
			XPathExpressionException {
		Document doc = documentXmlConverter.loadDocument(docString);
		// Create XPath instance
		XPathFactory xpathFact = XPathFactory.newInstance();
		XPath xpath = xpathFact.newXPath();
		xpath.setNamespaceContext(new PepNamespaceContext());
		Node node = (Node) xpath.evaluate(xPathExpr, doc, XPathConstants.NODE);
		Node attribute = node.getAttributes().getNamedItem(attributeName);
		if (attribute == null || "".equals(attribute.getNodeValue())) {
			Node newAttribute = doc.createAttribute(attributeName);
			newAttribute.setNodeValue(newValue);
			node.getAttributes().setNamedItem(newAttribute);
		} else {
			attribute.setNodeValue(newValue);
		}

		doc.normalizeDocument();
		return documentXmlConverter.convertXmlDocToString(doc);
	}

	public DocumentXmlConverter getDocumentXmlConverter() {
		return documentXmlConverter;
	}

	public void setDocumentXmlConverter(
			DocumentXmlConverter documentXmlConverter) {
		this.documentXmlConverter = documentXmlConverter;
	}
}
