package gov.samhsa.consent2share.pixclient.client;


public interface PixMgrClientService {
	
	public String addPerson(String reqXMLPath);

	public String updatePerson(String reqXMLPath);

	public String queryPerson(String xmlFilePath);

}
