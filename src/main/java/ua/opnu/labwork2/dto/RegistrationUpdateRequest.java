package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

public record RegistrationUpdateRequest(
        @Schema(description = "Оновлена дата та час запису на тренування", example = "2026-06-10T12:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Registration date is required")
        @FutureOrPresent(message = "Registration date cannot be in the past")
        LocalDateTime registrationDate,

        @Schema(description = "Оновлений статус запису", example = "ACTIVE", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Status is required")
        @Pattern(regexp = "^(ACTIVE|CANCELLED)$", message = "Status must be either ACTIVE or CANCELLED")
        String status
) {}
