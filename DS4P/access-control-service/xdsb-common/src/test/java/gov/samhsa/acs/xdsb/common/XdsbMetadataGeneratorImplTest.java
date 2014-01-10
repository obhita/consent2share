package gov.samhsa.acs.xdsb.common;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;

import java.io.IOException;
import java.util.LinkedList;

import oasis.names.tc.ebxml_regrep.xsd.lcm._3.SubmitObjectsRequest;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;
import org.xml.sax.SAXException;

public class XdsbMetadataGeneratorImplTest {

	private FileReaderImpl fileReader = new FileReaderImpl();
	private SimpleMarshallerImpl marshaller = new SimpleMarshallerImpl();

	private XdsbMetadataGeneratorImpl sut;

	@Test
	public void testGenerateMetadataXml_RemC32() throws Throwable {
		initClinical();
		String c32FileName = "remC32.xml";
		String expectedMetadataFileName = "unitTestMetaRemC32.xml";
		testGenerateMetadataXml(c32FileName, expectedMetadataFileName, createIgnorableXPathRegexList());
	}

	@Test
	public void testGenerateMetadataXml_CdaR2() throws Throwable {
		initClinical();
		String c32FileName = "cdaR2Consent.xml";
		String expectedMetadataFileName = "unitTestMetaCdaR2Consent.xml";
		testGenerateMetadataXml(c32FileName, expectedMetadataFileName, createIgnorableXPathRegexList());
	}
	
	@Test
	public void testGenerateMetadataXml_Xacml() throws Throwable {
		initPrivacy();
		String c32FileName = "xacml_policy.xml";
		String expectedMetadataFileName = "unitTestMetaXacml.xml";
		testGenerateMetadataXml(c32FileName, expectedMetadataFileName, createIgnorableXPathRegexList());
	}

	@Test
	public void testGenerateMetadata_RemC32() throws Throwable {
		initClinical();
		String metadataFileName = "unitTestMetaRemC32.xml";
		testGenerateMetadata(metadataFileName);
	}

	@Test
	public void testGenerateMetadata_CdaR2() throws Throwable {
		initClinical();
		String metadataFileName = "unitTestMetaCdaR2Consent.xml";
		testGenerateMetadata(metadataFileName);
	}
	
	@Test
	public void testGenerateMetadata_Xacml() throws Throwable {
		initPrivacy();
		String metadataFileName = "unitTestMetaXacml.xml";
		testGenerateMetadata(metadataFileName);
	}
	
	private void testGenerateMetadataXml(String fileName,
			String expectedMetadataFileName, LinkedList<String> ignorableXPathsRegex) throws IOException {
		// Arrange
		String file = fileReader.readFile(fileName);
		String expectedMetadata = fileReader.readFile(expectedMetadataFileName);

		// Act
		String meta = sut.generateMetadataXml(file, "1.1.1.1.1");

		// Assert
		DetailedDiff diff = XmlComparator.compareXMLs(expectedMetadata, meta,
				ignorableXPathsRegex);
		assertTrue(diff.similar());
	}
	
	private void testGenerateMetadata(String metadataFileName)
			throws IOException, Throwable, SAXException {
		// Arrange
		String metadata = fileReader.readFile(metadataFileName);
		String documentMock = "documentMock";
		String homeCommunityIdMock = "homeCommunityIdMock";
		sut = spy(sut);
		doReturn(metadata).when(sut).generateMetadataXml(documentMock,
				homeCommunityIdMock);
		XMLUnit.setIgnoreWhitespace(true);

		// Act
		SubmitObjectsRequest submitObjectsRequest = sut.generateMetadata(
				documentMock, homeCommunityIdMock);
		String xml = marshaller.marshall(submitObjectsRequest);

		// Assert
		assertNotNull(submitObjectsRequest);
		assertXMLEqual("", metadata, xml);
	}

	private LinkedList<String> createIgnorableXPathRegexList() {
		LinkedList<String> ignorableXPathsRegex = new LinkedList<String>();
		ignorableXPathsRegex
				.add("\\/SubmitObjectsRequest\\[1\\]/RegistryObjectList\\[1\\]/ExtrinsicObject\\[1\\]/Slot\\[1\\]/ValueList\\[1\\]/Value\\[1\\]");
		ignorableXPathsRegex
				.add("\\/SubmitObjectsRequest\\[1\\]/RegistryObjectList\\[1\\]/ExtrinsicObject\\[1\\]/ExternalIdentifier\\[2\\]/@value");
		ignorableXPathsRegex
				.add("\\/SubmitObjectsRequest\\[1\\]/RegistryObjectList\\[1\\]/RegistryPackage\\[1\\]/Slot\\[1\\]/ValueList\\[1\\]/Value\\[1\\]");
		ignorableXPathsRegex
				.add("\\/SubmitObjectsRequest\\[1\\]/RegistryObjectList\\[1\\]/RegistryPackage\\[1\\]/ExternalIdentifier\\[1\\]/@value");
		return ignorableXPathsRegex;
	}
	
	private void initClinical(){
		sut = new XdsbMetadataGeneratorImpl(new UniqueOidProviderImpl(),
				XdsbDocumentType.CLINICAL_DOCUMENT, marshaller);
	}
	
	private void initPrivacy(){
		sut = new XdsbMetadataGeneratorImpl(new UniqueOidProviderImpl(),
				XdsbDocumentType.PRIVACY_CONSENT, marshaller);
	}
}
