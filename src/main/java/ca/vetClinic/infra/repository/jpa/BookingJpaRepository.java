package ca.vetClinic.infra.repository.jpa;

import ca.vetClinic.infra.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookingJpaRepository extends JpaRepository<BookingEntity, UUID> {

}
