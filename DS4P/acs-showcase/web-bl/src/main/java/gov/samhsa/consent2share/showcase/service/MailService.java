package gov.samhsa.consent2share.showcase.service;

public interface MailService {
	public void sendMailOnFailure(ProviderDto senderProvider, ProviderDto receiverProvider, String message);
}
