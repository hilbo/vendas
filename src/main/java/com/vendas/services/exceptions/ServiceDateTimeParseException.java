package com.vendas.services.exceptions;

public class ServiceDateTimeParseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ServiceDateTimeParseException(String msg) {
		super(msg);
	}
}
