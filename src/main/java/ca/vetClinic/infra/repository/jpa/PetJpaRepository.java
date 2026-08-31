package ca.vetClinic.infra.repository.jpa;

import ca.vetClinic.infra.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PetJpaRepository extends JpaRepository<PetEntity, UUID> {
	Optional<PetEntity> findByUserId(UUID id);

	List<PetEntity> findAllByUserId(UUID userId);
}
