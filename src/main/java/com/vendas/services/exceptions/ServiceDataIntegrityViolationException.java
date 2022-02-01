package com.vendas.services.exceptions;

public class ServiceDataIntegrityViolationException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ServiceDataIntegrityViolationException(String msg) {
		super(msg);
	}
}
