package gov.samhsa.consent2share.showcase.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.c32.C32Parser;
import gov.samhsa.consent2share.c32.C32ParserException;
import gov.samhsa.consent2share.c32.dto.GreenCCD;
import gov.samhsa.consent2share.showcase.infrastructure.C32Getter;
import gov.samhsa.consent2share.showcase.service.EhrNumOneDto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EhrNumOneControllerTest {

	@InjectMocks
	EhrNumOneController sut;

	@Mock
	C32Getter c32GetterMock;

	@Mock
	C32Parser c32ParserMock;

	@Before
	public void before() {
		sut.setC32Getter(c32GetterMock);
		sut.setC32Parser(c32ParserMock);
	}

	@Test
	public void testGet() {
		// Arrange
		String c32XmlMock = "c32XmlMock";
		String patientIdMock = "patientIdMock";

		// Act
		when(c32GetterMock.getC32(patientIdMock)).thenReturn(c32XmlMock);
		String actualRet = sut.getC32(patientIdMock);

		// Assert
		assertEquals(c32XmlMock, actualRet);
	}

	@Test
	public void testGetC32() {
		// Arrange

		// Act
		EhrNumOneDto actualRet = sut.get();

		// Assert
		assertNotNull(actualRet);
	}

	@Test
	public void testParseC32() throws C32ParserException {
		// Arrange
		String c32XmlMock = "c32XmlMock";
		GreenCCD greenCCDMock = mock(GreenCCD.class);

		// Act
		when(c32ParserMock.ParseC32(c32XmlMock)).thenReturn(greenCCDMock);
		GreenCCD actualObj = sut.parseC32(c32XmlMock);

		// Assert
		assertEquals(greenCCDMock, actualObj);
	}

}
