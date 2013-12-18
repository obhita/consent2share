package gov.samhsa.consent2share.service.account;

import java.io.IOException;

import gov.samhsa.consent2share.hl7.Hl7v3TransformerException;
import gov.samhsa.consent2share.pixclient.util.PixManagerBean;


public interface PixQueryService  {

	public PixManagerBean queryPerson(String mrn) throws Hl7v3TransformerException, IOException;
	
	public String getEid(String mrn) throws Hl7v3TransformerException, IOException;

}
