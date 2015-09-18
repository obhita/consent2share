package com.feisystems.polrep.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class PolicyAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 2695704534308861836L;

	public PolicyAlreadyExistsException() {
		super();
	}

	public PolicyAlreadyExistsException(String message) {
		super(message);
	}

	public PolicyAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public PolicyAlreadyExistsException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PolicyAlreadyExistsException(Throwable cause) {
		super(cause);
	}

}
