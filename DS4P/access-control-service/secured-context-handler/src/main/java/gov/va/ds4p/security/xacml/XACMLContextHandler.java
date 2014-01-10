/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ds4p.security.xacml;

import com.jerichosystems.esds.evaluation.xacml.context.ActionType;
import com.jerichosystems.esds.evaluation.xacml.context.AttributeType;
import com.jerichosystems.esds.evaluation.xacml.context.AttributeValueType;
import com.jerichosystems.esds.evaluation.xacml.context.DecisionType;
import com.jerichosystems.esds.evaluation.xacml.context.EnvironmentType;
import com.jerichosystems.esds.evaluation.xacml.context.RequestType;
import com.jerichosystems.esds.evaluation.xacml.context.ResourceType;
import com.jerichosystems.esds.evaluation.xacml.context.ResponseType;
import com.jerichosystems.esds.evaluation.xacml.context.ResultType;
import com.jerichosystems.esds.evaluation.xacml.context.StatusType;
import com.jerichosystems.esds.evaluation.xacml.context.SubjectType;
import com.jerichosystems.esds.evaluation.xacml.policy.ObligationsType;
import com.jerichosystems.esds.evaluation.xacml.policy.PolicySetType;
import com.jerichosystems.services.xacmlpolicyevaluationservice._1.XACMLPolicyEvaluationService;
import com.jerichosystems.services.xacmlpolicyevaluationservice._1.XACMLPolicyEvaluationServiceService;

import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.ds4ppilot.schema.pep.RegisteryStoredQueryResponse;
import gov.samhsa.ds4ppilot.schema.pep.RetrieveDocumentSetResponse;
import gov.va.ds4p.cas.constants.DS4PConstants;
import gov.va.ds4p.cas.providers.ClinicalDocumentProvider;
import gov.va.ds4p.cas.providers.VocabularyProvider;
import gov.va.ds4p.policy.reference.ActInformationSensitivityPolicy;
import gov.va.ds4p.policy.reference.ActUSPrivacyLaw;
import gov.va.ds4p.policy.reference.ApplicableObligationPolicies;
import gov.va.ds4p.policy.reference.ApplicableRefrainPolicies;
import gov.va.ds4p.policy.reference.ApplicableSensitivityCodes;
import gov.va.ds4p.policy.reference.ApplicableUSLaws;
import gov.va.ds4p.policy.reference.ObligationPolicy;
import gov.va.ds4p.policy.reference.RefrainPolicy;
import gov.va.ds4p.registry.xdsbregistry.XdsbRegistry;
import gov.va.ds4p.registry.xdsbregistry.XdsbRegistryImpl;
import gov.va.ds4p.repository.xdsbrepository.XdsbRepository;
import gov.va.ehtac.ds4p.ws.EnforcePolicyResponse.Return;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest.DocumentRequest;
import ihe.iti.xds_b._2007.RetrieveDocumentSetResponse.DocumentResponse;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryRequest;
import oasis.names.tc.ebxml_regrep.xsd.query._3.AdhocQueryResponse;
import oasis.names.tc.ebxml_regrep.xsd.query._3.ResponseOptionType;
import oasis.names.tc.ebxml_regrep.xsd.rim._3.*;

import org.apache.xml.security.utils.Base64;
import org.hl7.v3.CD;
import org.hl7.v3.ED;
import org.hl7.v3.POCDMT000040Act;
import org.hl7.v3.POCDMT000040ClinicalDocument;
import org.hl7.v3.POCDMT000040Component2;
import org.hl7.v3.POCDMT000040Component3;
import org.hl7.v3.POCDMT000040Entry;
import org.hl7.v3.POCDMT000040EntryRelationship;
import org.hl7.v3.POCDMT000040ObservationMedia;
import org.hl7.v3.POCDMT000040Section;
import org.hl7.v3.POCDMT000040StructuredBody;
import org.oasis.names.tc.xspa.v2.PolicyEnforcementObject;
import org.oasis.names.tc.xspa.v2.XacmlResultType;
import org.oasis.names.tc.xspa.v2.XacmlStatusDetailType;
import org.oasis.names.tc.xspa.v2.XacmlStatusType;
import org.oasis.names.tc.xspa.v2.XspaResource;
import org.oasis.names.tc.xspa.v2.XspaSubject;

/**
 * 
 * @author Duane DeCouteau
 */
public class XACMLContextHandler {

	// for testing purposes create config file later....
	private String pdpEndpoint = "http://75.145.119.97/XACMLPolicyEvaluationService/soap?wsdl";
	
	/*  Modified by Burak Tasel 2/4/2013 - Added needed endpoints */
	/* begin */
	private String auditEndpoint = "http://174.78.146.228:8080/DS4PACSServices/DS4PAuditService?wsdl";
	private String xdsbRepositoryEndpoint = "http://feijboss01:8080/axis2/services/xdsrepositoryb";	 
	private String xdsbRegistryEndpoint = "http://feijboss01:8080/axis2/services/xdsregistryb";
	/* end */
	
