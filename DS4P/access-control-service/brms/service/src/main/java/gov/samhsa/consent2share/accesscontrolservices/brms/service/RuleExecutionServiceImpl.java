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
package gov.samhsa.consent2share.accesscontrolservices.brms.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.ws.rs.core.MediaType;

import gov.samhsa.consent2share.accesscontrolservices.brms.domain.ClinicalFact;
import gov.samhsa.consent2share.accesscontrolservices.brms.domain.FactModel;
import gov.samhsa.consent2share.accesscontrolservices.brms.domain.RuleExecutionContainer;
import gov.samhsa.consent2share.accesscontrolservices.brms.guvnor.GuvnorService;
import gov.samhsa.consent2share.schema.ruleexecutionservice.AssertAndExecuteClinicalFactsResponse;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.definition.rule.Rule;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.event.rule.AfterActivationFiredEvent;
import org.drools.event.rule.DefaultAgendaEventListener;
import org.drools.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The Class RuleExecutionServiceImpl.
 */
@Component
public class RuleExecutionServiceImpl implements RuleExecutionService {

	/** The knowledge session. */
	private StatefulKnowledgeSession session;
	
	private GuvnorService guvnorService;

	/** The knowledge base. */
	private KnowledgeBase knowledgeBase;

	/** The fired rule names. */
	private String firedRuleNames = "";

	/** The guvnor rest url. */
	private String guvnorRestUrl;
	
	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Instantiates a new rule execution service impl.
	 *
	 * @param guvnorRestUrl the guvnor rest url
	 */
	@Autowired
	public RuleExecutionServiceImpl(
			GuvnorService guvnorService) {
		super();
		this.guvnorService = guvnorService;
	}

	/**
	 * Creates the stateful knowledge session.
	 * 
	 * @param purposeOfUse
	 *            the purpose of use
	 * @param pdpObligations
	 *            the PDP obligations
	 * @param communityId
	 *            the community id
	 * @param messageId
	 *            the message id
	 */
	private void createStatefulKnowledgeSession(String purposeOfUse,
			List<String> pdpObligations, String communityId, String messageId) {

		try {
			KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
					.newKnowledgeBuilder();

			String casRules = guvnorService.getVersionedRulesFromPackage();

			kbuilder.add(ResourceFactory.newByteArrayResource(casRules.getBytes()),
					ResourceType.DRL);

			KnowledgeBuilderErrors errors = kbuilder.getErrors();
			if (errors.size() > 0) {
				for (KnowledgeBuilderError error : errors) {
					System.err.println(error);
				}
			}

			knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
			knowledgeBase.addKnowledgePackages(kbuilder.getKnowledgePackages());

			session = knowledgeBase.newStatefulKnowledgeSession();
			session.setGlobal("ruleExecutionContainer",
					new RuleExecutionContainer());
		} catch (Exception e) {
			// TODO: log exception
		}

	}

	/* (non-Javadoc)
	 * @see gov.samhsa.consent2share.accesscontrolservices.brms.RuleExecutionService#assertAndExecuteClinicalFacts(java.lang.String)
	 */
	@Override
	public AssertAndExecuteClinicalFactsResponse assertAndExecuteClinicalFacts(String factModelXmlString) {
		RuleExecutionContainer executionResponseContainer = null;
		FactModel factModel = new FactModel();
		AssertAndExecuteClinicalFactsResponse assertAndExecuteResponse = new
		AssertAndExecuteClinicalFactsResponse();

		StringWriter executionResponseContainerXML = new StringWriter();

		System.out.println("factModelXmlString: " + factModelXmlString);

		try {
			// unmarshall factmodel			
			factModel = unmarshallFromXml(
					FactModel.class, factModelXmlString);

			createStatefulKnowledgeSession(factModel.getXacmlResult()
					.getSubjectPurposeOfUse().getPurpose(), factModel
					.getXacmlResult().getPdpObligations(), factModel
					.getXacmlResult().getHomeCommunityId(), factModel
					.getXacmlResult().getMessageId());

			session.insert(factModel.getXacmlResult());
			for (ClinicalFact clinicalFact : factModel.getClinicalFactList()) {
				session.insert(clinicalFact);
			}

			session.addEventListener(new DefaultAgendaEventListener() {
				@Override
				public void afterActivationFired(AfterActivationFiredEvent event) {
					super.afterActivationFired(event);
					final Rule rule = event.getActivation().getRule();
					addRuleName(rule.getName());

				}
			});

			session.fireAllRules();
			
			logger.info(
					"Fired rules: {}...",	firedRuleNames);
			System.out.println("Fired rules: " + firedRuleNames);

			executionResponseContainer = (RuleExecutionContainer) session
					.getGlobal("ruleExecutionContainer");

			// Marshal rule execution response
			JAXBContext jaxbContext = JAXBContext.newInstance(RuleExecutionContainer.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty("com.sun.xml.bind.xmlDeclaration",
					Boolean.FALSE);
			marshaller.marshal(executionResponseContainer,
					executionResponseContainerXML);
		} catch (Exception e) {
			logger.error("Error occurred:", e);
		} finally {
			firedRuleNames = "";
			session.dispose();
		}
		
		 assertAndExecuteResponse
		 .setRuleExecutionResponseContainer(executionResponseContainerXML
		 .toString());
		 

		return assertAndExecuteResponse;
	}	

	/**
	 * Adds the rule name.
	 * 
	 * @param ruleName
	 *            the rule name
	 */
	private void addRuleName(String ruleName) {
		firedRuleNames = (!firedRuleNames.equals("")) ? firedRuleNames + ", "
				+ ruleName : ruleName;
	}

	private <T> T unmarshallFromXml(Class<T> clazz, String xml)
			throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Unmarshaller um = context.createUnmarshaller();
		ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes());
		return (T) um.unmarshal(input);
	}
}
