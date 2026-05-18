package ca.vetClinic.infra.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
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
		return Jwts.builder().subject(userPrincipal.getEmail()).claim("role", userPrincipal.getRole().name())
				.issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
				.signWith(key).compact();
	}
}
