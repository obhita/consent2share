package gov.samhsa.acs.contexthandler.exception;

import gov.samhsa.acs.common.exception.DS4PException;

public class NoPolicyFoundException extends DS4PException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NoPolicyFoundException(String message)
	{
		super(message);
	}
}
