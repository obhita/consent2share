package gov.samhsa.consent2share.infrastructure;

import echosign.api.clientv15.dto.SenderInfo;
import echosign.api.clientv15.dto8.EmbeddedWidgetCreationResult;
import echosign.api.clientv15.dto8.WidgetCreationInfo;
import echosign.api.clientv15.dto8.WidgetPersonalizationInfo;
import echosign.api.clientv15.service.EchoSignDocumentService15PortType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;



// TODO: Auto-generated Javadoc
/**
 * The Class EchoSignSignatureServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class EchoSignSignatureServiceImplTest {
	
	/** The document bytes. */
	byte[] DOCUMENT_BYTES="text".getBytes();
	
	/** The document file name. */
	String DOCUMENT_FILE_NAME="documentFileName";
	
	/** The document name. */
	String DOCUMENT_NAME="documentName";
	
	/** The signed document url. */
	String SIGNED_DOCUMENT_URL="signedDocumentUrl";
	
	/** The email. */
	String EMAIL="consent2shar@gmail.com";
	
	/** The echosign api key. */
	String ECHOSIGN_API_KEY="echoSignApiKey";
	
	/** The echosign service url. */
	String ECHOSIGN_SERVICE_URL="echoSignServiceUrl";
	
	/** The sut. */
	@InjectMocks
	EchoSignSignatureServiceImpl sut=spy(new EchoSignSignatureServiceImpl("echoSignServiceUrl","echoSignApiKey"));
	
	/**
	 * Test create embedded widget.
	 */
	@Test
	public void testCreateEmbeddedWidget() {
		EchoSignDocumentService15PortType service=mock(EchoSignDocumentService15PortType.class);
		EmbeddedWidgetCreationResult widgetResult=mock(EmbeddedWidgetCreationResult.class);
		doReturn(widgetResult).when(service).createPersonalEmbeddedWidget(eq("echoSignApiKey"), isNull(SenderInfo.class), any(WidgetCreationInfo.class), any(WidgetPersonalizationInfo.class));
		doReturn(service).when(sut).getCachedService();
		
		sut.createEmbeddedWidget(DOCUMENT_BYTES, DOCUMENT_FILE_NAME, DOCUMENT_NAME, SIGNED_DOCUMENT_URL, EMAIL);
		verify(service).createPersonalEmbeddedWidget(eq("echoSignApiKey"), isNull(SenderInfo.class), any(WidgetCreationInfo.class), any(WidgetPersonalizationInfo.class));
	}
	
	/**
	 * Test create embedded widget_when_file_name_is_null.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreateEmbeddedWidget_when_file_name_is_null() {
		sut.createEmbeddedWidget(DOCUMENT_BYTES, null, DOCUMENT_NAME, SIGNED_DOCUMENT_URL, EMAIL);
	}
	
	/**
	 * Test create embedded widget_when_file_document_byte_is_null.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreateEmbeddedWidget_when_file_document_byte_is_null() {
		sut.createEmbeddedWidget(null, DOCUMENT_FILE_NAME, DOCUMENT_NAME, SIGNED_DOCUMENT_URL, EMAIL);
	}

}
