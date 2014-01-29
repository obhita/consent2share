package gov.sahmsa.acs.pep.sts;

import org.apache.cxf.sts.service.EncryptionProperties;
import org.apache.cxf.sts.service.StaticService;

public class DynamicService extends StaticService {

	private EncryptionNameProvider encryptionNameProvider;

	public DynamicService(EncryptionNameProvider encryptionNameProvider) {
		this.encryptionNameProvider = encryptionNameProvider;
	}

	@Override
	public boolean isAddressInEndpoints(String address) {
		boolean result = super.isAddressInEndpoints(address);

		if (result) {
			EncryptionProperties encryptionProperties = getEncryptionProperties();
			encryptionProperties = encryptionProperties == null ? new EncryptionProperties()
					: encryptionProperties;

			String encryptionName = encryptionNameProvider
					.getEncryptionName(address);
			encryptionProperties.setEncryptionName(encryptionName);
			setEncryptionProperties(encryptionProperties);
		}

		return result;
	}
}
