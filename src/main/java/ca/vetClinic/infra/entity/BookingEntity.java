package ca.vetClinic.infra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "bookings")
public class BookingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
	private UUID id;
	@Column(name = "user_id", nullable = false)
	private UUID userId;
	@Column(name = "service_id", nullable = false)
	private UUID serviceId;
	@Column(name = "pet_id", nullable = false)
	private UUID petId;
	@Column(name = "veterinarian_id", nullable = false)
	private UUID veterinarianId;
	@Column(name = "time_slot_id", nullable = false)
	private UUID timeSlotId;
	@Column(name = "status", nullable = false)
	private String status;
	@Column(name = "notes", nullable = false)
	private String notes;
	@CreationTimestamp
	@Column(name = "booking_created_at", nullable = false, updatable = false)
	private Instant bookingCreatedAt;
	@UpdateTimestamp
	@Column(name = "booking_updated_at", nullable = false)
	private Instant bookingUpdatedAt;

	public BookingEntity(UUID userId, UUID vetId, UUID serviceId, UUID petId, UUID timeSlotId, String status,
			String notes) {
		this.userId = userId;
		this.serviceId = serviceId;
		this.petId = petId;
		this.timeSlotId = timeSlotId;
		this.status = status;
		this.notes = notes;
		this.veterinarianId = vetId;
	}

	public BookingEntity() {
	}

}
