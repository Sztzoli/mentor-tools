package mentortools.services;

import lombok.RequiredArgsConstructor;
import mentortools.commands.CreateModuleCommand;
import mentortools.commands.UpdateModuleCommand;
import mentortools.dtos.ModuleDto;
import mentortools.dtos.StudentDto;
import mentortools.exceptions.ModuleNotfoundException;
import mentortools.models.Module;
import mentortools.repositories.ModuleRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final ModelMapper mapper;

    public List<ModuleDto> listModules() {
        Type targetListType = new TypeToken<List<ModuleDto>>() {
        }.getType();
        return mapper.map(moduleRepository.findAll(), targetListType);
    }

    public ModuleDto findModuleById(Long id) {
        return mapper.map(findById(id), ModuleDto.class);
    }

    public Module findById(Long id) {
        return moduleRepository.findById(id).orElseThrow(() -> new ModuleNotfoundException(id));
    }

    public ModuleDto createModule(CreateModuleCommand command) {
        Module module = moduleRepository.save(new Module(command.getTitle(), command.getUrl()));
        return mapper.map(module, ModuleDto.class);
    }

    @Transactional
    public ModuleDto updateModule(Long id, UpdateModuleCommand command) {
        Module module = findById(id);
        module.setTitle(command.getTitle());
        module.setUrl(command.getUrl());
        return mapper.map(module, ModuleDto.class);
    }

    public void deleteById(Long id) {
        moduleRepository.deleteById(id);
    }
}
