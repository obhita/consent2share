package gov.samhsa.consent2share.si.entitlementmanager;

import java.io.*;
import java.rmi.RemoteException;

import javax.annotation.PostConstruct;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.transport.http.HTTPConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.wso2.carbon.authenticator.stub.AuthenticationAdminStub;
import org.wso2.carbon.identity.entitlement.stub.EntitlementPolicyAdminServiceStub;
import org.wso2.carbon.identity.entitlement.stub.dto.PolicyDTO;

/**
 * Created by IntelliJ IDEA.
 * User: pushpalanka
 * Date: 3/24/11
 * Time: 11:00 AM
 * To change this template use File | Settings | File Templates.
 */

@Component
public class EntitlementAdminClient {
	
    private String authCookie;
    @Value("${WSO2ServerURL}")
    private String backendServerURL;
    private ConfigurationContext configCtx;
    @Value("${WSO2adminusername}")
    private String adminUsername;
    @Value("${WSO2adminpassword}")
    private String adminPassword;
    @Value("${WSO2remoteIp}")
    private String remoteIp;
    @Value("${SSLKeyLocation}")
    private String sslKeyLocation;
    
    private EntitlementPolicyAdminServiceStub oauth;
    
    @PostConstruct
    public void initialize() throws FileNotFoundException, IOException{
//    	Properties prop=new Properties();
//    	prop.load(new FileInputStream("src/main/resources/META-INF/spring/WSO2Server.properties"));
//    	backendServerURL=prop.getProperty("WSO2ServerURL");
//    	adminUsername=prop.getProperty("WSO2adminusername");
//    	adminPassword=prop.getProperty("WSO2adminpassword");
//    	remoteIp=prop.getProperty("WSO2remoteIp");
    	login();
    	oauth=setEntitlementPolicyAdminServiceParameters();
    	
    }
//    public EntitlementAdminClient() throws FileNotFoundException, IOException{
//    	Properties prop=new Properties();
//	prop.load(new FileInputStream("src/main/resources/META-INF/spring/WSO2Server.properties"));
//    	backendServerURL=prop.getProperty("WSO2ServerURL");
//    	adminUsername=prop.getProperty("WSO2adminusername");
//    	adminPassword=prop.getProperty("WSO2adminpassword");
//    	remoteIp=prop.getProperty("WSO2remoteIp");
//    	login();
//    	oauth=setEntitlementPolicyAdminServiceParameters();
//    	
//    }
    

    //Read a policy in a pointed out path
    private String takePolicyContentFromFile(String qualifiedFileName){

        FileInputStream fstream = null;
        String strLine = " ";
        String fileString= " ";
        try {
            fstream = new FileInputStream(qualifiedFileName);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br1 = new BufferedReader(new InputStreamReader(in));

            //Read File Line By Line
            while ((strLine = br1.readLine()) != null)   {
                fileString = fileString + strLine;
            }
            //Close the input stream
            in.close();
            // Print the content on the console
            System.out.println (fileString);
        }catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally {
            try {
                if (fstream != null) {
                    fstream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return fileString;
    }
    
    public void addPolicyFromFile(String qualifiedFileName){
    	String policyString=takePolicyContentFromFile(qualifiedFileName);
    	addPolicy(policyString);
    }

    


    public boolean addPolicy(String policyString){
        PolicyDTO policyDTO = new PolicyDTO();
        policyDTO.setPolicy(policyString.trim());
        policyDTO.setPolicyId("testingpolicyfromhao") ;

        try{
            oauth.addPolicy(policyDTO);
            System.out.println("Policy added.");
        }
        catch (RemoteException re){
             re.printStackTrace();
             return false;
        }
        catch(Exception ae){
            ae.printStackTrace();
            return false;
        }
        
        return true;

    }

//    public void readExistingPolicy(){
//        String policy = null;
//        PolicyDTO policyDTO = new PolicyDTO();
//        try{
//            policyDTO = oauth.getPolicy("sample-kmarket-blue-policy");
//        }
//        catch(RemoteException re){
//            re.printStackTrace();
//        }
//        catch(Exception ae){
//            ae.printStackTrace();
//
//        }
//        policy = policyDTO.getPolicy();
//        System.out.println("Read Policy:"+ policy);
//    }

    public void removePolicy(String policyId){
        PolicyDTO policyDTO = new PolicyDTO();
        policyDTO.setPolicyId(policyId);

        try{
            oauth.removePolicy(policyDTO);
            System.out.println("Policy removed.");
        }
        catch(RemoteException re){
            re.printStackTrace();
        }
        catch(Exception ae){
            ae.printStackTrace();
        }

    }
    
    private boolean login() {

    	//path to keystore and setup truststore properties that contains CA certifcates to trust.
//      String path = System.getProperty("user.dir").replace('\\','/') + "/src/main/resources/wso2carbon.jks";
      System.setProperty("javax.net.ssl.trustStore", sslKeyLocation);
      System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");
      try {
          configCtx = ConfigurationContextFactory.createConfigurationContextFromFileSystem(null, null);
      } catch (AxisFault axisFault) {
          axisFault.printStackTrace();
      }


      boolean auth = false;
      try {
//          if (authenticate(userName, password, ip)) {
              if(authenticate(adminUsername,adminPassword,remoteIp)){
              System.out.println("user logged in.");
              auth = true;
          } else {
              System.out.println("Not allowed to login.");
              auth = false;
          }
      } catch (Exception e) {
          e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }
      return auth;
  }
    
    private EntitlementPolicyAdminServiceStub setEntitlementPolicyAdminServiceParameters(){
        String serviceURL = null;
        ServiceClient client = null;
        Options option = null;
        EntitlementPolicyAdminServiceStub oauth = null;
        
        serviceURL = backendServerURL + "EntitlementPolicyAdminService";

        try{
            oauth = new EntitlementPolicyAdminServiceStub(configCtx, serviceURL);
        }
        catch (org.apache.axis2.AxisFault af){
            af.printStackTrace();
        }
        client = oauth._getServiceClient();
        option = client.getOptions();
        option.setManageSession(true);
        option.setProperty(org.apache.axis2.transport.http.HTTPConstants.COOKIE_STRING, authCookie);


        return oauth;
    }
    
    public boolean authenticate(String userName, String password, String remoteIp) throws Exception {
        String serviceURL = null;
        AuthenticationAdminStub authStub = null;
        boolean authenticate = false;

        serviceURL = backendServerURL + "AuthenticationAdmin";
        authStub = new AuthenticationAdminStub(configCtx, serviceURL);
        authStub._getServiceClient().getOptions().setManageSession(true);
        authenticate = authStub.login(userName, password, remoteIp);
        authCookie = (String) authStub._getServiceClient().getServiceContext().getProperty(
                HTTPConstants.COOKIE_STRING);
        return authenticate;
    }
}
