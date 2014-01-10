package gov.samhsa.consent2share.web;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import gov.samhsa.consent2share.domain.account.Users;
import gov.samhsa.consent2share.domain.account.UsersRepository;
import gov.samhsa.consent2share.infrastructure.FieldValidator;
import gov.samhsa.consent2share.infrastructure.PixQueryService;
import gov.samhsa.consent2share.infrastructure.security.AuthenticatedUser;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.service.dto.LookupDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;
import gov.samhsa.consent2share.service.notification.NotificationService;
import gov.samhsa.consent2share.service.patient.PatientService;
import gov.samhsa.consent2share.service.reference.AdministrativeGenderCodeService;
import gov.samhsa.consent2share.service.reference.LanguageCodeService;
import gov.samhsa.consent2share.service.reference.MaritalStatusCodeService;
import gov.samhsa.consent2share.service.reference.RaceCodeService;
import gov.samhsa.consent2share.service.reference.ReligiousAffiliationCodeService;
import gov.samhsa.consent2share.service.reference.StateCodeService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@RunWith(MockitoJUnitRunner.class)
public class PatientControllerTest {

	@InjectMocks
	private PatientController sut = new PatientController();

	@Mock
	PatientService patientService;

	@Mock
	AdministrativeGenderCodeService administrativeGenderCodeService;

	@Mock
	LanguageCodeService languageCodeService;

	@Mock
	MaritalStatusCodeService maritalStatusCodeService;

	@Mock
	RaceCodeService raceCodeService;

	@Mock
	ReligiousAffiliationCodeService religiousAffiliationCodeService;

	@Mock
	StateCodeService stateCodeService;
	
	@Mock
	NotificationService notificationService;
	
	@Mock
	UserContext userContext;
	
	@Mock
	PixQueryService pixQueryService;

	@Mock
	private FieldValidator fieldValidator;

	private MockMvc mockMvc;
	
	final String validFirstName = "Tom";
	final String validLastName = "Lee";
	final String validBirthDate = "1/1/1950";
	final String validEmail = "test@test.com";
	final String validGenderCode = "administrativeGenderCode";
	final String validMrn = "PUI100000000001";
	final String validEid = "1c5c59f0-5788-11e3-84b3-00155d3a2124";

	@Before
	public void setUp() {
		mockMvc = standaloneSetup(this.sut).build();
	}

