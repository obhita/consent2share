package gov.samhsa.acs.pep.saml;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.ws.security.saml.ext.AssertionWrapper;
import org.opensaml.Configuration;
import org.opensaml.DefaultBootstrap;
import org.opensaml.saml2.core.Action;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AuthnContext;
import org.opensaml.saml2.core.AuthnStatement;
import org.opensaml.saml2.core.AuthzDecisionStatement;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.opensaml.common.xml.SAMLConstants;

/*
 * This sample SOAP Protocol Handler for DoubleIt checks for X.509 authentication,
 * attribute of Math degree, and authorization to double even numbers.
 *
 * WARNING: No actual security being coded here, sample 
 * just shows using OpenSAML to read a SAML token.
 */
public class SamlTokenParser {

	// change this to redirect output if desired
	private static PrintStream out = System.out;

	private static String WS_SECURITY_URI = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";

	public String parse(AssertionWrapper assertionWrapper, String urn) {
		try {
			Assertion samlAssertion = assertionWrapper.getSaml2();
			List<AttributeStatement> stmt = samlAssertion.getAttributeStatements();
			String nameId = samlAssertion.getSubject().getNameID().getValue();
			
			/** Below code works with OpenSAML API to check Authentication,
            * Authorization, and attributes. Using the XPath API with the
            * assertionElement above would probably be an easier and more
            * readable option.*/
			
			Element assertionElement = assertionWrapper.getElement();
			
            
			// Check if math degree, error otherwise
			List<AttributeStatement> asList = samlAssertion
					.getAttributeStatements();
			if (asList == null || asList.size() == 0) {
				throw createSOAPFaultException("Attributes are missing.", true);
			} else {
				boolean hasValidAttribute = false;
				String additionalValue = "";
				for (Iterator<AttributeStatement> it = asList.iterator(); it.hasNext();) {
					AttributeStatement as = it.next();
					List<Attribute> attList = as.getAttributes();
					if (attList == null || attList.size() == 0) {
						throw createSOAPFaultException(
								"Attributes are missing.", true);
					} else {
						for (Iterator<Attribute> it2 = attList.iterator(); it2.hasNext();) {
							Attribute att = it2.next();
							if (!att.getName().equals(urn)) {
								continue;
							} else {
								List<XMLObject> xoList = att
										.getAttributeValues();
								if (xoList == null || xoList.size() < 1
										|| xoList.size() > 1) {
									throw createSOAPFaultException(
											"Attributes are missing.", true);
								} else {
									XMLObject xmlObj = xoList.get(0);
									additionalValue = xmlObj.getDOM().getFirstChild()
											.getTextContent();
									return xmlObj.getDOM().getFirstChild().getTextContent();
//									if (xmlObj.getDOM().getFirstChild()
//											.getTextContent()
//											.equals("Mathematics")) {
//										hasMathDegree = true;
//									}
								}
							}
						}
					}
				}
				if (hasValidAttribute == false) {
					System.out.println("No Valid Attribute.");
					System.out.println(additionalValue);
				} 
			}

		} catch (Exception e) {
			throw createSOAPFaultException("Internal Error: " + e.getMessage(),
					false);
		}

		return null;
	}

	/*
	 * Convenience function used to generate a generic SOAPFaultException
	 */
	private SOAPFaultException createSOAPFaultException(String faultString,
			Boolean clientFault) {
		try {
			String faultCode = clientFault ? "Client" : "Server";
			SOAPFault fault = SOAPFactory.newInstance().createFault();
			fault.setFaultString(faultString);
			fault.setFaultCode(new QName(SOAPConstants.URI_NS_SOAP_ENVELOPE,
					faultCode));
			return new SOAPFaultException(fault);
		} catch (SOAPException e) {
			throw new RuntimeException(
					"Error creating SOAP Fault message, faultString: "
							+ faultString);
		}
	}
}
