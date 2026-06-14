package ua.opnu.labwork2.service;

import org.springframework.stereotype.Service;
import ua.opnu.labwork2.model.Trainer;
import ua.opnu.labwork2.model.Workout;
import ua.opnu.labwork2.repository.TrainerRepository;
import ua.opnu.labwork2.repository.WorkoutRepository;

import java.util.List;

@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final WorkoutRepository workoutRepository;

    public TrainerService(TrainerRepository tr, WorkoutRepository wr) {
        this.trainerRepository = tr;
        this.workoutRepository = wr;
    }

    public Trainer create(Trainer trainer) { return trainerRepository.save(trainer); }

    public List<Trainer> getAll() { return trainerRepository.findAll(); }

    public Trainer getById(Long id) {
        return trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));
    }

    public Trainer update(Long id, Trainer updated) {
        Trainer trainer = getById(id);
        trainer.setFirstName(updated.getFirstName());
        trainer.setLastName(updated.getLastName());
        trainer.setSpecialization(updated.getSpecialization());
        trainer.setEmail(updated.getEmail());
        return trainerRepository.save(trainer);
    }

    public void delete(Long id) {
        trainerRepository.deleteById(id);
    }

    public List<Workout> getTrainerWorkouts(Long trainerId) {
        return workoutRepository.findByTrainerId(trainerId);
    }
}
