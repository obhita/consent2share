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
package gov.samhsa.acs.documentsegmentation.valueset;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class SelfSignedSSLHelper.
 */
public class SelfSignedSSLHelper {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Instantiates a new self signed ssl helper.
	 */
	public SelfSignedSSLHelper() {
		logger.warn("SelfSignedSSLHelper should only be used in development environment. It shouldn't be used in actual production enviornment.");
	}

	/**
	 * Trust self signed ssl.
	 */
	public void trustSelfSignedSSL() {
		logger.warn("Trusting self signed SSL. This should only be done in development environment.");

		HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) {
				StringBuilder builder = new StringBuilder();
				builder.append("Warning: URL Host: ");
				builder.append(urlHostName);
				builder.append(" vs. ");
				builder.append(session.getPeerHost());
				logger.warn(builder.toString());
				return true;
			}
		};

		HttpsURLConnection.setDefaultHostnameVerifier(hv);

		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {

				@SuppressWarnings("unused")
				public void checkClientTrusted(X509Certificate[] xcs,
						String string) throws CertificateException {
				}

				@SuppressWarnings("unused")
				public void checkServerTrusted(X509Certificate[] xcs,
						String string) throws CertificateException {
				}

				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType)
						throws java.security.cert.CertificateException {
				}

				@Override
				public void checkServerTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType)
						throws java.security.cert.CertificateException {
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLContext.setDefault(ctx);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}
}
