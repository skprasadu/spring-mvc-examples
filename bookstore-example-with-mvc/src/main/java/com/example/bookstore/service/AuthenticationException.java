package com.example.bookstore.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when username or password are incorect
 * 
 * 
 * 
 * 
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class AuthenticationException extends Exception {

	private String code;

	public AuthenticationException(String message, String code) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

}