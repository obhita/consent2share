package gov.samhsa.consent2share.c32;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.c32.dto.GreenCCD;

import org.junit.Assert;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class C32ParserImplTest.
 */
public class C32ParserImplTest {

	/**
	 * Test constructor_ given_ null_ c32 to green ccd transformer_ throws_ illegal argument exception.
	 *
	 * @throws Throwable the throwable
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_Given_Null_C32ToGreenCcdTransformer_Throws_IllegalArgumentException()
			throws Throwable {
		GreenCcdSerializer greenCcdSerializern = mock(GreenCcdSerializer.class);
		C32ParserImpl sut = new C32ParserImpl(null, greenCcdSerializern);
	}

	/**
	 * Test constructor_ given_ null_ green ccd serializern_ throws_ illegal argument exception.
	 *
	 * @throws Throwable the throwable
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_Given_Null_GreenCcdSerializern_Throws_IllegalArgumentException()
			throws Throwable {
		C32ToGreenCcdTransformer c32ToGreenCcdTransformer = mock(C32ToGreenCcdTransformer.class);
		C32ParserImpl sut = new C32ParserImpl(c32ToGreenCcdTransformer, null);
	}

	/**
	 * Test transform c32 to green ccd_ given_ null_ c32 xml_ throws_ illegal argument exception.
	 *
	 * @throws Throwable the throwable
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testTransformC32ToGreenCcd_Given_Null_C32Xml_Throws_IllegalArgumentException()
			throws Throwable {
		// Arrange
		C32ToGreenCcdTransformer c32ToGreenCcdTransformer = mock(C32ToGreenCcdTransformer.class);
		GreenCcdSerializer greenCcdSerializern = mock(GreenCcdSerializer.class);
		C32ParserImpl sut = new C32ParserImpl(c32ToGreenCcdTransformer,
				greenCcdSerializern);

		// Act
		sut.ParseC32(null);
	}

	/**
	 * Test transform c32 to green ccd_ given_ empty_ c32 xml_ throws_ illegal argument exception.
	 *
	 * @throws Throwable the throwable
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testTransformC32ToGreenCcd_Given_Empty_C32Xml_Throws_IllegalArgumentException()
			throws Throwable {
		C32ToGreenCcdTransformer c32ToGreenCcdTransformer = mock(C32ToGreenCcdTransformer.class);
		GreenCcdSerializer greenCcdSerializern = mock(GreenCcdSerializer.class);
		C32ParserImpl sut = new C32ParserImpl(c32ToGreenCcdTransformer,
				greenCcdSerializern);

		// Act
		sut.ParseC32("");
	}

	/**
	 * Test transform c32 to green ccd_ given_ no text_ c32 xml_ throws_ illegal argument exception.
	 *
	 * @throws Throwable the throwable
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testTransformC32ToGreenCcd_Given_NoText_C32Xml_Throws_IllegalArgumentException()
			throws Throwable {
		// Arrange
		C32ToGreenCcdTransformer c32ToGreenCcdTransformer = mock(C32ToGreenCcdTransformer.class);
		GreenCcdSerializer greenCcdSerializern = mock(GreenCcdSerializer.class);
		C32ParserImpl sut = new C32ParserImpl(c32ToGreenCcdTransformer,
				greenCcdSerializern);

		// Act
		sut.ParseC32(" ");
	}

	/**
	 * Test parse c32_ given_ valid_ c32 xml_ succeeds.
	 *
	 * @throws C32ToGreenCcdTransformerException the c32 to green ccd transformer exception
	 * @throws GreenCcdSerializerException the green ccd serializer exception
	 * @throws C32ParserException the c32 parser exception
	 */
	@Test
	public void testParseC32_Given_Valid_C32Xml_Succeeds()
			throws C32ToGreenCcdTransformerException,
			GreenCcdSerializerException, C32ParserException {
		// Arrange
		String c32Xml = "Is this a valid c32?"; // Apparently not
		final String greenCcdXmlFileUri = "green_ccd.xml";
		String greenCcdXml = ResourceFileReader
				.getStringFromResourceFile(greenCcdXmlFileUri);

		C32ToGreenCcdTransformer c32ToGreenCcdTransformer = mock(C32ToGreenCcdTransformer.class);
		GreenCcdSerializer greenCcdSerializern = mock(GreenCcdSerializer.class);
		GreenCCD greenCCD = mock(GreenCCD.class);

		when(c32ToGreenCcdTransformer.TransformC32ToGreenCcd(anyString()))
				.thenReturn(greenCcdXml);
		when(greenCcdSerializern.Deserialize(greenCcdXml)).thenReturn(greenCCD);
		
		C32ParserImpl sut = new C32ParserImpl(c32ToGreenCcdTransformer, greenCcdSerializern);
		
		// Act
		GreenCCD result = sut.ParseC32(c32Xml);
		
		// Assert
		Assert.assertEquals(greenCCD, result);
	}
	
	/**
	 * Test parse c32_ given_ c32 to green ccd transformer exception_ throws_ c32 parser exception.
	 *
	 * @throws C32ToGreenCcdTransformerException the c32 to green ccd transformer exception
	 * @throws GreenCcdSerializerException the green ccd serializer exception
	 * @throws C32ParserException the c32 parser exception
	 */
	@Test(expected=C32ParserException.class)
	public void testParseC32_Given_C32ToGreenCcdTransformerException_Throws_C32ParserException()
			throws C32ToGreenCcdTransformerException,
			GreenCcdSerializerException, C32ParserException {
		// Arrange
		String c32Xml = "Is this a valid c32?"; // Apparently not

		C32ToGreenCcdTransformer c32ToGreenCcdTransformer = mock(C32ToGreenCcdTransformer.class);
		GreenCcdSerializer greenCcdSerializern = mock(GreenCcdSerializer.class);
		GreenCCD greenCCD = mock(GreenCCD.class);

		when(c32ToGreenCcdTransformer.TransformC32ToGreenCcd(anyString()))
				.thenThrow(C32ParserException.class);
		
		C32ParserImpl sut = new C32ParserImpl(c32ToGreenCcdTransformer, greenCcdSerializern);
		
		// Act
		GreenCCD result = sut.ParseC32(c32Xml);
	}
}
