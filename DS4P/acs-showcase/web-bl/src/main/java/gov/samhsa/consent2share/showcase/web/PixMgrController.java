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
package gov.samhsa.consent2share.showcase.web;

import gov.samhsa.consent2share.c32.C32ParserException;
import gov.samhsa.consent2share.hl7.Hl7v3TransformerException;
import gov.samhsa.consent2share.pixclient.util.PixManagerBean;
import gov.samhsa.consent2share.showcase.service.PixManagerTransformService;
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
@RequestMapping("/pixMgr")
public class PixMgrController {
	
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PixOperationsService pixOperationsService;
	
	@Autowired
	PixManagerTransformService pixManagerTransformService;
	
	@RequestMapping(value = "/tansformHl7Add", method = RequestMethod.POST)
	public @ResponseBody
	String  transformC32ToPixAdd(@RequestParam("c32Xml") String c32xml)
			throws C32ParserException, Hl7v3TransformerException {
		String pixAddXML = pixManagerTransformService.getPixAddXml(c32xml);
		return pixAddXML;
	}	
	@RequestMapping(value = "/tansformHl7Update", method = RequestMethod.POST)
	public @ResponseBody
	String  transformC32ToPixUpdate(@RequestParam("c32Xml") String c32xml)
			throws C32ParserException, Hl7v3TransformerException {
		String pixUpdateXML = pixManagerTransformService.getPixUpdateXml(c32xml);
		return pixUpdateXML;
	}
	
	@RequestMapping(value = "/tansformHl7Query", method = RequestMethod.POST)
	public @ResponseBody
	String  transformC32ToPixQuery(@RequestParam("c32Xml") String c32xml)
			throws C32ParserException, Hl7v3TransformerException {
		String pixQueryXML = pixManagerTransformService.getPixQueryXml(c32xml);
		return pixQueryXML;
	}	
	
	@RequestMapping(value = "/pixAdd", method = RequestMethod.POST)
	public @ResponseBody
	String  reqToPixAdd(@RequestParam("c32Xml") String c32xml)
			throws C32ParserException, Hl7v3TransformerException {
		
		String hl7v3Xml = pixManagerTransformService.getPixAddXml(c32xml);
		
		String pixAddMessage = pixOperationsService.addPerson(hl7v3Xml);
		return pixAddMessage;
	}	
	
	@RequestMapping(value = "/pixUpdate", method = RequestMethod.POST)
	public @ResponseBody
	String  reqToPixUpdate(@RequestParam("c32Xml") String c32xml)
			throws C32ParserException, Hl7v3TransformerException {
		String hl7v3Xml = pixManagerTransformService.getPixUpdateXml(c32xml);
		String pixUpdateMessage = pixOperationsService.updatePerson(hl7v3Xml);
		return pixUpdateMessage;
	}	
	
	@RequestMapping(value = "/pixQuery", method = RequestMethod.POST)
	public @ResponseBody
	PixManagerBean  reqToPixQuery(@RequestParam("c32Xml") String c32xml)
			throws C32ParserException, Hl7v3TransformerException {
		String hl7v3Xml = pixManagerTransformService.getPixQueryXml(c32xml);
		PixManagerBean pixManagerBean = pixOperationsService.queryPerson(hl7v3Xml);
		return pixManagerBean;
	}
	public void setPixOperationsService(PixOperationsService pixOperationsService) {
		this.pixOperationsService = pixOperationsService;
	}
	public void setPixManagerTransformService(
			PixManagerTransformService pixManagerTransformService) {
		this.pixManagerTransformService = pixManagerTransformService;
	}	
	
}
