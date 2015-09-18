package gov.samhsa.consent2share.si.rest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import gov.samhsa.consent2share.si.EndpointStarter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EndpointControllerTest {
	@Mock
	private EndpointStarter inboundChannelAdapterEndpointStarter;

	@InjectMocks
	private EndpointController sut;

	@Test
	public void testStart() {
		sut.start();

		verify(inboundChannelAdapterEndpointStarter, times(1)).start();
	}
}
