package mentortools.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class SyllabusNotFoundException extends AbstractThrowableProblem {
    public SyllabusNotFoundException(Long id) {
        super(URI.create("syllabus/not-found"),
                "Not found",
                Status.NOT_FOUND,
                String.format("Syllabus not found by id: %d", id));
    }
}
