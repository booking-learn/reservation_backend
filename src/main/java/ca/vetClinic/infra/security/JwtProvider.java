package ca.vetClinic.infra.security;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

	private final JwtProperties jwtProperties;
	private final JwtKeyProvider jwtKeyProvider;

	public String generateToken(UserPrincipal userPrincipal) {
		SecretKey key = jwtKeyProvider.generate();
		long now = System.currentTimeMillis();
		long expiryMillis = now + jwtProperties.getExpiration();

		return Jwts.builder().subject(userPrincipal.getEmail()).claim("role", userPrincipal.getRole().name())
				.issuedAt(new Date(now)).expiration(new Date(expiryMillis)).signWith(key).compact();
	}
}