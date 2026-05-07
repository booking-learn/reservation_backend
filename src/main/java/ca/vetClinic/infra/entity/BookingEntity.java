package ca.vetClinic.infra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "bookings")
public class BookingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private UUID userId;
	private UUID serviceId;
	private UUID petId;
	private UUID veterinarianId;
	private UUID timeSlotId;
	private String status;
	private String notes;
	@CreationTimestamp
	private Instant bookingCreatedAt;

	public BookingEntity(UUID userId, UUID serviceId, UUID petId, UUID timeSlotId, String status, String notes) {
		this.userId = userId;
		this.serviceId = serviceId;
		this.petId = petId;
		this.timeSlotId = timeSlotId;
		this.status = status;
		this.notes = notes;
	}

	public BookingEntity() {
	}

}
