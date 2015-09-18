package com.feisystems.polrep.service;

import static com.feisystems.polrep.util.DOMUtils.bytesToDocument;
import static com.feisystems.polrep.util.DOMUtils.getNode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.herasaf.xacml.core.simplePDP.SimplePDPFactory;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.StringUtils;

import com.feisystems.polrep.domain.Policy;
import com.feisystems.polrep.infrastructure.PolicyRepository;
import com.feisystems.polrep.service.dto.PolicyContainerDto;
import com.feisystems.polrep.service.dto.PolicyContentContainerDto;
import com.feisystems.polrep.service.dto.PolicyContentDto;
import com.feisystems.polrep.service.dto.PolicyDto;
import com.feisystems.polrep.service.dto.PolicyMetadataContainerDto;
import com.feisystems.polrep.service.dto.PolicyMetadataDto;
import com.feisystems.polrep.service.exception.ConflictingRequestException;
import com.feisystems.polrep.service.exception.InvalidPolicyCombiningAlgIdException;
import com.feisystems.polrep.service.exception.PolicyAlreadyExistsException;
import com.feisystems.polrep.service.exception.PolicyCombiningAlgIdNotFoundException;
import com.feisystems.polrep.service.exception.PolicyIdNotFoundException;
import com.feisystems.polrep.service.exception.PolicyNotFoundException;
import com.feisystems.polrep.service.xacml.XACMLXPath;

@RunWith(MockitoJUnitRunner.class)
public class PolicyServiceImplTest {

	private static final String POLICY_STRING1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Policy xmlns=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\" PolicyId=\"C2S.001:&amp;2.16.840.1.113883.3.704.100.200.1.1.3.1&amp;ISO:1174858088:1740515725:JKCE7C\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:permit-overrides\"><Description>This is a reference policy for	consent2share@outlook.com</Description><Target/><Rule Effect=\"Permit\" RuleId=\"primary-group-rule\"><Target><Resources><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">34133-9</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:typeCode\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">urn:oasis:names:tc:ebxml-regrep:StatusType:Approved</AttributeValue><ResourceAttributeDesignator AttributeId=\"xacml:status\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource></Resources><Actions><Action><ActionMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">xdsquery</AttributeValue><ActionAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ActionMatch></Action><Action><ActionMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">xdsretrieve</AttributeValue><ActionAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ActionMatch></Action></Actions></Target><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:and\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1740515725</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1174858088</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">TREATMENT</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-greater-than-or-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\"><EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2015-03-03T00:00:00-0500</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-less-than-or-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\"><EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2016-03-02T23:59:59-0500</AttributeValue></Apply></Apply></Condition></Rule><Rule Effect=\"Deny\" RuleId=\"deny-others\"/><Obligations><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">ADD</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">ALC</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">PSY</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">COM</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">GDIS</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">SEX</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">ETH</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">HIV</AttributeAssignment></Obligation></Obligations></Policy>";
	private static final String POLICY_STRING2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Policy xmlns=\"urn:oasis:names:tc:xacml:2.0:policy:schema:os\" PolicyId=\"C2S.002:&amp;2.16.840.1.113883.3.704.100.200.1.1.3.1&amp;ISO:1174858088:1740515725:JKCE7C\" RuleCombiningAlgId=\"urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:permit-overrides\"><Description>This is a reference policy for	consent2share@outlook.com</Description><Target/><Rule Effect=\"Permit\" RuleId=\"primary-group-rule\"><Target><Resources><Resource><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">34133-9</AttributeValue><ResourceAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:resource:typeCode\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch><ResourceMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">urn:oasis:names:tc:ebxml-regrep:StatusType:Approved</AttributeValue><ResourceAttributeDesignator AttributeId=\"xacml:status\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ResourceMatch></Resource></Resources><Actions><Action><ActionMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">xdsquery</AttributeValue><ActionAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ActionMatch></Action><Action><ActionMatch MatchId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">xdsretrieve</AttributeValue><ActionAttributeDesignator AttributeId=\"urn:oasis:names:tc:xacml:1.0:action:action-id\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></ActionMatch></Action></Actions></Target><Condition><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:and\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1740515725</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">1174858088</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:or\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:string-one-and-only\"><SubjectAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xspa:1.0:subject:purposeofuse\" DataType=\"http://www.w3.org/2001/XMLSchema#string\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">TREATMENT</AttributeValue></Apply></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-greater-than-or-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\"><EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2015-03-03T00:00:00-0500</AttributeValue></Apply><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-less-than-or-equal\"><Apply FunctionId=\"urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only\"><EnvironmentAttributeDesignator MustBePresent=\"false\" AttributeId=\"urn:oasis:names:tc:xacml:1.0:environment:current-dateTime\" DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\"/></Apply><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#dateTime\">2016-03-02T23:59:59-0500</AttributeValue></Apply></Apply></Condition></Rule><Rule Effect=\"Deny\" RuleId=\"deny-others\"/><Obligations><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">ADD</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">ALC</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">PSY</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">COM</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">GDIS</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">SEX</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">ETH</AttributeAssignment></Obligation><Obligation ObligationId=\"urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code\" FulfillOn=\"Permit\"><AttributeAssignment AttributeId=\"urn:oasis:names:tc:xacml:3.0:example:attribute:text\" DataType=\"http://www.w3.org/2001/XMLSchema#string\">HIV</AttributeAssignment></Obligation></Obligations></Policy>";
	private static final String POLICY_ID1 = "C2S.001:&2.16.840.1.113883.3.704.100.200.1.1.3.1&ISO:1174858088:1740515725:JKCE7C";
	private static final String POLICY_ID2 = "C2S.002:&2.16.840.1.113883.3.704.100.200.1.1.3.1&ISO:1174858088:1740515725:JKCE7C";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private PolicyRepository policyRepository;

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private PolicyCombiningAlgIdValidator policyCombiningAlgIdValidator;

