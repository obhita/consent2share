package gov.samhsa.consent2share.esignaturepolling;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ApplicationContextIntegrationTest {
	
	@Autowired
	private EsignaturePollingService esignaturePollingService;

	@Test
	public void testSimpleProperties() throws Exception {
		assertNotNull(esignaturePollingService);
	}
}
