package ca.vetClinic.infra.security.jwt;

import ca.vetClinic.domain.enumerator.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtService {

	private final JwtProperties jwtProperties;

	public String generateToken(String email, Role role) {
		long now = System.currentTimeMillis();
		long expiryMillis = now + jwtProperties.getExpiration();

		return Jwts.builder().subject(email).claim("role", role.name()).issuedAt(new Date(now))
				.expiration(new Date(expiryMillis)).signWith(getSigningKey()).compact();
	}

	public String extractUsername(String token) {
		return parseClaims(token).getSubject();
	}

	public boolean isValid(String token) {
		try {
			parseClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private Claims parseClaims(String token) {
		return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
	}

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtProperties.getSecret()));
	}
}