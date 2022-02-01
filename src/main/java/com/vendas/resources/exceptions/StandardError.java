package com.vendas.resources.exceptions;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class StandardError implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private LocalDateTime timestamp;;
    private Integer status; 
    private String error;
    private String path;
    private String defaultMessage;
    public List<SpecificError> errors = new ArrayList<>();
       
    public StandardError() {
    }

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String getDefaultMessage() {
		return defaultMessage;
	}

	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}

	public List<SpecificError> getErrors() {
		return errors;
	}
	
}
