package gov.samhsa.acs.pep.ws;

import gov.samhsa.acs.pep.PolicyTrying;
import gov.samhsa.acs.pep.ws.contract.TryPolicyPortType;

import java.io.IOException;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

/**
 * The Class PolicyTryingServiceImpl.
 */
@javax.jws.WebService(
                      serviceName = "TryPolicyService",
                      portName = "TryPolicyServicePort",
                      targetNamespace = "http://acs.samhsa.gov/pep/ws/contract",
                      wsdlLocation = "classpath:TryPolicy.wsdl",
                      endpointInterface = "gov.samhsa.acs.pep.ws.contract.TryPolicyPortType")
                      
public class PolicyTryingServiceImpl implements TryPolicyPortType {

    /** The Constant LOG. */
    private static final Logger LOG = Logger.getLogger(PolicyTryingServiceImpl.class.getName());
    
    /** The policy trying. */
    private PolicyTrying policyTrying;
    
    /**
     * Instantiates a new policy trying service impl.
     *
     * @param tryPolicy the try policy
     */
    public PolicyTryingServiceImpl(PolicyTrying tryPolicy ) {
		this.policyTrying = tryPolicy;
	}

    /* (non-Javadoc)
     * @see gov.samhsa.acs.pep.ws.contract.TryPolicyPortType#tryPolicy(java.lang.String, java.lang.String, java.lang.String)
     */
    public String tryPolicy(String c32Xml, String xacmlPolicy, String purposeOfUse) { 
        LOG.info("Executing operation tryPolicy");
        
        String segmentedC32 = null;
		try {
			segmentedC32 = policyTrying.tryPolicy(c32Xml, xacmlPolicy, purposeOfUse);
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
