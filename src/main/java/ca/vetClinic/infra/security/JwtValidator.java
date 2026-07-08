package ca.vetClinic.infra.security;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
@RequiredArgsConstructor
public class JwtValidator {

	private final JwtKeyProvider jwtKeyProvider;
	public boolean isValid(String token) {
		try {
			SecretKey key = jwtKeyProvider.generate();
			Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
