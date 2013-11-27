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
package gov.samhsa.consent2share.pixclient.client;

import gov.samhsa.consent2share.pixclient.util.PixManagerBean;
import gov.samhsa.consent2share.pixclient.util.PixManagerConstants;
import gov.samhsa.consent2share.pixclient.util.PixManagerRequestXMLToJava;
import gov.samhsa.consent2share.pixclient.ws.II;
import gov.samhsa.consent2share.pixclient.ws.MCCIIN000002UV01;
import gov.samhsa.consent2share.pixclient.ws.MCCIMT000200UV01Acknowledgement;
import gov.samhsa.consent2share.pixclient.ws.MCCIMT000200UV01AcknowledgementDetail;
import gov.samhsa.consent2share.pixclient.ws.MCCIMT000300UV01Acknowledgement;
import gov.samhsa.consent2share.pixclient.ws.MCCIMT000300UV01AcknowledgementDetail;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201301UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201302UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201309UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201310UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAMT201307UV02PatientIdentifier;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import org.apache.commons.jxpath.JXPathContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.WebServiceTemplate;

//@Service(value = "PixManagerClientService")
public class PixManagerSWSClientServiceImpl implements PixManagerClientService {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "webServiceTemplate")
	private WebServiceTemplate webServiceTemplate;

	@Autowired
	PixManagerRequestXMLToJava requestXMLToJava;

	@Override
	public String addPerson(String reqXMLPath) {

		PixManagerBean pixMgrBean = new PixManagerBean();

		logger.debug("Received request to PIXADD");

		PRPAIN201301UV02 request = new PRPAIN201301UV02();

		MCCIIN000002UV01 response = new MCCIIN000002UV01();
		try {

			request = requestXMLToJava.getPIXAddReqObject(reqXMLPath,PixManagerConstants.ENCODE_STRING);

			response = (MCCIIN000002UV01) webServiceTemplate
					.marshalSendAndReceive(request);
			getAddUpdateessage(response, pixMgrBean,PixManagerConstants.PIX_ADD);

		} catch (JAXBException e) {
			getGeneralExpMessage(e, pixMgrBean, PixManagerConstants.PIX_ADD);
		}

		return pixMgrBean.getAddMessage();
	}

	@Override
	public String updatePerson(String reqXMLPath) {

		PixManagerBean pixMgrBean = new PixManagerBean();

		logger.debug("Received request to PIXUpdate");

		PRPAIN201302UV02 request = new PRPAIN201302UV02();

		MCCIIN000002UV01 response = new MCCIIN000002UV01();
		try {

			request = requestXMLToJava.getPIXUpdateReqObject(reqXMLPath, PixManagerConstants.ENCODE_STRING);

			response = (MCCIIN000002UV01) webServiceTemplate
					.marshalSendAndReceive(request);
			getAddUpdateessage(response,pixMgrBean,PixManagerConstants.PIX_UPDATE);

		} catch (JAXBException e) {
			getGeneralExpMessage(e, pixMgrBean, PixManagerConstants.PIX_UPDATE);
		}

		return pixMgrBean.getUpdateMessage();
	}

	@Override
	public PixManagerBean queryPerson(String reqXMLPath) {

		PixManagerBean pixMgrBean = new PixManagerBean();

		logger.debug("Received request to PIXQuery");

		PRPAIN201309UV02 request = new PRPAIN201309UV02();

		PRPAIN201310UV02 response = new PRPAIN201310UV02();
		try {

			request = requestXMLToJava.getPIXQueryReqObject(reqXMLPath,PixManagerConstants.ENCODE_STRING);

			response = (PRPAIN201310UV02) webServiceTemplate
					.marshalSendAndReceive(request);
			getQueryMessage(response, pixMgrBean);

		} catch (JAXBException e) {
			getGeneralExpMessage(e, pixMgrBean, PixManagerConstants.PIX_QUERY);
		}

		return pixMgrBean;
	}

	@SuppressWarnings("unchecked")
	protected void getQueryMessage(PRPAIN201310UV02 response,
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
					idMap.put(pId.getRoot(),pId.getExtension());
					//System.out.println("Requested UID:  " + pId.getRoot());
					//System.out.println("Requested PID:  " + pId.getExtension());
					//queryMsg.append(" Requested PID: " + pId.getExtension());
					//queryMsg.append(" Requested UID: " + pId.getRoot());
					//queryMsg.append("\t");
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

	protected void getAddUpdateessage(MCCIIN000002UV01 response, PixManagerBean pixMgrBean,String msg) {
		// The message has been sent
		// But it doesn't mean we're subscribed successfully
		logger.debug("response ack code:" + response.getAcceptAckCode());
		logger.debug("response type id: " + response.getTypeId());
		List<MCCIMT000200UV01Acknowledgement> ackmntList = response
				.getAcknowledgement();
		for (MCCIMT000200UV01Acknowledgement ackmnt : ackmntList) {
			if (ackmnt.getTypeCode().getCode().equals("CA")) {
				if(PixManagerConstants.PIX_ADD.equalsIgnoreCase(msg)) {
					pixMgrBean.setAddMessage(msg + " Success! ");
				} else if(PixManagerConstants.PIX_UPDATE.equalsIgnoreCase(msg)) {
					pixMgrBean.setUpdateMessage(msg + " Success! ");
				}
				
				break;
			} else if (ackmnt.getTypeCode().getCode().equals("CE")) {
				List<MCCIMT000200UV01AcknowledgementDetail> ackmntDetList = ackmnt
						.getAcknowledgementDetail();
				for (MCCIMT000200UV01AcknowledgementDetail ackDet : ackmntDetList) {
					logger.error(msg + " Failure! "
							+ ackDet.getText().getContent());
					if(PixManagerConstants.PIX_ADD.equalsIgnoreCase(msg)) {
						pixMgrBean.setAddMessage(msg + " Failure! "
								+ ackDet.getText().getContent());
					} else if(PixManagerConstants.PIX_UPDATE.equalsIgnoreCase(msg)) {
						pixMgrBean.setUpdateMessage(msg + " Failure! "
								+ ackDet.getText().getContent());
					}					
					break;
				}

			} else {
				
				if(PixManagerConstants.PIX_ADD.equalsIgnoreCase(msg)) {
					pixMgrBean.setAddMessage(msg + " Failure! ");
				} else if(PixManagerConstants.PIX_UPDATE.equalsIgnoreCase(msg)) {
					pixMgrBean.setUpdateMessage(msg + " Failure! ");
				}					
			}
		}

	}
	
	protected void getGeneralExpMessage(JAXBException e, PixManagerBean pixMgrBean,String msg){
		// Expect the unexpected
		logger.error("Unexpected exception", e);

		// Add error
		logger.error("error",
				"Query Failure! Server error! A unexpected error has occured");
		logger.error("error", e.getMessage() + e);	
		if(PixManagerConstants.PIX_ADD.equalsIgnoreCase(msg)) {
			pixMgrBean
			.setAddMessage(msg + " Failure! Server error! A unexpected error has occured");			
		} else if(PixManagerConstants.PIX_QUERY.equalsIgnoreCase(msg)) {
			pixMgrBean
			.setQueryMessage(msg + " Failure! Server error! A unexpected error has occured");			
		} else if(PixManagerConstants.PIX_UPDATE.equalsIgnoreCase(msg)) {
			pixMgrBean
			.setUpdateMessage(msg +" Failure! Server error! A unexpected error has occured");			
		}		
	}
	


}
