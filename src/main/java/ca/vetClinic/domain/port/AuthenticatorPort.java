package ca.vetClinic.domain.port;

public interface AuthenticatorPort {
	void authenticate(String email, String password);
}
