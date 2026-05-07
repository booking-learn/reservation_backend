package ca.vetClinic.domain.exception;

public class NotFoundException extends RuntimeException {
	public NotFoundException(String message) {
		super("The " + message + " has not been found");
	}
}
