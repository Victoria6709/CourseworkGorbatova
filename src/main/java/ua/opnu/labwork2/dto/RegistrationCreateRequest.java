package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public record RegistrationCreateRequest(
        @Schema(description = "ID клієнта, який записується", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Member ID is required")
        Long memberId,

        @Schema(description = "ID тренування, на яке проводиться запис", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Workout ID is required")
        Long workoutId,

        @Schema(description = "Дата та час створення запису", example = "2026-06-04T15:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Registration date is required")
        LocalDateTime registrationDate,

        @Schema(description = "Початковий статус запису (ACTIVE або CANCELLED)", example = "ACTIVE", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Status is required")
        @Pattern(regexp = "^(ACTIVE|CANCELLED)$", message = "Status must be either ACTIVE or CANCELLED")
        String status
) {}