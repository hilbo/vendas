package com.vendas.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.vendas.entities.Order;
import com.vendas.entities.OrderItem;
import com.vendas.repositories.OrderItemRepository;
import com.vendas.repositories.OrderRepository;
import com.vendas.repositories.OrderStatusRepository;
import com.vendas.repositories.UserRepository;
import com.vendas.services.exceptions.ServiceDataIntegrityViolationException;
import com.vendas.services.exceptions.ServiceDateTimeException;
import com.vendas.services.exceptions.ServiceDateTimeParseException;
import com.vendas.services.exceptions.ServiceNotFoundException;
import com.vendas.tools.validatories.Validator;
import com.vendas.tools.validatories.implementations.ValidateOrder;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderStatusRepository orderStatusRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OrderItemRepository orderItenRepository;
	@Autowired
	private ValidateOrder validateOrder = new ValidateOrder();
	@Qualifier
	private Validator validator = validateOrder;
			
	public OrderService() {
	}
	
	public List<Order> findAll(){
		return orderRepository.findAll();
	}
	
	public Order findById(Long id) {
		try {
			orderRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ServiceNotFoundException("Pedido não encontrado !!");
		}
		return orderRepository.findById(id).get();
	}
	
	public Order insert(Order order, HttpServletRequest request) {
		order.setClient(userRepository.findById(order.getClient().getId()).get());
		order.setMoment(LocalDateTime.now());
		order.setOrderStatus(orderStatusRepository.findById(1L).get());
		return orderRepository.save(order);
		}
	
	public Order update(Order order) {
		return orderRepository.save(order);
	}
	
	public void delete(Long id) {
		try {
			orderRepository.delete(orderRepository.getById(id));
		} catch (DataIntegrityViolationException e) {
			throw new ServiceDataIntegrityViolationException("Não foi possível excluir o pedido !!!");
		}
	}
	
	public List<Order> findPerDateTime(String dateInitial, String dateFinal){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		List<Order> orders = new ArrayList<>();
		try {
			LocalDateTime momentInitial = LocalDateTime.parse(dateInitial, dtf);
			LocalDateTime momentFinal = LocalDateTime.parse(dateFinal, dtf);
			if (momentInitial.isAfter(momentFinal)) {
				throw new ServiceDateTimeException("A data inicial não poder superior a data final !");
			}
			orders.addAll(orderRepository.findPerDateTime(momentInitial, momentFinal));
		} catch (DateTimeParseException e) {
			throw new ServiceDateTimeParseException("Formato de data e hora inválidos !");
		}catch (ServiceDateTimeException e) {
			throw new ServiceDateTimeException(e.getMessage());
		}
		return orders;
	}
	
	public List<Order> findPerNameAndDate(String dateInitial, String dateFinal, String clientName){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		List<Order> orders = new ArrayList<>();
		try {
			LocalDateTime momentInitial = LocalDateTime.parse(dateInitial, dtf);
			LocalDateTime momentFinal = LocalDateTime.parse(dateFinal, dtf);
			if (momentInitial.isAfter(momentFinal)) {
				throw new ServiceDateTimeException("A data inicial não poder superior a data final !");
			}
			orders.addAll(orderRepository.findPerNameAndDate(momentInitial, momentFinal, clientName));
		} catch (DateTimeParseException e ) {
			throw new ServiceDateTimeParseException("Formato de data e hora inválidos !");
		}catch (ServiceDateTimeException e) {
			throw new ServiceDateTimeException(e.getMessage());
		}
		return orders;
	}
		
	public void clearOrderObsolet(Integer quantityDays) {
		LocalDateTime moment = LocalDateTime.now().minusDays(quantityDays);
		List<Order> orders = new ArrayList<>();
		orders.addAll(orderRepository.clearOrderObsolet(moment));
		for (Order order : orders) {
			for (OrderItem orderItem : order.getOrderItem()) {
				orderItenRepository.delete(orderItenRepository.findById(orderItem.getId()).get());
			}
		}
		orderRepository.deleteAll(orders);
	}
}
