package gov.samhsa.consent2share.web;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import gov.samhsa.consent2share.infrastructure.FieldValidator;
import gov.samhsa.consent2share.infrastructure.HashMapResultToProviderDtoConverter;
import gov.samhsa.consent2share.infrastructure.PixQueryService;
import gov.samhsa.consent2share.infrastructure.security.AuthenticatedUser;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.service.admin.AdminService;
import gov.samhsa.consent2share.service.audit.AdminAuditService;
import gov.samhsa.consent2share.service.consent.ConsentNotFoundException;
import gov.samhsa.consent2share.service.consent.ConsentService;
import gov.samhsa.consent2share.service.dto.AbstractPdfDto;
import gov.samhsa.consent2share.service.dto.AdminProfileDto;
import gov.samhsa.consent2share.service.dto.ConsentDto;
import gov.samhsa.consent2share.service.dto.ConsentListDto;
import gov.samhsa.consent2share.service.dto.ConsentPdfDto;
import gov.samhsa.consent2share.service.dto.ConsentRevokationPdfDto;
import gov.samhsa.consent2share.service.dto.IndividualProviderDto;
import gov.samhsa.consent2share.service.dto.OrganizationalProviderDto;
import gov.samhsa.consent2share.service.dto.PatientAdminDto;
import gov.samhsa.consent2share.service.dto.PatientConnectionDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;
import gov.samhsa.consent2share.service.dto.RecentAcctivityDto;
import gov.samhsa.consent2share.service.dto.SystemNotificationDto;
import gov.samhsa.consent2share.service.patient.PatientService;
import gov.samhsa.consent2share.service.provider.IndividualProviderService;
import gov.samhsa.consent2share.service.provider.OrganizationalProviderService;
import gov.samhsa.consent2share.service.provider.ProviderSearchLookupService;
import gov.samhsa.consent2share.service.reference.AdministrativeGenderCodeService;
import gov.samhsa.consent2share.service.reference.LanguageCodeService;
import gov.samhsa.consent2share.service.reference.MaritalStatusCodeService;
import gov.samhsa.consent2share.service.reference.RaceCodeService;
import gov.samhsa.consent2share.service.reference.ReligiousAffiliationCodeService;
import gov.samhsa.consent2share.service.reference.StateCodeService;
import gov.samhsa.consent2share.service.systemnotification.SystemNotificationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.NestedServletException;

@RunWith(MockitoJUnitRunner.class)
public class AdminControllerTest {
	
	@Mock
	PatientService patientService;
	
	@Mock
	ConsentService consentService;
	
	@Mock
	SystemNotificationService systemNotificationService;
	
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
	
	@Mock
	HashMap<String,String> Result;
	
	@Mock
	IndividualProviderService individualProviderService;
	
	@Mock
	OrganizationalProviderService organizationalProviderService;

	

	/** The religious affiliation code service. */
	@Mock
	ReligiousAffiliationCodeService religiousAffiliationCodeService;

	/** The state code service. */
	@Mock
	StateCodeService stateCodeService;
	
	@Mock
	HashMapResultToProviderDtoConverter hashMapResultToProviderDtoConverter;
	
	@Mock
	ProviderSearchLookupService providerSearchLookupService;
	
	@InjectMocks
	AdminController adminController;
	
	MockMvc mockMvc;
	
	@Rule
	public ExpectedException thrownException = ExpectedException.none();
	
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
		PatientConnectionDto patientConnectionDto=mock(PatientConnectionDto.class);
		@SuppressWarnings("unchecked")
		List<ConsentListDto> consentListDto=(List<ConsentListDto>)mock(List.class);
		List<SystemNotificationDto> systemNotificationDtos=(List<SystemNotificationDto>)mock(List.class);
		when(patientService.findPatient((long)2)).thenReturn(patientProfileDto);
		when(patientService.findPatientConnectionById((long)2)).thenReturn(patientConnectionDto);
		when(consentService.findAllConsentsDtoByPatient((long)2)).thenReturn(consentListDto);
		when(systemNotificationDtos=systemNotificationService.findAllSystemNotificationDtosByPatient((long)2)).thenReturn(systemNotificationDtos);
		final AuthenticatedUser currentUser = mock(AuthenticatedUser.class);
		when(adminUserContext.getCurrentUser()).thenReturn(
				currentUser);
		
