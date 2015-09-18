package com.feisystems.provider.web.controller.instrumentation;

import org.junit.Test;
import org.junit.Before;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;;

public class LoggerCheckControllerTest {
	
	private MockMvc mockMvc;
	LoggerCheckController loggerCheckController;
	
	private LoggerCheckController sut;
	
	
	@Before
	public void setUp() {
		sut = new LoggerCheckController("bla");

		mockMvc = standaloneSetup(this.sut).build();
	}

	@Test
	public void testCheck_Returns_Bad_Request_When_HttpGet_Without_Para_Key()
			throws Exception {
		
		mockMvc.perform(get("/instrumentation/loggerCheck?instrumentationKey")).andExpect(
				status().is(400));
	}
	
	@Test
	public void testCheck_When_HttpGet_With_Para_Key_And_When_Key_Incorrect()
			throws Exception {
		final String keyVaule = "1234";
		
		mockMvc.perform(
				get("/instrumentation/loggerCheck?instrumentationKey=").param("instrumentationKey", keyVaule))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.TEXT_HTML))
				.andExpect(content().string("<h2>You are not authorized to access this page.</h2>"));
	}
}
