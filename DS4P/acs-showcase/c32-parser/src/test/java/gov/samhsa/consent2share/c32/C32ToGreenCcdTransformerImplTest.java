package gov.samhsa.consent2share.c32;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;

public class C32ToGreenCcdTransformerImplTest {

	@Test(expected = IllegalArgumentException.class)
	public void testTransformC32ToGreenCcd_Given_Null_C32Xml_Throws_IllegalArgumentException()
			throws Throwable {
		// Arrange
		C32ToGreenCcdTransformerImpl sut = new C32ToGreenCcdTransformerImpl();

		// Act
		sut.TransformC32ToGreenCcd(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTransformC32ToGreenCcd_Given_Empty_C32Xml_Throws_IllegalArgumentException()
			throws Throwable {
		// Arrange
		C32ToGreenCcdTransformerImpl sut = new C32ToGreenCcdTransformerImpl();

		// Act
		sut.TransformC32ToGreenCcd("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTransformC32ToGreenCcd_Given_NoText_C32Xml_Throws_IllegalArgumentException()
			throws Throwable {
		// Arrange
		C32ToGreenCcdTransformerImpl sut = new C32ToGreenCcdTransformerImpl();

		// Act
		sut.TransformC32ToGreenCcd(" ");
	}

	@Test
	public void testTransformC32ToGreenCcd_Given_Valid_C32Xml_Succeeds()
			throws Throwable {
		// Arrange
		C32ToGreenCcdTransformerImpl sut = new C32ToGreenCcdTransformerImpl();

		// Read c32 file from resource
		final String c32FileUri = "c32.xml";
		String c32Xml = ResourceFileReader
				.getStringFromResourceFile(c32FileUri);

		// Act
		String greenCcd = sut.TransformC32ToGreenCcd(c32Xml);

		// Assert
		final String greenCcdFileUri = "green_ccd_transformed_from_c32.xml";
		String expectedGreenCcd = ResourceFileReader
				.getStringFromResourceFile(greenCcdFileUri);

		XMLUnit.setIgnoreWhitespace(true);
		XMLUnit.setNormalizeWhitespace(true);

		assertXMLEqual("", expectedGreenCcd, greenCcd);
	}

	@Test(expected = C32ToGreenCcdTransformerException.class)
	public void testTransformC32ToGreenCcd_Given_Invalid_C32Xml_Succeeds()
			throws Throwable {
		// Arrange
		C32ToGreenCcdTransformerImpl sut = new C32ToGreenCcdTransformerImpl();

		String c32 = "Invalid C32";

		// Act
		String greenCcd = sut.TransformC32ToGreenCcd(c32);
	}
}
