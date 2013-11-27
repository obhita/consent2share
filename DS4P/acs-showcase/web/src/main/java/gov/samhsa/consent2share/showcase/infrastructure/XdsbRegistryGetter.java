package gov.samhsa.consent2share.showcase.infrastructure;



public interface XdsbRegistryGetter {

	/**
	 * Adds the patient registry record.
	 */
	public String addPatientRegistryRecord(String hl7v3Xml, String eId, String eIdDomain);
	
	/** Resolve patient registry duplicates.
	 */
	public String resolvePatientRegistryDuplicates(String hl7v3Xml, String eId, String eIdDomain);
	
	/**
	 * Revise patient registry record.
	 */
	public String revisePatientRegistryRecord(String hl7v3Xml, String eId, String eIdDomain);
	
}
