package gov.samhsa.consent2share.infrastructure.domainevent.handler;


import org.springframework.context.ApplicationContext;

import gov.samhsa.consent2share.domain.domainevent.DomainEvent;
import gov.samhsa.consent2share.domain.domainevent.DomainEvents;

public abstract class Handler{
	
	protected String eventType;
	
	protected ApplicationContext applicationContext;
	
	DomainEvents domainEvents;
	
	public void handle(DomainEvent domainEvent){
		if(verifyEventType(domainEvent))
			execute();
	}
	
	/**
	 * Verify event type.
	 *
	 * @param domainEvent the domain event
	 * @return true, if successful
	 */
	private boolean verifyEventType(DomainEvent domainEvent){
		
		int HandlerMatchStart=eventType.lastIndexOf(".")+1;
		int EventMatchStart=domainEvent.getClass().getName().lastIndexOf(".")+1;
		int HandlerMatchEnd=eventType.lastIndexOf("Handler");
		int EventMatchEnd=domainEvent.getClass().getName().length();
		
		if(domainEvent.getClass().getName().substring(EventMatchStart, EventMatchEnd)
				.equals(eventType.substring(HandlerMatchStart,HandlerMatchEnd)))
			return true;
		return false;
	}
	
	abstract void execute();
	
	protected void initialize(){
		eventType=this.getClass().getName();
	}
	
	public String getEventType() {
		return eventType;
	}
	
	protected void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
	
}
