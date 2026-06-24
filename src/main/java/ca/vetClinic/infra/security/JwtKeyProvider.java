package ca.vetClinic.infra.security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;

@Component
@RequiredArgsConstructor
public class JwtKeyProvider {
	private final JwtProperties jwtProperties;

	public SecretKey generate() {
		return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtProperties.getSecret()));
	}

}
