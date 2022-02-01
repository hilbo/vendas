package com.vendas.services;

import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vendas.entities.Order;
import com.vendas.entities.Payment;
import com.vendas.repositories.OrderRepository;
import com.vendas.repositories.OrderStatusRepository;
import com.vendas.repositories.PaymentRepository;
import com.vendas.services.exceptions.ServiceNotFoundException;
import com.vendas.tools.validatories.implementations.ValidateOrder;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentRepository paymentRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderStatusRepository orderStatusRepository;
	
	@Autowired
	private ValidateOrder validateOrder;
		
	public PaymentService() {
	}
	
	public List<Payment> findAll(){
		return paymentRepository.findAll();
	}
	
	public Payment findById(Long id) {
		Payment payment;
		try {
			payment = paymentRepository.findById(id).get();
			} catch (NoSuchElementException e) {
			throw new ServiceNotFoundException("Pagamento não encontrada !!");
		}
		return payment;
	}
				
	public Payment insert(Payment payment, HttpServletRequest request) {
		Order order = orderRepository.findById(payment.getOrderCod()).get();
		validateOrder.validOrderStatusForPayment(order, request);
		payment.setOrder(order);
		payment = paymentRepository.save(payment);
		order.setPayment(payment);
		order.setOrderStatus(orderStatusRepository.findById(3L).get());
		orderRepository.save(order);
		return payment;
	}
}
