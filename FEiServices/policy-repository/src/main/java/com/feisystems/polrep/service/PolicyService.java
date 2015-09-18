package com.feisystems.polrep.service;

import java.util.Map;

import com.feisystems.polrep.service.dto.PolicyContainerDto;
import com.feisystems.polrep.service.dto.PolicyContentContainerDto;
import com.feisystems.polrep.service.dto.PolicyContentDto;
import com.feisystems.polrep.service.dto.PolicyDto;
import com.feisystems.polrep.service.dto.PolicyMetadataContainerDto;
import com.feisystems.polrep.service.dto.PolicyMetadataDto;

public interface PolicyService {

	public abstract PolicyMetadataContainerDto addPolicies(
			PolicyContentContainerDto addPolicyRequestContainerDto,
			boolean force);

	public abstract void deletePolicy(String policyId);

	public abstract PolicyMetadataContainerDto getAllPolicyMetadata();

	public abstract PolicyContainerDto getPolicies(String policyId,
			String wildcard);

	public abstract PolicyDto getPoliciesCombinedAsPolicySet(String policyId,
			String wildcard, String policySetId, String policyCombiningAlgId);

	public abstract Map<String, String> getPolicyCombiningAlgIds();

	public abstract PolicyMetadataDto updatePolicy(
			PolicyContentDto updatePolicyRequestDto, String policyId);

}