	@InjectMocks
	private PolicyServiceImpl sut;

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddPolicies_Forced() throws UnsupportedEncodingException {
		// Assert
		final boolean force = true;
		final PolicyContentDto addPolicyRequestDto1 = new PolicyContentDto();
		addPolicyRequestDto1.setPolicy(POLICY_STRING1.getBytes("UTF-8"));
		final PolicyContentDto addPolicyRequestDto2 = new PolicyContentDto();
		addPolicyRequestDto2.setPolicy(POLICY_STRING2.getBytes("UTF-8"));
		final PolicyContentContainerDto addPolicyRequestContainerDto = new PolicyContentContainerDto(
				addPolicyRequestDto1, addPolicyRequestDto2);
		final Policy policy1 = mock(Policy.class);
		when(policy1.getId()).thenReturn(POLICY_ID1);
		final Policy policy2 = mock(Policy.class);
		when(policy2.getId()).thenReturn(POLICY_ID2);
		when(modelMapper.map(addPolicyRequestDto1, Policy.class)).thenReturn(
				policy1);
		when(modelMapper.map(addPolicyRequestDto2, Policy.class)).thenReturn(
				policy2);
		final PolicyMetadataDto addPolicyResponseDto1 = mock(PolicyMetadataDto.class);
		final PolicyMetadataDto addPolicyResponseDto2 = mock(PolicyMetadataDto.class);
		when(modelMapper.map(policy1, PolicyMetadataDto.class)).thenReturn(
				addPolicyResponseDto1);
		when(modelMapper.map(policy2, PolicyMetadataDto.class)).thenReturn(
				addPolicyResponseDto2);

		// Act
		sut.addPolicies(addPolicyRequestContainerDto, force);

		// Assert
		verify(policyRepository, times(1)).save(anyListOf(Policy.class));
	}

