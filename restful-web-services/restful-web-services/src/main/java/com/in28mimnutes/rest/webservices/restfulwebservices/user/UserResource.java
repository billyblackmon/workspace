package com.in28mimnutes.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserResource {
	
	@Autowired
	private UserDaoService service;
	
	// GET /users
	@GetMapping("/users")
	 public List<User> retrieveAllUsers() {
		 return service.findAll();
	 }
	
	// GET /users/{id}
	@GetMapping("/users/{id}")
	 public User retrieveUser(@PathVariable int id) {
		
		User user = service.findOne(id);
		
		if(user == null) {
			throw new UserNotFoundException("retrieveUser() - User not found: id = " + id);
		}
		
		 return service.findOne(id);
	 }
	
	// created
	// input - details of user
	// output - CREATED & returns the created URI
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@RequestBody User user) {
		User savedUser = service.save(user);
		
		URI location = ServletUriComponentsBuilder
		.fromCurrentRequest()
		.path("/{id}")
		.buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{id}")
	public void delete(@PathVariable int id) {
		// return 200 successful
		User user = service.deleteById(id);
		
		if(user == null) {
			throw new UserNotFoundException("delete() - id: " + id);
		}
	}
	
	
}
