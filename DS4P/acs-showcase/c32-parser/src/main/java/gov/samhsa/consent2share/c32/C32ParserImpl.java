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

import gov.samhsa.consent2share.c32.dto.GreenCCD;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * The Class C32ParserImpl.
 */
public class C32ParserImpl implements C32Parser {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The c32 to green ccd transformer. */
	private C32ToGreenCcdTransformer c32ToGreenCcdTransformer;

	/** The green ccd serializer. */
	private GreenCcdSerializer greenCcdSerializer;

	/**
	 * Instantiates a new c32 parser impl.
	 */
	public C32ParserImpl() {
	}

	/**
	 * Instantiates a new c32 parser impl.
	 * 
	 * @param c32ToGreenCcdTransformer
	 *            the c32 to green ccd transformer
	 * @param greenCcdSerializer
	 *            the green ccd serializer
	 */
	public C32ParserImpl(C32ToGreenCcdTransformer c32ToGreenCcdTransformer,
			GreenCcdSerializer greenCcdSerializer) {
		Assert.notNull(c32ToGreenCcdTransformer);
		Assert.notNull(greenCcdSerializer);

		this.c32ToGreenCcdTransformer = c32ToGreenCcdTransformer;
		this.greenCcdSerializer = greenCcdSerializer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.c32.C32Parser#ParseC32(java.lang.String)
	 */
	@Override
	public GreenCCD ParseC32(String c32xml) throws C32ParserException {
		Assert.hasText(c32xml);

		try {
			String greenCcdXml = c32ToGreenCcdTransformer
					.TransformC32ToGreenCcd(c32xml);
			GreenCCD greenCcdDto = greenCcdSerializer.Deserialize(greenCcdXml);

			return greenCcdDto;

		} catch (Exception ex) {
			String errorMessage = "Error occured when trying to parse C32";
			logger.error(errorMessage, ex);
			throw new C32ParserException(errorMessage, ex);
		}
	}
}
