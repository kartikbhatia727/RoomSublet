package com.example.demo;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomException extends  ResponseEntityExceptionHandler{

	@ExceptionHandler(UserNotFoundException.class)
	//override method of ResponseEntityExceptionHandler class
	public final ResponseEntity<Object> handleUserNotFoundExceptions(UserNotFoundException ex, WebRequest request)
	{
	//creating exception response structure
	ExceptionResponse exceptionResponse= new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
	//returning exception structure and specific status 
	return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
	}
}