	@Test
	public void testProfile_When_HttpGet() throws Exception {
		final String username = "username";

		final AuthenticatedUser currentUser = mock(AuthenticatedUser.class);
		when(currentUser.getUsername()).thenReturn(username);

		when(userContext.getCurrentUser()).thenReturn(currentUser);

		final PatientProfileDto patientProfile = new PatientProfileDto();
		when(patientService.findPatientProfileByUsername(username)).thenReturn(
				patientProfile);

		List<LookupDto> administrativeGenderCodes = (List<LookupDto>) mock(List.class);
		when(
				administrativeGenderCodeService
						.findAllAdministrativeGenderCodes()).thenReturn(
				administrativeGenderCodes);

		List<LookupDto> maritalStatusCodes = (List<LookupDto>) mock(List.class);
		when(maritalStatusCodeService.findAllMaritalStatusCodes())
				.thenReturn(maritalStatusCodes);

		List<LookupDto> religiousAffiliationCodes = (List<LookupDto>) mock(List.class);
		when(
				religiousAffiliationCodeService
						.findAllReligiousAffiliationCodes()).thenReturn(
				religiousAffiliationCodes);

		List<LookupDto> raceCodes = (List<LookupDto>) mock(List.class);
		when(raceCodeService.findAllRaceCodes()).thenReturn(raceCodes);

		List<LookupDto> languageCodes = (List<LookupDto>) mock(List.class);
		when(languageCodeService.findAllLanguageCodes()).thenReturn(
				languageCodes);

		List<LookupDto> stateCodes = (List<LookupDto>) mock(List.class);
		when(stateCodeService.findAllStateCodes()).thenReturn(stateCodes);

		mockMvc.perform(get("/patients/profile.html"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("patientProfileDto"))
				.andExpect(
						model().attribute("patientProfileDto",
								equalTo(patientProfile)))
				.andExpect(
						model().attribute("currentUser", equalTo(currentUser)))
				.andExpect(
						model().attribute("administrativeGenderCodes",
								equalTo(administrativeGenderCodes)))
				.andExpect(model().attributeExists("maritalStatusCodes"))
				.andExpect(
						model().attribute("maritalStatusCodes",
								equalTo(maritalStatusCodes)))
				.andExpect(
						model().attribute("religiousAffiliationCodes",
								equalTo(religiousAffiliationCodes)))
				.andExpect(model().attribute("raceCodes", equalTo(raceCodes)))
				.andExpect(
						model().attribute("languageCodes",
								equalTo(languageCodes)))
				.andExpect(model().attribute("stateCodes", equalTo(stateCodes)))
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("views/patients/profile"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testProfile_When_HttpPost_With_Empty_Param_First_Name()
			throws Exception {

		fieldValidator = new FieldValidator();
		ReflectionTestUtils.setField(sut, "fieldValidator", fieldValidator);

		List<LookupDto> administrativeGenderCodes = (List<LookupDto>) mock(List.class);
		when(
				administrativeGenderCodeService
						.findAllAdministrativeGenderCodes()).thenReturn(
				administrativeGenderCodes);

		List<LookupDto> maritalStatusCodes = (List<LookupDto>) mock(List.class);
		when(maritalStatusCodeService.findAllMaritalStatusCodes())
				.thenReturn(maritalStatusCodes);

		List<LookupDto> religiousAffiliationCodes = (List<LookupDto>) mock(List.class);
		when(
				religiousAffiliationCodeService
						.findAllReligiousAffiliationCodes()).thenReturn(
				religiousAffiliationCodes);

		List<LookupDto> raceCodes = (List<LookupDto>) mock(List.class);
		when(raceCodeService.findAllRaceCodes()).thenReturn(raceCodes);

		List<LookupDto> languageCodes = (List<LookupDto>) mock(List.class);
		when(languageCodeService.findAllLanguageCodes()).thenReturn(
				languageCodes);

		List<LookupDto> stateCodes = (List<LookupDto>) mock(List.class);
		when(stateCodeService.findAllStateCodes()).thenReturn(stateCodes);

		mockMvc.perform(
				post("/patients/profile.html")
						.param("firstName", " ")
						.param("lastName", validLastName )
						.param("birthDate", validBirthDate)
						.param("email", "ex@test.com")
						.param(validGenderCode ,
								validGenderCode ))
				.andExpect(status().isOk())
				.andExpect(
						model().attribute("administrativeGenderCodes",
								equalTo(administrativeGenderCodes)))
				.andExpect(model().attributeExists("maritalStatusCodes"))
				.andExpect(
						model().attribute("maritalStatusCodes",
								equalTo(maritalStatusCodes)))
				.andExpect(
						model().attribute("religiousAffiliationCodes",
								equalTo(religiousAffiliationCodes)))
				.andExpect(model().attribute("raceCodes", equalTo(raceCodes)))
				.andExpect(
						model().attribute("languageCodes",
								equalTo(languageCodes)))
				.andExpect(model().attribute("stateCodes", equalTo(stateCodes)))
				.andExpect(model().hasErrors())
				.andExpect(view().name("views/patients/profile"))
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void testHome_when_authority_is_ROLE_USER() throws Exception {
		final String username = "username";
		final AuthenticatedUser currentUser = mock(AuthenticatedUser.class);
		when(currentUser.getUsername()).thenReturn(username);
		when(userContext.getCurrentUser()).thenReturn(currentUser);
		mockMvc.perform(get("/patients/home.html"))
			.andExpect(view().name("views/patients/home"));
	}


	@Test
	public void testProfile_When_HttpPost_With_Invalide_Length_Param_First_Name()
			throws Exception {

		fieldValidator = new FieldValidator();
		ReflectionTestUtils.setField(sut, "fieldValidator", fieldValidator);

		List<LookupDto> administrativeGenderCodes = (List<LookupDto>) mock(List.class);
		when(
				administrativeGenderCodeService
						.findAllAdministrativeGenderCodes()).thenReturn(
				administrativeGenderCodes);

		List<LookupDto> maritalStatusCodes = (List<LookupDto>) mock(List.class);
		when(maritalStatusCodeService.findAllMaritalStatusCodes())
				.thenReturn(maritalStatusCodes);

		List<LookupDto> religiousAffiliationCodes = (List<LookupDto>) mock(List.class);
		when(
				religiousAffiliationCodeService
						.findAllReligiousAffiliationCodes()).thenReturn(
				religiousAffiliationCodes);

		List<LookupDto> raceCodes = (List<LookupDto>) mock(List.class);
		when(raceCodeService.findAllRaceCodes()).thenReturn(raceCodes);

		List<LookupDto> languageCodes = (List<LookupDto>) mock(List.class);
		when(languageCodeService.findAllLanguageCodes()).thenReturn(
				languageCodes);

		List<LookupDto> stateCodes = (List<LookupDto>) mock(List.class);
		when(stateCodeService.findAllStateCodes()).thenReturn(stateCodes);

		mockMvc.perform(
				post("/patients/profile.html")
						.param("firstName", "1")
						.param("lastName", validLastName )
						.param("birthDate", validBirthDate)
						.param("email", "ex@test.com")
						.param(validGenderCode ,
								validGenderCode ))
				.andExpect(status().isOk())
				.andExpect(
						model().attribute("administrativeGenderCodes",
								equalTo(administrativeGenderCodes)))
				.andExpect(model().attributeExists("maritalStatusCodes"))
				.andExpect(
						model().attribute("maritalStatusCodes",
								equalTo(maritalStatusCodes)))
				.andExpect(
						model().attribute("religiousAffiliationCodes",
								equalTo(religiousAffiliationCodes)))
				.andExpect(model().attribute("raceCodes", equalTo(raceCodes)))
				.andExpect(
						model().attribute("languageCodes",
								equalTo(languageCodes)))
				.andExpect(model().attribute("stateCodes", equalTo(stateCodes)))
				.andExpect(model().hasErrors())
				.andExpect(view().name("views/patients/profile"))
				.andDo(MockMvcResultHandlers.print());

		mockMvc.perform(
				post("/patients/profile.html")
						.param("firstName", "1234567890123456789012345678901")
						.param("lastName", validLastName )
						.param("birthDate", validBirthDate)
						.param("email", "ex@test.com")
						.param(validGenderCode ,
								validGenderCode ))
				.andExpect(status().isOk())
				.andExpect(
						model().attribute("administrativeGenderCodes",
								equalTo(administrativeGenderCodes)))
				.andExpect(model().attributeExists("maritalStatusCodes"))
				.andExpect(
						model().attribute("maritalStatusCodes",
								equalTo(maritalStatusCodes)))
				.andExpect(
						model().attribute("religiousAffiliationCodes",
								equalTo(religiousAffiliationCodes)))
				.andExpect(model().attribute("raceCodes", equalTo(raceCodes)))
				.andExpect(
						model().attribute("languageCodes",
								equalTo(languageCodes)))
				.andExpect(model().attribute("stateCodes", equalTo(stateCodes)))
				.andExpect(model().hasErrors())
				.andExpect(view().name("views/patients/profile"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testProfile_When_HttpPost_With_Empty_Param_Last_Name()
			throws Exception {

		fieldValidator = new FieldValidator();
		ReflectionTestUtils.setField(sut, "fieldValidator", fieldValidator);

		List<LookupDto> administrativeGenderCodes = (List<LookupDto>) mock(List.class);
		when(
				administrativeGenderCodeService
						.findAllAdministrativeGenderCodes()).thenReturn(
				administrativeGenderCodes);

		List<LookupDto> maritalStatusCodes = (List<LookupDto>) mock(List.class);
		when(maritalStatusCodeService.findAllMaritalStatusCodes())
				.thenReturn(maritalStatusCodes);

		List<LookupDto> religiousAffiliationCodes = (List<LookupDto>) mock(List.class);
		when(
				religiousAffiliationCodeService
						.findAllReligiousAffiliationCodes()).thenReturn(
				religiousAffiliationCodes);

		List<LookupDto> raceCodes = (List<LookupDto>) mock(List.class);
		when(raceCodeService.findAllRaceCodes()).thenReturn(raceCodes);

		List<LookupDto> languageCodes = (List<LookupDto>) mock(List.class);
		when(languageCodeService.findAllLanguageCodes()).thenReturn(
				languageCodes);

		List<LookupDto> stateCodes = (List<LookupDto>) mock(List.class);
		when(stateCodeService.findAllStateCodes()).thenReturn(stateCodes);

		mockMvc.perform(
				post("/patients/profile.html")
						.param("lastName", " ")
						.param("firstName",validFirstName)
						.param("birthDate", validBirthDate)
						.param("email", "ex@test.com")
						.param(validGenderCode ,
								validGenderCode ))
				.andExpect(status().isOk())
				.andExpect(
						model().attribute("administrativeGenderCodes",
								equalTo(administrativeGenderCodes)))
				.andExpect(model().attributeExists("maritalStatusCodes"))
				.andExpect(
						model().attribute("maritalStatusCodes",
								equalTo(maritalStatusCodes)))
				.andExpect(
						model().attribute("religiousAffiliationCodes",
								equalTo(religiousAffiliationCodes)))
				.andExpect(model().attribute("raceCodes", equalTo(raceCodes)))
				.andExpect(
						model().attribute("languageCodes",
								equalTo(languageCodes)))
				.andExpect(model().attribute("stateCodes", equalTo(stateCodes)))
				.andExpect(model().hasErrors())
				.andExpect(view().name("views/patients/profile"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testProfile_When_HttpPost_With_Invalide_Length_Param_Last_Name()
			throws Exception {

		fieldValidator = new FieldValidator();
		ReflectionTestUtils.setField(sut, "fieldValidator", fieldValidator);

		List<LookupDto> administrativeGenderCodes = (List<LookupDto>) mock(List.class);
		when(
				administrativeGenderCodeService
						.findAllAdministrativeGenderCodes()).thenReturn(
				administrativeGenderCodes);

		List<LookupDto> maritalStatusCodes = (List<LookupDto>) mock(List.class);
		when(maritalStatusCodeService.findAllMaritalStatusCodes())
				.thenReturn(maritalStatusCodes);

		List<LookupDto> religiousAffiliationCodes = (List<LookupDto>) mock(List.class);
		when(
				religiousAffiliationCodeService
						.findAllReligiousAffiliationCodes()).thenReturn(
				religiousAffiliationCodes);

		List<LookupDto> raceCodes = (List<LookupDto>) mock(List.class);
		when(raceCodeService.findAllRaceCodes()).thenReturn(raceCodes);

		List<LookupDto> languageCodes = (List<LookupDto>) mock(List.class);
		when(languageCodeService.findAllLanguageCodes()).thenReturn(
				languageCodes);

		List<LookupDto> stateCodes = (List<LookupDto>) mock(List.class);
		when(stateCodeService.findAllStateCodes()).thenReturn(stateCodes);

		mockMvc.perform(
				post("/patients/profile.html")
						.param("lastName", "1")
						.param("firstName",validFirstName)
						.param("birthDate", validBirthDate)
						.param("email", "ex@test.com")
						.param(validGenderCode ,
								validGenderCode ))
				.andExpect(status().isOk())
				.andExpect(
						model().attribute("administrativeGenderCodes",
								equalTo(administrativeGenderCodes)))
				.andExpect(model().attributeExists("maritalStatusCodes"))
				.andExpect(
						model().attribute("maritalStatusCodes",
								equalTo(maritalStatusCodes)))
				.andExpect(
						model().attribute("religiousAffiliationCodes",
								equalTo(religiousAffiliationCodes)))
				.andExpect(model().attribute("raceCodes", equalTo(raceCodes)))
				.andExpect(
						model().attribute("languageCodes",
								equalTo(languageCodes)))
				.andExpect(model().attribute("stateCodes", equalTo(stateCodes)))
				.andExpect(model().hasErrors())
				.andExpect(view().name("views/patients/profile"))
				.andDo(MockMvcResultHandlers.print());

		mockMvc.perform(
				post("/patients/profile.html")
						.param("lastName", "1234567890123456789012345678901")
						.param("firstName",validFirstName)
						.param("birthDate", validBirthDate)
						.param("email", "ex@test.com")
						.param(validGenderCode ,
								validGenderCode ))
				.andExpect(status().isOk())
				.andExpect(
						model().attribute("administrativeGenderCodes",
								equalTo(administrativeGenderCodes)))
				.andExpect(model().attributeExists("maritalStatusCodes"))
				.andExpect(
						model().attribute("maritalStatusCodes",
								equalTo(maritalStatusCodes)))
				.andExpect(
						model().attribute("religiousAffiliationCodes",
								equalTo(religiousAffiliationCodes)))
				.andExpect(model().attribute("raceCodes", equalTo(raceCodes)))
				.andExpect(
						model().attribute("languageCodes",
								equalTo(languageCodes)))
				.andExpect(model().attribute("stateCodes", equalTo(stateCodes)))
				.andExpect(model().hasErrors())
				.andExpect(view().name("views/patients/profile"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testProfile_When_HttpPost_With_Null_Param_Birth_Date()
			throws Exception {

		fieldValidator = new FieldValidator();
		ReflectionTestUtils.setField(sut, "fieldValidator", fieldValidator);

		List<LookupDto> administrativeGenderCodes = (List<LookupDto>) mock(List.class);
		when(
				administrativeGenderCodeService
						.findAllAdministrativeGenderCodes()).thenReturn(
				administrativeGenderCodes);

		List<LookupDto> maritalStatusCodes = (List<LookupDto>) mock(List.class);
		when(maritalStatusCodeService.findAllMaritalStatusCodes())
				.thenReturn(maritalStatusCodes);

		List<LookupDto> religiousAffiliationCodes = (List<LookupDto>) mock(List.class);
		when(
				religiousAffiliationCodeService
						.findAllReligiousAffiliationCodes()).thenReturn(
				religiousAffiliationCodes);

		List<LookupDto> raceCodes = (List<LookupDto>) mock(List.class);
		when(raceCodeService.findAllRaceCodes()).thenReturn(raceCodes);

		List<LookupDto> languageCodes = (List<LookupDto>) mock(List.class);
		when(languageCodeService.findAllLanguageCodes()).thenReturn(
				languageCodes);

		List<LookupDto> stateCodes = (List<LookupDto>) mock(List.class);
		when(stateCodeService.findAllStateCodes()).thenReturn(stateCodes);

		mockMvc.perform(
				post("/patients/profile.html")
						.param("firstName",validFirstName)
						.param("lastName", validLastName )
						.param("email", "ex@test.com")
						.param(validGenderCode ,
								validGenderCode ))
				.andExpect(status().isOk())
				.andExpect(
						model().attribute("administrativeGenderCodes",
								equalTo(administrativeGenderCodes)))
				.andExpect(model().attributeExists("maritalStatusCodes"))
				.andExpect(
						model().attribute("maritalStatusCodes",
								equalTo(maritalStatusCodes)))
				.andExpect(
						model().attribute("religiousAffiliationCodes",
								equalTo(religiousAffiliationCodes)))
				.andExpect(model().attribute("raceCodes", equalTo(raceCodes)))
				.andExpect(
						model().attribute("languageCodes",
								equalTo(languageCodes)))
				.andExpect(model().attribute("stateCodes", equalTo(stateCodes)))
				.andExpect(model().hasErrors())
				.andExpect(view().name("views/patients/profile"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testProfile_When_HttpPost_With_Invalid_Param_Birth_Date()
			throws Exception {

		fieldValidator = new FieldValidator();
		ReflectionTestUtils.setField(sut, "fieldValidator", fieldValidator);

		List<LookupDto> administrativeGenderCodes = (List<LookupDto>) mock(List.class);
		when(
				administrativeGenderCodeService
						.findAllAdministrativeGenderCodes()).thenReturn(
				administrativeGenderCodes);

		List<LookupDto> maritalStatusCodes = (List<LookupDto>) mock(List.class);
		when(maritalStatusCodeService.findAllMaritalStatusCodes())
				.thenReturn(maritalStatusCodes);

		List<LookupDto> religiousAffiliationCodes = (List<LookupDto>) mock(List.class);
		when(
				religiousAffiliationCodeService
						.findAllReligiousAffiliationCodes()).thenReturn(
				religiousAffiliationCodes);

		List<LookupDto> raceCodes = (List<LookupDto>) mock(List.class);
		when(raceCodeService.findAllRaceCodes()).thenReturn(raceCodes);

		List<LookupDto> languageCodes = (List<LookupDto>) mock(List.class);
		when(languageCodeService.findAllLanguageCodes()).thenReturn(
				languageCodes);

		List<LookupDto> stateCodes = (List<LookupDto>) mock(List.class);
		when(stateCodeService.findAllStateCodes()).thenReturn(stateCodes);

		mockMvc.perform(
				post("/patients/profile.html")
						.param("birthDate", "12/31/1899")
						.param("firstName", " ")
						.param("lastName", validLastName )
						.param("email", "ex@test.com")
						.param(validGenderCode ,
								validGenderCode ))
				.andExpect(
						model().attribute("administrativeGenderCodes",
								equalTo(administrativeGenderCodes)))
				.andExpect(model().attributeExists("maritalStatusCodes"))
				.andExpect(
						model().attribute("maritalStatusCodes",
								equalTo(maritalStatusCodes)))
				.andExpect(
						model().attribute("religiousAffiliationCodes",
								equalTo(religiousAffiliationCodes)))
				.andExpect(model().attribute("raceCodes", equalTo(raceCodes)))
				.andExpect(
						model().attribute("languageCodes",
								equalTo(languageCodes)))
				.andExpect(model().attribute("stateCodes", equalTo(stateCodes)))
				.andExpect(model().hasErrors())
				.andExpect(view().name("views/patients/profile"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testProfile_When_HttpPost_With_Empty_Param_Email()
			throws Exception {

		fieldValidator = new FieldValidator();
		ReflectionTestUtils.setField(sut, "fieldValidator", fieldValidator);

		List<LookupDto> administrativeGenderCodes = (List<LookupDto>) mock(List.class);
		when(
				administrativeGenderCodeService
						.findAllAdministrativeGenderCodes()).thenReturn(
				administrativeGenderCodes);

		List<LookupDto> maritalStatusCodes = (List<LookupDto>) mock(List.class);
		when(maritalStatusCodeService.findAllMaritalStatusCodes())
				.thenReturn(maritalStatusCodes);

		List<LookupDto> religiousAffiliationCodes = (List<LookupDto>) mock(List.class);
		when(
				religiousAffiliationCodeService
						.findAllReligiousAffiliationCodes()).thenReturn(
				religiousAffiliationCodes);

		List<LookupDto> raceCodes = (List<LookupDto>) mock(List.class);
		when(raceCodeService.findAllRaceCodes()).thenReturn(raceCodes);

		List<LookupDto> languageCodes = (List<LookupDto>) mock(List.class);
		when(languageCodeService.findAllLanguageCodes()).thenReturn(
				languageCodes);

		List<LookupDto> stateCodes = (List<LookupDto>) mock(List.class);
		when(stateCodeService.findAllStateCodes()).thenReturn(stateCodes);

		mockMvc.perform(
				post("/patients/profile.html")
						.param("firstName",validFirstName)
						.param("lastName", validLastName )
						.param("birthDate", validBirthDate)
						.param("email", " ")
						.param(validGenderCode ,
								validGenderCode ))
				.andExpect(
						model().attribute("administrativeGenderCodes",
								equalTo(administrativeGenderCodes)))
				.andExpect(model().attributeExists("maritalStatusCodes"))
				.andExpect(
						model().attribute("maritalStatusCodes",
								equalTo(maritalStatusCodes)))
				.andExpect(
						model().attribute("religiousAffiliationCodes",
								equalTo(religiousAffiliationCodes)))
				.andExpect(model().attribute("raceCodes", equalTo(raceCodes)))
				.andExpect(
						model().attribute("languageCodes",
								equalTo(languageCodes)))
				.andExpect(model().attribute("stateCodes", equalTo(stateCodes)))
				.andExpect(model().hasErrors())
				.andExpect(view().name("views/patients/profile"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testProfile_When_HttpPost_With_Invalid_Param_Email()
			throws Exception {

		fieldValidator = new FieldValidator();
		ReflectionTestUtils.setField(sut, "fieldValidator", fieldValidator);

		List<LookupDto> administrativeGenderCodes = (List<LookupDto>) mock(List.class);
		when(
				administrativeGenderCodeService
						.findAllAdministrativeGenderCodes()).thenReturn(
				administrativeGenderCodes);

		List<LookupDto> maritalStatusCodes = (List<LookupDto>) mock(List.class);
		when(maritalStatusCodeService.findAllMaritalStatusCodes())
				.thenReturn(maritalStatusCodes);

		List<LookupDto> religiousAffiliationCodes = (List<LookupDto>) mock(List.class);
		when(
				religiousAffiliationCodeService
						.findAllReligiousAffiliationCodes()).thenReturn(
				religiousAffiliationCodes);

		List<LookupDto> raceCodes = (List<LookupDto>) mock(List.class);
		when(raceCodeService.findAllRaceCodes()).thenReturn(raceCodes);

		List<LookupDto> languageCodes = (List<LookupDto>) mock(List.class);
		when(languageCodeService.findAllLanguageCodes()).thenReturn(
				languageCodes);

		List<LookupDto> stateCodes = (List<LookupDto>) mock(List.class);
		when(stateCodeService.findAllStateCodes()).thenReturn(stateCodes);

		mockMvc.perform(
				post("/patients/profile.html")
						.param("firstName",validFirstName)
						.param("lastName", validLastName )
						.param("birthDate", validBirthDate)
						.param("email", "not-email-address")
						.param(validGenderCode ,
								validGenderCode ))
				.andExpect(
						model().attribute("administrativeGenderCodes",
								equalTo(administrativeGenderCodes)))
				.andExpect(model().attributeExists("maritalStatusCodes"))
				.andExpect(
						model().attribute("maritalStatusCodes",
								equalTo(maritalStatusCodes)))
				.andExpect(
						model().attribute("religiousAffiliationCodes",
								equalTo(religiousAffiliationCodes)))
				.andExpect(model().attribute("raceCodes", equalTo(raceCodes)))
				.andExpect(
						model().attribute("languageCodes",
								equalTo(languageCodes)))
				.andExpect(model().attribute("stateCodes", equalTo(stateCodes)))
				.andExpect(model().hasErrors())
				.andExpect(view().name("views/patients/profile"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testProfile_When_HttpPost_With_Valid_Params() throws Exception {

		final String username = "username";

		final AuthenticatedUser currentUser = mock(AuthenticatedUser.class);
		when(currentUser.getUsername()).thenReturn(username);

		when(userContext.getCurrentUser()).thenReturn(currentUser);

		fieldValidator = new FieldValidator();
		ReflectionTestUtils.setField(sut, "fieldValidator", fieldValidator);

		List<LookupDto> administrativeGenderCodes = (List<LookupDto>) mock(List.class);
		when(
				administrativeGenderCodeService
						.findAllAdministrativeGenderCodes()).thenReturn(
				administrativeGenderCodes);

		List<LookupDto> maritalStatusCodes = (List<LookupDto>) mock(List.class);
		when(maritalStatusCodeService.findAllMaritalStatusCodes())
				.thenReturn(maritalStatusCodes);

		List<LookupDto> religiousAffiliationCodes = (List<LookupDto>) mock(List.class);
		when(
				religiousAffiliationCodeService
						.findAllReligiousAffiliationCodes()).thenReturn(
				religiousAffiliationCodes);

		List<LookupDto> raceCodes = (List<LookupDto>) mock(List.class);
		when(raceCodeService.findAllRaceCodes()).thenReturn(raceCodes);

		List<LookupDto> languageCodes = (List<LookupDto>) mock(List.class);
		when(languageCodeService.findAllLanguageCodes()).thenReturn(
				languageCodes);

		List<LookupDto> stateCodes = (List<LookupDto>) mock(List.class);
		when(stateCodeService.findAllStateCodes()).thenReturn(stateCodes);
		
		when(pixQueryService.getEid(validMrn)).thenReturn(validEid);

		mockMvc.perform(
				post("/patients/profile.html")
						.param("firstName",validFirstName)
						.param("lastName", validLastName )
						.param("birthDate", validBirthDate)
						.param("email", validEmail)
						.param(validGenderCode ,
								validGenderCode )
						.param("username", username)
						.param("password","password")
						.param("medicalRecordNumber",validMrn))
				.andExpect(
						model().attribute("currentUser", equalTo(currentUser)))
				.andExpect(
						model().attribute("updatedMessage", "Updated your profile successfully!"))
				.andExpect(
						model().attribute("administrativeGenderCodes",
								equalTo(administrativeGenderCodes)))
				.andExpect(model().attributeExists("maritalStatusCodes"))
				.andExpect(
						model().attribute("maritalStatusCodes",
								equalTo(maritalStatusCodes)))
				.andExpect(
						model().attribute("religiousAffiliationCodes",
								equalTo(religiousAffiliationCodes)))
				.andExpect(model().attribute("raceCodes", equalTo(raceCodes)))
				.andExpect(
						model().attribute("languageCodes",
								equalTo(languageCodes)))
				.andExpect(model().attribute("stateCodes", equalTo(stateCodes)))
				.andExpect(model().hasNoErrors())
				.andExpect(view().name("views/patients/profile"))
				.andDo(MockMvcResultHandlers.print());
		
		ArgumentMatcher<PatientProfileDto> patientProfileArgumentMatcher = new ArgumentMatcher<PatientProfileDto>(){

			@Override
			public boolean matches(Object argument) {
				PatientProfileDto patientProfileDto = (PatientProfileDto)argument;
				if (patientProfileDto == null){
					return false;
				}
				
				if (patientProfileDto.getAddressCountryCode() == null || !patientProfileDto.getAddressCountryCode().equals("US")){
					return false;
				}
				
				if (patientProfileDto.getUsername() == null || !patientProfileDto.getUsername().equals(username)){
					return false;
				}
				
				if (patientProfileDto.getFirstName() == null || !patientProfileDto.getFirstName().equals(validFirstName)){
					return false;
				}
				
				if (patientProfileDto.getLastName() == null || !patientProfileDto.getLastName().equals(validLastName)){
					return false;
				}
				
				if (patientProfileDto.getEmail() == null || !patientProfileDto.getEmail().equals(validEmail)){
					return false;
				}
				
				if (patientProfileDto.getAdministrativeGenderCode() == null || !patientProfileDto.getAdministrativeGenderCode().equals(validGenderCode)){
					return false;
				}
				
				if (patientProfileDto.getMedicalRecordNumber() == null || !patientProfileDto.getMedicalRecordNumber().equals(validMrn)){
					return false;
				}
				
				if (patientProfileDto.getEnterpriseIdentifier() == null || !patientProfileDto.getEnterpriseIdentifier().equals(validEid)){
					return false;
				}
				
				return true;
			}
			
		};
		
		verify(patientService, times(1)).updatePatient(org.mockito.Matchers.argThat(patientProfileArgumentMatcher));
	}
}
