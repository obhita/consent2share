package com.feisystems.polrep.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PolicyNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8753419779647540367L;

	public PolicyNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PolicyNotFoundException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PolicyNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public PolicyNotFoundException(String message) {
		super(message);
	}

	public PolicyNotFoundException(Throwable cause) {
		super(cause);
	}
}
