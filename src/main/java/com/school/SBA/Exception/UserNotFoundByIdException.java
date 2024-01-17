package com.school.SBA.Exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundByIdException extends RuntimeException {

	private String message ;

	public UserNotFoundByIdException(String message) {
		super();
	}
	
	public UserNotFoundByIdException(HttpStatus notFound) {
		
	}	
	
	public void setMessage(String message) {
		this.message=message;
	}

	public String getMessage() {
		return message;
	}
	
}
