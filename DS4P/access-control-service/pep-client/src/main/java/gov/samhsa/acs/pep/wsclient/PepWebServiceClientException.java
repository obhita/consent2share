package gov.samhsa.acs.pep.wsclient;

import gov.samhsa.acs.common.exception.DS4PException;

public class PepWebServiceClientException extends DS4PException {

	private static final long serialVersionUID = 3393738597752682011L;

	public PepWebServiceClientException() {
		super();
	}

	public PepWebServiceClientException(String arg0) {
		super(arg0);
	}

	public PepWebServiceClientException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public PepWebServiceClientException(Throwable arg0) {
		super(arg0);
	}
}
