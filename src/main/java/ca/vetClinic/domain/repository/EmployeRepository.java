package ca.vetClinic.domain.repository;

import ca.vetClinic.domain.enumerator.Role;
import ca.vetClinic.domain.model.Employee;

import java.util.List;
import java.util.UUID;

public interface EmployeRepository {
	List<Employee> findAll();

	Employee findById(UUID id);

	Employee findByRole(Role role);

	void save(Employee employee);

	void delete(UUID id);

	Employee findByAccountId(UUID id);
}
