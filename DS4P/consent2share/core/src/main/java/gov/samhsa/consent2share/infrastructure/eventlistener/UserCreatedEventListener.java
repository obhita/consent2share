package gov.samhsa.consent2share.infrastructure.eventlistener;

import java.util.Map;

import gov.samhsa.acs.audit.PredicateKey;
import gov.samhsa.consent2share.domain.SecurityEvent;
import gov.samhsa.consent2share.infrastructure.securityevent.SecurityAuditVerb;
import gov.samhsa.consent2share.infrastructure.securityevent.UserCreatedEvent;

import org.springframework.stereotype.Component;

import ch.qos.logback.audit.AuditException;

@Component("userCreatedEventListener")
public class UserCreatedEventListener extends SecurityEventListener {

	@Override
	public boolean canHandle(Object event) {
		return event instanceof
				UserCreatedEvent;
	}

	@Override
	public void handle(Object event) {
		super.handle(event);
	}

	@Override
	public void audit(SecurityEvent event) {
		UserCreatedEvent userCreatedEvent=(UserCreatedEvent)event;
		Map<PredicateKey, String> predicateMap = auditService
				.createPredicateMap();
		try {
			auditService.audit("UserCreatedEventListener", 
					userCreatedEvent.getIpAddress(), 
					SecurityAuditVerb.CREATES_USER,
					userCreatedEvent.getUserName(), 
					predicateMap);
		} catch (AuditException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
