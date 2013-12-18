package gov.samhsa.acs.xdsb.common;

import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.xdsb.common.UniqueOidProviderImpl;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.acs.xdsb.common.XdsbMetadataGeneratorImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import oasis.names.tc.ebxml_regrep.xsd.lcm._3.SubmitObjectsRequest;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XdsbMetadataGeneratorImplTest {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(XdsbMetadataGeneratorImplTest.class);

	@Test
	public void testgenerateMetadataXml_C32() {
		testgenerateMetadataXml("remC32.xml");
	}

	@Test
	public void testgenerateMetadataXml_CdaR2() {
		testgenerateMetadataXml("cdaR2Consent.xml");
	}

	private void testgenerateMetadataXml(String fileName) {
		XdsbMetadataGeneratorImpl xdsbMetadataGeneratorImpl = new XdsbMetadataGeneratorImpl(
				new UniqueOidProviderImpl(), XdsbDocumentType.CLINICAL_DOCUMENT);
		InputStream is = xdsbMetadataGeneratorImpl.getClass().getClassLoader()
				.getResourceAsStream(fileName);

		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		StringBuilder c32Document = new StringBuilder();
		String line;
		try {
			while ((line = br.readLine()) != null) {
				c32Document.append(line);
			}

			br.close();
			is.close();
		} catch (IOException e) {
			throw new DS4PException(e.toString(), e);
		}

		String meta = xdsbMetadataGeneratorImpl.generateMetadataXml(
				c32Document.toString(), "1.1.1.1.1");

		LOGGER.debug(meta);

		SubmitObjectsRequest submitObjectsRequest = xdsbMetadataGeneratorImpl
				.generateMetadata(c32Document.toString(), "1.1.1.1.1");

		LOGGER.debug("Generated SubmitObjectsRequest");

		try {
			LOGGER.debug(marshall(submitObjectsRequest));
		} catch (Throwable e) {
			LOGGER.debug(e.toString(), e);
		}
	}

	private static String marshall(Object obj) throws Throwable {
		final JAXBContext context = JAXBContext.newInstance(obj.getClass());

		// Create the marshaller, this is the nifty little thing that will
		// actually transform the object into XML
		final Marshaller marshaller = context.createMarshaller();

		// Create a stringWriter to hold the XML
		final StringWriter stringWriter = new StringWriter();

		// Marshal the javaObject and write the XML to the stringWriter
		marshaller.marshal(obj, stringWriter);

		return stringWriter.toString();
	}
}
