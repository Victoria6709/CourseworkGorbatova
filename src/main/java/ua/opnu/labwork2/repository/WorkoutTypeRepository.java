package ua.opnu.labwork2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.opnu.labwork2.model.WorkType;

@Repository
public interface WorkoutTypeRepository extends JpaRepository<WorkType, Long> {

}
