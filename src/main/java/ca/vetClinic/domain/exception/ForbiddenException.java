package ca.vetClinic.domain.exception;

public class ForbiddenException extends RuntimeException {
	public ForbiddenException(String message) {
		super("You are not allowed to " + message);
	}
}
