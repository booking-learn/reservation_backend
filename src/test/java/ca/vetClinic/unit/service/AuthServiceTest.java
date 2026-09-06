package ca.vetClinic.unit.service;

import ca.vetClinic.application.command.ChangePasswordCmd;
import ca.vetClinic.application.command.LoginCmd;
import ca.vetClinic.application.command.RegisterCmd;
import ca.vetClinic.application.command.UpdatePasswordCmd;
import ca.vetClinic.application.dto.AuthResult;
import ca.vetClinic.application.service.AuthServiceImpl;
import ca.vetClinic.domain.enumerator.Role;
import ca.vetClinic.domain.exception.ConflictException;
import ca.vetClinic.domain.exception.ForbiddenException;
import ca.vetClinic.domain.exception.UnAuthorizedException;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.model.User;
import ca.vetClinic.domain.port.AuthenticatorPort;
import ca.vetClinic.domain.port.PasswordHasherPort;
import ca.vetClinic.domain.port.TokenPort;
import ca.vetClinic.domain.service.AccountService;
import ca.vetClinic.domain.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	private final String VALID_EMAIL = "jacob@gmail.com";
	private final String PASSWORD = "qwerty";
	private final String NEW_PASSWORD = "azerty";
	private final String FIRST_NAME = "jacob";
	private final String LAST_NAME = "houle";
	private final String PHONE_NUMBER = "1234567890";
	private final String ENCODED_PASSWORD = "encoded-password";
	private final String TOKEN = "fake-jwt-token";

	@Mock
	private AccountService accountService;
	@Mock
	private UserService userService;
	@Mock
	private PasswordHasherPort passwordHasher;
	@Mock
	private AuthenticatorPort authenticatorPort;
	@Mock
	private TokenPort tokenPort;

	@Captor
	ArgumentCaptor<Account> accountCaptor;
	@Captor
	ArgumentCaptor<User> userCaptor;

	private RegisterCmd registerCmd;
	private LoginCmd loginCmd;
	private ChangePasswordCmd changePasswordCmd;

	private AuthServiceImpl authService;

	@BeforeEach
	void setUp() {
		registerCmd = new RegisterCmd(VALID_EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, PHONE_NUMBER, Role.USER);
		loginCmd = new LoginCmd(VALID_EMAIL, PASSWORD, Role.USER);
		changePasswordCmd = new ChangePasswordCmd(VALID_EMAIL, PASSWORD, NEW_PASSWORD);

		authService = new AuthServiceImpl(accountService, userService, passwordHasher, authenticatorPort, tokenPort);
	}

	@Nested
	class Register {

		@BeforeEach
        void setUp() {
            when(passwordHasher.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);
        }

		@Test
		void givenWhenValidRequest_thenRegister() {
			authService.register(registerCmd);
			verify(accountService).save(accountCaptor.capture());
		}

		@Test
		void givenWhenValidRequest_thenCreateUser() {
			authService.register(registerCmd);
			verify(userService).save(userCaptor.capture());
		}

		@Test
		void givenWhenEmailExists_thenThrowConflictException() {
			authService.register(registerCmd);
			when(accountService.existsByEmail(registerCmd.email())).thenReturn(true);
			assertThrows(ConflictException.class, () -> authService.register(registerCmd));
		}

		@Test
        void givenWhenValidRequest_thenGenerateToken() {
            when(tokenPort.generateToken(registerCmd.email(), registerCmd.role())).thenReturn(TOKEN);

            AuthResult result = authService.register(registerCmd);

            assertNotNull(result.token());
        }
	}

	@Nested
	class Login {

		@Test
        void givenWhenValidRequest_thenLogin() {
            when(tokenPort.generateToken(VALID_EMAIL, loginCmd.role())).thenReturn(TOKEN);

            AuthResult result = authService.login(loginCmd);

            assertNotNull(result.token());
        }

		@Test
		void givenWhenInvalidCredentials_thenLoginFail() {
			doThrow(new UnAuthorizedException()).when(authenticatorPort).authenticate(VALID_EMAIL, PASSWORD);

			assertThrows(UnAuthorizedException.class, () -> authService.login(loginCmd));
		}

		@Test
        void givenWhenMustChangePassword_thenThrowForbiddenException() {
            when(accountService.isMustChangePassword(VALID_EMAIL)).thenReturn(true);

            assertThrows(ForbiddenException.class, () -> authService.login(loginCmd));
        }
	}

	@Nested
	class ChangePassword {

		@Test
		void givenWhenValidRequest_thenFindAccountByEmail() {
			Account account = new Account(UUID.randomUUID(), VALID_EMAIL, PASSWORD, Role.VETERINARIAN);
			when(accountService.findByEmail(VALID_EMAIL)).thenReturn(account);

			authService.changePassword(changePasswordCmd);

			verify(accountService).findByEmail(VALID_EMAIL);
		}

		@Test
		void givenWhenValidRequest_thenUpdatePasswordForAccountId() {
			UUID accountId = UUID.randomUUID();
			Account account = new Account(accountId, VALID_EMAIL, PASSWORD, Role.VETERINARIAN);
			when(accountService.findByEmail(VALID_EMAIL)).thenReturn(account);

			authService.changePassword(changePasswordCmd);

			verify(accountService).updatePassword(eq(accountId), any(UpdatePasswordCmd.class));
		}
	}
}