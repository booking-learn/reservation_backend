package ca.vetClinic.api.dto.response;

import ca.vetClinic.domain.enumerator.CareService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CareResponse(@NotBlank(message = "The name is required") @NotNull String name,
		@NotBlank(message = "The description is required") @NotNull String description, @Positive double price,
		@Positive int duration, @NotNull CareService careService) {
}
