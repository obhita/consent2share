package gov.samhsa.acs.pep.security;

public class CredentialProvider {
    private static String user = "alice";
    private static String password = "clarinet";

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        CredentialProvider.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        CredentialProvider.password = password;
    }
}
