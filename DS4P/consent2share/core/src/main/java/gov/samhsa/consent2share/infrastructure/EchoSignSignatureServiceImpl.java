/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.consent2share.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import echosign.api.clientv15.dto.ArrayOfDocumentKey;
import echosign.api.clientv15.dto.ArrayOfFileInfo;
import echosign.api.clientv15.dto.CallbackInfo;
import echosign.api.clientv15.dto.DocumentCreationInfo;
import echosign.api.clientv15.dto.FileInfo;
import echosign.api.clientv15.dto.Pong;
import echosign.api.clientv15.dto.SignatureFlow;
import echosign.api.clientv15.dto.SignatureType;
import echosign.api.clientv15.dto14.ArrayOfRecipientInfo;
import echosign.api.clientv15.dto14.RecipientInfo;
import echosign.api.clientv15.dto14.RecipientRole;
import echosign.api.clientv15.dto15.DocumentInfo;
import echosign.api.clientv15.service.EchoSignDocumentService15Client;
import echosign.api.clientv15.service.EchoSignDocumentService15PortType;

/**
 * The Class EchoSignSignatureServiceImpl.
 */
@Component
public class EchoSignSignatureServiceImpl implements EchoSignSignatureService {

	/** The echo sign service url. */
	private String echoSignServiceUrl;
	
	/** The echo sign api key. */
	private String echoSignApiKey;

	/** The cached service. */
	private EchoSignDocumentService15PortType cachedService;

	/**
	 * Instantiates a new echo sign signature service impl.
	 *
	 * @param echoSignServiceUrl the echo sign service url
	 * @param echoSignApiKey the echo sign api key
	 */
	@Autowired
	public EchoSignSignatureServiceImpl(@Value("${echoSignDocumentService15EndpointAddress}") String echoSignServiceUrl,
			@Value("${echoSignApiKey}") String echoSignApiKey) {
		Assert.hasText(echoSignServiceUrl);
		Assert.hasText(echoSignApiKey);
		
		this.echoSignServiceUrl = echoSignServiceUrl;
		this.echoSignApiKey = echoSignApiKey;
	}

	/**
	 * Gets the cached service.
	 *
	 * @return the cached service
	 */
	private EchoSignDocumentService15PortType getCachedService() {
		if (cachedService == null) {
			EchoSignDocumentService15Client client = new EchoSignDocumentService15Client();
			cachedService = client
					.getEchoSignDocumentService15HttpPort(echoSignServiceUrl);
		}
		return cachedService;
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.domain.commondomainservices.SignatureService#sendDocumentToSign(byte[], java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String sendDocumentToSign(byte[] documentBytes,
			String documentFileName, String documentName,
			String recipientEmail, String messageToRecipient) {

		String documentKey = sendDocumentToSign(documentBytes, documentFileName, documentName, recipientEmail, messageToRecipient, null); 

		return documentKey;
	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.domain.commondomainservices.SignatureService#sendDocumentToSign(byte[], java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String sendDocumentToSign(byte[] documentBytes,
			String documentFileName, String documentName,
			String recipientEmail, String messageToRecipient,
			String signedDocumentUrl) {

		Assert.notNull(documentBytes, "Document bytes must not be null.");
		Assert.hasText(documentFileName,
				"Document file name must not be null or empty.");
		Assert.notNull(recipientEmail,
				"Recipient email address must not be null.");

		RecipientInfo recipientInfo = new RecipientInfo();
		// email is required
		recipientInfo.setEmail(recipientEmail);
		// role is required
		recipientInfo.setRole(RecipientRole.SIGNER);

		ArrayOfRecipientInfo arrayOfRecipientInfo = new ArrayOfRecipientInfo();
		arrayOfRecipientInfo.getRecipientInfo().add(recipientInfo);

		FileInfo fileInfo = new FileInfo();
		// fileName is required
		fileInfo.setFileName(documentFileName);
		// The raw file content, encoded using base64.
		fileInfo.setFile(documentBytes);

		ArrayOfFileInfo fileInfos = new ArrayOfFileInfo();
		fileInfos.getFileInfo().add(fileInfo);

		DocumentCreationInfo documentInfo = new DocumentCreationInfo();

		// recipients is required
		documentInfo.setRecipients(arrayOfRecipientInfo);

		if (!StringUtils.hasText(documentName)) {
			documentName = documentFileName;
		}

		// The name of the agreement, which will be used to identify it in the
		// emails and on the website.
		// name is required
		documentInfo.setName(documentName);

		if (StringUtils.hasText(messageToRecipient)) {
			// An optional message to the recipient(s) describing to them what
			// is being sent and/or why their signature is required.
			documentInfo.setMessage(messageToRecipient);
		}

		// Optional parameter which sets how often you'd like the recipient(s)
		// to be reminded to sign this document.
		// reminderFrequency is not required
		// documentInfo.setReminderFrequency(value);

		if (StringUtils.hasText(signedDocumentUrl)) {
			// Sets the callback properties that allows EchoSign to notify you
			// when the agreement has been signed and avoid polling.
			// callbackInfo is not required
			CallbackInfo callbackInfo = new CallbackInfo();
			callbackInfo.setSignedDocumentUrl(signedDocumentUrl);
			documentInfo.setCallbackInfo(callbackInfo);
		}

		// The locale associated with this agreement - specifies the language
		// for the signing page and emails, for example en_US or fr_FR. If none
		// specified, defaults to the language configured for the widget's
		// sender.
		// locale is not required
		// documentInfo.setLocale(value);

		// Optional default values for fields to merge into the document. The
		// values will be presented to the signers for editable fields; for
		// read-only fields the provided values will not be editable during the
		// signing process.
		// mergeFieldInfo is not required
		// documentInfo.setMergeFieldInfo(value);

		documentInfo.setFileInfos(fileInfos);
		documentInfo.setSignatureType(SignatureType.ESIGN);
		documentInfo
				.setSignatureFlow(SignatureFlow.SENDER_SIGNATURE_NOT_REQUIRED);

		ArrayOfDocumentKey documentKeys = getCachedService().sendDocument(
				echoSignApiKey, null, documentInfo);

		String documentKey = documentKeys.getDocumentKey().get(0)
				.getDocumentKey();

		return documentKey;
	}
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.EchoSignSignatureService#getDocumentInfo(java.lang.String)
	 */
	@Override
	public DocumentInfo getDocumentInfo(String documentKey){
		Assert.hasText(documentKey, "Document key must not be null or empty.");
		
		DocumentInfo info = getCachedService().getDocumentInfo(echoSignApiKey,
				documentKey);

		return info;
	}
	
	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.infrastructure.EchoSignSignatureService#getLatestDocument(java.lang.String)
	 */
	@Override
	public byte[] getLatestDocument(String documentKey){
		Assert.hasText(documentKey, "Document key must not be null or empty.");
		byte[] data = getCachedService().getLatestDocument(echoSignApiKey,
				documentKey);
		
		return data;
	}
	
	/**
	 * Test ping.
	 *
	 * @return the pong
	 */
	public Pong testPing(){
		Pong pong = getCachedService().testPing(echoSignApiKey);
		return pong;
	}
	
	/**
	 * Test echo file.
	 *
	 * @param input the input
	 * @return the byte[]
	 */
	public byte[] testEchoFile(byte[] input){
		byte[] output = getCachedService().testEchoFile(echoSignApiKey, input);
		return output;
	}
}
