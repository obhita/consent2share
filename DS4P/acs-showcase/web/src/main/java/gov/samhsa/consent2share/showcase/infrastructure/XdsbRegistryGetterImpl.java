package gov.samhsa.consent2share.showcase.infrastructure;

import gov.samhsa.consent2share.accesscontrolservice.common.tool.SimpleMarshaller;
import gov.samhsa.consent2share.accesscontrolservice.common.tool.SimpleMarshallerImpl;
import gov.samhsa.consent2share.accesscontrolservice.xdsb.registry.XdsbRegistryWebServiceClient;

import org.hl7.v3.PRPAIN201301UV02;
import org.hl7.v3.PRPAIN201302UV02;
import org.hl7.v3.PRPAIN201304UV02;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import gov.samhsa.consent2share.showcase.exception.AcsShowCaseException;


@Component
public class XdsbRegistryGetterImpl implements XdsbRegistryGetter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	/** The endpoint address. */
	private String endpointAddress;

	SimpleMarshaller marshaller = new SimpleMarshallerImpl();

	
	private XdsbRegistryWebServiceClient xdsbRegistryWebServiceClient;
	/**
	 * Instantiates a new XdsbRegistryGetter implementation.
	 *
	 * @param endpointAddress the endpoint address
	 */
	@Autowired
	public XdsbRegistryGetterImpl(@Value("${xdsbRegistryEndpointAddress}") String endpointAddress) {
		this.endpointAddress = endpointAddress;
		xdsbRegistryWebServiceClient = new XdsbRegistryWebServiceClient(this.endpointAddress);
	}
	
	
	@Override
	public String addPatientRegistryRecord(String hl7v3Xml, String eId, String eIdDomain) {
		String addMsg= "";
		try {
			PRPAIN201301UV02 request = marshaller.unmarshallFromXml(
					PRPAIN201301UV02.class, hl7v3Xml);			
			//set eid values
			
			addMsg = xdsbRegistryWebServiceClient.addPatientRegistryRecord(request);
		} catch (Throwable e) {
			// Expect the unexpected
			logger.error("Unexpected exception", e);
			// Add error
			logger.error("error",
					"Query Failure! Server error! A unexpected error has occured");
			throw new AcsShowCaseException(e.toString(), e);
		}

		return addMsg;
	}

	@Override
	public String resolvePatientRegistryDuplicates(String hl7v3Xml, String eId, String eIdDomain) {
		String resolveMsg= "";
		try {
			PRPAIN201304UV02 input = marshaller.unmarshallFromXml(
					PRPAIN201304UV02.class, hl7v3Xml);
			resolveMsg = xdsbRegistryWebServiceClient.resolvePatientRegistryDuplicates(input);
		} catch (Throwable e) {
			// Expect the unexpected
			logger.error("Unexpected exception", e);
			// Add error
			logger.error("error",
					"Query Failure! Server error! A unexpected error has occured");
			throw new AcsShowCaseException(e.toString(), e);
		}

		return resolveMsg;
	}


	@Override
	public String revisePatientRegistryRecord(String hl7v3Xml, String eId, String eIdDomain) {
		String reviseMsg= "";
		try {
			PRPAIN201302UV02 input = marshaller.unmarshallFromXml(
					PRPAIN201302UV02.class, hl7v3Xml);;
			reviseMsg = xdsbRegistryWebServiceClient.revisePatientRegistryRecord(input);
		} catch (Throwable e) {
			// Expect the unexpected
			logger.error("Unexpected exception", e);
			// Add error
			logger.error("error",
					"Query Failure! Server error! A unexpected error has occured");
			throw new AcsShowCaseException(e.toString(), e);
		}

		return reviseMsg;
	}


/*	protected void setEidValues(Object req){
		
		if(req == null) {
			return;
		}
		
		if(req instanceof PRPAIN201301UV02){
			
			
		}
		
		
		
	}*/


	public void setMarshaller(SimpleMarshaller marshaller) {
		this.marshaller = marshaller;
	}

	
	public void setXdsbRegistryWebServiceClient(
			XdsbRegistryWebServiceClient xdsbRegistryWebServiceClient) {
		this.xdsbRegistryWebServiceClient = xdsbRegistryWebServiceClient;
	}


}
