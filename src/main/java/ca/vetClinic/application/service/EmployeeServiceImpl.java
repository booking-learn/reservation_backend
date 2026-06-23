package ca.vetClinic.application.service;

import ca.vetClinic.domain.model.Employee;
import ca.vetClinic.domain.repository.EmployeRepository;
import ca.vetClinic.domain.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
	private final EmployeRepository employeRepository;
	@Override
	public List<Employee> findAll() {
		return employeRepository.findAll();
	}

	@Override
	public Employee findById(UUID id) {
		return employeRepository.findById(id);
	}

	@Override
	public void save(Employee employee) {
		employeRepository.save(employee);
	}

	@Override
	public void delete(UUID id) {
		employeRepository.delete(id);
	}

	@Override
	public Employee findByAccountId(UUID id) {
		return employeRepository.findByAccountId(id);
	}
}
