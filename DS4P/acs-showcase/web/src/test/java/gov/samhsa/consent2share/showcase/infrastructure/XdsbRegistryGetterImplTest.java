package gov.samhsa.consent2share.showcase.infrastructure;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.xdsb.registry.wsclient.XdsbRegistryWebServiceClient;
import gov.samhsa.consent2share.showcase.exception.AcsShowCaseException;

import javax.xml.bind.JAXBException;

import org.hl7.v3.PRPAIN201301UV02;
import org.hl7.v3.PRPAIN201302UV02;
import org.hl7.v3.PRPAIN201304UV02;
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
public class XdsbRegistryGetterImplTest {

	@Spy
	@InjectMocks
	XdsbRegistryGetterImpl sut;

	@Mock
	XdsbRegistryWebServiceClient xdsbRegistryWebServiceClientMock;

	// private String endpointAddress;

	@Mock
	SimpleMarshaller marshallerMock;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private SimpleMarshallerImpl marshaller = new SimpleMarshallerImpl();

	@Test
	public void testXdsbRegistryGetterImpl() {
		// fail("Not yet implemented");
	}

	@Test
	public void testAddPatientRegistryRecord() throws Throwable {
		// Arrange
		sut.setMarshaller(marshallerMock);
		sut.setXdsbRegistryWebServiceClient(xdsbRegistryWebServiceClientMock);
		PRPAIN201301UV02 requestMock = mock(PRPAIN201301UV02.class);
		String hl7v3XmlMock = "hl7v3XmlMock";

		// Act
		when(
				marshallerMock.unmarshallFromXml(PRPAIN201301UV02.class,
						hl7v3XmlMock)).thenReturn(requestMock);
		doNothing().when(sut).setEidValues(requestMock, "eId", "eIdDomain");
		when(
				xdsbRegistryWebServiceClientMock
						.addPatientRegistryRecord(requestMock)).thenReturn(
				hl7v3XmlMock);

		String actRetResponse = sut.addPatientRegistryRecord(hl7v3XmlMock,
				"eId", "eIdDomain");

		// Asset
		assertEquals(hl7v3XmlMock, actRetResponse);
	}

	@Test
	public void testAddPatientRegistryRecord_Throws_AcsShowCaseException()
			throws Throwable {
		// Arrange
		sut.setMarshaller(marshallerMock);
		sut.setXdsbRegistryWebServiceClient(xdsbRegistryWebServiceClientMock);
		PRPAIN201301UV02 requestMock = mock(PRPAIN201301UV02.class);
		String hl7v3XmlMock = "hl7v3XmlMock";
		thrown.expect(AcsShowCaseException.class);

		// Act
		when(
				marshallerMock.unmarshallFromXml(PRPAIN201301UV02.class,
						hl7v3XmlMock)).thenReturn(requestMock);
		when(
				xdsbRegistryWebServiceClientMock
						.addPatientRegistryRecord(requestMock)).thenThrow(
				AcsShowCaseException.class);
		doNothing().when(sut).setEidValues(requestMock, "", "");

		sut.addPatientRegistryRecord(hl7v3XmlMock, "", "");
	}

	@Test
	public void testAddPatientRegistryRecord_Throws_JAXBException()
			throws Throwable {
		// Arrange
		sut.setMarshaller(marshallerMock);
		sut.setXdsbRegistryWebServiceClient(xdsbRegistryWebServiceClientMock);
		String hl7v3XmlMock = "hl7v3XmlMock";
		thrown.expect(AcsShowCaseException.class);

		// Act
		when(
				marshallerMock.unmarshallFromXml(PRPAIN201301UV02.class,
						hl7v3XmlMock)).thenThrow(JAXBException.class);

		sut.addPatientRegistryRecord(hl7v3XmlMock, "", "");
	}

	@Test
	public void testResolvePatientRegistryDuplicates() throws Throwable {
		// Arrange
		String eIdMock = "eIdMock";
		String eIdDomainMock = "eIdDomainMock";
		sut.setMarshaller(marshallerMock);
		sut.setXdsbRegistryWebServiceClient(xdsbRegistryWebServiceClientMock);
		PRPAIN201304UV02 requestMock = mock(PRPAIN201304UV02.class);
		String hl7v3XmlMock = "hl7v3XmlMock";

		when(
				marshallerMock.unmarshallFromXml(PRPAIN201304UV02.class,
						hl7v3XmlMock)).thenReturn(requestMock);
		when(
				xdsbRegistryWebServiceClientMock
						.resolvePatientRegistryDuplicates(requestMock))
				.thenReturn(hl7v3XmlMock);
		doNothing().when(sut).setEidValues(requestMock, eIdMock, eIdDomainMock);

		// Act
		String actRetResponse = sut.resolvePatientRegistryDuplicates(
				hl7v3XmlMock, eIdMock, eIdDomainMock);

		// Asset
		assertEquals(hl7v3XmlMock, actRetResponse);
	}

