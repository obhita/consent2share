/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.ds4ppilot.ws.client;

import gov.samhsa.ds4p.xdsbregistry.DocumentRegistryService;
import ihe.iti.xds_b._2007.XDSRegistry;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import oasis.names.tc.ebxml_regrep.xsd.query._3.ResponseOptionType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.AdhocQueryType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.SlotType1;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.ValueListType;

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
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;

/**
 * The Class XdsbRegistryWebServiceClient.
 */
public class XdsbRegistryWebServiceClient {

	/** The endpoint address. */
	private final String endpointAddress;

	/** The wsdl url. */
	final URL wsdlURL = this.getClass().getClassLoader()
			.getResource("XDS.b_registry.net.wsdl");

	/** The service name. */
	final QName serviceName = new QName("http://samhsa.gov/ds4p/XDSbRegistry/",
			"DocumentRegistryService");

	/** The port name. */
	final QName portName = new QName("http://samhsa.gov/ds4p/XDSbRegistry/",
			"XDSRegistry_HTTP_Endpoint");

	/**
	 * Instantiates a new xdsb registry web service client.
	 * 
	 * @param endpointAddress
	 *            the endpoint address
	 */
	public XdsbRegistryWebServiceClient(String endpointAddress) {
		this.endpointAddress = endpointAddress;
	}

	/**
	 * Registry stored query.
	 * 
	 * @param registryStoredQuery
	 *            the registry stored query
	 * @return the adhoc query response
	 */
	public AdhocQueryResponse registryStoredQuery(
			AdhocQueryRequest registryStoredQuery) {
		XDSRegistry port = createPort();

		return port.registryStoredQuery(registryStoredQuery);
	}

	/**
	 * Adds the patient registry record.
	 * 
	 * @param input
	 *            the input
	 * @return the string
	 * @throws Throwable
	 *             the throwable
	 */
	public String addPatientRegistryRecord(PRPAIN201301UV02 input)
			throws Throwable {
		String justPayloadXml = marshall(input);

		final QName operationName = new QName("urn:ihe:iti:xds-b:2007",
				"PatientRegistryRecordAdded");

		String responsePayload = getRawResponsePayload(operationName,
				justPayloadXml);

		return responsePayload;
	}

	/**
	 * Revise patient registry record.
	 * 
	 * @param input
	 *            the input
	 * @return the string
	 * @throws Throwable
	 *             the throwable
	 */
	public String revisePatientRegistryRecord(PRPAIN201302UV input)
			throws Throwable {
		String justPayloadXml = marshall(input);

		final QName operationName = new QName("urn:ihe:iti:xds-b:2007",
				"PatientRegistryRecordRevised");

		String responsePayload = getRawResponsePayload(operationName,
				justPayloadXml);

		return responsePayload;
	}

	/**
	 * Creates the port.
	 * 
	 * @return the xDS registry
	 */
	private XDSRegistry createPort() {
		XDSRegistry port = new DocumentRegistryService(wsdlURL, serviceName)
				.getXDSRegistryHTTPEndpoint();

		if (StringUtils.hasText(this.endpointAddress)) {
			BindingProvider bp = (BindingProvider) port;
			bp.getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddress);
		}

