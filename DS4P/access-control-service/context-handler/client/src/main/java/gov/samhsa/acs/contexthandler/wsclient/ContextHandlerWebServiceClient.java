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
package gov.samhsa.acs.contexthandler.wsclient;

import gov.va.ehtac.ds4p.ws.DS4PContextHandler;
import gov.va.ehtac.ds4p.ws.DS4PContextHandler_Service;
import gov.va.ehtac.ds4p.ws.EnforcePolicy;
import gov.va.ehtac.ds4p.ws.EnforcePolicyResponse.Return;

import java.net.URL;
import java.util.Date;
import java.util.UUID;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * The Class ContextHandlerWebServiceClient.
 */
public class ContextHandlerWebServiceClient {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ContextHandlerWebServiceClient.class);
	
	/** The endpoint address. */
	private String endpointAddress;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {	
		final EnforcePolicy.Xspasubject xspasubject = new EnforcePolicy.Xspasubject();
		final EnforcePolicy.Xsparesource xsparesource = new EnforcePolicy.Xsparesource();
		xspasubject.setSubjectPurposeOfUse("TREAT");
		xspasubject.setSubjectLocality("2.16.840.1.113883.3.467");
		xspasubject.setSubjectEmailAddress("leo.smith@direct.obhita-stage.org");
		xspasubject.setSubjectId("Duane_Decouteau@direct.healthvault.com");
		xspasubject.setOrganization("SAMHSA");
		xspasubject.setOrganizationId("FEiSystems");
		xspasubject.setMessageId(UUID.randomUUID().toString());
		
		xsparesource.setResourceId("PUI100010060001");
		xsparesource.setResourceName("NwHINDirectSend");
		xsparesource.setResourceType("C32");
		xsparesource.setResourceAction("Execute");	

		ContextHandlerWebServiceClient c32Service = new ContextHandlerWebServiceClient(null);		
		c32Service.run(xspasubject, xsparesource);
	}

	/**
	 * Instantiates a new context handler web service client.
	 *
	 * @param endpointAddress the endpoint address
	 */
	public ContextHandlerWebServiceClient(String endpointAddress) {
		this.endpointAddress = endpointAddress;
	}

	/**
	 * Enforce policy.
	 *
	 * @param xspasubject the XSPASUBJECT
	 * @param xsparesource the XSPARESOURCE
	 * @return the return
	 */
	public Return enforcePolicy(EnforcePolicy.Xspasubject xspasubject, EnforcePolicy.Xsparesource xsparesource) {
		DS4PContextHandler port;
		if (StringUtils.hasText(this.endpointAddress))
		{
			port = createPort(endpointAddress);
		}
		else
		{
			// Using default endpoint address defined in the wsdl:port of wsdl file
			port = createPort();
		}		
		return enforcePolicy(port, xspasubject, xsparesource);
	}

	/**
	 * Enforce policy.
	 *
	 * @param port the port
	 * @param xspasubject the XSPASUBJECT
	 * @param xsparesource the XSPARESOURCE
	 * @return the return
	 */
	private Return enforcePolicy(DS4PContextHandler port, EnforcePolicy.Xspasubject xspasubject, EnforcePolicy.Xsparesource xsparesource) {
		return port.enforcePolicy(xspasubject, xsparesource);
	}

	/**
	 * Creates the port.
	 *
	 * @return the d s4 p context handler
	 */
	private DS4PContextHandler createPort() {
		final URL WSDL_LOCATION = this.getClass().getClassLoader().getResource("DS4PContextHandler.wsdl");
		final QName SERVICE = new QName("http://ws.ds4p.ehtac.va.gov/",
				"DS4PContextHandler");

		DS4PContextHandler port = new DS4PContextHandler_Service(WSDL_LOCATION, SERVICE).getDS4PContextHandlerPort();
		return port;
	}

	/**
	 * Creates the port.
	 *
	 * @param endpointAddress the endpoint address
	 * @return the DS4P context handler
	 */
	private DS4PContextHandler createPort(String endpointAddress) {
		DS4PContextHandler port = createPort();
		BindingProvider bp = (BindingProvider) port;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				endpointAddress);
		return port;
	}

	/**
	 * Run.
	 *
	 * @param xspasubject the XSPASUBJECT
	 * @param xsparesource the XSPARESOURCE
	 */
	private void run(EnforcePolicy.Xspasubject xspasubject, EnforcePolicy.Xsparesource xsparesource) {
		try {			
			LOGGER.debug("Creating ContextHandler service instance ...");
			long start = new Date().getTime();
			
			// Get a reference to the SOAP service interface.
			DS4PContextHandler port = createPort();

			long end = new Date().getTime();
			LOGGER.debug("...Done! DS4PContextHandler instance: {}", port);
			LOGGER.debug(
					"Time required to initialize DS4PContextHandler service interface: {} seconds",
					(end - start) / 1000f);

			start = new Date().getTime();
			
			Return result = enforcePolicy(port, xspasubject, xsparesource);
			end = new Date().getTime();
			LOGGER.debug("Time required to invoke 'enforcePolicy': {} seconds",
					(end - start) / 1000f);

			System.out.print(result.getPdpDecision());
			LOGGER.debug("");
			LOGGER.debug("Program complete, exiting");
		} catch (final Exception e) {
			LOGGER.error("An exception occurred, exiting", e);
		}
	}
}