	@Test
	public void testResolvePatientRegistryDuplicates_Throws_AcsShowCaseException()
			throws Throwable {
		// Arrange
		sut.setMarshaller(marshallerMock);
		sut.setXdsbRegistryWebServiceClient(xdsbRegistryWebServiceClientMock);
		PRPAIN201304UV02 requestMock = mock(PRPAIN201304UV02.class);
		String hl7v3XmlMock = "hl7v3XmlMock";
		thrown.expect(AcsShowCaseException.class);

		// Act
		when(
				marshallerMock.unmarshallFromXml(PRPAIN201304UV02.class,
						hl7v3XmlMock)).thenReturn(requestMock);
		when(
				xdsbRegistryWebServiceClientMock
						.resolvePatientRegistryDuplicates(requestMock))
				.thenThrow(AcsShowCaseException.class);

		sut.resolvePatientRegistryDuplicates(hl7v3XmlMock, "", "");
	}

	@Test
	public void testResolvePatientRegistryDuplicates_Throws_JAXBException()
			throws Throwable {
		// Arrange
		sut.setMarshaller(marshallerMock);
		sut.setXdsbRegistryWebServiceClient(xdsbRegistryWebServiceClientMock);
		String hl7v3XmlMock = "hl7v3XmlMock";
		thrown.expect(AcsShowCaseException.class);

		// Act
		when(
				marshallerMock.unmarshallFromXml(PRPAIN201304UV02.class,
						hl7v3XmlMock)).thenThrow(JAXBException.class);

		sut.resolvePatientRegistryDuplicates(hl7v3XmlMock, "", "");
	}

	@Test
	public void testRevisePatientRegistryRecord() throws Throwable {
		// Arrange
		sut.setMarshaller(marshallerMock);
		sut.setXdsbRegistryWebServiceClient(xdsbRegistryWebServiceClientMock);
		PRPAIN201302UV02 requestMock = mock(PRPAIN201302UV02.class);
		String hl7v3XmlMock = "hl7v3XmlMock";
		String eIdMock = "eIdMock";
		String eIdDomainMock = "eIdDomainMock";

		// Act
		when(
				marshallerMock.unmarshallFromXml(PRPAIN201302UV02.class,
						hl7v3XmlMock)).thenReturn(requestMock);
		when(
				xdsbRegistryWebServiceClientMock
						.revisePatientRegistryRecord(requestMock)).thenReturn(
				hl7v3XmlMock);
		doNothing().when(sut).setEidValues(requestMock, eIdMock, eIdDomainMock);

		String actRetResponse = sut.revisePatientRegistryRecord(hl7v3XmlMock,
				eIdMock, eIdDomainMock);

		// Asset
		assertEquals(hl7v3XmlMock, actRetResponse);
	}

	@Test
	public void testRevisePatientRegistryRecord_Throws_AcsShowCaseException()
			throws Throwable {
		// Arrange
		sut.setMarshaller(marshallerMock);
		sut.setXdsbRegistryWebServiceClient(xdsbRegistryWebServiceClientMock);
		PRPAIN201302UV02 requestMock = mock(PRPAIN201302UV02.class);
		String hl7v3XmlMock = "hl7v3XmlMock";
		thrown.expect(AcsShowCaseException.class);

		// Act
		when(
				marshallerMock.unmarshallFromXml(PRPAIN201302UV02.class,
						hl7v3XmlMock)).thenReturn(requestMock);
		when(
				xdsbRegistryWebServiceClientMock
						.revisePatientRegistryRecord(requestMock)).thenThrow(
				AcsShowCaseException.class);

		sut.revisePatientRegistryRecord(hl7v3XmlMock, "", "");
	}

