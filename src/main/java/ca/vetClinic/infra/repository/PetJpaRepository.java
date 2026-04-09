package ca.vetClinic.infra.repository;

import ca.vetClinic.infra.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PetJpaRepository extends JpaRepository<PetEntity, UUID> {
}
