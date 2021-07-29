package mentortools.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class RegistrationNotFoundException extends AbstractThrowableProblem {
    public RegistrationNotFoundException(Long trainingClassId, Long studentId) {
        super(
                URI.create("regitration/not-found"),
                "Not found",
                Status.NOT_FOUND,
                String.format("registration not found by TrainingClass id: %d and Student id: %d",
                        trainingClassId, studentId)
        );
    }
}
