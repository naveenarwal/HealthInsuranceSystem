package com.dollop.app.exceptionhandler;


import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dollop.app.exception.ResourceNotFoundException;

@RestControllerAdvice
public class CustomExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<MyErrorResponse> resourceNotFound(ResourceNotFoundException rnfe){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new MyErrorResponse(new Date().toString(),"Exception in process",700,rnfe.getMessage()));
	}
	

}
