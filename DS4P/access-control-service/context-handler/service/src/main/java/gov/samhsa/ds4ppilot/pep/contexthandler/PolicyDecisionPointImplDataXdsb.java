/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *   
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *       * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *       * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *       * Neither the name of the <organization> nor the
 *         names of its contributors may be used to endorse or promote products
 *         derived from this software without specific prior written permission.
 *   
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *   DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.ds4ppilot.pep.contexthandler;

import gov.samhsa.consent2share.accesscontrolservice.xdsb.common.XdsbDocumentType;
import gov.samhsa.consent2share.accesscontrolservice.xdsb.registry.adapter.XdsbRegistryAdapter;
import gov.samhsa.consent2share.accesscontrolservice.xdsb.repository.adapter.XdsbRepositoryAdapter;
import gov.samhsa.ds4ppilot.common.exception.DS4PException;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse.DocumentResponse;

import java.io.ByteArrayInputStream;
import java.util.LinkedList;
import java.util.List;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.herasaf.xacml.core.policy.Evaluatable;
import org.herasaf.xacml.core.policy.PolicyMarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class PolicyDecisionPointImplDataXdsb.
 */
public class PolicyDecisionPointImplDataXdsb implements
		PolicyDecisionPointImplData {

	/** The Constant URN_POLICY_COMBINING_ALGORITHM_PERMIT_OVERRIDES. */
	public static final String URN_POLICY_COMBINING_ALGORITHM_PERMIT_OVERRIDES = "urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:permit-overrides";

	/** The urn policy combining algorithm. */
	private String urnPolicyCombiningAlgorithm;

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The xdsb registry. */
	private XdsbRegistryAdapter xdsbRegistry;

	/** The xdsb repository. */
	private XdsbRepositoryAdapter xdsbRepository;

	/**
	 * Instantiates a new policy decision point impl data xdsb.
	 * 
	 * @param xdsbRegistry
	 *            the xdsb registry
	 * @param xdsbRepository
	 *            the xdsb repository
	 */
	public PolicyDecisionPointImplDataXdsb(XdsbRegistryAdapter xdsbRegistry,
			XdsbRepositoryAdapter xdsbRepository) {
		this.xdsbRegistry = xdsbRegistry;
		this.xdsbRepository = xdsbRepository;
		this.urnPolicyCombiningAlgorithm = URN_POLICY_COMBINING_ALGORITHM_PERMIT_OVERRIDES;
	}

	/**
	 * Instantiates a new policy decision point impl data xdsb.
	 * 
	 * @param xdsbRegistry
	 *            the xdsb registry
	 * @param xdsbRepository
	 *            the xdsb repository
	 * @param urnPolicyCombiningAlgorithm
	 *            the urn policy combining algorithm
	 */
	public PolicyDecisionPointImplDataXdsb(XdsbRegistryAdapter xdsbRegistry,
			XdsbRepositoryAdapter xdsbRepository,
			String urnPolicyCombiningAlgorithm) {
		this.xdsbRegistry = xdsbRegistry;
		this.xdsbRepository = xdsbRepository;
		if (urnPolicyCombiningAlgorithm == null
				|| "".equals(urnPolicyCombiningAlgorithm)) {
			this.urnPolicyCombiningAlgorithm = URN_POLICY_COMBINING_ALGORITHM_PERMIT_OVERRIDES;
		} else {
			this.urnPolicyCombiningAlgorithm = urnPolicyCombiningAlgorithm;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.ds4ppilot.pep.contexthandler.PolicyDecisionPointImplData#
	 * getPolicies(java.lang.String)
	 */
	@Override
	public List<Evaluatable> getPolicies(String patientUniqueId) {
		List<Evaluatable> policies = new LinkedList<Evaluatable>();
		List<String> policiesString = new LinkedList<String>();
		try {
			// Retrieve policy documents
			AdhocQueryResponse response = xdsbRegistry.registryStoredQuery(
					patientUniqueId, null, XdsbDocumentType.PRIVACY_CONSENT,
					true);

			// Extract doc.request from query response
			RetrieveDocumentSetRequest retrieveDocumentSetRequest = xdsbRegistry
					.extractXdsbDocumentReferenceListAsRetrieveDocumentSetRequest(response);

			// Retrieve all policies
			RetrieveDocumentSetResponse retrieveDocumentSetResponse = xdsbRepository
					.retrieveDocumentSet(retrieveDocumentSetRequest);

			// Add policy documents to a string list
			for (DocumentResponse docResponse : retrieveDocumentSetResponse
					.getDocumentResponse()) {
				String docString = new String(docResponse.getDocument());
				docString = docString.replace(
						"<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
				policiesString.add(docString);
			}

			// Wrap policies in a policy set
			String policySet = makePolicySet(policiesString);

			// Unmarshall policy set as an Evaluatable and add to policy list
			Evaluatable policy = PolicyMarshaller
					.unmarshal(new ByteArrayInputStream(policySet.getBytes()));
			policies.add(policy);
		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
			throw new DS4PException(
					"Consent files cannot be queried/retrieved from XDS.b");
		}
		return policies;
	}

	/**
	 * Gets the policy set header.
	 * 
	 * @return the policy set header
	 */
	private String getPolicySetHeader() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><PolicySet xmlns=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os http://docs.oasis-open.org/xacml/access_control-xacml-2.0-policy-schema-os.xsd\" PolicySetId=\"urn:oasis:names:tc:xacml:2.0:example:policysetid:1\" PolicyCombiningAlgId=\""
				+ this.urnPolicyCombiningAlgorithm
				+ "\"><Description/><Target/>";
	}

	/**
	 * Gets the policy set footer.
	 * 
	 * @return the policy set footer
	 */
	private String getPolicySetFooter() {
		return "</PolicySet>";
	}

	/**
	 * Make policy set.
	 * 
	 * @param policies
	 *            the policies
	 * @return the string
	 */
	private String makePolicySet(List<String> policies) {
		StringBuilder builder = new StringBuilder();
		builder.append(getPolicySetHeader());
		for (String policy : policies) {
			builder.append(policy);
		}
		builder.append(getPolicySetFooter());
		return builder.toString();
	}
}
