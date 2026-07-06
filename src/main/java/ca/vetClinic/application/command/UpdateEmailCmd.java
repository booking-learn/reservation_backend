package ca.vetClinic.application.command;

public record UpdateEmailCmd(String oldEmail, String newEmail) {
}
