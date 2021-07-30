package mentortools.controllers;

import mentortools.commands.CreateModuleCommand;
import mentortools.commands.UpdateModuleCommand;
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
        "alter table syllabuses_modules drop foreign key if exists FK_ModuleSyl_Mod",
        "delete from modules"})
class ModuleControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    ModuleDto module;

    static String URL = "/api/modules";

    @BeforeEach
    void init() {
        module = template.postForObject(URL,
                new CreateModuleCommand("title","url"),
                ModuleDto.class);
    }

    @Test
    void testSave() {
        assertEquals("title", module.getTitle());
    }

    @Test
    void testFindById() {
        Map<String, String> params = new HashMap<>();
        params.put("id", module.getId().toString());
        ModuleDto result = template
                .getForObject(URL + "/{id}", ModuleDto.class, params);

        assertEquals("url", result.getUrl());
    }

    @Test
    void testListAll() {
        template.postForObject(URL,
                new CreateModuleCommand("title2","url2"),
                ModuleDto.class);

        List<ModuleDto> result = template.exchange(URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ModuleDto>>() {
                }).getBody();

        assertThat(result)
                .hasSize(2)
                .extracting(ModuleDto::getTitle)
                .containsExactly("title", "title2");
    }

    @Test
    void testUpdate() {
        Map<String, String> params = new HashMap<>();
        params.put("id", module.getId().toString());
        ModuleDto result = template.exchange(URL + "/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(
                       new UpdateModuleCommand("titleUpdate","urlUpdate")),
                ModuleDto.class,
                params
        ).getBody();

        assertEquals("titleUpdate", result.getTitle());
    }

    @Test
    void testDeleteById() {
        Map<String, String> params = new HashMap<>();
        params.put("id", module.getId().toString());
        template.delete(URL + "/{id}", params);

        List<ModuleDto> result = template.exchange(URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ModuleDto>>() {
                }).getBody();

        assertThat(result)
                .hasSize(0);
    }

    @Test
    void testModuleNotFound() {
        Map<String, String> params = new HashMap<>();
        params.put("id", "-1");
        Problem problem = template.getForObject(URL + "/{id}", Problem.class, params);

        assertEquals(Status.NOT_FOUND, problem.getStatus());
        assertEquals("Module not found by id: -1", problem.getDetail());
    }

    @Test
    void invalidTitleCreateEmpty() {
        Problem problem = template.postForObject(URL,
                new CreateModuleCommand("","url"),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }

    @Test
    void invalidUrlCreateLength() {
        Problem problem = template.postForObject(URL,
                new CreateModuleCommand("title","u".repeat(256)),
                Problem.class);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }


}