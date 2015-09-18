package gov.samhsa.consent2share.c32;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import gov.samhsa.acs.common.exception.DS4PException;
import gov.samhsa.acs.common.tool.SimpleMarshallerImpl;
import gov.samhsa.acs.common.tool.XmlTransformer;
import gov.samhsa.acs.common.tool.XmlTransformerImpl;
import gov.samhsa.consent2share.commonunit.io.ResourceFileReader;

import java.util.Optional;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class C32ToGreenCcdTransformerImplTest {

	@Mock
	private XmlTransformer xmlTransformer;

	@InjectMocks
	private C32ToGreenCcdTransformerImpl sut;

	@Test(expected = IllegalArgumentException.class)
	public void testTransformC32ToGreenCcd_Given_Empty_C32Xml_Throws_IllegalArgumentException()
			throws Throwable {
		// Arrange

		// Act
		sut.TransformC32ToGreenCcd("");
	}

	@Test(expected = C32ToGreenCcdTransformerException.class)
	public void testTransformC32ToGreenCcd_Given_Invalid_C32Xml_Succeeds_IT()
			throws Throwable {
		// Arrange
		ReflectionTestUtils.setField(sut, "xmlTransformer",
				createXmlTransformer());
		final String c32 = "Invalid C32";

		// Act
		@SuppressWarnings("unused")
		final String greenCcd = sut.TransformC32ToGreenCcd(c32);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = C32ToGreenCcdTransformerException.class)
	public void testTransformC32ToGreenCcd_Given_Invalid_C32Xml_Succeeds_Unit()
			throws Throwable {
		// Arrange
		final String c32 = "Invalid C32";
		when(
				xmlTransformer.transform(eq(c32), anyString(),
						any(Optional.class), any(Optional.class))).thenThrow(
				DS4PException.class);

		// Act
		@SuppressWarnings("unused")
		final String greenCcd = sut.TransformC32ToGreenCcd(c32);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTransformC32ToGreenCcd_Given_NoText_C32Xml_Throws_IllegalArgumentException()
			throws Throwable {
		// Arrange

		// Act
		sut.TransformC32ToGreenCcd(" ");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTransformC32ToGreenCcd_Given_Null_C32Xml_Throws_IllegalArgumentException()
			throws Throwable {
		// Arrange

		// Act
		sut.TransformC32ToGreenCcd(null);
	}

	@Test
	public void testTransformC32ToGreenCcd_Given_Valid_C32Xml_Succeeds_IT()
			throws Throwable {
		// Arrange
		ReflectionTestUtils.setField(sut, "xmlTransformer",
				createXmlTransformer());

		// Read c32 file from resource
		final String c32FileUri = "c32.xml";
		final String c32Xml = ResourceFileReader
				.getStringFromResourceFile(c32FileUri);

		// Act
		final String greenCcd = sut.TransformC32ToGreenCcd(c32Xml);

		// Assert
		final String greenCcdFileUri = "green_ccd_transformed_from_c32.xml";
		final String expectedGreenCcd = ResourceFileReader
				.getStringFromResourceFile(greenCcdFileUri);

		XMLUnit.setIgnoreWhitespace(true);
		XMLUnit.setNormalizeWhitespace(true);

		assertXMLEqual("", expectedGreenCcd, greenCcd);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testTransformC32ToGreenCcd_Given_Valid_C32Xml_Succeeds_Unit()
			throws Throwable {
		// Arrange
		final String greenCcdToReturn = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><greenCCD xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"urn:hl7-org:v3\" xmlns:hl7=\"urn:hl7-org:v3\" xmlns:sdtc=\"urn:hl7-org:sdtc\"><header><documentID root=\"2.16.840.1.113883.3.72\"/><documentTimestamp value=\"20000407130000+0500\"/><confidentiality code=\"N\"/><personalInformation><patientInformation><personID root=\"2.16.840.1.113883.19.5\" extension=\"996-756-495\"/><personAddress><streetAddressLine>streetAddressLine</streetAddressLine><city>city</city><state>state</state><postalCode>postalCode</postalCode></personAddress><personPhone value=\"1-999-999-9999\"/><personInformation><personName><family>Levin</family><given>Henry</given></personName><gender code=\"F\" codeSystem=\"2.16.840.1.113883.5.1\" codeSystemName=\"HL7 AdministrativeGender\" displayName=\"Female\"/><personDateOfBirth value=\"19320924\"/><maritalStatus code=\"S\" codeSystem=\"2.16.840.1.113883.5.2\" codeSystemName=\"HL7 Marital status\" displayName=\"Single\"/><religiousAffiliation code=\"1022\" codeSystem=\"2.16.840.1.113883.5.1076\" codeSystemName=\"ReligiousAffiliation\" displayName=\"Independent\"/><race code=\"2178-2\" codeSystem=\"2.16.840.1.113883.6.238\" codeSystemName=\"CDC Race and Ethnicity\" displayName=\"African American\"/><ethnicGroup/></personInformation></patientInformation></personalInformation><informationSource><author><authorTime value=\"20000407130000+0500\"/><authorName><family>family</family><given>given</given><prefix>prefix</prefix></authorName></author><informationSourceName><organizationName>Hospital</organizationName><organizationId extension=\"http://stelsewhere.com/\"/><organizationTelecom value=\"1-800-555-1234\"/></informationSourceName></informationSource></header><body><results><result><resultDateTime value=\"200003231430\"/><resultType code=\"30313-1\" codeSystem=\"2.16.840.1.113883.6.1\" codeSystemName=\"LOINC\" displayName=\"HGB\"/><procedureCode code=\"43789009\" codeSystem=\"2.16.840.1.113883.6.96\" displayName=\"CBC WO DIFFERENTIAL\"/><resultValue><physicalQuantity value=\"13.2\" unit=\"g/dl\"/></resultValue><resultInterpretation code=\"N\" codeSystem=\"2.16.840.1.113883.5.83\"/><resultReferenceRange>M 13-18 g/dl; F 12-16 g/dl</resultReferenceRange></result></results><encounters><encounter><encounterType code=\"99241\" codeSystem=\"2.16.840.1.113883.6.12\" codeSystemName=\"CPT\" displayName=\"Office consultation - 15 minutes\"><originalText>Office consultation - 15 minutes</originalText></encounterType><encounterDateTime value=\"20000407\"/><encounterProvider><providerName><family>Dolin</family><given>Robert</given><prefix>Dr.</prefix></providerName><providerOrganizationName>Good Health Clinic</providerOrganizationName></encounterProvider></encounter></encounters><medications><medication><medicationDateRange><low value=\"20101010\"/><high value=\"20111010\"/></medicationDateRange><admissionTiming><period value=\"6\" unit=\"h\"/></admissionTiming><route code=\"IPINHL\" codeSystem=\"2.16.840.1.113883.3.26.1.1\" displayName=\"Inhalation, oral\"/><dose value=\"2\" unit=\"unit\"/><productForm code=\"415215001\" codeSystem=\"2.16.840.1.113883.3.26.1.1\" displayName=\"Puff\"/><medicationInformation><codedProductName code=\"307782\" codeSystem=\"2.16.840.1.113883.6.88\" displayName=\"Albuterol 0.09 MG/ACTUAT inhalant solution\"/></medicationInformation><typeOfMedication code=\"329505003\" codeSystem=\"2.16.840.1.113883.6.96\" codeSystemName=\"SNOMED CT\"/><patientInstructions>This is the text of the Patient Instruction. Note that this instruction is printed in the narrative text of the parent Section and is refereced by the following pointer to it.</patientInstructions><fulfillmentInstructions>Don't forget your medication</fulfillmentInstructions><statusOfMedication code=\"code45\" codeSystem=\"codeSystem43\" codeSystemName=\"codeSystemName43\" displayName=\"displayName43\"/></medication></medications><immunizations><immunization><administeredDate value=\"20101010\"/><route code=\"IPINHL\" codeSystem=\"2.16.840.1.113883.3.26.1.1\" displayName=\"Inhalation, oral\"/><dose/><medicationInformation><codedProductName code=\"88\" codeSystem=\"2.16.840.1.113883.6.59\" displayName=\"Influenza virus vaccine\"><originalText>Influenza virus vaccine</originalText></codedProductName></medicationInformation><immunizationProvider><providerName><family>Dolin</family><given>Robert</given><prefix>Dr.</prefix></providerName></immunizationProvider><refusalReason code=\"PATOBJ\" codeSystem=\"2.16.840.1.113883.11.19725\" codeSystemName=\"HL7 ActNoImmunizationReason\" displayName=\"patient objection\"/></immunization></immunizations><allergies><allergy><adverseEventDate><low value=\"20101010\"/><high value=\"20101111\"/></adverseEventDate><adverseEventType code=\"416098002\" codeSystem=\"2.16.840.1.113883.6.96\" codeSystemName=\"SNOMED CT\" displayName=\"drug allergy\"/><product code=\"70618\" codeSystem=\"2.16.840.1.113883.6.88\" displayName=\"Penicillin\"/><reaction code=\"247472004\" codeSystem=\"2.16.840.1.113883.6.96\" displayName=\"Hives\"/><reaction code=\"247472004\" codeSystem=\"2.16.840.1.113883.6.96\" displayName=\"Hives Two\"/><allergyStatus code=\"55561003\" codeSystem=\"2.16.840.1.113883.6.96\" displayName=\"Active\"/></allergy><allergy><adverseEventDate><low value=\"20101010\"/><high value=\"20101111\"/></adverseEventDate><adverseEventType code=\"416098002\" codeSystem=\"2.16.840.1.113883.6.96\" codeSystemName=\"SNOMED CT\" displayName=\"drug allergy\"/><product code=\"70618\" codeSystem=\"2.16.840.1.113883.6.88\" displayName=\"Penicillin\"/><reaction code=\"247472004\" codeSystem=\"2.16.840.1.113883.6.96\" displayName=\"Hives\"/><reaction code=\"247472004\" codeSystem=\"2.16.840.1.113883.6.96\" displayName=\"Hives Two\"/><allergyStatus code=\"55561003\" codeSystem=\"2.16.840.1.113883.6.96\" displayName=\"Active\"/></allergy></allergies><vitalSigns><vitalSign><resultDateTime value=\"19991114\"/><resultType code=\"8302-2\" codeSystem=\"2.16.840.1.113883.6.1\" codeSystemName=\"LOINC\" displayName=\"Body Height\"/><resultValue><physicalQuantity value=\"177\" unit=\"cm\"/></resultValue><resultInterpretation nullFlavor=\"UNK\"/></vitalSign><vitalSign><resultDateTime value=\"19991114\"/><resultType code=\"3141-9\" codeSystem=\"2.16.840.1.113883.6.1\" codeSystemName=\"LOINC\" displayName=\"Body Weight\"/><resultValue><physicalQuantity value=\"86\" unit=\"kg\"/></resultValue><resultInterpretation nullFlavor=\"UNK\"/></vitalSign><vitalSign><resultDateTime value=\"19991114\"/><resultType code=\"8480-6\" codeSystem=\"2.16.840.1.113883.6.1\" codeSystemName=\"LOINC\" displayName=\"Systolic BP\"/><resultValue><physicalQuantity value=\"132\" unit=\"mm[Hg]\"/></resultValue><resultInterpretation nullFlavor=\"UNK\"/></vitalSign><vitalSign><resultDateTime value=\"19991114\"/><resultType code=\"8462-4\" codeSystem=\"2.16.840.1.113883.6.1\" codeSystemName=\"LOINC\" displayName=\"Diastolic BP\"/><resultValue><physicalQuantity value=\"86\" unit=\"mm[Hg]\"/></resultValue><resultInterpretation nullFlavor=\"UNK\"/></vitalSign></vitalSigns><procedures><procedure><procedureType code=\"52734007\" codeSystem=\"2.16.840.1.113883.6.96\" codeSystemName=\"SNOMED\" displayName=\"Total hip replacement\"><originalText>Total hip replacement</originalText></procedureType><procedureDateTime value=\"1998\"/><procedureProvider><providerName><family>Dolin</family><given>Robert</given><prefix>Dr.</prefix></providerName><providerOrganizationName/></procedureProvider></procedure></procedures><conditions><condition><problemDate nullFlavor=\"UNK\"><low nullFlavor=\"UNK\"/><high nullFlavor=\"UNK\"/></problemDate><diagnosisPriority>1</diagnosisPriority><problemType code=\"64572001\" codeSystem=\"2.16.840.1.113883.6.96\" codeSystemName=\"SNOMED-CT\" displayName=\"Condition\"/><problemCode code=\"195967001\" codeSystem=\"2.16.840.1.113883.6.96\" codeSystemName=\"SNOMED-CT\" displayName=\"Asthma\"/><problemStatus code=\"55561003\" codeSystem=\"2.16.840.1.113883.6.96\" codeSystemName=\"SNOMED-CT\" displayName=\"Active\"/><causeOfDeath><timeOfDeath value=\"20101010\"/><problemCode code=\"195967001\" codeSystem=\"2.16.840.1.113883.6.96\" codeSystemName=\"SNOMED-CT\" displayName=\"Asthma\"/></causeOfDeath></condition></conditions><planOfCare><plannedObservation><planType code=\"23426006\" codeSystem=\"2.16.840.1.113883.6.96\" displayName=\"Pulmonary function test\"/><plannedTime value=\"20101010\"/></plannedObservation><plannedObservation><planType code=\"268523001\" codeSystem=\"2.16.840.1.113883.6.96\" displayName=\"BMI Management\"/><plannedTime value=\"20101010\"/></plannedObservation><plannedObservation><planType code=\"103699006\" codeSystem=\"2.16.840.1.113883.6.96\" displayName=\"dietary consultation order\"/><plannedTime value=\"20101010\"/></plannedObservation></planOfCare><socialHistory><socialHistoryEntry><socialHistoryType code=\"266924008\" codeSystem=\"2.16.840.1.113883.6.96\" codeSystemName=\"SNOMED CT\" displayName=\"ex-heavy cigarette smoker (20-39/day)\"/><socialHistoryTime><low value=\"20101010\"/><high value=\"20101010\"/></socialHistoryTime></socialHistoryEntry></socialHistory></body></greenCCD>";
		// Read c32 file from resource
		final String c32FileUri = "c32.xml";
		final String c32Xml = ResourceFileReader
				.getStringFromResourceFile(c32FileUri);
		when(
				xmlTransformer.transform(eq(c32Xml), anyString(),
						any(Optional.class), any(Optional.class))).thenReturn(
				greenCcdToReturn);

		// Act
		final String greenCcd = sut.TransformC32ToGreenCcd(c32Xml);

		// Assert
		final String greenCcdFileUri = "green_ccd_transformed_from_c32.xml";
		final String expectedGreenCcd = ResourceFileReader
				.getStringFromResourceFile(greenCcdFileUri);

		XMLUnit.setIgnoreWhitespace(true);
		XMLUnit.setNormalizeWhitespace(true);

		assertXMLEqual("", expectedGreenCcd, greenCcd);
	}

	private XmlTransformer createXmlTransformer() {
		return new XmlTransformerImpl(new SimpleMarshallerImpl());
	}
}