	@Test
	public void testAddPolicies_Forced_One_Already_Exists()
			throws UnsupportedEncodingException {
		// Assert
		final boolean force = true;
		final PolicyContentDto addPolicyRequestDto1 = new PolicyContentDto();
		addPolicyRequestDto1.setPolicy(POLICY_STRING1.getBytes("UTF-8"));
		final PolicyContentDto addPolicyRequestDto2 = new PolicyContentDto();
		addPolicyRequestDto2.setPolicy(POLICY_STRING2.getBytes("UTF-8"));
		final PolicyContentContainerDto addPolicyRequestContainerDto = new PolicyContentContainerDto(
				addPolicyRequestDto1, addPolicyRequestDto2);
		final Policy policy1 = mock(Policy.class);
		when(policy1.getId()).thenReturn(POLICY_ID1);
		final Policy policy2 = mock(Policy.class);
		when(policy2.getId()).thenReturn(POLICY_ID2);
		when(modelMapper.map(addPolicyRequestDto1, Policy.class)).thenReturn(
				policy1);
		when(modelMapper.map(addPolicyRequestDto2, Policy.class)).thenReturn(
				policy2);
		final PolicyMetadataDto addPolicyResponseDto1 = mock(PolicyMetadataDto.class);
		final PolicyMetadataDto addPolicyResponseDto2 = mock(PolicyMetadataDto.class);
		when(modelMapper.map(policy1, PolicyMetadataDto.class)).thenReturn(
				addPolicyResponseDto1);
		when(modelMapper.map(policy2, PolicyMetadataDto.class)).thenReturn(
				addPolicyResponseDto2);
		when(policyRepository.findOne(POLICY_ID1)).thenReturn(new Policy());

		// Act
		sut.addPolicies(addPolicyRequestContainerDto, force);

		// Assert
		verify(policyRepository, times(1)).save(anyListOf(Policy.class));
	}

	@Test
	public void testAddPolicies_Not_Forced()
			throws UnsupportedEncodingException {
		// Assert
		final boolean force = false;
		final PolicyContentDto addPolicyRequestDto1 = new PolicyContentDto();
		addPolicyRequestDto1.setPolicy(POLICY_STRING1.getBytes("UTF-8"));
		final PolicyContentDto addPolicyRequestDto2 = new PolicyContentDto();
		addPolicyRequestDto2.setPolicy(POLICY_STRING2.getBytes("UTF-8"));
		final PolicyContentContainerDto addPolicyRequestContainerDto = new PolicyContentContainerDto(
				addPolicyRequestDto1, addPolicyRequestDto2);
		final Policy policy1 = mock(Policy.class);
		when(policy1.getId()).thenReturn(POLICY_ID1);
		final Policy policy2 = mock(Policy.class);
		when(policy2.getId()).thenReturn(POLICY_ID2);
		when(modelMapper.map(addPolicyRequestDto1, Policy.class)).thenReturn(
				policy1);
		when(modelMapper.map(addPolicyRequestDto2, Policy.class)).thenReturn(
				policy2);
		final PolicyMetadataDto addPolicyResponseDto1 = mock(PolicyMetadataDto.class);
		final PolicyMetadataDto addPolicyResponseDto2 = mock(PolicyMetadataDto.class);
		when(modelMapper.map(policy1, PolicyMetadataDto.class)).thenReturn(
				addPolicyResponseDto1);
		when(modelMapper.map(policy2, PolicyMetadataDto.class)).thenReturn(
				addPolicyResponseDto2);

		// Act
		sut.addPolicies(addPolicyRequestContainerDto, force);

		// Assert
		verify(policyRepository, times(1)).save(anyListOf(Policy.class));
	}

