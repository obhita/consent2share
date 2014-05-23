package gov.samhsa.consent2share.infrastructure.eventlistener;

import gov.samhsa.consent2share.infrastructure.security.AuthenticatedUser;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.infrastructure.securityevent.UnauthorizedAccessAttemptedEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthorizationFailureEventListenerTest {
	
	final static String IP_ADDRESS="192.168.0.1";
	final static String USER_NAME="user1";
	
	@Mock
	UserContext userContext;
	
	@Mock
	EventService eventService;
	
	@InjectMocks
	AuthorizationFailureEventListener authorizationFailureEventListener=new AuthorizationFailureEventListener();
	
	@Test
	public void testHandle() {
		AuthorizationFailureEvent event=mock(AuthorizationFailureEvent.class);
		WebAuthenticationDetails details=mock(WebAuthenticationDetails.class);
		Authentication authentication=mock(Authentication.class);
		AuthenticatedUser user=mock(AuthenticatedUser.class);
		doReturn(authentication).when(event).getAuthentication();
		doReturn(details).when(authentication).getDetails();
		doReturn(IP_ADDRESS).when(details).getRemoteAddress();
		doReturn(user).when(userContext).getCurrentUser();
		doReturn(USER_NAME).when(user).getUsername();
		authorizationFailureEventListener.handle(event);
		verify(eventService).raiseSecurityEvent(any(UnauthorizedAccessAttemptedEvent.class));
		
	}

}
