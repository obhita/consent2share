package gov.samhsa.ds4ppilot.documentprocessor;


import gov.samhsa.ds4ppilot.common.beans.XacmlResult;
import gov.samhsa.ds4ppilot.common.utils.FileHelper;
import gov.samhsa.ds4ppilot.common.utils.XmlHelper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.utils.EncryptionConstants;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DocumentProcessorTest {

	private static String xacmlResult;
	@BeforeClass
	public static void setUp() {
		LoadXMLAsString("c32.xml");
		xacmlResult = "<xacmlResult><pdpDecision>Permit</pdpDecision><purposeOfUse>TREAT</purposeOfUse><messageId>4617a579-1881-4e40-9f98-f85bd81d6502</messageId><homeCommunityId>2.16.840.1.113883.3.467</homeCommunityId><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:org:us-privacy-law:42CFRPart2</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:org:refrain-policy:NORDSLCD</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:redact:ETH</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:redact:PSY</pdpObligation><pdpObligation>urn:oasis:names:tc:xspa:2.0:resource:patient:mask:HIV</pdpObligation></xacmlResult>";
		try {
			unmarshallFromXml(XacmlResult.class,xacmlResult);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	private static String LoadXMLAsString(String xmlFileName) {
		InputStream in = null;
		StringBuilder c32Document = new StringBuilder();

		try {
			in = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("c32.xml");

			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String line;
			while ((line = br.readLine()) != null) {
				c32Document.append(line);
			}

			br.close();
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return c32Document.toString();
	}

	@Test
	public void processDocument_DecryptDocumentFromZip() {

		Document processedDoc;
		DESedeKeySpec desedeEncryptKeySpec;
		DESedeKeySpec desedeMaskKeySpec;
		try {

			org.apache.xml.security.Init.init();

			ZipInputStream zis = new ZipInputStream(Thread.currentThread()
					.getContextClassLoader()
					.getResourceAsStream("Patientone_Asample_XDM.zip"));

			byte[] processDocBytes = entryBytesFromZipBytes(zis,
					"SUBSET01/DOCUMENT.xml");
			String processDocString = new String(processDocBytes);
			FileHelper.writeStringToFile(processDocString,
					"processDocString.xml");

			processedDoc = XmlHelper.loadDocument(processDocString);

			byte[] kekEncryptionKeyBytes = entryBytesFromZipBytes(zis,
					"kekEncryptionKey");

			desedeEncryptKeySpec = new DESedeKeySpec(kekEncryptionKeyBytes);
			SecretKeyFactory skfEncrypt = SecretKeyFactory
					.getInstance("DESede");
			SecretKey desedeEncryptKey = skfEncrypt
					.generateSecret(desedeEncryptKeySpec);

			byte[] kekMaskingKeyBytes = entryBytesFromZipBytes(zis,
					"kekMaskingKey");

			desedeMaskKeySpec = new DESedeKeySpec(kekMaskingKeyBytes);
			SecretKeyFactory skfMask = SecretKeyFactory.getInstance("DESede");
			SecretKey desedeMaskKey = skfMask.generateSecret(desedeMaskKeySpec);

			zis.close();

			/*************************************************
			 * DECRYPT DOCUMENT
			 *************************************************/
			Element encryptedDataElement = (Element) processedDoc
					.getElementsByTagNameNS(
							EncryptionConstants.EncryptionSpecNS,
							EncryptionConstants._TAG_ENCRYPTEDDATA).item(0);

			/*
			 * The key to be used for decrypting xml data would be obtained from
			 * the keyinfo of the EncrypteData using the kek.
			 */
			XMLCipher xmlCipher = XMLCipher.getInstance();
			xmlCipher.init(XMLCipher.DECRYPT_MODE, null);
			xmlCipher.setKEK(desedeEncryptKey);

			/*
			 * The following doFinal call replaces the encrypted data with
			 * decrypted contents in the document.
			 */
			if (encryptedDataElement != null)
				xmlCipher.doFinal(processedDoc, encryptedDataElement);

			/*************************************************
			 * DECRYPT ELEMENTS
			 *************************************************/
			NodeList encryptedDataElements = processedDoc
					.getElementsByTagNameNS(
							EncryptionConstants.EncryptionSpecNS,
							EncryptionConstants._TAG_ENCRYPTEDDATA);

			while (encryptedDataElements.getLength() > 0) {
				/*
				 * The key to be used for decrypting xml data would be obtained
				 * from the keyinfo of the EncrypteData using the kek.
				 */
				XMLCipher xmlMaskCipher = XMLCipher.getInstance();
				xmlMaskCipher.init(XMLCipher.DECRYPT_MODE, null);
				xmlMaskCipher.setKEK(desedeMaskKey);

				xmlMaskCipher.doFinal(processedDoc,
						((Element) encryptedDataElements.item(0)));

				encryptedDataElements = processedDoc.getElementsByTagNameNS(
						EncryptionConstants.EncryptionSpecNS,
						EncryptionConstants._TAG_ENCRYPTEDDATA);
			}

			FileHelper.writeDocToFile(processedDoc,
					"unitTest_DecryptedUnMasked_C32_from_zip.xml");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private byte[] entryBytesFromZipBytes(ZipInputStream zip_inputstream,
			String entryName) throws IOException {

		ZipEntry current_zip_entry = null;
		byte[] buf = new byte[4096];
		boolean found = false;
		current_zip_entry = zip_inputstream.getNextEntry();
		while ((current_zip_entry != null) && !found) {
			if (current_zip_entry.getName().equals(entryName)) {
				found = true;
				ByteArrayOutputStream output = streamToOutputByteStream(zip_inputstream);
				buf = output.toByteArray();
				output.flush();
				output.close();
			} else {
				current_zip_entry = zip_inputstream.getNextEntry();
			}
		}

		return buf;
	}

	private ByteArrayOutputStream streamToOutputByteStream(
			ZipInputStream zip_inputstream) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		int data = 0;
		while ((data = zip_inputstream.read()) != -1) {
			output.write(data);
		}
		return output;
	}

	private static <T> T unmarshallFromXml(Class<T> clazz, String xml)
			throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Unmarshaller um = context.createUnmarshaller();
		ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes());
		return (T) um.unmarshal(input);
	}

}
