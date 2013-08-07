package gov.samhsa.ds4ppilot.orchestrator.ws;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.ws.security.WSPasswordCallback;

public class ServerPasswordCallback implements CallbackHandler {

	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {

		for (int i = 0; i < callbacks.length; i++) {

			WSPasswordCallback pc = (WSPasswordCallback) callbacks[i];
			
			System.out.println("pc.getUsage(): " + pc.getUsage());
			System.out.println("pc.getIdentifier(): " + pc.getIdentifier());
			if (pc.getUsage() == WSPasswordCallback.SIGNATURE
					|| pc.getUsage() == WSPasswordCallback.DECRYPT)
				

				if (pc.getIdentifier().equals("securedOrchestratorService"))
					pc.setPassword("fei123");
		}

	}
}
