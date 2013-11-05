package gov.samhsa.consent2share.pixclient.service;

import gov.samhsa.consent2share.pixclient.util.RequestXMLToJava;
import gov.samhsa.consent2share.pixclient.ws.MCCIIN000002UV01;
import gov.samhsa.consent2share.pixclient.ws.PIXManagerPortType;
import gov.samhsa.consent2share.pixclient.ws.PIXManagerService;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201301UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201302UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201304UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201309UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201310UV02;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service(value = "PixMgrService")
public class PixMgrServiceImpl implements PixMgrService {
	private String endpoint = null;
	private PIXManagerPortType port;

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	RequestXMLToJava requestXMLToJava = new RequestXMLToJava();
	
	public PixMgrServiceImpl() {
		this(null);
	}
	
	public PixMgrServiceImpl(String endpoint) {
		this.endpoint = endpoint;
		PIXManagerService service = new PIXManagerService();
		port = service.getPIXManagerPortSoap12();
		if ( this.endpoint != null )
			((BindingProvider)port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
	}
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.pixclient.client.PIXManager#pixManagerPRPAIN201301UV02(gov.samhsa.consent2share.pixclient.ws.PRPAIN201301UV02)
	 */
	@Override
	public MCCIIN000002UV01 pixManagerPRPAIN201301UV02(PRPAIN201301UV02 body) {
		return port.pixManagerPRPAIN201301UV02(body);
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.pixclient.client.PIXManager#pixManagerPRPAIN201302UV02(gov.samhsa.consent2share.pixclient.ws.PRPAIN201302UV02)
	 */
	@Override
	public MCCIIN000002UV01 pixManagerPRPAIN201302UV02(PRPAIN201302UV02 body) {
		return port.pixManagerPRPAIN201302UV02(body);
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.pixclient.client.PIXManager#pixManagerPRPAIN201304UV02(gov.samhsa.consent2share.pixclient.ws.PRPAIN201304UV02)
	 */
	@Override
	public MCCIIN000002UV01 pixManagerPRPAIN201304UV02(PRPAIN201304UV02 body) {
		return port.pixManagerPRPAIN201304UV02(body);
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.pixclient.client.PIXManager#pixManagerPRPAIN201309UV02(gov.samhsa.consent2share.pixclient.ws.PRPAIN201309UV02)
	 */
	@Override
	public PRPAIN201310UV02 pixManagerPRPAIN201309UV02(PRPAIN201309UV02 body) {
		return port.pixManagerPRPAIN201309UV02(body);
	}

}
