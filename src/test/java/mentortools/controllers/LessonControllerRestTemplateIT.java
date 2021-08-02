package mentortools.controllers;

import mentortools.commands.AddLessonToModuloCommand;
import mentortools.commands.CreateModuleCommand;
import mentortools.commands.UpdateModuleCommand;
import mentortools.dtos.LessonDto;
import mentortools.dtos.ModuleDto;
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
@Sql(statements = {
        "delete from lessons"
})
class LessonControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    ModuleDto module;
    LessonDto lesson;

    static String URL = "/api/modules/{id}/lessons";
    static Map<String, String> params = new HashMap<>();

    @BeforeEach
    void init() {
        module = template.postForObject("/api/modules",
                new CreateModuleCommand("title", "url"),
                ModuleDto.class);
        params.put("id", module.getId().toString());
        lesson = template.postForObject(URL,
                new AddLessonToModuloCommand("lessonTitle", "lessonUrl"),
                LessonDto.class,
                params);
        params.put("lessonId", lesson.getId().toString());
    }

    @Test
    void testSave() {

        assertEquals("lessonTitle", lesson.getTitle());
    }

    @Test
    void testFindById() {
        LessonDto result = template
                .getForObject(URL + "/{lessonId}", LessonDto.class, params);

        System.out.println(result);
        assertEquals("lessonUrl", result.getUrl());
    }

    @Test
    void testListAll() {
        template.postForObject(URL,
                new AddLessonToModuloCommand("lessonTitle2", "lessonUrl2"),
                LessonDto.class,
                params);

        List<LessonDto> result = template.exchange(URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LessonDto>>() {
                }, params).getBody();

        assertThat(result)
                .hasSize(2)
                .extracting(LessonDto::getTitle)
                .containsExactly("lessonTitle", "lessonTitle2");
    }

    @Test
    void testUpdate() {
        ModuleDto result = template.exchange(URL + "/{lessonId}",
                HttpMethod.PUT,
                new HttpEntity<>(
                        new UpdateModuleCommand("titleUpdate", "urlUpdate")),
                ModuleDto.class,
                params
        ).getBody();

        assertEquals("titleUpdate", result.getTitle());
    }

    @Test
    void testDeleteById() {
        template.delete(URL + "/{lessonId}", params);

        List<ModuleDto> result = template.exchange(URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ModuleDto>>() {
                }, params).getBody();

        assertThat(result)
                .hasSize(0);
    }


    @Test
    void invalidTitleCreateEmpty() {
        Problem problem = template.postForObject(URL,
                new CreateModuleCommand("","url"),
                Problem.class,params);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }

    @Test
    void invalidUrlCreateLength() {
        Problem problem = template.postForObject(URL,
                new CreateModuleCommand("title","u".repeat(256)),
                Problem.class,params);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }

}