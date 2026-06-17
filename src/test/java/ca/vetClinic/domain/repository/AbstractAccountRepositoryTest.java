package ca.vetClinic.domain.repository;

import ca.vetClinic.domain.enumerator.Role;
import ca.vetClinic.domain.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractAccountRepositoryTest {

	private final String EMAIL = "jacob@gmail.com";
	private final String PASSWORD = "qwerty";
	private final String OTHER_EMAIL = "jacob";
	private final UUID ID = UUID.randomUUID();
	private final UUID OTHER_ID = UUID.randomUUID();
	private final int EXPECTED_TWO = 2;

	protected AccountRepository repository;
	@BeforeEach
	void setUp() {
		repository = createRepository();
	}

	protected abstract AccountRepository createRepository();
	private Account createAccount(UUID ID, String EMAIL) {
		return new Account(ID, EMAIL, PASSWORD, Role.USER);
	}
	@Test
	void givenAcountSaved_thenFindByIdPresent() {
		Account account = createAccount(ID, EMAIL);
		repository.save(account);
		Account foundAccount = repository.findById(ID);
		assertEquals(account, foundAccount);
	}
	@Test
	void givenAccountsSaved_thenFindAll() {
		Account first_account = createAccount(ID, EMAIL);
		Account second_account = createAccount(OTHER_ID, OTHER_EMAIL);
		repository.save(first_account);
		repository.save(second_account);
		List<Account> foundAccounts = repository.findAll();
		assertEquals(EXPECTED_TWO, foundAccounts.size());
	}
	@Test
	void givenZeroAccountsSaved_thenReturnNothing() {
		List<Account> foundAccounts = repository.findAll();
		assertTrue(foundAccounts.isEmpty());
	}
	@Test
	void givenAcountSaved_thenFindByEmailPresent() {
		Account first_account = createAccount(ID, EMAIL);
		repository.save(first_account);
		Account foundAccount = repository.findByEmail(EMAIL);
		assertEquals(first_account, foundAccount);
	}
	@Test
	void givenAccountDeleted_thenDontFindAccount() {
		Account first_account = createAccount(ID, EMAIL);
		repository.save(first_account);
		repository.deleteById(ID);
		List<Account> foundAccounts = repository.findAll();
		assertTrue(foundAccounts.isEmpty());
	}

}