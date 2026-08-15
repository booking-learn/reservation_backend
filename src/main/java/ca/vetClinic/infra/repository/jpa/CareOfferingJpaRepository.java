package ca.vetClinic.infra.repository.jpa;

import ca.vetClinic.domain.enumerator.CareService;
import ca.vetClinic.infra.entity.CareOfferingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CareOfferingJpaRepository extends JpaRepository<CareOfferingEntity, UUID> {
	Optional<CareOfferingEntity> findByCareService(CareService careService);
}
