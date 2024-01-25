package com.school.SBA.ResponseDTO;

public class SchoolResponse {

	private int schoolId;
	private String schoolName;
	private String schoolContactNo;
	private String schoolEmail;
	private String schoolAddress;
	
	public SchoolResponse() {
		super();
	}

	public SchoolResponse(int schoolId, String schoolName, String schoolContactNo, String schoolEmail,
			String schoolAddress) {
		super();
		this.schoolId = schoolId;
		this.schoolName = schoolName;
		this.schoolContactNo = schoolContactNo;
		this.schoolEmail = schoolEmail;
		this.schoolAddress = schoolAddress;
	}
	
	

	public int getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
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
