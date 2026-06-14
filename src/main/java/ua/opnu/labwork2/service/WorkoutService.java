package ua.opnu.labwork2.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.opnu.labwork2.exception.BadRequestException;
import ua.opnu.labwork2.exception.ConflictOperationException;
import ua.opnu.labwork2.exception.ResourceNotFoundException;
import ua.opnu.labwork2.model.Member;
import ua.opnu.labwork2.model.RegistrationStatus;
import ua.opnu.labwork2.model.WorkType;
import ua.opnu.labwork2.model.Workout;
import ua.opnu.labwork2.repository.RegistrationRepository;
import ua.opnu.labwork2.repository.WorkoutRepository;
import ua.opnu.labwork2.repository.WorkoutTypeRepository;

import java.util.List;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final WorkoutTypeRepository typeRepository;
    private final MemberService memberService;
    private final RegistrationRepository registrationRepository; // Додано для контролю записів

    public WorkoutService(WorkoutRepository wr, WorkoutTypeRepository tr, MemberService ms, RegistrationRepository rr) {
        this.workoutRepository = wr;
        this.typeRepository = tr;
        this.memberService = ms;
        this.registrationRepository = rr;
    }

    @Transactional
    public Workout create(Workout workout) {
        if (workout.getDurationMinutes() <= 0 || workout.getDurationMinutes() > 480) {
            throw new BadRequestException("Workout duration must be between 1 and 480 minutes.");
        }
        return workoutRepository.save(workout);
    }

    public List<Workout> getAll() { return workoutRepository.findAll(); }

    public Workout getById(Long id) {
        return workoutRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found with id: " + id));
    }

    public List<Workout> getByTrainerId(Long trainerId) {
        return workoutRepository.findByTrainerId(trainerId);
    }

    public List<Workout> getByMemberId(Long memberId) {
        return workoutRepository.findByMembers_Id(memberId);
    }

    @Transactional
    public void addMemberToWorkout(Long workoutId, Long memberId) {
        Workout workout = getById(workoutId);
        Member member = memberService.getById(memberId);
        workout.addMember(member);
        workoutRepository.save(workout);
    }

    @Transactional
    public void addTypeToWorkout(Long workoutId, Long typeId) {
        Workout workout = getById(workoutId);
        WorkType type = typeRepository.findById(typeId)
                .orElseThrow(() -> new ResourceNotFoundException("Type not found with id: " + typeId));
        workout.getWorkoutTypes().add(type);
        workoutRepository.save(workout);
    }

    @Transactional
    public void removeTypeFromWorkout(Long workoutId, Long typeId) {
        Workout workout = getById(workoutId);
        workout.getWorkoutTypes().removeIf(t -> t.getId().equals(typeId));
        workoutRepository.save(workout);
    }

    @Transactional
    public void delete(Long id) {
        if (!workoutRepository.existsById(id)) {
            throw new ResourceNotFoundException("Workout not found with id: " + id);
        }

        boolean hasActiveRegistrations = registrationRepository.existsActiveByWorkout(id, RegistrationStatus.ACTIVE);
        if (hasActiveRegistrations) {
            throw new ConflictOperationException("Cannot delete workout: It has active member registrations.");
        }

        workoutRepository.deleteById(id);
    }
}
