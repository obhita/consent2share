package gov.samhsa.consent2share.showcase.service;

public class ProviderDto {
	private String name;
	private String email;
	
	public ProviderDto() {}

	public ProviderDto(String email) {
		super();
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
