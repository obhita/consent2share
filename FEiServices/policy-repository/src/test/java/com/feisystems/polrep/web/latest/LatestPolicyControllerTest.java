package com.feisystems.polrep.web.latest;

import static com.feisystems.polrep.util.DOMUtils.DEFAULT_ENCODING;
import static com.feisystems.polrep.web.v1.ArgumentMatchers.matching;
import static com.feisystems.polrep.web.v1.TestUtils.APPLICATION_JSON_UTF8;
import static com.feisystems.polrep.web.v1.TestUtils.convertObjectToJsonBytes;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;

import com.feisystems.polrep.service.PolicyService;
import com.feisystems.polrep.service.dto.PolicyContainerDto;
import com.feisystems.polrep.service.dto.PolicyContentContainerDto;
import com.feisystems.polrep.service.dto.PolicyContentDto;
import com.feisystems.polrep.service.dto.PolicyDto;
import com.feisystems.polrep.service.dto.PolicyMetadataContainerDto;
import com.feisystems.polrep.service.dto.PolicyMetadataDto;
import com.feisystems.polrep.service.exception.ConflictingRequestException;
import com.feisystems.polrep.service.exception.DOMUtilsException;
import com.feisystems.polrep.service.exception.InvalidPolicyCombiningAlgIdException;
import com.feisystems.polrep.service.exception.PolicyAlreadyExistsException;
import com.feisystems.polrep.service.exception.PolicyCombiningAlgIdNotFoundException;
import com.feisystems.polrep.service.exception.PolicyIdNotFoundException;
import com.feisystems.polrep.service.exception.PolicyNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class LatestPolicyControllerTest {

	private static final String API = "/rest/latest";

	private MockMvc mockMvc;

	@Mock
	private PolicyService policyService;

	@InjectMocks
	private LatestPolicyController sut;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(this.sut).build();
	}

	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Test
	public void testAddPolicies_Fail_By_Throwing_ConflictingRequestException()
			throws IOException, Exception {
		// Arrange
		final String policy1 = "policy1";
		final String policy2 = "policy2";
		final PolicyContentContainerDto addPolicyRequestContainerDto = new PolicyContentContainerDto(
				new PolicyContentDto(policy1.getBytes(DEFAULT_ENCODING)),
				new PolicyContentDto(policy2.getBytes(DEFAULT_ENCODING)));
		final boolean force = true;
		final String uri = API + "/policies";
		final String id1 = "id1";
		final String id2 = "id2";
		final boolean valid1 = true;
		final boolean valid2 = false;
		final PolicyMetadataDto response1 = new PolicyMetadataDto();
		response1.setId(id1);
		response1.setValid(valid1);
		final PolicyMetadataDto response2 = new PolicyMetadataDto();
		response2.setId(id2);
		response2.setValid(valid2);
		final PolicyMetadataContainerDto addPolicyResponseContainerDto = new PolicyMetadataContainerDto(
				response1, response2);
		when(
				policyService.addPolicies(any(PolicyContentContainerDto.class),
						eq(force)))
				.thenThrow(ConflictingRequestException.class);

		// Act and Assert
		mockMvc.perform(
				post(uri)
						.param("force", Boolean.toString(force))
						.contentType(APPLICATION_JSON_UTF8)
						.content(
								convertObjectToJsonBytes(addPolicyRequestContainerDto)))
				.andExpect(status().isConflict());

		// Assert
		verify(policyService, times(1)).addPolicies(
				argThat(matching((PolicyContentContainerDto req) -> (req
						.getPolicies().size() == 2))), eq(force));
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Test
	public void testAddPolicies_Fail_By_Throwing_PolicyAlreadyExistsException()
			throws IOException, Exception {
		// Arrange
		final String policy1 = "policy1";
		final String policy2 = "policy2";
		final PolicyContentContainerDto addPolicyRequestContainerDto = new PolicyContentContainerDto(
				new PolicyContentDto(policy1.getBytes(DEFAULT_ENCODING)),
				new PolicyContentDto(policy2.getBytes(DEFAULT_ENCODING)));
		final boolean force = true;
		final String uri = API + "/policies";
		final String id1 = "id1";
		final String id2 = "id2";
		final boolean valid1 = true;
		final boolean valid2 = false;
		final PolicyMetadataDto response1 = new PolicyMetadataDto();
		response1.setId(id1);
		response1.setValid(valid1);
		final PolicyMetadataDto response2 = new PolicyMetadataDto();
		response2.setId(id2);
		response2.setValid(valid2);
		final PolicyMetadataContainerDto addPolicyResponseContainerDto = new PolicyMetadataContainerDto(
				response1, response2);
		when(
				policyService.addPolicies(any(PolicyContentContainerDto.class),
						eq(force))).thenThrow(
				PolicyAlreadyExistsException.class);

		// Act and Assert
		mockMvc.perform(
				post(uri)
						.param("force", Boolean.toString(force))
						.contentType(APPLICATION_JSON_UTF8)
						.content(
								convertObjectToJsonBytes(addPolicyRequestContainerDto)))
				.andExpect(status().isConflict());

		// Assert
		verify(policyService, times(1)).addPolicies(
				argThat(matching((PolicyContentContainerDto req) -> (req
						.getPolicies().size() == 2))), eq(force));
	}

	@Test
	public void testAddPolicies_Fail_By_Wrong_Request_Body()
			throws IOException, Exception {
		// Arrange
		final String INVALID_JSON = "NOT_A_JSON_COMPATIABLE";
		final boolean force = true;
		final String uri = API + "/policies";
		final String id1 = "id1";
		final String id2 = "id2";
		final boolean valid1 = true;
		final boolean valid2 = false;
		final PolicyMetadataDto response1 = new PolicyMetadataDto();
		response1.setId(id1);
		response1.setValid(valid1);
		final PolicyMetadataDto response2 = new PolicyMetadataDto();
		response2.setId(id2);
		response2.setValid(valid2);
		final PolicyMetadataContainerDto addPolicyResponseContainerDto = new PolicyMetadataContainerDto(
				response1, response2);
		when(
				policyService.addPolicies(any(PolicyContentContainerDto.class),
						eq(force))).thenReturn(addPolicyResponseContainerDto);

		// Act and Assert
		mockMvc.perform(
				post(uri).param("force", Boolean.toString(force))
						.contentType(APPLICATION_JSON_UTF8)
						.content(convertObjectToJsonBytes(INVALID_JSON)))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void testAddPolicies_Success_Force_Not_Given() throws IOException,
			Exception {
		// Arrange
		final String policy1 = "policy1";
		final String policy2 = "policy2";
		final PolicyContentContainerDto addPolicyRequestContainerDto = new PolicyContentContainerDto(
				new PolicyContentDto(policy1.getBytes(DEFAULT_ENCODING)),
				new PolicyContentDto(policy2.getBytes(DEFAULT_ENCODING)));
		final boolean force = false;
		final String uri = API + "/policies";
		final String id1 = "id1";
		final String id2 = "id2";
		final boolean valid1 = true;
		final boolean valid2 = false;
		final PolicyMetadataDto response1 = new PolicyMetadataDto();
		response1.setId(id1);
		response1.setValid(valid1);
		final PolicyMetadataDto response2 = new PolicyMetadataDto();
		response2.setId(id2);
		response2.setValid(valid2);
		final PolicyMetadataContainerDto addPolicyResponseContainerDto = new PolicyMetadataContainerDto(
				response1, response2);
		when(
				policyService.addPolicies(any(PolicyContentContainerDto.class),
						eq(force))).thenReturn(addPolicyResponseContainerDto);

		// Act and Assert
		mockMvc.perform(
				post(uri).contentType(APPLICATION_JSON_UTF8).content(
						convertObjectToJsonBytes(addPolicyRequestContainerDto)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(
						jsonPath("$.policies",
								hasSize(addPolicyResponseContainerDto
										.getPolicies().size())))
				.andExpect(jsonPath("$.policies[0].id", is(id1)))
				.andExpect(jsonPath("$.policies[0].valid", is(valid1)))
				.andExpect(jsonPath("$.policies[1].id", is(id2)))
				.andExpect(jsonPath("$.policies[1].valid", is(valid2)));

		// Assert
		verify(policyService, times(1)).addPolicies(
				argThat(matching((PolicyContentContainerDto req) -> (req
						.getPolicies().size() == 2))), eq(force));
	}

	@Test
	public void testAddPolicies_Success_Forced() throws IOException, Exception {
		// Arrange
		final String policy1 = "policy1";
		final String policy2 = "policy2";
		final PolicyContentContainerDto addPolicyRequestContainerDto = new PolicyContentContainerDto(
				new PolicyContentDto(policy1.getBytes(DEFAULT_ENCODING)),
				new PolicyContentDto(policy2.getBytes(DEFAULT_ENCODING)));
		final boolean force = true;
		final String uri = API + "/policies";
		final String id1 = "id1";
		final String id2 = "id2";
		final boolean valid1 = true;
		final boolean valid2 = false;
		final PolicyMetadataDto response1 = new PolicyMetadataDto();
		response1.setId(id1);
		response1.setValid(valid1);
		final PolicyMetadataDto response2 = new PolicyMetadataDto();
		response2.setId(id2);
		response2.setValid(valid2);
		final PolicyMetadataContainerDto addPolicyResponseContainerDto = new PolicyMetadataContainerDto(
				response1, response2);
		when(
				policyService.addPolicies(any(PolicyContentContainerDto.class),
						eq(force))).thenReturn(addPolicyResponseContainerDto);

		// Act and Assert
		mockMvc.perform(
				post(uri)
						.param("force", Boolean.toString(force))
						.contentType(APPLICATION_JSON_UTF8)
						.content(
								convertObjectToJsonBytes(addPolicyRequestContainerDto)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(
						jsonPath("$.policies",
								hasSize(addPolicyResponseContainerDto
										.getPolicies().size())))
				.andExpect(jsonPath("$.policies[0].id", is(id1)))
				.andExpect(jsonPath("$.policies[0].valid", is(valid1)))
				.andExpect(jsonPath("$.policies[1].id", is(id2)))
				.andExpect(jsonPath("$.policies[1].valid", is(valid2)));

		// Assert
		verify(policyService, times(1)).addPolicies(
				argThat(matching((PolicyContentContainerDto req) -> (req
						.getPolicies().size() == 2))), eq(force));
	}

	@Test
	public void testAddPolicies_Success_Not_Forced() throws IOException,
			Exception {
		// Arrange
		final String policy1 = "policy1";
		final String policy2 = "policy2";
		final PolicyContentContainerDto addPolicyRequestContainerDto = new PolicyContentContainerDto(
				new PolicyContentDto(policy1.getBytes(DEFAULT_ENCODING)),
				new PolicyContentDto(policy2.getBytes(DEFAULT_ENCODING)));
		final boolean force = false;
		final String uri = API + "/policies";
		final String id1 = "id1";
		final String id2 = "id2";
		final boolean valid1 = true;
		final boolean valid2 = false;
		final PolicyMetadataDto response1 = new PolicyMetadataDto();
		response1.setId(id1);
		response1.setValid(valid1);
		final PolicyMetadataDto response2 = new PolicyMetadataDto();
		response2.setId(id2);
		response2.setValid(valid2);
		final PolicyMetadataContainerDto addPolicyResponseContainerDto = new PolicyMetadataContainerDto(
				response1, response2);
		when(
				policyService.addPolicies(any(PolicyContentContainerDto.class),
						eq(force))).thenReturn(addPolicyResponseContainerDto);

		// Act and Assert
		mockMvc.perform(
				post(uri)
						.param("force", Boolean.toString(force))
						.contentType(APPLICATION_JSON_UTF8)
						.content(
								convertObjectToJsonBytes(addPolicyRequestContainerDto)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(
						jsonPath("$.policies",
								hasSize(addPolicyResponseContainerDto
										.getPolicies().size())))
				.andExpect(jsonPath("$.policies[0].id", is(id1)))
				.andExpect(jsonPath("$.policies[0].valid", is(valid1)))
				.andExpect(jsonPath("$.policies[1].id", is(id2)))
				.andExpect(jsonPath("$.policies[1].valid", is(valid2)));

		// Assert
		verify(policyService, times(1)).addPolicies(
				argThat(matching((PolicyContentContainerDto req) -> (req
						.getPolicies().size() == 2))), eq(force));
	}

	@Test
	public void testDeletePolicy_Fail_By_Throwing_PolicyIdNotFoundException()
			throws Exception {
		// Arrange
		final String policyId = "policyId";
		final String uri = API + "/policies/" + policyId;
		doThrow(PolicyIdNotFoundException.class).when(policyService)
				.deletePolicy(policyId);

		// Act and Assert
		mockMvc.perform(delete(uri)).andExpect(status().isPreconditionFailed());

		// Assert
		verify(policyService, times(1)).deletePolicy(policyId);
	}

	@Test
	public void testDeletePolicy_Fail_By_Throwing_PolicyNotFoundException()
			throws Exception {
		// Arrange
		final String policyId = "policyId";
		final String uri = API + "/policies/" + policyId;
		doThrow(PolicyNotFoundException.class).when(policyService)
				.deletePolicy(policyId);

		// Act and Assert
		mockMvc.perform(delete(uri)).andExpect(status().isNotFound());

		// Assert
		verify(policyService, times(1)).deletePolicy(policyId);
	}

	@Test
	public void testDeletePolicy_Success() throws Exception {
		// Arrange
		final String policyId = "policyId";
		final String uri = API + "/policies/" + policyId;
		doNothing().when(policyService).deletePolicy(policyId);

		// Act and Assert
		mockMvc.perform(delete(uri)).andExpect(status().isNoContent());

		// Assert
		verify(policyService, times(1)).deletePolicy(policyId);
	}

	@Test
	public void testGetAllPolicyMetadata() throws Exception {
		// Arrange
		final String uri = API + "/policies";
		final String id1 = "id1";
		final String id2 = "id2";
		final boolean valid1 = true;
		final boolean valid2 = false;
		final PolicyMetadataDto response1 = new PolicyMetadataDto();
		response1.setId(id1);
		response1.setValid(valid1);
		final PolicyMetadataDto response2 = new PolicyMetadataDto();
		response2.setId(id2);
		response2.setValid(valid2);
		final PolicyMetadataContainerDto policyMetadataContainerDto = new PolicyMetadataContainerDto(
				response1, response2);
		when(policyService.getAllPolicyMetadata()).thenReturn(
				policyMetadataContainerDto);

		// Act and Assert
		mockMvc.perform(get(uri).contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(
						jsonPath("$.policies",
								hasSize(policyMetadataContainerDto
										.getPolicies().size())))
				.andExpect(jsonPath("$.policies[0].id", is(id1)))
				.andExpect(jsonPath("$.policies[0].valid", is(valid1)))
				.andExpect(jsonPath("$.policies[1].id", is(id2)))
				.andExpect(jsonPath("$.policies[1].valid", is(valid2)));

		// Assert
		verify(policyService, times(1)).getAllPolicyMetadata();
	}

	@Test
	public void testGetAllPolicyMetadata_Throws_PolicyNotFoundException()
			throws Exception {
		// Arrange
		final String uri = API + "/policies";
		when(policyService.getAllPolicyMetadata()).thenThrow(
				PolicyNotFoundException.class);

		// Act and Assert
		mockMvc.perform(get(uri).contentType(APPLICATION_JSON_UTF8)).andExpect(
				status().isNotFound());

		// Assert
		verify(policyService, times(1)).getAllPolicyMetadata();
	}

	@Test
	public void testGetPolicies_Success_Has_Wildcard_Multiple_Response()
			throws Exception {
		// Arrange
		final String policyId1 = "policyId1";
		final String policyId2 = "policyId2";
		final boolean valid1 = true;
		final boolean valid2 = false;
		final String policy1 = "policy1";
		final String policy2 = "policy2";
		final String wildcard = "wildcard";
		final PolicyDto getPolicyResponseDto1 = new PolicyDto();
		getPolicyResponseDto1.setId(policyId1);
		getPolicyResponseDto1.setValid(valid1);
		getPolicyResponseDto1.setPolicy(policy1.getBytes(DEFAULT_ENCODING));
		final PolicyDto getPolicyResponseDto2 = new PolicyDto();
		getPolicyResponseDto2.setId(policyId2);
		getPolicyResponseDto2.setValid(valid2);
		getPolicyResponseDto2.setPolicy(policy2.getBytes(DEFAULT_ENCODING));
		final PolicyContainerDto getPolicyResponseContainerDto = new PolicyContainerDto(
				getPolicyResponseDto1, getPolicyResponseDto2);
		final String uri = API + "/policies/" + policyId1;
		when(policyService.getPolicies(policyId1, wildcard)).thenReturn(
				getPolicyResponseContainerDto);

		// Act and Assert
		mockMvc.perform(
				get(uri).contentType(APPLICATION_JSON_UTF8).param("wildcard",
						wildcard))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(
						jsonPath("$.policies",
								hasSize(getPolicyResponseContainerDto
										.getPolicies().size())))
				.andExpect(jsonPath("$.policies[0].id", is(policyId1)))
				.andExpect(jsonPath("$.policies[0].valid", is(valid1)))
				.andExpect(
						jsonPath("$.policies[0].policy", is(Base64Utils
								.encodeToString(getPolicyResponseDto1
										.getPolicy()))))
				.andExpect(jsonPath("$.policies[1].id", is(policyId2)))
				.andExpect(jsonPath("$.policies[1].valid", is(valid2)))
				.andExpect(
						jsonPath("$.policies[1].policy", is(Base64Utils
								.encodeToString(getPolicyResponseDto2
										.getPolicy()))));

		// Assert
		verify(policyService, times(1)).getPolicies(policyId1, wildcard);
	}

	@SuppressWarnings("unused")
	@Test
	public void testGetPolicies_Success_Has_Wildcard_Single_Response()
			throws Exception {
		// Arrange
		final String policyId1 = "policyId";
		final boolean valid1 = true;
		final String wildcard = "wildcard";
		final String policy1 = "policy1";
		final String policy2 = "policy2";
		final PolicyDto getPolicyResponseDto1 = new PolicyDto();
		getPolicyResponseDto1.setId(policyId1);
		getPolicyResponseDto1.setValid(valid1);
		getPolicyResponseDto1.setPolicy(policy1.getBytes(DEFAULT_ENCODING));
		final PolicyContainerDto getPolicyResponseContainerDto = new PolicyContainerDto(
				getPolicyResponseDto1);
		final String uri = API + "/policies/" + policyId1;
		when(policyService.getPolicies(policyId1, wildcard)).thenReturn(
				getPolicyResponseContainerDto);

		// Act and Assert
		mockMvc.perform(
				get(uri).contentType(APPLICATION_JSON_UTF8).param("wildcard",
						wildcard))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(
						jsonPath("$.policies",
								hasSize(getPolicyResponseContainerDto
										.getPolicies().size())))
				.andExpect(jsonPath("$.policies[0].id", is(policyId1)))
				.andExpect(jsonPath("$.policies[0].valid", is(valid1)))
				.andExpect(
						jsonPath("$.policies[0].policy", is(Base64Utils
								.encodeToString(getPolicyResponseDto1
										.getPolicy()))));

		// Assert
		verify(policyService, times(1)).getPolicies(policyId1, wildcard);
	}

	@SuppressWarnings("unused")
	@Test
	public void testGetPolicies_Success_No_Wildcard_Single_Response()
			throws Exception {
		// Arrange
		final String policyId1 = "policyId";
		final boolean valid1 = true;
		final String wildcard = null;
		final String policy1 = "policy1";
		final String policy2 = "policy2";
		final PolicyDto getPolicyResponseDto1 = new PolicyDto();
		getPolicyResponseDto1.setId(policyId1);
		getPolicyResponseDto1.setValid(valid1);
		getPolicyResponseDto1.setPolicy(policy1.getBytes(DEFAULT_ENCODING));
		final PolicyContainerDto getPolicyResponseContainerDto = new PolicyContainerDto(
				getPolicyResponseDto1);
		final String uri = API + "/policies/" + policyId1;
		when(policyService.getPolicies(policyId1, wildcard)).thenReturn(
				getPolicyResponseContainerDto);

		// Act and Assert
		mockMvc.perform(get(uri).contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(
						jsonPath("$.policies",
								hasSize(getPolicyResponseContainerDto
										.getPolicies().size())))
				.andExpect(jsonPath("$.policies[0].id", is(policyId1)))
				.andExpect(jsonPath("$.policies[0].valid", is(valid1)))
				.andExpect(
						jsonPath("$.policies[0].policy", is(Base64Utils
								.encodeToString(getPolicyResponseDto1
										.getPolicy()))));

		// Assert
		verify(policyService, times(1)).getPolicies(policyId1, wildcard);
	}

	@Test
	public void testGetPoliciesCombinedAsPolicySet_Fail_By_Has_Wildcard_Has_PolicySetId_No_PolicyCombiningAlgId()
			throws Exception {
		// Arrange
		final String policyId = "policyId";
		final String policySet = "policySet";
		final String policySetId = "policySetId";
		final boolean policySetValid = true;
		final String policyCombiningAlgId = null;
		final String wildcard = "wildcard";
		final PolicyDto getPoliciesCombinedAsPolicySetResponseDto = new PolicyDto();
		getPoliciesCombinedAsPolicySetResponseDto.setId(policySetId);
		getPoliciesCombinedAsPolicySetResponseDto.setValid(policySetValid);
		getPoliciesCombinedAsPolicySetResponseDto.setPolicy(policySet
				.getBytes(DEFAULT_ENCODING));
		final String uri = API + "/policies/" + policyId + "/combined";
		when(
				policyService.getPoliciesCombinedAsPolicySet(policyId,
						wildcard, policySetId, policyCombiningAlgId))
				.thenReturn(getPoliciesCombinedAsPolicySetResponseDto);

		// Act and Assert
		mockMvc.perform(
				get(uri).contentType(APPLICATION_JSON_UTF8)
						.param("wildcard", wildcard)
						.param("policySetId", policySetId)
						.param("policyCombiningAlgId", policyCombiningAlgId))
				.andExpect(status().isBadRequest());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetPoliciesCombinedAsPolicySet_Fail_By_Throwing_DOMUtilsException()
			throws Exception {
		// Arrange
		final String policyId = "policyId";
		final String policySet = "policySet";
		final String policySetId = "policySetId";
		final boolean policySetValid = true;
		final String policyCombiningAlgId = "policyCombiningAlgId";
		final String wildcard = "wildcard";
		final PolicyDto getPoliciesCombinedAsPolicySetResponseDto = new PolicyDto();
		getPoliciesCombinedAsPolicySetResponseDto.setId(policySetId);
		getPoliciesCombinedAsPolicySetResponseDto.setValid(policySetValid);
		getPoliciesCombinedAsPolicySetResponseDto.setPolicy(policySet
				.getBytes(DEFAULT_ENCODING));
		final String uri = API + "/policies/" + policyId + "/combined";
		when(
				policyService.getPoliciesCombinedAsPolicySet(policyId,
						wildcard, policySetId, policyCombiningAlgId))
				.thenThrow(DOMUtilsException.class);

		// Act and Assert
		mockMvc.perform(
				get(uri).contentType(APPLICATION_JSON_UTF8)
						.param("wildcard", wildcard)
						.param("policySetId", policySetId)
						.param("policyCombiningAlgId", policyCombiningAlgId))
				.andExpect(status().isPreconditionFailed());

		// Assert
		verify(policyService, times(1)).getPoliciesCombinedAsPolicySet(
				policyId, wildcard, policySetId, policyCombiningAlgId);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetPoliciesCombinedAsPolicySet_Fail_By_Throwing_InvalidPolicyCombiningAlgIdException()
			throws Exception {
		// Arrange
		final String policyId = "policyId";
		final String policySet = "policySet";
		final String policySetId = "policySetId";
		final boolean policySetValid = true;
		final String policyCombiningAlgId = "policyCombiningAlgId";
		final String wildcard = "wildcard";
		final PolicyDto getPoliciesCombinedAsPolicySetResponseDto = new PolicyDto();
		getPoliciesCombinedAsPolicySetResponseDto.setId(policySetId);
		getPoliciesCombinedAsPolicySetResponseDto.setValid(policySetValid);
		getPoliciesCombinedAsPolicySetResponseDto.setPolicy(policySet
				.getBytes(DEFAULT_ENCODING));
		final String uri = API + "/policies/" + policyId + "/combined";
		when(
				policyService.getPoliciesCombinedAsPolicySet(policyId,
						wildcard, policySetId, policyCombiningAlgId))
				.thenThrow(InvalidPolicyCombiningAlgIdException.class);

		// Act and Assert
		mockMvc.perform(
				get(uri).contentType(APPLICATION_JSON_UTF8)
						.param("wildcard", wildcard)
						.param("policySetId", policySetId)
						.param("policyCombiningAlgId", policyCombiningAlgId))
				.andExpect(status().isBadRequest());

		// Assert
		verify(policyService, times(1)).getPoliciesCombinedAsPolicySet(
				policyId, wildcard, policySetId, policyCombiningAlgId);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetPoliciesCombinedAsPolicySet_Fail_By_Throwing_PolicyCombiningAlgIdNotFoundException()
			throws Exception {
		// Arrange
		final String policyId = "policyId";
		final String policySet = "policySet";
		final String policySetId = "policySetId";
		final boolean policySetValid = true;
		final String policyCombiningAlgId = "policyCombiningAlgId";
		final String wildcard = "wildcard";
		final PolicyDto getPoliciesCombinedAsPolicySetResponseDto = new PolicyDto();
		getPoliciesCombinedAsPolicySetResponseDto.setId(policySetId);
		getPoliciesCombinedAsPolicySetResponseDto.setValid(policySetValid);
		getPoliciesCombinedAsPolicySetResponseDto.setPolicy(policySet
				.getBytes(DEFAULT_ENCODING));
		final String uri = API + "/policies/" + policyId + "/combined";
		when(
				policyService.getPoliciesCombinedAsPolicySet(policyId,
						wildcard, policySetId, policyCombiningAlgId))
				.thenThrow(PolicyCombiningAlgIdNotFoundException.class);

		// Act and Assert
		mockMvc.perform(
				get(uri).contentType(APPLICATION_JSON_UTF8)
						.param("wildcard", wildcard)
						.param("policySetId", policySetId)
						.param("policyCombiningAlgId", policyCombiningAlgId))
				.andExpect(status().isBadRequest());

		// Assert
		verify(policyService, times(1)).getPoliciesCombinedAsPolicySet(
				policyId, wildcard, policySetId, policyCombiningAlgId);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetPoliciesCombinedAsPolicySet_Fail_By_Throwing_PolicyIdNotFoundException()
			throws Exception {
		// Arrange
		final String policyId = "policyId";
		final String policySet = "policySet";
		final String policySetId = "policySetId";
		final boolean policySetValid = true;
		final String policyCombiningAlgId = "policyCombiningAlgId";
		final String wildcard = "wildcard";
		final PolicyDto getPoliciesCombinedAsPolicySetResponseDto = new PolicyDto();
		getPoliciesCombinedAsPolicySetResponseDto.setId(policySetId);
		getPoliciesCombinedAsPolicySetResponseDto.setValid(policySetValid);
		getPoliciesCombinedAsPolicySetResponseDto.setPolicy(policySet
				.getBytes(DEFAULT_ENCODING));
		final String uri = API + "/policies/" + policyId + "/combined";
		when(
				policyService.getPoliciesCombinedAsPolicySet(policyId,
						wildcard, policySetId, policyCombiningAlgId))
				.thenThrow(PolicyIdNotFoundException.class);

		// Act and Assert
		mockMvc.perform(
				get(uri).contentType(APPLICATION_JSON_UTF8)
						.param("wildcard", wildcard)
						.param("policySetId", policySetId)
						.param("policyCombiningAlgId", policyCombiningAlgId))
				.andExpect(status().isPreconditionFailed());

		// Assert
		verify(policyService, times(1)).getPoliciesCombinedAsPolicySet(
				policyId, wildcard, policySetId, policyCombiningAlgId);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetPoliciesCombinedAsPolicySet_Fail_By_Throwing_PolicyNotFoundException()
			throws Exception {
		// Arrange
		final String policyId = "policyId";
		final String policySet = "policySet";
		final String policySetId = "policySetId";
		final boolean policySetValid = true;
		final String policyCombiningAlgId = "policyCombiningAlgId";
		final String wildcard = "wildcard";
		final PolicyDto getPoliciesCombinedAsPolicySetResponseDto = new PolicyDto();
		getPoliciesCombinedAsPolicySetResponseDto.setId(policySetId);
		getPoliciesCombinedAsPolicySetResponseDto.setValid(policySetValid);
		getPoliciesCombinedAsPolicySetResponseDto.setPolicy(policySet
				.getBytes(DEFAULT_ENCODING));
		final String uri = API + "/policies/" + policyId + "/combined";
		when(
				policyService.getPoliciesCombinedAsPolicySet(policyId,
						wildcard, policySetId, policyCombiningAlgId))
				.thenThrow(PolicyNotFoundException.class);

		// Act and Assert
		mockMvc.perform(
				get(uri).contentType(APPLICATION_JSON_UTF8)
						.param("wildcard", wildcard)
						.param("policySetId", policySetId)
						.param("policyCombiningAlgId", policyCombiningAlgId))
				.andExpect(status().isNotFound());

		// Assert
		verify(policyService, times(1)).getPoliciesCombinedAsPolicySet(
				policyId, wildcard, policySetId, policyCombiningAlgId);
	}

	@Test
	public void testGetPoliciesCombinedAsPolicySet_Success_Has_Wildcard_Has_PolicySetId()
			throws Exception {
		// Arrange
		final String policyId = "policyId";
		final String policySet = "policySet";
		final String policySetId = "policySetId";
		final boolean policySetValid = true;
		final String policyCombiningAlgId = "policyCombiningAlgId";
		final String wildcard = "wildcard";
		final PolicyDto getPoliciesCombinedAsPolicySetResponseDto = new PolicyDto();
		getPoliciesCombinedAsPolicySetResponseDto.setId(policySetId);
		getPoliciesCombinedAsPolicySetResponseDto.setValid(policySetValid);
		getPoliciesCombinedAsPolicySetResponseDto.setPolicy(policySet
				.getBytes(DEFAULT_ENCODING));
		final String uri = API + "/policies/" + policyId + "/combined";
		when(
				policyService.getPoliciesCombinedAsPolicySet(policyId,
						wildcard, policySetId, policyCombiningAlgId))
				.thenReturn(getPoliciesCombinedAsPolicySetResponseDto);

		// Act and Assert
		mockMvc.perform(
				get(uri).contentType(APPLICATION_JSON_UTF8)
						.param("wildcard", wildcard)
						.param("policySetId", policySetId)
						.param("policyCombiningAlgId", policyCombiningAlgId))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(policySetId)))
				.andExpect(jsonPath("$.valid", is(policySetValid)))
				.andExpect(
						jsonPath(
								"$.policy",
								is(Base64Utils
										.encodeToString(getPoliciesCombinedAsPolicySetResponseDto
												.getPolicy()))));

		// Assert
		verify(policyService, times(1)).getPoliciesCombinedAsPolicySet(
				policyId, wildcard, policySetId, policyCombiningAlgId);
	}

	@Test
	public void testGetPoliciesCombinedAsPolicySet_Success_Has_Wildcard_No_PolicySetId()
			throws Exception {
		// Arrange
		final String policyId = "policyId";
		final String policySet = "policySet";
		final String policySetId = null;
		final boolean policySetValid = true;
		final String policyCombiningAlgId = "policyCombiningAlgId";
		final String wildcard = "wildcard";
		final PolicyDto getPoliciesCombinedAsPolicySetResponseDto = new PolicyDto();
		getPoliciesCombinedAsPolicySetResponseDto.setId(policySetId);
		getPoliciesCombinedAsPolicySetResponseDto.setValid(policySetValid);
		getPoliciesCombinedAsPolicySetResponseDto.setPolicy(policySet
				.getBytes(DEFAULT_ENCODING));
		final String uri = API + "/policies/" + policyId + "/combined";
		when(
				policyService.getPoliciesCombinedAsPolicySet(policyId,
						wildcard, policySetId, policyCombiningAlgId))
				.thenReturn(getPoliciesCombinedAsPolicySetResponseDto);

		// Act and Assert
		mockMvc.perform(
				get(uri).contentType(APPLICATION_JSON_UTF8)
						.param("wildcard", wildcard)
						.param("policySetId", policySetId)
						.param("policyCombiningAlgId", policyCombiningAlgId))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(policySetId)))
				.andExpect(jsonPath("$.valid", is(policySetValid)))
				.andExpect(
						jsonPath(
								"$.policy",
								is(Base64Utils
										.encodeToString(getPoliciesCombinedAsPolicySetResponseDto
												.getPolicy()))));

		// Assert
		verify(policyService, times(1)).getPoliciesCombinedAsPolicySet(
				policyId, wildcard, policySetId, policyCombiningAlgId);
	}

	@Test
	public void testGetPoliciesCombinedAsPolicySet_Success_No_Wildcard_Has_PolicySetId()
			throws Exception {
		// Arrange
		final String policyId = "policyId";
		final String policySet = "policySet";
		final String policySetId = "policySetId";
		final boolean policySetValid = true;
		final String policyCombiningAlgId = "policyCombiningAlgId";
		final String wildcard = null;
		final PolicyDto getPoliciesCombinedAsPolicySetResponseDto = new PolicyDto();
		getPoliciesCombinedAsPolicySetResponseDto.setId(policySetId);
		getPoliciesCombinedAsPolicySetResponseDto.setValid(policySetValid);
		getPoliciesCombinedAsPolicySetResponseDto.setPolicy(policySet
				.getBytes(DEFAULT_ENCODING));
		final String uri = API + "/policies/" + policyId + "/combined";
		when(
				policyService.getPoliciesCombinedAsPolicySet(policyId,
						wildcard, policySetId, policyCombiningAlgId))
				.thenReturn(getPoliciesCombinedAsPolicySetResponseDto);

		// Act and Assert
		mockMvc.perform(
				get(uri).contentType(APPLICATION_JSON_UTF8)
						.param("wildcard", wildcard)
						.param("policySetId", policySetId)
						.param("policyCombiningAlgId", policyCombiningAlgId))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(policySetId)))
				.andExpect(jsonPath("$.valid", is(policySetValid)))
				.andExpect(
						jsonPath(
								"$.policy",
								is(Base64Utils
										.encodeToString(getPoliciesCombinedAsPolicySetResponseDto
												.getPolicy()))));

		// Assert
		verify(policyService, times(1)).getPoliciesCombinedAsPolicySet(
				policyId, wildcard, policySetId, policyCombiningAlgId);
	}

	@Test
	public void testGetPoliciesCombinedAsPolicySet_Success_No_Wildcard_No_PolicySetId()
			throws Exception {
		// Arrange
		final String policyId = "policyId";
		final String policySet = "policySet";
		final String policySetId = null;
		final boolean policySetValid = true;
		final String policyCombiningAlgId = "policyCombiningAlgId";
		final String wildcard = null;
		final PolicyDto getPoliciesCombinedAsPolicySetResponseDto = new PolicyDto();
		getPoliciesCombinedAsPolicySetResponseDto.setId(policySetId);
		getPoliciesCombinedAsPolicySetResponseDto.setValid(policySetValid);
		getPoliciesCombinedAsPolicySetResponseDto.setPolicy(policySet
				.getBytes(DEFAULT_ENCODING));
		final String uri = API + "/policies/" + policyId + "/combined";
		when(
				policyService.getPoliciesCombinedAsPolicySet(policyId,
						wildcard, policySetId, policyCombiningAlgId))
				.thenReturn(getPoliciesCombinedAsPolicySetResponseDto);

		// Act and Assert
		mockMvc.perform(
				get(uri).contentType(APPLICATION_JSON_UTF8)
						.param("wildcard", wildcard)
						.param("policySetId", policySetId)
						.param("policyCombiningAlgId", policyCombiningAlgId))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(policySetId)))
				.andExpect(jsonPath("$.valid", is(policySetValid)))
				.andExpect(
						jsonPath(
								"$.policy",
								is(Base64Utils
										.encodeToString(getPoliciesCombinedAsPolicySetResponseDto
												.getPolicy()))));

		// Assert
		verify(policyService, times(1)).getPoliciesCombinedAsPolicySet(
				policyId, wildcard, policySetId, policyCombiningAlgId);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdatePolicy_Fail_By_Throwing_ConflictingRequestException()
			throws Exception {
		// Arrange
		final String policyId = "policyId";
		final boolean valid = true;
		final String policy = "policy";
		final String uri = API + "/policies/" + policyId;
		final PolicyContentDto updatePolicyRequestDto = new PolicyContentDto();
		updatePolicyRequestDto.setPolicy(policy.getBytes(DEFAULT_ENCODING));
		final PolicyMetadataDto updatePolicyResponseDto = new PolicyMetadataDto();
		updatePolicyResponseDto.setId(policyId);
		updatePolicyResponseDto.setValid(valid);
		when(
				policyService.updatePolicy(any(PolicyContentDto.class),
						eq(policyId))).thenThrow(
				ConflictingRequestException.class);

		// Act and Assert
		mockMvc.perform(
				put(uri).contentType(APPLICATION_JSON_UTF8).content(
						convertObjectToJsonBytes(updatePolicyRequestDto)))
				.andExpect(status().isConflict());

		// Assert
		verify(policyService, times(1))
				.updatePolicy(
						argThat(matching((PolicyContentDto req) -> policy
								.equals(bytesToString(req)))),
						eq(policyId));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdatePolicy_Fail_By_Throwing_DOMUtilsException()
			throws Exception {
		// Arrange
		final String policyId = "policyId";
		final boolean valid = true;
		final String policy = "policy";
		final String uri = API + "/policies/" + policyId;
		final PolicyContentDto updatePolicyRequestDto = new PolicyContentDto();
		updatePolicyRequestDto.setPolicy(policy.getBytes(DEFAULT_ENCODING));
		final PolicyMetadataDto updatePolicyResponseDto = new PolicyMetadataDto();
		updatePolicyResponseDto.setId(policyId);
		updatePolicyResponseDto.setValid(valid);
		when(
				policyService.updatePolicy(any(PolicyContentDto.class),
						eq(policyId))).thenThrow(DOMUtilsException.class);

		// Act and Assert
		mockMvc.perform(
				put(uri).contentType(APPLICATION_JSON_UTF8).content(
						convertObjectToJsonBytes(updatePolicyRequestDto)))
				.andExpect(status().isPreconditionFailed());

		// Assert
		verify(policyService, times(1))
				.updatePolicy(
						argThat(matching((PolicyContentDto req) -> policy
								.equals(bytesToString(req)))),
						eq(policyId));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdatePolicy_Fail_By_Throwing_PolicyIdNotFoundException()
			throws Exception {
		// Arrange
		final String policyId = "policyId";
		final boolean valid = true;
		final String policy = "policy";
		final String uri = API + "/policies/" + policyId;
		final PolicyContentDto updatePolicyRequestDto = new PolicyContentDto();
		updatePolicyRequestDto.setPolicy(policy.getBytes(DEFAULT_ENCODING));
		final PolicyMetadataDto updatePolicyResponseDto = new PolicyMetadataDto();
		updatePolicyResponseDto.setId(policyId);
		updatePolicyResponseDto.setValid(valid);
		when(
				policyService.updatePolicy(any(PolicyContentDto.class),
						eq(policyId))).thenThrow(
				PolicyIdNotFoundException.class);

		// Act and Assert
		mockMvc.perform(
				put(uri).contentType(APPLICATION_JSON_UTF8).content(
						convertObjectToJsonBytes(updatePolicyRequestDto)))
				.andExpect(status().isPreconditionFailed());

		// Assert
		verify(policyService, times(1))
				.updatePolicy(
						argThat(matching((PolicyContentDto req) -> policy
								.equals(bytesToString(req)))),
						eq(policyId));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdatePolicy_Fail_By_Throwing_PolicyNotFoundException()
			throws Exception {
		// Arrange
		final String policyId = "policyId";
		final boolean valid = true;
		final String policy = "policy";
		final String uri = API + "/policies/" + policyId;
		final PolicyContentDto updatePolicyRequestDto = new PolicyContentDto();
		updatePolicyRequestDto.setPolicy(policy.getBytes(DEFAULT_ENCODING));
		final PolicyMetadataDto updatePolicyResponseDto = new PolicyMetadataDto();
		updatePolicyResponseDto.setId(policyId);
		updatePolicyResponseDto.setValid(valid);
		when(
				policyService.updatePolicy(any(PolicyContentDto.class),
						eq(policyId))).thenThrow(PolicyNotFoundException.class);

		// Act and Assert
		mockMvc.perform(
				put(uri).contentType(APPLICATION_JSON_UTF8).content(
						convertObjectToJsonBytes(updatePolicyRequestDto)))
				.andExpect(status().isNotFound());

		// Assert
		verify(policyService, times(1))
				.updatePolicy(
						argThat(matching((PolicyContentDto req) -> policy
								.equals(bytesToString(req)))),
						eq(policyId));
	}

	@Test
	public void testUpdatePolicy_Success() throws Exception {
		// Arrange
		final String policyId = "policyId";
		final boolean valid = true;
		final String policy = "policy";
		final String uri = API + "/policies/" + policyId;
		final PolicyContentDto updatePolicyRequestDto = new PolicyContentDto();
		updatePolicyRequestDto.setPolicy(policy.getBytes(DEFAULT_ENCODING));
		final PolicyMetadataDto updatePolicyResponseDto = new PolicyMetadataDto();
		updatePolicyResponseDto.setId(policyId);
		updatePolicyResponseDto.setValid(valid);
		when(
				policyService.updatePolicy(any(PolicyContentDto.class),
						eq(policyId))).thenReturn(updatePolicyResponseDto);

		// Act and Assert
		mockMvc.perform(
				put(uri).contentType(APPLICATION_JSON_UTF8).content(
						convertObjectToJsonBytes(updatePolicyRequestDto)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id", is(policyId)))
				.andExpect(jsonPath("$.valid", is(valid)));

		// Assert
		verify(policyService, times(1))
				.updatePolicy(
						argThat(matching((PolicyContentDto req) -> policy
								.equals(bytesToString(req)))),
						eq(policyId));
	}

	private static String bytesToString(PolicyContentDto req) {
		try {
			return new String(req.getPolicy(), DEFAULT_ENCODING);
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

}
