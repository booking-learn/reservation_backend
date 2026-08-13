package ca.vetClinic.application.service;

import ca.vetClinic.application.command.UpdateEmployeeCmd;
import ca.vetClinic.application.command.UpdatePasswordCmd;
import ca.vetClinic.domain.enumerator.Role;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.model.Employee;
import ca.vetClinic.domain.model.User;
import ca.vetClinic.domain.repository.EmployeRepository;
import ca.vetClinic.domain.service.AccountService;
import ca.vetClinic.domain.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
	private final EmployeRepository employeRepository;
	private final AccountService accountService;
	private final PasswordEncoder passwordEncoder;
	private void validateUUID(UUID uuid) {
		if (uuid == null) {
			throw new IllegalArgumentException("UUID is null");
		}
	}
	@Override
	public List<Employee> findAll() {
		return employeRepository.findAll();
	}

	@Override
	public Employee findById(UUID id) {
		validateUUID(id);
		return employeRepository.findById(id);
	}

	@Override
	public void save(Employee employee) {
		if (employee.getAccountId() == null) {
			throw new IllegalArgumentException("Account id is null");
		}
		employeRepository.save(employee);
	}

	@Override
	public void delete(UUID id) {
		validateUUID(id);
		Employee employee = employeRepository.findById(id);
		accountService.deleteById(employee.getAccountId());
		employeRepository.delete(id);
	}

	@Override
	public Employee findByAccountId(UUID id) {
		validateUUID(id);
		return employeRepository.findByAccountId(id);
	}

	@Override
	public Account createAccount(String firstName, String name, Role role, String password) {
		String email = createEmail(firstName, name);
		Account account = new Account(null, email, passwordEncoder.encode(password), role);
		account.setMustChangePassword(true);
		accountService.save(account);
		return account;
	}

	@Override
	public String createEmail(String firstName, String name) {
		int counter = 1;
		String domain = "vetClinic.ca";
		String baseEmail = firstName + "." + name;
		String email = baseEmail + "@" + domain;
		while (accountService.existsByEmail(email)) {
			counter++;
			email = baseEmail + counter + "@" + domain;
		}
		return email;
	}

	@Override
	public String createTemporaryPassword() {
		String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
		SecureRandom random = new SecureRandom();
		StringBuilder password = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			password.append(allChars.charAt(random.nextInt(allChars.length())));
		}
		return password.toString();
	}

	@Transactional
	@Override
	public String createEmployee(String firstName, String lastName, String phoneNumber, Role role) {
		String password = createTemporaryPassword();
		Account account = createAccount(firstName, lastName, role, password);
		Employee employee = new Employee(null, account.getId(), firstName, lastName, phoneNumber);
		save(employee);
		return password;
	}

	@Override
	public void updateEmployee(UUID accountId, UpdateEmployeeCmd cmd) {
		validateUUID(accountId);
		Employee employee = findByAccountId(accountId);
		employee.setFirstName(cmd.firstName());
		employee.setLastName(cmd.lastName());
		employee.setPhoneNumber(cmd.phoneNumber());
		save(employee);
	}

}
