package ca.vetClinic.domain.repository;

import ca.vetClinic.domain.model.Pet;

import java.util.List;
import java.util.UUID;

public interface PetRepository {
	void save(Pet pet);

	Pet findById(UUID id);

	List<Pet> findAll();

	Pet findByUserId(UUID userId);

	List<Pet> findAllByUserId(UUID userId);

	void deleteById(UUID id);
}
