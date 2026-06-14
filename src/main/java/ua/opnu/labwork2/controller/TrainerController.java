package ua.opnu.labwork2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.labwork2.dto.TrainerCreateRequest;
import ua.opnu.labwork2.dto.TrainerResponse;
import ua.opnu.labwork2.dto.TrainerUpdateRequest;
import ua.opnu.labwork2.dto.WorkoutResponse;
import ua.opnu.labwork2.model.Trainer;
import ua.opnu.labwork2.service.TrainerService;

import java.util.List;

@RestController
@RequestMapping("/trainers")
@Tag(name = "Тренери", description = "Управління тренерським складом клубу, їхніми спеціалізаціями та переглядом закріплених занять")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    @Operation(summary = "Додати нового тренера")
    public ResponseEntity<TrainerResponse> create(@Valid @RequestBody TrainerCreateRequest request) {
        Trainer trainer = new Trainer();
        trainer.setFirstName(request.firstName());
        trainer.setLastName(request.lastName());
        trainer.setSpecialization(request.specialization());
        trainer.setEmail(request.email());

        Trainer saved = trainerService.create(trainer);
        return ResponseEntity.status(HttpStatus.CREATED).body(TrainerResponse.fromEntity(saved));
    }

    @GetMapping
    @Operation(summary = "Отримати список всіх тренерів")
    public ResponseEntity<List<TrainerResponse>> getAll() {
        List<TrainerResponse> responseList = trainerService.getAll().stream()
                .map(TrainerResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Знайти тренера за ID")
    public ResponseEntity<TrainerResponse> getById(@PathVariable Long id) {
        Trainer trainer = trainerService.getById(id);
        return ResponseEntity.ok(TrainerResponse.fromEntity(trainer));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Оновити дані тренера")
    public ResponseEntity<TrainerResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody TrainerUpdateRequest request
    ) {
        Trainer details = new Trainer();
        details.setFirstName(request.firstName());
        details.setLastName(request.lastName());
        details.setSpecialization(request.specialization());
        details.setEmail(request.email());

        Trainer updated = trainerService.update(id, details);
        return ResponseEntity.ok(TrainerResponse.fromEntity(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Видалити тренера з системи")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        trainerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/workouts")
    @Operation(summary = "Отримати розклад занять конкретного тренера")
    public ResponseEntity<List<WorkoutResponse>> getTrainerWorkouts(@PathVariable Long id) {
        List<WorkoutResponse> workouts = trainerService.getTrainerWorkouts(id).stream()
                .map(WorkoutResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(workouts);
    }
}
