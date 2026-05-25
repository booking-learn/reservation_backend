package ca.vetClinic.domain.exception;

public class UnAuthorizedException extends RuntimeException {
	public UnAuthorizedException() {
		super("You have to log in or provide valid credentials");
	}
}
