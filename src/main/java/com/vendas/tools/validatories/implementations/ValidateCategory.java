package com.vendas.tools.validatories.implementations;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.vendas.entities.Category;
import com.vendas.resources.exceptions.SpecificError;
import com.vendas.services.exceptions.ServiceNotFoundException;
import com.vendas.services.exceptions.ServiceValidationException;
import com.vendas.tools.validatories.Validator;

@Component
public class ValidateCategory implements Validator {
	
	@Override
	public <T> void valid(T entitie, T request) {
		List<SpecificError> errors = new ArrayList<>();
		HttpServletRequest requestTmp = (HttpServletRequest) request;
		Category category = (Category) entitie;

		if (category.getName() == null || category.getName().equals("")) {
			SpecificError specificError = new SpecificError();
			specificError.setField("name");
			specificError.setMessage("O nome não pode ser branco ou nulo !!!!!");
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
