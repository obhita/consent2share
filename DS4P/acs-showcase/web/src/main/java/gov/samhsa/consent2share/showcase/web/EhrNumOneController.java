package gov.samhsa.consent2share.showcase.web;

import gov.samhsa.consent2share.c32.C32Parser;
import gov.samhsa.consent2share.c32.C32ParserException;
import gov.samhsa.consent2share.c32.dto.GreenCCD;
import gov.samhsa.consent2share.showcase.infrastructure.C32Getter;
import gov.samhsa.consent2share.showcase.service.EhrNumOneDto;
import gov.samhsa.consent2share.showcase.service.PatientDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ehrNumOne")
public class EhrNumOneController {

	@Autowired
	C32Getter c32Getter;

	@Autowired
	C32Parser c32Parser;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	EhrNumOneDto get() {
		EhrNumOneDto dto = new EhrNumOneDto();
		return dto;
	}

	@RequestMapping(value = "/c32/{patientId}", method = RequestMethod.GET)
	public @ResponseBody
	String getC32(@PathVariable("patientId") String patientId) {
		String c32Xml = this.c32Getter.getC32(patientId);
		return c32Xml;
	}

	@RequestMapping(value = "/parseC32", method = RequestMethod.POST)
	public @ResponseBody
	GreenCCD parseC32(@RequestParam("c32Xml") String c32xml)
			throws C32ParserException {
		GreenCCD greenCcd = c32Parser.ParseC32(c32xml);
		return greenCcd;
	}
}
