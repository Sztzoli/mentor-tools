package mentortools.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name = "lessons_completion")
public class LessonCompletion {

    @EmbeddedId
    private LessonCompletionKey id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Student student;

    @ManyToOne
    @MapsId("lessonId")
    @JoinColumn(name = "lesson_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Lesson lesson;


    private Video video;
    private Exercise exercise;

    public LessonCompletion(LessonCompletionKey id, Student student, Lesson lesson, Video video, Exercise exercise) {
        this.id = id;
        this.student = student;
        this.lesson = lesson;
        this.video = video;
        this.exercise = exercise;
    }


}
