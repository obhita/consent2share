package gov.samhsa.ds4ppilot.orchestrator.contexthandler;

import java.util.Iterator;

import org.herasaf.xacml.core.policy.Evaluatable;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PolicyDecisionPointIT {
	private static final Logger LOGGER = LoggerFactory.getLogger(PolicyDecisionPointImpl.class);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testDBConnection() {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		PolicyDecisionPoint policyDecisionPoint=(PolicyDecisionPoint) context.getBean("policyDecisionPoint");
		Iterator<Evaluatable> iterator=policyDecisionPoint.getPolicies("consent2share@gmail.com").iterator();
		while (iterator.hasNext()){
			LOGGER.debug(iterator.next().toString());
		}
	}

}
