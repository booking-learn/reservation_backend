package ca.vetClinic.application.command;

import ca.vetClinic.domain.enumerator.Role;

public record ChangePasswordCmd(String email, String oldPassword, String newPassword) {
}
