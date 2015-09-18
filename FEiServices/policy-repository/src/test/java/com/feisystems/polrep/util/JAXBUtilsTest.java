package com.feisystems.polrep.util;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBException;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.StringUtils;
import org.xml.sax.SAXException;

@RunWith(MockitoJUnitRunner.class)
public class JAXBUtilsTest {

	private static final String FIELD2_VALUE = "field2Value";
	private static final String FIELD1_VALUE = "field1Value";
	private static final String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><JAXBTestElement><field1>field1Value</field1><field2>field2Value</field2></JAXBTestElement>";
	private static final String XML_WITHOUT_ROOT = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><JAXBTestElementWithoutRoot><field1>field1Value</field1><field2>field2Value</field2></JAXBTestElementWithoutRoot>";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testMarshal() throws JAXBException, SAXException, IOException {
		// Arrange
		final JAXBTestElement obj = new JAXBTestElement();
		obj.setField1(FIELD1_VALUE);
		obj.setField2(FIELD2_VALUE);

		// Act
		final String xml = JAXBUtils.marshal(obj);

		// Assert
		assertNotNull(xml);
		assertTrue(StringUtils.hasText(xml));
		assertXMLEqual(XML, xml);

	}

	@Test
	public void testMarshal_JAXBException() throws JAXBException, SAXException,
			IOException {
		// Act
		thrown.expect(JAXBException.class);
		JAXBUtils.marshal(new Object());
	}

	@Test
	public void testMarshalWithoutRootElement() throws JAXBException,
			SAXException, IOException {
		// Arrange
		final JAXBTestElementWithoutRoot obj = new JAXBTestElementWithoutRoot();
		obj.setField1(FIELD1_VALUE);
		obj.setField2(FIELD2_VALUE);

		// Act
		final String xml = JAXBUtils.marshalWithoutRootElement(obj);

		// Assert
		assertNotNull(xml);
		assertTrue(StringUtils.hasText(xml));
		assertXMLEqual(XML_WITHOUT_ROOT, xml);
	}

	@Test
	public void testUnmarshalFromXml() throws UnsupportedEncodingException,
			JAXBException {
		// Act
		final JAXBTestElement e = JAXBUtils.unmarshalFromXml(
				JAXBTestElement.class, XML);

		// Assert
		assertNotNull(e);
		assertEquals(FIELD1_VALUE, e.getField1());
		assertEquals(FIELD2_VALUE, e.getField2());
	}

	@BeforeClass
	public static void setUp() throws Exception {
		XMLUnit.setIgnoreAttributeOrder(true);
		XMLUnit.setIgnoreWhitespace(true);
	}

}
