package mentortools.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class LessonNotFoundException extends AbstractThrowableProblem {

    public LessonNotFoundException(Long id) {
        super(
                URI.create("lesson/not-found"),
                "Not found",
                Status.NOT_FOUND,
                String.format("Lesson not found by id: %d", id)
        );
    }
}
