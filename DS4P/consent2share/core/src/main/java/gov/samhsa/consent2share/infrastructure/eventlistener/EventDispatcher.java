// EventDispatcher.java
package gov.samhsa.consent2share.infrastructure.eventlistener;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

@Component("eventDispatcher")
public class EventDispatcher implements
     org.springframework.context.ApplicationListener {

    List<EventListener> listeners = new ArrayList<EventListener>();

    /**
     * Method that allows registering of an Event Listener.
     */
    public void registerListener(EventListener listener) {
        listeners.add(listener);
    }

    /**
     * Spring executes this method with the event object.
     * This method iterates though the list of registered
     * Listeners and checks whether any listener can
     * handle the event. Calls handle method of the
     * Listener if it can handle the event.
     */
    public void onApplicationEvent(ApplicationEvent event) {
        for (EventListener listener: listeners) {
            if (listener.canHandle(event)) {
                listener.handle(event);
            }
        }
    }
}
