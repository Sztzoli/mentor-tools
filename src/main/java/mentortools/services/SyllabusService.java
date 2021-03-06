package mentortools.services;

import lombok.RequiredArgsConstructor;
import mentortools.commands.AddModuleToSyllabusCommand;
import mentortools.commands.CreateSyllabusCommand;
import mentortools.commands.UpdateSyllabusCommand;
import mentortools.dtos.SyllabusDto;
import mentortools.dtos.SyllabusWithModulesDto;
import mentortools.exceptions.SyllabusNotFoundException;
import mentortools.models.Module;
import mentortools.models.Syllabus;
import mentortools.repositories.SyllabusRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SyllabusService {

    private final SyllabusRepository syllabusRepository;
    private final ModuleService moduleService;
    private final ModelMapper mapper;

    public List<SyllabusDto> listSyllabuses() {
        Type targetListType = new TypeToken<List<SyllabusDto>>() {
        }.getType();
        return mapper.map(syllabusRepository.findAll(), targetListType);
    }

    public SyllabusDto findSyllabusById(Long id) {
        return mapper.map(findById(id), SyllabusDto.class);
    }

    public Syllabus findById(Long id) {
        return syllabusRepository.findById(id).orElseThrow(() -> new SyllabusNotFoundException(id));
    }

    public SyllabusDto createSyllabus(CreateSyllabusCommand command) {
        Syllabus syllabus = syllabusRepository.save(new Syllabus(command.getName()));
        return mapper.map(syllabus, SyllabusDto.class);
    }

    @Transactional
    public SyllabusDto updateSyllabus(Long id, UpdateSyllabusCommand command) {
        Syllabus syllabus = findById(id);
        syllabus.setName(command.getName());
        return mapper.map(syllabus, SyllabusDto.class);
    }

    public void deleteById(Long id) {
        syllabusRepository.deleteById(id);
    }

    @Transactional
    public SyllabusWithModulesDto addModuleToSyllabus(Long id, AddModuleToSyllabusCommand command) {
        Syllabus syllabus = findById(id);
        Module module = moduleService.findById(command.getModuloId());
        syllabus.addModule(module);
        return mapper.map(syllabus, SyllabusWithModulesDto.class);
    }

    public SyllabusWithModulesDto findModulesOfSyllabus(Long id) {
        Syllabus syllabus = findById(id);
        return mapper.map(syllabus, SyllabusWithModulesDto.class);
    }
}
