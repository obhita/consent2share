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
package gov.samhsa.acs.pep.trypolicy.wsclient;

import gov.samhsa.acs.pep.ws.contract.TryPolicyPortType;
import gov.samhsa.acs.pep.ws.contract.TryPolicyService;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.springframework.util.StringUtils;

/**
 * The Class TryPolicyWebServiceClient.
 */
public class TryPolicyWebServiceClient {
	
	/** The endpoint address. */
	private String endpointAddress;

	/**
	 * Instantiates a new try policy web service client.
	 *
	 * @param endpointAddress the endpoint address
	 */
	public TryPolicyWebServiceClient(String endpointAddress) {
		this.endpointAddress = endpointAddress;
	}


	/**
	 * Try policy.
	 *
	 * @param c32Xml the c32 xml
	 * @param xacmlPolicy the xacml policy
	 * @return the string
	 */
	public String tryPolicy(String c32Xml, String xacmlPolicy) {
		TryPolicyPortType port;
		if (StringUtils.hasText(this.endpointAddress))
		{
			port = createPort(endpointAddress);
		}
		else
		{
			// Using default endpoint address defined in the wsdl:port of wsdl file
			port = createPort();
		}
		
		return tryPolicy(port, c32Xml, xacmlPolicy);
	}


	/**
	 * Try policy.
	 *
	 * @param port the port
	 * @param c32Xml the c32 xml
	 * @param xacmlPolicy the xacml policy
	 * @return the string
	 */
	private String tryPolicy(TryPolicyPortType port, String c32Xml, String xacmlPolicy) {
		return port.tryPolicy(c32Xml, xacmlPolicy);
	}


	/**
	 * Creates the port.
	 *
	 * @return the try policy port type
	 */
	private TryPolicyPortType createPort() {
		final URL WSDL_LOCATION = this.getClass().getClassLoader().getResource("TryPolicy.wsdl");
		final QName SERVICE = new QName("http://acs.samhsa.gov/pep/ws/contract", "TryPolicyService");

		TryPolicyPortType port = new TryPolicyService(WSDL_LOCATION, SERVICE).getTryPolicyServicePort();
		
		return port;
	}


	/**
	 * Creates the port.
	 *
	 * @param endpointAddress the endpoint address
	 * @return the try policy port type
	 */
	private TryPolicyPortType createPort(String endpointAddress) {
		TryPolicyPortType port = createPort();
		BindingProvider bp = (BindingProvider) port;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				endpointAddress);
		return port;
	}
}
