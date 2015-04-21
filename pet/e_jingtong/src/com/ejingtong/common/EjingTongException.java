package com.ejingtong.common;

public class EjingTongException extends RuntimeException {

	public EjingTongException(String message) {
		super(message);
	}

	public EjingTongException(String message, Exception e) {
		super(message, e);
	}

	public EjingTongException(String message, Throwable e) {
		super(message, e);
	}
}
