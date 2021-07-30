package mentortools.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mentortools.commands.AddLessonToModuloCommand;
import mentortools.commands.UpdateLessonOfModuloCommand;
import mentortools.dtos.LessonDto;
import mentortools.services.LessonService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/modules/{id}/lessons")
@RequiredArgsConstructor
@Tag(name = "Operations on lesson")
public class LessonController {

    private final LessonService lessonService;

    @GetMapping()
    @Operation(summary = "list all lesson of Module")
    @ApiResponse(responseCode = "404", description = "module not found by id")
    public List<LessonDto> listLessons(
            @PathVariable Long id
    ) {
        return lessonService.listLessons(id);
    }

    @GetMapping("/{lessonId}")
    @Operation(summary = "receive a lesson of Module",
            description = "receive a lesson by id of Module")
    @ApiResponse(responseCode = "404", description = "module not found by id")
    @ApiResponse(responseCode = "404", description = "lesson not found by id")
    public LessonDto findLessonById(
            @PathVariable Long id,
            @PathVariable Long lessonId
    ) {
        return lessonService.findLessonById(id, lessonId);
    }

    @PostMapping()
    @Operation(summary = "add new lesson to module")
    @ApiResponse(responseCode = "201", description = "lesson has been created")
    @ApiResponse(responseCode = "400", description = "validation exception when added lesson to module")
    @ApiResponse(responseCode = "404", description = "module not found by id")
    public LessonDto createLesson(
            @PathVariable Long id,
            @Valid @RequestBody AddLessonToModuloCommand command
    ) {
        return lessonService.addLessonToModule(id, command);
    }

    @PutMapping("/{lessonId}")
    @Operation(summary = "update a lesson of module")
    @ApiResponse(responseCode = "400", description = "validation exception when added lesson to module")
    @ApiResponse(responseCode = "404", description = "module not found by id")
    @ApiResponse(responseCode = "404", description = "lesson not found by id")
    public LessonDto updateLesson(
            @PathVariable Long id,
            @PathVariable Long lessonId,
            @Valid @RequestBody UpdateLessonOfModuloCommand command
    ) {
        return lessonService.updateLessonOfModule(id, lessonId, command);
    }

    @DeleteMapping("/{lessonId}")
    @Operation(summary = "delete a lesson of module")
    @ApiResponse(responseCode = "404", description = "module not found by id")
    @ApiResponse(responseCode = "404", description = "lesson not found by id")
    public void deleteLesson(
            @PathVariable Long id,
            @PathVariable Long lessonId
    ) {
        lessonService.deleteLesson(id, lessonId);
    }
}
