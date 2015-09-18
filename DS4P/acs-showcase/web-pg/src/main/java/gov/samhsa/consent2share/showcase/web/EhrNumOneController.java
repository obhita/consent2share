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
package gov.samhsa.consent2share.showcase.web;

import gov.samhsa.consent2share.c32.C32Parser;
import gov.samhsa.consent2share.c32.C32ParserException;
import gov.samhsa.consent2share.c32.dto.GreenCCD;
import gov.samhsa.consent2share.showcase.infrastructure.C32Getter;
import gov.samhsa.consent2share.showcase.service.EhrNumOneDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

	public void setC32Getter(C32Getter c32Getter) {
		this.c32Getter = c32Getter;
	}

	public void setC32Parser(C32Parser c32Parser) {
		this.c32Parser = c32Parser;
	}
	
	
}
