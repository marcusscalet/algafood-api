package com.marcusscalet.algafood.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.marcusscalet.algafood.domain.exception.BusinessException;
import com.marcusscalet.algafood.domain.exception.EntityBeenUsedException;
import com.marcusscalet.algafood.domain.exception.EntityNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String GENERIC_ERROR_MESSAGE_FINAL_USER = "Ocorreu um erro interno inesperado no sistema. "
			+ "Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.";

	private String joinPath(List<Reference> references) {
		return references.stream()
				.map(ref->ref.getFieldName())
				.collect(Collectors.joining("."));
	}
		
	@ExceptionHandler(Exception.class)
	private ResponseEntity<Object> handleExceptionHandling(Exception ex, WebRequest request){
		
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemType problemType = ProblemType.SYSTEM_ERROR;
		
		String detail = GENERIC_ERROR_MESSAGE_FINAL_USER;
		ex.printStackTrace();
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
				
		if(rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException)rootCause, headers, status, request);
		} else if(rootCause instanceof PropertyBindingException) {
			return handlePropertyBindingException((PropertyBindingException)rootCause, headers, status, request);
		}
				
		ProblemType problemType = ProblemType.MESSAGE_NOT_READABLE;
		String detail = "O corpo da requisição possui erros. Verifique por erros de sintaxe.";
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(
				ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, 
	        HttpHeaders headers, HttpStatus status, WebRequest request) {
	    
	    ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
	    String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.", 
	            ex.getRequestURL());
	    
	    Problem problem = createProblemBuilder(status, problemType, detail).build();
	    
	    return handleExceptionInternal(ex, problem, headers, status, request);
	}  
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
		  	
		if(ex instanceof MethodArgumentTypeMismatchException) {
				return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
		}

		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.INVALID_DATA;
	    String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente";
	    
	    BindingResult bindingResult = ex.getBindingResult();
	    
	    List<Problem.Field> problemFields = bindingResult.getFieldErrors().stream()
	    		.map(fieldError -> Problem.Field.builder()
	    				.name(fieldError.getField())
	    				.userMessage(fieldError.getDefaultMessage())
	    				.build())
	    		.collect(Collectors.toList());
	    
	    Problem problem = createProblemBuilder(status, problemType, detail)
	    		.userMessage(detail)
	    		.fields(problemFields)
	    		.build();
	    
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
	        MethodArgumentTypeMismatchException ex, HttpHeaders headers,
	        HttpStatus status, WebRequest request) {

	    ProblemType problemType = ProblemType.INVALID_PARAMETER;

	    String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
	            + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
	            ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

	    Problem problem = createProblemBuilder(status, problemType, detail).build();

	    return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
		String path = joinPath(ex.getPath());
		
		ProblemType problemType = ProblemType.MESSAGE_NOT_READABLE;
		String detail = String.format("A propriedade '%s' não existe. "
				+ "Corrija ou remova essa propriedade e tente novamente.", path, ex.getKnownPropertyIds());
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(GENERIC_ERROR_MESSAGE_FINAL_USER)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
		
		//pegamos uma lista de referências com as propriedades que foram passadas inválidas
		String path = joinPath(ex.getPath());
		
		ProblemType problemType = ProblemType.MESSAGE_NOT_READABLE;
		String detail = String.format("A propriedade '%s' recebeu o valor '%s', "
				+ "que é de um tipo inválido. Corrija e informe um valor com o tipo '%s.'", path, ex.getValue(), ex.getTargetType().getSimpleName());
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(GENERIC_ERROR_MESSAGE_FINAL_USER)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleEntityNotFoundException(
			EntityNotFoundException ex, WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
		String detail = ex.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(
				ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntityBeenUsedException.class)
	public ResponseEntity<?> handleEntityInUseException(
			EntityBeenUsedException ex, WebRequest request) {

		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTITY_BEEN_USED;
		String detail = ex.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(
				ex, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> handleGenericException(BusinessException ex, WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.BUSINESS_ERROR;
		String detail = ex.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		return handleExceptionInternal(
				ex, problem, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (body == null) {
			
		body = Problem.builder()
				.timestamp(LocalDateTime.now())
				.title(status.getReasonPhrase())
				.status(status.value())
				.userMessage(GENERIC_ERROR_MESSAGE_FINAL_USER)
				.build();
		
		} else if(body instanceof String) {
			body = Problem.builder()
				.timestamp(LocalDateTime.now())
				.title((String) body)
				.status(status.value())
				.userMessage(GENERIC_ERROR_MESSAGE_FINAL_USER)
				.build();
		}
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
		
		return Problem.builder()
				.timestamp(LocalDateTime.now())
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail);
	}
}
