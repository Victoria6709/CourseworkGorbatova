package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record WorkoutTypeUpdateRequest(
        @Schema(description = "Нова назва фітнес-напрямку", example = "Гаряча Йога", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Name is required and cannot be empty or blank")
        @Size(min = 2, max = 150, message = "Name must be between 2 and 150 characters")
        String name,

        @Schema(description = "Оновлений опис умов та специфіки занять", example = "Заняття йогою у спеціально підігрітому приміщенні для еластичності зв'язок.", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Description is required")
        @Size(min = 10, max = 2000, message = "Description must be between 10 and 2000 characters")
        String description
) {}
