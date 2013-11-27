package gov.samhsa.consent2share.showcase.service;

import gov.samhsa.consent2share.pixclient.client.PixManagerClientService;

public interface PixOperationsService extends  PixManagerClientService {

	public String addPatientRegistryRecord(String c32xml);


}
