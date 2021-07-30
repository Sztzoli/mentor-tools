package mentortools.controllers;

import mentortools.commands.AddModuleToSyllabusCommand;
import mentortools.commands.CreateModuleCommand;
import mentortools.commands.CreateSyllabusCommand;
import mentortools.dtos.ModuleDto;
import mentortools.dtos.SyllabusDto;
import mentortools.dtos.SyllabusWithModulesDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {
        "alter table lessons drop foreign key if exists FK_ModulesLessons",
        "alter table syllabuses_modules drop foreign key if exists FK_SyllabusSyl_Mod",
        "delete from modules",
        "delete from syllabuses"})
public class SyllabusModuleRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    SyllabusDto syllabus;
    ModuleDto module;

    static String URL = "/api/syllabuses/{id}/module";

    @BeforeEach
    void init() {
        syllabus = template.postForObject("/api/syllabuses",
                new CreateSyllabusCommand("name"),
                SyllabusDto.class);
        module = template.postForObject("/api/modules",
                new CreateModuleCommand("title", "url"),
                ModuleDto.class);
    }

    @Test
    void postModuleToSyllabus() {
        Map<String, String> params = new HashMap<>();
        params.put("id", syllabus.getId().toString());
        SyllabusWithModulesDto result = template.postForObject(URL,
                new AddModuleToSyllabusCommand(module.getId()),
                SyllabusWithModulesDto.class,
                params);
        assertEquals("title" , new ArrayList<>(result.getModules()).get(0).getTitle());
    }

    @Test
    void getModulesOfSyllabus() {
        Map<String, String> params = new HashMap<>();
        params.put("id", syllabus.getId().toString());
        template.postForObject(URL,
                new AddModuleToSyllabusCommand(module.getId()),
                SyllabusWithModulesDto.class,
                params);
        SyllabusWithModulesDto result = template.getForObject(URL, SyllabusWithModulesDto.class,params);
        assertEquals("url" , new ArrayList<>(result.getModules()).get(0).getUrl());
    }

    @Test
    void invalidPostModuleToSyllabus() {
        Map<String, String> params = new HashMap<>();
        params.put("id", syllabus.getId().toString());
        Problem problem = template.postForObject(URL,
                new AddModuleToSyllabusCommand(-1L),
                Problem.class,
                params);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }
}
