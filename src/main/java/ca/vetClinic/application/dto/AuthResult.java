package ca.vetClinic.application.dto;

public record AuthResult(String token, String tokenType, long expiresIn) {
}
