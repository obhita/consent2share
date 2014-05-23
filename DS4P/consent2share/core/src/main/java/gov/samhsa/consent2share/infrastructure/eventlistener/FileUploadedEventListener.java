package gov.samhsa.consent2share.infrastructure.eventlistener;

import java.util.Map;

import gov.samhsa.acs.audit.PredicateKey;
import gov.samhsa.consent2share.domain.SecurityEvent;
import gov.samhsa.consent2share.infrastructure.securityevent.FileUploadedEvent;
import gov.samhsa.consent2share.infrastructure.securityevent.SecurityAuditVerb;
import gov.samhsa.consent2share.infrastructure.securityevent.SecurityPredicateKey;

import org.springframework.stereotype.Component;

import ch.qos.logback.audit.AuditException;

@Component("fileUploadedEventListener")
public class FileUploadedEventListener extends SecurityEventListener {

	@Override
	public boolean canHandle(Object event) {
		return event instanceof
				FileUploadedEvent;
	}

	@Override
	public void handle(Object event) {
		super.handle(event);
	}

	@Override
	public void audit(SecurityEvent event) {
		FileUploadedEvent fileUploadedEvent=(FileUploadedEvent)event;
		Map<PredicateKey, String> predicateMap = auditService
				.createPredicateMap();
		predicateMap.put(SecurityPredicateKey.IP_ADDRESS, event.getIpAddress());
		try {
			auditService.audit("FileUploadedEventListener", fileUploadedEvent.getUserName(), SecurityAuditVerb.UPLOADS_FILE,
					fileUploadedEvent.getFileUploaded(), predicateMap);
		} catch (AuditException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
