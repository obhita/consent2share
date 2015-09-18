package gov.samhsa.acs.pep.sts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.cxf.sts.request.ReceivedToken;
import org.apache.cxf.sts.request.TokenRequirements;
import org.apache.cxf.sts.token.provider.AttributeStatementProvider;
import org.apache.cxf.sts.token.provider.TokenProviderParameters;
import org.apache.cxf.ws.security.sts.provider.STSException;
import org.apache.cxf.ws.security.sts.provider.model.secext.UsernameTokenType;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.principal.SAMLTokenPrincipal;
import org.apache.wss4j.common.principal.SAMLTokenPrincipalImpl;
import org.apache.wss4j.common.saml.SamlAssertionWrapper;
import org.apache.wss4j.common.saml.bean.AttributeBean;
import org.apache.wss4j.common.saml.bean.AttributeStatementBean;
import org.apache.wss4j.dom.WSConstants;
import org.w3c.dom.Element;

public class CustomAttributeStatementProvider implements
		AttributeStatementProvider {

	/**
	 * Get an AttributeStatementBean using the given parameters.
	 */
	@Override
	public AttributeStatementBean getStatement(
			TokenProviderParameters providerParameters) {
		final AttributeStatementBean attrBean = new AttributeStatementBean();
		final List<AttributeBean> attributeList = new ArrayList<AttributeBean>();

		final TokenRequirements tokenRequirements = providerParameters
				.getTokenRequirements();
		final String tokenType = tokenRequirements.getTokenType();

		// create custom attributes
		final AttributeBean attributeBeanResourceId = createDefaultAttribute(
				tokenType, "urn:oasis:names:tc:xacml:1.0:resource:resource-id",
				"d9d460e0-e1fc-11e4-941d-00155d0a6a16^^^&2.16.840.1.113883.4.357&ISO");
		final AttributeBean attributeBeanPurposeOfUse = createDefaultAttribute(
				tokenType, "urn:oasis:names:tc:xspa:1.0:subject:purposeofuse",
				"TREATMENT");
		// 1114252178 MORGAN, TERRENCE ..from1740515725 HOANG, DAN
		final AttributeBean attributeBeanIntermediarySubject = createDefaultAttribute(
				tokenType,
				"urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject",
				"1659606549");
		// 1760717789 LAMONT BUNYON, OD, PA .. to1902131865 MASTER CARE, INC.
		final AttributeBean attributeBeanRecipientSubject = createDefaultAttribute(
				tokenType,
				"urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject",
				"1679808687");

		attributeList.add(attributeBeanResourceId);
		attributeList.add(attributeBeanPurposeOfUse);
		attributeList.add(attributeBeanIntermediarySubject);
		attributeList.add(attributeBeanRecipientSubject);

		final ReceivedToken onBehalfOf = tokenRequirements.getOnBehalfOf();
		final ReceivedToken actAs = tokenRequirements.getActAs();
		try {
			if (onBehalfOf != null) {
				final AttributeBean parameterBean = handleAdditionalParameters(
						false, onBehalfOf.getToken(), tokenType);
				if (!parameterBean.getAttributeValues().isEmpty()) {
					attributeList.add(parameterBean);
				}
			}
			if (actAs != null) {
				final AttributeBean parameterBean = handleAdditionalParameters(
						true, actAs.getToken(), tokenType);
				if (!parameterBean.getAttributeValues().isEmpty()) {
					attributeList.add(parameterBean);
				}
			}
		} catch (final WSSecurityException ex) {
			throw new STSException(ex.getMessage(), ex);
		}

		attrBean.setSamlAttributes(attributeList);

		return attrBean;
	}

	/**
	 * Create a default attribute
	 */
	private AttributeBean createDefaultAttribute(String tokenType, String name,
			String value) {
		final AttributeBean attributeBean = new AttributeBean();

		if (WSConstants.WSS_SAML2_TOKEN_TYPE.equals(tokenType)
				|| WSConstants.SAML2_NS.equals(tokenType)) {
			attributeBean.setQualifiedName(name);
			// attributeBean.setNameFormat("http://cxf.apache.org/sts");
		} else {
			attributeBean.setSimpleName(name);
			// attributeBean.setQualifiedName("http://cxf.apache.org/sts");
		}

		attributeBean.setAttributeValues(Collections
				.singletonList((Object) value));

		return attributeBean;
	}

	/**
	 * Handle ActAs or OnBehalfOf elements.
	 */
	private AttributeBean handleAdditionalParameters(boolean actAs,
			Object parameter, String tokenType) throws WSSecurityException {
		final AttributeBean parameterBean = new AttributeBean();

		final String claimType = actAs ? "ActAs" : "OnBehalfOf";
		if (WSConstants.WSS_SAML2_TOKEN_TYPE.equals(tokenType)
				|| WSConstants.SAML2_NS.equals(tokenType)) {
			parameterBean.setQualifiedName(claimType);
			// parameterBean.setNameFormat("http://cxf.apache.org/sts");
		} else {
			parameterBean.setSimpleName(claimType);
			// parameterBean.setQualifiedName("http://cxf.apache.org/sts");
		}
		if (parameter instanceof UsernameTokenType) {
			parameterBean.setAttributeValues(Collections
					.singletonList((Object) ((UsernameTokenType) parameter)
							.getUsername().getValue()));
		} else if (parameter instanceof Element) {
			final SamlAssertionWrapper wrapper = new SamlAssertionWrapper(
					(Element) parameter);
			final SAMLTokenPrincipal principal = new SAMLTokenPrincipalImpl(
					wrapper);
			parameterBean.setAttributeValues(Collections
					.singletonList((Object) principal.getName()));
		}

		return parameterBean;
	}

}