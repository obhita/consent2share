package gov.samhsa.consent2share.si;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.StringUtils;

@RunWith(MockitoJUnitRunner.class)
public class AbstractConsentMessageHandlerTest {

	private static final String FAIL = "FAIL";
	private static final String SUCCESS = "SUCCESS";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private ConsentMessageHandlerImpl sut;

	@Before
	public void setUp() throws Exception {
		sut = new ConsentMessageHandlerImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGenerateMessageId() {
		assertTrue(StringUtils.hasText(sut.generateMessageId()));
	}

	@Test
	public void testHandleMessage_Success() throws Throwable {
		assertEquals(SUCCESS, sut.handleMessage(SUCCESS));
	}

	@Test
	public void testHandleMessage_Throws_RuntimeException() throws Throwable {
		thrown.expect(RuntimeException.class);
		sut.handleMessage(FAIL);
	}

	private class ConsentMessageHandlerImpl extends
			AbstractConsentMessageHandler {

		@Override
		public String handleMessage(String data) throws Throwable {
			if (SUCCESS.equals(data)) {
				return data;
			} else {
				throw new RuntimeException();
			}
		}
	}
}