	@Test
	public void testAddPolicies_Not_Forced_Throws_PolicyAlreadyExistsException()
			throws UnsupportedEncodingException {
		// Assert
		thrown.expect(PolicyAlreadyExistsException.class);
		final boolean force = false;
		final PolicyContentDto addPolicyRequestDto1 = new PolicyContentDto();
		addPolicyRequestDto1.setPolicy(POLICY_STRING1.getBytes("UTF-8"));
		final PolicyContentDto addPolicyRequestDto2 = new PolicyContentDto();
		addPolicyRequestDto2.setPolicy(POLICY_STRING2.getBytes("UTF-8"));
		final PolicyContentContainerDto addPolicyRequestContainerDto = new PolicyContentContainerDto(
				addPolicyRequestDto1, addPolicyRequestDto2);
		final Policy policy1 = mock(Policy.class);
		when(policy1.getId()).thenReturn(POLICY_ID1);
		final Policy policy2 = mock(Policy.class);
		when(policy2.getId()).thenReturn(POLICY_ID2);
		when(policyRepository.findOne(POLICY_ID1)).thenReturn(new Policy());
		when(modelMapper.map(addPolicyRequestDto1, Policy.class)).thenReturn(
				policy1);
		when(modelMapper.map(addPolicyRequestDto2, Policy.class)).thenReturn(
				policy2);
		final PolicyMetadataDto addPolicyResponseDto1 = mock(PolicyMetadataDto.class);
		final PolicyMetadataDto addPolicyResponseDto2 = mock(PolicyMetadataDto.class);
		when(modelMapper.map(policy1, PolicyMetadataDto.class)).thenReturn(
				addPolicyResponseDto1);
		when(modelMapper.map(policy2, PolicyMetadataDto.class)).thenReturn(
				addPolicyResponseDto2);

		// Act
		sut.addPolicies(addPolicyRequestContainerDto, force);

		// Assert
		verify(policyRepository, times(1)).save(anyListOf(Policy.class));
	}

	@Test
	public void testAddPolicies_Throws_ConflictingRequestException()
			throws UnsupportedEncodingException {
		// Assert
		thrown.expect(ConflictingRequestException.class);
		final PolicyContentDto addPolicyRequestDto = new PolicyContentDto();
		addPolicyRequestDto.setPolicy(POLICY_STRING1.getBytes("UTF-8"));
		final PolicyContentContainerDto addPolicyRequestContainerDto = new PolicyContentContainerDto(
				addPolicyRequestDto, addPolicyRequestDto);

		// Act
		sut.addPolicies(addPolicyRequestContainerDto, false);
	}

	@Test
	public void testDeletePolicy() {
		// Arrange
		final String policyId = "policyId";

		// Act
		sut.deletePolicy(policyId);

		// Assert
		verify(policyRepository, times(1)).delete(policyId);
	}

	@Test
	public void testDeletePolicy_Throws_PolicyIdNotFoundException_By_Empty_String() {
		// Arrange
		final String policyId = "";
		thrown.expect(PolicyIdNotFoundException.class);

		// Act
		sut.deletePolicy(policyId);
	}

	@Test
	public void testDeletePolicy_Throws_PolicyIdNotFoundException_By_Null() {
		// Arrange
		final String policyId = null;
		thrown.expect(PolicyIdNotFoundException.class);

		// Act
		sut.deletePolicy(policyId);
	}

	@Test
	public void testDeletePolicy_Throws_PolicyNotFoundException() {
		// Arrange
		final String policyId = "policyId";
		doThrow(EmptyResultDataAccessException.class).when(policyRepository)
				.delete(policyId);
		thrown.expect(PolicyNotFoundException.class);

		// Act
		sut.deletePolicy(policyId);
	}

	@Test
	public void testGetAllPolicyMetadata() throws UnsupportedEncodingException {
		// Arrange
		final Policy p1 = new Policy();
		p1.setId(POLICY_ID1);
		p1.setPolicy(POLICY_STRING1.getBytes("UTF-8"));
		p1.setValid(true);
		final Policy p2 = new Policy();
		p2.setId(POLICY_ID2);
		p2.setPolicy(POLICY_STRING2.getBytes("UTF-8"));
		p2.setValid(false);
		final List<Policy> policyList = Arrays.asList(p1, p2);
		final PolicyMetadataDto m1 = new PolicyMetadataDto();
		final PolicyMetadataDto m2 = new PolicyMetadataDto();
		m1.setId(p1.getId());
		m1.setValid(p1.isValid());
		m2.setId(p2.getId());
		m2.setValid(p2.isValid());
		when(policyRepository.findAll()).thenReturn(policyList);
		when(modelMapper.map(p1, PolicyMetadataDto.class)).thenReturn(m1);
		when(modelMapper.map(p2, PolicyMetadataDto.class)).thenReturn(m2);

		// Act
		final PolicyMetadataContainerDto response = sut.getAllPolicyMetadata();

		// Assert
		assertEquals(policyList.size(), response.getPolicies().size());
		assertEquals(p1.getId(), response.getPolicies().get(0).getId());
		assertEquals(p1.isValid(), response.getPolicies().get(0).isValid());
		assertEquals(p2.getId(), response.getPolicies().get(1).getId());
		assertEquals(p2.isValid(), response.getPolicies().get(1).isValid());
	}

