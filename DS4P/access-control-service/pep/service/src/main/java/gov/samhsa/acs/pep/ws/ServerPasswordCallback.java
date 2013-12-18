package gov.samhsa.acs.pep.ws;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.ws.security.WSPasswordCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerPasswordCallback implements CallbackHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServerPasswordCallback.class);
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {

		for (int i = 0; i < callbacks.length; i++) {

			WSPasswordCallback pc = (WSPasswordCallback) callbacks[i];
			
			LOGGER.debug("pc.getUsage(): " + pc.getUsage());
			LOGGER.debug("pc.getIdentifier(): " + pc.getIdentifier());
			if (pc.getUsage() == WSPasswordCallback.SIGNATURE
					|| pc.getUsage() == WSPasswordCallback.DECRYPT)
				

				if (pc.getIdentifier().equals("securedPepService"))
					pc.setPassword("fei123");
		}

	}
}
