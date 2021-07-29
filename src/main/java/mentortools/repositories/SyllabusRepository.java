package mentortools.repositories;

import mentortools.models.Syllabus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SyllabusRepository extends JpaRepository<Syllabus, Long> {
}
