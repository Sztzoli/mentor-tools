package mentortools.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mentortools.commands.CreateModuleCommand;
import mentortools.commands.UpdateModuleCommand;
import mentortools.dtos.ModuleDto;
import mentortools.services.ModuleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/modules")
@RequiredArgsConstructor
@Tag(name = "Operations on modules")
public class ModuleController {

    private final ModuleService moduleService;

    @GetMapping()
    @Operation(summary = "list all modules")
    public List<ModuleDto> listModules() {
        return moduleService.listModules();
    }

    @GetMapping("/{id}")
    @Operation(summary = "receive a module",
            description = "receive a module by id")
    @ApiResponse(responseCode = "404", description = "module not found by id")
    public ModuleDto findModuleById(
            @PathVariable Long id
    ) {
        return moduleService.findModuleById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create new module")
    @ApiResponse(responseCode = "201", description = "module has been created")
    @ApiResponse(responseCode = "400", description = "validation exception when created module")
    public ModuleDto createModule(
            @Valid @RequestBody CreateModuleCommand command
    ) {
        return moduleService.createModule(command);
    }

    @PutMapping("/{id}")
    @Operation(summary = "update a module",
            description = "update a module by id")
    @ApiResponse(responseCode = "404", description = "module not found by id")
    @ApiResponse(responseCode = "400", description = "validation exception when updated module")
    public ModuleDto updateModule(
            @PathVariable Long id,
            @Valid @RequestBody UpdateModuleCommand command
    ) {
        return moduleService.updateModule(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete a module",
            description = "delete a module by id")
    @ApiResponse(responseCode = "204", description = "module has been deleted")
    @ApiResponse(responseCode = "404", description = "module not found by id")
    public void deleteById(
            @PathVariable Long id
    ) {
        moduleService.deleteById(id);
    }
}
