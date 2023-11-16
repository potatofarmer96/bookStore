package com.utility;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

//import javax.validation.ConstraintViolationException;


//import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.exception.BookStoreException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionControllerAdvice {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> handleValidationException(MethodArgumentNotValidException ex){
		ErrorMessage error = new ErrorMessage();
		error.setCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getBindingResult().getAllErrors()
				.stream().map(e -> e.getDefaultMessage())
				.collect(Collectors.joining(", ")));
		error.setTimeStamp(LocalDateTime.now().toString());
		
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorMessage> handleConstraintViolationException(ConstraintViolationException ex){
		ErrorMessage error = new ErrorMessage();
		error.setCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getConstraintViolations()
				.stream().map(e -> e.getMessage())
				.collect(Collectors.joining(", ")));
		error.setTimeStamp(LocalDateTime.now().toString());
		
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);	
	}
	
	@ExceptionHandler(BookStoreException.class)
	public ResponseEntity<ErrorMessage> handleExceptions(BookStoreException ex){
		ErrorMessage error = new ErrorMessage();
		error.setCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getMessage());
		error.setTimeStamp(LocalDateTime.now().toString());
		
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleExceptions(Exception ex){
		ErrorMessage error = new ErrorMessage();
		error.setCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage("Unexpected exception: "+ex.getMessage());
		error.setTimeStamp(LocalDateTime.now().toString());
		
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
		
	}
}
