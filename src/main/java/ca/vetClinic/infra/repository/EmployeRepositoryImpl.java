package ca.vetClinic.infra.repository;

import ca.vetClinic.domain.exception.NotFoundException;
import ca.vetClinic.domain.model.Employee;
import ca.vetClinic.domain.repository.EmployeRepository;
import ca.vetClinic.infra.entity.EmployeEntity;
import ca.vetClinic.infra.mapper.EmployeMapper;
import ca.vetClinic.infra.repository.jpa.EmployeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class EmployeRepositoryImpl implements EmployeRepository {
	private final EmployeJpaRepository jpaRepository;
	private final EmployeMapper mapper;

	@Override
	public List<Employee> findAll() {
		return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
	}

	@Override
	public Employee findById(UUID id) {
		return jpaRepository.findById(id).map(mapper::toDomain).orElseThrow(() -> new NotFoundException("id"));
	}

	@Override
	public void save(Employee employee) {
		EmployeEntity entity = mapper.toEntity(employee);
		jpaRepository.save(entity);
		employee.setId(entity.getId());
	}

	@Override
	public void delete(UUID id) {
		jpaRepository.deleteById(id);
	}

	@Override
	public Employee findByAccountId(UUID id) {
		return jpaRepository.findByAccountId(id).map(mapper::toDomain)
				.orElseThrow(() -> new NotFoundException("accountId"));
	}
}
