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

import com.vendas.entities.Category;
import com.vendas.services.CategoryService;


@RestController
@RequestMapping("/categories")
public class CategoryResource {

	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<List<Category>> findAll(){
		return ResponseEntity.ok().body(categoryService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Category> findById(@PathVariable Long id){
		return ResponseEntity.ok().body(categoryService.findById(id));
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Category>> findByName(@PathVariable String name){
		return ResponseEntity.ok().body(categoryService.findByName(name));
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<Category>> findByName(@RequestParam String atribut, @RequestParam String value){
		return ResponseEntity.ok().body(categoryService.search(atribut, value));
	}
	
	@PostMapping
	public ResponseEntity<Object> insert(@RequestBody Category category, HttpServletRequest request){
		category = categoryService.insert(category, request);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUri();
		return ResponseEntity.created(uri).body(category);
	}
		
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id){
		categoryService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Category category,HttpServletRequest request){
		category = categoryService.update(id, category, request);
		return ResponseEntity.ok().body(category);
	}
}
