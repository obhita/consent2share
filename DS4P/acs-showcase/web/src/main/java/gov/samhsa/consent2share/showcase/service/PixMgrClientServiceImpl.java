package gov.samhsa.consent2share.showcase.service;

import gov.samhsa.consent2share.pixclient.client.PixMgrClientService;
import gov.samhsa.consent2share.pixclient.service.PixMgrService;
import gov.samhsa.consent2share.pixclient.service.PixMgrServiceImpl;
import gov.samhsa.consent2share.pixclient.util.PIXMgrBean;
import gov.samhsa.consent2share.pixclient.util.PixManagerConstants;
import gov.samhsa.consent2share.pixclient.util.RequestXMLToJava;
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
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201310UV02MFMIMT700711UV01ControlActProcess;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201310UV02MFMIMT700711UV01RegistrationEvent;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201310UV02MFMIMT700711UV01Subject1;
import gov.samhsa.consent2share.pixclient.ws.PRPAMT201307UV02ParameterList;
import gov.samhsa.consent2share.pixclient.ws.PRPAMT201307UV02PatientIdentifier;
import gov.samhsa.consent2share.pixclient.ws.PRPAMT201307UV02QueryByParameter;

import java.util.List;

import javax.xml.bind.JAXBElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ws.soap.client.SoapFaultClientException;

