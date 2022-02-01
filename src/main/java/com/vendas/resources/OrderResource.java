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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vendas.entities.Order;
import com.vendas.services.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderResource {

	@Autowired
	private OrderService orderService;
			
	@GetMapping
	public ResponseEntity<List<Order>> orderList(){
		return ResponseEntity.ok().body(orderService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Order> findById(@PathVariable Long id){
		return ResponseEntity.ok().body(orderService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<Order> insert(@RequestBody Order order, HttpServletRequest request){
		order = orderService.insert(order, request);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + order.getId()).build().toUri();
		return ResponseEntity.created(uri).body(order);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Order> delete(@PathVariable Long id){
		orderService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/search/datetime")
	public ResponseEntity<List<Order>> findByDateTime(@RequestParam String dateTimeInitial, @RequestParam String dateTimeFinal){
			return ResponseEntity.ok().body(orderService.findPerDateTime(dateTimeInitial, dateTimeFinal));
	}
	
	@GetMapping("/search/datetimeclient")
	public ResponseEntity<List<Order>> findPerNameAndDate(@RequestParam String dateTimeInitial, @RequestParam String dateTimeFinal, @RequestParam String clientName){
			return ResponseEntity.ok().body(orderService.findPerNameAndDate(dateTimeInitial, dateTimeFinal, clientName));
	}
	
	@GetMapping("/clearorderobsolet")
	public ResponseEntity<Void> limpa(@RequestParam Integer quantityDays ){
		orderService.clearOrderObsolet(quantityDays);
		return ResponseEntity.noContent().build();
	}
}
