package ca.vetClinic.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
		@NotBlank(message = "The email is required") @Email(message = "The email format is not valid") @NotNull String email,
		@NotBlank(message = "The password is required") @NotNull String password) {
}
