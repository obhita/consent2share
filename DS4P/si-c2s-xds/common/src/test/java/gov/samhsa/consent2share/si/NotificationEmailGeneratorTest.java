package gov.samhsa.consent2share.si;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class NotificationEmailGeneratorTest {
	
	@Test(expected = IllegalArgumentException.class)
	public void testGenerateEmail_Throws_Exception_Given_Null_Notification(){
		NotificationEmailGenerator sut = new NotificationEmailGenerator();
		sut.generateEmail(null);
	}
	
	@Test
	public void testGenerateEmail_Returns_As_Expected(){
		NotificationEmailGenerator sut = new NotificationEmailGenerator();
		Notification notification = new Notification();
		final String details = "details";
		notification.setDetails(details);
		
		String result = sut.generateEmail(notification);
		
		assertEquals(details, result);
	}
}
