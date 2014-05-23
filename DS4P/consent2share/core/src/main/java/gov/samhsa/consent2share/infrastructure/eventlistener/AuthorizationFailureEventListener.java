package gov.samhsa.consent2share.infrastructure.eventlistener;

import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.infrastructure.securityevent.UnauthorizedAccessAttemptedEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component("authorizationFailureEventListener")
public class AuthorizationFailureEventListener extends EventListener {
	
	@Autowired
	UserContext userContext;

	@Override
	public boolean canHandle(Object event) {
		return event instanceof
				AuthorizationFailureEvent;
	}

	@Override
	public void handle(Object event) {
		WebAuthenticationDetails details=(WebAuthenticationDetails) ((AuthorizationFailureEvent)event).getAuthentication().getDetails();
		eventService.raiseSecurityEvent(new UnauthorizedAccessAttemptedEvent(details.getRemoteAddress(),userContext.getCurrentUser().getUsername()));
	}

}
