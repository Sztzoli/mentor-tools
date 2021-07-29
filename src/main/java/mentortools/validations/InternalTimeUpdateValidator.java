package mentortools.validations;

import mentortools.commands.UpdateTrainingClassCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InternalTimeUpdateValidator implements ConstraintValidator<InternalTime, UpdateTrainingClassCommand> {

    @Override
    public boolean isValid(UpdateTrainingClassCommand command, ConstraintValidatorContext constraintValidatorContext) {
        return new InternalTimeIsValid(command.getStartDate(), command.getEndDate()).isValid();
    }
}
