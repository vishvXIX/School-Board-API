package com.school.SBA.Service;

import org.springframework.http.ResponseEntity;

import com.school.SBA.RequestDTO.UserRequest;
import com.school.SBA.ResponseDTO.UserResponse;
import com.school.SBA.Utility.ResponseStructure;


public interface UserService {

	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(UserRequest userrequest);

	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(int userId);

	public ResponseEntity<ResponseStructure<UserResponse>> findUser(int userId);

	

}