		mockMvc.perform(get("/Administrator/adminPatientView.html?id=2"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("patientProfileDto",patientProfileDto))
		.andExpect(model().attribute("consentListDto",consentListDto))
		.andExpect(model().attribute("systemNotificationDtos",systemNotificationDtos))
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
		.andExpect(view().name("redirect:adminHome.html"));
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
			.andExpect(view().name("redirect:/Administrator/adminPatientView.html?notify=editpatientprofile&status=success&id=1"))	;	
	
	}
	
	@Test
	public void testSubmitConsentWhenConsentIsFound() throws Exception{
		ConsentPdfDto consentPdfDto = mock(ConsentPdfDto.class);
		
		when(consentService.findConsentPdfDto(any(long.class))).thenReturn(consentPdfDto);
		when(consentService.signConsent(any(ConsentPdfDto.class))).thenReturn(true);
		when(consentService.isConsentBelongToThisUser(any(long.class), any(long.class))).thenReturn(true);
		mockMvc.perform(post("/Administrator/adminPatientViewSubmitConsent.html")
				.param("consentId", "1")
				.param("patientId", "1"))
				.andExpect(view().name("redirect:adminPatientView.html?notify=submit&status=success&id=1"));
	}
	
	@Test
	public void testSubmitConsentWhenConsentIsFoundButSubmissionFails() throws Exception{
		ConsentDto consentDto = mock(ConsentDto.class);
		when(consentDto.getUsername()).thenReturn("patientusername");
		
		ConsentPdfDto consentPdfDto = mock(ConsentPdfDto.class);
		
		when(consentService.findConsentById(any(long.class))).thenReturn(consentDto);
		when(patientService.findUsernameById(any(long.class))).thenReturn("patientusername");
		when(consentService.findConsentPdfDto(any(long.class))).thenReturn(consentPdfDto);
		when(consentService.signConsent(any(ConsentPdfDto.class))).thenReturn(false);
		
		mockMvc.perform(post("/Administrator/adminPatientViewSubmitConsent.html")
				.param("consentId", "1")
				.param("patientId", "1"))
				.andExpect(view().name("redirect:adminPatientView.html?notify=submit&status=fail&id=1"));
	}
	
	@Test
	public void testSubmitConsentWhenConsentIsNotFound() throws Exception{
		when(consentService.findConsentById(any(long.class))).thenReturn(null);
		
		mockMvc.perform(post("/Administrator/adminPatientViewSubmitConsent.html")
				.param("consentId", "1")
				.param("patientId", "1"))
				.andExpect(view().name("redirect:adminPatientView.html?notify=submit&status=fail&id=1"));
	}
	
