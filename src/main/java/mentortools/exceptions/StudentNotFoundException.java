package mentortools.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class StudentNotFoundException extends AbstractThrowableProblem {

    public StudentNotFoundException(Long id) {
        super(
                URI.create("student/not-found"),
                "Not found",
                Status.NOT_FOUND,
                String.format("Student not found by id: %d", id)
        );
    }

}
