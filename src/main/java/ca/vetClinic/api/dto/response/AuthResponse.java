package ca.vetClinic.api.dto.response;

public record AuthResponse(String accessToken, String tokenType, long expiresIn) {
}
