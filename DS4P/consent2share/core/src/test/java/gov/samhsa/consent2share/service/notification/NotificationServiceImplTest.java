package gov.samhsa.consent2share.service.notification;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gov.samhsa.consent2share.domain.consent.Consent;
import gov.samhsa.consent2share.domain.consent.ConsentPdfGenerator;
import gov.samhsa.consent2share.domain.consent.ConsentRepository;
import gov.samhsa.consent2share.domain.consent.SignedPDFConsent;
import gov.samhsa.consent2share.domain.consent.SignedPDFConsentRevocation;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.patient.PatientRepository;
import gov.samhsa.consent2share.domain.provider.IndividualProvider;
import gov.samhsa.consent2share.domain.provider.IndividualProviderRepository;
import gov.samhsa.consent2share.domain.provider.OrganizationalProviderRepository;
import gov.samhsa.consent2share.domain.reference.ClinicalDocumentSectionTypeCodeRepository;
import gov.samhsa.consent2share.domain.reference.ClinicalDocumentTypeCodeRepository;
import gov.samhsa.consent2share.domain.reference.PurposeOfUseCodeRepository;
import gov.samhsa.consent2share.domain.reference.SensitivityPolicyCodeRepository;
import gov.samhsa.consent2share.infrastructure.EchoSignSignatureService;
import gov.samhsa.consent2share.infrastructure.security.AuthenticatedUser;
import gov.samhsa.consent2share.infrastructure.security.UserContext;
import gov.samhsa.consent2share.service.dto.ConsentDto;
import gov.samhsa.consent2share.service.dto.ConsentListDto;
import gov.samhsa.consent2share.service.dto.ConsentPdfDto;
import gov.samhsa.consent2share.service.dto.ConsentRevokationPdfDto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceImplTest {
	

	@Mock
	PatientRepository patientRepository;
	
		@InjectMocks
	NotificationService nst=new NotificationServiceImpl();


	
	@Before
	public void setUp(){
		Patient patient=mock(Patient.class);
		when(patientRepository.findByUsername(anyString())).thenReturn(patient);
		
	}
	
	
	@Test
	public void testnotificationStage_is_add_provider(){
		Patient patient=mock(Patient.class);
		when(patientRepository.findByUsername(anyString())).thenReturn(patient);
		String result=nst.notificationStage("username",null);
		assertEquals("notification_add_provider",result);
	}
	
	@Test
	public void testnotificationStage_is_add_one_provider_successed(){
		Patient patient=mock(Patient.class);
		when(patientRepository.findByUsername(anyString())).thenReturn(patient);
		IndividualProvider individualProvider=mock(IndividualProvider.class);
		Set<IndividualProvider> individualProviders=new HashSet<IndividualProvider>();
		individualProviders.add(individualProvider);
		when(patient.getIndividualProviders()).thenReturn(individualProviders);
		String result=nst.notificationStage("username","add");
		assertEquals("notification_add_one_provider_successed",result);
	}
	
	@Test
	public void testnotificationStage_is_add_second_provider(){
		Patient patient=mock(Patient.class);
		when(patientRepository.findByUsername(anyString())).thenReturn(patient);
		IndividualProvider individualProvider=mock(IndividualProvider.class);
		Set<IndividualProvider> individualProviders=new HashSet<IndividualProvider>();
		individualProviders.add(individualProvider);
		when(patient.getIndividualProviders()).thenReturn(individualProviders);
		String result=nst.notificationStage("username",null);
		assertEquals("notification_add_second_provider",result);
	}
	
	@Test
	public void testnotificationStage_is_add_second_provider_successed(){
		Patient patient=mock(Patient.class);
		when(patientRepository.findByUsername(anyString())).thenReturn(patient);
		IndividualProvider individualProvider=mock(IndividualProvider.class);
		IndividualProvider individualProvider2=mock(IndividualProvider.class);
		Set<IndividualProvider> individualProviders=new HashSet<IndividualProvider>();
		individualProviders.add(individualProvider);
		individualProviders.add(individualProvider2);
		when(patient.getIndividualProviders()).thenReturn(individualProviders);
		String result=nst.notificationStage("username","add");
		assertEquals("notification_add_second_provider_successed",result);
	}
	
	@Test
	public void testnotificationStage_is_add_consent(){
		Patient patient=mock(Patient.class);
		when(patientRepository.findByUsername(anyString())).thenReturn(patient);
		IndividualProvider individualProvider=mock(IndividualProvider.class);
		IndividualProvider individualProvider2=mock(IndividualProvider.class);
		Set<IndividualProvider> individualProviders=new HashSet<IndividualProvider>();
		individualProviders.add(individualProvider);
		individualProviders.add(individualProvider2);
		when(patient.getIndividualProviders()).thenReturn(individualProviders);
		String result=nst.notificationStage("username",null);
		assertEquals("notification_add_consent",result);
	}
	
	
	@Test
	public void testnotificationStage_is_add_consent_successed(){
		Patient patient=mock(Patient.class);
		when(patientRepository.findByUsername(anyString())).thenReturn(patient);
		IndividualProvider individualProvider=mock(IndividualProvider.class);
		IndividualProvider individualProvider2=mock(IndividualProvider.class);
		Set<IndividualProvider> individualProviders=new HashSet<IndividualProvider>();
		Set<Consent> consents=new HashSet<Consent>();
		Consent consent=mock(Consent.class);
		consents.add(consent);
		individualProviders.add(individualProvider);
		individualProviders.add(individualProvider2);
		when(patient.getIndividualProviders()).thenReturn(individualProviders);
		when(patient.getConsents()).thenReturn(consents);
		String result=nst.notificationStage("username","add");
		assertEquals("notification_add_consent_successed",result);
	}
	
	
	@Test
	public void testcheckConsentReviewStatus_when_consent_size_0(){
		Set<Consent> consents=new HashSet<Consent>();
		boolean result=nst.checkConsentReviewStatus(consents);
		assertEquals(false,result);
	}
	
	@Test
	public void testcheckConsentReviewStatus_when_consent_has_not_reviewed(){
		Set<Consent> consents=new HashSet<Consent>();
		Consent consent=mock(Consent.class);
		consents.add(consent);
		when(consent.getSignedPdfConsent()).thenReturn(null);
		boolean result=nst.checkConsentReviewStatus(consents);
		assertEquals(false,result);
	}
	
	@Test
	public void testcheckConsentReviewStatus_when_consent_has_reviewed(){
		Set<Consent> consents=new HashSet<Consent>();
		Consent consent=mock(Consent.class);
		SignedPDFConsent signedPdfConsent=mock(SignedPDFConsent.class);
		consents.add(consent);
		when(consent.getSignedPdfConsent()).thenReturn(signedPdfConsent);
		boolean result=nst.checkConsentReviewStatus(consents);
		assertEquals(true,result);
	}
	
	@Test
	public void testcheckConsentSignedStatus_when_consent_size_0(){
		Set<Consent> consents=new HashSet<Consent>();
		boolean result=nst.checkConsentSignedStatus(consents);
		assertEquals(false,result);
	}
	
	@Test
	public void testcheckConsentSignedStatus_when_consent_has_not_reviewed(){
		Set<Consent> consents=new HashSet<Consent>();
		Consent consent=mock(Consent.class);
		consents.add(consent);
		when(consent.getSignedPdfConsent()).thenReturn(null);
		boolean result=nst.checkConsentSignedStatus(consents);
		assertEquals(false,result);
	}
	
	@Test
	public void testcheckConsentSignedStatus_when_consent_has_reviewed_not_signed(){
		Set<Consent> consents=new HashSet<Consent>();
		Consent consent=mock(Consent.class);
		SignedPDFConsent signedPdfConsent=mock(SignedPDFConsent.class);
		consents.add(consent);
		when(consent.getSignedPdfConsent()).thenReturn(signedPdfConsent);
		when(consent.getSignedPdfConsent().getSignedPdfConsentContent()).thenReturn(null);
		boolean result=nst.checkConsentSignedStatus(consents);
		assertEquals(false,result);
	}
	
	@Test
	public void testcheckConsentSignedStatus_when_consent_has_reviewed_signed(){
		Set<Consent> consents=new HashSet<Consent>();
		Consent consent=mock(Consent.class);
		SignedPDFConsent signedPdfConsent=mock(SignedPDFConsent.class);
		byte[] signedPdfConsentContent=new byte[]{(byte)0xba, (byte)0x8a,};
		consents.add(consent);
		when(consent.getSignedPdfConsent()).thenReturn(signedPdfConsent);
		when(consent.getSignedPdfConsent().getSignedPdfConsentContent()).thenReturn(signedPdfConsentContent);
		boolean result=nst.checkConsentSignedStatus(consents);
		assertEquals(true,result);
	}
	
	

}
