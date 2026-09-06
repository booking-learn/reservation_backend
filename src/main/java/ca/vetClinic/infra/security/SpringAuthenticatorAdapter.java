package ca.vetClinic.infra.security;

import ca.vetClinic.domain.exception.UnAuthorizedException;
import ca.vetClinic.domain.port.AuthenticatorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringAuthenticatorAdapter implements AuthenticatorPort {
	private final AuthenticationManager authenticationManager;

	@Override
	public void authenticate(String email, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (BadCredentialsException e) {
			throw new UnAuthorizedException();
		}
	}
}
