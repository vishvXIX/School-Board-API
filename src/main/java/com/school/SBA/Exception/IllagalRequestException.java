package com.school.SBA.Exception;

public class IllagalRequestException extends RuntimeException {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public IllagalRequestException(String message) {
		super();
		this.message = message;
	}
	
	

}
