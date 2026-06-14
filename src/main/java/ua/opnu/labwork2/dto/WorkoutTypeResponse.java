package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ua.opnu.labwork2.model.WorkType;

public record WorkoutTypeResponse(
        @Schema(description = "ID типу тренування", example = "2")
        Long id,
        @Schema(description = "Назва категорії", example = "Пілатес")
        String name,
        @Schema(description = "Опис категорії занять", example = "Система вправ для зміцнення м'язів кору та покращення постави.")
        String description
) {
    public static WorkoutTypeResponse fromEntity(WorkType workType) {
        if (workType == null) return null;
        return new WorkoutTypeResponse(
                workType.getId(),
                workType.getName(),
                workType.getDescription()
        );
    }
}
