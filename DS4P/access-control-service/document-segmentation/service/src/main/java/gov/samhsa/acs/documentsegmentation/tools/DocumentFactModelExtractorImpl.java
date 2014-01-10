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
package gov.samhsa.acs.documentsegmentation.tools;

import gov.samhsa.acs.common.exception.DS4PException;

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

/**
 * The Class DocumentFactModelExtractorImpl.
 */
public class DocumentFactModelExtractorImpl implements
		DocumentFactModelExtractor {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The Constant EXTRACT_CLINICAL_FACTS_XSL. */
	private static final String EXTRACT_CLINICAL_FACTS_XSL = "extractClinicalFacts.xsl";

	/** The Constant PARAM_XACML_RESULT. */
	private static final String PARAM_XACML_RESULT = "xacmlResult";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.documentsegmentation.tools
	 * .DocumentFactModelExtractor#extractFactModel(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public String extractFactModel(String document, String enforcementPolicies) {
		StringWriter writer = new StringWriter();
		InputStream in = null;
		String factModel = null;

		try {
			in = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(EXTRACT_CLINICAL_FACTS_XSL);

			StreamSource source = new StreamSource(new StringReader(document));
			StreamSource style = new StreamSource(in);

			String xacmlResult = enforcementPolicies;

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer t = factory.newTransformer(style);

			t.setParameter(PARAM_XACML_RESULT, xacmlResult);

			t.setOutputProperty(OutputKeys.INDENT, "no");
			t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			t.transform(source, new StreamResult(writer));

			factModel = writer.toString();

			factModel = factModel.replaceAll("&lt;", "<");
			factModel = factModel.replaceAll("&gt;", ">");

			in.close();
			writer.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DS4PException(e.toString(), e);
		}
		factModel = factModel.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", "");
		return factModel;
	}
}