	@Test
	public void testRevisePatientRegistryRecord_Throws_JAXBException()
			throws Throwable {
		// Arrange
		String eIdMock = "eIdMock";
		String eIdDomainMock = "eIdDomainMock";
		sut.setMarshaller(marshallerMock);
		sut.setXdsbRegistryWebServiceClient(xdsbRegistryWebServiceClientMock);
		String hl7v3XmlMock = "hl7v3XmlMock";

		when(
				marshallerMock.unmarshallFromXml(PRPAIN201302UV02.class,
						hl7v3XmlMock)).thenThrow(JAXBException.class);
		thrown.expect(AcsShowCaseException.class);

		// Act
		sut.revisePatientRegistryRecord(hl7v3XmlMock, eIdMock, eIdDomainMock);
	}

	@Test
	public void testSetEidValues_PRPAIN201301UV02() throws JAXBException {
		// Arrange
		String xmlPRPAIN201301UV02 = "<PRPA_IN201301UV02 xmlns=\"urn:hl7-org:v3\" ITSVersion=\"XML_1.0\"><id root=\"21acf7be-007c-41e6-b176-d0969794983b\"></id><creationTime value=\"20091112115139\"></creationTime><interactionId extension=\"PRPA_IN201301UV02\" root=\"2.16.840.1.113883.1.6\"></interactionId><processingCode code=\"P\"></processingCode><processingModeCode code=\"T\"></processingModeCode><acceptAckCode code=\"AL\"></acceptAckCode><receiver typeCode=\"RCV\"><device classCode=\"DEV\" determinerCode=\"INSTANCE\"><id root=\"1.2.840.114350.1.13.99999.4567\"></id><asAgent classCode=\"AGNT\"><representedOrganization classCode=\"ORG\" determinerCode=\"INSTANCE\"><id root=\"1.2.840.114350.1.13.99999.1234\"></id></representedOrganization></asAgent></device></receiver><sender typeCode=\"SND\"><device classCode=\"DEV\" determinerCode=\"INSTANCE\"><id root=\"1.2.840.114350.1.13.99998.8734\"></id><asAgent classCode=\"AGNT\"><representedOrganization classCode=\"ORG\" determinerCode=\"INSTANCE\"><id root=\"1.2.840.114350.1.13.99998\"></id></representedOrganization></asAgent></device></sender><controlActProcess classCode=\"CACT\" moodCode=\"EVN\"><code code=\"PRPA_TE201301UV02\" codeSystem=\"2.16.840.1.113883.1.6\"></code><subject typeCode=\"SUBJ\"><registrationEvent classCode=\"REG\" moodCode=\"EVN\"><id nullFlavor=\"NA\"></id><statusCode code=\"active\"></statusCode><subject1 typeCode=\"SBJ\"><patient classCode=\"PAT\"><id assigningAuthorityName=\"NIST2010\" extension=\"1c5c59f0-5788-11e3-84b3-00155d3a2124\" root=\"2.16.840.1.113883.4.357\"></id><statusCode code=\"active\"></statusCode><patientPerson><name><given>Asample</given><family>Patientone</family></name><telecom use=\"H\" value=\"tel:610-220-4354\"></telecom><administrativeGenderCode code=\"M\"></administrativeGenderCode><birthTime value=\"19710510\"></birthTime><addr><streetAddressLine>3351 N chatham rd</streetAddressLine><city>ellicott city</city><state>Maryland</state></addr></patientPerson><providerOrganization><id root=\"1.2.840.114350.1.13.99998.8734\"></id><contactParty></contactParty></providerOrganization></patient></subject1><custodian typeCode=\"CST\"><assignedEntity classCode=\"ASSIGNED\"><id root=\"1.2.840.114350.1.13.99998.8734\"></id></assignedEntity></custodian></registrationEvent></subject></controlActProcess></PRPA_IN201301UV02>";
		PRPAIN201301UV02 requestMock = marshaller.unmarshallFromXml(
				PRPAIN201301UV02.class, xmlPRPAIN201301UV02);
		String eIdMock = "eIdMock";
		String eIdDomainMock = "eIdDomainMock";

		// Act
		sut.setEidValues(requestMock, eIdMock, eIdDomainMock);

		// Assert
		assertEquals(eIdMock, requestMock.getControlActProcess().getSubject()
				.getRegistrationEvent().getSubject1().getPatient().getId()
				.getExtension());
		assertEquals(eIdDomainMock, requestMock.getControlActProcess()
				.getSubject().getRegistrationEvent().getSubject1().getPatient()
				.getId().getRoot());
	}

