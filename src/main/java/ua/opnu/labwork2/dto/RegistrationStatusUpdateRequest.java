package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegistrationStatusUpdateRequest(
        @Schema(description = "Новий статус запису на заняття (ACTIVE або CANCELLED)", example = "CANCELLED", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Status is required")
        @Pattern(regexp = "^(ACTIVE|CANCELLED)$", message = "Status must be either ACTIVE or CANCELLED")
        String status
) {}
