package gov.samhsa.consent2share.showcase.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AcsShowCaseExceptionTest {


	@Test
	public void testAcsShowCaseException() {
		//Arrange
		AcsShowCaseException acs = new AcsShowCaseException();
		assertNotNull(acs);
		
		String errMsg = "acsShowCaseException Message";
		acs = new AcsShowCaseException(errMsg);
		assertEquals(errMsg, acs.getMessage());
		
		acs = new AcsShowCaseException(new Throwable(errMsg));
		assertEquals("java.lang.Throwable: "+ errMsg, acs.getMessage());
		
		acs = new AcsShowCaseException(errMsg, new Throwable(errMsg));
		assertEquals(errMsg, acs.getMessage());
		assertEquals(Throwable.class , acs.getCause().getClass());
		
	}

}
