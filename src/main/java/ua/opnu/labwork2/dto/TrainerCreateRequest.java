package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TrainerCreateRequest(
        @Schema(description = "Ім'я тренера", example = "Дмитро", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "First name is required and cannot be empty or blank")
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        String firstName,

        @Schema(description = "Прізвище тренера", example = "Марченко", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Last name is required and cannot be empty or blank")
        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
        String lastName,

        @Schema(description = "Напрямок спеціалізації тренера", example = "Кроссфіт, Стретчинг", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Specialization is required and cannot be empty or blank")
        @Size(min = 2, max = 150, message = "Specialization must be between 2 and 150 characters")
        String specialization,

        @Schema(description = "Контактний email тренера", example = "dmitro.coach@gym.com", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Email is required")
        @Email(message = "Email must match valid email format")
        @Size(max = 100, message = "Email cannot exceed 100 characters")
        String email
) {}
