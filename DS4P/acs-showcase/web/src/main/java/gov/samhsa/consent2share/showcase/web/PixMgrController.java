package gov.samhsa.consent2share.showcase.web;

import gov.samhsa.consent2share.showcase.service.PixMgrClientServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/pixMgr")
public class PixMgrController {
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PixMgrClientServiceImpl pixMgrClientServiceImpl;
	
	
	@RequestMapping(value = "/pixAdd", method = RequestMethod.GET)
	public String pixAddReq(@RequestParam String xmlFilePath,RedirectAttributes redirectAttributes) {
		
		return pixMgrClientServiceImpl.addPerson(xmlFilePath);		
	}	

}