	private String homeCommunityId = "2.16.840.1.113883.3.467";
	private String repositoryId = "1.3.6.1.4.1.21367.2010.1.2.1040";

	private PolicyEnforcementObject decisionObject;
	private XspaSubject currSubject;
	private XspaResource currResource;
	private String messageId;

	// xacml objects
	private RequestType query = new RequestType();
	private PolicySetType policy = new PolicySetType();
	private ResponseType response = new ResponseType();

	/** The xdsbRepository. */
	private XdsbRepository xdsbRepository;

	/** The xdsbRegistry. */
	private XdsbRegistry xdsbRegistry;

	// patient consent id
	private String documentId;
	private static final String CONSENT_NOTE_TYPE = "Consent Notes";
	private static final String CONSENT_MEDIA_TYPE = "application/xacml+xml";

	private String patientAuthorization = "";
	private Date requeststart;
	private Date requestcomplete;

	// Clinical Document Transforms
	private ClinicalDocumentProvider cProvider = new ClinicalDocumentProvider();
	// Vocabulary
	private VocabularyProvider vProvider = new VocabularyProvider();

	private static DatatypeFactory dateFac;
	static {
		try {
			dateFac = DatatypeFactory.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public XACMLContextHandler() {

	}

	public PolicyEnforcementObject enforcePolicy(XspaSubject subject,
			XspaResource resource) {
		decisionObject = new PolicyEnforcementObject();
		this.currResource = resource;
		this.currSubject = subject;
		this.messageId = subject.getMessageId();
		this.requeststart = new Date();

        
                //initialize interactions
                this.response = new ResponseType();
                this.query = new RequestType();
                this.policy = new PolicySetType();
        
        //retrieve patient consent
		getAndProcessPatientConsent();

		// create xacml request
		setXACMLRequestAuthorization();

		try {
			XACMLPolicyEvaluationServiceService service = new XACMLPolicyEvaluationServiceService();
			XACMLPolicyEvaluationService port = service
					.getXACMLPolicyEvaluationServicePort();
			((BindingProvider) port).getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, pdpEndpoint);

			response = port.evaluatePolicySet(query, policy);

			processDecision();
			if (DS4PConstants.PERMIT.equals(decisionObject.getPdpDecision())
					&& currResource.getResourceName()
							.equals("DocumentRetrieve")) {
				processObligationRequirements();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		this.requestcomplete = new Date();
		//logEvent();
		return decisionObject;
	}

	private void processDecision() {
		ResultType xResult = response.getResult().get(0);
		DecisionType d = xResult.getDecision();
		StatusType s = xResult.getStatus();
		ObligationsType o = xResult.getObligations();

		XacmlResultType x = new XacmlResultType();
		x.setXacmlResultTypeDecision(d.value());
		x.setXacmlResultTypeResourceId(xResult.getResourceId());

		XacmlStatusType xStat = new XacmlStatusType();
		xStat.setXacmlStatusCodeType(s.getStatusCode().getValue());
		xStat.setXacmlStatusMessage(s.getStatusMessage());
		XacmlStatusDetailType detail = new XacmlStatusDetailType();

		if (s.getStatusDetail() != null) {
			List<Object> sObjs = s.getStatusDetail().getAny();
			Iterator sObjsIter = sObjs.iterator();
			while (sObjsIter.hasNext()) {
				String obj = (String) sObjsIter.next();
				detail.getXacmlStatusDetail().add(obj);
			}
		}

		decisionObject.setPdpDecision(d.value());
		decisionObject.setPdpStatus("ok");
		// the following is a work around for XACML 2.0
		decisionObject.getPdpObligation().clear();
		decisionObject.setPdpRequest(dumpRequestToString());
		decisionObject.setPdpResponse(dumpResponseToString(xResult));
		decisionObject
				.setRequestTime(convertDateToXMLGregorianCalendar(requeststart));
		decisionObject
				.setResponseTime(convertDateToXMLGregorianCalendar(new Date()));
		decisionObject.setResourceName(currResource.getResourceName());
		decisionObject.setResourceId(currResource.getResourceId());
		decisionObject.setHomeCommunityId(currSubject.getSubjectLocality());
                decisionObject.setMessageId(currSubject.getMessageId());
	}

	private void getAndProcessPatientConsent() {
		try {
			String cdaR2 = getXDSbPatientConsent();
			POCDMT000040ClinicalDocument consentDocument = cProvider
					.createClinicalDocumentFromXMLString(cdaR2);
			processClinicalDocument(consentDocument);
		} catch (Exception ex) {
			throw new DS4PException(ex.getMessage(), ex);
		}

	}

	private void processClinicalDocument(POCDMT000040ClinicalDocument doc) {
		// make this more eloquent later...
		try {
			POCDMT000040Component2 comp2 = doc.getComponent();
			POCDMT000040StructuredBody body = comp2.getStructuredBody();
			POCDMT000040Component3 comp3 = body.getComponent().get(0);
			POCDMT000040Section section = comp3.getSection();
			POCDMT000040Entry entry = section.getEntry().get(0);
			POCDMT000040Act act = entry.getAct();
			List<POCDMT000040EntryRelationship> relationships = act
					.getEntryRelationship();
			Iterator iterRelationships = relationships.iterator();
			// determine if this is a negative disclosure
			while (iterRelationships.hasNext()) {
				POCDMT000040EntryRelationship rel = (POCDMT000040EntryRelationship) iterRelationships
						.next();
				POCDMT000040Act rAct = rel.getAct();
                                if (rAct != null) {
                                    CD cd = rAct.getCode();
                                    if (cd.getCode().equals("DISCLOSE")) {
					if (rAct.isNegationInd()) {
						patientAuthorization = "Deny";
					} else {
						patientAuthorization = "Disclose";
					}
                                    }
                                }
				try {
					POCDMT000040ObservationMedia obs = rel
							.getObservationMedia();
					if (obs != null) {
						ED eVal = obs.getValue();
						if (CONSENT_MEDIA_TYPE.equals(eVal.getMediaType())) {
							String pConsent = (String) eVal.getContent().get(0);
							policy = getXACMLPolicySetFromString(pConsent);
						}
					}
				} catch (Exception px) {
					// just ignore as many will be null
				}
			}
		} catch (Exception ex) {
			throw new DS4PException(ex.getMessage(), ex);
		}
	}

	private String getXDSbPatientConsent() {
		String cdaR2 = "";
		// get metadata
		try {
			RegisteryStoredQueryResponse resp = registeryStoredQueryRequest();
			AdhocQueryResponse adhoc = getQueryResponse(resp.getReturn());
			processMetaData(adhoc.getRegistryObjectList());
			RetrieveDocumentSetResponse resp2 = retrieveDocumentSetRequest(documentId);
			String currentDocument = resp2.getReturn();
			ihe.iti.xds_b._2007.RetrieveDocumentSetResponse xdsbRetrieveDocumentSetResponse = unmarshallFromXml(
					ihe.iti.xds_b._2007.RetrieveDocumentSetResponse.class,
					currentDocument);
			DocumentResponse documentResponse = xdsbRetrieveDocumentSetResponse
					.getDocumentResponse().get(0);
			byte[] processDocBytes = documentResponse.getDocument();
			cdaR2 = new String(processDocBytes);
		} catch (Exception ex) {
			throw new DS4PException(ex.getMessage(), ex);
		}
		return cdaR2;
	}

	private AdhocQueryResponse getQueryResponse(String mxml) {
		AdhocQueryResponse obj = null;
		try {
			JAXBContext context = JAXBContext
					.newInstance(AdhocQueryResponse.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			StringReader sr = new StringReader(mxml);

			Object o = unmarshaller.unmarshal(sr);
			obj = (AdhocQueryResponse) o;

		} catch (Exception e) {
			// log.warn("",e);
			e.printStackTrace();
		}
		return obj;

	}

	private void processMetaData(RegistryObjectListType objList) {
		List<JAXBElement<? extends IdentifiableType>> extrinsicObjects = objList
				.getIdentifiable();

		Date theLatestConsentDocumentEntryCreationTime = new Date(
				Long.MIN_VALUE);
		
		if (extrinsicObjects != null && extrinsicObjects.size() > 0) {
			for (JAXBElement<? extends IdentifiableType> jaxb : extrinsicObjects) {

				if (jaxb.getValue() instanceof ExtrinsicObjectType) {
					ExtrinsicObjectType extrinsicObject = (ExtrinsicObjectType) jaxb
							.getValue();

					if (extrinsicObject != null) {
						if (CONSENT_NOTE_TYPE
								.equals(extractDocumentType(extrinsicObject))) {
							
							/*  Modified by Burak Tasel 2/4/2013 - Extract most recent consent documentId */
							/* begin */
							Date creationDateTime = extractcreationDateTime(extrinsicObject);
							
							if (creationDateTime.after(theLatestConsentDocumentEntryCreationTime)) {
								theLatestConsentDocumentEntryCreationTime = creationDateTime;
								documentId = extractDocumentID(extrinsicObject);								
							}
							/* end */
						}
					}
				}
			}
		}
	}

	private PolicySetType getXACMLPolicySetFromString(String policy) {
		PolicySetType res = null;

		try {
			// this is a base65 value so decode
			String policyString = new String(Base64.decode(policy));
			JAXBContext context = JAXBContext.newInstance(PolicySetType.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			StringReader sr = new StringReader(policyString);

			Object o = unmarshaller.unmarshal(sr);
                        JAXBElement element = (JAXBElement)o;
                        res = (PolicySetType)element.getValue();

		} catch (Exception ex) {
			throw new DS4PException(ex.getMessage(), ex);
		}

		return res;
	}

	private void setXACMLRequestAuthorization() {

		RequestType rt = new RequestType();
		SubjectType st = new SubjectType();
		ActionType act = new ActionType();
		ResourceType resource = new ResourceType();
		EnvironmentType environment = new EnvironmentType();
		// Subject Information Identifier
		AttributeType at = new AttributeType();
		at.setAttributeId(DS4PConstants.SUBJECT_ID_NS);
		at.setDataType(DS4PConstants.STRING);
		AttributeValueType avt = new AttributeValueType();
		avt.getContent().add(currSubject.getSubjectId());
		at.getAttributeValue().add(avt);
		st.getAttribute().add(at);
		// Subject Purpose of Use
		at = new AttributeType();
		at.setAttributeId(DS4PConstants.SUBJECT_PURPOSE_OF_USE_NS);
		at.setDataType(DS4PConstants.STRING);
		avt = new AttributeValueType();
		avt.getContent().add(currSubject.getSubjectPurposeOfUse());
		at.getAttributeValue().add(avt);
		st.getAttribute().add(at);
		// Subject Home Community Identifier
		at = new AttributeType();
		at.setAttributeId(DS4PConstants.SUBJECT_LOCALITY_NS);
		at.setDataType(DS4PConstants.STRING);
		avt = new AttributeValueType();
		avt.getContent().add(currSubject.getSubjectLocality());
		at.getAttributeValue().add(avt);
		st.getAttribute().add(at);
		// Subject ROLE
		at = new AttributeType();
		at.setAttributeId(DS4PConstants.SUBJECT_STRUCTURED_ROLE_NS);
		at.setDataType(DS4PConstants.STRING);
		avt = new AttributeValueType();
		avt.getContent().add(currSubject.getSubjectStructuredRole().get(0));
		at.getAttributeValue().add(avt);
		st.getAttribute().add(at);

		// the next subject attributes may be informational only as our focus on
		// nwhin is homeCommunity...
		at = new AttributeType();
		at.setAttributeId(DS4PConstants.SUBJECT_ORGANIZATION_NS);
		at.setDataType(DS4PConstants.STRING);
		avt = new AttributeValueType();
		avt.getContent().add(currSubject.getOrganization());
		at.getAttributeValue().add(avt);
		st.getAttribute().add(at);
		// this is location-clinic placeholder
		at = new AttributeType();
		at.setAttributeId(DS4PConstants.SUBJECT_ORGANIZATION_ID_NS);
		at.setDataType(DS4PConstants.STRING);
		avt = new AttributeValueType();
		avt.getContent().add(currSubject.getOrganizationId());
		at.getAttributeValue().add(avt);
		st.getAttribute().add(at);

		// add sensitivity permissions
		if (!currSubject.getSubjectPermissions().isEmpty()) {
			Iterator iter = currSubject.getSubjectPermissions().iterator();
			at = new AttributeType();
			at.setAttributeId(DS4PConstants.SUBJECT_SENSITIVITY_PRIVILEGES);
			at.setDataType(DS4PConstants.STRING);
			while (iter.hasNext()) {
				String s = (String) iter.next();
				avt = new AttributeValueType();
				avt.getContent().add(s);
				at.getAttributeValue().add(avt);
			}
			if (!at.getAttributeValue().isEmpty())
				st.getAttribute().add(at);
		}

		// Set Resource Attributes - Organization and Region
		at = new AttributeType();
		at.setAttributeId(DS4PConstants.RESOURCE_NWHIN_SERVICE_NS);
		at.setDataType(DS4PConstants.STRING);
		avt = new AttributeValueType();
		avt.getContent().add(currResource.getResourceName());
		at.getAttributeValue().add(avt);
		resource.getAttribute().add(at);

		at = new AttributeType();
		at.setAttributeId(DS4PConstants.RESOURCE_LOCALITY_NS);
		at.setDataType(DS4PConstants.STRING);
		avt = new AttributeValueType();
		avt.getContent().add(homeCommunityId);
		at.getAttributeValue().add(avt);
		resource.getAttribute().add(at);

		// Kairon Patient Consent
		at = new AttributeType();
		at.setAttributeId(DS4PConstants.MITRE_PATIENT_AUTHORIZATION);
		at.setDataType(DS4PConstants.STRING);
		avt = new AttributeValueType();
		avt.getContent().add(patientAuthorization);
		at.getAttributeValue().add(avt);
		resource.getAttribute().add(at);

		// set request action
		at = new AttributeType();
		at.setAttributeId(DS4PConstants.RESOURCE_ACTION_ID_NS);
		at.setDataType(DS4PConstants.STRING);
		avt = new AttributeValueType();
		avt.getContent().add("Execute");
		at.getAttributeValue().add(avt);
		act.getAttribute().add(at);

		// set environment
		at = new AttributeType();
		at.setAttributeId(DS4PConstants.RESOURCE_LOCALITY_NS);
		at.setDataType(DS4PConstants.STRING);
		avt = new AttributeValueType();
		avt.getContent().add(homeCommunityId);
		at.getAttributeValue().add(avt);
		environment.getAttribute().add(at);

		query.getSubject().add(st);
		query.getResource().add(resource);
		query.setAction(act);
		query.setEnvironment(environment);

	}

	private String extractDocumentType(ExtrinsicObjectType extrinsicObject) {
		ClassificationType classification = extractClassification(
				extrinsicObject,
				"urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a");

		String documentTypeCode = classification.getName().getLocalizedString()
				.get(0).getValue();
		return documentTypeCode;
	}

	private ClassificationType extractClassification(
			ExtrinsicObjectType extrinsicObject, String classificationScheme) {
		ClassificationType classification = null;

		for (ClassificationType classificationItem : extrinsicObject
				.getClassification()) {
			if (classificationItem != null
					&& classificationItem.getClassificationScheme()
							.contentEquals(classificationScheme)) {
				classification = classificationItem;
				break;
			}
		}

		return classification;
	}

	private String extractDocumentID(ExtrinsicObjectType extrinsicObject) {
		String documentID = null;

		ExternalIdentifierType identifier = extractIndentifierType(
				extrinsicObject,
				"urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab");

		if (identifier != null) {
			documentID = identifier.getValue();
		}

		return documentID;
	}
	
	/*  Modified by Burak Tasel 2/4/2013 - Extract most recent consent documentId */
	/* begin */
	private Date extractcreationDateTime(ExtrinsicObjectType extrinsicObjectType) {
		Date creationDateTime = new Date(Long.MIN_VALUE);	

		for (SlotType1 slotType1 : extrinsicObjectType.getSlot()) {
			if (slotType1.getName().equals("creationTime")) {
				String datetimeString = slotType1.getValueList().getValue()
						.get(0);
				int lengthOfDateTimeString = datetimeString.length();

				int year = lengthOfDateTimeString >= 4 ? Integer
						.parseInt(datetimeString.substring(0, 4)) : 0;
				int month = lengthOfDateTimeString >= 6 ? Integer
						.parseInt(datetimeString.substring(4, 6)) : 0;
				int day = lengthOfDateTimeString >= 8 ? Integer
						.parseInt(datetimeString.substring(6, 8)) : 0;
				int hour = lengthOfDateTimeString >= 10 ? Integer
						.parseInt(datetimeString.substring(8, 10)) : 0;
				int minute = lengthOfDateTimeString >= 12 ? Integer
						.parseInt(datetimeString.substring(10, 12)) : 0;
				int second = lengthOfDateTimeString >= 14 ? Integer
						.parseInt(datetimeString.substring(12, 14)) : 0;

				GregorianCalendar gregorianCalendar = new GregorianCalendar(
						year, month, day, hour, minute, second);

				creationDateTime = gregorianCalendar.getTime();				
			}
		}

		return creationDateTime;
	}
	/* end */

	private ExternalIdentifierType extractIndentifierType(
			ExtrinsicObjectType extrinsicObject, String identificationScheme) {
		ExternalIdentifierType identifier = null;

		for (ExternalIdentifierType identifierItem : extrinsicObject
				.getExternalIdentifier()) {
			if (identifierItem != null
					&& identifierItem.getIdentificationScheme().contentEquals(
							identificationScheme)) {
				identifier = identifierItem;
				break;
			}
		}

		return identifier;
	}

	private RegisteryStoredQueryResponse registeryStoredQueryRequest() {
		AdhocQueryRequest registryStoredQuery = new AdhocQueryRequest();

		ResponseOptionType responseOptionType = new ResponseOptionType();
		responseOptionType.setReturnComposedObjects(true);
		responseOptionType.setReturnType("LeafClass");
		registryStoredQuery.setResponseOption(responseOptionType);

		AdhocQueryType adhocQueryType = new AdhocQueryType();
		adhocQueryType.setId("urn:uuid:14d4debf-8f97-4251-9a74-a90016b0af0d"); // FindDocuments
																				// by
																				// patientId
		registryStoredQuery.setAdhocQuery(adhocQueryType);

		SlotType1 patientIdSlotType = new SlotType1();
		patientIdSlotType.setName("$XDSDocumentEntryPatientId");
		ValueListType patientIdValueListType = new ValueListType();
		
		/*  Modified by Burak Tasel 2/4/2013 - Appended &ISO to patientId and trim homeCommunityId */
		/* begin */
		patientIdValueListType.getValue().add(
				"'" + currResource.getResourceId().trim() + "^^^&"
						+ homeCommunityId.trim() + "&ISO'"); // PatientId
		/* end */
		patientIdSlotType.setValueList(patientIdValueListType);
		adhocQueryType.getSlot().add(patientIdSlotType);

		SlotType1 statusSlotType = new SlotType1();
		statusSlotType.setName("$XDSDocumentEntryStatus");
		ValueListType statusValueListType = new ValueListType();
		statusValueListType.getValue().add(
				"('urn:oasis:names:tc:ebxml-regrep:StatusType:Approved')");
		statusSlotType.setValueList(statusValueListType);
		adhocQueryType.getSlot().add(statusSlotType);

		xdsbRegistry = new XdsbRegistryImpl(xdsbRegistryEndpoint);
		AdhocQueryResponse result = xdsbRegistry
				.registryStoredQuery(registryStoredQuery);

		RegisteryStoredQueryResponse response = new RegisteryStoredQueryResponse();

		try {
			String xmlResponse = marshall(result, result.getClass(),
					RegistryObjectListType.class);
			response.setReturn(xmlResponse);

		} catch (Throwable e) {
			throw new DS4PException(e.toString(), e);
		}

		return response;
	}

	public RetrieveDocumentSetResponse retrieveDocumentSetRequest(
			String documentUniqueId) {
		RetrieveDocumentSetResponse response = new RetrieveDocumentSetResponse();
		RetrieveDocumentSetRequest retrieveDocumentSet = new RetrieveDocumentSetRequest();
		DocumentRequest documentRequest = new DocumentRequest();
		documentRequest.setHomeCommunityId(homeCommunityId);
		documentRequest.setRepositoryUniqueId(repositoryId);
		documentRequest.setDocumentUniqueId(documentUniqueId);
		retrieveDocumentSet.getDocumentRequest().add(documentRequest);

		Return result = null;
		/*  Modified by Burak Tasel 2/4/2013 - Initialize xdsbRepository with endpoints */
		/* begin */
		ihe.iti.xds_b._2007.RetrieveDocumentSetResponse retrieveDocumentSetResponse = null;
		xdsbRepository = new gov.va.ds4p.repository.xdsbrepository.XdsbRepositoryImpl(
				xdsbRepositoryEndpoint);
		/* end */
		retrieveDocumentSetResponse = xdsbRepository
				.retrieveDocumentSetRequest(retrieveDocumentSet);
		try {
			String xmlResponse = marshall(retrieveDocumentSetResponse,
					retrieveDocumentSetResponse.getClass());
			response.setReturn(xmlResponse);
		} catch (Throwable e) {
			throw new DS4PException(e.toString(), e);
		}

		return response;
	}

	private String marshall(Object obj, Class<?>... classesToBeBound)
			throws Throwable {
		JAXBContext context = JAXBContext.newInstance(classesToBeBound);

		Marshaller marshaller = context.createMarshaller();
		StringWriter stringWriter = new StringWriter();
		marshaller.marshal(obj, stringWriter);

		System.out.println(stringWriter.toString());

		return stringWriter.toString();
	}

	private String dumpRequestToString() {
		String res = "";
		JAXBElement<RequestType> element = new JAXBElement<RequestType>(
				new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os",
						"Request"), RequestType.class, query);
		try {
			JAXBContext context = JAXBContext.newInstance(RequestType.class);
			Marshaller marshaller = context.createMarshaller();
			StringWriter sw = new StringWriter();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(element, sw);
			res = sw.toString();
		} catch (Exception ex) {
			throw new DS4PException("Unable to Dump Request ToString", ex);
			// ex.printStackTrace();
		}
		return res;
	}

	private String dumpResponseToString(ResultType resp) {
		String res = "";
		JAXBElement<ResultType> element = new JAXBElement<ResultType>(
				new QName("urn:oasis:names:tc:xacml:2.0:context:schema:os",
						"Result"), ResultType.class, resp);
		try {
			JAXBContext context = JAXBContext.newInstance(ResultType.class);
			Marshaller marshaller = context.createMarshaller();
			StringWriter sw = new StringWriter();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			marshaller.marshal(element, sw);
			res = sw.toString();
		} catch (Exception ex) {
			throw new DS4PException("Unable to Dump Response toString", ex);
			// ex.printStackTrace();
		}
		return res;
	}

	/*private void logEvent() {
		AuthLog log = new AuthLog();
		// log.setDecision(d.value());
		log.setHealthcareObject(currResource.getResourceName());
		log.setMsgDate(getCurrentDateTime());
		StringBuffer sb = new StringBuffer();
		if (!decisionObject.getPdpObligation().isEmpty()) {
			Iterator iter = decisionObject.getPdpObligation().iterator();
			while (iter.hasNext()) {
				String obS = (String) iter.next() + "\n";
				sb.append(obS);
			}
			log.setObligations(sb.toString());
		} else {
			log.setObligations("");
		}
		log.setDecision(decisionObject.getPdpDecision());
		log.setPurposeOfUse(currSubject.getSubjectPurposeOfUse());
		log.setRequestor(currSubject.getSubjectId());
		log.setUniqueIdentifier(currResource.getResourceId());
		log.setXacmlRequest(dumpRequestToString());
		// log.setXacmlRequest("");
		log.setXacmlResponse(dumpResponseToString(response.getResult().get(0)));
		// log.setXacmlResponse("");
		long startTime = requeststart.getTime();
		long endTime = requestcomplete.getTime();
		long respTime = endTime - startTime;
		log.setHieMsgId(decisionObject.getMessageId());

		log.setServicingOrg(currSubject.getSubjectLocality());

		log.setResponseTime(respTime);

		// log.setHieMsgId(currSubject.getMessageId());
		logEvent(log);

	}

	private void logEvent(AuthLog authlog) {
		try {
			DS4PAuditService service = new DS4PAuditService();
			DS4PAudit port = service.getDS4PAuditPort();
			((BindingProvider) port).getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, auditEndpoint);
			port.saveAuthorizationEvent(authlog);
		} catch (Exception ex) {
			throw new DS4PException(ex.getMessage(), ex);
		}
	}*/

	private XMLGregorianCalendar getCurrentDateTime() {
		XMLGregorianCalendar xgc = null;
		try {
			GregorianCalendar gc = new GregorianCalendar();
			DatatypeFactory dtf = DatatypeFactory.newInstance();
			xgc = dtf.newXMLGregorianCalendar(gc);
		} catch (Exception ex) {
			throw new DS4PException(ex.getMessage(), ex);
		}
		return xgc;
	}

	private <T> T unmarshallFromXml(Class<T> clazz, String xml)
			throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Unmarshaller um = context.createUnmarshaller();
		ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes());
		return (T) um.unmarshal(input);
	}

	private XMLGregorianCalendar convertDateToXMLGregorianCalendar(Date dt) {
		XMLGregorianCalendar xcal = null;
		try {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(dt);
			xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
		} catch (Exception ex) {
			throw new DS4PException(ex.getMessage(), ex);
		}
		return xcal;
	}

	private void processObligationRequirements() {
		processRedactionObligations();
		processMaskingObligations();
		processUSPrivacyLawObligations();
		processRefrainObligations();
		processDocumentHandlingObligations();
	}

	private void processRedactionObligations() {
		ApplicableSensitivityCodes codes = vProvider
				.getDataSegmentationObligations();
		List<ActInformationSensitivityPolicy> codeList = codes
				.getActInformationSensitivityPolicy();
		Iterator iter = codeList.iterator();
		while (iter.hasNext()) {
			ActInformationSensitivityPolicy policy = (ActInformationSensitivityPolicy) iter
					.next();
			String codeValue = policy.getCode();
			query.getResource().clear();
			ResourceType rType = createResourceTypeForObligationDecision(
					DS4PConstants.RESOURCE_DATA_REDACTION_NS, codeValue,
					DS4PConstants.RESOURCE_DATA_REDACTION);
			query.getResource().add(rType);
			String decision = getObligationDecision();
			if (DS4PConstants.PERMIT.equals(decision)) {
				String obligation = DS4PConstants.PATIENT_REDACT_CONSTRUCT
						+ codeValue;
				decisionObject.getPdpObligation().add(obligation);
			}
		}
	}

	private void processMaskingObligations() {
		ApplicableSensitivityCodes codes = vProvider
				.getDataSegmentationObligations();
		List<ActInformationSensitivityPolicy> codeList = codes
				.getActInformationSensitivityPolicy();
		Iterator iter = codeList.iterator();
		while (iter.hasNext()) {
			ActInformationSensitivityPolicy policy = (ActInformationSensitivityPolicy) iter
					.next();
			String codeValue = policy.getCode();
			query.getResource().clear();
			ResourceType rType = createResourceTypeForObligationDecision(
					DS4PConstants.RESOURCE_DATA_MASKING_NS, codeValue,
					DS4PConstants.RESOURCE_DATA_MASKING);
			query.getResource().add(rType);
			String decision = getObligationDecision();
			if (DS4PConstants.PERMIT.equals(decision)) {
				String obligation = DS4PConstants.PATIENT_MASK_CONSTRUCT
						+ codeValue;
				decisionObject.getPdpObligation().add(obligation);
			}
		}
	}

	private void processUSPrivacyLawObligations() {
		ApplicableUSLaws codes = vProvider.getPrivacyLawObligations();
		List<ActUSPrivacyLaw> codeList = codes.getActUSPrivacyLaw();
		Iterator iter = codeList.iterator();
		while (iter.hasNext()) {
			ActUSPrivacyLaw policy = (ActUSPrivacyLaw) iter.next();
			String codeValue = policy.getCode();
			query.getResource().clear();
			ResourceType rType = createResourceTypeForObligationDecision(
					DS4PConstants.RESOURCE_US_PRIVACY_LAW_NS, codeValue,
					DS4PConstants.RESOURCE_US_PRIVACY_LAW);
			query.getResource().add(rType);
			String decision = getObligationDecision();
			if (DS4PConstants.PERMIT.equals(decision)) {
				String obligation = DS4PConstants.ORG_PRIVACY_LAW_CONSTRUCT
						+ codeValue;
				decisionObject.getPdpObligation().add(obligation);
			}
		}
	}

	private void processRefrainObligations() {
		ApplicableRefrainPolicies codes = vProvider.getRefrainObligations();
		List<RefrainPolicy> codeList = codes.getRefrainPolicy();
		Iterator iter = codeList.iterator();
		while (iter.hasNext()) {
			RefrainPolicy policy = (RefrainPolicy) iter.next();
			String codeValue = policy.getCode();
			query.getResource().clear();
			ResourceType rType = createResourceTypeForObligationDecision(
					DS4PConstants.RESOURCE_REFRAIN_POLICY_NS, codeValue,
					DS4PConstants.RESOURCE_REFRAIN_POLICY);
			query.getResource().add(rType);
			String decision = getObligationDecision();
			if (DS4PConstants.PERMIT.equals(decision)) {
				String obligation = DS4PConstants.ORG_REFRAIN_POLICY_CONSTRUCT
						+ codeValue;
				decisionObject.getPdpObligation().add(obligation);
			}
		}
	}

	private void processDocumentHandlingObligations() {
		ApplicableObligationPolicies codes = vProvider
				.getDocumentHandlingObligations();
		List<ObligationPolicy> codeList = codes.getObligationPolicy();
		Iterator iter = codeList.iterator();
		while (iter.hasNext()) {
			ObligationPolicy policy = (ObligationPolicy) iter.next();
			String codeValue = policy.getCode();
			query.getResource().clear();
			ResourceType rType = createResourceTypeForObligationDecision(
					DS4PConstants.RESOURCE_DOCUMENT_HANDLING_NS, codeValue,
					DS4PConstants.RESOURCE_DOCUMENT_HANDLING);
			query.getResource().add(rType);
			String decision = getObligationDecision();
			if (DS4PConstants.PERMIT.equals(decision)) {
				String obligation = DS4PConstants.ORG_DOCUMENT_HANDLING_CONSTRUCT
						+ codeValue;
				decisionObject.getPdpObligation().add(obligation);
			}
		}
	}

	private ResourceType createResourceTypeForObligationDecision(
			String evaluationAttributeName, String value, String serviceTypeName) {
		// Set Resource Attributes - Organization and Region
		ResourceType resource = new ResourceType();

		AttributeType at = new AttributeType();
		AttributeValueType avt = new AttributeValueType();
		at.setAttributeId(DS4PConstants.RESOURCE_NWHIN_SERVICE_NS);
		at.setDataType(DS4PConstants.STRING);
		avt = new AttributeValueType();
		avt.getContent().add(serviceTypeName);
		at.getAttributeValue().add(avt);
		resource.getAttribute().add(at);

		at = new AttributeType();
		at.setAttributeId(DS4PConstants.RESOURCE_LOCALITY_NS);
		at.setDataType(DS4PConstants.STRING);
		avt = new AttributeValueType();
		avt.getContent().add(homeCommunityId);
		at.getAttributeValue().add(avt);
		resource.getAttribute().add(at);

		// Kairon Patient Consent
		at = new AttributeType();
		at.setAttributeId(DS4PConstants.MITRE_PATIENT_AUTHORIZATION);
		at.setDataType(DS4PConstants.STRING);
		avt = new AttributeValueType();
		avt.getContent().add(patientAuthorization);
		at.getAttributeValue().add(avt);
		resource.getAttribute().add(at);

		// add obligation values
		at = new AttributeType();
		at.setAttributeId(evaluationAttributeName);
		at.setDataType(DS4PConstants.STRING);
		avt = new AttributeValueType();
		avt.getContent().add(value);
		at.getAttributeValue().add(avt);
		resource.getAttribute().add(at);

		return resource;

	}

	private String getObligationDecision() {
		String decision = "Deny"; // default
		try {
			XACMLPolicyEvaluationServiceService service = new XACMLPolicyEvaluationServiceService();
			XACMLPolicyEvaluationService port = service
					.getXACMLPolicyEvaluationServicePort();
			((BindingProvider) port).getRequestContext().put(
					BindingProvider.ENDPOINT_ADDRESS_PROPERTY, pdpEndpoint);

			response = port.evaluatePolicySet(query, policy);

			decision = response.getResult().get(0).getDecision().value();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return decision;
	}

}
