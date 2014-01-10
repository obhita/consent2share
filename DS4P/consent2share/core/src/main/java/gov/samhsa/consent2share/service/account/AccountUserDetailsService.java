package gov.samhsa.consent2share.service.account;

import java.util.Calendar;

import gov.samhsa.consent2share.domain.account.Users;
import gov.samhsa.consent2share.domain.account.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AccountUserDetailsService implements UserDetailsService{
	
	@Autowired
	UsersRepository usersRepository;
	
	@Value("${maximum_failed_attempts}") 
    private short maxFailedAttempts;
	
	@Value("${auto_unlock_interval}") 
	private long autoUnlockInterval;
	
	@SuppressWarnings("unused")
	private AccountUserDetailsService(){}
	
	public AccountUserDetailsService(short maxFailedAttempts,long autoUnlockInterval){
		this.maxFailedAttempts=maxFailedAttempts;
		this.autoUnlockInterval=autoUnlockInterval;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Users user=usersRepository.loadUserByUsername(username);
		if (user==null){
			throw new UsernameNotFoundException("Invalid username/password");
		}
		if (user.getFailedLoginAttempts()>=maxFailedAttempts){
			Calendar cal=Calendar.getInstance();
			if (user.getLockoutTime()!=null)
			if (cal.getTimeInMillis()-user.getLockoutTime().getTimeInMillis()>=autoUnlockInterval){
				user.setLockoutTime(null);
				user.setFailedLoginAttemptsToZero();
				return user;
			}
			throw new LockedException("Your account has excceded maximum failed athentication attempts. Please wait 5 minutes to retry.");
		}
		return user;
	}

}
