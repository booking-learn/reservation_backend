package ca.vetClinic.domain.port;

import ca.vetClinic.domain.enumerator.Role;

public interface TokenPort {
	String generateToken(String email, Role role);
	long getExpiration();
}
