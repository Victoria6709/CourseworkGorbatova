package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record WorkoutCreateRequest(
        @Schema(description = "Назва тренувальної програми", example = "Йога для початківців", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Title is required and cannot be empty or blank")
        @Size(min = 2, max = 150, message = "Title must be between 2 and 150 characters")
        String title,

        @Schema(description = "Детальний опис вправ та вимог до заняття", example = "Комплекс вправ для розтяжки, гнучкості та правильного дихання", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Description is required")
        @Size(min = 10, max = 2000, message = "Description must be between 10 and 2000 characters")
        String description,

        @Schema(description = "Дата та час проведення заняття (ISO-формат)", example = "2026-06-15T18:30:00", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Date is required")
        @FutureOrPresent(message = "Workout date cannot be in the past")
        LocalDateTime date,

        @Schema(description = "Тривалість тренування у хвилинах", example = "60", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Duration is required")
        @Positive(message = "Duration must be a positive number")
        Integer durationMinutes,

        @Schema(description = "ID призначеного тренера", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Trainer ID is required")
        Long trainerId
) {}
