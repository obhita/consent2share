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
package gov.samhsa.consent2share.showcase.infrastructure;

import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.xdsb.registry.wsclient.XdsbRegistryWebServiceClient;
import gov.samhsa.consent2share.showcase.exception.AcsShowCaseException;

import org.hl7.v3.PRPAIN201301UV02;
import org.hl7.v3.PRPAIN201302UV02;
import org.hl7.v3.PRPAIN201304UV02;
import org.hl7.v3.PatientIdentityFeedRequestType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * The Class XdsbRegistryGetterImpl.
 */
@Component
public class XdsbRegistryGetterImpl implements XdsbRegistryGetter {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The endpoint address. */
	private String endpointAddress;

	/** The marshaller. */
	@Autowired
	private SimpleMarshaller marshaller;

	/** The xdsb registry web service client. */
	private XdsbRegistryWebServiceClient xdsbRegistryWebServiceClient;

	/**
	 * Instantiates a new XdsbRegistryGetter implementation.
	 * 
	 * @param endpointAddress
	 *            the endpoint address
	 */
	@Autowired
	public XdsbRegistryGetterImpl(
			@Value("${xdsbRegistryEndpointAddress}") String endpointAddress) {
		this.endpointAddress = endpointAddress;
		xdsbRegistryWebServiceClient = new XdsbRegistryWebServiceClient(
				this.endpointAddress);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.showcase.infrastructure.XdsbRegistryGetter#
	 * addPatientRegistryRecord(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String addPatientRegistryRecord(String hl7v3Xml, String eId,
			String eIdDomain) {

		return getMessage(PRPAIN201301UV02.class, hl7v3Xml, eId, eIdDomain);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.showcase.infrastructure.XdsbRegistryGetter#
	 * resolvePatientRegistryDuplicates(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String resolvePatientRegistryDuplicates(String hl7v3Xml, String eId,
			String eIdDomain) {

		return getMessage(PRPAIN201304UV02.class, hl7v3Xml, eId, eIdDomain);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.showcase.infrastructure.XdsbRegistryGetter#
	 * revisePatientRegistryRecord(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String revisePatientRegistryRecord(String hl7v3Xml, String eId,
			String eIdDomain) {

		return getMessage(PRPAIN201302UV02.class, hl7v3Xml, eId, eIdDomain);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.showcase.infrastructure.XdsbRegistryGetter#pidFeed
	 * (gov.samhsa.consent2share.showcase.infrastructure.PixOperation,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String pidFeed(PixOperation operation, String hl7v3Xml, String eId,
			String eIdDomain) {

		return getMessage(resolveOperation(operation), hl7v3Xml, eId, eIdDomain);
	}

	/**
	 * Sets the marshaller.
	 * 
	 * @param marshaller
	 *            the new marshaller
	 */
	public void setMarshaller(SimpleMarshaller marshaller) {
		this.marshaller = marshaller;
	}

	/**
	 * Sets the xdsb registry web service client.
	 * 
	 * @param xdsbRegistryWebServiceClient
	 *            the new xdsb registry web service client
	 */
	public void setXdsbRegistryWebServiceClient(
			XdsbRegistryWebServiceClient xdsbRegistryWebServiceClient) {
		this.xdsbRegistryWebServiceClient = xdsbRegistryWebServiceClient;
	}

	/**
	 * Sets the eid values.
	 * 
	 * @param request
	 *            the request
	 * @param eId
	 *            the e id
	 * @param eIdDomain
	 *            the e id domain
	 */
	protected void setEidValues(PatientIdentityFeedRequestType request,
			String eId, String eIdDomain) {
		request.getControlActProcess().getSubject().getRegistrationEvent()
				.getSubject1().getPatient().getId().setExtension(eId);
		request.getControlActProcess().getSubject().getRegistrationEvent()
				.getSubject1().getPatient().getId().setRoot(eIdDomain);
	}

	/**
	 * Gets the message.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @param hl7v3Xml
	 *            the hl7v3 xml
	 * @param eId
	 *            the e id
	 * @param eIdDomain
	 *            the e id domain
	 * @return the message
	 */
	private <T> String getMessage(Class<T> clazz, String hl7v3Xml, String eId,
			String eIdDomain) {
		assertInputs(hl7v3Xml, eId, eIdDomain);
		String msg = "";
		try {
			T input = marshaller.unmarshallFromXml(clazz, hl7v3Xml);
			// set eid values
			setEidValues((PatientIdentityFeedRequestType) input, eId, eIdDomain);
			if (PRPAIN201301UV02.class == clazz) {
				msg = xdsbRegistryWebServiceClient
						.addPatientRegistryRecord((PRPAIN201301UV02) input);
			} else if (PRPAIN201302UV02.class == clazz) {
				msg = xdsbRegistryWebServiceClient
						.revisePatientRegistryRecord((PRPAIN201302UV02) input);
			} else if (PRPAIN201304UV02.class == clazz) {
				msg = xdsbRegistryWebServiceClient
						.resolvePatientRegistryDuplicates((PRPAIN201304UV02) input);
			} else {
				String errorMsg = "Invalid request type for XDS.b PIDFEED operations.";
				logger.error(errorMsg);
				throw new AcsShowCaseException(errorMsg);
			}

		} catch (Throwable e) {
			// Expect the unexpected
			logger.error("Unexpected exception", e);
			// Add error
			logger.error("error",
					"Query Failure! Server error! A unexpected error has occured");
			throw new AcsShowCaseException(e.toString(), e);
		}
		return msg;
	}

	/**
	 * Assert inputs.
	 * 
	 * @param hl7v3Xml
	 *            the hl7v3 xml
	 * @param eId
	 *            the e id
	 * @param eIdDomain
	 *            the e id domain
	 */
	private void assertInputs(String hl7v3Xml, String eId, String eIdDomain) {
		Assert.notNull(hl7v3Xml);
		Assert.notNull(eId);
		Assert.notNull(eIdDomain);
	}

	/**
	 * Resolve operation.
	 * 
	 * @param operation
	 *            the operation
	 * @return the class
	 */
	@SuppressWarnings("rawtypes")
	private Class resolveOperation(PixOperation operation) {
		switch (operation) {
		case ADD:
			return PRPAIN201301UV02.class;
		case UPDATE:
			return PRPAIN201302UV02.class;
		case MERGE:
			return PRPAIN201304UV02.class;
		default:
			break;
		}
		return null;
	}
}
