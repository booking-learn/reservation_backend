package ca.vetClinic.domain.service;

import ca.vetClinic.application.command.UpdatePetCmd;
import ca.vetClinic.domain.model.Pet;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PetService {
	void save(Pet pet);

	List<Pet> findAll();

	List<Pet> findAllByOwnerEmail(String email);

	Pet createPet(UUID ownerId, String name, String species, String breed, String gender, LocalDate birthDate);

	Pet findByPetId(UUID petId);

	UUID findOwnerId(String email);

	void update(UUID petId, UpdatePetCmd cmd);

	void updateByOwner(String ownerEmail, UUID petId, UpdatePetCmd cmd);

	void deleteById(UUID id);

	void deleteByOwnerEmail(String email);
}
