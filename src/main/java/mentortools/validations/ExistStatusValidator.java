package mentortools.validations;

import mentortools.models.RegistrationStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistStatusValidator implements ConstraintValidator<ExistStatus, RegistrationStatus> {


    public boolean isValid(RegistrationStatus status, ConstraintValidatorContext context) {

       return RegistrationStatus.hasStatus(status);
    }
}
