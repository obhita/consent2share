package gov.samhsa.consent2share.showcase.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import gov.samhsa.consent2share.c32.C32ParserException;
import gov.samhsa.consent2share.hl7.Hl7v3TransformerException;
import gov.samhsa.consent2share.pixclient.util.PixManagerBean;
import gov.samhsa.consent2share.showcase.service.PixManagerTransformService;
import gov.samhsa.consent2share.showcase.service.PixOperationsService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PixMgrControllerTest {
	@InjectMocks
	PixMgrController sut;	
	
	@Mock
	PixOperationsService pixOperationsServiceMock;	
	@Mock
	PixManagerTransformService pixManagerTransformServiceMock;
	
	@Before
	public void before() {
		sut.setPixManagerTransformService(pixManagerTransformServiceMock);
		sut.setPixOperationsService(pixOperationsServiceMock);
	
	}
	
	@Test
	public void testTransformC32ToPixAdd() throws Hl7v3TransformerException, C32ParserException {		
		//Arrange
		String c32XmlMock = "c32XmlMock";
		String pixAddXmlMock = "pixAddXmlMock";
		
		//Act
		when(pixManagerTransformServiceMock.getPixAddXml(c32XmlMock)).thenReturn(pixAddXmlMock);
		String actualRet = sut.transformC32ToPixAdd(c32XmlMock);
		
		//Assert
		assertEquals(pixAddXmlMock, actualRet);
		
	}

	@Test
	public void testTransformC32ToPixUpdate() throws Hl7v3TransformerException, C32ParserException {
		//Arrange
		String c32XmlMock = "c32XmlMock";
		String pixUpdateXML = "pixUpdateXML";
		
		//Act
		when(pixManagerTransformServiceMock.getPixUpdateXml(c32XmlMock)).thenReturn(pixUpdateXML);
		String actualRet = sut.transformC32ToPixUpdate(c32XmlMock);
		
		//Assert
		assertEquals(pixUpdateXML, actualRet);
	}

	@Test
	public void testTransformC32ToPixQuery() throws Hl7v3TransformerException, C32ParserException {
		//Arrange
		String c32XmlMock = "c32XmlMock";
		String pixQueryXML = "pixQueryXML";
		
		//Act
		when(pixManagerTransformServiceMock.getPixQueryXml(c32XmlMock)).thenReturn(pixQueryXML);
		String actualRet = sut.transformC32ToPixQuery(c32XmlMock);
		
		//Assert
		assertEquals(pixQueryXML, actualRet);
	}

	@Test
	public void testReqToPixAdd() throws C32ParserException, Hl7v3TransformerException {
		//Arrange
		String c32XmlMock = "c32XmlMock";
		String hl7v3XmlMock = "hl7v3XmlMock";
		String pixAddMessageMock = "pixAddMessageMock";
		
		//Act
		when(pixManagerTransformServiceMock.getPixAddXml(c32XmlMock)).thenReturn(hl7v3XmlMock);
		when(pixOperationsServiceMock.addPerson(hl7v3XmlMock)).thenReturn(pixAddMessageMock);
		String actualRet = sut.reqToPixAdd(c32XmlMock);
		
		//Assert
		assertEquals(pixAddMessageMock, actualRet);
	}

	@Test
	public void testReqToPixUpdate() throws C32ParserException, Hl7v3TransformerException {
		//Arrange
		String c32XmlMock = "c32XmlMock";
		String hl7v3XmlMock = "hl7v3XmlMock";
		String pixUpdateMessageMock = "pixUpdateMessageMock";
		
		//Act
		when(pixManagerTransformServiceMock.getPixUpdateXml(c32XmlMock)).thenReturn(hl7v3XmlMock);
		when(pixOperationsServiceMock.updatePerson(hl7v3XmlMock)).thenReturn(pixUpdateMessageMock);
		String actualRet = sut.reqToPixUpdate(c32XmlMock);
		
		//Assert
		assertEquals(pixUpdateMessageMock, actualRet);
	}

	@Test
	public void testReqToPixQuery() throws C32ParserException, Hl7v3TransformerException {
		//Arrange
		String c32XmlMock = "c32XmlMock";
		String hl7v3XmlMock = "hl7v3XmlMock";
		PixManagerBean pixManagerBeanMock = mock(PixManagerBean.class);
		
		//Act
		when(pixManagerTransformServiceMock.getPixQueryXml(c32XmlMock)).thenReturn(hl7v3XmlMock);
		when(pixOperationsServiceMock.queryPerson(hl7v3XmlMock)).thenReturn(pixManagerBeanMock);
		PixManagerBean actualObj = sut.reqToPixQuery(c32XmlMock);
		
		//Assert
		assertEquals(pixManagerBeanMock, actualObj);
	}

}
