package com.feisystems.polrep.service;

import static com.feisystems.polrep.service.xacml.XACMLXPath.XPATH_POLICY_SET_ID;
import static com.feisystems.polrep.service.xacml.XACMLXPath.XPATH_POLICY_SET_POLICY_COMBINING_ALG_ID;
import static com.feisystems.polrep.util.AssertionUtils.assertNotExistsInPolicyRepository;
import static com.feisystems.polrep.util.AssertionUtils.assertPoliciesNotEmpty;
import static com.feisystems.polrep.util.AssertionUtils.assertPolicyId;
import static com.feisystems.polrep.util.AssertionUtils.assertPolicyIdConsistency;
import static com.feisystems.polrep.util.AssertionUtils.assertPolicyNotNull;
import static com.feisystems.polrep.util.AssertionUtils.assertUniqueIds;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;

import com.feisystems.polrep.domain.Policy;
import com.feisystems.polrep.infrastructure.PolicyRepository;
import com.feisystems.polrep.service.dto.PolicyContainerDto;
import com.feisystems.polrep.service.dto.PolicyContentContainerDto;
import com.feisystems.polrep.service.dto.PolicyContentDto;
import com.feisystems.polrep.service.dto.PolicyDto;
import com.feisystems.polrep.service.dto.PolicyMetadataContainerDto;
import com.feisystems.polrep.service.dto.PolicyMetadataDto;
import com.feisystems.polrep.service.exception.PolicyNotFoundException;
import com.feisystems.polrep.util.DOMUtils;
import com.feisystems.polrep.util.MappingUtils;
import com.feisystems.polrep.util.PolicyValidationUtils;

@Service
public class PolicyServiceImpl implements PolicyService {

	private static final String DEFAULT_WILDCARD = "*";

	private static final String LIKE = "%";

	private static final String POLICY_SET_XML_TEMPLATE_FILE_NAME = "PolicySetTemplate.xml";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PolicyRepository policyRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PolicyCombiningAlgIdValidator policyCombiningAlgIdValidator;

	@Override
	@Transactional
	public PolicyMetadataContainerDto addPolicies(
			PolicyContentContainerDto policyContentContainerDto, boolean force) {

		// Map to PolicyDto
		final List<PolicyDto> requestPolicyDtos = policyContentContainerDto
				.getPolicies().stream().map(MappingUtils::toPolicyDto)
				.collect(toList());

		// Assert unique ids
		assertUniqueIds(requestPolicyDtos);

		// Assert existence in policy repository if not forced
		if (force == false) {
			assertNotExistsInPolicyRepository(requestPolicyDtos,
					policyRepository::findOne);
		}

		// Validate and map to domain objects
		final List<Policy> policies = requestPolicyDtos.stream()
				.map(PolicyValidationUtils::validateAndReturn)
				.map(policyDto -> modelMapper.map(policyDto, Policy.class))
				.collect(toList());

		// Prepare response dtos from domain objects
		final List<PolicyMetadataDto> responsePolicyDtos = policies
				.stream()
				.map(policy -> modelMapper.map(policy, PolicyMetadataDto.class))
				.collect(toList());

		// Save to repository
		policyRepository.save(policies);

		// Return response
		return new PolicyMetadataContainerDto(responsePolicyDtos);
	}

