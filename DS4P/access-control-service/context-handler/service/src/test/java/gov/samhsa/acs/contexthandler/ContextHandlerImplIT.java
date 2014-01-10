package gov.samhsa.acs.contexthandler;

import static org.junit.Assert.assertEquals;
import gov.samhsa.acs.contexthandler.ContextHandler;
import gov.samhsa.acs.contexthandler.PolicyDecisionPoint;
import gov.samhsa.acs.contexthandler.RequestGenerator;

import gov.va.ehtac.ds4p.ws.EnforcePolicy;
import gov.va.ehtac.ds4p.ws.EnforcePolicyResponse.Return;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// TODO: These integration tests are written to test the manual context handler mapping for demo purposes
public class ContextHandlerImplIT {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ContextHandlerImplIT.class);
	
	private final String PERMIT = "Permit";
	private final String DENY = "Deny";
	private final String NOT_APPLICABLE = "Not_applicable";
	
	private ContextHandler contextHandler;
	
	private PolicyDecisionPoint policyDecisionPoint;
	
	private RequestGenerator requestGenerator;
	
	private String patientId;
	private boolean packageAsXdm;
	private String senderEmailAddress;
	private String recipientEmailAddres;


	// subject parameter config
	private String subjectPurposeOfUse;
	private String subjectLocality;
	private String organization;
	private String organizationId;
	
	// resource parameter config
	private String resourceName;
	private String resourceType;
	private String resourceAction;
