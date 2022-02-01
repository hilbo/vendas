package com.vendas.services;

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
import com.vendas.repositories.OrderStatusRepository;
import com.vendas.services.exceptions.ServiceDataIntegrityViolationException;
import com.vendas.services.exceptions.ServiceNotFoundException;
import com.vendas.tools.validatories.Validator;
import com.vendas.tools.validatories.implementations.ValidateOrderItem;

@Service
public class OrderItemService {
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired
	private OrderService orderService  ;
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderStatusRepository orderStatusRepository;
	private ValidateOrderItem validateOrderItem = new ValidateOrderItem();
	@Qualifier
	private Validator validator = validateOrderItem;
	
	public OrderItemService() {
	}
	
	public List<OrderItem> findAll(){
		return orderItemRepository.findAll();
	}
	
	public OrderItem findById(Long id) {
		OrderItem orderItem;
		try {
			orderItem = orderItemRepository.findById(id).get();
			orderItem.setOrderId(orderItem.getId());
		} catch (NoSuchElementException e) {
			throw new ServiceNotFoundException("Item de pedido não encontrado !!");
		}
		return orderItem;
	}
	
	public OrderItem insert(OrderItem orderItem, HttpServletRequest request) {
		return orderItemRepository.save(remountOrderItem(orderItem, request));
	}

	private OrderItem remountOrderItem(OrderItem orderItem, HttpServletRequest request) {
		validator.valid(orderItem , request);
		orderItem.setProduct(productService.findById(orderItem.getProduct().getId()));
		orderItem.setPrice(orderItem.getProduct().getPrice());
		orderItem.setSubtotal();
		Order order = (orderService.findById(orderItem.getOrderId()));
		orderItem.setOrder(order);
		order.setOrderStatus(orderStatusRepository.findById(2L).get());
		orderService.update(order);
		return orderItem;
	}
	
	public void delete(Long id) {
		try {
			orderItemRepository.delete(findById(id));
		} catch (DataIntegrityViolationException e) {
			throw new ServiceDataIntegrityViolationException("Não foi possível excluir o Item de pedido !!");
		}
	}
}
