package ca.vetClinic.infra.repository;

import ca.vetClinic.infra.entity.CareOffering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CareOfferingJpaRepository extends JpaRepository<CareOffering, UUID> {
}
