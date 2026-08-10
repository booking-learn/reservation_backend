package ca.vetClinic.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdatePasswordReq(@NotBlank(message = "The old password is required") @NotNull String oldPassword,
		@NotBlank(message = "The new password is required") @NotNull String newPassword) {
}
