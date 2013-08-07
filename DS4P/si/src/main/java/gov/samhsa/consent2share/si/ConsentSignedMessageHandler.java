package gov.samhsa.consent2share.si;

import gov.samhsa.consent2share.domain.consent.ConsentRepository;
import gov.samhsa.consent2share.service.consentexport.ConsentExportService;
import gov.samhsa.schemas.client.XdsDocumentWebServiceClient;

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

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public String handleMessage(String data) {
		logger.debug("Consent Signed Message Received: ConsentId" + new String(data));
		
		Long consentId = Long.parseLong(data);
		
		XdsDocumentWebServiceClient xdsService = new XdsDocumentWebServiceClient(xdsDocumentServiceEndpointAddress);
		
		// Save cdar2
		String cdar2 = consentExportService.exportCDAR2Consent(consentId);
		boolean result = xdsService.saveDocumentSetToXdsRepository(cdar2);
		
		
		if (result){
			return "Saved in xds.b repository";
		}
		else {
			return "Failed to save in xds.b repository";
		}
	}
}
