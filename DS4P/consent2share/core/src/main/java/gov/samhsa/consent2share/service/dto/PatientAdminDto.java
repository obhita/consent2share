package gov.samhsa.consent2share.service.dto;

public class PatientAdminDto {

	/** The first name. */
	private String firstName;
	
	/** The last name. */
	private String lastName;

	/** The id. */
	private Long id;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
