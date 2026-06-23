package ca.vetClinic.api.dto;

import ca.vetClinic.domain.enumerator.Role;

import java.util.UUID;

public record EmployeeDto(UUID id, String email, String firstName, String lastName, Role role) {
}
