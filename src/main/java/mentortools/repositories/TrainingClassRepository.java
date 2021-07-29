package mentortools.repositories;

import mentortools.models.TrainingClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingClassRepository extends JpaRepository<TrainingClass, Long> {
}
