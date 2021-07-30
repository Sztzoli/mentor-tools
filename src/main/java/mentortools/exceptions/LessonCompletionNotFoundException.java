package mentortools.exceptions;

import mentortools.models.LessonCompletionKey;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class LessonCompletionNotFoundException extends AbstractThrowableProblem {

    public LessonCompletionNotFoundException(LessonCompletionKey id) {
        super(
                URI.create("lesson-complation/not-found"),
                "Not found",
                Status.NOT_FOUND,
                String.format("Lesson completion not found by lesson id: %d and Student id: %d",
                        id.getLessonId(), id.getStudentId())
        );
    }
}
