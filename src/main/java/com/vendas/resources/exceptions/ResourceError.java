package com.vendas.resources.exceptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.vendas.services.exceptions.ServiceDataIntegrityViolationException;
import com.vendas.services.exceptions.ServiceDateTimeException;
import com.vendas.services.exceptions.ServiceDateTimeParseException;
import com.vendas.services.exceptions.ServiceNotFoundException;
import com.vendas.services.exceptions.ServiceValidationException;

@ControllerAdvice
public class ResourceError {
		
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> HttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request){
		StandardError error = new StandardError();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(400);
		error.setError("Bad Rquest");
		error.setDefaultMessage("Formato Json inválido");
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<?> HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request){
		StandardError error = new StandardError();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(400);
		error.setError("Bad Rquest");
		error.setDefaultMessage("O método informado não é válido");
		error.setPath(request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@SuppressWarnings("unchecked")
	@ExceptionHandler(ServiceValidationException.class)
	public ResponseEntity<StandardError> ServiceValidationException(ServiceValidationException e, HttpServletRequest request){
		StandardError error = new StandardError();
		List<SpecificError> specificErrors = (List<SpecificError>) request.getAttribute("errors");
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(400);
		error.setError("Bad Request");
		error.setPath(request.getRequestURI());
		error.setDefaultMessage("Erro de validação");
		for (SpecificError errorTmp : specificErrors) {
			error.getErrors().add(errorTmp);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<StandardError>> ResourcesMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request){
		List<StandardError> errors = new ArrayList<>();
			for (ObjectError errorTmp : e.getAllErrors()) {
				StandardError error = new StandardError();
				error.setTimestamp(LocalDateTime.now());
				error.setStatus(400);
				error.setError("Bad Request");
				error.setPath(request.getRequestURI());
				error.setDefaultMessage(errorTmp.getDefaultMessage());
				errors.add(error);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
	
	@ExceptionHandler(ServiceNotFoundException.class)
	public ResponseEntity<StandardError> ServiceNotFoundException(ServiceNotFoundException e, HttpServletRequest request){
		StandardError error = new StandardError();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(404);
		error.setError("Not Found");
		error.setPath(request.getRequestURI());
		error.setDefaultMessage(e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
		
	@ExceptionHandler(ServiceDataIntegrityViolationException.class)
	public ResponseEntity<?> ResourcesDataIntegrityViolationException(ServiceDataIntegrityViolationException e, HttpServletRequest request){
		StandardError error = new StandardError();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(400);
		error.setError("Bad Request");
		error.setPath(request.getRequestURI());
		error.setDefaultMessage(e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		}
	
	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<?> ResourcesNumberFormatException(NumberFormatException e, HttpServletRequest request){
		StandardError error = new StandardError();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(400);
		error.setError("Bad Request");
		error.setPath(request.getRequestURI());
		error.setDefaultMessage("O valor deve ser numérico !");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		}
	
	@ExceptionHandler(ServiceDateTimeException.class)
	public ResponseEntity<?> ResourcesServiceDateTimeException(ServiceDateTimeException e, HttpServletRequest request){
		StandardError error = new StandardError();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(400);
		error.setError("Bad Request");
		error.setPath(request.getRequestURI());
		error.setDefaultMessage(e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		}
	
	@ExceptionHandler(ServiceDateTimeParseException.class)
	public ResponseEntity<?> ResourcesDateTimeParseException(ServiceDateTimeParseException e, HttpServletRequest request){
		StandardError error = new StandardError();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(400);
		error.setError("Bad Request");
		error.setPath(request.getRequestURI());
		error.setDefaultMessage(e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<?> ResourcesMissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request){
		StandardError error = new StandardError();
		error.setTimestamp(LocalDateTime.now());
		error.setStatus(400);
		error.setError("Bad Request");
		error.setPath(request.getRequestURI());
		error.setDefaultMessage("Parâmetro inválido ou ausente" + " (" + e.getParameterName() + ")");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
		}
}

