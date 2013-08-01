package gov.samhsa.consent2share.service.consentexport;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import gov.samhsa.consent2share.domain.consent.Consent;
import gov.samhsa.consent2share.domain.consent.ConsentDoNotShareClinicalDocumentSectionTypeCode;
import gov.samhsa.consent2share.domain.consent.ConsentDoNotShareClinicalDocumentTypeCode;
import gov.samhsa.consent2share.domain.consent.ConsentShareForPurposeOfUseCode;
import gov.samhsa.consent2share.domain.consent.ConsentDoNotShareSensitivityPolicyCode;
import gov.samhsa.consent2share.domain.consent.ConsentIndividualProviderDisclosureIsMadeTo;
import gov.samhsa.consent2share.domain.consent.ConsentIndividualProviderPermittedToDisclose;
import gov.samhsa.consent2share.domain.consent.ConsentOrganizationalProviderDisclosureIsMadeTo;
import gov.samhsa.consent2share.domain.consent.ConsentOrganizationalProviderPermittedToDisclose;
import gov.samhsa.consent2share.domain.consent.ConsentRepository;
import gov.samhsa.consent2share.domain.patient.Patient;
import gov.samhsa.consent2share.domain.provider.IndividualProvider;
import gov.samhsa.consent2share.domain.provider.OrganizationalProvider;
import gov.samhsa.consent2share.domain.reference.ClinicalConceptCode;
import gov.samhsa.consent2share.domain.reference.ClinicalDocumentSectionTypeCode;
import gov.samhsa.consent2share.domain.reference.ClinicalDocumentTypeCode;
import gov.samhsa.consent2share.domain.reference.PurposeOfUseCode;
import gov.samhsa.consent2share.domain.reference.SensitivityPolicyCode;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(MockitoJUnitRunner.class)
public class ConsentExportServiceImplTest {

	@InjectMocks
	ConsentExportServiceImpl cest = new ConsentExportServiceImpl();

	@Mock
	ConsentRepository consentRepository;

	@Mock
	ModelMapper modelMapper;

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before
	public void setUp() {

		Consent consent = mock(Consent.class);
		when(consentRepository.findOne(anyLong())).thenReturn(consent);

	}

	@Test
	public void testJaxbMarshall_Empty_consent() {
		String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n";

		ConsentExportDto consentExportDto = mock(ConsentExportDto.class);

		ByteArrayOutputStream marshalresult = cest
				.jaxbMarshall(consentExportDto);
		String xmlContent = "<ConsentExport/>\n";

		Assert.assertEquals(xmlHeader + xmlContent, marshalresult.toString());
	}

