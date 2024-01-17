package com.school.SBA.RequestDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class SchoolRequest {

	
	private String schoolName;
	private String schoolContactNo;
	@NotEmpty(message = "Email cannot be blank/empty")
	@Email(regexp = "[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}", message = "invalid email ")
	private String schoolEmail;
	private String schoolAddress;
	
	public SchoolRequest() {
		super();
	}
	public SchoolRequest(String schoolName, String schoolContactNo, String schoolEmail, String schoolAddress) {
		super();
		this.schoolName = schoolName;
		this.schoolContactNo = schoolContactNo;
		this.schoolEmail = schoolEmail;
		this.schoolAddress = schoolAddress;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getSchoolContactNo() {
		return schoolContactNo;
	}
	public void setSchoolContactNo(String schoolContactNo) {
		this.schoolContactNo = schoolContactNo;
	}
	public String getSchoolEmail() {
		return schoolEmail;
	}
	public void setSchoolEmail(String schoolEmail) {
		this.schoolEmail = schoolEmail;
	}
	public String getSchoolAddress() {
		return schoolAddress;
	}
	public void setSchoolAddress(String schoolAddress) {
		this.schoolAddress = schoolAddress;
	}
	
	
	
}
