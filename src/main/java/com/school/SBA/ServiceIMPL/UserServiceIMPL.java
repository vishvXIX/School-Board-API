package com.school.SBA.ServiceIMPL;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.school.SBA.Entity.User;
import com.school.SBA.Exception.UserNotFoundByIdException;
import com.school.SBA.Repository.UserRepository;
import com.school.SBA.RequestDTO.UserRequest;
import com.school.SBA.ResponseDTO.UserResponse;
import com.school.SBA.Service.UserService;
import com.school.SBA.Utility.ResponseStructure;
import com.school.SBA.enums.UserRole;

import jakarta.validation.Valid;

@Service
public class UserServiceIMPL implements UserService{

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UserRepository repository;

	@Autowired
	private ResponseStructure<UserResponse> responseStructure;


	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> saveAdmin(@Valid UserRequest userrequest) {
		if(userrequest.getUserRole()==UserRole.ADMIN) {
			// Fetch existing users from the repository
			List<User> existingUsers = repository.findAll();

			// Check if the requested role is ADMIN and if an ADMIN already exists
			if (userrequest.getUserRole() == UserRole.ADMIN && isAdminExists(existingUsers)) {
				throw new IllegalArgumentException("An ADMIN user already exists.");
			}
			
			
			
		}
		else {
			throw new IllegalArgumentException("only Admin can register");
		}
		User user = repository.save(mapToUser(userrequest,false));
		responseStructure.setStatus(HttpStatus.CREATED.value());
		responseStructure.setMessage("User saved Sucessfully");
		responseStructure.setData(mapToUserResponse(user,false));
		return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure,HttpStatus.CREATED);

	}

	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> saveOtherUsers(UserRequest userrequest) {
		// Fetch existing users from the repository
		List<User> existingUsers = repository.findAll();

		// Check if the requested role is ADMIN and if an ADMIN already exists
		if (userrequest.getUserRole() == UserRole.ADMIN && isAdminExists(existingUsers)) {
			throw new IllegalArgumentException("An ADMIN user already exists.");
		}
		
		User user = repository.save(mapToUser(userrequest,false));
		
		if (user.getUserRole() == UserRole.TEACHER || user.getUserRole() == UserRole.STUDENT) {
			mapUserToAdminSchool(user);
        }
		
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
					return new ResponseEntity<ResponseStructure<UserResponse>>(responseStructure,HttpStatus.FOUND);
				})
				.orElseThrow(()-> new UserNotFoundByIdException("user not found by id"));
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


	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> deleteUser(int userId) {

		return repository.findById(userId)
				.map(user -> {
					user.setDeleted(true);
					//					repository.deleteById(userId);
					responseStructure.setStatus(HttpStatus.OK.value());
					responseStructure.setMessage("user deleted successfully");
					responseStructure.setData(mapToUserResponse(user,true));

					return new ResponseEntity<>(responseStructure, HttpStatus.OK);
				})
				.orElseThrow(() -> new UserNotFoundByIdException("User not found by id"));
	}
	
	


	User mapToUser(UserRequest request,boolean isDeleted) {

		User user = new User();
		user.setUserName(request.getUsername());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setEmail(request.getEmail());
		user.setPassword(encoder.encode(request.getPassword()));
		user.setContactNo(request.getContactNo());
		user.setUserRole(request.getUserRole());
		user.setDeleted(request.isDelete());

		return user;

	}

	public UserResponse mapToUserResponse(User user,boolean isDeleted) {

		UserResponse response = new UserResponse();
		response.setUserId(user.getUserId());
		response.setUsername(user.getUserName());
		response.setFirstName(user.getFirstName());
		response.setLastName(user.getLastName());
		response.setContactNo(user.getContactNo());
		response.setEmail(user.getEmail());
		response.setUserRole(user.getUserRole());
		response.setDeleted(user.isDeleted());

		return response ;

	}
	
	private void mapUserToAdminSchool(User user) {
		// Find the ADMIN user
		User admin =repository.findUserByUserRole(UserRole.ADMIN)
				.orElseThrow(() -> new IllegalStateException("Admin user not found."));

		// Map the user to the same school as the ADMIN
		user.setSchool(admin.getSchool());
		repository.save(user);

	}

	private boolean isAdminExists(List<User> users) {
		for (User user : users) {
			if (user.getUserRole() == UserRole.ADMIN) {
				return true;
			}
		}

		return false;
	}

	

	


}
