package gov.samhsa.consent;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsentTransformerImpl implements ConsentTransformer {

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public String transform(ConsentDto dto, String xslFileName) throws ConsentGenException {
		String cdar2 = "";
		try {
			ByteArrayOutputStream sr1 = jaxbMarshall(dto);
			StreamSource bais = new StreamSource(new ByteArrayInputStream(
					sr1.toByteArray()));
			URL cd2 = this.getClass().getClassLoader().getResource(xslFileName);
			String xslID = cd2.toString();
			StreamResult srcdar = saxonTransform(xslID, bais);

			cdar2 = srcdar.getOutputStream().toString();
		
		} catch (JAXBException e) {
			logger.error("Error in JAXB Transfroming",e);
			throw new ConsentGenException(e.getMessage());
		} catch (TransformerException e) {
			logger.error("Error in SAXON Transfroming", e);
			throw new ConsentGenException(e.getMessage());
		}


		return cdar2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.consentexport.ConsentExportService#
	 * jaxbMarshall
	 * (gov.samhsa.consent2share.service.consentexport.ConsentExportDto)
	 */
	protected ByteArrayOutputStream jaxbMarshall(ConsentDto consentDto) throws JAXBException {
		ByteArrayOutputStream marshalresult = new ByteArrayOutputStream();

		JAXBContext jaxbContext;
			jaxbContext = JAXBContext.newInstance(ConsentDto.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			marshaller.marshal(consentDto, marshalresult);

		return marshalresult;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.consent2share.service.consentexport.ConsentExportService#
	 * saxonTransform(java.lang.String, javax.xml.transform.stream.StreamSource)
	 */
	protected StreamResult saxonTransform(String xslID,
			StreamSource streamSource) throws TransformerException {

		StreamResult srcdar = new StreamResult(new ByteArrayOutputStream());
		TransformerFactory tfactory = TransformerFactory.newInstance();

		Transformer transformer;
		transformer = tfactory.newTransformer(new StreamSource(xslID));
		transformer.transform(streamSource, srcdar);

		return srcdar;

	}

}
