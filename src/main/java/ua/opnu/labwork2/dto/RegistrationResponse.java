package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ua.opnu.labwork2.model.Registration;

import java.time.LocalDateTime;

public record RegistrationResponse(
        @Schema(description = "Унікальний ID запису", example = "100")
        Long id,
        @Schema(description = "Дата реєстрації заняття", example = "2026-06-04T15:00:00")
        LocalDateTime registrationDate,
        @Schema(description = "Поточний статус", example = "ACTIVE")
        String status
) {
    public static RegistrationResponse fromEntity(Registration registration) {
        if (registration == null) return null;
        return new RegistrationResponse(
                registration.getId(),
                registration.getRegistrationDate(),
                registration.getStatus() != null ? registration.getStatus().name() : null
        );
    }
}