	@Test
	public void testGetAllPolicyMetadata_Throws_PolicyNotFoundException()
			throws UnsupportedEncodingException {
		// Arrange
		thrown.expect(PolicyNotFoundException.class);
		final List<Policy> policyList = new ArrayList<Policy>();
		when(policyRepository.findAll()).thenReturn(policyList);

		// Act
		sut.getAllPolicyMetadata();
	}

	@Test
	public void testGetPolicies_No_Wildcard() {
		// Arrange
		final String policyId = "policyId";
		final String wildcard = null;
		final Policy policy = mock(Policy.class);
		final PolicyDto getPolicyResponseDto = mock(PolicyDto.class);
		when(policyRepository.findOne(policyId)).thenReturn(policy);
		when(modelMapper.map(policy, PolicyDto.class)).thenReturn(
				getPolicyResponseDto);

		// Act
		final PolicyContainerDto response = sut.getPolicies(policyId, wildcard);

		// Assert
		assertEquals(getPolicyResponseDto, response.getPolicies().get(0));
	}

	@Test
	public void testGetPolicies_No_Wildcard_Throws_PolicyNotFoundException() {
		// Arrange
		final String policyId = "policyId";
		final String wildcard = null;
		thrown.expect(PolicyNotFoundException.class);
		when(policyRepository.findOne(policyId)).thenReturn(null);

		// Act
		sut.getPolicies(policyId, wildcard);
	}

	@Test
	public void testGetPolicies_Throws_PolicyIdNotFoundException() {
		// Arrange
		final String policyId = "";
		final String wildcard = null;
		thrown.expect(PolicyIdNotFoundException.class);

		// Assert
		sut.getPolicies(policyId, wildcard);
	}

	@Test
	public void testGetPolicies_Wildcard_Used() {
		// Arrange
		final String policyId = "policy*";
		final String wildcard = "*";
		final String policyIdLike = "policy%";
		final Policy policy1 = mock(Policy.class);
		final Policy policy2 = mock(Policy.class);
		final List<Policy> policyList = Arrays.asList(policy1, policy2);
		final PolicyDto getPolicyResponseDto1 = mock(PolicyDto.class);
		final PolicyDto getPolicyResponseDto2 = mock(PolicyDto.class);
		when(policyRepository.findAllByIdLike(policyIdLike)).thenReturn(
				policyList);
		when(modelMapper.map(policy1, PolicyDto.class)).thenReturn(
				getPolicyResponseDto1);
		when(modelMapper.map(policy2, PolicyDto.class)).thenReturn(
				getPolicyResponseDto2);

		// Act
		final PolicyContainerDto response = sut.getPolicies(policyId, wildcard);

		// Assert
		assertTrue(response.getPolicies().contains(getPolicyResponseDto1));
		assertTrue(response.getPolicies().contains(getPolicyResponseDto2));
	}

	@Test
	public void testGetPolicies_Wildcard_Used_Throws_PolicyNotFoundException() {
		// Arrange
		final String policyId = "policy*";
		final String wildcard = "*";
		final String policyIdLike = "policy%";
		thrown.expect(PolicyNotFoundException.class);
		final List<Policy> policyList = Collections.emptyList();
		when(policyRepository.findAllByIdLike(policyIdLike)).thenReturn(
				policyList);

		// Act
		sut.getPolicies(policyId, wildcard);
	}

