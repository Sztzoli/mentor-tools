package mentortools.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class TrainingClassNotFoundException extends AbstractThrowableProblem {

    public TrainingClassNotFoundException(Long id) {
        super(
                URI.create("trainingClass/not-found"),
                "Not found",
                Status.NOT_FOUND,
                String.format("Training class not found by id: %d", id)
        );
    }
}
