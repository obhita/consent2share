package gov.samhsa.consent2share.c32;

import gov.samhsa.consent2share.c32.dto.GreenCCD;

import org.junit.Assert;
import org.junit.Test;

public class GreenCcdSerializerImplTest {

	@Test(expected = IllegalArgumentException.class)
	public void testDeserialize_Given_Null_GreenCcdXml_Throws_IllegalArgumentException() throws Throwable {
		// Arrange
		GreenCcdSerializerImpl sut = new GreenCcdSerializerImpl();
		
		// Act
		sut.Deserialize(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDeserialize_Given_Empty_GreenCcdXml_Throws_IllegalArgumentException() throws Throwable {
		// Arrange
		GreenCcdSerializerImpl sut = new GreenCcdSerializerImpl();
		
		// Act
		sut.Deserialize("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDeserialize_Given_NoText_GreenCcdXml_Throws_IllegalArgumentException() throws Throwable {
		// Arrange
		GreenCcdSerializerImpl sut = new GreenCcdSerializerImpl();
		
		// Act
		sut.Deserialize(" ");
	}

	@Test
	public void testDeserialize_Given_Valid_GreenCcd_Succeeds() throws Throwable {
		// Arrange
		GreenCcdSerializerImpl sut = new GreenCcdSerializerImpl();

		// Read green ccd file from resource
		final String greenCcdXmlFileUri = "green_ccd_2.xml";
		String greenCcdXml = ResourceFileReader.getStringFromResourceFile(greenCcdXmlFileUri);

		// Act
		GreenCCD greenCcd = sut.Deserialize(greenCcdXml);

		// Assert
		Assert.assertEquals("2.16.840.1.113883.3.72", greenCcd.getHeader().getDocumentID().getRoot());
		
		Assert.assertEquals("streetAddressLine", greenCcd.getHeader().getPersonalInformation().getPatientInformation().getPersonAddress().get(0).getStreetAddressLine().get(0));
		Assert.assertEquals("city", greenCcd.getHeader().getPersonalInformation().getPatientInformation().getPersonAddress().get(0).getCity());
		Assert.assertEquals("state", greenCcd.getHeader().getPersonalInformation().getPatientInformation().getPersonAddress().get(0).getState());
		Assert.assertEquals("postalCode", greenCcd.getHeader().getPersonalInformation().getPatientInformation().getPersonAddress().get(0).getPostalCode());
		
		Assert.assertEquals("Levin", greenCcd.getHeader().getPersonalInformation().getPatientInformation().getPersonInformation().get(0).getPersonName().get(0).getFamily());
		Assert.assertEquals("Henry", greenCcd.getHeader().getPersonalInformation().getPatientInformation().getPersonInformation().get(0).getPersonName().get(0).getGiven());
		
		Assert.assertEquals("family", greenCcd.getHeader().getInformationSource().getAuthor().getAuthorName().getFamily());
		Assert.assertEquals("given", greenCcd.getHeader().getInformationSource().getAuthor().getAuthorName().getGiven());
		
		Assert.assertEquals("Hospital", greenCcd.getHeader().getInformationSource().getInformationSourceName().getOrganizationName().getContent());
		
		Assert.assertEquals(1, greenCcd.getBody().getResults().getResult().size());
		Assert.assertEquals("M 13-18 g/dl; F 12-16 g/dl", greenCcd.getBody().getResults().getResult().get(0).getResultReferenceRange().get(0));
		
		Assert.assertEquals(1, greenCcd.getBody().getEncounters().getEncounter().size());
		Assert.assertEquals("Dolin", greenCcd.getBody().getEncounters().getEncounter().get(0).getEncounterProvider().get(0).getProviderName().getFamily());
		Assert.assertEquals("Good Health Clinic", greenCcd.getBody().getEncounters().getEncounter().get(0).getEncounterProvider().get(0).getProviderOrganizationName().getContent());
		
		Assert.assertEquals(1, greenCcd.getBody().getMedications().getMedication().size());
		Assert.assertEquals("This is the text of the Patient Instruction. Note that this instruction is printed in the narrative text of the parent Section and is refereced by the following pointer to it.", greenCcd.getBody().getMedications().getMedication().get(0).getPatientInstructions().trim());
		Assert.assertEquals("Don't forget your medication", greenCcd.getBody().getMedications().getMedication().get(0).getFulfillmentInstructions().trim());
	}
	
	@Test(expected=GreenCcdSerializerException.class)
	public void testDeserialize_Given_InValid_GreenCcd_Throws_GreenCcdSerializerException() throws Throwable {
		// Arrange
		GreenCcdSerializerImpl sut = new GreenCcdSerializerImpl();
		String greenCcdXml = "<Invalid>GreenCcd</Invalid>";

		// Act
		GreenCCD greenCcd = sut.Deserialize(greenCcdXml);
	}
}
