package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record WorkoutUpdateRequest(
        @Schema(description = "Змінена назва тренування", example = "Йога для просунутих", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Title is required and cannot be empty or blank")
        @Size(min = 2, max = 150, message = "Title must be between 2 and 150 characters")
        String title,

        @Schema(description = "Новий опис вправ або вимог до форми", example = "Просунуті асани та дихальні практики медитації.", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Description is required")
        @Size(min = 10, max = 2000, message = "Description must be between 10 and 2000 characters")
        String description,

        @Schema(description = "Нова дата та час проведення заняття", example = "2026-06-25T19:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Date is required")
        @FutureOrPresent(message = "Workout date cannot be in the past")
        LocalDateTime date,

        @Schema(description = "Змінена тривалість тренування у хвилинах", example = "90", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Duration is required")
        @Positive(message = "Duration must be a positive number")
        Integer durationMinutes
) {}
