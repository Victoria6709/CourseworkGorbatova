package ua.opnu.labwork2.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.opnu.labwork2.model.WorkType;
import ua.opnu.labwork2.repository.WorkoutTypeRepository;

import java.util.List;

@Service
public class WorkoutTypeService {

    private final WorkoutTypeRepository repository;

    public WorkoutTypeService(WorkoutTypeRepository repository) {
        this.repository = repository;
    }

    public WorkType create(WorkType type) {
        return repository.save(type);
    }

    public List<WorkType> getAll() {
        return repository.findAll();
    }

    public WorkType getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workout Type not found with id: " + id));
    }

    @Transactional
    public WorkType update(Long id, WorkType updatedDetails) {
        WorkType existingType = getById(id);
        existingType.setName(updatedDetails.getName());
        existingType.setDescription(updatedDetails.getDescription());
        return repository.save(existingType);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}