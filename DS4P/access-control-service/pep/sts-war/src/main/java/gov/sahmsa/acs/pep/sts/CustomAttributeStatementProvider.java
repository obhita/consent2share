package gov.sahmsa.acs.pep.sts;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Element;

import org.apache.cxf.sts.request.ReceivedToken;
import org.apache.cxf.sts.request.TokenRequirements;
import org.apache.cxf.sts.token.provider.AttributeStatementProvider;
import org.apache.cxf.sts.token.provider.TokenProviderParameters;
import org.apache.cxf.ws.security.sts.provider.STSException;
import org.apache.cxf.ws.security.sts.provider.model.secext.UsernameTokenType;
import org.apache.ws.security.SAMLTokenPrincipal;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSSecurityException;
import org.apache.ws.security.saml.ext.AssertionWrapper;
import org.apache.ws.security.saml.ext.bean.AttributeBean;
import org.apache.ws.security.saml.ext.bean.AttributeStatementBean;

public class CustomAttributeStatementProvider implements AttributeStatementProvider {

    /**
     * Get an AttributeStatementBean using the given parameters.
     */
    public AttributeStatementBean getStatement(TokenProviderParameters providerParameters) {
        AttributeStatementBean attrBean = new AttributeStatementBean();
        List<AttributeBean> attributeList = new ArrayList<AttributeBean>();

        TokenRequirements tokenRequirements = providerParameters.getTokenRequirements();
        String tokenType = tokenRequirements.getTokenType();
        
        // create custom attributes
        AttributeBean attributeBeanResourceId = createDefaultAttribute(tokenType, "urn:oasis:names:tc:xacml:1.0:resource:resource-id", "d3bb3930-7241-11e3-b4f7-00155d3a2124^^^&2.16.840.1.113883.4.357&ISO");
        AttributeBean attributeBeanPurposeOfUse = createDefaultAttribute(tokenType, "urn:oasis:names:tc:xspa:1.0:subject:purposeofuse", "TREAT");
        AttributeBean attributeBeanIntermediarySubject = createDefaultAttribute(tokenType, "urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject", "1114252178");
        AttributeBean attributeBeanRecipientSubject = createDefaultAttribute(tokenType, "urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject", "1760717789");
        
        attributeList.add(attributeBeanResourceId);
        attributeList.add(attributeBeanPurposeOfUse);
        attributeList.add(attributeBeanIntermediarySubject);
        attributeList.add(attributeBeanRecipientSubject);
        
        ReceivedToken onBehalfOf = tokenRequirements.getOnBehalfOf();
        ReceivedToken actAs = tokenRequirements.getActAs();
        try {
            if (onBehalfOf != null) {
                AttributeBean parameterBean = 
                    handleAdditionalParameters(false, onBehalfOf.getToken(), tokenType);
                if (!parameterBean.getAttributeValues().isEmpty()) {
                    attributeList.add(parameterBean);
                }
            }
            if (actAs != null) {
                AttributeBean parameterBean = 
                    handleAdditionalParameters(true, actAs.getToken(), tokenType);
                if (!parameterBean.getAttributeValues().isEmpty()) {
                    attributeList.add(parameterBean);
                }
            }
        } catch (WSSecurityException ex) {
            throw new STSException(ex.getMessage(), ex);
        }
        
        attrBean.setSamlAttributes(attributeList);
        
        return attrBean;
    }
    
    /**
     * Create a default attribute
     */
    private AttributeBean createDefaultAttribute(String tokenType, String name, String value) {
        AttributeBean attributeBean = new AttributeBean();

        if (WSConstants.WSS_SAML2_TOKEN_TYPE.equals(tokenType)
            || WSConstants.SAML2_NS.equals(tokenType)) {
            attributeBean.setQualifiedName(name);
            //attributeBean.setNameFormat("http://cxf.apache.org/sts");
        } else {
            attributeBean.setSimpleName(name);
            //attributeBean.setQualifiedName("http://cxf.apache.org/sts");
        }
        
        attributeBean.setAttributeValues(Collections.singletonList(value));
        
        return attributeBean;
    }

    /**
     * Handle ActAs or OnBehalfOf elements.
     */
    private AttributeBean handleAdditionalParameters(
        boolean actAs, 
        Object parameter, 
        String tokenType
    ) throws WSSecurityException {
        AttributeBean parameterBean = new AttributeBean();

        String claimType = actAs ? "ActAs" : "OnBehalfOf";
        if (WSConstants.WSS_SAML2_TOKEN_TYPE.equals(tokenType) || WSConstants.SAML2_NS.equals(tokenType)) {
            parameterBean.setQualifiedName(claimType);
            //parameterBean.setNameFormat("http://cxf.apache.org/sts");
        } else {
            parameterBean.setSimpleName(claimType);
            //parameterBean.setQualifiedName("http://cxf.apache.org/sts");
        }
        if (parameter instanceof UsernameTokenType) {
            parameterBean.setAttributeValues(
                Collections.singletonList(((UsernameTokenType)parameter).getUsername().getValue())
            );
        } else if (parameter instanceof Element) {
            AssertionWrapper wrapper = new AssertionWrapper((Element)parameter);
            SAMLTokenPrincipal principal = new SAMLTokenPrincipal(wrapper);
            parameterBean.setAttributeValues(Collections.singletonList(principal.getName()));
        }

        return parameterBean;
    }


}