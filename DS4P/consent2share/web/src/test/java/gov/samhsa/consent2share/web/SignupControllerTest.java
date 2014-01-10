package gov.samhsa.consent2share.web;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import gov.samhsa.consent2share.domain.account.Users;
import gov.samhsa.consent2share.infrastructure.FieldValidator;
import gov.samhsa.consent2share.service.account.AccountService;
import gov.samhsa.consent2share.service.account.AccountVerificationService;
import gov.samhsa.consent2share.service.reference.AdministrativeGenderCodeService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class SignupControllerTest {

	@Mock
	AccountService accountService;
	
	@Mock
	AdministrativeGenderCodeService administrativeGenderCodeService;
	
	@Mock
	AccountVerificationService accountVerificationService;
	
	@Mock
	FieldValidator fieldValidator;
	
	MockMvc mockMvc;
	
	@Before
	public void before() {
		SignupController signupController = new SignupController(accountService, administrativeGenderCodeService, fieldValidator, accountVerificationService);
		mockMvc = MockMvcBuilders.standaloneSetup(signupController).build();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor_when_accountService_is_null_verify_exception_is_thrown(){
		new SignupController(null,administrativeGenderCodeService,fieldValidator,accountVerificationService);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor_when_administrativeGenderCodeService_is_null_verify_exception_is_thrown(){
		new SignupController(accountService,null,fieldValidator,accountVerificationService);
	}
	
	@Test
	public void testSignupModel() throws Exception {
		mockMvc.perform(get("/registration.html"))
			.andExpect(view().name("views/registration"));
	}

	@Test
	public void testSignupSignupDtoBindingResultHttpServletRequestRedirectAttributesModel() throws Exception {
		mockMvc.perform(post("/registration.html"))
			.andExpect(view().name("views/registration"));
		mockMvc.perform(post("/registration.html")
			.param("genderCode", "M"))
				.andExpect(view().name("views/signupVerification"));
		Users user = mock(Users.class);
		when(accountService.findUserByUsername(anyString())).thenReturn(user);
		mockMvc.perform(post("/registration.html")
			.param("genderCode", "M"))
				.andExpect(view().name("views/registration"));
	}

	@Test
	public void testVerifyLink() throws Exception {
		when(accountVerificationService.isAccountVerificationTokenExpired(anyString())).thenReturn(true);
		mockMvc.perform(get("/verifyLink.html?token=914653028441015461098868301011413486798"))
			.andExpect(status().isOk())
			.andExpect(view().name("views/signupVerification"));
		when(accountVerificationService.isAccountVerificationTokenExpired(anyString())).thenReturn(false);
		mockMvc.perform(get("/verifyLink.html?token=914653028441015461098868301011413486798"))
		 	.andExpect(redirectedUrl("/index.html"));
	}

}
