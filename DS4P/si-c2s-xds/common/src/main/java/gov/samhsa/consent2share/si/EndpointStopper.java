package gov.samhsa.consent2share.si;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class EndpointStopper {
	private final AtomicInteger counter = new AtomicInteger();

	@Autowired
	private BusController busController;

	@Autowired
	private NotificationPublisher notificationPublisher;

	private String endpointNeedToStop;
	private int numberOfTryingTimes;
	private int intervalIncrementalInMillisBetweenTrying;

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	public int getCounter() {
		return this.counter.get();
	}

	public void setCounter(int value) {
		this.counter.set(value);
	}

	public void increment() {
		this.counter.incrementAndGet();
	}

	public String getEndpointNeedToStop() {
		return this.endpointNeedToStop;
	}

	public void setEndpointNeedToStop(String endpointName) {
		this.endpointNeedToStop = endpointName;
	}

	public void setNumberOfTryingTimes(int numberOfTryingTimes) {
		this.numberOfTryingTimes = numberOfTryingTimes;
	}

	public void setIntervalIncrementalInMillisBetweenTrying(int intervalIncrementalInMillisBetweenTrying){
		this.intervalIncrementalInMillisBetweenTrying = intervalIncrementalInMillisBetweenTrying;
	}

	public void tryToStop() throws Throwable {
		increment();

		if (getCounter() >= numberOfTryingTimes) {
			String groovyCommand = endpointNeedToStop + ".stop()";
			busController.send(groovyCommand);
			setCounter(0);

			Notification notification = new Notification();
			notification.setDetails(groovyCommand + " was called. Please check as soon as possible.");
			notification.setSubject("The endpoint of si-c2s-xds stopped working");

			notificationPublisher.publish(notification);
		} else {
			int delayInMillis = intervalIncrementalInMillisBetweenTrying * getCounter();
			sleep(delayInMillis);
		}
	}

	void sleep(int intervalInMillisBetweenTrying) {
		try {
			Thread.sleep(intervalInMillisBetweenTrying);
		} catch (InterruptedException e) {
			logger.error("Error occured when trying to sleep in " + this.getClass().getCanonicalName(), e);
		}
	}
}
