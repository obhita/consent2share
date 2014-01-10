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
package gov.samhsa.consent2share.c32;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * The Class C32ToGreenCcdTransformerImpl.
 */
public class C32ToGreenCcdTransformerImpl implements C32ToGreenCcdTransformer {

	/** The Constant XSLTURI. */
	private final static String XSLTURI = "C32ToGreenCcd/C32toGreenCCD.xslt";

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.c32.C32ToGreenCcdTransformer#TransformC32ToGreenCcd
	 * (java.lang.String)
	 */
	@Override
	public String TransformC32ToGreenCcd(String c32xml)
			throws C32ToGreenCcdTransformerException {
		Assert.hasText(c32xml);

		try {
			// Set up transformer
			// Since Saxon is a dependency of this project, its
			// TransformerFactory will be picked
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			transformerFactory.setURIResolver(new UriResolverImpl());

			InputStream styleIs = Thread.currentThread()
					.getContextClassLoader().getResourceAsStream(XSLTURI);
			StreamSource styleSource = new StreamSource(styleIs);

			Transformer transformer = transformerFactory
					.newTransformer(styleSource);

			/*
			 * // Transformer using DOMSource DocumentBuilderFactory dFactory =
			 * DocumentBuilderFactory.newInstance();
			 * dFactory.setNamespaceAware(true); DocumentBuilder dBuilder =
			 * dFactory.newDocumentBuilder(); InputSource xslInputSource = new
			 * InputSource(styleIs); Document xslDoc =
			 * dBuilder.parse(xslInputSource); DOMSource xslDomSource = new
			 * DOMSource(xslDoc); Transformer transformer =
			 * transformerFactory.newTransformer(xslDomSource);
			 */

			transformer.setOutputProperty(OutputKeys.INDENT, "no");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

			// Get xml source
			StringReader xmlReader = new StringReader(c32xml);
			StreamSource xmlSource = new StreamSource(xmlReader);

			// Transform
			StringWriter writer = new StringWriter();
			StreamResult streamResult = new StreamResult(writer);
			transformer.transform(xmlSource, streamResult);

			String greenCcd = writer.toString();

			styleIs.close();
			xmlReader.close();
			writer.close();

			return greenCcd;

		} catch (Exception e) {

			String errorMessage = "Error happended when trying to transform C32 to Green CCD";

			logger.error(errorMessage, e);

			C32ToGreenCcdTransformerException transformerException = new C32ToGreenCcdTransformerException(
					errorMessage, e);

			throw transformerException;
		}
	}
}
