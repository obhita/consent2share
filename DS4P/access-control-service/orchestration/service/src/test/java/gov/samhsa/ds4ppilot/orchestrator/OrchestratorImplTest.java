package gov.samhsa.ds4ppilot.orchestrator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.samhsa.ds4ppilot.common.exception.DS4PException;
import gov.samhsa.ds4ppilot.orchestrator.c32getter.C32Getter;
import gov.samhsa.ds4ppilot.orchestrator.c32getter.C32GetterImpl;
import gov.samhsa.ds4ppilot.orchestrator.contexthandler.ContextHandler;
import gov.samhsa.ds4ppilot.orchestrator.contexthandler.ContextHandlerImpl;
import gov.samhsa.ds4ppilot.orchestrator.documentprocessor.DocumentProcessor;
import gov.samhsa.ds4ppilot.orchestrator.documentprocessor.DocumentProcessorImpl;
import gov.samhsa.ds4ppilot.orchestrator.xdsbregistry.XdsbRegistry;
import gov.samhsa.ds4ppilot.orchestrator.xdsbregistry.XdsbRegistryImpl;
import gov.samhsa.ds4ppilot.orchestrator.xdsbrepository.XdsbRepository;
import gov.samhsa.ds4ppilot.orchestrator.xdsbrepository.XdsbRepositoryImpl;
import gov.samhsa.ds4ppilot.schema.documentprocessor.ProcessDocumentResponse;
import gov.samhsa.ds4ppilot.schema.orchestrator.FilterC32Response;
import gov.samhsa.ds4ppilot.schema.orchestrator.RegisteryStoredQueryResponse;
import gov.samhsa.ds4ppilot.ws.client.XdsbRegistryWebServiceClient;
import gov.va.ehtac.ds4p.ws.EnforcePolicy;
import gov.va.ehtac.ds4p.ws.EnforcePolicy.Xsparesource;
import gov.va.ehtac.ds4p.ws.EnforcePolicy.Xspasubject;
import gov.va.ehtac.ds4p.ws.EnforcePolicyResponse.Return;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import junit.framework.Assert;

