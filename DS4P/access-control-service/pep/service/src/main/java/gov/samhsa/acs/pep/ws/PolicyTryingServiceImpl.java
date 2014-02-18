package gov.samhsa.acs.pep.ws;

import gov.samhsa.acs.pep.PolicyTrying;
import gov.samhsa.acs.pep.ws.contract.TryPolicyPortType;

import java.io.IOException;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

@javax.jws.WebService(
                      serviceName = "TryPolicyService",
                      portName = "TryPolicyServicePort",
                      targetNamespace = "http://acs.samhsa.gov/pep/ws/contract",
                      wsdlLocation = "classpath:TryPolicy.wsdl",
                      endpointInterface = "gov.samhsa.acs.pep.ws.contract.TryPolicyPortType")
                      
public class PolicyTryingServiceImpl implements TryPolicyPortType {

    private static final Logger LOG = Logger.getLogger(PolicyTryingServiceImpl.class.getName());
    
    private PolicyTrying policyTrying;
    
    public PolicyTryingServiceImpl(PolicyTrying tryPolicy ) {
		this.policyTrying = tryPolicy;
	}

    public String tryPolicy(String c32Xml, String xacmlPolicy) { 
        LOG.info("Executing operation tryPolicy");
        
        String segmentedC32 = null;
		try {
			segmentedC32 = policyTrying.tryPolicy(c32Xml, xacmlPolicy);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
        return segmentedC32;
    }
}
