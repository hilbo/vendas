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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vendas.entities.Product;
import com.vendas.services.ProductService;


@RestController
@RequestMapping("/producties")
public class ProductResource {

	@Autowired
	private ProductService productService;
				
	@GetMapping
	public ResponseEntity<List<Product>> findAll(){
		return ResponseEntity.ok().body(productService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id){
		return ResponseEntity.ok().body(productService.findById(id));
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Product>> findByName(@PathVariable String name){
		return ResponseEntity.ok().body(productService.findByName(name));
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<Product>> search(@RequestParam String atribut, @RequestParam String value){
		return ResponseEntity.ok().body(productService.search(atribut, value));
	}
		
	@PostMapping
	public ResponseEntity<Product> insert(@RequestBody Product product, HttpServletRequest request){
		product = productService.insert(product, request);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + product.getId()).build().toUri();
		return ResponseEntity.created(uri).body(product);
	}
		
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id){
		productService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product Product,HttpServletRequest request){
		Product = productService.update(id, Product, request);
		return ResponseEntity.ok().body(Product);
	}
		
}
