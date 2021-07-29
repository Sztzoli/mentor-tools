package mentortools.controllers;

import mentortools.commands.CreateStudentCommand;
import mentortools.commands.CreateSyllabusCommand;
import mentortools.commands.UpdateStudentCommand;
import mentortools.commands.UpdateSyllabusCommand;
import mentortools.dtos.StudentDto;
import mentortools.dtos.SyllabusDto;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {
        "alter table training_classes drop foreign key if exists FK_SyllabusTrainingClass",
        "delete from syllabuses"})
class SyllabusControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    SyllabusDto syllabus;

    static String URL = "/api/syllabuses";

    @BeforeEach
    void init() {
        syllabus = template.postForObject(URL,
                new CreateSyllabusCommand("name"),
                SyllabusDto.class);
    }

    @Test
    void testSave() {
        assertEquals("name", syllabus.getName());
    }

    @Test
    void testFindById() {
        Map<String, String> params = new HashMap<>();
        params.put("id", syllabus.getId().toString());
        SyllabusDto result = template
                .getForObject(URL + "/{id}", SyllabusDto.class, params);

        assertEquals("name", result.getName());
    }

    @Test
    void testListAll() {
        template.postForObject(URL,
                new CreateSyllabusCommand("name2"),
                SyllabusDto.class);

        List<SyllabusDto> result = template.exchange(URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<SyllabusDto>>() {
                }).getBody();

        assertThat(result)
                .hasSize(2)
                .extracting(SyllabusDto::getName)
                .containsExactly("name", "name2");
    }

    @Test
    void testUpdate() {
        Map<String, String> params = new HashMap<>();
        params.put("id", syllabus.getId().toString());
        SyllabusDto result = template.exchange(URL + "/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(
                        new UpdateSyllabusCommand("nameUpdated")),
                SyllabusDto.class,
                params
        ).getBody();

        assertEquals("nameUpdated", result.getName());
    }

    @Test
    void testDeleteById() {
        Map<String, String> params = new HashMap<>();
        params.put("id", syllabus.getId().toString());
        template.delete(URL + "/{id}", params);

        List<SyllabusDto> result = template.exchange(URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<SyllabusDto>>() {
                }).getBody();

        assertThat(result)
                .hasSize(0);
    }

    @Test
    void testSyllabusNotFound() {
        Map<String, String> params = new HashMap<>();
        params.put("id", "-1");
        Problem problem = template.getForObject(URL + "/{id}", Problem.class, params);

        assertEquals(Status.NOT_FOUND, problem.getStatus());
        assertEquals("Syllabus not found by id: -1", problem.getDetail());
    }

    @Test
    void invalidNameCreateEmpty() {
        Problem problem = template.postForObject(URL,
                new CreateSyllabusCommand(""),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }

    @Test
    void invalidNameCreateLength() {
        Problem problem = template.postForObject(URL,
                new CreateSyllabusCommand("a".repeat(256)),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }


}