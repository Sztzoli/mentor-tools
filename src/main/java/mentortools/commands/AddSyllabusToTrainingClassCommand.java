package mentortools.commands;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.validations.ExistSyllabus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddSyllabusToTrainingClassCommand {

    @ExistSyllabus
    @Schema(description = "id of Syllabus")
    private Long syllabusId;
}
