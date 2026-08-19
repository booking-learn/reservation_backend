package ca.vetClinic.integration.repository;

import ca.vetClinic.base.AbstractContainerBase;
import ca.vetClinic.domain.enumerator.Role;
import ca.vetClinic.domain.exception.NotFoundException;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.model.Employee;
import ca.vetClinic.domain.repository.AccountRepository;
import ca.vetClinic.domain.repository.EmployeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Sql(scripts = "/sql/CleanUp.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class EmployeRepositoryTest extends AbstractContainerBase {

	private final String EMAIL = "jacob@gmail.com";
	private final String OTHER_EMAIL = "other@email.com";
	private final String PASSWORD = "qwerty";
	private final String FIRST_NAME = "Jacob";
	private final String LAST_NAME = "Tremblay";
	private final String PHONE_NUMBER = "418-555-1234";
	private final String UPDATED_FIRST_NAME = "Jean";
	private final int EXPECTED_TWO = 2;
	@Autowired
	private EmployeRepository repository;
	@Autowired
	private AccountRepository accountRepository;

	private Account createAndSaveAccount(String email, Role role) {
		Account account = new Account(null, email, PASSWORD, role);
		accountRepository.save(account);
		return accountRepository.findByEmail(email);
	}

	private Employee createEmployee(UUID accountId) {
		return new Employee(null, accountId, FIRST_NAME, LAST_NAME, PHONE_NUMBER);
	}

	@Test
	void givenEmployeeSaved_thenFindByIdPresent() {
		Account account = createAndSaveAccount(EMAIL, Role.USER);
		Employee employee = createEmployee(account.getId());
		repository.save(employee);

		Employee found = repository.findById(employee.getId());
		assertEquals(employee.getFirstName(), found.getFirstName());
		assertEquals(employee.getAccountId(), found.getAccountId());
	}

	@Test
	void givenEmployeeIdNotFound_thenThrowNotFoundException() {
		UUID randomId = UUID.randomUUID();
		assertThrows(NotFoundException.class, () -> repository.findById(randomId));
	}

	@Test
	void givenEmployeesSaved_thenFindAll() {
		Account first = createAndSaveAccount(EMAIL, Role.USER);
		Account second = createAndSaveAccount(OTHER_EMAIL, Role.USER);
		repository.save(createEmployee(first.getId()));
		repository.save(createEmployee(second.getId()));

		List<Employee> found = repository.findAll();

		assertEquals(EXPECTED_TWO, found.size());
	}

	@Test
	void givenZeroEmployeesSaved_thenReturnNothing() {
		List<Employee> found = repository.findAll();
		assertTrue(found.isEmpty());
	}

	@Test
	void givenExistingEmployee_whenSaveCalledWithId_thenUpdate() {
		Account account = createAndSaveAccount(EMAIL, Role.USER);
		Employee employee = createEmployee(account.getId());
		repository.save(employee);

		Employee toUpdate = repository.findById(employee.getId());
		toUpdate.setFirstName(UPDATED_FIRST_NAME);
		repository.save(toUpdate);

		Employee updated = repository.findById(employee.getId());
		assertEquals(UPDATED_FIRST_NAME, updated.getFirstName());
	}

	@Test
	void givenEmployeeDeleted_thenDontFindEmployee() {
		Account account = createAndSaveAccount(EMAIL, Role.USER);
		Employee employee = createEmployee(account.getId());
		repository.save(employee);

		repository.delete(employee.getId());

		List<Employee> found = repository.findAll();
		assertTrue(found.isEmpty());
	}

	@Test
	void givenEmployeeSaved_thenFindByAccountIdPresent() {
		Account account = createAndSaveAccount(EMAIL, Role.USER);
		Employee employee = createEmployee(account.getId());
		repository.save(employee);

		Employee found = repository.findByAccountId(account.getId());

		assertEquals(employee.getFirstName(), found.getFirstName());
	}

	@Test
	void givenAccountIdNotFound_thenThrowNotFoundExceptionOnFindByAccountId() {
		UUID randomId = UUID.randomUUID();
		assertThrows(NotFoundException.class, () -> repository.findByAccountId(randomId));
	}

	@Test
	void givenEmployeeSaved_thenFindByRolePresent() {
		Account account = createAndSaveAccount(EMAIL, Role.USER);
		Employee employee = createEmployee(account.getId());
		repository.save(employee);

		Employee found = repository.findByRole(Role.USER);

		assertEquals(employee.getFirstName(), found.getFirstName());
	}

	@Test
	void givenRoleNotFound_thenThrowNotFoundExceptionOnFindByRole() {
		assertThrows(NotFoundException.class, () -> repository.findByRole(Role.USER));
	}
}