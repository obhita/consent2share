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

import gov.samhsa.consent2share.showcase.service.PixOperationsService;

import oasis.names.tc.ebxml_regrep.xsd.rs._3.RegistryResponse;

import org.hl7.v3.types.MCCIIN000002UV01;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The Class XdsbRegistryController.
 */
@Controller
@RequestMapping("/xdsb")
public class XdsbRegistryController {

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The pix operations service. */
	@Autowired
	PixOperationsService pixOperationsService;

	/**
	 * Req to xdsb registry add.
	 * 
	 * @param c32xml
	 *            the c32xml
	 * @return the mCCII n000002 u v01
	 */
	@RequestMapping(value = "/regAdd", method = RequestMethod.POST)
	public @ResponseBody
	MCCIIN000002UV01 reqToXdsbRegistryAdd(@RequestParam("c32Xml") String c32xml) {

		return pixOperationsService.addPatientRegistryRecord(c32xml);
	}

	/**
	 * Req to xdsb registry update.
	 * 
	 * @param c32xml
	 *            the c32xml
	 * @return the mCCII n000002 u v01
	 */
	@RequestMapping(value = "/regUpdate", method = RequestMethod.POST)
	public @ResponseBody
	MCCIIN000002UV01 reqToXdsbRegistryUpdate(
			@RequestParam("c32Xml") String c32xml) {

		return pixOperationsService.revisePatientRegistryRecord(c32xml);
	}

	/**
	 * Provide and register clinical document.
	 * 
	 * @param c32xml
	 *            the c32xml
	 * @return the registry response
	 */
	@RequestMapping(value = "/repProvide", method = RequestMethod.POST)
	public @ResponseBody
	RegistryResponse provideAndRegisterClinicalDocument(
			@RequestParam("c32Xml") String c32xml) {
		return pixOperationsService.provideAndRegisterClinicalDocument(c32xml);
	}

	/**
	 * Sets the pix operations service.
	 * 
	 * @param pixOperationsService
	 *            the new pix operations service
	 */
	public void setPixOperationsService(
			PixOperationsService pixOperationsService) {
		this.pixOperationsService = pixOperationsService;
	}
}
