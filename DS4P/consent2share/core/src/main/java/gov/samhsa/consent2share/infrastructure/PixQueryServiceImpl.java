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
package gov.samhsa.consent2share.infrastructure;

import gov.samhsa.consent2share.hl7.Hl7v3Transformer;
import gov.samhsa.consent2share.hl7.Hl7v3TransformerException;
import gov.samhsa.consent2share.pixclient.service.PixManagerService;
import gov.samhsa.consent2share.pixclient.util.PixManagerBean;
import gov.samhsa.consent2share.pixclient.util.PixManagerConstants;
import gov.samhsa.consent2share.pixclient.util.PixManagerRequestXMLToJava;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.apache.commons.jxpath.JXPathContext;
import org.hl7.v3.types.II;
import org.hl7.v3.types.MCCIMT000300UV01Acknowledgement;
import org.hl7.v3.types.MCCIMT000300UV01AcknowledgementDetail;
import org.hl7.v3.types.PRPAIN201309UV02;
import org.hl7.v3.types.PRPAIN201310UV02;
import org.hl7.v3.types.PRPAMT201307UV02PatientIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The Class PixQueryServiceImpl.
 */
@Service
public class PixQueryServiceImpl implements PixQueryService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The mrn domain. */
	@Value("${patientDomainId}")
	private String mrnDomain;

	/** The request xml to java. */
	@Autowired
	private PixManagerRequestXMLToJava requestXMLToJava;

	/** The pix manager service. */
	@Autowired
	private PixManagerService pixManagerService;

	/** pix operations. */
	@Autowired
	private Hl7v3Transformer hl7v3Transformer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.patient.PixQueryService#getEid(java.
	 * lang.String)
	 */
	@Override
	public String getEid(String mrn) {
		String eid = null;
		PixManagerBean pixManagerBean = null;
		try {
			pixManagerBean = queryPerson(mrn, this.mrnDomain);
		} catch (Hl7v3TransformerException e) {
			logger.error("The MRN cannot be transformed to an HL7v3 message.");
			logger.error(e.getMessage(), e);
		}
		if (pixManagerBean != null) {
			Map<String, String> queryMap = pixManagerBean.getQueryIdMap();

			if (null != queryMap) {
				// person already available in EMPI get global identifier
				// id(EID)
				// get Eid Values
				eid = queryMap.get(PixManagerConstants.GLOBAL_DOMAIN_ID);
			}
		}

		return eid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.service.patient.PixQueryService#queryPerson(
	 * java.lang.String)
	 */
	@Override
	public PixManagerBean queryPerson(String mrn, String mrnDomain)
			throws Hl7v3TransformerException {
		logger.debug("Received request to PIXQuery");
		PixManagerBean pixMgrBean = new PixManagerBean();
		String xmlFilePath = hl7v3Transformer.getPixQueryXml(mrn, mrnDomain,
				Hl7v3Transformer.XMLPIXQUERYURI);

		PRPAIN201309UV02 request = new PRPAIN201309UV02();

		PRPAIN201310UV02 response = new PRPAIN201310UV02();
		// Delegate to webServiceTemplate for the actual pixadd
		try {

			request = requestXMLToJava.getPIXQueryReqObject(xmlFilePath,
					PixManagerConstants.ENCODE_STRING);

			response = pixManagerService.pixManagerPRPAIN201309UV02(request);
			// pixMgrBean = new PixManagerBean();
			getQueryMessage(response, pixMgrBean);

		} catch (JAXBException e) {
			getGeneralExpMessage(e, pixMgrBean, PixManagerConstants.PIX_QUERY);
		} catch (IOException e) {
			getGeneralExpMessage(e, pixMgrBean, PixManagerConstants.PIX_QUERY);
		}

		return pixMgrBean;
	}

	/**
	 * Gets the query message.
	 * 
	 * @param response
	 *            the response
	 * @param pixMgrBean
	 *            the pix mgr bean
	 * @return the query message
	 */
	@SuppressWarnings("unchecked")
	private void getQueryMessage(PRPAIN201310UV02 response,
			PixManagerBean pixMgrBean) {

		// The message has been sent
		// But it doesn't mean we're subscribed successfully
		logger.debug("response ack code:" + response.getAcceptAckCode());
		logger.debug("response type id: " + response.getTypeId());

		JXPathContext context = JXPathContext.newContext(response);
		Iterator<MCCIMT000300UV01Acknowledgement> ackmntList = context
				.iterate("/acknowledgement");

		while (ackmntList.hasNext()) {
			MCCIMT000300UV01Acknowledgement ackmnt = ackmntList.next();

			if (ackmnt.getTypeCode().getCode().equals("AA")) {
				StringBuffer queryMsg = new StringBuffer("Query Success! ");
				Map<String, String> idMap = new HashMap<String, String>();

				Iterator<PRPAMT201307UV02PatientIdentifier> pidList = context
						.iterate("/controlActProcess/queryByParameter/value/parameterList/patientIdentifier");
				while (pidList.hasNext()) {
					PRPAMT201307UV02PatientIdentifier pid = pidList.next();
					List<II> ptIdList = pid.getValue();

					for (II ptId : ptIdList) {
						queryMsg.append(" Given PID: " + ptId.getExtension());
						queryMsg.append(" Given UID: " + ptId.getRoot());
						queryMsg.append("\t");
					}

				}

				Iterator<II> ptIdList = context
						.iterate("/controlActProcess/subject[1]/registrationEvent/subject1[typeCode='SBJ']/patient[classCode='PAT']/id");

				while (ptIdList.hasNext()) {
					II pId = ptIdList.next();
					idMap.put(pId.getRoot(), pId.getExtension());
					// System.out.println("Requested UID:  " + pId.getRoot());
					// System.out.println("Requested PID:  " +
					// pId.getExtension());
					// queryMsg.append(" Requested PID: " + pId.getExtension());
					// queryMsg.append(" Requested UID: " + pId.getRoot());
					// queryMsg.append("\t");
				}

				pixMgrBean.setQueryMessage(queryMsg.toString());
				pixMgrBean.setQueryIdMap(idMap);
				break;
			} else if (ackmnt.getTypeCode().getCode().equals("AE")) {

				List<MCCIMT000300UV01AcknowledgementDetail> ackmntDetList = ackmnt
						.getAcknowledgementDetail();
				for (MCCIMT000300UV01AcknowledgementDetail ackDet : ackmntDetList) {
					logger.error("Query Failure! "
							+ ackDet.getText().getContent());
					pixMgrBean.setQueryMessage("Query Failure! "
							+ ackDet.getText().getContent());
					pixMgrBean.setQueryIdMap(null);
					break;
				}

			} else {
				pixMgrBean.setQueryMessage("Query Failure! ");
				pixMgrBean.setQueryIdMap(null);
			}
		}
	}

	/**
	 * Gets the general exp message.
	 * 
	 * @param e
	 *            the e
	 * @param pixMgrBean
	 *            the pix mgr bean
	 * @param msg
	 *            the msg
	 * @return the general exp message
	 */
	private void getGeneralExpMessage(Exception e, PixManagerBean pixMgrBean,
			String msg) {
		// Expect the unexpected
		logger.error("Unexpected exception", e);

		// Add error
		logger.error("error",
				"Query Failure! Server error! A unexpected error has occured");

		String errMsg = " Failure! Server error! A unexpected error has occured";
		logger.error("exception: " + e.getCause());
		logger.error("detail message: " + e.getMessage());

		if (PixManagerConstants.PIX_ADD.equalsIgnoreCase(msg)) {
			pixMgrBean.setAddMessage(msg + errMsg);
		} else if (PixManagerConstants.PIX_QUERY.equalsIgnoreCase(msg)) {
			pixMgrBean.setQueryMessage(msg + errMsg);
		} else if (PixManagerConstants.PIX_UPDATE.equalsIgnoreCase(msg)) {
			pixMgrBean.setUpdateMessage(msg + errMsg);
		}
	}
}
