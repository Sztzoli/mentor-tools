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
public class UpdateLessonOfModuloCommand {

    @NotBlank(message = "Lesson's title cannot be null or blank")
    @Length(max = 255, message = "Lesson's title max length is 255")
    @Schema(description = "title of Lesson", example = "title")
    private String title;

    @NotBlank(message = "Lesson's url cannot be null or blank")
    @Length(max = 255, message = "Lesson's url max length is 255")
    @Schema(description = "url of Lesson", example = "url")
    private String url;
}
