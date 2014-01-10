package gov.samhsa.consent2share.web;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import gov.samhsa.consent2share.infrastructure.security.AuthenticatedUser;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.service.audit.AuditService;
import gov.samhsa.consent2share.service.dto.HistoryDto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class AuditTrailControllerTest {

	@InjectMocks
	AuditTrailController auditTrailController = new AuditTrailController();

	@Mock
	AuditService patientAuditService;

	@Mock
	UserContext userContext;

	@Before
	public void setUp() throws Exception {
		AuthenticatedUser user = mock(AuthenticatedUser.class);
		when(userContext.getCurrentUser()).thenReturn(user);
		when(user.getUsername()).thenReturn("mockedUser");

	}

	@Test
	public void testActivityHistory() throws Exception {

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(
				this.auditTrailController).build();

		List<HistoryDto> historyListDtos = new ArrayList<HistoryDto>();
		for (int i = 0; i < 3; i++) {
			historyListDtos.add(mock(HistoryDto.class));
		}
		List<HistoryDto> spyHistoryListDtoss = spy(historyListDtos);

		when(patientAuditService.findAllHistory(anyString())).thenReturn(
				spyHistoryListDtoss);
		mockMvc.perform(get("/patients/activityHistory.html"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("historys", historyListDtos))
				.andExpect(view().name("views/patients/activityHistory"));
	}

}
