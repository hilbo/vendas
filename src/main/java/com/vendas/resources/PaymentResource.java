package com.vendas.resources;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vendas.entities.Payment;
import com.vendas.services.PaymentService;


@RestController
@RequestMapping("/payments")
public class PaymentResource {

	@Autowired
	private PaymentService paymentService;
	@Autowired
		
	@GetMapping
	public ResponseEntity<List<Payment>> findAll(){
		return ResponseEntity.ok().body(paymentService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Payment> findById(@PathVariable Long id){
		return ResponseEntity.ok().body(paymentService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<Object> insert(@RequestBody Payment payment, HttpServletRequest request){
		payment = paymentService.insert(payment, request);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
		return ResponseEntity.created(uri).body(payment);
	}
	
}
