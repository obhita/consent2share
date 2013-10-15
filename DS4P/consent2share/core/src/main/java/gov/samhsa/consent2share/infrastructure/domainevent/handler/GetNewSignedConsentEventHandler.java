package gov.samhsa.consent2share.infrastructure.domainevent.handler;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn("domainEvents")
public class GetNewSignedConsentEventHandler extends Handler{
	
	public GetNewSignedConsentEventHandler(){
		initialize();
	}
	
	@Override
	void execute() {
		System.out.println("Just handled a new GetNewSignedConsentEvent");
	}
	
	public String getEventType() {
		return eventType;
	}

	
}
