package mentortools.validations;

import mentortools.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistStudentValidator implements ConstraintValidator<ExistStudent, Long> {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        return studentRepository.findById(id).isPresent();
    }
}
