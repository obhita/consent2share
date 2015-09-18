package gov.samhsa.consent2share.si;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EndpointStopperTest {
	@Mock
	private BusController busController;

	@Mock
	private NotificationPublisher notificationPublisher;

	private final String endpointNeedToStop = "endpointNeedToStop";
	private final int numberOfTryingTimes = 3;
	private final int intervalIncrementalInMillisBetweenTrying = 0;

	@InjectMocks
	private EndpointStopper sut;

	@Before
	public void setUp() {
		sut.setEndpointNeedToStop(endpointNeedToStop);
		sut.setNumberOfTryingTimes(numberOfTryingTimes);
		sut.setIntervalIncrementalInMillisBetweenTrying(intervalIncrementalInMillisBetweenTrying);
	}

	@Test
	public void testTryToStop_Counter_Returns_Correctly() throws Throwable {
		int i = sut.getCounter();
		while (i < numberOfTryingTimes - 1) {
			sut.tryToStop();
			assertEquals(++i, sut.getCounter());
		}

		sut.setCounter(numberOfTryingTimes - 1);
		sut.tryToStop();
		assertEquals(0, sut.getCounter());

		sut.setCounter(numberOfTryingTimes);
		sut.tryToStop();
		assertEquals(0, sut.getCounter());
	}

	@Test
	public void testTryToStop_BusController_Is_Sent_StopCommand_After_NumberOfTryingTimes_Is_Reached()
			throws Throwable {
		String expectedCommand = endpointNeedToStop + ".stop()";

		sut.setCounter(numberOfTryingTimes - 1);
		sut.tryToStop();
		verify(busController, times(1)).send(expectedCommand);

		reset(busController);
		sut.setCounter(numberOfTryingTimes);
		sut.tryToStop();
		verify(busController, times(1)).send(expectedCommand);
	}

	@Test
	public void testTryToStop_BusController_Is_Not_Sent_StopCommand_Before_NumberOfTryingTimes_Is_Reached()
			throws Throwable {
		String expectedCommand = endpointNeedToStop + ".stop()";

		sut.setCounter(numberOfTryingTimes - 2);
		sut.tryToStop();
		verify(busController, times(0)).send(expectedCommand);
	}

	@Test
	public void testTryToStop_Notification_Is_Published_After_NumberOfTryingTimes_Is_Reached()
			throws Throwable {
		String expectedDetails = endpointNeedToStop + ".stop()"
				+ " was called. Please check as soon as possible.";
		String expectedSubject = "The endpoint of si-c2s-xds stopped working";

		sut.setCounter(numberOfTryingTimes - 1);
		sut.tryToStop();
		verify(notificationPublisher, times(1)).publish(
				argThat(new IsNotificationAsExpected(expectedSubject,
						expectedDetails)));

		reset(notificationPublisher);
		sut.setCounter(numberOfTryingTimes);
		sut.tryToStop();
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
					&& notification.getDetails().equals(expectedDetails)) {
				return true;
			}
			return false;
		}
	}

	@Test
	public void testTryToStop_Notification_Is_Not_Published_Before_NumberOfTryingTimes_Is_Reached()
			throws Throwable {
		sut.setCounter(numberOfTryingTimes - 2);
		sut.tryToStop();
		verify(notificationPublisher, times(0))
				.publish(any(Notification.class));
	}

	@Test
	public void testTryToStop_Sleeps_As_Expected() throws Throwable {
		sut = spy(sut);
		int intervalIncrementalInMillis = 1;
		sut.setIntervalIncrementalInMillisBetweenTrying(intervalIncrementalInMillis);

		int i = sut.getCounter();
		while (i < numberOfTryingTimes - 1) {
			sut.tryToStop();
			i++;
			verify(sut).sleep(i * intervalIncrementalInMillis);
		}

		reset(sut);
		sut.setCounter(numberOfTryingTimes - 1);
		sut.tryToStop();
		verify(sut, times(0)).sleep(anyInt());

		reset(sut);
		sut.setCounter(numberOfTryingTimes);
		sut.tryToStop();
		verify(sut, times(0)).sleep(anyInt());
	}
}
