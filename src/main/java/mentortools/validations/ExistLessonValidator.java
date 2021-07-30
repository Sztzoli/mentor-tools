package mentortools.validations;

import mentortools.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistLessonValidator implements ConstraintValidator<ExistLesson, Long> {

    @Autowired
    LessonRepository lessonRepository;

    public boolean isValid(Long id, ConstraintValidatorContext context) {
        return lessonRepository.findById(id).isPresent();
    }
}
