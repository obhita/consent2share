package com.feisystems.polrep.web.v1;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.feisystems.polrep.service.PolicyService;
import com.feisystems.polrep.service.dto.PolicyContainerDto;
import com.feisystems.polrep.service.dto.PolicyContentContainerDto;
import com.feisystems.polrep.service.dto.PolicyContentDto;
import com.feisystems.polrep.service.dto.PolicyDto;
import com.feisystems.polrep.service.dto.PolicyMetadataContainerDto;
import com.feisystems.polrep.service.dto.PolicyMetadataDto;

@RestController
public class PolicyControllerV1 extends AbstractPolRepRestApiControllerV1 {

	@Autowired
	private PolicyService policyService;

	@RequestMapping(value = "/policies", method = RequestMethod.POST)
	public PolicyMetadataContainerDto addPolicies(
			@Valid @RequestBody PolicyContentContainerDto addPolicyRequestContainerDto,
			@RequestParam(value = "force", required = false) boolean force) {
		return policyService.addPolicies(addPolicyRequestContainerDto, force);
	}

	@RequestMapping(value = "/policies/{policyId}", method = RequestMethod.DELETE)
	public void deletePolicy(@PathVariable(value = "policyId") String policyId,
			HttpServletResponse response) {
		policyService.deletePolicy(policyId);
		response.setStatus(HttpServletResponse.SC_NO_CONTENT);

	}

	@RequestMapping(value = "/policies", method = RequestMethod.GET)
	public PolicyMetadataContainerDto getAllPolicyMetadata() {
		return policyService.getAllPolicyMetadata();
	}

	@RequestMapping(value = "/policies/{policyId}", method = RequestMethod.GET)
	public PolicyContainerDto getPolicies(
			@PathVariable(value = "policyId") String policyId,
			@RequestParam(value = "wildcard", required = false) String wildcard) {
		return policyService.getPolicies(policyId, wildcard);
	}

	@RequestMapping(value = "/policies/{policyId}/combined", method = RequestMethod.GET)
	public PolicyDto getPoliciesCombinedAsPolicySet(
			@PathVariable(value = "policyId") String policyId,
			@RequestParam(value = "wildcard", required = false) String wildcard,
			@RequestParam(value = "policySetId", required = false) String policySetId,
			@RequestParam(value = "policyCombiningAlgId", required = true) String policyCombiningAlgId) {
		return policyService.getPoliciesCombinedAsPolicySet(policyId, wildcard,
				policySetId, policyCombiningAlgId);
	}

	@RequestMapping(value = "/policyCombiningAlgIds", method = RequestMethod.GET)
	public Map<String, String> getPolicyCombiningAlgIds() {
		return policyService.getPolicyCombiningAlgIds();
	}

	@RequestMapping(value = "/policies/{policyId}", method = RequestMethod.PUT)
	public PolicyMetadataDto updatePolicy(
			@Valid @RequestBody PolicyContentDto updatePolicyRequestDto,
			@PathVariable(value = "policyId") String policyId) {
		return policyService.updatePolicy(updatePolicyRequestDto, policyId);
	}

}
