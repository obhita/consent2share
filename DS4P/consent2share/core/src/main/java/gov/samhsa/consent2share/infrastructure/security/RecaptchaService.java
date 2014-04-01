package gov.samhsa.consent2share.infrastructure.security;

import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RecaptchaService implements InitializingBean{
	@Value("${recaptchaHttpsServer}")
	private String httpsServer;
	@Value("${recaptchaHttpServer}")
	private String httpServer;
	@Value("${recaptchaVerificationServer}")
	private String verifyURL;
	@Value("${recaptchaPrivateKey}")
	private String privateKey;
	@Value("${recaptchaPublicKey}")
	private String publicKey;
	
	private ReCaptchaImpl reCaptchaImpl;
	
	@SuppressWarnings("unused")
	private RecaptchaService() {
	}
	
	public RecaptchaService(String httpsServer, String httpServer,
			String verifyURL, String privateKey, String publicKey,
			ReCaptchaImpl reCaptchaImpl) {
		super();
		this.httpsServer = httpsServer;
		this.httpServer = httpServer;
		this.verifyURL = verifyURL;
		this.privateKey = privateKey;
		this.publicKey = publicKey;
		this.reCaptchaImpl = reCaptchaImpl;
	}

	public String createSecureRecaptchaHtml() {
		return reCaptchaImpl.createRecaptchaHtml(null, null);
	}
	
	public boolean checkAnswer(String remoteAddr, String challenge, String uresponse) {
		ReCaptchaResponse reCaptchaResponse = reCaptchaImpl.checkAnswer(remoteAddr, challenge, uresponse);
		return reCaptchaResponse.isValid();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		reCaptchaImpl=(ReCaptchaImpl)ReCaptchaFactory.newSecureReCaptcha(publicKey, privateKey, false);
		reCaptchaImpl.setRecaptchaServer(httpsServer);
	}
	
	
}
