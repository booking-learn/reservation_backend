package ca.vetClinic.infra.repository.jpa;

import ca.vetClinic.domain.enumerator.CareService;
import ca.vetClinic.infra.entity.CareOfferingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CareOfferingJpaRepository extends JpaRepository<CareOfferingEntity, UUID> {
	List<CareOfferingEntity> findByCareService(CareService careService);
}
