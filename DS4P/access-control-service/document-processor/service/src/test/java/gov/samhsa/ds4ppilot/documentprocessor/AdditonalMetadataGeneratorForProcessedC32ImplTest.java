package gov.samhsa.ds4ppilot.documentprocessor;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

import junit.framework.Assert;

import org.junit.Test;

public class AdditonalMetadataGeneratorForProcessedC32ImplTest {

	@Test
	public void testGenerateMetadataXml() throws Exception {
		// Arrange
		String ruleExecutionResponseContainer = readStringFromFile("ruleExecutionResponseContainer.xml");
		String taggedC32Doc = readStringFromFile("tagged_C32.xml");
		AdditionalMetadataGeneratorForProcessedC32Impl additionalMetadataGeneratorForProcessedC32Impl = new AdditionalMetadataGeneratorForProcessedC32Impl();
		String senderEmailAddress = "sender@sender.com";
		String recipientEmailAddress = "receiver@receiver.com";
		String purposeOfUse = "TREAT";
		String xdsDocumentEntryUniqueId = "123";

		// Act
		String result = additionalMetadataGeneratorForProcessedC32Impl
				.generateMetadataXml(UUID.randomUUID().toString(), taggedC32Doc, ruleExecutionResponseContainer,
						senderEmailAddress, recipientEmailAddress, purposeOfUse, xdsDocumentEntryUniqueId);

		// Assert
		String expectedResult = readStringFromFile("additionalMetadataGeneratedFromRuleExecutionResponseContainer.xml");
		Assert.assertNotNull(result);
		//assertTrue(result.trim().equals(expectedResult.trim()));
	}

	private static String readStringFromFile(String fileName) {
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
			e.printStackTrace();
		}

		String result = resultStringBuilder.toString();
		return result;
	}
}
