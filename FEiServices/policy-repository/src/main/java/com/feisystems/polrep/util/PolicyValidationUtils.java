package com.feisystems.polrep.util;

import java.io.ByteArrayInputStream;

import org.herasaf.xacml.core.policy.Evaluatable;
import org.herasaf.xacml.core.policy.PolicyMarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.w3c.dom.Document;

import com.feisystems.polrep.service.dto.PolicyDto;

public class PolicyValidationUtils {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PolicyValidationUtils.class);

	public static boolean validate(final byte[] policy) {
		boolean isValid = false;
		try {
			final String policyString = new String(policy,
					DOMUtils.DEFAULT_ENCODING);
			final Document policyDoc = DOMUtils.bytesToDocument(policy);
			final Evaluatable policyObj = PolicyMarshaller
					.unmarshal(new ByteArrayInputStream(policy));
			Assert.notNull(policyString);
			Assert.notNull(policyObj);
			Assert.notNull(policyDoc);
			isValid = true;
		} catch (final Exception e) {
			LOGGER.debug(e.getMessage(), e);
		}
		return isValid;
	}

	public static PolicyDto validateAndReturn(PolicyDto policyDto) {
		policyDto.setValid(validate(policyDto.getPolicy()));
		return policyDto;
	}
}
