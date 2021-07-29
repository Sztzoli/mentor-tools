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
public class UpdateStudentCommand {

    @NotBlank(message = "Student's name cannot be null or blank")
    @Length(max = 255, message = "Student's name max length is 255")
    @Schema(description = "name of student", example = "John Doe")
    private String name;

    @NotBlank(message = "Student's email cannot be null or blank")
    @Length(max = 255, message = "Student's email max length is 255")
    @Schema(description = "email of student", example = "john@doe.com")
    private String email;

    @NotBlank(message = "Student's github username cannot be null or blank")
    @Length(max = 255, message = "Student's github username max length is 255")
    @Schema(description = "github username of student", example = "john")
    private String githubUsername;

    @Schema(description = "comment of student", example = "comment")
    private String comment;
}
