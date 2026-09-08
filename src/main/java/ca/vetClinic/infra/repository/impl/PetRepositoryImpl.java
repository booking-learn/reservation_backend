package ca.vetClinic.infra.repository.impl;

import ca.vetClinic.domain.exception.NotFoundException;
import ca.vetClinic.domain.model.Pet;
import ca.vetClinic.domain.repository.PetRepository;
import ca.vetClinic.infra.entity.PetEntity;
import ca.vetClinic.infra.mapper.PetMapper;
import ca.vetClinic.infra.repository.jpa.PetJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PetRepositoryImpl implements PetRepository {
	private final PetJpaRepository jpaRepository;
	private final PetMapper mapper;
	@Override
	public void save(Pet pet) {
		if (pet.getId() != null) {
			PetEntity entity = jpaRepository.findById(pet.getId()).orElseThrow(() -> new NotFoundException("id"));
			entity.setId(pet.getId());
			entity.setName(pet.getName());
			entity.setBirthDate(pet.getBirthDate());
			entity.setGender(pet.getGender());
			jpaRepository.save(entity);

		}
		PetEntity newPet = mapper.toEntity(pet);
		jpaRepository.save(newPet);
		pet.setId(newPet.getId());
	}

	@Override
	public Pet findById(UUID id) {
		return jpaRepository.findById(id).map(mapper::toDomain).orElseThrow(() -> new NotFoundException("id"));
	}

	@Override
	public List<Pet> findAll() {
		return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
	}

	@Override
	public List<Pet> findAllByUserId(UUID userId) {
		return jpaRepository.findAllByUserId(userId).stream().map(mapper::toDomain).toList();
	}

	@Override
	public void deleteById(UUID id) {
		jpaRepository.deleteById(id);
	}

	@Override
	public void deleteAllByUserId(UUID userId) {
		jpaRepository.deleteAllByUserId(userId);
	}
}
