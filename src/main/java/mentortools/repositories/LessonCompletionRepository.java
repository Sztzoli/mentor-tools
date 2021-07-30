package mentortools.repositories;

import mentortools.models.LessonCompletion;
import mentortools.models.LessonCompletionKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonCompletionRepository extends JpaRepository<LessonCompletion, LessonCompletionKey> {

    List<LessonCompletion> findAllByStudent_Id(Long id);
}