	@Test
	public void testSetEidValues_PRPAIN201302UV02() throws JAXBException {
		// Arrange
		String xmlPRPAIN201302UV02 = "<PRPA_IN201302UV02 xmlns=\"urn:hl7-org:v3\" ITSVersion=\"XML_1.0\"><id root=\"21acf7be-007c-41e6-b176-d0969794983b\"></id><creationTime value=\"20091112115139\"></creationTime><interactionId extension=\"PRPA_IN201302UV02\" root=\"2.16.840.1.113883.1.6\"></interactionId><processingCode code=\"P\"></processingCode><processingModeCode code=\"T\"></processingModeCode><acceptAckCode code=\"AL\"></acceptAckCode><receiver typeCode=\"RCV\"><device classCode=\"DEV\" determinerCode=\"INSTANCE\"><id root=\"1.2.840.114350.1.13.99999.4567\"></id><asAgent classCode=\"AGNT\"><representedOrganization classCode=\"ORG\" determinerCode=\"INSTANCE\"><id root=\"1.2.840.114350.1.13.99999.1234\"></id></representedOrganization></asAgent></device></receiver><sender typeCode=\"SND\"><device classCode=\"DEV\" determinerCode=\"INSTANCE\"><id root=\"1.2.840.114350.1.13.99998.8734\"></id><asAgent classCode=\"AGNT\"><representedOrganization classCode=\"ORG\" determinerCode=\"INSTANCE\"><id root=\"1.2.840.114350.1.13.99998\"></id></representedOrganization></asAgent></device></sender><controlActProcess classCode=\"CACT\" moodCode=\"EVN\"><code code=\"PRPA_TE201302UV02\" codeSystem=\"2.16.840.1.113883.1.6\"></code><subject typeCode=\"SUBJ\"><registrationEvent classCode=\"REG\" moodCode=\"EVN\"><id nullFlavor=\"NA\"></id><statusCode code=\"active\"></statusCode><subject1 typeCode=\"SBJ\"><patient classCode=\"PAT\"><id assigningAuthorityName=\"NIST2010\" extension=\"1c5c59f0-5788-11e3-84b3-00155d3a2124\" root=\"2.16.840.1.113883.4.357\"></id><statusCode code=\"active\"></statusCode><patientPerson><name><given>Asample</given><family>Patientone</family></name><telecom use=\"H\" value=\"555-255-5454\"></telecom><administrativeGenderCode code=\"M\"></administrativeGenderCode><birthTime value=\"19710510\"></birthTime><addr><streetAddressLine>3351 N chatham rd</streetAddressLine><city>ellicott city</city><state>Maryland</state></addr></patientPerson><providerOrganization><id root=\"1.2.840.114350.1.13.99998.8734\"></id><contactParty></contactParty></providerOrganization></patient></subject1><custodian typeCode=\"CST\"><assignedEntity classCode=\"ASSIGNED\"><id root=\"1.2.840.114350.1.13.99998.8734\"></id></assignedEntity></custodian></registrationEvent></subject></controlActProcess></PRPA_IN201302UV02>";
		PRPAIN201302UV02 requestMock = marshaller.unmarshallFromXml(
				PRPAIN201302UV02.class, xmlPRPAIN201302UV02);
		String eIdMock = "eIdMock";
		String eIdDomainMock = "eIdDomainMock";

		// Act
		sut.setEidValues(requestMock, eIdMock, eIdDomainMock);

		// Assert
		assertEquals(eIdMock, requestMock.getControlActProcess().getSubject()
				.getRegistrationEvent().getSubject1().getPatient().getId()
				.getExtension());
		assertEquals(eIdDomainMock, requestMock.getControlActProcess()
				.getSubject().getRegistrationEvent().getSubject1().getPatient()
				.getId().getRoot());
	}

	@Test
	public void testPidFeed_Add() throws Throwable {
		// Arrange
		String hl7v3XmlMock = "hl7v3XmlMock";
		String eIdMock = "eIdMock";
		String eIdDomainMock = "eIdDomainMock";
		String msgMock = "msgMock";
		PRPAIN201301UV02 mockPRPAIN201301UV02 = mock(PRPAIN201301UV02.class);
		sut.setMarshaller(marshallerMock);
		sut.setXdsbRegistryWebServiceClient(xdsbRegistryWebServiceClientMock);

		when(
				marshallerMock.unmarshallFromXml(PRPAIN201301UV02.class,
						hl7v3XmlMock)).thenReturn(mockPRPAIN201301UV02);
		doNothing().when(sut).setEidValues(mockPRPAIN201301UV02, eIdMock,
				eIdDomainMock);
		when(
				xdsbRegistryWebServiceClientMock
						.addPatientRegistryRecord(mockPRPAIN201301UV02))
				.thenReturn(msgMock);

		// Act
		String actualResponse = sut.pidFeed(PixOperation.ADD, hl7v3XmlMock,
				eIdMock, eIdDomainMock);

		// Assert
		assertEquals(msgMock, actualResponse);
	}

