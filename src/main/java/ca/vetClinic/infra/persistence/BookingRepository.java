package ca.vetClinic.infra.persistence;

import ca.vetClinic.infra.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {

}
