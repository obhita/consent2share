package gov.samhsa.consent2share.showcase.service;

import static org.junit.Assert.assertEquals;
import gov.samhsa.acs.common.tool.DocumentXmlConverterImpl;

import org.junit.Before;
import org.junit.Test;

public class XmlAttributeSetterImplTest {
	private final String XML_STRING_NO_EXISTING_ATTRIBUTE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><TestXml xmlns=\"urn:hl7-org:v3\"><TestItem></TestItem></TestXml>";
	private final String XML_STRING_WITH_ATTRIBUTE_EMPTY_VALUE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><TestXml xmlns=\"urn:hl7-org:v3\"><TestItem TestAttribute=\"\"></TestItem></TestXml>";
	private final String XML_STRING_WITH_ATTRIBUTE_NOT_EMPTY_VALUE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><TestXml xmlns=\"urn:hl7-org:v3\"><TestItem TestAttribute=\"SomeValue\"></TestItem></TestXml>";
	private final String EXPECTED_OUTPUT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><TestXml xmlns=\"urn:hl7-org:v3\">   <TestItem TestAttribute=\"TestValue\"/></TestXml>";
	private final String XPATH_EXPR = "//hl7:TestXml/child::hl7:TestItem";
	private final String TEST_ATTRIBUTE = "TestAttribute";
	private final String TEST_VALUE = "TestValue";

	private XmlAttributeSetterImpl sut;

	@Before
	public void setUp() throws Exception {
		// Arrange
		sut = new XmlAttributeSetterImpl();
		sut.setDocumentXmlConverter(new DocumentXmlConverterImpl());
	}

	@Test
	public void testSetAttributeValue_Without_Existing_Attribute()
			throws Exception {
		// Act
		String actualOutput = sut.setAttributeValue(
				XML_STRING_NO_EXISTING_ATTRIBUTE, XPATH_EXPR, TEST_ATTRIBUTE,
				TEST_VALUE);

		// Assert
		assertEquals(EXPECTED_OUTPUT, actualOutput);
	}

	@Test
	public void testSetAttributeValue_With_Existing_Attribute_Empty_Value()
			throws Exception {
		// Act
		String actualOutput = sut.setAttributeValue(XML_STRING_WITH_ATTRIBUTE_EMPTY_VALUE,
				XPATH_EXPR, TEST_ATTRIBUTE, TEST_VALUE);

		// Assert
		assertEquals(EXPECTED_OUTPUT, actualOutput);
	}
	
	@Test
	public void testSetAttributeValue_With_Existing_Attribute_Not_Empty_Value()
			throws Exception {
		// Act
		String actualOutput = sut.setAttributeValue(XML_STRING_WITH_ATTRIBUTE_NOT_EMPTY_VALUE,
				XPATH_EXPR, TEST_ATTRIBUTE, TEST_VALUE);

		// Assert
		assertEquals(EXPECTED_OUTPUT, actualOutput);
	}
}
