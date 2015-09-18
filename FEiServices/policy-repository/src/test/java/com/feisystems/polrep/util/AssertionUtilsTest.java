package com.feisystems.polrep.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.feisystems.polrep.domain.Policy;
import com.feisystems.polrep.service.dto.PolicyDto;
import com.feisystems.polrep.service.exception.ConflictingRequestException;
import com.feisystems.polrep.service.exception.PolicyAlreadyExistsException;
import com.feisystems.polrep.service.exception.PolicyIdNotFoundException;
import com.feisystems.polrep.service.exception.PolicyNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class AssertionUtilsTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAssertNotExistsInPolicyRepositoryListOfPolicyDtoFunctionOfStringPolicy_First_Exists_Throws_PolicyAlreadyExistsException() {
		// Arrange
		thrown.expect(PolicyAlreadyExistsException.class);
		final String policyId1 = "policyId1";
		final String policyId2 = "policyId2";
		final PolicyDto p1 = new PolicyDto();
		p1.setId(policyId1);
		final PolicyDto p2 = new PolicyDto();
		p2.setId(policyId2);
		final Function<String, Policy> policyFindFunction = s -> s
				.equals(policyId2) ? null : new Policy();
		final List<PolicyDto> policyDtos = Arrays.asList(p1, p2);

		// Act
		AssertionUtils.assertNotExistsInPolicyRepository(policyDtos,
				policyFindFunction);
	}

	@Test
	public void testAssertNotExistsInPolicyRepositoryListOfPolicyDtoFunctionOfStringPolicy_None_Exists() {
		// Arrange
		final String policyId1 = "policyId1";
		final String policyId2 = "policyId2";
		final PolicyDto p1 = new PolicyDto();
		p1.setId(policyId1);
		final PolicyDto p2 = new PolicyDto();
		p2.setId(policyId2);
		final Function<String, Policy> policyFindFunction = s -> s
				.equals(policyId1) || s.equals(policyId2) ? null : new Policy();
		final List<PolicyDto> policyDtos = Arrays.asList(p1, p2);

		// Act
		AssertionUtils.assertNotExistsInPolicyRepository(policyDtos,
				policyFindFunction);
	}

	@Test
	public void testAssertNotExistsInPolicyRepositoryListOfPolicyDtoFunctionOfStringPolicy_Second_Exists_Throws_PolicyAlreadyExistsException() {
		// Arrange
		thrown.expect(PolicyAlreadyExistsException.class);
		final String policyId1 = "policyId1";
		final String policyId2 = "policyId2";
		final PolicyDto p1 = new PolicyDto();
		p1.setId(policyId1);
		final PolicyDto p2 = new PolicyDto();
		p2.setId(policyId2);
		final Function<String, Policy> policyFindFunction = s -> s
				.equals(policyId1) ? null : new Policy();
		final List<PolicyDto> policyDtos = Arrays.asList(p1, p2);

		// Act
		AssertionUtils.assertNotExistsInPolicyRepository(policyDtos,
				policyFindFunction);
	}

	@Test
	public void testAssertNotExistsInPolicyRepositoryStringFunctionOfStringPolicy_Exist_Throws() {
		// Arrange
		thrown.expect(PolicyAlreadyExistsException.class);
		final String policyId1 = "policyId1";
		final String policyId2 = "policyId2";
		final PolicyDto p1 = new PolicyDto();
		p1.setId(policyId1);
		final PolicyDto p2 = new PolicyDto();
		p2.setId(policyId2);
		final Function<String, Policy> policyFindFunction = s -> s
				.equals(policyId1) ? null : new Policy();

		// Act
		AssertionUtils.assertNotExistsInPolicyRepository(policyId2,
				policyFindFunction);
	}

	@Test
	public void testAssertNotExistsInPolicyRepositoryStringFunctionOfStringPolicy_Not_Exists() {
		// Arrange
		final String policyId1 = "policyId1";
		final String policyId2 = "policyId2";
		final PolicyDto p1 = new PolicyDto();
		p1.setId(policyId1);
		final PolicyDto p2 = new PolicyDto();
		p2.setId(policyId2);
		final Function<String, Policy> policyFindFunction = s -> s
				.equals(policyId1) ? null : new Policy();

		// Act
		AssertionUtils.assertNotExistsInPolicyRepository(policyId1,
				policyFindFunction);
	}

	@Test
	public void testAssertPoliciesNotEmpty_Empty_Throws_PolicyNotFoundException() {
		// Arrange
		thrown.expect(PolicyNotFoundException.class);
		final String policyId = "policyId";
		final String wildcard = "wildcard";
		final List<Policy> policies = Collections.emptyList();

		// Act
		AssertionUtils.assertPoliciesNotEmpty(policies, policyId, wildcard);
	}

	@Test
	public void testAssertPoliciesNotEmpty_Not_Empty() {
		// Arrange
		final Policy policy = new Policy();
		final String policyId = "policyId";
		final String wildcard = "wildcard";
		final List<Policy> policies = Arrays.asList(policy);

		// Act
		AssertionUtils.assertPoliciesNotEmpty(policies, policyId, wildcard);
	}

	@Test
	public void testAssertPolicyId() {
		// Arrange
		final String policyId = "policyId";

		// Act
		AssertionUtils.assertPolicyId(policyId);
	}

	@Test
	public void testAssertPolicyId_Empty_String_Throws_PolicyIdNotFoundException() {
		// Arrange
		thrown.expect(PolicyIdNotFoundException.class);
		final String policyId = "";

		// Act
		AssertionUtils.assertPolicyId(policyId);
	}

	@Test
	public void testAssertPolicyId_Null_Throws_PolicyIdNotFoundException() {
		// Arrange
		thrown.expect(PolicyIdNotFoundException.class);
		final String policyId = null;

		// Act
		AssertionUtils.assertPolicyId(policyId);
	}

	@Test
	public void testAssertPolicyIdConsistency() {
		// Arrange
		final String requestPolicyId = "requestPolicyId";
		final String policyContentPolicyId = requestPolicyId;

		// Act
		AssertionUtils.assertPolicyIdConsistency(requestPolicyId,
				policyContentPolicyId);
	}

	@Test
	public void testAssertPolicyIdConsistency_Not_Consistent_Throws_ConflictingRequestException() {
		// Arrange
		thrown.expect(ConflictingRequestException.class);
		final String requestPolicyId = "requestPolicyId";
		final String policyContentPolicyId = requestPolicyId + ".";

		// Act
		AssertionUtils.assertPolicyIdConsistency(requestPolicyId,
				policyContentPolicyId);
	}

	@Test
	public void testAssertPolicyNotNull() {
		// Arrange
		final Policy policy = new Policy();
		final String policyId = "policyId";

		// Act
		AssertionUtils.assertPolicyNotNull(policy, policyId);
	}

	@Test
	public void testAssertPolicyNotNull_Throws_PolicyNotFoundException() {
		// Arrange
		thrown.expect(PolicyNotFoundException.class);
		final Policy policy = null;
		final String policyId = "policyId";

		// Act
		AssertionUtils.assertPolicyNotNull(policy, policyId);
	}

	@Test
	public void testAssertUniqueIds() {
		// Arrange
		final PolicyDto p1 = new PolicyDto();
		final PolicyDto p2 = new PolicyDto();
		final PolicyDto p3 = new PolicyDto();
		final String policyId1 = "policyId1";
		final String policyId2 = "policyId2";
		final String policyId3 = "policyId3";
		p1.setId(policyId1);
		p2.setId(policyId2);
		p3.setId(policyId3);
		final List<PolicyDto> policyDtos = Arrays.asList(p1, p2, p3);

		// Act
		AssertionUtils.assertUniqueIds(policyDtos);
	}

	@Test
	public void testAssertUniqueIds_All_Matches_Throws_ConflictingRequestException() {
		// Arrange
		thrown.expect(ConflictingRequestException.class);
		final PolicyDto p1 = new PolicyDto();
		final PolicyDto p2 = new PolicyDto();
		final PolicyDto p3 = new PolicyDto();
		final String policyId1 = "policyId1";
		final String policyId2 = policyId1;
		final String policyId3 = policyId2;
		p1.setId(policyId1);
		p2.setId(policyId2);
		p3.setId(policyId3);
		final List<PolicyDto> policyDtos = Arrays.asList(p1, p2, p3);

		// Act
		AssertionUtils.assertUniqueIds(policyDtos);
	}

	@Test
	public void testAssertUniqueIds_One_Match_Throws_ConflictingRequestException() {
		// Arrange
		thrown.expect(ConflictingRequestException.class);
		final PolicyDto p1 = new PolicyDto();
		final PolicyDto p2 = new PolicyDto();
		final PolicyDto p3 = new PolicyDto();
		final String policyId1 = "policyId1";
		final String policyId2 = "policyId2";
		final String policyId3 = policyId2;
		p1.setId(policyId1);
		p2.setId(policyId2);
		p3.setId(policyId3);
		final List<PolicyDto> policyDtos = Arrays.asList(p1, p2, p3);

		// Act
		AssertionUtils.assertUniqueIds(policyDtos);
	}

}
