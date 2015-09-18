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
package gov.samhsa.acs.pep.ws;

import gov.samhsa.acs.brms.domain.XacmlResult;
import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.log.AcsLogger;
import gov.samhsa.acs.common.log.AcsLoggerFactory;
import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.common.tool.exception.SimpleMarshallerException;
import gov.samhsa.acs.common.validation.exception.XmlDocumentReadFailureException;
import gov.samhsa.acs.documentsegmentation.DocumentSegmentation;
import gov.samhsa.acs.documentsegmentation.dto.SegmentDocumentResponse;
import gov.samhsa.acs.documentsegmentation.exception.InvalidSegmentedClinicalDocumentException;
import gov.samhsa.acs.dss.ws.contract.DSSPortType;
import gov.samhsa.acs.dss.ws.schema.DSSRequest;
import gov.samhsa.acs.dss.ws.schema.DSSRequestForDirect;
import gov.samhsa.acs.dss.ws.schema.DSSResponse;
import gov.samhsa.acs.dss.ws.schema.DSSResponseForDirect;

import java.util.Optional;

import ch.qos.logback.audit.AuditException;

/**
 * The Class DSSPortTypeImpl.
 */
@javax.jws.WebService(serviceName = "DSS", portName = "DSSPort", targetNamespace = "http://acs.samhsa.gov/dss/ws/contract", wsdlLocation = "classpath:/wsdl/DSS.wsdl", endpointInterface = "gov.samhsa.acs.dss.ws.contract.DSSPortType")
public class DSSPortTypeImpl implements DSSPortType {

	private final Boolean DEFAULT_IS_AUDITED;

	private final Boolean DEFAULT_IS_AUDIT_FAILURE_BY_PASS;

	/** The logger. */
	private final AcsLogger logger = AcsLoggerFactory.getLogger(this);

	/** The marshaller. */
	private final SimpleMarshaller marshaller;

	/** The dss. */
	private final DocumentSegmentation dss;

	/**
	 * Instantiates a new DSS port type impl.
	 *
	 * @param dss
	 *            the dss
	 * @param marshaller
	 *            the marshaller
	 */
	public DSSPortTypeImpl(boolean defaultIsAudited,
			boolean defaultIsAuditFailureByPass, DocumentSegmentation dss,
			SimpleMarshaller marshaller) {
		super();
		this.DEFAULT_IS_AUDITED = defaultIsAudited;
		this.DEFAULT_IS_AUDIT_FAILURE_BY_PASS = defaultIsAuditFailureByPass;
		this.dss = dss;
		this.marshaller = marshaller;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * gov.samhsa.acs.dss.ws.contract.DSSPortType#segmentDocument(gov.samhsa
	 * .acs.dss.ws.schema.DSSRequest)
	 */
	@Override
	public DSSResponse segmentDocument(DSSRequest parameters) {
		final DSSResponse response = new DSSResponse();

		try {
			final SegmentDocumentResponse segmentDocument = dss
					.segmentDocument(
							parameters.getDocumentXml(),
							parameters.getEnforcementPoliciesXml(),
							Optional.ofNullable(parameters.isAudited())
							.orElseGet(() -> DEFAULT_IS_AUDITED),
							Optional.ofNullable(
									parameters.isAuditFailureByPass())
									.orElseGet(
											() -> DEFAULT_IS_AUDIT_FAILURE_BY_PASS),
											Optional.ofNullable(
													parameters.isEnableTryPolicyResponse())
													.orElseGet(() -> Boolean.FALSE));
			response.setSegmentedDocumentXml(segmentDocument
					.getSegmentedDocumentXml());
			response.setTryPolicyDocumentXml(segmentDocument
					.getTryPolicyDocumentXml());
		} catch (InvalidSegmentedClinicalDocumentException
				| XmlDocumentReadFailureException | AuditException e) {
			try {
				final XacmlResult xacmlResult = marshaller.unmarshalFromXml(
						XacmlResult.class,
						parameters.getEnforcementPoliciesXml());
				logger.error(xacmlResult.getMessageId(), e.getMessage(), e);
				throw new DS4PException(e);
			} catch (final SimpleMarshallerException e1) {
				logger.getLogger().error(e.getMessage(), e);
				logger.getLogger().error(e1.getMessage(), e1);
				throw new DS4PException(e);
			}

		}
		return response;
	}

	@Override
	public DSSResponseForDirect segmentDocumentForDirect(
			DSSRequestForDirect parameters) {
		final DSSResponseForDirect response = new DSSResponseForDirect();

		XacmlResult xacmlResult = null;
		try {
			xacmlResult = marshaller.unmarshalFromXml(XacmlResult.class,
					parameters.getEnforcementPoliciesXml());
			final String senderEmailAddress = parameters
					.getSenderEmailAddress();
			final String recipientEmailAddress = parameters
					.getRecipientEmailAddress();
			final String xdsDocumentEntryUniqueId = parameters
					.getXdsDocumentEntryUniqueId();
			final SegmentDocumentResponse segmentDocument = dss
					.segmentDocument(
							parameters.getDocumentXml(),
							parameters.getEnforcementPoliciesXml(),
							Optional.ofNullable(parameters.isAudited())
							.orElseGet(() -> DEFAULT_IS_AUDITED),
							Optional.ofNullable(
									parameters.isAuditFailureByPass())
									.orElseGet(
											() -> DEFAULT_IS_AUDIT_FAILURE_BY_PASS),
							Boolean.FALSE);
			dss.setAdditionalMetadataForSegmentedClinicalDocument(
					segmentDocument, senderEmailAddress, recipientEmailAddress,
					xdsDocumentEntryUniqueId, xacmlResult);
			dss.setDocumentPayloadRawData(segmentDocument,
					parameters.isPackageAsXdm(), senderEmailAddress,
					recipientEmailAddress, xacmlResult);
			response.setSegmentedDocumentXml(segmentDocument
					.getSegmentedDocumentXml());
			response.setPostSegmentationMetadataXml(segmentDocument
					.getPostSegmentationMetadataXml());
			response.setDocumentPayloadRawData(segmentDocument
					.getDocumentPayloadRawData());
		} catch (final Exception e) {
			logger.error(xacmlResult.getMessageId(), e.getMessage(), e);
			throw new DS4PException(e);
		}
		return response;
	}
}
