/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.ds4ppilot.pep.contexthandler;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import org.herasaf.xacml.core.context.impl.RequestType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import gov.samhsa.ds4ppilot.pep.dto.XacmlRequest;
import gov.samhsa.ds4ppilot.pep.dto.XacmlResponse;
import gov.va.ehtac.ds4p.ws.EnforcePolicy.Xsparesource;
import gov.va.ehtac.ds4p.ws.EnforcePolicy.Xspasubject;
import gov.va.ehtac.ds4p.ws.EnforcePolicyResponse.Return;

/**
 * The Class ContextHandlerImpl.
 */
public class ContextHandlerImpl implements ContextHandler {

	/** The endpoint address. */
	private String endpointAddress;
	
	/** The request generator. */
	private RequestGenerator requestGenerator;
	
	/** The policy desicion point. */
	@Autowired
	private PolicyDecisionPoint policyDesicionPoint;
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ContextHandlerImpl.class);
	
	/**
	 * Instantiates a new context handler implementation.
	 *
	 * @param endpointAddress the endpoint address
	 */
	public ContextHandlerImpl(String endpointAddress) {
		this.endpointAddress = endpointAddress;
	}
	
	/**
	 * Instantiates a new context handler impl.
	 *
	 * @param policyDesicionPoint the policy desicion point
	 * @param requestGenerator the request generator
	 */
	public ContextHandlerImpl(PolicyDecisionPoint policyDesicionPoint, RequestGenerator requestGenerator) {
		this.policyDesicionPoint = policyDesicionPoint;
		this.requestGenerator = requestGenerator;
	}
	
	/* (non-Javadoc)
	 * @see gov.samhsa.ds4ppilot.pep.contexthandler.ContextHandler#enforcePolicy(gov.va.ehtac.ds4p.ws.EnforcePolicy.Xspasubject, gov.va.ehtac.ds4p.ws.EnforcePolicy.Xsparesource)
	 */
	@Override
	public Return enforcePolicy(Xspasubject xspasubject,
			Xsparesource xsparesource) {
		
//		TODO Below is existing code, disabled for demo
//		ContextHandlerWebServiceClient contextHandlerWebServiceClient = new ContextHandlerWebServiceClient(endpointAddress);
//        
//        Return result = contextHandlerWebServiceClient.enforcePolicy(xspasubject, xsparesource);
//        
//		return result;
		
		/////////////////////////////////////////////////////////////
		// TODO The code below is written for demo
		// map recepientSubjectNPI
		String recepientSubjectNPI = xspasubject.getSubjectId();
		if(recepientSubjectNPI.equals("Tao.Lin@direct.healthvault-stage.com") || recepientSubjectNPI.equals("joel_amoussou@direct.healthvault-stage.com"))
		{
			recepientSubjectNPI = "1083949036";
		}		
		// map intermediarySubjectNPI
		String intermediarySubjectNPI = "1174858088";		
		// map purposeOfUse
		String purposeOfUse = "TREAT";		
		// map resourceId
		String resourceId = xsparesource.getResourceId();
		if(xsparesource.getResourceId().equals("PUI100010060001"))
		{
			resourceId = "consent2share@outlook.com";
		}
		RequestType req = requestGenerator.generateRequest(recepientSubjectNPI, intermediarySubjectNPI, purposeOfUse, resourceId);
		policyDesicionPoint.deployPolicies(resourceId);
		XacmlResponse xacmlResponse = policyDesicionPoint.evaluateRequest(req);
		Return result = new Return();
		String pdpDecision = xacmlResponse.getPdpDecision();
		pdpDecision = pdpDecision.substring(0, 1).toUpperCase() + pdpDecision.substring(1).toLowerCase();
		result.setPdpDecision(pdpDecision);
		result.setPurposeOfUse(purposeOfUse);
		result.setResourceId(resourceId);
		setObligations(result, xacmlResponse.getPdpObligation());
		return result;
		/////////////////////////////////////////////////////////////
	}
	
	/////////////////////////////////////////////////////////////
	// TODO The code below is written for demo	
	private void setObligations(Return result, List<String> obligations)
	{
		Field field = null;
		try {
			field = result.getClass().getDeclaredField("pdpObligation");
		} catch (SecurityException e) {
			LOGGER.error(e.getMessage(),e);
		} catch (NoSuchFieldException e) {
			LOGGER.error(e.getMessage(),e);
		}
		makeAccessible(field);
		try {
			field.set(result,obligations);
		} catch (IllegalArgumentException e) {
			LOGGER.error(e.getMessage(),e);
		} catch (IllegalAccessException e) {
			LOGGER.error(e.getMessage(),e);
		}
	}
    private void makeAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers()) ||
            !Modifier.isPublic(field.getDeclaringClass().getModifiers()))
        {
            field.setAccessible(true);
        }
    }
	/////////////////////////////////////////////////////////////
	
	
	/* (non-Javadoc)
	 * @see gov.samhsa.ds4ppilot.pep.contexthandler.ContextHandler#enforcePolicy(gov.samhsa.ds4ppilot.pep.dto.XacmlRequest)
	 */
	@Override
	public XacmlResponse enforcePolicy(XacmlRequest xacmlRequest) {
		policyDesicionPoint.deployPolicies(xacmlRequest.getResourceId());
		RequestType req = requestGenerator.generateRequest(xacmlRequest.getRecepientSubjectNPI(), xacmlRequest.getIntermediarySubjectNPI(), xacmlRequest.getPurposeOfUse(), xacmlRequest.getResourceId());
		LOGGER.debug("policyDesicionPoint.evaluateRequest(req) is getting invoked");
		return policyDesicionPoint.evaluateRequest(req);
	}
	

	

}
