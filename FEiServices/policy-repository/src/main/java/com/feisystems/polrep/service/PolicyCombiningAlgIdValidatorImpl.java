package com.feisystems.polrep.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.feisystems.polrep.service.exception.InvalidPolicyCombiningAlgIdException;
import com.feisystems.polrep.service.exception.PolicyCombiningAlgIdNotFoundException;

@Component
@ConfigurationProperties(prefix = "polrep.policySet")
public class PolicyCombiningAlgIdValidatorImpl implements
		PolicyCombiningAlgIdValidator {

	private final Map<String, String> combiningAlgs = new HashMap<String, String>();

	@Override
	public Map<String, String> getCombiningAlgs() {
		return combiningAlgs;
	}

	@Override
	public String validateAndReturn(String policyCombiningAlgId) {
		if (!StringUtils.hasText(policyCombiningAlgId)) {
			throw new PolicyCombiningAlgIdNotFoundException(
					"policyCombiningAlgId must have a valid text!");
		}
		if (combiningAlgs.keySet().contains(policyCombiningAlgId)) {
			policyCombiningAlgId = combiningAlgs.get(policyCombiningAlgId);
		} else {
			if (combiningAlgs.entrySet().stream().map(Entry::getValue)
					.noneMatch(policyCombiningAlgId::equals)) {
				final StringBuilder errorBuilder = new StringBuilder();
				errorBuilder.append("The policyCombiningAlgId: ")
						.append(policyCombiningAlgId)
						.append(" is not a valid value!");
				throw new InvalidPolicyCombiningAlgIdException(
						errorBuilder.toString());
			}
		}
		return policyCombiningAlgId;
	}

}
