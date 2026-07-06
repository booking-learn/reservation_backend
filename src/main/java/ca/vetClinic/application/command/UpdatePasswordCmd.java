package ca.vetClinic.application.command;

public record UpdatePasswordCmd(String oldPassword, String newPassword) {
}
