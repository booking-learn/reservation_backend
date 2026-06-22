package ca.vetClinic.application.service;

import ca.vetClinic.api.dto.request.LoginRequest;
import ca.vetClinic.api.dto.request.RegisterRequest;
import ca.vetClinic.api.dto.response.AuthResponse;
import ca.vetClinic.domain.enumerator.Role;
import ca.vetClinic.domain.exception.ConflictException;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.repository.AccountRepository;
import ca.vetClinic.infra.security.JwtProperties;
import ca.vetClinic.infra.security.JwtProvider;
import ca.vetClinic.infra.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AccountRepository accountRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final JwtProvider jwtProvider;
	private final JwtProperties jwtProperties;

    @Transactional
	public AuthResponse register(RegisterRequest request) {
		Account account = new Account(null, request.email(), passwordEncoder.encode(request.password()), Role.USER);
		if (accountRepository.existsByEmail(request.email())) {
			throw new ConflictException("The account with this email already exists!");
		}
		accountRepository.save(account);

		UserPrincipal userPrincipal = (UserPrincipal) userDetailsService.loadUserByUsername(request.email());
		String token = jwtProvider.generateToken(userPrincipal);

		return new AuthResponse(token, "Bearer", jwtProperties.getExpiration());
	}

	public AuthResponse login(LoginRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

		UserPrincipal userPrincipal = (UserPrincipal) userDetailsService.loadUserByUsername(request.email());
		String token = jwtProvider.generateToken(userPrincipal);

		return new AuthResponse(token, "Bearer", jwtProperties.getExpiration());
	}
}