	@Override
	@Transactional
	public void deletePolicy(String policyId) {

		// Assert policy id
		assertPolicyId(policyId);

		// Delete policy
		try {
			policyRepository.delete(policyId);
		} catch (final EmptyResultDataAccessException e) {
			final StringBuilder errorBuilder = new StringBuilder();
			errorBuilder.append(
					"Policy/PolicySet cannot be found with given ID: ").append(
					policyId);
			throw new PolicyNotFoundException(errorBuilder.toString());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PolicyMetadataContainerDto getAllPolicyMetadata() {
		final List<Policy> allPolicies = policyRepository.findAll();
		if (allPolicies == null || allPolicies.size() == 0) {
			throw new PolicyNotFoundException(
					"There are no policies in this policy repository.");
		}
		final List<PolicyMetadataDto> policyMetadataDtos = allPolicies
				.stream()
				.map(policy -> modelMapper.map(policy, PolicyMetadataDto.class))
				.collect(toList());
		return new PolicyMetadataContainerDto(policyMetadataDtos);
	}

	@Override
	@Transactional(readOnly = true)
	public PolicyContainerDto getPolicies(String policyId, String wildcard) {

		// Assert policy id
		assertPolicyId(policyId);

		// if no wildcard is used (looking for exact match)
		if (wildcard == null) {
			// Try to find exact match for the policy
			final Policy policy = policyRepository.findOne(policyId);
			// Assert that the policy is found
			assertPolicyNotNull(policy, policyId);
			// Map policy to response object
			final PolicyDto policyDto = modelMapper
					.map(policy, PolicyDto.class);
			// Return the response
			return new PolicyContainerDto(policyDto);
		}
		// wildcard is used (looking for partial match)
		else {
			// Set default wildcard value if no value is specified
			wildcard = "".equals(wildcard) ? DEFAULT_WILDCARD : wildcard;
			// Setup partial match id format
			final String policyIdLike = policyId.replace(wildcard, LIKE);
			// Try to find partial match for the policies
			final List<Policy> policies = policyRepository
					.findAllByIdLike(policyIdLike);
			// Assert that at least one policy is found
			assertPoliciesNotEmpty(policies, policyId, wildcard);
			// Map policies to response object
			final List<PolicyDto> policyDtos = policies.stream()
					.map(p -> modelMapper.map(p, PolicyDto.class))
					.collect(toList());
			// Return the response
			return new PolicyContainerDto(policyDtos);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PolicyDto getPoliciesCombinedAsPolicySet(String policyId,
			String wildcard, String policySetId, String policyCombiningAlgId) {

		// Validate policyCombiningAlgId
		policyCombiningAlgId = policyCombiningAlgIdValidator
				.validateAndReturn(policyCombiningAlgId);

		// Get all policies
		final PolicyContainerDto policies = getPolicies(policyId, wildcard);

		// Construct a policy set template
		final Document policySet = initPolicySetTemplate();

		// Set policySetId and policyCombiningAlgId
		if (!StringUtils.hasText(policySetId)) {
			policySetId = UUID.randomUUID().toString();
		}
		DOMUtils.getNode(policySet, XPATH_POLICY_SET_ID).get()
				.setNodeValue(policySetId);
		DOMUtils.getNode(policySet, XPATH_POLICY_SET_POLICY_COMBINING_ALG_ID)
				.get().setNodeValue(policyCombiningAlgId);

		// Append all policies to the policy set template
		policies.getPolicies()
				.stream()
				.map(PolicyDto::getPolicy)
				.map(DOMUtils::bytesToDocument)
				.map(Document::getDocumentElement)
				.map(policy -> policy.cloneNode(true))
				.map(clonedPolicy -> policySet.importNode(clonedPolicy, true))
				.forEach(
						importedPolicy -> policySet.getDocumentElement()
								.appendChild(importedPolicy));

		// Construct and return the response
		final PolicyDto response = new PolicyDto();
		response.setId(policySetId);
		response.setPolicy(DOMUtils.documentToBytes(policySet));
		response.setValid(PolicyValidationUtils.validate(response.getPolicy()));
		debug(() -> new String(response.getPolicy()));
		return response;
	}

	@Override
	public Map<String, String> getPolicyCombiningAlgIds() {
		return policyCombiningAlgIdValidator.getCombiningAlgs();
	}

	@Override
	@Transactional
	public PolicyMetadataDto updatePolicy(
			PolicyContentDto updatePolicyRequestDto, String policyId) {

		// Assert policy id
		assertPolicyId(policyId);

		// Map to policyDto
		final PolicyDto policyDto = MappingUtils
				.toPolicyDto(updatePolicyRequestDto.getPolicy());

		// Assert that the policy ID in the request parameter matches with the
		// ID in the policy content
		assertPolicyIdConsistency(policyId, policyDto.getId());

		// Try to find the policy
		final Policy policy = policyRepository.findOne(policyId);

		// Assert that the policy is found
		assertPolicyNotNull(policy, policyId);

		// Update the policy with new values
		policy.setPolicy(updatePolicyRequestDto.getPolicy());
		policy.setValid(PolicyValidationUtils.validate(updatePolicyRequestDto
				.getPolicy()));
		policyRepository.save(policy);

		// Map policy to response object
		final PolicyMetadataDto updatePolicyResponseDto = modelMapper.map(
				policy, PolicyMetadataDto.class);

		// Return response
		return updatePolicyResponseDto;
	}

	private void debug(Supplier<String> logMsgSupplier) {
		if (logger.isDebugEnabled()) {
			logger.debug(logMsgSupplier.get());
		}
	}

	private Document initPolicySetTemplate() {
		byte[] policySetTemplateBytes = null;
		try {
			policySetTemplateBytes = IOUtils.toByteArray(getClass()
					.getClassLoader().getResourceAsStream(
							POLICY_SET_XML_TEMPLATE_FILE_NAME));
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		return DOMUtils.bytesToDocument(policySetTemplateBytes);
	}
}
