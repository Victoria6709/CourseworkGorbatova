package ua.opnu.labwork2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.labwork2.dto.WorkoutTypeCreateRequest;
import ua.opnu.labwork2.dto.WorkoutTypeResponse;
import ua.opnu.labwork2.dto.WorkoutTypeUpdateRequest;
import ua.opnu.labwork2.model.WorkType;
import ua.opnu.labwork2.service.WorkoutTypeService;

import java.util.List;

@RestController
@RequestMapping("/workout-types")
@Tag(name = "Типи тренувань", description = "Управління напрямками фітнесу та категоріями занять (наприклад: Кроссфіт, Стретчинг, Силові)")
public class WorkoutTypeController {

    private final WorkoutTypeService workoutTypeService;

    public WorkoutTypeController(WorkoutTypeService workoutTypeService) {
        this.workoutTypeService = workoutTypeService;
    }

    @PostMapping
    @Operation(summary = "Створити новий напрямок тренувань")
    public ResponseEntity<WorkoutTypeResponse> create(@Valid @RequestBody WorkoutTypeCreateRequest request) {
        WorkType type = new WorkType();
        type.setName(request.name());
        type.setDescription(request.description());

        WorkType saved = workoutTypeService.create(type);
        return ResponseEntity.status(HttpStatus.CREATED).body(WorkoutTypeResponse.fromEntity(saved));
    }

    @GetMapping
    @Operation(summary = "Отримати всі категорії занять")
    public ResponseEntity<List<WorkoutTypeResponse>> getAll() {
        List<WorkoutTypeResponse> responseList = workoutTypeService.getAll().stream()
                .map(WorkoutTypeResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Отримати деталі категорії за ID")
    public ResponseEntity<WorkoutTypeResponse> getById(@PathVariable Long id) {
        WorkType type = workoutTypeService.getById(id);
        return ResponseEntity.ok(WorkoutTypeResponse.fromEntity(type));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Оновити назву або опис типу тренування")
    public ResponseEntity<WorkoutTypeResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody WorkoutTypeUpdateRequest request
    ) {
        WorkType details = new WorkType();
        details.setName(request.name());
        details.setDescription(request.description());

        WorkType updated = workoutTypeService.update(id, details);
        return ResponseEntity.ok(WorkoutTypeResponse.fromEntity(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Видалити категорію тренування")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        workoutTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
