package gov.samhsa.consent2share.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {
	
	@InjectMocks
	HomeController homeController = new HomeController();
	
	MockMvc mockMvc;
	
	@Before
	public void before() {
		mockMvc = MockMvcBuilders.standaloneSetup(this.homeController).build();
	}

	@Test
	public void testHome() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(view().name("redirect:/defaultLoginPage.html"));
	}

	@Test
	public void testIndex() throws Exception {
		mockMvc.perform(get("/index.html"))
			.andExpect(view().name("redirect:/patients/home.html"));
	}

	@Test
	public void testError() throws Exception {
		mockMvc.perform(get("/error.html"))
			.andExpect(view().name("WEB-INF/views/error.html"));
	}

}