//
//	@Before
//	public void setUp() throws Exception {
//		this.requestGenerator = new RequestGenerator();
//		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//		this.policyDecisionPoint=(PolicyDecisionPointImpl) context.getBean("policyDecisionPoint");
//		contextHandler = new ContextHandlerImpl(policyDecisionPoint, requestGenerator);		
//	}
//
//	public EnforcePolicy.Xspasubject setXspaSubject(
//			String recipientEmailAddress, String messageId) {
//		EnforcePolicy.Xspasubject xspasubject = new EnforcePolicy.Xspasubject();
//		xspasubject.setSubjectPurposeOfUse(subjectPurposeOfUse);
//		xspasubject.setSubjectLocality(subjectLocality);
//		xspasubject.setSubjectEmailAddress(recipientEmailAddres);
//		xspasubject.setSubjectId(recipientEmailAddres);
//		xspasubject.setOrganization(organization);
//		xspasubject.setOrganizationId(organizationId);
//		xspasubject.setMessageId(messageId);
//		return xspasubject;
//	}
//	public EnforcePolicy.Xsparesource setXspaResource(String patientId) {
//		EnforcePolicy.Xsparesource xsparesource = new EnforcePolicy.Xsparesource();
//		xsparesource.setResourceId(patientId);
//		xsparesource.setResourceName(resourceName);
//		xsparesource.setResourceType(resourceType);
//		xsparesource.setResourceAction(resourceAction);
//		return xsparesource;
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	// TODO: This integration test is written to test the manual context handler mapping for demo purposes
//	@Test
//	public final void testEnforcePolicyXspasubjectXsparesource_Joel() {
//		
//		// Arrange
//		patientId = "PUI100010060001";
//		packageAsXdm = true;
//		senderEmailAddress = "leo.smith@direct.obhita-stage.org";
//		recipientEmailAddres = "joel_amoussou@direct.healthvault-stage.com";
//		
//		// subject parameter config
//		subjectPurposeOfUse ="TREAT";
//		subjectLocality ="2.16.840.1.113883.3.467";
//		organization ="SAMHSA";
//		organizationId ="FEiSystems";
//		
//		// resource parameter config
//		resourceName = "NwHINDirectSend";
//		resourceType = "C32";
//		resourceAction = "Execute";
//		
//		EnforcePolicy.Xspasubject xspasubject = setXspaSubject(this.recipientEmailAddres, UUID.randomUUID().toString());
//		EnforcePolicy.Xsparesource xsparesource = setXspaResource(this.patientId);
//		
//		// Act
//		Return result = contextHandler.enforcePolicy(xspasubject, xsparesource);
//		LOGGER.debug(result.getPdpDecision());
//		if(result.getPdpObligation() != null)
//		{
//			for(String s: result.getPdpObligation())
//			{
//				LOGGER.debug("obligation: " + s);
//			}
//		}
//		
//		// Assert
//		assertEquals(PERMIT, result.getPdpDecision());
//
//	}
//	
//	// TODO: This integration test is written to test the manual context handler mapping for demo purposes
//	@Test
//	public final void testEnforcePolicyXspasubjectXsparesource_Tao() {
//		
//		// Arrange
//		patientId = "PUI100010060001";
//		packageAsXdm = true;
//		senderEmailAddress = "leo.smith@direct.obhita-stage.org";
//		recipientEmailAddres = "Tao.Lin@direct.healthvault-stage.com";
//		
//		// subject parameter config
//		subjectPurposeOfUse ="TREAT";
//		subjectLocality ="2.16.840.1.113883.3.467";
//		organization ="SAMHSA";
//		organizationId ="FEiSystems";
//		
//		// resource parameter config
//		resourceName = "NwHINDirectSend";
//		resourceType = "C32";
//		resourceAction = "Execute";
//		
//		EnforcePolicy.Xspasubject xspasubject = setXspaSubject(this.recipientEmailAddres, UUID.randomUUID().toString());
//		EnforcePolicy.Xsparesource xsparesource = setXspaResource(this.patientId);
//		
//		// Act
//		Return result = contextHandler.enforcePolicy(xspasubject, xsparesource);
//		LOGGER.debug(result.getPdpDecision());
//		if(result.getPdpObligation() != null)
//		{
//			for(String s: result.getPdpObligation())
//			{
//				LOGGER.debug("obligation: " + s);
//			}
//		}
//		
//		// Assert
//		assertEquals(PERMIT, result.getPdpDecision());
//
//	}
//	
//	// TODO: This integration test is written to test the manual context handler mapping for demo purposes
//	@Test
//	public final void testEnforcePolicyXspasubjectXsparesource_DenyByRecEmail() {
//		
//		// Arrange
//		patientId = "PUI100010060001";
//		packageAsXdm = true;
//		senderEmailAddress = "leo.smith@direct.obhita-stage.org";
//		recipientEmailAddres = "wrong@emailaddress.com";
//		
//		// subject parameter config
//		subjectPurposeOfUse ="TREAT";
//		subjectLocality ="2.16.840.1.113883.3.467";
//		organization ="SAMHSA";
//		organizationId ="FEiSystems";
//		
//		// resource parameter config
//		resourceName = "NwHINDirectSend";
//		resourceType = "C32";
//		resourceAction = "Execute";
//		
//		EnforcePolicy.Xspasubject xspasubject = setXspaSubject(this.recipientEmailAddres, UUID.randomUUID().toString());
//		EnforcePolicy.Xsparesource xsparesource = setXspaResource(this.patientId);
//		
//		// Act
//		Return result = contextHandler.enforcePolicy(xspasubject, xsparesource);
//		LOGGER.debug(result.getPdpDecision());
//		if(result.getPdpObligation() != null)
//		{
//			for(String s: result.getPdpObligation())
//			{
//				LOGGER.debug("obligation: " + s);
//			}
//		}
//		
//		// Assert
//		assertEquals(DENY, result.getPdpDecision());
//
//	}
//	
//	// TODO: This integration test is written to test the manual context handler mapping for demo purposes
//	@Test
//	public final void testEnforcePolicyXspasubjectXsparesource_DenyByPatientId() {
//		
//		// Arrange
//		patientId = "XXXXXXXXXXXXX";
//		packageAsXdm = true;
//		senderEmailAddress = "leo.smith@direct.obhita-stage.org";
//		recipientEmailAddres = "Tao.Lin@direct.healthvault-stage.com";
//		
//		// subject parameter config
//		subjectPurposeOfUse ="TREAT";
//		subjectLocality ="2.16.840.1.113883.3.467";
//		organization ="SAMHSA";
//		organizationId ="FEiSystems";
//		
//		// resource parameter config
//		resourceName = "NwHINDirectSend";
//		resourceType = "C32";
//		resourceAction = "Execute";
//		
//		EnforcePolicy.Xspasubject xspasubject = setXspaSubject(this.recipientEmailAddres, UUID.randomUUID().toString());
//		EnforcePolicy.Xsparesource xsparesource = setXspaResource(this.patientId);
//		
//		// Act
//		Return result = contextHandler.enforcePolicy(xspasubject, xsparesource);
//		LOGGER.debug(result.getPdpDecision());
//		if(result.getPdpObligation() != null)
//		{
//			for(String s: result.getPdpObligation())
//			{
//				LOGGER.debug("obligation: " + s);
//			}
//		}
//		
//		// Assert
//		assertEquals(NOT_APPLICABLE, result.getPdpDecision());
//
//	}
//	
//	// TODO: This integration test is written to test the manual context handler mapping for demo purposes
//	@Test
//	public final void testEnforcePolicyXspasubjectXsparesource_DifferentSenderEmail() {
//		
//		// Arrange
//		patientId = "PUI100010060001";
//		packageAsXdm = true;
//		senderEmailAddress = "wrong@emailaddress.com";
//		recipientEmailAddres = "Tao.Lin@direct.healthvault-stage.com";
//		
//		// subject parameter config
//		subjectPurposeOfUse ="TREAT";
//		subjectLocality ="2.16.840.1.113883.3.467";
//		organization ="SAMHSA";
//		organizationId ="FEiSystems";
//		
//		// resource parameter config
//		resourceName = "NwHINDirectSend";
//		resourceType = "C32";
//		resourceAction = "Execute";
//		
//		EnforcePolicy.Xspasubject xspasubject = setXspaSubject(this.recipientEmailAddres, UUID.randomUUID().toString());
//		EnforcePolicy.Xsparesource xsparesource = setXspaResource(this.patientId);
//		
//		// Act
//		Return result = contextHandler.enforcePolicy(xspasubject, xsparesource);
//		LOGGER.debug(result.getPdpDecision());
//		if(result.getPdpObligation() != null)
//		{
//			for(String s: result.getPdpObligation())
//			{
//				LOGGER.debug("obligation: " + s);
//			}
//		}
//		
//		// Assert
//		assertEquals(PERMIT, result.getPdpDecision());
//
//	}
//	
//	// TODO: This integration test is written to test the manual context handler mapping for demo purposes
//	@Test
//	public final void testEnforcePolicyXspasubjectXsparesource_DifferentPurposeOfUse() {
//		
//		// Arrange
//		patientId = "PUI100010060001";
//		packageAsXdm = true;
//		senderEmailAddress = "leo.smith@direct.obhita-stage.org";
//		recipientEmailAddres = "Tao.Lin@direct.healthvault-stage.com";
//		
//		// subject parameter config
//		subjectPurposeOfUse ="MARKT";
//		subjectLocality ="2.16.840.1.113883.3.467";
//		organization ="SAMHSA";
//		organizationId ="FEiSystems";
//		
//		// resource parameter config
//		resourceName = "NwHINDirectSend";
//		resourceType = "C32";
//		resourceAction = "Execute";
//		
//		EnforcePolicy.Xspasubject xspasubject = setXspaSubject(this.recipientEmailAddres, UUID.randomUUID().toString());
//		EnforcePolicy.Xsparesource xsparesource = setXspaResource(this.patientId);
//		
//		// Act
//		Return result = contextHandler.enforcePolicy(xspasubject, xsparesource);
//		LOGGER.debug(result.getPdpDecision());
//		if(result.getPdpObligation() != null)
//		{
//			for(String s: result.getPdpObligation())
//			{
//				LOGGER.debug("obligation: " + s);
//			}
//		}
//		
//		// Assert
//		assertEquals(PERMIT, result.getPdpDecision());
//
//	}
//	

}
