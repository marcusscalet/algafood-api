package com.marcusscalet.algafood.exceptionhandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.marcusscalet.algafood.domain.exception.EntityInUseException;
import com.marcusscalet.algafood.domain.exception.EntityNotFoundException;
import com.marcusscalet.algafood.domain.exception.GenericException;

@ControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handlingEntityNotFoundException(EntityNotFoundException e) {
		Problem problem = Problem.builder()
				.dateTime(LocalDateTime.now())
				.message(e.getMessage()).build();
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
	}
	
	@ExceptionHandler(EntityInUseException.class)
	public ResponseEntity<?> handlingEntityInUseException(EntityInUseException e){
		Problem problem = Problem.builder()
				.dateTime(LocalDateTime.now())
				.message(e.getMessage()).build();
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
	}
	
	@ExceptionHandler(GenericException.class)
	public ResponseEntity<?> handlingGenericException(GenericException e) {
		Problem problem = Problem.builder()
				.dateTime(LocalDateTime.now())
				.message(e.getMessage()).build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problem);
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<?> handlingHttpMediaTypeNotSupportedException(){
		Problem problem = Problem.builder()
				.dateTime(LocalDateTime.now())
				.message("O tipo de mídia não é aceito").build();
		
		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(problem);
	}
}
