package com.vendas.tools.validatories.implementations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.vendas.entities.User;
import com.vendas.resources.exceptions.SpecificError;
import com.vendas.services.exceptions.ServiceNotFoundException;
import com.vendas.services.exceptions.ServiceValidationException;
import com.vendas.tools.validatories.Validator;

public class ValidateUser implements Validator {

	public ValidateUser() {
	}
	
	@Override
	public <T> void valid(T entitie, T request) {
		List<SpecificError> errors = new ArrayList<>();
		HttpServletRequest requestTmp = (HttpServletRequest) request;
		User user = (User) entitie;

		if (user.getName() == null || user.getName().equals("")) {
			SpecificError specificError = new SpecificError();
			specificError.setField("name");
			specificError.setMessage("O nome não pode ser branco ou nulo !!");
			errors.add(specificError);
		}
		if (user.getPhone() == null || user.getPhone().equals("")) {
			SpecificError specificError = new SpecificError();
			specificError.setField("phone");
			specificError.setMessage("O telefone não pode ser branco ou nulo !!");
			errors.add(specificError);
		}
		
		
		if (user.getPassword() == null || user.getPassword().equals("")) {
			SpecificError specificError = new SpecificError();
			specificError.setField("password");
			specificError.setMessage("A senha não pode ser branco ou nulo !!");
			errors.add(specificError);
		}
		if (user.getBirthday() == null || user.getBirthday().toString().equals("")) {
			SpecificError specificError = new SpecificError();
			specificError.setField("birthday");
			specificError.setMessage("A data de nascimento não pode ser branco ou nulo !!");
			errors.add(specificError);
		}
		if (user.getEmail() == null || user.getEmail().equals("")) {
			SpecificError specificError = new SpecificError();
			specificError.setField("email");
			specificError.setMessage("O email não pode ser branco ou nulo !!");
			errors.add(specificError);
		}
		if (!errors.isEmpty()) {
			requestTmp.setAttribute("errors", errors);
			throw new ServiceValidationException("");
		}
	}
	
	public void ValidAge(LocalDateTime birthDay, HttpServletRequest request) {
		List<SpecificError> errors = new ArrayList<>();
		HttpServletRequest requestTmp = (HttpServletRequest) request;
		LocalDateTime correntDate = LocalDateTime.now();		
		Integer age = 0;
		age = correntDate.getYear() - birthDay.getYear();
		if (age < 18) {
			SpecificError specificError = new SpecificError();
			specificError.setMessage("A idade não pode ser menor que 18 anos !");
			specificError.setField("birthDay");
			errors.add(specificError);
		}
		
		if (!errors.isEmpty()) {
			requestTmp.setAttribute("errors", errors);
			throw new ServiceValidationException("");
		}
	}
	
	public void validList(List<?> list){
		if (list.isEmpty()) {
			throw new ServiceNotFoundException("Não foram encontrados resultados para a pesquisa");
		}
	}
}
