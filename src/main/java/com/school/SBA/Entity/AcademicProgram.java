package com.school.SBA.Entity;

import java.time.LocalDate;
import java.util.List;

import com.school.SBA.enums.ProgramType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class AcademicProgram {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int programId;
	private ProgramType programType;
	private String programName;
	private LocalDate beginsAt;
	private LocalDate endsAt;
	
	@ManyToOne
	private School school;
	
	@ManyToMany
	private List<Subject> listSubjects;
	
	public List<Subject> getListSubjects() {
		return listSubjects;
	}

	public void setListSubjects(List<Subject> listSubjects) {
		this.listSubjects = listSubjects;
	}

	public AcademicProgram() {
		super();
	}
	
	
	public AcademicProgram(int programId, ProgramType programType, String programName, LocalDate beginsAt,
			LocalDate endsAt, School school) {
		super();
		this.programId = programId;
		this.programType = programType;
		this.programName = programName;
		this.beginsAt = beginsAt;
		this.endsAt = endsAt;
		this.school = school;
//		this.listSubjects = listSubjects;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public int getProgramId() {
		return programId;
	}

	public void setProgramId(int programId) {
		this.programId = programId;
	}

	public ProgramType getProgramType() {
		return programType;
	}

	public void setProgramType(ProgramType programType) {
		this.programType = programType;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public LocalDate getBeginsAt() {
		return beginsAt;
	}

	public void setBeginsAt(LocalDate beginsAt) {
		this.beginsAt = beginsAt;
	}

	public LocalDate getEndsAt() {
		return endsAt;
	}

	public void setEndsAt(LocalDate endsAt) {
		this.endsAt = endsAt;
	}


	
	
	
	
}
