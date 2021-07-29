package mentortools.services;

import lombok.RequiredArgsConstructor;
import mentortools.commands.CreateStudentCommand;
import mentortools.commands.UpdateStudentCommand;
import mentortools.dtos.StudentDto;
import mentortools.exceptions.StudentNotFoundException;
import mentortools.models.Student;
import mentortools.repositories.StudentRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

    public List<StudentDto> listStudents() {
        Type targetListType = new TypeToken<List<StudentDto>>() {
        }.getType();
        return mapper.map(studentRepository.findAll(), targetListType);
    }

    public StudentDto findStudentById(Long id) {
        return mapper.map(findById(id), StudentDto.class);
    }

    public StudentDto createStudent(CreateStudentCommand command) {
        Student student = studentRepository
                .save(new Student(command.getName(), command.getEmail(), command.getGithubUsername(),
                        command.getComment()));
        return mapper.map(student, StudentDto.class);
    }

    @Transactional
    public StudentDto updateStudent(Long id, UpdateStudentCommand command) {
        Student student = findById(id);
        student.setName(command.getName());
        student.setEmail(command.getEmail());
        student.setGithubUsername(command.getGithubUsername());
        student.setComment(command.getComment());
        return mapper.map(student, StudentDto.class);
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    public Student findById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
    }
}
