package mentortools.commands;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.models.RegistrationStatus;
import mentortools.validations.ExistStatus;
import mentortools.validations.ExistStudent;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRegistrationCommand {

    @ExistStudent
    @Schema(description = "id of Student", example = "1")
    private Long studentId;

    @ExistStatus
    @Schema(description = "status of registration", example = "EXIT_IN_PROGRESS")
    private RegistrationStatus status;
}
