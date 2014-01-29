package gov.samhsa.consent2share.showcase.web;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javax.xml.namespace.QName;

import gov.samhsa.acs.common.bean.XacmlResult;
import gov.samhsa.acs.common.dto.XacmlRequest;
import gov.samhsa.acs.common.dto.XacmlResponse;
import gov.samhsa.acs.pep.security.CredentialProvider;
import gov.samhsa.acs.pep.wsclient.WSClient;
import gov.samhsa.consent2share.c32.C32Parser;
import gov.samhsa.consent2share.c32.C32ParserException;
import gov.samhsa.consent2share.c32.dto.GreenCCD;
import gov.samhsa.consent2share.showcase.infrastructure.C32Getter;
import gov.samhsa.consent2share.showcase.service.EhrNumOneDto;
import gov.samhsa.consent2share.showcase.service.MailDto;
import gov.samhsa.consent2share.showcase.service.MailService;
import gov.samhsa.consent2share.showcase.service.MailServiceImpl;
import gov.samhsa.consent2share.showcase.service.PatientDto;
import gov.samhsa.consent2share.showcase.service.PixOperationsService;
import gov.samhsa.consent2share.showcase.service.ProviderDto;
import gov.samhsa.ds4ppilot.schema.pep.DirectEmailSendRequest;
import gov.samhsa.ds4ppilot.schema.pep.DirectEmailSendResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/pushC32")
public class PushC32Controller {

	@Autowired
	C32Getter c32Getter;

	@Autowired
	C32Parser c32Parser;

	MailService mailService;

	String endPointAddress = "http://localhost:8080/Pep/services/PepService";
	Properties props;
	String username;

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The pix operations service. */
	@Autowired
	PixOperationsService pixOperationsService;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	EhrNumOneDto get() {
		System.out.println("Loading PushC32");
		EhrNumOneDto dto = new EhrNumOneDto();

		return dto;
	}

	@RequestMapping(value = "/c32/{patientId}", method = RequestMethod.GET)
	public @ResponseBody
	String getC32(@PathVariable("patientId") String patientId) {
		String c32Xml = this.c32Getter.getC32(patientId);
		DirectEmailSendRequest directEmailSendRequest = new DirectEmailSendRequest();

		// set c32 to the request
		c32Xml = pixOperationsService.updatePatientAndAuthorId(c32Xml);
		directEmailSendRequest.setC32(c32Xml);

		mailService = new MailServiceImpl();
		WSClient client = new WSClient(endPointAddress);
		//WSClient client = new WSClient(endPointAddress, new CredentialProvider());

		DirectEmailSendResponse response = client
				.directEmailSend(directEmailSendRequest);

		System.out.println("Response: " + response.getPdpDecision());

		String errorMessage = "";
		if (response.getPdpDecision().equals("DENY")) {
			errorMessage = "Sending of the C32 is not allowed. Please check privacy policies for patient";
		} else if (response.getPdpDecision().equals("NO_POLICY")) {
			errorMessage = "Sending of the C32 is not allowed. There are no policies for patient";
		}

		// TODO: change not applicable to no policy
		if (response.getPdpDecision().equals("DENY")
				|| response.getPdpDecision().equals("NO_POLICY")) {
			mailService.sendMailOnFailure(new ProviderDto(
					"leo.smith@direct.obhita-stage.org"), new ProviderDto(),
					errorMessage);
		}

		return c32Xml;
	}

	public void setC32Getter(C32Getter c32Getter) {
		this.c32Getter = c32Getter;
	}

	public void setC32Parser(C32Parser c32Parser) {
		this.c32Parser = c32Parser;
	}

}
