package ca.vetClinic.infra.repository.jpa;

import ca.vetClinic.infra.entity.CareOfferingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CareOfferingJpaRepository extends JpaRepository<CareOfferingEntity, UUID> {
}
