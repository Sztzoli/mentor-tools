package mentortools.validations;

import mentortools.repositories.SyllabusRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistSyllabusValidator implements ConstraintValidator<ExistSyllabus, Long> {

    @Autowired
    SyllabusRepository syllabusRepository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        return syllabusRepository.findById(id).isPresent();
    }
}
