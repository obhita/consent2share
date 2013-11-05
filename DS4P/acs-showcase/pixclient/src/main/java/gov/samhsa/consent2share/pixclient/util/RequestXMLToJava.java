package gov.samhsa.consent2share.pixclient.util;

import gov.samhsa.consent2share.pixclient.ws.ObjectFactory;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201301UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201302UV02;
import gov.samhsa.consent2share.pixclient.ws.PRPAIN201309UV02;

import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

@Component
public class RequestXMLToJava {

	private JAXBContext jaxbContext = null;

	public RequestXMLToJava() {
		// 1. We need to create JAXContext instance

		try {
			if (jaxbContext == null) {
				jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @param args
	 * @throws JAXBException
	 */
	public static void main(String[] args) {
		try {
			// 1. We need to create JAXContext instance
			JAXBContext jaxbContext = JAXBContext
					.newInstance(ObjectFactory.class);
			// 2. Use JAXBContext instance to create the Unmarshaller.
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			// 3. Use the Unmarshaller to unmarshal the XML document to get an
			// instance of JAXBElement.
			// 4. Get the instance of the required JAXB Root Class from the
			// JAXBElement.
			PRPAIN201301UV02 reqObj = (PRPAIN201301UV02) unmarshaller
					.unmarshal(ClassLoader
							.getSystemResourceAsStream("xml/PRPA_IN201301UV02_Request.xml"));

			System.out.println(reqObj.getReceiver());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public PRPAIN201301UV02 getPIXAddReqObject(String reqXMLFilePath)
			throws JAXBException {

		// 2. Use JAXBContext instance to create the Unmarshaller.
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		// 3. Use the Unmarshaller to unmarshal the XML document to get an
		// instance of JAXBElement.
		// 4. Get the instance of the required JAXB Root Class from the
		// JAXBElement.
		PRPAIN201301UV02 reqObj = (PRPAIN201301UV02) unmarshaller
				.unmarshal(getClass().getClassLoader().getResourceAsStream(
						reqXMLFilePath));

		System.out.println(reqObj.getReceiver());
		return reqObj;
	}

	public PRPAIN201302UV02 getPIXUpdateReqObject(String reqXMLFilePath)
			throws JAXBException {

		// 2. Use JAXBContext instance to create the Unmarshaller.
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		// 3. Use the Unmarshaller to unmarshal the XML document to get an
		// instance of JAXBElement.
		// 4. Get the instance of the required JAXB Root Class from the
		// JAXBElement.
		PRPAIN201302UV02 reqObj = (PRPAIN201302UV02) unmarshaller
				.unmarshal(getClass().getClassLoader().getResourceAsStream(
						reqXMLFilePath));

		System.out.println(reqObj.getReceiver());
		return reqObj;
	}

	public PRPAIN201309UV02 getPIXQueryReqObject(String reqXMLFilePath)
			throws JAXBException {

		// 2. Use JAXBContext instance to create the Unmarshaller.
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		// 3. Use the Unmarshaller to unmarshal the XML document to get an
		// instance of JAXBElement.
		// 4. Get the instance of the required JAXB Root Class from the
		// JAXBElement.
		PRPAIN201309UV02 reqObj = (PRPAIN201309UV02) unmarshaller
				.unmarshal(getClass().getClassLoader().getResourceAsStream(
						reqXMLFilePath));

		System.out.println(reqObj.getReceiver());
		return reqObj;
	}

	public String convertXMLFileToString(String fileName) {
		try {
			InputStream inputStream = getClass().getClassLoader()
					.getResourceAsStream(fileName);
			StringWriter writer = new StringWriter();
			String encoding = "UTF-8";
			IOUtils.copy(inputStream, writer, encoding);
			System.out.println(writer.toString());
			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
