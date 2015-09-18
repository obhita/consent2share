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

import gov.samhsa.acs.common.tool.XmlTransformer;

import java.util.Optional;

import javax.xml.transform.URIResolver;

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

	/** The xml transformer. */
	private final XmlTransformer xmlTransformer;

	/**
	 * Instantiates a new c32 to green ccd transformer impl.
	 *
	 * @param xmlTransformer
	 *            the xml transformer
	 */
	public C32ToGreenCcdTransformerImpl(XmlTransformer xmlTransformer) {
		super();
		this.xmlTransformer = xmlTransformer;
	}

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
			final String xslUrl = Thread.currentThread()
					.getContextClassLoader().getResource(XSLTURI).toString();
			final Optional<URIResolver> uriResolver = Optional
					.of(new UriResolverImpl());
			final String greenCcd = xmlTransformer.transform(c32xml, xslUrl,
					Optional.empty(), uriResolver);
			logger.debug("greenCcd:");
			logger.debug(greenCcd);
			return greenCcd;
		} catch (final Exception e) {
			final String errorMessage = "Error happended when trying to transform C32 to Green CCD";
			logger.error(errorMessage, e);
			final C32ToGreenCcdTransformerException transformerException = new C32ToGreenCcdTransformerException(
					errorMessage, e);
			throw transformerException;
		}
	}
}
