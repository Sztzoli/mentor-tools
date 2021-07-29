package mentortools.controllers;

import mentortools.commands.AddSyllabusToTrainingClassCommand;
import mentortools.commands.CreateSyllabusCommand;
import mentortools.commands.CreateTrainingClassCommand;
import mentortools.commands.UpdateSyllabusOfTrainingClassCommand;
import mentortools.dtos.SyllabusDto;
import mentortools.dtos.TrainingClassDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from training_classes", "delete from syllabuses"})
public class TrainingClassSyllabusRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    TrainingClassDto trainingClass;
    SyllabusDto syllabus;

    static String URL = "/api/trainingclasses/{id}/syllabus";

    @BeforeEach
    void init() {
        trainingClass = template.postForObject("/api/trainingclasses",
                new CreateTrainingClassCommand(
                        "nameTrainingClass",
                        LocalDate.of(2021, 1, 1),
                        LocalDate.of(2021, 2, 1)),
                TrainingClassDto.class);
        syllabus = template.postForObject("/api/syllabuses",
                new CreateSyllabusCommand("nameSyllabus"),
                SyllabusDto.class);

    }

    @Test
    void postSyllabusToTrainingClass() {
        Map<String, String> params = new HashMap<>();
        params.put("id", trainingClass.getId().toString());
        TrainingClassDto result = template.postForObject(URL,
                new AddSyllabusToTrainingClassCommand(syllabus.getId()),
                TrainingClassDto.class,
                params);
        assertEquals("nameSyllabus", result.getSyllabus().getName());
        assertEquals("nameTrainingClass", result.getName());

    }

    @Test
    void notAllowedPostSyllabusToTrainingClass() {
        Map<String, String> params = new HashMap<>();
        params.put("id", trainingClass.getId().toString());
        template.postForObject(URL,
                new AddSyllabusToTrainingClassCommand(syllabus.getId()),
                TrainingClassDto.class,
                params);
        SyllabusDto syllabus2 = template.postForObject("/api/syllabuses",
                new CreateSyllabusCommand("nameSyllabus2"),
                SyllabusDto.class);
        Problem problem = template.postForObject(URL,
                new AddSyllabusToTrainingClassCommand(syllabus2.getId()),
                Problem.class,
                params);

        assertEquals(Status.METHOD_NOT_ALLOWED, problem.getStatus());
        assertEquals("post method not allowed", problem.getDetail());
    }

    @Test
    void updateSyllabusOfTrainingClass() {
        Map<String, String> params = new HashMap<>();
        params.put("id", trainingClass.getId().toString());
        template.postForObject(URL,
                new AddSyllabusToTrainingClassCommand(syllabus.getId()),
                TrainingClassDto.class,
                params);

        SyllabusDto syllabus2 = template.postForObject("/api/syllabuses",
                new CreateSyllabusCommand("nameSyllabus2"),
                SyllabusDto.class);

        TrainingClassDto result = template.exchange(URL,
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateSyllabusOfTrainingClassCommand(syllabus2.getId())),
                TrainingClassDto.class,
                params).getBody();

        System.out.println(result);
        assertEquals("nameSyllabus2", result.getSyllabus().getName());
        assertEquals("nameTrainingClass", result.getName());
    }

    @Test
    void inValidPost() {
        TrainingClassDto trainingClass2 = template.postForObject("/api/trainingclasses",
                new CreateTrainingClassCommand(
                        "nameTrainingClass2",
                        LocalDate.of(2021, 1, 1),
                        LocalDate.of(2021, 2, 1)),
                TrainingClassDto.class);

        Map<String, String> params = new HashMap<>();
        params.put("id", trainingClass2.getId().toString());
        Problem problem = template.postForObject(URL,
                new AddSyllabusToTrainingClassCommand(-1L),
                Problem.class,
                params);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }

    @Test
    void inValidPut() {
        Map<String, String> params = new HashMap<>();
        params.put("id", trainingClass.getId().toString());

        Problem problem = template.exchange(URL,
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateSyllabusOfTrainingClassCommand(-1L)),
                Problem.class,
                params).getBody();

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }


}
