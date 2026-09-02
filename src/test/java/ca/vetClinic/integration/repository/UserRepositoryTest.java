package ca.vetClinic.integration.repository;

import ca.vetClinic.base.AbstractContainerBase;
import ca.vetClinic.domain.enumerator.Role;
import ca.vetClinic.domain.exception.NotFoundException;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.model.User;
import ca.vetClinic.domain.repository.AccountRepository;
import ca.vetClinic.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "/sql/CleanUp.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UserRepositoryTest extends AbstractContainerBase {

	private final String EMAIL = "jacob@gmail.com";
	private final String OTHER_EMAIL = "other@email.com";
	private final String PASSWORD = "qwerty";
	private final String FIRST_NAME = "Jacob";
	private final String LAST_NAME = "Tremblay";
	private final String PHONE_NUMBER = "418-555-1234";
	private final String UPDATED_FIRST_NAME = "Jean";
	private final int EXPECTED_TWO = 2;

	@Autowired
	private UserRepository repository;
	@Autowired
	private AccountRepository accountRepository;

	private Account createAndSaveAccount(String email) {
		Account account = new Account(null, email, PASSWORD, Role.USER);
		accountRepository.save(account);
		return accountRepository.findByEmail(email);
	}
	private User createUser(UUID accountId) {
		return new User(null, accountId, FIRST_NAME, LAST_NAME, PHONE_NUMBER, Instant.now());
	}
	@Test
	void givenUserSaved_thenFindByIdPresent() {
		Account account = createAndSaveAccount(EMAIL);
		User user = createUser(account.getId());
		repository.save(user);

		User found = repository.findById(user.getId());
		assertEquals(user.getFirstName(), found.getFirstName());
		assertEquals(user.getAccountId(), found.getAccountId());
	}

	@Test
	void givenUserIdNotFound_thenThrowNotFoundException() {
		UUID randomId = UUID.randomUUID();

		assertThrows(NotFoundException.class, () -> repository.findById(randomId));
	}

	@Test
	void givenUsersSaved_thenFindAll() {
		Account first = createAndSaveAccount(EMAIL);
		Account second = createAndSaveAccount(OTHER_EMAIL);
		repository.save(createUser(first.getId()));
		repository.save(createUser(second.getId()));

		List<User> found = repository.findAll();

		assertEquals(EXPECTED_TWO, found.size());
	}

	@Test
	void givenZeroUsersSaved_thenReturnNothing() {
		List<User> found = repository.findAll();

		assertTrue(found.isEmpty());
	}

	@Test
	void givenExistingUser_whenSaveCalledWithId_thenUpdate() {
		Account account = createAndSaveAccount(EMAIL);
		User user = createUser(account.getId());
		repository.save(user);

		User toUpdate = repository.findById(user.getId());
		toUpdate.setFirstName(UPDATED_FIRST_NAME);
		repository.save(toUpdate);

		User updated = repository.findById(user.getId());
		assertEquals(UPDATED_FIRST_NAME, updated.getFirstName());
	}

	@Test
	void givenUserDeleted_thenDontFindUser() {
		Account account = createAndSaveAccount(EMAIL);
		User user = createUser(account.getId());
		repository.save(user);

		repository.delete(user.getId());

		List<User> found = repository.findAll();
		assertTrue(found.isEmpty());
	}

	@Test
	void givenUserSaved_thenFindByAccountIdPresent() {
		Account account = createAndSaveAccount(EMAIL);
		User user = createUser(account.getId());
		repository.save(user);

		User found = repository.findByAccountId(account.getId());

		assertEquals(user.getFirstName(), found.getFirstName());
	}

	@Test
	void givenAccountIdNotFound_thenThrowNotFoundExceptionOnFindByAccountId() {
		UUID randomId = UUID.randomUUID();

		assertThrows(NotFoundException.class, () -> repository.findByAccountId(randomId));
	}
}