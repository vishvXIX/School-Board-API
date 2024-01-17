package com.school.SBA.ResponseDTO;

import com.school.SBA.enums.UserRole;


public class UserResponse {
	

	private int userId;
	private String username;
	private String firstName;
	private String lastName;
	private String contactNo;
	private String email;
	private UserRole userRole;
	private boolean isDeleted;
	
	
	
	public UserResponse(int userId, String username, String firstName, String lastName, String contactNo, String email,
			UserRole userRole, boolean isDeleted) {
		super();
		this.userId = userId;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactNo = contactNo;
		this.email = email;
		this.userRole = userRole;
		this.isDeleted = isDeleted;
	}
	
	public UserResponse() {
		super();
	}
	
	public boolean isDelete() {
		return isDeleted;
	}
	public void setDelete(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
