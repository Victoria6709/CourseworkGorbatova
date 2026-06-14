package ua.opnu.labwork2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.labwork2.dto.MemberResponse;
import ua.opnu.labwork2.dto.TrainerResponse;
import ua.opnu.labwork2.dto.WorkoutResponse;
import ua.opnu.labwork2.service.MemberService;
import ua.opnu.labwork2.service.TrainerService;
import ua.opnu.labwork2.service.WorkoutService;

import java.util.List;

@RestController
@RequestMapping("/search")
@Tag(name = "Повнотекстовий пошук", description = "Глобальний пошук сутностей системи за текстовими ключовими словами")
public class SearchController {

    private final WorkoutService workoutService;
    private final TrainerService trainerService;
    private final MemberService memberService;

    public SearchController(WorkoutService workoutService, TrainerService trainerService, MemberService memberService) {
        this.workoutService = workoutService;
        this.trainerService = trainerService;
        this.memberService = memberService;
    }

    @GetMapping("/workouts")
    @Operation(summary = "Пошук тренувань за назвою або описом")
    public ResponseEntity<List<WorkoutResponse>> searchWorkouts(@RequestParam String query) {
        List<WorkoutResponse> results = workoutService.getAll().stream()
                .filter(w -> w.getTitle().toLowerCase().contains(query.toLowerCase())
                        || w.getDescription().toLowerCase().contains(query.toLowerCase()))
                .map(WorkoutResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/trainers")
    @Operation(summary = "Пошук тренерів")
    public ResponseEntity<List<TrainerResponse>> searchTrainers(@RequestParam String query) {
        List<TrainerResponse> results = trainerService.getAll().stream()
                .filter(t -> t.getFirstName().toLowerCase().contains(query.toLowerCase())
                        || t.getLastName().toLowerCase().contains(query.toLowerCase())
                        || t.getSpecialization().toLowerCase().contains(query.toLowerCase()))
                .map(TrainerResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(results);
    }

    @GetMapping("/members")
    @Operation(summary = "Пошук клієнтів")
    public ResponseEntity<List<MemberResponse>> searchMembers(@RequestParam String query) {
        List<MemberResponse> results = memberService.getAll().stream()
                .filter(m -> m.getFirstName().toLowerCase().contains(query.toLowerCase())
                        || m.getLastName().toLowerCase().contains(query.toLowerCase())
                        || m.getEmail().toLowerCase().contains(query.toLowerCase()))
                .map(MemberResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(results);
    }
}
