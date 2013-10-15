package gov.samhsa.consent2share.pdp;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.herasaf.xacml.core.api.PDP;
import org.herasaf.xacml.core.api.PolicyRepository;
import org.herasaf.xacml.core.api.PolicyRetrievalPoint;
import org.herasaf.xacml.core.api.UnorderedPolicyRepository;
import org.herasaf.xacml.core.context.RequestMarshaller;
import org.herasaf.xacml.core.context.impl.RequestType;
import org.herasaf.xacml.core.context.impl.ResponseType;
import org.herasaf.xacml.core.context.impl.ResultType;
import org.herasaf.xacml.core.policy.Evaluatable;
import org.herasaf.xacml.core.policy.EvaluatableID;
import org.herasaf.xacml.core.policy.PolicyMarshaller;
import org.herasaf.xacml.core.policy.impl.AttributeAssignmentType;
import org.herasaf.xacml.core.policy.impl.ObligationType;
import org.herasaf.xacml.core.simplePDP.SimplePDPFactory;

public class Consent2ShareSimplePDPPrototype {

	private PDP simplePDP;
	public static void main(String[] args) {
		Consent2ShareSimplePDPPrototype consent2ShareSimplePDPPrototype=new Consent2ShareSimplePDPPrototype();
		consent2ShareSimplePDPPrototype.pdpInitMethodSimple();
		Evaluatable policy=null;
		Evaluatable policy2=null;
		try {
			InputStream is = new FileInputStream("samplePolicy.xml");
			policy=PolicyMarshaller.unmarshal(is);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			InputStream is = new FileInputStream("samplePolicy2.xml");
			policy2=PolicyMarshaller.unmarshal(is);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		InputStream requestis=null;
		RequestType request=null;
		try {
			requestis=new FileInputStream("samplePolicyRequest.xml");
			request=RequestMarshaller.unmarshal(requestis);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Evaluatable> policies=new ArrayList<Evaluatable>();
		policies.add(policy);
		policies.add(policy2);
		consent2ShareSimplePDPPrototype.deployPolicies(policies);
		
		consent2ShareSimplePDPPrototype.evaluateRequest(request);
	}
	
	public void pdpInitMethodSimple() {
		
	        simplePDP = SimplePDPFactory.getSimplePDP();
	}
	 
	public void deployPolicy(Evaluatable policy) {
		 
        /**
         * Retrieve the policy repository and deploy the policy on it.
         */
        PolicyRetrievalPoint repo = simplePDP.getPolicyRepository();
        UnorderedPolicyRepository repository = (UnorderedPolicyRepository) repo; //An OrderedPolicyRepository could be used as well
        repository.deploy(policy);
    }
	
	public void deployPolicies(List<Evaluatable> policies) {
        /**
         * Deploy mulitple policies on the policy repository.
         */
        PolicyRetrievalPoint repo = simplePDP.getPolicyRepository();
        UnorderedPolicyRepository repository = (UnorderedPolicyRepository) repo; //An OrderedPolicyRepository could be used as well
        repository.deploy(policies);
    }
	
	public void undeployPolicy(Evaluatable policy) {
		 
        /**
         * Retrieve the policy repository and undeploy the policy by its ID.
         */
        PolicyRepository repo = (PolicyRepository) simplePDP.getPolicyRepository();
        repo.undeploy(policy.getId());
 
    }
 
    public void undeployPolicies(List<EvaluatableID> policyIds) {
        /**
         * Undeploy multiple policies on the policy repository.
         */
        PolicyRepository repo = (PolicyRepository) simplePDP.getPolicyRepository();
        repo.undeploy(policyIds);
    }
    public void evaluateRequest(RequestType request) {
    	 
        /**
         * Evaluate the request using the simplePDP and retrieve the response
         * from the PDP.
         */
        ResponseType response = simplePDP.evaluate(request);
        for (ResultType r:response.getResults()){
        	System.out.println(r.getDecision().toString());
        	if (r.getObligations()!=null)
        	for (ObligationType o:r.getObligations().getObligations())
        		for (AttributeAssignmentType a:o.getAttributeAssignments())
        			for(Object c:a.getContent())
        				System.out.println("With Obligation: "+c);
        }
    }


}
