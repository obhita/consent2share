/*
 * This software is being provided per FARS 52.227-14 Rights in Data - General.
 * Any redistribution or request for copyright requires written consent by the
 * Department of Veterans Affairs.
*/

package gov.va.ds4p.security.saml;

import com.sun.xml.wss.XWSSecurityException;
import com.sun.xml.wss.impl.callback.SAMLAssertionValidator;
import com.sun.xml.wss.impl.callback.SAMLAssertionValidator.SAMLValidationException;
import com.sun.xml.wss.saml.Evidence;
import com.sun.xml.wss.saml.NameID;
import com.sun.xml.wss.saml.SAMLAssertionFactory;
import com.sun.xml.wss.saml.SAMLException;
import com.sun.xml.wss.saml.Subject;
import com.sun.xml.wss.saml.SubjectConfirmation;
import com.sun.xml.wss.saml.assertion.saml20.jaxb20.Assertion;
import com.sun.xml.wss.saml.internal.saml20.jaxb20.AttributeStatementType;
import com.sun.xml.wss.saml.internal.saml20.jaxb20.AttributeType;
import com.sun.xml.wss.saml.internal.saml20.jaxb20.ConditionsType;
import com.sun.xml.wss.saml.internal.saml20.jaxb20.NameIDType;
import com.sun.xml.wss.saml.internal.saml20.jaxb20.StatementAbstractType;
import com.sun.xml.wss.saml.internal.saml20.jaxb20.SubjectType;
import com.sun.xml.wss.saml.util.SAMLUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
//import javax.security.auth.Subject;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.oasis.names.tc.xspa.v2.PolicyEnforcementObject;
import org.oasis.names.tc.xspa.v2.XspaResource;
import org.oasis.names.tc.xspa.v2.XspaSubject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import gov.va.ds4p.cas.constants.DS4PConstants;
import gov.va.ds4p.security.xacml.XACMLContextHandler;

/**
 *
 * @author Duane DeCouteau
 */
public class SAMLValidator implements SAMLAssertionValidator {
    
    private Element samlAssertion;
    
    private SimpleDateFormat ds = new SimpleDateFormat("MM/dd/yyyy");
    // todo move this out into config file
    
    private XACMLContextHandler handler = new XACMLContextHandler();
    
    public void validate(Element arg0) throws SAMLValidationException {
        try {
            samlAssertion = arg0;
            validate();
        }
        catch (Exception ex) {
            throw new SAMLValidationException("Invalid Element Assertion"); 
        }
    }

    public void validate(XMLStreamReader arg0) throws SAMLValidationException {
       try {
           samlAssertion = SAMLUtil.createSAMLAssertion(arg0);
           validate();
       }
       catch (Exception ex) {
          throw new SAMLValidationException("Invalid Stream Assertion");  
       }
    }