	@Test
	public void testJaxbMarshall_by_version() {
		String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
		Integer version = 1;
		ConsentExportDto consentExportDto = new ConsentExportDto();
		consentExportDto.setVersion(version);
		ByteArrayOutputStream marshalresult = cest
				.jaxbMarshall(consentExportDto);
		String xmlContent = "<ConsentExport><version>1</version></ConsentExport>";
		String result = marshalresult.toString().replaceAll("\\s+", "");
		String expected = (xmlHeader + xmlContent).replaceAll("\\s+", "");

		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testmakeConsentExportDto_return_correct_class() {
		Object object = cest.makeConsentExportDto();
		String className = object.getClass().getName();
		assertEquals("gov.samhsa.consent2share.service.consentexport.ConsentExportDto",
				className);
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testConsentExportMap_when_mapping_basic_information() {
		ConsentExportService cestSpy = spy(cest);
		ConsentExportDto consentExportDto = new ConsentExportDto();
		ConsentExportDto consentExportDtoSpy = spy(consentExportDto);
		Consent consent = mock(Consent.class);
		Patient patient = mock(Patient.class);
		Patient legalRep = mock(Patient.class);
		Date date = (mock(Date.class));
		TypeCodesDto typeCodesDto = new TypeCodesDto();

		when(cestSpy.makeConsentExportDto()).thenReturn(consentExportDtoSpy);
		when(consent.getConsentReferenceId()).thenReturn("consentReferenceId");
		when(consent.getPatient()).thenReturn(patient);
		when(consent.getLegalRepresentative()).thenReturn(legalRep);
		when(consent.getStartDate()).thenReturn(date);
		when(consent.getEndDate()).thenReturn(date);
		when(consent.getSignedDate()).thenReturn(date);
		when(consent.getVersion()).thenReturn(1);
		when(consent.getRevocationDate()).thenReturn(date);
		
		when(cestSpy.makeTypeCodesDto()).thenReturn(typeCodesDto);
	
		cestSpy.consentExportMap(consent);

		verify(consentExportDtoSpy, times(1)).setPatientExportDto(
				any(PatientExportDto.class));
		verify(consentExportDtoSpy, times(1)).setLegalRepresentative(
				any(PatientExportDto.class));
		
		verify(consentExportDtoSpy, times(1)).setDoNotShareClinicalDocumentTypeCodes((Set<TypeCodesDto>) any());
		verify(consentExportDtoSpy, times(1)).setDoNotShareClinicalDocumentSectionTypeCodes((Set<TypeCodesDto>) any());
		verify(consentExportDtoSpy, times(1)).setDoNotShareSensitivityPolicyCodes((Set<TypeCodesDto>) any());
		verify(consentExportDtoSpy, times(1)).setShareForPurposeOfUseCodes((Set<TypeCodesDto>) any());
		verify(consentExportDtoSpy, times(1)).setDoNotShareClinicalConceptCodes((Set<TypeCodesDto>) any());
		
		
		verify(consentExportDtoSpy, times(1)).setConsentReferenceid(anyString());
		verify(consentExportDtoSpy, times(1)).setConsentStart(any(Date.class));
		verify(consentExportDtoSpy, times(1)).setConsentEnd(any(Date.class));
		verify(consentExportDtoSpy, times(1)).setSignedDate(any(Date.class));
		verify(consentExportDtoSpy, times(1)).setVersion(anyInt());
		verify(consentExportDtoSpy, times(1))
				.setRevocationDate(any(Date.class));

	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testConsentExportMap_when_mapping_providers() {
		ConsentExportService cestSpy = spy(cest);
		ConsentExportDto consentExportDto = new ConsentExportDto();
		ConsentExportDto consentExportDtoSpy = spy(consentExportDto);
		Consent consent = mock(Consent.class);
		
		Set<ConsentIndividualProviderPermittedToDisclose> consentIndividualProviderPermittedToDisclose=new HashSet<ConsentIndividualProviderPermittedToDisclose>();
		ConsentIndividualProviderPermittedToDisclose pptd=mock(ConsentIndividualProviderPermittedToDisclose.class);
		consentIndividualProviderPermittedToDisclose.add(pptd);
		IndividualProvider individualProvider=mock(IndividualProvider.class);
		
		Set<ConsentIndividualProviderDisclosureIsMadeTo> consentIndividualProviderDisclosureIsMadeTo=new HashSet<ConsentIndividualProviderDisclosureIsMadeTo>();
		ConsentIndividualProviderDisclosureIsMadeTo pptd2=mock(ConsentIndividualProviderDisclosureIsMadeTo.class);
		consentIndividualProviderDisclosureIsMadeTo.add(pptd2);
		
		Set<ConsentOrganizationalProviderPermittedToDisclose> consentOrganizationalProviderPermittedToDisclose=new HashSet<ConsentOrganizationalProviderPermittedToDisclose>();
		ConsentOrganizationalProviderPermittedToDisclose optd=mock(ConsentOrganizationalProviderPermittedToDisclose.class);
		consentOrganizationalProviderPermittedToDisclose.add(optd);
		OrganizationalProvider organizationalProvider=mock(OrganizationalProvider.class);
		
		Set<ConsentOrganizationalProviderDisclosureIsMadeTo> consentOrganizationalProviderDisclosureIsMadeTo=new HashSet<ConsentOrganizationalProviderDisclosureIsMadeTo>();
		ConsentOrganizationalProviderDisclosureIsMadeTo optd2=mock(ConsentOrganizationalProviderDisclosureIsMadeTo.class);
		consentOrganizationalProviderDisclosureIsMadeTo.add(optd2);
		
		when(cestSpy.makeConsentExportDto()).thenReturn(consentExportDtoSpy);
		
		when(consent.getProvidersPermittedToDisclose()).thenReturn(consentIndividualProviderPermittedToDisclose);
		when(pptd.getIndividualProvider()).thenReturn(individualProvider);
		when(consent.getProvidersDisclosureIsMadeTo()).thenReturn(consentIndividualProviderDisclosureIsMadeTo);
		when(pptd2.getIndividualProvider()).thenReturn(individualProvider);
		
		when(consent.getOrganizationalProvidersPermittedToDisclose()).thenReturn(consentOrganizationalProviderPermittedToDisclose);
		when(optd.getOrganizationalProvider()).thenReturn(organizationalProvider);
		when(consent.getOrganizationalProvidersDisclosureIsMadeTo()).thenReturn(consentOrganizationalProviderDisclosureIsMadeTo);
		when(optd2.getOrganizationalProvider()).thenReturn(organizationalProvider);
		
	
		cestSpy.consentExportMap(consent);
		
		verify(consentExportDtoSpy, times(1)).setProvidersPermittedToDisclose((Set<IndividualProviderExportDto>) any());
		verify(consentExportDtoSpy, times(1)).setProvidersDisclosureIsMadeTo((Set<IndividualProviderExportDto>) any());
		verify(consentExportDtoSpy, times(1)).setOrganizationalProvidersPermittedToDisclose((Set<OrganizationalProviderExportDto>) any());
		verify(consentExportDtoSpy, times(1)).setOrganizationalProvidersDisclosureIsMadeTo((Set<OrganizationalProviderExportDto>) any());
		


	}
	
	
	@Test
	public void testConsentExportMap_when_no_LegalRep() {
		ConsentExportService cestSpy = spy(cest);
		ConsentExportDto consentExportDto = new ConsentExportDto();
		ConsentExportDto consentExportDtoSpy = spy(consentExportDto);
		Consent consent = mock(Consent.class);
		
		when(cestSpy.makeConsentExportDto()).thenReturn(consentExportDtoSpy);
		when(consent.getLegalRepresentative()).thenReturn(null);
		
		cestSpy.consentExportMap(consent);

		verify(consentExportDtoSpy, times(0)).setLegalRepresentative(
				any(PatientExportDto.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testConsentExportMap_when_mapping_ClinicalConceptCode() {
		ConsentExportService cestSpy = spy(cest);
		ConsentExportDto consentExportDto = new ConsentExportDto();
		ConsentExportDto consentExportDtoSpy = spy(consentExportDto);
		Consent consent = mock(Consent.class);
		
		Set<ClinicalConceptCode> clinicalConceptCodes=new HashSet<ClinicalConceptCode>();
		ClinicalConceptCode ccc=mock(ClinicalConceptCode.class);
		clinicalConceptCodes.add(ccc);
				
		when(cestSpy.makeConsentExportDto()).thenReturn(consentExportDtoSpy);
		when(consent.getDoNotShareClinicalConceptCodes()).thenReturn(clinicalConceptCodes);
		when(ccc.getDisplayName()).thenReturn("Display name");
		when(ccc.getCode()).thenReturn("code");
		when(ccc.getCodeSystem()).thenReturn("code system");
		when(ccc.getCodeSystemName()).thenReturn("code system name");
		
		
		cestSpy.consentExportMap(consent);

		verify(consentExportDtoSpy, times(1)).setDoNotShareClinicalConceptCodes((Set<TypeCodesDto>)	any());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testConsentExportMap_when_mapping_DoNotShareClinicalDocumentSectionTypeCode() {
		ConsentExportService cestSpy = spy(cest);
		ConsentExportDto consentExportDto = new ConsentExportDto();
		ConsentExportDto consentExportDtoSpy = spy(consentExportDto);
		Consent consent = mock(Consent.class);
		
		Set<ConsentDoNotShareClinicalDocumentSectionTypeCode> consentDoNotShareClinicalDocumentSectionTypeCode=new HashSet<ConsentDoNotShareClinicalDocumentSectionTypeCode>();
		ConsentDoNotShareClinicalDocumentSectionTypeCode ccdt=mock(ConsentDoNotShareClinicalDocumentSectionTypeCode.class);
		consentDoNotShareClinicalDocumentSectionTypeCode.add(ccdt);
		
		ClinicalDocumentSectionTypeCode csc=mock(ClinicalDocumentSectionTypeCode.class);
				
		when(cestSpy.makeConsentExportDto()).thenReturn(consentExportDtoSpy);
		when(consent.getDoNotShareClinicalDocumentSectionTypeCodes()).thenReturn(consentDoNotShareClinicalDocumentSectionTypeCode);
		when(ccdt.getClinicalDocumentSectionTypeCode()).thenReturn(csc);
		when(ccdt.getClinicalDocumentSectionTypeCode()
				.getDisplayName()).thenReturn("Display name");
		when(ccdt.getClinicalDocumentSectionTypeCode().getCode()).thenReturn("code");
		when(ccdt.getClinicalDocumentSectionTypeCode()
				.getCodeSystem()).thenReturn("code system");
		when(ccdt.getClinicalDocumentSectionTypeCode()
				.getCodeSystemName()).thenReturn("code system name");
		
		
		cestSpy.consentExportMap(consent);

		verify(consentExportDtoSpy, times(1)).setDoNotShareClinicalDocumentSectionTypeCodes((Set<TypeCodesDto>)	any());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testConsentExportMap_when_mapping_setDoNotShareClinicalDocumentTypeCodes() {
		ConsentExportService cestSpy = spy(cest);
		ConsentExportDto consentExportDto = new ConsentExportDto();
		ConsentExportDto consentExportDtoSpy = spy(consentExportDto);
		Consent consent = mock(Consent.class);
		
		Set<ConsentDoNotShareClinicalDocumentTypeCode> consentDoNotShareClinicalDocumentTypeCode=new HashSet<ConsentDoNotShareClinicalDocumentTypeCode>();
		ConsentDoNotShareClinicalDocumentTypeCode ccdt=mock(ConsentDoNotShareClinicalDocumentTypeCode.class);
		consentDoNotShareClinicalDocumentTypeCode.add(ccdt);
		
		ClinicalDocumentTypeCode csc=mock(ClinicalDocumentTypeCode.class);
				
		when(cestSpy.makeConsentExportDto()).thenReturn(consentExportDtoSpy);
		when(consent.getDoNotShareClinicalDocumentTypeCodes()).thenReturn(consentDoNotShareClinicalDocumentTypeCode);
		when(ccdt.getClinicalDocumentTypeCode()).thenReturn(csc);
		when(ccdt.getClinicalDocumentTypeCode()
				.getDisplayName()).thenReturn("Display name");
		when(ccdt.getClinicalDocumentTypeCode().getCode()).thenReturn("code");
		when(ccdt.getClinicalDocumentTypeCode()
				.getCodeSystem()).thenReturn("code system");
		when(ccdt.getClinicalDocumentTypeCode()
				.getCodeSystemName()).thenReturn("code system name");
		
		
		cestSpy.consentExportMap(consent);

		verify(consentExportDtoSpy, times(1)).setDoNotShareClinicalDocumentSectionTypeCodes((Set<TypeCodesDto>)	any());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testConsentExportMap_when_mapping_consentDoNotShareForPurposeOfUseCode() {
		ConsentExportService cestSpy = spy(cest);
		ConsentExportDto consentExportDto = new ConsentExportDto();
		ConsentExportDto consentExportDtoSpy = spy(consentExportDto);
		Consent consent = mock(Consent.class);
		
		Set<ConsentShareForPurposeOfUseCode> consentDoNotShareForPurposeOfUseCode=new HashSet<ConsentShareForPurposeOfUseCode>();
		ConsentShareForPurposeOfUseCode ccdt=mock(ConsentShareForPurposeOfUseCode.class);
		consentDoNotShareForPurposeOfUseCode.add(ccdt);
		
		PurposeOfUseCode csc=mock(PurposeOfUseCode.class);
				
		when(cestSpy.makeConsentExportDto()).thenReturn(consentExportDtoSpy);
		when(consent.getShareForPurposeOfUseCodes()).thenReturn(consentDoNotShareForPurposeOfUseCode);
		when(ccdt.getPurposeOfUseCode()).thenReturn(csc);
		when(ccdt.getPurposeOfUseCode()
				.getDisplayName()).thenReturn("Display name");
		when(ccdt.getPurposeOfUseCode().getCode()).thenReturn("code");
		when(ccdt.getPurposeOfUseCode()
				.getCodeSystem()).thenReturn("code system");
		when(ccdt.getPurposeOfUseCode()
				.getCodeSystemName()).thenReturn("code system name");
		
		
		cestSpy.consentExportMap(consent);

		verify(consentExportDtoSpy, times(1)).setShareForPurposeOfUseCodes((Set<TypeCodesDto>)	any());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testConsentExportMap_when_mapping_consentDoNotShareSensitivityPolicyCode() {
		ConsentExportService cestSpy = spy(cest);
		ConsentExportDto consentExportDto = new ConsentExportDto();
		ConsentExportDto consentExportDtoSpy = spy(consentExportDto);
		Consent consent = mock(Consent.class);
		
		Set<ConsentDoNotShareSensitivityPolicyCode> consentDoNotShareSensitivityPolicyCode=new HashSet<ConsentDoNotShareSensitivityPolicyCode>();
		ConsentDoNotShareSensitivityPolicyCode ccdt=mock(ConsentDoNotShareSensitivityPolicyCode.class);
		consentDoNotShareSensitivityPolicyCode.add(ccdt);
		
		SensitivityPolicyCode csc=mock(SensitivityPolicyCode.class);
				
		when(cestSpy.makeConsentExportDto()).thenReturn(consentExportDtoSpy);
		when(consent.getDoNotShareSensitivityPolicyCodes()).thenReturn(consentDoNotShareSensitivityPolicyCode);
		when(ccdt.getSensitivityPolicyCode()).thenReturn(csc);
		when(ccdt.getSensitivityPolicyCode()
				.getDisplayName()).thenReturn("Display name");
		when(ccdt.getSensitivityPolicyCode().getCode()).thenReturn("code");
		when(ccdt.getSensitivityPolicyCode()
				.getCodeSystem()).thenReturn("code system");
		when(ccdt.getSensitivityPolicyCode()
				.getCodeSystemName()).thenReturn("code system name");
		
		
		cestSpy.consentExportMap(consent);

		verify(consentExportDtoSpy, times(1)).setShareForPurposeOfUseCodes((Set<TypeCodesDto>)	any());
	}
	
	@Test
	public void testMakeTypeCodesDto_return_correct_class() {
		Object object = cest.makeTypeCodesDto();
		String className = object.getClass().getName();
		assertEquals("gov.samhsa.consent2share.service.consentexport.TypeCodesDto",
				className);
	}
	
	
}
