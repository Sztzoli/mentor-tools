package mentortools.validations;

import mentortools.commands.CreateTrainingClassCommand;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InternalTimeCreateValidator implements ConstraintValidator<InternalTime, CreateTrainingClassCommand> {

    @Override
    public boolean isValid(CreateTrainingClassCommand command, ConstraintValidatorContext constraintValidatorContext) {
        return new InternalTimeIsValid(command.getStartDate(), command.getEndDate()).isValid();
    }
}