		return port;
	}

	/**
	 * Gets the raw response payload.
	 * 
	 * @param operationName
	 *            the operation name
	 * @param justPayloadXml
	 *            the just payload xml
	 * @return the raw response payload
	 * @throws Throwable
	 *             the throwable
	 */
	private String getRawResponsePayload(QName operationName,
			String justPayloadXml) throws Throwable {
		Service jaxwsService = Service.create(wsdlURL, serviceName);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);

		InputStream is = new ByteArrayInputStream(
				justPayloadXml.getBytes("UTF-8"));
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document newDoc = builder.parse(is);

		DOMSource request = new DOMSource(newDoc);

		Dispatch<Source> disp = jaxwsService.createDispatch(portName,
				Source.class, Service.Mode.PAYLOAD);

		BindingProvider bp = disp;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				endpointAddress);

		bp.getRequestContext()
				.put(MessageContext.WSDL_OPERATION, operationName);

		Source responsePayloadSource = disp.invoke(request);

		Transformer trans = TransformerFactory.newInstance().newTransformer();
		StreamResult streamResult = new StreamResult();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		streamResult.setOutputStream(out);
		trans.transform(responsePayloadSource, streamResult);

		String responsePayload = new String(out.toByteArray());

		return responsePayload;
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws Throwable
	 *             the throwable
	 */
	public static void main(String[] args) throws Throwable {
		final String demoEndpoint = "http://xds-demo.feisystems.com:8080/axis2/services/xdsregistryb";
		final String javaVmEndpoint = "http://192.168.223.134:8080/axis2/services/xdsregistryb";
		final String dotnetVmEndpoint = "http://192.168.223.138:8080/xdsservice/xdsregistry";

		XdsbRegistryWebServiceClient xdsService = new XdsbRegistryWebServiceClient(
				demoEndpoint);

		System.out.println("Run registryStoredQuery");

		AdhocQueryRequest registryStoredQuery = new AdhocQueryRequest();

		ResponseOptionType responseOptionType = new ResponseOptionType();
		responseOptionType.setReturnComposedObjects(true);
		responseOptionType.setReturnType("LeafClass");
		registryStoredQuery.setResponseOption(responseOptionType);

		AdhocQueryType adhocQueryType = new AdhocQueryType();
		adhocQueryType.setId("urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d"); // FindDocuments
																				// by
																				// patientId
		registryStoredQuery.setAdhocQuery(adhocQueryType);

		SlotType1 patientIdSlotType = new SlotType1();
		patientIdSlotType.setName("$XDSDocumentEntryPatientId");
		ValueListType patientIdValueListType = new ValueListType();
		patientIdValueListType.getValue().add(
				"'24d3b01495f14e9^^^&1.3.6.1.4.1.21367.2010.1.2.300&ISO'"); // PatientId
		patientIdSlotType.setValueList(patientIdValueListType);
		adhocQueryType.getSlot().add(patientIdSlotType);

		SlotType1 statusSlotType = new SlotType1();
		statusSlotType.setName("$XDSDocumentEntryStatus");
		ValueListType statusValueListType = new ValueListType();
		statusValueListType.getValue().add(
				"('urn:oasis:names:tc:ebxml-regrep:StatusType:Approved')");
		statusSlotType.setValueList(statusValueListType);
		adhocQueryType.getSlot().add(statusSlotType);

		System.out.println(marshall(registryStoredQuery));

		Object result = xdsService.registryStoredQuery(registryStoredQuery);

		System.out.println(marshall(result));

		System.out.println("Run patientRegistryRecordAdded");

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

		System.out.println(xdsService
				.addPatientRegistryRecord(prpain201301uv02));

		System.out.println("Run patientRegistryRecordRevised");

		// PRPAIN201302UV
		PRPAIN201302UV prpain201302uv = new PRPAIN201302UV();

		prpain201301uv02.setControlActProcess(controlActProcess);

		prpain201302uv.setId(PRPAIN201302UVId);

		prpain201302uv.setReceiver(receiver);

		prpain201302uv.setSender(sender);

		addr.setCity("DC");
		System.out.println(xdsService
				.revisePatientRegistryRecord(prpain201302uv));
	}

	/**
	 * Marshall.
	 * 
	 * @param obj
	 *            the obj
	 * @return the string
	 * @throws Throwable
	 *             the throwable
	 */
	private static String marshall(Object obj) throws Throwable {
		final JAXBContext context = JAXBContext.newInstance(obj.getClass());

		// Create the marshaller, this is the nifty little thing that will
		// actually transform the object into XML
		final Marshaller marshaller = context.createMarshaller();

		// Create a stringWriter to hold the XML
		final StringWriter stringWriter = new StringWriter();

		// Marshal the javaObject and write the XML to the stringWriter
		marshaller.marshal(obj, stringWriter);

		return stringWriter.toString();
	}
}
