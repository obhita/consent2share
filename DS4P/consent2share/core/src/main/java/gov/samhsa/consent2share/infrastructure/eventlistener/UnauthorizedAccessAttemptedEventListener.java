package gov.samhsa.consent2share.infrastructure.eventlistener;

import java.util.Map;

import gov.samhsa.acs.audit.PredicateKey;
import gov.samhsa.consent2share.domain.SecurityEvent;
import gov.samhsa.consent2share.infrastructure.securityevent.MaliciousFileDetectedEvent;
import gov.samhsa.consent2share.infrastructure.securityevent.SecurityAuditVerb;
import gov.samhsa.consent2share.infrastructure.securityevent.SecurityPredicateKey;
import gov.samhsa.consent2share.infrastructure.securityevent.UnauthorizedAccessAttemptedEvent;

import org.springframework.stereotype.Component;

import ch.qos.logback.audit.AuditException;

@Component("unauthorizedAccessAttemptedEventListener")
public class UnauthorizedAccessAttemptedEventListener extends
		SecurityEventListener {

	@Override
	public boolean canHandle(Object event) {
		return event instanceof
				UnauthorizedAccessAttemptedEvent;
	}
	
	@Override
	public void handle(Object event) {
		super.handle(event);
	}

	@Override
	public void audit(SecurityEvent event) {
		UnauthorizedAccessAttemptedEvent unauthorizedAccessAttemptedEvent=(UnauthorizedAccessAttemptedEvent)event;
		Map<PredicateKey, String> predicateMap = auditService
				.createPredicateMap();
		predicateMap.put(SecurityPredicateKey.IP_ADDRESS, event.getIpAddress());
		try {
			auditService.audit("UnauthorizedAccessAttemptedEventListener", 
					unauthorizedAccessAttemptedEvent.getUserName(), 
					SecurityAuditVerb.ATTEMPTS_TO_ACCESS_UNAUTHORIZED_RESOURCE,
					"Unauthorized Page", 
					predicateMap);
		} catch (AuditException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
