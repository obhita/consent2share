package gov.samhsa.acs.common.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.common.validation.exception.InvalidXmlDocumentException;
import gov.samhsa.acs.common.validation.exception.XmlDocumentReadFailureException;

import java.io.ByteArrayInputStream;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class XmlValidationTest {
	
	private static final String BASE_PATH = "schema/cdar2c32/infrastructure/cda/";
	private String invalidC32;
	private String validC32;
	
	private FileReaderImpl fileReader;
	private XmlValidation sut;

	@Before
	public void setUp() throws Exception {
		fileReader = new FileReaderImpl();
		invalidC32 = fileReader.readFile("invalidC32.xml");
		validC32 = fileReader.readFile("validC32.xml");
		sut = new XmlValidation(this.getClass().getClassLoader().getResourceAsStream(BASE_PATH+"C32_CDA.xsd"), BASE_PATH);
	}

	@Test(expected = InvalidXmlDocumentException.class)
	public void testValidateInputStream() throws InvalidXmlDocumentException, XmlDocumentReadFailureException {
		assertFalse(sut.validate(invalidC32));
	}
	
	@Test
	public void testXmlValidation_Initialization_Failure() {
		// Act
		XmlValidation xmlValidation = new XmlValidation(new ByteArrayInputStream(new byte[2]), "a");
		
		// Assert
		assertNull(ReflectionTestUtils.getField(xmlValidation, "validator"));		
	}

	@Test
	public void testValidateString() throws InvalidXmlDocumentException, XmlDocumentReadFailureException {
		assertTrue(sut.validate(validC32));
	}
}
