package gov.samhsa.consent2share.web;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import gov.samhsa.consent2share.infrastructure.CodedConceptLookupService;
import gov.samhsa.consent2share.infrastructure.security.AuthenticatedUser;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.service.consent.ConsentService;
import gov.samhsa.consent2share.service.consentexport.ConsentExportService;
import gov.samhsa.consent2share.service.dto.AddConsentFieldsDto;
import gov.samhsa.consent2share.service.dto.AddConsentIndividualProviderDto;
import gov.samhsa.consent2share.service.dto.AddConsentOrganizationalProviderDto;
import gov.samhsa.consent2share.service.dto.ConsentDto;
import gov.samhsa.consent2share.service.dto.ConsentListDto;
import gov.samhsa.consent2share.service.dto.ConsentPdfDto;
import gov.samhsa.consent2share.service.dto.ConsentRevokationPdfDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;
import gov.samhsa.consent2share.service.notification.NotificationService;
import gov.samhsa.consent2share.service.patient.PatientService;
import gov.samhsa.consent2share.service.reference.ClinicalDocumentSectionTypeCodeService;
import gov.samhsa.consent2share.service.reference.ClinicalDocumentTypeCodeService;
import gov.samhsa.consent2share.service.reference.PurposeOfUseCodeService;
import gov.samhsa.consent2share.service.reference.SensitivityPolicyCodeService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * The Class ConsentControllerTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConsentControllerTest {
	
	/** The consent controller. */
	@InjectMocks
	ConsentController consentController=new ConsentController();
	
	/** The consent service. */
	@Mock
    ConsentService consentService;

	/** The patient service. */
	@Mock
    PatientService patientService;

	/** The clinical document section type code service. */
	@Mock
    ClinicalDocumentSectionTypeCodeService clinicalDocumentSectionTypeCodeService;

	/** The clinical document type code service. */
	@Mock
    ClinicalDocumentTypeCodeService clinicalDocumentTypeCodeService;

	/** The hippa space coded concept lookup service. */
	@Mock
	CodedConceptLookupService hippaSpaceCodedConceptLookupService;

	/** The purpose of use code service. */
	@Mock
    PurposeOfUseCodeService purposeOfUseCodeService;

	/** The sensitivity policy code service. */
	@Mock
    SensitivityPolicyCodeService sensitivityPolicyCodeService;
	
	@Mock
	NotificationService notificationService;
	
	/** The user context. */
	@Mock
	UserContext userContext;
	
	/** The consent export service. */
	@Mock
	ConsentExportService consentExportService;
	
	/** The mock mvc. */
	MockMvc mockMvc;
	
	/**
	 * Sets up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(this.consentController).build();
		
		AuthenticatedUser user=mock(AuthenticatedUser.class); 
		when(userContext.getCurrentUser()).thenReturn(user);
		when(user.getUsername()).thenReturn("mockedUser");
		
		PatientProfileDto patient=mock(PatientProfileDto.class);
		when(patientService.findPatientProfileByUsername(anyString())).thenReturn(patient);
		when(patient.getId()).thenReturn((long)1);
		
		List<ConsentListDto> consentListDtos=new ArrayList<ConsentListDto>();
		for (int i=0;i<3;i++){
			consentListDtos.add(mock(ConsentListDto.class));
		}
		List<ConsentListDto> spyConsentListDtos=spy(consentListDtos);
		when(consentService.findAllConsentsDtoByPatient(anyLong())).thenReturn(spyConsentListDtos);
	}

	/**
	 * Test ConsentMainPage when email sent modelAttribute is set and link to the correct view.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testConsentMainPage_When_email_Sent_modelAttribute_is_Set_and_link_to_the_Correct_View() throws Exception {
		 mockMvc.perform(get("/consents/listConsents.html?emailsent=true"))
         	.andExpect(status().isOk())
         	.andExpect(model().attribute("emailSent","true"))
         	.andExpect(view().name("views/consents/listConsents"));
	}
	
	@Test
	public void testConsentMainPage_When_emailSentParameter_is_null() throws Exception{
		mockMvc.perform(get("/consents/listConsents.html"))
     	.andExpect(status().isOk())
     	.andExpect(model().attribute("emailSent","false"))
     	.andExpect(view().name("views/consents/listConsents"));
	}
	
	/**
	 * Test callHipaaSpace. Checked status is OK and content type is correct.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testCallHipaaSpace_Checked_status_is_OK_and_Content_Type_is_Correct() throws Exception {
		 when(hippaSpaceCodedConceptLookupService.searchCodes(anyString(), anyString(), anyString())).thenReturn("artifitial JSON");
		 mockMvc.perform(get("/consents/callHippaSpace.html").param("domain", "icd9").param("q", "hiv").param("rt", "json"))
         	.andExpect(status().isOk())
         	.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	/**
	 * Test redirectToEmailLoginPage redirect to the right email.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testRedirectToEmailLoginPage_Redirect_To_Right_Email() throws Exception{
		when(patientService.findPatientEmailByUsername(anyString())).thenReturn("johndoe@gmail.com");
		mockMvc.perform(get("/consents/toEmail.html"))
			.andExpect(view().name("redirect:http://gmail.com"));
	}
	
	/**
	 * Test SignConsent when succeeds.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testSignConsent_when_Succeeds() throws Exception{
		when(consentService.isConsentBelongToThisUser(anyLong(), anyLong())).thenReturn(true);
		mockMvc.perform(post("/consents/signConsent.html").param("consentId","1"))
			.andExpect(view().name("redirect:listConsents.html?emailsent=true"));
		verify(consentService).signConsent(any(ConsentPdfDto.class));
		
	}
	

	/**
	 * Test signConsent when fails.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testSignConsent_when_Fails() throws Exception{
		when(consentService.isConsentBelongToThisUser(anyLong(), anyLong())).thenReturn(false);
		mockMvc.perform(post("/consents/signConsent.html").param("consentId","2"))
			.andExpect(view().name("redirect:listConsents.html?emailsent=false"));
		verify(consentService,never()).signConsent(any(ConsentPdfDto.class));
		
	}
	
	/**
	 * Test deleteConsent when succeeds.
	 *
	 * @throws Exception the exception
	 */
	@Test 
	public void testDeleteConsent_when_succeeds() throws Exception{
		when(consentService.isConsentBelongToThisUser(anyLong(),anyLong())).thenReturn(true);
		mockMvc.perform(post("/consents/deleteConsents").param("consentId", "1"))
			.andExpect(view().name("redirect:/consents/listConsents.html"));
		verify(consentService).deleteConsent(anyLong());
	}
	
	
	/**
	 * Test deleteConsent when authentication fails.
	 *
	 * @throws Exception the exception
	 */
	@Test 
	public void testDeleteConsent_when_authentication_fails() throws Exception{
		when(consentService.isConsentBelongToThisUser(anyLong(), anyLong())).thenReturn(false);
		mockMvc.perform(post("/consents/deleteConsents").param("consentId", "2"))
			.andExpect(view().name("redirect:/consents/listConsents.html"));
		verify(consentService,never()).deleteConsent(anyLong());
	}
	

	@Test
	public void testDownloadConsentPdfFile_when_doctype_is_consent() throws Exception{
		ConsentPdfDto pdfDto=mock(ConsentPdfDto.class);
		when(pdfDto.getFilename()).thenReturn("filename");
		byte[] byteArray=new byte[]{1,2,3};
		when(pdfDto.getContent()).thenReturn(byteArray);
		when(consentService.findConsentPdfDto(anyLong())).thenReturn(pdfDto);
		when(consentService.isConsentBelongToThisUser(anyLong(), anyLong())).thenReturn(true);
		mockMvc.perform(post("/consents/downloadPdf.html").param("consentId", "1").param("doctype", "consent"))
			.andExpect(status().isOk());
		verify(consentService).findConsentPdfDto((long)1);
		
	}
	
	@Test
	public void testDownloadConsentPdfFile_when_doctype_is_revokation() throws Exception{
		ConsentRevokationPdfDto pdfDto=mock(ConsentRevokationPdfDto.class);
		when(pdfDto.getFilename()).thenReturn("filename");
		byte[] byteArray=new byte[]{1,2,3};
		when(pdfDto.getContent()).thenReturn(byteArray);
		when(consentService.findConsentRevokationPdfDto(anyLong())).thenReturn(pdfDto);
		when(consentService.isConsentBelongToThisUser(anyLong(), anyLong())).thenReturn(true);
		mockMvc.perform(post("/consents/downloadPdf.html").param("consentId", "1").param("doctype", "revokation"))
			.andExpect(status().isOk());
		verify(consentService).findConsentRevokationPdfDto((long)1);
		
	}
	
	@Test
	public void testConsentAddGet_Check_if_all_attributes_are_added_and_right_view_is_returned() throws Exception{
		ConsentDto consentDto=mock(ConsentDto.class);
		@SuppressWarnings("unchecked")
		ArrayList<AddConsentIndividualProviderDto> individualProvidersDto=
			(ArrayList<AddConsentIndividualProviderDto>)mock(ArrayList.class);
		@SuppressWarnings("unchecked")
		ArrayList<AddConsentOrganizationalProviderDto> organizationalProvidersDto=
				(ArrayList<AddConsentOrganizationalProviderDto>)mock(ArrayList.class);
		@SuppressWarnings("unchecked")
		List<AddConsentFieldsDto> sensitivityPolicyDto=
				(List<AddConsentFieldsDto>)mock(ArrayList.class);
		@SuppressWarnings("unchecked")
		List<AddConsentFieldsDto> purposeOfUseDto=
				(List<AddConsentFieldsDto>)mock(ArrayList.class);
		@SuppressWarnings("unchecked")
		List<AddConsentFieldsDto> clinicalDocumentSectionTypeDto=
				(List<AddConsentFieldsDto>)mock(ArrayList.class);
		@SuppressWarnings("unchecked")
		List<AddConsentFieldsDto> clinicalDocumentTypeDto=
				(List<AddConsentFieldsDto>)mock(ArrayList.class);
		
		when(patientService.findAddConsentIndividualProviderDtoByUsername(anyString()))
			.thenReturn(individualProvidersDto);
		when(patientService.findAddConsentOrganizationalProviderDtoByUsername(anyString()))
			.thenReturn(organizationalProvidersDto);
		when(sensitivityPolicyCodeService.findAllSensitivityPolicyCodesAddConsentFieldsDto())
			.thenReturn(sensitivityPolicyDto);
		when(purposeOfUseCodeService.findAllPurposeOfUseCodesAddConsentFieldsDto())
			.thenReturn(purposeOfUseDto);
		when(clinicalDocumentSectionTypeCodeService.findAllClinicalDocumentSectionTypeCodesAddConsentFieldsDto())
			.thenReturn(clinicalDocumentSectionTypeDto);
		when(clinicalDocumentTypeCodeService.findAllClinicalDocumentTypeCodesAddConsentFieldsDto())
			.thenReturn(clinicalDocumentTypeDto);
		when(consentService.makeConsentDto())
			.thenReturn(consentDto);
		
		mockMvc.perform(get("/consents/addConsent.html"))
			.andExpect(model().attribute("currentUser", userContext.getCurrentUser()))
			.andExpect(model().attribute("consentDto", consentDto))
			.andExpect(model().attribute("individualProvidersDto", individualProvidersDto))
			.andExpect(model().attribute("clinicalDocumentSectionType", clinicalDocumentSectionTypeDto))
			.andExpect(model().attribute("clinicalDocumentType", clinicalDocumentTypeDto))
			.andExpect(model().attribute("sensitivityPolicy", sensitivityPolicyDto))
			.andExpect(model().attribute("purposeOfUse", purposeOfUseDto))
			.andExpect(model().attribute("organizationalProvidersDto", organizationalProvidersDto))
			.andExpect(view().name("views/consents/addConsent"));
		
	}
	
	/**
	 * Test add consent post_checks_if_consent_is_saved_and_right_view_is_returned.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testAddConsentPost_when_no_duplicates_checks_if_consent_is_saved_and_right_view_is_returned() throws Exception{

		when(consentService.areThereDuplicatesInTwoSets(Matchers.<HashSet<String>>any(),Matchers.<HashSet<String>>any())).thenReturn(false);
		mockMvc.perform(post("/consents/addConsent.html")
				.param("ICD9", "79571;Nonspecific serologic evidence of human immunodeficiency virus [HIV] (Nonspcf serlgc evdnc hiv)")
				.param("ICD9", "042;Human immunodeficiency virus [HIV] disease (Human immuno virus dis)")
				.param("providersPermittedToDisclose", "1346575297")
				.param("providersDisclosureIsMadeTo","1083949036")
				.param("organizationalProvidersDisclosureIsMadeTo", "1174858088")
				.param("organizationalProvidersPermittedToDisclose", "1174858089")
				.param("consentStart", "06/03/2013")
				.param("consentEnd", "06/17/2014")
				.param("doNotShareSensitivityPolicyCodes", "HIV")
				.param("doNotShareClinicalDocumentSectionTypeCodes", "46240-8")
				.param("doNotShareClinicalDocumentTypeCodes", "18842-5")
				.param("doNotShareForPurposeOfUseCodes", "CLINTRCH"))
			.andExpect(view().name("redirect:listConsents.html?notify=add"));
		verify(consentService).saveConsent(any(ConsentDto.class));
	}
	
	@Test
	public void testAddConsentPost_when_there_are_duplicates() throws Exception{
		when(consentService.areThereDuplicatesInTwoSets(Matchers.<HashSet<String>>any(),Matchers.<HashSet<String>>any())).thenReturn(true);
		mockMvc.perform(post("/consents/addConsent.html")
				.param("ICD9", "79571;Nonspecific serologic evidence of human immunodeficiency virus [HIV] (Nonspcf serlgc evdnc hiv)")
				.param("ICD9", "042;Human immunodeficiency virus [HIV] disease (Human immuno virus dis)"))
			.andExpect(view().name("views/resourceNotFound"));
		verify(consentService,never()).saveConsent(any(ConsentDto.class));
	}
	
	@Test
	public void testSignConsentRevokation_when_authentication_succeeds() throws Exception{
		ConsentRevokationPdfDto consentRevokationPdfDto=mock(ConsentRevokationPdfDto.class);
		when(consentService.findConsentRevokationPdfDto(anyLong())).thenReturn(consentRevokationPdfDto);
		when(consentService.isConsentBelongToThisUser(anyLong(), anyLong())).thenReturn(true);
		mockMvc.perform(post("/consents/signConsentRevokation").param("consentId", "1").param("revokationType", "NO NEVER"))
			.andExpect(view().name("redirect:listConsents.html?emailsent=true"));
		verify(consentRevokationPdfDto).setRevokationType("NO NEVER");
	}
	
	@Test
	public void testSignConsentRevokation_when_authentication_fails() throws Exception{
		ConsentRevokationPdfDto consentRevokationPdfDto=mock(ConsentRevokationPdfDto.class);
		when(consentService.findConsentRevokationPdfDto(anyLong())).thenReturn(consentRevokationPdfDto);
		when(consentService.isConsentBelongToThisUser(anyLong(), anyLong())).thenReturn(false);
		mockMvc.perform(post("/consents/signConsentRevokation").param("consentId", "1").param("revokationType", "NO NEVER"))
			.andExpect(view().name("redirect:listConsents.html?emailsent=false"));
		verify(consentService,never()).signConsentRevokation(consentRevokationPdfDto);
	}
	
	@Test
	public void testExportXACMLConsents_when_authentication_succeeds() throws Exception{
		when(consentService.isConsentBelongToThisUser(anyLong(), anyLong())).thenReturn(true);
		mockMvc.perform(get("/consents/exportXACMLConsents/1"))
		.andExpect(status().isOk());
		
		verify(consentExportService).exportConsent2XACML((long)1);
	}
	
	@Test
	public void testExportCDAR2Consents_when_authentication_succeeds() throws Exception{
		when(consentService.isConsentBelongToThisUser(anyLong(), anyLong())).thenReturn(true);
		mockMvc.perform(get("/consents/exportCDAR2Consents/1"))
		.andExpect(status().isOk());
		
		verify(consentExportService).exportConsent2CDAR2((long)1);
	}
	
	@Test
	public void testExportXACMLConsents_when_authentication_fails() throws Exception{
		when(consentService.isConsentBelongToThisUser(anyLong(), anyLong())).thenReturn(false);
		mockMvc.perform(get("/consents/exportXACMLConsents/1"))
		.andExpect(status().isOk());
		
		verify(consentExportService,never()).exportConsent2XACML((long)1);
	}
	
	@Test
	public void testExportCDAR2Consents_when_authentication_fails() throws Exception{
		when(consentService.isConsentBelongToThisUser(anyLong(), anyLong())).thenReturn(false);
		mockMvc.perform(get("/consents/exportCDAR2Consents/1"))
		.andExpect(status().isOk());
		
		verify(consentExportService,never()).exportConsent2CDAR2((long)1);
	}
	
	@Test
	public void testAjaxCheckConsentStatus_when_consent_was_signed() throws Exception{
				
		List<ConsentListDto> consentListDtos=new ArrayList<ConsentListDto>();
		ConsentListDto consentListDto=mock(ConsentListDto.class);
		consentListDtos.add(consentListDto);
		
		when(consentListDto.getId()).thenReturn((long)2);
		when(consentListDto.getConsentStage()).thenReturn(2);
		
		mockMvc.perform(get("/consents/listConsents.html/checkConsentStatus?consentPreSignList=2"))
		.andExpect(status().isOk());	
			
	}
	
	@Test
	public void testAjaxCheckConsentStatus_when_consent_not_signed() throws Exception{
				
		List<ConsentListDto> consentListDtos=new ArrayList<ConsentListDto>();
		ConsentListDto consentListDto=mock(ConsentListDto.class);
		consentListDtos.add(consentListDto);
		
		when(consentListDto.getId()).thenReturn((long)2);
		when(consentListDto.getConsentStage()).thenReturn(1);
		
		mockMvc.perform(get("/consents/listConsents.html/checkConsentStatus?consentPreSignList=2"))
		.andExpect(status().isOk());	
			
	}
	
	@Test
	public void testAjaxCheckConsentStatus_when_consent_was_revoked() throws Exception{
				
		List<ConsentListDto> consentListDtos=new ArrayList<ConsentListDto>();
		ConsentListDto consentListDto=mock(ConsentListDto.class);
		consentListDtos.add(consentListDto);
		
		when(consentListDto.getId()).thenReturn((long)2);
		when(consentListDto.getRevokeStage()).thenReturn(2);
		
		mockMvc.perform(get("/consents/listConsents.html/checkConsentStatus?consentPreRevokeList=2"))
		.andExpect(status().isOk());	
			
	}
	
	@Test
	public void testAjaxCheckConsentStatus_when_consent_not_revoked() throws Exception{
				
		List<ConsentListDto> consentListDtos=new ArrayList<ConsentListDto>();
		ConsentListDto consentListDto=mock(ConsentListDto.class);
		consentListDtos.add(consentListDto);
		
		when(consentListDto.getId()).thenReturn((long)2);
		when(consentListDto.getConsentStage()).thenReturn(1);
		
		mockMvc.perform(get("/consents/listConsents.html/checkConsentStatus?consentPreRevokeList=2"))
		.andExpect(status().isOk());	
			
	}
}
