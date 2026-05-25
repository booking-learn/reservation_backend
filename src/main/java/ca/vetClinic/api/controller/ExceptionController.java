package ca.vetClinic.api.controller;

import ca.vetClinic.api.dto.response.ErrorResponse;
import ca.vetClinic.domain.exception.ForbiddenException;
import ca.vetClinic.domain.exception.NotFoundException;
import ca.vetClinic.domain.exception.UnAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
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
	public ResponseEntity<ErrorResponse> handleException(ForbiddenException ex) {
		ErrorResponse error = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}

}
