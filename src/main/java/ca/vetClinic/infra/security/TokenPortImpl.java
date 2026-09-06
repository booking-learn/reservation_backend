package ca.vetClinic.infra.security;

import ca.vetClinic.domain.enumerator.Role;
import ca.vetClinic.domain.port.TokenPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenPortImpl implements TokenPort {
	private final JwtService jwtService;
	private final JwtProperties jwtProperties;
	@Override
	public String generateToken(String email, Role role) {
		return jwtService.generateToken(email, role);
	}

	@Override
	public long getExpiration() {
		return jwtProperties.getExpiration();
	}
}