	@Test
	public void testGetPoliciesCombinedAsPolicySet()
			throws UnsupportedEncodingException {
		// Arrange
		final String policyId = "C2S*";
		final String wildcard = "*";
		final String policyCombiningAlgId = "permit-overrides";
		final String policyCombiningAlgIdReturned = "urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:permit-overrides";
		final PolicyServiceImpl spy = spy(sut);
		final PolicyContainerDto getPolicyResponseContainerDto = new PolicyContainerDto();
		final PolicyDto p1 = new PolicyDto();
		p1.setId(POLICY_ID1);
		p1.setPolicy(POLICY_STRING1.getBytes("UTF-8"));
		p1.setValid(true);
		final PolicyDto p2 = new PolicyDto();
		p2.setId(POLICY_ID2);
		p2.setPolicy(POLICY_STRING2.getBytes("UTF-8"));
		p1.setValid(true);
		getPolicyResponseContainerDto.setPolicies(Arrays.asList(p1, p2));
		doReturn(getPolicyResponseContainerDto).when(spy).getPolicies(policyId,
				wildcard);
		when(
				policyCombiningAlgIdValidator
						.validateAndReturn(policyCombiningAlgId)).thenReturn(
				policyCombiningAlgIdReturned);

		// Act
		final PolicyDto response = spy.getPoliciesCombinedAsPolicySet(policyId,
				wildcard, null, policyCombiningAlgId);

		// Assert
		assertNotNull(response.getPolicy());
		assertTrue(StringUtils.hasText(getNode(
				bytesToDocument(response.getPolicy()),
				XACMLXPath.XPATH_POLICY_SET_ID).get().getNodeValue()));
		assertEquals(
				policyCombiningAlgIdReturned,
				getNode(bytesToDocument(response.getPolicy()),
						XACMLXPath.XPATH_POLICY_SET_POLICY_COMBINING_ALG_ID)
						.get().getNodeValue());
	}

