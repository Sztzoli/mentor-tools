package mentortools.commands;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.validations.ExistModule;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddModuleToSyllabusCommand {

    @ExistModule
    @Schema(description = "id of modulo")
    private Long moduloId;
}
