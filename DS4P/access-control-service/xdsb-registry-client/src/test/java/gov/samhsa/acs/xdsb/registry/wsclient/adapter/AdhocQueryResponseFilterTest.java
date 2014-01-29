package gov.samhsa.acs.xdsb.registry.wsclient.adapter;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import gov.samhsa.acs.common.tool.DocumentXmlConverterImpl;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.junit.Before;
import org.junit.Test;

public class AdhocQueryResponseFilterTest {

	private SimpleMarshallerImpl marshaller;
	private FileReaderImpl fileReader;
	private DocumentXmlConverterImpl documentXmlConverter;

	private AdhocQueryResponseFilter sut;

	@Before
	public void setUp() throws Exception {
		marshaller = new SimpleMarshallerImpl();
		documentXmlConverter = new DocumentXmlConverterImpl();
		fileReader = new FileReaderImpl();
		sut = new AdhocQueryResponseFilter(marshaller, documentXmlConverter);

	}

	@Test
	public void testFilterByAuthor() throws Exception, Throwable {
		// Arrange
		String expectedResponse = fileReader
				.readFile("unitTestAdhocQueryResponseFiltered.xml");
		AdhocQueryResponse responseMock = marshaller.unmarshallFromXml(
				AdhocQueryResponse.class,
				fileReader.readFile("unitTestAdhocQueryResponse.xml"));

		// Act
		AdhocQueryResponse actualResponse = sut.filterByAuthor(responseMock,
				"1114252178");
		String actualResponseXmlString = marshaller.marshall(actualResponse);

		// Assert
		assertXMLEqual("", expectedResponse, actualResponseXmlString);
	}
}
