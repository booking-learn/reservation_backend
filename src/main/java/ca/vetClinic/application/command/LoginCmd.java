package ca.vetClinic.application.command;

import ca.vetClinic.domain.enumerator.Role;

public record LoginCmd(String email, String password, Role role) {
}
