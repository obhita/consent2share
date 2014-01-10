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
package gov.samhsa.acs.brms.guvnor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class GuvnorServiceImpl.
 */
public class GuvnorServiceImpl implements GuvnorService {

	/** The endpoint address. */
	private String endpointAddress;

	private String guvnorServiceUsername;

	private String guvnorServicePassword;

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GuvnorServiceImpl.class);

	/**
	 * Instantiates a new clinically adaptive rules implementation.
	 * 
	 * @param endpointAddress
	 *            the endpoint address
	 */
	public GuvnorServiceImpl(String endpointAddress,
			String guvnorServiceUsername, String guvnorServicePassword) {
		this.endpointAddress = endpointAddress;
		this.guvnorServiceUsername = guvnorServiceUsername;
		this.guvnorServicePassword = guvnorServicePassword;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.accesscontrolservices.brms.RuleExecutionService
	 * #getVersionedRulesFromPackage()
	 */
	@Override
	public String getVersionedRulesFromPackage() throws IOException {
		String source = null;

		URL url = new URL(endpointAddress);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", MediaType.TEXT_PLAIN);
		String passwordString = guvnorServiceUsername + ":" + guvnorServicePassword;
		connection.setRequestProperty(
				"Authorization",
				"Basic "
						+ new Base64().encodeToString((passwordString
								.getBytes())));
		connection.connect();

		source = readAsString(connection.getInputStream());
		LOGGER.debug("DRL source: " + source);

		return source;
	}

	/**
	 * Read as string.
	 * 
	 * @param inputStream
	 *            the input stream
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private String readAsString(InputStream inputStream) throws IOException {
		StringBuffer ret = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));
		String line;
		while ((line = reader.readLine()) != null) {
			ret.append(line + "\n");
		}
		return ret.toString();
	}

}
