package mentortools.commands;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.validations.ExistStudent;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRegistrationCommand {

    @ExistStudent
    @Schema(description = "id of Student", example = "1")
    private Long studentId;

}
