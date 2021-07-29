package mentortools.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class ModuleNotfoundException extends AbstractThrowableProblem {

    public ModuleNotfoundException(Long id) {
        super(
                URI.create("module/not-found"),
                "Not found",
                Status.NOT_FOUND,
                String.format("Module not found by id: %d", id)
        );
    }
}
