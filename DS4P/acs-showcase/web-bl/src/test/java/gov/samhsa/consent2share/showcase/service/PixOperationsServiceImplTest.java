package gov.samhsa.consent2share.showcase.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.consent2share.hl7.Hl7v3TransformerException;
import gov.samhsa.consent2share.pixclient.service.PixManagerService;
import gov.samhsa.consent2share.pixclient.util.PixManagerBean;
import gov.samhsa.consent2share.pixclient.util.PixManagerConstants;
import gov.samhsa.consent2share.pixclient.util.PixManagerMessageHelper;
import gov.samhsa.consent2share.pixclient.util.PixManagerRequestXMLToJava;
import gov.samhsa.consent2share.showcase.exception.AcsShowCaseException;
import gov.samhsa.consent2share.showcase.exception.XmlAttributeSetterException;
import gov.samhsa.consent2share.showcase.infrastructure.PixOperation;
import gov.samhsa.consent2share.showcase.infrastructure.XdsbRegistryGetter;
import gov.samhsa.consent2share.showcase.infrastructure.XdsbRepositoryService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

import org.hl7.v3.types.MCCIIN000002UV01;
import org.hl7.v3.types.PRPAIN201301UV02;
import org.hl7.v3.types.PRPAIN201302UV02;
import org.hl7.v3.types.PRPAIN201309UV02;
import org.hl7.v3.types.PRPAIN201310UV02;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class PixOperationsServiceImplTest {

	@Spy
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

	@Mock
	XdsbRepositoryService xdsbRepositoryMock;

	@Mock
	XmlAttributeSetter xmlAttributeSetter;

	@Mock
	SimpleMarshaller marshallerMock;

	@Spy
	PixManagerMessageHelper pixManagerMessageHelperMock;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testAddPatientRegistryRecord() throws Throwable {
		// Arrange
		final String c32XmlMock = "c32XmlMock";
		final String hl7v3XmlMock = "hl7v3XmlMock";
		final String eIdMock = "eIdMock";
		final String addMsgMock = "addMsgMock";
		// PRPAIN201309UV02 requestMock = mock(PRPAIN201309UV02.class);
		// PRPAIN201310UV02 responseMock = mock(PRPAIN201310UV02.class);
		final MCCIIN000002UV01 expectedResponseMock = mock(MCCIIN000002UV01.class);
		final Map<String, String> queryMapMock = new HashMap<String, String>();
		queryMapMock.put(PixManagerConstants.GLOBAL_DOMAIN_ID, eIdMock);
		sut.setPixManagerTransformService(pixManagerTransformServiceMock);
		sut.setPixMgrService(pixMgrServiceMock);
		sut.setRequestXMLToJava(requestXMLToJavaMock);
		sut.setPixManagerTransformService(pixManagerTransformServiceMock);
		sut.setXdsbRegistryGetter(xdsbRegistryGetterMock);
		sut.setMarshaller(marshallerMock);
		when(
				pixManagerTransformServiceMock.getPixXml(PixOperation.QUERY,
						c32XmlMock)).thenReturn(hl7v3XmlMock);
		// when(pixManagerTransformServiceMock.getPixQueryXml(c32XmlMock)).thenReturn(hl7v3XmlMock);
		when(
				pixManagerTransformServiceMock.getPixXml(PixOperation.ADD,
						c32XmlMock)).thenReturn(hl7v3XmlMock);
		// when(pixManagerTransformServiceMock.getPixAddXml(c32XmlMock)).thenReturn(hl7v3XmlMock);
		when(
				xdsbRegistryGetterMock.pidFeed(PixOperation.ADD, hl7v3XmlMock,
						eIdMock, PixManagerConstants.GLOBAL_DOMAIN_ID))
				.thenReturn(addMsgMock);
		// when(xdsbRegistryGetterMock.addPatientRegistryRecord(hl7v3XmlMock,eIdMock,
		// PixManagerConstants.GLOBAL_DOMAIN_ID)).thenReturn(addMsgMock);
		when(
				marshallerMock.unmarshalFromXml(MCCIIN000002UV01.class,
						addMsgMock)).thenReturn(expectedResponseMock);
		// when(requestXMLToJavaMock.getPIXQueryReqObject(hl7v3XmlMock,PixManagerConstants.ENCODE_STRING)).thenReturn(requestMock);
		// when(pixMgrServiceMock.pixManagerPRPAIN201309UV02(requestMock)).thenReturn(responseMock);
		when(pixMgrBeanMock.getQueryIdMap()).thenReturn(queryMapMock);
		doReturn(pixMgrBeanMock).when(sut).queryPerson(hl7v3XmlMock);

		// Act
		final MCCIIN000002UV01 actRetResponse = sut
				.addPatientRegistryRecord(c32XmlMock);

		// Assert
		assertEquals(expectedResponseMock, actRetResponse);
	}

	@Test
	public void testAddPatientRegistryRecord_exception() throws JAXBException,
			Hl7v3TransformerException {
		// Arrange
		final String c32XmlMock = "c32XmlMock";
		sut.setPixManagerTransformService(pixManagerTransformServiceMock);
		sut.setXdsbRegistryGetter(xdsbRegistryGetterMock);
		thrown.expect(AcsShowCaseException.class);

		// Act
		when(pixManagerTransformServiceMock.getPixQueryXml(c32XmlMock))
				.thenThrow(AcsShowCaseException.class);
		sut.addPatientRegistryRecord(c32XmlMock);
	}

	@Test
	public void testAddPatientRegistryRecord_temp() throws Throwable {
		// Arrange
		final String c32XmlMock = "c32XmlMock";
		final String hl7v3Xml = "hl7v3Xml";
		final String eidMock = "eidMock";
		final String addMsgMock = "addMsgMock";
		final PixManagerBean pixManagerBeanMock = new PixManagerBean();
		final HashMap<String, String> queryMap = new HashMap<String, String>();
		queryMap.put(PixManagerConstants.GLOBAL_DOMAIN_ID, eidMock);
		pixManagerBeanMock.setQueryIdMap(queryMap);
		// PRPAIN201309UV02 mockPRPAIN201309UV02 = mock(PRPAIN201309UV02.class);
		// PRPAIN201310UV02 mockPRPAIN201310UV02 = mock(PRPAIN201310UV02.class);
		final MCCIIN000002UV01 expectedResponseMock = mock(MCCIIN000002UV01.class);

		sut.setPixManagerTransformService(pixManagerTransformServiceMock);
		sut.setXdsbRegistryGetter(xdsbRegistryGetterMock);
		sut.setPixMgrService(pixMgrServiceMock);
		sut.setRequestXMLToJava(requestXMLToJavaMock);
		sut.setMarshaller(marshallerMock);
		// when(pixManagerTransformServiceMock.getPixQueryXml(c32XmlMock)).thenReturn(hl7v3Xml);
		// when(pixManagerTransformServiceMock.getPixAddXml(c32XmlMock)).thenReturn(hl7v3Xml);
		when(
				pixManagerTransformServiceMock.getPixXml(PixOperation.QUERY,
						c32XmlMock)).thenReturn(hl7v3Xml);
		when(
				pixManagerTransformServiceMock.getPixXml(PixOperation.ADD,
						c32XmlMock)).thenReturn(hl7v3Xml);
		// when(requestXMLToJavaMock.getPIXQueryReqObject(hl7v3Xml,
		// PixManagerConstants.ENCODE_STRING)).thenReturn(mockPRPAIN201309UV02);
		// when(pixMgrServiceMock.pixManagerPRPAIN201309UV02(mockPRPAIN201309UV02)).thenReturn(mockPRPAIN201310UV02);
		doReturn(pixManagerBeanMock).when(sut).queryPerson(hl7v3Xml);
		when(
				xdsbRegistryGetterMock.pidFeed(PixOperation.ADD, hl7v3Xml,
						eidMock, PixManagerConstants.GLOBAL_DOMAIN_ID))
				.thenReturn(addMsgMock);
		// when(xdsbRegistryGetterMock.addPatientRegistryRecord(hl7v3Xml,eidMock,
		// PixManagerConstants.GLOBAL_DOMAIN_ID)).thenReturn(addMsgMock);
		when(
				marshallerMock.unmarshalFromXml(MCCIIN000002UV01.class,
						addMsgMock)).thenReturn(expectedResponseMock);

		// Act
		final MCCIIN000002UV01 actRetResponse = sut
				.addPatientRegistryRecord(c32XmlMock);

		// Assert
		assertEquals(expectedResponseMock, actRetResponse);
	}

	@Test
	public void testAddPerson() throws JAXBException, IOException {
		// Arrange
		final String reqMock = "reqMock";
		final PRPAIN201301UV02 requestMock = mock(PRPAIN201301UV02.class);
		final MCCIIN000002UV01 responseMock = mock(MCCIIN000002UV01.class);
		sut.setPixMgrService(pixMgrServiceMock);
		sut.setRequestXMLToJava(requestXMLToJavaMock);
		// Act
		when(
				requestXMLToJavaMock.getPIXAddReqObject(reqMock,
						PixManagerConstants.ENCODE_STRING)).thenReturn(
				requestMock);
		when(pixMgrServiceMock.pixManagerPRPAIN201301UV02(requestMock))
				.thenReturn(responseMock);
		final String actRet = sut.addPerson(reqMock);
		// Assert
		assertTrue("actual value" + actRet, actRet.trim().length() == 0);
	}

	@Test
	public void testAddPerson_exception() throws JAXBException, IOException {
		// Arrange
		final String reqMock = "reqMock";
		final PRPAIN201301UV02 requestMock = mock(PRPAIN201301UV02.class);
		sut.setPixMgrService(pixMgrServiceMock);
		sut.setRequestXMLToJava(requestXMLToJavaMock);
		// Act
		when(
				requestXMLToJavaMock.getPIXAddReqObject(reqMock,
						PixManagerConstants.ENCODE_STRING)).thenReturn(
				requestMock);
		when(pixMgrServiceMock.pixManagerPRPAIN201301UV02(requestMock))
				.thenThrow(JAXBException.class);
		final String actualObj = sut.addPerson(reqMock);

		// Assert
		assertTrue("Actual Add message " + actualObj,
				actualObj.startsWith("Add Failure! "));
	}

	@Test
	public void testProvideAndRegisterClinicalDocument() throws JAXBException,
			IOException, Hl7v3TransformerException {
		// Arrange
		final String c32XmlMock = "c32XmlMock";
		final String hl7v3Xml = "hl7v3Xml";
		final String eidMock = "eidMock";
		final PRPAIN201309UV02 mockPRPAIN201309UV02 = new PRPAIN201309UV02();
		final PRPAIN201310UV02 mockPRPAIN201310UV02 = new PRPAIN201310UV02();
		final PixManagerBean pixManagerBeanMock = new PixManagerBean();
		final HashMap<String, String> queryMap = new HashMap<String, String>();
		queryMap.put(PixManagerConstants.GLOBAL_DOMAIN_ID, eidMock);
		final RegistryResponse registryResponseMock = new RegistryResponse();

		pixManagerBeanMock.setQueryIdMap(queryMap);
		sut.setPixManagerTransformService(pixManagerTransformServiceMock);
		sut.setXdsbRegistryGetter(xdsbRegistryGetterMock);
		sut.setPixMgrService(pixMgrServiceMock);
		sut.setXdsbRepository(xdsbRepositoryMock);
		sut.setXmlAttributeSetter(xmlAttributeSetter);
		sut.setRequestXMLToJava(requestXMLToJavaMock);
		when(
				requestXMLToJavaMock.getPIXQueryReqObject(hl7v3Xml,
						PixManagerConstants.ENCODE_STRING)).thenReturn(
				mockPRPAIN201309UV02);
		when(pixMgrServiceMock.pixManagerPRPAIN201309UV02(mockPRPAIN201309UV02))
				.thenReturn(mockPRPAIN201310UV02);
		when(pixManagerTransformServiceMock.getPixQueryXml(c32XmlMock))
				.thenReturn(hl7v3Xml);
		doReturn(pixManagerBeanMock).when(sut).queryPerson(hl7v3Xml);
		when(
				xmlAttributeSetter.setAttributeValue(eq(c32XmlMock),
						anyString(), anyString(), anyString()))
				.thenReturn(c32XmlMock).thenReturn(c32XmlMock)
				.thenReturn(c32XmlMock).thenReturn(c32XmlMock)
				.thenReturn(c32XmlMock).thenReturn(c32XmlMock);
		when(
				xdsbRepositoryMock.provideAndRegisterDocumentSet(c32XmlMock,
						PixManagerConstants.GLOBAL_DOMAIN_ID,
						XdsbDocumentType.CLINICAL_DOCUMENT)).thenReturn(
				registryResponseMock);

		// Act
		final RegistryResponse actualResponse = sut
				.provideAndRegisterClinicalDocument(c32XmlMock);

		// Assert
		assertEquals(registryResponseMock, actualResponse);
	}

	@Test
	public void testProvideAndRegisterClinicalDocument_Throws_AcsShowCaseException()
			throws Hl7v3TransformerException, JAXBException, IOException {
		// Arrange
		final String c32XmlMock = "c32XmlMock";
		final String hl7v3XmlMock = "hl7v3XmlMock";
		final PixManagerBean pixManagerBeanMock = new PixManagerBean();
		pixManagerBeanMock.setQueryIdMap(null);
		sut.setPixManagerTransformService(pixManagerTransformServiceMock);
		sut.setRequestXMLToJava(requestXMLToJavaMock);
		when(pixManagerTransformServiceMock.getPixQueryXml(c32XmlMock))
				.thenReturn(hl7v3XmlMock);
		when(
				requestXMLToJavaMock.getPIXQueryReqObject(hl7v3XmlMock,
						PixManagerConstants.ENCODE_STRING)).thenThrow(
				JAXBException.class);
		thrown.expect(AcsShowCaseException.class);

		// Act
		@SuppressWarnings("unused")
		final RegistryResponse actualResponse = sut
				.provideAndRegisterClinicalDocument(c32XmlMock);
	}

	@Test
	public void testProvideAndRegisterClinicalDocument_Throws_Exception()
			throws JAXBException, IOException, Hl7v3TransformerException {
		// Arrange
		final String c32XmlMock = "c32XmlMock";
		final String hl7v3Xml = "hl7v3Xml";
		final String eidMock = "eidMock";
		final PRPAIN201309UV02 mockPRPAIN201309UV02 = new PRPAIN201309UV02();
		final PRPAIN201310UV02 mockPRPAIN201310UV02 = new PRPAIN201310UV02();
		final PixManagerBean pixManagerBeanMock = new PixManagerBean();
		final HashMap<String, String> queryMap = new HashMap<String, String>();
		queryMap.put(PixManagerConstants.GLOBAL_DOMAIN_ID, eidMock);
		pixManagerBeanMock.setQueryIdMap(queryMap);
		sut.setPixManagerTransformService(pixManagerTransformServiceMock);
		sut.setXdsbRegistryGetter(xdsbRegistryGetterMock);
		sut.setPixMgrService(pixMgrServiceMock);
		sut.setXmlAttributeSetter(xmlAttributeSetter);
		sut.setRequestXMLToJava(requestXMLToJavaMock);
		when(
				requestXMLToJavaMock.getPIXQueryReqObject(hl7v3Xml,
						PixManagerConstants.ENCODE_STRING)).thenReturn(
				mockPRPAIN201309UV02);
		when(pixMgrServiceMock.pixManagerPRPAIN201309UV02(mockPRPAIN201309UV02))
				.thenReturn(mockPRPAIN201310UV02);
		when(pixManagerTransformServiceMock.getPixQueryXml(c32XmlMock))
				.thenReturn(hl7v3Xml);
		doReturn(pixManagerBeanMock).when(sut).queryPerson(hl7v3Xml);
		when(
				xmlAttributeSetter.setAttributeValue(eq(c32XmlMock),
						anyString(), anyString(), anyString())).thenThrow(
				Exception.class);
		thrown.expect(AcsShowCaseException.class);

		// Act
		@SuppressWarnings("unused")
		final RegistryResponse actualResponse = sut
				.provideAndRegisterClinicalDocument(c32XmlMock);
	}

	@Test
	public void testProvideAndRegisterClinicalDocument_Throws_Hl7v3TransformerException()
			throws Hl7v3TransformerException {
		// Arrange
		final String c32XmlMock = "c32XmlMock";
		sut.setPixManagerTransformService(pixManagerTransformServiceMock);
		when(pixManagerTransformServiceMock.getPixQueryXml(c32XmlMock))
				.thenThrow(Hl7v3TransformerException.class);
		thrown.expect(AcsShowCaseException.class);

		// Act
		@SuppressWarnings("unused")
		final RegistryResponse actualResponse = sut
				.provideAndRegisterClinicalDocument(c32XmlMock);
	}

	@Test
	public void testProvideAndRegisterClinicalDocument_Throws_XPathExpressionException()
			throws Hl7v3TransformerException, JAXBException, IOException {
		// Arrange
		final String c32XmlMock = "c32XmlMock";
		final String hl7v3Xml = "hl7v3Xml";
		final String eidMock = "eidMock";
		final PRPAIN201309UV02 mockPRPAIN201309UV02 = new PRPAIN201309UV02();
		final PRPAIN201310UV02 mockPRPAIN201310UV02 = new PRPAIN201310UV02();
		final PixManagerBean pixManagerBeanMock = new PixManagerBean();
		final HashMap<String, String> queryMap = new HashMap<String, String>();
		queryMap.put(PixManagerConstants.GLOBAL_DOMAIN_ID, eidMock);
		pixManagerBeanMock.setQueryIdMap(queryMap);
		sut.setPixManagerTransformService(pixManagerTransformServiceMock);
		sut.setXdsbRegistryGetter(xdsbRegistryGetterMock);
		sut.setPixMgrService(pixMgrServiceMock);
		sut.setXmlAttributeSetter(xmlAttributeSetter);
		sut.setRequestXMLToJava(requestXMLToJavaMock);
		when(
				requestXMLToJavaMock.getPIXQueryReqObject(hl7v3Xml,
						PixManagerConstants.ENCODE_STRING)).thenReturn(
				mockPRPAIN201309UV02);
		when(pixMgrServiceMock.pixManagerPRPAIN201309UV02(mockPRPAIN201309UV02))
				.thenReturn(mockPRPAIN201310UV02);
		when(pixManagerTransformServiceMock.getPixQueryXml(c32XmlMock))
				.thenReturn(hl7v3Xml);
		doReturn(pixManagerBeanMock).when(sut).queryPerson(hl7v3Xml);
		when(
				xmlAttributeSetter.setAttributeValue(eq(c32XmlMock),
						anyString(), anyString(), anyString())).thenThrow(
				XmlAttributeSetterException.class);
		thrown.expect(AcsShowCaseException.class);

		// Act
		@SuppressWarnings("unused")
		final RegistryResponse actualResponse = sut
				.provideAndRegisterClinicalDocument(c32XmlMock);
	}

	@Test
	public void testQueryPerson() throws JAXBException, IOException {
		// Arrange
		final String reqMock = "reqMock";
		final PRPAIN201309UV02 requestMock = mock(PRPAIN201309UV02.class);
		final PRPAIN201310UV02 responseMock = mock(PRPAIN201310UV02.class);
		final PixManagerBean pixMgrBean = new PixManagerBean();
		sut.setPixMgrService(pixMgrServiceMock);
		sut.setRequestXMLToJava(requestXMLToJavaMock);
		// Act
		when(
				requestXMLToJavaMock.getPIXQueryReqObject(reqMock,
						PixManagerConstants.ENCODE_STRING)).thenReturn(
				requestMock);
		when(pixMgrServiceMock.pixManagerPRPAIN201309UV02(requestMock))
				.thenReturn(responseMock);
		final PixManagerBean actObj = sut.queryPerson(reqMock);
		// Assert
		assertEquals(pixMgrBean.getQueryMessage(), actObj.getQueryMessage());
	}

	@Test
	public void testQueryPerson_excception() throws JAXBException, IOException {
		// Arrange
		final String reqMock = "reqMock";
		final PRPAIN201309UV02 requestMock = mock(PRPAIN201309UV02.class);
		sut.setPixMgrService(pixMgrServiceMock);
		sut.setRequestXMLToJava(requestXMLToJavaMock);
		// Act
		when(
				requestXMLToJavaMock.getPIXQueryReqObject(reqMock,
						PixManagerConstants.ENCODE_STRING)).thenReturn(
				requestMock);
		when(pixMgrServiceMock.pixManagerPRPAIN201309UV02(requestMock))
				.thenThrow(JAXBException.class);
		final PixManagerBean actualObj = sut.queryPerson(reqMock);
		// Assert
		assertTrue("Actual Query message " + actualObj.getQueryMessage(),
				actualObj.getQueryMessage().startsWith("Query Failure! "));
	}

	@Test
	public void testUpdatePatientRegistryRecord() throws Throwable {
		// Arrange
		final String c32XmlMock = "c32XmlMock";
		final String hl7v3XmlMock = "hl7v3XmlMock";
		final String eIdMock = "eIdMock";
		final String updateMsgMock = "updateMsgMock";
		// PRPAIN201309UV02 requestMock = mock(PRPAIN201309UV02.class);
		// PRPAIN201310UV02 responseMock = mock(PRPAIN201310UV02.class);
		final MCCIIN000002UV01 expectedResponseMock = mock(MCCIIN000002UV01.class);
		final Map<String, String> queryMapMock = new HashMap<String, String>();
		queryMapMock.put(PixManagerConstants.GLOBAL_DOMAIN_ID, eIdMock);
		sut.setPixManagerTransformService(pixManagerTransformServiceMock);
		sut.setPixMgrService(pixMgrServiceMock);
		sut.setRequestXMLToJava(requestXMLToJavaMock);
		sut.setPixManagerTransformService(pixManagerTransformServiceMock);
		sut.setXdsbRegistryGetter(xdsbRegistryGetterMock);
		sut.setMarshaller(marshallerMock);
		when(
				pixManagerTransformServiceMock.getPixXml(PixOperation.QUERY,
						c32XmlMock)).thenReturn(hl7v3XmlMock);
		// when(pixManagerTransformServiceMock.getPixQueryXml(c32XmlMock)).thenReturn(hl7v3XmlMock);
		when(
				pixManagerTransformServiceMock.getPixXml(PixOperation.UPDATE,
						c32XmlMock)).thenReturn(hl7v3XmlMock);
		// when(pixManagerTransformServiceMock.getPixUpdateXml(c32XmlMock)).thenReturn(hl7v3XmlMock);
		when(
				xdsbRegistryGetterMock.pidFeed(PixOperation.UPDATE,
						hl7v3XmlMock, eIdMock,
						PixManagerConstants.GLOBAL_DOMAIN_ID)).thenReturn(
				updateMsgMock);
		// when(xdsbRegistryGetterMock.revisePatientRegistryRecord(hl7v3XmlMock,
		// eIdMock,PixManagerConstants.GLOBAL_DOMAIN_ID)).thenReturn(updateMsgMock);
		when(
				marshallerMock.unmarshalFromXml(MCCIIN000002UV01.class,
						updateMsgMock)).thenReturn(expectedResponseMock);
		// when(requestXMLToJavaMock.getPIXQueryReqObject(hl7v3XmlMock,PixManagerConstants.ENCODE_STRING)).thenReturn(requestMock);
		// when(pixMgrServiceMock.pixManagerPRPAIN201309UV02(requestMock)).thenReturn(responseMock);
		when(pixMgrBeanMock.getQueryIdMap()).thenReturn(queryMapMock);
		doReturn(pixMgrBeanMock).when(sut).queryPerson(hl7v3XmlMock);

		// Act
		final MCCIIN000002UV01 actRetResponse = sut
				.revisePatientRegistryRecord(c32XmlMock);

		// Assert
		assertEquals(expectedResponseMock, actRetResponse);
	}

	@Test
	public void testUpdatePatientRegistryRecord_Throws_Hl7v3TransformerException()
			throws Throwable {
		// Arrange
		final String c32XmlMock = "c32XmlMock";
		sut.setPixManagerTransformService(pixManagerTransformServiceMock);
		when(pixManagerTransformServiceMock.getPixQueryXml(c32XmlMock))
				.thenThrow(Hl7v3TransformerException.class);
		thrown.expect(AcsShowCaseException.class);

		// Act
		@SuppressWarnings("unused")
		final MCCIIN000002UV01 actRetResponse = sut
				.revisePatientRegistryRecord(c32XmlMock);
	}

	@Test
	public void testUpdatePerson() throws JAXBException, IOException {
		// Arrange
		final String reqMock = "reqMock";
		final PRPAIN201302UV02 requestMock = mock(PRPAIN201302UV02.class);
		final MCCIIN000002UV01 responseMock = mock(MCCIIN000002UV01.class);
		sut.setPixMgrService(pixMgrServiceMock);
		sut.setRequestXMLToJava(requestXMLToJavaMock);
		// Act
		when(
				requestXMLToJavaMock.getPIXUpdateReqObject(reqMock,
						PixManagerConstants.ENCODE_STRING)).thenReturn(
				requestMock);
		when(pixMgrServiceMock.pixManagerPRPAIN201302UV02(requestMock))
				.thenReturn(responseMock);
		final String actRet = sut.updatePerson(reqMock);
		// Assert
		assertTrue("actual value" + actRet, actRet.trim().length() == 0);
	}

	@Test
	public void testUpdatePerson_exception() throws JAXBException, IOException {
		// Arrange
		final String reqMock = "reqMock";
		final PRPAIN201302UV02 requestMock = mock(PRPAIN201302UV02.class);
		sut.setPixMgrService(pixMgrServiceMock);
		sut.setRequestXMLToJava(requestXMLToJavaMock);
		// Act
		when(
				requestXMLToJavaMock.getPIXUpdateReqObject(reqMock,
						PixManagerConstants.ENCODE_STRING)).thenReturn(
				requestMock);
		when(pixMgrServiceMock.pixManagerPRPAIN201302UV02(requestMock))
				.thenThrow(JAXBException.class);
		final String actualObj = sut.updatePerson(reqMock);
		// Assert
		assertTrue("Actual update message " + actualObj,
				actualObj.startsWith("Update Failure! "));
	}
}
