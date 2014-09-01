package com.github.nekdenis.simpledict.exception;

public class ServerException extends RuntimeException {

	public ServerException() {
		super();
	}

	public ServerException(String message) {
		super(message);
	}

	public ServerException(String message, Throwable cause) {
		super(message);
		initCause(cause);
	}

	@Override
	public String toString() {
		return getMessage();
	}
}
