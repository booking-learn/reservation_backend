package ca.vetClinic.api.dto.request;

import jakarta.validation.constraints.*;

public record RegisterRequest(
		@NotBlank(message = "The email is required") @Email(message = "The email format is not valid") @NotNull String email,
		@NotBlank(message = "The password is required") @NotNull String password,
		@NotBlank(message = "The firstName is required") @NotNull String firstName,
		@NotBlank(message = "The lastName is required") @NotNull String lastName,
		@Pattern(regexp = "^\\+?[0-9]{10,15}$") String phoneNumber) {
}
