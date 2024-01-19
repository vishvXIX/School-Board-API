package com.school.SBA.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int subjectId;
	private String subjectNames; 

	public Subject() {
		super();
	}

	
	public Subject(int subjectId, String subjectNames, List<AcademicProgram> listAcademicPrograms) {
		super();
		this.subjectId = subjectId;
		this.subjectNames = subjectNames;
		
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjects() {
		return subjectNames;
	}

	public void setSubjects(String subjectNames) {
		this.subjectNames = subjectNames;
	}
	
	
	
}
