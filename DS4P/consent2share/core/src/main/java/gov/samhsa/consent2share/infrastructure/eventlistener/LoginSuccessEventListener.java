package gov.samhsa.consent2share.infrastructure.eventlistener;

import gov.samhsa.consent2share.domain.account.Users;
import gov.samhsa.consent2share.domain.account.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component("loginSuccessEventListener")
public class LoginSuccessEventListener extends EventListener{
	 @Autowired
	 UsersRepository usersRepository;
	 
	@Override
	public boolean canHandle(Object event) {
		return event instanceof
				AuthenticationSuccessEvent;
	}

	@Override
	public void handle(Object event) {
		AuthenticationSuccessEvent loginSuccessEvent=(AuthenticationSuccessEvent)event;
		Object name = loginSuccessEvent.getAuthentication()
                .getPrincipal();
		if(name!=null){
			Users user=(Users)name;
			user.setFailedLoginAttemptsToZero();
			usersRepository.updateUser(user);
		}
		
	}

}
