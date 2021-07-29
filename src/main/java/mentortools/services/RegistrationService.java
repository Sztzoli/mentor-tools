package mentortools.services;

import lombok.RequiredArgsConstructor;
import mentortools.commands.CreateRegistrationCommand;
import mentortools.commands.UpdateRegistrationCommand;
import mentortools.dtos.RegistrationDto;
import mentortools.dtos.TrainingClassesOfStudentDto;
import mentortools.exceptions.RegistrationNotFoundException;
import mentortools.models.Registration;
import mentortools.models.RegistrationKey;
import mentortools.models.Student;
import mentortools.models.TrainingClass;
import mentortools.repositories.RegistrationRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final StudentService studentService;
    private final TrainingClassService trainingClassService;

    public RegistrationDto createRegistration(Long id, CreateRegistrationCommand command) {
        TrainingClass trainingClass = trainingClassService.findById(id);
        Student student = studentService.findById(command.getStudentId());
        RegistrationKey registrationKey = new RegistrationKey(student.getId(), trainingClass.getId());
        Registration registration = new Registration(registrationKey, student, trainingClass);
        registrationRepository.save(registration);
        return registrationRepository.getRegistrationDto(registrationKey);
    }

    public List<RegistrationDto> listRegistrationByTrainingClass(Long id) {
        return registrationRepository.listRegistrationByTrainingClassId(id);
    }

    @Transactional
    public RegistrationDto updateRegistration(Long id, UpdateRegistrationCommand command) {
        TrainingClass trainingClass = trainingClassService.findById(id);
        Student student = studentService.findById(command.getStudentId());
        RegistrationKey registrationKey = new RegistrationKey(student.getId(), trainingClass.getId());
        Registration registration = findRegistrationById(registrationKey);
        registration.setStatus(command.getStatus());
        return registrationRepository.getRegistrationDto(registrationKey);
    }

    public Registration findRegistrationById(RegistrationKey id) {
        return registrationRepository.findById(id)
                .orElseThrow(() -> new RegistrationNotFoundException(id.getTrainingClassId(), id.getStudentId()));
    }

    public List<TrainingClassesOfStudentDto> listTrainingClassesOfStudent(Long id) {
        return registrationRepository.listTrainingClassesOfStudent(id);
    }
}
