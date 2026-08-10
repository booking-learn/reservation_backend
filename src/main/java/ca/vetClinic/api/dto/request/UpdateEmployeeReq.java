package ca.vetClinic.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateEmployeeReq(@NotBlank(message = "The firstName is required") @NotNull String firstName,
		@NotBlank(message = "The lastName is required") @NotNull String lastName,
		@Pattern(regexp = "^\\+?[0-9]{10,15}$") String phoneNumber) {
}
