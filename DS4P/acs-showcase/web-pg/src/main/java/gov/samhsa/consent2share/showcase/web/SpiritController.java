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

import gov.samhsa.spirit.wsclient.adapter.SpiritAdapter;
import gov.samhsa.spirit.wsclient.exception.SpiritAdapterException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The Class SpiritController.
 */
@Controller
@RequestMapping("/spirit")
public class SpiritController {

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SpiritAdapter spirit;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	String homeSpiritPolRepo() {
		return "Please enter the policy id to retrieve...";
	}

	@RequestMapping(value = "/polRepo/{polId}", method = RequestMethod.GET)
	public @ResponseBody
	String spiritPolRepo(@PathVariable("polId") String policyId) {
		policyId = policyId.replace("{dot}", ".");
		try {
			String x = new String(spirit.retrievePolicy(policyId));
			logger.debug(x);
			return x;
		} catch (SpiritAdapterException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("The policy cannot be found or the connection to the Spirit Web Services is failed!");			
		}
	}
}
