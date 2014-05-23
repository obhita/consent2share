package gov.samhsa.consent2share.infrastructure.eventlistener;

import java.util.Map;

import gov.samhsa.acs.audit.PredicateKey;
import gov.samhsa.consent2share.domain.SecurityEvent;
import gov.samhsa.consent2share.infrastructure.securityevent.AuthenticationFailedEvent;
import gov.samhsa.consent2share.infrastructure.securityevent.FileDownloadedEvent;
import gov.samhsa.consent2share.infrastructure.securityevent.SecurityAuditVerb;
import gov.samhsa.consent2share.infrastructure.securityevent.SecurityPredicateKey;

import org.springframework.stereotype.Component;

import ch.qos.logback.audit.AuditException;

@Component("fileDownloadedEventListener")
public class FileDownloadedEventListener extends SecurityEventListener {

	@Override
	public boolean canHandle(Object event) {
		return event instanceof
				FileDownloadedEvent;
	}

	@Override
	public void handle(Object event) {
		super.handle(event);

	}

	@Override
	public void audit(SecurityEvent event) {
		FileDownloadedEvent fileDownloadedEvent=(FileDownloadedEvent)event;
		Map<PredicateKey, String> predicateMap = auditService
				.createPredicateMap();
		predicateMap.put(SecurityPredicateKey.IP_ADDRESS, event.getIpAddress());
		try {
			auditService.audit("FileDownloadedEventListener", fileDownloadedEvent.getUserName(), SecurityAuditVerb.DOWNLOADS_FILE,
					fileDownloadedEvent.getFileDownloaded(), predicateMap);
		} catch (AuditException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
