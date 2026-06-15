package ca.vetClinic.application.service;

import ca.vetClinic.api.dto.request.LoginRequest;
import ca.vetClinic.api.dto.request.RegisterRequest;
import ca.vetClinic.api.dto.response.AuthResponse;
import ca.vetClinic.domain.exception.ConflictException;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.repository.AccountRepository;
import ca.vetClinic.infra.security.JwtProperties;
import ca.vetClinic.infra.security.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	private final String VALID_EMAIL = "jacob@gmail.com";
	private final String PASSWORD = "qwerty";
	private final String INVALID_EMAIL = "jacob";

	@Mock
	private AccountRepository accountRepository;
	private PasswordEncoder passwordEncoder;
	@Mock
	private AuthenticationManager authenticationManager;
	@Mock
	private UserDetailsService userDetailsService;
	@Mock
	private JwtProvider jwtProvider;
	@Mock
	private JwtProperties jwtProperties;
	@Captor
	ArgumentCaptor<Account> captor;
	private RegisterRequest registerRequest;
	private LoginRequest loginRequest;
	private AuthService authService;
	@BeforeEach
	void setUp() {
		registerRequest = new RegisterRequest(VALID_EMAIL, PASSWORD);
		loginRequest = new LoginRequest(VALID_EMAIL, PASSWORD);
		passwordEncoder = new BCryptPasswordEncoder();
		authService = new AuthService(accountRepository, passwordEncoder, authenticationManager, userDetailsService,
				jwtProvider, jwtProperties);
	}
	@Nested
	class Register {

		@Test
		void givenWhenValidRequest_thenRegister() {
			authService.register(registerRequest);
			verify(accountRepository).save(captor.capture());
		}
		@Test
        void givenWhenEmailExists_thenThrowConflictException()
        {
            when(accountRepository.existsByEmail(VALID_EMAIL)).thenReturn(true);
            assertThrows(ConflictException.class,
                    () -> authService.register(new RegisterRequest(VALID_EMAIL, PASSWORD)));
        }
	}
	@Nested
	class Login {
		@BeforeEach
		void setUp() {
			authService.register(registerRequest);
		}
		@Test
		void givenWhenValidRequest_thenLogin() {
			AuthResponse response = authService.login(new LoginRequest(VALID_EMAIL, PASSWORD));
			assertNotNull(response);
		}

	}
}