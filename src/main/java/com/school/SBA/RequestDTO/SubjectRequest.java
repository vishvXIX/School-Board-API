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

	public List<String> getListString() {
		return subjectName;
	}

	public void setListString(List<String> subjectName) {
		this.subjectName = subjectName;
	}
	
	
	
}
