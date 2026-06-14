package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Структура стандартної відповіді сервера у разі виникнення помилки")
public record ApiErrorResponse(
        @Schema(description = "Таймштамп виникнення помилки", example = "2026-06-04T16:15:30")
        LocalDateTime timestamp,

        @Schema(description = "HTTP статус-код помилки", example = "400")
        int status,

        @Schema(description = "Офіційна назва HTTP помилки", example = "Bad Request")
        String error,

        @Schema(description = "Загальне повідомлення про причину збою або помилку валідації", example = "Validation failed for one or more fields")
        String message,

        @Schema(description = "Шлях (URI) ендпоінту, на якому стався збій", example = "/members")
        String path,

        @Schema(description = "Деталізована мапа помилок для конкретних полів (назва поля -> повідомлення)", example = "{\"email\": \"Email must match valid email format\", \"phone\": \"Phone number is required\"}")
        Map<String, String> errors
) {}
