package gov.samhsa.consent2share.showcase.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EhrNumOneDtoTest {

	private PatientDto[] patients;
		
	@Test
	public void testEhrNumOneDto() {
		//Arrange
		List<PatientDto> patientList = new ArrayList<PatientDto>();
		
		PatientDto firstPatient= new PatientDto();
		firstPatient.setId("PUI100010060001");
		firstPatient.setFullName("Asample Patientone");
		patientList.add(firstPatient);
		
		PatientDto secondPatient= new PatientDto();
		secondPatient.setId("PUI100010060007");
		secondPatient.setFullName("Asample Patienttwo");
		patientList.add(secondPatient);
		
		PatientDto thirdPatient= new PatientDto();
		thirdPatient.setId("PUI100015060013");
		thirdPatient.setFullName("Asample Patientthree");
		patientList.add(thirdPatient);
		
		PatientDto fourthPatient= new PatientDto();
		fourthPatient.setId("PUI100015060014");
		fourthPatient.setFullName("Asample Patienfour");
		patientList.add(fourthPatient);
		
		patients = patientList.toArray(new PatientDto[0]);
		
		//Act
		EhrNumOneDto sut =  new EhrNumOneDto();
		sut.setPatients(patients);
		sut.setSelectedPatient(thirdPatient);
		
		
		//Assert
		assertEquals(4, sut.getPatients().length);
		assertEquals(thirdPatient.getFullName(), sut.getSelectedPatient().getFullName());
		
	}



}
