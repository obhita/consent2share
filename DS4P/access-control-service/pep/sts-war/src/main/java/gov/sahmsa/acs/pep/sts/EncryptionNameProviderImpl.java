package gov.sahmsa.acs.pep.sts;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncryptionNameProviderImpl implements EncryptionNameProvider {
	
	private Map<Pattern, String> serviceProviderAddressPatternMap = new HashMap<Pattern, String>();

	@Override
	public String getEncryptionName(String serviceAddress) {
		
		String encryptionName = null;
		
		String addressToMatch = serviceAddress;
        if (addressToMatch == null) {
            addressToMatch = "";
        }
		
		for (Map.Entry<Pattern, String> entry : serviceProviderAddressPatternMap.entrySet()) {
			Pattern pattern = entry.getKey();
			
			final Matcher matcher = pattern.matcher(serviceAddress);
            if (matcher.matches()) {
            	encryptionName = entry.getValue();
            	break;
            }
		}
		
		return encryptionName;
	}

	public void setServiceProviderAddressPatternMap(Map<String, String> serviceProviderAddressPatternMap) {
		for (Map.Entry<String, String> entry : serviceProviderAddressPatternMap.entrySet()) {
			Pattern pattern = Pattern.compile(entry.getKey());
			this.serviceProviderAddressPatternMap.put(pattern, entry.getValue());
		}
	}
}
