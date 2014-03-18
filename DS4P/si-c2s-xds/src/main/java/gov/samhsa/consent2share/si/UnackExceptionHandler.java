package gov.samhsa.consent2share.si;

import org.springframework.integration.Message;
import org.springframework.integration.MessagingException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class UnackExceptionHandler {

	public void handError(Message<?> errorMessage) throws Throwable {
		Assert.notNull(errorMessage);
		
		Throwable e = ((MessagingException) errorMessage.getPayload()).getCause();
		// Re-throw the exception to un-acknowledge to amqp:inbound-channel-adapter which is using auto-ack
		throw e;
	}
}
