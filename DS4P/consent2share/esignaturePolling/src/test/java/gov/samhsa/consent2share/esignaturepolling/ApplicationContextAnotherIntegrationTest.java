package gov.samhsa.consent2share.esignaturepolling;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;

public class ApplicationContextAnotherIntegrationTest {

	@Test
	public void bootstrapAppFromXml() {
		// Here cannot use WEB-INF/spring/root-context.xml
		GenericXmlApplicationContext context = new GenericXmlApplicationContext();
		context.load("classpath:META-INF/spring/applicationContext*.xml");
		context.refresh();

		assertThat(context, is(notNullValue()));
		
		// EchoSignPollingService
		EsignaturePollingService esignaturePollingService = (EsignaturePollingService)(context.getBean("esignaturePollingService"));
		assertNotNull(esignaturePollingService);

		// EchoSignPollingService
		//EchoSignPollingService echoSignPollingService = (EchoSignPollingService)(context.getBean("esignaturePollingService"));
		//assertNotNull(echoSignPollingService);
	}
}
