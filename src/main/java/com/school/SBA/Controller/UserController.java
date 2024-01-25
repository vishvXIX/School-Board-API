package com.school.SBA.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	
//	@PostMapping("/users/register/all")
//	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(@RequestBody @Valid UserRequest userrequest) {
//		return service.saveUser(userrequest);	
//	}
	
	@PostMapping("/users/register")
	public ResponseEntity<ResponseStructure<UserResponse>> saveAdmin(@RequestBody @Valid UserRequest userrequest) {
		return service.saveAdmin(userrequest);	
	}
	
	@PostMapping("/users")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ResponseStructure<UserResponse>> saveOtherUsers(@RequestBody UserRequest request) {
		return service.saveOtherUsers(request);
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponse>> findUser(@PathVariable int userId){
		return service.findUser(userId);
	}
	

	@PutMapping("/update/{userId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(@PathVariable int userId,@Valid @RequestBody UserRequest userrequest) {
		return service.updateUser(userId,userrequest);
 
	}
	
	@DeleteMapping("/users/{userId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(@PathVariable int userId){
		return service.deleteUser(userId);
	}

	
}
