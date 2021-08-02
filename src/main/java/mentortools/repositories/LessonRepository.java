package mentortools.repositories;

import mentortools.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findAllByModule_Id(Long id);

    Lesson findByIdAndModule_Id(Long id, Long moduleId);
}
