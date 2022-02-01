package com.vendas.services.exceptions;

public class ServiceValidationException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ServiceValidationException(String msg) {
		super(msg);
	}
}
