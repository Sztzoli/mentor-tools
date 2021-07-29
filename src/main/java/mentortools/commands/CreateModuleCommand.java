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
public class CreateModuleCommand {

    @NotBlank(message = "Module's title cannot be null or blank")
    @Length(max = 255, message = "Module's title max length is 255")
    @Schema(description = "title of Module", example = "title")
    private String title;

    @NotBlank(message = "Module's url cannot be null or blank")
    @Length(max = 255, message = "Module's url max length is 255")
    @Schema(description = "url of Module", example = "url")
    private String url;
}
