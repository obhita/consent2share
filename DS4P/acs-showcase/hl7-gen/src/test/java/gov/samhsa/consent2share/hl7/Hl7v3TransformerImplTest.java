package gov.samhsa.consent2share.hl7;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class Hl7v3TransformerImplTest {
	
	@InjectMocks
	Hl7v3TransformerImpl sut;
	
	@Mock
	TransformerFactory transformerFactoryMock;
	

	@Test
	public void testTransformC32ToHl7v3PixXml() throws IOException, URISyntaxException, Hl7v3TransformerException {
		// Arrange
		sut.setTransformerFactory( TransformerFactory.newInstance());
		String c32XMLString = FileUtils.readFileToString(new File(getClass()
				.getClassLoader()
				.getResource("xml/c32.xml")
				.toURI()));		

		// Act
		String hl7v3PixXML = sut.transformC32ToHl7v3PixXml(c32XMLString, Hl7v3Transformer.XSLTPIXADDURI);
		
		// Assert
		assertNotNull(hl7v3PixXML);	
	}
	

	@SuppressWarnings("unchecked")
	@Test(expected = Hl7v3TransformerException.class)
	public void testTransformC32ToHl7v3PixXml_exception() throws IOException, URISyntaxException, Hl7v3TransformerException, TransformerConfigurationException {
		// Arrange
		when(transformerFactoryMock.newTransformer((StreamSource)notNull())).thenThrow(Exception.class);
		
		String c32XMLString = FileUtils.readFileToString(new File(getClass()
				.getClassLoader()
				.getResource("xml/c32.xml")
				.toURI()));		

		// Act
		String hl7v3PixXML = sut.transformC32ToHl7v3PixXml(c32XMLString, Hl7v3Transformer.XSLTPIXADDURI);
		
		// Assert
		assertNotNull(hl7v3PixXML);	
	}
		
	@Test
	public void testTransformC32ToHl7v3PixXml_hl7v3exception() throws IOException, URISyntaxException, Hl7v3TransformerException, TransformerConfigurationException {
		// Arrange
		String errorMsg = " Testing Error Msg";
		
		//Act
		Hl7v3TransformerException hl7v3 = new Hl7v3TransformerException(errorMsg);
		
		// Assert
		assertEquals(errorMsg, hl7v3.getMessage());	
	}
	
	@Test
	public void testGetPixQueryXml() throws IOException, URISyntaxException, Hl7v3TransformerException, TransformerConfigurationException {
		// Arrange
		String mrnMock = "mrnMock";
		String mrnDomainMock = "mrnDomainMock";
		sut.setTransformerFactory( TransformerFactory.newInstance());
		String picQueryxml = FileUtils.readFileToString(new File(getClass()
				.getClassLoader()
				.getResource("xml/c32ToHl7v3PixQuery.xsl")
				.toURI()));			
		//String picQueryxml = "xm/c32ToHl7v3PixQuery.xsl";

		// Act
		String hl7v3PixXML = sut.getPixQueryXml(mrnMock, mrnDomainMock, picQueryxml);
		
		// Assert
		assertNotNull(hl7v3PixXML);	
	}		


	
}
