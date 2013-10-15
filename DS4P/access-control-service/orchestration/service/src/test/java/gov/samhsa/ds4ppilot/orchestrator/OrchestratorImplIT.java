package gov.samhsa.ds4ppilot.orchestrator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import gov.samhsa.ds4ppilot.orchestrator.c32getter.C32GetterImpl;
import gov.samhsa.ds4ppilot.orchestrator.contexthandler.ContextHandlerImpl;
import gov.samhsa.ds4ppilot.orchestrator.contexthandler.PolicyDecisionPoint;
import gov.samhsa.ds4ppilot.orchestrator.contexthandler.PolicyDecisionPointImpl;
import gov.samhsa.ds4ppilot.orchestrator.contexthandler.RequestGenerator;
import gov.samhsa.ds4ppilot.orchestrator.xdsbregistry.XdsbRegistryImpl;
import gov.samhsa.ds4ppilot.orchestrator.xdsbrepository.XdsbRepositoryImpl;
import gov.samhsa.ds4ppilot.schema.orchestrator.FilterC32Response;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.herasaf.xacml.core.context.RequestMarshaller;
import org.herasaf.xacml.core.context.impl.RequestType;
import org.herasaf.xacml.core.policy.Evaluatable;
import org.herasaf.xacml.core.policy.PolicyMarshaller;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class OrchestratorImplIT {
	
//	private static final Logger LOGGER = LoggerFactory.getLogger(OrchestratorImplIT.class);
//
//	private static boolean packageXdm;
//	private static String senderEmailAddress;
//	private static String reciepientEmailAddress;
//
//	private final static String PERMIT_DECISION = "PERMIT";
//	private final static String DENY_DECISION = "DENY";
//	private final static String NOT_APPLICABLE = "NOT_APPLICABLE";
//	
//	private PolicyDecisionPointImpl policyDecisionPoint;
//
//	@Before
//	public void setUp() {
//		packageXdm = true;
//		senderEmailAddress = "leo.smith@direct.obhita-stage.org";
//		reciepientEmailAddress = "Duane_Decouteau@direct.healthvault-stage.com";
//		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//		policyDecisionPoint=(PolicyDecisionPointImpl) context.getBean("policyDecisionPoint");
//		
//	}
//
//	// Integration test
//	@Test
//	public void testHandleC32Request_Permit() throws Exception {
//		
//		// Arrange
//
//		String samplePolicyPath = "src/test/resources/samplePolicy.xml";
//		String samplePolicyRequestPath = "src/test/resources/samplePolicyRequest.xml";
//		String recepientSubjectNPI = "1568797520";
//		String intermediarySubjectNPI = "1285969170";
//		String resourceId = "consent2share@outlook.com";
////		String purposeOfUse = "PWATRNY";
//		String purposeOfUse = "TREAT";
//		String xdsDocumentEntryUniqueId = "123";
//		
//		// pdp
//		PolicyDecisionPoint pdpSpy = spy(policyDecisionPoint);
//		Evaluatable policy=null;
//		try {
//			InputStream is = new FileInputStream(samplePolicyPath);
//			policy=PolicyMarshaller.unmarshal(is);
//		} catch (Exception e) {
//			LOGGER.debug(e.toString(),e);
//		}
//		List<Evaluatable> policies = new ArrayList<Evaluatable>();
//		policies.add(policy);
//		when(pdpSpy.getPolicies(resourceId)).thenReturn(policies);
//		
//		// pdp request
//		RequestGenerator requestGeneratorMock = mock(RequestGenerator.class);
//		InputStream requestis=null;
//		RequestType request=null;
//		try {
//			requestis=new FileInputStream(samplePolicyRequestPath);
//			request=RequestMarshaller.unmarshal(requestis);
//		} catch (Exception e) {
//			LOGGER.debug(e.toString(),e);
//		}
//		when(requestGeneratorMock.generateRequest(recepientSubjectNPI, intermediarySubjectNPI, purposeOfUse, resourceId)).thenReturn(request);
//		
//		testPermit(recepientSubjectNPI, intermediarySubjectNPI, resourceId,
//				purposeOfUse, xdsDocumentEntryUniqueId, pdpSpy,
//				requestGeneratorMock);
//	}
//
//	private void testPermit(String recepientSubjectNPI,
//			String intermediarySubjectNPI, String resourceId,
//			String purposeOfUse, String xdsDocumentEntryUniqueId,
//			PolicyDecisionPoint pdpSpy, RequestGenerator requestGeneratorMock)
//			throws IOException, FileNotFoundException {
//		// context handler
//		ContextHandlerImpl contextHandler = new ContextHandlerImpl(pdpSpy, requestGeneratorMock);
//
//		// c32 getter
////		final String endpointAddress = "http://localhost/Rem.Web/C32Service.svc";
////		C32GetterImpl c32Getter = new C32GetterImpl(endpointAddress);
//		C32GetterImpl c32Getter = mock(C32GetterImpl.class);
//		String c32String = loadXMLAsString("c32.xml");
//		when(c32Getter.getC32(resourceId)).thenReturn(c32String);
//		
//		// document segmentation
//		final String documentSegmentationEndpointAddress = "http://localhost:90/DocumentSegmentation/services/DocumentSegmentationService";
//		DocumentSegmentationHandlerImpl documentSegmentation = new DocumentSegmentationHandlerImpl();
//
//		// dataHandlerToBytesConverter
//		DataHandlerToBytesConverter dataHandlerToBytesConverter = new DataHandlerToBytesConverterImpl();
//		
//		// xdsbRepository
//		final String xdsbRepositoryEndpointAddress = "http://feijboss01:8080/axis2/services/xdsrepositoryb";
//		XdsbRepositoryImpl xdsbRepository = new XdsbRepositoryImpl(
//				xdsbRepositoryEndpointAddress);
//		
//		// xdsbRegistry
//		final String xdsbRegistryEndpointAddress = "http://feijboss01:8080/axis2/services/xdsregistryb";
//		XdsbRegistryImpl xdsbRegistry = new XdsbRegistryImpl(
//				xdsbRegistryEndpointAddress);
//
//		
//		// orchestrator
//		OrchestratorImpl orchestrator = new OrchestratorImpl(contextHandler,
//				c32Getter, documentSegmentation, dataHandlerToBytesConverter,
//				xdsbRepository, xdsbRegistry);		
////		orchestrator.setSubjectPurposeOfUse("TREAT");
//		orchestrator.setSubjectPurposeOfUse(purposeOfUse);
//		orchestrator.setSubjectLocality("2.16.840.1.113883.3.467");
//		orchestrator.setOrganization("SAMHSA");
//		orchestrator.setOrganizationId("FEiSystems");
//		orchestrator.setResourceName("NwHINDirectSend");
//		orchestrator.setResourceType("C32");
//		orchestrator.setResourceAction("Execute");
//		orchestrator.setHomeCommunityId("2.16.840.1.113883.3.467");
//
//		// Act
////		FilterC32Response c32Response = orchestrator.handleC32Request(
////				patientIdPermit, packageXdm, senderEmailAddress,
////				reciepientEmailAddress);
//		FilterC32Response c32Response = orchestrator.handleC32Request(recepientSubjectNPI, intermediarySubjectNPI, resourceId, packageXdm, senderEmailAddress, reciepientEmailAddress, xdsDocumentEntryUniqueId);
//		writePackageToFile(c32Response);
//		LOGGER.debug(c32Response.getPdpDecision());
//		LOGGER.debug(c32Response.getMaskedDocument());
//		
//		// Assert
//		assertEquals(PERMIT_DECISION, c32Response.getPdpDecision());
//	}
//
//	
//	/**
//	 * @param c32Response
//	 * @throws IOException
//	 * @throws FileNotFoundException
//	 */
//	private void writePackageToFile(FilterC32Response c32Response)
//			throws IOException, FileNotFoundException {
//
//		byte[] bytes = c32Response.getFilteredStreamBody();
//
//		FileOutputStream fos = new FileOutputStream("xdm.zip");
//		try {
//			fos.write(bytes);
//		} finally {
//			fos.close();
//		}
//	}
//
//	// Integration test
//	@Test
//	public void testHandleC32Request_DenyByIntermediarySubjectNPIMismatch() throws Exception {
//		
//		// Arrange
//
//		String samplePolicyPath = "src/test/resources/samplePolicyDenyByIntermediarySubjectNPIMismatch.xml";
//		String samplePolicyRequestPath = "src/test/resources/samplePolicyRequest.xml";
//		String recepientSubjectNPI = "1568797520";
//		String intermediarySubjectNPI = "1111111111";
//		String resourceId = "consent2share@outlook.com";
////		String purposeOfUse = "PWATRNY";
//		String purposeOfUse = "TREAT";
//		String xdsDocumentEntryUniqueId = "123";
//		
//		// pdp
//		PolicyDecisionPoint pdpSpy = spy(policyDecisionPoint);
//		Evaluatable policy=null;
//		try {
//			InputStream is = new FileInputStream(samplePolicyPath);
//			policy=PolicyMarshaller.unmarshal(is);
//		} catch (Exception e) {
//			LOGGER.debug(e.toString(),e);
//		}
//		List<Evaluatable> policies = new ArrayList<Evaluatable>();
//		policies.add(policy);
//		when(pdpSpy.getPolicies(resourceId)).thenReturn(policies);
//		
//		// pdp request
//		RequestGenerator requestGeneratorMock = mock(RequestGenerator.class);
//		InputStream requestis=null;
//		RequestType request=null;
//		try {
//			requestis=new FileInputStream(samplePolicyRequestPath);
//			request=RequestMarshaller.unmarshal(requestis);
//		} catch (Exception e) {
//			LOGGER.debug(e.toString(),e);
//		}
//		when(requestGeneratorMock.generateRequest(recepientSubjectNPI, intermediarySubjectNPI, purposeOfUse, resourceId)).thenReturn(request);
//		
//		testDeny(recepientSubjectNPI, intermediarySubjectNPI, resourceId,
//				purposeOfUse, xdsDocumentEntryUniqueId, pdpSpy,
//				requestGeneratorMock);
//	}
//
//	private void testDeny(String recepientSubjectNPI,
//			String intermediarySubjectNPI, String resourceId,
//			String purposeOfUse, String xdsDocumentEntryUniqueId,
//			PolicyDecisionPoint pdpSpy, RequestGenerator requestGeneratorMock) {
//		// context handler
//		ContextHandlerImpl contextHandler = new ContextHandlerImpl(pdpSpy, requestGeneratorMock);
//
//		// c32 getter
////		final String endpointAddress = "http://localhost/Rem.Web/C32Service.svc";
////		C32GetterImpl c32Getter = new C32GetterImpl(endpointAddress);
//		C32GetterImpl c32Getter = mock(C32GetterImpl.class);
//		String c32String = loadXMLAsString("c32.xml");
//		when(c32Getter.getC32(resourceId)).thenReturn(c32String);
//
//		// document segmentation
//		final String documentSegmentationEndpointAddress = "http://localhost:90/DocumentSegmentation/services/DocumentSegmentationService";
//		DocumentSegmentationHandlerImpl documentSegmentation = new DocumentSegmentationHandlerImpl();
//
//		// dataHandlerToBytesConverter
//		DataHandlerToBytesConverter dataHandlerToBytesConverter = new DataHandlerToBytesConverterImpl();
//		
//		// xdsbRepository
//		final String xdsbRepositoryEndpointAddress = "http://feijboss01:8080/axis2/services/xdsrepositoryb";
//		XdsbRepositoryImpl xdsbRepository = new XdsbRepositoryImpl(
//				xdsbRepositoryEndpointAddress);
//
//		// xdsbRegistry
//		final String xdsbRegistryEndpointAddress = "http://feijboss01:8080/axis2/services/xdsregistryb";
//		XdsbRegistryImpl xdsbRegistry = new XdsbRegistryImpl(
//				xdsbRegistryEndpointAddress);
//
//		// orchestrator
//		OrchestratorImpl orchestrator = new OrchestratorImpl(contextHandler,
//				c32Getter, documentSegmentation, dataHandlerToBytesConverter,
//				xdsbRepository, xdsbRegistry);
////		orchestrator.setSubjectPurposeOfUse("TREAT");
//		orchestrator.setSubjectPurposeOfUse(purposeOfUse);
//		orchestrator.setSubjectLocality("2.16.840.1.113883.3.467");
//		orchestrator.setOrganization("SAMHSA");
//		orchestrator.setOrganizationId("FEiSystems");
//		orchestrator.setResourceName("NwHINDirectSend");
//		orchestrator.setResourceType("C32");
//		orchestrator.setResourceAction("Execute");
//		orchestrator.setHomeCommunityId("2.16.840.1.113883.3.467");
//
//		// Act
////		FilterC32Response c32Response = orchestrator.handleC32Request(
////				patientIdPermit, packageXdm, senderEmailAddress,
////				reciepientEmailAddress);
//		FilterC32Response c32Response = orchestrator.handleC32Request(recepientSubjectNPI, intermediarySubjectNPI, resourceId, packageXdm, senderEmailAddress, reciepientEmailAddress, xdsDocumentEntryUniqueId);
//		LOGGER.debug(c32Response.getPdpDecision());
//		LOGGER.debug(c32Response.getMaskedDocument());
//		
//		// Assert
//		assertEquals(DENY_DECISION, c32Response.getPdpDecision());
//		assertNull("c32Response shouldn't have any documents, because it should have been denied.", c32Response.getMaskedDocument());
//	}
//
//	// Integration test
//	@Test
//	public void testHandleC32Request_DenyByTime() throws Exception {
//		
//		// Arrange
//
//		String samplePolicyPath = "src/test/resources/samplePolicy.xml";
////		String samplePolicyRequestPath = "";
//		String recepientSubjectNPI = "1568797520";
//		String intermediarySubjectNPI = "1285969170";
//		String resourceId = "consent2share@outlook.com";
////		String purposeOfUse = "PWATRNY";
//		String purposeOfUse = "TREAT";
//		String xdsDocumentEntryUniqueId = "123";
//		
//		// pdp
//		PolicyDecisionPoint pdpSpy = spy(policyDecisionPoint);
//		Evaluatable policy=null;
//		try {
//			InputStream is = new FileInputStream(samplePolicyPath);
//			policy=PolicyMarshaller.unmarshal(is);
//		} catch (Exception e) {
//			LOGGER.debug(e.toString(),e);
//		}
//		List<Evaluatable> policies = new ArrayList<Evaluatable>();
//		policies.add(policy);
//		when(pdpSpy.getPolicies(resourceId)).thenReturn(policies);
//		
//		// pdp request
//		RequestGenerator requestGenerator = new RequestGenerator();
//		
//		testDeny(recepientSubjectNPI, intermediarySubjectNPI, resourceId,
//				purposeOfUse, xdsDocumentEntryUniqueId, pdpSpy,
//				requestGenerator);
//	}
//
//	// Integration test
//	@Test
//	public void testHandleC32Request_DenyByRecepientSubjectNPIMismatch() throws Exception {
//		
//		// Arrange
//
//		String samplePolicyPath = "src/test/resources/samplePolicyDenyByRecepientSubjectNPIMismatch.xml";
//		String samplePolicyRequestPath = "src/test/resources/samplePolicyRequest.xml";
//		String recepientSubjectNPI = "1111111111";
//		String intermediarySubjectNPI = "1285969170";
//		String resourceId = "consent2share@outlook.com";
////		String purposeOfUse = "PWATRNY";
//		String purposeOfUse = "TREAT";
//		String xdsDocumentEntryUniqueId = "123";
//		
//		// pdp
//		PolicyDecisionPoint pdpSpy = spy(policyDecisionPoint);
//		Evaluatable policy=null;
//		try {
//			InputStream is = new FileInputStream(samplePolicyPath);
//			policy=PolicyMarshaller.unmarshal(is);
//		} catch (Exception e) {
//			LOGGER.debug(e.toString(),e);
//		}
//		List<Evaluatable> policies = new ArrayList<Evaluatable>();
//		policies.add(policy);
//		when(pdpSpy.getPolicies(resourceId)).thenReturn(policies);
//		
//		// pdp request
//		RequestGenerator requestGeneratorMock = mock(RequestGenerator.class);
//		InputStream requestis=null;
//		RequestType request=null;
//		try {
//			requestis=new FileInputStream(samplePolicyRequestPath);
//			request=RequestMarshaller.unmarshal(requestis);
//		} catch (Exception e) {
//			LOGGER.debug(e.toString(),e);
//		}
//		when(requestGeneratorMock.generateRequest(recepientSubjectNPI, intermediarySubjectNPI, purposeOfUse, resourceId)).thenReturn(request);
//		
//		testDeny(recepientSubjectNPI, intermediarySubjectNPI, resourceId,
//				purposeOfUse, xdsDocumentEntryUniqueId, pdpSpy,
//				requestGeneratorMock);
//	}
//
//	// Integration test
//	@Test
//	public void testHandleC32Request_DenyByResourceIdMismatch() throws Exception {
//		
//		// Arrange
//	
//		String samplePolicyPath = "src/test/resources/samplePolicyDenyByResourceIdMismatch.xml";
//		String samplePolicyRequestPath = "src/test/resources/samplePolicyRequest.xml";
//		String recepientSubjectNPI = "1568797520";
//		String intermediarySubjectNPI = "1285969170";
//		String resourceId = "wrongresourceid@outlook.com";
////		String purposeOfUse = "PWATRNY";
//		String purposeOfUse = "TREAT";
//		String xdsDocumentEntryUniqueId = "123";
//		
//		// pdp
//		PolicyDecisionPoint pdpSpy = spy(policyDecisionPoint);
//		Evaluatable policy=null;
//		try {
//			InputStream is = new FileInputStream(samplePolicyPath);
//			policy=PolicyMarshaller.unmarshal(is);
//		} catch (Exception e) {
//			LOGGER.debug(e.toString(),e);
//		}
//		List<Evaluatable> policies = new ArrayList<Evaluatable>();
//		policies.add(policy);
//		when(pdpSpy.getPolicies(resourceId)).thenReturn(policies);
//		
//		// pdp request
//		RequestGenerator requestGeneratorMock = mock(RequestGenerator.class);
//		InputStream requestis=null;
//		RequestType request=null;
//		try {
//			requestis=new FileInputStream(samplePolicyRequestPath);
//			request=RequestMarshaller.unmarshal(requestis);
//		} catch (Exception e) {
//			LOGGER.debug(e.toString(),e);
//		}
//		when(requestGeneratorMock.generateRequest(recepientSubjectNPI, intermediarySubjectNPI, purposeOfUse, resourceId)).thenReturn(request);
//		
//		testDeny(recepientSubjectNPI, intermediarySubjectNPI, resourceId,
//				purposeOfUse, xdsDocumentEntryUniqueId, pdpSpy,
//				requestGeneratorMock);
//	}
//
//	// Integration test
//	@Test
//	public void testHandleC32Request_Permit_withActualPolicies() throws Exception {
//		
//		// Arrange
//
////		String samplePolicyPath = "src/test/resources/samplePolicy.xml";
////		String samplePolicyRequestPath = "src/test/resources/samplePolicyRequest.xml";
//		String recepientSubjectNPI = "1083949036";
//		String intermediarySubjectNPI = "1174858088";
//		String resourceId = "consent2share@outlook.com";
////		String purposeOfUse = "PWATRNY";
//		String purposeOfUse = "TREAT";
//		String xdsDocumentEntryUniqueId = "123";
//		
////		// pdp request
////		RequestGenerator requestGeneratorMock = mock(RequestGenerator.class);
////		InputStream requestis=null;
////		RequestType request=null;
////		try {
////			requestis=new FileInputStream(samplePolicyRequestPath);
////			request=RequestMarshaller.unmarshal(requestis);
////		} catch (Exception e) {
////			LOGGER.debug(e.toString(),e);
////		}
////		when(requestGeneratorMock.generateRequest(recepientSubjectNPI, intermediarySubjectNPI, purposeOfUse, resourceId)).thenReturn(request);
//		RequestGenerator requestGeneratorMock = new RequestGenerator();
//		testPermit(recepientSubjectNPI, intermediarySubjectNPI, resourceId,
//				purposeOfUse, xdsDocumentEntryUniqueId, policyDecisionPoint,
//				requestGeneratorMock);
//	}
//
//// Integration test
//	@Test
//	public void testHandleC32Request_DenyByTime_withActualPolicies() throws Exception {
//		
//		// Arrange
//
////		String samplePolicyPath = "src/test/resources/samplePolicy.xml";
////		String samplePolicyRequestPath = "src/test/resources/samplePolicyRequest.xml";
//		String recepientSubjectNPI = "1083949036";
//		String intermediarySubjectNPI = "1174858088";
//		String resourceId = "consent2share@outlook.com";
////		String purposeOfUse = "PWATRNY";
//		String purposeOfUse = "TREAT";
//		String xdsDocumentEntryUniqueId = "123";
//		
////		// pdp request
//		RequestGenerator requestGenerator = new RequestGenerator();
//		RequestGenerator spyRequestGenerator = spy(requestGenerator);
//		when(spyRequestGenerator.getDate()).thenReturn("2030-07-18T00:00:00-04:00");
//		
//		testDeny(recepientSubjectNPI, intermediarySubjectNPI, resourceId,
//				purposeOfUse, xdsDocumentEntryUniqueId, policyDecisionPoint,
//				spyRequestGenerator);
//	}
//
//	// Integration test
//	@Test
//	public void testHandleC32Request_DenyByRecepientSubjectNPIMistmatch_withActualPolicies() throws Exception {
//		
//		// Arrange
//
//		String recepientSubjectNPI = "1111111111";
//		String intermediarySubjectNPI = "1174858088";
//		String resourceId = "consent2share@outlook.com";
//		String purposeOfUse = "TREAT";
//		String xdsDocumentEntryUniqueId = "123";
//		
//		RequestGenerator requestGeneratorMock = new RequestGenerator();
//		testDeny(recepientSubjectNPI, intermediarySubjectNPI, resourceId,
//				purposeOfUse, xdsDocumentEntryUniqueId, policyDecisionPoint,
//				requestGeneratorMock);
//	}
//
//	// Integration test
//	@Test
//	public void testHandleC32Request_DenyByIntermediarySubjectNPIMistmatch_withActualPolicies() throws Exception {
//		
//		// Arrange
//	
//		String recepientSubjectNPI = "1083949036";
//		String intermediarySubjectNPI = "1111111111";
//		String resourceId = "consent2share@outlook.com";
//		String purposeOfUse = "TREAT";
//		String xdsDocumentEntryUniqueId = "123";
//		
//		RequestGenerator requestGeneratorMock = new RequestGenerator();
//		testDeny(recepientSubjectNPI, intermediarySubjectNPI, resourceId,
//				purposeOfUse, xdsDocumentEntryUniqueId, policyDecisionPoint,
//				requestGeneratorMock);
//	}
//
//	// Integration test
//	@Test
//	public void testHandleC32Request_DenyByResourceIdMistmatch_withActualPolicies() throws Exception {
//		
//		// Arrange
//	
//		String recepientSubjectNPI = "1083949036";
//		String intermediarySubjectNPI = "1174858088";
//		String resourceId = "consent2share@wrongEmailAddress.com";
//		String purposeOfUse = "TREAT";
//		String xdsDocumentEntryUniqueId = "123";
//		
//		RequestGenerator requestGeneratorMock = new RequestGenerator();
//		testNotApplicable(recepientSubjectNPI, intermediarySubjectNPI, resourceId,
//				purposeOfUse, xdsDocumentEntryUniqueId, policyDecisionPoint,
//				requestGeneratorMock);
//	}
//
//	// Integration test
//	@Test
//	public void testHandleC32Request_DenyByPurposeOfUseMistmatch_withActualPolicies() throws Exception {
//		
//		// Arrange
//	
//		String recepientSubjectNPI = "1083949036";
//		String intermediarySubjectNPI = "1174858088";
//		String resourceId = "consent2share@outlook.com";
//		String purposeOfUse = "HMARKT";
//		String xdsDocumentEntryUniqueId = "123";
//		
//		RequestGenerator requestGeneratorMock = new RequestGenerator();
//		testDeny(recepientSubjectNPI, intermediarySubjectNPI, resourceId,
//				purposeOfUse, xdsDocumentEntryUniqueId, policyDecisionPoint,
//				requestGeneratorMock);
//	}
//
//	private void testNotApplicable(String recepientSubjectNPI,
//			String intermediarySubjectNPI, String resourceId,
//			String purposeOfUse, String xdsDocumentEntryUniqueId,
//			PolicyDecisionPoint pdpSpy, RequestGenerator requestGeneratorMock) {
//		// context handler
//		ContextHandlerImpl contextHandler = new ContextHandlerImpl(pdpSpy, requestGeneratorMock);
//
//		// c32 getter
////		final String endpointAddress = "http://localhost/Rem.Web/C32Service.svc";
////		C32GetterImpl c32Getter = new C32GetterImpl(endpointAddress);
//		C32GetterImpl c32Getter = mock(C32GetterImpl.class);
//		String c32String = loadXMLAsString("c32.xml");
//		when(c32Getter.getC32(resourceId)).thenReturn(c32String);
//
//		// document segmentation
//		final String documentSegmentationEndpointAddress = "http://localhost:90/DocumentSegmentation/services/DocumentSegmentationService";
//		DocumentSegmentationHandlerImpl documentSegmentation = new DocumentSegmentationHandlerImpl();
//
//		// dataHandlerToBytesConverter
//		DataHandlerToBytesConverter dataHandlerToBytesConverter = new DataHandlerToBytesConverterImpl();
//		
//		// xdsbRepository
//		final String xdsbRepositoryEndpointAddress = "http://feijboss01:8080/axis2/services/xdsrepositoryb";
//		XdsbRepositoryImpl xdsbRepository = new XdsbRepositoryImpl(
//				xdsbRepositoryEndpointAddress);
//
//		// xdsbRegistry
//		final String xdsbRegistryEndpointAddress = "http://feijboss01:8080/axis2/services/xdsregistryb";
//		XdsbRegistryImpl xdsbRegistry = new XdsbRegistryImpl(
//				xdsbRegistryEndpointAddress);
//
//		// orchestrator
//		OrchestratorImpl orchestrator = new OrchestratorImpl(contextHandler,
//				c32Getter, documentSegmentation, dataHandlerToBytesConverter,
//				xdsbRepository, xdsbRegistry);
////		orchestrator.setSubjectPurposeOfUse("TREAT");
//		orchestrator.setSubjectPurposeOfUse(purposeOfUse);
//		orchestrator.setSubjectLocality("2.16.840.1.113883.3.467");
//		orchestrator.setOrganization("SAMHSA");
//		orchestrator.setOrganizationId("FEiSystems");
//		orchestrator.setResourceName("NwHINDirectSend");
//		orchestrator.setResourceType("C32");
//		orchestrator.setResourceAction("Execute");
//		orchestrator.setHomeCommunityId("2.16.840.1.113883.3.467");
//
//		// Act
////		FilterC32Response c32Response = orchestrator.handleC32Request(
////				patientIdPermit, packageXdm, senderEmailAddress,
////				reciepientEmailAddress);
//		FilterC32Response c32Response = orchestrator.handleC32Request(recepientSubjectNPI, intermediarySubjectNPI, resourceId, packageXdm, senderEmailAddress, reciepientEmailAddress, xdsDocumentEntryUniqueId);
//		LOGGER.debug(c32Response.getPdpDecision());
//		LOGGER.debug(c32Response.getMaskedDocument());
//		
//		// Assert
//		assertEquals(NOT_APPLICABLE, c32Response.getPdpDecision());
//		assertNull("c32Response shouldn't have any documents, because it should have been denied.", c32Response.getMaskedDocument());
//	}
//
//	// Integration test
//	@Test
//	public void testHandleC32Request_DenyByPurposeOfUseMismatch() throws Exception {
//		
//		// Arrange
//	
//		String samplePolicyPath = "src/test/resources/samplePolicyDenyByPurposeOfUseMismatch.xml";
//		String samplePolicyRequestPath = "src/test/resources/samplePolicyRequest.xml";
//		String recepientSubjectNPI = "1568797520";
//		String intermediarySubjectNPI = "1285969170";
//		String resourceId = "consent2share@outlook.com";
////		String purposeOfUse = "PWATRNY";
//		String purposeOfUse = "HMARKT";
//		String xdsDocumentEntryUniqueId = "123";
//		
//		// pdp
//		PolicyDecisionPoint pdpSpy = spy(policyDecisionPoint);
//		Evaluatable policy=null;
//		try {
//			InputStream is = new FileInputStream(samplePolicyPath);
//			policy=PolicyMarshaller.unmarshal(is);
//		} catch (Exception e) {
//			LOGGER.debug(e.toString(),e);
//		}
//		List<Evaluatable> policies = new ArrayList<Evaluatable>();
//		policies.add(policy);
//		when(pdpSpy.getPolicies(resourceId)).thenReturn(policies);
//		
//		// pdp request
//		RequestGenerator requestGeneratorMock = mock(RequestGenerator.class);
//		InputStream requestis=null;
//		RequestType request=null;
//		try {
//			requestis=new FileInputStream(samplePolicyRequestPath);
//			request=RequestMarshaller.unmarshal(requestis);
//		} catch (Exception e) {
//			LOGGER.debug(e.toString(),e);
//		}
//		when(requestGeneratorMock.generateRequest(recepientSubjectNPI, intermediarySubjectNPI, purposeOfUse, resourceId)).thenReturn(request);
//		
//		testDeny(recepientSubjectNPI, intermediarySubjectNPI, resourceId,
//				purposeOfUse, xdsDocumentEntryUniqueId, pdpSpy,
//				requestGeneratorMock);
//	}
//
//	private static String loadXMLAsString(String xmlFileName) {
//		InputStream in = null;
//		StringBuilder c32Document = new StringBuilder();
//
//		try {
//			in = Thread.currentThread().getContextClassLoader()
//					.getResourceAsStream(xmlFileName);
//
//			BufferedReader br = new BufferedReader(new InputStreamReader(in));
//
//			String line;
//			while ((line = br.readLine()) != null) {
//				c32Document.append(line);
//			}
//
//			br.close();
//			in.close();
//
//		} catch (Exception e) {
//			LOGGER.debug(e.toString(),e);
//		}
//
//		return c32Document.toString();
//	}
}
