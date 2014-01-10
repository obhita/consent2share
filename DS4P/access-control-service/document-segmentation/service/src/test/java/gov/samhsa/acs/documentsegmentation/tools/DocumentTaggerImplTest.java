package gov.samhsa.acs.documentsegmentation.tools;

import static org.junit.Assert.assertTrue;

import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.tool.FileReaderImpl;
import gov.samhsa.acs.documentsegmentation.tools.DocumentTaggerImpl;

import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentTaggerImplTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static FileReaderImpl fileReader;
	private static String c32;
	private static String executionResponseContainer;
	private static String messageId;

	private static DocumentTaggerImpl documentTagger;

	@BeforeClass
	public static void setUp() throws Exception {
		// Arrange
		fileReader = new FileReaderImpl();
		c32 = fileReader.readFile("c32.xml");
		executionResponseContainer = fileReader
				.readFile("ruleExecutionResponseContainer.xml");
		messageId = UUID.randomUUID().toString();

		documentTagger = new DocumentTaggerImpl();
	}

	@Test
	public void testTagDocument() throws Exception {
		// Arrange
		logger.debug(c32);

		// Act
		String taggedDocument = documentTagger.tagDocument(c32,
				executionResponseContainer, messageId);

		// Assert
		logger.debug(taggedDocument);

		assertTrue(!taggedDocument.contains("<confidentialityCode/>"));
		assertTrue(taggedDocument
				.contains("<confidentialityCode xmlns:ds4p=\"http://www.siframework.org/ds4p\""));
		assertTrue(taggedDocument.contains("code=\"R\""));
		assertTrue(taggedDocument
				.contains("codeSystem=\"2.16.840.1.113883.5.25\""));

		assertTrue(c32.contains("<confidentialityCode/>"));
		assertTrue(!c32
				.contains("<confidentialityCode xmlns:ds4p=\"http://www.siframework.org/ds4p\""));
		assertTrue(!c32.contains("code=\"R\""));
		assertTrue(!c32.contains("codeSystem=\"2.16.840.1.113883.5.25\""));
	}

	@Test(expected = DS4PException.class)
	public void testTagDocument_Throws_DS4PException() {
		// Empty xml file
		@SuppressWarnings("unused")
		String taggedDocument = documentTagger.tagDocument("",
				executionResponseContainer, messageId);
	}
}
