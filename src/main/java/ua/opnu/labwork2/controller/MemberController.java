package ua.opnu.labwork2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.labwork2.dto.MemberCreateRequest;
import ua.opnu.labwork2.dto.MemberResponse;
import ua.opnu.labwork2.dto.MemberUpdateRequest;
import ua.opnu.labwork2.dto.WorkoutResponse;
import ua.opnu.labwork2.model.Member;
import ua.opnu.labwork2.service.MemberService;
import ua.opnu.labwork2.service.RegistrationService;

import java.util.List;

@RestController
@RequestMapping("/members")
@Tag(name = "Клієнти клубу", description = "Операції для реєстрації відвідувачів, оновлення їхніх даних, видалення або перегляду персонального розкладу")
public class MemberController {

    private final MemberService memberService;
    private final RegistrationService registrationService;

    public MemberController(MemberService memberService, RegistrationService registrationService) {
        this.memberService = memberService;
        this.registrationService = registrationService;
    }

    @PostMapping
    @Operation(summary = "Зареєструвати нового клієнта спортклубу")
    public ResponseEntity<MemberResponse> create(@Valid @RequestBody MemberCreateRequest request) {
        Member member = new Member();
        member.setFirstName(request.firstName());
        member.setLastName(request.lastName());
        member.setEmail(request.email());
        member.setPhone(request.phone());

        Member saved = memberService.create(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved != null ? MemberResponse.fromEntity(saved) : null);
    }

    @GetMapping
    @Operation(summary = "Отримати повний список клієнтів")
    public ResponseEntity<List<MemberResponse>> getAll() {
        List<MemberResponse> responseList = memberService.getAll().stream()
                .map(MemberResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Знайти клієнта за ID")
    public ResponseEntity<MemberResponse> getById(@PathVariable Long id) {
        Member member = memberService.getById(id);
        return ResponseEntity.ok(MemberResponse.fromEntity(member));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Оновити дані клієнта")
    public ResponseEntity<MemberResponse> update(@PathVariable Long id, @Valid @RequestBody MemberUpdateRequest request) {
        Member details = new Member();
        details.setFirstName(request.firstName());
        details.setLastName(request.lastName());
        details.setEmail(request.email());
        details.setPhone(request.phone());

        Member updated = memberService.update(id, details);
        return ResponseEntity.ok(MemberResponse.fromEntity(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Видалити клієнта з бази")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/workouts")
    @Operation(summary = "Отримати розклад занять відвідувача")
    public ResponseEntity<List<WorkoutResponse>> getMemberWorkouts(@PathVariable Long id) {
        List<WorkoutResponse> workouts = registrationService.getMemberWorkouts(id).stream()
                .map(WorkoutResponse::fromEntity)
                .toList();
        return ResponseEntity.ok(workouts);
    }
}
