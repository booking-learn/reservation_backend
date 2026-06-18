package ca.vetClinic.infra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "time_slots")
public class TimeSlotEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "vetenerian_id", nullable = false)
	private UUID vetenerianId;

	@Column(name = "start_time", nullable = false)
	private LocalTime startTime;

	@Column(name = "end_time", nullable = false)
	private LocalTime endTime;

	@Column(name = "date", nullable = false)
	private LocalDate date;

	@Column(name = "available", nullable = false)
	private boolean available;

	public TimeSlotEntity() {
	}

	public TimeSlotEntity(UUID vetenerianId, LocalTime startTime, LocalTime endTime, LocalDate date,
			boolean available) {
		this.vetenerianId = vetenerianId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.date = date;
		this.available = available;
	}
}