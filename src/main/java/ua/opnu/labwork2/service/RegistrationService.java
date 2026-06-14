package ua.opnu.labwork2.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.opnu.labwork2.exception.BadRequestException;
import ua.opnu.labwork2.exception.ConflictOperationException;
import ua.opnu.labwork2.model.Member;
import ua.opnu.labwork2.model.Registration;
import ua.opnu.labwork2.model.RegistrationStatus;
import ua.opnu.labwork2.model.Workout;
import ua.opnu.labwork2.repository.RegistrationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistrationService {

    private final RegistrationRepository repository;
    private final WorkoutService workoutService;
    private final MemberService memberService;

    public RegistrationService(
            RegistrationRepository repository,
            WorkoutService workoutService,
            MemberService memberService
    ) {
        this.repository = repository;
        this.workoutService = workoutService;
        this.memberService = memberService;
    }

    @Transactional
    public Registration create(Registration registration) {
        if (registration.getRegistrationDate() != null &&
                registration.getRegistrationDate().toLocalDate().isAfter(LocalDateTime.now().toLocalDate())) {
            throw new BadRequestException("Registration date cannot be in the future.");
        }

        Workout workout = workoutService.getById(registration.getWorkout().getId());
        Member member = memberService.getById(registration.getMember().getId());

        if (workout.getDate().isBefore(LocalDateTime.now())) {
            throw new ConflictOperationException("Cannot register for a workout that has already taken place.");
        }

        boolean alreadyRegistered = repository.existsRegistration(
                member.getId(), workout.getId(), RegistrationStatus.ACTIVE
        );

        if (alreadyRegistered) {
            throw new ConflictOperationException("Member is already registered and active for this workout.");
        }

        registration.setWorkout(workout);
        registration.setMember(member);

        if (registration.getStatus() == null) {
            registration.setStatus(RegistrationStatus.ACTIVE);
        }

        Registration saved = repository.save(registration);
        workout.addMember(member);

        return saved;
    }

    public List<Registration> getAll() {
        return repository.findAll();
    }

    public Registration getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));
    }

    public Registration updateStatus(Long id, RegistrationStatus status) {
        Registration registration = getById(id);
        registration.setStatus(status);
        return repository.save(registration);
    }

    public List<Registration> getByWorkoutId(Long workoutId) {
        return repository.findByWorkoutId(workoutId);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Workout> getMemberWorkouts(Long memberId) {
        return repository.findByMemberId(memberId)
                .stream()
                .map(Registration::getWorkout)
                .distinct()
                .toList();
    }
}
