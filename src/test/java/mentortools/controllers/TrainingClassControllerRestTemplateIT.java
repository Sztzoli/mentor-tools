package mentortools.controllers;

import mentortools.commands.CreateTrainingClassCommand;
import mentortools.commands.UpdateTrainingClassCommand;
import mentortools.dtos.TrainingClassDto;
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

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from training_classes"})
class TrainingClassControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    TrainingClassDto trainingClass;

    @BeforeEach
    void init() {
        trainingClass = template.postForObject("/api/trainingclasses",
                new CreateTrainingClassCommand(
                        "name",
                        LocalDate.of(2021, 1, 1),
                        LocalDate.of(2021, 2, 1)),
                TrainingClassDto.class);
    }

    @Test
    void testSave() {
        assertEquals("name", trainingClass.getName());
    }

    @Test
    void testFindById() {
        Map<String, String> params = new HashMap<>();
        params.put("id", trainingClass.getId().toString());
        TrainingClassDto result = template
                .getForObject("/api/trainingclasses/{id}", TrainingClassDto.class, params);

        assertEquals(LocalDate.of(2021, 2, 1), result.getEndDate());
    }

    @Test
    void testListAll() {
        template.postForObject("/api/trainingclasses",
                new CreateTrainingClassCommand(
                        "name2",
                        LocalDate.of(2021, 2, 2),
                        LocalDate.of(2021, 4, 2)),
                TrainingClassDto.class);

        List<TrainingClassDto> result = template.exchange("/api/trainingclasses",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TrainingClassDto>>() {
                }).getBody();

        assertThat(result)
                .hasSize(2)
                .extracting(TrainingClassDto::getStartDate)
                .containsExactly(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 2, 2));
    }

    @Test
    void testUpdateTrainingClass() {
        Map<String, String> params = new HashMap<>();
        params.put("id", trainingClass.getId().toString());
        TrainingClassDto result = template.exchange("/api/trainingclasses/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateTrainingClassCommand("updateName", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 2, 2))),
                TrainingClassDto.class,
                params
        ).getBody();

        assertEquals("updateName", result.getName());
    }

    @Test
    void testDeleteById() {
        Map<String, String> params = new HashMap<>();
        params.put("id", trainingClass.getId().toString());
        template.delete("/api/trainingclasses/{id}", params);

        List<TrainingClassDto> result = template.exchange("/api/trainingclasses",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TrainingClassDto>>() {
                }).getBody();

        assertThat(result)
                .hasSize(0);
    }

    @Test
    void testTrainingClassNotFound() {
        Map<String, String> params = new HashMap<>();
        params.put("id", "-1");
        Problem problem = template.getForObject("/api/trainingclasses/{id}", Problem.class, params);

        assertEquals(Status.NOT_FOUND,problem.getStatus());
        assertEquals("Training class not found by id: -1",problem.getDetail());
    }

    @Test
    void inValidNameCreateEmpty() {
        Problem problem = template.postForObject("/api/trainingclasses",
                new CreateTrainingClassCommand(
                        "",
                        LocalDate.of(2021, 1, 1),
                        LocalDate.of(2021, 2, 1)),
                Problem.class);

        assertEquals(Status.BAD_REQUEST,problem.getStatus());
        assertEquals("Constraint Violation",problem.getTitle());

    }

    @Test
    void inValidNameUpdateNull() {
        Map<String, String> params = new HashMap<>();
        params.put("id", trainingClass.getId().toString());
        Problem problem = template.exchange("/api/trainingclasses/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateTrainingClassCommand(null, LocalDate.of(2020, 1, 1), LocalDate.of(2020, 2, 2))),
                Problem.class,
                params
        ).getBody();

        assertEquals(Status.BAD_REQUEST,problem.getStatus());
        assertEquals("Constraint Violation",problem.getTitle());
    }

    @Test
    void inValidStartDateCreateNull() {
        Problem problem = template.postForObject("/api/trainingclasses",
                new CreateTrainingClassCommand(
                        "name",
                        null,
                        LocalDate.of(2021, 2, 1)),
                Problem.class);

        assertEquals(Status.BAD_REQUEST,problem.getStatus());
        assertEquals("Constraint Violation",problem.getTitle());
    }

    @Test
    void inValidEndDateUpdateNull() {
        Map<String, String> params = new HashMap<>();
        params.put("id", trainingClass.getId().toString());
        Problem problem = template.exchange("/api/trainingclasses/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateTrainingClassCommand("updateName", LocalDate.of(2020, 1, 1), null)),
                Problem.class,
                params
        ).getBody();

        assertEquals(Status.BAD_REQUEST,problem.getStatus());
        assertEquals("Constraint Violation",problem.getTitle());
    }

    @Test
    void inValidCreateInternalTime() {
        Problem problem = template.postForObject("/api/trainingclasses",
                new CreateTrainingClassCommand(
                        "name",
                        LocalDate.of(2021, 2, 1),
                        LocalDate.of(2021, 1, 1)),
                Problem.class);

        assertEquals(Status.BAD_REQUEST,problem.getStatus());
        assertEquals("Constraint Violation",problem.getTitle());
    }

    @Test
    void inValidInternalTimeUpdate() {
        Map<String, String> params = new HashMap<>();
        params.put("id", trainingClass.getId().toString());
        Problem problem = template.exchange("/api/trainingclasses/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateTrainingClassCommand("updateName", LocalDate.of(2020, 2, 1), LocalDate.of(2020, 1, 1))),
                Problem.class,
                params
        ).getBody();

        assertEquals(Status.BAD_REQUEST,problem.getStatus());
        assertEquals("Constraint Violation",problem.getTitle());
    }


}