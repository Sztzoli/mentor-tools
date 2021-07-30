package mentortools.services;

import lombok.RequiredArgsConstructor;
import mentortools.commands.CreateLessonCompletionCommand;
import mentortools.commands.UpdateLessonCompletionCommand;
import mentortools.dtos.LessonCompletionDto;
import mentortools.exceptions.LessonCompletionNotFoundException;
import mentortools.models.LessonCompletion;
import mentortools.models.LessonCompletionKey;
import mentortools.repositories.LessonCompletionRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonCompletionService {

    private final LessonCompletionRepository lessonCompletionRepository;
    private final StudentService studentService;
    private final LessonService lessonService;
    private final ModelMapper mapper;

    public List<LessonCompletionDto> listLessonsCompletion(Long id) {
        Type targetListType = new TypeToken<List<LessonCompletionDto>>() {
        }.getType();
        List<LessonCompletion> lessonCompletions = lessonCompletionRepository.findAllByStudent_Id(id);
        return mapper.map(lessonCompletions, targetListType);
    }

    public LessonCompletionDto findLessonCompletionById(Long id, Long lessonId) {
        LessonCompletion lessonCompletion = findById(new LessonCompletionKey(id, lessonId));
        return mapper.map(lessonCompletion, LessonCompletionDto.class);
    }

    public LessonCompletion findById(LessonCompletionKey id) {
        return lessonCompletionRepository.findById(id).orElseThrow(() -> new LessonCompletionNotFoundException(id));
    }

    public LessonCompletionDto createLessonCompletion(Long id, CreateLessonCompletionCommand command) {
        LessonCompletion lessonCompletion = lessonCompletionRepository.save(new LessonCompletion(
                        new LessonCompletionKey(id, command.getLessonId()),
                        studentService.findById(id),
                        lessonService.findById(command.getLessonId()),
                        command.getVideo(),
                        command.getExercise()
                )
        );
        return mapper.map(lessonCompletion, LessonCompletionDto.class);
    }

    @Transactional
    public LessonCompletionDto updateLessonCompletion(Long id, UpdateLessonCompletionCommand command) {
        LessonCompletion lessonCompletion = findById(new LessonCompletionKey(id, command.getLessonId()));
        lessonCompletion.setVideo(command.getVideo());
        lessonCompletion.setExercise(command.getExercise());
        return mapper.map(lessonCompletion, LessonCompletionDto.class);
    }

    public void deleteById(Long id, Long lessonId) {
        lessonCompletionRepository.deleteById(new LessonCompletionKey(id, lessonId));
    }
}
