package gov.samhsa.consent2share.si;

import org.springframework.beans.factory.annotation.Autowired;

public class EndpointStarter {
	@Autowired
	private BusController busController;

	@Autowired
	private NotificationPublisher notificationPublisher;

	private EndpointStopper endpointStopperCounterpart;

	public void setEndpointStopperCounterpart(
			EndpointStopper endpointStopperCounterpart) {
		this.endpointStopperCounterpart = endpointStopperCounterpart;
	}

	public void start() {
		// Clean up EndpointStopper counter
		endpointStopperCounterpart.setCounter(0);

		String groovyCommand = endpointStopperCounterpart.getEndpointNeedToStop() + ".start()";
		busController.send(groovyCommand);

		Notification notification = new Notification();
		notification.setDetails(groovyCommand + " was called.");
		notification.setSubject("The endpoint of si-c2s-xds was started");

		notificationPublisher.publish(notification);
	}
}
