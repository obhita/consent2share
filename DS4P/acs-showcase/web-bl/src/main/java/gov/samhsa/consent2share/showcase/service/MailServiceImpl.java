package gov.samhsa.consent2share.showcase.service;

import gov.samhsa.consent2share.showcase.exception.AcsShowCaseException;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailServiceImpl implements MailService {

	private Properties properties;
	private MailDto mailDto;
	private String errorMessage;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());


	public MailServiceImpl() {
		try {
			//props.load(new FileInputStream("mail.properties"));
			properties = new Properties();
			properties.load(this.getClass().getClassLoader().getResourceAsStream("mail.properties"));
			
			mailDto = new MailDto();
			mailDto.setImapServerHost(properties.getProperty("imapServerHost"));
			mailDto.setImapPort(properties.getProperty("imapPort"));
			mailDto.setSmtpServerHost(properties.getProperty("smtpServerHost"));
			mailDto.setSmptPort(properties.getProperty("smtpPort"));
			mailDto.setUsername(properties.getProperty("username"));
			mailDto.setPassword(properties.getProperty("password"));
		} catch (IOException ex) {
			logger.error("Cannot load properties");
			ex.printStackTrace();
		}
	}

	@Override
	public void sendMailOnFailure(ProviderDto senderProvider, ProviderDto receiverProvider, String message) {
		String to = senderProvider.getEmail();
		String from = "admin@direct.obhita-stage.org";

		// Create properties, get Session
		Properties props = new Properties();

		// If using static Transport.send(),
		// need to specify which host to send it to
		props.put("mail.smtp.host", mailDto.getSmtpServerHost());
		// To see what is going on behind the scene
		props.put("mail.debug", "true");
		props.setProperty("mail.store.protocol", "imap");

		Session session = Session.getInstance(props);
		try {
			Store store = session.getStore("imap");

			store.connect(mailDto.getImapServerHost(),
					mailDto.getUsername(), mailDto.getPassword());

			logger.debug("Store: " + store);			
			logger.debug("Username: " + mailDto.getUsername());
			logger.debug("Password: " + mailDto.getPassword());

			// Instantiate a message
			Message msg = new MimeMessage(session);

			// Set message attributes
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] address = { new InternetAddress(to) };
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject("Error with segmenting c32 Document");
			msg.setSentDate(new Date());
			msg.setText(message);

			Folder[] f = store.getDefaultFolder().list();
			for (Folder fd : f) {
				logger.debug(">> " + fd.getName());

				// Store message in sent folder
				if (fd.getName().equals("INBOX")) {
					fd.appendMessages(new Message[] { msg });
				}
			}
			// Send the message
			// Transport.send(msg);
		} catch (MessagingException mex) {
			// Prints all nested (chained) exceptions as well
			mex.printStackTrace();
		}
	}
	

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
