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
import gov.samhsa.acs.common.util.StringURIResolver;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class MetadataGeneratorImpl.
 */
public class MetadataGeneratorImpl implements MetadataGenerator {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.acs.documentsegmentation.util
	 * .MetadataGenerator#generateMetadataXml(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String generateMetadataXml(String document,
			String executionResponseContainer, String homeCommunityId,
			String senderEmailAddress, String recipientEmailAddress) {
		StringWriter out = new StringWriter();
		InputStream in = null;
		String metadataXml = null;

		try {

			in = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("metadata.xsl");

			// add namespace execution response container for transformation
			executionResponseContainer = executionResponseContainer.replace(
					"<ruleExecutionContainer>",
					"<ruleExecutionContainer xmlns=\"urn:hl7-org:v3\">");

			StreamSource style = new StreamSource(in);

			TransformerFactory factory = TransformerFactory.newInstance();
			Templates template = factory.newTemplates(style);

			Transformer t = template.newTransformer();
			t.setURIResolver(new StringURIResolver().put(
					"ruleExecutionResponseContainer",
					executionResponseContainer));
			t.setParameter("homeCommunityId", homeCommunityId);
			t.setParameter("authorTelecommunication", senderEmailAddress);
			t.setParameter("intendedRecipient", recipientEmailAddress);

			t.transform(new StreamSource(new StringReader(document)),
					new StreamResult(out));

			metadataXml = out.toString();

			out.close();
			in.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DS4PException(e.toString(), e);
		}

		return metadataXml;
	}
}
