package ca.vetClinic.domain.service;

import ca.vetClinic.domain.enumerator.Role;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.model.Employee;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {
	List<Employee> findAll();

	Employee findById(UUID id);

	void save(Employee employee);

	void delete(UUID id);

	Employee findByAccountId(UUID id);

	Account createAccount(String prenom, String nom, Role role);

	String createEmail(String prenom, String nom);

	String createTemporaryPassword();
}
