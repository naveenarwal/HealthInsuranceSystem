package com.rihis.exceptionHandler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rihis.exception.ResourceAlreadyExist;
import com.rihis.exception.ResourceNotFoundException;
import com.rihis.exception.UserNotFoundException;
import com.rihis.exception.UsernameAlreadyExist;

@RestControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<MyErrorResponse> userNotFound(UserNotFoundException unfe){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new MyErrorResponse(new Date().toString(),"Exception in process",700,unfe.getMessage()));
	}
	
	@ExceptionHandler(UsernameAlreadyExist.class)
	public ResponseEntity<MyErrorResponse> usernameAlreadyExist(UsernameAlreadyExist uae){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new MyErrorResponse(new Date().toString(),"Exception in process",700,uae.getMessage()));
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<MyErrorResponse> resourceNotFound(ResourceNotFoundException rnfe){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new MyErrorResponse(new Date().toString(),"Exception in process",700,rnfe.getMessage()));
	}
	
	@ExceptionHandler(ResourceAlreadyExist.class)
	public ResponseEntity<MyErrorResponse> resourceAlreadyExist(ResourceAlreadyExist rae){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new MyErrorResponse(new Date().toString(),"Exception in process",700,rae.getMessage()));
	}
}
