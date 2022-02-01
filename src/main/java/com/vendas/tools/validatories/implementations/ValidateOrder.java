package com.vendas.tools.validatories.implementations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.vendas.entities.Order;
import com.vendas.resources.exceptions.SpecificError;
import com.vendas.services.exceptions.ServiceValidationException;
import com.vendas.tools.validatories.Validator;
@Component
public class ValidateOrder implements Validator {

	public ValidateOrder() {
	}
	
	@Override
	public <T> void valid(T entitie, T request) {
		List<SpecificError> errors = new ArrayList<>();
		HttpServletRequest requestTmp = (HttpServletRequest) request;
		Order order = (Order) entitie;

		if (order.getMoment() == null || order.getMoment().toString().equals("")) {
			SpecificError specificError = new SpecificError();
			specificError.setField("moment");
			specificError.setMessage("A data não pode ser branco ou nulo !!");
			errors.add(specificError);
		}
		if (order.getClient() == null || order.getClient().toString().equals("")) {
			SpecificError specificError = new SpecificError();
			specificError.setField("client");
			specificError.setMessage("O clinte não pode ser branco ou nulo !!");
			errors.add(specificError);
		}
		if (order.getOrderStatus() == null || order.getOrderStatus().toString().equals("")) {
			SpecificError specificError = new SpecificError();
			specificError.setField("orderstatus");
			specificError.setMessage("O status não pode ser branco ou nulo !!");
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
	
	public void validOrderStatusForPayment(Order order, HttpServletRequest request){
		List<SpecificError> errors = new ArrayList<>();
		HttpServletRequest requestTmp = (HttpServletRequest) request;
		Long id = order.getOrderStatus().getId();
		if (id == 3 ) {
			SpecificError specificError = new SpecificError();
			specificError.setMessage("Pedido já pago !");
			specificError.setField("orderStatus");
			errors.add(specificError);
		}
		if (!errors.isEmpty()) {
			requestTmp.setAttribute("errors", errors);
			throw new ServiceValidationException("");
		}
	}
}
