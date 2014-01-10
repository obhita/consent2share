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

public interface Hl7v3Transformer {
	

	/** The Constant XSLTURI. */
	public final static String XSLTPIXADDURI = "c32ToHl7v3Pixadd.xsl";
	public final static String XSLTPIXUPDATEURI = "c32ToHl7v3PixUpdate.xsl";
	public final static String XSLTPIXQUERYURI = "c32ToHl7v3PixQuery.xsl";	
	public final static String XMLPIXQUERYURI = "Hl7v3PixQuery.xml";
	/**
	 * Transform c32 to green ccd.
	 *
	 * @param c32xml the c32xml
	 * @return the string
	 * @throws C32ToGreenCcdTransformerException the c32 to green ccd transformer exception
	 */
	public String transformC32ToHl7v3PixXml(String c32xml, String XSLTURI)
			throws Hl7v3TransformerException;

	/**
	 * Transform c32 to green ccd.
	 *
	 * @param mrn the medical record no of patient
	 * @param mrnDomain the eHRdomain id
	 * @param xsltUri the xsl for pixquery
	 * @return the string
	 * @throws C32ToGreenCcdTransformerException the c32 to green ccd transformer exception
	 */	
	public String getPixQueryXml(String mrn, String mrnDomain, String xsltUri) throws Hl7v3TransformerException ;	
}
