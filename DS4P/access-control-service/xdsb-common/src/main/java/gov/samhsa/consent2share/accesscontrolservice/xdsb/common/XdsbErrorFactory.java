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
package gov.samhsa.consent2share.accesscontrolservice.xdsb.common;

import java.util.LinkedList;
import java.util.List;

import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest.DocumentRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryError;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryErrorList;
import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

/**
 * A factory for creating XdsbError objects.
 */
public class XdsbErrorFactory {

	/**
	 * Error retrieve document set response multiple repository id.
	 * 
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse errorRetrieveDocumentSetResponseMultipleRepositoryId() {
		String codeContext = "All repository ids in RetrieveDocumentSetRequest need to be same.";
		String errorCode = "XDSRepositoryError";

		return createRetrieveDocumentSetResponseError(codeContext, errorCode);
	}

	/**
	 * Error retrieve document set response not exists or accessible.
	 * 
	 * @param input
	 *            the input
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse errorRetrieveDocumentSetResponseNotExistsOrAccessible(
			RetrieveDocumentSetRequest input) {
		List<String> list = new LinkedList<String>();
		for (DocumentRequest req : input.getDocumentRequest()) {
			list.add(req.getDocumentUniqueId() + ":"
					+ req.getRepositoryUniqueId());
		}
		String codeContext = "The document(s) " + list.toString()
				+ " do not exist or access denied by Policy Decision Point.";
		String errorCode = "XDSRepositoryError";

		return createRetrieveDocumentSetResponseError(codeContext, errorCode);
	}

	/**
	 * Error retrieve document set response repository not available.
	 * 
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse errorRetrieveDocumentSetResponseRepositoryNotAvailable() {
		String codeContext = "Policy Enforcement Point is unable to access the XDS.b Repository.";
		String errorCode = "XDSRepositoryError";

		return createRetrieveDocumentSetResponseError(codeContext, errorCode);
	}

	/**
	 * Error retrieve document set response internal pep error.
	 * 
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse errorRetrieveDocumentSetResponseInternalPEPError() {
		String codeContext = "An internal error occured in Policy Enforcement Point.";
		String errorCode = "XDSRepositoryError";

		return createRetrieveDocumentSetResponseError(codeContext, errorCode);
	}

	/**
	 * Error retrieve document set response access denied by pdp.
	 * 
	 * @return the retrieve document set response
	 */
	public RetrieveDocumentSetResponse errorRetrieveDocumentSetResponseAccessDeniedByPDP() {
		String codeContext = "The access to patient documents is denied by Policy Decision Point.";
		String errorCode = "XDSRepositoryError";

		return createRetrieveDocumentSetResponseError(codeContext, errorCode);
	}

	/**
	 * Error adhoc query response inconsistent patient unique id.
	 * 
	 * @param patientUniqueIdFromRequest
	 *            the patient unique id from request
	 * @param patientUniqueIdFromSAML
	 *            the patient unique id from saml
	 * @return the adhoc query response
	 */
	public AdhocQueryResponse errorAdhocQueryResponseInconsistentPatientUniqueId(
			String patientUniqueIdFromRequest, String patientUniqueIdFromSAML) {
		String codeContext = "The document entry patient id in $XDSDocumentEntryPatientId ("
				+ patientUniqueIdFromRequest
				+ ") does not match the patient unique id ("
				+ patientUniqueIdFromSAML + ") generated from SAML header.";
		String errorCode = "XDSRegistryError";

		return createAdhocQueryResponseError(codeContext, errorCode);
	}

	/**
	 * Error adhoc query response registry not available.
	 * 
	 * @return the adhoc query response
	 */
	public AdhocQueryResponse errorAdhocQueryResponseRegistryNotAvailable() {
		String codeContext = "Policy Enforcement Point is unable to access the XDS.b Registry.";
		String errorCode = "XDSRegistryError";

		return createAdhocQueryResponseError(codeContext, errorCode);
	}

	/**
	 * Error adhoc query response access denied by pdp.
	 * 
	 * @return the adhoc query response
	 */
	public AdhocQueryResponse errorAdhocQueryResponseAccessDeniedByPDP() {
		String codeContext = "The access to patient documents is denied by Policy Decision Point.";
		String errorCode = "XDSRegistryError";

		return createAdhocQueryResponseError(codeContext, errorCode);
	}

