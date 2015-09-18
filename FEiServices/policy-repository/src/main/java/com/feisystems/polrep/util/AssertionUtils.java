package com.feisystems.polrep.util;

import java.util.List;
import java.util.function.Function;

import org.springframework.util.StringUtils;

import com.feisystems.polrep.domain.Policy;
import com.feisystems.polrep.service.dto.PolicyDto;
import com.feisystems.polrep.service.exception.ConflictingRequestException;
import com.feisystems.polrep.service.exception.PolicyAlreadyExistsException;
import com.feisystems.polrep.service.exception.PolicyIdNotFoundException;
import com.feisystems.polrep.service.exception.PolicyNotFoundException;

public class AssertionUtils {
	public static void assertNotExistsInPolicyRepository(
			List<PolicyDto> policyDtos,
			Function<String, Policy> policyFindFunction) {
		policyDtos
				.stream()
				.map(PolicyDto::getId)
				.forEach(
						policyId -> assertNotExistsInPolicyRepository(policyId,
								policyFindFunction));
	}

	public static void assertNotExistsInPolicyRepository(String policyId,
			Function<String, Policy> policyFindFunction) {
		final Policy policy = policyFindFunction.apply(policyId);
		if (policy != null) {
			final StringBuilder errorBuilder = new StringBuilder();
			errorBuilder
					.append("There is already a policy/policySet in the repository with the given id: ")
					.append(policyId)
					.append(". Consider using force=true request parameter to force-update the content of existing policy.");
			throw new PolicyAlreadyExistsException(errorBuilder.toString());
		}
	}

	public static void assertPoliciesNotEmpty(final List<Policy> policies,
			final String policyId, final String wildcard) {
		if (policies == null || policies.size() == 0) {
			final StringBuilder errorBuilder = new StringBuilder();
			errorBuilder.append(
					"Policy/PolicySet cannot be found with given ID: ").append(
					policyId);
			if (StringUtils.hasText(wildcard)) {
				errorBuilder.append("; wildcard: ").append(wildcard);
			}
			throw new PolicyNotFoundException(errorBuilder.toString());
		}
	}

	public static void assertPolicyId(String policyId) {
		if (!StringUtils.hasText(policyId)) {
			throw new PolicyIdNotFoundException("Policy ID must have a text!");
		}
	}

	public static void assertPolicyIdConsistency(String requestPolicyId,
			final String policyContentPolicyId) {
		if (!requestPolicyId.equals(policyContentPolicyId)) {
			final StringBuilder errorBuilder = new StringBuilder()
					.append("Policy/PolicySet IDs in the request (")
					.append(requestPolicyId)
					.append(") and the policy content (")
					.append(policyContentPolicyId).append(") do not match! ");
			throw new ConflictingRequestException(errorBuilder.toString());
		}
	}

	public static void assertPolicyNotNull(final Policy policy,
			final String policyId) {
		if (policy == null) {
			final StringBuilder errorBuilder = new StringBuilder();
			errorBuilder.append(
					"Policy/PolicySet cannot be found with given ID: ").append(
					policyId);
			throw new PolicyNotFoundException(errorBuilder.toString());
		}
	}

	public static void assertUniqueIds(List<PolicyDto> policyDtos) {
		final long countDistinctIds = policyDtos.stream().map(PolicyDto::getId)
				.distinct().count();
		if (countDistinctIds != policyDtos.size()) {
			throw new ConflictingRequestException(
					"Policy IDs are not unique in the request.");
		}
	}
}
