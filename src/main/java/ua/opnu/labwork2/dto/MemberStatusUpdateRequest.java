package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record MemberStatusUpdateRequest(
        @Schema(description = "Новий статус клієнта в системі (ACTIVE або CANCELLED)", example = "ACTIVE", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Status is required")
        @Pattern(regexp = "^(ACTIVE|CANCELLED)$", message = "Status must be either ACTIVE or CANCELLED")
        String status
) {}
