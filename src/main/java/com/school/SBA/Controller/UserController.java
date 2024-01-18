package com.school.SBA.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.SBA.RequestDTO.UserRequest;
import com.school.SBA.ResponseDTO.UserResponse;
import com.school.SBA.Service.UserService;
import com.school.SBA.Utility.ResponseStructure;

import jakarta.validation.Valid;

@RestController
public class UserController {
	
	@Autowired
	private UserService service;
	
	@PostMapping("/users")
	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(@RequestBody @Valid UserRequest userrequest) {
		return service.saveUser(userrequest);	
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> findUser(@PathVariable int userId){
		return service.findUser(userId);
	}
	
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(@PathVariable int userId){
		return service.deleteUser(userId);
	}

	
}
