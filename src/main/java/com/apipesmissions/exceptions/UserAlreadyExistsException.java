package com.apipesmissions.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter @Setter
public class UserAlreadyExistsException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private final Integer statusCode;
	private String message;
	
	public UserAlreadyExistsException(String message) {
		this.statusCode = HttpStatus.BAD_REQUEST.value();
		this.message = message;
	}
	
}
