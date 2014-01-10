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
package gov.samhsa.consent2share.showcase.service;

import gov.samhsa.acs.common.tool.SimpleMarshaller;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.consent2share.hl7.Hl7v3TransformerException;
import gov.samhsa.consent2share.pixclient.service.PixManagerService;
import gov.samhsa.consent2share.pixclient.util.PixManagerBean;
import gov.samhsa.consent2share.pixclient.util.PixManagerConstants;
import gov.samhsa.consent2share.pixclient.util.PixManagerRequestXMLToJava;
import gov.samhsa.consent2share.showcase.exception.AcsShowCaseException;
import gov.samhsa.consent2share.showcase.infrastructure.PixOperation;
import gov.samhsa.consent2share.showcase.infrastructure.XdsbRegistryGetter;
import gov.samhsa.consent2share.showcase.infrastructure.XdsbRepositoryService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.xpath.XPathExpressionException;

import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

import org.apache.commons.jxpath.JXPathContext;
import org.hl7.v3.types.II;
import org.hl7.v3.types.MCCIIN000002UV01;
import org.hl7.v3.types.MCCIMT000200UV01Acknowledgement;
import org.hl7.v3.types.MCCIMT000200UV01AcknowledgementDetail;
import org.hl7.v3.types.MCCIMT000300UV01Acknowledgement;
import org.hl7.v3.types.MCCIMT000300UV01AcknowledgementDetail;
import org.hl7.v3.types.PRPAIN201301UV02;
import org.hl7.v3.types.PRPAIN201302UV02;
import org.hl7.v3.types.PRPAIN201309UV02;
import org.hl7.v3.types.PRPAIN201310UV02;
import org.hl7.v3.types.PRPAMT201307UV02PatientIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The Class PixOperationsServiceImpl.
 */
@Service
public class PixOperationsServiceImpl implements PixOperationsService {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The request xml to java. */
	@Autowired
	private PixManagerRequestXMLToJava requestXMLToJava;

	/** The pix mgr bean. */
	private PixManagerBean pixMgrBean = new PixManagerBean();

	/** The pix mgr service. */
	@Autowired
	private PixManagerService pixMgrService;

	/** The marshaller. */
	@Autowired
	private SimpleMarshaller marshaller;

	/** The pix manager transform service. */
	@Autowired
	PixManagerTransformService pixManagerTransformService;

	/** The xdsb registry getter. */
	@Autowired
	XdsbRegistryGetter xdsbRegistryGetter;

	/** The xdsb repository. */
	@Autowired
	private XdsbRepositoryService xdsbRepository;

	/** The xml attribute setter. */
	@Autowired
	private XmlAttributeSetter xmlAttributeSetter;

	/** The intermediary subject npi. */
	@Value("${intermediarySubjectNPI}")
	private String intermediarySubjectNPI;

	/** The assigning authority name. */
	@Value("${assigningAuthorityName}")
	private String assigningAuthorityName;

