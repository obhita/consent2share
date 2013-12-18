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

import gov.samhsa.acs.common.bean.XacmlResult;
import gov.samhsa.acs.common.namespace.PepNamespaceContext;
import gov.samhsa.acs.common.tool.DocumentXmlConverter;
import gov.samhsa.acs.common.tool.FileReader;
import gov.samhsa.acs.common.util.FileHelper;
import gov.samhsa.acs.common.xdm.XdmZipUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.axiom.attachments.ByteArrayDataSource;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * The Class DocumentEditorImpl.
 */
public class DocumentEditorImpl implements DocumentEditor {

	/** The metadata generator. */
	private MetadataGenerator metadataGenerator;

	/** The file reader. */
	private FileReader fileReader;

	/** The document xml converter. */
	private DocumentXmlConverter documentXmlConverter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.documentsegmentation.util
	 * .DocumentEditor#setDocumentCreationDate(java.lang.String)
	 */
	@Override
	public String setDocumentCreationDate(String document) throws Exception,
			XPathExpressionException, XMLEncryptionException {
		Document xmlDocument;
		xmlDocument = documentXmlConverter.loadDocument(document);

		// current date
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		Date date = new Date();
		String xPathExprEffectiveDate = "//hl7:effectiveTime";

		Element dateElement = getElement(xmlDocument, xPathExprEffectiveDate);
		dateElement.setAttribute("value", dateFormat.format(date));

		document = documentXmlConverter.convertXmlDocToString(xmlDocument);
		return document;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.documentsegmentation.util
	 * .DocumentEditor#getElement(org.w3c.dom.Document, java.lang.String)
	 */
	@Override
	public Element getElement(Document xmlDocument, String xPathExprDisplayName)
			throws XPathExpressionException, XMLEncryptionException, Exception {

		// Create XPath instance
		XPathFactory xpathFact = XPathFactory.newInstance();
		XPath xpath = xpathFact.newXPath();
		xpath.setNamespaceContext(new PepNamespaceContext());

		// Evaluate XPath expression against parsed document
		Node node = (Node) xpath.evaluate(xPathExprDisplayName, xmlDocument,
				XPathConstants.NODE);

		return (Element) node;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.documentsegmentation.util
	 * .DocumentEditor#setDocumentPayloadRawData(java.lang.Object,
	 * java.lang.String, boolean, java.lang.String, java.lang.String,
	 * gov.samhsa.acs.common.bean.XacmlResult, java.lang.String, byte[],
	 * byte[])
	 */
	@Override
	public ByteArrayDataSource setDocumentPayloadRawData(String document,
			boolean packageAsXdm, String senderEmailAddress,
			String recipientEmailAddress, XacmlResult xacmlResult,
			String executionResponseContainer, byte[] maskingKeyBytes,
			byte[] encryptionKeyBytes) throws Exception, IOException {
		ByteArrayDataSource rawData;
		byte[] documentPayload = null;
		if (packageAsXdm) {

			// generate metadata xml
			String metadataXml = metadataGenerator.generateMetadataXml(
					document, executionResponseContainer,
					xacmlResult.getHomeCommunityId(), senderEmailAddress,
					recipientEmailAddress);
			// FileHelper.writeStringToFile(metadataXml, "metadata.xml");

			documentPayload = XdmZipUtils.createXDMPackage(metadataXml,
					fileReader.readFile("CCD.xsl"), document,
					fileReader.readFile("INDEX.htm"),
					fileReader.readFile("README.txt"), maskingKeyBytes,
					encryptionKeyBytes);
		} else {
			documentPayload = document.getBytes();
		}

		rawData = new ByteArrayDataSource(documentPayload);
		return rawData;
	}

	/**
	 * Gets the metadata generator.
	 * 
	 * @return the metadata generator
	 */
	public MetadataGenerator getMetadataGenerator() {
		return metadataGenerator;
	}

	/**
	 * Sets the metadata generator.
	 * 
	 * @param metadataGenerator
	 *            the new metadata generator
	 */
	public void setMetadataGenerator(MetadataGenerator metadataGenerator) {
		this.metadataGenerator = metadataGenerator;
	}

	/**
	 * Gets the file reader.
	 * 
	 * @return the file reader
	 */
	public FileReader getFileReader() {
		return fileReader;
	}

	/**
	 * Sets the file reader.
	 * 
	 * @param fileReader
	 *            the new file reader
	 */
	public void setFileReader(FileReader fileReader) {
		this.fileReader = fileReader;
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
