package mentortools.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mentortools.commands.CreateLessonCompletionCommand;
import mentortools.commands.UpdateLessonCompletionCommand;
import mentortools.dtos.LessonCompletionDto;
import mentortools.services.LessonCompletionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/students/{id}/lessioncompletition")
@RequiredArgsConstructor
@Tag(name = "Operation on lessons completion")
public class LessonCompletionController {

    private final LessonCompletionService lessonCompletionService;

    @GetMapping
    @Operation(summary = "list all lessons completion")
    @ApiResponse(responseCode = "404", description = "student not found by id")
    public List<LessonCompletionDto> listLessonsCompletion(
            @PathVariable Long id
    ) {
        return lessonCompletionService.listLessonsCompletion(id);
    }

    @GetMapping("/{lessonId}")
    @Operation(summary = "receive a lessons completion")
    @ApiResponse(responseCode = "404", description = "lessons completion not found by id")
    public LessonCompletionDto findLessonCompletionById(
            @PathVariable Long id,
            @PathVariable Long lessonId
    ) {
        return lessonCompletionService.findLessonCompletionById(id, lessonId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create a lessons completion")
    @ApiResponse(responseCode = "201", description = "lessons completion has been created")
    @ApiResponse(responseCode = "400", description = "validation exception when created lessons completion")
    public LessonCompletionDto createLessonCompletion(
            @PathVariable Long id,
            @Valid @RequestBody CreateLessonCompletionCommand command
    ) {
        return lessonCompletionService.createLessonCompletion(id, command);
    }

    @PutMapping()
    @Operation(summary = "update a lessons completion")
    @ApiResponse(responseCode = "404", description = "lessons completion not found by id")
    @ApiResponse(responseCode = "400", description = "validation exception when updated lessons completion")
    public LessonCompletionDto updateLessonCompletion(
            @PathVariable Long id,
            @Valid @RequestBody UpdateLessonCompletionCommand command
    ) {
        return lessonCompletionService.updateLessonCompletion(id, command);
    }

    @DeleteMapping("/{lessonId}")
    @Operation(summary = "delete a lessons completion")
    @ApiResponse(responseCode = "404", description = "lessons completion not found by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @PathVariable Long id,
            @PathVariable Long lessonId
    ) {
        lessonCompletionService.deleteById(id,lessonId);
    }
}
