package gov.samhsa.consent2share.showcase.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.hl7.Hl7v3Transformer;
import gov.samhsa.consent2share.hl7.Hl7v3TransformerException;
import gov.samhsa.consent2share.showcase.infrastructure.PixOperation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PixManagerTransformServiceImplTest {

	@InjectMocks
	PixManagerTransformServiceImpl sut;

	@Mock
	Hl7v3Transformer hl7v3TransformerMock;

	@Test
	public void testGetPixAddXml() throws Hl7v3TransformerException {
		// Arrange
		String pixAddMock = "pixAddMock";
		String c32XmlMock = "c32XmlMock";

		// Act
		when(
				hl7v3TransformerMock.transformC32ToHl7v3PixXml(c32XmlMock,
						Hl7v3Transformer.XSLTPIXADDURI)).thenReturn(pixAddMock);
		String actualRet = sut.getPixAddXml(c32XmlMock);

		// Assert
		assertEquals(pixAddMock, actualRet);

	}

	@Test
	public void testGetPixUpdateXml() throws Hl7v3TransformerException {
		// Arrange
		String pixUpdateMock = "pixUpdateMock";
		String c32XmlMock = "c32XmlMock";

		// Act
		when(
				hl7v3TransformerMock.transformC32ToHl7v3PixXml(c32XmlMock,
						Hl7v3Transformer.XSLTPIXUPDATEURI)).thenReturn(
				pixUpdateMock);
		String actualRet = sut.getPixUpdateXml(c32XmlMock);

		// Assert
		assertEquals(pixUpdateMock, actualRet);
	}

	@Test
	public void testGetPixQueryXml() throws Hl7v3TransformerException {
		// Arrange
		String pixQueryMock = "pixQueryMock";
		String c32XmlMock = "c32XmlMock";

		// Act
		when(
				hl7v3TransformerMock.transformC32ToHl7v3PixXml(c32XmlMock,
						Hl7v3Transformer.XSLTPIXQUERYURI)).thenReturn(
				pixQueryMock);
		String actualRet = sut.getPixQueryXml(c32XmlMock);

		// Assert
		assertEquals(pixQueryMock, actualRet);
	}

	@Test
	public void testGetPixXml() throws Hl7v3TransformerException {
		// Arrange
		String c32XmlMock = "c32XmlMock";
		String pixAddXmlMock = "pixAddXmlMock";
		String pixUpdateXmlMock = "pixUpdateXmlMock";
		String pixQueryXmlMock = "pixQueryXmlMock";
		String nullReturnMock = "nullReturnMock";

		when(
				hl7v3TransformerMock.transformC32ToHl7v3PixXml(c32XmlMock,
						Hl7v3Transformer.XSLTPIXADDURI)).thenReturn(
				pixAddXmlMock);
		when(
				hl7v3TransformerMock.transformC32ToHl7v3PixXml(c32XmlMock,
						Hl7v3Transformer.XSLTPIXUPDATEURI)).thenReturn(
				pixUpdateXmlMock);
		when(
				hl7v3TransformerMock.transformC32ToHl7v3PixXml(c32XmlMock,
						Hl7v3Transformer.XSLTPIXQUERYURI)).thenReturn(
				pixQueryXmlMock);
		when(hl7v3TransformerMock.transformC32ToHl7v3PixXml(c32XmlMock, null))
				.thenReturn(nullReturnMock);

		// Act
		String addResponse = sut.getPixXml(PixOperation.ADD, c32XmlMock);
		String updateResponse = sut.getPixXml(PixOperation.UPDATE, c32XmlMock);
		String queryResponse = sut.getPixXml(PixOperation.QUERY, c32XmlMock);
		String nullResponse = sut.getPixXml(PixOperation.MERGE, c32XmlMock);

		// Assert
		assertEquals(pixAddXmlMock, addResponse);
		assertEquals(pixUpdateXmlMock, updateResponse);
		assertEquals(pixQueryXmlMock, queryResponse);
		assertEquals(nullReturnMock, nullResponse);
	}
}
