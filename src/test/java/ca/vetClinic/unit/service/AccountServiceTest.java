package ca.vetClinic.unit.service;

import ca.vetClinic.application.command.UpdateEmailCmd;
import ca.vetClinic.application.command.UpdatePasswordCmd;
import ca.vetClinic.application.service.AccountServiceImpl;
import ca.vetClinic.domain.enumerator.Role;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

	private final String OLD_PASSWORD = "oldPassword";
	private final String NEW_PASSWORD = "newPassword";
	private final String BAD_PASSWORD = "badPassword";
	private final String OLD_EMAIL = "gontran@gmail.com";
	private final String NEW_EMAIL = "gont@gmail.com";
	private final String BAD_EMAIL = "g@gmail.com";
	private PasswordEncoder passwordEncoder;
	@Mock
	private AccountRepository accountRepository;
	@Captor
	ArgumentCaptor<Account> captor;
	private AccountServiceImpl accountService;
	private UpdatePasswordCmd updatePasswordCmd;
	private UpdatePasswordCmd invalidUpdatePasswordCmd;
	private UpdateEmailCmd updateEmailCmd;
	private UpdateEmailCmd invalidUpdateEmailCmd;
	private final UUID uuid = UUID.randomUUID();
	@Spy
	private Account account = new Account(null, OLD_EMAIL, OLD_PASSWORD, Role.USER);

	@BeforeEach
	void setUp() {
		passwordEncoder = new BCryptPasswordEncoder();
		updateEmailCmd = new UpdateEmailCmd(OLD_EMAIL, NEW_EMAIL);
		updatePasswordCmd = new UpdatePasswordCmd(OLD_PASSWORD, NEW_PASSWORD);
		invalidUpdateEmailCmd = new UpdateEmailCmd(BAD_EMAIL, OLD_EMAIL);
		invalidUpdatePasswordCmd = new UpdatePasswordCmd(BAD_PASSWORD, OLD_PASSWORD);
		accountService = new AccountServiceImpl(accountRepository, passwordEncoder);
	}
	@Test
	void givenNullAccount_thenThrowException() {
		assertThrows(IllegalArgumentException.class, () -> accountService.save(null));
	}
	@Test
	void givenWhenValidOldEmail_thenUpdateEmail() {
		doReturn(uuid).when(account).getId();
		doReturn(OLD_EMAIL).when(account).getEmail();
		when(accountRepository.findById(uuid)).thenReturn(account);

		accountService.updateEmail(account.getId(), updateEmailCmd);
		verify(accountRepository, times(1)).save(captor.capture());
	}
	@Test
	void givenWhenInvalidOldEmail_thenThrowException() {
		doReturn(OLD_EMAIL).when(account).getEmail();
		when(accountRepository.findById(uuid)).thenReturn(account);
		assertThrows(BadCredentialsException.class, () -> accountService.updateEmail(uuid, invalidUpdateEmailCmd));
	}
	@Test
	void givenWhenValidOldPassword_thenUpdatePassword() {
		doReturn(uuid).when(account).getId();
		String oldPassword = passwordEncoder.encode(OLD_PASSWORD);
		doReturn(oldPassword).when(account).getPassword();
		when(accountRepository.findById(uuid)).thenReturn(account);
		accountService.updatePassword(account.getId(), updatePasswordCmd);
		verify(accountRepository, times(1)).save(captor.capture());
	}
	@Test
	void givenUpdatedPassword_thenIsMustChangePasswordIsFalse() {
		doReturn(uuid).when(account).getId();
		String oldPassword = passwordEncoder.encode(OLD_PASSWORD);
		doReturn(oldPassword).when(account).getPassword();
		when(accountRepository.findById(uuid)).thenReturn(account);
		accountService.updatePassword(account.getId(), updatePasswordCmd);
		assertFalse(account.isMustChangePassword());
	}
	@Test
	void givenWhenInvalidOldPassword_thenThrowException() {
		doReturn(OLD_PASSWORD).when(account).getPassword();
		when(accountRepository.findById(uuid)).thenReturn(account);
		assertThrows(BadCredentialsException.class,
				() -> accountService.updatePassword(uuid, invalidUpdatePasswordCmd));
	}
}