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
package gov.samhsa.consent2share.pixclient.service;

import gov.samhsa.consent2share.pixclient.ws.MCCIIN000002UV01;
import gov.samhsa.consent2share.pixclient.ws.PIXManagerPortType;
import gov.samhsa.consent2share.pixclient.ws.PIXManagerService;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201301UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201302UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201304UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201309UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201310UV02;

import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value = "PixManagerService")
public class PixManagerServiceImpl implements PixManagerService {
	private String endpoint = null;
	private PIXManagerPortType port;

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	public PixManagerServiceImpl() {
		this(null);
	}

	public PixManagerServiceImpl(String endpoint) {
		this.endpoint = endpoint;
		PIXManagerService service = new PIXManagerService();
		port = service.getPIXManagerPortSoap12();
		if (this.endpoint != null)
			((BindingProvider) port).getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
	}

	public String getEndpoint() {
		return endpoint;
	}

	public PIXManagerPortType getPort() {
		return port;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.pixclient.client.PIXManager#
	 * pixManagerPRPAIN201301UV02
	 * (gov.samhsa.consent2share.pixclient.ws.PRPAIN201301UV02)
	 */
	@Override
	public MCCIIN000002UV01 pixManagerPRPAIN201301UV02(PRPAIN201301UV02 body) {
		return port.pixManagerPRPAIN201301UV02(body);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.pixclient.client.PIXManager#
	 * pixManagerPRPAIN201302UV02
	 * (gov.samhsa.consent2share.pixclient.ws.PRPAIN201302UV02)
	 */
	@Override
	public MCCIIN000002UV01 pixManagerPRPAIN201302UV02(PRPAIN201302UV02 body) {
		return port.pixManagerPRPAIN201302UV02(body);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.pixclient.client.PIXManager#
	 * pixManagerPRPAIN201304UV02
	 * (gov.samhsa.consent2share.pixclient.ws.PRPAIN201304UV02)
	 */
	@Override
	public MCCIIN000002UV01 pixManagerPRPAIN201304UV02(PRPAIN201304UV02 body) {
		return port.pixManagerPRPAIN201304UV02(body);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.pixclient.client.PIXManager#
	 * pixManagerPRPAIN201309UV02
	 * (gov.samhsa.consent2share.pixclient.ws.PRPAIN201309UV02)
	 */
	@Override
	public PRPAIN201310UV02 pixManagerPRPAIN201309UV02(PRPAIN201309UV02 body) {
		return port.pixManagerPRPAIN201309UV02(body);
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public void setPort(PIXManagerPortType port) {
		this.port = port;
	}

}
