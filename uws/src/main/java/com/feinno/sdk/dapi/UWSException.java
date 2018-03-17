package com.feinno.sdk.dapi;

public class UWSException extends Exception {
	public UWSException() {
	}

	public UWSException(String name) {
		super(name);
	}

	public UWSException(String name, Throwable cause) {
		super(name, cause);
	}

	public UWSException(Exception cause) {
		super(cause);
	}
}
