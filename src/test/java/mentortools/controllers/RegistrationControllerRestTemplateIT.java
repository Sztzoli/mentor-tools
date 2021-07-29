package mentortools.controllers;

import mentortools.commands.CreateRegistrationCommand;
import mentortools.commands.UpdateRegistrationCommand;
import mentortools.dtos.RegistrationDto;
import mentortools.dtos.TrainingClassesOfStudentDto;
import mentortools.models.RegistrationStatus;
import mentortools.models.Student;
import mentortools.models.TrainingClass;
import mentortools.repositories.StudentRepository;
import mentortools.repositories.TrainingClassRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {
        "alter table registrations drop foreign key if exists FK_TrainingClassRegistration ",
        "alter table registrations drop foreign key if exists FK_StudentRegistration",
        "delete from students",
        "delete from training_classes",
        "delete from registrations"})
class RegistrationControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TrainingClassRepository trainingClassRepository;


    @Test
    void registration() {
        Student student = studentRepository
                .save(new Student("name", "email", "githubUsername", "comment"));
        TrainingClass trainingClass = trainingClassRepository
                .save(new TrainingClass("class", LocalDate.of(2021, 1, 1)
                        , LocalDate.of(2021, 2, 1)));
        Map<String, String> params = new HashMap<>();
        params.put("id", trainingClass.getId().toString());
        RegistrationDto registration = template.postForObject("/api/trainingclasses/{id}/registrations",
                new CreateRegistrationCommand(student.getId()),
                RegistrationDto.class,
                params);

        assertEquals(RegistrationStatus.ACTIVE, registration.getStatus());
        assertEquals("name", registration.getName());
        assertEquals(student.getId(), registration.getId());
    }

    @Test
    void listRegistrationByTrainingClass() {
        Student student = studentRepository
                .save(new Student("name", "email", "githubUsername", "comment"));
        Student student2 = studentRepository
                .save(new Student("name2", "email2", "githubUsername2", "comment2"));
        TrainingClass trainingClass = trainingClassRepository
                .save(new TrainingClass("class", LocalDate.of(2021, 1, 1)
                        , LocalDate.of(2021, 2, 1)));
        Map<String, String> params = new HashMap<>();
        params.put("id", trainingClass.getId().toString());
        template.postForObject("/api/trainingclasses/{id}/registrations",
                new CreateRegistrationCommand(student.getId()),
                RegistrationDto.class,
                params);
        template.postForObject("/api/trainingclasses/{id}/registrations",
                new CreateRegistrationCommand(student2.getId()),
                RegistrationDto.class,
                params);

        List<RegistrationDto> result = template.exchange("/api/trainingclasses/{id}/registrations",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RegistrationDto>>() {
                },
                params).getBody();

        assertThat(result)
                .hasSize(2)
                .extracting(RegistrationDto::getName)
                .containsExactly("name", "name2");
    }

    @Test
    void updateRegistrationStatus() {
        Student student = studentRepository
                .save(new Student("name", "email", "githubUsername", "comment"));
        TrainingClass trainingClass = trainingClassRepository
                .save(new TrainingClass("class", LocalDate.of(2021, 1, 1)
                        , LocalDate.of(2021, 2, 1)));
        Map<String, String> params = new HashMap<>();
        params.put("id", trainingClass.getId().toString());

        template.postForObject("/api/trainingclasses/{id}/registrations",
                new CreateRegistrationCommand(student.getId()),
                RegistrationDto.class,
                params);

        RegistrationDto result = template.exchange("/api/trainingclasses/{id}/registrations",
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateRegistrationCommand(student.getId(), RegistrationStatus.EXIT_IN_PROGRESS)),
                RegistrationDto.class,
                params).getBody();

        assertEquals(RegistrationStatus.EXIT_IN_PROGRESS, result.getStatus());

    }

    @Test
    void listTrainingClassesOfStudent() {
        Student student = studentRepository
                .save(new Student("name", "email", "githubUsername", "comment"));
        TrainingClass trainingClass = trainingClassRepository
                .save(new TrainingClass("class", LocalDate.of(2021, 1, 1)
                        , LocalDate.of(2021, 2, 1)));
        TrainingClass trainingClass2 = trainingClassRepository
                .save(new TrainingClass("class2", LocalDate.of(2021, 2, 2)
                        , LocalDate.of(2021, 4, 4)));
        Map<String, String> params = new HashMap<>();
        params.put("id", trainingClass.getId().toString());
        template.postForObject("/api/trainingclasses/{id}/registrations",
                new CreateRegistrationCommand(student.getId()),
                RegistrationDto.class,
                params);
        params.put("id", trainingClass2.getId().toString());
        template.postForObject("/api/trainingclasses/{id}/registrations",
                new CreateRegistrationCommand(student.getId()),
                RegistrationDto.class,
                params);

        params.put("id", student.getId().toString());
        List<TrainingClassesOfStudentDto> result = template.exchange("/api/students/{id}/registrations",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TrainingClassesOfStudentDto>>() {
                }, params).getBody();

        assertThat(result)
                .hasSize(2)
                .extracting(TrainingClassesOfStudentDto::getTrainingClassName)
                .containsExactly("class", "class2");
    }

    @Test
    void invalidRegistration() {
        Student student = studentRepository
                .save(new Student("name", "email", "githubUsername", "comment"));
        TrainingClass trainingClass = trainingClassRepository
                .save(new TrainingClass("class", LocalDate.of(2021, 1, 1)
                        , LocalDate.of(2021, 2, 1)));
        Map<String, String> params = new HashMap<>();
        params.put("id", trainingClass.getId().toString());
        Problem problem = template.postForObject("/api/trainingclasses/{id}/registrations",
                new CreateRegistrationCommand(-1l),
                Problem.class,
                params);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }

    @Test
    void invalidUpdateRegistration() {
        Student student = studentRepository
                .save(new Student("name", "email", "githubUsername", "comment"));
        TrainingClass trainingClass = trainingClassRepository
                .save(new TrainingClass("class", LocalDate.of(2021, 1, 1)
                        , LocalDate.of(2021, 2, 1)));
        Map<String, String> params = new HashMap<>();
        params.put("id", trainingClass.getId().toString());

        template.postForObject("/api/trainingclasses/{id}/registrations",
                new CreateRegistrationCommand(student.getId()),
                RegistrationDto.class,
                params);

        Problem problem = template.exchange("/api/trainingclasses/{id}/registrations",
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateRegistrationCommand(-1l, RegistrationStatus.EXIT_IN_PROGRESS)),
                Problem.class,
                params).getBody();

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }

    @Test
    void invalidUpdateRegistrationStatus() {
        Student student = studentRepository
                .save(new Student("name", "email", "githubUsername", "comment"));
        TrainingClass trainingClass = trainingClassRepository
                .save(new TrainingClass("class", LocalDate.of(2021, 1, 1)
                        , LocalDate.of(2021, 2, 1)));
        Map<String, String> params = new HashMap<>();
        params.put("id", trainingClass.getId().toString());

        template.postForObject("/api/trainingclasses/{id}/registrations",
                new CreateRegistrationCommand(student.getId()),
                RegistrationDto.class,
                params);

        Problem problem = template.exchange("/api/trainingclasses/{id}/registrations",
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateRegistrationCommand(student.getId(), null)),
                Problem.class,
                params).getBody();

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }
}