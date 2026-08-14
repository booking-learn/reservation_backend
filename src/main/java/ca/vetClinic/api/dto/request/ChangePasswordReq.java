package ca.vetClinic.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChangePasswordReq(
		@NotBlank(message = "The email is required") @Email(message = "The email format is not valid") @NotNull String email,
		@NotBlank(message = "The old password is required") @NotNull String oldPassword,
		@NotBlank(message = "The new password is required") @NotNull String newPassword) {
}
