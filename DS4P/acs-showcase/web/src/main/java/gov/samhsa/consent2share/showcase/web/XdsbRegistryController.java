package gov.samhsa.consent2share.showcase.web;

import gov.samhsa.consent2share.showcase.service.PixOperationsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/xdsb")
public class XdsbRegistryController {

	
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PixOperationsService pixOperationsService;

	
	@RequestMapping(value = "/regAdd", method = RequestMethod.POST)
	public @ResponseBody
	String  reqToXdsbRegistryAdd(@RequestParam("c32Xml") String c32xml){
		
		return  pixOperationsService.addPatientRegistryRecord(c32xml);
	}


	public void setPixOperationsService(PixOperationsService pixOperationsService) {
		this.pixOperationsService = pixOperationsService;
	}	
	
/*	
	@RequestMapping(value = "/xdsbRevise", method = RequestMethod.POST)
	public @ResponseBody
	String  reqToXDSBUpdate(@RequestParam("c32Xml") String c32xml)
			throws C32ParserException, Hl7v3TransformerException {
		
		return  xdsbRegistryService.revisePatientRegistryRecord(c32xml);
	}	
		*/
		
	
}
