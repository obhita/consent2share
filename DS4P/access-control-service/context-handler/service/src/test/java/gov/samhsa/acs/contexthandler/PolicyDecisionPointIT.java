package gov.samhsa.acs.contexthandler;

import static org.junit.Assert.assertTrue;
import gov.samhsa.acs.contexthandler.PolicyDecisionPointImpl;

import java.util.Iterator;

import org.herasaf.xacml.core.policy.Evaluatable;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PolicyDecisionPointIT {
	private static final Logger LOGGER = LoggerFactory.getLogger(PolicyDecisionPointImpl.class);
	private static StringBuilder result;
	@BeforeClass
	public static void setUp() throws Exception {
		result = new StringBuilder();
	}

	// TODO: Refactor when we use XDS.b repository
//	@Test
//	public void testDBConnection() {
//		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//		PolicyDecisionPoint policyDecisionPoint=(PolicyDecisionPoint) context.getBean("policyDecisionPoint");
//		Iterator<Evaluatable> iterator=policyDecisionPoint.getPolicies("consent2share@outlook.com").iterator();
//		while (iterator.hasNext()){
//			String s = iterator.next().toString();
//			LOGGER.debug(s);
//			result.append(s);
//		}
//		assertTrue(result.toString().startsWith("org.herasaf.xacml.core.policy.impl.PolicyType@"));
//	}

}
