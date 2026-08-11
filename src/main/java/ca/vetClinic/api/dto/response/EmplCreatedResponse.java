package ca.vetClinic.api.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EmplCreatedResponse(@NotBlank(message = "The password is required") @NotNull String password) {
}
