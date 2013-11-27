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
package gov.samhsa.consent2share.pixclient.util;

import gov.samhsa.consent2share.pixclient.ws.ObjectFactory;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201301UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201302UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201309UV02;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PixManagerRequestXMLToJava {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private JAXBContext jaxbContext = null;

	public PixManagerRequestXMLToJava() throws JAXBException {
		// 1. We need to create JAXContext instance

		if (jaxbContext == null) {
			jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
		}
	}

	public PRPAIN201301UV02 getPIXAddReqObject(String reqXMLFilePath,
			String encodeString) throws JAXBException {

		// 2. Use JAXBContext instance to create the Unmarshaller.
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		PRPAIN201301UV02 reqObj = null;
		if (reqXMLFilePath == null) {
			throw new JAXBException("input is null");
		}

		// if the string starts with <?xml then its a xml document
		// otherwise its xml file path
		if (reqXMLFilePath.startsWith("<?xml")) {
			// 3. Use the Unmarshaller to unmarshal the XML document to get an
			// instance of JAXBElement.
			// 4. Get the instance of the required JAXB Root Class from the
			// JAXBElement.
			try {
				reqObj = (PRPAIN201301UV02) unmarshaller
						.unmarshal(new ByteArrayInputStream(reqXMLFilePath
								.getBytes(encodeString)));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				throw new JAXBException(e.getMessage(), e);
			}
		} else {
			// 3. Use the Unmarshaller to unmarshal the XML document to get an
			// instance of JAXBElement.
			// 4. Get the instance of the required JAXB Root Class from the
			// JAXBElement.
			reqObj = (PRPAIN201301UV02) unmarshaller.unmarshal(getClass()
					.getClassLoader().getResourceAsStream(reqXMLFilePath));
		}

		return reqObj;
	}

	public PRPAIN201302UV02 getPIXUpdateReqObject(String reqXMLFilePath,
			String encodeString) throws JAXBException {

		// 2. Use JAXBContext instance to create the Unmarshaller.
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		PRPAIN201302UV02 reqObj = null;

		if (reqXMLFilePath == null) {
			throw new JAXBException("input is null");
		}

		// 3. Use the Unmarshaller to unmarshal the XML document to get an
		// instance of JAXBElement.
		// 4. Get the instance of the required JAXB Root Class from the
		// JAXBElement.
		if (reqXMLFilePath.startsWith("<?xml")) {
			try {
				reqObj = (PRPAIN201302UV02) unmarshaller
						.unmarshal(new ByteArrayInputStream(reqXMLFilePath
								.getBytes(encodeString)));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				throw new JAXBException(e.getMessage(), e);
			}
		} else {

			reqObj = (PRPAIN201302UV02) unmarshaller.unmarshal(getClass()
					.getClassLoader().getResourceAsStream(reqXMLFilePath));
		}
		return reqObj;
	}

	public PRPAIN201309UV02 getPIXQueryReqObject(String reqXMLFilePath,
			String encodeString) throws JAXBException {

		// 2. Use JAXBContext instance to create the Unmarshaller.
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		PRPAIN201309UV02 reqObj = null;
		if (reqXMLFilePath == null) {
			throw new JAXBException("input is null");
		}

		// 3. Use the Unmarshaller to unmarshal the XML document to get an
		// instance of JAXBElement.
		// 4. Get the instance of the required JAXB Root Class from the
		// JAXBElement.
		if (reqXMLFilePath.startsWith("<?xml")) {
			try {
				reqObj = (PRPAIN201309UV02) unmarshaller
						.unmarshal(new ByteArrayInputStream(reqXMLFilePath
								.getBytes(encodeString)));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				throw new JAXBException(e.getMessage(), e);
			}
		} else {

			reqObj = (PRPAIN201309UV02) unmarshaller.unmarshal(getClass()
					.getClassLoader().getResourceAsStream(reqXMLFilePath));
		}
		return reqObj;
	}

}
