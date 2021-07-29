package mentortools.services;

import lombok.RequiredArgsConstructor;
import mentortools.commands.AddSyllabusToTrainingClassCommand;
import mentortools.commands.CreateTrainingClassCommand;
import mentortools.commands.UpdateSyllabusOfTrainingClassCommand;
import mentortools.commands.UpdateTrainingClassCommand;
import mentortools.dtos.TrainingClassDto;
import mentortools.exceptions.TrainingClassNotFoundException;
import mentortools.models.Syllabus;
import mentortools.models.TrainingClass;
import mentortools.repositories.TrainingClassRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingClassService {

    private final TrainingClassRepository trainingClassRepository;
    private final SyllabusService syllabusService;
    private final ModelMapper mapper;

    public List<TrainingClassDto> listTrainingClasses() {
        Type targetListType = new TypeToken<List<TrainingClassDto>>() {
        }.getType();
        return mapper.map(trainingClassRepository.findAll(), targetListType);
    }

    public TrainingClassDto findTrainingClassById(Long id) {
        return mapper.map(findById(id), TrainingClassDto.class);
    }

    public TrainingClassDto createTrainingClass(CreateTrainingClassCommand command) {
        TrainingClass trainingClass = trainingClassRepository
                .save(new TrainingClass(command.getName(), command.getStartDate(), command.getEndDate()));
        return mapper.map(trainingClass, TrainingClassDto.class);
    }

    @Transactional
    public TrainingClassDto updateTrainingClass(Long id, UpdateTrainingClassCommand command) {
        TrainingClass trainingClass = findById(id);
        trainingClass.setName(command.getName());
        trainingClass.setStartDate(command.getStartDate());
        trainingClass.setEndDate(command.getEndDate());
        return mapper.map(trainingClass, TrainingClassDto.class);
    }

    public void deleteById(Long id) {
        trainingClassRepository.deleteById(id);
    }

    public TrainingClass findById(Long id) {
        return trainingClassRepository.findById(id).orElseThrow(() -> new TrainingClassNotFoundException(id));
    }

    @Transactional
    public TrainingClassDto addSyllabusToTrainingClass(Long id, AddSyllabusToTrainingClassCommand command) {
        TrainingClass trainingClass = findById(id);
        Syllabus syllabus = syllabusService.findById(command.getSyllabusId());
        trainingClass.addSyllabus(syllabus);
        return mapper.map(trainingClass, TrainingClassDto.class);
    }

    @Transactional
    public TrainingClassDto updateSyllabusOfTrainingClass(Long id, UpdateSyllabusOfTrainingClassCommand command) {
        TrainingClass trainingClass = findById(id);
        Syllabus syllabus = syllabusService.findById(command.getSyllabusId());
        trainingClass.setSyllabus(syllabus);
        return mapper.map(trainingClass, TrainingClassDto.class);
    }
}
