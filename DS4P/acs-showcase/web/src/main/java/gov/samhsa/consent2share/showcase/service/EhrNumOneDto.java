package gov.samhsa.consent2share.showcase.service;

import java.util.ArrayList;
import java.util.List;

public class EhrNumOneDto {
	private PatientDto[] patients;
	private PatientDto selectedPatient;
	
	public EhrNumOneDto() {
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
	}

	public PatientDto[] getPatients() {
		return patients;
	}

	public void setPatients(PatientDto[] patients) {
		this.patients = patients;
	}

	public PatientDto getSelectedPatient() {
		return selectedPatient;
	}

	public void setSelectedPatient(PatientDto selectedPatient) {
		this.selectedPatient = selectedPatient;
	}
	
}