import org.hl7.v3.Device;
import org.hl7.v3.Id;
import org.hl7.v3.PRPAIN201301UV02;
import org.hl7.v3.PRPAIN201302UV;
import org.hl7.v3.PatientIdentityFeedRequestType.ControlActProcess;
import org.hl7.v3.PatientIdentityFeedRequestType.ControlActProcess.Subject;
import org.hl7.v3.PatientIdentityFeedRequestType.ControlActProcess.Subject.RegistrationEvent;
import org.hl7.v3.PatientIdentityFeedRequestType.ControlActProcess.Subject.RegistrationEvent.Subject1;
import org.hl7.v3.PatientIdentityFeedRequestType.ControlActProcess.Subject.RegistrationEvent.Subject1.Patient;
import org.hl7.v3.PatientIdentityFeedRequestType.ControlActProcess.Subject.RegistrationEvent.Subject1.Patient.PatientPerson;
import org.hl7.v3.PatientIdentityFeedRequestType.ControlActProcess.Subject.RegistrationEvent.Subject1.Patient.PatientPerson.Addr;
import org.hl7.v3.PatientIdentityFeedRequestType.ControlActProcess.Subject.RegistrationEvent.Subject1.Patient.PatientPerson.BirthTime;
import org.hl7.v3.PatientIdentityFeedRequestType.ControlActProcess.Subject.RegistrationEvent.Subject1.Patient.PatientPerson.Name;
import org.hl7.v3.PatientIdentityFeedRequestType.Receiver;
import org.hl7.v3.PatientIdentityFeedRequestType.Sender;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class OrchestratorImplTest {

	private static boolean packageXdm;
	private static String patientIdDeny;
	private static String patientIdPermit;
	private static String senderEmailAddress;
	private static String reciepientEmailAddress;

	private final static String PERMIT = "Permit";

	@Before
	public void setUp() {
		patientIdPermit = "PUI100010060001";
		patientIdDeny = "PUI100010060006";
		packageXdm = true;
		senderEmailAddress = "leo.smith@direct.obhita-stage.org";
		reciepientEmailAddress = "Duane_Decouteau@direct.healthvault-stage.com";
		
	}

	@Ignore("This test should be configured to run as an integration test.")
	@Test
	public void testHandleC32Request_Deny() {

		final String contextHandlerEndpointAddress = "http://174.78.146.228:8080/DS4PACSServices/DS4PContextHandler";
		ContextHandler contextHandler = new ContextHandlerImpl(
				contextHandlerEndpointAddress);

		final String c32ServiceEndpointAddress = "http://localhost/Rem.Web/C32Service.svc";
		C32Getter c32Getter = new C32GetterImpl(c32ServiceEndpointAddress);

		final String documentProcessorEndpointAddress = "http://localhost:90/DocumentProcessor/services/processdocumentservice";
		DocumentProcessor documentProcessor = new DocumentProcessorImpl(
				documentProcessorEndpointAddress);

		DataHandlerToBytesConverter dataHandlerToBytesConverter = new DataHandlerToBytesConverterImpl();

		final String xdsbRepositoryEndpointAddress = "http://xds-demo.feisystems.com:8080/axis2/services/xdsrepositoryb";
		XdsbRepositoryImpl xdsbRepository = new XdsbRepositoryImpl(
				xdsbRepositoryEndpointAddress);

		final String xdsbRegistryEndpointAddress = "http://xds-demo.feisystems.com:8080/axis2/services/xdsregistryb";
		XdsbRegistryImpl xdsbRegistry = new XdsbRegistryImpl(
				xdsbRegistryEndpointAddress);

		OrchestratorImpl orchestrator = new OrchestratorImpl(contextHandler,
				c32Getter, documentProcessor, dataHandlerToBytesConverter,
				xdsbRepository, xdsbRegistry);

		orchestrator.setSubjectPurposeOfUse("TREAT");
		orchestrator.setSubjectLocality("2.16.840.1.113883.3.467");
		orchestrator.setOrganization("SAMHSA");
		orchestrator.setOrganizationId("FEiSystems");

		orchestrator.setResourceName("NwHINDirectSend");
		orchestrator.setResourceType("C32");
		orchestrator.setResourceAction("Execute");

		FilterC32Response c32Response = orchestrator.handleC32Request(
				patientIdDeny, packageXdm, senderEmailAddress,
				reciepientEmailAddress);

		assertEquals("Deny", c32Response.getPdpDecision());
	}

	@Ignore("This test should be configured to run as an integration test.")
	@Test
	public void testHandleC32Request_Permit() throws Exception {

		final String contextHandlerEndpointAddress = "http://174.78.146.228:8080/DS4PACSServices/DS4PContextHandler";
		ContextHandlerImpl contextHandler = new ContextHandlerImpl(
				contextHandlerEndpointAddress);

		final String endpointAddress = "http://localhost/Rem.Web/C32Service.svc";
		C32GetterImpl c32Getter = new C32GetterImpl(endpointAddress);

		final String documentProcessorEndpointAddress = "http://localhost:90/DocumentProcessor/services/processdocumentservice";
		DocumentProcessorImpl documentProcessor = new DocumentProcessorImpl(
				documentProcessorEndpointAddress);

		DataHandlerToBytesConverter dataHandlerToBytesConverter = new DataHandlerToBytesConverterImpl();

		final String xdsbRepositoryEndpointAddress = "http://xds-demo.feisystems.com:8080/axis2/services/xdsrepositoryb";
		XdsbRepositoryImpl xdsbRepository = new XdsbRepositoryImpl(
				xdsbRepositoryEndpointAddress);

		final String xdsbRegistryEndpointAddress = "http://xds-demo.feisystems.com:8080/axis2/services/xdsregistryb";
		XdsbRegistryImpl xdsbRegistry = new XdsbRegistryImpl(
				xdsbRegistryEndpointAddress);

		OrchestratorImpl orchestrator = new OrchestratorImpl(contextHandler,
				c32Getter, documentProcessor, dataHandlerToBytesConverter,
				xdsbRepository, xdsbRegistry);

		orchestrator.setSubjectPurposeOfUse("TREAT");
		orchestrator.setSubjectLocality("2.16.840.1.113883.3.467");
		orchestrator.setOrganization("SAMHSA");
		orchestrator.setOrganizationId("FEiSystems");

		orchestrator.setResourceName("NwHINDirectSend");
		orchestrator.setResourceType("C32");
		orchestrator.setResourceAction("Execute");

		FilterC32Response c32Response = orchestrator.handleC32Request(
				patientIdPermit, packageXdm, senderEmailAddress,
				reciepientEmailAddress);
		writePackageToFile(c32Response);

		assertEquals(PERMIT, c32Response.getPdpDecision());
	}

	@Ignore("This test should be configured to run as an integration test.")
	@Test
	public void testRetrieveDocumentSetRequest() {
		final String xdsbRepositoryEndpointAddress = "http://xds-demo.feisystems.com:8080/axis2/services/xdsrepositoryb";

		final String contextHandlerEndpointAddress = "http://174.78.146.228:8080/DS4PACSServices/DS4PContextHandler";
		ContextHandlerImpl contextHandler = new ContextHandlerImpl(
				contextHandlerEndpointAddress);

		final String endpointAddress = "http://localhost/Rem.Web/C32Service.svc";
		C32GetterImpl c32Getter = new C32GetterImpl(endpointAddress);

		final String documentProcessorEndpointAddress = "http://localhost:90/DocumentProcessor/services/processdocumentservice";
		DocumentProcessorImpl documentProcessor = new DocumentProcessorImpl(
				documentProcessorEndpointAddress);

		DataHandlerToBytesConverter dataHandlerToBytesConverter = new DataHandlerToBytesConverterImpl();

		XdsbRepositoryImpl xdsbRepository = new XdsbRepositoryImpl(
				xdsbRepositoryEndpointAddress);

		final String xdsbRegistryEndpointAddress = "http://xds-demo.feisystems.com:8080/axis2/services/xdsregistryb";
		XdsbRegistryImpl xdsbRegistry = new XdsbRegistryImpl(
				xdsbRegistryEndpointAddress);

		OrchestratorImpl orchestrator = new OrchestratorImpl(contextHandler,
				c32Getter, documentProcessor, dataHandlerToBytesConverter,
				xdsbRepository, xdsbRegistry);

		orchestrator.setSubjectPurposeOfUse("TREAT");
		orchestrator.setSubjectLocality("2.16.840.1.113883.3.467");
		orchestrator.setOrganization("SAMHSA");
		orchestrator.setOrganizationId("FEiSystems");

		orchestrator.setResourceName("NwHINDirectSend");
		orchestrator.setResourceType("C32");
		orchestrator.setResourceAction("Execute");

		Xspasubject xspasubject = orchestrator.setXspaSubject(
				"Duane_Decouteau@direct.healthvault-stage.com", UUID
				.randomUUID().toString());
		Xsparesource xsparesource = orchestrator
				.setXspaResource("PUI100010060001");
		EnforcePolicy enforcePolicy = new EnforcePolicy();
		enforcePolicy.setXsparesource(xsparesource);
		enforcePolicy.setXspasubject(xspasubject);

		gov.samhsa.ds4ppilot.schema.orchestrator.RetrieveDocumentSetResponse response = orchestrator
				.retrieveDocumentSetRequest("HC",
						"1.3.6.1.4.1.21367.2010.1.2.1040",
						"16807046.11206.4380.81335.421575012145604"/*"88101412251.133129.4131014.8141111.159001521200914"*/, UUID
						.randomUUID().toString(), enforcePolicy);

		assertNotNull(response);
	}

	@Ignore("This test should be configured to run as an integration test.")
	@Test
	public void testRegisteryStoredQueryRequest() {
		final String xdsbRepositoryEndpointAddress = "http://xds-demo.feisystems.com:8080/axis2/services/xdsrepositoryb";

		final String contextHandlerEndpointAddress = "http://174.78.146.228:8080/DS4PACSServices/DS4PContextHandler";
		ContextHandlerImpl contextHandler = new ContextHandlerImpl(
				contextHandlerEndpointAddress);

		final String endpointAddress = "http://localhost/Rem.Web/C32Service.svc";
		C32GetterImpl c32Getter = new C32GetterImpl(endpointAddress);

		final String documentProcessorEndpointAddress = "http://localhost:90/DocumentProcessor/services/processdocumentservice";
		DocumentProcessorImpl documentProcessor = new DocumentProcessorImpl(
				documentProcessorEndpointAddress);

		DataHandlerToBytesConverter dataHandlerToBytesConverter = new DataHandlerToBytesConverterImpl();

		XdsbRepositoryImpl xdsbRepository = new XdsbRepositoryImpl(
				xdsbRepositoryEndpointAddress);

		final String xdsbRegistryEndpointAddress = "http://xds-demo.feisystems.com:8080/axis2/services/xdsregistryb";
		XdsbRegistryImpl xdsbRegistry = new XdsbRegistryImpl(
				xdsbRegistryEndpointAddress);

		OrchestratorImpl orchestrator = new OrchestratorImpl(contextHandler,
				c32Getter, documentProcessor, dataHandlerToBytesConverter,
				xdsbRepository, xdsbRegistry);

		orchestrator.setSubjectPurposeOfUse("TREAT");
		orchestrator.setSubjectLocality("2.16.840.1.113883.3.467");
		orchestrator.setOrganization("SAMHSA");
		orchestrator.setOrganizationId("FEiSystems");

		orchestrator.setResourceName("NwHINDirectSend");
		orchestrator.setResourceType("C32");
		orchestrator.setResourceAction("Execute");

		Xspasubject xspasubject = orchestrator.setXspaSubject(
				"Duane_Decouteau@direct.healthvault-stage.com", UUID
				.randomUUID().toString());
		Xsparesource xsparesource = orchestrator
				.setXspaResource("PUI100010060001");
		EnforcePolicy enforcePolicy = new EnforcePolicy();
		enforcePolicy.setXsparesource(xsparesource);
		enforcePolicy.setXspasubject(xspasubject);

		RegisteryStoredQueryResponse response = orchestrator
				.registeryStoredQueryRequest(
						"PUI100010060001^^^&2.16.840.1.113883.3.467&ISO",
						enforcePolicy);

		assertNotNull(response);

	}

	@Ignore("This test should be configured to run as an integration test.")
	@Test
	public void testAddAndRevisePatientRegistryRecord() throws Throwable {
		final String demoEndpoint = "http://xds-demo.feisystems.com:8080/axis2/services/xdsregistryb";

		XdsbRegistryWebServiceClient xdsService = new XdsbRegistryWebServiceClient(
				demoEndpoint);
		// PatientPerson
		PatientPerson patientPerson = new PatientPerson();
		Name name = new Name();
		name.setFamily("Family");
		name.setGiven("Given");
		patientPerson.setName(name);

		BirthTime birthTime = new BirthTime();
		birthTime.setValue("19570323");
		patientPerson.setBirthTime(birthTime);

		Addr addr = new Addr();
		addr.setStreetAddressLine("3443 South Beach Avenue");
		addr.setCity("Columbia");
		addr.setState("MD");
		patientPerson.getAddr().add(addr);

		// Patient
		Patient patient = new Patient();
		Id patientId = new Id();
		patientId.setRoot("1.2.840.114350.1.13.99998.8734"); // Domain Id
		patientId.setExtension("10"); // PatientId in the domain
		patient.setId(patientId);
		patient.setPatientPerson(patientPerson);

		// Subject 1
		Subject1 subject1 = new Subject1();
		subject1.setPatient(patient);

		// RegistrationEvent
		RegistrationEvent registrationEvent = new RegistrationEvent();
		registrationEvent.setSubject1(subject1);

		// Subject
		Subject subject = new Subject();
		subject.setRegistrationEvent(registrationEvent);

		// ControlActProcess
		ControlActProcess controlActProcess = new ControlActProcess();
		controlActProcess.setSubject(subject);

		// PRPAIN201301UV02
		PRPAIN201301UV02 prpain201301uv02 = new PRPAIN201301UV02();
		prpain201301uv02.setControlActProcess(controlActProcess);

		Id PRPAIN201302UVId = new Id();
		PRPAIN201302UVId.setRoot("cdc0d3fa-4467-11dc-a6be-3603d686610257");
		prpain201301uv02.setId(PRPAIN201302UVId);

		Receiver receiver = new Receiver();
		receiver.setTypeCode("RCV");
		Device receiverDevice = new Device();
		receiverDevice.setDeterminerCode("INSTANCE");
		Id receiverDeviceId = new Id();
		receiverDeviceId.setRoot("1.2.840.114350.1.13.99999.4567");
		receiverDevice.setId(receiverDeviceId);
		receiver.setDevice(receiverDevice);
		prpain201301uv02.setReceiver(receiver);

		Sender sender = new Sender();
		sender.setTypeCode("SND");
		Device senderDevice = new Device();
		senderDevice.setDeterminerCode("INSTANCE");
		Id senderDeviceId = new Id();
		senderDeviceId.setRoot("1.2.840.114350.1.13.99998.8734");
		senderDevice.setId(senderDeviceId);
		sender.setDevice(senderDevice);
		prpain201301uv02.setSender(sender);

		// PRPAIN201302UV
		PRPAIN201302UV prpain201302uv = new PRPAIN201302UV();

		prpain201301uv02.setControlActProcess(controlActProcess);

		prpain201302uv.setId(PRPAIN201302UVId);

		prpain201302uv.setReceiver(receiver);

		prpain201302uv.setSender(sender);

		String revisePatientResponse = null;
		String addPatientResponse = null;
		try {
			addPatientResponse = xdsService
					.addPatientRegistryRecord(prpain201301uv02);
			System.out.println("Run patientRegistryRecordRevised");

			addr.setCity("DC");

			revisePatientResponse = xdsService
					.revisePatientRegistryRecord(prpain201302uv);
		} catch (Exception e) {
			throw new DS4PException(e.toString(), e);
		}

		Assert.assertNotNull(addPatientResponse);
		Assert.assertNotNull(revisePatientResponse);
	}

	@Test(expected = DS4PException.class)
	public void testHandleC32Request_ThrowsExceptionWhenContextHandlerEnforcePolicyThrowsException() {
		// Arrange
		ContextHandler contextHandlerMock = mock(ContextHandler.class);
		C32Getter c32GetterMock = mock(C32Getter.class);
		DocumentProcessor documentProcessorMock = mock(DocumentProcessor.class);
		DataHandlerToBytesConverter dataHandlerToBytesConverterMock = mock(DataHandlerToBytesConverter.class);
		XdsbRepository xdsbRepositoryMock = mock(XdsbRepository.class);
		XdsbRegistry xdsbRegistryMock = mock(XdsbRegistry.class);
		OrchestratorImpl sut = new OrchestratorImpl(contextHandlerMock,
				c32GetterMock, documentProcessorMock,
				dataHandlerToBytesConverterMock, xdsbRepositoryMock,
				xdsbRegistryMock);

		when(
				contextHandlerMock.enforcePolicy(isA(Xspasubject.class),
						isA(Xsparesource.class))).thenThrow(
								new RuntimeException());

		// Act
		sut.handleC32Request(null, false, null, null);

		// Assert
		verify(contextHandlerMock).enforcePolicy(isA(Xspasubject.class),
				isA(Xsparesource.class));
	}

	@Test
	public void testHandleC32Request_WorksWhenHavingNotPermitDecision() {
		// Arrange
		ContextHandler contextHandlerMock = mock(ContextHandler.class);
		C32Getter c32GetterMock = mock(C32Getter.class);
		DocumentProcessor documentProcessorMock = mock(DocumentProcessor.class);
		DataHandlerToBytesConverter dataHandlerToBytesConverterMock = mock(DataHandlerToBytesConverter.class);
		XdsbRepository xdsbRepositoryMock = mock(XdsbRepository.class);
		XdsbRegistry xdsbRegistryMock = mock(XdsbRegistry.class);
		OrchestratorImpl sut = new OrchestratorImpl(contextHandlerMock,
				c32GetterMock, documentProcessorMock,
				dataHandlerToBytesConverterMock, xdsbRepositoryMock,
				xdsbRegistryMock);

		Return returnMock = mock(Return.class);
		when(returnMock.getPdpDecision()).thenReturn("Deny");

		when(
				contextHandlerMock.enforcePolicy(isA(Xspasubject.class),
						isA(Xsparesource.class))).thenReturn(returnMock);

		final String patientId = "patientId";

		// Act
		FilterC32Response c32Response = sut.handleC32Request(patientId, true,
				"senderEmailAddress", "recipientEmailAddress");

		// Assert
		assertTrue(!(PERMIT.equals(c32Response.getPdpDecision())));
		assertNull(c32Response.getFilteredStreamBody());
		assertNull(c32Response.getMaskedDocument());
		assertEquals(patientId, c32Response.getPatientId());
	}

	@Test
	public void testHandleC32Request_WorksWhenHavingPermitDecision()
			throws IOException {
		// Arrange
		ContextHandler contextHandlerMock = mock(ContextHandler.class);
		C32Getter c32GetterMock = mock(C32Getter.class);
		DocumentProcessor documentProcessorMock = mock(DocumentProcessor.class);
		DataHandlerToBytesConverter dataHandlerToBytesConverterMock = mock(DataHandlerToBytesConverter.class);
		XdsbRepository xdsbRepositoryMock = mock(XdsbRepository.class);
		XdsbRegistry xdsbRegistryMock = mock(XdsbRegistry.class);
		OrchestratorImpl sut = new OrchestratorImpl(contextHandlerMock,
				c32GetterMock, documentProcessorMock,
				dataHandlerToBytesConverterMock, xdsbRepositoryMock,
				xdsbRegistryMock);

		Return returnMock = mock(Return.class);
		when(returnMock.getPdpDecision()).thenReturn(PERMIT);

		when(
				contextHandlerMock.enforcePolicy(isA(Xspasubject.class),
						isA(Xsparesource.class))).thenReturn(returnMock);

		final String patientId = "patientId";
		final String c32 = "c32";
		final String recipientEmailAddress = "recipientEmailAddress";
		final String senderEmailAddress = "senderEmailAddress";
		final boolean packageAsXdm = true;
		final boolean encryptDocument = true;
		final String maskedDocument = "maskedDocument";
		final byte[] filteredStreamBody = new byte[1];

		when(c32GetterMock.getC32(patientId)).thenReturn(c32);

		ProcessDocumentResponse processDocumentResponseMock = mock(ProcessDocumentResponse.class);
		when(processDocumentResponseMock.getMaskedDocument()).thenReturn(
				maskedDocument);

		DataHandler dataHandlerMock = mock(DataHandler.class);
		when(processDocumentResponseMock.getProcessedDocument()).thenReturn(
				dataHandlerMock);

		when(
				documentProcessorMock.processDocument(eq(c32), anyString(),
						eq(packageAsXdm), eq(encryptDocument),
						eq(senderEmailAddress), eq(recipientEmailAddress), anyString()))
						.thenReturn(processDocumentResponseMock);

		when(
				dataHandlerToBytesConverterMock
				.toByteArray(isA(DataHandler.class))).thenReturn(
						filteredStreamBody);

		// Act
		FilterC32Response c32Response = sut.handleC32Request(patientId,
				packageAsXdm, senderEmailAddress, recipientEmailAddress);

		// Assert
		assertEquals(PERMIT, c32Response.getPdpDecision());
		assertEquals(filteredStreamBody, c32Response.getFilteredStreamBody());
		assertEquals(maskedDocument, c32Response.getMaskedDocument());
		assertEquals(patientId, c32Response.getPatientId());
	}

	@Ignore("")
	@Test
	public void testSaveDocumentSetToXdsRepository() {
		// Arrange
		String c32Xml = getXmlFromXmlFile("cdaR2Consent.xml");// remC32.xml

		ContextHandler contextHandlerMock = mock(ContextHandler.class);
		C32Getter c32GetterMock = mock(C32Getter.class);
		DocumentProcessor documentProcessorMock = mock(DocumentProcessor.class);
		DataHandlerToBytesConverter dataHandlerToBytesConverterMock = mock(DataHandlerToBytesConverter.class);

		final String demoRespositoryEndpoint = "http://xds-demo.feisystems.com:8080/axis2/services/xdsrepositoryb";
		final String demoRegistryEndpoint = "http://xds-demo.feisystems.com:8080/axis2/services/xdsregistryb";

		XdsbRepository xdsbRepository = new XdsbRepositoryImpl(
				demoRespositoryEndpoint);
		XdsbRegistry xdsbRegistry = new XdsbRegistryImpl(demoRegistryEndpoint);

		OrchestratorImpl sut = new OrchestratorImpl(contextHandlerMock,
				c32GetterMock, documentProcessorMock,
				dataHandlerToBytesConverterMock, xdsbRepository, xdsbRegistry);

		sut.setSubjectLocality("1.1.1.1");

		// Act
		boolean result = sut.saveDocumentSetToXdsRepository(c32Xml);

		System.out.println(result);
	}

	@Test
	public void testPatientExistsInRegistyBeforeAdding() {
		// Arrange
		String responseWithCeOfAddPatientToRegistry = getXmlFromXmlFile("responseWithCeOfAddPatientToRegistry.xml");

		// Act
		boolean result = OrchestratorImpl
				.patientExistsInRegistyBeforeAdding(responseWithCeOfAddPatientToRegistry);

		// Assert
		assertEquals(result, true);
	}

	@SuppressWarnings("unused")
	private static void displayC32(String xml) {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = tf.newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(
				"{http://xml.apache.org/xslt}indent-amount", "4");

		Document xmlDocument = null;
		try {
			xmlDocument = loadXmlFrom(xml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			transformer.transform(new DOMSource(xmlDocument), new StreamResult(
					new OutputStreamWriter(System.out, "UTF-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("\n\n\r");
	}

	private static Document loadXmlFrom(String xml) throws Exception {
		InputSource is = new InputSource(new StringReader(xml));
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = null;
		builder = factory.newDocumentBuilder();
		Document doc = builder.parse(is);
		return doc;
	}

	/**
	 * @param c32Response
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private void writePackageToFile(FilterC32Response c32Response)
			throws IOException, FileNotFoundException {

		byte[] bytes = c32Response.getFilteredStreamBody();

		FileOutputStream fos = new FileOutputStream("xdm.zip");
		try {
			fos.write(bytes);
		} finally {
			fos.close();
		}
	}

	private String getXmlFromXmlFile(String xmlFileNameInResources) {
		InputStream in = null;
		BufferedReader br = null;
		StringBuilder c32Document = new StringBuilder();

		try {
			in = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(xmlFileNameInResources);

			br = new BufferedReader(new InputStreamReader(in));

			String line;
			while ((line = br.readLine()) != null) {
				c32Document.append(line);
			}
		} catch (IOException e) {
			throw new DS4PException(e.toString(), e);
		} finally {
			try {
				br.close();
				in.close();
			} catch (IOException e) {
				// do nothing here
			}
		}

		return c32Document.toString();
	}
}
