package gov.samhsa.acs.pep.security;

public class CredentialProviderImpl implements CredentialProvider {
    private static String username = "alice";
    private static String password = "clarinet";

    @Override
	public String getUsername() {
		return username;
	}

    @Override
    public String getPassword() {
        return password;
    }
}
