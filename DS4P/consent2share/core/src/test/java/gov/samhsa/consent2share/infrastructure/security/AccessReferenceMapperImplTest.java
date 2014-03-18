package gov.samhsa.consent2share.infrastructure.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import echosign.api.clientv15.dto8.EmbeddedWidgetCreationResult;
import gov.samhsa.consent.ConsentGenException;
import gov.samhsa.consent2share.domain.consent.Consent;
import gov.samhsa.consent2share.domain.consent.ConsentPdfGenerator;
import gov.samhsa.consent2share.domain.consent.ConsentRepository;
import gov.samhsa.consent2share.domain.consent.SignedPDFConsent;
import gov.samhsa.consent2share.domain.consent.SignedPDFConsentRevocation;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.provider.IndividualProviderRepository;
import gov.samhsa.consent2share.domain.provider.OrganizationalProviderRepository;
import gov.samhsa.consent2share.domain.reference.ClinicalDocumentSectionTypeCodeRepository;
import gov.samhsa.consent2share.domain.reference.ClinicalDocumentTypeCodeRepository;
import gov.samhsa.consent2share.domain.reference.PurposeOfUseCodeRepository;
import gov.samhsa.consent2share.domain.reference.SensitivityPolicyCodeRepository;
import gov.samhsa.consent2share.infrastructure.EchoSignSignatureService;
import gov.samhsa.consent2share.infrastructure.security.AuthenticatedUser;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.service.consentexport.ConsentExportService;
import gov.samhsa.consent2share.service.dto.ConsentDto;
import gov.samhsa.consent2share.service.dto.ConsentListDto;
import gov.samhsa.consent2share.service.dto.ConsentPdfDto;
import gov.samhsa.consent2share.service.dto.ConsentRevokationPdfDto;
import gov.samhsa.consent2share.service.dto.HasId;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.owasp.esapi.AccessReferenceMap;
import org.springframework.data.domain.Page;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.owasp.esapi.AccessReferenceMap;
import org.owasp.esapi.errors.AccessControlException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * The Class ConsentServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class AccessReferenceMapperImplTest {
	
	AccessReferenceMapperImpl sut;
	HttpSession session;
	final String INDIRECT_REFERENCE="ScrambledText";
	final Long DIRECT_REFERENCE=(long) 1;
	final String MAP_NAME="AccessReferenceMap";
	
	@Before
	public void setUp() throws AccessControlException {
		AccessReferenceMapperImpl accessReferenceMapper=new AccessReferenceMapperImpl();
		sut=spy(accessReferenceMapper);
		session=mock(HttpSession.class);
		@SuppressWarnings("unchecked")
		AccessReferenceMap<String> map=(AccessReferenceMap<String>)mock(AccessReferenceMap.class);
		Object directReference=String.valueOf(DIRECT_REFERENCE);
		
		doReturn(map).when(sut).getMap();
		doReturn(INDIRECT_REFERENCE).when(map).addDirectReference(anyString());
		doReturn(INDIRECT_REFERENCE).when(map).addDirectReference(anyLong());
		doReturn(directReference).when(map).getDirectReference(INDIRECT_REFERENCE);
		doReturn(session).when(sut).getSession();
	}
	
	@Test
	public void testGetDirectReference() {
		assertEquals(DIRECT_REFERENCE, Long.valueOf(sut.getDirectReference(INDIRECT_REFERENCE)));
	}
	
	@Test
	public void testGetIndirectReference() {
		assertEquals(INDIRECT_REFERENCE, sut.getIndirectReference(DIRECT_REFERENCE));
	}
	
	@Test
	public void testSetupAccessReferenceMap() {
		Set<HasId> objectSet=new HashSet<HasId>();
		HasId hasId1=mock(HasId.class);
		HasId hasId2=mock(HasId.class);
		objectSet.add(hasId1);
		objectSet.add(hasId2);
		doReturn("1").when(hasId1).getId();
		doReturn("2").when(hasId2).getId();
		
		sut.setupAccessReferenceMap(objectSet);
		verify(hasId1).setId(INDIRECT_REFERENCE);
		verify(hasId2).setId(INDIRECT_REFERENCE);
	}

}
