package mentortools.validations;

import mentortools.repositories.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistModuleValidator implements ConstraintValidator<ExistModule, Long> {

    @Autowired
    ModuleRepository moduleRepository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        return moduleRepository.findById(id).isPresent();
    }
}
