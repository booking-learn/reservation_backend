package ca.vetClinic.infra.repository;

import ca.vetClinic.domain.enumerator.Role;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Rollback
public abstract class AbstractAccountRepositoryTest {

	private final String EMAIL = "jacob@gmail.com";
	private final String OTHER_EMAIL = "other@email.com";
	private final String PASSWORD = "qwerty";
	private final int EXPECTED_TWO = 2;

	protected AccountRepository repository;

	@BeforeEach
	void setUp() {
		repository = createRepository();
	}

	protected abstract AccountRepository createRepository();

	private Account createAccount(String email) {
		return new Account(null, email, PASSWORD, Role.USER);
	}

	@Test
	void givenAcountSaved_thenFindByIdPresent() {
		Account account = createAccount(EMAIL);
		repository.save(account);
		Account saved = repository.findByEmail(EMAIL);
		Account found = repository.findById(saved.getId());
		assertEquals(saved, found);
	}

	@Test
	void givenAccountsSaved_thenFindAll() {
		Account first = createAccount(EMAIL);
		Account second = createAccount(OTHER_EMAIL);
		repository.save(first);
		repository.save(second);
		List<Account> found = repository.findAll();
		assertEquals(EXPECTED_TWO, found.size());
	}

	@Test
	void givenZeroAccountsSaved_thenReturnNothing() {
		List<Account> found = repository.findAll();
		assertTrue(found.isEmpty());
	}

	@Test
	void givenAcountSaved_thenFindByEmailPresent() {
		Account account = createAccount(EMAIL);
		repository.save(account);
		Account found = repository.findByEmail(EMAIL);
		assertEquals(account.getEmail(), found.getEmail());
	}

	@Test
	void givenAccountDeleted_thenDontFindAccount() {
		Account account = createAccount(EMAIL);
		repository.save(account);
		Account saved = repository.findByEmail(EMAIL);
		repository.deleteById(saved.getId());
		List<Account> found = repository.findAll();
		assertTrue(found.isEmpty());
	}
}