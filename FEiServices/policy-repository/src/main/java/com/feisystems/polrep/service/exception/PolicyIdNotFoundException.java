package com.feisystems.polrep.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class PolicyIdNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5542092869875099257L;

	public PolicyIdNotFoundException() {
		super();
	}

	public PolicyIdNotFoundException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PolicyIdNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PolicyIdNotFoundException(String message) {
		super(message);
	}

	public PolicyIdNotFoundException(Throwable cause) {
		super(cause);
	}
}
