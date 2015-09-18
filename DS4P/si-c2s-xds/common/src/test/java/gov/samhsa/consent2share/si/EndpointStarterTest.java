package gov.samhsa.consent2share.si;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EndpointStarterTest {
	@Mock
	private BusController busController;

	@Mock
	private NotificationPublisher notificationPublisher;

	@Mock
	private EndpointStopper endpointStopperCounterpart;

	@InjectMocks
	private EndpointStarter sut;

	@Test
	public void testStart_EndpointStopperCounterpart_Is_SetCounter_To_Zero() {
		sut.start();

		verify(endpointStopperCounterpart, times(1)).setCounter(0);
	}

	@Test
	public void testStart_BusController_Is_Sent_StartCommand() {
		final String endpointNeedToStop = "EndpointNeedToStop";
		when(endpointStopperCounterpart.getEndpointNeedToStop()).thenReturn(endpointNeedToStop);

		sut.start();

		String expectedCommand = endpointNeedToStop + ".start()";
		verify(busController, times(1)).send(expectedCommand);
	}

	@Test
	public void testStart_Notification_Is_Published() {
		final String endpointNeedToStop = "EndpointNeedToStop";
		when(endpointStopperCounterpart.getEndpointNeedToStop()).thenReturn(endpointNeedToStop);

		sut.start();

		String expectedDetails = endpointNeedToStop + ".start()"  + " was called.";
		String expectedSubject = "The endpoint of si-c2s-xds was started";
		verify(notificationPublisher, times(1)).publish(argThat(new IsNotificationAsExpected(expectedSubject, expectedDetails)));
	}

	private class IsNotificationAsExpected extends
			ArgumentMatcher<Notification> {
		private String expectedSubject;
		private String expectedDetails;

		public IsNotificationAsExpected(String expectedSubject, String expectedDetails) {
			this.expectedSubject = expectedSubject;
			this.expectedDetails = expectedDetails;
		}

		@Override
		public boolean matches(Object argument) {
			Notification notification = (Notification) argument;
			if (notification.getSubject().equals(expectedSubject) && notification.getDetails().equals(expectedDetails)) {
				return true;
			}
			return false;
		}
	}
}
