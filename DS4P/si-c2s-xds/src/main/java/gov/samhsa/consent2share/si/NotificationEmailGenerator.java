package gov.samhsa.consent2share.si;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class NotificationEmailGenerator {
	public String generateEmail(Notification notification) {
		Assert.notNull(notification);
		
		return notification.getDetails();
	}
}
