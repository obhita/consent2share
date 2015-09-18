package gov.samhsa.consent2share.showcase.service;

public class MailDto {
	private String SmtpServerHost;
	private String SmptPort;
	private String ImapServerHost;
	private String ImapPort;
	private String username;
	private String password;

	public String getSmtpServerHost() {
		return SmtpServerHost;
	}

	public void setSmtpServerHost(String smtpServerHost) {
		SmtpServerHost = smtpServerHost;
	}

	public String getSmptPort() {
		return SmptPort;
	}

	public void setSmptPort(String smptPort) {
		SmptPort = smptPort;
	}

	public String getImapServerHost() {
		return ImapServerHost;
	}

	public void setImapServerHost(String imapServerHost) {
		ImapServerHost = imapServerHost;
	}

	public String getImapPort() {
		return ImapPort;
	}

	public void setImapPort(String imapPort) {
		ImapPort = imapPort;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
