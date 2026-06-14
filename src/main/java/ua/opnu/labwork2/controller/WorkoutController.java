package ua.opnu.labwork2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.labwork2.dto.MemberResponse;
import ua.opnu.labwork2.dto.RegistrationResponse;
import ua.opnu.labwork2.dto.WorkoutCreateRequest;
import ua.opnu.labwork2.dto.WorkoutResponse;
import ua.opnu.labwork2.model.Workout;
import ua.opnu.labwork2.service.RegistrationService;
import ua.opnu.labwork2.service.WorkoutService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/workouts")
@Tag(name = "Тренування", description = "Управління розкладом занять, закріпленням тренерів та типами тренувань")
public class WorkoutController {

    private final WorkoutService workoutService;
    private final RegistrationService registrationService;

    public WorkoutController(WorkoutService ws, RegistrationService rs) {
        this.workoutService = ws;
        this.registrationService = rs;
    }

    @PostMapping
    @Operation(summary = "Створити нове тренування")
    public ResponseEntity<WorkoutResponse> create(@Valid @RequestBody WorkoutCreateRequest request) {
        Workout workout = new Workout();
        workout.setTitle(request.title());
        workout.setDescription(request.description());
        workout.setDate(request.date());
        workout.setDurationMinutes(request.durationMinutes());

        if (request.trainerId() != null) {
            ua.opnu.labwork2.model.Trainer trainer = new ua.opnu.labwork2.model.Trainer();
            trainer.setId(request.trainerId());
            workout.setTrainer(trainer);
        }

        Workout saved = workoutService.create(workout);
        return ResponseEntity.status(HttpStatus.CREATED).body(WorkoutResponse.fromEntity(saved));
    }

    @GetMapping
    @Operation(summary = "Отримати всі тренування")
    public ResponseEntity<List<WorkoutResponse>> getAll() {
        List<WorkoutResponse> responseList = workoutService.getAll().stream()
                .map(WorkoutResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Отримати деталі тренування за ID")
    public ResponseEntity<WorkoutResponse> getById(@PathVariable Long id) {
        Workout workout = workoutService.getById(id);
        return ResponseEntity.ok(WorkoutResponse.fromEntity(workout));
    }

    @PostMapping("/{id}/members/{memberId}")
    @Operation(summary = "Додати клієнта на тренування напряму")
    public ResponseEntity<Void> addMember(@PathVariable Long id, @PathVariable Long memberId) {
        workoutService.addMemberToWorkout(id, memberId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/trainer/{trainerId}")
    @Operation(summary = "Пошук тренувань конкретного тренера")
    public ResponseEntity<List<WorkoutResponse>> getByTrainer(@PathVariable Long trainerId) {
        List<WorkoutResponse> responseList = workoutService.getByTrainerId(trainerId).stream()
                .map(WorkoutResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}/members")
    @Operation(summary = "Отримати список учасників заняття")
    public ResponseEntity<Set<MemberResponse>> getMembers(@PathVariable Long id) {
        Workout workout = workoutService.getById(id);
        Set<MemberResponse> responseSet = workout.getMembers().stream()
                .map(MemberResponse::fromEntity)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(responseSet);
    }

    @GetMapping("/{id}/registrations")
    @Operation(summary = "Переглянути записи на тренування")
    public ResponseEntity<List<RegistrationResponse>> getRegistrations(@PathVariable Long id) {
        List<RegistrationResponse> responseList = registrationService.getByWorkoutId(id).stream()
                .map(RegistrationResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @PostMapping("/{id}/types/{typeId}")
    @Operation(summary = "Призначити тип/напрямок для тренування")
    public ResponseEntity<Void> addType(@PathVariable Long id, @PathVariable Long typeId) {
        workoutService.addTypeToWorkout(id, typeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/types/{typeId}")
    @Operation(summary = "Видалити тип тренування")
    public ResponseEntity<Void> removeType(@PathVariable Long id, @PathVariable Long typeId) {
        workoutService.removeTypeFromWorkout(id, typeId);
        return ResponseEntity.noContent().build();
    }
}
