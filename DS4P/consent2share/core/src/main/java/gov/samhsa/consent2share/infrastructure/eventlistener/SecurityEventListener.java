package gov.samhsa.consent2share.infrastructure.eventlistener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import gov.samhsa.acs.audit.AuditService;
import gov.samhsa.consent2share.domain.SecurityEvent;

public abstract class SecurityEventListener extends EventListener {
	
	/** The audit service. */
	@Autowired
	@Qualifier("logbackAuditServiceImpl")
	protected AuditService auditService;
	
	@Override
	public void handle(Object event) {
		audit((SecurityEvent)event);
	}
	
	public abstract void audit(SecurityEvent event);
}
