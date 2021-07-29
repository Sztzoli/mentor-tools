package mentortools.commands;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSyllabusCommand {

    @NotBlank(message = "Syllabus's name cannot be null or blank")
    @Length(max = 255, message = "Syllabus's name max length is 255")
    @Schema(description = "name of Syllabus", example = "Syllabus")
    private String name;
}
