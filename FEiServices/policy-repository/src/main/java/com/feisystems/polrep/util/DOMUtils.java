package com.feisystems.polrep.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.feisystems.polrep.service.exception.DOMUtilsException;
import com.feisystems.polrep.service.xacml.XACMLNamespaceContext;

public class DOMUtils {
	private static final String DEFAULT_TRANSFORMER_PROPERTY_INDENT_AMOUNT = "4";
	private static final String DEFAULT_TRANSFORMER_PROPERTY_INDENT = "yes";
	private static final String DEFAULT_TRANSFORMER_PROPERTY_METHOD = "xml";
	private static final String DEFAULT_TRANSFORMER_PROPERTY_OMIT_XML_DECLARATION = "no";

	public static final String DEFAULT_ENCODING = "UTF-8";

	public static Document bytesToDocument(byte[] bytes) {
		String xacmlString;
		try {
			xacmlString = new String(bytes, DEFAULT_ENCODING);
		} catch (final UnsupportedEncodingException e) {
			throw new DOMUtilsException(e);
		}
		return xmlToDocument(xacmlString);
	}

	public static byte[] documentToBytes(Document xmlDocument) {
		try {
			return documentToXml(xmlDocument).getBytes(DEFAULT_ENCODING);
		} catch (final UnsupportedEncodingException e) {
			throw new DOMUtilsException(e);
		}
	}

	public static String documentToXml(Document xmlDocument) {
		try {
			String xmlString = "";

			final TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer;

			transformer = tf.newTransformer();

			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
					DEFAULT_TRANSFORMER_PROPERTY_OMIT_XML_DECLARATION);
			transformer.setOutputProperty(OutputKeys.METHOD,
					DEFAULT_TRANSFORMER_PROPERTY_METHOD);
			transformer.setOutputProperty(OutputKeys.INDENT,
					DEFAULT_TRANSFORMER_PROPERTY_INDENT);
			transformer
					.setOutputProperty(OutputKeys.ENCODING, DEFAULT_ENCODING);
			transformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount",
					DEFAULT_TRANSFORMER_PROPERTY_INDENT_AMOUNT);

			final StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(xmlDocument), new StreamResult(
					writer));
			xmlString = writer.getBuffer().toString().replaceAll("\n|\r", "");
			Assert.hasText(xmlString,
					"Document object cannot be converted to XML string!");
			return xmlString;
		} catch (TransformerException | IllegalArgumentException e) {
			throw new DOMUtilsException(e);
		}
	}

	public static Optional<Node> getNode(Document xmlDocument,
			String xPathExpr, String... arguments) {
		xPathExpr = setXpathArguments(xPathExpr, arguments);

		// Create XPath instance
		final XPath xpath = xpath();

		// Evaluate XPath expression against parsed document
		Node node = null;
		try {
			node = (Node) xpath.evaluate(xPathExpr, xmlDocument,
					XPathConstants.NODE);
			return Optional.ofNullable(node);
		} catch (final XPathExpressionException e) {
			throw new DOMUtilsException(e);
		}
	}

	public static Optional<NodeList> getNodeList(Document xmlDocument,
			String xPathExpr, String... arguments) {
		xPathExpr = setXpathArguments(xPathExpr, arguments);

		// Create XPath instance
		final XPath xpath = xpath();

		// Evaluate XPath expression against parsed document
		NodeList nodeList = null;
		try {
			nodeList = (NodeList) xpath.evaluate(xPathExpr, xmlDocument,
					XPathConstants.NODESET);
			if (nodeList != null && nodeList.getLength() == 0) {
				nodeList = null;
			}
			return Optional.ofNullable(nodeList);
		} catch (final XPathExpressionException e) {
			throw new DOMUtilsException(e);
		}
	}

	public static Document xmlToDocument(String xmlString) {
		try {
			final DocumentBuilderFactory dbf = DocumentBuilderFactory
					.newInstance();
			dbf.setValidating(false);
			dbf.setNamespaceAware(true);
			final DocumentBuilder db = dbf.newDocumentBuilder();

			final InputSource source = new InputSource(new StringReader(
					xmlString));

			final Document doc = db.parse(source);
			Assert.notNull(doc,
					"XML string cannot be converted to Document object!");
			return doc;
		} catch (ParserConfigurationException | SAXException | IOException
				| IllegalArgumentException e) {
			throw new DOMUtilsException(e);
		}
	}

	private static String setXpathArguments(String xPath, String... arguments) {
		for (int i = 0; i < arguments.length; i++) {
			xPath = xPath.replace("%" + Integer.toString(i + 1), arguments[i]);
		}
		return xPath;
	}

	private static XPath xpath() {
		// Create XPath instance
		final XPath xpath = XPathFactory.newInstance().newXPath();
		xpath.setNamespaceContext(XACMLNamespaceContext.CONTEXT);
		return xpath;
	}
}
