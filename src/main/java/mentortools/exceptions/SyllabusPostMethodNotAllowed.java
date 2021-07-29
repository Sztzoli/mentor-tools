package mentortools.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class SyllabusPostMethodNotAllowed extends AbstractThrowableProblem {

    public SyllabusPostMethodNotAllowed() {
        super(
                URI.create("trainingclass/method-not-allowed"),
                "Method not allowed",
                Status.METHOD_NOT_ALLOWED,
                "post method not allowed"
        );
    }
}
