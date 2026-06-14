package ua.opnu.labwork2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ua.opnu.labwork2.model.Member;

public record MemberResponse(
        @Schema(description = "Унікальний ідентифікатор клієнта в базі даних", example = "1")
        Long id,

        @Schema(description = "Ім'я клієнта", example = "Олександр")
        String firstName,

        @Schema(description = "Прізвище клієнта", example = "Коваленко")
        String lastName,

        @Schema(description = "Електронна пошта клієнта", example = "olex.koval@gmail.com")
        String email,

        @Schema(description = "Номер телефону клієнта", example = "+380501234567")
        String phone
) {
    public static MemberResponse fromEntity(Member member) {
        if (member == null) return null;
        return new MemberResponse(
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                member.getEmail(),
                member.getPhone()
        );
    }
}