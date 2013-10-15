package gov.samhsa.ds4ppilot.orchestrator;

import oasis.names.tc.ebxml_regrep.xsd.lcm._3.SubmitObjectsRequest;

/**
 * The Interface XdsbMetadataGenerator.
 */
public interface XdsbMetadataGenerator {

	/**
	 * Generate metadata xml.
	 * 
	 * @param document
	 *            the document
	 * @param homeCommunityId
	 *            the home community id
	 * @return the string
	 */
	public String generateMetadataXml(String document, String homeCommunityId);

	/**
	 * Generate metadata.
	 * 
	 * @param document
	 *            the document
	 * @param homeCommunityId
	 *            the home community id
	 * @return the submit objects request
	 */
	public SubmitObjectsRequest generateMetadata(String document,
			String homeCommunityId);
}
