package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberCreateRequest(
        @Schema(description = "Ім'я клієнта клубу", example = "Олександр", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "First name is required and cannot be empty or blank")
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        String firstName,

        @Schema(description = "Прізвище клієнта клубу", example = "Коваленко", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Last name is required and cannot be empty or blank")
        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
        String lastName,

        @Schema(description = "Унікальна електронна пошта клієнта", example = "olex.koval@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Email is required")
        @Email(message = "Email must match valid email format")
        @Size(max = 100, message = "Email cannot exceed 100 characters")
        String email,

        @Schema(description = "Контактний номер телефону", example = "+380501234567", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Phone number is required")
        @Size(min = 10, max = 20, message = "Phone must be between 10 and 20 characters")
        @Pattern(regexp = "^[0-9+\\s]+$", message = "Phone can only contain digits, spaces, and '+' symbol")
        String phone
) {}
