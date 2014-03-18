package gov.samhsa.consent2share.si;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.integration.Message;
import org.springframework.integration.MessagingException;

@RunWith(MockitoJUnitRunner.class)
public class UnackExceptionHandlerTest {

	@Mock
	private NotificationPublisher notificationPublisher;

	@InjectMocks
	UnackExceptionHandler sut;

	@Test(expected = IllegalArgumentException.class)
	public void testHandError_Throws_Exception_Given_Null_Message() throws Throwable {
		sut.handError(null);
	}

	public void testHandError_ReThrows_Exception_As_Expected() {
		Message<MessagingException> errorMessageMock = (Message<MessagingException>) mock(Message.class);
		MessagingException messagingExceptionMock = mock(MessagingException.class);
		when(errorMessageMock.getPayload()).thenReturn(messagingExceptionMock);

		Throwable exMock = mock(Throwable.class);
		when(messagingExceptionMock.getCause()).thenReturn(exMock);


		try {
			sut.handError(errorMessageMock);
		} catch (Throwable e) {
			assertEquals(exMock, e);
		}
	}
}
