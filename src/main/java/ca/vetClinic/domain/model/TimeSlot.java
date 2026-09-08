package ca.vetClinic.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class TimeSlot {

	private UUID id;
	private UUID vetenerianId;
	private LocalTime startTime;
	private LocalTime endTime;
	private LocalDate date;
	private boolean available;

	public TimeSlot(UUID id, UUID vetenerianId, LocalTime startTime, LocalTime endTime, LocalDate date) {
		this.id = id;
		this.vetenerianId = vetenerianId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.date = date;
		this.available = true;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getVetenerianId() {
		return vetenerianId;
	}

	public void setVetenerianId(UUID vetenerianId) {
		this.vetenerianId = vetenerianId;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		TimeSlot timeSlot = (TimeSlot) o;
		return this.id.equals(timeSlot.id);
	}
}
