package ca.vetClinic.domain.repository;

import ca.vetClinic.domain.model.Employe;

import java.util.List;
import java.util.UUID;

public interface EmployeRepository {
	List<Employe> findAll();

	Employe findById(UUID id);

	void save(Employe employe);

	void delete(UUID id);

	Employe findByAccountId(UUID id);
}
