package ca.vetClinic.infra.persistence;

import ca.vetClinic.infra.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PetRepository extends JpaRepository<PetEntity, UUID> {
}
