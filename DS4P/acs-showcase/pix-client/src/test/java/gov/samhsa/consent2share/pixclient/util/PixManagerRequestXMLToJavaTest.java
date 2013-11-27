package gov.samhsa.consent2share.pixclient.util;

import static org.junit.Assert.assertEquals;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201301UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201302UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201309UV02;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.jxpath.JXPathContext;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PixManagerRequestXMLToJavaTest {

	@InjectMocks
	PixManagerRequestXMLToJava cstl;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private String encodeString = "UTF-8";



	@Test
	public void testPixManagerRequestXMLToJava() throws JAXBException {

		// Act
		PixManagerRequestXMLToJava actualCstl = new PixManagerRequestXMLToJava();

		// Assert
		assertEquals(cstl.getClass(), actualCstl.getClass());
	}

	@Test
	public void testGetPIXAddReqObject_by_ValidDomainXMLFile()
			throws JAXBException {
		// Arrange
		// PRPAIN201301UV02 pRPAIN201301UV02Mock = mock(PRPAIN201301UV02.class);
		String expectedItVersion = "XML_1.0";
		String expectedFirstName = "WILMA";

		// Act
		PRPAIN201301UV02 pRPAIN201301UV02 = cstl.getPIXAddReqObject(
				"xml/PRPA_IN201301UV02_PIXADD_VD1_Req.xml", encodeString);
		String actualItversion = pRPAIN201301UV02.getITSVersion();
		JXPathContext context = JXPathContext.newContext(pRPAIN201301UV02);
		String actualFirstName = (String) context
				.getValue("controlActProcess/subject[1]/registrationEvent/subject1/patient/patientPerson/value/name[1]/content[2]/value/content");

		// Assert
		assertEquals(expectedItVersion, actualItversion);
		assertEquals(expectedFirstName, actualFirstName);

	}

	@Test
	public void testGetPIXAddReqObject_by_ValidDomainXMLString()
			throws JAXBException, IOException, URISyntaxException {
		// Arrange
		// PRPAIN201301UV02 pRPAIN201301UV02Mock = mock(PRPAIN201301UV02.class);
		String expectedItVersion = "XML_1.0";
		String xmlString = FileUtils.readFileToString(new File(getClass()
				.getClassLoader()
				.getResource("xml/PRPA_IN201301UV02_PIXADD_VD1_Req.xml")
				.toURI()));
		// Act
		PRPAIN201301UV02 pRPAIN201301UV02 = cstl.getPIXAddReqObject(xmlString,
				encodeString);
		String actualItversion = pRPAIN201301UV02.getITSVersion();

		// Assert
		assertEquals(expectedItVersion, actualItversion);

	}

	@Test
	public void testGetPIXAddReqObject_by_null_Exception() throws JAXBException {
		// Arrange
		// PRPAIN201301UV02 pRPAIN201301UV02Mock = mock(PRPAIN201301UV02.class);
		thrown.expect(JAXBException.class);

		// Act
		cstl.getPIXAddReqObject(null, encodeString);

	}

	@Test
	public void testGetPIXAddReqObject_by_usup_Exception()
			throws JAXBException, IOException, URISyntaxException {
		// Arrange
		thrown.expect(JAXBException.class);
		String xmlString = FileUtils.readFileToString(new File(getClass()
				.getClassLoader()
				.getResource("xml/PRPA_IN201301UV02_PIXADD_VD1_Req.xml")
				.toURI()));

		// Act
		cstl.getPIXAddReqObject(xmlString, "UTF-123");

	}

	@Test
	public void testGetPIXUpdateReqObject_by_ValidDomainXMLFile()
			throws JAXBException {
		// Arrange
		// PRPAIN201301UV02 pRPAIN201301UV02Mock = mock(PRPAIN201301UV02.class);
		String expectedItVersion = "XML_1.0";
		String expectedFirstName = "WILMA";

		// Act
		PRPAIN201302UV02 pRPAIN201302UV02 = cstl.getPIXUpdateReqObject(
				"xml/PRPA_IN201302UV02_PIXUpdate_Addr_Req.xml", encodeString);
		String actualItversion = pRPAIN201302UV02.getITSVersion();
		JXPathContext context = JXPathContext.newContext(pRPAIN201302UV02);
		String actualFirstName = (String) context
				.getValue("controlActProcess/subject[1]/registrationEvent/subject1/patient/patientPerson/value/name[1]/content[2]/value/content");

		// Assert
		assertEquals(expectedItVersion, actualItversion);
		assertEquals(expectedFirstName, actualFirstName);

	}

	@Test
	public void testGetPIXUpdateReqObject_by_ValidDomainXMLString()
			throws JAXBException, IOException, URISyntaxException {
		// Arrange
		// PRPAIN201301UV02 pRPAIN201301UV02Mock = mock(PRPAIN201301UV02.class);
		String expectedItVersion = "XML_1.0";
		String xmlString = FileUtils.readFileToString(new File(getClass()
				.getClassLoader()
				.getResource("xml/PRPA_IN201302UV02_PIXUpdate_Addr_Req.xml")
				.toURI()));

		// Act
		PRPAIN201302UV02 pRPAIN201302UV02 = cstl.getPIXUpdateReqObject(
				xmlString, encodeString);
		String actualItversion = pRPAIN201302UV02.getITSVersion();

		// Assert
		assertEquals(expectedItVersion, actualItversion);

	}

	@Test
	public void testGetPIXUpdateReqObject_by_null_Exception()
			throws JAXBException {
		// Arrange
		thrown.expect(JAXBException.class);

		// Act
		cstl.getPIXUpdateReqObject(null, encodeString);

	}

	@Test
	public void testGetPIXUpdateReqObject_by_usup_Exception()
			throws JAXBException, IOException, URISyntaxException {
		// Arrange
		thrown.expect(JAXBException.class);
		String xmlString = FileUtils.readFileToString(new File(getClass()
				.getClassLoader()
				.getResource("xml/PRPA_IN201302UV02_PIXUpdate_Addr_Req.xml")
				.toURI()));

		// Act
		cstl.getPIXUpdateReqObject(xmlString, "UTF-123");

	}

	@Test
	public void testGetPIXQueryReqObject_by_ValidDomainXMLFile()
			throws JAXBException {
		// Arrange
		// PRPAIN201301UV02 pRPAIN201301UV02Mock = mock(PRPAIN201301UV02.class);
		String expectedItVersion = "XML_1.0";
		String expectedId = "JW-824-v3";

		// Act
		PRPAIN201309UV02 pRPAIN201309UV02 = cstl
				.getPIXQueryReqObject(
						"xml/PRPA_IN201309UV02_PIXQuery_VD1INALL_Req.xml",
						encodeString);
		String actualItversion = pRPAIN201309UV02.getITSVersion();
		JXPathContext context = JXPathContext.newContext(pRPAIN201309UV02);
		String actualId = (String) context
				.getValue("controlActProcess/queryByParameter/value/parameterList/patientIdentifier[1]/value[1]/extension");

		// Assert
		assertEquals(expectedItVersion, actualItversion);
		assertEquals(expectedId, actualId);

	}

	@Test
	public void testGetPIXQueryReqObject_by_ValidDomainXMLString()
			throws JAXBException, IOException, URISyntaxException {
		// Arrange
		// PRPAIN201301UV02 pRPAIN201301UV02Mock = mock(PRPAIN201301UV02.class);
		String expectedItVersion = "XML_1.0";
		String xmlString = FileUtils.readFileToString(new File(getClass()
				.getClassLoader()
				.getResource("xml/PRPA_IN201309UV02_PIXQuery_VD1INALL_Req.xml")
				.toURI()));

		// Act
		PRPAIN201309UV02 pRPAIN201309UV02 = cstl.getPIXQueryReqObject(
				xmlString, encodeString);
		String actualItversion = pRPAIN201309UV02.getITSVersion();

		// Assert
		assertEquals(expectedItVersion, actualItversion);

	}

	@Test
	public void testGetPIXQueryReqObject_by_null_Exception()
			throws JAXBException {
		// Arrange
		// PRPAIN201301UV02 pRPAIN201301UV02Mock = mock(PRPAIN201301UV02.class);
		thrown.expect(JAXBException.class);

		// Act
		cstl.getPIXQueryReqObject(null, encodeString);

	}

	@Test
	public void testGetPIXQueryReqObject_by_usup_Exception()
			throws JAXBException, IOException, URISyntaxException {
		// Arrange
		thrown.expect(JAXBException.class);
		String xmlString = FileUtils.readFileToString(new File(getClass()
				.getClassLoader()
				.getResource("xml/PRPA_IN201309UV02_PIXQuery_VD1INALL_Req.xml")
				.toURI()));

		// Act
		cstl.getPIXQueryReqObject(xmlString, "UTF-123");

	}

}
