package ca.vetClinic.application.command;

import ca.vetClinic.domain.enumerator.Role;

public record RegisterCmd(String email, String password, String firstName, String lastName, String phoneNumber,
		Role role) {
}
