package ca.vetClinic.application.service;

import ca.vetClinic.domain.enumerator.Role;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.model.Employee;
import ca.vetClinic.domain.repository.EmployeRepository;
import ca.vetClinic.domain.service.AccountService;
import ca.vetClinic.domain.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
		employeRepository.save(employee);
	}

	@Override
	public void delete(UUID id) {
		validateUUID(id);
		employeRepository.delete(id);
	}

	@Override
	public Employee findByAccountId(UUID id) {
		validateUUID(id);
		return employeRepository.findByAccountId(id);
	}

	@Override
	public Account createAccount(String prenom, String nom, Role role) {
		String email = createEmail(prenom, nom);
		String password = createTemporaryPassword();
		Account account = new Account(null, email, passwordEncoder.encode(password), role);
		accountService.save(account);
		return account;
	}

	@Override
	public String createEmail(String prenom, String nom) {
		int counter = 1;
		String domain = "vetClinic.ca";
		String baseEmail = prenom + "." + nom;
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
}
