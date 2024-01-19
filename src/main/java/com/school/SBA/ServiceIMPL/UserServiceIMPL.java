package com.school.SBA.ServiceIMPL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.school.SBA.Entity.User;
import com.school.SBA.Exception.UserNotFoundByIdException;
import com.school.SBA.Repository.UserRepository;
import com.school.SBA.RequestDTO.UserRequest;
import com.school.SBA.ResponseDTO.UserResponse;
import com.school.SBA.Service.UserService;
import com.school.SBA.Utility.ResponseStructure;
import com.school.SBA.enums.UserRole;

@Service
public class UserServiceIMPL implements UserService{

	@Autowired
	private UserRepository repository;

	@Autowired
	private ResponseStructure<UserResponse> responseStructure;

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> saveUser(UserRequest userrequest) {
		// Fetch existing users from the repository
		List<User> existingUsers = repository.findAll();

		// Check if the requested role is ADMIN and if an ADMIN already exists
		if (userrequest.getUserRole() == UserRole.ADMIN && isAdminExists(existingUsers)) {
			throw new IllegalArgumentException("An ADMIN user already exists.");
		}
		User user = (User) repository.save(mapToUser(userrequest, false));
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("User saved Sucessfully");
		responseStructure.setData(mapToUserResponse(user,false));
		return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure,HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> findUser(int userId) {
		return repository.findById(userId)
				.map(user->{
					responseStructure.setStatus(HttpStatus.FOUND.value());
					responseStructure.setMessage("user fatch susscessfully");
					responseStructure.setData(mapToUserResponse(user,false));
					return new ResponseEntity<>(responseStructure,HttpStatus.FOUND);
				})
				.orElseThrow(()-> new UserNotFoundByIdException("user not found by id"));
	}
	
	
	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(int userId) {

		return repository.findById(userId)
				.map(user -> {
					user.setDeleted(true);
//					userrepository.deleteById(userId);
					responseStructure.setStatus(HttpStatus.OK.value());
					responseStructure.setMessage("user deleted successfully");
					responseStructure.setData(mapToUserResponse(user,true));

					return new ResponseEntity<>(responseStructure, HttpStatus.OK);
				})
				.orElseThrow(() -> new UserNotFoundByIdException("User not found by id"));
	}
	

	private User mapToUser(UserRequest request,boolean isDeleted) {

		User user = new User();
		user.setUserName(request.getUsername());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setUserEmail(request.getEmail());
		user.setpassword(request.getPassword());
		user.setcontactNo(request.getContactNo());
		user.setUserRole(request.getUserRole());
		user.setDeleted(request.isDelete());

		return user;

	}
	
	private UserResponse mapToUserResponse(User user,boolean isDeleted) {

		UserResponse response = new UserResponse();
		response.setUserId(user.getUserId());
		response.setUsername(user.getUserName());
		response.setFirstName(user.getFirstName());
		response.setLastName(user.getLastName());
		response.setContactNo(user.getcontactNo());
		response.setEmail(user.getUserEmail());
		response.setUserRole(user.getUserRole());
		response.setDelete(user.isDeleted());

		return response ;

	}

	private boolean isAdminExists(List<User> users) {
		for (User user : users) {
			if (user.getUserRole() == UserRole.ADMIN) {
				return true;
			}
		}

		return false;
	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> updateUser(int userId, UserRequest userrequest) {
		User user = repository.findById(userId)
				.map(existingUser -> {
					User updatedUser = mapToUser(userrequest,false);
					updatedUser.setUserId(userId);
					return repository.save(updatedUser);
				})
				.orElseThrow(() -> new UserNotFoundByIdException("User not found by id"));

		ResponseStructure<UserResponse> structure = new ResponseStructure<>();
		structure.setStatus(HttpStatus.OK.value());
		structure.setMessage("User updated successfully");
		structure.setData(mapToUserResponse(user,false));

		return new ResponseEntity<ResponseStructure<UserResponse>>(structure, HttpStatus.OK);
	}


	

	

	



}
