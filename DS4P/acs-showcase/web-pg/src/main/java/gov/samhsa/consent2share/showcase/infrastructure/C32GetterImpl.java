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
package gov.samhsa.consent2share.showcase.infrastructure;

import gov.samhsa.acs.c32.wsclient.C32WebServiceClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The Class C32GetterImpl.
 */
@Component
public class C32GetterImpl implements C32Getter {

	/** The endpoint address. */
	private String endpointAddress;
	
	private C32WebServiceClient c32WebServiceClient;
	
	/**
	 * Instantiates a new C32 getter implementation.
	 *
	 * @param endpointAddress the endpoint address
	 */
	@Autowired
	public C32GetterImpl(@Value("${c32GetterWebServiceEndpointAddress}") String endpointAddress) {
		this.endpointAddress = endpointAddress;
		c32WebServiceClient = new C32WebServiceClient(this.endpointAddress);
	}
	
	/* (non-Javadoc)
	 * @see gov.samhsa.ds4ppilot.orchestrator.c32getter.C32Getter#getC32(java.lang.String)
	 */
	@Override
	public String getC32(String patientId) {
		
		String c32 = c32WebServiceClient.getC32(patientId);

		return c32;
	}

	public void setC32WebServiceClient(C32WebServiceClient c32WebServiceClient) {
		this.c32WebServiceClient = c32WebServiceClient;
	}
	
}
