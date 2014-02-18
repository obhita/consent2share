package gov.samhsa.consent2share.infrastructure;

import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.pep.trypolicy.wsclient.TryPolicyWebServiceClient;
import gov.samhsa.consent.ConsentGenException;
import gov.samhsa.consent2share.service.consentexport.ConsentExportService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TryPolicyServiceImpl implements TryPolicyService {
	
	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/** The try my policy service url. */
	private String tryPolicyServiceUrl;
	
	@Autowired
	private ConsentExportService consentExportService;
	
	/**
	 * Instantiates a new echo sign signature service impl.
	 *
	 * @param tryPolicyServiceUrl the echo sign service url
	 */
	@Autowired
	public TryPolicyServiceImpl(@Value("${tryMyPolicyEndPointAddress}") String tryPolicyServiceUrl) {
		Assert.hasText(tryPolicyServiceUrl);
		
		this.tryPolicyServiceUrl = tryPolicyServiceUrl;
	}

	@Override
	public String tryPolicy(String originalC32, Long consentId) {
		TryPolicyWebServiceClient client = new TryPolicyWebServiceClient(tryPolicyServiceUrl);
		String segmentedC32 = null;
		try {
			segmentedC32 = client.tryPolicy(originalC32, consentExportService.exportConsent2XACML(consentId));
		} catch (ConsentGenException e) {
			logger.error("Error in try Policy: " + e.getMessage());
		}
		
		return segmentedC32;
	}
}
