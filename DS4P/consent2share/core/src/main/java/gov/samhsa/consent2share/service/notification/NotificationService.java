package gov.samhsa.consent2share.service.notification;

import gov.samhsa.consent2share.domain.consent.Consent;

import java.util.Set;

import org.springframework.security.access.annotation.Secured;

@Secured("ROLE_USER")
public interface NotificationService {
	String notificationStage(String username, String notify);

	boolean checkConsentReviewStatus(Set<Consent> consents);

	boolean checkConsentSignedStatus(Set<Consent> consents);

}
