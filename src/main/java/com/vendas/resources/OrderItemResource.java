package com.vendas.resources;


import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vendas.entities.OrderItem;
import com.vendas.services.OrderItemService;

@RestController
@RequestMapping("/ordersitens")
public class OrderItemResource {

	@Autowired
	private OrderItemService orderItemService;
		
	@GetMapping
	public ResponseEntity<List<OrderItem>> findAll(){
		return ResponseEntity.ok().body(orderItemService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id, HttpServletRequest request){
		return ResponseEntity.ok().body(orderItemService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<OrderItem> insert(@RequestBody OrderItem orderItem, HttpServletRequest request ){
		orderItem = orderItemService.insert(orderItem, request);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + orderItem.getId()).build().toUri();
		return ResponseEntity.created(uri).body(orderItem);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<OrderItem> delete(@PathVariable Long id){
		orderItemService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
	
	