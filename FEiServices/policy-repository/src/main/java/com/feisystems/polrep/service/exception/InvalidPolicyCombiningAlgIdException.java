package com.feisystems.polrep.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidPolicyCombiningAlgIdException extends RuntimeException {

	private static final long serialVersionUID = -4102481378552847566L;

	public InvalidPolicyCombiningAlgIdException() {
		super();
	}

	public InvalidPolicyCombiningAlgIdException(String message) {
		super(message);
	}

	public InvalidPolicyCombiningAlgIdException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPolicyCombiningAlgIdException(String message,
			Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidPolicyCombiningAlgIdException(Throwable cause) {
		super(cause);
	}

}
