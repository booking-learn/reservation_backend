package ca.vetClinic.domain.service;

import ca.vetClinic.domain.model.TimeSlot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TimeSlotService {

	List<TimeSlot> generateAvailableSlots(UUID employeeId, UUID careOfferingId, LocalDate from, LocalDate to);

	boolean isSlotAvailable(UUID employeeId, LocalDateTime start, LocalDateTime end);

	boolean hasOverlap(UUID employeeId, LocalDateTime start, LocalDateTime end);

	void releaseSlot(UUID employeeId, LocalDateTime start, LocalDateTime end);
}
