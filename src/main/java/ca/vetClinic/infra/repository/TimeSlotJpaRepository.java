package ca.vetClinic.infra.repository;

import ca.vetClinic.infra.entity.TimeSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TimeSlotJpaRepository extends JpaRepository<TimeSlotEntity, UUID> {
}
