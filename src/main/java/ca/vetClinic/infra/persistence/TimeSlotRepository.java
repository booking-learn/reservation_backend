package ca.vetClinic.infra.persistence;

import ca.vetClinic.infra.entity.TimeSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TimeSlotRepository extends JpaRepository<TimeSlotEntity, UUID> {
}
