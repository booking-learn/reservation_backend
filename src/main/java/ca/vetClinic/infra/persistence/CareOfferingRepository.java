package ca.vetClinic.infra.persistence;

import ca.vetClinic.infra.entity.CareOffering;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CareOfferingRepository extends JpaRepository<CareOffering, UUID> {
}
