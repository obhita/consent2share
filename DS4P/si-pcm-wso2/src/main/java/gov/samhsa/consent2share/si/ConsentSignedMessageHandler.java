package gov.samhsa.consent2share.si;

import gov.samhsa.consent2share.domain.consent.ConsentRepository;
import gov.samhsa.consent2share.service.consentexport.ConsentExportService;
import gov.samhsa.consent2share.si.entitlementmanager.EntitlementAdminClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class ConsentSignedMessageHandler {
	@Autowired
	private ConsentExportService consentExportService;
	
	@Autowired
	private ConsentRepository consentRepository; 
	
	@Autowired
	@Value("${endpointAddress.XDSDocumentService}") 
	private String xdsDocumentServiceEndpointAddress;
	
	@Autowired
	private EntitlementAdminClient entitlementAdminClient;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public String handleMessage(String data) {
		logger.debug("Consent Signed Message Received: ConsentId" + new String(data));
		Long consentId = Long.parseLong(data);
		String xacml = consentExportService.exportXACMLConsent(consentId);
		boolean result = entitlementAdminClient.addPolicy(xacml);
		if (result){
			return "Saved in WSO2 Server";
		}
		else {
			return "Failed to save in WSO2 Server";
		}
	}
}
