package ua.opnu.labwork2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.opnu.labwork2.model.Registration;
import ua.opnu.labwork2.model.RegistrationStatus;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    @Query("SELECT COUNT(r) > 0 FROM Registration r WHERE r.member.id = :memberId AND r.workout.id = :workoutId AND r.status = :status")
    boolean existsRegistration(
            @Param("memberId") Long memberId,
            @Param("workoutId") Long workoutId,
            @Param("status") RegistrationStatus status
    );

    @Query("SELECT COUNT(r) > 0 FROM Registration r WHERE r.workout.id = :workoutId AND r.status = :status")
    boolean existsActiveByWorkout(
            @Param("workoutId") Long workoutId,
            @Param("status") RegistrationStatus status
    );

    @Query("SELECT COUNT(r) > 0 FROM Registration r WHERE r.member.id = :memberId AND r.status = :status")
    boolean existsActiveByMember(
            @Param("memberId") Long memberId,
            @Param("status") RegistrationStatus status
    );

    @Query("SELECT r FROM Registration r WHERE r.workout.id = :workoutId")
    List<Registration> findByWorkoutId(@Param("workoutId") Long workoutId);

    @Query("SELECT r FROM Registration r WHERE r.member.id = :memberId")
    List<Registration> findByMemberId(@Param("memberId") Long memberId);
}
