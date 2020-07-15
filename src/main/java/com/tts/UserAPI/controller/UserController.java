package com.tts.UserAPI.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tts.UserAPI.model.User;
import com.tts.UserAPI.repository.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(value = "/users")
	public List<User> getUsers(@RequestParam(value = "state", required = false) String state){
		if(state != null) {
			return(List<User>) userRepository.findAllByState(state);
		}
		return (List<User>) userRepository.findAll();
	}
	
	@GetMapping(value = "/users/{id}")
	public Optional<User> getUserById(@PathVariable(value = "id") Long id){
		return userRepository.findById(id);
	}
	
	@PostMapping(value="/users")
	public void createUser(@RequestBody User user) {
		userRepository.save(user);
	}
	
	@PutMapping(value = "/users/{id}")
	public void updateUser(@PathVariable(value="id") Long id, @RequestBody User user) {
		userRepository.save(user);
	}
	
	@DeleteMapping(value="/users/{id}")
	public void deleteById(@PathVariable(value = "id") Long id) {
		userRepository.deleteById(id);
	}
	
}
