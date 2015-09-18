package gov.samhsa.consent2share.si;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;
import org.springframework.util.StringUtils;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class NotificationExceptionHandlerTest {

	@Mock
	private NotificationPublisher notificationPublisher;

	@InjectMocks
	private NotificationExceptionHandler sut;

	@Test(expected = IllegalArgumentException.class)
	public void testHandleError_Throws_Exception_Given_Null_Message() {
		sut.handleError(null);
	}

	@Test
	public void testHandleError_Notification_Is_Published() {
		Message<MessagingException> errorMessageMock = (Message<MessagingException>) mock(Message.class);
		MessagingException messagingExceptionMock = mock(MessagingException.class);
		when(errorMessageMock.getPayload()).thenReturn(messagingExceptionMock);
		Message<?> failedMessageMock = (Message<?>) mock(Message.class);
		doReturn(failedMessageMock).when(messagingExceptionMock)
				.getFailedMessage();
		String origMessagePayload = "origMessagePayload";
		doReturn(origMessagePayload).when(failedMessageMock).getPayload();
		Throwable exMock = mock(Throwable.class);
		when(messagingExceptionMock.getCause()).thenReturn(exMock);

		String expectedDetails = "bla";
		String expectedSubject = "Consent signed/revoked message hanlding error";

		sut.handleError(errorMessageMock);

		verify(notificationPublisher, times(1)).publish(
				argThat(new IsNotificationAsExpected(expectedSubject,
						expectedDetails)));
	}

	private class IsNotificationAsExpected extends
			ArgumentMatcher<Notification> {
		private String expectedSubject;
		private String expectedDetails;

		public IsNotificationAsExpected(String expectedSubject,
				String expectedDetails) {
			this.expectedSubject = expectedSubject;
			this.expectedDetails = expectedDetails;
		}

		@Override
		public boolean matches(Object argument) {
			Notification notification = (Notification) argument;
			if (notification.getSubject().equals(expectedSubject)
					&& StringUtils.hasText(notification.getDetails())) {
				return true;
			}
			return false;
		}
	}
}
