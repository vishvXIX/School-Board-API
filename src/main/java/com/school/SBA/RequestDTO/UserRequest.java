package com.school.SBA.RequestDTO;

import com.school.SBA.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class UserRequest {
	
	@NotEmpty
	private String username;
//	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
//	@Pattern(regexp = "^(?=.[0-9])(?=.[a-z])(?=.[A-Z])(?=.[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must"
//			+ " contain at least one letter, one number, one special character")
	private String password;
	private String firstName;
	private String lastName;
	private String contactNo;
	private boolean isDelete;
	@NotEmpty(message = "Email cannot be blank/empty")
	@Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email ")
	private String email;
	private UserRole userRole;
	
	
	public UserRequest() {
		super();
	}
	
	public UserRequest(@NotEmpty String username, String password, String firstName, String lastName, String contactNo,
			boolean isDelete,
			@NotEmpty(message = "Email cannot be blank/empty") @Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email ") String email,
			UserRole userRole) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactNo = contactNo;
		this.isDelete = isDelete;
		this.email = email;
		this.userRole = userRole;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	
	

}