	@Test
	public void testGetPoliciesCombinedAsPolicySet_Custom_PolicySetId()
			throws UnsupportedEncodingException {
		// Arrange
		final String policyId = "C2S*";
		final String wildcard = "*";
		final String policyCombiningAlgId = "permit-overrides";
		final String policyCombiningAlgIdReturned = "urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:permit-overrides";
		final String policySetId = "policySetId";
		final PolicyServiceImpl spy = spy(sut);
		final PolicyContainerDto getPolicyResponseContainerDto = new PolicyContainerDto();
		final PolicyDto p1 = new PolicyDto();
		p1.setId(POLICY_ID1);
		p1.setPolicy(POLICY_STRING1.getBytes("UTF-8"));
		p1.setValid(true);
		final PolicyDto p2 = new PolicyDto();
		p2.setId(POLICY_ID2);
		p2.setPolicy(POLICY_STRING2.getBytes("UTF-8"));
		p1.setValid(true);
		getPolicyResponseContainerDto.setPolicies(Arrays.asList(p1, p2));
		doReturn(getPolicyResponseContainerDto).when(spy).getPolicies(policyId,
				wildcard);
		when(
				policyCombiningAlgIdValidator
						.validateAndReturn(policyCombiningAlgId)).thenReturn(
				policyCombiningAlgIdReturned);

		// Act
		final PolicyDto response = spy.getPoliciesCombinedAsPolicySet(policyId,
				wildcard, policySetId, policyCombiningAlgId);

		// Assert
		assertNotNull(response.getPolicy());
		assertEquals(
				policySetId,
				getNode(bytesToDocument(response.getPolicy()),
						XACMLXPath.XPATH_POLICY_SET_ID).get().getNodeValue());
		assertEquals(
				policyCombiningAlgIdReturned,
				getNode(bytesToDocument(response.getPolicy()),
						XACMLXPath.XPATH_POLICY_SET_POLICY_COMBINING_ALG_ID)
						.get().getNodeValue());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetPoliciesCombinedAsPolicySet_Throws_InvalidPolicyCombiningAlgIdException()
			throws UnsupportedEncodingException {
		// Arrange
		thrown.expect(InvalidPolicyCombiningAlgIdException.class);
		final String policyId = "C2S*";
		final String wildcard = "*";
		final String policyCombiningAlgId = "permit-overrides";
		@SuppressWarnings("unused")
		final String policyCombiningAlgIdReturned = "urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:permit-overrides";
		final PolicyServiceImpl spy = spy(sut);
		final PolicyContainerDto getPolicyResponseContainerDto = new PolicyContainerDto();
		final PolicyDto p1 = new PolicyDto();
		p1.setId(POLICY_ID1);
		p1.setPolicy(POLICY_STRING1.getBytes("UTF-8"));
		p1.setValid(true);
		final PolicyDto p2 = new PolicyDto();
		p2.setId(POLICY_ID2);
		p2.setPolicy(POLICY_STRING2.getBytes("UTF-8"));
		p1.setValid(true);
		getPolicyResponseContainerDto.setPolicies(Arrays.asList(p1, p2));
		doReturn(getPolicyResponseContainerDto).when(spy).getPolicies(policyId,
				wildcard);
		when(
				policyCombiningAlgIdValidator
						.validateAndReturn(policyCombiningAlgId)).thenThrow(
				InvalidPolicyCombiningAlgIdException.class);

		// Act
		spy.getPoliciesCombinedAsPolicySet(policyId, wildcard, null,
				policyCombiningAlgId);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetPoliciesCombinedAsPolicySet_Throws_PolicyCombiningAlgIdNotFoundException()
			throws UnsupportedEncodingException {
		// Arrange
		thrown.expect(PolicyCombiningAlgIdNotFoundException.class);
		final String policyId = "C2S*";
		final String wildcard = "*";
		final String policyCombiningAlgId = "permit-overrides";
		@SuppressWarnings("unused")
		final String policyCombiningAlgIdReturned = "urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:permit-overrides";
		final PolicyServiceImpl spy = spy(sut);
		final PolicyContainerDto getPolicyResponseContainerDto = new PolicyContainerDto();
		final PolicyDto p1 = new PolicyDto();
		p1.setId(POLICY_ID1);
		p1.setPolicy(POLICY_STRING1.getBytes("UTF-8"));
		p1.setValid(true);
		final PolicyDto p2 = new PolicyDto();
		p2.setId(POLICY_ID2);
		p2.setPolicy(POLICY_STRING2.getBytes("UTF-8"));
		p1.setValid(true);
		getPolicyResponseContainerDto.setPolicies(Arrays.asList(p1, p2));
		doReturn(getPolicyResponseContainerDto).when(spy).getPolicies(policyId,
				wildcard);
		when(
				policyCombiningAlgIdValidator
						.validateAndReturn(policyCombiningAlgId)).thenThrow(
				PolicyCombiningAlgIdNotFoundException.class);

		// Act
		spy.getPoliciesCombinedAsPolicySet(policyId, wildcard, null,
				policyCombiningAlgId);
	}

	@Test
	public void testUpdatePolicy() throws UnsupportedEncodingException {
		// Arrange
		final byte[] policyBytes = POLICY_STRING1.getBytes("UTF-8");
		final PolicyContentDto updatePolicyRequestDto = mock(PolicyContentDto.class);
		final Policy policy = mock(Policy.class);
		when(updatePolicyRequestDto.getPolicy()).thenReturn(policyBytes);
		when(policyRepository.findOne(POLICY_ID1)).thenReturn(policy);
		final PolicyMetadataDto updatePolicyResponseDto = mock(PolicyMetadataDto.class);
		when(modelMapper.map(policy, PolicyMetadataDto.class)).thenReturn(
				updatePolicyResponseDto);

		// Act
		final PolicyMetadataDto response = sut.updatePolicy(
				updatePolicyRequestDto, POLICY_ID1);

		// Assert
		verify(policy, times(1)).setPolicy(policyBytes);
		verify(policy, times(1)).setValid(true);
		verify(policyRepository, times(1)).save(policy);
		assertEquals(updatePolicyResponseDto, response);
	}

	@Test
	public void testUpdatePolicy_Throws_PolicyIdNotFoundException()
			throws UnsupportedEncodingException {
		// Arrange
		thrown.expect(PolicyIdNotFoundException.class);
		final String policyId = "";
		final PolicyContentDto updatePolicyRequestDto = mock(PolicyContentDto.class);

		// Act
		sut.updatePolicy(updatePolicyRequestDto, policyId);
	}

	@BeforeClass
	public static void setUp() throws Exception {
		SimplePDPFactory.getSimplePDP();
	}

}
