package ca.vetClinic.application.command;

public record UpdateUserCommand(String firstName, String lastName, String phoneNumber) {
}
