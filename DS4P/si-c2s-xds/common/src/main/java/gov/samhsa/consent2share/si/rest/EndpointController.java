package gov.samhsa.consent2share.si.rest;

import gov.samhsa.consent2share.si.EndpointStarter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/endpoint")
public class EndpointController {
	@Autowired
	@Qualifier("inboundChannelAdapterEndpointStarter")
	private EndpointStarter inboundChannelAdapterEndpointStarter;
	
	@RequestMapping(value = "/start", method = RequestMethod.PUT)
	public @ResponseBody
	void start() {
		inboundChannelAdapterEndpointStarter.start();
	}
}
