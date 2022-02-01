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

import com.vendas.entities.User;
import com.vendas.entities.DTO.UserDTO;
import com.vendas.services.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {

	@Autowired
	private UserService userService;
	@Autowired
	private UserDTO userDto;
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
		return ResponseEntity.ok().body(userDto.ToListUserFromListUserDTO(userService.findAll()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id, HttpServletRequest request) {
		return ResponseEntity.ok().body(userDto = new UserDTO(userService.findById(id)));
	}
	
	@GetMapping("/name")
	public ResponseEntity<List<User>> findByName(@RequestParam (value = "value") String value) {
		return ResponseEntity.ok().body(userService.findByName(value));
	}
	
	@GetMapping("/email")
	public ResponseEntity<List<User>> findByEmail(@RequestParam (value = "value") String value) {
		return ResponseEntity.ok().body(userService.findByEmail(value));
	}
		
	@GetMapping("/search")
	public ResponseEntity<List<User>> search(@RequestParam(value = "atribut") String atribut, @RequestParam(value = "value") String value) {
		return ResponseEntity.ok().body(userService.search(atribut, value));
	}

	@PostMapping
	public ResponseEntity<Object> insert(@RequestBody User user, HttpServletRequest request) {
		user = userService.insert(user, request);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + user.getId()).build().toUri();
		return ResponseEntity.created(uri).body(new UserDTO(user));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody User user, HttpServletRequest request) {
		return ResponseEntity.ok().body(userService.update(id, user, request));
	}
}
