package com.school.SBA.Service;

import org.springframework.http.ResponseEntity;

import com.school.SBA.RequestDTO.UserRequest;
import com.school.SBA.ResponseDTO.UserResponse;
import com.school.SBA.Utility.ResponseStructure;

import jakarta.validation.Valid;


public interface UserService {

	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(UserRequest userrequest);

	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(int userId);

	public ResponseEntity<ResponseStructure<UserResponse>> findUser(int userId);

	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(int userId, @Valid UserRequest userrequest);

	

}
