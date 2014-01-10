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
package gov.samhsa.acs.contexthandler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.herasaf.xacml.core.SyntaxException;
import org.herasaf.xacml.core.context.RequestMarshaller;
import org.herasaf.xacml.core.context.impl.RequestType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class RequestGenerator.
 */
public class RequestGenerator {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestGenerator.class);
	
	/**
	 * Generate request.
	 *
	 * @param recepientSubjectNPI the recepient subject npi
	 * @param intermediarySubjectNPI the intermediary subject npi
	 * @param purposeOfUse the purpose of use
	 * @param patientId the patient id
	 * @return the request type
	 */
	public RequestType generateRequest(String recepientSubjectNPI, String intermediarySubjectNPI, String purposeOfUse, String patientId){
		RequestType requestType=null;
		
		String request=generateRequestString (recepientSubjectNPI, intermediarySubjectNPI, purposeOfUse, patientId);
		InputStream is =new ByteArrayInputStream(request.getBytes());
		try {
			// Need call SimplePDPFactory.getSimplePDP() to use RequestMarshaller from herasaf
			requestType=unmarshalRequest(is);
		} catch (SyntaxException e) {
			LOGGER.debug(e.toString(),e);
		}
		return requestType;
	}
	
	RequestType unmarshalRequest(InputStream inputStream) throws SyntaxException{
		return RequestMarshaller.unmarshal(inputStream);
	}
	
	public String generateRequestString(String recepientSubjectNPI, String intermediarySubjectNPI, String purposeOfUse, String patientId){

		String date = getDate();
		
		String request="<Request xmlns=\"urn:oasis:names:tc:xacml:2.0:context:schema:os\"     " +
				"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">     <Subject>      " +
				"<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject\"       " +
				"DataType=\"http://www.w3.org/2001/XMLSchema#string\">       " +
				"<AttributeValue>"+recepientSubjectNPI+"</AttributeValue>      </Attribute>      " +
				"<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject\"       " +
				"DataType=\"http://www.w3.org/2001/XMLSchema#string\">       <AttributeValue>"+intermediarySubjectNPI+"</AttributeValue>      " +
				"</Attribute>	  <Attribute AttributeId=\"gov.samhsa.consent2share.purpose-of-use-code\"       " +
				"DataType=\"http://www.w3.org/2001/XMLSchema#string\">       <AttributeValue>"+purposeOfUse+"</AttributeValue>      " +
				"</Attribute>     </Subject>     <Resource>      " +
				"<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\"       " +
				"DataType=\"http://www.w3.org/2001/XMLSchema#string\">       " +
				"<AttributeValue>"+patientId+"</AttributeValue>      </Attribute>     </Resource>     " +
				"<Action>      <Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\"       " +
				"DataType=\"http://www.w3.org/2001/XMLSchema#string\">       <AttributeValue>write</AttributeValue>      " +
				"</Attribute>     </Action>     <Environment>		" +
				"<Attribute AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\"       " +
				"DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">       " +
				"<AttributeValue>"+date+"</AttributeValue>      </Attribute>	 </Environment>    </Request>";
		return request;
	}
	
	/**
	 * Gets the current date.
	 *
	 * @return the date
	 */
	public String getDate()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		return sdf.format(new Date());
	}
}
