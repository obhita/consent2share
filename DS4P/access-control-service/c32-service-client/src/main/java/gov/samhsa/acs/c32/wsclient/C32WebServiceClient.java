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
package gov.samhsa.acs.c32.wsclient;

import java.net.URL;
import java.util.Date;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import gov.samhsa.schemas.c32service.*;

/**
 * The Class C32WebServiceClient.
 */
public class C32WebServiceClient {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(C32WebServiceClient.class);
	
	/** The endpoint address. */
	private String endpointAddress;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		final String patientId = "PUI100010060001";
		/*
		 * if (args.length != 1) {
		 * System.out.println("Usage: C32Service &lt;patient id>");
		 * System.exit(-1); }
		 */

		C32WebServiceClient c32Service = new C32WebServiceClient(null);
		// String patientId = args[0];
		c32Service.run(patientId);
	}

	/**
	 * Instantiates a new c32 web service client.
	 *
	 * @param endpointAddress the endpoint address
	 */
	public C32WebServiceClient(String endpointAddress) {
		this.endpointAddress = endpointAddress;
	}

	/**
	 * Gets the c32.
	 *
	 * @param patientId the patient id
	 * @return the c32
	 */
	public String getC32(String patientId) {
		IC32Service port;
		if (StringUtils.hasText(this.endpointAddress))
		{
			port = createPort(endpointAddress);
		}
		else
		{
			// Using default endpoint address defined in the wsdl:port of wsdl file
			port = createPort();
		}
		
		return getC32(port, patientId);
	}

	/**
	 * Gets the c32.
	 *
	 * @param port the port
	 * @param patientId the patient id
	 * @return the c32
	 */
	private String getC32(IC32Service port, String patientId) {
		return port.getC32(patientId);
	}

	/**
	 * Creates the port.
	 *
	 * @return the i c32 service
	 */
	private IC32Service createPort() {
		final URL WSDL_LOCATION = this.getClass().getClassLoader().getResource("C32Service.wsdl");
		final QName SERVICE = new QName("http://schemas.samhsa.gov/c32service",
				"C32Service");

		IC32Service port = new C32Service(WSDL_LOCATION, SERVICE)
				.getBasicHttpBindingIC32Service();
		return port;
	}

	/**
	 * Creates the port.
	 *
	 * @param endpointAddress the endpoint address
	 * @return the i c32 service
	 */
	private IC32Service createPort(String endpointAddress) {
		IC32Service port = createPort();
		BindingProvider bp = (BindingProvider) port;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
				endpointAddress);
		return port;
	}

	/**
	 * Run.
	 *
	 * @param patientId the patient id
	 */
	private void run(String patientId) {
		try {
			// Add the certificate that is not accepted to your key store and
			// tell your Java/socket factory to use this "trust store"
			// System.setProperty( "javax.net.ssl.trustStore",
			// "C:\\DS4P\\jssecacerts" );
			// System.setProperty( "javax.net.ssl.trustStorePassword", "" );

			// Or turn off SSL check
			// XTrustProvider.install();

			LOGGER.debug("Creating C32 service instance ...");
			long start = new Date().getTime();
			// Get a reference to the SOAP service interface.
			IC32Service port = createPort();

			long end = new Date().getTime();
			LOGGER.debug("...Done! IC32Service instance: {}", port);
			LOGGER.debug(
					"Time required to initialize c32 service interface: {} seconds",
					(end - start) / 1000f);

			start = new Date().getTime();

			// String c32 = getC32("http://localhost/Rem.Web/C32Service.svc",
			// patientId);
			// String c32 =
			// getC32("http://taolinpc2.fei.local/Rem.Web/C32Service.svc",
			// patientId);
			String c32 = getC32(port, patientId);
			end = new Date().getTime();
			LOGGER.debug("Time required to invoke 'getC32': {} seconds",
					(end - start) / 1000f);

			System.out.print(c32);
			LOGGER.debug("");
			LOGGER.debug("Program complete, exiting");
		} catch (final Exception e) {
			LOGGER.error("An exception occurred, exiting", e);
		}
	}
}