	/**
	 * Error adhoc query response no documents found.
	 * 
	 * @param patientUniqueId
	 *            the patient unique id
	 * @param intermediarySubjectNPI
	 *            the intermediary subject npi
	 * @return the adhoc query response
	 */
	public AdhocQueryResponse errorAdhocQueryResponseNoDocumentsFound(
			String patientUniqueId, String intermediarySubjectNPI) {
		String codeContext = "No documents found for patient "
				+ patientUniqueId + " authored by " + intermediarySubjectNPI
				+ ".";
		String errorCode = "XDSRegistryError";

		return createAdhocQueryResponseError(codeContext, errorCode);
	}

	/**
	 * Error adhoc query response unsupported format code.
	 * 
	 * @param unsupportedFormatCode
	 *            the unsupported format code
	 * @return the adhoc query response
	 */
	public AdhocQueryResponse errorAdhocQueryResponseUnsupportedFormatCode(
			String unsupportedFormatCode) {
		String codeContext = unsupportedFormatCode
				+ " format code is not supported by Policy Enforcement Point."
				+ " The only supported format code is '2.16.840.1.113883.10.20.1^^HITSP'.";
		String errorCode = "XDSRegistryError";

		return createAdhocQueryResponseError(codeContext, errorCode);
	}

	/**
	 * Error adhoc query response unsupported response option type.
	 * 
	 * @param supportedResponseOptionType
	 *            the supported response option type
	 * @return the adhoc query response
	 */
	public AdhocQueryResponse errorAdhocQueryResponseUnsupportedResponseOptionType(
			String supportedResponseOptionType) {
		String codeContext = "Policy Enforcement Point only supports '"
				+ supportedResponseOptionType + "' response option return type.";
		String errorCode = "XDSRegistryError";

		return createAdhocQueryResponseError(codeContext, errorCode);
	}

	/**
	 * Error adhoc query response missing parameters.
	 * 
	 * @return the adhoc query response
	 */
	public AdhocQueryResponse errorAdhocQueryResponseMissingParameters() {
		String codeContext = "$XDSDocumentEntryPatientId, $XDSDocumentEntryStatus and $XDSDocumentEntryFormatCode are required by Policy Enforcement Point.";
		String errorCode = "XDSStoredQueryMissingParam";

		return createAdhocQueryResponseError(codeContext, errorCode);
	}

	/**
	 * Creates a new XdsbError object.
	 * 
	 * @param codeContext
	 *            the code context
	 * @param errorCode
	 *            the error code
	 * @return the adhoc query response
	 */
	private AdhocQueryResponse createAdhocQueryResponseError(
			String codeContext, String errorCode) {
		AdhocQueryResponse errorResponse = new AdhocQueryResponse();
		errorResponse
				.setStatus("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure");

		RegistryErrorList registryErrorList = createRegistryErrorList(
				codeContext, errorCode);

		errorResponse.setRegistryErrorList(registryErrorList);
		return errorResponse;
	}

	/**
	 * Creates a new XdsbError object.
	 * 
	 * @param codeContext
	 *            the code context
	 * @param errorCode
	 *            the error code
	 * @return the retrieve document set response
	 */
	private RetrieveDocumentSetResponse createRetrieveDocumentSetResponseError(
			String codeContext, String errorCode) {
		RetrieveDocumentSetResponse errorRetrieveDocumentSetResponse = new RetrieveDocumentSetResponse();
		RegistryResponse errorResponse = errorRetrieveDocumentSetResponse
				.getRegistryResponse();
		errorResponse
				.setStatus("urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Failure");

		RegistryErrorList registryErrorList = createRegistryErrorList(
				codeContext, errorCode);

		errorResponse.setRegistryErrorList(registryErrorList);
		return errorRetrieveDocumentSetResponse;
	}

	/**
	 * Creates a new XdsbError object.
	 * 
	 * @param codeContext
	 *            the code context
	 * @param errorCode
	 *            the error code
	 * @return the registry error list
	 */
	private RegistryErrorList createRegistryErrorList(String codeContext,
			String errorCode) {
		RegistryErrorList registryErrorList = new RegistryErrorList();

		RegistryError error = new RegistryError();
		error.setCodeContext(codeContext);
		error.setErrorCode(errorCode);
		error.setLocation("");
		error.setSeverity("urn:oasis:names:tc:ebxml-regrep:ErrorSeverityType:Error");
		registryErrorList.getRegistryError().add(error);
		return registryErrorList;
	}
}
