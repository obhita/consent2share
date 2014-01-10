package gov.samhsa.consent2share.web;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import gov.samhsa.consent2share.infrastructure.security.AuthenticatedUser;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.service.clinicaldata.ClinicalDocumentService;
import gov.samhsa.consent2share.service.dto.ClinicalDocumentDto;
import gov.samhsa.consent2share.service.dto.LookupDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;
import gov.samhsa.consent2share.service.patient.PatientService;
import gov.samhsa.consent2share.service.reference.ClinicalDocumentTypeCodeService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class ClinicalDocumentControllerTest {

	@InjectMocks
	ClinicalDocumentController clinicalDocumentController = new ClinicalDocumentController();

	@Mock
	ClinicalDocumentService clinicalDocumentService;

	@Mock
	PatientService patientService;

	@Mock
	ClinicalDocumentTypeCodeService clinicalDocumentTypeCodeService;

	@Mock
	UserContext userContext;

	@Mock
	AuthenticatedUser authenticatedUser;

	MockMvc mockMvc;

	@Before
	public void before() {
		mockMvc = MockMvcBuilders.standaloneSetup(
				this.clinicalDocumentController).build();
		when(userContext.getCurrentUser()).thenReturn(authenticatedUser);
		when(authenticatedUser.getUsername()).thenReturn("albert.smith");
	}

	@Test
	public void testDocumentHome() throws Exception {
		mockMvc.perform(get("/patients/medicalinfo.html")).andExpect(
				view().name("views/clinicaldocuments/mymedicalinfo"));

	}

	@Test
	public void testShowClinicalDocuments() throws Exception {
		PatientProfileDto patientProfileDto = mock(PatientProfileDto.class);
		List<ClinicalDocumentDto> clinicaldocumentDtos = new ArrayList<ClinicalDocumentDto>();
		ClinicalDocumentDto notNullClinicalDocumentDto=mock(ClinicalDocumentDto.class);
		LookupDto lookupDto=mock(LookupDto.class);
		when(notNullClinicalDocumentDto.getClinicalDocumentTypeCode()).thenReturn(lookupDto);
		clinicaldocumentDtos.add(notNullClinicalDocumentDto);
		for (int i = 0; i < 3; i++) {
			ClinicalDocumentDto clinicalDocumentDto = mock(ClinicalDocumentDto.class);
			clinicaldocumentDtos.add(clinicalDocumentDto);
		}
		List<LookupDto> allDocumentTypeCodes = new ArrayList<LookupDto>();
		for (int i = 0; i < 3; i++) {
			LookupDto clinicalDocumentTypeCode = mock(LookupDto.class);
			allDocumentTypeCodes.add(clinicalDocumentTypeCode);
		}
		when(patientService.findPatientProfileByUsername(anyString()))
				.thenReturn(patientProfileDto);
		when(clinicalDocumentService.findDtoByPatientDto(patientProfileDto))
				.thenReturn(clinicaldocumentDtos);
		when(clinicalDocumentTypeCodeService.findAllClinicalDocumentTypeCodes())
				.thenReturn(allDocumentTypeCodes);

		mockMvc.perform(get("/patients/clinicaldocuments.html")).andExpect(
				view().name("views/clinicaldocuments/clinicalDocuments"));

	}
	

	@Test
	public void testDownload_when_authentication_succeeds() throws Exception {
		ClinicalDocumentDto clinicalDocumentDto = mock(ClinicalDocumentDto.class);
		String s = "mock string";
		byte[] byteArray = s.getBytes();
		when(clinicalDocumentService.findClinicalDocumentDto(anyLong()))
				.thenReturn(clinicalDocumentDto);
		when(
				clinicalDocumentService
						.isDocumentBelongsToThisUser(clinicalDocumentDto))
				.thenReturn(true);
		when(clinicalDocumentDto.getContent()).thenReturn(byteArray);
		mockMvc.perform(
				post("/patients/downloaddoc.html").param("download_id", "1"))
				.andExpect(status().isOk())
				.andExpect(header().string("Content-Type", "application/null"));

	}
	
	@Test
	public void testDownload_when_authentication_fails() throws Exception {
		ClinicalDocumentDto clinicalDocumentDto = mock(ClinicalDocumentDto.class);
		String s = "mock string";
		byte[] byteArray = s.getBytes();
		when(clinicalDocumentService.findClinicalDocumentDto(anyLong()))
				.thenReturn(clinicalDocumentDto);
		when(
				clinicalDocumentService
						.isDocumentBelongsToThisUser(clinicalDocumentDto))
				.thenReturn(false);
		when(clinicalDocumentDto.getContent()).thenReturn(byteArray);
		mockMvc.perform(
				post("/patients/downloaddoc.html").param("download_id", "1"))
				.andExpect(status().isOk());
		verify(clinicalDocumentDto,never()).getContent();

	}

	@Test
	public void testRemove_when_authentication_fails() throws Exception {
		ClinicalDocumentDto clinicalDocumentDto = mock(ClinicalDocumentDto.class);
		when(clinicalDocumentService.findClinicalDocumentDto(anyLong()))
				.thenReturn(clinicalDocumentDto);
		when(
				clinicalDocumentService
						.isDocumentBelongsToThisUser(clinicalDocumentDto))
				.thenReturn(false);
		mockMvc.perform(
				post("/patients/deletedoc.html").param("delete_id", "1"))
				.andExpect(redirectedUrl("/patients/clinicaldocuments.html"));
		verify (clinicalDocumentService,never()).deleteClinicalDocument(any(ClinicalDocumentDto.class));
	}
	
	@Test
	public void testRemove_when_authentication_succeeds() throws Exception {
		ClinicalDocumentDto clinicalDocumentDto = mock(ClinicalDocumentDto.class);
		when(clinicalDocumentService.findClinicalDocumentDto(anyLong()))
				.thenReturn(clinicalDocumentDto);
		when(
				clinicalDocumentService
						.isDocumentBelongsToThisUser(clinicalDocumentDto))
				.thenReturn(true);
		mockMvc.perform(
				post("/patients/deletedoc.html").param("delete_id", "1"))
				.andExpect(redirectedUrl("/patients/clinicaldocuments.html"));
		verify (clinicalDocumentService).deleteClinicalDocument(any(ClinicalDocumentDto.class));
	}
	
	@Test
	public void testUploadClinicalDocuments() throws Exception{
		MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());
		mockMvc.perform(fileUpload("/patients/clinicaldocuments.html").file(file)
				.param("name", "mocked_name")
				.param("description", "mocked_description")
				.param("documentType", "mocked_type"))
			.andExpect(view().name("redirect:/patients/clinicaldocuments.html"));
	}

}
