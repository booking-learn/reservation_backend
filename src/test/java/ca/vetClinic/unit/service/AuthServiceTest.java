package ca.vetClinic.unit.service;

import ca.vetClinic.api.dto.request.RegisterReq;
import ca.vetClinic.api.dto.request.LoginReq;
import ca.vetClinic.api.dto.response.AuthResponse;
import ca.vetClinic.application.service.AuthService;
import ca.vetClinic.domain.enumerator.Role;
import ca.vetClinic.domain.exception.ConflictException;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.model.User;
import ca.vetClinic.domain.service.AccountService;
import ca.vetClinic.domain.service.UserService;
import ca.vetClinic.infra.security.JwtProperties;
import ca.vetClinic.infra.security.JwtService;
import ca.vetClinic.infra.security.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	private final String VALID_EMAIL = "jacob@gmail.com";
	private final String PASSWORD = "qwerty";
	private final String INVALID_EMAIL = "jacob";
	private final String INVALID_PASSWORD = "azerty";
	private final String FIRST_NAME = "jacob";
	private final String LAST_NAME = "houle";
	private final String PHONE_NUMBER = "1234567890";

	@Mock
	private AccountService accountService;
	private PasswordEncoder passwordEncoder;
	@Mock
	private AuthenticationManager authenticationManager;
	@Mock
	private UserDetailsService userDetailsService;
	@Mock
	private UserPrincipal userPrincipal;
	@Mock
	private JwtService jwtService;
	@Mock
	private JwtProperties jwtProperties;
	@Mock
	private UserService userService;
	@Captor
	ArgumentCaptor<Account> accountCaptor;
	@Captor
	ArgumentCaptor<User> userCaptor;
	private RegisterReq registerReq;
	private LoginReq loginReq;
	private AuthService authService;
	@BeforeEach
	void setUp() {
		registerReq = new RegisterReq(VALID_EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, PHONE_NUMBER);
		loginReq = new LoginReq(VALID_EMAIL, PASSWORD);
		passwordEncoder = new BCryptPasswordEncoder();
		authService = new AuthService(accountService, passwordEncoder, authenticationManager, userDetailsService,
				jwtService, jwtProperties, userService);
	}
	@Nested
	class Register {

		@Test
		void givenWhenValidRequest_thenRegister() {
			authService.register(registerReq);
			verify(accountService).save(accountCaptor.capture());
		}
		@Test
		void givenWhenValidRequest_thenCreateUser() {
			authService.register(registerReq);
			verify(userService).save(userCaptor.capture());
		}
		@Test
        void givenWhenEmailExists_thenThrowConflictException()
        {
            when(accountService.existsByEmail(VALID_EMAIL)).thenReturn(true);
            assertThrows(ConflictException.class,
                    () -> authService.register(new RegisterReq(VALID_EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, PHONE_NUMBER)));
        }
		@Test
        void givenWhenValidRequest_thenGenerateToken()
        {
            when(userDetailsService.loadUserByUsername(registerReq.email()))
                    .thenReturn(userPrincipal);
            when(jwtService.generateToken(userPrincipal))
                    .thenReturn("fake-jwt-token");
            AuthResponse response=authService.register(registerReq);
            assertNotNull(response.accessToken());
        }
	}
	@Nested
	class Login {
		@BeforeEach
		void setUp() {
			authService.register(registerReq);
		}
		@Test
		void givenWhenValidRequest_thenLogin() {
			UserPrincipal userPrincipal = UserPrincipal
					.fromAccount(new Account(UUID.randomUUID(), VALID_EMAIL, PASSWORD, Role.VETERINARIAN));

			when(userDetailsService.loadUserByUsername(VALID_EMAIL)).thenReturn(userPrincipal);
			when(jwtService.generateToken(userPrincipal)).thenReturn("token");

			AuthResponse response = authService.login(new LoginReq(VALID_EMAIL, PASSWORD));

			assertNotNull(response.accessToken());
		}
		@Test
        void givenWhenInvalidEmail_thenLoginFail() {
            when(authenticationManager.authenticate(any()))
                    .thenThrow(new BadCredentialsException("Invalid credentials"));

            assertThrows(BadCredentialsException.class,
                    () -> authService.login(new LoginReq(INVALID_EMAIL, PASSWORD)));
        }

		@Test
        void givenWhenInvalidPassword_thenLoginFail() {
            when(authenticationManager.authenticate(any()))
                    .thenThrow(new BadCredentialsException("Invalid credentials"));

            assertThrows(BadCredentialsException.class,
                    () -> authService.login(new LoginReq(VALID_EMAIL, INVALID_PASSWORD)));
        }

	}

}