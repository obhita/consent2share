package gov.samhsa.acs.common.tool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.xml.xpath.XPathExpressionException;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DocumentAccessorImplTest {

	private FileReaderImpl fileReader;
	private DocumentXmlConverterImpl documentXmlConverter;
	private Document c32Doc;

	private DocumentAccessorImpl sut;

	@Before
	public void setUp() throws Exception {
		fileReader = new FileReaderImpl();
		documentXmlConverter = new DocumentXmlConverterImpl();
		String c32 = fileReader.readFile("c32.xml");
		c32Doc = documentXmlConverter.loadDocument(c32);
		sut = new DocumentAccessorImpl();
	}

	@Test
	public void testGetElement() throws XPathExpressionException {
		// Arrange
		String xPath = "/hl7:ClinicalDocument//hl7:patientRole//hl7:city";

		// Act
		Element element = sut.getElement(c32Doc, xPath);

		// Assert
		assertNotNull(element);
		assertEquals("CityName", element.getTextContent());
	}

	@Test
	public void testGetNode() throws XPathExpressionException {
		// Arrange
		String xPath = "/hl7:ClinicalDocument//hl7:patientRole//hl7:city";

		// Act
		Node node = sut.getNode(c32Doc, xPath);

		// Assert
		assertNotNull(node);
		assertEquals("CityName", node.getTextContent());
	}

	@Test
	public void testGetNodeList() throws XPathExpressionException {
		// Arrange
		String xPath = "//hl7:templateId";

		// Act
		NodeList nodeList = sut.getNodeList(c32Doc, xPath);

		// Assert
		assertEquals(176, nodeList.getLength());
	}
}
