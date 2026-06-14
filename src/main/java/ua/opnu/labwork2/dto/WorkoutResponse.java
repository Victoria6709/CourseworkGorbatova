package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ua.opnu.labwork2.model.Workout;
import java.time.LocalDateTime;

public record WorkoutResponse(
        @Schema(description = "Унікальний ідентифікатор тренування в базі даних", example = "12")
        Long id,

        @Schema(description = "Назва тренування", example = "Йога для початківців")
        String title,

        @Schema(description = "Детальний опис тренування", example = "Комплекс вправ для розтяжки, гнучкості та правильного дихання")
        String description,

        @Schema(description = "Дата та час початку заняття", example = "2026-06-15T18:30:00")
        LocalDateTime date,

        @Schema(description = "Тривалість у хвилинах", example = "60")
        Integer durationMinutes
) {
    public static WorkoutResponse fromEntity(Workout workout) {
        if (workout == null) return null;
        return new WorkoutResponse(
                workout.getId(),
                workout.getTitle(),
                workout.getDescription(),
                workout.getDate(),
                workout.getDurationMinutes()
        );
    }
}
