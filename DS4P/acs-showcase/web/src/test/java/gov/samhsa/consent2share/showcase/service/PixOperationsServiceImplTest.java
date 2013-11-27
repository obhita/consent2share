package gov.samhsa.consent2share.showcase.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.consent2share.hl7.Hl7v3TransformerException;
import gov.samhsa.consent2share.pixclient.service.PixManagerService;
import gov.samhsa.consent2share.pixclient.util.PixManagerBean;
import gov.samhsa.consent2share.pixclient.util.PixManagerConstants;
import gov.samhsa.consent2share.pixclient.util.PixManagerRequestXMLToJava;
import gov.samhsa.consent2share.pixclient.ws.MCCIIN000002UV01;
import gov.samhsa.consent2share.pixclient.ws.ObjectFactory;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201301UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201302UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201309UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201310UV02;
import gov.samhsa.consent2share.showcase.exception.AcsShowCaseException;
import gov.samhsa.consent2share.showcase.infrastructure.XdsbRegistryGetter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class PixOperationsServiceImplTest {

	@InjectMocks
	PixOperationsServiceImpl sut;
	
	@Mock
	private PixManagerRequestXMLToJava requestXMLToJavaMock;
	
	@Mock
	private PixManagerBean pixMgrBeanMock;

	@Mock
	private PixManagerService pixMgrServiceMock;
	
	@Mock
	PixManagerTransformService pixManagerTransformServiceMock;
	
	@Mock
	XdsbRegistryGetter xdsbRegistryGetterMock;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testAddPerson() throws JAXBException {
		// Arrange
		String reqMock = "reqMock";
		PRPAIN201301UV02 requestMock = mock(PRPAIN201301UV02.class);
		MCCIIN000002UV01 responseMock = mock(MCCIIN000002UV01.class);
		sut.setPixMgrService(pixMgrServiceMock);
		sut.setRequestXMLToJava(requestXMLToJavaMock);
		// Act
		when(
				requestXMLToJavaMock.getPIXAddReqObject(reqMock,
						PixManagerConstants.ENCODE_STRING)).thenReturn(
				requestMock);
		when(pixMgrServiceMock.pixManagerPRPAIN201301UV02(requestMock))
				.thenReturn(responseMock);
		String actRet = sut.addPerson(reqMock);
		// Assert
		assertTrue("actual value" + actRet, actRet.trim().length() == 0);
	}

	@Test
	public void testAddPerson_exception() throws JAXBException {
		// Arrange
		String reqMock = "reqMock";
		PRPAIN201301UV02 requestMock = mock(PRPAIN201301UV02.class);
		sut.setPixMgrService(pixMgrServiceMock);
		sut.setRequestXMLToJava(requestXMLToJavaMock);
		// Act
		when(
				requestXMLToJavaMock.getPIXAddReqObject(reqMock,
						PixManagerConstants.ENCODE_STRING)).thenReturn(
				requestMock);
		when(pixMgrServiceMock.pixManagerPRPAIN201301UV02(requestMock))
				.thenThrow(JAXBException.class);
		String actualObj = sut.addPerson(reqMock);

		// Assert
		assertTrue("Actual Add message " + actualObj,
				actualObj.startsWith("Add Failure! "));

	}

	@Test
	public void testUpdatePerson() throws JAXBException {
		// Arrange
		String reqMock = "reqMock";
		PRPAIN201302UV02 requestMock = mock(PRPAIN201302UV02.class);
		MCCIIN000002UV01 responseMock = mock(MCCIIN000002UV01.class);
		sut.setPixMgrService(pixMgrServiceMock);
		sut.setRequestXMLToJava(requestXMLToJavaMock);
		// Act
		when(
				requestXMLToJavaMock.getPIXUpdateReqObject(reqMock,
						PixManagerConstants.ENCODE_STRING)).thenReturn(
				requestMock);
		when(pixMgrServiceMock.pixManagerPRPAIN201302UV02(requestMock))
				.thenReturn(responseMock);
		String actRet = sut.updatePerson(reqMock);
		// Assert
		assertTrue("actual value" + actRet, actRet.trim().length() == 0);
	}

	@Test
	public void testUpdatePerson_exception() throws JAXBException {
		// Arrange
		String reqMock = "reqMock";
		PRPAIN201302UV02 requestMock = mock(PRPAIN201302UV02.class);
		sut.setPixMgrService(pixMgrServiceMock);
		sut.setRequestXMLToJava(requestXMLToJavaMock);
		// Act
		when(
				requestXMLToJavaMock.getPIXUpdateReqObject(reqMock,
						PixManagerConstants.ENCODE_STRING)).thenReturn(
				requestMock);
		when(pixMgrServiceMock.pixManagerPRPAIN201302UV02(requestMock))
				.thenThrow(JAXBException.class);
		String actualObj = sut.updatePerson(reqMock);
		// Assert
		assertTrue("Actual update message " + actualObj,
				actualObj.startsWith("Update Failure! "));
	}

	@Test
	public void testQueryPerson() throws JAXBException {
		// Arrange
		String reqMock = "reqMock";
		PRPAIN201309UV02 requestMock = mock(PRPAIN201309UV02.class);
		PRPAIN201310UV02 responseMock = mock(PRPAIN201310UV02.class);
		PixManagerBean pixMgrBean = new PixManagerBean();
		sut.setPixMgrService(pixMgrServiceMock);
		sut.setRequestXMLToJava(requestXMLToJavaMock);
		// Act
		when(
				requestXMLToJavaMock.getPIXQueryReqObject(reqMock,
						PixManagerConstants.ENCODE_STRING)).thenReturn(
				requestMock);
		when(pixMgrServiceMock.pixManagerPRPAIN201309UV02(requestMock))
				.thenReturn(responseMock);
		PixManagerBean actObj = sut.queryPerson(reqMock);
		// Assert
		assertEquals(pixMgrBean.getQueryMessage(), actObj.getQueryMessage());
	}

	@Test
	public void testQueryPerson_excception() throws JAXBException {
		// Arrange
		String reqMock = "reqMock";
		PRPAIN201309UV02 requestMock = mock(PRPAIN201309UV02.class);
		sut.setPixMgrService(pixMgrServiceMock);
		sut.setRequestXMLToJava(requestXMLToJavaMock);
		// Act
		when(
				requestXMLToJavaMock.getPIXQueryReqObject(reqMock,
						PixManagerConstants.ENCODE_STRING)).thenReturn(
				requestMock);
		when(pixMgrServiceMock.pixManagerPRPAIN201309UV02(requestMock))
				.thenThrow(JAXBException.class);
		PixManagerBean actualObj = sut.queryPerson(reqMock);
		// Assert
		assertTrue("Actual Query message " + actualObj.getQueryMessage(),
				actualObj.getQueryMessage().startsWith("Query Failure! "));
	}

	@Test
	public void testGetGeneralExpMessage_add() {
		// Arrange
		JAXBException JAXBExceptionMock = mock(JAXBException.class);
		PixManagerBean pixManagerBean = new PixManagerBean();

		// Act
		sut.getGeneralExpMessage(JAXBExceptionMock, pixManagerBean,
				PixManagerConstants.PIX_ADD);

		// Asserts
		assertTrue("Actual Add message " + pixManagerBean.getAddMessage(),
				pixManagerBean.getAddMessage().startsWith("Add Failure! "));
	}

	@Test
	public void testGetGeneralExpMessage_Update() {
		// Arrange
		JAXBException JAXBExceptionMock = mock(JAXBException.class);
		PixManagerBean pixManagerBean = new PixManagerBean();

		// Act
		sut.getGeneralExpMessage(JAXBExceptionMock, pixManagerBean,
				PixManagerConstants.PIX_UPDATE);

		// Assert
		assertTrue(
				"Actual Update message " + pixManagerBean.getUpdateMessage(),
				pixManagerBean.getUpdateMessage()
						.startsWith("Update Failure! "));

	}

	@Test
	public void testGetGeneralExpMessage_Query() {
		// Arrange
		JAXBException JAXBExceptionMock = mock(JAXBException.class);
		PixManagerBean pixManagerBean = new PixManagerBean();

		// Act
		sut.getGeneralExpMessage(JAXBExceptionMock, pixManagerBean,
				PixManagerConstants.PIX_QUERY);

		// Assert
		assertTrue("Actual Query message " + pixManagerBean.getQueryMessage(),
				pixManagerBean.getQueryMessage().startsWith("Query Failure! "));

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
		assertTrue("Actual Update message " + pixManagerBean.getUpdateMessage() , pixManagerBean.getUpdateMessage().startsWith("Update Failure! "))	;	
		
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
	
	//@Test
	public void testAddPatientRegistryRecord() throws JAXBException, Hl7v3TransformerException {
		// Arrange
	
		String c32XmlMock = "c32XmlMock";
		String hl7v3XmlMock = "hl7v3XmlMock";
		sut.setPixManagerTransformService(pixManagerTransformServiceMock);
		PRPAIN201309UV02 requestMock = mock(PRPAIN201309UV02.class);
		PRPAIN201310UV02 responseMock = mock(PRPAIN201310UV02.class);
		sut.setPixMgrService(pixMgrServiceMock);
		sut.setRequestXMLToJava(requestXMLToJavaMock);	
		sut.setPixManagerTransformService(pixManagerTransformServiceMock);
		sut.setXdsbRegistryGetter(xdsbRegistryGetterMock);
		
		// Act
		when(pixManagerTransformServiceMock.getPixQueryXml(c32XmlMock)).thenReturn(hl7v3XmlMock);
		when(
				requestXMLToJavaMock.getPIXQueryReqObject(hl7v3XmlMock,
						PixManagerConstants.ENCODE_STRING)).thenReturn(
				requestMock);
		when(pixMgrServiceMock.pixManagerPRPAIN201309UV02(requestMock))
				.thenReturn(responseMock);
	
		
		
		String actRet = sut.addPatientRegistryRecord(c32XmlMock);

		// Assert
		assertTrue("actual value" + actRet, actRet.trim().length() == 0);
		
	
	}
	
	//@Test
	public void testAddPatientRegistryRecord_exception() throws JAXBException, Hl7v3TransformerException {
		// Arrange
		String c32XmlMock = "c32XmlMock";
		sut.setPixManagerTransformService(pixManagerTransformServiceMock);
		sut.setXdsbRegistryGetter(xdsbRegistryGetterMock);
		thrown.expect(AcsShowCaseException.class);
	
		// Act
		when(pixManagerTransformServiceMock.getPixQueryXml(c32XmlMock)).thenThrow(AcsShowCaseException.class);
		sut.addPatientRegistryRecord(c32XmlMock);
		
	}	
	
	@Test
	public void testAddPatientRegistryRecord_temp() throws JAXBException, Hl7v3TransformerException {
		// Arrange
		
		String c32XmlMock = "c32XmlMock";
		sut.setPixManagerTransformService(pixManagerTransformServiceMock);
		sut.setXdsbRegistryGetter(xdsbRegistryGetterMock);
		
		// Act
		String actRet = sut.addPatientRegistryRecord(c32XmlMock);

		// Assert
		assertTrue("actual value" + actRet, actRet.trim().length() == 0);
		
	}


}
