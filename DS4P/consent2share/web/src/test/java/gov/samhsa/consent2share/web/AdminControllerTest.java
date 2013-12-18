package gov.samhsa.consent2share.web;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.service.consent.ConsentService;
import gov.samhsa.consent2share.service.dto.AbstractPdfDto;
import gov.samhsa.consent2share.service.dto.ConsentListDto;
import gov.samhsa.consent2share.service.dto.PatientAdminDto;
import gov.samhsa.consent2share.service.dto.PatientProfileDto;
import gov.samhsa.consent2share.service.patient.PatientService;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
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
	
	@InjectMocks
	AdminController adminController;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp(){
		mockMvc = MockMvcBuilders.standaloneSetup(this.adminController).build();
	}
	
	@Test
	public void testGetAdminHome() throws Exception{
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
		.andExpect(view().name("views/Administrator/adminPatientView"));
	}
	
	@Test
	public void testAdminPatientView_when_id_is_not_present() throws Exception{
		mockMvc.perform(get("/Administrator/adminPatientView.html"))
		.andExpect(status().isFound())
		.andExpect(view().name("redirect:/Administrator/adminHome.html"));
	}
	
	@Test
	public void testGetByFirstAndLastName() throws Exception{
		List<PatientAdminDto> PatientAdminDtos=new ArrayList<PatientAdminDto>();
		PatientAdminDto patientAdminDto=new PatientAdminDto();
		patientAdminDto.setFirstName("Mary");
		patientAdminDto.setLastName("Doe");
		patientAdminDto.setId((long)1);
		PatientAdminDtos.add(patientAdminDto);
		when(patientService.findAllPatientByFirstNameAndLastName("mary", "doe")).thenReturn(PatientAdminDtos);
		mockMvc.perform(get("/Administrator//patientlookup/query?firstname=mary&lastname=doe"))
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
	
	

}