	/** The assigning authority oid. */
	@Value("${assigningAuthorityOID}")
	private String assigningAuthorityOID;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.pixclient.client.PixManagerClientService#addPerson
	 * (java.lang.String)
	 */
	@Override
	public String addPerson(String reqXMLPath) {

		// Convert c32 to pixadd string

		PRPAIN201301UV02 request = new PRPAIN201301UV02();

		MCCIIN000002UV01 response = new MCCIIN000002UV01();
		// Delegate to webServiceTemplate for the actual pixadd
		try {
			// xmlFilePath = "xml/PRPA_IN201301UV02_PIXADD_VD1_Req.xml";
			request = requestXMLToJava.getPIXAddReqObject(reqXMLPath,
					PixManagerConstants.ENCODE_STRING);

			response = pixMgrService.pixManagerPRPAIN201301UV02(request);
			getAddUpdateMessage(response, pixMgrBean,
					PixManagerConstants.PIX_ADD);

		} catch (JAXBException e) {
			getGeneralExpMessage(e, pixMgrBean, PixManagerConstants.PIX_ADD);
		} catch (IOException e) {
			getGeneralExpMessage(e, pixMgrBean, PixManagerConstants.PIX_ADD);
		}
		return pixMgrBean.getAddMessage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.pixclient.client.PixManagerClientService#
	 * updatePerson(java.lang.String)
	 */
	@Override
	public String updatePerson(String reqXMLPath) {
		logger.debug("Received request to PIXUpdate");

		PRPAIN201302UV02 request = new PRPAIN201302UV02();

		MCCIIN000002UV01 response = new MCCIIN000002UV01();
		// Delegate to webServiceTemplate for the actual pixadd
		try {

			request = requestXMLToJava.getPIXUpdateReqObject(reqXMLPath,
					PixManagerConstants.ENCODE_STRING);

			response = pixMgrService.pixManagerPRPAIN201302UV02(request);

			getAddUpdateMessage(response, pixMgrBean,
					PixManagerConstants.PIX_UPDATE);

		} catch (JAXBException e) {
			getGeneralExpMessage(e, pixMgrBean, PixManagerConstants.PIX_UPDATE);
		} catch (IOException e) {
			getGeneralExpMessage(e, pixMgrBean, PixManagerConstants.PIX_UPDATE);
		}

		return pixMgrBean.getUpdateMessage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.pixclient.client.PixManagerClientService#queryPerson
	 * (java.lang.String)
	 */
	@Override
	public PixManagerBean queryPerson(String xmlFilePath) {
		logger.debug("Received request to PIXQuery");

		PRPAIN201309UV02 request = new PRPAIN201309UV02();

		PRPAIN201310UV02 response = new PRPAIN201310UV02();
		// Delegate to webServiceTemplate for the actual pixadd
		try {

			request = requestXMLToJava.getPIXQueryReqObject(xmlFilePath,
					PixManagerConstants.ENCODE_STRING);

			response = pixMgrService.pixManagerPRPAIN201309UV02(request);
			pixMgrBean = new PixManagerBean();
			getQueryMessage(response, pixMgrBean);

		} catch (JAXBException e) {
			getGeneralExpMessage(e, pixMgrBean, PixManagerConstants.PIX_QUERY);
		} catch (IOException e) {
			getGeneralExpMessage(e, pixMgrBean, PixManagerConstants.PIX_QUERY);
		}

		return pixMgrBean;
	}

	/**
	 * Gets the adds the update message.
	 * 
	 * @param response
	 *            the response
	 * @param pixMgrBean
	 *            the pix mgr bean
	 * @param msg
	 *            the msg
	 * @return the adds the update message
	 */
	protected void getAddUpdateMessage(MCCIIN000002UV01 response,
			PixManagerBean pixMgrBean, String msg) {
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
						pixMgrBean.setAddMessage(msg + " Failure! "
								+ ackDet.getText().getContent());
					} else if (PixManagerConstants.PIX_UPDATE
							.equalsIgnoreCase(msg)) {
						pixMgrBean.setUpdateMessage(msg + " Failure! "
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
	protected void getGeneralExpMessage(Exception e, PixManagerBean pixMgrBean,
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.showcase.service.PixOperationsService#
	 * addPatientRegistryRecord(java.lang.String)
	 */
	@Override
	public MCCIIN000002UV01 addPatientRegistryRecord(String c32xml) {
		PixOperation pixOperation = PixOperation.ADD;
		return pidFeed(pixOperation, c32xml);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.showcase.service.PixOperationsService#
	 * revisePatientRegistryRecord(java.lang.String)
	 */
	@Override
	public MCCIIN000002UV01 revisePatientRegistryRecord(String c32xml) {
		PixOperation pixOperation = PixOperation.UPDATE;
		return pidFeed(pixOperation, c32xml);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.pixclient.client.PixManagerClientService#
	 * provideAndRegisterClinicalDocument(java.lang.String)
	 */
	@Override
	public RegistryResponse provideAndRegisterClinicalDocument(String c32xml) {
		String hl7v3Xml = null;
		try {
			hl7v3Xml = pixManagerTransformService.getPixQueryXml(c32xml);
		} catch (Hl7v3TransformerException e) {
			logger.error(e.getMessage(), e);
			throw new AcsShowCaseException(
					"PixOperationsService is failed! Cannot get PIX Query XML from PIX Manager Transform Service!");
		}
		PixManagerBean pixManagerBean = queryPerson(hl7v3Xml);
		Map<String, String> queryMap = pixManagerBean.getQueryIdMap();
		String eid = null;
		if (null != queryMap) {
			// get Eid Values
			eid = queryMap.get(PixManagerConstants.GLOBAL_DOMAIN_ID);
			String xPathExpr = "//hl7:recordTarget/child::hl7:patientRole/child::hl7:id";
			try {
				c32xml = xmlAttributeSetter.setAttributeValue(c32xml,
						xPathExpr, "extension", eid);
				c32xml = xmlAttributeSetter
						.setAttributeValue(c32xml, xPathExpr, "root",
								PixManagerConstants.GLOBAL_DOMAIN_ID);
				c32xml = xmlAttributeSetter.setAttributeValue(c32xml,
						xPathExpr, "assigningAuthorityName",
						PixManagerConstants.GLOBAL_DOMAIN_NAME);

				// TODO: These 4 lines below is added to manually insert author
				// id (intermediarySubjectNPI), because it is not populated by
				// REM.
				String authorIdXpath = "//hl7:author/child::hl7:assignedAuthor/child::hl7:id";
				c32xml = xmlAttributeSetter
						.setAttributeValue(c32xml, authorIdXpath, "extension",
								this.intermediarySubjectNPI);
				c32xml = xmlAttributeSetter.setAttributeValue(c32xml,
						authorIdXpath, "root", this.assigningAuthorityOID);
				c32xml = xmlAttributeSetter.setAttributeValue(c32xml,
						authorIdXpath, "assigningAuthorityName",
						this.assigningAuthorityName);

			} catch (XPathExpressionException e) {
				logger.error(e.getMessage(), e);
				throw new AcsShowCaseException(
						"PixOperationsService failed! Cannot run the XPath Expression!");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new AcsShowCaseException(
						"PixOperationsService failed! Unexpected problem occured!");
			}
		} else {
			logger.error("Error! queryMap is null, eid cannot be retrieved!");
			throw new AcsShowCaseException(
					"PixOperationsService failed! Cannot retrieve EID.");
		}
		return xdsbRepository.provideAndRegisterDocumentSet(c32xml,
				PixManagerConstants.GLOBAL_DOMAIN_ID,
				XdsbDocumentType.CLINICAL_DOCUMENT);
	}

	/**
	 * Sets the pix manager transform service.
	 * 
	 * @param pixManagerTransformService
	 *            the new pix manager transform service
	 */
	public void setPixManagerTransformService(
			PixManagerTransformService pixManagerTransformService) {
		this.pixManagerTransformService = pixManagerTransformService;
	}

	/**
	 * Sets the xdsb registry getter.
	 * 
	 * @param xdsbRegistryGetter
	 *            the new xdsb registry getter
	 */
	public void setXdsbRegistryGetter(XdsbRegistryGetter xdsbRegistryGetter) {
		this.xdsbRegistryGetter = xdsbRegistryGetter;
	}

	/**
	 * Sets the request xml to java.
	 * 
	 * @param requestXMLToJava
	 *            the new request xml to java
	 */
	public void setRequestXMLToJava(PixManagerRequestXMLToJava requestXMLToJava) {
		this.requestXMLToJava = requestXMLToJava;
	}

	/**
	 * Sets the pix mgr service.
	 * 
	 * @param pixMgrService
	 *            the new pix mgr service
	 */
	public void setPixMgrService(PixManagerService pixMgrService) {
		this.pixMgrService = pixMgrService;
	}

	/**
	 * Gets the xdsb repository.
	 * 
	 * @return the xdsb repository
	 */
	public XdsbRepositoryService getXdsbRepository() {
		return xdsbRepository;
	}

	/**
	 * Sets the xdsb repository.
	 * 
	 * @param xdsbRepository
	 *            the new xdsb repository
	 */
	public void setXdsbRepository(XdsbRepositoryService xdsbRepository) {
		this.xdsbRepository = xdsbRepository;
	}

	/**
	 * Gets the xml attribute setter.
	 * 
	 * @return the xml attribute setter
	 */
	public XmlAttributeSetter getXmlAttributeSetter() {
		return xmlAttributeSetter;
	}

	/**
	 * Sets the xml attribute setter.
	 * 
	 * @param xmlAttributeSetter
	 *            the new xml attribute setter
	 */
	public void setXmlAttributeSetter(XmlAttributeSetter xmlAttributeSetter) {
		this.xmlAttributeSetter = xmlAttributeSetter;
	}

	/**
	 * Gets the marshaller.
	 * 
	 * @return the marshaller
	 */
	public SimpleMarshaller getMarshaller() {
		return marshaller;
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
	 * Gets the pix mgr bean.
	 * 
	 * @return the pix mgr bean
	 */
	PixManagerBean getPixMgrBean() {
		return pixMgrBean;
	}

	/**
	 * Sets the pix mgr bean.
	 * 
	 * @param pixMgrBean
	 *            the new pix mgr bean
	 */
	void setPixMgrBean(PixManagerBean pixMgrBean) {
		this.pixMgrBean = pixMgrBean;
	}

	private MCCIIN000002UV01 pidFeed(PixOperation pixOperation, String c32xml) {
		MCCIIN000002UV01 response = null;

		PixOperation pixQuery = PixOperation.QUERY;
		String msg = null;
		try {
			String hl7v3Xml = pixManagerTransformService.getPixXml(pixQuery,
					c32xml);
			PixManagerBean pixManagerBean = queryPerson(hl7v3Xml);
			Map<String, String> queryMap = pixManagerBean.getQueryIdMap();

			if (null != queryMap) {
				// person already available in EMPI get global identifier
				// id(EID)
				// get piadd hl7v3 xml
				hl7v3Xml = pixManagerTransformService.getPixXml(pixOperation,
						c32xml);
				// get Eid Values
				String eid = queryMap.get(PixManagerConstants.GLOBAL_DOMAIN_ID);
				msg = xdsbRegistryGetter.pidFeed(pixOperation, hl7v3Xml, eid,
						PixManagerConstants.GLOBAL_DOMAIN_ID);
				response = marshaller.unmarshallFromXml(MCCIIN000002UV01.class,
						msg);

			}
		} catch (Throwable e) {
			// Expect the unexpected
			logger.error("Unexpected exception", e);
			// Add error
			logger.error("error",
					"Query Failure! Server error! A unexpected error has occured");
			throw new AcsShowCaseException(e.toString(), e);
		}

		return response;
	}
}