     private void validate() throws SAMLValidationException {
         try {         
           SAMLAssertionFactory samlFactory = SAMLAssertionFactory.newInstance(SAMLAssertionFactory.SAML2_0);
           Assertion assertion = (com.sun.xml.wss.saml.assertion.saml20.jaxb20.Assertion)samlFactory.createAssertion(samlAssertion);
           
           System.out.println("==== Begin XSPA-SAML Network ACD ====");
           String msgId = assertion.getAssertionID();
           //validate date range
           ConditionsType cond = assertion.getConditions();
           XMLGregorianCalendar dbefore = cond.getNotBefore();
           XMLGregorianCalendar dafter = cond.getNotOnOrAfter();
           Calendar bcal = dbefore.toGregorianCalendar();
           Calendar acal = dafter.toGregorianCalendar();
           
           Date b = bcal.getTime();
           Date a = acal.getTime();
           Date today = new Date();
           if (today.after(b) && today.before(a)) {
            //   if (today.after(a)) {
               //ok
               System.out.println("====  NoteBeforeDate: "+ds.format(b)+" NotOnOrAfterDate: "+ds.format(a)+" Today: "+ds.format(today));
           }
           else {
               System.out.println("XSPASAMLValidator:validateDate: Date Range Failed ");
               System.out.println("XSPASAMLValidator:validateDate: BeforeDate: "+ds.format(b)+" AfterDate: "+ds.format(a)+" Today: "+ds.format(today));               
               throw new SAMLValidationException("Failed Date Range Check");
           }
           
           //validate issuer          
           String samlissuer = assertion.getSamlIssuer();
           if (samlissuer == null) {
               System.out.println("==== SAML Issuer can not be blank ====");
               System.out.println("==== End XSPA-SAML Network ACD ====");
               throw new SAMLValidationException("SAML Issuer can not be blank");
           }
           else {
               System.out.println("==== SAML Issuer: "+samlissuer+" ====");
           }
           
           NameIDType name = assertion.getIssuer();
           if (name == null) {
               System.out.println("==== Name ID can not be blank ====");
               System.out.println("==== End XSPA-SAML Network ACD ====");
               throw new SAMLValidationException("Name ID can not be blank");               
           }
           else {
               System.out.println("==== Name ID "+name.getValue()+" =====");
           }
           
           System.out.println("==== End XSPA-SAML Network ACD ====");
                      
           System.out.println("==== Begin Service Provider ACD ====");
           
           //for testing purposes
           /*NameID nameId = samlFactory.createNameID(name.getValue(), null, null);
           SubjectConfirmation conf = samlFactory.createSubjectConfirmation(nameId, "urn:oasis:names:tc:SAML:2.0:cm:bearer");
           Subject subject = samlFactory.createSubject(nameId, conf);*/ 
           
           //detail stuff needed by PEP
           XspaResource xresource = new XspaResource();
           XspaSubject xsubject = new XspaSubject();
           xsubject.setMessageId(msgId);
           //Resultant policy decisions seperate in case we choose to log differently
           PolicyEnforcementObject policyReturn = new PolicyEnforcementObject();
           List<StatementAbstractType> las = assertion.getStatementOrAuthnStatementOrAuthzDecisionStatement();
           Iterator iter = las.iterator();
           while (iter.hasNext()) {
               AttributeStatementType sat = (AttributeStatementType)iter.next();
               List satlist = sat.getAttributeOrEncryptedAttribute();
               Iterator siter = satlist.iterator();
               while (siter.hasNext()) {
                   AttributeType m = (AttributeType)siter.next();
                   String rname = m.getName();
                   System.out.println("==== Attribute "+rname+" ====");
                   List val = m.getAttributeValue();
                   Iterator viter = val.iterator();
                   while(viter.hasNext()) {
                        String sval = (String)viter.next();
                        //subject object
                        if (rname.equals(DS4PConstants.SUBJECT_ID_NS)) xsubject.setSubjectId(sval);
                        if (rname.equals(DS4PConstants.SUBJECT_STRUCTURED_ROLE_NS)) xsubject.getSubjectStructuredRole().add(sval);
                        if (rname.equals(DS4PConstants.SUBJECT_PURPOSE_OF_USE_NS)) xsubject.setSubjectPurposeOfUse(sval);
                        if (rname.equals(DS4PConstants.SUBJECT_ORGANIZATION_NS)) xsubject.setOrganization(sval);
                        if (rname.equals(DS4PConstants.SUBJECT_ORGANIZATION_ID_NS)) xsubject.setOrganizationId(sval);                        
                        if (rname.equals(DS4PConstants.SUBJECT_LOCALITY_NS)) xsubject.setSubjectLocality(sval);
                        if (rname.equals(DS4PConstants.SUBJECT_SENSITIVITY_PRIVILEGES)) xsubject.getSubjectPermissions().add(sval);                       
                                                
                        //resource object
                        if (rname.equals(DS4PConstants.RESOURCE_ID_NS)) xresource.setResourceId(sval);
                        if (rname.equals(DS4PConstants.RESOURCE_TYPE_NS)) xresource.setResourceType(sval);
                        if (rname.equals(DS4PConstants.RESOURCE_ACTION_ID_NS)) xresource.setResourceAction(sval);
                        if (rname.equals(DS4PConstants.RESOURCE_NWHIN_SERVICE_NS)) xresource.setResourceName(sval);

                        

                        System.out.println("==== Value "+sval+" ====");
                   }
               }
               
                   try {
                       policyReturn = handler.enforcePolicy(xsubject, xresource);

                       //until we have a PDP
                       String authdecision = policyReturn.getPdpDecision();
                       //String authdecision = "Permit";

                       if (!authdecision.equals("Permit")) {
                           //just like nwhin we throw and exception on deny....
                           Exception ex = new Exception(policyReturn.getPdpResponse());
                           throw new SAMLValidationException(ex);
                       }
                       else {
                           String decision = "Permit";
                           List actions = new ArrayList();
                           Iterator obligationsIterator = policyReturn.getPdpObligation().iterator();
                           while (obligationsIterator.hasNext()) {
                               String obl = (String)obligationsIterator.next();
                               actions.add(obl);
                           }
                           Evidence evidence = null;
                           
                       }
                   }
                   catch (Exception ex) {
                       System.err.println("XSPAServiceProvider:XSPASAMLValidator "+ex.getMessage());
                       throw new SAMLValidationException(ex);
                   }
           }
       } 
       catch (XWSSecurityException ex) {
            System.err.println(ex.getMessage());
            throw new SAMLValidationException(ex);
       } 
       catch (SAMLException ex) {
           System.err.println(ex.getMessage());
           throw new SAMLValidationException(ex);
       }
    }

    private void logRequest(XspaSubject xsubject, XspaResource xresource, Element samlAssertion) {
        // log now from context handler
    }
    private void logAuthRequestAndDecision(PolicyEnforcementObject orgacd, PolicyEnforcementObject patientacd, String resourceid, String resourcename) {
        // log now from context handler
    }

    private String dumpNodeToString(Node node) {
        String res = "";
        return res;
    }

    public void validate(Element elmnt, Map map, Subject sbjct) throws SAMLValidationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