@Service
public class PixMgrClientServiceImpl implements PixMgrClientService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private RequestXMLToJava requestXMLToJava = new RequestXMLToJava();
	private PIXMgrBean pixMgrBean = new PIXMgrBean();
	private PixMgrService pixMgrService;

	/**
	 * Instantiates a new C32 getter implementation.
	 * 
	 * @param endpointAddress
	 *            the endpoint address
	 */
	@Autowired
	public PixMgrClientServiceImpl(
			@Value("${openEMPIWebServiceEndpointAddress}") String endpointAddress) {
		this.pixMgrService = new PixMgrServiceImpl(endpointAddress);
	}

	@Override
	public String addPerson(String reqXMLPath) {

		PRPAIN201301UV02 request = new PRPAIN201301UV02();

		MCCIIN000002UV01 response = new MCCIIN000002UV01();
		// Delegate to webServiceTemplate for the actual pixadd
		try {
			// xmlFilePath = "xml/PRPA_IN201301UV02_PIXADD_VD1_Req.xml";
			request = requestXMLToJava.getPIXAddReqObject(reqXMLPath);

			response = pixMgrService.pixManagerPRPAIN201301UV02(request);
			getAddUpdateessage(response, pixMgrBean,
					PixManagerConstants.PIX_ADD);

		} catch (SoapFaultClientException sfce) {

			getSoapExpMessage(sfce, pixMgrBean, PixManagerConstants.PIX_ADD);

		} catch (Exception e) {
			getGeneralExpMessage(e, pixMgrBean, PixManagerConstants.PIX_ADD);
		}

		return pixMgrBean.getAddMessage();
	}

	@Override
	public String updatePerson(String reqXMLPath) {
		logger.debug("Received request to PIXUpdate");

		PRPAIN201302UV02 request = new PRPAIN201302UV02();

		MCCIIN000002UV01 response = new MCCIIN000002UV01();
		// Delegate to webServiceTemplate for the actual pixadd
		try {

			request = requestXMLToJava.getPIXUpdateReqObject(reqXMLPath);

			response = pixMgrService.pixManagerPRPAIN201302UV02(request);

			getAddUpdateessage(response, pixMgrBean,
					PixManagerConstants.PIX_UPDATE);

		} catch (SoapFaultClientException sfce) {

			getSoapExpMessage(sfce, pixMgrBean, PixManagerConstants.PIX_UPDATE);

		} catch (Exception e) {
			getGeneralExpMessage(e, pixMgrBean, PixManagerConstants.PIX_UPDATE);
		}

		return pixMgrBean.getUpdateMessage();
	}

	@Override
	public String queryPerson(String xmlFilePath) {
		logger.debug("Received request to PIXQuery");

		PRPAIN201309UV02 request = new PRPAIN201309UV02();

		PRPAIN201310UV02 response = new PRPAIN201310UV02();
		// Delegate to webServiceTemplate for the actual pixadd
		try {

			request = requestXMLToJava.getPIXQueryReqObject(xmlFilePath);

			response = pixMgrService.pixManagerPRPAIN201309UV02(request);
			getQueryMessage(response, pixMgrBean);

		} catch (SoapFaultClientException sfce) {

			getSoapExpMessage(sfce, pixMgrBean, PixManagerConstants.PIX_QUERY);

		} catch (Exception e) {
			getGeneralExpMessage(e, pixMgrBean, PixManagerConstants.PIX_QUERY);
		}

		return pixMgrBean.getQueryMessage();
	}

	private void getAddUpdateessage(MCCIIN000002UV01 response,
			PIXMgrBean pixMgrBean, String msg) {
		// The message has been sent
		// But it doesn't mean we're subscribed successfully
		logger.debug("response ack code:" + response.getAcceptAckCode());
		logger.debug("response type id: " + response.getTypeId());
		List<MCCIMT000200UV01Acknowledgement> ackmntList = response
				.getAcknowledgement();
		for (MCCIMT000200UV01Acknowledgement ackmnt : ackmntList) {
			if (ackmnt.getTypeCode().getCode().equals("CA")) {
				if (PixManagerConstants.PIX_ADD.equalsIgnoreCase(msg)) {
					pixMgrBean.setAddMessage(msg + " Success! ");
				} else if (PixManagerConstants.PIX_UPDATE.equalsIgnoreCase(msg)) {
					pixMgrBean.setUpdateMessage(msg + " Success! ");
				}

				break;
			} else if (ackmnt.getTypeCode().getCode().equals("CE")) {
				List<MCCIMT000200UV01AcknowledgementDetail> ackmntDetList = ackmnt
						.getAcknowledgementDetail();
				for (MCCIMT000200UV01AcknowledgementDetail ackDet : ackmntDetList) {
					logger.error(msg + " error : "
							+ ackDet.getText().getContent());
					if (PixManagerConstants.PIX_ADD.equalsIgnoreCase(msg)) {
						pixMgrBean.setAddMessage(msg + " error : "
								+ ackDet.getText().getContent());
					} else if (PixManagerConstants.PIX_UPDATE
							.equalsIgnoreCase(msg)) {
						pixMgrBean.setUpdateMessage(msg + " error : "
								+ ackDet.getText().getContent());
					}
					break;
				}

			} else {

				if (PixManagerConstants.PIX_ADD.equalsIgnoreCase(msg)) {
					pixMgrBean.setAddMessage(msg + " Failure! ");
				} else if (PixManagerConstants.PIX_UPDATE.equalsIgnoreCase(msg)) {
					pixMgrBean.setUpdateMessage(msg + " Failure! ");
				}
			}
		}

	}

	private void getQueryMessage(PRPAIN201310UV02 response,
			PIXMgrBean pixMgrBean) {

		// The message has been sent
		// But it doesn't mean we're subscribed successfully
		logger.debug("response ack code:" + response.getAcceptAckCode());
		logger.debug("response type id: " + response.getTypeId());
		List<MCCIMT000300UV01Acknowledgement> ackmntList = response
				.getAcknowledgement();
		for (MCCIMT000300UV01Acknowledgement ackmnt : ackmntList) {
			if (ackmnt.getTypeCode().getCode().equals("AA")) {
				StringBuffer queryMsg = new StringBuffer("Query Success! ");
				// get the query ids
				PRPAIN201310UV02MFMIMT700711UV01ControlActProcess cap = response
						.getControlActProcess();
				JAXBElement<PRPAMT201307UV02QueryByParameter> qbpjaxb = cap
						.getQueryByParameter();
				PRPAMT201307UV02QueryByParameter qbp = qbpjaxb.getValue();
				PRPAMT201307UV02ParameterList plist = qbp.getParameterList();
				List<PRPAMT201307UV02PatientIdentifier> pidList = plist
						.getPatientIdentifier();
				for (PRPAMT201307UV02PatientIdentifier pid : pidList) {
					List<II> ptIdList = pid.getValue();
					for (II ptId : ptIdList) {
						queryMsg.append(" Given PID: " + ptId.getExtension());
						queryMsg.append(" Given UID: " + ptId.getRoot());
						queryMsg.append("\t");
					}
				}
				List<PRPAIN201310UV02MFMIMT700711UV01Subject1> subjList = cap
						.getSubject();
				for (PRPAIN201310UV02MFMIMT700711UV01Subject1 subj : subjList) {
					PRPAIN201310UV02MFMIMT700711UV01RegistrationEvent regEvt = subj
							.getRegistrationEvent();
					List<II> ptIdList = regEvt.getSubject1().getPatient()
							.getId();
					for (II pId : ptIdList) {
						System.out.println("Requested UID:  " + pId.getRoot());
						System.out.println("Requested PID:  "
								+ pId.getExtension());
						queryMsg.append(" Requested PID: " + pId.getExtension());
						queryMsg.append(" Requested UID: " + pId.getRoot());
						queryMsg.append("\t");
					}

				}

				pixMgrBean.setQueryMessage(queryMsg.toString());
				break;
			} else if (ackmnt.getTypeCode().getCode().equals("AE")) {
				List<MCCIMT000300UV01AcknowledgementDetail> ackmntDetList = ackmnt
						.getAcknowledgementDetail();
				for (MCCIMT000300UV01AcknowledgementDetail ackDet : ackmntDetList) {
					logger.error("Query error : "
							+ ackDet.getText().getContent());
					pixMgrBean.setQueryMessage("Query error : "
							+ ackDet.getText().getContent());
					break;
				}

			} else {
				pixMgrBean.setQueryMessage("Query Failure! ");
			}
		}

	}

	private void getSoapExpMessage(SoapFaultClientException sfce,
			PIXMgrBean pixMgrBean, String msg) {

		// This indicates there's something wrong with our message
		// For example a validation error
		logger.error("We sent an invalid message", sfce);

		// Add error
		logger.error(
				"error",
				msg
						+ " Failure! Validation error! Invalid Message: Request cannot be processed");

		if (PixManagerConstants.PIX_ADD.equalsIgnoreCase(msg)) {
			pixMgrBean
					.setAddMessage(msg
							+ " Failure! Validation error! Invalid Message: Request cannot be processed");
		} else if (PixManagerConstants.PIX_QUERY.equalsIgnoreCase(msg)) {
			pixMgrBean
					.setQueryMessage(msg
							+ " Failure! Validation error! Invalid Message: Request cannot be processed");
		} else if (PixManagerConstants.PIX_UPDATE.equalsIgnoreCase(msg)) {
			pixMgrBean
					.setUpdateMessage(msg
							+ " Failure! Validation error! Invalid Message: Request cannot be processed");
		}

	}

	private void getGeneralExpMessage(Exception e, PIXMgrBean pixMgrBean,
			String msg) {
		// Expect the unexpected
		logger.error("Unexpected exception", e);

		// Add error
		logger.error("error",
				"Query Failure! Server error! A unexpected error has occured");

		if (PixManagerConstants.PIX_ADD.equalsIgnoreCase(msg)) {
			pixMgrBean.setAddMessage(msg
					+ " Failure! Server error! A unexpected error has occured");
		} else if (PixManagerConstants.PIX_QUERY.equalsIgnoreCase(msg)) {
			pixMgrBean.setQueryMessage(msg
					+ " Failure! Server error! A unexpected error has occured");
		} else if (PixManagerConstants.PIX_UPDATE.equalsIgnoreCase(msg)) {
			pixMgrBean.setUpdateMessage(msg
					+ " Failure! Server error! A unexpected error has occured");
		}
	}

}
