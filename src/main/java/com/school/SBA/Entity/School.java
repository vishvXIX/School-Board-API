package com.school.SBA.Entity;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
	
@Entity
public class School {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int schoolId;
	private String schoolName;
	private String schoolContactNo;
	private String schoolEmail;
	private String schoolAddress;
	
	@OneToOne
	private Schedule schedule;
	
	@OneToMany(mappedBy = "school")
	private List<AcademicProgram> lAcademicProgram;

	
	public List<AcademicProgram> getAcademicProgram() {
		return lAcademicProgram;
	}

	public void setAcademicProgram(List<AcademicProgram> lAcademicProgram) {
		this.lAcademicProgram = lAcademicProgram;
	}

	public School() {
		super();
	}

	public School(int schoolId, String schoolName, String schoolContactNo, String schoolEmail, String schoolAddress,
			Schedule schedule) {
		super();
		this.schoolId = schoolId;
		this.schoolName = schoolName;
		this.schoolContactNo = schoolContactNo;
		this.schoolEmail = schoolEmail;
		this.schoolAddress = schoolAddress;
		this.schedule = schedule;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
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

	@Override
	public String toString() {
		return "School [schoolId=" + schoolId + ", schoolName=" + schoolName + ", schoolContactNo=" + schoolContactNo
				+ ", schoolEmail=" + schoolEmail + ", schoolAddress=" + schoolAddress + ", schedule=" + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(schoolAddress, schoolContactNo, schoolEmail, schoolId, schoolName);
	}

	
	
}
