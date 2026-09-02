package ca.vetClinic.api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PetReq(@NotBlank(message = "The name is required") @NotNull String name,
		@NotBlank(message = "The species is required") @NotNull String species,
		@NotBlank(message = "The breed is required") String breed,
		@NotBlank(message = "The gender is required") @NotNull String gender,
		@NotNull @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birthDate) {
}
