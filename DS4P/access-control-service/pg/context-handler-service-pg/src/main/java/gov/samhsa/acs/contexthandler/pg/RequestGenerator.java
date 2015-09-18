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
package gov.samhsa.acs.contexthandler.pg;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.herasaf.xacml.core.SyntaxException;
import org.herasaf.xacml.core.api.PDP;
import org.herasaf.xacml.core.api.PolicyRetrievalPoint;
import org.herasaf.xacml.core.api.UnorderedPolicyRepository;
import org.herasaf.xacml.core.context.RequestMarshaller;
import org.herasaf.xacml.core.context.impl.RequestType;
import org.herasaf.xacml.core.context.impl.ResponseType;
import org.herasaf.xacml.core.policy.Evaluatable;
import org.herasaf.xacml.core.policy.PolicyMarshaller;
import org.herasaf.xacml.core.simplePDP.SimplePDPFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class RequestGenerator.
 */
public class RequestGenerator {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RequestGenerator.class);

	/**
	 * Generate request.
	 *
	 * @param recepientSubjectNPI
	 *            the recepient subject npi
	 * @param intermediarySubjectNPI
	 *            the intermediary subject npi
	 * @param purposeOfUse
	 *            the purpose of use
	 * @return the request type
	 */
	public RequestType generateRequest(String recepientSubjectNPI,
			String intermediarySubjectNPI, String purposeOfUse) {
		RequestType requestType = null;

		final String request = generateRequestString(recepientSubjectNPI,
				intermediarySubjectNPI, purposeOfUse);
		final InputStream is = new ByteArrayInputStream(request.getBytes());
		try {
			// Need call SimplePDPFactory.getSimplePDP() to use
			// RequestMarshaller from herasaf
			requestType = unmarshalRequest(is);
		} catch (final SyntaxException e) {
			LOGGER.debug(e.toString(), e);
		}
		return requestType;
	}

	/**
	 * Generate request string.
	 *
	 * @param recepientSubjectNPI
	 *            the recepient subject npi
	 * @param intermediarySubjectNPI
	 *            the intermediary subject npi
	 * @param purposeOfUse
	 *            the purpose of use
	 * @return the string
	 */
	public String generateRequestString(String recepientSubjectNPI,
			String intermediarySubjectNPI, String purposeOfUse) {

		final String date = getDate();

		final StringBuilder requestStringBuilder = new StringBuilder();
		requestStringBuilder
				.append(" <xacml-context:Request xmlns:xacml-context=\"urn:oasis:names:tc:xacml:2.0:context:schema:os\">");
		requestStringBuilder
				.append(" <xacml-context:Subject SubjectCategory=\"urn:oasis:names:tc:xacml:1.0:subject-category:access-subject\">");
		requestStringBuilder
				.append(" <xacml-context:Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">");
		requestStringBuilder
				.append("                                <xacml-context:AttributeValue>"
						+ intermediarySubjectNPI
						+ "</xacml-context:AttributeValue>");
		requestStringBuilder
				.append("                </xacml-context:Attribute>");
		requestStringBuilder
				.append("                <xacml-context:Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">");
		requestStringBuilder
				.append("                                <xacml-context:AttributeValue>"
						+ recepientSubjectNPI
						+ "</xacml-context:AttributeValue>");
		requestStringBuilder
				.append("                </xacml-context:Attribute>");
		requestStringBuilder
				.append("                <xacml-context:Attribute AttributeId=\"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">");
		requestStringBuilder
				.append("                                <xacml-context:AttributeValue>"
						+ purposeOfUse + "</xacml-context:AttributeValue>");
		requestStringBuilder
				.append("                </xacml-context:Attribute>");
		/*
		 * requestStringBuilder.append(
		 * "                <xacml-context:Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">"
		 * ); requestStringBuilder.append(
		 * "                                <xacml-context:AttributeValue>" +
		 * patientId + "</xacml-context:AttributeValue>");
		 * requestStringBuilder.append
		 * ("                </xacml-context:Attribute>");
		 */requestStringBuilder.append("</xacml-context:Subject>");
		requestStringBuilder
				.append("<Resource xmlns=\"urn:oasis:names:tc:xacml:2.0:context:schema:os\">");
		requestStringBuilder
				.append("                <Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:typeCode\" DataType=\"http://www.w3.org/2001/XMLSchema#string\" XdsId=\"urn:uuid:f0306f51-975f-434e-a61c-c59651d33983\">");
		requestStringBuilder
				.append("                                <AttributeValue>34133-9</AttributeValue>");
		requestStringBuilder.append("                </Attribute>");
		requestStringBuilder
				.append("                <Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:practiceSettingCode\" DataType=\"http://www.w3.org/2001/XMLSchema#string\" XdsId=\"urn:uuid:cccf5598-8b07-4b77-a05e-ae952c785ead\">");
		requestStringBuilder
				.append("                                <AttributeValue>Psychiatry</AttributeValue>");
		requestStringBuilder.append("                </Attribute>");
		requestStringBuilder
				.append("                <Attribute AttributeId=\"xacml:status\" DataType=\"http://www.w3.org/2001/XMLSchema#string\" XdsId=\"status\">");
		requestStringBuilder
				.append("                                <AttributeValue>urn:oasis:names:tc:ebxml-regrep:StatusType:Approved</AttributeValue>");
		requestStringBuilder.append("                </Attribute>");
		requestStringBuilder.append(" </Resource>");
		requestStringBuilder.append("<xacml-context:Action>");
		requestStringBuilder
				.append("                <xacml-context:Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">");
		requestStringBuilder
				.append("                                <xacml-context:AttributeValue>xdsquery</xacml-context:AttributeValue>");
		requestStringBuilder
				.append("                </xacml-context:Attribute>");
		requestStringBuilder.append("</xacml-context:Action>");
		requestStringBuilder.append("<xacml-context:Environment>");
		requestStringBuilder
				.append("                <xacml-context:Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">");
		requestStringBuilder
				.append("                                <xacml-context:AttributeValue>"
						+ date + "</xacml-context:AttributeValue>");
		requestStringBuilder
				.append("                </xacml-context:Attribute>");
		requestStringBuilder.append("</xacml-context:Environment>");
		requestStringBuilder.append("</xacml-context:Request>");

		/*
		 * 
		 * 
		 * 
		 * requestStringBuilder.append(
		 * "<Request xmlns=\"urn:oasis:names:tc:xacml:2.0:context:schema:os\"     "
		 * ); requestStringBuilder.append(
		 * "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">     <Subject>      "
		 * ); requestStringBuilder.append(
		 * "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject\"       "
		 * ); requestStringBuilder.append(
		 * "DataType=\"http://www.w3.org/2001/XMLSchema#string\">       ");
		 * requestStringBuilder.append("<AttributeValue>");
		 * requestStringBuilder.append(recepientSubjectNPI);
		 * requestStringBuilder
		 * .append("</AttributeValue>      </Attribute>      ");
		 * requestStringBuilder.append(
		 * "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject\"       "
		 * ); requestStringBuilder.append(
		 * "DataType=\"http://www.w3.org/2001/XMLSchema#string\">       <AttributeValue>"
		 * ); requestStringBuilder.append(intermediarySubjectNPI);
		 * requestStringBuilder.append("</AttributeValue>      ");
		 * requestStringBuilder.append(
		 * "</Attribute>	  <Attribute AttributeId=\"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse\"       "
		 * ); requestStringBuilder.append(
		 * "DataType=\"http://www.w3.org/2001/XMLSchema#string\">       <AttributeValue>"
		 * ); requestStringBuilder.append(purposeOfUse);
		 * requestStringBuilder.append("</AttributeValue>      ");
		 * requestStringBuilder
		 * .append("</Attribute>     </Subject>     <Resource>      ");
		 * requestStringBuilder.append(
		 * "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\"       "
		 * ); requestStringBuilder.append(
		 * "DataType=\"http://www.w3.org/2001/XMLSchema#string\">       ");
		 * requestStringBuilder.append("<AttributeValue>");
		 * requestStringBuilder.append(patientId); requestStringBuilder.append(
		 * "</AttributeValue>      </Attribute>     </Resource>     ");
		 * requestStringBuilder.append(
		 * "<Action>      <Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\"       "
		 * ); requestStringBuilder.append(
		 * "DataType=\"http://www.w3.org/2001/XMLSchema#string\">       <AttributeValue>xdsquery</AttributeValue>      "
		 * ); requestStringBuilder.append(
		 * "</Attribute>     </Action>     <Environment>		");
		 * requestStringBuilder.append(
		 * "<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\"       "
		 * ); requestStringBuilder.append(
		 * "DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">       ");
		 * requestStringBuilder.append("<AttributeValue>");
		 * requestStringBuilder.append(date); requestStringBuilder.append(
		 * "</AttributeValue>      </Attribute>	 </Environment>    </Request>");
		 */

		return requestStringBuilder.toString();
	}

	/**
	 * Gets the current date.
	 *
	 * @return the date
	 */
	public String getDate() {
		final SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ssZ");
		return sdf.format(new Date());
	}

	/**
	 * Unmarshal request.
	 *
	 * @param inputStream
	 *            the input stream
	 * @return the request type
	 * @throws SyntaxException
	 *             the syntax exception
	 */
	RequestType unmarshalRequest(InputStream inputStream)
			throws SyntaxException {
		return RequestMarshaller.unmarshal(inputStream);
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 * @throws SyntaxException
	 *             the syntax exception
	 */
	public static void main(String[] args) throws UnsupportedEncodingException,
			SyntaxException {
		final PDP pdp = SimplePDPFactory.getSimplePDP();
		// String req = generateRequestString("1285969170", "0000000000",
		// "TREATMENT", "FAM.14007808943143");
		final String req = "<xacml-context:Request xmlns:xacml-context=\"urn:oasis:names:tc:xacml:2.0:context:schema:os\"><xacml-context:Subject SubjectCategory=\"urn:oasis:names:tc:xacml:1.0:subject-category:access-subject\"><xacml-context:Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject:subject-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"><xacml-context:AttributeValue>Mie Physician</xacml-context:AttributeValue></xacml-context:Attribute><xacml-context:Attribute AttributeId=\"urn:oasis:names:tc:xacml:2.0:subject:role\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"><xacml-context:AttributeValue>Administrator</xacml-context:AttributeValue></xacml-context:Attribute><xacml-context:Attribute AttributeId=\"urn:oasis:names:tc:xspa:1.0:subject:organization\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"><xacml-context:AttributeValue>MIE</xacml-context:AttributeValue></xacml-context:Attribute><xacml-context:Attribute AttributeId=\"urn:oasis:names:tc:xspa:1.0:subject:organization-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"><xacml-context:AttributeValue>2.16.840.1.113883.3.704.1.100.102</xacml-context:AttributeValue></xacml-context:Attribute><xacml-context:Attribute AttributeId=\"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"><xacml-context:AttributeValue>TREATMENT</xacml-context:AttributeValue></xacml-context:Attribute><xacml-context:Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"><xacml-context:AttributeValue>2222222222</xacml-context:AttributeValue></xacml-context:Attribute><xacml-context:Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"><xacml-context:AttributeValue>1427467752</xacml-context:AttributeValue></xacml-context:Attribute></xacml-context:Subject><Resource xmlns=\"urn:oasis:names:tc:xacml:2.0:context:schema:os\"><Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:typeCode\" DataType=\"http://www.w3.org/2001/XMLSchema#string\" XdsId=\"urn:uuid:f0306f51-975f-434e-a61c-c59651d33983\"><AttributeValue>34133-9</AttributeValue></Attribute><Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:practiceSettingCode\" DataType=\"http://www.w3.org/2001/XMLSchema#string\" XdsId=\"urn:uuid:cccf5598-8b07-4b77-a05e-ae952c785ead\"><AttributeValue>Home</AttributeValue></Attribute><Attribute AttributeId=\"xacml:status\" DataType=\"http://www.w3.org/2001/XMLSchema#string\" XdsId=\"status\"><AttributeValue>urn:oasis:names:tc:ebxml-regrep:StatusType:Approved</AttributeValue></Attribute></Resource><xacml-context:Action><xacml-context:Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"><xacml-context:AttributeValue>xdsquery</xacml-context:AttributeValue></xacml-context:Attribute></xacml-context:Action><xacml-context:Environment><xacml-context:Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"><xacml-context:AttributeValue>2014-08-18T18:45:12.633Z</xacml-context:AttributeValue></xacml-context:Attribute></xacml-context:Environment></xacml-context:Request>";
		System.out.println(req);
		final RequestType request = RequestMarshaller
				.unmarshal(new ByteArrayInputStream(req.getBytes("UTF8")));
		// String xacml =
		// "<Policy PolicyId=\"FAM.14007808943143:1285969170:1568797520:4915213a-b003-4c6e-9f4c-25bc62ca5b9f\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:permit-overrides\" xmlns=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\"><Description>This is a reference policy for consent2share@outlook.com</Description><Target/><Rule Effect=\"Permit\" RuleId=\"primary-group-rule\"><Target><Resources><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-regexp-match\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">34133-9</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:typeCode\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-regexp-match\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">urn:oasis:names:tc:ebxml-regrep:StatusType:Approved</AttributeValue><ResourceAttributeDesignator AttributeId=\"xacml:status\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource>                                                      </Resources>                                                    <Actions><Action><ActionMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">xdsquery</AttributeValue><ActionAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ActionMatch></Action><Action><ActionMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">xdsretrieve</AttributeValue><ActionAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ActionMatch></Action>                                                                            </Actions>                                          </Target><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:and\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\" MustBePresent=\"false\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">FAM.14007808943143</AttributeValue></Apply></Apply>                                                             <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\" MustBePresent=\"false\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1568797520</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\" MustBePresent=\"false\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1285969170</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator AttributeId=\"gov.samhsa.consent2share.purpose-of-use-code\" DataType=\"http://www.w3.org/2001/XMLSchema#string\" MustBePresent=\"false\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">TREATMENT</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-greater-than-or-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\"><EnvironmentAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\" MustBePresent=\"false\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2014-07-14T00:00:00-0400</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-less-than-or-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\"><EnvironmentAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\" MustBePresent=\"false\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2015-07-13T23:59:59-0400</AttributeValue></Apply></Apply></Condition></Rule><Rule Effect=\"Deny\" RuleId=\"deny-others\"/><Obligations><Obligation FulfillOn=\"Permit\" ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">HIV</AttributeAssignment></Obligation><Obligation FulfillOn=\"Permit\" ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">GDIS</AttributeAssignment></Obligation><Obligation FulfillOn=\"Permit\" ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">ETH</AttributeAssignment></Obligation></Obligations></Policy>";
		final String xacml = " <Policy PolicyId=\"REG.1DVRUZMRCA:&amp;2.16.840.1.113883.3.704.100.990.1&amp;ISO:2222222222:1427467752:092d4e2a-3508-4be5-bea6-6c3cdcf085bc\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:permit-overrides\" xmlns=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\"><Description>This is a reference policy for	consent2share@outlook.com</Description><Target/><Rule Effect=\"Permit\" RuleId=\"primary-group-rule\"><Target><Resources><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">34133-9</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:typeCode\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">urn:oasis:names:tc:ebxml-regrep:StatusType:Approved</AttributeValue><ResourceAttributeDesignator AttributeId=\"xacml:status\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource></Resources><Actions><Action><ActionMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">xdsquery</AttributeValue><ActionAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ActionMatch></Action><Action><ActionMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">xdsretrieve</AttributeValue><ActionAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ActionMatch></Action></Actions></Target><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:and\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\" MustBePresent=\"false\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1427467752</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\" MustBePresent=\"false\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">2222222222</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse\" DataType=\"http://www.w3.org/2001/XMLSchema#string\" MustBePresent=\"false\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">PAYMENT</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse\" DataType=\"http://www.w3.org/2001/XMLSchema#string\" MustBePresent=\"false\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">RESEARCH</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator AttributeId=\"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse\" DataType=\"http://www.w3.org/2001/XMLSchema#string\" MustBePresent=\"false\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">TREATMENT</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-greater-than-or-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\"><EnvironmentAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\" MustBePresent=\"false\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2014-08-13T00:00:00-0400</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-less-than-or-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\"><EnvironmentAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\" MustBePresent=\"false\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2015-08-12T23:59:59-0400</AttributeValue></Apply></Apply></Condition></Rule><Rule Effect=\"Deny\" RuleId=\"deny-others\"/><Obligations><Obligation FulfillOn=\"Permit\" ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">PSY</AttributeAssignment></Obligation><Obligation FulfillOn=\"Permit\" ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">ALC</AttributeAssignment></Obligation><Obligation FulfillOn=\"Permit\" ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">GDIS</AttributeAssignment></Obligation><Obligation FulfillOn=\"Permit\" ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">COM</AttributeAssignment></Obligation><Obligation FulfillOn=\"Permit\" ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">SEX</AttributeAssignment></Obligation><Obligation FulfillOn=\"Permit\" ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">ETH</AttributeAssignment></Obligation><Obligation FulfillOn=\"Permit\" ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">ADD</AttributeAssignment></Obligation></Obligations></Policy>";
		// String xacml =
		// "<Policy PolicyId=\"FAM.14007808943143:1285969170:1568797520:4915213a-b003-4c6e-9f4c-25bc62ca5b9f\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:permit-overrides\" xmlns=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\"><Description>This is a reference policy for consent2share@outlook.com</Description><Target/><Rule Effect=\"Permit\" RuleId=\"primary-group-rule\"><Target><Resources><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-regexp-match\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">34133-9</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:typeCode\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-regexp-match\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">urn:oasis:names:tc:ebxml-regrep:StatusType:Approved</AttributeValue><ResourceAttributeDesignator AttributeId=\"xacml:status\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource>                                                      </Resources>                                                    <Actions><Action><ActionMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">xdsquery</AttributeValue><ActionAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ActionMatch></Action><Action><ActionMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">xdsretrieve</AttributeValue><ActionAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ActionMatch></Action>                                                                            </Actions>                                          </Target><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:and\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\" >1568797520</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\" >1285969170</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\" >TREATMENT</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-greater-than-or-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\"><EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\" >2014-07-14T00:00:00-0400</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-less-than-or-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\"><EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\" >2015-07-13T23:59:59-0400</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\" >FAM.14007808943143</AttributeValue></Apply></Apply></Apply></Condition></Rule><Rule Effect=\"Deny\" RuleId=\"deny-others\"/><Obligations><Obligation FulfillOn=\"Permit\" ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">HIV</AttributeAssignment></Obligation><Obligation FulfillOn=\"Permit\" ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">GDIS</AttributeAssignment></Obligation><Obligation FulfillOn=\"Permit\" ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">ETH</AttributeAssignment></Obligation></Obligations></Policy>";
		final Evaluatable e = PolicyMarshaller
				.unmarshal(new ByteArrayInputStream(xacml.getBytes("UTF8")));

		final PolicyRetrievalPoint repo = pdp.getPolicyRepository();
		final UnorderedPolicyRepository repository = (UnorderedPolicyRepository) repo;
		repository.deploy(Arrays.asList(e));

		final ResponseType resp = pdp.evaluate(request);
		System.out.println(resp.getResults().get(0).getDecision().toString());
	}
}
