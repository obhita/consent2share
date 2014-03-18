/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.acs.xdsb.common;

import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.tool.SimpleMarshaller;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBException;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import oasis.names.tc.ebxml_regrep.xsd.lcm._3.SubmitObjectsRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class XdsbMetadataGeneratorImpl.
 */
public class XdsbMetadataGeneratorImpl implements XdsbMetadataGenerator {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The unique oid provider. */
	private final UniqueOidProvider uniqueOidProvider;

	/** The Constant XDSB_METADATA_XSL_FILE_NAME_FOR_CLINICAL_DOCUMENT. */
	private static final String XDSB_METADATA_XSL_FILE_NAME_FOR_CLINICAL_DOCUMENT = "XdsbMetadata.xsl";

	/** The Constant XDSB_METADATA_XSL_FILE_NAME_FOR_PRIVACY_CONSENT. */
	private static final String XDSB_METADATA_XSL_FILE_NAME_FOR_PRIVACY_CONSENT = "XdsbMetadataForXacmlPolicy.xsl";

	/** The Constant XDSB_METADATA_XSL_FILE_NAME_FOR_PRIVACY_CONSENT_DEPRECATED. */
	private static final String XDSB_METADATA_XSL_FILE_NAME_FOR_PRIVACY_CONSENT_DEPRECATED = "XdsbMetadataForXacmlPolicyDeprecated.xsl";

	/** The Constant HomeCommunityId_Parameter_Name. */
	private static final String HomeCommunityId_Parameter_Name = "homeCommunityId";

	/** The Constant PatientUniqueId_Parameter_Name. */
	private static final String PatientUniqueId_Parameter_Name = "patientUniqueId";

	/** The Constant XdsDocumentEntry_UniqueId_Parameter_Name. */
	private static final String XdsDocumentEntry_UniqueId_Parameter_Name = "XDSDocumentEntry_uniqueId";

	/** The Constant XdsSubmissionSet_UniqueId_Parameter_Name. */
	private static final String XdsSubmissionSet_UniqueId_Parameter_Name = "XDSSubmissionSet_uniqueId";

	/** The Constant XdsDocumentEntry_EntryUUID_Parameter_Name. */
	private static final String XdsDocumentEntry_EntryUUID_Parameter_Name = "XDSDocumentEntry_entryUUID";

	/** The document type for xdsb metadata. */
	private XdsbDocumentType documentTypeForXdsbMetadata;

	/** The marshaller. */
	private SimpleMarshaller marshaller;

	/**
	 * Instantiates a new xdsb metadata generator impl.
	 * 
	 * @param uniqueOidProvider
	 *            the unique oid provider
	 * @param documentTypeForXdsbMetadata
	 *            the document type for xdsb metadata
	 * @param marshaller
	 *            the marshaller
	 */
	public XdsbMetadataGeneratorImpl(UniqueOidProvider uniqueOidProvider,
			XdsbDocumentType documentTypeForXdsbMetadata,
			SimpleMarshaller marshaller) {
		this.uniqueOidProvider = uniqueOidProvider;
		this.documentTypeForXdsbMetadata = documentTypeForXdsbMetadata;
		this.marshaller = marshaller;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.xdsb.common.XdsbMetadataGenerator#generateMetadataXml(
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String generateMetadataXml(String document, String homeCommunityId,
			String patientUniqueId, String entryUUID) {

		StringWriter stringWriter = null;
		InputStream inputStream = null;

		try {
			inputStream = Thread
					.currentThread()
					.getContextClassLoader()
					.getResourceAsStream(
							resolveXslFileName(documentTypeForXdsbMetadata));

			StreamSource styleSheetStremSource = new StreamSource(inputStream);

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Templates template = transformerFactory
					.newTemplates(styleSheetStremSource);

			Transformer transformer = template.newTransformer();

			String xdsDocumentEntryUniqueId = uniqueOidProvider.getOid();
			String xdsSubmissionSetUniqueId = uniqueOidProvider.getOid();

			transformer.setParameter(HomeCommunityId_Parameter_Name,
					homeCommunityId);
			transformer.setParameter(XdsDocumentEntry_UniqueId_Parameter_Name,
					xdsDocumentEntryUniqueId);
			transformer.setParameter(XdsSubmissionSet_UniqueId_Parameter_Name,
					xdsSubmissionSetUniqueId);

			setParameterIfDeprecateDocumentAction(transformer,
					XdsDocumentEntry_EntryUUID_Parameter_Name, entryUUID,
					"entryUUID can only be injected while deprecating a document.");
			setParameterIfDeprecateDocumentAction(transformer,
					PatientUniqueId_Parameter_Name, patientUniqueId,
					"patientUniqueId can only be injected while deprecating a document.");

			stringWriter = new StringWriter();
			transformer.transform(new StreamSource(new StringReader(document)),
					new StreamResult(stringWriter));

			String metadataXml = stringWriter.toString();

			inputStream.close();
			stringWriter.close();

			return metadataXml;

		} catch (IOException e) {
			throw new DS4PException(e.toString(), e);

		} catch (TransformerException e) {
			throw new DS4PException(e.toString(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.xdsb.common.XdsbMetadataGenerator#generateMetadata(java
	 * .lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public SubmitObjectsRequest generateMetadata(String document,
			String homeCommunityId, String patientUniqueId, String entryUUID) {

		String metadataXml = generateMetadataXml(document, homeCommunityId,
				patientUniqueId, entryUUID);
		SubmitObjectsRequest submitObjectsRequest = null;
		try {
			submitObjectsRequest = marshaller.unmarshallFromXml(
					SubmitObjectsRequest.class, metadataXml);
		} catch (JAXBException e) {
			throw new DS4PException(e.toString(), e);
		}

		return submitObjectsRequest;
	}

	/**
	 * Resolve xsl file name.
	 * 
	 * @param documentTypeForXdsbMetadata
	 *            the document type for xdsb metadata
	 * @return the string
	 */
	private String resolveXslFileName(
			XdsbDocumentType documentTypeForXdsbMetadata) {

		switch (documentTypeForXdsbMetadata) {
		case CLINICAL_DOCUMENT:
			return XDSB_METADATA_XSL_FILE_NAME_FOR_CLINICAL_DOCUMENT;
		case PRIVACY_CONSENT:
			return XDSB_METADATA_XSL_FILE_NAME_FOR_PRIVACY_CONSENT;
		case DEPRECATE_PRIVACY_CONSENT:
			return XDSB_METADATA_XSL_FILE_NAME_FOR_PRIVACY_CONSENT_DEPRECATED;
		default:
			throw new DS4PException(
					"Unsupported document type for XdsbMetadataGenerator.");
		}
	}

	/**
	 * Sets the parameter if deprecate document action.
	 * 
	 * @param transformer
	 *            the transformer
	 * @param parameterName
	 *            the parameter name
	 * @param parameterValue
	 *            the parameter value
	 * @param errorMessage
	 *            the error message
	 */
	private void setParameterIfDeprecateDocumentAction(Transformer transformer,
			String parameterName, String parameterValue, String errorMessage) {
		if (parameterValue != null) {
			if (!documentTypeForXdsbMetadata
					.equals(XdsbDocumentType.DEPRECATE_PRIVACY_CONSENT)) {
				logger.error(errorMessage);
				throw new DS4PException(errorMessage);
			} else {
				transformer.setParameter(parameterName, parameterValue.replace("'", ""));
			}
		}
	}
}