	@Test
	public void testPidFeed_Update() throws Throwable {
		// Arrange
		String hl7v3XmlMock = "hl7v3XmlMock";
		String eIdMock = "eIdMock";
		String eIdDomainMock = "eIdDomainMock";
		String msgMock = "msgMock";
		PRPAIN201302UV02 mockPRPAIN201302UV02 = mock(PRPAIN201302UV02.class);
		sut.setMarshaller(marshallerMock);
		sut.setXdsbRegistryWebServiceClient(xdsbRegistryWebServiceClientMock);

		when(
				marshallerMock.unmarshallFromXml(PRPAIN201302UV02.class,
						hl7v3XmlMock)).thenReturn(mockPRPAIN201302UV02);
		doNothing().when(sut).setEidValues(mockPRPAIN201302UV02, eIdMock,
				eIdDomainMock);
		when(
				xdsbRegistryWebServiceClientMock
						.revisePatientRegistryRecord(mockPRPAIN201302UV02))
				.thenReturn(msgMock);

		// Act
		String actualResponse = sut.pidFeed(PixOperation.UPDATE, hl7v3XmlMock,
				eIdMock, eIdDomainMock);

		// Assert
		assertEquals(msgMock, actualResponse);
	}

	@Test
	public void testPidFeed_Merge() throws Throwable {
		// Arrange
		String hl7v3XmlMock = "hl7v3XmlMock";
		String eIdMock = "eIdMock";
		String eIdDomainMock = "eIdDomainMock";
		String msgMock = "msgMock";
		PRPAIN201304UV02 mockPRPAIN201304UV02 = mock(PRPAIN201304UV02.class);
		sut.setMarshaller(marshallerMock);
		sut.setXdsbRegistryWebServiceClient(xdsbRegistryWebServiceClientMock);

		when(
				marshallerMock.unmarshallFromXml(PRPAIN201304UV02.class,
						hl7v3XmlMock)).thenReturn(mockPRPAIN201304UV02);
		doNothing().when(sut).setEidValues(mockPRPAIN201304UV02, eIdMock,
				eIdDomainMock);
		when(
				xdsbRegistryWebServiceClientMock
						.resolvePatientRegistryDuplicates(mockPRPAIN201304UV02))
				.thenReturn(msgMock);

		// Act
		String actualResponse = sut.pidFeed(PixOperation.MERGE, hl7v3XmlMock,
				eIdMock, eIdDomainMock);

		// Assert
		assertEquals(msgMock, actualResponse);
	}

	@Test
	public void testPidFeed_Throws_AcsShowCaseException() throws Throwable {
		// Arrange
		String hl7v3XmlMock = "hl7v3XmlMock";
		String eIdMock = "eIdMock";
		String eIdDomainMock = "eIdDomainMock";
		String msgMock = "msgMock";
		PRPAIN201304UV02 mockPRPAIN201304UV02 = mock(PRPAIN201304UV02.class);
		sut.setMarshaller(marshallerMock);
		sut.setXdsbRegistryWebServiceClient(xdsbRegistryWebServiceClientMock);
		thrown.expect(AcsShowCaseException.class);

		when(
				marshallerMock.unmarshallFromXml(PRPAIN201304UV02.class,
						hl7v3XmlMock)).thenReturn(mockPRPAIN201304UV02);
		doNothing().when(sut).setEidValues(mockPRPAIN201304UV02, eIdMock,
				eIdDomainMock);
		when(
				xdsbRegistryWebServiceClientMock
						.resolvePatientRegistryDuplicates(mockPRPAIN201304UV02))
				.thenReturn(msgMock);

		// Act
		String actualResponse = sut.pidFeed(PixOperation.QUERY, hl7v3XmlMock,
				eIdMock, eIdDomainMock);

		// Assert
		assertEquals(msgMock, actualResponse);
	}
}
