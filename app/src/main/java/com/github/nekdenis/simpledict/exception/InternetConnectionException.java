package com.github.nekdenis.simpledict.exception;

public class InternetConnectionException extends RuntimeException {
	
	public InternetConnectionException() {
		super();
	}
	
	public InternetConnectionException(String message) {
		super(message);
	}

	public InternetConnectionException(String message, Throwable cause) {
		super(message);
		initCause(cause);
	}

	@Override
	public String toString() {
		return getMessage();
	}
}
