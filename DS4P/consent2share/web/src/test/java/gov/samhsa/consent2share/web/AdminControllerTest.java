package gov.samhsa.consent2share.web;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import gov.samhsa.consent2share.infrastructure.FieldValidator;
import gov.samhsa.consent2share.infrastructure.PixQueryService;
import gov.samhsa.consent2share.infrastructure.security.AuthenticatedUser;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.service.admin.AdminService;
import gov.samhsa.consent2share.service.audit.AdminAuditService;
import gov.samhsa.consent2share.service.consent.ConsentService;
import gov.samhsa.consent2share.service.dto.AbstractPdfDto;
import gov.samhsa.consent2share.service.dto.AdminProfileDto;
import gov.samhsa.consent2share.service.dto.ConsentListDto;
import gov.samhsa.consent2share.service.dto.PatientAdminDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;
import gov.samhsa.consent2share.service.dto.RecentAcctivityDto;
import gov.samhsa.consent2share.service.patient.PatientService;
import gov.samhsa.consent2share.service.reference.AdministrativeGenderCodeService;
import gov.samhsa.consent2share.service.reference.LanguageCodeService;
import gov.samhsa.consent2share.service.reference.MaritalStatusCodeService;
import gov.samhsa.consent2share.service.reference.RaceCodeService;
import gov.samhsa.consent2share.service.reference.ReligiousAffiliationCodeService;
import gov.samhsa.consent2share.service.reference.StateCodeService;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTest {
	
	@Mock
	PatientService patientService;
	
	@Mock
	ConsentService consentService;
	
	@Mock
	UserContext adminUserContext;
	
	/** The administrative gender code service. */
	@Mock
	AdministrativeGenderCodeService administrativeGenderCodeService;
	
	/** The language code service. */
	@Mock
	LanguageCodeService languageCodeService;

	/** The marital status code service. */
	@Mock
	MaritalStatusCodeService maritalStatusCodeService;

	/** The race code service. */
	@Mock
	RaceCodeService raceCodeService;
	
	/** The admin service. */
	@Mock
	AdminService adminService;
	
	/** The patient audit service. */
	@Mock
	AdminAuditService adminAuditService;
	
	/** The field validator. */
	@Mock
	private FieldValidator fieldValidator;
	
	@Mock
	PixQueryService pixQueryService;
	

	/** The religious affiliation code service. */
	@Mock
	ReligiousAffiliationCodeService religiousAffiliationCodeService;

	/** The state code service. */
	@Mock
	StateCodeService stateCodeService;
	
	@InjectMocks
	AdminController adminController;
	
	MockMvc mockMvc;
	
	final String validFirstName = "Tom";
	final String validLastName = "Lee";
	final String validBirthDate = "1/1/1950";
	final String validEmail = "test@test.com";
	final String validGenderCode = "administrativeGenderCode";
	final String validMrn = "PUI100000000001";
	final String validEid = "1c5c59f0-5788-11e3-84b3-00155d3a2124";
	
	@Before
	public void setUp(){
		mockMvc = MockMvcBuilders.standaloneSetup(this.adminController).build();
	}
	
	@Test
	public void testGetAdminHome() throws Exception{
		final String username = "username";
		final AuthenticatedUser currentUser = mock(AuthenticatedUser.class);
		when(adminUserContext.getCurrentUser()).thenReturn(
				currentUser);
		List<RecentAcctivityDto> recentActivityDtos=(List<RecentAcctivityDto>) mock(List.class);
		when(adminAuditService.findAdminHistoryByUsername(username)).thenReturn(
				recentActivityDtos);
		
		mockMvc.perform(get("/Administrator/adminHome.html?notify=true"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("notifyevent","true"))
			.andExpect(view().name("views/Administrator/adminHome"));
	}
	
	@Test
	public void testAdminPatientView_when_id_is_present() throws Exception{
		PatientProfileDto patientProfileDto=mock(PatientProfileDto.class);
		@SuppressWarnings("unchecked")
		List<ConsentListDto> consentListDto=(List<ConsentListDto>)mock(List.class);
		when(patientService.findPatient((long)2)).thenReturn(patientProfileDto);
		when(consentService.findAllConsentsDtoByPatient((long)2)).thenReturn(consentListDto);
		
		mockMvc.perform(get("/Administrator/adminPatientView.html?id=2"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("patientProfileDto",patientProfileDto))
		.andExpect(model().attribute("consentListDto",consentListDto))
		.andExpect(model().attribute("administrativeGenderCodes", administrativeGenderCodeService.findAllAdministrativeGenderCodes()))
		.andExpect(model().attribute("maritalStatusCodes", maritalStatusCodeService.findAllMaritalStatusCodes()))
		.andExpect(model().attribute("religiousAffiliationCodes", religiousAffiliationCodeService.findAllReligiousAffiliationCodes()))
		.andExpect(model().attribute("raceCodes", raceCodeService.findAllRaceCodes()))
		.andExpect(model().attribute("languageCodes", languageCodeService.findAllLanguageCodes()))
		.andExpect(model().attribute("stateCodes", stateCodeService.findAllStateCodes()))
		.andExpect(view().name("views/Administrator/adminPatientView"));
	}
	

	@Test
	public void testAdminPatientView_when_id_is_not_present() throws Exception{
		mockMvc.perform(get("/Administrator/adminPatientView.html"))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testGetByFirstAndLastName() throws Exception{
		List<PatientAdminDto> PatientAdminDtos=new ArrayList<PatientAdminDto>();
		PatientAdminDto patientAdminDto=new PatientAdminDto();
		patientAdminDto.setFirstName("Mary");
		patientAdminDto.setLastName("Doe");
		patientAdminDto.setId((long)1);
		PatientAdminDtos.add(patientAdminDto);
		when(patientService.findAllPatientByFirstNameAndLastName(any(String[].class))).thenReturn(PatientAdminDtos);
		mockMvc.perform(get("/Administrator//patientlookup/query?token=mary%2C+doe"))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		//Subject to change when PatientAdminDto is changed
		.andExpect(content().string("[{\"firstName\":\"Mary\",\"lastName\":\"Doe\",\"id\":1}]"));

	}
	
	@Test
	public void testDownloadConsentPdfFile() throws Exception{
		AbstractPdfDto pdfDto=mock(AbstractPdfDto.class);
		byte[] byteArray=new byte[]{1,2,3};
		when(pdfDto.getContent()).thenReturn(byteArray);
		when(consentService.findConsentContentDto(anyLong())).thenReturn(pdfDto);
		mockMvc.perform(get("/Administrator/downloadPdf.html").param("consentId", "1"))
			.andExpect(status().isOk());
		verify(consentService).findConsentContentDto((long)1);
		
	}
	
	@Test
	public void testEditAdminProfile_when_HTTPGet() throws Exception{
		final String username = "username";

		final AuthenticatedUser currentUser = mock(AuthenticatedUser.class);
		when(adminUserContext.getCurrentUser()).thenReturn(currentUser);
		
		AdminProfileDto adminProfileDto=mock(AdminProfileDto.class);
		when(adminService.findAdminProfileByUsername(username)).thenReturn(adminProfileDto);
		
		
		mockMvc.perform(get("/Administrator/editAdminProfile.html"))
			.andExpect(status().isOk());
			
	}
	
	@Test
	public void testAdminProfile_When_HttpPost_With_Valid_Params() throws Exception {

		final String username = "username";
    
		final AuthenticatedUser currentUser = mock(AuthenticatedUser.class);
		when(currentUser.getUsername()).thenReturn(username);

		when(adminUserContext.getCurrentUser()).thenReturn(currentUser);

		fieldValidator = new FieldValidator();
		ReflectionTestUtils.setField(adminController, "fieldValidator", fieldValidator);
		
		mockMvc.perform(
				post("/Administrator/editAdminProfile.html")
						.param("firstName",validFirstName)
						.param("lastName", validLastName )
						.param("email", validEmail)
						.param(validGenderCode ,
								validGenderCode )
						.param("username", username)
						.param("password","password"))
			.andExpect(status().isOk())
			.andExpect(model().hasNoErrors())				
			.andExpect(view().name("views/Administrator/editAdminProfile"))	;	
	
	}
	
	@Test
	public void testAdminEditPatientProfile_When_HttpPost_With_Valid_Params() throws Exception {

		final String username = "username";
		final Long pateintId = (long) 1;
		PatientProfileDto patient=mock(PatientProfileDto.class);
    
		final AuthenticatedUser currentUser = mock(AuthenticatedUser.class);
		when(currentUser.getUsername()).thenReturn(username);
		when(patientService.findPatient(pateintId)).thenReturn(patient); 
		when(patientService.findPatient(pateintId).getUsername()).thenReturn(username);

		fieldValidator = new FieldValidator();
		ReflectionTestUtils.setField(adminController, "fieldValidator", fieldValidator);
		when(pixQueryService.getEid(validMrn)).thenReturn(validEid);
		
		mockMvc.perform(
				post("/Administrator/adminEditPatientProfile.html")
						.param("id","1")
						.param("firstName",validFirstName)
						.param("lastName", validLastName )
						.param("email", validEmail)
						.param("birthDate", validBirthDate)
						.param("medicalRecordNumber",validMrn)
						.param(validGenderCode ,
								validGenderCode ))
			.andExpect(model().hasNoErrors())	
			.andExpect(view().name("redirect:/Administrator/adminPatientView.html?id=1"))	;	
	
	}
	
	

}
