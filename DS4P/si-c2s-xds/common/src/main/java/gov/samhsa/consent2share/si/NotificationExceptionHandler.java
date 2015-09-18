package gov.samhsa.consent2share.si;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class NotificationExceptionHandler {

	@Autowired
	private NotificationPublisher notificationPublisher;

	public void handleError(Message<MessagingException> errorMessage) {
		Assert.notNull(errorMessage);

		MessagingException messagingExceptionPayload = errorMessage.getPayload();
		Throwable ex = messagingExceptionPayload.getCause();

		Object origMessagePayload = messagingExceptionPayload.getFailedMessage().getPayload();

		StringBuilder detailsStringBuilder = new StringBuilder();
		detailsStringBuilder.append("Error occured when handling consent signed/revoked message.");

		detailsStringBuilder.append("\n\n");

		detailsStringBuilder.append("Original message payload:");
		detailsStringBuilder.append("\n");
		detailsStringBuilder.append(origMessagePayload.toString());

		detailsStringBuilder.append("\n\n");

		detailsStringBuilder.append("Error message:");
		detailsStringBuilder.append("\n");
		detailsStringBuilder.append(ex.getMessage());

		detailsStringBuilder.append("\n\n");

		StringWriter errors = new StringWriter();
		ex.printStackTrace(new PrintWriter(errors));

		detailsStringBuilder.append("Error stack trace:");
		detailsStringBuilder.append("\n");
		detailsStringBuilder.append(errors.toString());

		Notification notification = new Notification();
		notification.setDetails(detailsStringBuilder.toString());

		notification.setSubject("Consent signed/revoked message hanlding error");

		notificationPublisher.publish(notification);
	}
}
