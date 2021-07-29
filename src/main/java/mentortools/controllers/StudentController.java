package mentortools.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mentortools.commands.CreateStudentCommand;
import mentortools.commands.UpdateStudentCommand;
import mentortools.dtos.StudentDto;
import mentortools.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/students")
@RequiredArgsConstructor
@Tag(name = "Operations on Student")
public class StudentController {

    private final StudentService studentService;

    @GetMapping()
    @Operation(summary = "list all students")
    public List<StudentDto> listStudents() {
        return studentService.listStudents();
    }

    @GetMapping("/{id}")
    @Operation(summary = "receive a student",
            description = "receive a student by id")
    @ApiResponse(responseCode = "404", description = "student not found by id")
    public StudentDto findStudentById(
            @PathVariable Long id
    ) {
        return studentService.findStudentById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create new student")
    @ApiResponse(responseCode = "201", description = "student has been created")
    @ApiResponse(responseCode = "400", description = "validation exception when created student")
    public StudentDto createStudent(
            @Valid @RequestBody CreateStudentCommand command
    ) {
        return studentService.createStudent(command);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update a student",
            description = "update a student by id")
    @ApiResponse(responseCode = "404", description = "student not found by id")
    @ApiResponse(responseCode = "400", description = "validation exception when updated student")
    public StudentDto updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStudentCommand command
    ) {
        return studentService.updateStudent(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete a student",
            description = "delete a student by id")
    @ApiResponse(responseCode = "204", description = "student has been deleted")
    @ApiResponse(responseCode = "404", description = "student not found by id")
    public void deleteById(
            @PathVariable Long id
    ) {
        studentService.deleteById(id);
    }
}
