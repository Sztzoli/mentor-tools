package mentortools.commands;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mentortools.validations.InternalTime;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@InternalTime
public class UpdateTrainingClassCommand {


    @NotBlank(message = "Training class's name cannot be null or blank")
    @Length(max = 255, message = "Training class's name max length is 255")
    @Schema(description = "name of trainingClass", example = "John Doe")
    private String name;

    @Schema(description = "start date of trainingClass", example = "2021-01-01")
    private LocalDate startDate;

    @Schema(description = "end date of trainingClass", example = "2021-02-01")
    private LocalDate endDate;
}
