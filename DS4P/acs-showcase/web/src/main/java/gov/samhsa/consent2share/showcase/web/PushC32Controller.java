package gov.samhsa.consent2share.showcase.web;

import gov.samhsa.acs.pep.wsclient.WSClient;
import gov.samhsa.consent2share.c32.C32Parser;
import gov.samhsa.consent2share.showcase.infrastructure.C32Getter;
import gov.samhsa.consent2share.showcase.service.EhrNumOneDto;
import gov.samhsa.consent2share.showcase.service.MailService;
import gov.samhsa.consent2share.showcase.service.MailServiceImpl;
import gov.samhsa.consent2share.showcase.service.PixOperationsService;
import gov.samhsa.consent2share.showcase.service.ProviderDto;
import gov.samhsa.ds4ppilot.schema.pep.DirectEmailSendRequest;
import gov.samhsa.ds4ppilot.schema.pep.DirectEmailSendResponse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

//	public static void main(String[] args) {
//		PushC32Controller controller = new PushC32Controller();
//		controller.setC32Getter(new C32GetterImpl(controller.endPointAddress));
//		controller.getRedactedC32("PUI100010060001");
//	}
	
	@RequestMapping(value = "/c32/{patientId}", method = RequestMethod.GET)
	public @ResponseBody
	String getC32(@PathVariable("patientId") String patientId) {
		String c32Xml = this.c32Getter.getC32(patientId);
		DirectEmailSendRequest directEmailSendRequest = new DirectEmailSendRequest();

		// set c32 to the request
		String segmentedC32Xml = pixOperationsService.updatePatientAndAuthorId(c32Xml);
		directEmailSendRequest.setC32(segmentedC32Xml);

		mailService = new MailServiceImpl();
		WSClient client = new WSClient(endPointAddress);
		// WSClient client = new WSClient(endPointAddress, new
		// CredentialProvider());

		DirectEmailSendResponse response = client
				.directEmailSend(directEmailSendRequest);

		System.out.println("Direct Email Send Response: "
				+ response.getPdpDecision());

		String errorMessage = "";
		if (response.getPdpDecision().equals("DENY")) {
			errorMessage = "Sending of the C32 is not allowed. Please check privacy policies for patient.";
		} else if (response.getPdpDecision().equals("NO_POLICY")) {
			errorMessage = "Sending of the C32 is not allowed. There are no policies for patient.";
		}

		String signature = "\n\nThank you,\nREM 1 Support";

		if (response.getPdpDecision().equals("DENY")
				|| response.getPdpDecision().equals("NO_POLICY")) {
			mailService.sendMailOnFailure(new ProviderDto(
					"leo.smith@direct.obhita-stage.org"), new ProviderDto(),
					errorMessage + signature);
			return "Failure! of C32!!";
		}

		// saveXML(c32Xml, segmentedC32Xml);
		
		return segmentedC32Xml;
	}

	public void saveXML(String c32Xml, String segmentedC32Xml) {
		File originalC32File;
		File segmentedC32File;
		FileOutputStream fop = null;
		try {
			 
			String content = "This is the content to write into file";
 
			originalC32File = new File("/c32_1.xml");
			segmentedC32File = new File("/segmentedc32_1.xml");
 
			// if file doesnt exists, then create it
			if (!originalC32File.exists()) {
				originalC32File.createNewFile();
			}
			
			if (!segmentedC32File.exists()) {
				segmentedC32File.createNewFile();
			}
 
			FileWriter fw = new FileWriter(originalC32File.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
 
			fw = new FileWriter(segmentedC32File.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setC32Getter(C32Getter c32Getter) {
		this.c32Getter = c32Getter;
	}

	public void setC32Parser(C32Parser c32Parser) {
		this.c32Parser = c32Parser;
	}

}
