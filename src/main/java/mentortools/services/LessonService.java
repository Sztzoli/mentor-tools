package mentortools.services;

import lombok.RequiredArgsConstructor;
import mentortools.commands.AddLessonToModuloCommand;
import mentortools.commands.UpdateLessonOfModuloCommand;
import mentortools.dtos.LessonDto;
import mentortools.exceptions.LessonNotFoundException;
import mentortools.exceptions.ModuleNotfoundException;
import mentortools.models.Lesson;
import mentortools.models.Module;
import mentortools.repositories.LessonRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final ModuleService moduleService;
    private final ModelMapper mapper;

    public List<LessonDto> listLessons(Long id) {
        Type targetListType = new TypeToken<List<LessonDto>>() {
        }.getType();
        List<Lesson> lessons= lessonRepository.findAllByModule_Id(id);
        return mapper.map(lessons, targetListType);
    }

    public LessonDto findLessonById(Long id, Long lessonId) {
        Lesson lesson = findById(lessonId);
        if (!lesson.getModule().getId().equals(id)){
            throw new ModuleNotfoundException(id);
        }
        return mapper.map(lesson, LessonDto.class);
    }

    public Lesson findById(Long id) {
        return lessonRepository.findById(id).orElseThrow(() -> new LessonNotFoundException(id));
    }

    @Transactional
    public LessonDto addLessonToModule(Long id, AddLessonToModuloCommand command) {
        Module module = moduleService.findById(id);
        Lesson lesson = lessonRepository.save(new Lesson(command.getTitle(), command.getUrl(),module));
        return mapper.map(lesson, LessonDto.class);
    }

    @Transactional
    public LessonDto updateLessonOfModule(Long id, Long lessonId, UpdateLessonOfModuloCommand command) {
        Lesson lesson = findById(lessonId);
        if (!lesson.getModule().getId().equals(id)){
            throw new ModuleNotfoundException(id);
        }
        lesson.setTitle(command.getTitle());
        lesson.setUrl(command.getUrl());
        return mapper.map(lesson, LessonDto.class);
    }

    @Transactional
    public void deleteLesson(Long id, Long lessonId) {
        Lesson lesson = findById(lessonId);
        if (!lesson.getModule().getId().equals(id)){
            throw new ModuleNotfoundException(id);
        }
       lessonRepository.delete(lesson);
    }
}
