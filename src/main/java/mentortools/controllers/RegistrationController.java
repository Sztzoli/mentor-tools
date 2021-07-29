package mentortools.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mentortools.commands.CreateRegistrationCommand;
import mentortools.commands.UpdateRegistrationCommand;
import mentortools.dtos.RegistrationDto;
import mentortools.dtos.TrainingClassesOfStudentDto;
import mentortools.services.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
@Tag(name = "Operations on registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/trainingclasses/{id}/registrations")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create registration for training class",
            description ="list registrations for training class by training class id")
    @ApiResponse(responseCode = "201", description = "registration has been created")
    @ApiResponse(responseCode = "404", description = "student not found by id")
    @ApiResponse(responseCode = "404", description = "training class not found by id")
    @ApiResponse(responseCode = "400", description = "validation exception when created registration")
    public RegistrationDto createRegistration(
            @PathVariable Long id,
            @Valid @RequestBody CreateRegistrationCommand command
    ) {
        return registrationService.createRegistration(id, command);
    }

    @GetMapping("/trainingclasses/{id}/registrations")
    @Operation(summary = "list registrations by training class",
            description ="list registrations by training class id")
    public List<RegistrationDto> listRegistrationByTrainingClass(
            @PathVariable Long id
    ) {
        return registrationService.listRegistrationByTrainingClass(id);
    }

    @PutMapping("/trainingclasses/{id}/registrations")
    @Operation(summary = "update a registration")
    @ApiResponse(responseCode = "404", description = "student not found by id")
    @ApiResponse(responseCode = "404", description = "training class not found by id")
    @ApiResponse(responseCode = "400", description = "validation exception when updated registration")
    public RegistrationDto updateRegistration(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRegistrationCommand command
    ) {
        return registrationService.updateRegistration(id, command);
    }

    @GetMapping("/students/{id}/registrations")
    @Operation(summary = "list training classes of student")
    public List<TrainingClassesOfStudentDto> listTrainingClassesOfStudent(
            @PathVariable Long id
    ) {
        return registrationService.listTrainingClassesOfStudent(id);
    }
}
