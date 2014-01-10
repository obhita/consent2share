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
package gov.samhsa.acs.xdsb.repository.wsclient.adapter;

import gov.samhsa.acs.common.namespace.PepNamespaceContext;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;
import gov.samhsa.acs.xdsb.common.XdsbErrorFactory;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse.DocumentResponse;

import java.util.LinkedList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * The Class RetrieveDocumentSetResponseFilter.
 */
public class RetrieveDocumentSetResponseFilter {

	/** The converter. */
	private DocumentXmlConverter converter;

	/** The xdsb error factory. */
	private XdsbErrorFactory xdsbErrorFactory;

	/**
	 * Instantiates a new retrieve document set response filter.
	 */
	public RetrieveDocumentSetResponseFilter() {
		super();
	}

	/**
	 * Instantiates a new retrieve document set response filter.
	 * 
	 * @param converter
	 *            the converter
	 * @param xdsbErrorFactory
	 *            the xdsb error factory
	 */
	public RetrieveDocumentSetResponseFilter(DocumentXmlConverter converter,
			XdsbErrorFactory xdsbErrorFactory) {
		super();
		this.converter = converter;
		this.xdsbErrorFactory = xdsbErrorFactory;
	}

	/**
	 * Filter by patient and author.
	 * 
	 * @param response
	 *            the response
	 * @param patientId
	 *            the patient id
	 * @param authorNPI
	 *            the author npi
	 * @return the retrieve document set response
	 * @throws Throwable
	 *             the throwable
	 */
	public RetrieveDocumentSetResponse filterByPatientAndAuthor(
			RetrieveDocumentSetResponse response, String patientId,
			String authorNPI) throws Throwable {
		LinkedList<DocumentResponse> removeList = new LinkedList<DocumentResponse>();
		for (DocumentResponse docResponse : response.getDocumentResponse()) {
			String docString = new String(docResponse.getDocument());

			String xPathExpr = "//hl7:recordTarget/child::hl7:patientRole/child::hl7:id";
			String attributeName = "extension";
			String docPatientId = getAttributeValue(docString, xPathExpr,
					attributeName);

			xPathExpr = "//hl7:author/child::hl7:assignedAuthor/child::hl7:id";
			attributeName = "extension";
			String docAuthorNPI = getAttributeValue(docString, xPathExpr,
					attributeName);

			if (!patientId.equals(docPatientId)
					|| !authorNPI.equals(docAuthorNPI)) {
				removeList.add(docResponse);
			}
		}
		if (removeList.size() > 0) {
			response.getDocumentResponse().removeAll(removeList);
			response = xdsbErrorFactory
					.setRetrieveDocumentSetResponseRegistryErrorListFilteredByPatientAndAuthor(
							response, removeList.size(), patientId, authorNPI);
		}
		return response;
	}

	/**
	 * Gets the attribute value.
	 * 
	 * @param docString
	 *            the doc string
	 * @param xPathExpr
	 *            the x path expr
	 * @param attributeName
	 *            the attribute name
	 * @return the attribute value
	 * @throws Exception
	 *             the exception
	 * @throws XPathExpressionException
	 *             the x path expression exception
	 */
	private String getAttributeValue(String docString, String xPathExpr,
			String attributeName) throws Exception, XPathExpressionException {
		Document doc = converter.loadDocument(docString);
		// Create XPath instance
		XPathFactory xpathFact = XPathFactory.newInstance();
		XPath xpath = xpathFact.newXPath();
		xpath.setNamespaceContext(new PepNamespaceContext());
		Node node = (Node) xpath.evaluate(xPathExpr, doc, XPathConstants.NODE);
		return node.getAttributes().getNamedItem(attributeName).getNodeValue();
	}
}