	@Test
	public void testAddIndividualProvider_when_succeeds() throws Exception {
		when(Result.get("entityType")).thenReturn("entityTypeValue");
		when(Result.get("providerOrganizationName")).thenReturn("providerOrganizationNameValue");
		when(Result.get("authorizedOfficialLastName")).thenReturn("authorizedOfficialLastNameValue");
		when(Result.get("authorizedOfficialLastName")).thenReturn("authorizedOfficialLastNameValue");
		when(Result.get("authorizedOfficialTitleorPosition")).thenReturn("authorizedOfficialTitleorPositionValue");
		when(Result.get("authorizedOfficialNamePrefixText")).thenReturn("authorizedOfficialNamePrefixTextValue");
		when(Result.get("authorizedOfficialTelephoneNumber")).thenReturn("authorizedOfficialTelephoneNumberValue");
		
		when(individualProviderService.addNewIndividualProvider(any(IndividualProviderDto.class))).thenReturn(true);
		
		mockMvc.perform(post("/Administrator/connectionProviderAdd.html").param("querySent", "{\"npi\":\"1114252178\",\"entityType\":\"Individual\",\"replacementNpi\":\"\",\"employerIdentificationNumber\":\"\",\"isSoleProprietor\":false,\"isOrganizationSubpart\":false,\"parentOrganizationLbn\":\"\",\"parentOrganizationTin\":\"\",\"providerOrganizationName\":\"\",\"providerLastName\":\"MORGAN\",\"providerFirstName\":\"TERRENCE\",\"providerMiddleName\":\"\",\"providerNamePrefixText\":\"MR.\",\"providerNameSuffixText\":\"\",\"providerCredentialText\":\"LGSW, CSC-AD\",\"providerFirstLineBusinessMailingAddress\":\"9100 FRANKLIN SQUARE DR\",\"providerSecondLineBusinessMailingAddress\":\"\",\"providerBusinessMailingAddressCityName\":\"BALTIMORE\",\"providerBusinessMailingAddressStateName\":\"MD\",\"providerBusinessMailingAddressPostalCode\":\"212373903\",\"providerBusinessMailingAddressCountryCode\":\"US\",\"providerBusinessMailingAddressTelephoneNumber\":\"4108876465\",\"providerBusinessMailingAddressFaxNumber\":\"4106876005\",\"providerFirstLineBusinessPracticeLocationAddress\":\"9100 FRANKLIN SQUARE DR\",\"providerSecondLineBusinessPracticeLocationAddress\":\"\",\"providerBusinessPracticeLocationAddressCityName\":\"BALTIMORE\",\"providerBusinessPracticeLocationAddressStateName\":\"MD\",\"providerBusinessPracticeLocationAddressPostalCode\":\"212373903\",\"providerBusinessPracticeLocationAddressCountryCode\":\"US\",\"providerBusinessPracticeLocationAddressTelephoneNumber\":\"4108876465\",\"providerBusinessPracticeLocationAddressFaxNumber\":\"4106876005\",\"providerEnumerationDate\":\"10/08/2009\",\"lastUpdateDate\":\"10/08/2009\",\"npideactivationReasonCode\":\"\",\"npideactivationReason\":\"\",\"npideactivationDate\":\"\",\"npireactivationDate\":\"\",\"providerGenderCode\":\"M\",\"providerGender\":\"Male\",\"authorizedOfficialLastName\":\"\",\"authorizedOfficialFirstName\":\"\",\"authorizedOfficialMiddleName\":\"\",\"authorizedOfficialTitleorPosition\":\"\",\"authorizedOfficialNamePrefixText\":\"\",\"authorizedOfficialNameSuffixText\":\"\",\"authorizedOfficialCredentialText\":\"\",\"authorizedOfficialTelephoneNumber\":\"\",\"healthcareProviderTaxonomyCode_1\":\"101YM0800X\",\"providerLicenseNumber_1\":\"14742\",\"providerLicenseNumberStateCode_1\":\"MD\",\"healthcareProviderTaxonomy_1\":\"Mental Health\"}")
				.param("patientusername", "albert.smith").param("patientId", "1"))
				.andExpect(status().isOk())
				.andExpect(content().string("Success"));
	}
	
