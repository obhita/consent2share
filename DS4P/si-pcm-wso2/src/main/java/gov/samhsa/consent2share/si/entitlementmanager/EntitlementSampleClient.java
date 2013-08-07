package gov.samhsa.consent2share.si.entitlementmanager;

import gov.samhsa.consent2share.si.entitlementmanager.EntitlementAdminClient;



public class EntitlementSampleClient {

    public static void main(String[] args) throws Exception {
        EntitlementAdminClient adminMethods = new EntitlementAdminClient();
        adminMethods.addPolicyFromFile("PolicySample.xml");
//        adminMethods.readExistingPolicy();
//        adminMethods.removePolicy("urn:sample:xacml:2.0:samplepolicy124");


    }
}
