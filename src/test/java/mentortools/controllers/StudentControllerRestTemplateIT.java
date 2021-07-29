package mentortools.controllers;

import mentortools.commands.CreateStudentCommand;
import mentortools.commands.UpdateStudentCommand;
import mentortools.dtos.StudentDto;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from students"})
class StudentControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    StudentDto student;

    @BeforeEach
    void init() {
        student = template.postForObject("/api/students",
                new CreateStudentCommand("name", "email", "githubusername", "comment"),
                StudentDto.class);
    }

    @Test
    void testSave() {
        assertEquals("name", student.getName());
    }

    @Test
    void testFindById() {
        Map<String, String> params = new HashMap<>();
        params.put("id", student.getId().toString());
        StudentDto result = template
                .getForObject("/api/students/{id}", StudentDto.class, params);

        assertEquals("email", result.getEmail());
    }

    @Test
    void testListAll() {
        template.postForObject("/api/students",
                new CreateStudentCommand("name2", "email2", "githubusername2", "comment2"),
                StudentDto.class);

        List<StudentDto> result = template.exchange("/api/students",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<StudentDto>>() {
                }).getBody();

        assertThat(result)
                .hasSize(2)
                .extracting(StudentDto::getGithubUsername)
                .containsExactly("githubusername", "githubusername2");
    }

    @Test
    void testUpdate() {
        Map<String, String> params = new HashMap<>();
        params.put("id", student.getId().toString());
        StudentDto result = template.exchange("/api/students/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(
                        new UpdateStudentCommand("nameUpdate", "emailUpdate",
                                "githubusernameUpdate", "commentUpdate")),
                StudentDto.class,
                params
        ).getBody();

        assertEquals("commentUpdate", result.getComment());
    }

    @Test
    void testDeleteById() {
        Map<String, String> params = new HashMap<>();
        params.put("id", student.getId().toString());
        template.delete("/api/students/{id}", params);

        List<StudentDto> result = template.exchange("/api/students",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<StudentDto>>() {
                }).getBody();

        assertThat(result)
                .hasSize(0);
    }

    @Test
    void testStudentNotFound() {
        Map<String, String> params = new HashMap<>();
        params.put("id", "-1");
        Problem problem = template.getForObject("/api/students/{id}", Problem.class, params);

        assertEquals(Status.NOT_FOUND, problem.getStatus());
        assertEquals("Student not found by id: -1", problem.getDetail());
    }

    @Test
    void invalidNameCreateEmpty() {
        Problem problem = template.postForObject("/api/students",
                new CreateStudentCommand("", "email", "githubusername", "comment"),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }

    @Test
    void invalidNameCreateLength() {
        Problem problem = template.postForObject("/api/students",

                new CreateStudentCommand("a".repeat(256)
                        , "email", "githubusername", "comment"),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }

    @Test
    void invalidEmailCreateEmpty() {
        Problem problem = template.postForObject("/api/students",
                new CreateStudentCommand("name", "", "githubusername", "comment"),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }

    @Test
    void invalidEmailCreateLength() {
        Problem problem = template.postForObject("/api/students",

                new CreateStudentCommand("name"
                        , "a".repeat(256), "githubusername", "comment"),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }

    @Test
    void invalidGitHubCreateEmpty() {
        Problem problem = template.postForObject("/api/students",
                new CreateStudentCommand("name", "email", "", "comment"),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }

    @Test
    void invalidGitHubCreateLength() {
        Problem problem = template.postForObject("/api/students",

                new CreateStudentCommand("name"
                        , "email", "a".repeat(256), "comment"),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }

}