package gov.samhsa.acs.documentsegmentation.tools;

import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.documentsegmentation.tools.AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdditonalMetadataGeneratorForProcessedC32ImplTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@After
	public void tearDown() throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl additionalMetadataGeneratorForProcessedC32Impl = new AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl();
		Field field = additionalMetadataGeneratorForProcessedC32Impl
				.getClass()
				.getDeclaredField(
						"AdditonalMetadataStylesheetForProcessedC32_Xsl_File_Name");
		field.setAccessible(true);
		field.set(additionalMetadataGeneratorForProcessedC32Impl,
				"AdditonalMetadataStylesheetForProcessedC32.xsl");
	}

	@Test
	public void testGenerateMetadataXml() throws Exception {
		// Arrange
		String ruleExecutionResponseContainer = readStringFromFile("ruleExecutionResponseContainer.xml");
		String taggedC32Doc = readStringFromFile("tagged_C32.xml");
		AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl additionalMetadataGeneratorForProcessedC32Impl = new AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl();
		String senderEmailAddress = "sender@sender.com";
		String recipientEmailAddress = "receiver@receiver.com";
		String purposeOfUse = "TREAT";
		String xdsDocumentEntryUniqueId = "123";

		// Act
		String result = additionalMetadataGeneratorForProcessedC32Impl
				.generateMetadataXml(UUID.randomUUID().toString(),
						taggedC32Doc, ruleExecutionResponseContainer,
						senderEmailAddress, recipientEmailAddress,
						purposeOfUse, xdsDocumentEntryUniqueId);

		// Assert
		String expectedResult = readStringFromFile("additionalMetadataGeneratedFromRuleExecutionResponseContainer.xml");
		Assert.assertNotNull(result);
		// assertTrue(result.trim().equals(expectedResult.trim()));
	}

	@Test(expected = DS4PException.class)
	public void testGenerateMetadataXml_Throws_DS4PException_Having_TransformerConfigurationException()
			throws Exception {
		// Arrange
		String ruleExecutionResponseContainer = readStringFromFile("ruleExecutionResponseContainer.xml");
		String taggedC32Doc = readStringFromFile("tagged_C32.xml");
		AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl additionalMetadataGeneratorForProcessedC32Impl = new AdditionalMetadataGeneratorForSegmentedClinicalDocumentImpl();
		String senderEmailAddress = "sender@sender.com";
		String recipientEmailAddress = "receiver@receiver.com";
		String purposeOfUse = "TREAT";
		String xdsDocumentEntryUniqueId = "123";

		// Try to use a wrong xsl file
		Field field = additionalMetadataGeneratorForProcessedC32Impl
				.getClass()
				.getDeclaredField(
						"AdditonalMetadataStylesheetForProcessedC32_Xsl_File_Name");
		field.setAccessible(true);
		// Field modifiersField = Field.class.getDeclaredField( "modifiers" );
		// modifiersField.setAccessible( true );
		// modifiersField.setInt( field, field.getModifiers() & ~Modifier.FINAL
		// );
		field.set(additionalMetadataGeneratorForProcessedC32Impl,
				"WRONG_FILE_NAME.XSL");

		// Act
		@SuppressWarnings("unused")
		String result = additionalMetadataGeneratorForProcessedC32Impl
				.generateMetadataXml(UUID.randomUUID().toString(),
						taggedC32Doc, ruleExecutionResponseContainer,
						senderEmailAddress, recipientEmailAddress,
						purposeOfUse, xdsDocumentEntryUniqueId);

		// Assert
		// expect exception
	}

	private String readStringFromFile(String fileName) {
		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(fileName);

		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		StringBuilder resultStringBuilder = new StringBuilder();
		try {
			String line;
			while ((line = br.readLine()) != null) {
				resultStringBuilder.append(line);
				resultStringBuilder.append("\n");
			}

			br.close();

			is.close();

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		String result = resultStringBuilder.toString();
		return result;
	}
}
