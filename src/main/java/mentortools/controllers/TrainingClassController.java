package mentortools.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mentortools.commands.AddSyllabusToTrainingClassCommand;
import mentortools.commands.CreateTrainingClassCommand;
import mentortools.commands.UpdateSyllabusOfTrainingClassCommand;
import mentortools.commands.UpdateTrainingClassCommand;
import mentortools.dtos.TrainingClassDto;
import mentortools.services.TrainingClassService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/trainingclasses")
@RequiredArgsConstructor
@Tag(name = "Operations on TrainingClass")
public class TrainingClassController {

    private final TrainingClassService trainingClassService;

    @GetMapping()
    @Operation(summary = "list all trainingClasses")
    public List<TrainingClassDto> listTrainingClasses() {
        return trainingClassService.listTrainingClasses();
    }

    @GetMapping("/{id}")
    @Operation(summary = "receive a trainingClass",
            description = "receive a trainingClass by id")
    @ApiResponse(responseCode = "404", description = "trainingClass not found by id")
    public TrainingClassDto findTrainingClassById(
            @PathVariable Long id
    ) {
        return trainingClassService.findTrainingClassById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create new trainingClass")
    @ApiResponse(responseCode = "201", description = "trainingClass has been created")
    @ApiResponse(responseCode = "400", description = "validation exception when created trainingClass")
    public TrainingClassDto createTrainingClass(
            @Valid @RequestBody CreateTrainingClassCommand command
            ) {
        return trainingClassService.createTrainingClass(command);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update a trainingClass",
            description = "update a trainingClass by id")
    @ApiResponse(responseCode = "404", description = "trainingClass not found by id")
    @ApiResponse(responseCode = "400", description = "validation exception when updated trainingClass")
    public TrainingClassDto updateTrainingClass(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTrainingClassCommand command
            ) {
        return trainingClassService.updateTrainingClass(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete a trainingClass",
            description = "delete a trainingClass by id")
    @ApiResponse(responseCode = "204", description = "trainingClass has been deleted")
    @ApiResponse(responseCode = "404", description = "trainingClass not found by id")
    public void deleteById(
            @PathVariable Long id
    ) {
        trainingClassService.deleteById(id);
    }

    @PostMapping("/{id}/syllabus")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "add syllabus to training class")
    @ApiResponse(responseCode = "201", description = "syllabus has been added to training class")
    @ApiResponse(responseCode = "404", description = "trainingClass not found by id")
    @ApiResponse(responseCode = "400", description = "validation exception when added syllabus to training class")
    @ApiResponse(responseCode = "405", description = "post method allow only once")
    public TrainingClassDto addSyllabusToTrainingClass(
            @PathVariable Long id,
            @Valid @RequestBody AddSyllabusToTrainingClassCommand command
            ) {
        return trainingClassService.addSyllabusToTrainingClass(id, command);
    }

    @PutMapping("/{id}/syllabus")
    @Operation(summary = "update syllabus to training class")
    @ApiResponse(responseCode = "404", description = "trainingClass not found by id")
    @ApiResponse(responseCode = "400", description = "validation exception when updated syllabus for training class")
    public TrainingClassDto updateSyllabusOfTrainingClass(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSyllabusOfTrainingClassCommand command
    ) {
        return trainingClassService.updateSyllabusOfTrainingClass(id, command);
    }

}
