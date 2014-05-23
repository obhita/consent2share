package gov.samhsa.consent2share.infrastructure.eventlistener;

import java.util.Map;

import gov.samhsa.acs.audit.PredicateKey;
import gov.samhsa.consent2share.domain.SecurityEvent;
import gov.samhsa.consent2share.infrastructure.securityevent.FileUploadedEvent;
import gov.samhsa.consent2share.infrastructure.securityevent.MaliciousFileDetectedEvent;
import gov.samhsa.consent2share.infrastructure.securityevent.SecurityAuditVerb;
import gov.samhsa.consent2share.infrastructure.securityevent.SecurityPredicateKey;

import org.springframework.stereotype.Component;

import ch.qos.logback.audit.AuditException;

@Component("maliciousFileDetectedEventListener")
public class MaliciousFileDetectedEventListener extends SecurityEventListener {

	@Override
	public boolean canHandle(Object event) {
		return event instanceof
				MaliciousFileDetectedEvent;
	}

	@Override
	public void handle(Object event) {
		super.handle(event);
		System.out.println("maliciousFileDetectedEventListener");

	}

	@Override
	public void audit(SecurityEvent event) {
		MaliciousFileDetectedEvent maliciousFileDetectedEvent=(MaliciousFileDetectedEvent)event;
		Map<PredicateKey, String> predicateMap = auditService
				.createPredicateMap();
		predicateMap.put(SecurityPredicateKey.IP_ADDRESS, event.getIpAddress());
		try {
			auditService.audit("MaliciousFileDetectedEventListener", 
					maliciousFileDetectedEvent.getUserName(), 
					SecurityAuditVerb.UPLOADS_MALICIOUS_FILE,
					maliciousFileDetectedEvent.getFileName(), 
					predicateMap);
		} catch (AuditException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
