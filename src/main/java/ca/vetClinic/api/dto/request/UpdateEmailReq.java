package ca.vetClinic.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateEmailReq(
		@NotBlank(message = "The old email is required") @Email(message = "The old email format is not valid") @NotNull String oldEmail,
		@NotBlank(message = "The new email is required") @Email(message = "The new email format is not valid") @NotNull String newEmail) {
}
