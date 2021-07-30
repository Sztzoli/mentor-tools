package mentortools.controllers;

import mentortools.commands.*;
import mentortools.dtos.LessonCompletionDto;
import mentortools.dtos.LessonDto;
import mentortools.dtos.ModuleDto;
import mentortools.dtos.StudentDto;
import mentortools.models.Exercise;
import mentortools.models.ExerciseStatus;
import mentortools.models.Video;
import mentortools.models.VideoStatus;
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
@Sql(statements = {
        "alter table lessons_completion drop foreign key if exists FK_StudentLeComp",
        "delete from students",
        "delete from lessons_completion"
})
class LessonCompletionControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    static String URL = "/api/students/{id}/lessioncompletition";
    static Map<String, String> params = new HashMap<>();

    StudentDto student;
    LessonDto lesson;
    LessonCompletionDto lessonCompletion;

    @BeforeEach
    void setUp() {
        student = template.postForObject("/api/students",
                new CreateStudentCommand("name", "email", "githubusername", "comment"),
                StudentDto.class);
        ModuleDto module = template.postForObject("/api/modules",
                new CreateModuleCommand("moduleTitle", "moduleUrl"),
                ModuleDto.class);
        params.put("moduleId", module.getId().toString());
        lesson = template.postForObject("/api/modules/{moduleId}/lessons",
                new AddLessonToModuloCommand("lessonTitle", "lessonUrl"),
                LessonDto.class,
                params);
        params.put("id", student.getId().toString());
        System.out.println(lesson);
        lessonCompletion = template.postForObject(
                URL,
                new CreateLessonCompletionCommand(lesson.getId(),
                        new Video(VideoStatus.COMPLETED, LocalDate.of(2021, 1, 1)),
                        new Exercise(ExerciseStatus.NOT_COMPLETED, null, null)),
                LessonCompletionDto.class,
                params);
    }

    @Test
    void save() {
        assertEquals(VideoStatus.COMPLETED, lessonCompletion.getVideo().getVideoStatus());
        assertEquals(ExerciseStatus.NOT_COMPLETED, lessonCompletion.getExercise().getExerciseStatus());
        assertEquals(lesson, lessonCompletion.getLesson());
    }

    @Test
    void findById() {
        params.put("lessonId", lesson.getId().toString());
        LessonCompletionDto result = template
                .getForObject(URL + "/{lessonId}", LessonCompletionDto.class, params);

        assertEquals(VideoStatus.COMPLETED, result.getVideo().getVideoStatus());
        assertEquals(ExerciseStatus.NOT_COMPLETED, result.getExercise().getExerciseStatus());
        assertEquals(lesson, result.getLesson());

    }

    @Test
    void testListAll() {
        LessonDto lesson2 = template.postForObject("/api/modules/{moduleId}/lessons",
                new AddLessonToModuloCommand("lessonTitle2", "lessonUrl"),
                LessonDto.class,
                params);
        LessonCompletionDto lessonCompletion2 = template.postForObject(
                URL,
                new CreateLessonCompletionCommand(lesson2.getId(),
                        new Video(VideoStatus.COMPLETED, LocalDate.of(2021, 1, 1)),
                        new Exercise(ExerciseStatus.COMPLETED, LocalDate.of(2021, 1, 1), "url")),
                LessonCompletionDto.class,
                params);

        List<LessonCompletionDto> result = template.exchange(URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LessonCompletionDto>>() {
                }, params).getBody();

        assertThat(result)
                .hasSize(2)
                .extracting(LessonCompletionDto::getLesson)
                .extracting(LessonDto::getTitle)
                .containsExactly("lessonTitle", "lessonTitle2");
    }

    @Test
    void testUpdate() {
        LessonCompletionDto result = template.exchange(URL,
                HttpMethod.PUT,
                new HttpEntity<>(
                        new UpdateLessonCompletionCommand(lesson.getId(),
                                new Video(VideoStatus.COMPLETED, LocalDate.of(2021, 1, 1)),
                                new Exercise(ExerciseStatus.COMPLETED, LocalDate.of(2021, 1, 1), "url"))),
                LessonCompletionDto.class,
                params
        ).getBody();

        assertEquals(VideoStatus.COMPLETED, result.getVideo().getVideoStatus());
        assertEquals(ExerciseStatus.COMPLETED, result.getExercise().getExerciseStatus());
        assertEquals(lesson, result.getLesson());
    }

    @Test
    void testDeleteById() {
        params.put("lessonId", lesson.getId().toString());
        template.delete(URL + "/{lessonId}", params);

        List<LessonCompletionDto> result = template.exchange(URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LessonCompletionDto>>() {
                }, params).getBody();

        assertThat(result)
                .hasSize(0);
    }

    @Test
    void invalidPost() {
        Problem problem = template.postForObject(
                URL,
                new CreateLessonCompletionCommand(-1L,
                        new Video(VideoStatus.COMPLETED, LocalDate.of(2021, 1, 1)),
                        new Exercise(ExerciseStatus.NOT_COMPLETED, null, null)),
                Problem.class,
                params);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }

}