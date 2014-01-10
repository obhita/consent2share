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
package gov.samhsa.consent2share.hl7;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hl7v3TransformerImpl implements Hl7v3Transformer {

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	// Set up transformer
	// Since Saxon is a dependency of this project, its
	// TransformerFactory will be picked
	private TransformerFactory transformerFactory = TransformerFactory
			.newInstance();

	@Override
	public String transformC32ToHl7v3PixXml(String c32xml, String XSLTURI)
			throws Hl7v3TransformerException {
		try {
			InputStream styleIs = Thread.currentThread()
					.getContextClassLoader().getResourceAsStream(XSLTURI);
			StreamSource styleSource = new StreamSource(styleIs);

			Transformer transformer = transformerFactory
					.newTransformer(styleSource);

			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

			// Get xml source
			StringReader xmlReader = new StringReader(c32xml);
			StreamSource xmlSource = new StreamSource(xmlReader);

			// Transform
			StringWriter writer = new StringWriter();
			StreamResult streamResult = new StreamResult(writer);
			transformer.transform(xmlSource, streamResult);

			String hl7v3PixXML = writer.toString();

			styleIs.close();
			xmlReader.close();
			writer.close();

			return hl7v3PixXML;

		} catch (Exception e) {

			String errorMessage = "Error happended when trying to transform C32 to hl7v3PixAdd";

			logger.error(errorMessage, e);

			Hl7v3TransformerException transformerException = new Hl7v3TransformerException(
					errorMessage, e);

			throw transformerException;
		}
	}

	public void setTransformerFactory(TransformerFactory transformerFactory) {
		this.transformerFactory = transformerFactory;
	}

	@Override
	public String getPixQueryXml(String mrn, String mrnDomain, String xsltUri)
			throws Hl7v3TransformerException {
		String newxsltUri = "";

		try {
			String extension = "@extension";
			String root = "@root";
			String queryXML = xsltUri;
			if (null != xsltUri && !xsltUri.startsWith("<?xml")) {
				// read the xsl file from resources folder
				InputStream styleIs = Thread.currentThread()
						.getContextClassLoader().getResourceAsStream(xsltUri);
				queryXML = IOUtils.toString(styleIs, "UTF-8");
			}

			newxsltUri = queryXML.replaceAll(extension, mrn);
			newxsltUri = newxsltUri.replaceAll(root, mrnDomain);
		} catch (Exception e) {

			String errorMessage = "Error happended when trying to mrn data to hl7v3PixQuery";

			logger.error(errorMessage, e);

			Hl7v3TransformerException transformerException = new Hl7v3TransformerException(
					errorMessage, e);

			throw transformerException;
		}

		return newxsltUri;
	}
}