	@Test
	public void testAddIndividualProvider_when_provider_is_already_added() throws Exception {
		when(Result.get("entityType")).thenReturn("entityTypeValue");
		when(Result.get("providerOrganizationName")).thenReturn("providerOrganizationNameValue");
		when(Result.get("authorizedOfficialLastName")).thenReturn("authorizedOfficialLastNameValue");
		when(Result.get("authorizedOfficialLastName")).thenReturn("authorizedOfficialLastNameValue");
		when(Result.get("authorizedOfficialTitleorPosition")).thenReturn("authorizedOfficialTitleorPositionValue");
		when(Result.get("authorizedOfficialNamePrefixText")).thenReturn("authorizedOfficialNamePrefixTextValue");
		when(Result.get("authorizedOfficialTelephoneNumber")).thenReturn("authorizedOfficialTelephoneNumberValue");
		
		when(individualProviderService.addNewIndividualProvider(any(IndividualProviderDto.class))).thenReturn(false);
		
		mockMvc.perform(post("/Administrator/connectionProviderAdd.html").param("querySent", "{\"npi\":\"1114252178\",\"entityType\":\"Individual\",\"replacementNpi\":\"\",\"employerIdentificationNumber\":\"\",\"isSoleProprietor\":false,\"isOrganizationSubpart\":false,\"parentOrganizationLbn\":\"\",\"parentOrganizationTin\":\"\",\"providerOrganizationName\":\"\",\"providerLastName\":\"MORGAN\",\"providerFirstName\":\"TERRENCE\",\"providerMiddleName\":\"\",\"providerNamePrefixText\":\"MR.\",\"providerNameSuffixText\":\"\",\"providerCredentialText\":\"LGSW, CSC-AD\",\"providerFirstLineBusinessMailingAddress\":\"9100 FRANKLIN SQUARE DR\",\"providerSecondLineBusinessMailingAddress\":\"\",\"providerBusinessMailingAddressCityName\":\"BALTIMORE\",\"providerBusinessMailingAddressStateName\":\"MD\",\"providerBusinessMailingAddressPostalCode\":\"212373903\",\"providerBusinessMailingAddressCountryCode\":\"US\",\"providerBusinessMailingAddressTelephoneNumber\":\"4108876465\",\"providerBusinessMailingAddressFaxNumber\":\"4106876005\",\"providerFirstLineBusinessPracticeLocationAddress\":\"9100 FRANKLIN SQUARE DR\",\"providerSecondLineBusinessPracticeLocationAddress\":\"\",\"providerBusinessPracticeLocationAddressCityName\":\"BALTIMORE\",\"providerBusinessPracticeLocationAddressStateName\":\"MD\",\"providerBusinessPracticeLocationAddressPostalCode\":\"212373903\",\"providerBusinessPracticeLocationAddressCountryCode\":\"US\",\"providerBusinessPracticeLocationAddressTelephoneNumber\":\"4108876465\",\"providerBusinessPracticeLocationAddressFaxNumber\":\"4106876005\",\"providerEnumerationDate\":\"10/08/2009\",\"lastUpdateDate\":\"10/08/2009\",\"npideactivationReasonCode\":\"\",\"npideactivationReason\":\"\",\"npideactivationDate\":\"\",\"npireactivationDate\":\"\",\"providerGenderCode\":\"M\",\"providerGender\":\"Male\",\"authorizedOfficialLastName\":\"\",\"authorizedOfficialFirstName\":\"\",\"authorizedOfficialMiddleName\":\"\",\"authorizedOfficialTitleorPosition\":\"\",\"authorizedOfficialNamePrefixText\":\"\",\"authorizedOfficialNameSuffixText\":\"\",\"authorizedOfficialCredentialText\":\"\",\"authorizedOfficialTelephoneNumber\":\"\",\"healthcareProviderTaxonomyCode_1\":\"101YM0800X\",\"providerLicenseNumber_1\":\"14742\",\"providerLicenseNumberStateCode_1\":\"MD\",\"healthcareProviderTaxonomy_1\":\"Mental Health\"}")
				.param("patientusername", "albert.smith").param("patientId", "1"))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("Unable to add this new provider because this provider already exists."));
	}
	
	@Test
	public void testAddIndividualProvider_when_nonnumeric_patientid_sent() throws Exception {
		when(Result.get("entityType")).thenReturn("entityTypeValue");
		when(Result.get("providerOrganizationName")).thenReturn("providerOrganizationNameValue");
		when(Result.get("authorizedOfficialLastName")).thenReturn("authorizedOfficialLastNameValue");
		when(Result.get("authorizedOfficialLastName")).thenReturn("authorizedOfficialLastNameValue");
		when(Result.get("authorizedOfficialTitleorPosition")).thenReturn("authorizedOfficialTitleorPositionValue");
		when(Result.get("authorizedOfficialNamePrefixText")).thenReturn("authorizedOfficialNamePrefixTextValue");
		when(Result.get("authorizedOfficialTelephoneNumber")).thenReturn("authorizedOfficialTelephoneNumberValue");
		
		when(patientService.findUsernameById(anyLong())).thenThrow(new NumberFormatException());
		
		mockMvc.perform(post("/Administrator/connectionProviderAdd.html").param("querySent", "{\"npi\":\"1114252178\",\"entityType\":\"Individual\",\"replacementNpi\":\"\",\"employerIdentificationNumber\":\"\",\"isSoleProprietor\":false,\"isOrganizationSubpart\":false,\"parentOrganizationLbn\":\"\",\"parentOrganizationTin\":\"\",\"providerOrganizationName\":\"\",\"providerLastName\":\"MORGAN\",\"providerFirstName\":\"TERRENCE\",\"providerMiddleName\":\"\",\"providerNamePrefixText\":\"MR.\",\"providerNameSuffixText\":\"\",\"providerCredentialText\":\"LGSW, CSC-AD\",\"providerFirstLineBusinessMailingAddress\":\"9100 FRANKLIN SQUARE DR\",\"providerSecondLineBusinessMailingAddress\":\"\",\"providerBusinessMailingAddressCityName\":\"BALTIMORE\",\"providerBusinessMailingAddressStateName\":\"MD\",\"providerBusinessMailingAddressPostalCode\":\"212373903\",\"providerBusinessMailingAddressCountryCode\":\"US\",\"providerBusinessMailingAddressTelephoneNumber\":\"4108876465\",\"providerBusinessMailingAddressFaxNumber\":\"4106876005\",\"providerFirstLineBusinessPracticeLocationAddress\":\"9100 FRANKLIN SQUARE DR\",\"providerSecondLineBusinessPracticeLocationAddress\":\"\",\"providerBusinessPracticeLocationAddressCityName\":\"BALTIMORE\",\"providerBusinessPracticeLocationAddressStateName\":\"MD\",\"providerBusinessPracticeLocationAddressPostalCode\":\"212373903\",\"providerBusinessPracticeLocationAddressCountryCode\":\"US\",\"providerBusinessPracticeLocationAddressTelephoneNumber\":\"4108876465\",\"providerBusinessPracticeLocationAddressFaxNumber\":\"4106876005\",\"providerEnumerationDate\":\"10/08/2009\",\"lastUpdateDate\":\"10/08/2009\",\"npideactivationReasonCode\":\"\",\"npideactivationReason\":\"\",\"npideactivationDate\":\"\",\"npireactivationDate\":\"\",\"providerGenderCode\":\"M\",\"providerGender\":\"Male\",\"authorizedOfficialLastName\":\"\",\"authorizedOfficialFirstName\":\"\",\"authorizedOfficialMiddleName\":\"\",\"authorizedOfficialTitleorPosition\":\"\",\"authorizedOfficialNamePrefixText\":\"\",\"authorizedOfficialNameSuffixText\":\"\",\"authorizedOfficialCredentialText\":\"\",\"authorizedOfficialTelephoneNumber\":\"\",\"healthcareProviderTaxonomyCode_1\":\"101YM0800X\",\"providerLicenseNumber_1\":\"14742\",\"providerLicenseNumberStateCode_1\":\"MD\",\"healthcareProviderTaxonomy_1\":\"Mental Health\"}")
				.param("patientusername", "albert.smith").param("patientId", "y"))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("Unable to add this new provider because the request parameters contained invalid data."));
	}
	
	
	@Test
	public void testAddOrganizationalProvider_when_succeeds() throws Exception {
		when(Result.get("entityType")).thenReturn("entityTypeValue");
		when(Result.get("providerOrganizationName")).thenReturn("providerOrganizationNameValue");
		when(Result.get("authorizedOfficialLastName")).thenReturn("authorizedOfficialLastNameValue");
		when(Result.get("authorizedOfficialLastName")).thenReturn("authorizedOfficialLastNameValue");
		when(Result.get("authorizedOfficialTitleorPosition")).thenReturn("authorizedOfficialTitleorPositionValue");
		when(Result.get("authorizedOfficialNamePrefixText")).thenReturn("authorizedOfficialNamePrefixTextValue");
		when(Result.get("authorizedOfficialTelephoneNumber")).thenReturn("authorizedOfficialTelephoneNumberValue");
		
		when(organizationalProviderService.addNewOrganizationalProvider(any(OrganizationalProviderDto.class))).thenReturn(true);
		
		mockMvc.perform(post("/Administrator/connectionProviderAdd.html").param("querySent", "{\"npi\":\"1114252178\",\"entityType\":\"Organization\",\"replacementNpi\":\"\",\"employerIdentificationNumber\":\"\",\"isSoleProprietor\":false,\"isOrganizationSubpart\":false,\"parentOrganizationLbn\":\"\",\"parentOrganizationTin\":\"\",\"providerOrganizationName\":\"SUMMIT ANESTHESIA LLC\",\"providerCredentialText\":\"LGSW, CSC-AD\",\"providerFirstLineBusinessMailingAddress\":\"9100 FRANKLIN SQUARE DR\",\"providerSecondLineBusinessMailingAddress\":\"\",\"providerBusinessMailingAddressCityName\":\"BALTIMORE\",\"providerBusinessMailingAddressStateName\":\"MD\",\"providerBusinessMailingAddressPostalCode\":\"212373903\",\"providerBusinessMailingAddressCountryCode\":\"US\",\"providerBusinessMailingAddressTelephoneNumber\":\"4108876465\",\"providerBusinessMailingAddressFaxNumber\":\"4106876005\",\"providerFirstLineBusinessPracticeLocationAddress\":\"9100 FRANKLIN SQUARE DR\",\"providerSecondLineBusinessPracticeLocationAddress\":\"\",\"providerBusinessPracticeLocationAddressCityName\":\"BALTIMORE\",\"providerBusinessPracticeLocationAddressStateName\":\"MD\",\"providerBusinessPracticeLocationAddressPostalCode\":\"212373903\",\"providerBusinessPracticeLocationAddressCountryCode\":\"US\",\"providerBusinessPracticeLocationAddressTelephoneNumber\":\"4108876465\",\"providerBusinessPracticeLocationAddressFaxNumber\":\"4106876005\",\"providerEnumerationDate\":\"10/08/2009\",\"lastUpdateDate\":\"10/08/2009\",\"npideactivationReasonCode\":\"\",\"npideactivationReason\":\"\",\"npideactivationDate\":\"\",\"npireactivationDate\":\"\",\"providerGenderCode\":\"M\",\"providerGender\":\"Male\",\"authorizedOfficialLastName\":\"\",\"authorizedOfficialFirstName\":\"\",\"authorizedOfficialMiddleName\":\"\",\"authorizedOfficialTitleorPosition\":\"\",\"authorizedOfficialNamePrefixText\":\"\",\"authorizedOfficialNameSuffixText\":\"\",\"authorizedOfficialCredentialText\":\"\",\"authorizedOfficialTelephoneNumber\":\"\",\"healthcareProviderTaxonomyCode_1\":\"101YM0800X\",\"providerLicenseNumber_1\":\"14742\",\"providerLicenseNumberStateCode_1\":\"MD\",\"healthcareProviderTaxonomy_1\":\"Mental Health\"}")
				.param("patientusername", "albert.smith").param("patientId", "1"))
				.andExpect(status().isOk())
				.andExpect(content().string("Success"));
	}
	
	
	@Test
	public void testAddOrganizationalProvider_when_provider_is_already_added() throws Exception {
		when(Result.get("entityType")).thenReturn("entityTypeValue");
		when(Result.get("providerOrganizationName")).thenReturn("providerOrganizationNameValue");
		when(Result.get("authorizedOfficialLastName")).thenReturn("authorizedOfficialLastNameValue");
		when(Result.get("authorizedOfficialLastName")).thenReturn("authorizedOfficialLastNameValue");
		when(Result.get("authorizedOfficialTitleorPosition")).thenReturn("authorizedOfficialTitleorPositionValue");
		when(Result.get("authorizedOfficialNamePrefixText")).thenReturn("authorizedOfficialNamePrefixTextValue");
		when(Result.get("authorizedOfficialTelephoneNumber")).thenReturn("authorizedOfficialTelephoneNumberValue");
		
		when(organizationalProviderService.addNewOrganizationalProvider(any(OrganizationalProviderDto.class))).thenReturn(false);
		
		mockMvc.perform(post("/Administrator/connectionProviderAdd.html").param("querySent", "{\"npi\":\"1114252178\",\"entityType\":\"Organization\",\"replacementNpi\":\"\",\"employerIdentificationNumber\":\"\",\"isSoleProprietor\":false,\"isOrganizationSubpart\":false,\"parentOrganizationLbn\":\"\",\"parentOrganizationTin\":\"\",\"providerOrganizationName\":\"SUMMIT ANESTHESIA LLC\",\"providerCredentialText\":\"LGSW, CSC-AD\",\"providerFirstLineBusinessMailingAddress\":\"9100 FRANKLIN SQUARE DR\",\"providerSecondLineBusinessMailingAddress\":\"\",\"providerBusinessMailingAddressCityName\":\"BALTIMORE\",\"providerBusinessMailingAddressStateName\":\"MD\",\"providerBusinessMailingAddressPostalCode\":\"212373903\",\"providerBusinessMailingAddressCountryCode\":\"US\",\"providerBusinessMailingAddressTelephoneNumber\":\"4108876465\",\"providerBusinessMailingAddressFaxNumber\":\"4106876005\",\"providerFirstLineBusinessPracticeLocationAddress\":\"9100 FRANKLIN SQUARE DR\",\"providerSecondLineBusinessPracticeLocationAddress\":\"\",\"providerBusinessPracticeLocationAddressCityName\":\"BALTIMORE\",\"providerBusinessPracticeLocationAddressStateName\":\"MD\",\"providerBusinessPracticeLocationAddressPostalCode\":\"212373903\",\"providerBusinessPracticeLocationAddressCountryCode\":\"US\",\"providerBusinessPracticeLocationAddressTelephoneNumber\":\"4108876465\",\"providerBusinessPracticeLocationAddressFaxNumber\":\"4106876005\",\"providerEnumerationDate\":\"10/08/2009\",\"lastUpdateDate\":\"10/08/2009\",\"npideactivationReasonCode\":\"\",\"npideactivationReason\":\"\",\"npideactivationDate\":\"\",\"npireactivationDate\":\"\",\"providerGenderCode\":\"M\",\"providerGender\":\"Male\",\"authorizedOfficialLastName\":\"\",\"authorizedOfficialFirstName\":\"\",\"authorizedOfficialMiddleName\":\"\",\"authorizedOfficialTitleorPosition\":\"\",\"authorizedOfficialNamePrefixText\":\"\",\"authorizedOfficialNameSuffixText\":\"\",\"authorizedOfficialCredentialText\":\"\",\"authorizedOfficialTelephoneNumber\":\"\",\"healthcareProviderTaxonomyCode_1\":\"101YM0800X\",\"providerLicenseNumber_1\":\"14742\",\"providerLicenseNumberStateCode_1\":\"MD\",\"healthcareProviderTaxonomy_1\":\"Mental Health\"}")
				.param("patientusername", "albert.smith").param("patientId", "1"))
				.andExpect(status().isInternalServerError())
				.andExpect(content().string("Unable to add this new provider because this provider already exists."));
	}
	
	@Test
	public void testAddOrganizationalProvider_when_nonnumeric_patientid_sent() throws Exception {
		when(Result.get("entityType")).thenReturn("entityTypeValue");
		when(Result.get("providerOrganizationName")).thenReturn("providerOrganizationNameValue");
		when(Result.get("authorizedOfficialLastName")).thenReturn("authorizedOfficialLastNameValue");
		when(Result.get("authorizedOfficialLastName")).thenReturn("authorizedOfficialLastNameValue");
		when(Result.get("authorizedOfficialTitleorPosition")).thenReturn("authorizedOfficialTitleorPositionValue");
		when(Result.get("authorizedOfficialNamePrefixText")).thenReturn("authorizedOfficialNamePrefixTextValue");
		when(Result.get("authorizedOfficialTelephoneNumber")).thenReturn("authorizedOfficialTelephoneNumberValue");
		
		when(patientService.findUsernameById(anyLong())).thenThrow(new NumberFormatException());
		
		mockMvc.perform(post("/Administrator/connectionProviderAdd.html").param("querySent", "{\"npi\":\"1114252178\",\"entityType\":\"Organization\",\"replacementNpi\":\"\",\"employerIdentificationNumber\":\"\",\"isSoleProprietor\":false,\"isOrganizationSubpart\":false,\"parentOrganizationLbn\":\"\",\"parentOrganizationTin\":\"\",\"providerOrganizationName\":\"SUMMIT ANESTHESIA LLC\",\"providerCredentialText\":\"LGSW, CSC-AD\",\"providerFirstLineBusinessMailingAddress\":\"9100 FRANKLIN SQUARE DR\",\"providerSecondLineBusinessMailingAddress\":\"\",\"providerBusinessMailingAddressCityName\":\"BALTIMORE\",\"providerBusinessMailingAddressStateName\":\"MD\",\"providerBusinessMailingAddressPostalCode\":\"212373903\",\"providerBusinessMailingAddressCountryCode\":\"US\",\"providerBusinessMailingAddressTelephoneNumber\":\"4108876465\",\"providerBusinessMailingAddressFaxNumber\":\"4106876005\",\"providerFirstLineBusinessPracticeLocationAddress\":\"9100 FRANKLIN SQUARE DR\",\"providerSecondLineBusinessPracticeLocationAddress\":\"\",\"providerBusinessPracticeLocationAddressCityName\":\"BALTIMORE\",\"providerBusinessPracticeLocationAddressStateName\":\"MD\",\"providerBusinessPracticeLocationAddressPostalCode\":\"212373903\",\"providerBusinessPracticeLocationAddressCountryCode\":\"US\",\"providerBusinessPracticeLocationAddressTelephoneNumber\":\"4108876465\",\"providerBusinessPracticeLocationAddressFaxNumber\":\"4106876005\",\"providerEnumerationDate\":\"10/08/2009\",\"lastUpdateDate\":\"10/08/2009\",\"npideactivationReasonCode\":\"\",\"npideactivationReason\":\"\",\"npideactivationDate\":\"\",\"npireactivationDate\":\"\",\"providerGenderCode\":\"M\",\"providerGender\":\"Male\",\"authorizedOfficialLastName\":\"\",\"authorizedOfficialFirstName\":\"\",\"authorizedOfficialMiddleName\":\"\",\"authorizedOfficialTitleorPosition\":\"\",\"authorizedOfficialNamePrefixText\":\"\",\"authorizedOfficialNameSuffixText\":\"\",\"authorizedOfficialCredentialText\":\"\",\"authorizedOfficialTelephoneNumber\":\"\",\"healthcareProviderTaxonomyCode_1\":\"101YM0800X\",\"providerLicenseNumber_1\":\"14742\",\"providerLicenseNumberStateCode_1\":\"MD\",\"healthcareProviderTaxonomy_1\":\"Mental Health\"}")
				.param("patientusername", "albert.smith").param("patientId", "y"))
				.andExpect(status().isBadRequest())
				.andExpect(content().string("Unable to add this new provider because the request parameters contained invalid data."));
	}
	
	
	@Test
	public void testSignConsentRevokation_when_authentication_succeeds() throws Exception{
		ConsentRevokationPdfDto consentRevokationPdfDto=mock(ConsentRevokationPdfDto.class);
		when(consentService.findConsentRevokationPdfDto(anyLong())).thenReturn(consentRevokationPdfDto);
		when(consentService.isConsentBelongToThisUser(anyLong(), anyLong())).thenReturn(true);
		mockMvc.perform(post("/Administrator/adminPatientViewRevokeConsent.html").param("patientId", "1").param("consentId", "1").param("revokationType", "NO NEVER"))
			.andExpect(view().name("redirect:adminPatientView.html?notify=revokepatientconsent&status=success&id=1"));
		verify(consentRevokationPdfDto).setRevokationType("NO NEVER");
	}
	
	@Test
	public void testSignConsentRevokation_when_authentication_fails() throws Exception{
		ConsentRevokationPdfDto consentRevokationPdfDto=mock(ConsentRevokationPdfDto.class);
		when(consentService.findConsentRevokationPdfDto(anyLong())).thenReturn(consentRevokationPdfDto);
		when(consentService.isConsentBelongToThisUser(anyLong(), anyLong())).thenReturn(false);
		mockMvc.perform(post("/Administrator/adminPatientViewRevokeConsent.html").param("patientId", "1").param("consentId", "1").param("revokationType", "NO NEVER"))
			.andExpect(view().name("redirect:adminPatientView.html?notify=revokepatientconsent&status=fail&id=1"));
		verify(consentService,never()).signConsentRevokation(consentRevokationPdfDto);
	}
	
	@Test
	public void testAjaxProviderSearch_Checked_Status_Is_OK() throws Exception {
		 when(providerSearchLookupService.isValidatedSearch(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(true);
		 when(providerSearchLookupService.providerSearch(anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn("artifitial JSON");
		 mockMvc.perform(get("/Administrator/providerSearch.html"))
         	.andExpect(status().isOk())
         	.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

}
