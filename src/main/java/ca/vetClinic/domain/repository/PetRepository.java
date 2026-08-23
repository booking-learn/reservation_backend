package ca.vetClinic.domain.repository;

import ca.vetClinic.domain.model.Pet;

import java.util.List;
import java.util.UUID;

public interface PetRepository {
	Pet save(Pet pet);

	List<Pet> findByAll();

	List<Pet> findByOwnerId(UUID ownerId);

	void deleteById(UUID id);
}
