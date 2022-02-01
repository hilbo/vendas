package com.vendas.resources.exceptions;

import java.io.Serializable;
import org.springframework.stereotype.Component;

@Component
public class SpecificError implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private String message;
    private String field;
    
    public SpecificError() {
    }
    
	public SpecificError(String message, String field) {
		this.message = message;
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

}
