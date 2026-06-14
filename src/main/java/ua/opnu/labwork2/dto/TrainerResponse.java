package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ua.opnu.labwork2.model.Trainer;

public record TrainerResponse(
        @Schema(description = "ID тренера в базі даних", example = "3")
        Long id,
        @Schema(description = "Ім'я", example = "Дмитро")
        String firstName,
        @Schema(description = "Прізвище", example = "Марченко")
        String lastName,
        @Schema(description = "Спеціалізація", example = "Кроссфіт, Стретчинг")
        String specialization,
        @Schema(description = "Email", example = "dmitro.coach@gym.com")
        String email
) {
    public static TrainerResponse fromEntity(Trainer trainer) {
        if (trainer == null) return null;
        return new TrainerResponse(
                trainer.getId(),
                trainer.getFirstName(),
                trainer.getLastName(),
                trainer.getSpecialization(),
                trainer.getEmail()
        );
    }
}
