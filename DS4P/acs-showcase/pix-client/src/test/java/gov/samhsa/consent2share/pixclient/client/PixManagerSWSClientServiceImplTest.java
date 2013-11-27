package gov.samhsa.consent2share.pixclient.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.pixclient.util.PixManagerBean;
import gov.samhsa.consent2share.pixclient.util.PixManagerConstants;
import gov.samhsa.consent2share.pixclient.util.PixManagerRequestXMLToJava;
import gov.samhsa.consent2share.pixclient.ws.MCCIIN000002UV01;
import gov.samhsa.consent2share.pixclient.ws.ObjectFactory;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201301UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201302UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201309UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201310UV02;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ws.client.core.WebServiceTemplate;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class PixManagerSWSClientServiceImplTest {

	@InjectMocks
	PixManagerSWSClientServiceImpl sut;

	@Mock
	WebServiceTemplate wsTemplateMock = null;
	
	@Mock
	InputStream isPlaceMock;

	@Mock
	PixManagerRequestXMLToJava requestXMLToJavaMock;	
	
	@Test
	public void testAddPerson() throws JAXBException {
		// Arrange
		PRPAIN201301UV02 pRPAIN201301UV02Mock = mock(PRPAIN201301UV02.class);
		MCCIIN000002UV01 mCCIIN000002UV01Mock = mock(MCCIIN000002UV01.class);
		String reqXMLPathMock = "";
		when(requestXMLToJavaMock.getPIXAddReqObject(reqXMLPathMock,PixManagerConstants.ENCODE_STRING))
				.thenReturn(pRPAIN201301UV02Mock);
		when(wsTemplateMock.marshalSendAndReceive(pRPAIN201301UV02Mock))
		.thenReturn(mCCIIN000002UV01Mock);
		
		when(wsTemplateMock.marshalSendAndReceive(pRPAIN201301UV02Mock))
		.thenReturn(mCCIIN000002UV01Mock);

		// Act
		String  actualObj = sut.addPerson(reqXMLPathMock);

		// Assert
		assertEquals(reqXMLPathMock, actualObj);
		
	}


	@Test
	public void testAddPerson_JaxbException() throws JAXBException {
		// Arrange
		String reqXMLPathMock = "";
		when(requestXMLToJavaMock.getPIXAddReqObject(reqXMLPathMock,PixManagerConstants.ENCODE_STRING))
		.thenThrow(JAXBException.class);
		// Act
		String  actualObj = sut.addPerson(reqXMLPathMock);

		// Assert
		assertTrue("Actual Add message " + actualObj , actualObj.startsWith("Add Failure! "))	;	
		
	}	
	
	@Test
	public void testUpdatePerson() throws JAXBException {
		// Arrange
		PRPAIN201302UV02 pRPAIN201302UV02Mock = mock(PRPAIN201302UV02.class);
		MCCIIN000002UV01 mCCIIN000002UV01Mock = mock(MCCIIN000002UV01.class);
		String reqXMLPathMock = "";
		when(requestXMLToJavaMock.getPIXUpdateReqObject(reqXMLPathMock,PixManagerConstants.ENCODE_STRING))
				.thenReturn(pRPAIN201302UV02Mock);
		when(wsTemplateMock.marshalSendAndReceive(pRPAIN201302UV02Mock))
		.thenReturn(mCCIIN000002UV01Mock);
		
		when(wsTemplateMock.marshalSendAndReceive(pRPAIN201302UV02Mock))
		.thenReturn(mCCIIN000002UV01Mock);

		// Act
		String  actualObj = sut.updatePerson(reqXMLPathMock);

		// Assert
		assertEquals(reqXMLPathMock, actualObj);
		
	}

	@Test
	public void testUpdatePerson_JaxbException() throws JAXBException {
		// Arrange
		String reqXMLPathMock = "";
		when(requestXMLToJavaMock.getPIXUpdateReqObject(reqXMLPathMock,PixManagerConstants.ENCODE_STRING))
		.thenThrow(JAXBException.class);
		// Act
		String  actualObj = sut.updatePerson(reqXMLPathMock);

		// Assert
		assertTrue("Actual Update message " + actualObj , actualObj.startsWith("Update Failure! "))	;	
		
	}	
	
	@Test
	public void testQueryPerson() throws JAXBException {
		// Arrange
		PRPAIN201309UV02 pRPAIN201309UV02Mock = mock(PRPAIN201309UV02.class);
		PRPAIN201310UV02 pRPAIN201310UV02Mock = mock(PRPAIN201310UV02.class);
		String reqXMLPathMock = "";
		PixManagerBean  pixMgrBean = new PixManagerBean();
		when(requestXMLToJavaMock.getPIXQueryReqObject(reqXMLPathMock,PixManagerConstants.ENCODE_STRING))
				.thenReturn(pRPAIN201309UV02Mock);
		when(wsTemplateMock.marshalSendAndReceive(pRPAIN201309UV02Mock))
		.thenReturn(pRPAIN201310UV02Mock);
		
		when(wsTemplateMock.marshalSendAndReceive(pRPAIN201309UV02Mock))
		.thenReturn(pRPAIN201310UV02Mock);

		// Act
		PixManagerBean  actualObj = sut.queryPerson(reqXMLPathMock);

		// Assert
		assertEquals(pixMgrBean.getQueryMessage(), actualObj.getQueryMessage());		
	}
	
	@Test
	public void testQueryPerson_JaxbException() throws JAXBException {
		// Arrange
		String reqXMLPathMock = "";
		when(requestXMLToJavaMock.getPIXQueryReqObject(reqXMLPathMock,PixManagerConstants.ENCODE_STRING))
		.thenThrow(JAXBException.class);
		// Act
		PixManagerBean  actualObj = sut.queryPerson(reqXMLPathMock);

		// Assert
		assertTrue("Actual Query message " + actualObj.getQueryMessage() , actualObj.getQueryMessage().startsWith("Query Failure! "))	;	
		
	}
	


	@Test
	public void testGetGeneralExpMessage_Add() {
		// Arrange
		JAXBException JAXBExceptionMock = mock(JAXBException.class);
		PixManagerBean pixManagerBean = new PixManagerBean();
		
		// Act
		sut.getGeneralExpMessage(JAXBExceptionMock, pixManagerBean, PixManagerConstants.PIX_ADD);

		// Assert
		assertTrue("Actual Add message " + pixManagerBean.getAddMessage() , pixManagerBean.getAddMessage().startsWith("Add Failure! "))	;	
	
	}
		
	@Test
	public void testGetGeneralExpMessage_Update() {
		// Arrange
		JAXBException JAXBExceptionMock = mock(JAXBException.class);
		PixManagerBean pixManagerBean = new PixManagerBean();
		
		// Act
		sut.getGeneralExpMessage(JAXBExceptionMock, pixManagerBean, PixManagerConstants.PIX_UPDATE);

		// Assert
		assertTrue("Actual Update message " + pixManagerBean.getUpdateMessage() , pixManagerBean.getUpdateMessage().startsWith("Update Failure! "))	;	
	
	}
	@Test
	public void testGetGeneralExpMessage_Query() {
		// Arrange
		JAXBException JAXBExceptionMock = mock(JAXBException.class);
		PixManagerBean pixManagerBean = new PixManagerBean();
		
		// Act
		sut.getGeneralExpMessage(JAXBExceptionMock, pixManagerBean, PixManagerConstants.PIX_QUERY);

		// Assert
		assertTrue("Actual Query message " + pixManagerBean.getQueryMessage() , pixManagerBean.getQueryMessage().startsWith("Query Failure! "))	;	
	
	}	
	
	@Test
	public void testGetAddUpdateessage() throws JAXBException {
		// Arrange
	
		MCCIIN000002UV01 mCCIIN000002UV01 = (MCCIIN000002UV01) getPIXResObject("xml/AddSample_MCCIIN000002UV01.xml",PixManagerConstants.ENCODE_STRING);
			
		PixManagerBean pixManagerBean =  new PixManagerBean();
		
		// Act
		sut.getAddUpdateessage(mCCIIN000002UV01, pixManagerBean, PixManagerConstants.PIX_ADD);

		// Assert
		assertTrue("Actual Add message " + pixManagerBean.getAddMessage(), pixManagerBean.getAddMessage().startsWith("Add Success! "))	;	
		
	}

	@Test
	public void testGetAddUpdateessage_error() throws JAXBException {
		
		MCCIIN000002UV01 mCCIIN000002UV01 = (MCCIIN000002UV01) getPIXResObject("xml/AddSampleError_MCCIIN000002UV01.xml",PixManagerConstants.ENCODE_STRING);
			
		PixManagerBean pixManagerBean =  new PixManagerBean();
		
		// Act
		sut.getAddUpdateessage(mCCIIN000002UV01, pixManagerBean, PixManagerConstants.PIX_ADD);


		// Assert
		assertTrue("Actual Add message " + pixManagerBean.getAddMessage() , pixManagerBean.getAddMessage().startsWith("Add Failure! "))	;	
		
	}	
	
	@Test
	public void testGetAddUpdateessage_errorcode() throws JAXBException {
		MCCIIN000002UV01 mCCIIN000002UV01 = (MCCIIN000002UV01) getPIXResObject("xml/AddSampleErrorCode_MCCIIN000002UV01.xml",PixManagerConstants.ENCODE_STRING);
			
		PixManagerBean pixManagerBean =  new PixManagerBean();
		
		// Act
		sut.getAddUpdateessage(mCCIIN000002UV01, pixManagerBean, PixManagerConstants.PIX_ADD);


		// Assert
		assertTrue("Actual Add message " + pixManagerBean.getAddMessage() , pixManagerBean.getAddMessage().startsWith("Add Failure! "))	;	
		
	}	


	@Test
	public void testGetAddUpdateessage_Update() throws JAXBException {
		// Arrange
	
		MCCIIN000002UV01 mCCIIN000002UV01 = (MCCIIN000002UV01) getPIXResObject("xml/AddSample_MCCIIN000002UV01.xml",PixManagerConstants.ENCODE_STRING);
			
		PixManagerBean pixManagerBean =  new PixManagerBean();
		
		// Act
		sut.getAddUpdateessage(mCCIIN000002UV01, pixManagerBean, PixManagerConstants.PIX_UPDATE);

		// Assert
		assertTrue("Actual Update message " + pixManagerBean.getUpdateMessage(), pixManagerBean.getUpdateMessage().startsWith("Update Success! "))	;	
		
	}

	@Test
	public void testGetAddUpdateessage_Update_error() throws JAXBException {
		
		MCCIIN000002UV01 mCCIIN000002UV01 = (MCCIIN000002UV01) getPIXResObject("xml/AddSampleError_MCCIIN000002UV01.xml",PixManagerConstants.ENCODE_STRING);
			
		PixManagerBean pixManagerBean =  new PixManagerBean();
		
		// Act
		sut.getAddUpdateessage(mCCIIN000002UV01, pixManagerBean, PixManagerConstants.PIX_UPDATE);


		// Assert
		assertTrue("Actual Update message " + pixManagerBean.getUpdateMessage() , pixManagerBean.getUpdateMessage().startsWith("Update Failure! "))	;	
		
	}	
	
	@Test
	public void testGetAddUpdateessage_Update_errorcode() throws JAXBException {
		MCCIIN000002UV01 mCCIIN000002UV01 = (MCCIIN000002UV01) getPIXResObject("xml/AddSampleErrorCode_MCCIIN000002UV01.xml",PixManagerConstants.ENCODE_STRING);
			
		PixManagerBean pixManagerBean =  new PixManagerBean();
		
		// Act
		sut.getAddUpdateessage(mCCIIN000002UV01, pixManagerBean, PixManagerConstants.PIX_UPDATE);


		// Assert
		assertTrue("Actual Add message " + pixManagerBean.getUpdateMessage() , pixManagerBean.getUpdateMessage().startsWith("Update Failure! "))	;	
		
	}		
	
	@Test
	public void testGetQueryMessage() throws JAXBException {
		// Arrange
	
		PRPAIN201310UV02 pRPAIN201310UV02 = (PRPAIN201310UV02)  getPIXResObject("xml/QuerySample_PRPA_IN201310UV02.xml",PixManagerConstants.ENCODE_STRING);
			
		PixManagerBean pixManagerBean =  new PixManagerBean();
		
		// Act
		sut.getQueryMessage(pRPAIN201310UV02, pixManagerBean);

		// Assert
		assertTrue("Actual query message " + pixManagerBean.getQueryMessage(), pixManagerBean.getQueryMessage().startsWith("Query Success! "))	;	
		
	}

	@Test
	public void testGetQueryMessage_error() throws JAXBException {
		// Arrange
	
		PRPAIN201310UV02 pRPAIN201310UV02 = (PRPAIN201310UV02) getPIXResObject("xml/QuerySampleError_PRPA_IN201310UV02.xml",PixManagerConstants.ENCODE_STRING);
			
		PixManagerBean pixManagerBean =  new PixManagerBean();
		
		// Act
		sut.getQueryMessage(pRPAIN201310UV02, pixManagerBean);

		// Assert
		assertTrue("Actual query message " + pixManagerBean.getQueryMessage() , pixManagerBean.getQueryMessage().startsWith("Query Failure! "))	;	
		
	}	
	
	@Test
	public void testGetQueryMessage_errorcode() throws JAXBException {
		// Arrange
	
		PRPAIN201310UV02 pRPAIN201310UV02 = (PRPAIN201310UV02) getPIXResObject("xml/QuerySampleErrorCode_PRPA_IN201310UV02.xml",PixManagerConstants.ENCODE_STRING);
			
		PixManagerBean pixManagerBean =  new PixManagerBean();
		
		// Act
		sut.getQueryMessage(pRPAIN201310UV02, pixManagerBean);

		// Assert
		assertTrue("Actual query message " + pixManagerBean.getQueryMessage() , pixManagerBean.getQueryMessage().startsWith("Query Failure! "))	;	
		
	}		


	private Object getPIXResObject(String reqXMLFilePath,
			String encodeString) throws JAXBException {

		// 1. We need to create JAXContext instance
		JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
		
		// 2. Use JAXBContext instance to create the Unmarshaller.
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		if (reqXMLFilePath == null) {
			throw new JAXBException("input is null");
		}


		// 3. Use the Unmarshaller to unmarshal the XML document to get an
		// instance of JAXBElement.
		// 4. Get the instance of the required JAXB Root Class from the
		// JAXBElement.
		Object resObj =  unmarshaller.unmarshal(getClass()
				.getClassLoader().getResourceAsStream(reqXMLFilePath));


		return resObj;
	}	
	
	

}
