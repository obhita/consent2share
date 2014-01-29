package gov.samhsa.acs.pep.ws;

import gov.samhsa.acs.pep.PolicyEnforcementPoint;
import gov.samhsa.acs.pep.saml.SamlTokenParser;
import gov.samhsa.ds4ppilot.contract.pep.PepPortType;
import gov.samhsa.ds4ppilot.schema.pep.DirectEmailSendRequest;
import gov.samhsa.ds4ppilot.schema.pep.DirectEmailSendResponse;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.apache.cxf.feature.Features;
import org.apache.cxf.interceptor.InInterceptors;
import org.apache.cxf.interceptor.OutInterceptors;
import org.apache.ws.security.SAMLTokenPrincipal;

/**
 * The Class PepPortTypeImpl.
 */
@WebService(targetNamespace = "http://www.samhsa.gov/ds4ppilot/contract/pep", portName = "XDS_HTTP_Endpoint", serviceName = "PepService", endpointInterface = "gov.samhsa.ds4ppilot.contract.pep.PepPortType")
public class PepPortTypeImpl implements PepPortType {
	
	/** The pep. */
	private PolicyEnforcementPoint pep;

	/**
	 * Instantiates a new pep port type impl.
	 * 
	 * @param pep
	 *            the pep
	 */
	public PepPortTypeImpl(PolicyEnforcementPoint pep) {
		this.pep = pep;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.ds4ppilot.contract.pep.PepPortType#directEmailSend(gov.samhsa
	 * .ds4ppilot.schema.pep.DirectEmailSendRequest)
	 */
	@Override
	public DirectEmailSendResponse directEmailSend(
			DirectEmailSendRequest parameters) {
		
		return pep.directEmailSend(parameters);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.ds4ppilot.contract.pep.PepPortType#registryStoredQuery(oasis
	 * .names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest)
	 */
	@Override
	public AdhocQueryResponse registryStoredQuery(AdhocQueryRequest input) {
		
		return pep.registryStoredQuery(input);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.ds4ppilot.contract.pep.PepPortType#retrieveDocumentSet(ihe
	 * .iti.xds_b._2007.RetrieveDocumentSetRequest)
	 */
	@Override
	public RetrieveDocumentSetResponse retrieveDocumentSet(
			RetrieveDocumentSetRequest input) {
		
		return pep.retrieveDocumentSet(input);
	}
}