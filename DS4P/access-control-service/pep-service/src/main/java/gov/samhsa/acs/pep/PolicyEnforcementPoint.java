package gov.samhsa.acs.pep;

import gov.samhsa.ds4ppilot.schema.pep.DirectEmailSendRequest;
import gov.samhsa.ds4ppilot.schema.pep.DirectEmailSendResponse;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

/**
 * The Interface PolicyEnforcementPoint.
 */
public interface PolicyEnforcementPoint {

	/**
	 * Direct email send.
	 * 
	 * @param parameters
	 *            the parameters
	 * @return the direct email send response
	 */
	public abstract DirectEmailSendResponse directEmailSend(
			DirectEmailSendRequest parameters);

	/**
	 * Registry stored query.
	 * 
	 * @param input
	 *            the input
	 * @return the adhoc query response
	 */
	public abstract AdhocQueryResponse registryStoredQuery(
			AdhocQueryRequest input);

	/**
	 * Retrieve document set.
	 * 
	 * @param input
	 *            the input
	 * @return the retrieve document set response
	 */
	public abstract RetrieveDocumentSetResponse retrieveDocumentSet(
			RetrieveDocumentSetRequest input);
}