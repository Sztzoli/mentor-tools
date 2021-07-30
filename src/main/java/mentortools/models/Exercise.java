package mentortools.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {

    private ExerciseStatus exerciseStatus;
    private LocalDate exerciseDate;
    private String commitUrl;
}
