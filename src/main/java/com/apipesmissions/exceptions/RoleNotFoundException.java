package com.apipesmissions.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter @Setter
public class RoleNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private final Integer statusCode;
	private String message;
	
	public RoleNotFoundException(String message) {
		this.statusCode = HttpStatus.NOT_FOUND.value();
		this.message = message;
	}
	
}
