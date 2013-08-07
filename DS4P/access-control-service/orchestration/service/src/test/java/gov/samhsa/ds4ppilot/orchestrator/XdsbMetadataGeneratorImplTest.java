package gov.samhsa.ds4ppilot.orchestrator;

import gov.samhsa.ds4ppilot.common.exception.DS4PException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import oasis.names.tc.ebxml_regrep.xsd.lcm._3.SubmitObjectsRequest;

import org.junit.Test;

public class XdsbMetadataGeneratorImplTest {

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
				new UniqueOidProviderImpl());
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

		System.out.println(meta);

		SubmitObjectsRequest submitObjectsRequest = xdsbMetadataGeneratorImpl
				.generateMetadata(c32Document.toString(), "1.1.1.1.1");

		System.out.println("Generated SubmitObjectsRequest");

		try {
			System.out.println(marshall(submitObjectsRequest));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
