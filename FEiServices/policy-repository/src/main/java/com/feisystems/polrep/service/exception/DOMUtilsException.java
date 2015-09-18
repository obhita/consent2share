package com.feisystems.polrep.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class DOMUtilsException extends RuntimeException {

	private static final long serialVersionUID = -1683604179956248526L;

	public DOMUtilsException() {
		super();
	}

	public DOMUtilsException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DOMUtilsException(String message, Throwable cause) {
		super(message, cause);
	}

	public DOMUtilsException(String message) {
		super(message);
	}

	public DOMUtilsException(Throwable cause) {
		super(cause);
	}
}
