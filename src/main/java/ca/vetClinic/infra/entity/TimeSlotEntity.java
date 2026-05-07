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
@Table(name = "timeSlots")
public class TimeSlotEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private UUID vetenerianId;
	private LocalTime startTime;
	private LocalTime endTime;
	private LocalDate date;
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
