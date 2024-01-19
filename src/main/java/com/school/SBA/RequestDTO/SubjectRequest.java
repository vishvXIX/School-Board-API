package com.school.SBA.RequestDTO;

import java.util.List;

public class SubjectRequest {

	private List<String> subjectName;

	public SubjectRequest() {
		super();
	}

	public SubjectRequest(List<String> subjectName) {
		super();
		this.subjectName = subjectName;
	}

	public List<String> getsubjectName() {
		return subjectName;
	}

	public void setsubjectName(List<String> subjectName) {
		this.subjectName = subjectName;
	}
	
	
	
}
