package gov.samhsa.consent2share;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import gov.samhsa.consent2share.domain.account.TokenGenerator;
import gov.samhsa.consent2share.domain.commondomainservices.SignatureService;
import gov.samhsa.consent2share.infrastructure.EchoSignSignatureService;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class ApplicationContextIntegrationTest {

	@Test
	public void bootstrapAppFromXml() {
		// Here cannot use WEB-INF/spring/root-context.xml
		GenericXmlApplicationContext context = new GenericXmlApplicationContext();
		context.getEnvironment().setActiveProfiles("test");
		context.load("classpath:META-INF/spring/applicationContext*.xml");
		context.refresh();

		assertThat(context, is(notNullValue()));

		// Test StandardPasswordEncoder bean is loaded
		StandardPasswordEncoder passwordEncoder = (StandardPasswordEncoder) context
				.getBean(StandardPasswordEncoder.class);
		assertNotNull(passwordEncoder);

		// ModelMapper
		ModelMapper modelMapper = context.getBean(ModelMapper.class);
		assertNotNull(modelMapper);

		// DataSource
		BasicDataSource dataSource = (BasicDataSource) (context
				.getBean("dataSource"));
		assertNotNull(dataSource);
		// assertEquals("jdbc:mysql://localhost:3306/test",
		// dataSource.getUrl());

		// TokenGenerator
		TokenGenerator tokenGenerator = (TokenGenerator) (context
				.getBean(TokenGenerator.class));
		assertNotNull(tokenGenerator);

		// SignatureService
		SignatureService signatureService = (SignatureService) (context
				.getBean(SignatureService.class));
		assertNotNull(signatureService);

		// EchoSignSignatureService
		EchoSignSignatureService echoSignSignatureService = (EchoSignSignatureService) (context
				.getBean(EchoSignSignatureService.class));
		assertNotNull(echoSignSignatureService);
	}
}
