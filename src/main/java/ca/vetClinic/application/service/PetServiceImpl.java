package ca.vetClinic.application.service;

import ca.vetClinic.application.command.UpdatePetCmd;
import ca.vetClinic.domain.model.Pet;
import ca.vetClinic.domain.repository.PetRepository;
import ca.vetClinic.domain.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {
	private final PetRepository petRepository;

	private void validateUUID(UUID uuid) {
		if (uuid == null) {
			throw new IllegalArgumentException("UUID is null");
		}
	}

	@Override
	public void save(Pet pet) {
		petRepository.save(pet);
	}

	@Override
	public List<Pet> findAll() {
		return petRepository.findAll();
	}

	@Override
	public List<Pet> findAllByOwnerId(UUID ownerId) {
		validateUUID(ownerId);
		return petRepository.findAllByUserId(ownerId);
	}

	@Override
	public Pet findByOwnerId(UUID ownerId) {
		return petRepository.findByUserId(ownerId);
	}

	@Override
	public Pet createPet(UUID ownerId, String name, String species, String breed, String gender, LocalDate birthDate) {
		return new Pet(null, ownerId, name, species, breed, gender, birthDate);
	}

	@Override
	public Pet findByPetId(UUID petId) {
		validateUUID(petId);
		return petRepository.findByUserId(petId);
	}

	@Override
	public void update(UUID petId, UpdatePetCmd cmd) {
		validateUUID(petId);
		Pet pet = findByPetId(petId);
		pet.setName(cmd.name());
		petRepository.save(pet);
	}
	@Override
	public void deleteById(UUID id) {
		validateUUID(id);
		petRepository.deleteById(id);
	}

	@Override
	public void deleteByOwnerId(UUID ownerId) {
		Pet pet = findByOwnerId(ownerId);
		petRepository.deleteById(pet.getId());
	}
}
