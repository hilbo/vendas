package com.vendas.services.exceptions;

public class ServiceDateTimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ServiceDateTimeException(String msg) {
		super(msg);
	}
}
