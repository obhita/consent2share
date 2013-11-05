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
package gov.samhsa.ds4ppilot.pep;

import gov.samhsa.consent2share.accesscontrolservice.pep.xdsb.XdsbDocumentType;
import gov.samhsa.ds4ppilot.common.exception.DS4PException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oasis.names.tc.ebxml_regrep.xsd.lcm._3.SubmitObjectsRequest;

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

	/** The Constant HomeCommunityId_Parameter_Name. */
	private static final String HomeCommunityId_Parameter_Name = "homeCommunityId";

	/** The Constant XdsDocumentEntry_UniqueId_Parameter_Name. */
	private static final String XdsDocumentEntry_UniqueId_Parameter_Name = "XDSDocumentEntry_uniqueId";

	/** The Constant XdsSubmissionSet_UniqueId_Parameter_Name. */
	private static final String XdsSubmissionSet_UniqueId_Parameter_Name = "XDSSubmissionSet_uniqueId";

	/** The document type for xdsb metadata. */
	private XdsbDocumentType documentTypeForXdsbMetadata;

	/**
	 * Instantiates a new xdsb metadata generator impl.
	 * 
	 * @param uniqueOidProvider
	 *            the unique oid provider
	 * @param documentTypeForXdsbMetadata
	 *            the document type for xdsb metadata
	 */
	public XdsbMetadataGeneratorImpl(UniqueOidProvider uniqueOidProvider,
			XdsbDocumentType documentTypeForXdsbMetadata) {
		this.uniqueOidProvider = uniqueOidProvider;
		this.documentTypeForXdsbMetadata = documentTypeForXdsbMetadata;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * XdsbMetadataGenerator#generateMetadataXml
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public String generateMetadataXml(String document, String homeCommunityId) {

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
	 * XdsbMetadataGenerator#generateMetadata
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public SubmitObjectsRequest generateMetadata(String document,
			String homeCommunityId) {

		String metadataXml = generateMetadataXml(document, homeCommunityId);
		SubmitObjectsRequest submitObjectsRequest = null;
		try {
			submitObjectsRequest = unmarshallFromXml(
					SubmitObjectsRequest.class, metadataXml);
		} catch (JAXBException e) {
			throw new DS4PException(e.toString(), e);
		}

		return submitObjectsRequest;
	}

	/**
	 * Unmarshall from xml.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @param xml
	 *            the xml
	 * @return the t
	 * @throws JAXBException
	 *             the jAXB exception
	 */
	@SuppressWarnings("unchecked")
	private static <T> T unmarshallFromXml(Class<T> clazz, String xml)
			throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Unmarshaller um = context.createUnmarshaller();
		ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes());

		return (T) um.unmarshal(input);
	}

	/**
	 * Marshall.
	 * 
	 * @param obj
	 *            the obj
	 * @return the string
	 * @throws Throwable
	 *             the throwable
	 */
	@SuppressWarnings("unused")
	private static String marshall(Object obj) throws Throwable {
		final JAXBContext context = JAXBContext.newInstance(obj.getClass());

		// Create the marshaller, this is the nifty little thing that will
		// actually transform the object into XML
		final Marshaller marshaller = context.createMarshaller();

		// Create a stringWriter to hold the XML
		final StringWriter stringWriter = new StringWriter();

		// Marshal the javaObject and write the XML to the stringWriter
		marshaller.marshal(obj, stringWriter);

		return stringWriter.toString();
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
		if (XdsbDocumentType.CLINICAL_DOCUMENT
				.equals(documentTypeForXdsbMetadata)) {
			return XDSB_METADATA_XSL_FILE_NAME_FOR_CLINICAL_DOCUMENT;
		} else if (XdsbDocumentType.PRIVACY_CONSENT
				.equals(documentTypeForXdsbMetadata)) {
			return XDSB_METADATA_XSL_FILE_NAME_FOR_PRIVACY_CONSENT;
		} else {
			logger.error("Invalid document type for XdsbMetadataGenerator.");
			throw new DS4PException(
					"Invalid document type for XdsbMetadataGenerator.");
		}
	}
}
