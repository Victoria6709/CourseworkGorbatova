package ua.opnu.labwork2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.labwork2.dto.RegistrationCreateRequest;
import ua.opnu.labwork2.dto.RegistrationResponse;
import ua.opnu.labwork2.dto.RegistrationStatusUpdateRequest;
import ua.opnu.labwork2.model.Registration;
import ua.opnu.labwork2.model.RegistrationStatus;
import ua.opnu.labwork2.service.RegistrationService;

import java.util.List;

@RestController
@RequestMapping("/registrations")
@Tag(name = "Записи на тренування", description = "Управління заявками клієнтів на заняття та зміна їхніх статусів (активний/скасований)")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    @Operation(summary = "Створити новий запис на тренування")
    public ResponseEntity<RegistrationResponse> create(@Valid @RequestBody RegistrationCreateRequest request) {
        Registration registration = new Registration();
        registration.setRegistrationDate(request.registrationDate());
        registration.setStatus(RegistrationStatus.valueOf(request.status()));

        ua.opnu.labwork2.model.Workout workout = new ua.opnu.labwork2.model.Workout();
        workout.setId(request.workoutId());
        registration.setWorkout(workout);

        ua.opnu.labwork2.model.Member member = new ua.opnu.labwork2.model.Member();
        member.setId(request.memberId());
        registration.setMember(member);

        Registration saved = registrationService.create(registration);
        return ResponseEntity.status(HttpStatus.CREATED).body(RegistrationResponse.fromEntity(saved));
    }

    @GetMapping
    @Operation(summary = "Отримати всі записи")
    public ResponseEntity<List<RegistrationResponse>> getAll() {
        List<RegistrationResponse> responseList = registrationService.getAll().stream()
                .map(RegistrationResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Знайти запис за ID")
    public ResponseEntity<RegistrationResponse> getById(@PathVariable Long id) {
        Registration registration = registrationService.getById(id);
        return ResponseEntity.ok(RegistrationResponse.fromEntity(registration));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Оновити статус запису")
    public ResponseEntity<RegistrationResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody RegistrationStatusUpdateRequest request
    ) {
        RegistrationStatus statusEnum = RegistrationStatus.valueOf(request.status());
        Registration updated = registrationService.updateStatus(id, statusEnum);
        return ResponseEntity.ok(RegistrationResponse.fromEntity(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Видалити запис про тренування")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        registrationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
