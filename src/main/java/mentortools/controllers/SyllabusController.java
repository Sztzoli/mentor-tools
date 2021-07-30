package mentortools.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mentortools.commands.AddModuleToSyllabusCommand;
import mentortools.commands.CreateSyllabusCommand;
import mentortools.commands.UpdateSyllabusCommand;
import mentortools.dtos.SyllabusDto;
import mentortools.dtos.SyllabusWithModulesDto;
import mentortools.services.SyllabusService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/syllabuses")
@RequiredArgsConstructor
@Tag(name = "Operations on syllabus")
public class SyllabusController {

    private final SyllabusService syllabusService;

    @GetMapping()
    @Operation(summary = "list all syllabus")
    public List<SyllabusDto> listSyllabuses() {
        return syllabusService.listSyllabuses();
    }

    @GetMapping("/{id}")
    @Operation(summary = "receive a syllabus",
            description = "receive a syllabus by id")
    @ApiResponse(responseCode = "404", description = "syllabus not found by id")
    public SyllabusDto findSyllabusById(
            @PathVariable Long id
    ) {
        return syllabusService.findSyllabusById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create new syllabus")
    @ApiResponse(responseCode = "201", description = "syllabus has been created")
    @ApiResponse(responseCode = "400", description = "validation exception when created syllabus")
    public SyllabusDto createSyllabus(
            @Valid @RequestBody CreateSyllabusCommand command
    ) {
        return syllabusService.createSyllabus(command);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update a syllabus",
            description = "update a syllabus by id")
    @ApiResponse(responseCode = "404", description = "syllabus not found by id")
    @ApiResponse(responseCode = "400", description = "validation exception when updated syllabus")
    public SyllabusDto updateSyllabus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSyllabusCommand command
    ) {
        return syllabusService.updateSyllabus(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete a syllabus",
            description = "delete a syllabus by id")
    @ApiResponse(responseCode = "204", description = "syllabus has been deleted")
    @ApiResponse(responseCode = "404", description = "syllabus not found by id")
    public void deleteById(
            @PathVariable Long id
    ) {
        syllabusService.deleteById(id);
    }

    @PostMapping("/{id}/module")
    @Operation(summary = "add module to syllabus")
    @ApiResponse(responseCode = "201", description = "module has been added to syllabus")
    @ApiResponse(responseCode = "400", description = "validation exception when module added to syllabus")
    @ApiResponse(responseCode = "404", description = "syllabus not found by id")
    @ResponseStatus(HttpStatus.CREATED)
    public SyllabusWithModulesDto addModuleToSyllabus(
            @PathVariable Long id,
            @Valid @RequestBody AddModuleToSyllabusCommand command
            ) {
        return syllabusService.addModuleToSyllabus(id, command);
    }

    @GetMapping("/{id}/module")
    @Operation(summary ="receive a syllabus with modules" )
    public SyllabusWithModulesDto findModulesOfSyllabus(
            @PathVariable Long id
    ) {
        return syllabusService.findModulesOfSyllabus(id);
    }
}
