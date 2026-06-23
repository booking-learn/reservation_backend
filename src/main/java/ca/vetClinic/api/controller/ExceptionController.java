package ca.vetClinic.api.controller;

import ca.vetClinic.api.dto.response.ErrorResponse;
import ca.vetClinic.domain.exception.ConflictException;
import ca.vetClinic.domain.exception.ForbiddenException;
import ca.vetClinic.domain.exception.NotFoundException;
import ca.vetClinic.domain.exception.UnAuthorizedException;
import com.fasterxml.jackson.core.JsonParseException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(NotFoundException ex) {
		ErrorResponse error = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(UnAuthorizedException ex) {
		ErrorResponse error = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException ex) {
		ErrorResponse error = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(ForbiddenException ex) {
		ErrorResponse error = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(ConflictException ex) {
		ErrorResponse error = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException ex) {
		ErrorResponse error = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(ConstraintViolationException ex) {
		ErrorResponse error = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(HttpMediaTypeNotSupportedException ex) {
		ErrorResponse error = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(JsonParseException ex) {
		ErrorResponse error = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(HttpMessageNotReadableException ex) {
		ErrorResponse error = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {
		log.error("Unhandled exception", ex);
		ErrorResponse error = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
