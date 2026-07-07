package ca.vetClinic.integration.service;

import ca.vetClinic.application.command.UpdateEmailCmd;
import ca.vetClinic.application.command.UpdatePasswordCmd;
import ca.vetClinic.base.AbstractContainerBase;
import ca.vetClinic.domain.enumerator.Role;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.repository.AccountRepository;
import ca.vetClinic.domain.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
@Sql(scripts = "/sql/CleanUp.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class AccountServiceIntegrationTest extends AbstractContainerBase {

	private final String OLD_PASSWORD = "oldPassword";
	private final String NEW_PASSWORD = "newPassword";
	private final String BAD_PASSWORD = "badPassword";
	private final String OLD_EMAIL = "gontran@gmail.com";
	private final String NEW_EMAIL = "gont@gmail.com";
	private final String BAD_EMAIL = "g@gmail.com";
	private UpdatePasswordCmd updatePasswordCmd;
	private UpdatePasswordCmd invalidUpdatePasswordCmd;
	private UpdateEmailCmd updateEmailCmd;
	private UpdateEmailCmd invalidUpdateEmailCmd;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountRepository accountRepository;
	private Account account;

	private String encodePassWord(String password) {
		return passwordEncoder.encode(password);
	}

	private Account createAccount(String email) {
		return new Account(null, OLD_EMAIL, encodePassWord(OLD_PASSWORD), Role.USER);
	}

	@BeforeEach
	void setUp() {
		account = createAccount(OLD_EMAIL);
		updateEmailCmd = new UpdateEmailCmd(OLD_EMAIL, NEW_EMAIL);
		updatePasswordCmd = new UpdatePasswordCmd(OLD_PASSWORD, NEW_PASSWORD);
		invalidUpdateEmailCmd = new UpdateEmailCmd(BAD_EMAIL, OLD_EMAIL);
		invalidUpdatePasswordCmd = new UpdatePasswordCmd(BAD_PASSWORD, NEW_PASSWORD);
		accountRepository.save(account);
	}

	@Test
	void givenValidOldEmail_thenUpdateEmailPersistsChange() {
		accountService.updateEmail(account.getId(), updateEmailCmd);
		Account updated = accountRepository.findById(account.getId());
		assertEquals(NEW_EMAIL, updated.getEmail());
	}

	@Test
	void givenInvalidValidOldEmail_thenDontUpdateEmailPersists() {
		assertThrows(BadCredentialsException.class,
				() -> accountService.updateEmail(account.getId(), invalidUpdateEmailCmd));
	}

	@Test
	void givenValidOldPassword_thenUpdatePasswordPersistsChange() {
		accountService.updatePassword(account.getId(), updatePasswordCmd);
		Account updated = accountRepository.findById(account.getId());
		assertTrue(passwordEncoder.matches(NEW_PASSWORD, updated.getPassword()));
	}

	@Test
	void givenInvalidOldPassword_thenDontUpdatePasswordPersists() {
		assertThrows(BadCredentialsException.class,
				() -> accountService.updatePassword(account.getId(), invalidUpdatePasswordCmd));
	}

}