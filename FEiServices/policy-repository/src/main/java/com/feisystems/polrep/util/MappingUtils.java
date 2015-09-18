package com.feisystems.polrep.util;

import static com.feisystems.polrep.service.xacml.XACMLXPath.XPATH_POLICY_ID;
import static com.feisystems.polrep.service.xacml.XACMLXPath.XPATH_POLICY_SET_ID;
import static com.feisystems.polrep.util.DOMUtils.bytesToDocument;

import java.util.Optional;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.feisystems.polrep.service.dto.PolicyContentDto;
import com.feisystems.polrep.service.dto.PolicyDto;
import com.feisystems.polrep.service.exception.PolicyIdNotFoundException;

public class MappingUtils {

	public static PolicyDto toPolicyDto(byte[] policy) {
		String id = null;
		final Document policyDoc = bytesToDocument(policy);
		final Optional<Node> policySetId = DOMUtils.getNode(policyDoc,
				XPATH_POLICY_SET_ID);
		if (policySetId.isPresent()) {
			id = policySetId.get().getNodeValue();
		} else {
			final Optional<Node> policyId = DOMUtils.getNode(policyDoc,
					XPATH_POLICY_ID);
			if (policyId.isPresent()) {
				id = policyId.get().getNodeValue();
			}
		}
		if (id == null) {
			throw new PolicyIdNotFoundException(
					"Cannot find the PolicyId/PolicySetId in one or more of the submitted policies!");
		} else {
			final PolicyDto dto = new PolicyDto();
			dto.setId(id);
			dto.setPolicy(policy);
			return dto;
		}
	}

	public static PolicyDto toPolicyDto(PolicyContentDto policyContentDto) {
		return toPolicyDto(policyContentDto.getPolicy());
	}
}
