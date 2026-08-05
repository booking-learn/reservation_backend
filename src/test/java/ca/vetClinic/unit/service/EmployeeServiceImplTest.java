package ca.vetClinic.unit.service;

import ca.vetClinic.application.service.EmployeeServiceImpl;
import ca.vetClinic.domain.enumerator.Role;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.model.Employee;
import ca.vetClinic.domain.repository.EmployeRepository;
import ca.vetClinic.domain.service.AccountService;
import ca.vetClinic.domain.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
	private final String FIRST_NAME = "jacob";
	private final String LAST_NAME = "houle";
	private final String PHONE_NUMBER = "1234567890";
	private final UUID uuid = UUID.randomUUID();

	@Mock
	private EmployeRepository employeeRepository;
	@Captor
	private ArgumentCaptor<Employee> employeeCaptor;
	@Captor
	private ArgumentCaptor<Account> accountCaptor;
	@Spy
	private AccountService accountService;
	private PasswordEncoder passwordEncoder;
	private EmployeeService employeeService;

	@BeforeEach
	void setUp() {
		passwordEncoder = new BCryptPasswordEncoder();
		employeeService = new EmployeeServiceImpl(employeeRepository, accountService, passwordEncoder);
	}

	@Nested
	class Find {
		@Test
		void thenReturnAllEmployees() {
			List<Employee> employees = List
					.of(new Employee(uuid, UUID.randomUUID(), FIRST_NAME, LAST_NAME, PHONE_NUMBER));
			when(employeeRepository.findAll()).thenReturn(employees);

			List<Employee> result = employeeService.findAll();

			assertEquals(employees, result);
		}

		@Test
		void givenValidId_thenReturnEmployee() {
			Employee employee = new Employee(uuid, UUID.randomUUID(), FIRST_NAME, LAST_NAME, PHONE_NUMBER);
			when(employeeRepository.findById(uuid)).thenReturn(employee);

			Employee result = employeeService.findById(uuid);
			assertEquals(employee, result);
		}

		@Test
		void givenNullId_thenThrowIllegalArgumentException() {
			assertThrows(IllegalArgumentException.class, () -> employeeService.findById(null));
			verify(employeeRepository, never()).findById(any());
		}

		@Test
		void givenValidAccountId_thenReturnEmployee() {
			Employee employee = new Employee(uuid, UUID.randomUUID(), FIRST_NAME, LAST_NAME, PHONE_NUMBER);
			when(employeeRepository.findByAccountId(uuid)).thenReturn(employee);

			Employee result = employeeService.findByAccountId(uuid);

			assertEquals(employee, result);
		}

		@Test
		void givenNullAccountId_thenThrowIllegalArgumentException() {
			assertThrows(IllegalArgumentException.class, () -> employeeService.findByAccountId(null));
			verify(employeeRepository, never()).findByAccountId(any());
		}
	}

	@Nested
	class Save {
		@Test
		void givenEmployee_thenSaveIsCalledOnRepository() {
			Employee employee = new Employee(uuid, UUID.randomUUID(), FIRST_NAME, LAST_NAME, PHONE_NUMBER);

			employeeService.save(employee);

			verify(employeeRepository, times(1)).save(employee);
		}
	}

	@Nested
	class Delete {
		@Test
		void givenValidId_thenDeleteIsCalledOnRepository() {
			employeeService.delete(uuid);
			verify(employeeRepository, times(1)).delete(uuid);
		}

		@Test
		void givenNullId_thenThrowIllegalArgumentException() {
			assertThrows(IllegalArgumentException.class, () -> employeeService.delete(null));
			verify(employeeRepository, never()).delete(any());
		}
	}

	@Nested
	class Create {
		@Test
		void givenValidName_thenCreateEmail() {
			String email = employeeService.createEmail(FIRST_NAME, LAST_NAME);
			assertEquals("jacob.houle@vetClinic.ca", email);
		}

		@Test
		void givenValidName_thenCreateUniqueEmail() {
			doReturn(true).when(accountService).existsByEmail("jacob.houle@vetClinic.ca");
			employeeService.createEmail(FIRST_NAME, LAST_NAME);
			String email = employeeService.createEmail(FIRST_NAME, LAST_NAME);
			assertEquals("jacob.houle2@vetClinic.ca", email);
		}

		@Test
		void thenCreateTemporaryPasswordOfLength10() {
			String password = employeeService.createTemporaryPassword();
			assertEquals(10, password.length());
		}

		@Test
		void givenValidInfo_thenCreateAccountWithHashedPasswordAndCorrectRole() {
			Account account = employeeService.createAccount(FIRST_NAME, LAST_NAME, Role.RECEPTIONIST);
			verify(accountService, times(1)).save(accountCaptor.capture());
			assertTrue(account.getPassword().startsWith("$2"));
		}

	}
}