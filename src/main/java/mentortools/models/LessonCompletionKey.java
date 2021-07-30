package mentortools.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonCompletionKey implements Serializable {

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "lesson_id")
    private Long lessonId;
}
