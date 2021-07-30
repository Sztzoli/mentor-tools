package mentortools.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {

    @Enumerated(EnumType.STRING)
    private ExerciseStatus exerciseStatus;
    private LocalDate exerciseDate;
    private String commitUrl;
}
