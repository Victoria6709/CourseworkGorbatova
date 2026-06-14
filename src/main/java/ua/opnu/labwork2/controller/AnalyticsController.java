package ua.opnu.labwork2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.labwork2.dto.TrainerResponse;
import ua.opnu.labwork2.model.RegistrationStatus;
import ua.opnu.labwork2.service.MemberService;
import ua.opnu.labwork2.service.RegistrationService;
import ua.opnu.labwork2.service.TrainerService;
import ua.opnu.labwork2.service.WorkoutService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/analytics")
@Tag(name = "Аналітичні запити", description = "Отримання статистичних даних та метрик щодо роботи фітнес-клубу")
public class AnalyticsController {

    private final WorkoutService workoutService;
    private final MemberService memberService;
    private final TrainerService trainerService;
    private final RegistrationService registrationService;

    public AnalyticsController(WorkoutService workoutService, MemberService memberService,
                               TrainerService trainerService, RegistrationService registrationService) {
        this.workoutService = workoutService;
        this.memberService = memberService;
        this.trainerService = trainerService;
        this.registrationService = registrationService;
    }

    @GetMapping("/workouts/count")
    @Operation(summary = "Загальна кількість тренувань")
    public ResponseEntity<Long> getWorkoutsCount() {
        long count = workoutService.getAll().size();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/members/count")
    @Operation(summary = "Кількість клієнтів")
    public ResponseEntity<Long> getMembersCount() {
        long count = memberService.getAll().size();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/workouts/active")
    @Operation(summary = "Кількість активних тренувань (записів на тренування зі статусом ACTIVE)")
    public ResponseEntity<Long> getActiveWorkoutsCount() {
        long count = registrationService.getAll().stream()
                .filter(r -> r.getStatus() == RegistrationStatus.ACTIVE)
                .count();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/workouts/by-type")
    @Operation(summary = "Кількість тренувань за типом")
    public ResponseEntity<Map<String, Long>> getWorkoutsByTypeCount() {
        Map<String, Long> stats = workoutService.getAll().stream()
                .filter(w -> w.getWorkoutTypes() != null)
                .flatMap(w -> w.getWorkoutTypes().stream())
                .filter(type -> type.getName() != null)
                .collect(Collectors.groupingBy(type -> type.getName(), Collectors.counting()));

        return ResponseEntity.ok(stats);
    }

    @GetMapping("/trainers/popular")
    @Operation(summary = "Найпопулярніші тренери (сортування за кількістю занять)")
    public ResponseEntity<List<TrainerResponse>> getPopularTrainers() {
        List<TrainerResponse> popular = trainerService.getAll().stream()
                .sorted((t1, t2) -> Integer.compare(
                        trainerService.getTrainerWorkouts(t2.getId()).size(),
                        trainerService.getTrainerWorkouts(t1.getId()).size()
                ))
                .map(TrainerResponse::fromEntity)
                .toList();

        return ResponseEntity.ok(popular);
    }
}
