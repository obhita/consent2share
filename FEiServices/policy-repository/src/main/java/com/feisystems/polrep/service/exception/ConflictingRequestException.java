package com.feisystems.polrep.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictingRequestException extends RuntimeException {
	
	private static final long serialVersionUID = 1926212369879484996L;

	public ConflictingRequestException() {
		super();
	}

	public ConflictingRequestException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ConflictingRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConflictingRequestException(String message) {
		super(message);
	}

	public ConflictingRequestException(Throwable cause) {
		super(cause);
	}
}
