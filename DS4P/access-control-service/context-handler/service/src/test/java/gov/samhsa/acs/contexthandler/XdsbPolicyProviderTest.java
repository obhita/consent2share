package gov.samhsa.acs.contexthandler;

import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.xdsb.common.XdsbDocumentType;
import gov.samhsa.acs.xdsb.registry.wsclient.adapter.XdsbRegistryAdapter;
import gov.samhsa.acs.xdsb.repository.wsclient.adapter.XdsbRepositoryAdapter;

import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest.DocumentRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse.DocumentResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;

import org.herasaf.xacml.core.policy.Evaluatable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class XdsbPolicyProviderTest {

	public static final String URN_POLICY_COMBINING_ALGORITHM_PERMIT_OVERRIDES = "urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:permit-overrides";

	private String urnPolicyCombiningAlgorithm;

	@Mock
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Mock
	private XdsbRegistryAdapter xdsbRegistry;

	@Mock
	private XdsbRepositoryAdapter xdsbRepository;

	@Mock
	private XacmlPolicyListFilter xacmlPolicyListFilter;

	@InjectMocks
	XdsbPolicyProvider policyDecisionPointImplDataXdsb;

	@Before
	public void setUp() {
		policyDecisionPointImplDataXdsb = new XdsbPolicyProvider(
				xdsbRegistry, xdsbRepository, xacmlPolicyListFilter,
				urnPolicyCombiningAlgorithm);
	}

	@Test
	public void testConstructorWithXdsbRegistryAdapterAndXdsbRepositoryAdapter() {
		policyDecisionPointImplDataXdsb = new XdsbPolicyProvider(
				xdsbRegistry, xdsbRepository, xacmlPolicyListFilter);
		assertEquals(
				policyDecisionPointImplDataXdsb.urnPolicyCombiningAlgorithm,
				"urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:permit-overrides");
	}

	@Test
	public void testConstructorWithXdsbRegistryAdapterAndXdsbRepositoryAdapterAndEmptyUrnPolicyCombiningAlgorithm() {
		policyDecisionPointImplDataXdsb = new XdsbPolicyProvider(
				xdsbRegistry, xdsbRepository, xacmlPolicyListFilter, "");
		assertEquals(
				policyDecisionPointImplDataXdsb.urnPolicyCombiningAlgorithm,
				"urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:permit-overrides");
	}

	@Test
	public void testConstructorWithXdsbRegistryAdapterAndXdsbRepositoryAdapterAndNullUrnPolicyCombiningAlgorithm() {
		policyDecisionPointImplDataXdsb = new XdsbPolicyProvider(
				xdsbRegistry, xdsbRepository, null);
		assertEquals(
				policyDecisionPointImplDataXdsb.urnPolicyCombiningAlgorithm,
				"urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:permit-overrides");
	}

	@Test
	public void testConstructorWithXdsbRegistryAdapterAndXdsbRepositoryAdapterAndMeaningfulUrnPolicyCombiningAlgorithm() {
		policyDecisionPointImplDataXdsb = new XdsbPolicyProvider(
				xdsbRegistry, xdsbRepository, xacmlPolicyListFilter,
				URN_POLICY_COMBINING_ALGORITHM_PERMIT_OVERRIDES);
		assertEquals(
				policyDecisionPointImplDataXdsb.urnPolicyCombiningAlgorithm,
				"urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:permit-overrides");
	}

	@Test
	// unable to mock static class
	public void testGetPolicies() throws Exception, Throwable {
		XdsbPolicyProvider sut = spy(policyDecisionPointImplDataXdsb);
		byte[] xacmlPolicy = "<Policy xmlns=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\"  PolicyId=\"a07478e8-3642-42ff-980e-911e26ec3f47\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:first-applicable\">\r\n   <Description>This is a reference policy forconsent2share@outlook.com</Description>\r\n   <Target></Target>\r\n   <Rule Effect=\"Permit\" RuleId=\"primary-group-rule\">\r\n      <Target>\r\n         <Resources>\r\n            <Resource>\r\n               <ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-regexp-match\">\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">consent2share@outlook.com</AttributeValue>\r\n                  <ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></ResourceAttributeDesignator>\r\n               </ResourceMatch>\r\n            </Resource>\r\n         </Resources>\r\n         <Actions>\r\n            <Action>\r\n               <ActionMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">write</AttributeValue>\r\n                  <ActionAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></ActionAttributeDesignator>\r\n               </ActionMatch>\r\n            </Action>\r\n         </Actions>\r\n      </Target>\r\n      <Condition>\r\n         <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:and\">\r\n            <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\">\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n                  <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\">\r\n                     <SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></SubjectAttributeDesignator>\r\n                  </Apply>\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1568797520</AttributeValue>\r\n               </Apply>\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n                  <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\">\r\n                     <SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></SubjectAttributeDesignator>\r\n                  </Apply>\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1083949036</AttributeValue>\r\n               </Apply>\r\n            </Apply>\r\n            <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\">\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n                  <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\">\r\n                     <SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></SubjectAttributeDesignator>\r\n                  </Apply>\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1285969170</AttributeValue>\r\n               </Apply>\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n                  <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\">\r\n                     <SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></SubjectAttributeDesignator>\r\n                  </Apply>\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1346575297</AttributeValue>\r\n               </Apply>\r\n            </Apply>\r\n            <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\">\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n                  <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\">\r\n                     <SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"gov.samhsa.consent2share.purpose-of-use-code\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></SubjectAttributeDesignator>\r\n                  </Apply>\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">TREAT</AttributeValue>\r\n               </Apply>\r\n            </Apply>\r\n            <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\">\r\n                  <ActionAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></ActionAttributeDesignator>\r\n               </Apply>\r\n               <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">write</AttributeValue>\r\n            </Apply>\r\n            <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-greater-than-or-equal\">\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\">\r\n                  <EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"></EnvironmentAttributeDesignator>\r\n               </Apply>\r\n               <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2013-06-12T00:00:00-04:00</AttributeValue>\r\n            </Apply>\r\n            <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-less-than-or-equal\">\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\">\r\n                  <EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"></EnvironmentAttributeDesignator>\r\n               </Apply>\r\n               <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2013-07-18T00:00:00-04:00</AttributeValue>\r\n            </Apply>\r\n         </Apply>\r\n      </Condition>\r\n   </Rule>\r\n   \r\n   <Rule Effect=\"Deny\" RuleId=\"Deny-the-else\"/>\r\n   \r\n   <Obligations>\r\n   \t\t<Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\">\r\n   \t\t\t<AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">47420-5</AttributeAssignment>\r\n   \t\t</Obligation>\r\n   \t\t\r\n   \t\t<Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-sensitivity-code\" FulfillOn=\"Permit\">\r\n         <AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">ETH</AttributeAssignment>\r\n      </Obligation>\r\n   </Obligations>\r\n   \r\n   \r\n   \r\n   \r\n</Policy>    "
				.getBytes();
		RetrieveDocumentSetResponse retrieveDocumentSetResponse = mock(RetrieveDocumentSetResponse.class);
		RetrieveDocumentSetRequest retrieveDocumentSetRequest = mock(RetrieveDocumentSetRequest.class);
		AdhocQueryResponse response = mock(AdhocQueryResponse.class);
		DocumentResponse docResponse1 = mock(DocumentResponse.class);
		DocumentResponse docResponse2 = mock(DocumentResponse.class);
		List<RetrieveDocumentSetResponse.DocumentResponse> policyDocuments = new ArrayList<RetrieveDocumentSetResponse.DocumentResponse>();
		policyDocuments.add(docResponse1);
		policyDocuments.add(docResponse2);
		Evaluatable evaluatable = mock(Evaluatable.class);
		doReturn(evaluatable).when(sut).unmarshal(any(InputStream.class));
		when(
				xdsbRegistry.registryStoredQuery("1", null,
						XdsbDocumentType.PRIVACY_CONSENT, true)).thenReturn(
				response);
		when(
				xdsbRegistry
						.extractXdsbDocumentReferenceListAsRetrieveDocumentSetRequest(response))
				.thenReturn(retrieveDocumentSetRequest);
		when(xdsbRepository.retrieveDocumentSet(retrieveDocumentSetRequest))
				.thenReturn(retrieveDocumentSetResponse);
		when(retrieveDocumentSetResponse.getDocumentResponse()).thenReturn(
				policyDocuments);
		when(docResponse2.getDocument()).thenReturn(xacmlPolicy);
		when(docResponse1.getDocument()).thenReturn(xacmlPolicy);
		@SuppressWarnings("unchecked")
		List<DocumentRequest> docReqListMock = mock(List.class);
		when(retrieveDocumentSetRequest.getDocumentRequest()).thenReturn(
				docReqListMock);
		when(docReqListMock.size()).thenReturn(1);

		sut.getPolicies("1", "1568797520", "1285969170");

		verify(xdsbRegistry, times(1))
				.extractXdsbDocumentReferenceListAsRetrieveDocumentSetRequest(
						response);
		verify(xdsbRepository, times(1)).retrieveDocumentSet(
				retrieveDocumentSetRequest);
	}

	@Test(expected = DS4PException.class)
	public void testGetPoliciesWhenDocumentCannotBeFound() throws Exception,
			Throwable {
		XdsbPolicyProvider sut = spy(policyDecisionPointImplDataXdsb);
		byte[] xacmlPolicy = "<Policy xmlns=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\"  PolicyId=\"a07478e8-3642-42ff-980e-911e26ec3f47\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:first-applicable\">\r\n   <Description>This is a reference policy forconsent2share@outlook.com</Description>\r\n   <Target></Target>\r\n   <Rule Effect=\"Permit\" RuleId=\"primary-group-rule\">\r\n      <Target>\r\n         <Resources>\r\n            <Resource>\r\n               <ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-regexp-match\">\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">consent2share@outlook.com</AttributeValue>\r\n                  <ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:resource-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></ResourceAttributeDesignator>\r\n               </ResourceMatch>\r\n            </Resource>\r\n         </Resources>\r\n         <Actions>\r\n            <Action>\r\n               <ActionMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">write</AttributeValue>\r\n                  <ActionAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></ActionAttributeDesignator>\r\n               </ActionMatch>\r\n            </Action>\r\n         </Actions>\r\n      </Target>\r\n      <Condition>\r\n         <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:and\">\r\n            <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\">\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n                  <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\">\r\n                     <SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></SubjectAttributeDesignator>\r\n                  </Apply>\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1568797520</AttributeValue>\r\n               </Apply>\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n                  <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\">\r\n                     <SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></SubjectAttributeDesignator>\r\n                  </Apply>\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1083949036</AttributeValue>\r\n               </Apply>\r\n            </Apply>\r\n            <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\">\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n                  <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\">\r\n                     <SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></SubjectAttributeDesignator>\r\n                  </Apply>\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1285969170</AttributeValue>\r\n               </Apply>\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n                  <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\">\r\n                     <SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></SubjectAttributeDesignator>\r\n                  </Apply>\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1346575297</AttributeValue>\r\n               </Apply>\r\n            </Apply>\r\n            <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\">\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n                  <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\">\r\n                     <SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"gov.samhsa.consent2share.purpose-of-use-code\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></SubjectAttributeDesignator>\r\n                  </Apply>\r\n                  <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">TREAT</AttributeValue>\r\n               </Apply>\r\n            </Apply>\r\n            <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\">\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\">\r\n                  <ActionAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"></ActionAttributeDesignator>\r\n               </Apply>\r\n               <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">write</AttributeValue>\r\n            </Apply>\r\n            <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-greater-than-or-equal\">\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\">\r\n                  <EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"></EnvironmentAttributeDesignator>\r\n               </Apply>\r\n               <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2013-06-12T00:00:00-04:00</AttributeValue>\r\n            </Apply>\r\n            <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-less-than-or-equal\">\r\n               <Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\">\r\n                  <EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"></EnvironmentAttributeDesignator>\r\n               </Apply>\r\n               <AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2013-07-18T00:00:00-04:00</AttributeValue>\r\n            </Apply>\r\n         </Apply>\r\n      </Condition>\r\n   </Rule>\r\n   \r\n   <Rule Effect=\"Deny\" RuleId=\"Deny-the-else\"/>\r\n   \r\n   <Obligations>\r\n   \t\t<Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\">\r\n   \t\t\t<AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">47420-5</AttributeAssignment>\r\n   \t\t</Obligation>\r\n   \t\t\r\n   \t\t<Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-sensitivity-code\" FulfillOn=\"Permit\">\r\n         <AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">ETH</AttributeAssignment>\r\n      </Obligation>\r\n   </Obligations>\r\n   \r\n   \r\n   \r\n   \r\n</Policy>    "
				.getBytes();
		RetrieveDocumentSetResponse retrieveDocumentSetResponse = mock(RetrieveDocumentSetResponse.class);
		RetrieveDocumentSetRequest retrieveDocumentSetRequest = mock(RetrieveDocumentSetRequest.class);
		AdhocQueryResponse response = mock(AdhocQueryResponse.class);
		DocumentResponse docResponse1 = mock(DocumentResponse.class);
		DocumentResponse docResponse2 = mock(DocumentResponse.class);
		List<RetrieveDocumentSetResponse.DocumentResponse> policyDocuments = new ArrayList<RetrieveDocumentSetResponse.DocumentResponse>();
		policyDocuments.add(docResponse1);
		policyDocuments.add(docResponse2);
		Evaluatable evaluatable = mock(Evaluatable.class);
		doReturn(evaluatable).when(sut).unmarshal(any(InputStream.class));
		when(
				xdsbRegistry.registryStoredQuery("1", null,
						XdsbDocumentType.PRIVACY_CONSENT, true)).thenReturn(
				response);
		doThrow(new IOException()).when(xdsbRegistry)
				.extractXdsbDocumentReferenceListAsRetrieveDocumentSetRequest(
						response);
		when(xdsbRepository.retrieveDocumentSet(retrieveDocumentSetRequest))
				.thenReturn(retrieveDocumentSetResponse);
		when(retrieveDocumentSetResponse.getDocumentResponse()).thenReturn(
				policyDocuments);
		when(docResponse2.getDocument()).thenReturn(xacmlPolicy);
		when(docResponse1.getDocument()).thenReturn(xacmlPolicy);

		sut.getPolicies("1", "1568797520", "1285969170");
	}

}
